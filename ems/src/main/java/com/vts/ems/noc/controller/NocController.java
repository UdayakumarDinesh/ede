package com.vts.ems.noc.controller;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.vts.ems.Admin.Service.AdminService;

import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportDto;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadDto;
import com.vts.ems.noc.service.NocService;
import com.vts.ems.pis.model.Passport;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;


@Controller
public class NocController {
	private static final Logger logger = LogManager.getLogger(NocController.class);
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Autowired
	AdminService adminservice;
	
	@Autowired
	NocService service;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@Value("${ProjectFiles}")
	private String LabLogoPath;
	
	
	 public String getLabLogoAsBase64() throws IOException {

			String path = LabLogoPath + "/images/lablogos/lablogo1.png";
			try {
				return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(path)));
			} catch (FileNotFoundException e) {
				System.err.println("File Not Found at Path " + path);
			}
			return "/print/.jsp";
		}
	 
	 
	
	@RequestMapping(value = "NOC.htm")
	public String NOC(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
	    ses.setAttribute("formmoduleid", "34"); 
	    ses.setAttribute("SidebarActive", "NOC.htm");
		String UserId=(String)ses.getAttribute("Username");
		String LoginType=(String)ses.getAttribute("LoginType");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		
		logger.info(new Date()+"Inside NOC.htm "+UserId);
		try {
			
			 List<Object[]> admindashboard = adminservice.HeaderSchedulesList("12" ,LoginType);
			 return "noc/noc";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside NOC.htm "+UserId, e); 
		    return "static/Error"; 
		}
}

	@RequestMapping(value = "Passport.htm")
	public String Passport(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
		ses.setAttribute("formmoduleid", "34");  
	    ses.setAttribute("SidebarActive", "Passport_htm");
		String UserId=(String)ses.getAttribute("Username");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		String EmpId =  ses.getAttribute("EmpId").toString();
		
		logger.info(new Date()+"Inside Passport.htm "+UserId);
		try {
			
			
			String CEO = service.GetCEOEmpNo();
			List<String> PandAs = service.GetPandAAdminEmpNos();
			List<String> DGMs = service.GetDGMEmpNos();
			List<String> DHs = service.GetDHEmpNos();
			List<String> GHs = service.GetGHEmpNos();
			
			 req.setAttribute("CEOEmpNos", CEO);
			 req.setAttribute("PandAsEmpNos", PandAs);
			 req.setAttribute("DGMEmpNos", DGMs);
			 req.setAttribute("DivisionHeadEmpNos", DHs);
			 req.setAttribute("GroupHeadEmpNos", GHs);
			 
			 req.setAttribute("CeoName", service.GetCeoName());
			 req.setAttribute("PandAEmpName", service.GetPandAEmpName());
			 
			 if(!DGMs.contains(EmpNo)) {
					req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
				}
			 req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
			 req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
			 
			 req.setAttribute("EmployeeD", service.getEmpData(EmpId));
			 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
			 
			req.setAttribute("NOCPASSPORTLIST",service.getnocPassportList(EmpNo) );
			return "noc/Passport";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside Passport.htm "+UserId, e); 
		    return "static/Error"; 
		}
   }
	

	@RequestMapping(value = "PassportAdd.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PassportAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
	    String UserId=(String)ses.getAttribute("Username");
		String EmpId =  ses.getAttribute("EmpId").toString();
		logger.info(new Date()+"Inside PassportAdd.htm "+UserId);
		try {
			
			  
			  req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
			  req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
			 req.setAttribute("EmpId", EmpId);
			  return "noc/AddEditPassport";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside PassportAdd.htm "+UserId, e); 
		    return "static/Error"; 
		}
	}
	
	@RequestMapping(value = "PassportEdit.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PassportEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
	    String UserId=(String)ses.getAttribute("Username");
		String EmpId =  ses.getAttribute("EmpId").toString();
		logger.info(new Date()+"Inside PassportEdit.htm "+UserId);
		try {
			
			  
			  req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
			  req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
			  String NocPassportId=req.getParameter("NocPassportId");
			  NocPassport passport=service.getNocPassportId(Long.parseLong(NocPassportId));
			  req.setAttribute("passport",passport);
			  return "noc/AddEditPassport";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside PassportEdit.htm "+UserId, e); 
		    return "static/Error"; 
		}
	}
		@RequestMapping(value = "PassportAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String PassportAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside PassportAddSubmit.htm "+UserId);
			try {
				
				
				Passport pport= new Passport();
				
				pport.setEmpId(EmpId);
				pport.setPassportType(req.getParameter("PassportType"));
				pport.setStatus(req.getParameter("Status"));
				pport.setPassportNo(req.getParameter("PassportNo"));
				pport.setValidFrom(DateTimeFormatUtil.dateConversionSql(req.getParameter("ValidFrom")));
				pport.setValidTo(DateTimeFormatUtil.dateConversionSql(req.getParameter("ValidTo")));
				pport.setCreatedBy(UserId);
				pport.setCreatedDate(sdf.format(new Date()));
				
				
				NocPassportDto dto=  NocPassportDto.builder()
						 .EmpNo(EmpNo)
						 .PassportExist(req.getParameter("PassportExist"))
						 .RelationType(req.getParameter("RelationType"))
						 .RelationName(req.getParameter("RelationName"))
						 .RelationOccupation(req.getParameter("RelationOccupation"))
						 .RelationAddress(req.getParameter("RelationAddress"))
						 .RelationAbroad(req.getParameter("RelationAbroad"))
						 .EmployementDetails(req.getParameter("EmployementDetails"))
						 .LostPassport(req.getParameter("LostPassport"))
						 .PassportType(req.getParameter("Passporttype"))
						 .ContractualObligation(req.getParameter("ContractualObligation"))
						 .FromDate(sdf.format(rdf.parse(req.getParameter("fromdate"))))
					     .ToDate(sdf.format(rdf.parse(req.getParameter("todate"))))
				         .build();
				
				long save=service.NocPassportAdd(dto,UserId,pport);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "NOC Passport Added Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "NOC Passport Add Unsuccessful");
	 			}
				  
				  
				  return "redirect:/Passport.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside PassportAddSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
   }
	
		@RequestMapping(value = "PassportEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String PassportEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside PassportEditSubmit.htm "+UserId);
			try {
				
				String NocPassportId = req.getParameter("NocPassportId");
				NocPassportDto dto=  NocPassportDto.builder()
						 .NocPassportId(Long.parseLong(NocPassportId))
						 .PassportExist(req.getParameter("PassportExist"))
						 .PassportEntries(req.getParameter("PassportEntries"))
						 .RelationType(req.getParameter("RelationType"))
						 .RelationName(req.getParameter("RelationName"))
						 .RelationOccupation(req.getParameter("RelationOccupation"))
						 .RelationAddress(req.getParameter("RelationAddress"))
						 .RelationAbroad(req.getParameter("RelationAbroad"))
						 .EmployementDetails(req.getParameter("EmployementDetails"))
						 .LostPassport(req.getParameter("LostPassport"))
						 .PassportType(req.getParameter("Passporttype"))
						 .ContractualObligation(req.getParameter("ContractualObligation"))
						 .FromDate(sdf.format(rdf.parse(req.getParameter("fromdate"))))
					     .ToDate(sdf.format(rdf.parse(req.getParameter("todate"))))
				         .build();
				
				long save=service.NOCPassportUpdate(dto,UserId);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "NOC Passport Edited Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "NOC Passport Edited Unsuccessful");
	 			}
				  
				  
				  return "redirect:/Passport.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside PassportEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
   }
	
		
	
//	  @RequestMapping(value = "PassportNOCPrint.htm", method =
//	  {RequestMethod.GET,RequestMethod.POST}) public String
//	  PassportNOCPrint(HttpServletRequest req, HttpSession ses, RedirectAttributes
//	  redir) throws Exception {
//	  
//	  String UserId=(String)ses.getAttribute("Username"); String EmpId =
//	  ses.getAttribute("EmpId").toString(); logger.info(new
//	  Date()+"Inside PassportNOCPrint.htm "+UserId); try {
//	  
//	  req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
//	  req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId)); return
//	  "noc/PassportPrint";
//	  
//	  } catch (Exception e) { e.printStackTrace(); logger.error(new
//	  Date()+" Inside PassportNOCPrint.htm "+UserId, e); return "static/Error"; } }
//	 
//	
//	
	@RequestMapping(value = "PassportPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PassportPreview(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
	    String UserId=(String)ses.getAttribute("Username");
		String EmpId =  ses.getAttribute("EmpId").toString();
		String LoginType=(String)ses.getAttribute("LoginType").toString();
		logger.info(new Date()+"Inside PassportPreview.htm "+UserId);
		try {
			
			
			String passportid=req.getParameter("Passportid");
			String isApproval = req.getParameter("isApproval");
			System.out.println("isApproval---"+isApproval);
			
			NocPassport passport=service.getNocPassportId(Long.parseLong(passportid));
			req.setAttribute("passport",passport);
			req.setAttribute("isApproval", isApproval);
//			req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
//			req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));	
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			req.setAttribute("NocPassportFormDetails", service.getPassportFormDetails(passportid));
			req.setAttribute("LoginType", LoginType);
			 return "noc/passportpreview";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside PassportPreview.htm "+UserId, e); 
		    return "static/Error"; 
		}
   }
	
	
	@RequestMapping(value = "PassportNOCPrint.htm",method=RequestMethod.GET)
	public void PassportNOCPrint(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		String EmpId =  ses.getAttribute("EmpId").toString();
		logger.info(new Date() +"Inside PassportNOCPrint.htm "+UserId);
		
		try {
			
			
			String passportid=req.getParameter("Passportid");
				
			req.setAttribute("NocPassportDetails", service.getPassportFormDetails(passportid));
		    req.setAttribute("lablogo",getLabLogoAsBase64());
			String filename="NOCPassport-"+passportid.toString().trim().replace("/", "-");
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/noc/PassportPrint.jsp").forward(req, customResponse);
			String html = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
	         
	        res.setContentType("application/pdf");
	        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
	       
	       
	        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
	        File f=new File(path +File.separator+ filename+".pdf");
	        FileInputStream fis = new FileInputStream(f);
	        DataOutputStream os = new DataOutputStream(res.getOutputStream());
	        res.setHeader("Content-Length",String.valueOf(f.length()));
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = fis.read(buffer)) >= 0) {
	            os.write(buffer, 0, len);
	        } 
	        os.close();
	        fis.close();
	       
	       
	        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
	        Files.delete(pathOfFile);		
	       	
		}
			   
		
		catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside PassportNOCPrint.htm "+UserId, e); 
		}

	}
	 @RequestMapping(value = "NOCPassportTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCPassportTransaction(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCPassportTransactionStatus.htm "+UserId);
			try {
				String passportid=req.getParameter("PassportId");
				System.out.println("passportid---"+req.getParameter("PassportId"));
				req.setAttribute("TransactionList", service.NOCPassportTransactionList(passportid));
				
				return "noc/NocTranscationStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCPassportTransactionStatus.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 @RequestMapping(value = "NOCPassportForward.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCPassportForward(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		 
		    String UserId = (String) ses.getAttribute("Username");
		    String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside NOCPassportForward.htm "+UserId);
			try {
				
				String passportid=req.getParameter("passportid");
				System.out.println("passportid---"+req.getParameter("passportid"));
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				NocPassport Noc=service.getNocPassportById(Long.parseLong(passportid));
				String NocStatusCode = Noc.getNocStatusCode();
				
//				NocPassportDto dto=  NocPassportDto.builder()
//						.NocPassportId(Long.parseLong(passportid))
//						.NocStatusCode(NocStatusCode)
//						.Remarks(req.getParameter("remarks"))
//				        .build();
				
				long save=service.NOCPassportForward(passportid,UserId,action,remarks,EmpNo,LoginType);
				if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
						NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE")) {
							if (save > 0) {
								redir.addAttribute("result", "NOC Passport Forwarded Successfully");
							} else {
								redir.addAttribute("resultfail", "NOC Passport Forward Unsuccessful");	
							}	
							return "redirect:/Passport.htm";
						}
						else  
						{
							if (save > 0) {
								redir.addAttribute("result", "NOC Passport verification Successfull");
							} else {
								redir.addAttribute("resultfail", "NOC Passport verification Unsuccessful");	
							}	
							return "redirect:/NocApproval.htm";
						}
						
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCPassportForward.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 
	 @RequestMapping(value = "NocApproval.htm")
		public String NocApproval(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	
			String Username = (String) ses.getAttribute("Username");
			ses.setAttribute("formmoduleid", "34");			
			ses.setAttribute("SidebarActive","NocApproval_htm");	
			logger.info(new Date() +"Inside NocApproval.htm"+Username);		
			try {				
				
				
				 req.setAttribute("ApprovalList", service.NocApprovalsList(EmpNo));
				
				return "noc/NocApproval";
			}catch (Exception e) {
				logger.error(new Date() +" Inside NocApproval.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}	
	 
	 @RequestMapping(value = "NOCPassportPAForm.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCPassportPAForm(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCPassportPAForm.htm "+UserId);
			try {
				
				String passportid=req.getParameter("Passportid");
				NocPassportDto dto=  NocPassportDto.builder()
						
						 .NocPassportId(Long.parseLong(passportid))
						 .PassportEntries(req.getParameter("PassportEntries"))
						 .PassportEntriesDetails(req.getParameter("Variation"))
						 .EmployeeSuspensed(req.getParameter("EmpSuspension"))
						 .EmployeeInvolvement(req.getParameter("EmpInvolvment"))
						 .EmployeeCaseDetails(req.getParameter("CaseDetails"))
						 .ContractualObligation(req.getParameter("obligation"))
						 .FromDate(sdf.format(rdf.parse(req.getParameter("FromDate"))))
						 .ToDate(sdf.format(rdf.parse(req.getParameter("ToDate"))))
						 .build();
				long save=service.PandAFromUpdate(dto,UserId);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "Details Updated  Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "Details Update Unsuccessful");
	 			}
				  redir.addAttribute("Passportid", passportid);
				  redir.addAttribute("isApproval","Y");
				return "redirect:/PassportPreview.htm";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCPassportPAForm.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
//	 @RequestMapping(value = "PassportNOCCertificate.htm")
//		public String PassportNOCCertificate(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
//		{
//			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
//			String EmpNo = (String) ses.getAttribute("EmpNo");
//	    	
//			String Username = (String) ses.getAttribute("Username");
//			ses.setAttribute("formmoduleid", "34");			
//			ses.setAttribute("SidebarActive","PassportNOCCertificate_htm");	
//			logger.info(new Date() +"Inside PassportNOCCertificate.htm"+Username);		
//			try {				
//				
//				String passportid=req.getParameter("Passportid");
//				
//				 req.setAttribute("ApprovalList", service.NocApprovalsList(EmpNo));
//				 req.setAttribute("NocPassportDetails", service.getPassportFormDetails(passportid));
//				 
//				return "noc/NocPassportCertificate";
//				
//			}catch (Exception e) {
//				logger.error(new Date() +" Inside PassportNOCCertificate.htm"+Username, e);
//				e.printStackTrace();	
//				return "static/Error";
//			}
//			
//		}
	 
	 @RequestMapping(value = "PassportNOCCertificate.htm", method=RequestMethod.GET)
		public void PassportNOCCertificate(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside PassportNOCCertificate.htm "+UserId);
			
			try {
				   
				    String passportid=req.getParameter("Passportid");
				    req.setAttribute("Labmaster", service.getLabMasterDetails().get(0));
				    req.setAttribute("lablogo",getLabLogoAsBase64());
				    req.setAttribute("ApprovalList", service.NocApprovalsList(EmpNo));
				    req.setAttribute("NocPassportDetails", service.getPassportFormDetails(passportid));
				    String path = req.getServletContext().getRealPath("/view/temp");
				    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
					req.getRequestDispatcher("/view/noc/NocPassportCertificate.jsp").forward(req, customResponse);
					String html = customResponse.getOutput();
					byte[] data = html.getBytes();
					InputStream fis1 = new ByteArrayInputStream(data);
					PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/PassportCertificate.pdf"));
					pdfDoc.setTagged();
					Document document = new Document(pdfDoc, PageSize.LEGAL);
					document.setMargins(50, 100, 150, 50);
					ConverterProperties converterProperties = new ConverterProperties();
					FontProvider dfp = new DefaultFontProvider(true, true, true);
					converterProperties.setFontProvider(dfp);
					HtmlConverter.convertToPdf(fis1, pdfDoc, converterProperties);
					/*
					 * document.close(); pdfDoc.close(); fis1.close();
					 */
					res.setContentType("application/pdf");
					res.setHeader("Content-disposition", "inline;filename=PassportCertificate.pdf");
					File f = new File(path + "/PassportCertificate.pdf");
					FileInputStream fis = new FileInputStream(f);
					DataOutputStream os = new DataOutputStream(res.getOutputStream());
					res.setHeader("Content-Length", String.valueOf(f.length()));
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) >= 0) {
						os.write(buffer, 0, len);
					}
					os.close();
					fis.close();
					Path pathOfFile2 = Paths.get(path + "/PassportCertificate.pdf");
					Files.delete(pathOfFile2);
				    

				} catch (Exception e) {
					logger.error(new Date() + " Getting Error From  PassportNOCCertificate" + UserId, e);
				}
			
		}
		
	

	@RequestMapping(value = "ProceedingAbroad.htm")
		public String ProceedingAbroad(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
			ses.setAttribute("formmoduleid", "34");  
		    ses.setAttribute("SidebarActive", "ProceedingAbroad_htm");
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			String EmpId =  ses.getAttribute("EmpId").toString();
			
			logger.info(new Date()+"Inside Passport.htm "+UserId);
			try {
				
				
				String CEO = service.GetCEOEmpNo();
				List<String> PandAs = service.GetPandAAdminEmpNos();
				List<String> DGMs = service.GetDGMEmpNos();
				List<String> DHs = service.GetDHEmpNos();
				List<String> GHs = service.GetGHEmpNos();
				
				 req.setAttribute("CEOEmpNos", CEO);
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("DGMEmpNos", DGMs);
				 req.setAttribute("DivisionHeadEmpNos", DHs);
				 req.setAttribute("GroupHeadEmpNos", GHs);
				 
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("PandAEmpName", service.GetPandAEmpName());
				 
				 if(!DGMs.contains(EmpNo)) {
						req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
					}
				 req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
				 req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
				 
				 req.setAttribute("EmployeeD", service.getEmpData(EmpId));
				 req.setAttribute("NocProcAbraodList",service.getProcAbroadList(EmpNo));
				
				return "noc/ProceedingAbroad";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside Passport.htm "+UserId, e); 
			    return "static/Error"; 
			}
	   }
	 
	 @RequestMapping(value = "ProcAbroadAdd.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ProcAbroadAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	
			String Username = (String) ses.getAttribute("Username");
		
			logger.info(new Date() +"Inside ProcAbroadAdd.htm"+Username);		
			try {				
				
				 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
				 req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
				 req.setAttribute("EmpId", EmpId);
				 return "noc/AddEditProcAbroad";
			}catch (Exception e) {
				logger.error(new Date() +" Inside ProcAbroadAdd.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	 
	 @RequestMapping(value = "ProcAbroadEdit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ProcAbroadEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	
			String Username = (String) ses.getAttribute("Username");
		
			logger.info(new Date() +"Inside ProcAbroadEdit.htm"+Username);		
			try {				
				 
				 String ProcAbrId=req.getParameter("ProcAbrId");
				 
				 NocProceedingAbroad ProcAbroad =service.getNocProceedingAbroadById(Long.parseLong(ProcAbrId));
				 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
				 req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
				 req.setAttribute("EmpId", EmpId);
				 req.setAttribute("ProcAbroad", ProcAbroad);
				 return "noc/AddEditProcAbroad";
			}catch (Exception e) {
				logger.error(new Date() +" Inside ProcAbroadEdit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	 
	 @RequestMapping(value = "ProcAbroadAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ProcAbroadAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("FormFile") MultipartFile FormFile)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside ProcAbroadAddSubmit.htm "+UserId);
			try {
				
				NocProceedingAbroadDto dto=  NocProceedingAbroadDto.builder()
						
						 .EmpNo(EmpNo)
						 .RelationType(req.getParameter("RelationType"))
						 .RelationName(req.getParameter("RelationName"))
						 .RelationOccupation(req.getParameter("RelationOccupation"))
						 .RelationAddress(req.getParameter("RelationAddress"))
						 .RelationAbroad(req.getParameter("RelationAbroad"))
						 .EmployementDetails(req.getParameter("EmployementDetails"))
						 .EmployeeInvolvement(req.getParameter("EmployeeInvolvement"))
						 .PropertyFiled(req.getParameter("PropertyFiled"))
						 .ForeignVisit(req.getParameter("ForeignVisit"))
						 .ForeignVisitDetails(req.getParameter("ForeignVisitDetails"))
						 .CountriesProposed(req.getParameter("CountriesProposed"))
						 .DepartureDate(sdf.format(rdf.parse(req.getParameter("DepartureDate"))))
						 .VisitPurpose(req.getParameter("VisitPurpose"))
						 .StayDuration(Long.parseLong(req.getParameter("StayDuration")))
						 .ReturnDate(sdf.format(rdf.parse(req.getParameter("ReturnDate"))))
						 .Going(req.getParameter("Going"))
						 .FamilyDetails(req.getParameter("FamilyDetails"))
						 .ExpectedAmount(Long.parseLong(req.getParameter("ExpectedAmount")))
						 .FinancedBy(req.getParameter("FinancedBy"))
						 .AmountSpend(Long.parseLong(req.getParameter("AmountSpend")))
						 .NameNationality(req.getParameter("NameNationality"))
						 .Relationship(req.getParameter("Relationship"))
						 .RelationshipAddress(req.getParameter("RelationshipAddress"))
						 .FormFile(FormFile)
						 .FileName(EmpNo)
						 .LostPassport(req.getParameter("LostPassport"))
						 .PassportType(req.getParameter("Passporttype"))
						 .ContractualObligation(req.getParameter("ContractualObligation"))
						 .FromDate(sdf.format(rdf.parse(req.getParameter("fromdate"))))
					     .ToDate(sdf.format(rdf.parse(req.getParameter("todate"))))
					     .Hospatility(req.getParameter("Hospatility"))
				         .build();
				
				long save=service.NocProcAbroadAdd(dto,UserId);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "NOC Proceeding Abroad Added Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "NOC Proceeding Abroad Add Unsuccessful");
	 			}
				  
				  
				  return "redirect:/ProceedingAbroad.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ProcAbroadAddSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
      }
		
	 @RequestMapping(value = "ProcAbroadEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ProcAbroadEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("FormFile") MultipartFile FormFile)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside ProcAbroadEditSubmit.htm "+UserId);
			try {
				
				 String ProcAbrId=req.getParameter("ProcAbroadId");
				 System.out.println("ProcAbrId---"+ProcAbrId);
				 
				 NocProceedingAbroadDto dto=  NocProceedingAbroadDto.builder()
						
						 .NocProcId(Long.parseLong(ProcAbrId))
						 .EmpNo(EmpNo)
						 .RelationType(req.getParameter("RelationType"))
						 .RelationName(req.getParameter("RelationName"))
						 .RelationOccupation(req.getParameter("RelationOccupation"))
						 .RelationAddress(req.getParameter("RelationAddress"))
						 .RelationAbroad(req.getParameter("RelationAbroad"))
						 .EmployementDetails(req.getParameter("EmployementDetails"))
						 .EmployeeInvolvement(req.getParameter("EmployeeInvolvement"))
						 .PropertyFiled(req.getParameter("PropertyFiled"))
						 .ForeignVisit(req.getParameter("ForeignVisit"))
						 .ForeignVisitDetails(req.getParameter("ForeignVisitDetails"))
						 .CountriesProposed(req.getParameter("CountriesProposed"))
						 .DepartureDate(sdf.format(rdf.parse(req.getParameter("DepartureDate"))))
						 .VisitPurpose(req.getParameter("VisitPurpose"))
						 .StayDuration(Long.parseLong(req.getParameter("StayDuration")))
						 .ReturnDate(sdf.format(rdf.parse(req.getParameter("ReturnDate"))))
						 .Going(req.getParameter("Going"))
						 .FamilyDetails(req.getParameter("FamilyDetails"))
						 .ExpectedAmount(Long.parseLong(req.getParameter("ExpectedAmount")))
						 .FinancedBy(req.getParameter("FinancedBy"))
						 .AmountSpend(Long.parseLong(req.getParameter("AmountSpend")))
						 .NameNationality(req.getParameter("NameNationality"))
						 .Relationship(req.getParameter("Relationship"))
						 .RelationshipAddress(req.getParameter("RelationshipAddress"))
						 .FormFile(FormFile)
						 .FileName(EmpNo)
						 .LostPassport(req.getParameter("LostPassport"))
						 .PassportType(req.getParameter("Passporttype"))
						 .ContractualObligation(req.getParameter("ContractualObligation"))
						 .FromDate(sdf.format(rdf.parse(req.getParameter("fromdate"))))
					     .ToDate(sdf.format(rdf.parse(req.getParameter("todate"))))
					     .Hospatility(req.getParameter("Hospatility"))
				         .build();
				
				long save=service.NocProcAbroadUpdate(dto,UserId);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "NOC Proceeding Abroad Updated Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "NOC Proceeding Abroad Updated Unsuccessful");
	 			}
				  
				  
				  return "redirect:/ProceedingAbroad.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ProcAbroadEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
      }
	 
	 
	 
	  @RequestMapping(value = "NocProcAbroadDownload.htm")
	    public void TicketFormDownload(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
		{				
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside NocProcAbroadDownload.htm "+UserId);
			try {
				
				String ProcAbrId=req.getParameter("ProcAbrId");
				System.out.println("ProcAbrId---"+ProcAbrId);
				NocProceedingAbroad form=service.getNocProceedingAbroadById(Long.parseLong(ProcAbrId));
				res.setContentType("Application/octet-stream");	
				File my_file=new File(emsfilespath+form.getFilePath());
				res.setHeader("Content-disposition","attachment;filename="+form.getFileName());
				OutputStream out=res.getOutputStream();
				FileInputStream in=new FileInputStream(my_file);
				byte[] buffer=new byte[4096];
				int length;
				while((length=in.read(buffer))>0){
				out.write(buffer,0,length);
				}
				in.close();
				out.flush();
				out.close();
			}catch(Exception e) {
				logger.error(new Date() +"Inside NocProcAbroadDownload.htm "+UserId,e);
				e.printStackTrace();
			}
			
		}
	 @RequestMapping(value = "NOCProcTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCProcTransactionStatus(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCProcTransactionStatus.htm "+UserId);
			try {
				
				String ProcAbrId=req.getParameter("ProcAbrId");
				System.out.println("ProcAbrId---"+req.getParameter("ProcAbrId"));
				req.setAttribute("TransactionList", service.NOCProcAbroadTransactionList(ProcAbrId));
				
				return "noc/NocTranscationStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCProcTransactionStatus.htm "+UserId, e);
				return "static/Error";
			}
		}



	@RequestMapping(value = "ProcAbroadPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String ProcAbroadPreview(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
	    String UserId=(String)ses.getAttribute("Username");
	String EmpId =  ses.getAttribute("EmpId").toString();
	String LoginType=(String)ses.getAttribute("LoginType").toString();
	logger.info(new Date()+"Inside ProcAbroadPreview.htm "+UserId);
	try {
		
		
		
		String ProcAbrId=req.getParameter("ProcAbrId");
		String isApproval = req.getParameter("isApproval");
		req.setAttribute("isApproval", isApproval);
	//		req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
	//		req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));	
		req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
		req.setAttribute("NocProcAbroadDetails",service.getNocProcAbroadDetails(ProcAbrId) );
		
		 return "noc/ProcAbroadPreview";
				 
	} catch (Exception e) 
	 { 
		e.printStackTrace(); 
	    logger.error(new Date()+" Inside ProcAbroadPreview.htm "+UserId, e); 
	    return "static/Error"; 
		}
	}
	
	
	@RequestMapping(value = "ProcAbroadPrint.htm",method=RequestMethod.GET)
	public String ProcAbroadPrint(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		String EmpId =  ses.getAttribute("EmpId").toString();
		logger.info(new Date() +"Inside ProcAbroadPrint.htm "+UserId);
		
		try {
			
			
			String ProcAbrId=req.getParameter("ProcAbrId");
				
			
			req.setAttribute("NocProcAbroadDetails",service.getNocProcAbroadDetails(ProcAbrId));

			String filename="ProceedingAbroad-"+ProcAbrId.toString().trim().replace("/", "-");
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/noc/ProcAbroadPrint.jsp").forward(req, customResponse);
			String html = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
	         
	        res.setContentType("application/pdf");
	        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
	       
	        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
	        File f=new File(path +File.separator+ filename+".pdf");
	        FileInputStream fis = new FileInputStream(f);
	        DataOutputStream os = new DataOutputStream(res.getOutputStream());
	        res.setHeader("Content-Length",String.valueOf(f.length()));
	        byte[] buffer = new byte[1024];
	        int len = 0;
            while ((len = fis.read(buffer)) >= 0) {
	            os.write(buffer, 0, len);
	        } 
	        os.close();
	        fis.close();
	       
	       
	        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
	        Files.delete(pathOfFile);	
			
		}
			   
		catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside ProcAbroadPrint.htm "+UserId, e); 
		}
		return "";

	}
	
	@RequestMapping(value = "NOCProcAbroadForward.htm" , method={RequestMethod.POST,RequestMethod.GET})
	public String NOCProcAbroadForward(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
	 
	    String UserId = (String) ses.getAttribute("Username");
	    String EmpNo = (String) ses.getAttribute("EmpNo");
    	String LoginType=(String)ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside NOCProcAbroadForward.htm "+UserId);
		try {
			
			
			String ProcAbroadId=req.getParameter("ProcAbroadId");
			System.out.println("ProcAbroadId---"+req.getParameter("ProcAbroadId"));
			String action = req.getParameter("Action");
			String remarks = req.getParameter("remarks");
			
			NocProceedingAbroad Noc=service.getNocProceedingAbroadById(Long.parseLong(ProcAbroadId));
			String NocStatusCode = Noc.getNocStatusCode();
			
//			NocPassportDto dto=  NocPassportDto.builder()
//					.NocPassportId(Long.parseLong(passportid))
//					.NocStatusCode(NocStatusCode)
//					.Remarks(req.getParameter("remarks"))
//			        .build();
			
			long save=service.NOCProcAbraodForward(ProcAbroadId,UserId,action,remarks,EmpNo,LoginType);
			if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
					NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE")) {
						if (save > 0) {
							redir.addAttribute("result", "NOC Proceeding Abroad  Forwarded Successfully");
						} else {
							redir.addAttribute("resultfail", "NOC Proceeding Abroad  Forward Unsuccessful");	
						}	
						return "redirect:/ProceedingAbroad.htm";
					}
					else  
					{
						if (save > 0) {
							redir.addAttribute("result", "NOC Proceeding Abroad verification Successfull");
						} else {
							redir.addAttribute("resultfail", "NOC Proceeding Abroad verification Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm";
					}
					
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside NOCProcAbroadForward.htm "+UserId, e);
			return "static/Error";
		}
	}
	
}


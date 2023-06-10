package com.vts.ems.noc.controller;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.InputStream;

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
import com.vts.ems.noc.model.ExamIntimation;
import com.vts.ems.noc.model.ExamIntimationDto;
import com.vts.ems.noc.model.NocHigherEducation;
import com.vts.ems.noc.model.NocHigherEducationDto;
import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportDto;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadDto;
import com.vts.ems.noc.service.NocService;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.Passport;
import com.vts.ems.pis.service.PisService;
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
	
	@Autowired
	PisService service1;
	
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
			 List<String> Sos = service.GetSosEmpNos();
			
			 req.setAttribute("CEOEmpNos", CEO);
			 req.setAttribute("PandAsEmpNos", PandAs);
			 req.setAttribute("DGMEmpNos", DGMs);
			 req.setAttribute("DivisionHeadEmpNos", DHs);
			 req.setAttribute("GroupHeadEmpNos", GHs);
			 req.setAttribute("SOEmpNos", Sos);
			 
			 req.setAttribute("CeoName", service.GetCeoName());
			 req.setAttribute("PandAEmpName", service.GetPandAEmpName());
			 
			 if(!DGMs.contains(EmpNo)) {
					req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
				}
			 req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
			 req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
			 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			 
			 req.setAttribute("NocApprovalFlow", service.getNocApprovalFlow(EmpNo));
			
			 req.setAttribute("EmployeeD", service.getEmpData(EmpId));
			 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
			 
			
			req.setAttribute("NOCPASSPORTLIST",service.getnocPassportList(EmpNo) );
			return "noc/NocPassport";
					 
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
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date()+"Inside PassportAdd.htm "+UserId);
		try {
			
			  
			  req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
			  req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
			  
			 req.setAttribute("EmpId", EmpId);
			 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			  return "noc/NocAddEditPassport";
					 
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
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date()+"Inside PassportEdit.htm "+UserId);
		try {
			
			  
			  req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
			  req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
			  req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			  String NocPassportId=req.getParameter("NocPassportId");
			  Passport pispassport = service1.getPassportData(EmpId);
			  NocPassport passport=service.getNocPassportId(Long.parseLong(NocPassportId));
			  req.setAttribute("passport",passport);
			  req.setAttribute("pispassport",pispassport);
			  
			  return "noc/NocAddEditPassport";
					 
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
				  
				  redir.addAttribute("Passportid",save);
				  return "redirect:/PassportPreview.htm";
						 
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
				
				
	            Passport pport= new Passport();
				
				pport.setEmpId(EmpId);
				pport.setPassportType(req.getParameter("PassportType"));
				pport.setStatus(req.getParameter("Status"));
				pport.setPassportNo(req.getParameter("PassportNo"));
				pport.setValidFrom(DateTimeFormatUtil.dateConversionSql(req.getParameter("ValidFrom")));
				pport.setValidTo(DateTimeFormatUtil.dateConversionSql(req.getParameter("ValidTo")));
				pport.setCreatedBy(UserId);
				pport.setCreatedDate(sdf.format(new Date()));
				
				
				String NocPassportId = req.getParameter("Passportid");
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
				
				long save=service.NOCPassportUpdate(dto,UserId,pport);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "NOC Passport Edited Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "NOC Passport Edited Unsuccessful");
	 			}
				  
				  redir.addAttribute("Passportid",save);
				  return "redirect:/PassportPreview.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside PassportEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
   }
	
	
	@RequestMapping(value = "PassportPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PassportPreview(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
	    String UserId=(String)ses.getAttribute("Username");
		String EmpId =  ses.getAttribute("EmpId").toString();
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		String LoginType=(String)ses.getAttribute("LoginType").toString();
		logger.info(new Date()+"Inside PassportPreview.htm "+UserId);
		try {
			
			
			String passportid=req.getParameter("Passportid");
			String isApproval = req.getParameter("isApproval");
			
			String CEO = service.GetCEOEmpNo();
			List<String> PandAs = service.GetPandAAdminEmpNos();
			NocPassport passport=service.getNocPassportId(Long.parseLong(passportid));
			req.setAttribute("CeoName", service.GetCeoName());
			
			req.setAttribute("NOCPassportRemarkHistory",service.getPassportRemarksHistory(passportid) );
			req.setAttribute("passport",passport);
			req.setAttribute("isApproval", isApproval);
			req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			req.setAttribute("NOCPassportApprovalData", service.getNOCPassportApprovalData(passportid));
            req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			req.setAttribute("NocPassportFormDetails", service.getPassportFormDetails(passportid));
			req.setAttribute("LoginType", LoginType);
			req.setAttribute("CEOempno", CEO);
			req.setAttribute("PandAsEmpNos", PandAs);
			 
			 return "noc/NocPassportPreview";
					 
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
			req.setAttribute("CeoName", service.GetCeoName());		
			req.setAttribute("NocPassportDetails", service.getPassportFormDetails(passportid));
		    req.setAttribute("lablogo",getLabLogoAsBase64());
		    req.setAttribute("NOCPassportApprovalData", service.getNOCPassportApprovalData(passportid));
		    String path = req.getServletContext().getRealPath("/view/temp");
		    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/noc/NocPassportPrint.jsp").forward(req, customResponse);
			String html = customResponse.getOutput();
			byte[] data = html.getBytes();
			InputStream fis1 = new ByteArrayInputStream(data);
			PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/NocPassport.pdf"));
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
			res.setHeader("Content-disposition", "inline;filename=NocPassport.pdf");
			File f = new File(path + "/NocPassport.pdf");
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
			Path pathOfFile2 = Paths.get(path + "/NocPassport.pdf");
			Files.delete(pathOfFile2);

		    
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
				
				String passportid=req.getParameter("Passportid");
				
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				NocPassport Noc=service.getNocPassportById(Long.parseLong(passportid));
				String NocStatusCode = Noc.getNocStatusCode();
				
			
				long save=service.NOCPassportForward(passportid,UserId,action,remarks,EmpNo,LoginType);
				if(action.equalsIgnoreCase("A")) {
					
				
				if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
						NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RSO") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE")) {
							if (save > 0) {
								redir.addAttribute("result", "NOC Passport Forwarded Successfully");
							} else {
								redir.addAttribute("resultfail", "NOC Passport Forward Unsuccessful");	
							}	
							return "redirect:/Passport.htm";
						}
				
				

				   else if(NocStatusCode.equalsIgnoreCase("FWD") || NocStatusCode.equalsIgnoreCase("VGI")  || NocStatusCode.equalsIgnoreCase("VDI") || NocStatusCode.equalsIgnoreCase("VDG") ) { 
						   
					   
					   if (save > 0) {
							redir.addAttribute("result", "NOC Passport Recommended Successful");
						} else {
							redir.addAttribute("resultfail", "NOC Passport Recommend Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
				   else if(NocStatusCode.equalsIgnoreCase("VSO")) {
						  
					   
					   if (save > 0) {
							redir.addAttribute("result", "NOC Passport Verified Successful");
						} else {
							redir.addAttribute("resultfail", "NOC Passport Verify Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
                   else if(NocStatusCode.equalsIgnoreCase("VPA")) {
						  
					     if (save > 0) {
							redir.addAttribute("result", "NOC Passport Approved  Successful");
						} else {
							redir.addAttribute("resultfail", "NOC Passport Approve Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
				}
				
				if(action.equalsIgnoreCase("D")) 
				{
					if (save > 0) {
						redir.addAttribute("result", "NOC Passport DisApprove Successfull");
					} else {
						redir.addAttribute("resultfail", "NOC Passport DisApprove Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm";
				}
				
				
				if(action.equalsIgnoreCase("R")) 
				{
					if (save > 0) {
						redir.addAttribute("result", "NOC Passport Returned Successful");
					} else {
						redir.addAttribute("resultfail", "NOC Passport Return Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm";
				}
				
				
				return "redirect:/NocApproval.htm";	
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
	    	String LoginType=(String)ses.getAttribute("LoginType");
			ses.setAttribute("formmoduleid", "34");			
			ses.setAttribute("SidebarActive","NocApproval_htm");	
			logger.info(new Date() +"Inside NocApproval.htm"+Username);		
			try {				
				
				
				String fromdate = req.getParameter("fromdate");
				String todate = req.getParameter("todate");
				
				LocalDate today=LocalDate.now();
				
				if(fromdate==null) 
				{
					
					fromdate=today.withDayOfMonth(1).toString();
					todate = today.toString();
					
				}else
				{
					fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
					todate=DateTimeFormatUtil.RegularToSqlDate(todate);
				}
	
				 List<String> PandAs = service.GetPandAAdminEmpNos();
				 List<String> Sos = service.GetSosEmpNos();
				 req.setAttribute("fromdate", fromdate);
				 req.setAttribute("todate", todate);
				 req.setAttribute("tab", req.getParameter("tab"));
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("SosEmpNos", Sos);
				 req.setAttribute("ApprovalList", service.NocApprovalsList(EmpNo));
				 req.setAttribute("ApprovedList", service.NocApprovedList(EmpNo,fromdate,todate));
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 req.setAttribute("LoginType",LoginType);
				 
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
				 // redir.addAttribute("isApproval","Y");
				return "redirect:/PassportPreview.htm";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCPassportPAForm.htm "+UserId, e);
				return "static/Error";
			}
		}
	 

	 
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
				    req.setAttribute("EmpGenderPassport",service.getEmpGenderPassport(passportid) );
				  
				    req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
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
				List<String> Sos = service.GetSosEmpNos();
				 
				 req.setAttribute("CEOEmpNos", CEO);
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("DGMEmpNos", DGMs);
				 req.setAttribute("DivisionHeadEmpNos", DHs);
				 req.setAttribute("GroupHeadEmpNos", GHs);
				 req.setAttribute("SOEmpNos", Sos);
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("PandAEmpName", service.GetPandAEmpName());
				 
				 if(!DGMs.contains(EmpNo)) {
						req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
					}
				 req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
				 req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
				 req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
				 req.setAttribute("EmployeeD", service.getEmpData(EmpId));
				 req.setAttribute("NocProcAbraodList",service.getProcAbroadList(EmpNo));
				 req.setAttribute("NocApprovalFlow", service.getNocApprovalFlow(EmpNo));
				
				return "noc/NocProceedingAbroad";
						 
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
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 
				 return "noc/NocAddEditProcAbroad";
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
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 
				 return "noc/NocAddEditProcAbroad";
			}catch (Exception e) {
				logger.error(new Date() +" Inside ProcAbroadEdit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	 
	 @RequestMapping(value = "ProcAbroadAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ProcAbroadAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
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
						 .StayDuration(req.getParameter("StayDuration"))
						 .ReturnDate(sdf.format(rdf.parse(req.getParameter("ReturnDate"))))
						 .Going(req.getParameter("Going"))
						 .FamilyDetails(req.getParameter("FamilyDetails"))
						 .ExpectedAmount(req.getParameter("ExpectedAmount"))
						 .FinancedBy(req.getParameter("FinancedBy"))
						 .AmountSource(req.getParameter("AmountSource"))
						 .Name(req.getParameter("Name1"))
						 .Nationality(req.getParameter("Nationality"))
						 .Relationship(req.getParameter("Relationship"))
						 .RelationshipAddress(req.getParameter("RelationshipAddress"))
						 .LostPassport(req.getParameter("LostPassport"))
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
				  
				  redir.addAttribute("ProcAbrId",save);
				  return "redirect:/ProcAbroadPreview.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ProcAbroadAddSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
      }
		
	 @RequestMapping(value = "ProcAbroadEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ProcAbroadEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside ProcAbroadEditSubmit.htm "+UserId);
			try {
				
				 String ProcAbrId=req.getParameter("ProcAbroadId");
				
				
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
						 .StayDuration(req.getParameter("StayDuration"))
						 .ReturnDate(sdf.format(rdf.parse(req.getParameter("ReturnDate"))))
						 .Going(req.getParameter("Going"))
						 .FamilyDetails(req.getParameter("FamilyDetails"))
						 .ExpectedAmount(req.getParameter("ExpectedAmount"))
						 .FinancedBy(req.getParameter("FinancedBy"))
						 .AmountSource(req.getParameter("AmountSource"))
						 .Name(req.getParameter("Name1"))
						 .Nationality(req.getParameter("Nationality"))
						 .Relationship(req.getParameter("Relationship"))
						 .RelationshipAddress(req.getParameter("RelationshipAddress"))
						 .LostPassport(req.getParameter("LostPassport"))
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
				  
				  redir.addAttribute("ProcAbrId",save);
				  return "redirect:/ProcAbroadPreview.htm";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ProcAbroadEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
      }
	 
	 
	 
	 
	 @RequestMapping(value = "NOCProcTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCProcTransactionStatus(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCProcTransactionStatus.htm "+UserId);
			try {
				
				String ProcAbrId=req.getParameter("ProcAbrId");
				
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
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		String LoginType=(String)ses.getAttribute("LoginType").toString();
	    logger.info(new Date()+"Inside ProcAbroadPreview.htm "+UserId);
	  try {
		
		
		List<String> GHs = service.GetGHEmpNos();
		List<String> DGMs = service.GetDGMEmpNos();
		List<String> DHs = service.GetDHEmpNos();
		 List<String> Sos = service.GetSosEmpNos();
		String CEO = service.GetCEOEmpNo();
		List<String> PandAs = service.GetPandAAdminEmpNos();
		String ProcAbrId=req.getParameter("ProcAbrId");
		String isApproval = req.getParameter("isApproval");
		
		NocProceedingAbroad ProcAbroad =service.getNocProceedingAbroadById(Long.parseLong(ProcAbrId));
			
		req.setAttribute("isApproval", isApproval);
	    req.setAttribute("GroupHeadEmpNos", GHs);
	    req.setAttribute("PandAsEmpNos", PandAs);
		req.setAttribute("DGMEmpNos", DGMs);
		req.setAttribute("DivisionHeadEmpNos", DHs);
		req.setAttribute("NOCProceedingAbroadRemarkHistory",service.getProceedinAbraodRemarksHistory(ProcAbrId));
		req.setAttribute("EmpData",service.getEmpNameDesig(EmpNo));
		req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
		req.setAttribute("NocProcAbroadDetails",service.getNocProcAbroadDetails(ProcAbrId) );
		req.setAttribute("ApprovalData", service.getNocProcAbroadApprovalData(ProcAbrId));
		req.setAttribute("EmpNo",EmpNo);
		req.setAttribute("ProcAbroad", ProcAbroad);
		req.setAttribute("CEOempno", CEO);
		req.setAttribute("SOEmpNos", Sos);
		req.setAttribute("PandAsEmpNos", PandAs);
		 req.setAttribute("CeoName", service.GetCeoName());
		
		 return "noc/NocProcAbroadPreview";
				 
	} catch (Exception e) 
	 { 
		e.printStackTrace(); 
	    logger.error(new Date()+" Inside ProcAbroadPreview.htm "+UserId, e); 
	    return "static/Error"; 
		}
	}
	
	
	
	@RequestMapping(value = "ProcAbroadPrint.htm",method=RequestMethod.GET)
	public void ProcAbroadPrint(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		String EmpId =  ses.getAttribute("EmpId").toString();
		logger.info(new Date() +"Inside ProcAbroadPrint.htm "+UserId);
		
		try {
			
			    String ProcAbrId=req.getParameter("ProcAbrId");
			    req.setAttribute("CeoName", service.GetCeoName());
			    req.setAttribute("NocProcAbroadDetails",service.getNocProcAbroadDetails(ProcAbrId));
			    req.setAttribute("NOCProceedingAbroadRemarkHistory",service.getProceedinAbraodRemarksHistory(ProcAbrId));
			    req.setAttribute("lablogo",getLabLogoAsBase64());
				req.setAttribute("ApprovalData", service.getNocProcAbroadApprovalData(ProcAbrId));
			    String path = req.getServletContext().getRealPath("/view/temp");
			    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/noc/NocProcAbroadPrint.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();
				byte[] data = html.getBytes();
				InputStream fis1 = new ByteArrayInputStream(data);
				PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/NOCProceedingAbroad.pdf"));
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
				res.setHeader("Content-disposition", "inline;filename=NOCProceedingAbroad.pdf");
				File f = new File(path + "/NOCProceedingAbroad.pdf");
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
				Path pathOfFile2 = Paths.get(path + "/NOCProceedingAbroad.pdf");
				Files.delete(pathOfFile2);
	
			
		}
			   
		catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside ProcAbroadPrint.htm "+UserId, e); 
		}
		

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
			
			String action = req.getParameter("Action");
			String remarks = req.getParameter("remarks");
			
			NocProceedingAbroad Noc=service.getNocProceedingAbroadById(Long.parseLong(ProcAbroadId));
			String NocStatusCode = Noc.getNocStatusCode();
			
            long save=service.NOCProcAbraodForward(ProcAbroadId,UserId,action,remarks,EmpNo,LoginType);
            
			if(action.equalsIgnoreCase("A")) {
				
			if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
					NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RSO") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE")) {
						if (save > 0) {
							redir.addAttribute("result", "NOC Proceeding Abroad Forwarded Successfull");
						} else {
							redir.addAttribute("resultfail", "NOC Proceeding Abroad Forward Unsuccessful");	
						}	
						return "redirect:/ProceedingAbroad.htm";
					}
			
			
			
			   else if(NocStatusCode.equalsIgnoreCase("FWD") || NocStatusCode.equalsIgnoreCase("VGI")  || NocStatusCode.equalsIgnoreCase("VDI") || NocStatusCode.equalsIgnoreCase("VDG") ) { 
					   
				   
				   if (save > 0) {
						redir.addAttribute("result", "NOC Proceeding Abroad Recommended Successful");
					} else {
						redir.addAttribute("resultfail", "NOC Proceeding Abroad Recommend Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm"; 
				   
			   }
			   
			   else if(NocStatusCode.equalsIgnoreCase("VSO")) {
					  
				   
				   if (save > 0) {
						redir.addAttribute("result", "NOC Proceeding Abroad Verified Successful");
					} else {
						redir.addAttribute("resultfail", "NOC Proceeding Abroad Verify Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm"; 
				   
			   }
			   
               else if(NocStatusCode.equalsIgnoreCase("VPA")) {
					  
				     if (save > 0) {
						redir.addAttribute("result", "NOC Proceeding Abroad Approved  Successful");
					} else {
						redir.addAttribute("resultfail", "NOC Proceeding Abroad Approve Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm"; 
				   
			   }
			   
			}
			
			if(action.equalsIgnoreCase("D")) 
			{
				if (save > 0) {
					redir.addAttribute("result", "NOC Proceeding Abroad DisApprove Successfull");
				} else {
					redir.addAttribute("resultfail", "NOC Proceeding Abroad DisApprove Unsuccessful");	
				}	
				return "redirect:/NocApproval.htm";
			}
			
			
			if(action.equalsIgnoreCase("R")) 
			{
				if (save > 0) {
					redir.addAttribute("result", "NOC Proceeding Abroad Returned Successful");
				} else {
					redir.addAttribute("resultfail", "NOC Proceeding Abroad Return Unsuccessful");	
				}	
				return "redirect:/NocApproval.htm";
			}
			
			return "redirect:/ProceedingAbroad.htm";
					
					
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside NOCProcAbroadForward.htm "+UserId, e);
			return "static/Error";
		}
	}
	 @RequestMapping(value = "NOCProcAbroadDeptDetailsUpdate.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCProcAbroadDeptDetails(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCProcAbroadDeptDetailsUpdate.htm "+UserId);
			try {
				
				String ProcAbrId=req.getParameter("ProcAbrId");
				NocProceedingAbroadDto dto=  NocProceedingAbroadDto.builder()
						
						 .NocProcId(Long.parseLong(ProcAbrId))
						 .WorkHandled(req.getParameter("WorkHandled"))
						 .VisitRecommended(req.getParameter("VisitRecommended"))
						 .LeaveGranted(req.getParameter("LeaveGranted"))
						 .build();
				long save=service.DeptDetailsUpdate(dto,UserId);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "Details Updated  Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "Details Update Unsuccessful");
	 			}
				  redir.addAttribute("ProcAbrId",ProcAbrId );
				  redir.addAttribute("isApproval","Y");
				  
				return "redirect:/ProcAbroadPreview.htm";
				
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCProcAbroadDeptDetailsUpdate.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 
	 @RequestMapping(value = "NOCProcAbroadPAForm.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCProcAbroadPAForm(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCProcAbroadPAForm.htm "+UserId);
			try {
				
				String ProcAbrId=req.getParameter("ProcAbrId");
				NocProceedingAbroadDto dto=  NocProceedingAbroadDto.builder()
						
						 .NocProcId(Long.parseLong(ProcAbrId))
						 .ProcAbroadEntries(req.getParameter("PassportEntries"))
						 .ProcAbroadEntriesDetails(req.getParameter("EntryDetails"))
						 .EmployeeInvolvement(req.getParameter("EmpInvolvment"))
						 .EmployeeCaseDetails(req.getParameter("CaseDetails"))
						 .EmployeeDues(req.getParameter("EmpDues"))
						 .ContractualObligation(req.getParameter("obligation"))
						 .FromDate(sdf.format(rdf.parse(req.getParameter("FromDate"))))
						 .ToDate(sdf.format(rdf.parse(req.getParameter("ToDate"))))
						 .build();
				long save=service.ProcAbroadPandAFromUpdate(dto,UserId);
				  
				  if (save > 0) {
		  				
	                  redir.addAttribute("result", "Details Updated  Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "Details Update Unsuccessful");
	 			}
				  
				  redir.addAttribute("ProcAbrId", ProcAbrId);
				  redir.addAttribute("isApproval","Y");
				  
				return "redirect:/ProcAbroadPreview.htm";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCProcAbroadPAForm.htm "+UserId, e);
				return "static/Error";
			}
		}
	
	 
	 @RequestMapping(value = "NOCProcAbroadCertificate.htm", method=RequestMethod.GET)
		public void NOCProcAbroadCertificate(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside NOCProcAbroadCertificate.htm "+UserId);
			
			try {
				   
				    String ProcAbrId=req.getParameter("ProcAbrId");
				    req.setAttribute("Labmaster", service.getLabMasterDetails().get(0));
				    req.setAttribute("lablogo",getLabLogoAsBase64());
				    req.setAttribute("ApprovalList", service.NocApprovalsList(EmpNo));
				    req.setAttribute("NocProcAbroadDetails",service.getNocProcAbroadDetails(ProcAbrId));
				    req.setAttribute("EmpGender", service.getEmpGender(ProcAbrId));
				    req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				    String path = req.getServletContext().getRealPath("/view/temp");
				    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
					req.getRequestDispatcher("/view/noc/NocProcAbroadCertificate.jsp").forward(req, customResponse);
					String html = customResponse.getOutput();
					byte[] data = html.getBytes();
					InputStream fis1 = new ByteArrayInputStream(data);
					PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/ProceedingAbroadCertificate.pdf"));
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
					res.setHeader("Content-disposition", "inline;filename=ProceedingAbroadCertificate.pdf");
					File f = new File(path + "/ProceedingAbroadCertificate.pdf");
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
					Path pathOfFile2 = Paths.get(path + "/ProceedingAbroadCertificate.pdf");
					Files.delete(pathOfFile2);
				    

				} catch (Exception e) {
					logger.error(new Date() + " Getting Error From  NOCProcAbroadCertificate" + UserId, e);
				}
			
		}
	 
	 @RequestMapping(value = "IntimateExam.htm")
		public String IntimationForExam(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
			ses.setAttribute("formmoduleid", "34");  
		    ses.setAttribute("SidebarActive", "IntimateExam_htm");
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			String EmpId =  ses.getAttribute("EmpId").toString();
			
			logger.info(new Date()+"Inside IntimationForExam.htm "+UserId);
			try {
				
				
				String CEO = service.GetCEOEmpNo();
				List<String> PandAs = service.GetPandAAdminEmpNos();
				List<String> DGMs = service.GetDGMEmpNos();
				List<String> DHs = service.GetDHEmpNos();
				List<String> GHs = service.GetGHEmpNos();
				List<String> SOs = service.GetSOsEmpNos();
				
				 req.setAttribute("CEOEmpNos", CEO);
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("DGMEmpNos", DGMs);
				 req.setAttribute("DivisionHeadEmpNos", DHs);
				 req.setAttribute("GroupHeadEmpNos", GHs);
				 req.setAttribute("SOEmpNos", SOs);
				 
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("PandAEmpName", service.GetPandAEmpName());
				
				 
				 if(!DGMs.contains(EmpNo)) {
						req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
					}
				 req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
				 req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 
				 req.setAttribute("EmployeeD", service.getEmpData(EmpId));
				 
				 req.setAttribute("ExamIntimationDetails", service .getExamIntimationDetails(EmpNo));
				 req.setAttribute("NocApprovalFlow", service.getNocApprovalFlow(EmpNo));
				
				return "noc/IntimationForExam";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside IntimationForExam.htm "+UserId, e); 
			    return "static/Error"; 
			}
	   }
	 
	 @RequestMapping(value = "IntimationExamAdd.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String IntimationExamAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside IntimationExamAdd.htm "+UserId);
			try {
				
				
				req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				return "noc/IntimationForExamAddEdit";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside IntimationExamAdd.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
	 
	 
	 
	 @RequestMapping(value = "IntimationExamAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String IntimationExamAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside IntimationExamAddSubmit.htm "+UserId);
			try {
				
				ExamIntimationDto dto = new ExamIntimationDto();
						
						dto.setEmpNo(EmpNo);
						dto.setAdvNo(req.getParameter("AdNo"));
						dto.setAdvDate(sdf.format(rdf.parse(req.getParameter("AdDate"))));
						dto.setOrganizationName(req.getParameter("OrgName"));
						dto.setPlace(req.getParameter("Place"));
						dto.setPostName(req.getParameter("PostName"));
						dto.setPostCode(req.getParameter("PostCode"));
						dto.setPayLevel(req.getParameter("PayLevel"));
						dto.setApplicationThrough(req.getParameter("ApplThrough"));
						dto.setExamName(req.getParameter("ExamName"));
						dto.setProbableDate(sdf.format(rdf.parse(req.getParameter("ProbableDate"))));
				
						long save =service.ExamDetailsAdd(dto,UserId);
						
						 if (save > 0) {
				  				
			                  redir.addAttribute("result", "Intimation For Exam Added Succesfully");

			 			} else {
			                  redir.addAttribute("resultfail", "Intimation For Exam Add Unsuccessful");
			 			}
						 
						 redir.addAttribute("ExamId",save);
			        return "redirect:/ExamIntimationPreview.htm";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside IntimationExamAddSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
			
	 
	 
	 
	 @RequestMapping(value = "IntimationExamEdit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String IntimationExamEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside IntimationExamEdit.htm "+UserId);
			try {
				
				 String ExamId=req.getParameter("ExamId");
				 
				req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				
                ExamIntimation exam =service.getIntimationById(Long.parseLong(ExamId));
				req.setAttribute("ExamIntimation", exam);
				
			  return "noc/IntimationForExamAddEdit";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside IntimationExamEdit.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
	 
	 
	 @RequestMapping(value = "IntimationExamEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String IntimationExamUpdate(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside IntimationExamEditSubmit.htm "+UserId);
			try {
				
				 String ExamId=req.getParameter("ExamId");
				 
				 ExamIntimationDto dto = new ExamIntimationDto();
				    dto.setExamId(Long.parseLong(ExamId));
				    dto.setAdvNo(req.getParameter("AdNo"));
					dto.setAdvDate(sdf.format(rdf.parse(req.getParameter("AdDate"))));
					dto.setOrganizationName(req.getParameter("OrgName"));
					dto.setPlace(req.getParameter("Place"));
					dto.setPostName(req.getParameter("PostName"));
					dto.setPostCode(req.getParameter("PostCode"));
					dto.setPayLevel(req.getParameter("PayLevel"));
					dto.setApplicationThrough(req.getParameter("ApplThrough"));
				    dto.setExamName(req.getParameter("ExamName"));
					dto.setProbableDate(sdf.format(rdf.parse(req.getParameter("ProbableDate"))));
				 
					long update =service.ExamDetailsUpdate(dto,UserId);
					
				 if (update > 0) {
		  				
	                  redir.addAttribute("result", "Intimation For Exam Updated Succesfully");

	 			} else {
	                  redir.addAttribute("resultfail", "Intimation For Exam Update Unsuccessful");
	 			}
	 
				 
				    redir.addAttribute("ExamId",update);
			        return "redirect:/ExamIntimationPreview.htm";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside IntimationExamEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
	 
	 @RequestMapping(value = "IntimationTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String IntimationTransactionStatus(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside IntimationTransactionStatus.htm "+UserId);
			try {
				
				 String ExamId=req.getParameter("ExamId");
				
				req.setAttribute("TransactionList", service.ExamIntimationTransactionList(ExamId));
				
				return "noc/NocTranscationStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside IntimationTransactionStatus.htm "+UserId, e);
				return "static/Error";
			}
		}

	 @RequestMapping(value = "ExamIntimationPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ExamIntimationPreview(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside ExamIntimationPreview.htm "+UserId);
			try {
				
				 List<String> PandAs = service.GetPandAAdminEmpNos();
				 String ExamId=req.getParameter("ExamId");
				 System.out.println("examid------"+ExamId);
				 String isApproval = req.getParameter("isApproval");
				 
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("isApproval", isApproval);
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 req.setAttribute("RemarksHistory",service.getIntimationRemarks(ExamId) );
				 req.setAttribute("ExamIntimationDetails",service.getIntimationData(ExamId) );
				 req.setAttribute("IntimationApprovalData", service.IntimationApprovalData(ExamId));
				 req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			     return "noc/IntimateExamPreview";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ExamDetailsEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
	 

	 @RequestMapping(value = "ExamIntimationLetterDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public void ExamIntimationLetterDownload(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,HttpServletResponse res)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside ExamIntimationDownload.htm "+UserId);
			try {
				
				 String ExamId=req.getParameter("ExamId");
				 
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 req.setAttribute("ExamIntimationDetails",service.getIntimationData(ExamId) );
				 req.setAttribute("RemarksHistory",service.getIntimationRemarks(ExamId) );
				 req.setAttribute("IntimationApprovalData", service.IntimationApprovalData(ExamId));
				 req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
				 String path = req.getServletContext().getRealPath("/view/temp");
			    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/noc/IntimationExamDownload.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();
				byte[] data = html.getBytes();
				InputStream fis1 = new ByteArrayInputStream(data);
				PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/IntimationExamDownload.pdf"));
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
				res.setHeader("Content-disposition", "inline;filename=IntimationExamDownload.pdf");
				File f = new File(path + "/IntimationExamDownload.pdf");
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
				Path pathOfFile2 = Paths.get(path + "/IntimationExamDownload.pdf");
				Files.delete(pathOfFile2);
			 

				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ExamIntimationLetterDownload.htm "+UserId, e); 
			   
			}
		}
	 
	 @RequestMapping(value = "IntimationForExamForward.htm")
		public String IntimationForExamForward(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside IntimationForExamForward.htm"+Username);
			try {
				
				
				String ExamId=req.getParameter("ExamId");
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				ExamIntimation exam = service.IntimatedExam(ExamId);
				String  IntimationStatusCode = exam.getIntimateStatusCode();
				
				long count = service.IntimationForExamForward(ExamId, Username, action,remarks,EmpNo,LoginType);
				
				if(action.equalsIgnoreCase("A")) {
				
				if(IntimationStatusCode.equalsIgnoreCase("INI") || IntimationStatusCode.equalsIgnoreCase("RDG") || IntimationStatusCode.equalsIgnoreCase("RPA")
						
						||  IntimationStatusCode.equalsIgnoreCase("RSO")) {
					if (count > 0) {
						redir.addAttribute("result", "Intimation For Exam Forwarded Successful");
					} else {
						redir.addAttribute("resultfail", "Intimation For Exam  Forward Unsuccessful");	
					}	
					return "redirect:/IntimateExam.htm";
				}
				
				else if(IntimationStatusCode.equalsIgnoreCase("FWD") || IntimationStatusCode.equalsIgnoreCase("VDG") ) { 
					   
					   
					   if (count > 0) {
							redir.addAttribute("result", "NOC For Higher Education Recommended Successful");
						} else {
							redir.addAttribute("resultfail", "NOC For Higher Education Recommend Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
				   else if(IntimationStatusCode.equalsIgnoreCase("VSO") || IntimationStatusCode.equalsIgnoreCase("FWD")) {
						  
					   
					   if (count > 0) {
							redir.addAttribute("result", "NOC For Higher Education Verified Successful");
						} else {
							redir.addAttribute("resultfail", "NOC For Higher Education Verify Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				
				}
				
				if(action.equalsIgnoreCase("R"))
				{
					if (count > 0) {
						redir.addAttribute("result", "Intimation For Exam Returned Successful");
					} else {
						redir.addAttribute("resultfail", "Intimation For Exam Return Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm";
				}
				
				return "redirect:/IntimateExam.htm";
				
			}catch (Exception e) {
				logger.error(new Date() +" Inside IntimationForExamForward.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	 
	 
	 @RequestMapping(value = "HigherEducation.htm")
		public String HigherEducation(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
			ses.setAttribute("formmoduleid", "34");  
		    ses.setAttribute("SidebarActive", "HigherEducation_htm");
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			String EmpId =  ses.getAttribute("EmpId").toString();
			
			logger.info(new Date()+"Inside HigherEducation.htm "+UserId);
			try {
				
				
				String CEO = service.GetCEOEmpNo();
				List<String> PandAs = service.GetPandAAdminEmpNos();
					
				List<String> DGMs = service.GetDGMEmpNos();
				List<String> DHs = service.GetDHEmpNos();
				List<String> GHs = service.GetGHEmpNos();
				List<String> Sos = service.GetSosEmpNos();
				
				 req.setAttribute("CEOEmpNos", CEO);
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("DGMEmpNos", DGMs);
				 req.setAttribute("DivisionHeadEmpNos", DHs);
				 req.setAttribute("GroupHeadEmpNos", GHs);
				 req.setAttribute("SOEmpNos", Sos);
				 
				 req.setAttribute("NocApprovalFlow", service.getNocApprovalFlow(EmpNo));
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("PandAEmpName", service.GetPandAEmpName());
				 
				 if(!DGMs.contains(EmpNo)) {
					 
						req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
					}
				 req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
				 req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 
				req.setAttribute("EmployeeD", service.getEmpData(EmpId));
				req.setAttribute("NOCHigherEducationList",service.getNOCHigherEducationList(EmpNo));
				 req.setAttribute("EmpQuali",service.getEmpQualification(EmpNo));
				 
				return "noc/NocHigherEducation";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside HigherEducation.htm "+UserId, e); 
			    return "static/Error"; 
			}
	   }
	 
	 @RequestMapping(value = "HigherEducationAdd.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String HigherEducationAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	
			String Username = (String) ses.getAttribute("Username");
		
			logger.info(new Date() +"Inside HigherEducationAdd.htm"+Username);		
			try {				
				
				 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
				 req.setAttribute("EmpId", EmpId);
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				
				 return "noc/NocHigherEducationAddEdit";
				 
			}catch (Exception e) {
				logger.error(new Date() +" Inside HigherEducationAdd.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	 
	 @RequestMapping(value = "HigherEducationAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String HigherEducationAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside HigherEducationAddSubmit.htm "+UserId);
			try {
				
				NocHigherEducationDto dto = new NocHigherEducationDto ();
						
				
						dto.setEmpNo(EmpNo);
						dto.setInstitutionType(req.getParameter("InstitutionType"));
						dto.setInstitutionName(req.getParameter("InstitutionName"));
						dto.setAcademicYear(req.getParameter("AcademicYear"));
						dto.setCourse(req.getParameter("CourseName"));
						dto.setSpecialization(req.getParameter("Specialization"));
						dto.setEducationType(req.getParameter("EducationType"));
						dto.setQualifiactionRequired(req.getParameter("QualificationRequired"));
						
						
						long save =service.HigherEducationAdd(dto,UserId);
						
						 if (save > 0) {
				  				
			                  redir.addAttribute("result", "NOC for Higher Education Added Succesfully");

			 			} else {
			                  redir.addAttribute("resultfail", "NOC for Higher Education Add Unsuccessful");
			 			}
						 
						 
						redir.addAttribute("EducationId",save) ;
			        return "redirect:/NOCHigherEducationPreview.htm";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside HigherEducationAddSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
			
	 
	 @RequestMapping(value = "HigherEducationEdit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String HigherEducationEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String Username = (String) ses.getAttribute("Username");
		
			logger.info(new Date() +"Inside HigherEducationEdit.htm"+Username);		
			try {				
				 
				 String NOCHigherEducId=req.getParameter("EducationId");
				 
				 NocHigherEducation HigherEduc =service.getNocHigherEducationById(Long.parseLong(NOCHigherEducId));
				 req.setAttribute("NocEmpList", service.getNocEmpList(EmpId));
				 req.setAttribute("EmpPassport", service.getEmpPassportData(EmpId));
				 req.setAttribute("EmpId", EmpId);
				 req.setAttribute("HigherEducation", HigherEduc);
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 
				 return "noc/NocHigherEducationAddEdit";
				 
			}catch (Exception e) {
				logger.error(new Date() +" Inside HigherEducationEdit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	 
	 
	 @RequestMapping(value = "NOCHigherEducationEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String NOCHigherEducationUpdate(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside NOCHigherEducationEditSubmit.htm "+UserId);
			try {
				
				String NOCHigherEducId=req.getParameter("EducationId");
				 
				NocHigherEducationDto dto = new NocHigherEducationDto ();
				dto.setNocEducationId(Long.parseLong(NOCHigherEducId));
				dto.setEmpNo(EmpNo);
				dto.setInstitutionType(req.getParameter("InstitutionType"));
				dto.setInstitutionName(req.getParameter("InstitutionName"));
				dto.setAcademicYear(req.getParameter("AcademicYear"));
				dto.setCourse(req.getParameter("CourseName"));
				dto.setSpecialization(req.getParameter("Specialization"));
				dto.setEducationType(req.getParameter("EducationType"));
				dto.setQualifiactionRequired(req.getParameter("QualificationRequired"));
				
				 
					long update =service.NOCHigherEducationUpdate(dto,UserId);
					
				 if (update > 0) {
		  				
	                  redir.addAttribute("result", "NOC for Higher Education Updated Succesfully");

	 			} else {
	                  redir.addAttribute("resultfail", "NOC for Higher Education Update Unsuccessful");
	 			}
	 
				 
				 redir.addAttribute("EducationId",update) ;
			        return "redirect:/NOCHigherEducationPreview.htm";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside NOCHigherEducationEditSubmit.htm "+UserId, e); 
			    return "static/Error"; 
			}
		}
	 
	  @RequestMapping(value = "NOCHigherEducationTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String NOCHigherEducationTransactionStatus(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
		    String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NOCHigherEducationTransactionStatus.htm "+UserId);
			try {
				
				 String NOCHigherEducId=req.getParameter("EducationId");
				
				req.setAttribute("TransactionList", service.HigherEducationTransactionList(NOCHigherEducId));
				
				return "noc/NocTranscationStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NOCHigherEducationTransactionStatus.htm "+UserId, e);
				return "static/Error";
			}
		}

		@RequestMapping(value = "NOCHigherEducationPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String NOCHigherEducationPreview(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside NOCHigherEducationPreview.htm "+UserId);
			try {
				
				 List<String> PandAs = service.GetPandAAdminEmpNos();
				 String CEO = service.GetCEOEmpNo();
				 String isApproval = req.getParameter("isApproval");
				 String NOCHigherEducId=req.getParameter("EducationId");
				 
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("isApproval", isApproval);
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("CEOempno", CEO);
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				 req.setAttribute("HigherEducationRemarks",service.getNocHigherEducationRemarks(NOCHigherEducId) );
				 req.setAttribute("NOCHigherEducationDetails", service.getHigherEducationDetails(NOCHigherEducId));
				 req.setAttribute("HigherEducationApprovalData", service.getHigherEducationApprovalData(NOCHigherEducId) );
				 req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
				 
			     return "noc/NocHigherEducationPreview";
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside NOCHigherEducationPreview.htm "+UserId, e); 
			    return "static/Error"; 
			}
			
		}

		@RequestMapping(value = "NOCHigherEducationDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public void NOCHigherEducationDownload(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,HttpServletResponse res)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside NOCHigherEducationDownload.htm "+UserId);
			try {
				
				 List<String> PandAs = service.GetPandAAdminEmpNos();
				
				 String NOCHigherEducId=req.getParameter("EducationId");
				 req.setAttribute("PandAsEmpNos", PandAs);
				 req.setAttribute("CeoName", service.GetCeoName());
				 req.setAttribute("NOCHigherEducationDetails", service.getHigherEducationDetails(NOCHigherEducId));
				 req.setAttribute("HigherEducationApprovalData", service.getHigherEducationApprovalData(NOCHigherEducId) );
				 req.setAttribute("lablogo",getLabLogoAsBase64());
				 
				String path = req.getServletContext().getRealPath("/view/temp");
			    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/noc/NocHigherEducationDownload.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();
				byte[] data = html.getBytes();
				InputStream fis1 = new ByteArrayInputStream(data);
				PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/NocHigherEducationDownload.pdf"));
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
				res.setHeader("Content-disposition", "inline;filename=NocHigherEducationDownload.pdf");
				File f = new File(path + "/NocHigherEducationDownload.pdf");
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
				Path pathOfFile2 = Paths.get(path + "/NocHigherEducationDownload.pdf");
				Files.delete(pathOfFile2);
				 
			   
				
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside NOCHigherEducationDownload.htm "+UserId, e); 
			    
			}
			
		  }
		
		
		@RequestMapping(value = "NOCHigherEducationForward.htm")
		public String NOCHigherEducationForward(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside NOCHigherEducationForward.htm"+Username);
			try {
				
				
				 String NOCHigherEducId=req.getParameter("EducationId");
				 System.out.println("NOCHigherEducId---"+NOCHigherEducId);
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				
				NocHigherEducation education = service.HigherEducation(NOCHigherEducId);
				
			    String NocStatusCode = education.getNocStatusCode();
				
				long count = service.NOCHigherEducationForward(NOCHigherEducId, Username, action,remarks,EmpNo,LoginType);
				
				if(action.equalsIgnoreCase("A")) {
					
				   if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI")
						|| NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RSO") || NocStatusCode.equalsIgnoreCase("RPA") ||
						NocStatusCode.equalsIgnoreCase("RCE")) {
						
					if (count > 0) {
						redir.addAttribute("result", "NOC For Higher Education Forwarded Successful");
					} else {
						redir.addAttribute("resultfail", "NOC For Higher Education Forward Unsuccessful");	
					}	
					return "redirect:/HigherEducation.htm";
				}
				
				   else if(NocStatusCode.equalsIgnoreCase("FWD") || NocStatusCode.equalsIgnoreCase("VGI")  || NocStatusCode.equalsIgnoreCase("VDI") || NocStatusCode.equalsIgnoreCase("VDG") ) { 
						   
					   
					   if (count > 0) {
							redir.addAttribute("result", "NOC For Higher Education Recommended Successful");
						} else {
							redir.addAttribute("resultfail", "NOC For Higher Education Recommend Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
				   else if(NocStatusCode.equalsIgnoreCase("VSO")) {
						  
					   
					   if (count > 0) {
							redir.addAttribute("result", "NOC For Higher Education Verified Successful");
						} else {
							redir.addAttribute("resultfail", "NOC For Higher Education Verify Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
                      else if(NocStatusCode.equalsIgnoreCase("VPA")) {
						  
					     if (count > 0) {
							redir.addAttribute("result", "NOC For Higher Education Approved  Successful");
						} else {
							redir.addAttribute("resultfail", "NOC For Higher Education  Approve Unsuccessful");	
						}	
						return "redirect:/NocApproval.htm"; 
					   
				   }
				   
				}
				
				if(action.equalsIgnoreCase("D")) 
				{
					if (count > 0) {
						redir.addAttribute("result", "NOC For Higher Education DisApprove Successfull");
					} else {
						redir.addAttribute("resultfail", "NOC For Higher Education DisApprove Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm";
				}
				
				
				if(action.equalsIgnoreCase("R")) 
				{
					if (count > 0) {
						redir.addAttribute("result", "NOC For Higher Education Returned Successful");
					} else {
						redir.addAttribute("resultfail", "NOC For Higher Education Return Unsuccessful");	
					}	
					return "redirect:/NocApproval.htm";
				}
				
				return "redirect:/HigherEducation.htm";
			
			
			}catch (Exception e) {
				logger.error(new Date() +" Inside NOCHigherEducationForward.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		@RequestMapping(value = "NOCHigherEducationLetter.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public void NOCHIGHEREDUCATIONLetter(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,HttpServletResponse res)  throws Exception 
		{
			
		    String UserId=(String)ses.getAttribute("Username");
			String EmpId =  ses.getAttribute("EmpId").toString();
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date()+"Inside NOCHigherEducationLetter.htm "+UserId);
			try {
				
				String NOCHigherEducId=req.getParameter("EducationId");
				List<String> PandAs = service.GetPandAAdminEmpNos();
				req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
				req.setAttribute("NOCHigherEducationDetails", service.getHigherEducationDetails(NOCHigherEducId));
				req.setAttribute("PandAsEmpNos", PandAs);
				req.setAttribute("lablogo",getLabLogoAsBase64());
				String path = req.getServletContext().getRealPath("/view/temp");
			    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/noc/NocHigherEducationLetter.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();
				byte[] data = html.getBytes();
				InputStream fis1 = new ByteArrayInputStream(data);
				PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/NOCHigherEducationLetter.pdf"));
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
				res.setHeader("Content-disposition", "inline;filename=NOCHigherEducationLetter.pdf");
				File f = new File(path + "/NOCHIGHEREDUCATIONLetter.pdf");
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
				Path pathOfFile2 = Paths.get(path + "/NOCHigherEducationLetter.pdf");
				Files.delete(pathOfFile2);
					 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside NOCHigherEducationLetter.htm "+UserId, e); 
			   
			}
		}
		
}




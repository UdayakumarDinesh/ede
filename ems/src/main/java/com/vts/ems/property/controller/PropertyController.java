package com.vts.ems.property.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pi.service.PIService;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.model.PisMovableProperty;
import com.vts.ems.property.model.PisMovablePropertyTrans;
import com.vts.ems.property.service.PropertyService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;



@Controller
public class PropertyController {

	private static final Logger logger = LogManager.getLogger(PropertyController.class);
	
	@Autowired
	Environment env;
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Autowired
	PropertyService service;
	
	@Autowired
	PIService piservice;
	
	@Autowired
	PisService pisservice;
	
    private final String formmoduleid="15";
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@RequestMapping(value="PropertyDashBoard.htm")
	public String propertyDashboard(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception{
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PropertyDashBoard.htm"+Username);
		String EmpNo = (String) ses.getAttribute("EmpNo");
		try {
			ses.setAttribute("formmoduleid", formmoduleid);
			ses.setAttribute("SidebarActive","PropertyDashBoard_htm");
			req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			
			return "property/PropertyDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PropertyDashBoard.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}

	}
	
	@RequestMapping(value="AcquiringDisposing.htm")
	public String AcquiringDisposingList( HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception{
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside AcquiringDisposing.htm"+Username);	
		String EmpNo = (String)ses.getAttribute("EmpNo");
		
		try {
			ses.setAttribute("formmoduleid", formmoduleid);
			ses.setAttribute("SidebarActive","AcquiringDisposing_htm");
			
			String CEO = piservice.GetCEOEmpNo();
			List<String> PandAs = piservice.GetPandAAdminEmpNos();
			List<String> DGMs = piservice.GetDGMEmpNos();

            req.setAttribute("CeoName", piservice.GetCeoName());
            req.setAttribute("CEOEmpNos", CEO);
			
			req.setAttribute("PandAEmpName", piservice.GetPandAEmpName());
			req.setAttribute("PandAsEmpNos", PandAs);
			
			if(!DGMs.contains(EmpNo)) {
				req.setAttribute("DGMEmpName", piservice.GetEmpDGMEmpName(EmpNo));
			}
			req.setAttribute("DGMEmpNos", DGMs);
			req.setAttribute("EmpApprFlow", piservice.GetApprovalFlowEmp(EmpNo));
			req.setAttribute("Employee", piservice.getEmpDataByEmpNo(EmpNo));			
			req.setAttribute("ImmPropDetails", service.ImmPropDetails(EmpNo));
			req.setAttribute("MovPropDetails", service.movPropDetails(EmpNo));
			req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			return "property/AcquiringDisposingList";
		}catch (Exception e) {
			logger.error(new Date() +" Inside AcquiringDisposing.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="ImmovablePropAddEdit.htm")
	public String ImmovablePropAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir ) throws Exception{
		
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+ "Inside ImmovablePropAddEdit.htm"+Username);
		String EmpNo = (String) ses.getAttribute("EmpNo");
		try {
			String Action = req.getParameter("Action");
			String immpropertyid = req.getParameter("immpropertyid");
			req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			
			req.setAttribute("States", pisservice.getStates());
			if("EDIT".equalsIgnoreCase(Action)) {
				req.setAttribute("ImmProperty", service.getImmovablePropertyById(Long.parseLong(immpropertyid)));
				return "property/ImmovablePropEdit";
			}else {		
			    return "property/ImmovablePropAdd";	
			}
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropAddEdit.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value="ImmovablePropAdd.htm")
	public String immovablePropAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception{
		
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside ImmovablePropAdd.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		try {
			
			String transState = req.getParameter("transState");
			String transDate = req.getParameter("transDate");
			String financeSource = "";
			String mode = req.getParameter("mode");
			String applicantInterest = req.getParameter("applicantInterest");
			String description = req.getParameter("description");
			String osParticulars = req.getParameter("osParticulars");
			String osShare = req.getParameter("osShare");
			String partyRealted = req.getParameter("partyRealted");
			String relavantFacts = req.getParameter("relavantFacts");
	
			PisImmovableProperty immovable = new PisImmovableProperty();
			
			immovable.setImmIntimationDate(sdf.format(new Date()));
			immovable.setEmpNo(EmpNo);
			immovable.setPurpose(req.getParameter("purpose"));
			immovable.setTransState(transState);
			immovable.setTransDate(DateTimeFormatUtil.dateConversionSql(transDate));
			immovable.setMode(mode);
			immovable.setLocation(req.getParameter("location").trim());
			immovable.setDistrict(req.getParameter("district").trim());
			immovable.setState(req.getParameter("state"));
			immovable.setPincode(req.getParameter("pincode").trim());
			immovable.setOwnership(req.getParameter("ownership"));
			
			if(description!=null) {
			    immovable.setDescription(description.trim());
			}
			
			immovable.setApplicantInterest(applicantInterest);
			
			if("P".equalsIgnoreCase(applicantInterest)) {
				immovable.setPartialInterest(req.getParameter("partialInterest").trim());
			}
			
			if(osParticulars!=null) {
			    immovable.setOsParticulars(osParticulars.trim());
			}
			
			if(osShare!=null) {
			    immovable.setOsShare(osShare.trim());
			}
			
			immovable.setPrice( Double.parseDouble(req.getParameter("price").trim()) );
			
			if("A".equalsIgnoreCase(transState)) {
				financeSource = req.getParameter("financeSource");

			if("Other sources".equalsIgnoreCase(financeSource)) {
				financeSource = req.getParameter("otherSource");
			}			
			if("Gift".equalsIgnoreCase(mode)) {
				immovable.setSanctionRequired(req.getParameter("sanctionRequired")); ;
			}
			
			    immovable.setFinanceSource(financeSource.trim());
			}
			else {
				immovable.setRequisiteSanction(req.getParameter("requisite")); 
			}
			
			immovable.setPartyName(req.getParameter("partyName").trim());
			immovable.setPartyAddress(req.getParameter("partyAddress").trim());
			immovable.setTransArrangement(req.getParameter("transArrangement").trim());
			immovable.setPartyRelated(partyRealted);
			
			if("Y".equalsIgnoreCase(partyRealted)) {
				immovable.setRelationship(req.getParameter("relationship").trim());
			}
			
			immovable.setFutureDealings(req.getParameter("futureDealings"));
			immovable.setDealingNature(req.getParameter("dealingNature").trim());
			
			if(relavantFacts!=null) {
			immovable.setRelavantFact(relavantFacts.trim());
			}
			
			immovable.setImmStatus("N");
			immovable.setPisStatusCode("INI");
			immovable.setPisStatusCodeNext("INI");
			immovable.setIsActive(1);
			immovable.setCreatedBy(Username);
			immovable.setCreatedDate(sdtf.format(new Date()));
			
			Long result = service.addImmovableProperty(immovable);
			
			 if(result>0) { 
				 
				 PisImmovablePropertyTrans transaction = PisImmovablePropertyTrans.builder()	
							                             .ImmPropertyId(result)
							                             .PisStatusCode("INI")
							                             .ActionBy(EmpNo)
							                             .Remarks("")
							                             .ActionDate(sdtf.format(new Date()))
							                             .build();
                       service.addImmovablePropertyTransaction(transaction);
                       
     	    	 redir.addAttribute("result", "Immovable Property Added Successfully");	
     		} else {
     			 redir.addAttribute("resultfail", "Immovable Property Add Unsuccessful");	    			
     	    }	
			redir.addAttribute("immPropertyId", result);
			
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropAdd.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		return "redirect:/ImmovablePropPreview.htm";
	}
	
	@RequestMapping(value="ImmovablePropEdit.htm")
	public String ImmovablePropEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception{
		
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside ImmovablePropEdit.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		try {
			
			String immpropertyId = req.getParameter("immpropertyId");
			String transState = req.getParameter("transState");
			String transDate = req.getParameter("transDate");
			String financeSource = "";
			String mode = req.getParameter("mode");
			String applicantInterest = req.getParameter("applicantInterest");
			String description = req.getParameter("description");
			String osParticulars = req.getParameter("osParticulars");
			String osShare = req.getParameter("osShare");
			String partyRealted = req.getParameter("partyRealted");
			String relavantFacts = req.getParameter("relavantFacts");
			
			PisImmovableProperty immovable = new PisImmovableProperty();
			
			immovable.setImmPropertyId(Long.parseLong(immpropertyId.trim()));
			immovable.setPurpose(req.getParameter("purpose"));
			immovable.setTransState(transState);
			immovable.setTransDate(DateTimeFormatUtil.dateConversionSql(transDate));
			immovable.setMode(mode);
			immovable.setLocation(req.getParameter("location").trim());
			immovable.setDistrict(req.getParameter("district").trim());
			immovable.setState(req.getParameter("state"));
			immovable.setPincode(req.getParameter("pincode").trim());
			immovable.setOwnership(req.getParameter("ownership"));
			
			if(description!=null) {
				immovable.setDescription(description.trim());
			}
			
			immovable.setApplicantInterest(applicantInterest);
			
			if("P".equalsIgnoreCase(applicantInterest)) {
				immovable.setPartialInterest(req.getParameter("partialInterest").trim());
			}
			
			if(osParticulars!=null) {
				immovable.setOsParticulars(osParticulars.trim());
			}
			
			if(osShare!=null) {
				immovable.setOsShare(osShare.trim());
			}
			
			immovable.setPrice( Double.parseDouble(req.getParameter("price").trim()) );
			
			if("A".equalsIgnoreCase(transState)) {
				financeSource = req.getParameter("financeSource");
				
				if("Other sources".equalsIgnoreCase(financeSource)) {
					financeSource = req.getParameter("otherSource");
				}			
				if("Gift".equalsIgnoreCase(mode)) {
					immovable.setSanctionRequired(req.getParameter("sanctionRequired")); ;
				}
				
				immovable.setFinanceSource(financeSource.trim());
			}
			else {
				immovable.setRequisiteSanction(req.getParameter("requisite")); 
			}
			
			immovable.setPartyName(req.getParameter("partyName").trim());
			immovable.setPartyAddress(req.getParameter("partyAddress").trim());
			immovable.setTransArrangement(req.getParameter("transArrangement").trim());
			immovable.setPartyRelated(partyRealted);
			
			if("Y".equalsIgnoreCase(partyRealted)) {
				immovable.setRelationship(req.getParameter("relationship").trim());
			}
			
			immovable.setFutureDealings(req.getParameter("futureDealings"));
			immovable.setDealingNature(req.getParameter("dealingNature").trim());
			
			if(relavantFacts!=null) {
				immovable.setRelavantFact(relavantFacts.trim());
			}
			
			immovable.setModifiedBy(Username);
			immovable.setModifiedDate(sdtf.format(new Date()));
			
			Long result = service.editImmovableProperty(immovable);
			
			if(result>0) {   	    
				redir.addAttribute("result", "Immovable Property Edited Successfully");	
			} else {
				redir.addAttribute("resultfail", "Immovable Property Edit Unsuccessful");	
			}		
			redir.addAttribute("immPropertyId", result);
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropEdit.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		return "redirect:/ImmovablePropPreview.htm";
	}
	
	@RequestMapping(value="ImmovablePropTransStatus.htm")
	public String immovablePropTransStatus(HttpServletRequest req, HttpSession ses) throws Exception
	{
		String Username =(String) ses.getAttribute("Username");
		logger.info(new Date()+"Inside ImmovablePropTransStatus.htm"+Username);
		try {
			String immpropertyid = req.getParameter("immpropertyid");
			if(immpropertyid!=null) {
				req.setAttribute("TransactionList", service.immmovablePropertyTransList(immpropertyid.trim()));
			}
			return "property/PropertyTransStatus";
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropTransStatus.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="ImmovablePropPreview.htm")
	public String immovablePropPreview(HttpServletRequest req,HttpSession ses) throws Exception
	{		
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside ImmovablePropPreview.htm"+Username);
		try {
			String immPropertyId = req.getParameter("immPropertyId");
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			if(immPropertyId!=null) {
				String isApproval = req.getParameter("isApproval");
				if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
					req.setAttribute("SidebarActive","PropertyApprovals_htm");
				}
				req.setAttribute("isApproval", isApproval);
				PisImmovableProperty immovableProperty = service.getImmovablePropertyById(Long.parseLong(immPropertyId.trim()));
			    req.setAttribute("ImmPropFormData",immovableProperty );	
			    req.setAttribute("CEOEmpNo",piservice.GetCEOEmpNo() );
			    req.setAttribute("ApprovalEmpData", service.immPropTransactionApprovalData(immPropertyId.trim()));
			    req.setAttribute("ImmIntimationRemarks", service.immPropertyRemarksHistory(immPropertyId));
			    req.setAttribute("EmpData", service.getEmpNameDesig(immovableProperty.getEmpNo().trim()));
			}
			return "property/ImmovablePropForm";
		}catch(Exception e) {
			logger.info(new Date()+"Inside ImmovablePropPreview.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="ImmovablePropFormSubmit.htm")
	public String immovablePropFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside ImmovablePropFormSubmit.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		String LoginType=(String)ses.getAttribute("LoginType");
		try {
			String immPropertyId = req.getParameter("immPropertyId");
			String remarks = req.getParameter("remarks");
			String action = req.getParameter("Action");
			
			PisImmovableProperty immovable = service.getImmovablePropertyById(Long.parseLong(immPropertyId));			
			String pisStatusCode = immovable.getPisStatusCode();
			
			long count = service.immovablePropForward(immPropertyId, Username, action, remarks, EmpNo, LoginType);
			
			if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG")||
			   pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE"))
			{
				if(count>0) {
					redir.addAttribute("result", "Immovable Property Application Sent For Verification Successfully");
				}else {
					redir.addAttribute("resultfail","Immovable Property Application Sent For Verification Unsuccessful");
				}
				return "redirect:/AcquiringDisposing.htm";
			}
			else {
				if(count>0) {
					redir.addAttribute("result", "Immovable Property verification Successful");
				}else {
					redir.addAttribute("resultfail", "Immovable Property verification Unsuccessful");
				}
				return "redirect:/PropertyApprovals.htm";
			}
			
		}catch (Exception e) {
			logger.info(new Date()+"Inside ImmovablePropFormSubmit.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="PropertyApprovals.htm")
	public String propertyApprovals(HttpServletRequest req,HttpSession ses) throws Exception
	{
		String Username =(String) ses.getAttribute("Username");
		logger.info(new Date()+" Inside PropertyApprovals.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		try {
			ses.setAttribute("formmoduleid", formmoduleid);
			ses.setAttribute("SidebarActive","PropertyApprovals_htm");
			
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

			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("tab", req.getParameter("tab"));
			
			req.setAttribute("PendingList", service.propertyApprovalList(EmpNo));
			req.setAttribute("ApprovedList", service.propertyApprovedList(EmpNo, fromdate, todate));
			req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			return "property/PropertyApprovals";
		}catch (Exception e) {
			logger.info(new Date()+"Inside PropertyApprovals.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="ImmovablePropFormDownload.htm")
	public void immovablePropFormDownload( Model model,HttpServletRequest req,HttpSession ses,RedirectAttributes redir,HttpServletResponse res) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside ImmovablePropFormDownload.htm "+Username);
		
		try {	
			String immPropertyId = req.getParameter("immPropertyId");
			String pagePart =  req.getParameter("pagePart");
			
			String filename="";
			if(immPropertyId!=null) {
				PisImmovableProperty immovableProperty = service.getImmovablePropertyById(Long.parseLong(immPropertyId.trim()));
			    req.setAttribute("ImmPropFormData",immovableProperty );	
			    req.setAttribute("CEOEmpNo",piservice.GetCEOEmpNo() );
			    req.setAttribute("ApprovalEmpData", service.immPropTransactionApprovalData(immPropertyId.trim()));
			    req.setAttribute("EmpData", service.getEmpNameDesig(immovableProperty.getEmpNo().trim()));	
				filename="Immovable_Property";
			}
			
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("pagePart","3" );
			
			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
	        req.getRequestDispatcher("/view/property/ImmovablePropFormPrint.jsp").forward(req, customResponse);

			String html = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
	         
	        res.setContentType("application/pdf");
	        res.setHeader("Content-disposition","inline;filename="+filename+".pdf");
	       
	       
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
			logger.error(new Date() +" Inside ImmovablePropFormDownload.htm "+Username, e); 
		}

    }
	
	@RequestMapping(value="MovablePropAddEdit.htm")
	public String movablePropAddEdit(HttpServletRequest req, HttpSession ses) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside MovablePropAddEdit.htm"+Username);
		String EmpNo = (String) ses.getAttribute("EmpNo");
		try {
			String action = req.getParameter("Action");
			
			req.setAttribute("States", pisservice.getStates());
			req.setAttribute("EmpData", service.getEmpNameDesig(EmpNo));
			
			if(action.equalsIgnoreCase("EDITMov")) {
				String movPropertyId = req.getParameter("movpropertyid");
				req.setAttribute("MovProperty", service.getMovablePropertyById(Long.parseLong(movPropertyId)));
				return "property/MovablePropEdit";
			}
			else {
				return "property/MovablePropAdd";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(new Date()+"Inside MovablePropAddEdit.htm"+Username,e);
			return "static/Error";
		}
		
		
	}
	
	@RequestMapping(value="MovablePropAdd.htm")
	public String movablePropAdd(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside MovablePropAdd.htm"+Username);
		String EmpNo = (String) ses.getAttribute("EmpNo");
		try {
			String transState = req.getParameter("transState");
			String transDate = req.getParameter("transDate");
			String description = req.getParameter("description");
			String mode = req.getParameter("mode");
			String financeSource = "";
			String partyRealted = req.getParameter("partyRealted");
			String relavantFacts = req.getParameter("relavantFacts");
			
			PisMovableProperty movable = new PisMovableProperty();
			
			movable.setEmpNo(EmpNo);
			movable.setMovIntimationDate(sdf.format(new Date()));
			movable.setPurpose(req.getParameter("purpose"));
			movable.setTransState(transState);
			movable.setTransDate(DateTimeFormatUtil.dateConversionSql(transDate));
			movable.setDescription(description);
			if(("Four Wheeler").equalsIgnoreCase(description) || ("Two Wheeler").equalsIgnoreCase(description)) {
				movable.setMakeAndModel(req.getParameter("makeAndModel").trim());
			}
			
			movable.setMode(mode);
			movable.setPrice(Double.parseDouble(req.getParameter("price").trim()) );
			if("A".equalsIgnoreCase(transState)) {
				financeSource = req.getParameter("financeSource");

			if("Other sources".equalsIgnoreCase(financeSource)) {
				financeSource = req.getParameter("otherSource");
			}			
			if("Gift".equalsIgnoreCase(mode)) {
				movable.setSanctionRequired(req.getParameter("sanctionRequired")); ;
			}
			
			    movable.setFinanceSource(financeSource.trim());
			}
			else {
				movable.setRequisiteSanction(req.getParameter("requisite")); 
			}
			
			movable.setPartyName(req.getParameter("partyName").trim());
			movable.setPartyAddress(req.getParameter("partyAddress").trim());
			movable.setTransArrangement(req.getParameter("transArrangement").trim());
			movable.setPartyRelated(partyRealted);
			
			if("Y".equalsIgnoreCase(partyRealted)) {
				movable.setRelationship(req.getParameter("relationship").trim());
			}
			
			movable.setFutureDealings(req.getParameter("futureDealings"));
			movable.setDealingNature(req.getParameter("dealingNature").trim());
			
			if(relavantFacts!=null) {
			movable.setRelavantFacts(relavantFacts.trim());
			}
			
			movable.setMovStatus("N");
			movable.setPisStatusCode("INI");
			movable.setPisStatusCodeNext("INI");
			movable.setIsActive(1);
			movable.setCreatedBy(Username);
			movable.setCreatedDate(sdtf.format(new Date()));
			
			Long result = service.addMovableProperty(movable);
			
			 if(result>0) { 
				 
				 PisMovablePropertyTrans transaction = PisMovablePropertyTrans.builder()	
							                           .MovPropertyId(result)
							                           .PisStatusCode("INI")
							                           .ActionBy(EmpNo)
							                           .Remarks("")
							                           .ActionDate(sdtf.format(new Date()))
							                           .build();
                      service.addMovablePropertyTransaction(transaction);
                      
    	    	 redir.addAttribute("result", "Movable Property Added Successfully");	
    		} else {
    			 redir.addAttribute("resultfail", "Movable Property Add Unsuccessful");	    			
    	    }	
			redir.addAttribute("movPropertyId", result);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(new Date()+"Inside MovablePropAdd.htm"+Username,e);
			return "static/Error";
		}
		
		return "redirect:/MovablePropPreview.htm";
	}
	
	@RequestMapping(value="MovablePropEdit.htm")
	public String movablePropEdit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside MovablePropEdit.htm"+Username);
		try {
			String transState = req.getParameter("transState");
			String transDate = req.getParameter("transDate");
			String description = req.getParameter("description");
			String mode = req.getParameter("mode");
			String financeSource = "";
			String partyRealted = req.getParameter("partyRealted");
			String relavantFacts = req.getParameter("relavantFacts");
			
			PisMovableProperty movable = new PisMovableProperty();
			
            movable.setMovPropertyId(Long.parseLong(req.getParameter("movpropertyId")));
			movable.setPurpose(req.getParameter("purpose"));
			movable.setTransState(transState);
			movable.setTransDate(DateTimeFormatUtil.dateConversionSql(transDate));
			movable.setDescription(description);
			if(("Four Wheeler").equalsIgnoreCase(description) || ("Two Wheeler").equalsIgnoreCase(description)) {
				movable.setMakeAndModel(req.getParameter("makeAndModel").trim());
			}
			
			movable.setMode(mode);
			movable.setPrice(Double.parseDouble(req.getParameter("price").trim()) );
			if("A".equalsIgnoreCase(transState)) {
				financeSource = req.getParameter("financeSource");

			if("Other sources".equalsIgnoreCase(financeSource)) {
				financeSource = req.getParameter("otherSource");
			}			
			if("Gift".equalsIgnoreCase(mode)) {
				movable.setSanctionRequired(req.getParameter("sanctionRequired")); ;
			}
			
			    movable.setFinanceSource(financeSource.trim());
			}
			else {
				movable.setRequisiteSanction(req.getParameter("requisite")); 
			}
			
			movable.setPartyName(req.getParameter("partyName").trim());
			movable.setPartyAddress(req.getParameter("partyAddress").trim());
			movable.setTransArrangement(req.getParameter("transArrangement").trim());
			movable.setPartyRelated(partyRealted);
			
			if("Y".equalsIgnoreCase(partyRealted)) {
				movable.setRelationship(req.getParameter("relationship").trim());
			}
			
			movable.setFutureDealings(req.getParameter("futureDealings"));
			movable.setDealingNature(req.getParameter("dealingNature").trim());
			
			if(relavantFacts!=null) {
			movable.setRelavantFacts(relavantFacts.trim());
			}
			
			movable.setModifiedBy(Username);
			movable.setModifiedDate(sdtf.format(new Date()));
			
			Long result = service.editMovableProperty(movable);
			
			 if(result>0) {                      
    	    	 redir.addAttribute("result", "Movable Property Edited Successfully");	
    		} else {
    			 redir.addAttribute("resultfail", "Movable Property Edit Unsuccessful");	    			
    	    }	
			redir.addAttribute("movPropertyId", result);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(new Date()+"Inside MovablePropEdit.htm"+Username,e);
			return "static/Error";
		}
		
		return "redirect:/MovablePropPreview.htm";
	}
	
	@RequestMapping(value="MovablePropTransStatus.htm")
	public String movablePropTransStatus(HttpServletRequest req, HttpSession ses) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside MovablePropTransStatus.htm"+Username);
		try {
			String movpropertyId = req.getParameter("movpropertyId");
			if(movpropertyId!=null) {
				req.setAttribute("TransactionList", service.movablePropertyTransList(movpropertyId));
			}
			return "property/PropertyTransStatus";
		}catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside MovablePropTransStatus.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MovablePropPreview.htm")
	public String movablePropPreview(HttpServletRequest req,HttpSession ses) throws Exception
	{		
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside MovablePropPreview.htm"+Username);
		try {
			String movPropertyId = req.getParameter("movPropertyId");
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			if(movPropertyId!=null) {
				String isApproval = req.getParameter("isApproval");
				if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
					req.setAttribute("SidebarActive","PropertyApprovals_htm");
				}
				req.setAttribute("isApproval", isApproval);
				PisMovableProperty movableProperty = service.getMovablePropertyById(Long.parseLong(movPropertyId.trim()));
			    req.setAttribute("movPropFormData",movableProperty );	
			    req.setAttribute("ApprovalEmpData", service.movPropTransactionApprovalData(movPropertyId.trim()));
			    req.setAttribute("EmpData", service.getEmpNameDesig(movableProperty.getEmpNo().trim()));
			    req.setAttribute("CEOEmpNo",piservice.GetCEOEmpNo() );
			    req.setAttribute("MovIntimationRemarks", service.movPropertyRemarksHistory(movPropertyId));
			}
			return "property/MovablePropForm";
		}catch(Exception e) {
			logger.info(new Date()+"Inside MovablePropPreview.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MovablePropFormSubmit.htm")
	public String movablePropFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside MovablePropFormSubmit.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		String LoginType=(String)ses.getAttribute("LoginType");
		try {
			String movPropertyId = req.getParameter("movPropertyId");
			String remarks = req.getParameter("remarks");
			String action = req.getParameter("Action");
			
			PisMovableProperty movable = service.getMovablePropertyById(Long.parseLong(movPropertyId.trim()));			
			String pisStatusCode = movable.getPisStatusCode();
			
			long count = service.movablePropForward(movPropertyId, Username, action, remarks, EmpNo, LoginType);
			
			if( pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG")||
			    pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE"))
			{
				if(count>0) {
					redir.addAttribute("result", "Movable Property Application Sent For Verification Successfully");
				}else {
					redir.addAttribute("resultfail","Movable Property Application Sent For Verification Unsuccessful");
				}
				return "redirect:/AcquiringDisposing.htm";
			}
			else {
				if(count>0) {
					redir.addAttribute("result", "Movable Property verification Successful");
				}else {
					redir.addAttribute("resultfail", "Movable Property verification Unsuccessful");
				}
				return "redirect:/PropertyApprovals.htm";
			}
			
		}catch (Exception e) {
			logger.info(new Date()+"Inside MovablePropFormSubmit.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MovablePropFormDownload.htm")
	public void movablePropFormDownload( Model model,HttpServletRequest req,HttpSession ses,RedirectAttributes redir,HttpServletResponse res) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MovablePropFormDownload.htm "+Username);
		
		try {	
			String movPropertyId = req.getParameter("movPropertyId");
			String pagePart =  req.getParameter("pagePart");
			
			String filename="";
			if(movPropertyId!=null) {
				PisMovableProperty movableProperty = service.getMovablePropertyById(Long.parseLong(movPropertyId.trim()));
			    req.setAttribute("movPropFormData",movableProperty );	
			    req.setAttribute("CEOEmpNo",piservice.GetCEOEmpNo() );
			    req.setAttribute("ApprovalEmpData", service.movPropTransactionApprovalData(movPropertyId.trim()));
			    req.setAttribute("EmpData", service.getEmpNameDesig(movableProperty.getEmpNo().trim()));
				filename="Movovable_Property";
			}
			
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("pagePart","3" );
			
			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
	        req.getRequestDispatcher("/view/property/MovablePropFormPrint.jsp").forward(req, customResponse);

			String html = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
	         
	        res.setContentType("application/pdf");
	        res.setHeader("Content-disposition","inline;filename="+filename+".pdf");
	       
	       
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
			logger.error(new Date() +" Inside MovablePropFormDownload.htm "+Username, e); 
		}

    }
}

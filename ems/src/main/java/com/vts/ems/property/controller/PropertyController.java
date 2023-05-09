package com.vts.ems.property.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pi.service.PIService;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.service.PropertyService;
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
		String EmpNo = (String) ses.getAttribute("Empno");
		try {
			ses.setAttribute("formmoduleid", formmoduleid);
			ses.setAttribute("SidebarActive","PropertyDashBoard_htm");
			
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
			req.setAttribute("Employee", piservice.getEmpDataByEmpNo(EmpNo));			
			req.setAttribute("ImmPropDetails", service.ImmPropDetails(EmpNo));
			
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
		try {
			String Action = req.getParameter("Action");
			String immpropertyid = req.getParameter("immpropertyid");
			
			
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
                       
     	    	 redir.addAttribute("result", "Immovable Property Add Successfull");	
     		} else {
     			 redir.addAttribute("resultfail", "Immovable Property Add Unsuccessful");	
     	    }		
			
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropAdd.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		return "redirect:/AcquiringDisposing.htm";
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
				redir.addAttribute("result", "Immovable Property Edit Successfull");	
			} else {
				redir.addAttribute("resultfail", "Immovable Property Edit Unsuccessful");	
			}		
			
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropEdit.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		return "redirect:/AcquiringDisposing.htm";
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
				PisImmovableProperty immovableProperty = service.getImmovablePropertyById(Long.parseLong(immPropertyId.trim()));
			    req.setAttribute("ImmPropFormData",immovableProperty );	
			    req.setAttribute("EmpData", piservice.getEmpDataByEmpNo(immovableProperty.getEmpNo()));
			}
			return "property/ImmovablePropForm";
		}catch(Exception e) {
			logger.info(new Date()+"Inside ImmovablePropPreview.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
}

package com.vts.ems.property.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
			}		
			return "property/ImmovablePropAddEdit";			
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
			if("Gift".equalsIgnoreCase("mode")) {
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
     	    	 redir.addAttribute("result", "Immovable Property Add Successfull");	
     		} else {
     			 redir.addAttribute("resultfail", "Immovable Property ADD Unsuccessful");	
     	    }		
			
		}catch (Exception e) {
			logger.error(new Date() +" Inside ImmovablePropAdd.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		return "redirect:/AcquiringDisposing.htm";
	}
	
}

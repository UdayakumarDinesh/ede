package com.vts.ems.chss.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.DateTimeFormatUtil;
import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.service.CHSSService;

@Controller
public class CHSSController {

	private static final Logger logger = LogManager.getLogger(CHSSController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Autowired
	CHSSService service;
	
	@RequestMapping(value = "CHSSDashboard.htm" )
	public String CHSSDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSDashboard.htm "+Username);
		try {
			
			return "chss/CHSSDashboard";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSDashboard.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSApply.htm" )
	public String CHSSApplyhtm(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSApply.htm "+Username);
		try {
			
			
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("empfamilylist", service.familyDetailsList(EmpNo));
			return "chss/CHSSApply";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApply.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	@RequestMapping(value = "CHSSApplyDetails.htm" )
	public String CHSSApplyDetails(HttpServletRequest req, HttpSession ses)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSApplyDetails.htm "+Username);
		try {
			String patientid = req.getParameter("patientid");
			String isself=req.getParameter("isself");
			
			
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("treattypelist", service.CHSSTreatTypeList());
			req.setAttribute("isself",isself);
			if(isself.equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(patientid));
			}
			return "chss/CHSSApplyDetails";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApplyDetails.htm "+Username, e);
			return "static/Error";
		}
	}

	
	@RequestMapping(value = "CHSSApplySubmit.htm" )
	public String CHSSApplySubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSApplySubmit.htm "+Username);
		try {
			String patientid = req.getParameter("patientid");
			String relationid=req.getParameter("relationid");
			String treatmenttype=req.getParameter("treatmenttype");
			String noenclosures=req.getParameter("enclosurecount");
			
			String[] centernames=req.getParameterValues("centername");
			String[] billno=req.getParameterValues("billno");
			String[] billdate=req.getParameterValues("billdate");
			String[] billamount=req.getParameterValues("billamount");
			
			CHSSApplyDto dto=new CHSSApplyDto();
			dto.setRelationId(relationid);
			dto.setEmpId(EmpId);
			dto.setPatientId(patientid);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
			dto.setBillAmount(billamount);
			dto.setTreatTypeId(treatmenttype);
			dto.setCHSSType("OPD");
			dto.setCreatedBy(Username);
			dto.setNoEnclosures(noenclosures);
			
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
			dto.setBillAmount(billamount);
			dto.setCreatedBy(Username);
			
			
			long count= service.CHSSApplySubmit(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill(s) Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill(s) Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",String.valueOf(count));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApplySubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSAppliedList.htm" )
	public String CHSSAppliedList(Model model,HttpServletRequest req, HttpSession ses)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSAppliedList.htm "+Username);
		try {
			
			
			req.setAttribute("empchsslist", service.empCHSSList(EmpId));
			return "chss/CHSSAppliedList";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSAppliedList.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSAppliedDetails.htm" )
	public String CHSSAppliedDetails(Model model,HttpServletRequest req, HttpSession ses)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSAppliedDetails.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			if (chssapplyid == null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
			}		
						
			Object[] apply= service.CHSSAppliedData(chssapplyid);
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("treattypelist", service.CHSSTreatTypeList());
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			req.setAttribute("testmainlist", service.CHSSTestMainList());
			if(apply[3].toString().equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(apply[2].toString()));
			}
			
			return "chss/CHSSAppliedData";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSAppliedDetails.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	@RequestMapping(value = "CHSSBillEdit.htm", method = RequestMethod.POST )
	public String CHSSBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSBillEdit.htm "+Username);
		try {
			
			String billid = req.getParameter("billid");
			String centername = req.getParameter("centername-"+billid);
			String billno = req.getParameter("billno-"+billid);
			String billdate = req.getParameter("billdate-"+billid);
			String billamount = req.getParameter("billamount-"+billid);
			
			CHSSBill bill = new CHSSBill();
			bill.setBillId(Long.parseLong(billid));
			bill.setCenterName(centername);
			bill.setBillNo(billno);
			bill.setBillDate(sdf.format(rdf.parse(billdate)));
			bill.setBillAmount(Integer.parseInt(billamount));
			bill.setModifiedBy(Username);
			
			long count = service.CHSSBillEdit(bill);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSBillDelete.htm", method = RequestMethod.POST )
	public String CHSSBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSBillDelete.htm "+Username);
		try {
			
			String billid = req.getParameter("billid");
			long count = service.CHSSBillDelete(billid,Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill deleting Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSBillAdd.htm" )
	public String CHSSBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			String[] centernames=req.getParameterValues("centername");
			String[] billno=req.getParameterValues("billno");
			String[] billdate=req.getParameterValues("billdate");
			String[] billamount=req.getParameterValues("billamount");
			
			CHSSApplyDto dto=new CHSSApplyDto();
			
			dto.setCHSSApplyId(chssapplyid);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
			dto.setBillAmount(billamount);
			dto.setCreatedBy(Username);
			
			long count= service.CHSSApplySubmit(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	@RequestMapping(value = "CHSSApplyEdit.htm", method = RequestMethod.POST )
	public String CHSSApplyEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSApplyEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			
			String enclosurecount = req.getParameter("enclosurecount");
			String treatmenttype = req.getParameter("treatmenttype");
			
			CHSSApplyDto dto= new CHSSApplyDto();
			dto.setCHSSApplyId(chssapplyid);
			dto.setNoEnclosures(enclosurecount);
			dto.setTreatTypeId(treatmenttype);
			dto.setModifiedBy(Username);
			
			long count = service.CHSSApplyEdit(dto);
			
			
			if (count > 0) {
				redir.addAttribute("result", "CHSS Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "CHSS Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApplyEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "GetBillDataAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String GetBillDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetBillDataAjax.htm "+Username);
		CHSSBill bill=null;
		try {
			String billid = req.getParameter("billid");
			bill = service.getCHSSBill(billid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside GetBillDataAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(bill);
	}
	
	@RequestMapping(value = "GetTestSubListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String GetTestSubListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetTestSubListAjax.htm "+Username);
		List<CHSSTestSub> list=new ArrayList<CHSSTestSub>();
		try {
			String testmainid = req.getParameter("testmainid");
			list = service.CHSSTestSubList(testmainid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside GetTestSubListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	
	@RequestMapping(value = "ConsultationBillAdd.htm", method = RequestMethod.POST )
	public String ConsultationBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultationBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] consulttype=req.getParameterValues("consult-type");
			String[] docname=req.getParameterValues("doc-name");
			String[] docqualification=req.getParameterValues("doc-qualification");
			String[] consdate=req.getParameterValues("cons-date");
			String[] conscharge=req.getParameterValues("cons-charge");
			
			CHSSConsultationDto dto=new CHSSConsultationDto();
			
			dto.setBillId(billid);
			dto.setConsultType(consulttype);
			dto.setDocName(docname);
			dto.setDocQualification(docqualification);
			dto.setConsultDate(consdate);
			dto.setConsultCharge(conscharge);
			
			dto.setCreatedBy(Username);
			
			long count= service.ConsultationBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultationBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ChssConsultationListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ChssConsultationListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetChssConsultationList.htm "+Username);
		List<CHSSConsultation> list=new ArrayList<CHSSConsultation>();
		try {
			String billid = req.getParameter("billid");
			list = service.CHSSConsultationList(billid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ChssConsultationListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "ConsultationBillEdit.htm", method = RequestMethod.POST )
	public String ConsultationBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultationBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String consultationid = req.getParameter("consultationid"); 
			
			String consulttype = req.getParameter("consult-type-"+consultationid);
			String docname = req.getParameter("doc-name-"+consultationid);
			String docqualification = req.getParameter("doc-qualification-"+consultationid);
			String consdate = req.getParameter("cons-date-"+consultationid);
			String conscharge = req.getParameter("cons-charge-"+consultationid);
			
			CHSSConsultation consult= new CHSSConsultation();
			consult.setConsultationId(Long.parseLong(consultationid));
			consult.setConsultType(consulttype);
			consult.setDocName(docname);
			consult.setDocQualification(docqualification);
			consult.setConsultDate(sdf.format(rdf.parse(consdate)));
			consult.setConsultCharge(Integer.parseInt(conscharge));
			consult.setModifiedBy(Username);
			
			long count = service.ConsultationBillEdit(consult);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultationBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ConsultationBillDelete.htm", method = RequestMethod.POST )
	public String ConsultationBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultationBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String consultationid = req.getParameter("consultationid"); 
						
			long count = service.ConsultationBillDelete(consultationid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultationBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "MedicinesBillAdd.htm", method = RequestMethod.POST )
	public String MedicinesBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MedicinesBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] medsname=req.getParameterValues("meds-name");
			String[] medsdate=req.getParameterValues("meds-date");
			String[] medscost=req.getParameterValues("meds-cost");
			
			CHSSMedicineDto dto=new CHSSMedicineDto();
			
			dto.setBillId(billid);
			dto.setMedicineName(medsname);
			dto.setMedicineDate(medsdate);
			dto.setMedicineCost(medscost);
			
//			dto.setCreatedBy(Username);
			
			long count= service.MedicinesBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedicinesBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ChssMedicinesListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ChssMedicinesListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ChssMedicinesListAjax.htm "+Username);
		List<CHSSMedicine> list=new ArrayList<CHSSMedicine>();
		try {
			String billid = req.getParameter("billid");
			list = service.CHSSMedicineList(billid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ChssMedicinesListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "MedicineBillEdit.htm", method = RequestMethod.POST )
	public String MedicineBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MedicineBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String medicineid = req.getParameter("medicineid"); 
			
			String medsname = req.getParameter("meds-name-"+medicineid);
			String medsdate = req.getParameter("meds-date-"+medicineid);
			String medscost = req.getParameter("meds-cost-"+medicineid);
			
			CHSSMedicine meds= new CHSSMedicine();
			meds.setMedicineId(Long.parseLong(medicineid));
			meds.setMedicineName(medsname);
			meds.setMedicineDate(sdf.format(rdf.parse(medsdate)));
			meds.setMedicineCost(Integer.parseInt(medscost));
			
			
			long count = service.MedicineBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Medicines Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Medicines Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedicineBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "MedicineBillDelete.htm", method = RequestMethod.POST )
	public String MedicineBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MedicineBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String medicineid = req.getParameter("medicineid"); 
						
			long count = service.MedicineBillDelete(medicineid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Medicines Data Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Medicines Data Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedicineBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
}

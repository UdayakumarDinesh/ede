package com.vts.ems.chss.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSMiscDto;
import com.vts.ems.chss.Dto.CHSSOtherDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
import com.vts.ems.chss.service.CHSSService;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;

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
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSApply.htm "+Username);
		try {
			
			
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("empfamilylist", service.familyDetailsList(EmpId));
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
			String ailment = req.getParameter("ailment");
			
			String[] centernames=req.getParameterValues("centername");
			String[] billno=req.getParameterValues("billno");
			String[] billdate=req.getParameterValues("billdate");
			
			CHSSApplyDto dto=new CHSSApplyDto();
			dto.setRelationId(relationid);
			dto.setEmpId(EmpId);
			dto.setPatientId(patientid);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);

			dto.setTreatTypeId(treatmenttype);
			dto.setCHSSType("OPD");
			dto.setCreatedBy(Username);
			dto.setNoEnclosures(noenclosures);
			dto.setAilment(ailment);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
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
	public String CHSSAppliedDetails(Model model,HttpServletRequest req, HttpSession ses,  RedirectAttributes redir)throws Exception
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
			String billid = req.getParameter("billid");
			if (billid == null) 
			{
				Map md=model.asMap();
				billid=(String)md.get("billid");
			}	
			if(chssapplyid==null) {
				redir.addAttribute("result", "Refresh Not Allowed");
				return "redirect:/CHSSAppliedList.htm";
			}
						
			Object[] apply= service.CHSSAppliedData(chssapplyid);
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("treattypelist", service.CHSSTreatTypeList());
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			req.setAttribute("testmainlist", service.CHSSTestMainList());
			req.setAttribute("otheritemslist", service.OtherItemsList());
			req.setAttribute("billid", billid);
			
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
			
			CHSSBill bill = new CHSSBill();
			bill.setBillId(Long.parseLong(billid));
			bill.setCenterName(centername);
			bill.setBillNo(billno);
			bill.setBillDate(sdf.format(rdf.parse(billdate)));
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
			
			CHSSApplyDto dto=new CHSSApplyDto();
			
			dto.setCHSSApplyId(chssapplyid);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
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
			
			String ailment = req.getParameter("ailment");
			String enclosurecount = req.getParameter("enclosurecount");
			String treatmenttype = req.getParameter("treatmenttype");
			
			CHSSApplyDto dto= new CHSSApplyDto();
			dto.setCHSSApplyId(chssapplyid);
			dto.setNoEnclosures(enclosurecount);
			dto.setTreatTypeId(treatmenttype);
			dto.setModifiedBy(Username);
			dto.setAilment(ailment);
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
		Object[] bill=null;
		try {
			String billid = req.getParameter("billid");
			bill = service.CHSSBill(billid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside GetBillDataAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(bill);
	}
	
	
	
	@RequestMapping(value = "GetTestMainListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String GetTestMainListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetTestMainListAjax.htm "+Username);
		List<CHSSTestMain> list=new ArrayList<CHSSTestMain>();
		try {
			list = service.CHSSTestMainList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside GetTestMainListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
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
			
//			dto.setCreatedBy(Username);
			
			long count= service.ConsultationBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
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
//			consult.setModifiedBy(Username);
			
			long count = service.ConsultationBillEdit(consult);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
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
			redir.addFlashAttribute("billid",req.getParameter("billid"));
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
			String[] medsquantity=req.getParameterValues("meds-quantity");
			CHSSMedicineDto dto=new CHSSMedicineDto();
			
			dto.setBillId(billid);
			dto.setMedicineName(medsname);
			dto.setMedicineDate(medsdate);
			dto.setMedicineCost(medscost);
			dto.setMedQuantity(medsquantity);
			
			long count= service.MedicinesBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
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
			String medsquantity = req.getParameter("meds-quantity-"+medicineid);

			CHSSMedicine meds= new CHSSMedicine();
			meds.setMedicineId(Long.parseLong(medicineid));
			meds.setMedicineName(medsname);
			meds.setMedicineDate(sdf.format(rdf.parse(medsdate)));
			meds.setMedicineCost(Integer.parseInt(medscost));
			meds.setMedQuantity(Integer.parseInt(medsquantity));
			
			long count = service.MedicineBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Medicines Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Medicines Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
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
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedicineBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "TestsBillAdd.htm", method = RequestMethod.POST )
	public String TestsBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TestsBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] testtype=req.getParameterValues("test-type");
			String[] testid=req.getParameterValues("test-id");
			String[] testscost=req.getParameterValues("tests-cost");
		
			CHSSTestsDto dto = new CHSSTestsDto();
			dto.setBillId(billid);
			dto.setTestMainId(testtype);
			dto.setTestSubId(testid);
			dto.setTestCost(testscost);
			dto.setCreatedBy(Username);
			
			
			long count= service.TestsBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Tests Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Tests Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TestsBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ChssTestsListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ChssTestsListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ChssTestsListAjax.htm "+Username);
		List<CHSSTests> list=new ArrayList<CHSSTests>();
		try {
			String billid = req.getParameter("billid");
			list = service.CHSSTestsList(billid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ChssTestsListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "TestBillEdit.htm", method = RequestMethod.POST )
	public String TestBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TestBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
			
			String testtype = req.getParameter("test-maintype-"+testid);
			String testsubid = req.getParameter("test-subid-"+testid);
			String testcost = req.getParameter("test-cost-"+testid);
			
			CHSSTests test= new CHSSTests();
			test.setCHSSTestId(Long.parseLong(testid));
			test.setTestMainId(Long.parseLong(testtype));
			test.setTestSubId(Long.parseLong(testsubid));
			test.setTestCost(Integer.parseInt(testcost));
			
			
			long count = service.TestBillEdit(test);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Test Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Test Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TestBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "TestBillDelete.htm", method = RequestMethod.POST )
	public String TestBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TestBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
						
			long count = service.TestBillDelete(testid, Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Test Data Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Test Data Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TestBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	@RequestMapping(value = "MiscBillAdd.htm", method = RequestMethod.POST )
	public String MiscBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MiscBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] miscname=req.getParameterValues("misc-name");
			String[] misccost=req.getParameterValues("misc-cost");
			
			CHSSMiscDto dto=new CHSSMiscDto();
			
			dto.setBillId(billid);
			dto.setMiscItemName(miscname);
			dto.setMiscItemCost(misccost);
			
			
			long count= service.MiscBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill Item Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Item Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MiscBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ChssMiscListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ChssMiscListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ChssMiscListAjax.htm "+Username);
		List<CHSSMisc> list=new ArrayList<CHSSMisc>();
		try {
			String billid = req.getParameter("billid");
			list = service.CHSSMiscList(billid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ChssMiscListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "MiscBillEdit.htm", method = RequestMethod.POST )
	public String MiscBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MiscBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssmiscid = req.getParameter("chssmiscid"); 
			
			String medsname = req.getParameter("misc-name-"+chssmiscid);
			String micscost = req.getParameter("misc-cost-"+chssmiscid);
			
			CHSSMisc meds= new CHSSMisc();
			meds.setChssMiscId(Long.parseLong(chssmiscid));
			meds.setMiscItemName(medsname);
			meds.setMiscItemCost(Integer.parseInt(micscost));
			
			
			long count = service.MiscBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MiscBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "MiscBillDelete.htm", method = RequestMethod.POST )
	public String MiscBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MiscBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssotherid = req.getParameter("chssotherid"); 
						
			long count = service.MiscBillDelete(chssotherid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MiscBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "GetOtherItemsListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String GetOtherItemsListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetOtherItemsListAjax.htm "+Username);
		List<CHSSOtherItems> bill=new ArrayList<CHSSOtherItems>();
		try {
			bill = service.OtherItemsList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside GetOtherItemsListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(bill);
	}
	
	@RequestMapping(value = "ChssOtherListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ChssOtherListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ChssOtherListAjax.htm "+Username);
		List<CHSSOther> list=new ArrayList<CHSSOther>();
		try {
			String billid = req.getParameter("billid");
			list = service.CHSSOtherList(billid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ChssOtherListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "OtherBillAdd.htm", method = RequestMethod.POST )
	public String OtherBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside OtherBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] otheritemid=req.getParameterValues("otheritemid");
			String[] otheritemcost=req.getParameterValues("otheritemcost");
			
			CHSSOtherDto dto=new CHSSOtherDto();
			
			dto.setBillId(billid);
			dto.setOtherItemId(otheritemid);
			dto.setOtherItemCost(otheritemcost);
			dto.setEmpid(EmpId);
			
			long count= service.OtherBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill Item Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Item Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OtherBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "OtherBillEdit.htm", method = RequestMethod.POST )
	public String OtherBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside OtherBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssotherid = req.getParameter("chssotherid"); 
			
			String otheritemid = req.getParameter("otheritemid-"+chssotherid);
			String otheritemcost = req.getParameter("otheritemcost-"+chssotherid);
			
			CHSSOther meds= new CHSSOther();
			meds.setCHSSOtherId(Long.parseLong(chssotherid));
			meds.setOtherItemId(Integer.parseInt(otheritemid));
			meds.setOtherItemCost(Integer.parseInt(otheritemcost));
			
			
			long count = service.OtherBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OtherBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "OtherBillDelete.htm", method = RequestMethod.POST )
	public String OtherBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside OtherBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssotherid = req.getParameter("chssotherid"); 
						
			long count = service.OtherBillDelete(chssotherid, Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSAppliedDetails.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OtherBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSForm.htm", method = RequestMethod.POST )
	public String CHSSForm(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSForm.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			Object[] chssapplicationdata = service.CHSSAppliedData(chssapplyid);
			Employee employee = service.getEmployee(chssapplicationdata[1].toString());
			
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			req.setAttribute("TestsDataList", service.CHSSTestsDataList(chssapplyid));
			req.setAttribute("MiscDataList", service.CHSSMiscDataList(chssapplyid));
			req.setAttribute("ConsultDataList", service.CHSSConsultDataList(chssapplyid));
			req.setAttribute("MedicineDataList", service.CHSSMedicineDataList(chssapplyid));
			req.setAttribute("OtherDataList", service.CHSSOtherDataList(chssapplyid));
			
			req.setAttribute("chssapplydata", chssapplicationdata);
			req.setAttribute("employee", employee);
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			
			return "chss/CHSSForm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSForm.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSFormEmpDownload.htm")
	public void CHSSFormEmpDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside CHSSFormEmpDownload.htm "+UserId);
		
		try {	
			String chssapplyid = req.getParameter("chssapplyid");
			
			Object[] chssapplicationdata = service.CHSSAppliedData(chssapplyid);
			Employee employee = service.getEmployee(chssapplicationdata[1].toString());
			
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			
			req.setAttribute("ConsultDataList", service.CHSSConsultDataList(chssapplyid));
			req.setAttribute("TestsDataList", service.CHSSTestsDataList(chssapplyid));
			req.setAttribute("MiscDataList", service.CHSSMiscDataList(chssapplyid));
			req.setAttribute("MedicineDataList", service.CHSSMedicineDataList(chssapplyid));
			req.setAttribute("OtherDataList", service.CHSSOtherDataList(chssapplyid));
			req.setAttribute("chssapplydata", chssapplicationdata);
			req.setAttribute("employee", employee);
			
			String filename="CHSS-Claim";
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
			
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/chss/CHSSForm.jsp").forward(req, customResponse);
			String html1 = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html1,new FileOutputStream(path+File.separator+filename+".pdf")); 
	         
	        res.setContentType("application/pdf");
	        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
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
			logger.error(new Date() +" Inside CHSSFormEmpDownload.htm "+UserId, e); 
		}

	}
	
	@RequestMapping(value = "CHSSClaimFwdApproveAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String CHSSClaimFwdApproveAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSClaimFwdApproveAjax.htm "+Username);
		long allow=0;
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			List<Object[]> claimdata = service.CHSSBillsList(chssapplyid);
			long claimamount=0;
			for(Object[] bill : claimdata) {
				claimamount += Long.parseLong(bill[5].toString());
			}
			
			if(claimamount>0) {
				allow=1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSClaimFwdApproveAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(allow);
	}
	
	@RequestMapping(value = "CHSSUserForward.htm", method = RequestMethod.POST)
	public String CHSSUserForward(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSUserForward.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String action = req.getParameter("claimaction");
			
			long count = service.CHSSUserForward(chssapplyid, Username, action);
			if (count > 0) {
				redir.addAttribute("result", "Claim application Forwarded Successfully");
			} else {
				redir.addAttribute("resultfail", "Claim application Forwarding Unsuccessful");	
			}	
			return "redirect:/CHSSAppliedList.htm";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSUserForward.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	@RequestMapping(value = "CHSSApprovalsList.htm", method = RequestMethod.POST)
	public String CHSSApprovalsList(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSApprovalsList.htm "+Username);
		try {
			String fromdate =req.getParameter("fromdate");
			String todate =req.getParameter("todate");
			
			if(fromdate ==null || todate==null) 
			{
				LocalDate today= LocalDate.now();
								
				fromdate = today.minusMonths(1).withDayOfMonth(21).toString();
				todate = today.minusDays(today.getDayOfMonth()).plusDays(20).toString();
				
			}else
			{
				fromdate = sdf.format(rdf.parse(fromdate));
				todate = sdf.format(rdf.parse(todate));
			}
			
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("chssclaimlist", service.CHSSApproveClaimList(LoginType, fromdate, todate));
			return "chss/CHSSApprovalList";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApprovalsList.htm "+Username, e);
			return "static/Error";
		}
		
	}	
	
	@RequestMapping(value = "CHSSFormEdit.htm")
	public String CHSSFormEdit(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSFormEdit.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			if (chssapplyid == null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
			}	
			
			Object[] chssapplicationdata = service.CHSSAppliedData(chssapplyid);
			Employee employee = service.getEmployee(chssapplicationdata[1].toString());
			
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			req.setAttribute("TestsDataList", service.CHSSTestsDataList(chssapplyid));
			req.setAttribute("MiscDataList", service.CHSSMiscDataList(chssapplyid));
			req.setAttribute("ConsultDataList", service.CHSSConsultDataList(chssapplyid));
			req.setAttribute("MedicineDataList", service.CHSSMedicineDataList(chssapplyid));
			req.setAttribute("OtherDataList", service.CHSSOtherDataList(chssapplyid));
			
			req.setAttribute("chssapplydata", chssapplicationdata);
			req.setAttribute("employee", employee);
			
			
			
			return "chss/CHSSFormEdit";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSFormEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "ConsultRemAmountEdit.htm", method = RequestMethod.POST )
	public String ConsultRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String consultationid = req.getParameter("consultationid"); 
			
			String consultremamount = req.getParameter("consultremamount-"+consultationid);
			
			
			CHSSConsultation consult= new CHSSConsultation();
			consult.setConsultationId(Long.parseLong(consultationid));
			consult.setConsultRemAmount(Integer.parseInt(consultremamount));
			
			long count = service.ConsultRemAmountEdit(consult);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
}

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
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSMiscDto;
import com.vts.ems.chss.Dto.CHSSOtherDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
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
	@Autowired
	AdminService adminservice;
	@RequestMapping(value = "CHSSDashboard.htm" )
	public String CHSSDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSDashboard.htm "+Username);
		
		try {
			String logintype = (String)ses.getAttribute("LoginType");
			
		
			List<Object[]> chssdashboard = adminservice.HeaderSchedulesList("4" ,logintype); 
			req.setAttribute("dashboard", chssdashboard);
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("empfamilylist", service.familyDetailsList(EmpId));
			req.setAttribute("empchsslist", service.empCHSSList(EmpId));
			
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
			redir.addFlashAttribute("billid",service.CHSSBillsList(String.valueOf(count)).get(0)[0].toString());
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
			
			String tab = req.getParameter("tab");
			if (tab == null) 
			{
				Map md=model.asMap();
				tab=(String)md.get("tab");
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
			req.setAttribute("testmainlist", service.CHSSTestSubList(""));
			req.setAttribute("otheritemslist", service.OtherItemsList());
			req.setAttribute("doctorrates", service.getCHSSDoctorRates(apply[7].toString()));
			req.setAttribute("billid", billid);
			req.setAttribute("tab", tab);
			req.setAttribute("consultcount", service.claimConsultationsCount(chssapplyid));
			req.setAttribute("medicinecount", service.claimMedicinesCount(chssapplyid));
			
			
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
			redir.addFlashAttribute("billid",String.valueOf(count));
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
	
	
	
	@RequestMapping(value = "GetTestsListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String GetTestMainListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetTestsListAjax.htm "+Username);
		List<CHSSTestSub> list=new ArrayList<CHSSTestSub>();
		try {
			list = service.CHSSTestSubList("");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside GetTestsListAjax.htm "+Username, e);
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
			redir.addFlashAttribute("tab","co");
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
			redir.addFlashAttribute("tab","co");
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
			redir.addFlashAttribute("tab","co");
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
//			String[] medsdate=req.getParameterValues("meds-date");
			String[] medscost=req.getParameterValues("meds-cost");
			String[] medspresquantity=req.getParameterValues("meds-presquantity");
			String[] medsquantity=req.getParameterValues("meds-quantity");
			CHSSMedicineDto dto=new CHSSMedicineDto();
			
			dto.setBillId(billid);
			dto.setMedicineName(medsname);
//			dto.setMedicineDate(medsdate);
			dto.setMedicineCost(medscost);
			dto.setPresQuantity(medspresquantity);
			dto.setMedQuantity(medsquantity);
			
			long count= service.MedicinesBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","me");
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
//			String medsdate = req.getParameter("meds-date-"+medicineid);
			String medscost = req.getParameter("meds-cost-"+medicineid);
			String medsquantity = req.getParameter("meds-quantity-"+medicineid);
			String medspresquantity = req.getParameter("meds-presquantity-"+medicineid);
			
			CHSSMedicine meds= new CHSSMedicine();
			meds.setMedicineId(Long.parseLong(medicineid));
			meds.setMedicineName(medsname);
//			meds.setMedicineDate(sdf.format(rdf.parse(medsdate)));
			meds.setMedicineCost(Integer.parseInt(medscost));
			meds.setMedQuantity(Integer.parseInt(medsquantity));
			meds.setPresQuantity(Integer.parseInt(medspresquantity));
			
			
			long count = service.MedicineBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Medicines Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Medicines Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","me");
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
			redir.addFlashAttribute("tab","me");
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
			redir.addFlashAttribute("tab","te");
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
			test.setTestMainId(Long.parseLong(testsubid.split("_")[0]));
			test.setTestSubId(Long.parseLong(testsubid.split("_")[1]));
			test.setTestCost(Integer.parseInt(testcost));
			
			
			long count = service.TestBillEdit(test);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Test Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Test Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","te");
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
			redir.addFlashAttribute("tab","te");
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
			redir.addFlashAttribute("tab","mi");
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
			redir.addFlashAttribute("tab","mi");
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
			redir.addFlashAttribute("tab","mi");
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
			redir.addFlashAttribute("tab","ot");
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
			redir.addFlashAttribute("tab","ot");
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
			redir.addFlashAttribute("tab","ot");
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
			
			String isapproval = req.getParameter("isapproval");
			
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
			
			req.setAttribute("isapproval", isapproval);
			
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
			
			int consultationcount = Integer.parseInt(service.claimConsultationsCount(chssapplyid)[0].toString());
			
			if(claimamount>0  ) 
			{
				if(consultationcount<1) 
				{
					allow=-1;
				}
				else
				{
					allow=1;
				}
			}else
			{
				allow=0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSClaimFwdApproveAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(allow);
	}
	
	@RequestMapping(value = "CHSSDoctorRatesAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String CHSSDoctorRatesAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSDoctorRatesAjax.htm "+Username);
		List<CHSSDoctorRates> docrates = new ArrayList<CHSSDoctorRates>();
		try {
			String treattypeid = req.getParameter("treattypeid");
			docrates = service.getCHSSDoctorRates(treattypeid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSDoctorRatesAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(docrates);
	}
	
	@RequestMapping(value = "CHSSUserPreview.htm", method = RequestMethod.POST)
	public String CHSSUserPreview(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSUserPreview.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String enclosurecount = req.getParameter("enclosurecount");
			
			CHSSApplyDto dto =new CHSSApplyDto();
			dto.setCHSSApplyId(chssapplyid);
			dto.setNoEnclosures(enclosurecount);
			
			service.CHSSApplyEncCountEdit(dto);
			
			redir.addAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSFormEdit.htm";
			
		} catch (Exception e) {
					
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSUserPreview.htm "+Username, e);
			return "static/Error";
		}
			
	}
	
	@RequestMapping(value = "CHSSUserForward.htm", method = RequestMethod.POST)
	public String CHSSUserForward(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSUserForward.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String action = req.getParameter("claimaction");
			String remarks = req.getParameter("remarks");
			
			CHSSApply claim1 = service.CHSSApplied(chssapplyid);
			int chssstatusid= claim1.getCHSSStatusId();
			
			long count = service.CHSSUserForward(chssapplyid, Username, action,remarks,EmpId);
			
			if (chssstatusid == 1 || chssstatusid ==3 ) 
			{
				if (count > 0) {
					redir.addAttribute("result", "Claim application Sent for Processing  Successfully");
				} else {
					redir.addAttribute("resultfail", "Claim application Sent for Processing Unsuccessful");	
				}	
				
			}
			else if (chssstatusid == 2 || chssstatusid ==4 || chssstatusid >=5) 
			{
			 

				if(action.equalsIgnoreCase("F")) {
					if (count > 0) {
						redir.addAttribute("result", "Claim application Forwarded Successfully");
					} else {
						redir.addAttribute("resultfail", "Claim application Forwarding Unsuccessful");	
					}	
				}
				if(action.equalsIgnoreCase("R")) {
					if (count > 0) {
						redir.addAttribute("result", "Claim application Returned Successfully");
					} else {
						redir.addAttribute("resultfail", "Claim application Return Unsuccessful");	
					}	
				}
				
				if(chssstatusid>=6) 
				{
					return "redirect:/CHSSBatchList.htm";
				}else
				{
					return "redirect:/CHSSApprovalsList.htm";
				}
			} 
			return "redirect:/CHSSAppliedList.htm";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSUserForward.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	
	@RequestMapping(value = "CHSSApprovalsList.htm" )
	public String CHSSApprovalsList(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSApprovalsList.htm "+Username);
		try {
						
			req.setAttribute("chssclaimlist", service.CHSSApproveClaimList(LoginType));
			return "chss/CHSSApprovalList";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApprovalsList.htm "+Username, e);
			return "static/Error";
		}
		
	}	
	
	@RequestMapping(value = "CHSSFormEdit.htm", method = {RequestMethod.POST,RequestMethod.GET})
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
	
	@RequestMapping(value = "TestRemAmountEdit.htm", method = RequestMethod.POST )
	public String TestRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TestRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
			
			String testremamount = req.getParameter("testremamount-"+testid);
			
			
			CHSSTests test= new CHSSTests();
			test.setCHSSTestId(Long.parseLong(testid));
			test.setTestRemAmount(Integer.parseInt(testremamount));
			
			long count = service.TestRemAmountEdit(test);
			
			
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
			logger.error(new Date() +" Inside TestRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "OtherRemAmountEdit.htm", method = RequestMethod.POST )
	public String OtherRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside OtherRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String otherid = req.getParameter("otherid"); 
			
			String otheridremamount = req.getParameter("otherremamount-"+otherid);
			
			
			CHSSOther other= new CHSSOther();
			other.setCHSSOtherId(Long.parseLong(otherid));
			other.setOtherRemAmount(Integer.parseInt(otheridremamount));
			
			long count = service.OtherRemAmountEdit(other);
			
			
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
			logger.error(new Date() +" Inside OtherRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "MedRemAmountEdit.htm", method = RequestMethod.POST )
	public String MedRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MedRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String medicineid = req.getParameter("medicineid"); 
			
			String medicineremamount = req.getParameter("medicineremamount-"+medicineid);
			
			
			CHSSMedicine medicine= new CHSSMedicine();
			medicine.setMedicineId(Long.parseLong(medicineid));
			medicine.setMedsRemAmount(Integer.parseInt(medicineremamount));
			
			long count = service.MedRemAmountEdit(medicine);
			
			
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
			logger.error(new Date() +" Inside MedRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "MiscRemAmountEdit.htm", method = RequestMethod.POST )
	public String MiscRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MiscRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String miscid = req.getParameter("miscid"); 
			
			String miscremamount = req.getParameter("miscremamount-"+miscid);
			
			
			CHSSMisc misc= new CHSSMisc();
			misc.setChssMiscId(Long.parseLong(miscid));
			misc.setMiscRemAmount(Integer.parseInt(miscremamount));
			
			long count = service.MiscRemAmountEdit(misc);
			
			
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
			logger.error(new Date() +" Inside MiscRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSBatchList.htm" )
	public String CHSSBatchApproval(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSBatchList.htm "+Username);
		try {

			String fromdate = req.getParameter("fromdate");
			String todate = req.getParameter("todate");
			
			if(fromdate == null || todate == null) 
			{
				fromdate = LocalDate.now().minusMonths(1).withDayOfMonth(21).toString();
				todate = LocalDate.now().withDayOfMonth(20).toString();
			}else {
				fromdate = sdf.format(rdf.parse(fromdate));
				todate = sdf.format(rdf.parse(todate));				
			}
			
		
			
			List<Object[]> claimslist = service.CHSSBatchApproval(LoginType, fromdate, todate,"0");
			req.setAttribute("chssclaimlist", claimslist);
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			
			return "chss/CHSSBatchList";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBatchList.htm "+Username, e);
			return "static/Error";
		}		
	}	
	
	
	@RequestMapping(value = "CHSSContingentGenerate.htm", method = RequestMethod.POST)
	public String CHSSContingentGenerate(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSContingentGenerate.htm "+Username);
		try {
			String chssapplyid[] = req.getParameterValues("chssapplyidcb");
			String action = req.getParameter("claimaction");
			String billcontent = req.getParameter("billcontent");
			
			long count= service.ContingentGenerate(chssapplyid, Username, action, billcontent, LoginType,EmpId);
//			CHSSContingent contingent = service.getCHSSContingent(String.valueOf(count));
			
			
				if (count > 0) {
					redir.addAttribute("result", "Contingent Bill Generated Successfully");
				} else {
					redir.addAttribute("resultfail", "Contingent Bill Generation Unsuccessful");	
				}	
			redir.addFlashAttribute("contingentid",String.valueOf(count));
			return "redirect:/ContingentBillData.htm";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSContingentGenerate.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	
	@RequestMapping(value = "ContingentBillData.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String ContingentBillData(Model model,HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside ContingentBillData.htm "+Username);
		try {
			
			String contingentid = req.getParameter("contingentid");
			if (contingentid == null) 
			{
				Map md=model.asMap();
				contingentid=(String)md.get("contingentid");
			}	
			
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.CHSSContingentData(contingentid));
			req.setAttribute("logintype", LoginType);
					
			return "chss/ContingentBillView";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingentBillData.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	@RequestMapping(value = "CHSSContingentApprove.htm", method = RequestMethod.POST)
	public String CHSSClaimsApprove(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSClaimsApprove.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			String action = req.getParameter("action");
			String remarks = req.getParameter("remarks");
			
			long count= service.CHSSClaimsApprove(contingentid, Username, action, remarks, LoginType,EmpId);
//			CHSSContingent contingent = service.getCHSSContingent(String.valueOf(count));
			
			
			if(action.equalsIgnoreCase("F")) {
				if (count > 0) {
					redir.addAttribute("result", "Claim application(s) Approved Successfully");
				} else {
					redir.addAttribute("resultfail", "Claim application(s) Approved Unsuccessful");	
				}	
			}
			if(action.equalsIgnoreCase("R")) {
				if (count > 0) {
					redir.addAttribute("result", "Claim application(s) Returned Successfully");
				} else {
					redir.addAttribute("resultfail", "Claim application(s) Return Unsuccessful");	
				}	
			}
			
			return "redirect:/ContingentApprovals.htm";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSClaimsApprove.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	@RequestMapping(value = "ContingentApprovals.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String ContingentApprovals(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside ContingentApprovals.htm "+Username);
		try {
			
			req.setAttribute("ContingentList", service.getCHSSContingentList(LoginType));
						
			return "chss/ContingentBillsList";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingentApprovals.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "ContingetBill.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String ContingetBill(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ContingetBill.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.CHSSContingentData(contingentid));
						
			return "chss/ContingetBill";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingetBill.htm "+Username, e);
			return "static/Error";
		}
	}

	@RequestMapping(value = "Chss-Status-details.htm" , method={RequestMethod.POST,RequestMethod.GET})
	public String ChssStatusDetails(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside Chss-Status-details.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			System.out.println(chssapplyid);
			req.setAttribute("ChssStatisDetails", service.CHSSStatusDetails(chssapplyid));
			
						System.out.println(service.CHSSStatusDetails(chssapplyid).size());
			return "chss/CHSSStatus";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside Chss-Status-details.htm "+Username, e);
			return "static/Error";
		}
	}

	
	
	@RequestMapping(value = "ContingetBillDownload.htm")
	public void ContingetBillDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside CHSSFormEmpDownload.htm "+UserId);
		
		try {	
			String contingentid = req.getParameter("contingentid");
			
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.CHSSContingentData(contingentid));
			
			String filename="CHSSContingentList";
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
			
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/chss/ContingetBill.jsp").forward(req, customResponse);
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
	
	
}

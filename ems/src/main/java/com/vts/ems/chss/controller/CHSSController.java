package com.vts.ems.chss.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.chss.dto.CHSSApplyDto;
import com.vts.ems.chss.dto.CHSSConsultationDto;
import com.vts.ems.chss.dto.CHSSContingentDto;
import com.vts.ems.chss.dto.CHSSEquipDto;
import com.vts.ems.chss.dto.CHSSIPDPackageDto;
import com.vts.ems.chss.dto.CHSSImplantDto;
import com.vts.ems.chss.dto.CHSSMedicineDto;
import com.vts.ems.chss.dto.CHSSMiscDto;
import com.vts.ems.chss.dto.CHSSOtherDto;
import com.vts.ems.chss.dto.CHSSTestsDto;
import com.vts.ems.chss.dto.ChssBillsDto;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSApplyDispute;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSBillConsultation;
import com.vts.ems.chss.model.CHSSBillEquipment;
import com.vts.ems.chss.model.CHSSBillImplants;
import com.vts.ems.chss.model.CHSSBillMedicine;
import com.vts.ems.chss.model.CHSSBillMisc;
import com.vts.ems.chss.model.CHSSBillOther;
import com.vts.ems.chss.model.CHSSBillPkg;
import com.vts.ems.chss.model.CHSSBillTests;
import com.vts.ems.chss.model.CHSSConsultMain;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.chss.model.CHSSIPDClaimsInfo;
import com.vts.ems.chss.model.CHSSIPDPkgItems;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.service.CHSSService;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.CHSSDoctorRates;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.utils.AmountWordConveration;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;
import com.vts.ems.utils.IndianRupeeFormat;

@Controller
public class CHSSController 
{

	private static final Logger logger = LogManager.getLogger(CHSSController.class);
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	@Autowired
	CHSSService service;
	@Autowired
	AdminService adminservice;
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Autowired
	Environment env;
	
	private final String formmoduleid="20";
	
	@RequestMapping(value = "CHSSDashboard.htm")
	public String CHSSDashBoard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
    	String LoginType=(String)ses.getAttribute("LoginType");
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSDashBoard.htm "+Username);		
		try {
			String logintype = (String)ses.getAttribute("LoginType");
			List<Object[]> chssdashboard = adminservice.HeaderSchedulesList("4" ,logintype); 
			ses.setAttribute("formmoduleid", "20");
			req.setAttribute("dashboard", chssdashboard);
			

	    	String IsSelf = req.getParameter("isselfvalue");
			String FromDate = req.getParameter("FromDate");
			String ToDate = req.getParameter("ToDate");
			LocalDate today= LocalDate.now();
			int currentmonth= today.getMonthValue();
			String DbFromDate="";
			String DbToDate="";
			
			if(FromDate== null) {
				String start ="";
				if(currentmonth<4) 
				{
					start = String.valueOf(today.getYear()-1);
				}else{
					start=String.valueOf(today.getYear());
				}
				
				FromDate=start;
				DbFromDate = start+"-04-01";
				
			}
			
			if(ToDate== null) {
				String end="";
				if(currentmonth<4) 
				{
					end =String.valueOf(today.getYear());
				}else{
					end =String.valueOf(today.getYear()+1);
				}
				
				ToDate=end;
				DbToDate=end+"-03-31";
			}
			
			DbFromDate = FromDate+"-04-01";
			DbToDate= ToDate+"-03-31";
				
			if(IsSelf==null) {
				IsSelf="Y";
			}
			if(new File(env.getProperty("ProjectFiles")+ "/ProjectManuals/chss-policy.pdf").exists()) {
			req.setAttribute("chss_policy_pdf", Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(env.getProperty("ProjectFiles")+ "/ProjectManuals/chss-policy.pdf"))) );
			}
			req.setAttribute("countdata", service.CHSSDashboardCountData(EmpId, DbFromDate, DbToDate,IsSelf) );
			req.setAttribute("Fromdate", FromDate);
			req.setAttribute("Todate", ToDate);
			req.setAttribute("graphdata",  service.CHSSDashboardGraphData(EmpId, DbFromDate, DbToDate) );
			req.setAttribute("amountdata", service.CHSSDashboardAmountData(EmpId, DbFromDate, DbToDate,IsSelf));
			req.setAttribute("amountdataindividual", service.CHSSDashboardIndividualAmountData(EmpId, DbFromDate, DbToDate));
			req.setAttribute("logintype", LoginType);
			req.setAttribute("isself", IsSelf);
			req.setAttribute("monthlywisedata", service.MonthlyWiseDashboardData(DbFromDate, DbToDate));
//			req.setAttribute("logintypeslist",service.EmpHandOverLoginTypeList(EmpId,LoginId));
			
			req.setAttribute("EmpanelledHospitals", service.GetEmpanelledHostpitalList());
			req.setAttribute("EmpanelledDoctors", service.GetDoctorEmpanelledList());
			
			ses.setAttribute("SidebarActive","Home");
			return "chss/CHSSDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside CHSSDashboard.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "CHSSApplyDashboard.htm" )
	public String CHSSApplyDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSApplyDashboard.htm "+Username);
		
		try {
			ses.setAttribute("SidebarActive","CHSSApplyDashboard_htm");
	
			String PatientId="0";
			if(req.getParameter("patientidvalue")!=null) {
				PatientId= req.getParameter("patientidvalue");
			}
			String IsSelf="";
			if(req.getParameter("isselfvalue")!=null) {
				IsSelf= req.getParameter("isselfvalue");
			}
			String PatientName="All";
			if(req.getParameter("patientname")!=null) {
				PatientName=req.getParameter("patientname");
			}
			
			System.out.println("IsSelf"+IsSelf);
			String FromDate= req.getParameter("fromdate");
			String ToDate= req.getParameter("todate");						
			LocalDate today=LocalDate.now();
			
			if(FromDate==null) 
			{
				if(today.getMonthValue()<4) 
				{
					FromDate = String.valueOf(today.getYear()-1);
					ToDate=String.valueOf(today.getYear());
					
				}else{
					FromDate = String.valueOf(today.getYear());
					ToDate=String.valueOf(today.getYear()+1);
				}
				FromDate ="01-04-"+FromDate;
				ToDate ="31-03-"+ToDate;
			} /*
				 * else { FromDate=DateTimeFormatUtil.RegularToSqlDate(FromDate);
				 * ToDate=DateTimeFormatUtil.RegularToSqlDate(ToDate); }
				 */
		
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("empfamilylist", service.familyDetailsList(EmpId));
			req.setAttribute("empchsslist", service.empCHSSList(EmpId,PatientId,FromDate,ToDate,IsSelf));
			req.setAttribute("Fromdate", FromDate );
			req.setAttribute("Todate", ToDate );
			req.setAttribute("patientidvalue", req.getParameter("patientidvalue"));
			
			req.setAttribute("patientname", PatientName);
			req.setAttribute("IsSelf", IsSelf);
			
			
			return "chss/CHSSApplyDashboard";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApplyDashboard.htm "+Username, e);
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
			return "chss/CHSSClaimApply";
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
//			req.setAttribute("doctorrates", service.getCHSSDoctorRates(apply[7].toString()));
			
			if(isself.equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(patientid));
			}
			return "chss/CHSSClaimDetails";
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
			String chsstype = req.getParameter("chsstype");
			
			String[] consulttype=req.getParameterValues("consult-type");
			String[] docname=req.getParameterValues("doc-name");
			String[] consdate=req.getParameterValues("cons-date");
			
			CHSSApplyDto dto=new CHSSApplyDto();
			dto.setRelationId(relationid);
			dto.setEmpId(EmpId);
			dto.setPatientId(patientid);
			
//			dto.setConsulttype(consulttype);
			dto.setDocName(docname);
			dto.setConsultDate(consdate);

			dto.setTreatTypeId(treatmenttype);
			dto.setCHSSType(chsstype);
			dto.setCreatedBy(Username);
			dto.setNoEnclosures(noenclosures);
			dto.setAilment(ailment);
			
			dto.setCreatedBy(Username);
			
			
			long count= service.CHSSApplySubmit(dto);
			if (count > 0) 
			{
				redir.addAttribute("result", "Claim Created Successfully");
				
				if(chsstype.equalsIgnoreCase("OPD")) {
					redir.addFlashAttribute("chssapplyid",String.valueOf(count));
					return "redirect:/CHSSConsultMainData.htm";
				}else if(chsstype.equalsIgnoreCase("IPD"))
				{
					redir.addFlashAttribute("chssapplyid",String.valueOf(count));
					return "redirect:/CHSSIPDApply.htm";
				}
				
			} else {
				redir.addAttribute("resultfail", "Internal Error !");	
				return "redirect:/CHSSApplyDashboard.htm";
			}	
			redir.addFlashAttribute("chssapplyid",String.valueOf(count));
			return "redirect:/CHSSConsultMainData.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSApplySubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	

	@RequestMapping(value = "CHSSMedicinesListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String CHSSMedicinesListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSMedicinesListAjax.htm "+Username);
		List<CHSSMedicinesList> list=new ArrayList<CHSSMedicinesList>();
		try {
			String treattypeid = req.getParameter("treattypeid");
			list = service.getCHSSMedicinesList(treattypeid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSMedicinesListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "CHSSConsultMainAdd.htm" )
	public String CHSSBilCHSSConsultMainAddlAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSConsultMainAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			String[] docname=req.getParameterValues("docname");
			String[] consultdate=req.getParameterValues("consultdate");
			String[] docqualification=req.getParameterValues("doc-qualification");
			
			CHSSApplyDto dto=new CHSSApplyDto();
			
			dto.setCHSSApplyId(chssapplyid);
			dto.setDocName(docname);
//			dto.setConsultDate(consultdate); 
			dto.setDocQualification(docqualification);
			dto.setCreatedBy(Username);
			
			long count= service.CHSSApplySubmit(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("consultmainid",String.valueOf(count));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSConsultMainAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSConsultMainEdit.htm", method = RequestMethod.POST )
	public String CHSSConsultMainEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSConsultMainEdit.htm "+Username);
		try {			
			String consultmainid = req.getParameter("consultmainid");
			String docname = req.getParameter("docname-"+consultmainid);
			String consultdate = req.getParameter("consultdate-"+consultmainid);
			String docqualification = req.getParameter("doc-qualification-"+consultmainid);
			
			CHSSConsultMain consultmain = new CHSSConsultMain();
			consultmain.setCHSSConsultMainId(Long.parseLong(consultmainid));
			consultmain.setDocName(docname);
//			consultmain.setConsultDate(sdf.format(rdf.parse(consultdate)));
			consultmain.setModifiedBy(Username);
			consultmain.setDocQualification(Integer.parseInt(docqualification));
			long count = service.CHSSConsultMainEdit(consultmain);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			return "redirect:/CHSSConsultMainData.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSConsultMainEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSConsultMainDelete.htm", method = RequestMethod.POST )
	public String CHSSConsultMainDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSConsultMainDelete.htm "+Username);
		try {
			
			String consultmainid = req.getParameter("consultmainid");
			long count = service.CHSSConsultMainDelete(consultmainid,Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data  Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data  Deleting Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			return "redirect:/CHSSConsultMainData.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSConsultMainDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSConsultBills.htm" )
	public String CHSSConsultBills(Model model,HttpServletRequest req, HttpSession ses,  RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSConsultBills.htm "+Username);
		try {
			String consultmainid = req.getParameter("consultmainid");
			if (consultmainid == null) 
			{
				Map md=model.asMap();
				consultmainid=(String)md.get("consultmainid");
			}	
			
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
				return "redirect:/CHSSApplyDashboard.htm";
			}
						
			Object[] apply= service.CHSSAppliedData(chssapplyid);
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", service.getEmployee(EmpId));
//			req.setAttribute("treattypelist", service.CHSSTreatTypeList());
			req.setAttribute("chssbillslist", service.CHSSConsultMainBillsList(consultmainid,chssapplyid));
			req.setAttribute("otheritemslist", service.OtherItemsList());
			req.setAttribute("testmainlist", service.CHSSTestSubList(apply[7].toString()));
			req.setAttribute("doctorrates", service.getCHSSDoctorRates(apply[7].toString()));
			req.setAttribute("billid", billid);
			req.setAttribute("tab", tab);
			req.setAttribute("consultcount", service.claimConsultationsCount(chssapplyid));
			req.setAttribute("medicinecount", service.claimMedicinesCount(chssapplyid));
			req.setAttribute("allowedmed", service.getCHSSMedicinesList(apply[7].toString()));
			
			req.setAttribute("consultmain", service.getCHSSConsultMain(consultmainid));
			req.setAttribute("consultmainid", consultmainid);	
			if(apply[3].toString().equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(apply[2].toString()));
			}
			
			if(apply[6].toString().equalsIgnoreCase("OPD")) 
			{
				return "chss/CHSSConsultBillsData";
			}
			else 
			{
				redir.addFlashAttribute("chssapplyid",chssapplyid);
				return "redirect:/CHSSIPDApply.htm";
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSConsultBills.htm "+Username, e);
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
			String consultmainid = req.getParameter("consultmainid");
			
			String[] centernames=req.getParameterValues("centername");
			String[] billno=req.getParameterValues("billno");
			String[] billdate=req.getParameterValues("billdate");
			String[] GSTAmt=req.getParameterValues("GSTAmt");
			String[] DiscountAmt=req.getParameterValues("DiscountAmt");
			String[] DiscountPer=req.getParameterValues("DiscountPer");
			String[] finalbillamount=req.getParameterValues("finalbillamount");
			
			ChssBillsDto dto=new ChssBillsDto();
			
			dto.setCHSSApplyId(chssapplyid);
			dto.setCHSSConsultMainId(consultmainid);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
			dto.setFinalbillamount(finalbillamount);
			dto.setGSTAmount(GSTAmt);
			dto.setDiscountPer(DiscountPer);
			dto.setDiscount(DiscountAmt);
			dto.setCreatedBy(Username);
			
			long count= service.CHSSConsultBillsAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",String.valueOf(count));
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSIPDBillAdd.htm" )
	public String CHSSIPDBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String consultmainid = req.getParameter("consultmainid");
			
			String[] centernames=req.getParameterValues("centername");
			String[] billno=req.getParameterValues("billno");
			String[] billdate=req.getParameterValues("billdate");
			String[] GSTAmt=req.getParameterValues("GSTAmt");
			String[] DiscountAmt=req.getParameterValues("DiscountAmt");
			String[] DiscountPer=req.getParameterValues("DiscountPer");
			String[] finalbillamount=req.getParameterValues("finalbillamount");
			
			ChssBillsDto dto=new ChssBillsDto();
			
			dto.setCHSSApplyId(chssapplyid);
			dto.setCHSSConsultMainId(consultmainid);
			dto.setCenterName(centernames);
			dto.setBillNo(billno);
			dto.setBillDate(billdate);
			dto.setFinalbillamount(finalbillamount);
			dto.setGSTAmount(GSTAmt);
			dto.setDiscountPer(DiscountPer);
			dto.setDiscount(DiscountAmt);
			dto.setCreatedBy(Username);
			
			long count= service.CHSSIPDBillsAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Bill Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",String.valueOf(count));
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	public double CropTo2Decimal(String Amount)throws Exception
	{
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		return Double.parseDouble(decimalformat.format(Double.parseDouble(Amount)));
	}
	
	public double CropTo6Decimal(String Amount)throws Exception
	{
		DecimalFormat decimalformat = new DecimalFormat("0.000000");
		return Double.parseDouble(decimalformat.format(Double.parseDouble(Amount)));
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
			String GSTAmt = req.getParameter("GSTAmt-"+billid);
			String Discount = req.getParameter("Discount-"+billid);
			String finalbillamount = req.getParameter("finalbillamount-"+billid);
			String DiscountPer = req.getParameter("DiscountPer-"+billid);
			
			CHSSBill bill = new CHSSBill();
			bill.setBillId(Long.parseLong(billid));
			bill.setCenterName(centername);
			bill.setBillNo(billno);
			bill.setBillDate(sdf.format(rdf.parse(billdate)));
//			bill.setGSTAmount(CropTo2Decimal(GSTAmt));
			bill.setGSTAmount(0.00);
			bill.setDiscount(CropTo2Decimal(Discount));
			bill.setDiscountPercent(CropTo6Decimal(DiscountPer));
			bill.setFinalBillAmt(CropTo2Decimal(finalbillamount));
			bill.setModifiedBy(Username);
			
			long count = service.CHSSBillEdit(bill);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSIPDBillEdit.htm", method = RequestMethod.POST )
	public String CHSSIPDBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDBillEdit.htm "+Username);
		try {
			String billid = req.getParameter("billid");
			String centername = req.getParameter("centername-"+billid);
			String billno = req.getParameter("billno-"+billid);
			String billdate = req.getParameter("billdate-"+billid);
			String finalbillamount = req.getParameter("finalbillamount-"+billid);
			
			CHSSBill bill = new CHSSBill();
			bill.setBillId(Long.parseLong(billid));
			bill.setCenterName(centername);
			bill.setBillNo(billno);
			bill.setBillDate(sdf.format(rdf.parse(billdate)));
//			bill.setGSTAmount(CropTo2Decimal(GSTAmt));
//			bill.setGSTAmount(0.00);
//			bill.setDiscount(CropTo2Decimal(Discount));
//			bill.setDiscountPercent(CropTo6Decimal(DiscountPer));
			bill.setFinalBillAmt(CropTo2Decimal(finalbillamount));
			bill.setModifiedBy(Username);
			
			long count = service.CHSSIPDBillEdit(bill);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDBillEdit.htm "+Username, e);
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
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			
			Object[] apply=service.CHSSAppliedData(req.getParameter("chssapplyid"));
			if(apply[6].toString().equalsIgnoreCase("OPD")) 
			{
				return "redirect:/CHSSConsultBills.htm";
			}
			else 
			{
				return "redirect:/CHSSIPDApply.htm";
			}
			
			
//			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSBillDelete.htm "+Username, e);
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
			String claimtype = req.getParameter("chsstype");
			CHSSApplyDto dto= new CHSSApplyDto();
			dto.setCHSSApplyId(chssapplyid);
			dto.setNoEnclosures(enclosurecount);
			dto.setTreatTypeId(treatmenttype);
			dto.setModifiedBy(Username);
			dto.setAilment(ailment);
			dto.setCHSSType(claimtype);
			long count = service.CHSSApplyEdit(dto);
						
			if (count > 0) {
				redir.addAttribute("result", "CHSS Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "CHSS Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			if(claimtype.equalsIgnoreCase("OPD")) {
				return "redirect:/CHSSConsultMainData.htm";
			}
			else 
			{
				return "redirect:/CHSSIPDApply.htm";
			}
						
			
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
			String treattypeid = req.getParameter("treattypeid");
			list = service.CHSSTestSubList(treattypeid);
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
			String consultmainidold=req.getParameter("consultmainid-old");
			CHSSConsultationDto dto=new CHSSConsultationDto();
			
			dto.setBillId(billid);
			dto.setConsultType(consulttype);
			dto.setDocName(docname);
			dto.setDocQualification(docqualification);
			dto.setConsultDate(consdate);
			dto.setConsultCharge(conscharge);
			dto.setCreatedBy(Username);
			
			long count= service.ConsultationBillAdd(dto,chssapplyid,consultmainidold);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","co");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultationBillAdd.htm "+Username, e);
			return "static/Error";
		}
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
			String consultmainidold=req.getParameter("consultmainid-old");
			
			CHSSBillConsultation consult= new CHSSBillConsultation();
			consult.setConsultationId(Long.parseLong(consultationid));
			consult.setConsultType(consulttype);
			consult.setDocName(docname);
			consult.setDocQualification(Integer.parseInt(docqualification));
			consult.setConsultDate(sdf.format(rdf.parse(consdate)));
			consult.setConsultCharge(Double.parseDouble(conscharge));
			consult.setModifiedBy(Username);
			consult.setBillId(Long.parseLong(req.getParameter("billid")));
			long count = service.ConsultationBillEdit(consult,chssapplyid,consultmainidold);
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","co");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultationBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	

	@RequestMapping(value = "ChssConsultationListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ChssConsultationListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ChssConsultationListAjax.htm "+Username);
		List<CHSSBillConsultation> list=new ArrayList<CHSSBillConsultation>();
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
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			
			
			return "redirect:/CHSSConsultBills.htm";
				
			
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
			String[] medscost=req.getParameterValues("meds-cost");
			String[] medspresquantity=req.getParameterValues("meds-presquantity");
			String[] medsquantity=req.getParameterValues("meds-quantity");
			CHSSMedicineDto dto=new CHSSMedicineDto();
			
			dto.setBillId(billid);
			dto.setMedicineName(medsname);
			dto.setMedicineCost(medscost);
			dto.setPresQuantity(medspresquantity);
			dto.setMedQuantity(medsquantity);
			dto.setCreatedBy(Username);
			
			long count= service.MedicinesBillAdd(dto,chssapplyid);
			if (count > 0) {
				redir.addAttribute("result", "Medicine Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Medicine Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","me");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
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
		List<CHSSBillMedicine> list=new ArrayList<CHSSBillMedicine>();
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
			String medscost = req.getParameter("meds-cost-"+medicineid);
			String medsquantity = req.getParameter("meds-quantity-"+medicineid);
			String medspresquantity = req.getParameter("meds-presquantity-"+medicineid);
			
			CHSSBillMedicine meds= new CHSSBillMedicine();
			meds.setCHSSMedicineId(Long.parseLong(medicineid));
			meds.setMedicineName(medsname);
			meds.setMedicineCost(Double.parseDouble(medscost));
			meds.setMedQuantity(Integer.parseInt(medsquantity));
			meds.setPresQuantity(Integer.parseInt(medspresquantity));
			meds.setModifiedBy(Username);
			meds.setBillId(Long.parseLong(req.getParameter("billid")));
			
			long count = service.MedicineBillEdit(meds,chssapplyid);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Medicine(s) Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Medicine(s) Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","me");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
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
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
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
				redir.addAttribute("result", "Tests/Procedures Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Tests/Procedures Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","te");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			
		
			return "redirect:/CHSSConsultBills.htm";
			

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
		List<CHSSBillTests> list=new ArrayList<CHSSBillTests>();
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
			
			String testsubid = req.getParameter("test-subid-"+testid);
			String testcost = req.getParameter("test-cost-"+testid);
			
			CHSSBillTests test= new CHSSBillTests();
			test.setCHSSTestId(Long.parseLong(testid));
			test.setTestMainId(Long.parseLong(testsubid.split("_")[0]));
			test.setTestSubId(Long.parseLong(testsubid.split("_")[1]));
			test.setTestCost(Double.parseDouble(testcost));
			test.setBillId(Long.parseLong(req.getParameter("billid")));
			test.setModifiedBy(Username);
			
			long count = service.TestBillEdit(test);
			
			if (count > 0) {
				redir.addAttribute("result", "Test/Procedure/Investigation Details Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Test/Procedure/Investigation Details Update Unsuccessful");	
			}	
			
			
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","te");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));

		
			return "redirect:/CHSSConsultBills.htm";
			
			
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
				redir.addAttribute("result", "Test/Procedure/Investigation Details Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Test/Procedure/Investigation Details Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","te");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			
		
			return "redirect:/CHSSConsultBills.htm";
			
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
			String[] misccount=req.getParameterValues("misc-count");
			
			CHSSMiscDto dto=new CHSSMiscDto();
			
			dto.setBillId(billid);
			dto.setMiscItemName(miscname);
			dto.setMiscItemCost(misccost);
			dto.setMiscCount(misccount);
			dto.setCreatedBy(Username);
			
			long count= service.MiscBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Item Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","mi");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			Object[] apply=service.CHSSAppliedData(chssapplyid);
			
			if(apply[6].toString().equalsIgnoreCase("OPD")) 
			{
				return "redirect:/CHSSConsultBills.htm";
			}
			else 
			{
				return "redirect:/CHSSIPDApply.htm";
			}
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
		List<CHSSBillMisc> list=new ArrayList<CHSSBillMisc>();
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
			String micscount = req.getParameter("misc-count-"+chssmiscid);
			
			CHSSBillMisc meds= new CHSSBillMisc();
			meds.setChssMiscId(Long.parseLong(chssmiscid));
			meds.setMiscItemName(medsname);
			meds.setMiscCount(Integer.parseInt(micscount));
			meds.setMiscItemCost(Double.parseDouble(micscost));
			meds.setModifiedBy(Username);
			
			long count = service.MiscBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","mi");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			Object[] apply=service.CHSSAppliedData(chssapplyid);
			
			if(apply[6].toString().equalsIgnoreCase("OPD")) 
			{
				return "redirect:/CHSSConsultBills.htm";
			}
			else 
			{
				return "redirect:/CHSSIPDApply.htm";
			}
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
			String chssotherid = req.getParameter("chssmiscid"); 
						
			long count = service.MiscBillDelete(chssotherid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","mi");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			Object[] apply=service.CHSSAppliedData(chssapplyid);
			
			if(apply[6].toString().equalsIgnoreCase("OPD")) 
			{
				return "redirect:/CHSSConsultBills.htm";
			}
			else 
			{
				return "redirect:/CHSSIPDApply.htm";
			}
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
		List<CHSSBillOther> list=new ArrayList<CHSSBillOther>();
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
			dto.setCreatedBy(Username);
			
			
			long count= service.OtherBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Item Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","ot");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
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
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside OtherBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssotherid = req.getParameter("chssotherid"); 
			
			String otheritemid = req.getParameter("otheritemid-"+chssotherid);
			String otheritemcost = req.getParameter("otheritemcost-"+chssotherid);
			
			CHSSBillOther meds= new CHSSBillOther();
			meds.setCHSSOtherId(Long.parseLong(chssotherid));
			meds.setOtherItemId(Integer.parseInt(otheritemid));
			meds.setOtherItemCost(Double.parseDouble(otheritemcost));
			meds.setModifiedBy(Username);
			
			long count = service.OtherBillEdit(meds,EmpId);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","ot");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
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
				redir.addAttribute("result", "Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","ot");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			return "redirect:/CHSSConsultBills.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OtherBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSForm.htm")
	public String CHSSForm(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSForm.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String ActivateDisp=req.getParameter("ActivateDisp");
			
			if(chssapplyid==null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
				ActivateDisp=(String)md.get("ActivateDisp");
			}
			
			Object[] chssapplicationdata = service.CHSSAppliedData(chssapplyid);
			Object[] employee = service.getEmployee(chssapplicationdata[1].toString());
			
			req.setAttribute("consultmainlist", service.ClaimConsultMainList(chssapplyid));
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			
			req.setAttribute("TestsDataList", service.CHSSTestsDataList(chssapplyid));
			req.setAttribute("MiscDataList", service.CHSSMiscDataList(chssapplyid));
			req.setAttribute("ConsultDataList", service.CHSSConsultDataList(chssapplyid));
			req.setAttribute("MedicineDataList", service.CHSSMedicineDataList(chssapplyid));
			req.setAttribute("OtherDataList", service.CHSSOtherDataList(chssapplyid));
		
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("chssapplydata", chssapplicationdata);
			req.setAttribute("employee", employee);
			req.setAttribute("ClaimapprovedPOVO", service.ClaimApprovedPOVOData(chssapplyid));
			req.setAttribute("ClaimRemarksHistory", service.ClaimRemarksHistory(chssapplyid));
			req.setAttribute("ClaimDisputeData", service.getClaimDisputeData(chssapplyid));
			
			
			req.setAttribute("logintype", LoginType);			
			
			req.setAttribute("ActivateDisp", ActivateDisp);
			
			req.setAttribute("dispReplyEnable", req.getParameter("dispReplyEnable"));
			
			return "chss/CHSSFormEdit";
//			return "chss/CHSSForm";
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
			String pagePart =  req.getParameter("pagePart");
			
			Object[] chssapplicationdata = service.CHSSAppliedData(chssapplyid);
			Object[] employee = service.getEmployee(chssapplicationdata[1].toString());
			
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
//			req.setAttribute("consultmainlist", service.ClaimConsultMainList(chssapplyid));
			req.setAttribute("ConsultDataList", service.CHSSConsultDataList(chssapplyid));
			req.setAttribute("TestsDataList", service.CHSSTestsDataList(chssapplyid));
			req.setAttribute("MiscDataList", service.CHSSMiscDataList(chssapplyid));
			req.setAttribute("MedicineDataList", service.CHSSMedicineDataList(chssapplyid));
			req.setAttribute("OtherDataList", service.CHSSOtherDataList(chssapplyid));
			req.setAttribute("chssapplydata", chssapplicationdata);
			req.setAttribute("employee", employee);
			req.setAttribute("ClaimapprovedPOVO", service.ClaimApprovedPOVOData(chssapplyid));
//			req.setAttribute("ClaimRemarksHistory", service.ClaimRemarksHistory(chssapplyid));
			
			req.setAttribute("pagePart","3" );
			
			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			String filename="Claim-"+chssapplicationdata[16].toString().trim().replace("/", "-");
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/chss/CHSSForm.jsp").forward(req, customResponse);
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
			logger.error(new Date() +" Inside CHSSFormEmpDownload.htm "+UserId, e); 
		}

	}
	
	
	
	
	@RequestMapping(value = "CHSSClaimFwdApproveAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String CHSSClaimFwdApproveAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSClaimFwdApproveAjax.htm "+Username);
		String allow[]= {"0","0","0","0","0","0"};
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			List<Object[]> claimdata = service.CHSSBillsList(chssapplyid);
			Object[] chssapplydata = service.CHSSAppliedData(chssapplyid);
			double claimamount=0;
			
			LocalDate applydate = LocalDate.now();
			
			if(Integer.parseInt(chssapplydata[9].toString())>1) 
			{
				applydate =LocalDate.parse(chssapplydata[15].toString());
			}
			
			for(Object[] bill : claimdata) 
			{
				LocalDate billdate = LocalDate.parse(bill[4].toString());
				
				if(!billdate.isAfter(applydate.minusMonths(3)) && !applydate.minusMonths(3).isEqual(billdate) ) 
				{
					allow[4]="1";
					allow[5]=bill[2].toString();
					break;
				}
				
				if(Math.round(Double.parseDouble(bill[6].toString())+Double.parseDouble(bill[7].toString())) != Math.round(Double.parseDouble(bill[9].toString())))
				{
					allow[2]="1";
					allow[3]=bill[2].toString();
					break;
				}
				
				if(Double.parseDouble(bill[9].toString())==0) 
				{
					allow[1]="1";
					break;
				}
				claimamount += Double.parseDouble(bill[9].toString());
			}
			
			if(claimamount==0) 
			{
				allow[0]="1";
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
	
	@RequestMapping(value = "CHSSUserForward.htm", method = RequestMethod.POST)
	public String CHSSUserForward(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String LoginType = (String) ses.getAttribute("LoginType");
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date() +"Inside CHSSUserForward.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String action = req.getParameter("claimaction");
			String remarks = req.getParameter("remarks");
			
			CHSSApply claim1 = service.CHSSApplied(chssapplyid);
			int chssstatusid= claim1.getCHSSStatusId();
			long contingentid=claim1.getContingentId();
			long count = service.CHSSUserForward(chssapplyid, Username, action,remarks,EmpId,EmpNo,LoginType);
			
			if (chssstatusid == 1 || chssstatusid ==3 ) 
			{
				String enclosurecount = req.getParameter("enclosurecount");
				if(enclosurecount!= null && Integer.parseInt(enclosurecount)>0) 
				{
					CHSSApplyDto dto =new CHSSApplyDto();
					dto.setCHSSApplyId(chssapplyid);
					dto.setNoEnclosures(enclosurecount);
					
					service.CHSSApplyEncCountEdit(dto);
				}
				
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
				
				
				if((chssstatusid==6 || chssstatusid == 9 || chssstatusid == 11 || chssstatusid == 13) && contingentid>0 ) {
					redir.addFlashAttribute("contingentid", String.valueOf(contingentid));
					return "redirect:/ContingetBill.htm";
				}
				
				if(chssstatusid>=6 ) 
				{
					return "redirect:/CHSSBatchList.htm";
				}else
				{
					return "redirect:/CHSSApprovalsList.htm";
				}
			}
			return "redirect:/CHSSApplyDashboard.htm";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSUserForward.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "CHSSIPDApprovalsList.htm" )
	public String CHSSIPDApprovalsList(Model model, HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDApprovalsList.htm "+Username);
		redir.addAttribute("tab","IPD");
		redir.addAttribute("resultfail",req.getParameter("resultfail"));
		redir.addAttribute("result",req.getParameter("result"));
		
		return "redirect:/CHSSApprovalsList.htm";
		
		
	}
	
	@RequestMapping(value = "CHSSApprovalsList.htm" )
	public String CHSSApprovalsList(Model model, HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		
		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive","CHSSApprovalsList_htm");
		
		logger.info(new Date() +"Inside CHSSApprovalsList.htm "+Username);
		try {
			
			String tab = req.getParameter("tab");
			if (tab == null) 
			{
				Map md=model.asMap();
				tab=(String)md.get("tab");
			}	
			
			req.setAttribute("OPDclaimlist", service.CHSSApproveClaimList(LoginType,EmpId,"OPD"));
			req.setAttribute("IPDclaimlist", service.CHSSApproveClaimList(LoginType,EmpId,"IPD"));
			
			req.setAttribute("tab",tab);
			
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
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSFormEdit.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			if (chssapplyid == null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
			}
			
			String view_mode=req.getParameter("view_mode");
			if (view_mode == null) 
			{
				Map md=model.asMap();
				view_mode=(String)md.get("view_mode");
			}
			
			
			Object[] chssapplydata = service.CHSSAppliedData(chssapplyid);
			Object[] employee = service.getEmployee(chssapplydata[1].toString());
			
			req.setAttribute("chssbillslist", service.CHSSBillsList(chssapplyid));
			req.setAttribute("consultmainlist", service.ClaimConsultMainList(chssapplyid));
						
			req.setAttribute("ConsultDataList", service.CHSSConsultDataList(chssapplyid));
			req.setAttribute("TestsDataList", service.CHSSTestsDataList(chssapplyid));
			req.setAttribute("MedicineDataList", service.CHSSMedicineDataList(chssapplyid));
			req.setAttribute("OtherDataList", service.CHSSOtherDataList(chssapplyid));
			req.setAttribute("MiscDataList", service.CHSSMiscDataList(chssapplyid));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("chssapplydata", chssapplydata);
			req.setAttribute("employee", employee);
			req.setAttribute("ClaimapprovedPOVO", service.ClaimApprovedPOVOData(chssapplyid));
			req.setAttribute("ClaimRemarksHistory", service.ClaimRemarksHistory(chssapplyid));

			req.setAttribute("logintype", LoginType);
			req.setAttribute("view_mode", view_mode);
			
			req.setAttribute("ClaimDisputeData", service.getClaimDisputeData(chssapplyid));
			req.setAttribute("ActivateDisp", req.getParameter("ActivateDisp"));
			req.setAttribute("dispReplyEnable", req.getParameter("dispReplyEnable"));
			
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
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside ConsultRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String consultationid = req.getParameter("consultationid"); 
			
			String consultremamount = req.getParameter("consultremamount-"+consultationid);
			String consulttype = req.getParameter("consulttype-"+consultationid);
			String consultcomment = req.getParameter("consultcomment-"+consultationid);
			
			CHSSBillConsultation consult= new CHSSBillConsultation();
			consult.setConsultationId(Long.parseLong(consultationid));
			consult.setConsultType(consulttype);
			consult.setConsultRemAmount(Double.parseDouble(consultremamount));
			consult.setComments(consultcomment);
			consult.setModifiedBy(Username);
			consult.setUpdateByEmpId(EmpId);
			consult.setUpdateByRole(LoginType);
			
			long count = service.ConsultRemAmountEdit(consult);
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
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
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside TestRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
			
			String testremamount = req.getParameter("testremamount-"+testid);
			String testcomment = req.getParameter("testcomment-"+testid);
			
			
			CHSSBillTests test= new CHSSBillTests();
			test.setCHSSTestId(Long.parseLong(testid));
			test.setTestRemAmount(Double.parseDouble(testremamount));
			test.setComments(testcomment);
			test.setModifiedBy(Username);
			test.setUpdateByEmpId(EmpId);
			test.setUpdateByRole(LoginType);


			
			long count = service.TestRemAmountEdit(test);
						
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TestRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "MedRemAmountEdit.htm", method = RequestMethod.POST )
	public String MedRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside MedRemAmountEdit.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String medicineid = req.getParameter("medicineid"); 
			
			String medicineremamount = req.getParameter("medicineremamount-"+medicineid);
			String medscomment = req.getParameter("medscomment-"+medicineid);
			
			CHSSBillMedicine medicine= new CHSSBillMedicine();
			medicine.setCHSSMedicineId(Long.parseLong(medicineid));
			medicine.setMedsRemAmount(Double.parseDouble(medicineremamount));
			medicine.setComments(medscomment);
			medicine.setModifiedBy(Username);
			
			medicine.setUpdateByEmpId(EmpId);
			medicine.setUpdateByRole(LoginType);
			
			long count = service.MedRemAmountEdit(medicine);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	@RequestMapping(value = "OtherRemAmountEdit.htm", method = RequestMethod.POST )
	public String OtherRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside OtherRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String otherid = req.getParameter("otherid"); 
			
			String otheridremamount = req.getParameter("otherremamount-"+otherid);
			String otherscomment = req.getParameter("otherscomment-"+otherid);
			
			CHSSBillOther other= new CHSSBillOther();
			other.setCHSSOtherId(Long.parseLong(otherid));
			other.setOtherRemAmount(Double.parseDouble(otheridremamount));
			other.setComments(otherscomment);
			other.setModifiedBy(Username);
			
			other.setUpdateByEmpId(EmpId);
			other.setUpdateByRole(LoginType);
			
			long count = service.OtherRemAmountEdit(other);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OtherRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	

	
	@RequestMapping(value = "MiscRemAmountEdit.htm", method = RequestMethod.POST )
	public String MiscRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside MiscRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String miscid = req.getParameter("miscid"); 
			
			String miscremamount = req.getParameter("miscremamount-"+miscid);
			String miscomment = req.getParameter("miscomment-"+miscid);
			
			CHSSBillMisc misc= new CHSSBillMisc();
			misc.setChssMiscId(Long.parseLong(miscid));
			misc.setMiscRemAmount(Double.parseDouble(miscremamount));
			misc.setComments(miscomment);
			misc.setModifiedBy(Username);
			misc.setUpdateByEmpId(EmpId);
			misc.setUpdateByRole(LoginType);
			
			long count = service.MiscRemAmountEdit(misc);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
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

			String todate = req.getParameter("todate");
			String claims_type = req.getParameter("claims_type");
			
			if(todate == null) 
			{
				if(LocalDate.now().getDayOfMonth()<20) {
					todate = LocalDate.now().minusMonths(1).withDayOfMonth(20).toString();
				}else
				{
					todate = LocalDate.now().withDayOfMonth(20).toString();
				}
				
			}else {
				todate = sdf.format(rdf.parse(todate));				
			}
			
			if(claims_type==null) 
			{
				claims_type = "OPD";
			}
			
			List<Object[]> claimslist = service.CHSSBatchApproval(LoginType, todate,"0",claims_type);
			req.setAttribute("chssclaimlist", claimslist);
			req.setAttribute("todate", todate);
			req.setAttribute("claims_type", claims_type);
			
			return "chss/ContingentGenerate";
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
			String genTilldate = req.getParameter("genTilldate");
			
			String claims_type = req.getParameter("claims_type");
			
			long count= service.ContingentGenerate(chssapplyid, Username, action, billcontent, LoginType,EmpId,genTilldate,claims_type);
//			CHSSContingent contingent = service.getCHSSContingent(String.valueOf(count));
			
				if (count > 0) {
					redir.addAttribute("result", "Contingent Bill Generated Successfully");
				} else {
					redir.addAttribute("resultfail", "Contingent Bill Generation Unsuccessful");	
				}	
			redir.addFlashAttribute("contingentid",String.valueOf(count));
			return "redirect:/ContingetBill.htm";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSContingentGenerate.htm "+Username, e);
			return "static/Error";
		}
		
	}
		
	@RequestMapping(value = "CHSSContingentApprove.htm", method = RequestMethod.POST)
	public String CHSSClaimsApprove(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date() +"Inside CHSSContingentApprove.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			String action = req.getParameter("action");
			String remarks = req.getParameter("remarks");
			
			String billcontent = req.getParameter("billcontent");
			
			CHSSContingentDto dto = new CHSSContingentDto();
			dto.setContingentid(contingentid);
			dto.setUsername(Username);
			dto.setAction(action);
			dto.setRemarks(remarks);
			dto.setLogintype(LoginType);
			dto.setEmpId(EmpId);
			dto.setBillcontent(billcontent);
			dto.setPO(billcontent);
			
			long count= service.CHSSClaimsApprove( dto,EmpNo);
			
			if(action.equalsIgnoreCase("F")) 
			{
				CHSSContingent contingent = service.getCHSSContingent(dto.getContingentid());
				if(contingent.getContingentStatusId()==15)
				{
					if (count > 0) {
						redir.addAttribute("result", "Contingent Bill Status Updated Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Status Update Unsuccessful");	
					}
					
					return "redirect:/ApprovedBills.htm";
					
				}
				else if(LoginType.equalsIgnoreCase("Z")) 
				{
					if (count > 0) {
						redir.addAttribute("result", "Contingent Bill Approved Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Approved Unsuccessful");	
					}
					
				}else if(LoginType.equalsIgnoreCase("W"))
				{
					if (count > 0) {
						redir.addAttribute("result", "Contingent Bill Recommended Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Recommend Unsuccessful");	
					}
				}
				else
				{
					if (count > 0)
					{
						redir.addAttribute("result", "Contingent Bill Forwarded Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Forward Unsuccessful");	
					}
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
			logger.error(new Date() +" Inside CHSSContingentApprove.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	@RequestMapping(value = "ContingentApprovals.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String ContingentApprovals(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		
		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive","ContingentApprovals_htm");
		
		logger.info(new Date() +"Inside ContingentApprovals.htm "+Username);
		try {
			String fromdate = req.getParameter("fromdate");
			String todate = req.getParameter("todate");
			
			LocalDate today=LocalDate.now();
			
			if(fromdate==null) 
			{
				if(today.getMonthValue()<4) 
				{
					fromdate = String.valueOf(today.getYear()-1);
					todate=String.valueOf(today.getYear());
					
				}else{
					fromdate = String.valueOf(today.getYear());
					todate=String.valueOf(today.getYear()+1);
				}
				fromdate +="-04-01"; 
				todate +="-03-31";
			}else
			{
				fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
				todate=DateTimeFormatUtil.RegularToSqlDate(todate);
			}
		
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("ContingentList", service.getCHSSContingentList(LoginType,fromdate,todate));
			req.setAttribute("logintype", LoginType);
			req.setAttribute("isapproval", "Y");
			
			return "chss/ContingentBillsList";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +"Inside ContingentApprovals.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "ContingetBill.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String ContingetBill(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String LoginType = (String) ses.getAttribute("LoginType");
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ContingetBill.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			String claim_view_mode=req.getParameter("claim_view_mode");
			
			if(contingentid==null) {
				Map md=model.asMap();
				contingentid=(String)md.get("contingentid");
				claim_view_mode=(String)md.get("claim_view_mode");
			}
			
			if(claim_view_mode==null) {
				claim_view_mode="A";
			}
			
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.CHSSContingentData(contingentid));
			req.setAttribute("ApprovalAuth", service.CHSSApprovalAuthList(contingentid));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("labdata", service.getLabCode());
			req.setAttribute("contingentremarks", service.ContingentBillRemarkHistory(contingentid));
			req.setAttribute("onlyview","Y");
			req.setAttribute("logintype",LoginType);
			req.setAttribute("view_mode",claim_view_mode);

			return "chss/ContingentBillView";
			
//			return "chss/ContingentBill";
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
			req.setAttribute("ChssStatisDetails", service.CHSSStatusDetails(chssapplyid));
			
			return "chss/CHSSStatus";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside Chss-Status-details.htm "+Username, e);
			return "static/Error";
		}
	}

	@RequestMapping(value = "ContingentTransaction.htm" , method={RequestMethod.POST,RequestMethod.GET})
	public String ContingentTransactions(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ContingentTransaction.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			req.setAttribute("Transactions", service.ContingentTransactions(contingentid));
			
			return "chss/ContingentTransaction";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingentTransaction.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ContingentBillDownload.htm")
	public void ContingentBillDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside ContingentBillDownload.htm "+UserId);
		
		try {	
			String contingentid = req.getParameter("contingentid");
			Object[] contingentdata = service.CHSSContingentData(contingentid);
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", contingentdata);
			req.setAttribute("ApprovalAuth", service.CHSSApprovalAuthList(contingentid));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("labdata", service.getLabCode());
			String filename="Contingent - "+contingentdata[1].toString().trim().replace("/", "-");
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
			
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/chss/ContingentBill.jsp").forward(req, customResponse);
			String html1 = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html1,new FileOutputStream(path+File.separator+filename+".pdf")); 
	        
	        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
	        
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
			logger.error(new Date() +" Inside contingentbilldownload.htm "+UserId, e); 
		}
	}
	
	@RequestMapping(value="ApprovedBills.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	public String ApprovedBiils(HttpSession ses,HttpServletRequest req , RedirectAttributes redir)throws Exception
	{
		String UserId = (String) ses.getAttribute("Username");
		
		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive","ApprovedBills_htm");
		
		logger.info(new Date() +"Inside ApprovedBills.htm "+UserId);
		try {
			
			redir.addFlashAttribute("tab","approved");
			
			return "redirect:/ContingentBillsList.htm";
			
//			List<Object[]> approvedlist = service.getCHSSContingentList("0",fromdate,todate);
//			req.setAttribute("ApprovedBills", approvedlist);
			
//			return "chss/ContingentApprovedBills";
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ApprovedBills.htm "+UserId, e); 
			return "static/Error";
		}
		
	}
	
	
	
	@RequestMapping(value = "ConsultationHistoryAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ConsultationHistoryAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultationHistoryAjax.htm "+Username);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			list = service.ConsultationHistory(chssapplyid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultationHistoryAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "TestsHistoryAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String TestsHistoryAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TestsHistoryAjax.htm "+Username);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			list = service.TestsHistory(chssapplyid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TestsHistoryAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "MedicinesHistoryAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String MedicinesHistoryAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MedicinesHistoryAjax.htm "+Username);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			list = service.MedicinesHistory(chssapplyid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedicinesHistoryAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "OthersHistoryAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String OthersHistoryAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside OthersHistoryAjax.htm "+Username);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			list = service.OthersHistory(chssapplyid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OthersHistoryAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	
	@RequestMapping(value = "MiscItemsHistoryAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String MiscItemsHistoryAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MiscItemsHistoryAjax.htm "+Username);
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			list = service.MiscItemsHistory(chssapplyid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MiscItemsHistoryAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	

	
	@RequestMapping(value = "ConsultBillsConsultCountAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ConsultBillsConsultCountAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultBillsConsultCountAjax.htm "+Username);
		int count=0;
		try {
			String consultmainid = req.getParameter("consultmainid");
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			count = Integer.parseInt(service.ConsultBillsConsultCount(consultmainid,chssapplyid,billid)[0].toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultBillsConsultCountAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(count);
	}
	
	
	@RequestMapping(value = "ConsultMainDataAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ConsultMainDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsultMainDataAjax.htm "+Username);
		CHSSConsultMain consultmain=new CHSSConsultMain();;
		try {
			String consultmainid = req.getParameter("consultmainid");
			consultmain = service.getCHSSConsultMain(consultmainid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ConsultMainDataAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(consultmain);
	}

	@RequestMapping(value = "OldConsultMedsListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String OldConsultMedsListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside OldConsultMedsListAjax.htm "+Username);
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			String consultmainid = req.getParameter("consultmainid");
			String chssapplyid = req.getParameter("chssapplyid");
			list = service.OldConsultMedsList(consultmainid,chssapplyid);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside OldConsultMedsListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	
	@RequestMapping(value = "MedsNameListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String MedsNameListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MedsNameListAjax.htm "+Username);
		List<Object[]> list=new ArrayList<Object[]>();
		try {
			String medname = req.getParameter("medname");
			
			list = service.MedAdmissibleList(medname.trim(),"1");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside MedsNameListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(list);
	}
	
	@RequestMapping(value = "CHSSEmpClaimRevoke.htm", method = RequestMethod.POST)
	public String CHSSEmpClaimRevoke(HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSEmpClaimRevoke.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			long count =service.CHSSUserRevoke(chssapplyid, Username, EmpId);
			
			if (count > 0) {
				redir.addAttribute("result", "Claim Revoked Successfully");
			}else if(count==-1) 
			{
				redir.addAttribute("resultfail", "Claim Already Acknowledged by Processing Officer");	
			}
			else {
				redir.addAttribute("resultfail", "Claim Revoke Unsuccessful");	
			}	
			
			return "redirect:/CHSSApplyDashboard.htm";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSEmpClaimRevoke.htm "+Username, e);
			return "static/Error";			
		}
		
	}
	
	
	
	
	@RequestMapping(value = "ContingentBillPayReport.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String ContingentBillPayReport(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ContingentBillPayReport.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.CHSSContingentData(contingentid));
			req.setAttribute("ApprovalAuth", service.CHSSApprovalAuthList(contingentid));
			req.setAttribute("labdata", service.getLabCode());
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			return "chss/ContingentBillPayReport";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingentBillPayReport.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ContingentBillPayReportDownload.htm")
	public void ContingentBillPayReportDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside ContingentBillPayReportDownload.htm "+UserId);
		
		try {	
			String contingentid = req.getParameter("contingentid");
			
			req.setAttribute("ContingentList", service.CHSSContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.CHSSContingentData(contingentid));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("labdata", service.getLabCode());
			String filename="CHSSConsolidatedBill";
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
			
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/chss/ContingentBillPayReport.jsp").forward(req, customResponse);
			String html1 = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html1,new FileOutputStream(path+File.separator+filename+".pdf")); 
	        
	        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
	        
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
			logger.error(new Date() +" Inside ContingentBillPayReportDownload.htm "+UserId, e); 
		}

	}
	
	@RequestMapping(value = "POAcknowledgeUpdateAjax.htm", method = RequestMethod.GET)
	public void POAcknowledgeUpdateAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside POAcknowledgeUpdateAjax.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");			
			service.POAcknowldgedUpdate(chssapplyid,"1");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside POAcknowledgeUpdateAjax.htm "+Username, e);
		}
//		Gson json = new Gson();
//		return json.toJson(list);
	}
	
	
	@RequestMapping(value = "CHSSContingentDelete.htm", method = RequestMethod.POST)
	public String CHSSContingentDelete(HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSContingentDelete.htm "+Username);
		long count=0;
		try {
			String contingentid = req.getParameter("contingentid");
			count = service.CHSSContingentDelete(contingentid,Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Delete Unsuccessful");	
			}	
			
			
			return "redirect:/ContingentApprovals.htm";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSContingentDelete.htm "+Username, e);
			return "static/Error";
		}
		
	}
	@RequestMapping(value ="ClaimsList.htm" , method = {RequestMethod.POST , RequestMethod.GET } )
	public String CHSSClaimsList(HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ClaimsList.htm "+Username);
		try {
			ses.setAttribute("SidebarActive", "ClaimsList_htm");
			
			String empid = req.getParameter("empid");
			String status =  req.getParameter("status");
			String fromdate = req.getParameter("fromdate");
			String todate = req.getParameter("todate");
			
//			if(fromdate == null || todate == null) 
//			{
//				fromdate = LocalDate.now().minusMonths(1).withDayOfMonth(21).toString();
//				todate = LocalDate.now().toString();
//				status = "I";
//				empid = "0";
//			}else {
//				fromdate = sdf.format(rdf.parse(fromdate));
//				todate = sdf.format(rdf.parse(todate));				
//			}
			LocalDate today= LocalDate.now();
			if(fromdate==null) 
			{
				if(today.getMonthValue()<4) 
				{
					fromdate = String.valueOf(today.getYear()-1);
					
				}else{
					fromdate = String.valueOf(today.getYear());
				}
				status = "I";
				empid = "0";
				
				fromdate +="-04-01"; 
				todate=today.toString();
			}else
			{
				fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
				todate=DateTimeFormatUtil.RegularToSqlDate(todate);
			}
		
			
			req.setAttribute("empid", empid);
			req.setAttribute("status", status);
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("emplist", service.EmployeesList());
			req.setAttribute("claimslist", service.GetClaimsList(fromdate , todate , empid,status));
			return "chss/CHSSClaimsList";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimsList.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
//	@RequestMapping(value ="ClaimsReport.htm" , method = {RequestMethod.GET, RequestMethod.POST} )
//	public String ClaimsReport(HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir)throws Exception
//	{
//		String Username = (String) ses.getAttribute("Username");
//		logger.info(new Date() +"Inside ClaimsReport.htm "+Username);
//		try {
//			String fromdate = (String)req.getParameter("fromdate");
//			String todate = (String) req.getParameter("todate");
//			String empid = (String)req.getParameter("empid");
//			LocalDate today = LocalDate.now();
//			if(fromdate==null) 
//			{
//				if(today.getMonthValue()<4) 
//				{
//					fromdate = String.valueOf(today.getYear()-1);
//					todate=String.valueOf(today.getYear());
//				}
//				else
//				{
//					fromdate = String.valueOf(today.getYear());
//					todate=String.valueOf(today.getYear()+1);
//				}
//				fromdate +="-04-01"; 
//				todate +="-03-31";
//				empid="0";
//			}else
//			{
//				fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
//				todate=DateTimeFormatUtil.RegularToSqlDate(todate);
//			}
//			
//			
//			req.setAttribute("empid", empid);
//			req.setAttribute("fromdate", fromdate);
//			req.setAttribute("todate", todate);
//			req.setAttribute("emplist", service.EmployeesList());
//			req.setAttribute("claimslist", service.GetClaimsReport(fromdate , todate , empid));
//			return "chss/CHSSClaimsReport";
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(new Date() +" Inside ClaimsReport.htm "+Username, e);
//			return "static/Error";
//		}
//		
//	}
	
	@RequestMapping(value = "ClaimsReport.htm", method = {RequestMethod.POST , RequestMethod.GET } )
	public String ClaimsReport(Model model, HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ses.setAttribute("SidebarActive", "ClaimsReportList_htm");
		logger.info(new Date() +"Inside ClaimsReport.htm "+Username);
		try {
			ses.setAttribute("SidebarActive","ClaimsReport_htm");		
			String empid = (String)req.getParameter("EmpId");			
            String fromdate = (String)req.getParameter("FromDate");
			String todate =  (String)req.getParameter("ToDate");
			String claimtype = (String)req.getParameter("ClaimType");
			String status = "A";
			LocalDate today= LocalDate.now();
			if(fromdate==null) 
			{
				if(today.getMonthValue()<4) 
				{
					fromdate = String.valueOf(today.getYear()-1);
					
				}else{
					fromdate = String.valueOf(today.getYear());
				}
				
				empid = "0";
				claimtype="All";
				
				fromdate +="-04-01"; 
				todate=today.toString();
			}else
			{
				fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
				todate=DateTimeFormatUtil.RegularToSqlDate(todate);
			}
            req.setAttribute("empid", empid);
			req.setAttribute("frmDt", fromdate);
			req.setAttribute("toDt",   todate);
			req.setAttribute("claimtype",claimtype);
			req.setAttribute("status",status);
			req.setAttribute("emplist", service.EmployeesList());
			req.setAttribute("claimsReportList", service.GetClaimsReportList(empid,fromdate,todate,claimtype,status));
			
			return "chss/ClaimsReport";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimsReport.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "ClaimsReportDownload.htm")
	public void ClaimsReportDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside ClaimsReportDownload.htm "+UserId);
		
		try {

			String[] passedValues = (String[])req.getParameterValues("selectedParameters");
			String empid = null;
			String fromdate = null;
			String todate = null;
			String claimtype = null;
			String status = null;
			for(int i=0;i<passedValues.length;i++) {
				empid=passedValues[i].split("#")[0];
				fromdate=passedValues[i].split("#")[1];
				todate=passedValues[i].split("#")[2];
				claimtype=passedValues[i].split("#")[3];
				status=passedValues[i].split("#")[4];
			}
			LocalDate today= LocalDate.now();
			String currentDate = rdf.format(sdf.parse(today+""));
			
			List <Object[]> claimsReportData = service.GetClaimsReportList(empid,fromdate,todate,claimtype,status);
			req.setAttribute("claimsReportData",claimsReportData );
			req.setAttribute("labdata", service.getLabCode());
			req.setAttribute("currentDate", currentDate);
			
			req.setAttribute("pagePart","3" );
			
			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			String filename="ClaimsReport";
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/chss/ClaimsReportPrint.jsp").forward(req, customResponse);
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
			logger.error(new Date() +" Inside ClaimsReportDownload.htm "+UserId, e); 
		}

	}
	
	
	
	
	
	@RequestMapping(value = "ClaimsExcelDownload.htm")
	public void ClaimsExcelDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ClaimsExcelDownload.htm "+UserId);
		try {	
			

			String[] passedValues = (String[])req.getParameterValues("selectedParameters");
			String empid = null;
			String fromdate = null;
			String todate = null;
			String claimtype = null;
			String status = null;
			for(int i=0;i<passedValues.length;i++) {
				empid=passedValues[i].split("#")[0];
				fromdate=passedValues[i].split("#")[1];
				todate=passedValues[i].split("#")[2];
				claimtype=passedValues[i].split("#")[3];
				status=passedValues[i].split("#")[4];
			}
		
			SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
			SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
			IndianRupeeFormat nfc=new IndianRupeeFormat();

			List <Object[]> ClaimReportList= service.GetClaimsReportList(empid,fromdate,todate,claimtype,status);


			AmountWordConveration awc = new AmountWordConveration();
			int rowNo=0;
			// Creating a worksheet
			Workbook workbook = new XSSFWorkbook();

			Sheet sheet = workbook.createSheet("Contingent Report");
			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 4000);
			sheet.setColumnWidth(2, 1500);
			sheet.setColumnWidth(3, 4000);
			sheet.setColumnWidth(4, 6500);
			sheet.setColumnWidth(5, 3500);
			//sheet.setColumnWidth(6, 2500);
			sheet.setColumnWidth(6, 4500);
			sheet.setColumnWidth(7, 4500);
			
			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			// style for file header
			CellStyle file_header_Style = workbook.createCellStyle();
			file_header_Style.setLocked(true);
			file_header_Style.setFont(font);
			file_header_Style.setWrapText(true);
			file_header_Style.setAlignment(HorizontalAlignment.CENTER);
			file_header_Style.setVerticalAlignment(VerticalAlignment.CENTER);
			
			// style for table header
			CellStyle t_header_style = workbook.createCellStyle();
			t_header_style.setLocked(true);
			t_header_style.setFont(font);
			t_header_style.setWrapText(true);
			// style for table cells
			CellStyle t_body_style = workbook.createCellStyle();
			t_body_style.setWrapText(true);

			
			// File header Row
			Row file_header_row = sheet.createRow(rowNo++);
			sheet.addMergedRegion(new CellRangeAddress(0, 0,0, 8));   // Merging Header Cells 
			Cell cell= file_header_row.createCell(0);
			cell.setCellValue("CHSS Claims Report :");
			file_header_row.setHeightInPoints((3*sheet.getDefaultRowHeightInPoints()));
			cell.setCellStyle(file_header_Style);
			
			CellStyle file_header_Style2 = workbook.createCellStyle();
			file_header_Style2.setLocked(true);
			file_header_Style2.setFont(font);
			file_header_Style2.setWrapText(true);
			file_header_Style2.setAlignment(HorizontalAlignment.RIGHT);
			file_header_Style2.setVerticalAlignment(VerticalAlignment.CENTER);
			
//			Row file_header_row2 = sheet.createRow(rowNo++);
//			sheet.addMergedRegion(new CellRangeAddress(1, 1,0, 8));   // Merging Header Cells 
//			cell= file_header_row2.createCell(0);
//			cell.setCellValue("Approved On : ");
//			cell.setCellStyle(file_header_Style2);

			// Table in file header Row
			Row t_header_row = sheet.createRow(rowNo++);
			cell= t_header_row.createCell(0); 
			cell.setCellValue("SN"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(1); 
			cell.setCellValue("Claim No"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(2); 
			cell.setCellValue("Type"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(3); 
			cell.setCellValue("Applicant"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(4); 
			cell.setCellValue("Patient"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(5); 
			cell.setCellValue("Applied Date"); 
			cell.setCellStyle(t_header_style);
			
//			cell= t_header_row.createCell(6); 
//			cell.setCellValue("No of Bills"); 
//			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(6); 
			cell.setCellValue("Claimed Amt"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(7); 
			cell.setCellValue("Settled Amt"); 
			cell.setCellStyle(t_header_style);
			
			
			
			long slno =0;
		     for(Object[] obj: ClaimReportList)
		     {slno++;
		     
		     Row t_body_row = sheet.createRow(rowNo++);
				cell= t_body_row.createCell(0); 
				cell.setCellValue(slno); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(1); 
				cell.setCellValue(obj[20].toString()); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(2); 
				cell.setCellValue(obj[10].toString()); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(3); 
				cell.setCellValue(obj[23].toString()); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(4); 
				cell.setCellValue(obj[16].toString()+"("+obj[18].toString() +")"); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(5); 
				cell.setCellValue(rdf.format(sdf.parse(obj[19].toString()))); 
				cell.setCellStyle(t_body_style);
				
				
//				cell= t_body_row.createCell(6); 
//				cell.setCellValue(""); 
//				cell.setCellStyle(t_body_style);
				
				
				cell= t_body_row.createCell(6); 
				cell.setCellValue(nfc.rupeeFormat(obj[1].toString())); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(7); 
				cell.setCellValue(nfc.rupeeFormat(obj[2].toString())); 
				cell.setCellStyle(t_body_style);
				
		
		     
		     }

			    long totalClaimedAmt = 0;
			    long totalSettledAmt = 0;
		     
			     for(Object[] obj: ClaimReportList)
				{
			    	totalClaimedAmt += Math.round( Double.parseDouble(obj[1].toString()));
			    	totalSettledAmt += Math.round( Double.parseDouble(obj[2].toString()));
				}

			// table footer style
			CellStyle t_footer_style = workbook.createCellStyle();
			t_footer_style.setLocked(true);
			t_footer_style.setFont(font);
			t_footer_style.setWrapText(true);
			t_footer_style.setFont(font);
			
			// table footer total
			Row f_foot_row = sheet.createRow(rowNo++);
			cell= f_foot_row.createCell(5); 
			cell.setCellValue("Total :"); 
			cell.setCellStyle(t_footer_style);
					
		    cell= f_foot_row.createCell(6); 
			cell.setCellValue(nfc.rupeeFormat(String.valueOf( totalClaimedAmt))); 
			cell.setCellStyle(t_footer_style);  
			
			cell= f_foot_row.createCell(7); 
			cell.setCellValue(nfc.rupeeFormat(String.valueOf( totalSettledAmt))); 
			cell.setCellStyle(t_footer_style);  
			
			
			//footer style
			CellStyle file_footer_Style = workbook.createCellStyle();
			font.setBold(true);
			file_footer_Style.setLocked(true);
			file_footer_Style.setWrapText(true);
			file_footer_Style.setFont(font);
			// footer text amount
			
//			Row f_foot_row2 = sheet.createRow(rowNo);
//			sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo,0, 8));   
//			cell= f_foot_row2.createCell(0);
//			cell.setCellValue("In words Rupees"+" Only");
//			cell.setCellStyle(file_footer_Style);
//			rowNo++;

		
			String path = req.getServletContext().getRealPath("/view/temp");
			String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
			
			
			String filename="CHSSClaims-Excel";
			
     
	        res.setContentType("Application/octet-stream");
	        res.setHeader("Content-disposition","attachment;filename="+filename+".xlsx");
	        File f=new File(fileLocation);
	         
	        
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
	              	
		}
		catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside CHSSClaimsExcelDownload.htm "+UserId, e); 
		}

	}
	
	
	
	
	@RequestMapping(value ="ClaimDeleteEmp.htm" , method = { RequestMethod.POST} )
	public String ClaimDeleteEmp(HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ClaimDeleteEmp.htm "+Username);
		try {
			String chssapplyid =req.getParameter("chssapplyid");
			
			int count = service.DeleteClaimData(chssapplyid, Username);
			if (count > 0) {
				redir.addAttribute("result", "Claim Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Claim Delete Unsuccessful");	
			}	
			return "redirect:/CHSSApplyDashboard.htm";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimDeleteEmp.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	@RequestMapping(value = "CHSSConsultMainData.htm" )
	public String CHSSConsultMainData(Model model,HttpServletRequest req, HttpSession ses,  RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSConsultMainData.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			if (chssapplyid == null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
			}	
			
			if(chssapplyid==null) {
				redir.addAttribute("result", "Refresh Not Allowed");
				return "redirect:/CHSSApplyDashboard.htm";
			}
						
			Object[] apply= service.CHSSAppliedData(chssapplyid);
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("treattypelist", service.CHSSTreatTypeList());
			
			
			if(apply[3].toString().equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(apply[2].toString()));
			}
			
				req.setAttribute("doctorrates", service.getCHSSDoctorRates(apply[7].toString()));
				req.setAttribute("consultcount", service.claimConsultationsCount(chssapplyid));
				
				req.setAttribute("medicinecount", service.claimMedicinesCount(chssapplyid));
				req.setAttribute("consultmainlist", service.getCHSSConsultMainList(chssapplyid));
				req.setAttribute("consulthistory", service.PatientConsultHistory(chssapplyid));
				
				return "chss/CHSSClaimConsult";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSConsultMainData.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSIPDApply.htm" )
	public String CHSSIPDApply(Model model,HttpServletRequest req, HttpSession ses,  RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside CHSSIPDApply.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			if (chssapplyid == null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
			}	
			
			if(chssapplyid==null) {
				redir.addAttribute("result", "Refresh Not Allowed");
				return "redirect:/CHSSApplyDashboard.htm";
			}
			
			String tab = req.getParameter("tab");
			if (tab == null) 
			{
				Map md=model.asMap();
				tab=(String)md.get("tab");
			}	
			
			
			Object[] apply= service.CHSSAppliedData(chssapplyid);
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", service.getEmployee(EmpId));
			req.setAttribute("treattypelist", service.CHSSTreatTypeList());
			
			
			if(apply[3].toString().equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(apply[2].toString()));
			}
			
			
				List<Object[]> chssbill = service.CHSSConsultMainBillsList("0",chssapplyid);
				String billid ="0";
				if(chssbill.size()>0){
					billid = chssbill.get(0)[0].toString();
				}
				
				req.setAttribute("ipdbasicinfo", service.IpdClaimInfo(chssapplyid));
				req.setAttribute("chssbill", chssbill );
				req.setAttribute("pkgSubItems", service.getCHSSIPDPkgItemsList());
				req.setAttribute("NonPackageItems", service.IPDBillOtherItems(billid));
				
				req.setAttribute("testmainlist", service.CHSSTestSubList(apply[7].toString()));
				req.setAttribute("doctorrates", service.getCHSSDoctorRates(apply[7].toString()));
				
				req.setAttribute("ClaimPackages", service.ClaimPackagesList(billid));
				req.setAttribute("miscitems", service.CHSSMiscList(billid));
				req.setAttribute("consultations", service.CHSSConsultationList(billid));
				req.setAttribute("billtests", service.CHSSTestsList(billid));
				req.setAttribute("equipments", service.CHSSEquipmentList(billid));
				req.setAttribute("implants", service.CHSSImplantList(billid));
				
				req.setAttribute("ClaimAttachDeclare", service.IPDClaimAttachments(chssapplyid));
				
				req.setAttribute("tab", tab);
				return "chss/CHSSClaimDetailsIPD";
					
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDApply.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSIPDBasicInfoAdd.htm" )
	public String CHSSIPDBasicInfoAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDBasicInfoAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			String hospitalname = req.getParameter("hospitalname");
			String roomtype = req.getParameter("room-type");
			String admitteddate = req.getParameter("admitted-date");
			String admittedtime = req.getParameter("admitted-time");
			String dischargeddate = req.getParameter("discharged-date");
			String dischargedtime = req.getParameter("discharged-time");
			String DomicHospi = req.getParameter("DomicHospi");
			String DayCare = req.getParameter("DayCare");
			String ExtCareRehab = req.getParameter("ExtCareRehab");
			
			
			CHSSIPDClaimsInfo claiminfo= new CHSSIPDClaimsInfo();
			claiminfo.setCHSSApplyId(Long.parseLong(chssapplyid));
			claiminfo.setHospitalName(WordUtils.capitalize(hospitalname).trim());
			claiminfo.setRoomType(roomtype);
			claiminfo.setAdmissionDate(DateTimeFormatUtil.RegularToSqlDate(admitteddate) );
			claiminfo.setAdmissionTime(admittedtime);
			claiminfo.setDischargeDate(DateTimeFormatUtil.RegularToSqlDate(dischargeddate));
			claiminfo.setDischargeTime(dischargedtime);
			claiminfo.setDomiciliaryHosp(Integer.parseInt(DomicHospi));
			claiminfo.setDayCare(Integer.parseInt(DayCare));
			claiminfo.setExtCareRehab(Integer.parseInt(ExtCareRehab));
			claiminfo.setIsActive(1);
			claiminfo.setCreatedBy(Username);
			claiminfo.setCreatedDate(sdtf.format(new Date()));
			
			
			long count=service.CHSSIPDBasicInfoAdd(claiminfo);
			if (count > 0) {
				redir.addAttribute("result", "Claim Info Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Claim Info Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("consultmainid",String.valueOf(count));
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDBasicInfoAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSIPDBasicInfoEdit.htm" )
	public String CHSSIPDBasicInfoEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDBasicInfoEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String ipdclaiminfoid = req.getParameter("ipdclaiminfoid");
			
			String hospitalname = req.getParameter("hospitalname");
			String roomtype = req.getParameter("room-type");
			String admitteddate = req.getParameter("admitted-date");
			String admittedtime = req.getParameter("admitted-time");
			String dischargeddate = req.getParameter("discharged-date");
			String dischargedtime = req.getParameter("discharged-time");
			String DomicHospi = req.getParameter("DomicHospi");
			String DayCare = req.getParameter("DayCare");
			String ExtCareRehab = req.getParameter("ExtCareRehab");
			
			
			CHSSIPDClaimsInfo claiminfo= new CHSSIPDClaimsInfo();
			claiminfo.setIPDClaimInfoId(Long.parseLong(ipdclaiminfoid));
			claiminfo.setHospitalName(hospitalname);
			claiminfo.setRoomType(roomtype);
			claiminfo.setAdmissionDate(admitteddate);
			claiminfo.setAdmissionTime(admittedtime);
			claiminfo.setDischargeDate(dischargeddate);
			claiminfo.setDischargeTime(dischargedtime);
			claiminfo.setDomiciliaryHosp(Integer.parseInt(DomicHospi));
			claiminfo.setDayCare(Integer.parseInt(DayCare));
			claiminfo.setExtCareRehab(Integer.parseInt(ExtCareRehab));
			claiminfo.setModifiedBy(Username);
			
			
			
			long count=service.CHSSIPDBasicInfoEdit(claiminfo);
			if (count > 0) {
				redir.addAttribute("result", "Claim Info Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Claim Updated Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("consultmainid",String.valueOf(count));
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDBasicInfoEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "AddMedicineToInadmissible.htm" )
	public String AddMedicineToInadmissible(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside AddMedicineToInadmissible.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			String medicinename = req.getParameter("medicinename");
			String trattypeid = req.getParameter("trattypeid");
			
			String comment = req.getParameter("comment");
			
			int  MedNo = service.GetMaxMedNo(trattypeid);
			CHSSMedicinesList  medicinelist = new CHSSMedicinesList();
			
			medicinelist.setMedNo(String.valueOf(++MedNo));
			medicinelist.setMedicineName(WordUtils.capitalizeFully(medicinename.trim()));
			medicinelist.setTreatTypeId(Long.parseLong("1"));
			medicinelist.setIsAdmissible("N");
			medicinelist.setCategoryId(0l);
			medicinelist.setIsActive(1);
			long count = service.AddMedicine(medicinelist);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Medicine Info Added to List Successfully");
				
				MasterEdit masteredit = new MasterEdit();
				masteredit.setTableRowId(count);
				masteredit.setTableName("chss_medicines_list");
				masteredit.setCreatedBy(Username);
				masteredit.setCreatedDate(sdtf.format(new Date()));
				masteredit.setComments(comment);
				
				MasterEditDto masterdto = new MasterEditDto();
				masterdto.setFilePath(null);
				
				service.AddMasterEditComments(masteredit , masterdto);
				
				
			} else {
				redir.addAttribute("resultfail", "Medicine Info Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside AddMedicineToInadmissible.htm "+Username, e);
			return "static/Error";
		}
	}

	@RequestMapping(value = "IPDConsultAdd.htm", method = RequestMethod.POST )
	public String IPDConsultAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDConsultAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			
			String[] consulttype=req.getParameterValues("consult-type");
			String[] docname=req.getParameterValues("doc-name");
			String[] docqualification=req.getParameterValues("doc-qualification");
			String[] consdate=req.getParameterValues("cons-date");
			String[] conscharge=req.getParameterValues("cons-charge");
			String consultmainidold=req.getParameter("consultmainid-old");
			CHSSConsultationDto dto=new CHSSConsultationDto();
			
			dto.setBillId(billid);
			dto.setConsultType(consulttype);
			dto.setDocName(docname);
			dto.setDocQualification(docqualification);
			dto.setConsultDate(consdate);
			dto.setConsultCharge(conscharge);
			dto.setCreatedBy(Username);
			
			long count= service.IPDConsultAdd(dto,chssapplyid,consultmainidold);
			if (count > 0) {
				redir.addAttribute("result", "Consultation Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("tab","co");
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDConsultAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "IPDConsultEdit.htm", method = RequestMethod.POST )
	public String IPDConsultEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDConsultEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String consultationid = req.getParameter("consultationid"); 
			
			String docname = req.getParameter("doc-name-"+consultationid);
			String docqualification = req.getParameter("doc-qualification-"+consultationid);
			String consdate = req.getParameter("cons-date-"+consultationid);
			String conscharge = req.getParameter("cons-charge-"+consultationid);
			String consultmainidold=req.getParameter("consultmainid-old");
			
			CHSSBillConsultation consult= new CHSSBillConsultation();
			consult.setConsultationId(Long.parseLong(consultationid));
			consult.setDocName(docname);
			consult.setDocQualification(Integer.parseInt(docqualification));
			consult.setConsultDate(sdf.format(rdf.parse(consdate)));
			consult.setConsultCharge(Double.parseDouble(conscharge));
			consult.setModifiedBy(Username);
			consult.setBillId(Long.parseLong(req.getParameter("billid")));
			long count = service.IPDConsultEdit(consult,chssapplyid,consultmainidold);
			
			if (count > 0) {
				redir.addAttribute("result", "Consultation Data Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Consultation Data Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("tab","co");
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDConsultEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "IPDConsultationDelete.htm", method = RequestMethod.POST )
	public String IPDConsultationDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDConsultationDelete.htm "+Username);
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
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDConsultationDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "IPDTestsBillAdd.htm", method = RequestMethod.POST )
	public String IPDTestsBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDTestsBillAdd.htm "+Username);
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
			
			
			long count= service.IPDTestsBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Tests/Procedures Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Tests/Procedures Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("tab","te");
			
			return "redirect:/CHSSIPDApply.htm";
		

		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDTestsBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDTestBillEdit.htm", method = RequestMethod.POST )
	public String IPDTestBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDTestBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
			
			String testsubid = req.getParameter("test-subid-"+testid);
			String testcost = req.getParameter("test-cost-"+testid);
			
			CHSSBillTests test= new CHSSBillTests();
			test.setCHSSTestId(Long.parseLong(testid));
			test.setTestMainId(Long.parseLong(testsubid.split("_")[0]));
			test.setTestSubId(Long.parseLong(testsubid.split("_")[1]));
			test.setTestCost(Double.parseDouble(testcost));
			test.setBillId(Long.parseLong(req.getParameter("billid")));
			test.setModifiedBy(Username);
			
			long count = service.IPDTestBillEdit(test);
			
			if (count > 0) {
				redir.addAttribute("result", "Test/Procedure/Investigation Details Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Test/Procedure/Investigation Details Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("tab","te");
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDTestBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDTestBillDelete.htm", method = RequestMethod.POST )
	public String IPDTestBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDTestBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
						
			long count = service.IPDTestBillDelete(testid, Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Test/Procedure/Investigation Details Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Test/Procedure/Investigation Details Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("tab","te");
						
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDTestBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "IPDMiscBillAdd.htm", method = RequestMethod.POST )
	public String IPDMiscBillAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDMiscBillAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] miscname=req.getParameterValues("misc-name");
			String[] misccost=req.getParameterValues("misc-cost");
			String[] misccount=req.getParameterValues("misc-count");
			
			CHSSMiscDto dto=new CHSSMiscDto();
			
			dto.setBillId(billid);
			dto.setMiscItemName(miscname);
			dto.setMiscItemCost(misccost);
			dto.setMiscCount(misccount);
			dto.setCreatedBy(Username);
			
			long count= service.IPDMiscBillAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Item Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("tab","mi");
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDMiscBillAdd.htm "+Username, e);
			return "static/Error";
		}
	}

	@RequestMapping(value = "IPDMiscBillEdit.htm", method = RequestMethod.POST )
	public String IPDMiscBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDMiscBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssmiscid = req.getParameter("chssmiscid"); 
			
			String medsname = req.getParameter("misc-name-"+chssmiscid);
			String micscost = req.getParameter("misc-cost-"+chssmiscid);
			String micscount = req.getParameter("misc-count-"+chssmiscid);
			
			CHSSBillMisc meds= new CHSSBillMisc();
			meds.setChssMiscId(Long.parseLong(chssmiscid));
			meds.setMiscItemName(medsname);
			meds.setMiscCount(Integer.parseInt(micscount));
			meds.setMiscItemCost(Double.parseDouble(micscost));
			meds.setModifiedBy(Username);
			
			long count = service.IPDMiscBillEdit(meds);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("tab","mi");
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDMiscBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDMiscBillDelete.htm", method = RequestMethod.POST )
	public String IPDMiscBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDMiscBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssotherid = req.getParameter("chssmiscid"); 
						
			long count = service.IPDMiscBillDelete(chssotherid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("tab","mi");
		
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDMiscBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value ="ContingentBillsList.htm" )
	public String ContingentBillsList(Model model,HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside ContingentBillsList.htm "+Username);
		try {
			ses.setAttribute("SidebarActive", "ContingentBillsList_htm");
			
			String fromdate = req.getParameter("fromdate");
			String todate = req.getParameter("todate");
			String tab=  req.getParameter("tab");
			
			if(fromdate==null) {
				
				Map md=model.asMap();
				fromdate=(String)md.get("fromdate");
				todate=(String)md.get("todate");
				tab=(String)md.get("tab");
			}
			
			
			LocalDate today=LocalDate.now();
			
			if(fromdate==null) 
			{
				if(today.getMonthValue()<4) 
				{
					fromdate = String.valueOf(today.getYear()-1);
					todate=String.valueOf(today.getYear());
					
				}else{
					fromdate = String.valueOf(today.getYear());
					todate=String.valueOf(today.getYear()+1);
				}
				fromdate +="-04-01"; 
				todate +="-03-31";
							
			}else
			{
				fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
				todate=DateTimeFormatUtil.RegularToSqlDate(todate);
			}
			
			if(tab==null) {
				tab="progress";
			}
		
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			
			req.setAttribute("ApprovedBills", service.getCHSSContingentList("0",fromdate,todate));
			req.setAttribute("ProgressBills", service.getCHSSContingentList("1",fromdate,todate));
			req.setAttribute("logintype", LoginType);
			req.setAttribute("tab",tab);
			return "chss/ContingentBillsAll";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingentBillsList.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	@RequestMapping(value = "ConsolidatedExcelDownload.htm")
	public void ConsolidatedExcelDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ConsolidatedExcelDownload.htm "+UserId);
		try {	
			String contingentid = req.getParameter("contingentid");
			LinkedHashMap<Long, ArrayList<Object[]>> ContingentList = service.CHSSContingentClaimList(contingentid);
			Object[] contingentdata=service.CHSSContingentData(contingentid);
				
			IndianRupeeFormat nfc=new IndianRupeeFormat();
			AmountWordConveration awc = new AmountWordConveration();
			int rowNo=0;
			// Creating a worksheet
			Workbook workbook = new XSSFWorkbook();

			Sheet sheet = workbook.createSheet("Contingent Report");
			sheet.setColumnWidth(0, 1000);
			sheet.setColumnWidth(1, 2500);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 4500);
			
			XSSFFont font = ((XSSFWorkbook) workbook).createFont();
			font.setFontName("Times New Roman");
			font.setFontHeightInPoints((short) 10);
			font.setBold(true);
			// style for file header
			CellStyle file_header_Style = workbook.createCellStyle();
			file_header_Style.setLocked(true);
			file_header_Style.setFont(font);
			file_header_Style.setWrapText(true);
			file_header_Style.setAlignment(HorizontalAlignment.CENTER);
			file_header_Style.setVerticalAlignment(VerticalAlignment.CENTER);
			
			// style for table header
			CellStyle t_header_style = workbook.createCellStyle();
			t_header_style.setLocked(true);
			t_header_style.setFont(font);
			t_header_style.setWrapText(true);
			// style for table cells
			CellStyle t_body_style = workbook.createCellStyle();
			t_body_style.setWrapText(true);

			
			// File header Row
			Row file_header_row = sheet.createRow(rowNo++);
			sheet.addMergedRegion(new CellRangeAddress(0, 0,0, 4));   // Merging Header Cells 
			Cell cell= file_header_row.createCell(0);
			cell.setCellValue("CHSS Contingent Bill Pay Report \r\nRef: "+contingentdata[1].toString());
			file_header_row.setHeightInPoints((3*sheet.getDefaultRowHeightInPoints()));
			cell.setCellStyle(file_header_Style);
			
			CellStyle file_header_Style2 = workbook.createCellStyle();
			file_header_Style2.setLocked(true);
			file_header_Style2.setFont(font);
			file_header_Style2.setWrapText(true);
			file_header_Style2.setAlignment(HorizontalAlignment.RIGHT);
			file_header_Style2.setVerticalAlignment(VerticalAlignment.CENTER);
			
			Row file_header_row2 = sheet.createRow(rowNo++);
			sheet.addMergedRegion(new CellRangeAddress(1, 1,0, 4));   // Merging Header Cells 
			cell= file_header_row2.createCell(0);
			cell.setCellValue("Approved On : "+DateTimeFormatUtil.SqlToRegularDate(contingentdata[9].toString()));
			cell.setCellStyle(file_header_Style2);

			// Table in file header Row
			Row t_header_row = sheet.createRow(rowNo++);
			cell= t_header_row.createCell(0); 
			cell.setCellValue("SN"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(1); 
			cell.setCellValue("Emp. No."); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(2); 
			cell.setCellValue("Name"); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(3); 
			cell.setCellValue("Bank Account No."); 
			cell.setCellStyle(t_header_style);
			
			cell= t_header_row.createCell(4); 
			cell.setCellValue("Bank Transfer (₹)"); 
			cell.setCellStyle(t_header_style);
			
			
			
			long allowedamt=0;
			int i=0;
			
			for (Map.Entry mapEle : ContingentList.entrySet()) 
			{ 
				i++;
				int k=0;
				ArrayList<Object[]> arrlist = (ArrayList<Object[]>)mapEle.getValue();
				
				Row t_body_row = sheet.createRow(rowNo++);
				cell= t_body_row.createCell(0); 
				cell.setCellValue(i); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(1); 
				cell.setCellValue(arrlist.get(0)[24].toString()); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(2); 
				cell.setCellValue(arrlist.get(0)[22] .toString()); 
				cell.setCellStyle(t_body_style);
				
				cell= t_body_row.createCell(3); 
				cell.setCellValue(arrlist.get(0)[26].toString()); 
				cell.setCellStyle(t_body_style);
				
				long empallowedamount = 0;
			    for(Object[] obj :arrlist )
				{
			    	empallowedamount += Math.round( Double.parseDouble(obj[2].toString()));
				}
			    allowedamt +=empallowedamount;
				    
			    cell= t_body_row.createCell(4); 
				cell.setCellValue(nfc.rupeeFormat(String.valueOf( empallowedamount))); 
				cell.setCellStyle(t_body_style);   
				
				
							
			}
			// table footer style
			CellStyle t_footer_style = workbook.createCellStyle();
			t_footer_style.setLocked(true);
			t_footer_style.setFont(font);
			t_footer_style.setWrapText(true);
			t_footer_style.setFont(font);
			
			// table footer total
			Row f_foot_row = sheet.createRow(rowNo++);
			cell= f_foot_row.createCell(3); 
			cell.setCellValue("Total"); 
			cell.setCellStyle(t_footer_style);
					
		    cell= f_foot_row.createCell(4); 
			cell.setCellValue(nfc.rupeeFormat(String.valueOf( allowedamt))); 
			cell.setCellStyle(t_footer_style);  
			
			
			//footer style
			CellStyle file_footer_Style = workbook.createCellStyle();
			font.setBold(true);
			file_footer_Style.setLocked(true);
			file_footer_Style.setWrapText(true);
			file_footer_Style.setFont(font);
			// footer text amount
			Row f_foot_row2 = sheet.createRow(rowNo);
			sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo,0, 4));   
			cell= f_foot_row2.createCell(0);
			cell.setCellValue("In words Rupees "+awc.convert1(allowedamt)+" Only");
			cell.setCellStyle(file_footer_Style);
			rowNo++;

		
			String path = req.getServletContext().getRealPath("/view/temp");
			String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

			FileOutputStream outputStream = new FileOutputStream(fileLocation);
			workbook.write(outputStream);
			workbook.close();
			
			
			String filename="CHSSConsolidatedBill-Excel";
			
     
	        res.setContentType("Application/octet-stream");
	        res.setHeader("Content-disposition","attachment;filename="+filename+".xlsx");
	        File f=new File(fileLocation);
	         
	        
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
	       
	       
//	        Path pathOfFile= Paths.get( path+File.separator+filename+".xlsx"); 
//	        Files.delete(pathOfFile);		
		}
		catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside ConsolidatedExcelDownload.htm "+UserId, e); 
		}

	}
	
	
	@RequestMapping(value = "ClaimDisputeList.htm")
	public String ClaimDisputeList(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive","ClaimDisputeList_htm");
		
		logger.info(new Date() +"Inside ClaimDisputeList.htm "+Username);
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
			
			req.setAttribute("ClaimDisputeList", service.ClaimDisputeList("",""));
			req.setAttribute("ClosedDisputesList", service.ClaimDisputeClosedList(fromdate,todate));
			
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("tab", req.getParameter("tab"));
			return "chss/ClaimDisputeList";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimDisputeList.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	
	
	
	@RequestMapping(value = "ClaimDisputeSubmit.htm", method = RequestMethod.POST )
	public String ClaimDisputeSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ClaimDisputeSubmit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String disputemsg = req.getParameter("disputemsg"); 
			
			CHSSApplyDispute dispute= new CHSSApplyDispute();
			dispute.setCHSSApplyId(Long.parseLong(chssapplyid));
			dispute.setDisputeMsg(disputemsg);
			
			long count=service.ClaimDisputeSubmit(dispute, ses);
			
			if (count > 0) {
				redir.addAttribute("result", "Dispute Submitted Successfully");
			} else {
				redir.addAttribute("resultfail", "Dispute Submission Unsuccessful");	
			}	
			
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("ActivateDisp","Y");
			
			redir.addFlashAttribute("view_mode","U");
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimDisputeSubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ClaimDisputeResponceSubmit.htm", method = RequestMethod.POST )
	public String ClaimDisputeResponceSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ClaimDisputeResponceSubmit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String disputemsg = req.getParameter("Responcemsg"); 
			
			CHSSApplyDispute dispute= new CHSSApplyDispute();
			dispute.setCHSSApplyId(Long.parseLong(chssapplyid));
			dispute.setResponseMsg(disputemsg);
			
			long count=service.ClaimDisputeResponseSubmit(dispute, ses);
			
			if (count > 0) {
				redir.addAttribute("result", "Dispute Response Submitted Successfully");
			} else {
				redir.addAttribute("resultfail", "Dispute Response Submission Unsuccessful");	
			}	
			
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("ActivateDisp","Y");
			
			redir.addFlashAttribute("view_mode","A");
			return "redirect:/CHSSFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimDisputeResponceSubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "EquipmentItemAdd.htm", method = RequestMethod.POST )
	public String EquipmentItemAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EquipmentItemAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] equip_name=req.getParameterValues("equip_name");
			String[] equip_cost=req.getParameterValues("equip_cost");
			
			CHSSEquipDto dto=new CHSSEquipDto();
			
			dto.setBillId(billid);
			dto.setEquipName(equip_name);
			dto.setEquipCost(equip_cost);
			dto.setCreatedBy(Username);
			
			long count= service.EquipmentItemAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Item Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","eq");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EquipmentItemAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "EquipmentBillEdit.htm", method = RequestMethod.POST )
	public String EquipmentBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EquipmentBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssequipid = req.getParameter("chssequipid"); 
			
			String equip_name = req.getParameter("equip_name_"+chssequipid);
			String equip_cost = req.getParameter("equip_cost_"+chssequipid);
			
			CHSSBillEquipment equipment= new CHSSBillEquipment();
			equipment.setCHSSEquipmentId(Long.parseLong(chssequipid));
			equipment.setEquipmentName(equip_name);
			equipment.setEquipmentCost(Double.parseDouble(equip_cost));
			equipment.setModifiedBy(Username);
			
			long count = service.EquipmentItemEdit(equipment);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","eq");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EquipmentBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "EquipmentBillDelete.htm", method = RequestMethod.POST )
	public String EquipmentBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EquipmentBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssequipid = req.getParameter("chssequipid"); 
						
			long count = service.EquipmentBillDelete(chssequipid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","eq");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
			
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EquipmentBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ImplantItemAdd.htm", method = RequestMethod.POST )
	public String ImplantItemAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ImplantItemAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String[] impl_name=req.getParameterValues("impl_name");
			String[] impl_cost=req.getParameterValues("impl_cost");
			
			CHSSImplantDto dto=new CHSSImplantDto();
			
			dto.setBillId(billid);
			dto.setImplantName(impl_name);
			dto.setImplantCost(impl_cost);
			dto.setCreatedBy(Username);
			
			long count= service.ImplantItemAdd(dto);
			if (count > 0) {
				redir.addAttribute("result", "Item Details Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Details Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","im");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ImplantItemAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ImplantBillEdit.htm", method = RequestMethod.POST )
	public String ImplantBillEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ImplantBillEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssimplantid = req.getParameter("chssimplantid"); 
			
			String impl_name = req.getParameter("impl_name_"+chssimplantid);
			String impl_cost = req.getParameter("impl_cost_"+chssimplantid);
			
			CHSSBillImplants equipment= new CHSSBillImplants();
			equipment.setCHSSImplantId(Long.parseLong(chssimplantid));
			equipment.setImplantName(impl_name);
			equipment.setImplantCost(Double.parseDouble(impl_cost));
			equipment.setModifiedBy(Username);
			
			long count = service.ImplantItemEdit(equipment);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","im");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ImplantBillEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "ImplantBillDelete.htm", method = RequestMethod.POST )
	public String ImplantBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ImplantBillDelete.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssimplantid = req.getParameter("chssimplantid"); 
						
			long count = service.ImplantBillDelete(chssimplantid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Item Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Item Delete Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","im");
			redir.addFlashAttribute("consultmainid",req.getParameter("consultmainid"));
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ImplantBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	
	
	
	
	@RequestMapping(value = "CHSSIPDPackageAdd.htm", method = RequestMethod.POST )
	public String CHSSIPDPackageAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDPackageAdd.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String pkg_id = req.getParameter("pkg_id");
			String pkg_total_cost = req.getParameter("pkg_total_cost");
			
			String[] pkgitem_id=req.getParameterValues("pkgitem_id");
			String[] pkgitem_cost=req.getParameterValues("pkgitem_cost");
			
			CHSSIPDPackageDto pkgdto =  new CHSSIPDPackageDto();
			pkgdto.setBillid(billid);
			pkgdto.setPkg_id(pkg_id);
			pkgdto.setPkg_total_cost(pkg_total_cost);
			pkgdto.setPkgitem_id(pkgitem_id);
			pkgdto.setPkgitem_cost(pkgitem_cost);
			pkgdto.setCreatedBy(Username);
			
			long count=service.IPDPackageAddService(pkgdto);
			
			if (count > 0) {
				redir.addAttribute("result", "Package Added Successfully");
			} else {
				redir.addAttribute("resultfail", "Package Adding Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","pk");
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDPackageAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSIPDPackageEdit.htm", method = RequestMethod.POST )
	public String CHSSIPDPackageEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDPackageEdit.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String billid = req.getParameter("billid");
			
			String bill_pkg_id = req.getParameter("bill_pkg_id");
			String pkg_id = req.getParameter("pkg_id");
			String pkg_total_cost = req.getParameter("pkg_total_cost");
			
			String[] pkgitem_id=req.getParameterValues("pkgitem_id");
			String[] pkgitem_cost=req.getParameterValues("pkgitem_cost");
			
			CHSSIPDPackageDto pkgdto =  new CHSSIPDPackageDto();
			pkgdto.setCHSSBillPkgId(bill_pkg_id);
			pkgdto.setBillid(billid);
			pkgdto.setPkg_id(pkg_id);
			pkgdto.setPkg_total_cost(pkg_total_cost);
			pkgdto.setPkgitem_id(pkgitem_id);
			pkgdto.setPkgitem_cost(pkgitem_cost);
			pkgdto.setModifiedBy(Username);
			
			long count=service.IPDPackageEditService(pkgdto);
			
			if (count > 0) {
				redir.addAttribute("result", "Package Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Package Update Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",billid);
			redir.addFlashAttribute("tab","pk");
		
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDPackageEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSIPDPackageDelete.htm", method = RequestMethod.POST )
	public String CHSSIPDPackageDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDPackageDelete.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			
			String bill_pkg_id = req.getParameter("bill_pkg_id");
		
			CHSSBillPkg pkg = new CHSSBillPkg();
			pkg.setCHSSBillPkgId(Long.parseLong(bill_pkg_id));
			pkg.setModifiedBy(Username);
			long count=service.IPDPackageDeleteService(pkg);
			
			if (count > 0) {
				redir.addAttribute("result", "Package Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Package Delete Unsuccessful");	
			}	
			redir.addFlashAttribute("chssapplyid",chssapplyid);
//			redir.addFlashAttribute("tab","pk");
			return "redirect:/CHSSIPDApply.htm";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDPackageDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	

	@RequestMapping(value = "ClaimPkgItemsListAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String ClaimPkgItemsListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ClaimPkgItemsListAjax.htm "+Username);
		List<CHSSIPDPkgItems> pkgitems = new ArrayList<CHSSIPDPkgItems>();
		try {
			String billid = req.getParameter("billid");
			String billpkgid = req.getParameter("billpkgid");
			
			pkgitems = service.ClaimPkgItemsAddedAjax(billid, billpkgid);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ClaimPkgItemsListAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(pkgitems);
	}
	
	
	
	@RequestMapping(value = "IPDAttachmentsUpdateAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String IPDAttachmentsUpdateAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDAttachmentsUpdateAjax.htm "+Username);
		long count=0; 
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String attachtypeid = req.getParameter("attachtypeid");
			String value = req.getParameter("value");
			count = service.IPDAttachmentsUpdate(chssapplyid, attachtypeid, value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDAttachmentsUpdateAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(count);
	}
	
	@RequestMapping(value = "IPDSingleBillHeadsAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String IPDSingleBillHeadsAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDSingleBillHeadsAjax.htm "+Username);
		long count=0; 
		try {
		
			String billid = req.getParameter("billid");
			String billheadid = req.getParameter("billheadid");
			String billheadcost = req.getParameter("billheadcost");
			if(billheadcost==null || billheadcost.trim().equalsIgnoreCase("")) {
				billheadcost="0";
			}
			
			CHSSBillOther other = new CHSSBillOther();
			other.setOtherItemId(Integer.parseInt(billheadid));
			other.setBillId(Long.parseLong(billid));
			other.setOtherItemCost(Double.parseDouble(billheadcost));
			other.setCreatedBy(Username);
			count= service.IPDBillHeadDataAddEdit(other);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDSingleBillHeadsAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(count);
	}
	
	@RequestMapping(value = "IPDClaimBillDelete.htm", method = RequestMethod.POST )
	public String IPDClaimBillDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside IPDClaimBillDelete.htm "+Username);
		try {
			
			String billid = req.getParameter("billid");
			long count = service.IPDClaimBillDelete(billid,Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Deletion Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",req.getParameter("chssapplyid"));
			return "redirect:/CHSSIPDApply.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDClaimBillDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSIPDClaimFwdApproveAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String CHSSIPDClaimFwdApproveAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSIPDClaimFwdApproveAjax.htm "+Username);
		String Msg = "";
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			List<Object[]> billslist = service.CHSSBillsList(chssapplyid);
			Object[] chssapplydata = service.CHSSAppliedData(chssapplyid);
			
			double claimamount=0;
			
			
			LocalDate applydate = LocalDate.now();
			
			if(Integer.parseInt(chssapplydata[9].toString())>1) 
			{
				applydate =LocalDate.parse(chssapplydata[15].toString());
			}
			
			for(Object[] bill : billslist) 
			{
				LocalDate billdate = LocalDate.parse(bill[4].toString());
				
				if( !billdate.isAfter(applydate.minusMonths(3)) && !applydate.minusMonths(3).isEqual(billdate) ) 
				{
					Msg = "Cannot Forward Claim Since Bill No : '"+bill[2]+"' is older than 3 months.";
					return Msg;
				}
				
				if(Double.parseDouble(bill[9].toString())==0) 
				{
					Msg = "Please Enter Atleast Breakup of the Bill";
					return Msg;
				}
				
				if(Math.round(Double.parseDouble(bill[6].toString())+Double.parseDouble(bill[7].toString())) != Math.round(Double.parseDouble(bill[9].toString())))
				{
					Msg = "Sum of Items Cost in Bill No '"+bill[2]+"' does not Tally with Amount Paid. \nBill Amount ="
							+(Double.parseDouble(bill[6].toString())+Double.parseDouble(bill[7].toString()))+"\nItems Total = "+bill[9];
					return Msg;
				}
				
				
				claimamount += Double.parseDouble(bill[9].toString());
			}
			
			if(claimamount==0) 
			{
				Msg = "Total claim amount should not be zero !";
				return Msg;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDClaimFwdApproveAjax.htm "+Username, e);
		}
		return Msg;
	}
	
	
	@RequestMapping(value = "CHSSIPDFormEdit.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String CHSSIPDFormEdit(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSIPDFormEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			
			String view_mode = req.getParameter("view_mode");
			
			if (chssapplyid == null) 
			{
				Map md=model.asMap();
				chssapplyid=(String)md.get("chssapplyid");
			}	
			
			if(chssapplyid==null) {
				redir.addAttribute("result", "Refresh Not Allowed");
				return "redirect:/CHSSApplyDashboard.htm";
			}
			
//			String tab = req.getParameter("tab");
//			if (tab == null) 
//			{
//				Map md=model.asMap();
//				tab=(String)md.get("tab");
//			}	
			
			Object[] apply = service.CHSSAppliedData(chssapplyid);
			Object[] employee = service.getEmployee(apply[1].toString());
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", employee);
			
			if(apply[3].toString().equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(apply[2].toString()));
			}
			
			long basicpay=0;
			if(employee[4]!=null) {
				 basicpay=Long.parseLong(employee[4].toString());
			}		
			CHSSOtherPermitAmt chssremamt = service.getCHSSOtherPermitAmt("1",basicpay);
			
				List<Object[]> chssbill = service.CHSSConsultMainBillsList("0",chssapplyid);
				String billid ="0";
				if(chssbill.size()>0){
					billid = chssbill.get(0)[0].toString();
				}
				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("ipdbasicinfo", service.IpdClaimInfo(chssapplyid));
				req.setAttribute("chssbill", chssbill );
				req.setAttribute("NonPackageItems", service.IPDBillOtherItems(billid));
				
				req.setAttribute("ClaimPackages", service.ClaimPackagesList(billid));
				req.setAttribute("ClaimPkgItems", service.ClaimAllPackageItemsList(billid));
				
				req.setAttribute("consultations", service.CHSSConsultDataList(chssapplyid));
				req.setAttribute("billtests",service.CHSSTestsDataList(chssapplyid));
				req.setAttribute("miscitems",service.CHSSMiscDataList(chssapplyid));
				
				req.setAttribute("equipments", service.ClaimEquipmentList(chssapplyid));
				req.setAttribute("implants", service.ClaimImplantList(chssapplyid));
				
				req.setAttribute("ClaimAttachDeclare", service.IPDClaimAttachments(chssapplyid));
				
				req.setAttribute("view_mode", view_mode);
				req.setAttribute("ClaimapprovedPOVO", service.ClaimApprovedPOVOData(chssapplyid));
				req.setAttribute("ClaimRemarksHistory", service.ClaimRemarksHistory(chssapplyid));
				req.setAttribute("ClaimDisputeData", service.getClaimDisputeData(chssapplyid));
				req.setAttribute("logintype", LoginType);
				req.setAttribute("ActivateDisp", req.getParameter("ActivateDisp"));
				req.setAttribute("dispReplyEnable", req.getParameter("dispReplyEnable"));
				
				return "chss/CHSSFormEditIPD";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDFormEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CHSSUserIPDForward.htm", method = RequestMethod.POST)
	public String CHSSUserIPDForward(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String LoginType = (String) ses.getAttribute("LoginType");
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date() +"Inside CHSSUserIPDForward.htm "+Username);
		try {
			String chssapplyid = req.getParameter("chssapplyid");
			String action = req.getParameter("claimaction");
			String remarks = req.getParameter("remarks");
			
			CHSSApply claim1 = service.CHSSApplied(chssapplyid);
			int chssstatusid= claim1.getCHSSStatusId();
			long contingentid=claim1.getContingentId();
			long count = service.CHSSUserIPDForward(chssapplyid, Username, action,remarks,EmpId,EmpNo,LoginType);
			
			if (chssstatusid == 1 || chssstatusid ==3 ) 
			{
				String enclosurecount = req.getParameter("enclosurecount");
				if(enclosurecount!= null && Integer.parseInt(enclosurecount)>0) 
				{
					CHSSApplyDto dto =new CHSSApplyDto();
					dto.setCHSSApplyId(chssapplyid);
					dto.setNoEnclosures(enclosurecount);
					
					service.CHSSApplyEncCountEdit(dto);
				}
				
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
				
				
				if((chssstatusid==6 || chssstatusid == 9 || chssstatusid == 11 || chssstatusid == 13) && contingentid>0 ) {
					redir.addFlashAttribute("contingentid", String.valueOf(contingentid));
					return "redirect:/ContingetBill.htm";
				}
				
				if(chssstatusid>=6 ) 
				{
					return "redirect:/CHSSBatchList.htm";
				}
				else
				{
					return "redirect:/CHSSIPDApprovalsList.htm";
				}
			}
			return "redirect:/CHSSApplyDashboard.htm";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSUserIPDForward.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "IPDConsultRemAmountEdit.htm", method = RequestMethod.POST )
	public String IPDConsultRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDConsultRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String consultationid = req.getParameter("consultationid"); 
			
			String consultremamount = req.getParameter("consultremamount-"+consultationid);
			String consulttype = req.getParameter("consulttype-"+consultationid);
			String consultcomment = req.getParameter("consultcomment-"+consultationid);
			
			CHSSBillConsultation consult= new CHSSBillConsultation();
			consult.setConsultationId(Long.parseLong(consultationid));
			consult.setConsultType(consulttype);
			consult.setConsultRemAmount(Double.parseDouble(consultremamount));
			consult.setComments(consultcomment);
			consult.setModifiedBy(Username);
			consult.setUpdateByEmpId(EmpId);
			consult.setUpdateByRole(LoginType);
			
			long count = service.IPDConsultRemAmountEdit(consult);
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("tab","");
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDConsultRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDBillheadRemAmountEdit.htm", method = RequestMethod.POST )
	public String IPDBillheadRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDBillheadRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssotherid = req.getParameter("chssotherid"); 
			
			String otherremamount = req.getParameter("otherremamount-"+chssotherid);
			String othercomment = req.getParameter("othercomment-"+chssotherid);
			
			CHSSBillOther other = new CHSSBillOther();
			other.setCHSSOtherId(Long.parseLong(chssotherid));
			other.setOtherRemAmount(Double.parseDouble(otherremamount));
			other.setComments(othercomment);
			other.setModifiedBy(Username);
			other.setUpdateByEmpId(EmpId);
			other.setUpdateByRole(LoginType);
			
			
			long count = service.IPDOtherRemAmountEdit(other);
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDBillheadRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDPkgRemAmountEdit.htm", method = RequestMethod.POST )
	public String IPDPkgRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDPkgRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String chssbillpkgid = req.getParameter("chssbillpkgid"); 
			
			String pkg_remamount = req.getParameter("pkg_remamount-"+chssbillpkgid);
			String pkg_comment = req.getParameter("pkg_comment-"+chssbillpkgid);
			
			CHSSBillPkg pkg = new CHSSBillPkg();
			pkg.setCHSSBillPkgId(Long.parseLong(chssbillpkgid));
			pkg.setPkgRemAmt(Double.parseDouble(pkg_remamount));
			pkg.setComments(pkg_comment);
			pkg.setModifiedBy(Username);
			pkg.setUpdateByEmpId(EmpId);
			pkg.setUpdateByRole(LoginType);
			
			
			long count = service.IPDPkgRemAmountEdit(pkg);
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDPkgRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDTestRemAmountEdit.htm", method = RequestMethod.POST )
	public String IPDTestRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDTestRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String testid = req.getParameter("testid"); 
			
			String testremamount = req.getParameter("testremamount-"+testid);
			String testcomment = req.getParameter("testcomment-"+testid);
			
			
			CHSSBillTests test= new CHSSBillTests();
			test.setCHSSTestId(Long.parseLong(testid));
			test.setTestRemAmount(Double.parseDouble(testremamount));
			test.setComments(testcomment);
			test.setModifiedBy(Username);
			test.setUpdateByEmpId(EmpId);
			test.setUpdateByRole(LoginType);
			
			long count = service.IPDTestRemAmountEdit(test);
						
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDTestRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "IPDEquipRemEdit.htm", method = RequestMethod.POST )
	public String IPDEquipRemEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDEquipRemEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String equipmentid = req.getParameter("equipmentid"); 
			
			String equipremamount = req.getParameter("equipremamount-"+equipmentid);
			String equipcomment = req.getParameter("equipcomment-"+equipmentid);
			
			
			CHSSBillEquipment equipment= new CHSSBillEquipment();
			equipment.setCHSSEquipmentId(Long.parseLong(equipmentid));
			equipment.setComments(equipcomment);
			equipment.setEquipmentRemAmt(Double.parseDouble(equipremamount));
			equipment.setModifiedBy(Username);
			equipment.setUpdateByEmpId(EmpId);
			equipment.setUpdateByRole(LoginType);

			long count = service.IPDEquipmentRemEdit(equipment);
						
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDEquipRemEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDImplantRemEdit.htm", method = RequestMethod.POST )
	public String IPDImplantRemEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDImplantRemEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String implantid = req.getParameter("implantid"); 
			
			String implantremamount = req.getParameter("implantremamount-"+implantid);
			String implantcomment = req.getParameter("implantcomment-"+implantid);
			
			
			CHSSBillImplants implant = new CHSSBillImplants();
			implant.setCHSSImplantId(Long.parseLong(implantid));
			implant.setComments(implantcomment);
			implant.setImplantRemAmt(Double.parseDouble(implantremamount));
			implant.setModifiedBy(Username);
			implant.setUpdateByEmpId(EmpId);
			implant.setUpdateByRole(LoginType);

			long count = service.IPDImplantRemEdit(implant);
						
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDImplantRemEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "IPDMiscRemAmountEdit.htm", method = RequestMethod.POST )
	public String IPDMiscRemAmountEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		logger.info(new Date() +"Inside IPDMiscRemAmountEdit.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			String miscid = req.getParameter("miscid"); 
			
			String miscremamount = req.getParameter("miscremamount-"+miscid);
			String miscomment = req.getParameter("miscomment-"+miscid);
			
			CHSSBillMisc misc= new CHSSBillMisc();
			misc.setChssMiscId(Long.parseLong(miscid));
			misc.setMiscRemAmount(Double.parseDouble(miscremamount));
			misc.setComments(miscomment);
			misc.setModifiedBy(Username);
			misc.setUpdateByEmpId(EmpId);
			misc.setUpdateByRole(LoginType);
			
			long count = service.MiscRemAmountEdit(misc);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Reimbursable Amount Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Reimbursable Amount Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("chssapplyid",chssapplyid);
			redir.addFlashAttribute("billid",req.getParameter("billid"));
			
			redir.addFlashAttribute("view_mode","E");
			return "redirect:/CHSSIPDFormEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside IPDMiscRemAmountEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CHSSIPDFormDownload.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public void CHSSIPDFormDownload(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CHSSIPDFormDownload.htm "+Username);
		try {
			
			String chssapplyid = req.getParameter("chssapplyid");
			
			String view_mode = req.getParameter("view_mode");
			
			
			Object[] apply= service.CHSSAppliedData(chssapplyid);
			
			req.setAttribute("chssapplydata", apply);
			req.setAttribute("employee", service.getEmployee(apply[1].toString()));
			
			
			if(apply[3].toString().equalsIgnoreCase("N")) 
			{
				req.setAttribute("familyMemberData", service.familyMemberData(apply[2].toString()));
			}
			
				List<Object[]> chssbill = service.CHSSConsultMainBillsList("0",chssapplyid);
				String billid ="0";
				if(chssbill.size()>0){
					billid = chssbill.get(0)[0].toString();
				}
				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("ipdbasicinfo", service.IpdClaimInfo(chssapplyid));
				req.setAttribute("chssbill", chssbill );
				req.setAttribute("NonPackageItems", service.IPDBillOtherItems(billid));
				
				req.setAttribute("ClaimPackages", service.ClaimPackagesList(billid));
				req.setAttribute("ClaimPkgItems", service.ClaimAllPackageItemsList(billid));
				
				req.setAttribute("consultations", service.CHSSConsultDataList(chssapplyid));
				req.setAttribute("billtests",service.CHSSTestsDataList(chssapplyid));
				req.setAttribute("miscitems",service.CHSSMiscDataList(chssapplyid));
				
				req.setAttribute("equipments", service.ClaimEquipmentList(chssapplyid));
				req.setAttribute("implants", service.ClaimImplantList(chssapplyid));
				
				req.setAttribute("ClaimAttachDeclare", service.IPDClaimAttachments(chssapplyid));
				
				req.setAttribute("view_mode", view_mode);
				req.setAttribute("ClaimapprovedPOVO", service.ClaimApprovedPOVOData(chssapplyid));
				req.setAttribute("ClaimRemarksHistory", service.ClaimRemarksHistory(chssapplyid));
			
				
				
				String filename="Claim-"+apply[16].toString().trim().replace("/", "-");
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/chss/CHSSFormIPD.jsp").forward(req, customResponse);
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
				
				
//				return "chss/CHSSFormIPD";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CHSSIPDFormDownload.htm "+Username, e);
//			return "static/Error";
		}
	}
		
	@RequestMapping(value = "ContingentClaimDrop.htm", method = RequestMethod.POST)
	public String ContingentFormDrop(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ContingentClaimDrop.htm "+Username);
		try {
			String[] chssapplyids = req.getParameterValues("chssapplyid");
			String contingentid = req.getParameter("contingentid");
			
			long count = service.ContingentClaimDrop(chssapplyids, Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Contingent Bill Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Contingent Bill Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("contingentid", String.valueOf(contingentid));
			return "redirect:/ContingetBill.htm";		
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside ContingentClaimDrop.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "DependantsList.htm", method = {RequestMethod.POST , RequestMethod.GET } )
	public String DependantsList(Model model, HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ses.setAttribute("SidebarActive", "DependantsList_htm");
		logger.info(new Date() +"Inside DependantsList.htm "+Username);
		String EmpNo=null;
		List<Object[]> dependantList=null;
			
		try {
			ses.setAttribute("SidebarActive","DependantsList_htm");
			EmpNo= req.getParameter("EmpNo");
			
			List<Object[]> Emplist=service.getEmpList();
			if(EmpNo!=null) {				
				dependantList=service.getDependantsList(EmpNo);
				req.setAttribute("dependantList", dependantList);
				req.setAttribute("EmpNo", EmpNo);
			}
			else {
			Long  EmpId=(Long)ses.getAttribute("EmpId");
			EmpNo=EmpId.toString();
			dependantList=service.getDependantsList(EmpNo);
			req.setAttribute("dependantList", dependantList);
			req.setAttribute("EmpNo", EmpNo);
			}
			req.setAttribute("Emplist", Emplist);
			return"chss/DependantsList";
		} catch (Exception e) {		
			e.printStackTrace();
			logger.error(new Date() +" Inside DependantsList.htm "+Username, e);
			return "static/Error";
			
		}
		
	}
}

package com.vts.ems.leave.controller;


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
import com.vts.ems.leave.dto.LeaveApplyDto;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.service.LeaveService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class LeaveController {
	private static final Logger logger = LogManager.getLogger(LeaveController.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	@Autowired
	private LeaveService service;
	
	
	@RequestMapping(value = "PisHolidayList.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PisHolidayList(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PisHolidayList.htm "+UserId);		
		try {
			String yr=req.getParameter("Year");
			if(yr==null)
			yr=sdf.getCurrentYear();	
		    req.setAttribute("HoliList", service.PisHolidayList(yr));
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside PisHolidayList.htm "+UserId, e);
	       }
	   return "pis/PisHolidayList";

	}
	
	@RequestMapping(value = "LeaveCredit.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveCredit(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside LeaveCredit.htm"+UserId);		
		try {
			String month=req.getParameter("month");
			String yr=req.getParameter("Year");
			if(yr==null) {
			yr=sdf.getCurrentYear();
			month="January";
			}
		    req.setAttribute("CreditList", service.LeaveCreditList(month,yr));
		    req.setAttribute("EmpList", service.EmpList());
		    req.setAttribute("month", month);
		    req.setAttribute("year", yr);
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveCredit.htm"+UserId, e);
	       }
	   return "leave/LeaveCreditList";

	}
	
	@RequestMapping(value = "CreditPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String CreditPreview(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CreditPreview.htm"+UserId);		
		try {
			String month=req.getParameter("month");
			String yr=req.getParameter("Year");
			req.setAttribute("year",yr);
			req.setAttribute("month",month);
			req.setAttribute("empNo",req.getParameter("empNo"));
		    req.setAttribute("CreditPreview", service.LeaveCreditPreview(month,yr,req.getParameter("empNo")));
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside CreditPreview.htm"+UserId, e);
	       }
	   return "leave/LeaveCreditPreview";

	}
	
	@RequestMapping(value = "LeaveCredited.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveCredited(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveCredited.htm"+UserId);		
		try {
			String month=req.getParameter("month");
			String yr=req.getParameter("Year");
			req.setAttribute("year",yr);
			req.setAttribute("month",month);
		    long result=service.LeaveCredited(month,yr,req.getParameter("empNo"));
		    if (result>0) {
				redir.addAttribute("result", "Leave Credited Successfully For "+month+" "+yr);
			} else {
				redir.addAttribute("resultfail", "Leave Credit unsuccessful For "+month+" "+yr);
			}
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveCredited.htm"+UserId, e);
	       }
	   return "redirect:/LeaveCredit.htm";

	}
	
	
	@RequestMapping(value = "GetCreditDetails.htm" , method = RequestMethod.GET)
	public @ResponseBody String GetCreditDetails(HttpServletRequest req ,HttpSession ses) throws Exception {
		
		Gson json = new Gson();
		List<Object[]> GetCreditDetails = null;
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetCreditDetails.htm "+UserId);		
		try {
			GetCreditDetails =service.LeaveCreditInd(req.getParameter("month"),req.getParameter("year"),req.getParameter("empNo")); 
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetCreditDetails.htm "+UserId, e);
		}
			return json.toJson(GetCreditDetails);	
	}
	
	@RequestMapping(value = "GetCreditByEmp.htm" , method = RequestMethod.GET)
	public @ResponseBody String GetCreditByEmp(HttpServletRequest req ,HttpSession ses) throws Exception {
		
		Gson json = new Gson();
		List<Object[]> GetCreditDetails = null;
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside GetCreditByEmp.htm "+UserId);		
		try {
			GetCreditDetails =service.LeaveCreditById(req.getParameter("registerid"));
		     
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetCreditByEmp.htm "+UserId, e);
		}
			return json.toJson(GetCreditDetails);	
	}

	@RequestMapping(value = "LeaveApply.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveApply(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveApply.htm "+UserId);		
		try { 
			 String EmpNo=req.getParameter("EmpNo");
			 if(EmpNo==null) {
				 EmpNo=(String)ses.getAttribute("EmpNo");
			 }
			 req.setAttribute("EmpNo",EmpNo);
			 req.setAttribute("EmpList", service.EmployeeList());
			 req.setAttribute("empdetails", service.EmpDetails(EmpNo));
			 req.setAttribute("officerdetails", service.OfficerDetails(EmpNo));
			 req.setAttribute("leaveType", service.LeaveCode(EmpNo));
			 req.setAttribute("purposeList", service.purposeList());
			 req.setAttribute("register", service.getRegister(EmpNo,sdf.getCurrentYear()));
			 req.setAttribute("applied", service.getAppliedLeave(EmpNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveApply.htm "+UserId, e);
	       }
	   return "leave/LeaveApply";

	}
	
	@RequestMapping(value = "AddUpdateCredit.htm", method = {RequestMethod.POST})
	public String AddUpdateCredit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside AddUpdateCredit.htm"+UserId);		
		try {
			String month=req.getParameter("mnth");
			String year=req.getParameter("yr");
			LeaveRegister register=new LeaveRegister();
			if(req.getParameter("type").equalsIgnoreCase("C")) {
			register.setEMPID(req.getParameter("EmpId"));
			}else if(req.getParameter("type").equalsIgnoreCase("U")) {
				register.setRegisterId(Long.parseLong(req.getParameter("RegId")));
				register.setEMPID(req.getParameter("EmpId"));
			}
			register.setCL(Double.parseDouble(req.getParameter("cl")));
			register.setEL(Integer.parseInt(req.getParameter("el")));
			register.setHPL(Integer.parseInt(req.getParameter("hpl")));
			register.setCML(0);
			register.setRH(Integer.parseInt(req.getParameter("rh")));
			register.setEL_LAPSE(0);
			register.setML(Integer.parseInt(req.getParameter("ml")));
			register.setPL(Integer.parseInt(req.getParameter("pl")));
			register.setSL(Integer.parseInt(req.getParameter("sl")));
			register.setCCL(Integer.parseInt(req.getParameter("ccl")));
			register.setADV_EL(0);
			register.setADV_HPL(0);
			register.setEOL(0);
			register.setYEAR(year);
			if("January".equalsIgnoreCase(month)){
			register.setFROM_DATE(year+"-01-01");
			register.setTO_DATE(year+"-01-01");
			register.setSTATUS("LKU");
			register.setMONTH(month);
			}else if("July".equalsIgnoreCase(month)){
				register.setFROM_DATE(year+"-07-01");
				register.setTO_DATE(year+"-07-01");
				register.setSTATUS("LKU");
				register.setMONTH(month);
			}else if("LOB".equalsIgnoreCase(month)){
				register.setFROM_DATE(year+"-01-01");
				register.setTO_DATE(year+"-01-01");
				register.setSTATUS("LOB");
				register.setMONTH("January");
			}
			register.setAPPL_ID("0");
			register.setCREDITED_BY(UserId);
			register.setCREDITED_ON(sdf.getSqlDateAndTimeFormat().format(new Date()));
			register.setREMARKS("SYSTEM");

			
			long result=service.LeaveCreditedAddUpdate(register,req.getParameter("type"));
		    if (result>0) {
				redir.addAttribute("result", "Leave Credited Successfully");
			} else {
				redir.addAttribute("resultfail", "Leave Credit Update unsuccessful");
			}
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside AddUpdateCredit.htm"+UserId, e);
	       }
	   return "redirect:/LeaveCredit.htm";

	}
	
	
	@RequestMapping(value = "GetHolidays.htm" , method = RequestMethod.GET)
	public @ResponseBody String GetHolidays(HttpServletRequest req ,HttpSession ses) throws Exception {
		
		Gson json = new Gson();
		List<Object[]> GetCreditDetails = null;
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside GetHolidays.htm "+UserId);		
		try {
			GetCreditDetails =service.GetHolidays(req.getParameter("type"));
		     
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetHolidays.htm "+UserId, e);
		}
			return json.toJson(GetCreditDetails);	
	}
	

	@RequestMapping(value = "GetLeaveChecked.htm" , method = RequestMethod.GET)
	public @ResponseBody String GetLeaveChecked(HttpServletRequest req ,HttpSession ses) throws Exception {
		String[] Result=null;
		Gson json = new Gson();
		String UserId=req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside GetLeaveChecked.htm "+UserId);		
		try {
			LeaveApplyDto dto=new LeaveApplyDto();
			dto.setEmpNo(req.getParameter("empno"));
			dto.setLTC(req.getParameter("ELCash"));
			dto.setFromDate(req.getParameter("fdate"));
			dto.setToDate(req.getParameter("tdate"));
			dto.setLeaveType(req.getParameter("leavetype"));
			dto.setHalfOrFull(req.getParameter("halforfull"));
			dto.setHours(req.getParameter("hours"));
			dto.setHandingOverEmpid(req.getParameter("HandingOverEmpid"));
			dto.setUserId(UserId);
			Result=service.LeaveCheck(dto);
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetLeaveChecked.htm "+UserId, e);
		}
			return json.toJson(Result);	
	}
	
	@RequestMapping(value = "apply-leave-add.htm" , method = RequestMethod.POST)
	public  String applyLeaveAdd(HttpServletRequest req ,HttpSession ses,RedirectAttributes redir) throws Exception {
		String[] Result=null;
		String UserId=req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside applyLeaveAdd.htm "+UserId);		
		try {
			LeaveApplyDto dto=new LeaveApplyDto();
			dto.setEmpNo(req.getParameter("empNo"));
			dto.setLTC(req.getParameter("elcash"));
			dto.setFromDate(req.getParameter("startdate"));
			dto.setToDate(req.getParameter("enddate"));
			dto.setLeaveType(req.getParameter("leavetypecode"));
			dto.setHalfOrFull(req.getParameter("fullhalf"));
			dto.setHours(req.getParameter("hours"));
			dto.setAnFN(req.getParameter("anfn"));
			dto.setPurLeave(req.getParameter("leavepurpose"));
			dto.setLeaveAddress(req.getParameter("leaveadd"));
			dto.setRemarks(req.getParameter("remark"));
			dto.setHandingOverEmpid(req.getParameter("HandingOverEmpid"));
			dto.setUserId(UserId);
			dto.setActEmpNo((String)ses.getAttribute("EmpNo"));
			Result=service.applyLeaveAdd(dto);
			if("Pass".equals(Result[1])) {
				redir.addAttribute("result",Result[0]);
				redir.addFlashAttribute("EmpNo",dto.getEmpNo());
			}else if("Fail".equals(Result[1])) {
				redir.addAttribute("resultfail",Result[0]);
			}
			
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside applyLeaveAdd.htm "+UserId, e);
		}
			return "redirect:/applyLeaveRedirect.htm";	
	}
	
	@RequestMapping(value = "applyLeaveRedirect.htm")
	public String applyLeaveRedirect(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,Model model) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside applyLeaveRedirect.htm "+UserId);		
		try { 
			 Map md=model.asMap();
			 String EmpNo=(String)md.get("EmpNo");
			 if(EmpNo==null) {
				 redir.addAttribute("result", "Refresh Not Allowed");
					return "redirect:/LeaveApply.htm";
			 }
			 req.setAttribute("EmpNo",EmpNo);
			 req.setAttribute("EmpList", service.EmployeeList());
			 req.setAttribute("empdetails", service.EmpDetails(EmpNo));
			 req.setAttribute("officerdetails", service.OfficerDetails(EmpNo));
			 req.setAttribute("leaveType", service.LeaveCode(EmpNo));
			 req.setAttribute("purposeList", service.purposeList());
			 req.setAttribute("register", service.getRegister(EmpNo,sdf.getCurrentYear()));
			 req.setAttribute("applied", service.getAppliedLeave(EmpNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside applyLeaveRedirect.htm "+UserId, e);
	       }
	   return "leave/LeaveApply";

	}

	@RequestMapping(value = "LeaveRegister.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveRegister(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveRegister.htm"+UserId);		
		try {
			String empNo=req.getParameter("empNo");
			String yr=req.getParameter("Year");
			if(yr==null) {
			yr=sdf.getCurrentYear();
			empNo=(String)ses.getAttribute("EmpNo");
			}
			req.setAttribute("register", service.RegisterOpening(empNo,yr));
		    req.setAttribute("RegisterList", service.LeaveRegisterList(empNo,yr));
		    req.setAttribute("EmpList", service.EmpList());
		    req.setAttribute("empNo", empNo);
		    req.setAttribute("year", yr);
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveRegister.htm"+UserId, e);
	       }
	   return "leave/LeaveRegister";

	}
	
	
}

package com.vts.ems.leave.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.leave.dto.LeaveCheckDto;
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
			 req.setAttribute("officerdetails", service.EmpDetails(EmpNo));
			 req.setAttribute("leaveType", service.LeaveCode(EmpNo));
			 req.setAttribute("purposeList", service.purposeList());
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
			register.setCML(Integer.parseInt(req.getParameter("cml")));
			register.setRH(Integer.parseInt(req.getParameter("rh")));
			register.setEL_LAPSE(0);
			register.setML(Integer.parseInt(req.getParameter("ml")));
			register.setPL(Integer.parseInt(req.getParameter("pl")));
			register.setSL(Integer.parseInt(req.getParameter("sl")));
			register.setCCL(Integer.parseInt(req.getParameter("ccl")));
			register.setADV_EL(0);
			register.setADV_HPL(0);
			register.setEOL(0);
			register.setMONTH(month);
			register.setYEAR(year);
			register.setSTATUS("LKU");
			if("January".equalsIgnoreCase(month)){
			register.setFROM_DATE(year+"-01-01");
			register.setTO_DATE(year+"-01-01");
			}else if("July".equalsIgnoreCase(month)){
				register.setFROM_DATE(year+"-07-01");
				register.setTO_DATE(year+"-07-01");
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
		String Result="Please Try Again";
		Gson json = new Gson();
		String UserId=req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside GetLeaveChecked.htm "+UserId);		
		try {
			LeaveCheckDto dto=new LeaveCheckDto();
			dto.setEmpNo(req.getParameter("empno"));
			dto.setElCash(req.getParameter("ELCash"));
			dto.setFromDate(req.getParameter("fdate"));
			dto.setToDate(req.getParameter("tdate"));
			dto.setLeaveType(req.getParameter("leavetype"));
			dto.setHalfOrFull(req.getParameter("halforfull"));
			dto.setHours(req.getParameter("hours"));
			dto.setUserId(UserId);
			Result=service.LeaveCheck(dto);
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetLeaveChecked.htm "+UserId, e);
		}
			return json.toJson(Result);	
	}
}

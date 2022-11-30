package com.vts.ems.leave.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.leave.dto.LeaveApplyDto;
import com.vts.ems.leave.dto.LeaveAttachmentDto;
import com.vts.ems.leave.model.LeaveMC_FC;
import com.vts.ems.leave.model.LeaveRaSa;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.service.LeaveService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
@RequestMapping("/Blocked")
public class LeaveController {
	private static final Logger logger = LogManager.getLogger(LeaveController.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	@Autowired
	private LeaveService service;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;

	@Autowired
    private Environment env;
	
	@RequestMapping(value = "PisHolidayList.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PisHolidayList(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PisHolidayList.htm "+UserId);		
		try {
			String yr=req.getParameter("Year");
			if(yr==null)
			yr=sdf.getCurrentYear();	
		    req.setAttribute("HoliList", service.PisHolidayList(yr));
		    ses.setAttribute("SidebarActive", "PisHolidayList_htm");
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside PisHolidayList.htm "+UserId, e);
	       }
	   return "pis/PisHolidayList";

	}
	
	@RequestMapping(value = "LeaveCredit.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveCredit(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside LeaveCredit.htm "+UserId);		
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
		    ses.setAttribute("SidebarActive", "LeaveCredit_htm");
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveCredit.htm "+UserId, e);
	       }
	   return "leave/LeaveCreditList";

	}
	
	@RequestMapping(value = "CreditPreview.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String CreditPreview(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CreditPreview.htm "+UserId);		
		try {
			String month=req.getParameter("month");
			String yr=req.getParameter("Year");
			req.setAttribute("year",yr);
			req.setAttribute("month",month);
			req.setAttribute("empNo",req.getParameter("empNo"));
		    req.setAttribute("CreditPreview", service.LeaveCreditPreview(month,yr,req.getParameter("empNo")));
	
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside CreditPreview.htm "+UserId, e);
	       }
	   return "leave/LeaveCreditPreview";

	}
	
	@RequestMapping(value = "LeaveCredited.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveCredited(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveCredited.htm "+UserId);		
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
			 logger.error(new Date() +" Inside LeaveCredited.htm "+UserId, e);
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
			 ses.setAttribute("SidebarActive", "LeaveApply_htm");
			 String EmpNo=req.getParameter("EmpNo");
			 if(EmpNo==null) {
				 EmpNo=(String)ses.getAttribute("EmpNo");
			 }
			 service.laeveNotModified(EmpNo);
			 req.setAttribute("EmpNo",EmpNo);
			 req.setAttribute("EmpList", service.EmployeeList());
			 req.setAttribute("empdetails", service.EmpDetails(EmpNo));
			 req.setAttribute("officerdetails", service.OfficerDetails(EmpNo));
			 req.setAttribute("leaveType", service.LeaveCode(EmpNo));
			 req.setAttribute("purposeList", service.purposeList());
			 req.setAttribute("register", service.getRegister(EmpNo,sdf.getCurrentYear()));
			 req.setAttribute("applied", service.getAppliedLeave(EmpNo));
			 req.setAttribute("sanction", service.getSanctionedLeave(EmpNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveApply.htm "+UserId, e);
	       }
	   return "leave/LeaveApply";

	}
	
	@RequestMapping(value = "AddUpdateCredit.htm", method = {RequestMethod.POST})
	public String AddUpdateCredit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside AddUpdateCredit.htm "+UserId);		
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
			 logger.error(new Date() +" Inside AddUpdateCredit.htm "+UserId, e);
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
			 service.laeveNotModified(EmpNo);
			 req.setAttribute("EmpNo",EmpNo);
			 req.setAttribute("EmpList", service.EmployeeList());
			 req.setAttribute("empdetails", service.EmpDetails(EmpNo));
			 req.setAttribute("officerdetails", service.OfficerDetails(EmpNo));
			 req.setAttribute("leaveType", service.LeaveCode(EmpNo));
			 req.setAttribute("purposeList", service.purposeList());
			 req.setAttribute("register", service.getRegister(EmpNo,sdf.getCurrentYear()));
			 req.setAttribute("applied", service.getAppliedLeave(EmpNo));
			 req.setAttribute("sanction", service.getSanctionedLeave(EmpNo));

	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside applyLeaveRedirect.htm "+UserId, e);
	       }
	   return "leave/LeaveApply";

	}

	@RequestMapping(value = "LeaveRegister.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveRegister(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveRegister.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "LeaveRegister_htm");
			String empNo=req.getParameter("empNo");
			String yr=req.getParameter("Year");
			if(yr==null) {
			yr=sdf.getCurrentYear();
			empNo=(String)ses.getAttribute("EmpNo");
			}
			
			service.laeveNotModified(empNo);
			req.setAttribute("register", service.RegisterOpening(empNo,yr));
		    req.setAttribute("RegisterList", service.LeaveRegisterList(empNo,yr));
		    req.setAttribute("EmpList", service.EmpList());
		    req.setAttribute("empNo", empNo);
		    req.setAttribute("year", yr);
		    req.setAttribute("empDetails", service.getEmployee(empNo));
		    
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveRegister.htm "+UserId, e);
	       }
	   return "leave/LeaveRegister";

	}
	
	@RequestMapping(value = "LeaveApproval.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveApproval(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveApproval.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "LeaveApproval_htm");
			String empNo=empNo=(String)ses.getAttribute("EmpNo");
		    req.setAttribute("GhApprovalList", service.LeaveApprovalGh(empNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveApproval.htm "+UserId, e);
	       }
	   return "leave/LeaveApprovalGh";

	}
	
	@RequestMapping(value = "/leaveprint.htm")
	public String leavePrint(HttpServletRequest req) {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside leavePrint.htm"+UserId);
		String LeaveApplId = req.getParameter("applId");
		try {
		req.setAttribute("print", service.LeavePrint(LeaveApplId));
		req.setAttribute("trans", service.LeaveTransaction(LeaveApplId));
		req.setAttribute("Lab", service.getLabCode());
		}
	     catch (Exception e) {
			 logger.error(new Date() +" Inside leavePrint.htm "+UserId, e);
	       }
	return "leave/LeavePrint";	
	}
	
	@RequestMapping(value = "/leaveApprovals.htm")
	public String leaveApprovals(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside leaveApprovals.htm "+UserId);
		 ApprovalDto dto=new ApprovalDto();
		 String returnType=null;
		try {
        dto.setApprove( req.getParameterValues("approve"));
        dto.setReject( req.getParameterValues("reject"));
        dto.setEmpNo((String)ses.getAttribute("EmpNo"));
        dto.setType(req.getParameter("Type"));
		dto.setUserName(UserId);
		int result=service.getApproved(dto,req);
		if(result>0) {
			redir.addAttribute("result","Leave Transaction Successfull");
		}else{
			redir.addAttribute("resultfail","Leave Transaction Unsuccessful");
		}
		}
	     catch (Exception e) {
			 logger.error(new Date() +" Inside leaveApprovals.htm "+UserId, e);
	       }
		if(dto.getType().equals("GH")) {
			returnType="redirect:/LeaveApproval.htm";
		}else if(dto.getType().equals("DIR")) {
			returnType="redirect:/LeaveApprovalDir.htm";
		}else if(dto.getType().equals("ADM")) {
			returnType="redirect:/LeaveApprovalAdm.htm";
		}
		return returnType;
		}
	
	@RequestMapping(value = "/delete-leave.htm", method = RequestMethod.POST)
	public String leaveDelete(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside delete-leave.htm "+UserId);
		try {
		ApprovalDto dto=new ApprovalDto();
        dto.setApplId(req.getParameter("appl_id"));
        dto.setEmpNo(req.getParameter("EmpNo"));
		dto.setUserName(UserId);
		int result = service.deleteLeave(dto);
		redir.addFlashAttribute("EmpNo",dto.getEmpNo());
		if(result>0) {
			redir.addAttribute("result","Leave Deleted Successfull");
		}else{
			redir.addAttribute("resultfail","Leave Delete Unsuccessful");
		}
		}
	     catch (Exception e) {
			 logger.error(new Date() +" Inside delete-leave.htm"+UserId, e);
	       }
		return "redirect:/applyLeaveRedirect.htm";
	}
	
	
	@RequestMapping(value = "/edit-leave.htm", method = RequestMethod.POST)
	public String leaveEdit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside edit-leave.htm "+UserId);
		try {
			String EmpNo=req.getParameter("EmpNo");
			String applid = req.getParameter("appl_id");
			String type=req.getParameter("Type");
			req.setAttribute("EmpNo",EmpNo);
			Object[] obj=service.getLeaveData(applid);
			 if(obj[8].toString().equals("LAU")&&"LEU".equals(type)) {
                   if("cancel".equals(req.getParameter("act"))) {
                	   ApprovalDto dto=new ApprovalDto();
                       dto.setApplId(applid);
                       dto.setEmpNo(EmpNo);
               		   dto.setUserName(UserId);
               	    int	result= service.getCancelLeave(dto);
             		redir.addFlashAttribute("EmpNo",dto.getEmpNo());
            		if(result>0) {
            			redir.addAttribute("result","Leave Cancelled Successfull");
            		}else{
            			redir.addAttribute("resultfail","Leave Cancel Unsuccessful");
            		}
				    }else {
					 ApprovalDto dto=new ApprovalDto();
					 dto.setApplId(applid);
					 dto.setStatus(req.getParameter("Type"));
					 service.getUpdateAppl(dto);
					 service.getUpdateRegister(dto);
				   }
			 }else if("LME".equals(type)&&(obj[8].toString().equals("LSO")||obj[8].toString().equals("LDO"))) {
				 if("cancel".equals(req.getParameter("act"))) {
					    ApprovalDto dto=new ApprovalDto();
					       dto.setApplId(applid);
	                       dto.setEmpNo(EmpNo);
	               		   dto.setUserName(UserId);
	               		int	result= service.getCancelLeave(dto);
	             		redir.addFlashAttribute("EmpNo",dto.getEmpNo());
	            		if(result>0) {
	            			redir.addAttribute("result","Leave Cancelled Successfull");
	            		}else{
	            			redir.addAttribute("resultfail","Leave Cancel Unsuccessful");
	            		}
				 }else {
					 ApprovalDto dto=new ApprovalDto();
					 dto.setApplId(applid);
					 dto.setStatus(req.getParameter("Type"));
					 service.getUpdateAppl(dto);
					 service.getUpdateRegister(dto);
				 }
			 }else {
				 redir.addFlashAttribute("EmpNo",EmpNo);
				 redir.addAttribute("resultfail","Leave Modification Not Allowed");
				 return "redirect:/applyLeaveRedirect.htm";
			 }
			 req.setAttribute("type",type);
			 req.setAttribute("leavedetails",obj);
			 req.setAttribute("EmpList", service.EmployeeList());
			 req.setAttribute("empdetails", service.EmpDetails(EmpNo));
			 req.setAttribute("officerdetails", service.OfficerDetails(EmpNo));
			 req.setAttribute("leaveType", service.LeaveCode(EmpNo));
			 req.setAttribute("purposeList", service.purposeList());
			 req.setAttribute("register", service.getRegister(EmpNo,sdf.getCurrentYear()));


		}
	     catch (Exception e) {
			 logger.error(new Date() +" Inside edit-leave.htm "+UserId, e);
	       }
		return "leave/LeaveEdit";
	}

	
	@RequestMapping(value = "/edited-leave.htm", method = RequestMethod.POST)
	public String leaveEdited(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside edited-leave.htm "+UserId);
		try {
	    LeaveApplyDto dto=new LeaveApplyDto();
	    dto.setType(req.getParameter("type"));
        dto.setApplId(req.getParameter("appl_id"));
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
		service.deleteLeaveRegiHo(dto.getApplId());
		String[] Result = service.applyLeaveAdd(dto);
		redir.addFlashAttribute("EmpNo",dto.getEmpNo());
		if("Pass".equals(Result[1])) {
			if(dto.getType().equals("LEU")) {
			redir.addAttribute("result","Leave Updated Successfully");
			}else if(dto.getType().equals("LME")) {
			redir.addAttribute("result","Leave Modified Successfully");
			}
     	}else if("Fail".equals(Result[1])) {
			redir.addAttribute("resultfail",Result[0]);
		}
		}
	     catch (Exception e) {
			 logger.error(new Date() +" Inside edited-leave.htm "+UserId, e);
	       }
		return "redirect:/applyLeaveRedirect.htm";
	}
	
	@RequestMapping(value = "LeaveStatusList.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveStatusList(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveStatusList.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "LeaveStatusList_htm");
			String empNo=empNo=(String)ses.getAttribute("EmpNo");
		    req.setAttribute("StatusList", service.LeaveStatusList(empNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveStatusList.htm "+UserId, e);
	       }
	   return "leave/LeaveStatusList";

	}
	
	@RequestMapping(value = "/leavestatus.htm")
	public String leaveStatus(HttpServletRequest req) {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside leavestatus.htm "+UserId);
		String LeaveApplId = req.getParameter("applId");
		try {
		req.setAttribute("print", service.LeavePrint(LeaveApplId));
		req.setAttribute("trans", service.LeaveTransaction(LeaveApplId));
		}
	     catch (Exception e) {
			 logger.error(new Date() +" Inside leavestatus.htm "+UserId, e);
	       }
	return "leave/LeaveStatus";	
	}
	
	@RequestMapping(value = "LeaveApprovalAdm.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveApprovalAdm(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveApprovalAdm.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "LeaveApprovalAdm_htm");
			String empNo=empNo=(String)ses.getAttribute("EmpNo");
		    req.setAttribute("LeaveApprovalAdm", service.LeaveApprovalAdm(empNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveApprovalAdm.htm "+UserId, e);
	       }
	   return "leave/LeaveApprovalAdm";

	}
	
	@RequestMapping(value = "LeaveApprovalDir.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String LeaveApprovalDir(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside LeaveApprovalDir.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "LeaveApprovalDir_htm");
			String empNo=empNo=(String)ses.getAttribute("EmpNo");
		    req.setAttribute("LeaveApprovalDir", service.LeaveApprovalDir(empNo));
		    req.setAttribute("LeaveApprovalDirRecc", service.LeaveApprovalDirRecc(empNo));
		    req.setAttribute("LeaveApprovalDirNR", service.LeaveApprovalDirNR(empNo));
	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside LeaveApprovalDir.htm "+UserId, e);
	       }
	   return "leave/LeaveApprovalDir";

	}	
	
	@RequestMapping(value = "AssignReccSanc.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String AssignReccSanc(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside AssignReccSanc.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "AssignReccSanc_htm");
			req.setAttribute("AllRaSaAssignEmployee", service.AssignReccSanc());

	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside AssignReccSanc.htm "+UserId, e);
	       }
	   return "leave/AssignReccSanc";

	}
	
	@RequestMapping(value = "assign-recc-sanc.htm", method = RequestMethod.POST)
	
	public String assignReccSancAddEdit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside assign-recc-sanc.htm "+UserId);
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String returnPage="leave/AssignReccSancAddEdit";
		String empid = req.getParameter("selecRadioForEmpid");
			if (req.getParameter("Add") != null) {
				empid =EmpNo;
				
			}else if (req.getParameter("add") != null) {

             LeaveRaSa saRa=new LeaveRaSa();
             saRa.setTD_STATUS(req.getParameter("TdStatus"));
             saRa.setLeave_Status(req.getParameter("LeaveStatus"));
             saRa.setRA(req.getParameter("RA1"));
             saRa.setRA2(req.getParameter("RA2"));
             saRa.setRA3(req.getParameter("RA3"));
             saRa.setSA(req.getParameter("SA"));
             saRa.setTD_RA(req.getParameter("TDRA"));
             saRa.setTD_SA(req.getParameter("TDSA"));
             saRa.setRANO(Integer.parseInt(req.getParameter("RANO")));
             saRa.setEMPID(empid);
             saRa.setCreatedBy(UserId);
             saRa.setCreatedDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
             if(!"0".equals(req.getParameter("saRaId"))) {
            	 saRa.setLeaveSaRaId(Long.parseLong(req.getParameter("saRaId")));
             }

 			
 			long result=service.saveRaSa(saRa);
 		    if (result>0) {
 				redir.addAttribute("result", "Assigned   Successfully");
 			} else {
 				redir.addAttribute("resultfail", "Assign  unsuccessful");
 			}
            returnPage="redirect:/AssignReccSanc.htm"; 

		}
	    req.setAttribute("RaSaStatus", service.getRaSaStatus());
	    req.setAttribute("ReccSancById", service.getReccSancById(empid));
		req.setAttribute("ReccSanc", service.getReccSanc(empid));
		req.setAttribute("empNo", empid);
		req.setAttribute("AllEmployee", service.EmpList());

		return returnPage;
	}
	
	@RequestMapping(value = "UploadMcFc.htm", method = {RequestMethod.GET})
	public String UploadMcFc(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside UploadMcFc.htm "+UserId);		
		try {

			String yr=req.getParameter("yr");
			if(yr==null) {
			yr=sdf.getCurrentYear();
			}
			ses.setAttribute("SidebarActive", "UploadMcFc_htm");
			req.setAttribute("UploadMcFc", service.UploadMcFc((String) ses.getAttribute("EmpNo"),yr));
			req.setAttribute("yr", yr);

	    }
	     catch (Exception e) {
			 logger.error(new Date() +" Inside UploadMcFc.htm "+UserId, e);
	       }
	   return "leave/UploadMcFc";

	}
	
	@RequestMapping(value = "UploadMcFc.htm", method = {RequestMethod.POST})
	public String UploadMcFcSubmit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestParam(name = "document" ,required = false) MultipartFile document) throws Exception {
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside UploadMcFc.htm "+UserId);		
        long count =0;	
		try {
						
			LeaveAttachmentDto dto = new LeaveAttachmentDto();
			dto.setApplId(req.getParameter("applid"));
			dto.setMcFc(req.getParameter("mcFc"));
			dto.setCreatedBy(req.getUserPrincipal().getName());
			dto.setCreatedDate(sdf.getSqlDateAndTimeFormat().format(new Date()));
		    dto.setFile(document);
			
			
			count=service.McFcAttachmentFile(dto);

		}catch(Exception e) {
			logger.error(new Date() +" Inside UploadMcFc.htm "+UserId, e);
			e.printStackTrace();
		}
		
		if(count>0) 
		{
			redir.addAttribute("result","Document Added Successfully");
		}
		else
		{
			redir.addAttribute("resultfail","Document Add Unsuccessful");
		}
	   return "redirect:/UploadMcFc.htm";

	}
	@RequestMapping(value = {"downloadMcFc.htm"})
	public void downloadMcFc(HttpServletRequest req, HttpSession ses, HttpServletResponse res)throws Exception 
	{
		logger.info(new Date() +"Inside downloadMcFc "+ req.getUserPrincipal().getName());
		try
		{
			String path= emsfilespath+"EMS\\MC_FC\\";
		    String mcFc="MC";
			String applid=req.getParameter("MC");
			if(applid==null) {
				applid=req.getParameter("FC");
				mcFc="FC";
			}
			res.setContentType("Application/octet-stream");	
			LeaveMC_FC mcFcdto=service.getMcFc(applid);
			File my_file=null;
		
			my_file = new File(path+File.separator+("MC".equals(mcFc)?mcFcdto.getMC_file():mcFcdto.getFC_file())); 
	        res.setHeader("Content-disposition","attachment; filename="+("MC".equals(mcFc)?mcFcdto.getMC_file():mcFcdto.getFC_file())); 
	        OutputStream out = res.getOutputStream();
	        FileInputStream in = new FileInputStream(my_file);
	        byte[] buffer = new byte[4096];
	        int length;
	        while ((length = in.read(buffer)) > 0){
	           out.write(buffer, 0, length);
	        }
	        in.close();
	        out.flush();
	        out.close();	
		}catch (Exception e) {
				e.printStackTrace(); 
				logger.error(new Date() +"Inside downloadMcFc "+ req.getUserPrincipal().getName(),e);
		}
	}
}

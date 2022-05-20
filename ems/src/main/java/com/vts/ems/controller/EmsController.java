package com.vts.ems.controller;

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
import com.vts.ems.login.Login;
import com.vts.ems.login.LoginRepository;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.service.EMSMainService;

@Controller
public class EmsController {
	private static final Logger logger = LogManager.getLogger(EmsController.class);

	@Autowired
	LoginRepository Repository;

	@Autowired
	EMSMainService service;

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model, HttpServletRequest req, HttpSession ses) throws Exception {
		logger.info(new Date() + " Login By " + req.getUserPrincipal().getName());
		try {
			Login login = Repository.findByUsername(req.getUserPrincipal().getName());
			
			ses.setAttribute("LoginId", login.getLoginId());
			ses.setAttribute("Username", req.getUserPrincipal().getName());
			ses.setAttribute("LoginType", login.getLoginType());
			ses.setAttribute("EmpId", login.getEmpId());
			ses.setAttribute("FormRole", login.getFormRoleId());

			Employee employee = service.EmployeeInfo(login.getEmpId());
			ses.setAttribute("EmpNo", employee.getEmpNo());
			ses.setAttribute("EmpName", employee.getEmpName());
			ses.setAttribute("emplogintypelist",service.EmpHandOverLoginTypeList(String.valueOf(employee.getEmpId())));
			
			
		} catch (Exception e) {
			logger.error(new Date() + " Login Issue Occures When Login By " + req.getUserPrincipal().getName(), e);
		}

		return "redirect:/MainDashBoard.htm";
	}

	@RequestMapping(value = "MainDashBoard.htm", method = RequestMethod.GET)
	public String MainDashBoard(HttpServletRequest req, HttpSession ses) throws Exception {
		logger.info(new Date() + "Inside MainDashBoard.htm ");
//    	String LoginType=(String)ses.getAttribute("LoginType");
//    	String LoginId=String.valueOf(ses.getAttribute("LoginId"));
//    	String EmpNo=(String)ses.getAttribute("EmpNo"); 

//    	req.setAttribute("employeedata", service.EmployeeData(EmpId));

		return "static/maindashboard";
	}
	
	@RequestMapping(value = "EmpLogitypeChange.htm" , method = RequestMethod.POST)
	public String EmpLogitypeChange(HttpServletRequest req ,HttpSession ses) throws Exception {		
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmpLogitypeChange.htm "+UserId);		
		try {
			String logintype= req.getParameter("logintype");
			ses.setAttribute("LoginType", logintype);
			return "redirect:/MainDashBoard.htm";
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EmpLogitypeChange.htm "+UserId, e);
			return "redirect:/MainDashBoard.htm";
		}
		
	}
	
	
	
	@RequestMapping(value = "NotificationList.htm" , method = RequestMethod.GET)
	public @ResponseBody String NotificationList(HttpServletRequest request ,HttpSession ses) throws Exception 
	{
			
		List<EMSNotification> notificationlist= new ArrayList<EMSNotification>();
		 
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NotificationList.htm "+UserId);		
		try {
		
			Long EmpId= ((Long) ses.getAttribute("EmpId"));
			
		    notificationlist = service.NotificationList(EmpId);
		    
		    
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NotificationList.htm "+UserId, e);
		}
		 
		Gson json = new Gson();  
		return json.toJson(notificationlist);
		
		
	}
	
	
	@RequestMapping(value = "NotificationUpdate.htm" , method = RequestMethod.GET)
	public @ResponseBody String NotificationUpdate(HttpServletRequest request ,HttpSession ses) throws Exception {
		Gson json = new Gson();
		int count=0;
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NotificationUpdate.htm "+UserId);		
		try {
			String NotificationId=request.getParameter("notificationid");
			
			count= service.NotificationUpdate(NotificationId);
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NotificationUpdate.htm "+UserId, e);
		}
			return json.toJson(count);
			
		
	}
	
  
	 @RequestMapping(value = "fpwd/ForgotPassword.htm", method = {RequestMethod.POST,RequestMethod.GET}) 
	 public String forgotPassword(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside fpwd/ForgotPassword.htm ");
		try 
		{
			String username=req.getParameter("username");
			
			if (username == null) {
				Map md = model.asMap();
				username = (String) md.get("username");
			}
			
			if(username==null) {
				return "redirect:/login"; 
			}
			
			Object[] userinfo = service.LoginExistCheck(username);
			
			if(userinfo==null) 
			{
				redir.addFlashAttribute("error","Login for this Username not found, Please Contact Admin.");
				return "redirect:/login";
			}
			
			if(userinfo[4]==null || userinfo[4].toString().trim().equals("") )
			{
				redir.addFlashAttribute("error","Email id not found, Please Contact Admin.");
				return "redirect:/login";
			}
			
			String otp =service.getPasswordResetOTP(userinfo[0].toString());
			
			req.setAttribute("otp", otp);
	    	req.setAttribute("userinfo", userinfo);
	    	return "static/ForgotPassword";
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
		}
	
	    
	 }

	
	 @RequestMapping(value = "fpwd/ResetPassword.htm", method = RequestMethod.POST) 
	 public String resetPassword(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside fpwd/ResetPassword.htm ");
		try 
		{
			String loginid=req.getParameter("loginid");
			String NewPassword=req.getParameter("NewPassword");
			
			int count =service.userResetPassword(loginid, NewPassword);
			
			if(count>0) {
				redir.addFlashAttribute("success","Password reset Successfull.");
				return "redirect:/login";
			}
			
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
		}
	
	    
	 }
	 
	 @RequestMapping(value = "fpwd/ResendOTP.htm", method = RequestMethod.POST) 
	 public String ResendOTP(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside fpwd/ResendOTP.htm ");
		try 
		{
			String loginid=req.getParameter("loginid");
			String username=req.getParameter("username");
			
			service.reSendResetOTP(loginid);
			
			redir.addAttribute("username", username);
			return "redirect:/fpwd/ForgotPassword.htm";
		} catch (Exception e) {
			e.printStackTrace();
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
		}
	
	    
	 }
	 


	
	

}

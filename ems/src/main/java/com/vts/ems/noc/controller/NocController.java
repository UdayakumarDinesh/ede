package com.vts.ems.noc.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.noc.service.NocService;


@Controller
public class NocController {
	private static final Logger logger = LogManager.getLogger(NocController.class);
	
	@Autowired
	AdminService adminservice;
	
	@Autowired
	NocService service;
	
	@RequestMapping(value = "NOC.htm")
	public String NOC(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
	    ses.setAttribute("formmoduleid", "34"); 
	    ses.setAttribute("SidebarActive", "NOC.htm");
		String UserId=(String)ses.getAttribute("Username");
		String LoginType=(String)ses.getAttribute("LoginType");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		
		logger.info(new Date()+"Inside NOC.htm "+UserId);
		try {
			
			 List<Object[]> admindashboard = adminservice.HeaderSchedulesList("12" ,LoginType);
			 return "noc/noc";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside NOC.htm "+UserId, e); 
		    return "static/Error"; 
		}
}

	@RequestMapping(value = "Passport.htm")
	public String Passport(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
		ses.setAttribute("formmoduleid", "34");  
	    ses.setAttribute("SidebarActive", "Passport_htm");
		String UserId=(String)ses.getAttribute("Username");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		
		logger.info(new Date()+"Inside Passport.htm "+UserId);
		try {
			
			return "noc/passport";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside Passport.htm "+UserId, e); 
		    return "static/Error"; 
		}
   }
	
	@RequestMapping(value = "PassportAdd.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String PassportAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		
	    String UserId=(String)ses.getAttribute("Username");
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date()+"Inside PassportAdd.htm "+UserId);
		try {
			
			  req.setAttribute("NocEmpList", service.getNocEmpList(EmpNo));
			  return "noc/passportadd";
					 
		} catch (Exception e) 
		 { 
			e.printStackTrace(); 
		    logger.error(new Date()+" Inside PassportAdd.htm "+UserId, e); 
		    return "static/Error"; 
		}
   }
	
}

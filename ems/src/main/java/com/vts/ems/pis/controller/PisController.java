package com.vts.ems.pis.controller;

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

import com.vts.ems.pis.service.PisService;

@Controller
public class PisController {

	private static final Logger logger = LogManager.getLogger(PisController.class);
			
	@Autowired
	private PisService service;
	
	@RequestMapping(value = "PisAdminDashboard.htm", method = RequestMethod.GET)
	public String PisDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside CommitteeAddSubmit.htm "+Username);		
		try {
			
			
			
			return "pis/PisDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside CommitteeAddSubmit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	
	
	
	@RequestMapping(value = "PisAdminEmpList.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String PisAdminEmpList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside PisAdminEmpList.htm "+Username);		
		try {
			List<Object[]> EmployeeDetailsList =null;
			if(LoginType.equalsIgnoreCase("A")) 
			{
				EmployeeDetailsList = service.EmployeeDetailsList(LoginType, EmpId);
			}			
			req.setAttribute("EmployeeDetailsList", EmployeeDetailsList);
			return "pis/EmployeeList";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminEmpList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	@RequestMapping(value = "EmployeeDetails.htm", method = RequestMethod.POST)
	public String EmployeeDetails(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PisAdminEmpList.htm "+Username);		
		try {
			String empid=req.getParameter("empid");
			Object[] employeedetails = service.EmployeeDetails(empid);
			
			req.setAttribute("empid", empid);
			req.setAttribute("employeedetails", employeedetails);
			
			return "pis/EmpBasicDetails";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminEmpList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	@RequestMapping(value = "EmployeeAdd.htm")
	public String EmployeeAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PisAdminEmpList.htm "+Username);		
		try {
			
			req.setAttribute("desiglist", service.DesigList());
			
			req.setAttribute("piscategorylist", service.PisCategoryList());
			req.setAttribute("piscatclasslist", service.PisCatClassList());
			req.setAttribute("piscaderlist", service.PisCaderList());
			req.setAttribute("empstatuslist", service.EmpStatusList());
			req.setAttribute("paylevellist", service.PayLevelList());	
			
			req.setAttribute("divisionlist", service.DivisionList());			
			return "pis/EmployeeAdd";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminEmpList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
}

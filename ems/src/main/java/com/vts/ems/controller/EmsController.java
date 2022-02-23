package com.vts.ems.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vts.ems.login.Login;
import com.vts.ems.login.LoginRepository;
import com.vts.ems.modal.Employee;
import com.vts.ems.service.EMSMainService;

@Controller
public class EmsController 
{	
	private static final Logger logger=LogManager.getLogger(EmsController.class);
	
	@Autowired
	LoginRepository Repository;
	
	@Autowired
	EMSMainService service;
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model,HttpServletRequest req,HttpSession ses) throws Exception 
    {    	
	    logger.info(new Date() +" Login By "+req.getUserPrincipal().getName());
	    try 
	    {     	
		  Login login=Repository.findByUsername(req.getUserPrincipal().getName());
		  
		  ses.setAttribute("Username",req.getUserPrincipal().getName());
		  ses.setAttribute("LoginId",login.getLoginId() ); 
		  ses.setAttribute("LoginType", login.getLoginType()); 
		  ses.setAttribute("EmpId", login.getEmpId()); 
		  ses.setAttribute("FormRole", login.getFormRoleId());
		    
		  Employee employee= service.EmployeeInfo(login.getEmpId());
		  ses.setAttribute("EmpNo", employee.getEmpNo());	    
		    
	    }catch (Exception e) {
	    	logger.error(new Date() +" Login Problem Occures When Login By "+req.getUserPrincipal().getName(), e);
	    }
     
	    return "redirect:/MainDashBoard.htm";
    }
    
    
    @RequestMapping(value = "MainDashBoard.htm", method = RequestMethod.GET)
    public String MainDashBoard(HttpServletRequest req,HttpSession ses) throws Exception {
    	logger.info(new Date() +"Inside MainDashBoard.htm ");
    	String EmpId= ((Long) ses.getAttribute("EmpId")).toString();
//    	String LoginType=(String)ses.getAttribute("LoginType");
//    	String LoginId=String.valueOf(ses.getAttribute("LoginId"));
//    	String EmpNo=(String)ses.getAttribute("EmpNo"); 
    	
//    	req.setAttribute("employeedata", service.EmployeeData(EmpId));
	      
     return "static/maindashboard";
    }
 
}

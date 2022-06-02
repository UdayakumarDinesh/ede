package com.vts.ems.login;

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

import com.vts.ems.service.EMSMainService;

@Controller
public class LoginController 
{

	private static final Logger logger=LogManager.getLogger(LoginController.class);
	@Autowired
	LoginRepository Repository;
    
	@Autowired
	EMSMainService service;

	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public String login(Model model, String error, String logout, HttpServletRequest req,HttpSession ses,HttpServletResponse response) 
	 {	 
		logger.info(new Date() +"Inside login ");
	    if (error != null) 
	        model.addAttribute("error", "Your username or password is invalid.");

	    if (logout != null)
	        model.addAttribute("message", "You have been logged out successfully.");
	    
	    
//	    error= req.getParameter("error");
//		if (error == null) {
//			Map md = model.asMap();
//			error = (String) md.get("error");
//		}
//	    if(error!=null) {
//	    	 model.addAttribute("error", error);
//	    }
//	    
	    
	    List<Object[]> circulatlist = new ArrayList<Object[]>();
		   	 try {
		   		 circulatlist = service.circulatlist();
			} catch (Exception e) {
				e.printStackTrace();
			}
		req.setAttribute("circularlist", circulatlist);
	    
	    String success= req.getParameter("success");
		if (success == null) {
			Map md = model.asMap();
			success = (String) md.get("success");
		}
	    if(success!=null) {
	    	 model.addAttribute("success", success);
	    }
	    
 	    return "static/login";
	 }
	 

    @RequestMapping(value = {"/sessionExpired","/invalidSession"}, method = RequestMethod.GET)
    public String sessionExpired(Model model,HttpServletRequest req,HttpSession ses) {
    	logger.info(new Date() +"Inside sessionExpired ");
    	try {
      	}
      	catch (Exception e) {
				e.printStackTrace();
			}
    	
        return "SessionExp";
    }
    
    @RequestMapping(value = {"/accessdenied"}, method = RequestMethod.GET)
    public String accessdenied(Model model,HttpServletRequest req,HttpSession ses) {
    	
        return "accessdenied";
    }

}

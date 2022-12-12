package com.vts.ems.login;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;

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
	 public String login(Model model, @RequestParam(value = "error", required=false) String error,
			 						@RequestParam(value = "logout", required=false) String logout, 
			 						@RequestParam(value = "session", required=false) String session,
			 						HttpServletRequest req,HttpSession ses,HttpServletResponse response) 
	 {	 
		logger.info(new Date() +"Inside login ");
	
		if (error != null && error.equalsIgnoreCase("1")) 
		{
	        model.addAttribute("error", "Your username or password is invalid.");
	    }
		
	    if (logout != null && logout.equalsIgnoreCase("1")) 
	    {
	        model.addAttribute("message", "You have been logged out successfully.");
	    }
	    
	    if (logout != null && logout.equalsIgnoreCase("1")) 
	    {
	        model.addAttribute("message", "You have been logged out successfully.");
	    }
	    
	    String success= req.getParameter("success");
		if (success == null) {
			Map md = model.asMap();
			success = (String) md.get("success");
		}
	    if(success!=null) {
	    	 model.addAttribute("success", success);
	    }
	    
	    try {
	    	req.setAttribute("alerts-marquee", service.getCircularOrdersNotice());
	    }
	    catch (Exception e) {
	    	req.setAttribute("alerts-marquee", new ArrayList<Object[]>());
	    }
 	    return "static/login";
	 }
	 

    @RequestMapping(value = {"/sessionExpired","/invalidSession"}, method = RequestMethod.GET)
    public String sessionExpired(Model model,HttpServletRequest req,HttpSession ses) {
    	logger.info(new Date() +"Inside sessionExpired ");
        return "SessionExp";
    }
    
    @RequestMapping(value = {"/accessdenied"}, method = RequestMethod.GET)
    public String accessdenied(Model model,HttpServletRequest req,HttpSession ses) {
    	
        return "accessdenied";
    }

}

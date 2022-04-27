package com.vts.ems.login;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	 public String login(Model model, String error, String logout, String fpwd,HttpServletRequest req,HttpSession ses,HttpServletResponse response) 
	 {	 
		logger.info(new Date() +"Inside login ");
	    if (error != null) 
	        model.addAttribute("error", "Your username or password is invalid.");

	    if (logout != null)
	        model.addAttribute("message", "You have been logged out successfully.");
	    
	    fpwd = req.getParameter("fpwd");
		if (fpwd == null) {
			Map md = model.asMap();
			fpwd = (String) md.get("fpwd");
		}
	    if(fpwd!=null) {
	    	 model.addAttribute("error", fpwd);
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
    
    
	 @RequestMapping(value = "fpwd/ForgotPassword.htm", method = RequestMethod.POST) 
	 public String forgotPassword(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside forgotPassword ");
		try {
			String username=req.getParameter("username");
			Object[] userinfo = service.LoginExistCheck(username);
			if(userinfo==null) 
			{
				redir.addFlashAttribute("fpwd","1");
				return "redirect:/login";
			}
			
	    	req.setAttribute("username", username);
	    	return "static/ForgotPassword";
		} catch (Exception e) {
			e.printStackTrace();
			redir.addFlashAttribute("fpwd","Login for this Username not found.");
			return "redirect:/login";
		}
	
	    
	 }

   
   

}

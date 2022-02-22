package com.vts.ems.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@Autowired
	LoginRepository Repository;

	@Autowired
    private LoginDetailsServiceImpl loginService;

    @Autowired
    private SecurityServiceImpl securityService;
   
	
    

	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String login(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response) {
	 
		 ////logger.info(new Date() +"Inside login ");
	    	if (error != null) 
	            model.addAttribute("error", "Your username and password is invalid.");

	    	 if (logout != null)
	             model.addAttribute("message", "You have been logged out successfully.");
	    	
	        return "static/login";
	    }

    
  
    
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model,HttpServletRequest req,HttpSession ses) throws Exception {
    	
      //logger.info(new Date() +" Login By "+req.getUserPrincipal().getName());
   
      try 
      {     	
	    Login login=Repository.findByUsername(req.getUserPrincipal().getName());
	    
	    ses.setAttribute("Username",req.getUserPrincipal().getName());
	    ses.setAttribute("LoginId",login.getLoginId() ); 
	    ses.setAttribute("LoginType", login.getLoginType()); 
	    ses.setAttribute("EmpId", login.getEmpId()); 
	    ses.setAttribute("FormRole", login.getFormRoleId());
	   
      }catch (Exception e) {
    	
    	 //logger.error(new Date() +" Login Problem Occures When Login By "+req.getUserPrincipal().getName(), e);
     }
     
      return "redirect:/MainDashBoard.htm";
    }
    
    
    @RequestMapping(value = "MainDashBoard.htm", method = RequestMethod.GET)
    public String MainDashBoard(HttpServletRequest req,HttpSession ses) throws Exception {
   
    	String EmpId= ((Long) ses.getAttribute("EmpId")).toString();
    	String LoginType=(String)ses.getAttribute("LoginType");
    	String LoginId=String.valueOf(ses.getAttribute("LoginId"));
    	String empNo=(String)ses.getAttribute("empNo"); 
    	
    	//logger.info(new Date() +"Inside MainDashBoard.htm ");
	      
     return "static/maindashboard";
    }
 
 
    
    
    
    @RequestMapping(value = {"/sessionExpired","/invalidSession"}, method = RequestMethod.GET)
    public String sessionExpired(Model model,HttpServletRequest req,HttpSession ses) {
    	//logger.info(new Date() +"Inside sessionExpired ");
    	try {
      	  String LogId = ((Long) ses.getAttribute("LoginId")).toString();
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

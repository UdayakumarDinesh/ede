package com.vts.ems.login;

import java.io.File;
import java.net.InetAddress;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vts.ems.model.AuditStamping;
import com.vts.ems.service.EMSMainService;

@Controller
public class LoginController 
{

	private static final Logger logger=LogManager.getLogger(LoginController.class);
	@Autowired
	LoginRepository Repository;
    
	@Autowired
	EMSMainService service;
	
	@Value("${ProjectFiles}")
	private String projectfilespath;


	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	 public String login(Model model, String error, String logout, HttpServletRequest req,HttpSession ses,HttpServletResponse response) 
	 {	 
		logger.info(new Date() +"Inside login ");
		
		System.out.println(error);
		
		if (error != null) {
	        model.addAttribute("error", "Your username or password is invalid.");
	        
	        return "static/login";
	    }
	    if (logout != null) {
	        model.addAttribute("message", "You have been logged out successfully.");
	    }
	    
//	    error= req.getParameter("error");
//		if (error == null) {
//			Map md = model.asMap();
//			error = (String) md.get("error");
//		}
//	    if(error!=null) {
//	    	 model.addAttribute("error", error);
//	    }
//	    
	    
	     
		   	 try {
		   		List<Object[]> Empanelled  = service.GetEmpanelledHostpitalList();
	        	req.setAttribute("Empanelled",Empanelled);
	        	
		   		 List<Object[]> doctorlist  = service.GetDoctorList();
	        	 req.setAttribute("doctorlist",doctorlist);
	        	 
	   			List<Object[]> 	 circulatlist = service.CirculatList();
	   			req.setAttribute("circularlist", circulatlist);
	   			List<Object[]> 	circular = service.GetCircularList();
	   			req.setAttribute("circular", circular);
	   			String path = projectfilespath+ "/ProjectManuals/chss-policy.pdf";
				String chss_policy_pdf = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(path)));
				req.setAttribute("Messege", "hello");
				req.setAttribute("path", path);
				req.setAttribute("chss_policy_pdf", chss_policy_pdf);
	   			
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	    
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

package com.vts.ems.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;



public class CustomLogoutHandler  implements LogoutHandler  {


	
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		 HttpSession ses=request.getSession();
		 try {
       	  String LogId = ((Long) ses.getAttribute("LoginId")).toString();
       	  
       	}
       	catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	
}

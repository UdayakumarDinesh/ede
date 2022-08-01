package com.vts.ems.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.vts.ems.service.EMSMainService;

public class CustomLogoutHandler  implements LogoutHandler  {

	@Autowired
	EMSMainService EMSService;
	
	
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		 HttpSession ses=request.getSession();
		 try {
       	  String LogId = ((Long) ses.getAttribute("LoginId")).toString();
       	  EMSService.LoginStampingUpdate(LogId, "L");
       	}
       	catch (Exception e) {
				e.printStackTrace();
			}	
	}
	
	
}

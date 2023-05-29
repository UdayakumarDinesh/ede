package com.vts.ems.apf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vts.ems.apf.service.ApfServiceImpl;


@Controller
public class ApfController {
	
	@Autowired
	private ApfServiceImpl service;
	
	private static final Logger logger = LogManager.getLogger(ApfController.class);
	
	
	@RequestMapping("APF.htm")
	public String APFPAplyList(HttpServletRequest req) 
	{
		HttpSession ses = req.getSession(false);
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside APF.htm " + Username);
		
		try {
			
			
			ses.setAttribute("SidebarActive", "APF_htm");
			List<Object[]> AllAPFList = service.allAPFApply(EmpNo);
			req.setAttribute("AllAPFList", AllAPFList);
			req.setAttribute("empNameAndDesi", service.empNameAndDesi(EmpNo));

			return "APF/APFHome";
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + " Inside APF.htm " + Username, e);
			return "static/Error";
		}
	}
}

package com.vts.ems.dopart.controller;

import java.util.Calendar;
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

import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.dopart.service.DoPartService;

@Controller
public class DoController {

	@Autowired
	AdminService adminservice;
	
	@Autowired
	DoPartService service;
	
	
	private static final Logger logger = LogManager.getLogger(DoController.class);
	
	private int year = Calendar.getInstance().get(Calendar.YEAR);
	
	@RequestMapping(value = "DoDashboard.htm", method = RequestMethod.GET)
	public String DoDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside DoDashboard.htm "+Username);		
		try {
			
			String logintype = (String)ses.getAttribute("LoginType");
			List<Object[]> dodashboard = adminservice.HeaderSchedulesList("8" ,logintype); 
		
			ses.setAttribute("formmoduleid", "8"); 
			ses.setAttribute("SidebarActive", "DoDashboard_htm");
			req.setAttribute("dashboard", dodashboard);
		
			
			return "DoPart/DoDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside DoDashboard.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "DoHome.htm" ,method = RequestMethod.GET)
	public String DOHome(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside DoHome.htm "+Username);	
		try {
			
			String year2=Integer.toString(year);
			req.setAttribute("DoEditList", service.GetDOList());
			List<Object[]> dolist=service.GetDOList();
			if(dolist!=null&&dolist.size()!=0) {
			for(Object[] dop:dolist) {
				req.setAttribute("DoData",service.RetriveContent(Integer.parseInt(dop[2].toString()),year2));
			}}

			ses.setAttribute("SidebarActive", "DoHome_htm");
			return "DoPart/DoHome";
		} catch (Exception e) {
			logger.error(new Date() +" Inside DoHome.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
}

package com.vts.ems.Admin.Controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.chss.controller.CHSSController;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class AdminController {

	
private static final Logger logger = LogManager.getLogger(CHSSController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	
	@Autowired
	AdminService service;
	
	
	   @RequestMapping(value = "Role.htm" )
			public String RoleFormAccess(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)
					throws Exception {
				String UserId = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside LoginTypeList.htm "+UserId);		
				try {
			
					req.setAttribute("LoginTypeRoles",service.LoginTypeRoles());
					req.setAttribute("FormDetailsList", service.FormDetailsList(req.getParameter("logintype"),req.getParameter("moduleid")));
					req.setAttribute("FormModulesList", service.FormModulesList());
					req.setAttribute("logintype", req.getParameter("logintype"));
					req.setAttribute("moduleid", req.getParameter("moduleid"));
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +" Inside LoginTypeList.htm "+UserId, e);
				}	
				 return "Admin/RoleFormAccess";
			}
		@RequestMapping(value = "PisAdminDashboard.htm", method = RequestMethod.GET)
		public String PisDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside PisAdminDashboard.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				String formrole =(String)req.getParameter("formroleid");
			
				List<Object[]> admindashboard = service.HeaderSchedulesList(formrole ,logintype); 
			
				req.setAttribute("dashboard", admindashboard);
				return "pis/PisDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside PisAdminDashboard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		@RequestMapping(value = "HeaderModuleList.htm" , method = RequestMethod.GET)
		public @ResponseBody String HeaderModuleList(HttpServletRequest request ,HttpSession ses) throws Exception {
			
			Gson json = new Gson();
			List<Object[]> HeaderModuleList = null;
			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside HeaderModuleList.htm "+UserId);		
			try {
				String LoginType = ((String) ses.getAttribute("LoginType"));
				
				
			    HeaderModuleList =service.FormModuleList(LoginType);
			    request.setAttribute("ProjectInitiationList", "");    
			}
			catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside HeaderModuleList.htm "+UserId, e);
			}
				return json.toJson(HeaderModuleList);	
		}
		
	    @RequestMapping(value = "FormRoleActive.htm", method = RequestMethod.GET)
			public @ResponseBody String FormRoleActive(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)
					throws Exception 
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside FormRoleActive.htm "+UserId);
				try
				{
					String FormRoleAccessId = req.getParameter("formroleaccessid");
					System.out.println(FormRoleAccessId);
					
					service.FormRoleActive(FormRoleAccessId);
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +"Inside FormRoleActive.htm "+UserId,e);
				}

				return "admin/RoleFormAccess";
			}
		    
}

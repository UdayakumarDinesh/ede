package com.vts.ems.attendance.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.attendance.service.AttendanceService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class AttendanceController {
	
	private static final Logger logger = LogManager.getLogger(AttendanceController.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	@Autowired
	private AttendanceService service;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;

	@Autowired
    private Environment env;
	
	@RequestMapping("AttendanceSync.htm")
	public String attendancePunchDataSync(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER AttendanceSync.htm "+UserId);		
		try {
			
			long result = service.syncAttendancePunchData();
			
			if(result != 0){
    			redir.addAttribute("result", "Attendance Sync Successfull");
			}else{
				redir.addAttribute("resultfail", "Attendance Sync Unsuccessfull");
			}
			
			return "redirect:/MainDashBoard.htm";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER AttendanceSync.htm "+UserId, e);
			return "static/Error";
		}	
	}
	
	
}

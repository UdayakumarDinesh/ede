package com.vts.ems.attendance.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.attendance.service.AttendanceService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class AttendanceController {
	
	private static final Logger logger = LogManager.getLogger(AttendanceController.class);
	
	@Autowired
	private AttendanceService service;
	
//	@Value("${EMSFilesPath}")
//	private String emsfilespath;
//
//	@Autowired
//    private Environment env;
	
	SimpleDateFormat rf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@RequestMapping("AttendanceSync.htm")
	public String attendancePunchDataSync(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER AttendanceSync.htm "+UserId);		
		try {
			
			long result = service.syncAttendancePunchData();
			
			if(result != 0){
    			redir.addAttribute("result", "Attendance Sync Successfull for the date -"+DateTimeFormatUtil.SqlToRegularDate(LocalDate.now().minusDays(1).toString()));
			}else{
				redir.addAttribute("resultfail", "Attendance Sync Unsuccessfull");
			}
			
			return "redirect:/PisAdminDashboard.htm";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER AttendanceSync.htm "+UserId, e);
			return "static/Error";
		}	
	}
	
	
	
	@Scheduled(cron = "0 0 10 * * ?")
	public void attendancePunchDataSyncCronScheduler()throws Exception 
	{
		logger.info(new Date() +"Inside CONTROLLER attendancePunchDataSyncCronScheduler ");		
		try {
			service.syncAttendancePunchData();
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER attendancePunchDataSyncCronScheduler "+ e);
		}	
	}
	
	
	@RequestMapping(value = "AttendanceDashBoard.htm")
	public String MainDashBoard(HttpServletRequest req, HttpSession ses) throws Exception 
	{
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside AttendanceDashBoard.htm "+UserId);
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String Empno = ( ses.getAttribute("EmpNo")).toString();
		String LoginType=(String)ses.getAttribute("LoginType");
    	List <Object[]> attendlist=null;
    	try {
			ses.setAttribute("SidebarActive","AttendanceDashBoard_htm");
			ses.setAttribute("formmoduleid", "17");
		    List<Object[]>  emplist=service.EmployeeList();
			req.setAttribute("Emplist", emplist);
			String empNo=req.getParameter("empNo");
			String fromDate=req.getParameter("FromDate");
			String toDate=req.getParameter("ToDate");	
			
			if(fromDate==null  && toDate==null) 
			{
				String fd = LocalDate.now().minusMonths(1).toString();
				String td = LocalDate.now().toString();
				fromDate=rdf.format(sdf.parse(fd.toString()));
				toDate=rdf.format(sdf.parse(td.toString()));
				
			}
										
			if(empNo!=null && fromDate!=null && fromDate!=null) {
				 attendlist=service.getAttendanceDetails(empNo,fromDate,toDate);
				 req.setAttribute("EmpNo", empNo);
				}else {   
					if(Empno!=null && fromDate!=null && fromDate!=null)
					 attendlist=service.getAttendanceDetails(Empno,fromDate,toDate);				
					req.setAttribute("EmpNo", Empno);
				}
			req.setAttribute("logintype", LoginType);
			req.setAttribute("ToDate", toDate);
			req.setAttribute("FromDate",fromDate);						
			req.setAttribute("attendlist", attendlist);
			req.setAttribute("EmpId",EmpId);
			req.setAttribute("LastSyncDateTime", service.getlastSyncDateTime());
			
			return "attendance/AttendanceDashboard";
    	}catch (Exception e) {
    		e.printStackTrace();
			logger.error(new Date() +" Inside AttendanceDashBoard.htm "+UserId, e);
			return "static/Error";
		}
	}
	
	
}

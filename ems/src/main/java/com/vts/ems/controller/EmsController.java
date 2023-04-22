package com.vts.ems.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Admin.model.EmployeeContract;
import com.vts.ems.config.MsAccessDBConnectionConfig;
import com.vts.ems.login.EmpContractRepo;
import com.vts.ems.login.Login;
import com.vts.ems.login.LoginRepository;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.service.EMSMainService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class EmsController {
	private static final Logger logger = LogManager.getLogger(EmsController.class);

	@Autowired
	private LoginRepository Repository;

	@Autowired
	private EmpContractRepo contractemprepo;
	
	@Autowired
	private EMSMainService service;


	@Value("${ProjectFiles}")
	private String projectfilespath;
	SimpleDateFormat rf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model, HttpServletRequest req, HttpSession ses) throws Exception {
		logger.info(new Date() + " Login By " + req.getUserPrincipal().getName());
		try {
			boolean isContractEmp=false;
			Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for(SimpleGrantedAuthority auth : authorities)
			{
				String role = auth.getAuthority();
				if(role.equalsIgnoreCase("ROLE_CE"))
				{
					isContractEmp=true;
				}
			}
			
			LabMaster labinfo =service.getLabDetails();
			if(labinfo!=null && labinfo.getLabCode()!=null) {
				ses.setAttribute("LabCode", labinfo.getLabCode());
			}
			else
			{
				ses.setAttribute("LabCode", "");
			}
			
			
        	String IpAddress="Not Available";
     		try{
     		
	     		 IpAddress = req.getRemoteAddr();
	     		 
	     		if("0:0:0:0:0:0:0:1".equalsIgnoreCase(IpAddress))
	     		{     			
	     			InetAddress ip = InetAddress.getLocalHost();
	     			IpAddress= ip.getHostAddress();
	     		}
     		
     		}
     		catch(Exception e)
     		{
	     		IpAddress="Not Available";	
	     		e.printStackTrace();	
     		}

			
			
			if(!isContractEmp) 
			{
			
					Login login = Repository.findByUsername(req.getUserPrincipal().getName());
					ses.setAttribute("LoginId", login.getLoginId());
					ses.setAttribute("Username", req.getUserPrincipal().getName());
					ses.setAttribute("LoginType", login.getLoginType());
					ses.setAttribute("EmpId", login.getEmpId());
					ses.setAttribute("FormRole", login.getFormRoleId());
					
					Employee employee = service.EmployeeInfo(login.getEmpId());
					ses.setAttribute("EmpNo", employee.getEmpNo());
					ses.setAttribute("EmpName", employee.getEmpName());
					ses.setAttribute("EmpDesig",service.DesignationInfo(employee.getDesigId()).getDesignation() );
					
					long pwdCount = service.PasswordChangeHystoryCount(String.valueOf(login.getLoginId()));
					if(pwdCount==0) 
					{
						return "redirect:/ForcePasswordChange.htm";
					}
		     		
		     		AuditStamping stamping=new AuditStamping();
			        stamping.setLoginId(login.getLoginId());
			        stamping.setLoginDate(new java.sql.Date(new Date().getTime()));
			        stamping.setUsername(login.getUsername());
			        stamping.setIpAddress(IpAddress);
			        stamping.setLoginDateTime(LocalDateTime.now().toString());
			        service.LoginStampingInsert(stamping);
			        
			}
			else
			{
				EmployeeContract login = contractemprepo.findByContractEmpNo(req.getUserPrincipal().getName());
				ses.setAttribute("LoginId", login.getContractEmpId() );
				ses.setAttribute("Username", req.getUserPrincipal().getName());
				ses.setAttribute("LoginType", "CE");
				ses.setAttribute("EmpId", login.getContractEmpId() );
				ses.setAttribute("FormRole", 0);
				
				ses.setAttribute("EmpNo", login.getContractEmpNo());
				ses.setAttribute("EmpName", login.getEmpName());
				ses.setAttribute("EmpDesig","Contract Employee" );
					
				AuditStamping stamping=new AuditStamping();
		        stamping.setLoginId(login.getContractEmpId());
		        stamping.setLoginDate(new java.sql.Date(new Date().getTime()));
		        stamping.setUsername(login.getContractEmpNo());
		        stamping.setIpAddress(IpAddress);
		        stamping.setLoginDateTime(LocalDateTime.now().toString());
		        service.LoginStampingInsert(stamping);
		        
		       
			}
		     	
			
		} catch (Exception e) {
			logger.error(new Date() + " Login Issue Occured When Login By " + req.getUserPrincipal().getName(), e);
			e.printStackTrace();
		}

		return "redirect:/MainDashBoard.htm";
	}

	@RequestMapping(value = "MainDashBoard.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String MainDashBoard(HttpServletRequest req, HttpSession ses) throws Exception 
	{
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside MainDashBoard.htm "+UserId);
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String Empno = ( ses.getAttribute("EmpNo")).toString();
    	String LoginType=(String)ses.getAttribute("LoginType");
    	String LoginId=((Long) ses.getAttribute("LoginId")).toString();
    	List <Object[]> attendlist=null;
    	try {
    		req.setAttribute("alerts-marquee", service.getCircularOrdersNotice());
			req.setAttribute("logintypeslist",service.EmpHandOverLoginTypeList(EmpId,LoginId));
			req.setAttribute("logintype", LoginType);
			ses.setAttribute("SidebarActive","Home");
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
			
			   
			req.setAttribute("ToDate", toDate);
			req.setAttribute("FromDate",fromDate);						
			req.setAttribute("attendlist", attendlist);
			req.setAttribute("EmpId",EmpId);
			req.setAttribute("LastSyncDateTime", service.getlastSyncDateTime());
			
			return "static/maindashboard";
    	}catch (Exception e) {
    		e.printStackTrace();
			logger.error(new Date() +" Inside MainDashBoard.htm "+UserId, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "ForcePasswordChange.htm", method = RequestMethod.GET)
	public String ForcePasswordChange(HttpServletRequest req, HttpSession ses) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside ForcePasswordChange.htm "+UserId);
		req.setAttribute("ForcePwd", "Y");
		return "pis/PasswordChange";
	}
	
	
	
	@RequestMapping(value = "Calendar.htm")
	public String calendar(HttpServletRequest req, HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside MainDashBoard.htm "+UserId);
    	
    	try {
    		String year = req.getParameter("year");
    		String month = req.getParameter("month");
    		if(year==null) {
    			LocalDate today = LocalDate.now();
    			year=today.getYear()+"";
    			month = today.getMonthValue()+"";
    		}
    		
    		req.setAttribute("calendarEventType", service.calendarEventTypes());
    		req.setAttribute("calendarEvents", service.calendarEvents(year));
    		req.setAttribute("year",year);
    		req.setAttribute("month",month);
			return "static/EventsCalendar";
    	}catch (Exception e) {
    		e.printStackTrace();
			logger.error(new Date() +" Inside MainDashBoard.htm "+UserId, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "EmpLogitypeChange.htm" , method = RequestMethod.POST)
	public String EmpLogitypeChange(HttpServletRequest req ,HttpSession ses) throws Exception {		
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmpLogitypeChange.htm "+UserId);		
		try {
			String logintype= req.getParameter("logintype");
			ses.setAttribute("LoginType", logintype);
			return "redirect:/MainDashBoard.htm";
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EmpLogitypeChange.htm "+UserId, e);
			return "redirect:/MainDashBoard.htm";
		}
		
	}
	
	
	
	@RequestMapping(value = "NotificationList.htm" , method = RequestMethod.GET)
	public @ResponseBody String NotificationList(HttpServletRequest request ,HttpSession ses) throws Exception 
	{
			
		List<EMSNotification> notificationlist= new ArrayList<EMSNotification>();
		 
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NotificationList.htm "+UserId);		
		try {
		
			String EmpNo= ((String) ses.getAttribute("EmpNo"));
			
		    notificationlist = service.NotificationList(EmpNo);
		    
		    
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NotificationList.htm "+UserId, e);
		}
		 
		Gson json = new Gson();  
		return json.toJson(notificationlist);
		
		
	}
	
	@RequestMapping(value = "AllNoticeCount.htm" ,method=RequestMethod.GET)
	public  @ResponseBody String AllNoticeCount(HttpSession ses , HttpServletRequest req)throws Exception
	{
		String UserId = (String)ses.getAttribute("Username");			
		logger.info(new Date() +"Inside AllNoticeCount.htm "+UserId);
		long count=0;
		try 
		{			
			count= service.AllNoticeCount();
		} 
		catch (Exception e) {
			logger.error(new Date() +"Inside AllNoticeCount.htm "+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}

		Gson json = new Gson();  
		return json.toJson(count);
	}

	
	
	@RequestMapping(value = "NotificationUpdate.htm" , method = RequestMethod.GET)
	public @ResponseBody String NotificationUpdate(HttpServletRequest request ,HttpSession ses) throws Exception {
		Gson json = new Gson();
		int count=0;
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NotificationUpdate.htm "+UserId);		
		try {
			String NotificationId=request.getParameter("notificationid");
			
			count= service.NotificationUpdate(NotificationId);
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NotificationUpdate.htm "+UserId, e);
		}
			return json.toJson(count);
			
		
	}
	
  
	 @RequestMapping(value = "fpwd/ForgotPassword.htm", method = {RequestMethod.POST,RequestMethod.GET}) 
	 public String forgotPassword(Model model, HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		 String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside fpwd/ForgotPassword.htm "+UserId);
		try 
		{
			String username=req.getParameter("username");
			
			if (username == null) {
				Map md = model.asMap();
				username = (String) md.get("username");
			}
			
			if(username==null) {
				return "redirect:/login"; 
			}
			
			Object[] userinfo = service.LoginExistCheck(username);
			
			if(userinfo==null) 
			{
				redir.addFlashAttribute("error","Login for this Username not found, Please Contact Admin.");
				return "redirect:/login";
			}
			
			if(userinfo[4]==null || userinfo[4].toString().trim().equals("") )
			{
				redir.addFlashAttribute("error","Email id not found, Please Contact Admin.");
				return "redirect:/login";
			}
			
			String otp =service.getPasswordResetOTP(userinfo[0].toString());
			
			req.setAttribute("otp", otp);
	    	req.setAttribute("userinfo", userinfo);
	    	return "static/ForgotPassword";
	    	
	    	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside fpwd/ForgotPassword.htm "+ e);
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
		}
	
	    
	 }

	
	 @RequestMapping(value = "fpwd/ResetPassword.htm", method = RequestMethod.POST) 
	 public String resetPassword(Model model,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		 String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside fpwd/ResetPassword.htm "+UserId);
		try 
		{
			String loginid=req.getParameter("loginid");
			String NewPassword=req.getParameter("NewPassword");
			
			int count =service.userResetPassword(loginid, NewPassword);
			
			if(count>0) {
				redir.addFlashAttribute("success","Password reset Successfull.");
				return "redirect:/login";
			}
			
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside fpwd/ResetPassword.htm "+ e);
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
		}
	
	    
	 }
	 
	 @RequestMapping(value = "fpwd/ResendOTP.htm", method = RequestMethod.POST) 
	 public String ResendOTP(Model model, HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		 String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside fpwd/ResendOTP.htm "+UserId);
		try 
		{
			String loginid=req.getParameter("loginid");
			String username=req.getParameter("username");
			
			service.reSendResetOTP(loginid);
			
			redir.addAttribute("username", username);
			return "redirect:/fpwd/ForgotPassword.htm";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside fpwd/ResendOTP.htm "+ e);
			redir.addFlashAttribute("error","Internal error occured, Please Contact Admin.");
			return "redirect:/login";
		}  
	 }




	 
	    @RequestMapping(value = "CircularDownload.htm")
		public void CircularDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside CircularDownload.htm "+UserId);
			try {	
			
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String filename="CHSSContingentList";
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
				
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/chss/ContingetBill.jsp").forward(req, customResponse);
				String html1 = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html1,new FileOutputStream(path+File.separator+filename+".pdf")); 
		         
		        res.setContentType("application/pdf");
		        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
		        File f=new File(path +File.separator+ filename+".pdf");
		         
		        
		        FileInputStream fis = new FileInputStream(f);
		        DataOutputStream os = new DataOutputStream(res.getOutputStream());
		        res.setHeader("Content-Length",String.valueOf(f.length()));
		        byte[] buffer = new byte[1024];
		        int len = 0;
		        while ((len = fis.read(buffer)) >= 0) {
		            os.write(buffer, 0, len);
		        } 
		        os.close();
		        fis.close();
		       
		       
		        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
		        Files.delete(pathOfFile);		
			}
			catch (Exception e) {
				e.printStackTrace();  
				logger.error(new Date() +" Inside CircularDownload.htm "+UserId, e); 
				
			}

		}
	 
	@RequestMapping(value = "UserManualDoc.htm", method = RequestMethod.GET)
	public void UserManualDoc(HttpServletRequest req, HttpSession ses, HttpServletResponse res)	throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside UserManualDoc.htm "+UserId);		
		try {
			String path=projectfilespath+"/ProjectManuals/User-Manual-chss.pdf";

			res.setContentType("application/pdf");
			res.setHeader("Content-Disposition", String.format("inline; filename=\"" + req.getParameter("path") + "\""));
	
			File my_file = new File(path);
	
			OutputStream out = res.getOutputStream();
			FileInputStream in = new FileInputStream(my_file);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
			out.close();	
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside UserManualDoc.htm "+UserId, e);
		}
	}
	
	@RequestMapping(value = "WorkFlow.htm", method = RequestMethod.GET)
	public void WorkFlow(HttpServletRequest req, HttpSession ses, HttpServletResponse res)	throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside WorkFlow.htm "+UserId);		
		try {

		String path =projectfilespath +"/ProjectManuals/Work-Flow-chss.pdf";
	
		res.setContentType("application/pdf");
		res.setHeader("Content-Disposition", String.format("inline; filename=\"" + req.getParameter("path") + "\""));

		File my_file = new File(path);

		OutputStream out = res.getOutputStream();
		FileInputStream in = new FileInputStream(my_file);
		byte[] buffer = new byte[4096];
		int length;
		while ((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
		out.close();	
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside WorkFlow.htm "+UserId, e);
		}
	}
	@RequestMapping(value = "AttendancePieChart.htm",method= {RequestMethod.GET,RequestMethod.POST} )
	public String AttendanceReportPieChart(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside AttendancePieChart.htm "+Username);	
		try {
			String date=req.getParameter("date");
			String date1=null;
			if(date==null) {
				 date = LocalDate.now().toString();
				 req.setAttribute("date",rdf.format(sf.parse(date)));
				 date1=date;
			}else {
				date1=sdf.format(rf.parse(date));
			 	req.setAttribute("date", date);	
			}	
		Object[] count1=service.getEmpCountFirstSes(date1);
		Object[] count2=service.getEmpCountSecondSes(date1);
		Object[] count3=service.getEmpCountThirdSes(date1);
		Object[] count4=service.getEmpCountFourthSes(date1);	
		Object[] count5=service.getEmpCountFifthSes(date1);
			
		List<Object[]> list=new ArrayList<Object[]>();
		list.add(count1);
		list.add(count2);
		list.add(count3);
		list.add(count4);
		list.add(count5);				
		req.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" AttendancePieChart.htm "+Username, e);
		}
			
			return "Admin/AttendanceReportInPiechart";
	
	}
}

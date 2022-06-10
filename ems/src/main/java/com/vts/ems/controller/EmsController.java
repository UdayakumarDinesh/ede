package com.vts.ems.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.chss.service.CHSSService;
import com.vts.ems.login.Login;
import com.vts.ems.login.LoginRepository;
import com.vts.ems.master.service.MasterService;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.service.EMSMainService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class EmsController {
	private static final Logger logger = LogManager.getLogger(EmsController.class);

	@Autowired
	LoginRepository Repository;

	@Autowired
	EMSMainService service;
	

	@Autowired
	MasterService masterservice;
	  

	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");


	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model, HttpServletRequest req, HttpSession ses) throws Exception {
		logger.info(new Date() + " Login By " + req.getUserPrincipal().getName());
		try {
			Login login = Repository.findByUsername(req.getUserPrincipal().getName());
			
			ses.setAttribute("LoginId", login.getLoginId());
			ses.setAttribute("Username", req.getUserPrincipal().getName());
			ses.setAttribute("LoginType", login.getLoginType());
			ses.setAttribute("EmpId", login.getEmpId());
			ses.setAttribute("FormRole", login.getFormRoleId());

			Employee employee = service.EmployeeInfo(login.getEmpId());
			ses.setAttribute("EmpNo", employee.getEmpNo());
			ses.setAttribute("EmpName", employee.getEmpName());
			ses.setAttribute("emplogintypelist",service.EmpHandOverLoginTypeList(String.valueOf(employee.getEmpId())));
			
			long pwdCount = service.PasswordChangeHystoryCount(String.valueOf(login.getLoginId()));
			if(pwdCount==0) 
			{
				return "redirect:/PasswordChange.htm";
			}
			
		} catch (Exception e) {
			logger.error(new Date() + " Login Issue Occured When Login By " + req.getUserPrincipal().getName(), e);
		}

		return "redirect:/MainDashBoard.htm";
	}

	@RequestMapping(value = "MainDashBoard.htm", method = {RequestMethod.GET, RequestMethod.POST})
	public String MainDashBoard(HttpServletRequest req, HttpSession ses) throws Exception {
		logger.info(new Date() + "Inside MainDashBoard.htm ");
    	String LoginType=(String)ses.getAttribute("LoginType");
//    	String LoginId=String.valueOf(ses.getAttribute("LoginId"));
//    	String EmpNo=(String)ses.getAttribute("EmpNo"); 

//    	req.setAttribute("employeedata", service.EmployeeData(EmpId));
		
    	String IsSelf = req.getParameter("isselfvalue");
		String EmpId= ses.getAttribute("EmpId").toString();
		String FromDate = req.getParameter("FromDate");
		String ToDate = req.getParameter("ToDate");
		LocalDate today= LocalDate.now();
		int currentmonth= today.getMonthValue();
		String DbFromDate="";
		String DbToDate="";
		
		if(FromDate== null) {
			String start ="";
			if(currentmonth<4) 
			{
				start = String.valueOf(today.getYear()-1);
			}else{
				start=String.valueOf(today.getYear());
			}
			
			FromDate=start;
			DbFromDate = start+"-04-01";
			
		}
		
		if(ToDate== null) {
			String end="";
			if(currentmonth<4) 
			{
				end =String.valueOf(today.getYear());
			}else{
				end =String.valueOf(today.getYear()+1);
			}
			
			ToDate=end;
			DbToDate=end+"-03-31";
		}
		
		DbFromDate = FromDate+"-04-01";
		DbToDate= ToDate+"-03-31";
			
		if(IsSelf==null) {
			IsSelf="Y";
		}
		
		
		req.setAttribute("countdata", service.MainDashboardCountData(EmpId, DbFromDate, DbToDate,IsSelf) );
		req.setAttribute("Fromdate", FromDate);
		req.setAttribute("Todate", ToDate);
		req.setAttribute("graphdata",  service.MainDashboardGraphData(EmpId, DbFromDate, DbToDate) );
		req.setAttribute("amountdata", service.MainDashboardAmountData(EmpId, DbFromDate, DbToDate,IsSelf));
		req.setAttribute("amountdataindividual", service.MainDashboardIndividualAmountData(EmpId, DbFromDate, DbToDate));
		req.setAttribute("logintype", LoginType);
		req.setAttribute("isself", IsSelf);
		req.setAttribute("monthlywisedata", service.MonthlyWiseDashboardData(DbFromDate, DbToDate));

		return "static/maindashboard";
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
		
			Long EmpId= ((Long) ses.getAttribute("EmpId"));
			
		    notificationlist = service.NotificationList(EmpId);
		    
		    
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside NotificationList.htm "+UserId, e);
		}
		 
		Gson json = new Gson();  
		return json.toJson(notificationlist);
		
		
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
	 public String forgotPassword(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside fpwd/ForgotPassword.htm ");
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
	 public String resetPassword(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside fpwd/ResetPassword.htm ");
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
	 public String ResendOTP(Model model, String error, String logout,HttpServletRequest req,HttpSession ses,HttpServletResponse response, RedirectAttributes redir ) throws Exception 
	 {	 
		logger.info(new Date() +"Inside fpwd/ResendOTP.htm ");
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

	 @RequestMapping(value ="EmpanneledHospital.htm", method = RequestMethod.GET)
		public String EmpanneledHospital(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		 logger.info(new Date() +"Inside EmpanneledHospital.htm ");
			try 
			{				
				List<Object[]> Empanelled = new ArrayList<Object[]>();
				Empanelled = service.GetEmpanelledHostpitalList();
	        	req.setAttribute("Empanelled",Empanelled);
				return "chss/CHSSEmpanelledHospital";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside EmpanneledHospital.htm "+ e);
				return "chss/CHSSEmpanelledHospital";
			}
		}
 
	 @RequestMapping(value ="DoctorsList.htm", method = RequestMethod.GET)
		public String DoctorsList(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		 logger.info(new Date() +"Inside DoctorsList.htm ");
			try 
			{				
				 List<Object[]> doctorlist = new ArrayList<Object[]>();
				 doctorlist = service.GetDoctorList();
	        	 req.setAttribute("doctorlist",doctorlist);
				return "chss/CHSSDoctorList";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DoctorsList.htm "+ e);
				return "chss/CHSSDoctorList";
			}
		}
	
	 @RequestMapping(value = "CHSSPolicy.htm", method = RequestMethod.GET)
		public String CHSSPolicy(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		 logger.info(new Date() +"Inside CHSSPolicy.htm ");
			try 
			{	
				String path = req.getServletContext().getRealPath("/manuals/" + "chss-policy.pdf");
				String chss_policy_pdf = Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(path)));
				req.setAttribute("Messege", "hello");
				req.setAttribute("path", path);
				req.setAttribute("chss_policy_pdf", chss_policy_pdf);
				return "chss/CHSSPolicy";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside CHSSPolicy.htm "+ e);
				return "chss/CHSSPolicy";
			}
			
		}
	 @RequestMapping(value = "Circulars.htm", method = {RequestMethod.GET,RequestMethod.POST})
	 public String Circulars(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
	 
		 logger.info(new Date() +"Inside Circulars.htm ");
			try 
			{
				
				String fromdate = (String)req.getParameter("fromdate");
				String todate   = (String)req.getParameter("todate");
				 List<Object[]> circulatlist = new ArrayList<Object[]>();
				 if(fromdate==null && todate==null) {
					 fromdate = DateTimeFormatUtil.getFirstDayofCurrentMonthRegularFormat();
		   			   todate  = DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now());
				 }
				 

				 circulatlist = masterservice.GetCircularList(fromdate,todate);
	        	 
	        	 req.setAttribute("circulatlist",circulatlist);
	        	 req.setAttribute("fromdate", fromdate);	
				 req.setAttribute("todate",todate);
				return "chss/CHSSCircularList";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside Circulars.htm "+ e);
				return "chss/CHSSCircularList";
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
			String path = req.getServletContext().getRealPath("/manuals/" + "User-Manual-chss.pdf");

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

		String path = req.getServletContext().getRealPath("/manuals/" + "Work-Flow-chss.pdf");

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
		}
		catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside WorkFlow.htm "+UserId, e);
		}
	}

}

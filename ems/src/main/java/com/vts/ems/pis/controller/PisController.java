package com.vts.ems.pis.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.DateTimeFormatUtil;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.service.PisService;



@Controller
public class PisController {

	private static final Logger logger = LogManager.getLogger(PisController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
			
	@Autowired
	private PisService service;
	
	@Value("${Image_uploadpath}")
	private String uploadpath;
	
	@RequestMapping(value = "PisAdminDashboard.htm", method = RequestMethod.GET)
	public String PisDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PisAdminDashboard.htm "+Username);		
		try {
			
			
			return "pis/PisDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminDashboard.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "PisAdminEmpList.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String PisAdminEmpList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside PisAdminEmpList.htm "+Username);		
		try {
			List<Object[]> EmployeeDetailsList =null;
			if(LoginType.equalsIgnoreCase("A")) 
			{
				EmployeeDetailsList = service.EmployeeDetailsList(LoginType, EmpId);
			}			
			req.setAttribute("EmployeeDetailsList", EmployeeDetailsList);
			return "pis/EmployeeList";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminEmpList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "EmployeeDetails.htm", method = RequestMethod.POST)
	public String EmployeeDetails(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeDetails.htm "+Username);		
		try {
			
			String empid=req.getParameter("empid");
			Object[] employeedetails = service.EmployeeDetails(empid);	
            String basevalue=service.getimage(empid);
			req.setAttribute("empid", empid);
			req.setAttribute("employeedetails", employeedetails);
            req.setAttribute("basevalue", basevalue);
			
			return "pis/EmpBasicDetails";
		}catch (Exception e) {
			logger.error(new Date() +" Inside EmployeeDetails.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	@RequestMapping(value = "EmployeeAdd.htm")
	public String EmployeeAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeAdd.htm "+Username);		
		try {			
			req.setAttribute("desiglist", service.DesigList());
			
			req.setAttribute("piscategorylist", service.PisCategoryList());
			req.setAttribute("piscatclasslist", service.PisCatClassList());
			req.setAttribute("piscaderlist", service.PisCaderList());
			req.setAttribute("empstatuslist", service.EmpStatusList());
			req.setAttribute("paylevellist", service.PayLevelList());	
			
			req.setAttribute("divisionlist", service.DivisionList());			
			return "pis/EmployeeAdd";
		}catch (Exception e) {
			logger.error(new Date() +" Inside EmployeeAdd.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	
	@RequestMapping(value = "EmployeeAddSubmit.htm")
	public String EmployeeAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeAddSubmit.htm "+Username);		
		try {
			String salutation = req.getParameter("salutation");
			String empname = req.getParameter("empname");
			String Designationid = req.getParameter("Designationid");
			String caderid = req.getParameter("caderid");
			String catcode = req.getParameter("catcode");
			String gender = req.getParameter("gender");
			String bloodgroup = req.getParameter("bloodgroup");
			String gq = req.getParameter("gq");
			String divisionid = req.getParameter("divisionid");
			String pan = req.getParameter("pan");
			String uid = req.getParameter("uid");
			String drona = req.getParameter("drona");
			String gpf = req.getParameter("gpf");
			String ph = req.getParameter("ph");
			String ServiceStatus = req.getParameter("ServiceStatus");
			String internalNo = req.getParameter("internalNo");
			String category = req.getParameter("category");
			String subcategory = req.getParameter("subcategory");
			String HomeTown = req.getParameter("HomeTown");
			String MaritalStatus = req.getParameter("MaritalStatus");
			String payLevel = req.getParameter("payLevel");
			String SBI = req.getParameter("SBI");
			String religion = req.getParameter("religion");
			String height = req.getParameter("height");
			String email = req.getParameter("email");
			String ExMan = req.getParameter("ExMan");
			String PunchCardNo = req.getParameter("PunchCardNo");
			String idMark = req.getParameter("idMark");
			String empstatus = req.getParameter("empstatus");
			String EmpStatusDate = req.getParameter("EmpStatusDate");
			String PermPassNo = req.getParameter("PermPassNo");
			
			
			Employee emp= new Employee();
			emp.setEmpName(empname.trim());
			emp.setDesignationId(Integer.parseInt(Designationid));
			emp.setTitle(salutation);
		
			// date conversion
        	java.sql.Date dob = DateTimeFormatUtil.dateConversionSql(req.getParameter("dob"));
			java.sql.Date doj = DateTimeFormatUtil.dateConversionSql(req.getParameter("doj"));
			java.sql.Date doa = DateTimeFormatUtil.dateConversionSql(req.getParameter("doa"));
			
			java.sql.Date dor;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date DobInUtilDate = sdf.parse(req.getParameter("dob"));

				// dob plus 60 year
				Calendar cal = Calendar.getInstance();
				cal.setTime(DobInUtilDate);
				cal.add(Calendar.YEAR, 60);
				Date DOBDatePlusSIXTY = cal.getTime();
				String DOBDatePlusSixtyInString = sdf.format(DOBDatePlusSIXTY);

				// conversion of last date of month
				LocalDate convertedDate = LocalDate.parse(DOBDatePlusSixtyInString,
						DateTimeFormatter.ofPattern("dd-MM-yyyy"));

				int getDay = convertedDate.getDayOfMonth();
				if (getDay != 1) {
					convertedDate = convertedDate
							.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
				} else {
					convertedDate = convertedDate.minusMonths(1);
					convertedDate = convertedDate
							.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String DorInStringForm = convertedDate.format(formatter);

				dor = DateTimeFormatUtil.dateConversionSql(DorInStringForm);

			} catch (Exception e) {

				e.printStackTrace();
				dor = DateTimeFormatUtil.dateConversionSql("31-12-2050");
			}
			System.out.println(dor);
			emp.setDOB(dob);
			emp.setDOA(doa);
			emp.setDOJL(doj);
			emp.setDOR(dor);
			emp.setCategoryId(Integer.parseInt(category));
			emp.setGroupId(0);
			emp.setDivisionId(Integer.parseInt(divisionid));
			emp.setCadreId(Integer.parseInt(caderid));
			emp.setCatId(catcode);
			emp.setGender(gender);
			emp.setBloodGroup(bloodgroup);
			emp.setMaritalStatus(MaritalStatus);
			emp.setReligion(religion);
			emp.setEmpStatus(empstatus);
			emp.setGPFNo(gpf);
			emp.setPAN(pan);
			emp.setPINNo(drona);
			emp.setPunchCard(PunchCardNo);
			if(uid!=null && !uid.trim().equalsIgnoreCase("")) {
				emp.setUID(Long.parseLong(uid));
			}
			emp.setQuarters(gq);
			emp.setPH(ph);
			emp.setEmail(email);
			emp.setHomeTown(HomeTown);
			emp.setServiceStatus(ServiceStatus);
			emp.setPayLevelId(Integer.parseInt(payLevel));
			emp.setSBIAccNo(SBI);
			emp.setIdMark(idMark);
			emp.setHeight(height);
			emp.setIsActive(1);
			emp.setCreatedBy(Username);
			emp.setCreatedDate(new Date().toString());
			emp.setInternalNumber(internalNo);
			emp.setSubCategary(subcategory);
			
			
			
			Long value=service.EmployeeAddSubmit(emp);
			if (value != 0) {
				redir.addAttribute("result", "EMPLOYEE ADDED SUCCESSFUL");
			} else {
				redir.addAttribute("resultfail", "EMPLOYEE ADDED UNSUCCESSFUL");
			}
			
			return "redirect:/PisAdminEmpList.htm";
		}catch (Exception e) {
			logger.error(new Date() +" Inside EmployeeAddSubmit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	@RequestMapping(value = "EmployeeEdit.htm")
	public String EmployeeEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeEdit.htm "+Username);		
		try {
			
			String empid=req.getParameter("empid");
			
			req.setAttribute("employee", service.getEmployee(empid));			
			req.setAttribute("desiglist", service.DesigList());
			req.setAttribute("piscategorylist", service.PisCategoryList());
			req.setAttribute("piscatclasslist", service.PisCatClassList());
			req.setAttribute("piscaderlist", service.PisCaderList());
			req.setAttribute("empstatuslist", service.EmpStatusList());
			req.setAttribute("paylevellist", service.PayLevelList());	
			
			req.setAttribute("divisionlist", service.DivisionList());			
			return "pis/EmployeeEdit";
		}catch (Exception e) {
			logger.error(new Date() +" Inside EmployeeEdit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	@RequestMapping(value = "EmployeeEditSubmit.htm")
	public String EmployeeEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside EmployeeEditSubmit.htm " + Username);
		try {
			String salutation = req.getParameter("salutation");
			String empname = req.getParameter("empname");
			String Designationid = req.getParameter("Designationid");
			String caderid = req.getParameter("caderid");
			String catcode = req.getParameter("catcode");
			String gender = req.getParameter("gender");
			String bloodgroup = req.getParameter("bloodgroup");
			String gq = req.getParameter("gq");
			String divisionid = req.getParameter("divisionid");
			String pan = req.getParameter("pan");
			String uid = req.getParameter("uid");
			String drona = req.getParameter("drona");
			String gpf = req.getParameter("gpf");
			String ph = req.getParameter("ph");
			String ServiceStatus = req.getParameter("ServiceStatus");
			String internalNo = req.getParameter("internalNo");
			String category = req.getParameter("category");
			String subcategory = req.getParameter("subcategory");
			String HomeTown = req.getParameter("HomeTown");
			String MaritalStatus = req.getParameter("MaritalStatus");
			String payLevel = req.getParameter("payLevel");
			String SBI = req.getParameter("SBI");
			String religion = req.getParameter("religion");
			String height = req.getParameter("height");
			String email = req.getParameter("email");
			String ExMan = req.getParameter("ExMan");
			String PunchCardNo = req.getParameter("PunchCardNo");
			String idMark = req.getParameter("idMark");
			String empstatus = req.getParameter("empstatus");
			String PermPassNo = req.getParameter("PermPassNo");
			String EmpId = req.getParameter("EmpId");

			Employee emp = new Employee();
			emp.setEmpName(empname.trim());
			emp.setDesignationId(Integer.parseInt(Designationid));
			emp.setTitle(salutation);
			
			// date conversion
        	java.sql.Date dob = DateTimeFormatUtil.dateConversionSql(req.getParameter("dob"));
			java.sql.Date doj = DateTimeFormatUtil.dateConversionSql(req.getParameter("doj"));
			java.sql.Date doa = DateTimeFormatUtil.dateConversionSql(req.getParameter("doa"));
			
			java.sql.Date dor;

			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date DobInUtilDate = sdf.parse(req.getParameter("dob"));

				// dob plus 60 year
				Calendar cal = Calendar.getInstance();
				cal.setTime(DobInUtilDate);
				cal.add(Calendar.YEAR, 60);
				Date DOBDatePlusSIXTY = cal.getTime();
				String DOBDatePlusSixtyInString = sdf.format(DOBDatePlusSIXTY);

				// conversion of last date of month
				LocalDate convertedDate = LocalDate.parse(DOBDatePlusSixtyInString,
						DateTimeFormatter.ofPattern("dd-MM-yyyy"));

				int getDay = convertedDate.getDayOfMonth();
				if (getDay != 1) {
					convertedDate = convertedDate
							.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
				} else {
					convertedDate = convertedDate.minusMonths(1);
					convertedDate = convertedDate
							.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));
				}

				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				String DorInStringForm = convertedDate.format(formatter);

				dor = DateTimeFormatUtil.dateConversionSql(DorInStringForm);

			} catch (Exception e) {

				e.printStackTrace();
				dor = DateTimeFormatUtil.dateConversionSql("31-12-2050");
			}
			System.out.println(dor);
			emp.setDOB(dob);
			emp.setDOA(doa);
			emp.setDOJL(doj);
			emp.setDOR(dor);
			emp.setCategoryId(Integer.parseInt(category));
			emp.setGroupId(0);
			emp.setDivisionId(Integer.parseInt(divisionid));
			emp.setCadreId(Integer.parseInt(caderid));
			emp.setCatId(catcode);
			emp.setGender(gender);
			emp.setBloodGroup(bloodgroup);
			emp.setMaritalStatus(MaritalStatus);
			emp.setReligion(religion);
			emp.setEmpStatus(empstatus);
			emp.setGPFNo(gpf);
			emp.setPAN(pan);
			emp.setPINNo(drona);
			emp.setPunchCard(PunchCardNo);
			
			if (uid != null && !uid.trim().equalsIgnoreCase("")) {
				emp.setUID(Long.parseLong(uid));
			}
			emp.setQuarters(gq);
			emp.setPH(ph);
			emp.setEmail(email);
			emp.setHomeTown(HomeTown);
			emp.setServiceStatus(ServiceStatus);
			emp.setPayLevelId(Integer.parseInt(payLevel));
			emp.setSBIAccNo(SBI);
			emp.setIdMark(idMark);
			emp.setHeight(height);
			emp.setIsActive(1);
			emp.setModifiedBy(Username);
			emp.setInternalNumber(internalNo);
			emp.setSubCategary(subcategory);
			emp.setEmpId(Integer.parseInt(EmpId));

			long value =service.EmployeeEditSubmit(emp);
			if (value != 0) {
				redir.addAttribute("result", "EMPLOYEE EDITED SUCCESSFUL");
			} else {
				redir.addAttribute("resultfail", "EMPLOYEE EDITED UNSUCCESSFUL");
			}
			return "redirect:/PisAdminEmpList.htm";
		} catch (Exception e) {
			logger.error(new Date() + " Inside EmployeeEditSubmit.htm " + Username, e);
			req.setAttribute("resultfail", "SOME PROBLEM OCCURE!");
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "/requestbypunchajax", method = RequestMethod.GET)
	public @ResponseBody String PunchRequestData(HttpServletRequest req, HttpServletResponse response, HttpSession ses)throws Exception 
	{
		String PunchCardNo = req.getParameter("PunchCardNo");

		int result = 0;
		try {
			result = service.PunchcardList(PunchCardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Gson json = new Gson();
		return json.toJson(result);
	}

	@RequestMapping(value = "PisImageUpload.htm", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public String PisImageUpload(HttpSession ses, RedirectAttributes redir, HttpServletRequest req,HttpServletResponse res, @RequestParam("photo1") MultipartFile file) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside PisImageUpload.htm " + Username);
		int value = 0;
		String EmpId = (String) req.getParameter("employeeid");
		try {
			
			String imagename = service.PhotoPath(EmpId);
			File f = new File(uploadpath + "\\" + imagename);
			if (f.exists()) {
				f.delete();
			}
			value = service.saveEmpImage(file, EmpId, uploadpath);
			Object[] employeedetails = service.EmployeeDetails(EmpId);
			String basevalue = service.getimage(EmpId);
			req.setAttribute("empid", EmpId);
			req.setAttribute("employeedetails", employeedetails);
			req.setAttribute("basevalue", basevalue);
			if (value != 0) {
				req.setAttribute("result", "Photo Upload SUCCESSFUL");
			} else {
				req.setAttribute("resultfail", "Photo Upload UNSUCCESSFUL");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("resultfail", "Photo Upload UNSUCCESSFUL");
			logger.error(new Date() + " Inside EmployeeEditSubmit.htm " + Username, e);
		}
		return "pis/EmpBasicDetails";
	}
	
	@RequestMapping(value = "LoginMaster.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String LoginMaster(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		logger.info(new Date() +"Inside LoginMasters.htm "+Username);		
		try {
			List<Object[]> loginmaster =null;
			
				loginmaster = service.LoginMasterList(LoginType, EmpId);
						
			req.setAttribute("loginmaster", loginmaster);
			return "pis/LoginMaster";
		}catch (Exception e) {
			logger.error(new Date() +" Inside LoginMasters.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	

	@RequestMapping(value = "LoginMasterAddEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	public String LoginAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{   
		String Username = (String) ses.getAttribute("Username");
		String Action =(String)req.getParameter("action");
		String empid = (String)req.getParameter("empid");
		
		if("Add".equalsIgnoreCase(Action)){
			req.setAttribute("emplist", service.getEmpList());
			req.setAttribute("loginlist", service.getLoginTypeList());
						
			return "pis/LoginAdd";
		}else if("Edit".equalsIgnoreCase(Action)) {
			req.setAttribute("emplist", service.getEmpList());
			req.setAttribute("loginlist", service.getLoginTypeList());
			return "pis/LoginEdit";
		}else {
			int count = service.UserManagerDelete(Username,empid);
			if (count > 0) {
				redir.addAttribute("result", "USER DELETE SUCCESSFULLY");
			} else {
				redir.addAttribute("resultfail", "USER DELETE UNSUCCESSFUL");
			}
			return "redirect:/LoginMaster.htm";
		}
		
	}
	
	
	@RequestMapping(value = "UserNamePresentCount.htm", method = RequestMethod.GET)
	public @ResponseBody String UserNamePresentCount(HttpServletRequest req,HttpSession ses) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		int UserNamePresentCount = 0;
		logger.info(new Date() +"Inside UserNamePresentCount.htm "+Username);	
		try {
			UserNamePresentCount = service.UserNamePresentCount(req.getParameter("UserName"));			
		} catch (Exception e) {
			logger.error(new Date() +" Inside UserNamePresentCount.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		Gson json = new Gson();
		return json.toJson(UserNamePresentCount);
	}
	

    @RequestMapping(value = "UserManagerAddSubmit.htm", method = RequestMethod.POST)
	public String UserManagerAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)
			throws Exception 
    {
    	Long count = 0L;
    	String Username = (String) ses.getAttribute("Username");
    
    	logger.info(new Date() +"Inside UserManagerAddSubmit.htm "+Username);
		try {
				
    	  UserManageAdd useradd = new UserManageAdd();
    	  useradd.setUserName(req.getParameter("UserName"));
    	  useradd.setLoginType(req.getParameter("LoginType"));
    	  useradd.setEmpId(req.getParameter("Employee"));
    	  useradd.setLoginType(req.getParameter(Username));
    	  useradd.setCreatedBy(Username);
    	  useradd.setCreatedDate(sdf.format(new Date()));
    	
		  count = service.UserManagerAdd(useradd);
		  
		} catch (Exception e) {
			
			logger.info(new Date() +"Inside UserManagerAddSubmit.htm "+Username);
			e.printStackTrace();
			redir.addAttribute("resultfail", "SOME ERROR OCCURED OR USERNAME NOT AVAILABLE");
			return "redirect:/LoginMaster.htm";
		}
		if (count > 0) {
			
			redir.addAttribute("result", "USER ADD SUCCESSFULLY");
			
		} else {
			redir.addAttribute("resultfail", "USER ADD UNSUCCESSFUL");
		}
    	
		return "redirect:/LoginMaster.htm";
	}
    
   
}

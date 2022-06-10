package com.vts.ems.pis.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.master.service.MasterService;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.AddressEmec;
import com.vts.ems.pis.model.AddressNextKin;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.utils.DateTimeFormatUtil;



@Controller
public class PisController {

	private static final Logger logger = LogManager.getLogger(PisController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@Autowired
	private PisService service;
	
	@Autowired
	MasterService masterservice;
	
	@Value("${Image_uploadpath}")
	private String uploadpath;
	                                                   

	@RequestMapping(value = "PisUserDashboard.htm", method = RequestMethod.GET)
	public String PisUserDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside PisUserDashboard.htm "+Username);		
		try {
			String loginid = (String)ses.getAttribute("LoginId");
			//Object[] userdetails = (Object[])service.getUserDeatails(loginid);
			
			return "pis/PisDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisUserDashboard.htm "+Username, e);
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
			List<Object[]> EmployeeDetailsList =new ArrayList<Object[]>();
			EmployeeDetailsList = service.EmployeeDetailsList(LoginType, EmpId);				
			req.setAttribute("EmployeeDetailsList", EmployeeDetailsList);
			return "pis/EmployeeList";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminEmpList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "EmployeeDetails.htm", method = {RequestMethod.POST , RequestMethod.GET})
	public String EmployeeDetails(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeDetails.htm "+Username);		
		try {
			
			String empid=req.getParameter("empid");
			if(empid==null) {
				empid=String.valueOf((Long)ses.getAttribute("EmpId"));
			}
			
			Object[] empdata = service.GetEmpData(empid);
			Object[] employeedetails = service.EmployeeDetails(empid);	
			Object[] emeaddressdetails = service.EmployeeEmeAddressDetails(empid);	
			Object[] nextaddressdetails = service.EmployeeNextAddressDetails(empid);	
			Object[] peraddressdetails = service.EmployeePerAddressDetails(empid);	
			List<Object[]> resaddressdetails  = service.EmployeeResAddressDetails(empid);	
			List<Object[]> familydetails = service.getFamilydetails(empid);
						
            String basevalue=null;
            if(empdata!=null && empdata[3]!=null) {
            	basevalue=service.getimage(empdata[3].toString());

            }        		
            
			req.setAttribute("empid", empid);
			req.setAttribute("employeedetails", employeedetails);
			req.setAttribute("emeaddressdetails", emeaddressdetails);
			req.setAttribute("nextaddressdetails", nextaddressdetails);
			req.setAttribute("resaddressdetails", resaddressdetails);
			req.setAttribute("peraddressdetails", peraddressdetails);
			req.setAttribute("familydetails", familydetails);
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
			String basicpay = req.getParameter("basicpay");
			String email = req.getParameter("email");
			String ExMan = req.getParameter("ExMan");
			String PunchCardNo = req.getParameter("PunchCardNo");
			String idMark = req.getParameter("idMark");
			String empstatus = req.getParameter("empstatus");
			String phoneno = req.getParameter("PhoneNo");
			String EmpStatusDate = req.getParameter("EmpStatusDate");
			String PermPassNo = req.getParameter("PermPassNo");
			Employee employee=new Employee();
			employee.setEmail(email);
			employee.setDivisionId(Long.parseLong(divisionid));
			
			employee.setDesigId(Long.parseLong(Designationid));
			employee.setEmpName(WordUtils.capitalize(empname.trim()));
			employee.setSrNo(Long.parseLong("0"));
            employee.setEmpNo(PunchCardNo.trim());
			employee.setIsActive(1);
			
			EmployeeDetails emp= new EmployeeDetails();
			emp.setTitle(salutation);
		
			// date conversion
        	java.sql.Date dob = DateTimeFormatUtil.dateConversionSql(req.getParameter("dob"));
			java.sql.Date doj = DateTimeFormatUtil.dateConversionSql(req.getParameter("doj"));
			//java.sql.Date doa = DateTimeFormatUtil.dateConversionSql(req.getParameter("doa"));
			
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
			
			emp.setDOB(dob);
			emp.setDOA(doj);
			emp.setDOJL(doj);
			emp.setDOR(dor);
			emp.setCategoryId(Integer.parseInt(category));
			emp.setGroupId(0);
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
			emp.setPhoneNo(phoneno);
			if(uid!=null && !uid.trim().equalsIgnoreCase("")) {
				emp.setUID(Long.parseLong(uid));
			}
			emp.setQuarters(gq);
			emp.setPH(ph);
			emp.setHomeTown(HomeTown);
			emp.setServiceStatus(ServiceStatus);
			emp.setPayLevelId(Integer.parseInt(payLevel));
			emp.setSBIAccNo(SBI);
			emp.setIdMark(idMark);
			emp.setHeight("0");
			emp.setBasicPay(Long.parseLong(basicpay));
			emp.setIsActive(1);
			emp.setCreatedBy(Username);
			emp.setCreatedDate(new Date().toString());
			emp.setInternalNumber(internalNo);
			emp.setSubCategary(subcategory);
			emp.setEmpNo(PunchCardNo.trim());
			
			
			Long value=service.EmployeeAddSubmit(employee,emp);
			if (value != 0) {
				redir.addAttribute("result", "Employee details added successfully");
			} else {
				redir.addAttribute("resultfail", "Employee details added Unsuccessfull");
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
				
			req.setAttribute("emp", service.getEmp(empid));
			req.setAttribute("employee", service.getEmployeeDetailsData(service.getEmp(empid).getEmpNo()));			
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
			String basicpay = req.getParameter("basicpay");
			String email = req.getParameter("email");
			String ExMan = req.getParameter("ExMan");
			String PunchCardNo = req.getParameter("PunchCardNo");
			String idMark = req.getParameter("idMark");
			String empstatus = req.getParameter("empstatus");
			String PermPassNo = req.getParameter("PermPassNo");
			String phno = req.getParameter("PhoneNo");
			String empdetailsid=req.getParameter("empdetailsid");
			String EmpId= req.getParameter("EmpId");
			
			Employee employee=new Employee();
				employee.setEmpNo(PunchCardNo);
				employee.setEmpName(WordUtils.capitalize(empname.trim()));
				employee.setDesigId(Long.parseLong(Designationid));
				employee.setEmail(email);
				employee.setDivisionId(Long.parseLong(divisionid));
				employee.setEmpNo(PunchCardNo.trim());
				employee.setEmpId(Long.parseLong(EmpId));
			EmployeeDetails emp = new EmployeeDetails();
			emp.setTitle(salutation);
			
			// date conversion
        	java.sql.Date dob = DateTimeFormatUtil.dateConversionSql(req.getParameter("dob"));
			java.sql.Date doj = DateTimeFormatUtil.dateConversionSql(req.getParameter("doj"));
			//java.sql.Date doa = DateTimeFormatUtil.dateConversionSql(req.getParameter("doa"));
			
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
		
			emp.setDOB(dob);
			//emp.setDOA(doa);
			emp.setDOJL(doj);
			emp.setDOR(dor);
			emp.setCategoryId(Integer.parseInt(category));
			emp.setGroupId(0);
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
			emp.setPhoneNo(phno);
			emp.setEmpNo(PunchCardNo);
			if (uid != null && !uid.trim().equalsIgnoreCase("")) {
				emp.setUID(Long.parseLong(uid));
			}
			emp.setQuarters(gq);
			emp.setPH(ph);
			emp.setHomeTown(HomeTown);
			emp.setServiceStatus(ServiceStatus);
			emp.setPayLevelId(Integer.parseInt(payLevel));
			emp.setSBIAccNo(SBI);
			emp.setIdMark(idMark);
			emp.setHeight("0");
			emp.setBasicPay(Long.parseLong(basicpay));
			emp.setIsActive(1);
			emp.setModifiedBy(Username);
			emp.setInternalNumber(internalNo);
			emp.setSubCategary(subcategory);
			emp.setEmpDetailsId(Long.parseLong(empdetailsid));

			long value =service.EmployeeEditSubmit(employee);
			if(value>0) {
			value =service.EmployeeDetailsEditSubmit(emp);
			}
			String comments = (String)req.getParameter("comments");
			MasterEdit masteredit = new MasterEdit();
			masteredit.setTableRowId(Long.parseLong(EmpId));
			masteredit.setTableName("employee");
			masteredit.setComments(comments);
			masteredit.setCreatedDate(sdtf.format(new Date()));
			masteredit.setCreatedBy(Username);
			masterservice.AddMasterEditComments(masteredit);
			if (value != 0) {
				redir.addAttribute("result", "Employee details Edited Successfully");
			} else {
				redir.addAttribute("resultfail", "Employee details Edited Unsuccessfull");
			}
			return "redirect:/PisAdminEmpList.htm";
		} catch (Exception e) {
			logger.error(new Date() + " Inside EmployeeEditSubmit.htm " + Username, e);
			req.setAttribute("resultfail", "Some Problem Occure!");
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
		String EmpId = (String) req.getParameter("empid");
		try {
			Object[] empdata = service.GetEmpData(EmpId);
			String imagename = null;
			if(empdata!=null && empdata[3]!=null) {
				imagename = service.PhotoPath(empdata[3]+"");
			}
					
					
			File f = new File(uploadpath + "\\" + imagename);
			if (f.exists()) {
				f.delete();
			}
			value = service.saveEmpImage(file, empdata[3]+"", uploadpath);
			Object[] employeedetails = service.EmployeeDetails(EmpId);
			String basevalue = service.getimage(empdata[3]+"");
			req.setAttribute("empid", EmpId);
			req.setAttribute("employeedetails", employeedetails);
			req.setAttribute("basevalue", basevalue);
			if (value != 0) {
				req.setAttribute("result", "Photo Upload Successful");
			} else {
				req.setAttribute("resultfail", "Photo Upload UnSuccessful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("resultfail", "Photo Upload UnSuccessful");
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
			
				loginmaster = service.LoginMasterList();
						
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
		String loginid = (String)req.getParameter("loginid");
		
		if("Add".equalsIgnoreCase(Action)){
			req.setAttribute("emplist", service.getEmpList());
			req.setAttribute("loginlist", service.getLoginTypeList());
						
			return "pis/LoginAdd";
		}else if("Edit".equalsIgnoreCase(Action)) {
			
			
			req.setAttribute("logineditdata", service.getLoginEditData(loginid));
			req.setAttribute("emplist", service.getEmpList());
			req.setAttribute("loginlist", service.getLoginTypeList());
			
			return "pis/LoginEdit";
		}else {
			
			int count = service.UserManagerDelete(Username,loginid);
			if (count > 0) {
				redir.addAttribute("result", "LoginID deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "LoginID deleted Unsuccessfull");
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
    	  useradd.setCreatedBy(Username);
    	  useradd.setCreatedDate(sdf.format(new Date()));
    	
		  count = service.UserManagerAdd(useradd);
		  
		} catch (Exception e) {
			
			logger.info(new Date() +"Inside UserManagerAddSubmit.htm "+Username);
			e.printStackTrace();
			redir.addAttribute("resultfail", "Some Error Occured Or Username Not Available");
			return "redirect:/LoginMaster.htm";
		}
		if (count > 0) {
			
			redir.addAttribute("result", "LoginID created Successfully");
			
		} else {
			redir.addAttribute("resultfail", "LoginID created Unsuccessfull");
		}
    	
		return "redirect:/LoginMaster.htm";
	}
    
  
   @RequestMapping(value = "UserManagerEditSubmit.htm", method = RequestMethod.POST)
   public String UserManagerEditSubmit(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
   {	String Username = (String) ses.getAttribute("Username");
        logger.info(new Date() +"Inside UserManagerEditSubmit.htm "+Username);
        try {
			//String Empid     = (String)req.getParameter("Employee");
        	String Logintype = (String)req.getParameter("LoginType");
        	String Loginid   = (String)req.getParameter("loginid");
        	
        	int count =service.UserMangerEdit( Logintype,Username,Loginid);
        	if(count>0) {
        		redir.addAttribute("result", "LoginID Edited successfully");	
    		} else {
    			redir.addAttribute("resultfail", "LoginID Edited Unsuccessfull");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return "redirect:/LoginMaster.htm";
   }
   
   @RequestMapping(value="FamilyMembersList.htm",method= {RequestMethod.POST,RequestMethod.GET})
   public String FamilyMembersList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
   {	
	   String Username = (String) ses.getAttribute("Username");
        logger.info(new Date() +"Inside FamilyMembersList.htm "+Username);
        String empid =null;
        	
        try {
        	  empid = (String)req.getParameter("empid");
        	if(empid==null)  {
				Map md=model.asMap();
				empid=(String)md.get("Employee");
				
			}
			req.setAttribute("familymemberslist", service.getFamilyMembersList(empid));
			req.setAttribute("Empdata", service.GetEmpData(empid));
			
		} catch (Exception e) {		
			e.printStackTrace();
			 return "redirect:/PisAdminEmpList.htm";
		}
        return "pis/FamilyMemberList";
   }

	@RequestMapping(value = "FamilyMemberAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
	public String FamilyMemberAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside FamilyMemberAddEditDelete.htm " + Username);
		String empid = (String) req.getParameter("empid");
		String Action = (String) req.getParameter("Action");

		if ("ADD".equalsIgnoreCase(Action)) {
			req.setAttribute("Empdata", service.GetEmpData(empid));
			req.setAttribute("FamilyRelation", service.getFamilyRelation());
			req.setAttribute("FamilyStatus", service.getFamilyStatus());
	
			return "pis/FamilyMemberAdd";
		} else if ("EDIT".equalsIgnoreCase(Action)) {
			String familyid = (String) req.getParameter("familyid");
		
			req.setAttribute("memberdetails", service.getMemberDetails(familyid));
			req.setAttribute("Empdata", service.GetEmpData(empid));
			req.setAttribute("FamilyRelation", service.getFamilyRelation());
			req.setAttribute("FamilyStatus", service.getFamilyStatus());

			return "pis/FamilyMemberEdit";
		} else {
                                                                                        
			String familyid = (String) req.getParameter("familyid");
			int count = service.DeleteMeber(familyid, Username);
			if (count > 0) {
				redir.addAttribute("result", "Family Member deleted sucessfully");
			} else {
				redir.addAttribute("resultfail", "Family Member deleted Unsucessfull");
			}
			redir.addFlashAttribute("Employee", empid);
			return "redirect:/FamilyMembersList.htm";
		}

	}
   
   @RequestMapping(value="AddFamilyDetails.htm" ,  method=RequestMethod.POST)  
   public String familyDetailsAdd(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside familyDetailsAdd.htm "+Username);
       
       try {
    	   String name = (String)req.getParameter("memberName");
    	   String dob  = (String)req.getParameter("dob");
    	   String relation = (String)req.getParameter("relation");
    	   String benId =  (String)req.getParameter("benId");
    	   String status = (String)req.getParameter("status");
    	   String statusdate = (String)req.getParameter("statusDate");
    	   String bloodgroup = (String)req.getParameter("bloodgroup");
    	   String Phone = (String)req.getParameter("PH");
    	   String medicaldep = (String)req.getParameter("medicaldep");
    	   String medicaldepdate = (String)req.getParameter("medicaldepdate");
    	   String ltcdep  = (String)req.getParameter("ltcdep");
    	   String LTC = (String)req.getParameter("LTC");
    	   String marriagestatus = (String)req.getParameter("married_unmarried");
    	   String emp_unemp = (String)req.getParameter("emp_unemp");
    	   String gender = (String)req.getParameter("Gender");
    	   String empid = (String)req.getParameter("empid");
    	   
    	   
    	   EmpFamilyDetails details = new EmpFamilyDetails();
    	 
    	   details.setMember_name(WordUtils.capitalize(name.trim()));
    	   details.setDob(DateTimeFormatUtil.dateConversionSql(dob));
    	   details.setRelation_id(Integer.parseInt(relation));
    	   details.setGender(gender);
    	   details.setFamily_status_id(1);
    	   details.setCghs_ben_id(benId);
    	   details.setFamily_status_id(Integer.parseInt(status));
    	   details.setStatus_from(DateTimeFormatUtil.dateConversionSql(statusdate));
    	   details.setBlood_group(bloodgroup);
    	   details.setPH(Phone);  	   
    	   details.setMed_dep(medicaldep);
    	   details.setMed_dep_from(DateTimeFormatUtil.dateConversionSql(medicaldepdate));
    	   details.setLtc_dep(ltcdep);
    	   details.setLtc_dep_from(DateTimeFormatUtil.dateConversionSql(LTC));
    	   details.setMar_unmarried(marriagestatus);
    	   details.setEmp_unemp(emp_unemp);
    	   details.setEmpid(empid);
    	   details.setIsActive(1);
    	   details.setCreatedBy(Username);
    	   details.setCreatedDate(sdf.format(new Date()));
    	   if(emp_unemp.equalsIgnoreCase("Y")) {
    		   details.setEmpStatus((String)req.getParameter("EmpStatus"));
    	   }
    	   Long result = service.AddFamilyDetails(details);
    	   if(result>0){
    		   redir.addAttribute("result", "Family Member Saved Successfully");	
   		   } else {
   			redir.addAttribute("resultfail", "Family Member Saved UNSuccessful");
    	   }
    	  
    	   redir.addFlashAttribute("Employee", empid);
	   } catch (Exception e) {
	  	 e.printStackTrace();
	  }
       return "redirect:/FamilyMembersList.htm";
   }
   
   
   @RequestMapping(value="EditFamilyDetails.htm" , method=RequestMethod.POST)
   public String MembereEdit(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside EditFamilyDetails.htm "+Username);
       try {
    	   String name = (String)req.getParameter("memberName");
    	   String dob  = (String)req.getParameter("dob");
    	   String relation = (String)req.getParameter("relation");
    	   String benId =  (String)req.getParameter("benId");
    	   String status = (String)req.getParameter("status");
    	   String statusdate = (String)req.getParameter("statusDate");
    	   String bloodgroup = (String)req.getParameter("bloodgroup");
    	   String Phone = (String)req.getParameter("PH");
    	   String medicaldep = (String)req.getParameter("medicaldep");
    	   String medicaldepdate = (String)req.getParameter("medicaldepdate");
    	   String ltcdep  = (String)req.getParameter("ltcdep");
    	   String LTC = (String)req.getParameter("LTC");
    	   String marriagestatus = (String)req.getParameter("married_unmarried");
    	   String emp_unemp = (String)req.getParameter("emp_unemp");
    	   String empid = (String)req.getParameter("empid");
    	   String familyid = (String)req.getParameter("familyid");
    	   String gender   = (String)req.getParameter("Gender");
    	   EmpFamilyDetails details = new EmpFamilyDetails();
    	 
    	   details.setMember_name(WordUtils.capitalize(name.trim()));
    	   details.setDob(DateTimeFormatUtil.dateConversionSql(dob));
    	   details.setRelation_id(Integer.parseInt(relation));
    	   details.setCghs_ben_id(benId);
    	   details.setFamily_status_id(Integer.parseInt(status));
    	   details.setStatus_from(DateTimeFormatUtil.dateConversionSql(statusdate));
    	   details.setBlood_group(bloodgroup);
    	   details.setPH(Phone); 	  
    	   details.setMed_dep(medicaldep);
    	   details.setMed_dep_from(DateTimeFormatUtil.dateConversionSql(medicaldepdate));
    	   details.setLtc_dep(ltcdep);
    	   details.setLtc_dep_from(DateTimeFormatUtil.dateConversionSql(LTC));
    	   details.setMar_unmarried(marriagestatus);
    	   details.setEmp_unemp(emp_unemp);
    	   if(emp_unemp.equalsIgnoreCase("Y")) {
		   details.setEmpStatus((String)req.getParameter("EmpStatus"));
	       }
    	   details.setGender(gender);
    	   details.setEmpid(empid);
    	   details.setModifiedBy(Username);
    	   details.setModifiedDate(sdtf.format(new Date()));  
    	   details.setFamily_details_id(Long.parseLong(familyid));
    	   
    	   String comments = (String)req.getParameter("comments");
    	   MasterEdit masteredit  = new MasterEdit();
    	   masteredit.setCreatedBy(Username);
    	   masteredit.setCreatedDate(sdtf.format(new Date()));
    	   masteredit.setTableRowId(Long.parseLong(familyid));
    	   masteredit.setComments(comments);
    	   masteredit.setTableName("pis_emp_family_details");
    	   
    	   masterservice.AddMasterEditComments(masteredit);
    	   
    	   Long result = service.EditFamilyDetails(details);
    	   if(result>0){
    		   redir.addAttribute("result", "Family member Edited sucessfully");	
   		   } else {
   			redir.addAttribute("resultfail", "Family Member Edited UnSuccessful");
    	   }
    	  
    	   redir.addFlashAttribute("Employee", empid);   
    	   
	      } catch (Exception e) {
	    	  logger.error(new Date() + " Inside EditFamilyDetails.htm " + Username, e);
				req.setAttribute("resultfail", "Internal Problem!");
				e.printStackTrace();
				return "static/Error";
	      }
       return "redirect:/FamilyMembersList.htm";
   }
   
   @RequestMapping(value="Address.htm"  , method= {RequestMethod.GET,RequestMethod.POST})
   public String AddressDashboard(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
   {
	
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddressDashboard.htm "+Username);
       String empid =null;
	   try {
		  
		    empid = (String)req.getParameter("empid");
		    
		   if(empid==null) {
			   Map md=model.asMap();
				empid=(String)md.get("Employee");
				if((String)md.get("Employee")==null) {
					return "redirect:/PisAdminEmpList.htm";
				}
		   }
		  	                        
		   Object[]  perAddress = (Object[])service.getPerAddress(empid);
		   List<Object[]> resAddress = (List<Object[]>)service.getResAddress(empid);
		   Object[] kinAddress = (Object[]) service.getKinAddress(empid);
		   Object[] emeAddress = (Object[]) service.getEmeAddress(empid);
		   
		   req.setAttribute("EmeAddress", emeAddress);
		   req.setAttribute("kinAddress", kinAddress);
		   req.setAttribute("ResAddress", resAddress);
		   req.setAttribute("Empdata", service.GetEmpData(empid));
		   req.setAttribute("perAddress", perAddress);
		   
	   }catch (Exception e){
		  e.printStackTrace();
	   }
	   
	   return "pis/AddressList";
   }
   
   @RequestMapping(value ="AddEditPerAddress.htm" , method= {RequestMethod.GET,RequestMethod.POST})
   public String AddEditPerAddress(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddEditPerAddress.htm "+Username);
       try {

    	   String Action = (String) req.getParameter("Action");
    	   String empid =(String)req.getParameter("empid");
    	   
    	   	if("EDITPerAddress".equalsIgnoreCase(Action)) {
    	   	
    	   		AddressPer peraddress = service.getPerAddressData(empid);
    	   		
    	   		List<Object[]> States = service.getStates();
    	   		 req.setAttribute("peraddress", peraddress);
    	     	 req.setAttribute("Empdata", service.GetEmpData(empid));
 	   		     req.setAttribute("States", States);
    	   	      return "pis/AddEditPerAddress";
           }else{
    	   		List<Object[]> States = service.getStates();
    	   	    req.setAttribute("Empdata", service.GetEmpData(empid));
    	   		req.setAttribute("States", States);
    	   	 return "pis/AddEditPerAddress";
    	   	}
    	   	
	    } catch (Exception e) {
	 	    e.printStackTrace();
	    }
      
       return "pis/AddEditPerAddress";
   }
   
   @RequestMapping(value = "AddAddressDetails.htm" , method= RequestMethod.POST)
   public String AddPerAddressDetails(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddPerAddressDetails.htm "+Username);  
       String Action = (String)req.getParameter("Action");
       try {
    	   String state =(String)req.getParameter("state");
    	   String city  =(String)req.getParameter("city");
    	   String cityPin =(String)req.getParameter("cityPin");
    	   String mobile =(String)req.getParameter("mobile");
    	   String altMobile =(String)req.getParameter("altMobile");
    	   String landineNo =(String)req.getParameter("landineNo");
    	   String fromPer =(String)req.getParameter("fromPer");
    	   String empid =(String)req.getParameter("empid");
    	   String perAdd=(String)req.getParameter("perAdd");
    	   
    	
    	   AddressPer peraddress = new AddressPer();
    	   	if(landineNo!=null) {
    	   	 peraddress.setLandline(landineNo);
    	   }
    	   if(altMobile!=null) {
    		  peraddress.setAlt_mobile(altMobile);
    	   }
    	   if(fromPer!=null) {
    		   peraddress.setFrom_per_addr(DateTimeFormatUtil.dateConversionSql(fromPer));
    	   }   	  
    	   peraddress.setMobile(mobile);
    	   peraddress.setPin(cityPin);
    	   peraddress.setState(state);
    	   peraddress.setCity(city);  	  
    	   peraddress.setEmpid(empid);
    	   peraddress.setPer_addr(perAdd);
    	   
    	   if("ADD".equalsIgnoreCase(Action)) {
    		   peraddress.setCreatedBy(Username);
        	   peraddress.setCreatedDate(sdf.format(new Date()));
        	  long result  =  service.AddPerAddress(peraddress); 
        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Parmanent Address Add Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Parmanent Address Add UnSuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
    	   }else if("EDIT".equalsIgnoreCase(Action)) {
    		   String addressid = (String)req.getParameter("addressId");
    		   peraddress.setAddress_per_id(Long.parseLong(addressid));
    		   peraddress.setModifiedBy(Username);
        	   peraddress.setModifiedDate(sdtf.format(new Date()));
        	   
        	   
        	   String comments = (String)req.getParameter("comments");
        	   MasterEdit masteredit  = new MasterEdit();
        	   masteredit.setCreatedBy(Username);
        	   masteredit.setCreatedDate(sdtf.format(new Date()));
        	   masteredit.setTableRowId(Long.parseLong(addressid));
        	   masteredit.setComments(comments);
        	   masteredit.setTableName("pis_address_per");
        	   
        	   masterservice.AddMasterEditComments(masteredit);

        	   long result  =  service.EditPerAddress(peraddress); 
          	 
	       	    if(result>0) {
	       	    	 redir.addAttribute("result", "Parmanent Address Edit Successfull");	
	       		} else {
	       			 redir.addAttribute("resultfail", "Parmanent Address Edit UnSuccessful");	
	       	    }
	       	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   e.printStackTrace();
		   redir.addAttribute("resultfail", "Some Problem Occure !");	
	  }  
       return "redirect:/Address.htm";
   }
   
  
   @RequestMapping(value = "ResAddressAddEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	public String ResAddressAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{   
		String Username = (String) ses.getAttribute("Username");
		String Action =(String)req.getParameter("Action");
		String empid = (String)req.getParameter("empid");
		
		logger.info(new Date() +"Inside ResAddressAddEdit.htm "+Username); 
		
		if("ADD".equalsIgnoreCase(Action)){
			
				 List<Object[]> States = service.getStates();
				 req.setAttribute("Empdata", service.GetEmpData(empid));
	   		     req.setAttribute("States", States);
						
		  return "pis/AddEditResAddress";
		}else if("EDIT".equalsIgnoreCase(Action)) {
			
			  String Addressid = (String)req.getParameter("addressid"); 	
			  AddressRes addres = service.getResAddressData(Addressid);
			  List<Object[]> States = service.getStates();
			  req.setAttribute("Empdata", service.GetEmpData(empid));
			  req.setAttribute("addres", addres);
			  req.setAttribute("States", States);
			  
		   return "pis/AddEditResAddress";
		}else{
			
			String addressid = (String)req.getParameter("addressid");
					
			int count =service.deleteResAdd(addressid,Username);
			if (count > 0) {
				redir.addAttribute("result", "Residential Address Deleted Sucessfully");
			} else {
				redir.addAttribute("resultfail", "Residential Address Delete UnSuccessful");
			}
			redir.addFlashAttribute("Employee", empid);
			return "redirect:/Address.htm";
		}
		
	}
           
   
   @RequestMapping(value="AddEditResAddressDetails.htm" , method= {RequestMethod.POST,RequestMethod.GET})
   public String AddResAddressDetails(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	  
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddResAddressDetails.htm "+Username);  
       String Action = (String)request.getParameter("Action");
	   try {
		    String resAdd = request.getParameter("resAdd").trim().replaceAll(" +", " ");
			String fromRes = request.getParameter("fromRes");
			String mobile = request.getParameter("mobile").trim().replaceAll(" +", " ");
			String altMobile = request.getParameter("altMobile").trim().replaceAll(" +", " ");
			String landineNo = request.getParameter("landineNo").trim().replaceAll(" +", " ");
			String extNo = request.getParameter("extNo").trim().replaceAll(" +", " ");
			String eDrona = request.getParameter("eDrona").trim().replaceAll(" +", " ");
			String eOfficial = request.getParameter("eOfficial").trim().replaceAll(" +", " ");
			String ePersonal = request.getParameter("ePersonal").trim().replaceAll(" +", " ");
			String eOutlook = request.getParameter("eOutlook").trim().replaceAll(" +", " ");
			String qtrNo = request.getParameter("qtrNo").trim().replaceAll(" +", " ");
			String qtrType = request.getParameter("qtrType").trim().replaceAll(" +", " ");
			String qtrDetail = request.getParameter("qtrDetail").trim().replaceAll(" +", " ");
			String state = request.getParameter("state").trim().replaceAll(" +", " ");
			String city = request.getParameter("city").trim().replaceAll(" +", " ");
			String pin = request.getParameter("cityPin").trim().replaceAll(" +", " ");
			String empid= (String)request.getParameter("empid"); 
			
					
			int pinInt = Integer.parseInt(pin);

			long mobileInt = 1;
			long altMobileInt = 1;
			long landineNoInt = 1;
			int extNoInt = 1;

			if (!"".equalsIgnoreCase(mobile)) {
				mobileInt = Long.parseLong(mobile);
			}

			if (!"".equalsIgnoreCase(altMobile)) {
				altMobileInt = Long.parseLong(altMobile);
			}
			if (!"".equalsIgnoreCase(landineNo)) {
				landineNoInt = Long.parseLong(landineNo);
			}
			if (!"".equalsIgnoreCase(extNo)) {
				extNoInt = Integer.parseInt(extNo);
			}
			
			AddressRes resadd= new AddressRes();
			
			resadd.setEmpid(empid);
			resadd.setRes_addr(resAdd);
			resadd.setFrom_res_addr(DateTimeFormatUtil.dateConversionSql(fromRes));
			resadd.setMobile(mobile);
			resadd.setAlt_mobile(altMobile);
			resadd.setLandline(landineNo);
			resadd.setExt(extNo);
			resadd.setEmailDrona(eDrona);
			resadd.setEmailOfficial(eOfficial);
			resadd.setEmailPersonal(ePersonal);
			resadd.setEmailOutlook(eOutlook);
			resadd.setQtrNo(qtrNo);
			resadd.setQtrType(qtrType);
			resadd.setQtrDetails(qtrDetail);
			resadd.setState(state);
			resadd.setCity(city);
			resadd.setPin(pin);
		
			
		if("ADD".equalsIgnoreCase(Action)) {
			resadd.setIsActive(1);
			resadd.setCreatedBy(Username);
			resadd.setCreatedDate(sdf.format(new Date()));
        	  long result  =  service.AddResAddress(resadd); 
        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Residential Address Add Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Residential Address ADD UnSuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
		}else if ("EDIT".equalsIgnoreCase(Action)) {
			String addressresid = (String)request.getParameter("addressresid");
			resadd.setAddress_res_id(Long.parseLong(addressresid));
			resadd.setModifiedBy(Username);
			resadd.setModifiedDate(sdtf.format(new Date()));
			
			   String comments = (String)request.getParameter("comments");
	    	   MasterEdit masteredit  = new MasterEdit();
	    	   masteredit.setCreatedBy(Username);
	    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    	   masteredit.setTableRowId(Long.parseLong(addressresid));
	    	   masteredit.setComments(comments);
	    	   masteredit.setTableName("pis_address_res");
	    	   
	    	   masterservice.AddMasterEditComments(masteredit);
						
        	  long result  =  service.EditResAddress(resadd); 
        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Residential Address Edit Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Residential Address Edit UnSuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return "redirect:/Address.htm";
   }
   
   
 
   @RequestMapping(value = "KinAddressAddEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
  	public String NextkinAddressAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
  	{   
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside KinAddressAddEdit.htm "+Username);
       try {

    	   String Action = (String) req.getParameter("Action");
    	   String empid =(String)req.getParameter("empid");
    	   
    	   	if("EDITNextKinAdd".equalsIgnoreCase(Action)) {
    	   	
    	   		AddressNextKin nextkinaddress = service.getNextKinrAddressData(empid);
    	   		
    	   		List<Object[]> States = service.getStates();
    	   		 req.setAttribute("nextkinaddress", nextkinaddress);
    	     	 req.setAttribute("Empdata", service.GetEmpData(empid));
 	   		     req.setAttribute("States", States);
    	   	      return "pis/AddEditNextKinAddress";
           }else{
    	   		List<Object[]> States = service.getStates();
    	   	    req.setAttribute("Empdata", service.GetEmpData(empid));
    	   		req.setAttribute("States", States);
    	   	 return "pis/AddEditNextKinAddress";
    	   	}
    	   	
	    } catch (Exception e) {
	 	    e.printStackTrace();
	    }
       
       return "pis/AddEditNextKinAddress";
  		
  	}
   
   @RequestMapping(value = "NextKinAddAddressDetails.htm" , method= RequestMethod.POST)
   public String AddNextKinAddressDetails(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddNextKinAddressDetails.htm "+Username);  
       String Action = (String)req.getParameter("Action");
       try {
    	   String state =(String)req.getParameter("state");
    	   String city  =(String)req.getParameter("city");
    	   String cityPin =(String)req.getParameter("cityPin");
    	   String mobile =(String)req.getParameter("mobile");
    	   String altMobile =(String)req.getParameter("altMobile");
    	   String landineNo =(String)req.getParameter("landineNo");
    	   String fromPer =(String)req.getParameter("fromPer");
    	   String empid =(String)req.getParameter("empid");
    	   String nextKinAdd=(String)req.getParameter("nextKinAdd");
    	   
    	   AddressNextKin kinaddress = new AddressNextKin();
    	   kinaddress.setLandline(landineNo);
    	   kinaddress.setFrom_per_addr(DateTimeFormatUtil.dateConversionSql(fromPer));
    	   kinaddress.setMobile(mobile);
    	   kinaddress.setPin(cityPin);
    	   kinaddress.setState(state);
    	   kinaddress.setCity(city);
    	   kinaddress.setAlt_mobile(altMobile);
    	   kinaddress.setEmpid(empid);
    	   kinaddress.setNextkin_addr(nextKinAdd);
    	   
    	   if("ADD".equalsIgnoreCase(Action)) {
    		   kinaddress.setCreatedBy(Username);
    		   kinaddress.setCreatedDate(sdf.format(new Date()));
        	  long result  =  service.AddNextAddress(kinaddress); 
        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Next Kin Address Add Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Next Kin Address ADD UnSuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
    	   }else if("EDIT".equalsIgnoreCase(Action)) {
    		   String addressid = (String)req.getParameter("addressId");
    		   kinaddress.setAddress_kin_id(Long.parseLong(addressid));
    		   kinaddress.setModifiedBy(Username);
    		   kinaddress.setModifiedDate(sdtf.format(new Date()));
    		   
    		   String comments = (String)req.getParameter("comments");
        	   MasterEdit masteredit  = new MasterEdit();
        	   masteredit.setCreatedBy(Username);
        	   masteredit.setCreatedDate(sdtf.format(new Date()));
        	   masteredit.setTableRowId(Long.parseLong(addressid));
        	   masteredit.setComments(comments);
        	   masteredit.setTableName("pis_address_kin");
        	   
        	   masterservice.AddMasterEditComments(masteredit);
    		      		   
        	   long result  =  service.EditNextKinAddress(kinaddress); 
          	 
	       	    if(result>0) {
	       	    	 redir.addAttribute("result", "Next Kin Address Edit Successfull");	
	       		} else {
	       			 redir.addAttribute("resultfail", "Next Kin Address Edit UnSuccessful");	
	       	    }
	       	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   e.printStackTrace();
		   redir.addAttribute("resultfail", "Some Problem Occure !");	
	  }  
       return "redirect:/Address.htm";
   }
   

   
   @RequestMapping(value = "EmecAddressAddEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
  	public String EmecAddressAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
  	{   
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside EmecAddressAddEdit.htm "+Username);
       try {

    	   String Action = (String) req.getParameter("Action");
    	   String empid =(String)req.getParameter("empid");
    	   
    	   	if("EDITEmecAddress".equalsIgnoreCase(Action)) {
    	 
    	   	AddressEmec emecaddress = service.getEmecAddressData(empid);
    	   		
    	   		List<Object[]> States = service.getStates();
    	   		 req.setAttribute("emecaddress", emecaddress);
    	     	 req.setAttribute("Empdata", service.GetEmpData(empid));
 	   		     req.setAttribute("States", States);
    	   	      return "pis/AddEditEmecAddress";
           }else{
    	   		List<Object[]> States = service.getStates();
    	   	    req.setAttribute("Empdata", service.GetEmpData(empid));
    	   		req.setAttribute("States", States);
    	   	 return "pis/AddEditEmecAddress";
    	   	}
    	   	
	    } catch (Exception e) {
	 	    e.printStackTrace();
	    }
       
       return "pis/AddEditNextKinAddress";
  		
  	}
   
   @RequestMapping(value = "EmecAddAddressDetails.htm" , method= RequestMethod.POST)
   public String AddEmecAddressDetails(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddEmecAddressDetails.htm "+Username);  
       String Action = (String)req.getParameter("Action");
       try {
    	   String state =(String)req.getParameter("state");
    	   String city  =(String)req.getParameter("city");
    	   String cityPin =(String)req.getParameter("cityPin");
    	   String mobile =(String)req.getParameter("mobile");
    	   String altMobile =(String)req.getParameter("altMobile");
    	   String landineNo =(String)req.getParameter("landineNo");
    	   String fromPer =(String)req.getParameter("fromPer");
    	   String empid =(String)req.getParameter("empid");
    	   String EmecAdd=(String)req.getParameter("EmecAdd");
    	   
    	   AddressEmec emecaddress = new AddressEmec();
    	   emecaddress.setLandline(landineNo);
    	   emecaddress.setFrom_per_addr(DateTimeFormatUtil.dateConversionSql(fromPer));
    	   emecaddress.setMobile(mobile);
    	   emecaddress.setPin(cityPin);
    	   emecaddress.setState(state);
    	   emecaddress.setCity(city);
    	   emecaddress.setAlt_mobile(altMobile);
    	   emecaddress.setEmpid(empid);
    	   emecaddress.setEmer_addr(EmecAdd);
    	   
    	   if("ADD".equalsIgnoreCase(Action)) {
    		   emecaddress.setCreatedBy(Username);
    		   emecaddress.setCreatedDate(sdf.format(new Date()));
        	  long result  =   service.AddEmecAddress(emecaddress); 
        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Emergency Address Add Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Emergency Address Add UNSuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
    	   }else if("EDIT".equalsIgnoreCase(Action)) {
    		   String addressid = (String)req.getParameter("addressId");
    		   emecaddress.setAddress_emer_id(Long.parseLong(addressid));
    		   emecaddress.setModifiedBy(Username);
    		   emecaddress.setModifiedDate(sdtf.format(new Date()));
    		   
    		   String comments = (String)req.getParameter("comments");
        	   MasterEdit masteredit  = new MasterEdit();
        	   masteredit.setCreatedBy(Username);
        	   masteredit.setCreatedDate(sdtf.format(new Date()));
        	   masteredit.setTableRowId(Long.parseLong(addressid));
        	   masteredit.setComments(comments);
        	   masteredit.setTableName("pis_address_emer");
        	   
        	   masterservice.AddMasterEditComments(masteredit);

        	   long result  = service.EditEmecAddress(emecaddress); 
          	 
	       	    if(result>0) {
	       	    	 redir.addAttribute("result", "Emergency Address Edit Successfull");	
	       		} else {
	       			 redir.addAttribute("resultfail", "Emergency Address Edit UnSuccessful");	
	       	    }
	       	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   e.printStackTrace();
		   redir.addAttribute("resultfail", "Some Problem Occure !");	
	  }  
       return "redirect:/Address.htm";
   }
   
   
	@RequestMapping(value="ReqEmerAddajax.htm",method=RequestMethod.GET)
	public  @ResponseBody Object[] ReqEmerAddajax(HttpServletRequest req,HttpServletResponse response,HttpSession ses) throws Exception {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ReqEmerAddajax.htm "+Username);  
	       List<Object[]> EmerAddress=null;
	       try { 
	    	   
	    	String empid= req.getParameter("EmerEmpid");
	   	    ServletOutputStream out=response.getOutputStream();	
	   		EmerAddress = service.ReqEmerAddajax(empid);	
	   		
			} catch (Exception e) {
				e.printStackTrace();
			}	
	    	return EmerAddress.toArray();

     }
	
	@RequestMapping(value="AuditStamping.htm" , method= {RequestMethod.GET,RequestMethod.POST})
	 public String AuditStampling(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
		  String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AuditStamping.htm "+Username); 
	       long LoginId = (long) ses.getAttribute("LoginId");
	       try {
	    	   String Usernameparam=req.getParameter("username");
	    	   
				String Fromdate=req.getParameter("fromdate");
				String Todate=req.getParameter("todate");
	    	   List<Object[]> list=null;
	    	  
	    	   req.setAttribute("list", list);
	    	   req.setAttribute("emplist", service.GetEmployeeList());
	    	   
	    		if(Usernameparam == null) {
					
					req.setAttribute("auditstampinglist", service.AuditStampingList(String.valueOf(LoginId) ,Fromdate, Todate));
					req.setAttribute("Fromdate", LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
					req.setAttribute("Todate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
					req.setAttribute("loginid", String.valueOf(LoginId));
					req.setAttribute("Username",(String) ses.getAttribute("EmpName"));
				}else {
				String[] userName=Usernameparam.split("-");	
				req.setAttribute("auditstampinglist", service.AuditStampingList(userName[0],Fromdate, Todate));
				req.setAttribute("Username", userName[1]);
				req.setAttribute("loginid", userName[0]);
				req.setAttribute("Fromdate", Fromdate);
				req.setAttribute("Todate", Todate);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	       return "Admin/AuditStamping";
	}
	
	@RequestMapping(value = "PasswordChange.htm", method = RequestMethod.GET)
	public String PasswordChange(HttpServletRequest req, HttpSession ses) throws Exception {
		
		return "pis/PasswordChange";
	}
	
	@RequestMapping(value = "PasswordChanges.htm", method = RequestMethod.POST)
	public String PasswordChangeSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");	
		logger.info(new Date() +"Inside PasswordChanges.htm "+Username);		
		try {
				long LoginId = (long) ses.getAttribute("LoginId");  
				String NewPassword =req.getParameter("NewPassword");
				String OldPassword =req.getParameter("OldPassword");
				int count  =  service.PasswordChange(OldPassword, NewPassword, String.valueOf(LoginId),Username);
				
				if (count > 0) 
				{
					redir.addAttribute("result", "Password Changed Successfully");
					return "redirect:/MainDashBoard.htm";
				} 
				else 
				{
					redir.addAttribute("resultfail", "Password Change Unsuccessfull ");
					return "redirect:/PasswordChange.htm";
				}
			
			}catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside PasswordChange.htm "+Username, e);
			}

		return "redirect:/MainDashBoard.htm";
		
	}
	
	@RequestMapping(value="Resetpassword.htm" , method=RequestMethod.POST)
	public String Resetpassword(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");	
		logger.info(new Date() +"Inside PasswordChange.htm "+Username);		
		try {
			String loginid = (String) req.getParameter("loginid");
			
			int result=service.ResetPassword(loginid,Username);
			if (result > 0) 
			{
				redir.addAttribute("result", "Reset Password  Successfull");
			}else{
				redir.addAttribute("resultfail", "Reset Password  UnSuccessfull");
			}
			return "redirect:/LoginMaster.htm";
		}catch(Exception e) {
			redir.addAttribute("resultfail", "Some Problem Occure!");
			e.printStackTrace();
			return "redirect:/LoginMaster.htm";
		}		
			
	}
	
	@RequestMapping(value="UpdateEmployeeSeniority.htm" , method=RequestMethod.POST)
	public String UpdateSeniority(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");	
		logger.info(new Date() +"Inside UpdateEmployeeSeniority.htm "+Username);		
		try {
			String action = (String)req.getParameter("action");
			if("UpdateSeniority".equalsIgnoreCase(action)) {
				String empid = (String)req.getParameter("empid");
				Object[]  empdetails = service.GetEmpDetails(empid); 
				req.setAttribute("empdetails", empdetails);
				return "pis/UpdateSeniority";
			}else {
				String empid= req.getParameter("empid");
				String newSeniorityNumber=req.getParameter("UpdatedSrNo");
		        int result= service.UpdateSeniorityNumber(empid,newSeniorityNumber);
				   if(result>0) {
						redir.addAttribute("result", "Emlpoyee Seniority Number Updated Successfully ");
					}
					else {
						redir.addAttribute("resultfail", "Emlpoyee Seniority Number Updated Unsuccessful");
					}					
				   return "redirect:/PisAdminEmpList.htm";
			}
		} catch (Exception e) {
			redir.addAttribute("resultfail", "Internal Error!");
			e.printStackTrace();
			 return "redirect:/PisAdminEmpList.htm";
		}
		
	}

}

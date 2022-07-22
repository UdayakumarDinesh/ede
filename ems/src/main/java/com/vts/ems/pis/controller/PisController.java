package com.vts.ems.pis.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.master.dto.MasterEditDto;
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
import com.vts.ems.pis.model.PisEmpFamilyForm;
import com.vts.ems.pis.model.PisFamFormMembers;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;



@Controller
public class PisController {

	private static final Logger logger = LogManager.getLogger(PisController.class);
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@Autowired
	private PisService service;
	
	@Autowired
	MasterService masterservice;
	
	@Value("${EMSFilesPath}")
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
			ses.setAttribute("SidebarActive","PisAdminEmpList_htm");
			return "pis/EmployeeList";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PisAdminEmpList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "EmployeeDetails.htm", method = {RequestMethod.POST , RequestMethod.GET})
	public String EmployeeDetails(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeDetails.htm "+Username);		
		try {
			
			String empid=req.getParameter("empid");
			if(empid==null)  {
				Map md=model.asMap();
				empid=(String)md.get("empid");
			}
			
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
			String PunchCardNo = req.getParameter("PunchCardNo");
			String idMark = req.getParameter("idMark");
			String empstatus = req.getParameter("empstatus");
			String phoneno = req.getParameter("PhoneNo");
			String AltPhoneno = req.getParameter("AltPhoneno");
			String uanno = req.getParameter("UANNo");
					
			
			Employee employee = new Employee();
			employee.setEmail(email);
			employee.setDivisionId(Long.parseLong(divisionid));
			
			employee.setDesigId(Long.parseLong(Designationid));
			employee.setEmpName(WordUtils.capitalize(empname.trim()));
			employee.setSrNo(Long.parseLong("0"));
            employee.setEmpNo(PunchCardNo.trim());
			employee.setIsActive(1);
			employee.setCreatedBy(Username);
			employee.setExtNo(internalNo);
			EmployeeDetails emp= new EmployeeDetails();
			emp.setTitle(salutation);
			emp.setUANNo(uanno);
			// date conversion
        	java.sql.Date dob = DateTimeFormatUtil.dateConversionSql(req.getParameter("dob"));
			java.sql.Date doj = DateTimeFormatUtil.dateConversionSql(req.getParameter("doj"));
			
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

			} catch (Exception e)
			{
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
			emp.setAltPhoneNo(AltPhoneno);
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
	public String EmployeeEditSubmit(HttpServletRequest req, HttpSession ses, @RequestPart("selectedFile") MultipartFile selectedFile, RedirectAttributes redir) {
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
			String PunchCardNo = req.getParameter("PunchCardNo");
			String idMark = req.getParameter("idMark");
			String empstatus = req.getParameter("empstatus");
			String phno = req.getParameter("PhoneNo");
			String empdetailsid=req.getParameter("empdetailsid");
			String uanno = req.getParameter("UANNo");
			String EmpId= req.getParameter("EmpId");
			String AltPhoneno = req.getParameter("AltPhoneno");
			
			Employee employee=new Employee();
				employee.setEmpNo(PunchCardNo);
				employee.setEmpName(WordUtils.capitalize(empname.trim()));
				employee.setDesigId(Long.parseLong(Designationid));
				employee.setEmail(email);
				employee.setDivisionId(Long.parseLong(divisionid));
				employee.setEmpNo(PunchCardNo.trim());
				employee.setEmpId(Long.parseLong(EmpId));			
				employee.setExtNo(internalNo);
				employee.setModifiedBy(Username);
				employee.setModifiedDate(sdtf.format(new Date()));
				
			EmployeeDetails emp = new EmployeeDetails();
			emp.setTitle(salutation);
			emp.setUANNo(uanno);
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
			emp.setAltPhoneNo(AltPhoneno);
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
			
			MasterEditDto masterdto = new MasterEditDto();
			masterdto.setFilePath(selectedFile);
			
			masterservice.AddMasterEditComments(masteredit ,masterdto);
			if (value != 0) {
				redir.addAttribute("result", "Employee details Edited Successfully");
			} else {
				redir.addAttribute("resultfail", "Employee details Edited Unsuccessfull");
			}
			return "redirect:/PisAdminEmpList.htm";
		} catch (Exception e) {
			logger.error(new Date() + " Inside EmployeeEditSubmit.htm " + Username, e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "/requestbypunchajax", method = RequestMethod.GET)
	public @ResponseBody String PunchRequestData(HttpServletRequest req, HttpServletResponse response, HttpSession ses)throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside requestbypunchajax " + Username);
		String PunchCardNo = req.getParameter("PunchCardNo");

		int result = 0;
		try {
			result = service.PunchcardList(PunchCardNo);
		} catch (Exception e) {
			logger.error(new Date() + "Inside requestbypunchajax " + Username,e);
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
				String path = 	uploadpath + "\\empimages";
					
			File f = new File(path + "\\" + imagename);
			if (f.exists()) {
				f.delete();
			}
			value = service.saveEmpImage(file, empdata[3]+"", path);
			Object[] employeedetails = service.EmployeeDetails(EmpId);
			String basevalue = service.getimage(empdata[3]+"");
			req.setAttribute("empid", EmpId);
			req.setAttribute("employeedetails", employeedetails);
			req.setAttribute("basevalue", basevalue);
			if (value != 0) {
				req.setAttribute("result", "Photo Upload Successful");
			} else {
				req.setAttribute("resultfail", "Photo Upload Unsuccessful");
			}
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("resultfail", "Photo Upload Unsuccessful");
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
			ses.setAttribute("SidebarActive","LoginMaster_htm");
			
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
		logger.info(new Date() + "Inside LoginMasterAddEdit.htm " + Username);
		try {
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
		} catch (Exception e) {
			logger.error(new Date() +" Inside LoginMasterAddEdit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
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
   public String UserManagerEditSubmit(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception
   {	String Username = (String) ses.getAttribute("Username");
        logger.info(new Date() +"Inside UserManagerEditSubmit.htm "+Username);
        try {
			//String Empid     = (String)req.getParameter("Employee");
        	String Logintype = (String)req.getParameter("LoginType");
        	String Loginid   = (String)req.getParameter("loginid");
        	
        	int count =service.UserMangerEdit( Logintype,Username,Loginid);
        	
           String comments = (String)req.getParameter("comments");
     	   MasterEdit masteredit  = new MasterEdit();
     	   masteredit.setCreatedBy(Username);
     	   masteredit.setCreatedDate(sdtf.format(new Date()));
     	   masteredit.setTableRowId(Long.parseLong(Loginid));
     	   masteredit.setComments(comments);
     	   masteredit.setTableName("login");
     	   
     	   MasterEditDto masterdto = new MasterEditDto();
     	   masterdto.setFilePath(selectedFile);
     	   
     	   masterservice.AddMasterEditComments(masteredit,masterdto);

        	if(count>0) {
        		redir.addAttribute("result", "LoginID Edited successfully");	
    		} else {
    			redir.addAttribute("resultfail", "LoginID Edited Unsuccessfull");
        	}
		} catch (Exception e) {
			 logger.error(new Date() +"Inside UserManagerEditSubmit.htm "+Username,e);
			e.printStackTrace();
			return "static/Error";
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
			logger.info(new Date() +"Inside FamilyMembersList.htm "+Username);
			e.printStackTrace();
			 return "static/Error";
		}
        return "pis/FamilyMemberList";
   }

	@RequestMapping(value = "FamilyMemberAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
	public String FamilyMemberAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside FamilyMemberAddEditDelete.htm " + Username);
		
		try {
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
		} catch (Exception e) {
			logger.error(new Date() + "Inside FamilyMemberAddEditDelete.htm " + Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
		

	}
   
   @RequestMapping(value="AddFamilyDetails.htm" ,  method=RequestMethod.POST)  
   public String AddFamilyDetails(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
   {
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside AddFamilyDetails.htm "+Username);
       
       try {
    	   String name = req.getParameter("memberName");
    	   String dob  = req.getParameter("dob");
    	   String relation = req.getParameter("relation");
    	   String benId =  req.getParameter("benId");
    	   String status = req.getParameter("status");
    	   String statusdate = req.getParameter("statusDate");
    	   String bloodgroup = req.getParameter("bloodgroup");
    	   String Phone = req.getParameter("PH");
    	   String medicaldep = req.getParameter("medicaldep");
    	   String medicaldepdate = req.getParameter("medicaldepdate");
    	   String ltcdep  = req.getParameter("ltcdep");
    	   String LTC = req.getParameter("LTC");
    	   String marriagestatus = req.getParameter("married_unmarried");
    	   String emp_unemp = req.getParameter("emp_unemp");
    	   String gender = req.getParameter("Gender");
    	   String empid = req.getParameter("empid");
    	   String memberOccupation = req.getParameter("memberOccupation");
    	   String memberIncome = req.getParameter("memberIncome").trim();
    	  
    	   
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

           if(emp_unemp.equalsIgnoreCase("Y")) {
    	   	   details.setMemberOccupation(memberOccupation);
    	   	   if(memberIncome!=null) {
    	   		   details.setMemberIncome(Long.parseLong(memberIncome));
    	   	   }
           }else {
        	   details.setMemberOccupation(null);
        	   details.setMemberIncome(0l);
           }
           
    	   
    	   
    	   details.setIsActive(1);
    	   details.setCreatedBy(Username);
    	   details.setCreatedDate(sdf.format(new Date()));
    	   if(emp_unemp.equalsIgnoreCase("Y")) 
    	   {
    		   details.setEmpStatus((String)req.getParameter("EmpStatus"));
    	   }
    	   Long result = service.AddFamilyDetails(details);
    	   if(result>0){
    		   redir.addAttribute("result", "Family Member Details Saved Successfully");	
   		   } else {
   			redir.addAttribute("resultfail", "Family Member Details Saved Unsuccessful");
    	   }
    	  
    	   redir.addFlashAttribute("Employee", empid);
    	   
    	   
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside AddFamilyDetails.htm "+Username,e);
	  	 e.printStackTrace();
	  	return "static/Error";
	  }
       return "redirect:/FamilyMembersList.htm";
   }
   
   
   @RequestMapping(value="EditFamilyDetails.htm" , method=RequestMethod.POST)
   public String MembereEdit(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
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
    	   String memberOccupation = req.getParameter("memberOccupation");
    	   String memberIncome = req.getParameter("memberIncome").trim();
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
    	   if(emp_unemp.equalsIgnoreCase("Y")) {
	    	   details.setMemberOccupation(memberOccupation);
	    	   if(memberIncome!=null) {
	    		   details.setMemberIncome(Long.parseLong(memberIncome));
	    	   }
    	   }else {
    		   details.setMemberOccupation(null);
    		   details.setMemberIncome(0l);
    	   }
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
    	  
    	   MasterEditDto masterdto = new MasterEditDto();
    	   masterdto.setFilePath(selectedFile);
    	   
    	   masterservice.AddMasterEditComments(masteredit , masterdto);
    	   
    	   Long result = service.EditFamilyDetails(details);
    	   if(result>0){
    		   redir.addAttribute("result", "Family member Details Edited sucessfully");	
   		   } else {
   			   redir.addAttribute("resultfail", "Family Member Details Edited Unsuccessful");
    	   }
    	  
    	   redir.addFlashAttribute("Employee", empid);   
    	   
	      } catch (Exception e) {
	    	  logger.error(new Date() + " Inside EditFamilyDetails.htm " + Username, e);
				e.printStackTrace();
				return "static/Error";
	      }
       return "redirect:/FamilyMembersList.htm";
   }
   
   @RequestMapping(value="Address.htm"  , method= {RequestMethod.GET,RequestMethod.POST})
   public String AddressDashboard(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
   {
	
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside Address.htm "+Username);
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
		   logger.error(new Date() +"Inside Address.htm "+Username,e);
		  e.printStackTrace();
		  return "static/Error";
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
	    	 logger.error(new Date() +"Inside AddEditPerAddress.htm "+Username ,e);
	 	    e.printStackTrace();
	 	    return "static/Error";
	    }
      
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
        			 redir.addAttribute("resultfail", "Parmanent Address Add Unsuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside AddPerAddressDetails.htm "+Username,e);  
		   e.printStackTrace();
		  return "static/Error";
	  }  
       return "redirect:/Address.htm";
   }
   
   
   @RequestMapping(value = "EditAddressDetails.htm" , method= RequestMethod.POST)
   public String EditPerAddressDetails(HttpServletRequest req , HttpSession ses , @RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside EditAddressDetails.htm "+Username);  
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
    	   
    	 if("EDIT".equalsIgnoreCase(Action)) {
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
        	   
        	   MasterEditDto masterdto = new MasterEditDto();
        	   masterdto.setFilePath(selectedFile);
        	   masterservice.AddMasterEditComments(masteredit , masterdto);

        	   long result  =  service.EditPerAddress(peraddress); 
          	 
	       	    if(result>0) {
	       	    	 redir.addAttribute("result", "Parmanent Address Edit Successfull");	
	       		} else {
	       			 redir.addAttribute("resultfail", "Parmanent Address Edit Unsuccessful");	
	       	    }
	       	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside EditAddressDetails.htm "+Username,e); 
		   e.printStackTrace();
		  return "static/Error";
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
		try {
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
				redir.addAttribute("resultfail", "Residential Address Delete Unsuccessful");
			}
			redir.addFlashAttribute("Employee", empid);
			return "redirect:/Address.htm";
		}
		} catch (Exception e) {
			logger.error(new Date() +"Inside ResAddressAddEdit.htm "+Username,e); 
			e.printStackTrace();
			return "static/Error";
		}
		
		
	}
           
   
   @RequestMapping(value="AddResAddressDetails.htm" , method= {RequestMethod.POST,RequestMethod.GET})
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
			String empid= request.getParameter("empid"); 
			
					
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
        			 redir.addAttribute("resultfail", "Residential Address ADD Unsuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
		}
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside AddResAddressDetails.htm "+Username ,e);  
			e.printStackTrace();
			return "static/Error";
		}
	   return "redirect:/Address.htm";
   }
   
   @RequestMapping(value="EditResAddressDetails.htm" , method= {RequestMethod.POST,RequestMethod.GET})
   public String EditResAddressDetails(HttpServletRequest request , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
	  
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside EditResAddressDetails.htm "+Username);  
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
		
			
	 if ("EDIT".equalsIgnoreCase(Action)) {
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
	    	   
	    	   MasterEditDto masterdto = new MasterEditDto();
	    	   masterdto.setFilePath(selectedFile);
	    	   masterservice.AddMasterEditComments(masteredit , masterdto);
						
        	  long result  =  service.EditResAddress(resadd); 
        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Residential Address Edit Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Residential Address Edit Unsuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
		}
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside EditResAddressDetails.htm "+Username ,e);
			e.printStackTrace();
			 return "static/Error";
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
	    	logger.error(new Date() +"Inside KinAddressAddEdit.htm "+Username,e);
	 	    e.printStackTrace();
	 	    return "static/Error";
	    }
  		
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
        			 redir.addAttribute("resultfail", "Next Kin Address ADD Unsuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside AddNextKinAddressDetails.htm "+Username,e);  
		   e.printStackTrace();
		   return "static/Error";
	  }  
       return "redirect:/Address.htm";
   }
   
   @RequestMapping(value = "NextKinEditAddressDetails.htm" , method= RequestMethod.POST)
   public String EditNextKinAddressDetails(HttpServletRequest req , HttpSession ses , @RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
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
    	   
    	 if("EDIT".equalsIgnoreCase(Action)) {
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
        	   
        	   MasterEditDto masterdto = new MasterEditDto();
        	   masterdto.setFilePath(selectedFile);
        	   
        	   masterservice.AddMasterEditComments(masteredit , masterdto);
    		      		   
        	   long result  =  service.EditNextKinAddress(kinaddress); 
          	 
	       	    if(result>0) {
	       	    	 redir.addAttribute("result", "Next Kin Address Edit Successfull");	
	       		} else {
	       			 redir.addAttribute("resultfail", "Next Kin Address Edit Unsuccessful");	
	       	    }
	       	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside AddNextKinAddressDetails.htm "+Username ,e);
		   e.printStackTrace();
		   return "static/Error";	
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
	    	logger.error(new Date() +"Inside EmecAddressAddEdit.htm "+Username,e);
	 	    e.printStackTrace();
	 	    return "static/Error";
	    }	
  	}
   
   @RequestMapping(value = "EmecAddAddressDetails.htm" , method= RequestMethod.POST)
   public String AddEmecAddressDetails(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside EmecAddAddressDetails.htm "+Username);  
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
        			 redir.addAttribute("resultfail", "Emergency Address Add Unsuccessful");	
        	    }
        	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside EmecAddAddressDetails.htm "+Username,e);
		   e.printStackTrace();
		  return "static/Error";
	  }  
       return "redirect:/Address.htm";
   }
   
   @RequestMapping(value = "EmecEditAddressDetails.htm" , method= RequestMethod.POST)
   public String EditEmecAddressDetails(HttpServletRequest req , HttpSession ses , @RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
	   String Username = (String) ses.getAttribute("Username");
       logger.info(new Date() +"Inside EmecEditAddressDetails.htm "+Username);  
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
    	   
    	    if("EDIT".equalsIgnoreCase(Action)) {
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
        	   
        	   MasterEditDto masterdto = new MasterEditDto();
        	   masterdto.setFilePath(selectedFile);
        	   masterservice.AddMasterEditComments(masteredit , masterdto);

        	   long result  = service.EditEmecAddress(emecaddress); 
          	 
	       	    if(result>0) {
	       	    	 redir.addAttribute("result", "Emergency Address Edit Successfull");	
	       		} else {
	       			 redir.addAttribute("resultfail", "Emergency Address Edit Unsuccessful");	
	       	    }
	       	    redir.addFlashAttribute("Employee", empid);
    	   }
    	    
	   } catch (Exception e) {
		   logger.error(new Date() +"Inside EmecEditAddressDetails.htm "+Username , e); 
		   e.printStackTrace();
		   return "static/Error";
	  }  
       return "redirect:/Address.htm";
   }
   
   
	@RequestMapping(value="ReqEmerAddajax.htm",method=RequestMethod.GET)
	public  @ResponseBody Object[] ReqEmerAddajax(HttpServletRequest req,HttpServletResponse response,HttpSession ses) throws Exception 
	{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ReqEmerAddajax.htm "+Username);  
	       List<Object[]> EmerAddress=null;
	       try { 
	    	   
	    	String empid= req.getParameter("EmerEmpid");
	   	    ServletOutputStream out=response.getOutputStream();	
	   		EmerAddress = service.ReqEmerAddajax(empid);	
	   		
			} catch (Exception e) {
				logger.error(new Date() +"Inside ReqEmerAddajax.htm "+Username ,e);
				e.printStackTrace();
			}	
	    	return EmerAddress.toArray();
     }
	
	@RequestMapping(value="AuditStamping.htm" , method= {RequestMethod.GET,RequestMethod.POST})
	public String AuditStampling(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	{
		  String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AuditStamping.htm "+Username); 
	       String LoginId = String.valueOf((long) ses.getAttribute("LoginId"));
	       try {
	    	   String LoginType = (String) ses.getAttribute("LoginType");
	    	   
	    	   String loginid=req.getParameter("loginid");
	    	   String Fromdate=req.getParameter("fromdate");
	    	   String Todate=req.getParameter("todate");
	    	
	    	   if(Fromdate == null || Todate == null) 
	    	   { 
	    		   Todate = LocalDate.now().toString();
	    		   Fromdate= LocalDate.now().minusMonths(1).toString(); 
	    	   }
	    	   else { 
					 
	    		   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    		   Fromdate = LocalDate.parse(Fromdate,formatter).toString();
	    		   Todate = LocalDate.parse(Todate,formatter).toString();
				 
	    	   }
	    	   
	    	   if(LoginType.equalsIgnoreCase("A")) 
	    	   {
	    		   if(loginid==null) {
	    			   loginid = "0";
	    		   }
	    		   req.setAttribute("emplist", service.GetEmployeeList());
		    	   req.setAttribute("auditstampinglist", service.AuditStampingList(loginid,Fromdate, Todate));
		    	  
	    	   }else
	    	   {
	    		   loginid = LoginId;
	    		   req.setAttribute("emplist", service.GetEmployeeLoginData(LoginId));
		    	   req.setAttribute("auditstampinglist", service.AuditStampingList(loginid,Fromdate, Todate));
		    	   
	    	   }
	    	   
	    	   req.setAttribute("loginid", loginid);
	    	   req.setAttribute("Fromdate", Fromdate);
	    	   req.setAttribute("Todate", Todate);
	    	   req.setAttribute("LoginType", LoginType);

	    	   
			} catch (Exception e) {
				logger.error(new Date() +"Inside AuditStamping.htm "+Username,e);
				e.printStackTrace();
				return "static/Error";
			}
	
	       return "Admin/AuditStamping";
	}
		
		@RequestMapping(value = "PasswordChange.htm", method = RequestMethod.GET)
		public String PasswordChange(HttpServletRequest req, HttpSession ses) throws Exception {
			
			req.setAttribute("ForcePwd", "N");
			return "pis/PasswordChange";
		}
		
		@RequestMapping(value = "PasswordChanges.htm", method = RequestMethod.POST)
		public String PasswordChangeSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception 
		{
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
				return "static/Error";
			}	
		
		}
		
		@RequestMapping(value = "NewPasswordChangeCheck.htm")
		public @ResponseBody String NewPasswordChangeCheck(HttpServletRequest req, HttpServletResponse response, HttpSession ses)throws Exception 
		{
			String Username = (String) ses.getAttribute("Username");
			long LoginId = (long) ses.getAttribute("LoginId");
			logger.info(new Date() + "Inside requestbypunchajax " + Username);

			int retVar = 0;
			try {
				String oldpass = req.getParameter("oldpass");
				String loginid = String.valueOf(LoginId);
				String ExistingPassword = service.ExistingPassword(loginid);
				boolean result =encoder.matches(oldpass, ExistingPassword);
				if(result) {
					retVar=1;
				}
				
			} catch (Exception e) {
				logger.error(new Date() + "Inside requestbypunchajax " + Username,e);
				e.printStackTrace();
			}
			Gson json = new Gson();
			return json.toJson(retVar);
		}
	 	
		
		
		@RequestMapping(value="Resetpassword.htm" , method=RequestMethod.POST)
		public String Resetpassword(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");	
			logger.info(new Date() +"Inside Resetpassword.htm "+Username);		
			try {
				String loginid = (String) req.getParameter("loginid");
				
				int result=service.ResetPassword(loginid,Username);
				if (result > 0) 
				{
					redir.addAttribute("result", "Reset Password  Successfull");
				}else{
					redir.addAttribute("resultfail", "Reset Password  Unsuccessfull");
				}
				return "redirect:/LoginMaster.htm";
			}catch(Exception e) {
	
				logger.error(new Date() +"Inside PasswordChange.htm "+Username,e);
				e.printStackTrace();
				return "static/Error";
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
			logger.error(new Date() +"Inside UpdateEmployeeSeniority.htm "+Username,e);	
			e.printStackTrace();
			 return "static/Error";
		}
		
	}

	
		@RequestMapping(value = "FamilyMemDataAjax.htm", method = RequestMethod.GET)
		public @ResponseBody EmpFamilyDetails FamilyMemDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside FamilyMemDataAjax.htm "+Username);
			EmpFamilyDetails modal=new EmpFamilyDetails();
			try {
				String familydetailsid = req.getParameter("familydetailsid");
				modal = service.getFamilyMemberModal(familydetailsid);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside FamilyMemDataAjax.htm "+Username, e);
			}
			return modal;
		}
		
	@RequestMapping(value = "EmpBloodGropuEdit.htm" , method= {RequestMethod.POST})
		public String EmpBloodGropuEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{   
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside EmpBloodGropuEdit.htm " + Username);
			try {
				String bloodgroup =req.getParameter("bloodgroup");
				String empid = req.getParameter("empid");
				String empno = req.getParameter("empno");
			
				int count = service.EmpBloodGropuEdit(empno, bloodgroup);
				if (count > 0) {
					redir.addAttribute("result", "Blood Group updated Successfully");
				} else {
					redir.addAttribute("resultfail", "Blood Group update Unsuccessfull");
				}
				
				redir.addFlashAttribute("empid",empid);
				return "redirect:/EmployeeDetails.htm";
			
			} catch (Exception e) {
				logger.error(new Date() +" Inside EmpBloodGropuEdit.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
			
		}
		
	
		public static String saveFile(String ToFilePath, String fileFullName, MultipartFile multipartFile) throws IOException 
		{
		 	Path uploadPath = Paths.get(ToFilePath);
	         if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	        
	        String fileName = fileFullName.substring(0, fileFullName.lastIndexOf("."));
	        String ext = fileFullName.substring(fileFullName.lastIndexOf('.')+1);
	      
	        String destinationFilePath = uploadPath+"\\"+fileName+"."+ext;
	        
	        try (InputStream inputStream = multipartFile.getInputStream())
	        {
	        	int count=1;
	        	
	        	File tempFile  = new File(destinationFilePath);
	        	if(tempFile.exists())
	        	{
	        	    while(true)
	        	    {
	        	    	destinationFilePath = uploadPath+"\\"+fileName+"("+count+")."+ext;
	        	    	tempFile  = new File(destinationFilePath);
	        	        if(!tempFile.exists())
	        	        {
	        	            break;
	        	        }
	        	        else
	        	        {
	        	            count++;
	        	        }
	        	    }
	        	}
	        	Files.copy(inputStream, Paths.get(destinationFilePath), StandardCopyOption.REPLACE_EXISTING);
	        	
	        	return destinationFilePath;
	        }catch (Exception ioe) {
	        	ioe.printStackTrace();
	        	return null;
			}
	       
		}
	
	
		@RequestMapping(value="FamIncExcFwdList.htm" )
		public String FamIncExcFwdList(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");	
			logger.info(new Date() +"Inside FamIncExcFwdList.htm "+Username);		
			try {
				String empid = ((Long) ses.getAttribute("EmpId")).toString();	
				
				ses.setAttribute("SidebarActive", "FamIncExcFwdList_htm");
				req.setAttribute("formslist",service.EmpFamFormsList(empid, ""));
				req.setAttribute("empid", empid);
				return "pis/FamIncExcFormsList";
			} catch (Exception e) {
				logger.error(new Date() +" Inside FamIncExcFwdList.htm "+Username, e);
				e.printStackTrace();
				return "static/Error";
			}
			
		}
		
	 	@RequestMapping(value="FamFormsApproveList.htm" )
		public String FamFormsApproveList(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");	
			logger.info(new Date() +"Inside FamFormsApproveList.htm "+Username);		
			try {
				ses.setAttribute("SidebarActive", "FamFormsApproveList_htm");
				req.setAttribute("FamFwdFormsList", service.FamMemFwdEmpList());
				
				return "pis/FamIncExcFormsApproveList";
			} catch (Exception e) {
				logger.error(new Date() +" Inside FamFormsApproveList.htm "+Username, e);
				e.printStackTrace();
				return "static/Error";
			}
			
		}
	 	
	 	
	 	@RequestMapping(value ="DependentExcForm.htm" , method = {RequestMethod.GET, RequestMethod.POST} )
		public void DependentDeleteForm(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DependentExcForm.htm "+Username);
			try {
				String formid = req.getParameter("formid");
				
				Object[] formdata = service.GetFamFormData(formid);
				
				String empid = formdata[1].toString();
				
				req.setAttribute("formdetails" , formdata);
				req.setAttribute("empdetails",service.getEmployeeInfo(empid) );				
				req.setAttribute("employeeResAddr",service.employeeResAddr(empid) );
				req.setAttribute("FamilymemDropdown",service.EmpFamMembersListMedDep(empid));
				req.setAttribute("ExcMemberDetails",service.GetExcFormMembersList(formid));
				
				req.setAttribute("relationtypes" , service.familyRelationList() );				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
//				return "pis/DependentFormExc";
				
				String filename="Dependent Exclusion Form";
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
					        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pis/DependentFormExc.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")); 
		        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf", (String) ses.getAttribute("LabCode"));
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
							
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DependentExcForm.htm "+Username, e);
//				return "static/Error";
			}
			
		}
	 	
	 	@RequestMapping(value="FamilyFormForwardRet.htm" )
		public String FamilyFormForwardRet(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			logger.info(new Date() +"Inside FamilyFormForwardRet.htm "+Username);		
			try {
				String formid = req.getParameter("formid");
				String action = req.getParameter("action");
				String remarks = req.getParameter("remarks");
				
				long result= service.FamilyMemDetailsForward(formid,action,Username,EmpId,remarks);
				if(result>0) 
				{
					if(action.equalsIgnoreCase("F")) {
						redir.addAttribute("result", "Form Forwarded Successfully ");
					}if(action.equalsIgnoreCase("R")) {
						redir.addAttribute("result", "Form  Returned Successfully ");
					}if(action.equalsIgnoreCase("A")) {
						redir.addAttribute("result", "Form  Approved Successfully ");
					}
				}
				else 
				{
					if(action.equalsIgnoreCase("F")) {
						redir.addAttribute("resultfail", "Form Forward Unsuccessful");
					}if(action.equalsIgnoreCase("R")) {
						redir.addAttribute("resultfail", "Form Return Unsuccessful");
					}if(action.equalsIgnoreCase("A")) {
						redir.addAttribute("resultfail", "Form Approval Unsuccessful");
					}
				}		
				
				if(action.equalsIgnoreCase("F")) {
					return "redirect:/FamIncExcFwdList.htm";
				}else
				{
					return "redirect:/FamFormsApproveList.htm";
				}
			} catch (Exception e) {
				logger.error(new Date() +" Inside FamilyFormForwardRet.htm "+Username, e);
				e.printStackTrace();
				return "static/Error";
			}
		}
		
	
	 	@RequestMapping(value ="DepInclusionFormView.htm" , method = {RequestMethod.GET, RequestMethod.POST} )
		public String DepInclusionFormView(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepInclusionFormView.htm "+Username);
			try {
				
				String empid =""; //req.getParameter("empid");
				String formid = req.getParameter("formid");
				String isapproval = req.getParameter("isApprooval");
				if(formid==null)  {
					Map md=model.asMap();
					formid=(String)md.get("formid");
	//				empid=(String)md.get("empid");
					isapproval ="N";
				}
				
				if(formid==null) 
				{
					return "static/Error";
				}
				
				Object[] formdata = service.GetFamFormData(formid);
				if(formdata!=null) {
					empid = formdata[1].toString();
				}else
				{
					empid = ((Long) ses.getAttribute("EmpId")).toString();
				}
				
				
				req.setAttribute("formdetails" , formdata);
				req.setAttribute("FwdMemberDetails",service.GetFormMembersList(formid));
				req.setAttribute("empdetails",service.getEmployeeInfo(empid) );
				req.setAttribute("employeeResAddr",service.employeeResAddr(empid) );
				req.setAttribute("relationtypes" , service.familyRelationList() );
				
				req.setAttribute("FamilymemDropdown" , service.EmpFamMembersNotMedDep(empid));
				
				req.setAttribute("isApprooval" , isapproval );
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				return "pis/DependentFormIncView";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepInclusionFormView.htm "+Username, e);
				return "static/Error";
			}
			
		}
	
	
	 	@RequestMapping(value ="ExistingMemAddSubmit.htm" , method =  RequestMethod.POST )
		public String ExistingMemAddSubmit(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestPart("mem-attach") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String empid = ((Long) ses.getAttribute("EmpId")).toString();	
			logger.info(new Date() +"Inside DepMemAddSubmit.htm "+Username);
			try {
				String familydetailsid = req.getParameter("familydetailsid");
		    	String IncDate  = req.getParameter("mem-IncDate");
		    	String comment  = req.getParameter("mem-comment");
		    	String IncFormId  = req.getParameter("incformid");
		    	
		    	EmpFamilyDetails member =  service.getFamilyMemberModal(familydetailsid);
		    	member.setMed_dep_from(DateTimeFormatUtil.dateConversionSql(IncDate));
		    	member.setMed_dep("N");
		    	service.EditFamilyDetails(member);
		    	
		    	
		    	String filepath=null;
		    	if(IncAttachment.getOriginalFilename()!=null  && IncAttachment.getSize()>0) 
		    	{
		    		filepath = saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
		    	}
		    	else if(IncAttachment.getOriginalFilename()!=null && IncAttachment.getSize()==0)
		    	{
		    		redir.addAttribute("resultfail", "Invalid File, Please Check the file");
		    		redir.addFlashAttribute("formid",IncFormId);
			    	
					return "redirect:/DepInclusionFormView.htm";
		    	}
		    	
		    	if(IncFormId.equals("0")) 
		        {
		        	PisEmpFamilyForm form = new PisEmpFamilyForm();
		        	form.setEmpid(Long.parseLong(empid));
		        	form.setFormStatus("C");
		        	form.setFormType("I");
		        	form.setIsActive(1);
	//	        	details.setIncFormId(service.EmpFamilyFormAdd(form));
		        	IncFormId = String.valueOf(service.EmpFamilyFormAdd(form));
		        	
		        }
	    	   
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFamilyFormId(Long.parseLong(IncFormId));
		    	formmember.setFamilyDetailsId(Long.parseLong(familydetailsid));
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(IncDate));
		    	formmember.setComments(comment);
		    	formmember.setAttachFilePath(filepath);
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	long count=service.PisFamFormMembersAdd(formmember);
		    	
		    	
		    	if(count>0){
		    		redir.addAttribute("result", "Adding Member to Form Successfully");	
		   		} else {
		   			redir.addAttribute("resultfail", "Adding Member to Form Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",IncFormId);
		    	
				return "redirect:/DepInclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ExistingMemAddSubmit.htm "+Username, e);
				return "static/Error";
			}
			
		}
		
	
	
		
		@RequestMapping(value ="DepMemAddSubmit.htm" , method =  RequestMethod.POST )
		public String DepMemAddSubmit(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestPart("mem-attach") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepMemAddSubmit.htm "+Username);
			try {
				String empid = ((Long) ses.getAttribute("EmpId")).toString();			
				String name = req.getParameter("mem-name");
		    	String relation  = req.getParameter("mem-relation");
		    	String dob  = req.getParameter("mem-dob");
		    	String occupation =  req.getParameter("mem-occupation").trim();
		    	String income = req.getParameter("mem-income");
		    	String comment = req.getParameter("mem-comment");
		    	String IncDate = req.getParameter("mem-IncDate");
		    	
		    	Object[] empdetails = service.EmployeeDetails(empid);
		    	Object[] relationdata = service.RelationshipData(relation);
		    	
		    	String filepath =  null;	    	
				EmpFamilyDetails details = new EmpFamilyDetails();
		    	 
				details.setMember_name(WordUtils.capitalize(name.trim()));
		    	details.setDob(DateTimeFormatUtil.dateConversionSql(dob));
		    	details.setRelation_id(Integer.parseInt(relation));
		    	details.setEmpid(empid);
		    	details.setCghs_ben_id(empdetails[2].toString());
		    	details.setFamily_status_id(1);
		    	details.setPH("N"); 
		    	details.setBlood_group("Not Available");
		    	details.setGender(relationdata[2].toString());
		    	
	//	    	details.setStatus_from(new Date());
		    	details.setMed_dep("Y");
		    	details.setMed_dep_from(DateTimeFormatUtil.dateConversionSql(IncDate));
		    	details.setLtc_dep("N");
	//	    	details.setLtc_dep_from(DateTimeFormatUtil.dateConversionSql(LTC));
		    	
		    	List<String> relidlist = Arrays.asList("1","2","3","4","5","8","11","12","15","16");
		    	if( relidlist.contains(relation.trim()))
		    	{
		    		details.setMar_unmarried("Y");
		    	}
		    	else
		    	{
		    		details.setMar_unmarried("N");
		    	}
		    	
		    	if(occupation!=null && !occupation.equals("") && Long.parseLong(income)>0 ) 
		    	{
		    		details.setEmp_unemp("Y");
		    		details.setEmpStatus("Private");
		    		details.setMemberOccupation(occupation);
		    		details.setMemberIncome(Long.parseLong(income));
		    	}
		    	else
		    	{
		    		details.setEmp_unemp("N");
		    	}
		    	details.setCreatedBy(Username);
		    	details.setCreatedDate(sdtf.format(new Date()));  
		    	details.setIsActive(0);
		    	
		    	
		    	String IncFormId = req.getParameter("formid");
		        if(IncFormId.equals("0")) 
		        {
		        	PisEmpFamilyForm form = new PisEmpFamilyForm();
		        	form.setEmpid(Long.parseLong(empid));
		        	form.setFormStatus("C");
		        	form.setFormType("I");
		        	form.setIsActive(1);
	//	        	details.setIncFormId(service.EmpFamilyFormAdd(form));
		        	IncFormId = String.valueOf(service.EmpFamilyFormAdd(form));
		        	
		        }
	//	        else
	//	        {
	//	        	details.setIncFormId(Long.parseLong(IncFormId));
	//	        }
		    	
	//	    	details.setIncComment(comment);
		  
		    	if(IncAttachment.getOriginalFilename()!=null  && IncAttachment.getSize()>0) 
		    	{
	//	    		details.setIncFilePath(saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment));
		    		filepath = saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
		    	}
		    	else if(IncAttachment.getOriginalFilename()!=null && IncAttachment.getSize()==0)
		    	{
		    		redir.addAttribute("resultfail", "Invalid File, Please Check the file");
		    		redir.addFlashAttribute("formid",IncFormId);
			    	
					return "redirect:/DepInclusionFormView.htm";
		    	}
		    	long familydetailsid = service.AddFamilyDetails(details);
	    	   
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFamilyFormId(Long.parseLong(IncFormId));
		    	formmember.setFamilyDetailsId(familydetailsid);
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(IncDate));
		    	formmember.setComments(comment);
		    	formmember.setAttachFilePath(filepath);
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	service.PisFamFormMembersAdd(formmember);
		    	
		    	if(familydetailsid>0){
		    		redir.addAttribute("result", "Family Member Details Saved Successfully");	
		   		} else {
		   			redir.addAttribute("resultfail", "Family Member Details Saved Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",IncFormId);
		    	
				return "redirect:/DepInclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepMemAddSubmit.htm "+Username, e);
				return "static/Error";
			}
			
		}

	 	@RequestMapping(value ="DepMemEditSubmit.htm" , method =  RequestMethod.POST )
		public String DepMemEditSubmit(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestPart("mem-attach-edit") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepMemEditSubmit.htm "+Username);
			try {
							
				String empid = ((Long) ses.getAttribute("EmpId")).toString();			
				
				String familydetailsid = req.getParameter("familydetailsid");
				
				String name = req.getParameter("mem-name");
		    	String relation  = req.getParameter("mem-relation");
		    	String dob  = req.getParameter("mem-dob");
		    	String occupation =  req.getParameter("mem-occupation").trim();
		    	String income = req.getParameter("mem-income");
		    	String comment = req.getParameter("mem-comment");
		    	String IncDate = req.getParameter("mem-IncDate");
		    	
		    	
		    	Object[] empdetails = service.EmployeeDetails(empid);
		    	Object[] relationdata = service.RelationshipData(relation);
		    	   
				EmpFamilyDetails details = new EmpFamilyDetails();
		    	 
				details.setMember_name(WordUtils.capitalize(name.trim()));
		    	details.setDob(DateTimeFormatUtil.dateConversionSql(dob));
		    	details.setRelation_id(Integer.parseInt(relation));
		    	details.setEmpid(empid);
		    	details.setCghs_ben_id(empdetails[2].toString());
		    	details.setFamily_status_id(1);
		    	details.setPH("N");
		    	details.setBlood_group("Not Available");
		    	details.setGender(relationdata[2].toString());
		    	
//		    	details.setStatus_from(new Date());
		    	details.setMed_dep("Y");
		    	details.setMed_dep_from(DateTimeFormatUtil.dateConversionSql(IncDate));
		    	details.setLtc_dep("N");
//		    	details.setLtc_dep_from(DateTimeFormatUtil.dateConversionSql(LTC));
		    	
		    	List<String> relidlist = Arrays.asList("1","2","3","4","5","8","11","12","15","16");
		    	if( relidlist.contains(relation.trim()))
		    	{
		    		details.setMar_unmarried("Y");
		    	}
		    	else
		    	{
		    		details.setMar_unmarried("N");
		    	}
		    	
		    	if(occupation!=null && !occupation.equals("") && Long.parseLong(income)>0 ) 
		    	{
		    		details.setEmp_unemp("Y");
		    		details.setEmpStatus("Private");
		    		details.setMemberOccupation(occupation);
		    		details.setMemberIncome(Long.parseLong(income));
		    	}
		    	else
		    	{
		    		details.setEmp_unemp("N");
		    	}
		    	details.setCreatedBy(Username);
		    	details.setCreatedDate(sdtf.format(new Date()));  
		    	details.setIsActive(0);
		    	
//		    	details.setIncComment(comment);
		    	details.setFamily_details_id(Long.parseLong(familydetailsid));
		    	
		    	String IncFormId = req.getParameter("formid");
		    	String formmemberid = req.getParameter("formmemberid");
		    	
		    	Object[] famMemberdata = service.getMemberdata(formmemberid);
		    	String filepath = null;
		    	
		    	
		    		if(!IncAttachment.isEmpty()) 
			    	{
//			    		details.setIncFilePath(saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment));
			    		filepath = saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
			    		if(famMemberdata[10]!=null) {
			    			new File(famMemberdata[10].toString()).delete();
			    		}
			    	}
			    	else if(IncAttachment.isEmpty())
			    	{
			    		filepath = famMemberdata[10].toString();
			    	}
		    		
		    	
		    	
		    	Long result = service.DepMemEditSubmit(details);
		    	 
		    	
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFormMemberId(Long.parseLong(formmemberid));
		    	formmember.setFamilyFormId(Long.parseLong(IncFormId));
		    	formmember.setFamilyDetailsId(result);
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(IncDate));
		    	formmember.setComments(comment);
		    	formmember.setAttachFilePath(filepath);
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	service.PisFamFormMemberEdit(formmember);
		    	
		    	
		    	
		    	if(result>0){
		    		redir.addAttribute("result", "Family Member Details Updated Successfully");	
		   		} else {
		   			redir.addAttribute("resultfail", "Family Member Details Update Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",req.getParameter("formid"));
				return "redirect:/DepInclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepMemEditSubmit.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	
	 	@RequestMapping(value ="FormFamilyMemberDelete.htm" , method =  RequestMethod.POST )
		public String FormFamilyMemberDelete(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside FormFamilyMemberDelete.htm "+Username);
			try {
				String formmemberid = req.getParameter("formmemberid");
				
				long count = service.FormFamilyMemberDelete(formmemberid);
				
				if(count>0) {
					redir.addAttribute("result", "Member Removed From the Form Successfully ");
				}
				else {
					redir.addAttribute("resultfail", "Member Removal Unsuccessful");
				}		
				
				redir.addFlashAttribute("formid",req.getParameter("formid"));
				return "redirect:/DepInclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside FormFamilyMemberDelete.htm "+Username, e);
				return "static/Error";
			}
			
		} 	
	 	
	 	@RequestMapping(value = "FamIncExcAttachDownload.htm", method = RequestMethod.POST)
		public void FamIncExcAttachDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside FamIncExcAttachDownload.htm "+UserId);
			try {	
				String formmemberid=req.getParameter("formmemberid");
				Object[] Memberdata = service.getMemberdata(formmemberid) ;
				String path= Memberdata[10].toString().trim();
				
                Path filepath=Paths.get(path);
                res.setContentType("Application/octet-stream");
                res.setHeader("Content-disposition","attachment;filename="+filepath.getFileName() ); 
                File f=new File(path);
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
			}
			catch (Exception e)
			{
				e.printStackTrace();  
				logger.error(new Date() +" Inside FamIncExcAttachDownload.htm "+UserId, e);
			}
		}
	 	
	 	
	 	@RequestMapping(value ="DependentIncForm.htm" , method = {RequestMethod.GET, RequestMethod.POST} )
		public void DependentIncForm(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DependentIncForm.htm "+Username);
			try {
				String empid = req.getParameter("empid");
				String formid = req.getParameter("formid");
				
				Object[] formdata = service.GetFamFormData(formid);
				req.setAttribute("formdetails" , formdata);
				req.setAttribute("FwdMemberDetails",service.GetFormMembersList(formid));
				req.setAttribute("empdetails",service.getEmployeeInfo(empid) );
				req.setAttribute("employeeResAddr",service.employeeResAddr(empid) );
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String filename="Dependent Addition Form";
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path); //DependentIncFormView.js
					        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pis/DependentFormInc.jsp").forward(req, customResponse);
				String html1 = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html1,new FileOutputStream(path+File.separator+filename+".pdf")); 
		        
		        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
		        
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
				
				
//				return "pis/DependentAddForm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DependentIncForm.htm "+Username, e);
//				return "static/Error";
			}
			
		}
	 	
	 	
	 	@RequestMapping(value ="DepExclusionFormView.htm" , method = {RequestMethod.GET, RequestMethod.POST} )
		public String DepExclusionFormView(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepExclusionFormView.htm "+Username);
			try {
				
				String empid =""; //req.getParameter("empid");
				String formid = req.getParameter("formid");
				String isapproval = req.getParameter("isApprooval");
				
				if(formid==null)  {
					Map md=model.asMap();
					formid=(String)md.get("formid");
//					empid=(String)md.get("empid");
					isapproval ="N";
				}
				
				if(formid==null) 
				{
					return "static/Error";
				}
				
				Object[] formdata = service.GetFamFormData(formid);
				if(formdata!=null) {
					empid = formdata[1].toString();
				}else
				{
					empid = ((Long) ses.getAttribute("EmpId")).toString();
				}
				
				
				req.setAttribute("formdetails" , formdata);
				req.setAttribute("empdetails",service.getEmployeeInfo(empid) );				
				req.setAttribute("employeeResAddr",service.employeeResAddr(empid) );
				req.setAttribute("FamilymemDropdown",service.EmpFamMembersListMedDep(empid));
				req.setAttribute("ExcMemberDetails",service.GetExcFormMembersList(formid));
				
				req.setAttribute("relationtypes" , service.familyRelationList() );				
				req.setAttribute("isApprooval" , isapproval );
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				return "pis/DependentFormExcView";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepExclusionFormView.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	
	 	@RequestMapping(value ="DepExcusionFormMemAdd.htm" , method =  RequestMethod.POST )
		public String DepExcusionFormMemAdd(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestPart("mem-attach") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepExcusionFormMemAdd.htm "+Username);
			try {
				String empid = ((Long) ses.getAttribute("EmpId")).toString();			
				
				String familydetailsid = req.getParameter("familydetailsid");
		    	String comment = req.getParameter("mem-comment");
		    	String ExcDate = req.getParameter("mem-exc-date");
		    	String ExcFormId = req.getParameter("formid");
		    	
		    	
		    	
		        if(ExcFormId.equals("0")) 
		        {
		        	PisEmpFamilyForm form = new PisEmpFamilyForm();
		        	form.setEmpid(Long.parseLong(empid));
		        	form.setFormStatus("C");
		        	form.setFormType("E");
		        	form.setIsActive(1);
		        	ExcFormId=String.valueOf(service.EmpFamilyFormAdd(form));
		        }
//		       
		        String filepath=null;
		    	if(IncAttachment.getOriginalFilename()!=null  && IncAttachment.getSize()>0) 
		    	{
		    		filepath=saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
		    	}
		    	
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFamilyFormId(Long.parseLong(ExcFormId));
		    	formmember.setFamilyDetailsId(Long.parseLong(familydetailsid));
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(ExcDate));
		    	formmember.setComments(comment);
		    	formmember.setAttachFilePath(filepath);
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	long result =service.PisFamFormMembersAdd(formmember);
		    	
	    	   
		    	if(result>0){
		    		redir.addAttribute("result", "Member Added To Exclusion From Successful");	
		   		} else {
		   			redir.addAttribute("resultfail", "Member Adding To Exclusion From Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",ExcFormId);
		    	
				return "redirect:/DepExclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepExcusionFormMemAdd.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	
	 	
	 	@RequestMapping(value ="ExclusionFormMemberEdit.htm" , method =  RequestMethod.POST )
		public String ExclusionFormMemberEdit(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestPart("mem-attach-edit") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside ExclusionFormMemberEdit.htm "+Username);
			try {
							
				String empid = ((Long) ses.getAttribute("EmpId")).toString();			
				
				String familydetailsid = req.getParameter("familydetailsid");
		    	String comment = req.getParameter("mem-comment");
		    	String ExcDate = req.getParameter("mem-exc-date");
		    	String ExcFormId = req.getParameter("formid");
		    	String formmemberid = req.getParameter("formmemberid");
		    	Object[] famMemberdata = service.getMemberdata(formmemberid);
		    	
		    	
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFormMemberId(Long.parseLong(formmemberid));
		    	if( !IncAttachment.isEmpty()  ) 
		    	{
		    		if(famMemberdata[10]!=null) 
		    		{
		    			new File(famMemberdata[10].toString()).delete();
		    		}
		    		
		    		formmember.setAttachFilePath(saveFile(uploadpath+"EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment));
		    	}
		    	else if(famMemberdata[10]!=null && IncAttachment.isEmpty() )
		    	{
		    		formmember.setAttachFilePath(famMemberdata[10].toString());
		    	}
		    	else
		    	{
		    		formmember.setAttachFilePath(null);
		    	}
		    	
		    	
		    	formmember.setFamilyFormId(Long.parseLong(ExcFormId));
		    	formmember.setFamilyDetailsId(Long.parseLong(familydetailsid));
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(ExcDate));
		    	formmember.setComments(comment);
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	long result =service.PisFamFormMemberEdit(formmember);
		    	
	    	   
		    	if(result>0){
		    		redir.addAttribute("result", "Family Member Details Updated Successfully");	
		   		} else {
		   			redir.addAttribute("resultfail", "Family Member Details Update Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",ExcFormId);
				return "redirect:/DepExclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ExclusionFormMemberEdit.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	
	 	
	 	@RequestMapping(value ="ExcFormRemoveMember.htm" , method =  RequestMethod.POST )
		public String ExcFormRemoveMember(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestPart("mem-attach-edit") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside ExcFormRemoveMember.htm "+Username);
			try {
				String formmemberid = req.getParameter("formmemberid");
				
				long count = service.FormFamilyMemberDelete(formmemberid);
				
				if(count>0) {
					redir.addAttribute("result", "Member Removed From the Form Successfully ");
				}
				else {
					redir.addAttribute("resultfail", "Member Removal Unsuccessful");
				}		
				
				
		    	redir.addFlashAttribute("formid",req.getParameter("formid"));
				return "redirect:/DepExclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ExcFormRemoveMember.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	
	 	
	 	
}

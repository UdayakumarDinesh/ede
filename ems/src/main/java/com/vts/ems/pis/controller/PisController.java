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
import java.util.HashMap;
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
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.master.service.MasterService;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.AddressEmec;
import com.vts.ems.pis.model.AddressNextKin;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.Appointments;
import com.vts.ems.pis.model.Awards;
import com.vts.ems.pis.model.DisciplineCode;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.pis.model.PISEmpFamilyDeclaration;
import com.vts.ems.pis.model.Passport;
import com.vts.ems.pis.model.PassportForeignVisit;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisEmpFamilyForm;
import com.vts.ems.pis.model.PisFamFormMembers;
import com.vts.ems.pis.model.PisPayLevel;
import com.vts.ems.pis.model.Property;
import com.vts.ems.pis.model.PropertyDetails;
import com.vts.ems.pis.model.Publication;
import com.vts.ems.pis.model.Qualification;
import com.vts.ems.pis.model.QualificationCode;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.utils.AgeCalculations;
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
	
	@Autowired
	AdminService adminservice;
	
	@Value("${EMSFilesPath}")
	private String uploadpath;
	                                                   
	private static final String profileformmoduleid="3";
	private static final String adminformmoduleid="4";
	
	
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
		String logintype = (String)ses.getAttribute("LoginType");
		logger.info(new Date() +"Inside EmployeeDetails.htm "+Username);		
		try {
			List<Object[]> chssdashboard = adminservice.HeaderSchedulesList("3" ,logintype);
			ses.setAttribute("formmoduleid", profileformmoduleid);
			req.setAttribute("dashboard", chssdashboard);
			ses.setAttribute("SidebarActive","EmployeeDetails_htm");
			
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
			String phoneno = req.getParameter("PhoneNo");
			String AltPhoneno = req.getParameter("AltPhoneno");
			String uanno = req.getParameter("UANNo");
			
			String empstatus = req.getParameter("empstatus");
			
			String empstatusdate = req.getParameter("EmpStatusDate");
			String height = req.getParameter("Height");
			String exman = req.getParameter("ExMan");
			String permpassno =  req.getParameter("PermPassNo");
			String dop=req.getParameter("DOP");
			String chssno=req.getParameter("CHSSNo");
			String dcmafno=req.getParameter("DCMAFNo");
			String benovelentfundno=req.getParameter("BenovelentNo");
			String iticreditsocno=req.getParameter("ITICreditSocNo");
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
			
			emp.setEmpStatus(empstatus);
            emp.setDOP(DateTimeFormatUtil.dateConversionSql(dop));
            emp.setCHSSNo(chssno);
            emp.setDCMAFNo(dcmafno);
            emp.setBenovelentFundNo(benovelentfundno);
            emp.setITICreditSocNo(iticreditsocno);
			emp.setEmpStatusDate(DateTimeFormatUtil.dateConversionSql(empstatusdate));
			emp.setHeight(height);
			emp.setExServiceMan(exman);
			emp.setPerPassNo(permpassno);
			
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
			emp.setGPFNo(gpf);
			emp.setPAN(pan);
			emp.setPINNo(drona);
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
			String phno = req.getParameter("PhoneNo");
			String empdetailsid=req.getParameter("empdetailsid");
			String uanno = req.getParameter("UANNo");
			String EmpId= req.getParameter("EmpId");
			String AltPhoneno = req.getParameter("AltPhoneno");
			String empstatus = req.getParameter("empstatus");
			String empstatusdate = req.getParameter("EmpStatusDate");
			String height = req.getParameter("Height");
			String exman = req.getParameter("ExMan");
			String permpassno =  req.getParameter("PermPassNo");
			String dop=req.getParameter("DOP");
			
			String chssno=req.getParameter("CHSSNo");
			String dcmafno=req.getParameter("DCMAFNo");
			String benovelentfundno=req.getParameter("BenovelentNo");
			String iticreditsocno=req.getParameter("ITICreditSocNo");
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
			
			emp.setEmpStatus(empstatus);
			emp.setDOP(DateTimeFormatUtil.dateConversionSql(dop));
	        emp.setCHSSNo(chssno);
	        emp.setDCMAFNo(dcmafno);
	        emp.setBenovelentFundNo(benovelentfundno);
	        emp.setITICreditSocNo(iticreditsocno);
			emp.setEmpStatusDate(DateTimeFormatUtil.dateConversionSql(empstatusdate));
			emp.setHeight(height);
			emp.setExServiceMan(exman);
			emp.setPerPassNo(permpassno);
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
			//emp.setPunchCard(PunchCardNo);
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
				String path = 	uploadpath + "EMS\\empimages";
					
			File f = new File(path + "\\" + imagename);
			if (f.exists()) {
				f.delete();
			}
			value = service.saveEmpImage(file, empdata[3]+"", path);
			Object[] employeedetails = service.EmployeeDetails(EmpId);
			String basevalue = service.getimage(empdata[3]+"");
			
			req.setAttribute("employeedetails", employeedetails);
			req.setAttribute("basevalue", basevalue);
			if (value != 0) {
				redir.addAttribute("result", "Photo Upload Successful");
			} else {
				redir.addAttribute("resultfail", "Photo Upload Unsuccessful");
			}
			
			
			redir.addFlashAttribute("empid",EmpId);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("resultfail", "Photo Upload Unsuccessful");
			logger.error(new Date() + " Inside PisImageUpload.htm " + Username, e);
		}
		return "redirect:/IndividualDetails.htm";
	}
	
	@RequestMapping(value = "PisEmployeeImageUpload.htm", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
	public String PisEmployeeImageUpload(HttpSession ses, RedirectAttributes redir, HttpServletRequest req,HttpServletResponse res, @RequestParam("photo1") MultipartFile file) throws Exception 
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
				String path = 	uploadpath + "EMS\\empimages";
					
			File f = new File(path + "\\" + imagename);
			if (f.exists()) {
				f.delete();
			}
			value = service.saveEmpImage(file, empdata[3]+"", path);
			Object[] employeedetails = service.EmployeeDetails(EmpId);
			String basevalue = service.getimage(empdata[3]+"");
			
			req.setAttribute("employeedetails", employeedetails);
			req.setAttribute("basevalue", basevalue);
			if (value != 0) {
				redir.addAttribute("result", "Photo Upload Successful");
			} else {
				redir.addAttribute("resultfail", "Photo Upload Unsuccessful");
			}
			
			
			redir.addFlashAttribute("empid",EmpId);
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("resultfail", "Photo Upload Unsuccessful");
			logger.error(new Date() + " Inside PisImageUpload.htm " + Username, e);
		}
		return "redirect:/EmployeeDetails.htm";
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
			logger.info(new Date() + "Inside NewPasswordChangeCheck " + Username);

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
				logger.error(new Date() + "Inside NewPasswordChangeCheck " + Username,e);
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
		        
		        Object[] emp = service.Getemp(Long.parseLong(empid));
				   if(result>0) {
						redir.addAttribute("result", emp[2]+" Seniority Number Updated Successfully ");
					}else {
						redir.addAttribute("resultfail", emp[2]+" Seniority Number Updated Unsuccessful");
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
				return "redirect:/IndividualDetails.htm";
			
			} catch (Exception e) {
				logger.error(new Date() +" Inside EmpBloodGropuEdit.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
			
		}
	
	@RequestMapping(value = "UserBloodGropuEdit.htm" , method= {RequestMethod.POST})
	public String UserBloodGropuEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{   
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside UserBloodGropuEdit.htm " + Username);
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
			logger.error(new Date() +" Inside UserBloodGropuEdit.htm "+Username, e);
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
				String controllerMapping = new Object(){}.getClass().getEnclosingMethod().getAnnotation(RequestMapping.class).value()[0];
				ses.setAttribute("formmoduleid", "4");
				ses.setAttribute("SidebarActive",controllerMapping.replace(".", "_"));
				
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
		public String FamFormsApproveList(Model model,HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");	
			logger.info(new Date() +"Inside FamFormsApproveList.htm "+Username);		
			try {
				
				String tab= req.getParameter("tab");;
				
				if(tab==null)  {
					Map md=model.asMap();
					tab=(String)md.get("tab");
				}
				
				tab = tab==null ? "P" : tab;
				
				ses.setAttribute("SidebarActive", "FamFormsApproveList_htm");
				req.setAttribute("FamFwdFormsList", service.FamMemFwdEmpList());
				req.setAttribute("FamMemApprovedList", service.FamMemApprovedList());
				req.setAttribute("tab", tab);
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
//				req.setAttribute("FamilymemDropdown",service.EmpFamMembersListMedDep(empid,formid));
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
		public String FamilyFormForwardRet(HttpSession ses , HttpServletRequest req ,HttpServletResponse res, RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			logger.info(new Date() +"Inside FamilyFormForwardRet.htm "+Username);		
			try {
				String formid = req.getParameter("formid");
				String action = req.getParameter("action");
				String remarks = req.getParameter("remarks");
				
				long result= service.FamilyMemDetailsForward(formid,action,Username,EmpId,remarks,req,res);
				if(result>0) 
				{
					if(action.equalsIgnoreCase("F")) {
						redir.addAttribute("result", "Form Forwarded Successfully ");
					}
					else if(action.equalsIgnoreCase("R")) {
						redir.addAttribute("result", "Form  Returned Successfully ");
					}
					else if(action.equalsIgnoreCase("A")) {
						redir.addAttribute("result", "Form  Approved Successfully ");
					}
				}
				else 
				{
					if(action.equalsIgnoreCase("F")) {
						redir.addAttribute("resultfail", "Form Forward Unsuccessful");
					}
					else if(action.equalsIgnoreCase("R")) {
						redir.addAttribute("resultfail", "Form Return Unsuccessful");
					}
					else if(action.equalsIgnoreCase("A")) {
						redir.addAttribute("resultfail", "Form Approval Unsuccessful");
					}
				}		
				
				if(action.equalsIgnoreCase("F")) {
					return "redirect:/FamIncExcFwdList.htm";
				}
				redir.addAttribute("tab", "A");
				return "redirect:/FamFormsApproveList.htm";
				
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
				
				String empid ="0"; 
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
				
				req.setAttribute("FamilymemDropdown" , service.EmpFamMembersNotMedDep(empid,formid));
				
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
		public String ExistingMemAddSubmit(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestParam(required = false, name = "mem-attach") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String empid = ((Long) ses.getAttribute("EmpId")).toString();	
			logger.info(new Date() +"Inside ExistingMemAddSubmit.htm "+Username);
			try {
				String familydetailsid = req.getParameter("familydetailsid");
		    	String IncDate  = req.getParameter("mem-IncDate");
		    	String comment  = req.getParameter("mem-comment");
		    	String IncFormId  = req.getParameter("incformid");
		    	String Decformid =req.getParameter("Decformid");
		    	EmpFamilyDetails member =  service.getFamilyMemberModal(familydetailsid);
		    	member.setMed_dep_from(DateTimeFormatUtil.dateConversionSql(IncDate));
		    	member.setMed_dep("N");
		    	service.EditFamilyDetails(member);
		    	
		    	
		    	String filepath=null;
		    	if(IncAttachment.getOriginalFilename()!=null  && IncAttachment.getSize()>0) 
		    	{
		    		filepath = saveFile(uploadpath+"EMS\\EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
		    	}
		    	else if(IncAttachment.getOriginalFilename()!=null && IncAttachment.getSize()==0)
		    	{
		    		redir.addAttribute("resultfail", "Invalid File, Please Check the file");
		    		redir.addFlashAttribute("formid",IncFormId);
			    	
		    		if(Decformid!=null) {
			    		return "redirect:/DepDeclareFormView.htm";
			    	}
		    		
					return "redirect:/DepInclusionFormView.htm";
		    	}
		    	
		    	if(IncFormId.equals("0")) 
		        {
		        	PisEmpFamilyForm form = new PisEmpFamilyForm();
		        	form.setEmpid(Long.parseLong(empid));
		        	form.setFormStatus("C");
		        	if(Decformid!=null) {
		        		form.setFormType("D");
		        	}else
		        	{
		        		form.setFormType("I");
		        	}
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
		    	formmember.setIncExc("I");
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	long count=service.PisFamFormMembersAdd(formmember);
		    	
		    	
		    	if(count>0){
		    		redir.addAttribute("result", "Adding Member to Form Successfully");	
		   		} else {
		   			redir.addAttribute("resultfail", "Adding Member to Form Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",IncFormId);
		    	
		    	if(Decformid!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
		    	
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
		    	String Decformid =req.getParameter("Decformid");
		    	
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
		        	if(Decformid!=null) {
		        		form.setFormType("D");
		        	}else
		        	{
		        		form.setFormType("I");
		        	}
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
		    		filepath = saveFile(uploadpath+"EMS\\EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
		    	}
		    	else if(IncAttachment.getOriginalFilename()!=null && IncAttachment.getSize()==0)
		    	{
		    		redir.addAttribute("resultfail", "Invalid File, Please Check the file");
		    		redir.addFlashAttribute("formid",IncFormId);
			    	
			    	
			    	if(Decformid!=null) {
			    		return "redirect:/DepDeclareFormView.htm";
			    	}
			    	
					return "redirect:/DepInclusionFormView.htm";
		    	}
		    	long familydetailsid = service.AddFamilyDetails(details);
	    	   
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFamilyFormId(Long.parseLong(IncFormId));
		    	formmember.setFamilyDetailsId(familydetailsid);
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(IncDate));
		    	formmember.setComments(comment);
		    	formmember.setAttachFilePath(filepath);
		    	formmember.setIncExc("I");
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	service.PisFamFormMembersAdd(formmember);
		    	
		    	if(familydetailsid>0){
		    		redir.addAttribute("result", "Family Member Details Saved Successfully");	
		   		} else {
		   			redir.addAttribute("resultfail", "Family Member Details Saved Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",IncFormId);
		    	
		    	if(Decformid!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
		    	
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
			    		filepath = saveFile(uploadpath+"EMS\\EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
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
		    	formmember.setIncExc("I");
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
		    	
		    	if(req.getParameter("Decformid")!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
		    	
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
				
				if(req.getParameter("Decformid")!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
				
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
				
				String formid = req.getParameter("formid");
				
				String empid = "0";
				Object[] formdata = service.GetFamFormData(formid);
				if(formdata!=null) {
					empid = formdata[1].toString();
				}
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
				req.setAttribute("FamilymemDropdown",service.EmpFamMembersListMedDep(empid,formid));
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
		public String DepExcusionFormMemAdd(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestParam(required = false, name = "mem-attach") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepExcusionFormMemAdd.htm "+Username);
			try {
				String empid = ((Long) ses.getAttribute("EmpId")).toString();			
				
				String familydetailsid = req.getParameter("familydetailsid");
		    	String comment = req.getParameter("mem-comment");
		    	String ExcDate = req.getParameter("mem-exc-date");
		    	String ExcFormId = req.getParameter("formid");
		    	String Decformid =req.getParameter("Decformid");
		    	
		    	
		        if(ExcFormId.equals("0")) 
		        {
		        	PisEmpFamilyForm form = new PisEmpFamilyForm();
		        	form.setEmpid(Long.parseLong(empid));
		        	form.setFormStatus("C");
		        	if(Decformid!=null) {
		        		form.setFormType("D");
		        	}else
		        	{
		        		form.setFormType("E");
		        	}
		        	form.setIsActive(1);
		        	ExcFormId=String.valueOf(service.EmpFamilyFormAdd(form));
		        }
//		       
		        String filepath=null;
		    	if(IncAttachment!=null && IncAttachment.getOriginalFilename()!=null  && IncAttachment.getSize()>0) 
		    	{
		    		filepath=saveFile(uploadpath+"EMS\\EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment);
		    	}
		    	
		    	PisFamFormMembers formmember = new PisFamFormMembers();
		    	formmember.setFamilyFormId(Long.parseLong(ExcFormId));
		    	formmember.setFamilyDetailsId(Long.parseLong(familydetailsid));
		    	formmember.setIncExcDate(DateTimeFormatUtil.RegularToSqlDate(ExcDate));
		    	formmember.setComments(comment);
		    	formmember.setAttachFilePath(filepath);
		    	formmember.setIncExc("E");
		    	formmember.setIsActive(1);
		    	formmember.setCreatedBy(Username);
		    	long result =service.PisFamFormMembersAdd(formmember);
		    	
	    	   
		    	if(result>0){
		    		redir.addAttribute("result", "Member Added To Exclusion From Successful");	
		   		} else {
		   			redir.addAttribute("resultfail", "Member Adding To Exclusion From Unsuccessful");
		    	}                    
				
		    	redir.addFlashAttribute("formid",ExcFormId);
		    	
		    	
		    	if(Decformid!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
		    	
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
		    		
		    		formmember.setAttachFilePath(saveFile(uploadpath+"EMS\\EmpFmailyDocs\\",IncAttachment.getOriginalFilename(),IncAttachment));
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
		    	
		    	if(req.getParameter("Decformid")!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
		    	
				return "redirect:/DepExclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ExclusionFormMemberEdit.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	
	 	
	 	@RequestMapping(value ="ExcFormRemoveMember.htm" , method =  RequestMethod.POST )
		public String ExcFormRemoveMember(HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir, @RequestParam(required=false,name="mem-attach-edit") MultipartFile IncAttachment)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside ExcFormRemoveMember.htm "+Username);
			try {
				String formmemberid = req.getParameter("formmemberid");
				
				long count = service.FormFamilyMemberDelete(formmemberid);
				
				if(count>0) {
					redir.addAttribute("result", "Member Removed From the Form Successfully");
				}
				else {
					redir.addAttribute("resultfail", "Member Removal Unsuccessful");
				}		
				
				
		    	redir.addFlashAttribute("formid",req.getParameter("formid"));
		    	
		    	if(req.getParameter("Decformid")!=null) {
		    		return "redirect:/DepDeclareFormView.htm";
		    	}
		    	
				return "redirect:/DepExclusionFormView.htm";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ExcFormRemoveMember.htm "+Username, e);
				return "static/Error";
			}
			
		}
	 	
	 	@RequestMapping(value = "EducationList.htm" , method = {RequestMethod.POST,RequestMethod.GET })
	 	public String EducationList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	    {	
	 	   String Username = (String) ses.getAttribute("Username");
	         logger.info(new Date() +"Inside EducationList.htm "+Username);
	         String empid =null;
	         	
	         try {
	         	  empid = (String)req.getParameter("empid");
	         	if(empid==null)  {
	 				Map md=model.asMap();
	 				empid=(String)md.get("Employee");
	 				
	 			}
	 			req.setAttribute("Educationlist", service.getEducationList(empid));
	 			req.setAttribute("Empdata", service.GetEmpData(empid));
	 			
	 		} catch (Exception e) {		
	 			logger.info(new Date() +"Inside EducationList.htm "+Username);
	 			e.printStackTrace();
	 			 return "static/Error";
	 		}
	         return "pis/EducationList";
	    }
	 	
	 	@RequestMapping(value = "EducationAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
		public String EducationAddEditDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside EducationAddEditDelete.htm " + Username);
			
			try {
				String empid = (String) req.getParameter("empid");
				String Action = (String) req.getParameter("Action");
				if ("ADD".equalsIgnoreCase(Action)) {
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("QualificationList", service.getQualificationList());
					req.setAttribute("DisciplineList", service.getDiscipline());
			
					return "pis/EducationAdd";
				} else if ("EDIT".equalsIgnoreCase(Action)) {
					String qualiid = (String) req.getParameter("qualificationId");
				
					req.setAttribute("QualificationDetails", service.getQualificationDetails(Integer.parseInt(qualiid)));
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("QualificationList", service.getQualificationList());
					req.setAttribute("DisciplineList", service.getDiscipline());
			
					return "pis/EducationEdit";
				} else {
			                                                                                    
					String qualiid = (String) req.getParameter("qualificationId");
					int count = service.DeleteQualification(qualiid, Username);
					if (count > 0) {
						redir.addAttribute("result", "Qualification Details deleted Sucessfully");
					} else {
						redir.addAttribute("resultfail", "Qualification Details deleted UnSucessfull");
					}
					redir.addFlashAttribute("Employee", empid);
					return "redirect:/EducationList.htm";
				}
			} catch (Exception e) {
				logger.error(new Date() + "Inside EducationAddEditDelete.htm " + Username ,e);
				e.printStackTrace();
				return "static/Error";
			}
			

		}
	   
	   @RequestMapping(value="AddEducation.htm" ,  method=RequestMethod.POST)  
	   public String AddEducation(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddEducation.htm "+Username);
	       
	       try {
	    	   String qualification = req.getParameter("Qualification");
	    	   String sponsored  = req.getParameter("Sponsored");
	    	   String discipline = req.getParameter("Discipline");
	    	   String hindiprof =  req.getParameter("HindiProf");
	    	   String university = req.getParameter("University");
	    	   String division = req.getParameter("Division");
	    	   String specialization = req.getParameter("Specialization");
	    	   String honours = req.getParameter("Honours");
	    	   String acquired = req.getParameter("Acquired");
	    	   String yearofpassing = req.getParameter("yearofpassing");
	    	   String cgpa  = req.getParameter("CGPA");
	    	   String empid = req.getParameter("empid");
	    	  
	    	  Qualification quali = new Qualification();
	    	  
	    	  quali.setQuali_id(Integer.valueOf(qualification));
	    	  quali.setSponsored(sponsored);
	    	  quali.setDisci_id(Integer.valueOf(discipline));
	    	  quali.setHindi_prof(hindiprof);
	    	  quali.setUniversity(university);
	    	  quali.setDivision(division);
	    	  quali.setSpecialization(specialization);
	    	  quali.setHonours(honours);
	    	  quali.setAcq_bef_aft(acquired);
	    	  quali.setYearofpassing(yearofpassing);
	    	  quali.setCgpa(cgpa);
	    	  quali.setEmpid(empid);
	    	  quali.setIs_active(1);
	    	  quali.setCreatedby(Username);
	    	  quali.setCreateddate(sdtf.format(new Date()));
	    	  
	    	   int result = service.AddQualification(quali);
	    	   if(result>0){
	    		   redir.addAttribute("result", "Qualification Details Saved Successfully");	
	   		   } else {
	   			redir.addAttribute("resultfail", "Qualification Details Saved Unsuccessful");
	    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);
	    	   
	    	   
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside AddEducation.htm "+Username,e);
		  	 e.printStackTrace();
		  	return "static/Error";
		  }
	       return "redirect:/EducationList.htm";
	   }
	   
	   
	   @RequestMapping(value="EditEducation.htm" , method=RequestMethod.POST)
	   public String EducationEdit(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside EditEducation.htm "+Username);
	       try {
	    	   String qualification = req.getParameter("Qualification");
	    	   String sponsored  = req.getParameter("Sponsored");
	    	   String discipline = req.getParameter("Discipline");
	    	   String hindiprof =  req.getParameter("HindiProf");
	    	   String university = req.getParameter("University");
	    	   String division = req.getParameter("Division");
	    	   String specialization = req.getParameter("Specialization");
	    	   String honours = req.getParameter("Honours");
	    	   String acquired = req.getParameter("Acquired");
	    	   String yearofpassing = req.getParameter("yearofpassing");
	    	   String cgpa  = req.getParameter("CGPA");
	    	   String empid = req.getParameter("empid");
	    	  
	    	   
	    	   String qualificationid = req.getParameter("qualificationid");
	    	  Qualification quali = new Qualification();
	    	  
	    	  quali.setQualification_id(Integer.valueOf(qualificationid));
	    	  quali.setQuali_id(Integer.valueOf(qualification));
	    	  quali.setSponsored(sponsored);
	    	  quali.setDisci_id(Integer.valueOf(discipline));
	    	  quali.setHindi_prof(hindiprof);
	    	  quali.setUniversity(university);
	    	  quali.setDivision(division);
	    	  quali.setSpecialization(specialization);
	    	  quali.setHonours(honours);
	    	  quali.setAcq_bef_aft(acquired);
	    	  quali.setYearofpassing(yearofpassing);
	    	  quali.setCgpa(cgpa);
	    	  quali.setEmpid(empid);
	    	  quali.setModifiedby(Username);
	    	  quali.setModifieddate(sdtf.format(new Date()));
	    	   
	    	   String comments = (String)req.getParameter("comments");
	    	   MasterEdit masteredit  = new MasterEdit();
	    	   masteredit.setCreatedBy(Username);
	    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    	   masteredit.setTableRowId(Long.parseLong(qualificationid));
	    	   masteredit.setComments(comments);
	    	   masteredit.setTableName("pis_qualification");
	    	  
	    	   MasterEditDto masterdto = new MasterEditDto();
	    	   masterdto.setFilePath(selectedFile);
	    	   
	    	   masterservice.AddMasterEditComments(masteredit , masterdto);
	    	   
	    	   int result = service.EditQualification(quali);
		    	   if(result>0){
		    		   redir.addAttribute("result", "Qualification Details Edited Sucessfully");	
		   		   } else {
		   			   redir.addAttribute("resultfail", "Qualification Details Edited UnSuccessful");
		    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);   
	    	   
		      } catch (Exception e) {
		    	  logger.error(new Date() + " Inside EditEducation.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
		      }
	       return "redirect:/EducationList.htm";
	   }
	 	
	   
	   @RequestMapping(value = "AppointmentList.htm" , method = {RequestMethod.GET, RequestMethod.POST})
	   public String AppointmentList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AppointmentList.htm "+Username);
			try {
				String empid =null;
				 empid = (String)req.getParameter("empid");
		         	if(empid==null)  {
		 				Map md=model.asMap();
		 				empid=(String)md.get("Employee");
		 			}
		         	
		 			req.setAttribute("Appointmentlist", service.getAppointmentList(empid));
		 			req.setAttribute("Empdata", service.GetEmpData(empid));
		 			 return "pis/AppointmentList";
			} catch (Exception e) {
				 logger.error(new Date() + " Inside AppointmentList.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
			}
	   }
	   
	   @RequestMapping(value = "AppointmentAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
		public String AppointmentAddEditDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside AppointmentAddEditDelete.htm " + Username);
			
			try {
				String empid = (String) req.getParameter("empid");
				String Action = (String) req.getParameter("Action");
				if ("ADD".equalsIgnoreCase(Action)) {
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("DesignationList", service.getDesignationList());
					req.setAttribute("Recruitment", service.getRecruitment());
			
					return "pis/AppointmentAdd";
				} else if ("EDIT".equalsIgnoreCase(Action)) {
					String appointmentid = (String) req.getParameter("AppointmentId");
				
					req.setAttribute("AppointmentsDetails", service.getAppointmentsDetails(Integer.parseInt(appointmentid)));
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("DesignationList", service.getDesignationList());
					req.setAttribute("Recruitment", service.getRecruitment());
			
					return "pis/AppointmentEdit";
				} else {
			                                                                                    
					String qualiid = (String) req.getParameter("AppointmentId");
					int count = service.DeleteAppointment(qualiid, Username);
					if (count > 0) {
						redir.addAttribute("result", "Appointment Details deleted Sucessfully");
					} else {
						redir.addAttribute("resultfail", "Appointment Details deleted Unsucessfull");
					}
					redir.addFlashAttribute("Employee", empid);
					return "redirect:/AppointmentList.htm";
				}
			} catch (Exception e) {
				logger.error(new Date() + "Inside AppointmentAddEditDelete.htm " + Username ,e);
				e.printStackTrace();
				return "static/Error";
			}
		}
	  
	   @RequestMapping(value="AddAppointment.htm" ,  method=RequestMethod.POST)  
	   public String AddAppointment(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddAppointment.htm "+Username);
	       
	       try {
	    	   String recruitmentid = req.getParameter("Recruitmentid");
	    	   String drdo  = req.getParameter("drdo");
	    	   String designation = req.getParameter("Designation");
	    	   String orglab =  req.getParameter("OrgLab");
	    	   String fromdate = req.getParameter("fromdate");
	    	   String todate = req.getParameter("todate");
	    	   String ceptamcycle = req.getParameter("CeptamCycle");
	    	   String vacancyyear = req.getParameter("vacancyyear");
	    	   String recruitmentyear = req.getParameter("recruitmentyear");
	    	
	    	   String empid = req.getParameter("empid");
	    	  
	    	  Appointments app = new Appointments();
	    	  
	    	  app.setMode_recruitment(recruitmentid);
	    	  app.setDrdo_others(drdo);
	    	  app.setDesig_id(Integer.parseInt(designation));
	    	  app.setOrg_lab(orglab);
	    	  app.setFrom_date(DateTimeFormatUtil.dateConversionSql(fromdate));
	    	  app.setTo_date(DateTimeFormatUtil.dateConversionSql(todate));
	    	  app.setCeptam_cycle(ceptamcycle);
	    	  app.setVacancy_year(vacancyyear);
	    	  app.setRecruitment_year(recruitmentyear);
	    	  app.setEmpid(empid);
	    	  app.setIs_active(1);
	    	  app.setCreatedby(Username);
	    	  app.setCreateddate(sdtf.format(new Date()));
	    	  
	    	   int result = service.AddAppointment(app);
	    	   if(result>0){
	    		   redir.addAttribute("result", "Appointment Details Saved Successfully");	
	   		   } else {
	   			redir.addAttribute("resultfail", "Appointment Details Saved Unsuccessful");
	    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);
	    	   
	    	   
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside AddAppointment.htm "+Username,e);
		  	 e.printStackTrace();
		  	return "static/Error";
		  }
	       return "redirect:/AppointmentList.htm";
	   }
	   
	   
	   @RequestMapping(value="EditAppointment.htm" , method=RequestMethod.POST)
	   public String EditAppointment(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside EditAppointment.htm "+Username);
	       try {
	    	   String recruitmentid = req.getParameter("Recruitmentid");
	    	   String drdo  = req.getParameter("drdo");
	    	   String designation = req.getParameter("Designation");
	    	   String orglab =  req.getParameter("OrgLab");
	    	   String fromdate = req.getParameter("fromdate");
	    	   String todate = req.getParameter("todate");
	    	   String ceptamcycle = req.getParameter("CeptamCycle");
	    	   String vacancyyear = req.getParameter("vacancyyear");
	    	   String recruitmentyear = req.getParameter("recruitmentyear");
	    	   String appointmentid = req.getParameter("appointmentid");
	    	   String empid = req.getParameter("empid");
	    	  
	    	  Appointments app = new Appointments();
	    	  
	    	  app.setMode_recruitment(recruitmentid);
	    	  app.setDrdo_others(drdo);
	    	  app.setDesig_id(Integer.parseInt(designation));
	    	  app.setOrg_lab(orglab);
	    	  app.setFrom_date(DateTimeFormatUtil.dateConversionSql(fromdate));
	    	  app.setTo_date(DateTimeFormatUtil.dateConversionSql(todate));
	    	  app.setCeptam_cycle(ceptamcycle);
	    	  app.setVacancy_year(vacancyyear);
	    	  app.setRecruitment_year(recruitmentyear);	    	  
	    	  app.setAppointment_id(Integer.parseInt(appointmentid));
	    	  app.setEmpid(empid);
	    	  app.setModifiedby(Username);
	    	  app.setModifieddate(sdtf.format(new Date()));
	    	   
	    	   String comments = (String)req.getParameter("comments");
	    	   MasterEdit masteredit  = new MasterEdit();
	    	   masteredit.setCreatedBy(Username);
	    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    	   masteredit.setTableRowId(Long.parseLong(appointmentid));
	    	   masteredit.setComments(comments);
	    	   masteredit.setTableName("pis_appointments");
	    	  
	    	   MasterEditDto masterdto = new MasterEditDto();
	    	   masterdto.setFilePath(selectedFile);
	    	   
	    	   masterservice.AddMasterEditComments(masteredit , masterdto);
	    	   
	    	   int result = service.EditAppointment(app);
		    	   if(result>0){
		    		   redir.addAttribute("result", "Appointment Details Edited Sucessfully");	
		   		   } else {
		   			   redir.addAttribute("resultfail", "Appointment Details Edited UnSuccessful");
		    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);   
	    	   
		      } catch (Exception e) {
		    	  logger.error(new Date() + " Inside EditAppointment.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
		      }
	       return "redirect:/AppointmentList.htm";
	   }
	   
	   
	   
	   @RequestMapping(value = "AwardsList.htm" , method = {RequestMethod.GET, RequestMethod.POST})
	   public String AwardsList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AwardsList.htm "+Username);
			try {
				String empid =null;
				 empid = (String)req.getParameter("empid");
		         	if(empid==null)  {
		 				Map md=model.asMap();
		 				empid=(String)md.get("Employee");
		 			}
		         	
		 			req.setAttribute("Awardslist", service.getAwardsList(empid));
		 			req.setAttribute("Empdata", service.GetEmpData(empid));
		 			 return "pis/AwardsList";
			} catch (Exception e) {
				 logger.error(new Date() + " Inside AwardsList.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
			}
	   }
	   
	   @RequestMapping(value = "AwardsAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
		public String AwardsAddEditDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside AwardsAddEditDelete.htm " + Username);
			
			try {
				String empid = (String) req.getParameter("empid");
				String Action = (String) req.getParameter("Action");
				if ("ADD".equalsIgnoreCase(Action)) {
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("PisAwardsList", service.getPisAwardsList());
					return "pis/AwardsAdd";
				} else if ("EDIT".equalsIgnoreCase(Action)) {
					String appointmentid = (String) req.getParameter("AwardId");
				
					req.setAttribute("AwardssDetails", service.getAwardsDetails(Integer.parseInt(appointmentid)));
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("PisAwardsList", service.getPisAwardsList());
					
			
					return "pis/AwardsEdit";
				} else {
			                                                                                    
					String qualiid = (String) req.getParameter("AwardId");
					int count = service.DeleteAwards(qualiid, Username);
					if (count > 0) {
						redir.addAttribute("result", "Awards Details deleted Sucessfully");
					} else {
						redir.addAttribute("resultfail", "Awards Details deleted Unsucessfull");
					}
					redir.addFlashAttribute("Employee", empid);
					return "redirect:/AwardsList.htm";
				}
			} catch (Exception e) {
				logger.error(new Date() + "Inside AwardsAddEditDelete.htm " + Username ,e);
				e.printStackTrace();
				return "static/Error";
			}
		}
	  
	   @RequestMapping(value="AddAwards.htm" ,  method=RequestMethod.POST)  
	   public String AddAwards(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddAwards.htm "+Username);
	       
	       try {
	    	   String awardname = req.getParameter("Awardname");
	    	   String certificate  = req.getParameter("certificate");
	    	   String awardfrom = req.getParameter("AwardFrom");
	    	   String citation =  req.getParameter("Citation");
	    	   String awarddetails = req.getParameter("AwardDetails");
	    	   String cash = req.getParameter("Cash");
	    	   String awardcategory = req.getParameter("AwardCategory");
	    	   String awardyear = req.getParameter("AwardYear");
	    	   String cashamount = req.getParameter("CashAmount");
	    	   String medallion = req.getParameter("Medallion");
	    	   String awarddate = req.getParameter("AwardDate");
	    	
	    	   String empid = req.getParameter("empid");
	    	  
	    	   Awards app = new Awards();
	    	  
	    	  app.setAwardListId(Integer.parseInt(awardname));
	    	  app.setCertificate(certificate);
	    	  app.setAward_by(awardfrom);
	    	  app.setCitation(citation);
	    	  app.setAward_date(DateTimeFormatUtil.dateConversionSql(awarddate));
	    	  app.setDetails(awarddetails);
	    	  app.setAward_cat(awardcategory);
	    	  app.setCash(cash);
	    	  app.setCash_amt(cashamount);
	    	  app.setAward_year(awardyear);
	    	  app.setMedallion(medallion);
	    	  app.setEmpid(empid);
	    	  app.setIs_active(1);
	    	  app.setCreatedby(Username);
	    	  app.setCreateddate(sdtf.format(new Date()));
	    	  
	    	   int result = service.AddAwards(app);
	    	   if(result>0){
	    		   redir.addAttribute("result", "Awards Details Saved Successfully");	
	   		   } else {
	   			redir.addAttribute("resultfail", "Awards Details Saved Unsuccessful");
	    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);
	    	   
	    	   
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside AddAwards.htm "+Username,e);
		  	 e.printStackTrace();
		  	return "static/Error";
		  }
	       return "redirect:/AwardsList.htm";
	   }
	   
	   
	   @RequestMapping(value="EditAwards.htm" , method=RequestMethod.POST)
	   public String EditAwards(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside EditAwards.htm "+Username);
	       try {
	    	   String awardname = req.getParameter("AwardName");
	    	   String certificate  = req.getParameter("certificate");
	    	   String awardfrom = req.getParameter("AwardFrom");
	    	   String citation =  req.getParameter("Citation");
	    	   String awarddetails = req.getParameter("AwardDetails");
	    	   String cash = req.getParameter("Cash");
	    	   String awardcategory = req.getParameter("AwardCategory");
	    	   String awardyear = req.getParameter("AwardYear");
	    	   String cashamount = req.getParameter("CashAmount");
	    	   String medallion = req.getParameter("Medallion");
	    	   String awarddate = req.getParameter("AwardDate");
	    	   String  awardsid = req.getParameter("awardsid");
	    	   String empid = req.getParameter("empid");
	    	  
	    	   Awards app = new Awards();
	    	  
	    	  app.setAwards_id(Integer.parseInt(awardsid));
	    	  app.setAwardListId(Integer.parseInt(awardname));
	    	  app.setCertificate(certificate);
	    	  app.setAward_by(awardfrom);
	    	  app.setCitation(citation);
	    	  app.setAward_date(DateTimeFormatUtil.dateConversionSql(awarddate));
	    	  app.setDetails(awarddetails);
	    	  app.setAward_cat(awardcategory);
	    	  app.setCash(cash);
	    	  app.setCash_amt(cashamount);
	    	  app.setAward_year(awardyear);
	    	  app.setMedallion(medallion);
	    	  app.setEmpid(empid);
	    	  app.setIs_active(1);
	    	  app.setModifiedby(Username);
	    	  app.setModifieddate(sdtf.format(new Date()));
	    	   
	    	   String comments = (String)req.getParameter("comments");
	    	   MasterEdit masteredit  = new MasterEdit();
	    	   masteredit.setCreatedBy(Username);
	    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    	   masteredit.setTableRowId(Long.parseLong(awardsid));
	    	   masteredit.setComments(comments);
	    	   masteredit.setTableName("pis_appointments");
	    	  
	    	   MasterEditDto masterdto = new MasterEditDto();
	    	   masterdto.setFilePath(selectedFile);
	    	   
	    	   masterservice.AddMasterEditComments(masteredit , masterdto);
	    	   
	    	   int result = service.EditAwards(app);
		    	   if(result>0){
		    		   redir.addAttribute("result", "Awards Details Edited Sucessfully");	
		   		   } else {
		   			   redir.addAttribute("resultfail", "Awards Details Edited UnSuccessful");
		    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);   
	    	   
		      } catch (Exception e) {
		    	  logger.error(new Date() + " Inside EditAwards.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
		      }
	       return "redirect:/AwardsList.htm";
	   }
	   
  
	   
	   @RequestMapping(value = "PropertyList.htm" , method = {RequestMethod.GET, RequestMethod.POST})
	   public String PropertyList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside PropertyList.htm "+Username);
			try {
				String empid =null;
				 empid = (String)req.getParameter("empid");
		         	if(empid==null)  {
		 				Map md=model.asMap();
		 				empid=(String)md.get("Employee");
		 			}
		         	
		 			req.setAttribute("Propertylist", service.getPropertyList(empid));
		 			req.setAttribute("Empdata", service.GetEmpData(empid));
		 			 return "pis/PropertyList";
			} catch (Exception e) {
				 logger.error(new Date() + " Inside PropertyList.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
			}
	   }
	   
	   @RequestMapping(value = "PropertyAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
		public String PropertyAddEditDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception 
	   {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside PropertyAddEditDelete.htm " + Username);
			
			try {
				String empid = (String) req.getParameter("empid");
				String Action = (String) req.getParameter("Action");
				if ("ADD".equalsIgnoreCase(Action)) {
					req.setAttribute("Empdata", service.GetEmpData(empid));
					
					return "pis/PropertyAdd";
				} else if ("EDIT".equalsIgnoreCase(Action)) {
					String appointmentid = (String) req.getParameter("PropertyId");
				
					req.setAttribute("PropertyDetails", service.getPropertyDetails(Integer.parseInt(appointmentid)));
					req.setAttribute("Empdata", service.GetEmpData(empid));

					return "pis/PropertyEdit";
				} else {
			                                                                                    
					String qualiid = (String) req.getParameter("PropertyId");
					int count = service.DeleteProperty(qualiid, Username);
					if (count > 0) {
						redir.addAttribute("result", "Property Details deleted Sucessfully");
					} else {
						redir.addAttribute("resultfail", "Property Details deleted Unsucessfull");
					}
					redir.addFlashAttribute("Employee", empid);
					return "redirect:/PropertyList.htm";
				}
			} catch (Exception e) {
				logger.error(new Date() + "Inside PropertyAddEditDelete.htm " + Username ,e);
				e.printStackTrace();
				return "static/Error";
			}
		}
	  
	   @RequestMapping(value="AddProperty.htm" ,  method=RequestMethod.POST)  
	   public String AddProperty(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddProperty.htm "+Username);
	       
	       try {
	    	   String movable = req.getParameter("Movable");
	    	   String acquired  = req.getParameter("Acquired");
	    	   String details = req.getParameter("Details");
	    	   String remark =  req.getParameter("Remark");
	    	   String dop = req.getParameter("DOP");
	    	   String notingon = req.getParameter("NotingOn");
	    	   String value = req.getParameter("Value");
	    	  
	    	
	    	   String empid = req.getParameter("empid");
	    	  
	    	   Property app = new Property();
	    	  
	    	  app.setMovable(movable);
	    	  app.setAcquired_type(acquired);
	    	  app.setDetails(details);
	    	  app.setRemarks(remark);
	    	  app.setDop(DateTimeFormatUtil.dateConversionSql(dop));
	    	  app.setNoting_on(DateTimeFormatUtil.dateConversionSql(notingon));
	    	  app.setValue(value);
	    	  app.setEmpid(empid);
	    	  app.setIs_active(1);
	    	  app.setCreatedby(Username);
	    	  app.setCreateddate(sdtf.format(new Date()));
	    	  
	    	   int result = service.AddProperty(app);
	    	   if(result>0){
	    		   redir.addAttribute("result", "Property Details Saved Successfully");	
	   		   } else {
	   			redir.addAttribute("resultfail", "Property Details Saved Unsuccessful");
	    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);
	    	   
	    	   
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside AddProperty.htm "+Username,e);
		  	 e.printStackTrace();
		  	return "static/Error";
		  }
	       return "redirect:/PropertyList.htm";
	   }
	   
	   
	   @RequestMapping(value="EditProperty.htm" , method=RequestMethod.POST)
	   public String EditProperty(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside EditProperty.htm "+Username);
	       try {
	    	   String movable = req.getParameter("Movable");
	    	   String acquired  = req.getParameter("Acquired");
	    	   String details = req.getParameter("Details");
	    	   String remark =  req.getParameter("Remark");
	    	   String dop = req.getParameter("DOP");
	    	   String notingon = req.getParameter("NotingOn");
	    	   String value = req.getParameter("Value");
	    	  String propertyid = req.getParameter("PropertyId");
	    	
	    	   String empid = req.getParameter("empid");
	    	  
	    	   Property app = new Property();
	    	  
	    	      app.setProperty_id(Integer.parseInt(propertyid));
		    	  app.setMovable(movable);
		    	  app.setAcquired_type(acquired);
		    	  app.setDetails(details);
		    	  app.setRemarks(remark);
		    	  app.setDop(DateTimeFormatUtil.dateConversionSql(dop));
		    	  app.setNoting_on(DateTimeFormatUtil.dateConversionSql(notingon));
		    	  app.setValue(value);
		    	  app.setEmpid(empid);
		    	  app.setIs_active(1);
		    	  app.setModifiedby(Username);
		    	  app.setModifieddate(sdtf.format(new Date()));
	    	   
	    	   String comments = (String)req.getParameter("comments");
	    	   MasterEdit masteredit  = new MasterEdit();
	    	   masteredit.setCreatedBy(Username);
	    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    	   masteredit.setTableRowId(Long.parseLong(propertyid));
	    	   masteredit.setComments(comments);
	    	   masteredit.setTableName("pis_property");
	    	  
	    	   MasterEditDto masterdto = new MasterEditDto();
	    	   masterdto.setFilePath(selectedFile);
	    	   
	    	   masterservice.AddMasterEditComments(masteredit , masterdto);
	    	   
	    	   int result = service.EditProperty(app);
		    	   if(result>0){
		    		   redir.addAttribute("result", "Property Details Edited Sucessfully");	
		   		   } else {
		   			   redir.addAttribute("resultfail", "Property Details Edited UnSuccessful");
		    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);   
	    	   
		      } catch (Exception e) {
		    	  logger.error(new Date() + " Inside EditProperty.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
		      }
	       return "redirect:/PropertyList.htm";
	   }
	   
	   @RequestMapping(value = "PublicationList.htm" , method = {RequestMethod.GET, RequestMethod.POST})
	   public String PublicationList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside PublicationList.htm "+Username);
			try {
				String empid =null;
				 empid = (String)req.getParameter("empid");
		         	if(empid==null)  {
		 				Map md=model.asMap();
		 				empid=(String)md.get("Employee");
		 			}
		         	
		 			req.setAttribute("Publicationlist", service.getPublicationList(empid));
		 			req.setAttribute("Empdata", service.GetEmpData(empid));
		 			 return "pis/PublicationList";
			} catch (Exception e) {
				 logger.error(new Date() + " Inside PublicationList.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
			}
	   }
	   
	   @RequestMapping(value = "PublicationAddEditDelete.htm", method = {RequestMethod.GET ,RequestMethod.POST})
		public String PublicationAddEditDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception 
	   {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside PublicationAddEditDelete.htm " + Username);
			
			try {
				String empid = (String) req.getParameter("empid");
				String Action = (String) req.getParameter("Action");
				if ("ADD".equalsIgnoreCase(Action)) {
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("PisStateList", service.getPisStateList());
					return "pis/PublicationAdd";
				} else if ("EDIT".equalsIgnoreCase(Action)) {
					String appointmentid = (String) req.getParameter("PublicationId");
				
					req.setAttribute("PublicationDetails", service.getPublicationDetails(Integer.parseInt(appointmentid)));
					req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("PisStateList", service.getPisStateList());
					return "pis/PublicationEdit";
				} else {
			                                                                                    
					String qualiid = (String) req.getParameter("PublicationId");
					int count = service.DeleteProperty(qualiid, Username);
					if (count > 0) {
						redir.addAttribute("result", "Publication Details deleted Sucessfully");
					} else {
						redir.addAttribute("resultfail", "Publication Details deleted Unsucessfull");
					}
					redir.addFlashAttribute("Employee", empid);
					return "redirect:/PublicationList.htm";
				}
			} catch (Exception e) {
				logger.error(new Date() + "Inside PublicationAddEditDelete.htm " + Username ,e);
				e.printStackTrace();
				return "static/Error";
			}
		}
	  
	   @RequestMapping(value="AddPublication.htm" ,  method=RequestMethod.POST)  
	   public String AddPublication(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddPublication.htm "+Username);
	       
	       try {
	    	   String publicationtype = req.getParameter("PublicationType");
	    	   String author  = req.getParameter("Author");
	    	   String discipline = req.getParameter("Discipline");
	    	   String title =  req.getParameter("Title");
	    	   String publication = req.getParameter("Publication");
	    	   String publicationdate = req.getParameter("PublicationDate");
	    	   String patentno = req.getParameter("PatentNo");
	    	   String country = req.getParameter("Country");
	    	
	    	   String empid = req.getParameter("empid");
	    	  
	    	   Publication app = new Publication();
	    	  
	    	  app.setPub_type(publicationtype);
	    	  app.setAuthors(author);
	    	  app.setDiscipline(discipline);
	    	  app.setTitle(title);
	    	  app.setPub_name_vno_pno(publication);
	    	  app.setPub_date(DateTimeFormatUtil.dateConversionSql(publicationdate));
	    	  app.setPatent_no(patentno);
	    	  app.setCountry(country);
	    	  app.setEmpid(empid);
	    	  app.setIs_active(1);
	    	  app.setCreatedby(Username);
	    	  app.setCreateddate(sdtf.format(new Date()));
	    	  
	    	   int result = service.AddPublication(app);
	    	   if(result>0){
	    		   redir.addAttribute("result", "Publication Details Saved Successfully");	
	   		   } else {
	   			redir.addAttribute("resultfail", "Publication Details Saved Unsuccessful");
	    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);
	    	   
	    	   
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside AddPublication.htm "+Username,e);
		  	 e.printStackTrace();
		  	return "static/Error";
		  }
	       return "redirect:/PublicationList.htm";
	   }
	   
	   
	   @RequestMapping(value="EditPublication.htm" , method=RequestMethod.POST)
	   public String EditPublication(HttpServletRequest req , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside EditPublication.htm "+Username);
	       try {
	    	   String publicationtype = req.getParameter("PublicationType");
	    	   String author  = req.getParameter("Author");
	    	   String discipline = req.getParameter("Discipline");
	    	   String title =  req.getParameter("Title");
	    	   String publication = req.getParameter("Publication");
	    	   String publicationdate = req.getParameter("PublicationDate");
	    	   String patentno = req.getParameter("PatentNo");
	    	   String country = req.getParameter("Country");
	    	
	    	   String publicationid = req.getParameter("publicationid");
	    	   String empid = req.getParameter("empid");
	    	  
	    	   Publication app = new Publication();
	    	   		
	    	      app.setPublication_id(Integer.parseInt(publicationid));
		    	  app.setPub_type(publicationtype);
		    	  app.setAuthors(author);
		    	  app.setDiscipline(discipline);
		    	  app.setTitle(title);
		    	  app.setPub_name_vno_pno(publication);
		    	  app.setPub_date(DateTimeFormatUtil.dateConversionSql(publicationdate));
		    	  app.setPatent_no(patentno);
		    	  app.setCountry(country);
		    	  app.setModifiedby(Username);
		    	  app.setModifieddate(sdtf.format(new Date()));
	    	   
	    	   String comments = (String)req.getParameter("comments");
	    	   MasterEdit masteredit  = new MasterEdit();
	    	   masteredit.setCreatedBy(Username);
	    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    	   masteredit.setTableRowId(Long.parseLong(publicationid));
	    	   masteredit.setComments(comments);
	    	   masteredit.setTableName("pis_publication");
	    	  
	    	   MasterEditDto masterdto = new MasterEditDto();
	    	   masterdto.setFilePath(selectedFile);
	    	   
	    	   masterservice.AddMasterEditComments(masteredit , masterdto);
	    	   
	    	   int result = service.EditPublication(app);
		    	   if(result>0){
		    		   redir.addAttribute("result", "Publication Details Edited Sucessfully");	
		   		   } else {
		   			   redir.addAttribute("resultfail", "Publication Details Edited UnSuccessful");
		    	   }
	    	  
	    	   redir.addFlashAttribute("Employee", empid);   
	    	   
		      } catch (Exception e) {
		    	  logger.error(new Date() + " Inside EditPublication.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
		      }
	       return "redirect:/PublicationList.htm";
	   }
	   
	   @RequestMapping(value = "PassportList.htm" , method = {RequestMethod.GET, RequestMethod.POST})
	   public String PassportList(Model model,HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside PassportList.htm "+Username);
			try {
				String empid =null;
				 empid = (String)req.getParameter("empid");
		         	if(empid==null)  {
		 				Map md=model.asMap();
		 				empid=(String)md.get("Employee");
		 			}
		         	
		 			req.setAttribute("PassportVisitList", service.getPassportVisitList(empid));
		 			req.setAttribute("PassportList", service.getPassportList(empid));
		 			req.setAttribute("Empdata", service.GetEmpData(empid));
		 			 return "pis/PassportList";
			} catch (Exception e) {
				 logger.error(new Date() + " Inside PassportList.htm " + Username, e);
					e.printStackTrace();
					return "static/Error";
			}
	   }
	   
	   @RequestMapping(value ="AddEditPassport.htm" , method= {RequestMethod.GET,RequestMethod.POST})
	   public String AddEditPassport(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddEditPassport.htm "+Username);
	       try {

	    	   String Action = (String) req.getParameter("Action");
	    	   String empid =(String)req.getParameter("empid");
	    	   
	    	   	if("PassportEdit".equalsIgnoreCase(Action)) {
	    	   	
	    	   		Passport passport = service.getPassportData(empid);
	    	   		req.setAttribute("Empdata", service.GetEmpData(empid));
	    	   		 req.setAttribute("passport", passport);
	    	     
	    	   	      return "pis/PassportAddEdit";
	           }else{
	    	   	    req.setAttribute("Empdata", service.GetEmpData(empid));
	    	   	 return "pis/PassportAddEdit";
	    	   	}
	    	   	
		    } catch (Exception e) {
		    	 logger.error(new Date() +"Inside AddEditPassport.htm "+Username ,e);
		 	    e.printStackTrace();
		 	    return "static/Error";
		    }
	      
	   }
	   
	   
	   @RequestMapping(value="AddPassport.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	   public String AddPassport(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception{
		  
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddPassport.htm "+Username);  
	       String Action = (String)request.getParameter("Action");
		   try {
			    String passporttype = request.getParameter("PassportType").trim().replaceAll(" +", " ");
				String status = request.getParameter("Status");
				String passportno = request.getParameter("PassportNo").trim().replaceAll(" +", " ");
				String validfrom = request.getParameter("ValidFrom").trim().replaceAll(" +", " ");
				String validto = request.getParameter("ValidTo").trim().replaceAll(" +", " ");
		
				String empid= request.getParameter("empid"); 

				Passport pport= new Passport();

				pport.setEmpId(empid);
				pport.setPassportType(passporttype);
				pport.setStatus(status);
				pport.setPassportNo(passportno);
				pport.setValidFrom(DateTimeFormatUtil.dateConversionSql(validfrom));
				pport.setValidTo(DateTimeFormatUtil.dateConversionSql(validto));

			if("ADD".equalsIgnoreCase(Action)) {
				
				pport.setCreatedBy(Username);
				pport.setCreatedDate(sdf.format(new Date()));
	        	  long result  =  service.AddPassport(pport); 
	        	 
	        	    if(result>0) {
	        	    	 redir.addAttribute("result", "Passport details Add Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Passport details  ADD Unsuccessful");	
	        	    }
	        	    redir.addFlashAttribute("Employee", empid);
			}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside AddPassport.htm "+Username ,e);  
				e.printStackTrace();
				return "static/Error";
			}
		   return "redirect:/PassportList.htm";
	   }
	   
	   @RequestMapping(value="EditPassport.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	   public String EditPassport(HttpServletRequest request , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
		  
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside EditPassport.htm "+Username);  
	       String Action = (String)request.getParameter("Action");
		   try {
			   String passporttype = request.getParameter("PassportType").trim().replaceAll(" +", " ");
				String status = request.getParameter("Status");
				String passportno = request.getParameter("PassportNo").trim().replaceAll(" +", " ");
				String validfrom = request.getParameter("ValidFrom").trim().replaceAll(" +", " ");
				String validto = request.getParameter("ValidTo").trim().replaceAll(" +", " ");
		
				String empid= request.getParameter("empid"); 

				Passport pport= new Passport();

				pport.setEmpId(empid);
				pport.setPassportType(passporttype);
				pport.setStatus(status);
				pport.setPassportNo(passportno);
				pport.setValidFrom(DateTimeFormatUtil.dateConversionSql(validfrom));
				pport.setValidTo(DateTimeFormatUtil.dateConversionSql(validto));
				
			

		 if ("EDIT".equalsIgnoreCase(Action)) {
				String passportid = (String)request.getParameter("passportid");
				pport.setPassportId(Integer.parseInt(passportid));
				pport.setModifiedBy(Username);
				pport.setModifiedDate(sdtf.format(new Date()));
				
				   String comments = (String)request.getParameter("comments");
		    	   MasterEdit masteredit  = new MasterEdit();
		    	   masteredit.setCreatedBy(Username);
		    	   masteredit.setCreatedDate(sdtf.format(new Date()));
		    	   masteredit.setTableRowId(Long.parseLong(passportid));
		    	   masteredit.setComments(comments);
		    	   masteredit.setTableName("pis_passport");
		    	   
		    	   MasterEditDto masterdto = new MasterEditDto();
		    	   masterdto.setFilePath(selectedFile);
		    	   masterservice.AddMasterEditComments(masteredit , masterdto);
							
	        	  long result  =  service.EditPassport(pport); 
	        	 
	        	    if(result>0) {
	        	    	 redir.addAttribute("result", "Passport Details Edit Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Passport Details Edit Unsuccessful");	
	        	    }
	        	    redir.addFlashAttribute("Employee", empid);
			}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside EditPassport.htm "+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
		   return "redirect:/PassportList.htm";
	   }
	   
	   @RequestMapping(value = "ForeignVisitAddEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		public String ForeignVisitAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{   
			String Username = (String) ses.getAttribute("Username");
			String Action =(String)req.getParameter("Action");
			String empid = (String)req.getParameter("empid");
			
			logger.info(new Date() +"Inside ForeignVisitAddEdit.htm "+Username); 
			try {
				if("ADD".equalsIgnoreCase(Action)){
					 req.setAttribute("Empdata", service.GetEmpData(empid));
					req.setAttribute("States", service.getPisStateList());
							
			  return "pis/PassportForeignVisitAddEdit";
			}else if("EDIT".equalsIgnoreCase(Action)) {
				
				  String pasportid = (String)req.getParameter("PassportVisitId"); 	
				  PassportForeignVisit foreignvisit = service.getForeignVisitData(Integer.parseInt(pasportid) );
				  req.setAttribute("foreignvisit", foreignvisit);
				  req.setAttribute("Empdata", service.GetEmpData(empid));				
				  req.setAttribute("States", service.getPisStateList());
				  
			   return "pis/PassportForeignVisitAddEdit";
			}else{
				
				String passportvisitid = (String)req.getParameter("PassportVisitId");
						
				int count =service.deleteForeignVisit(passportvisitid,Username);
				if (count > 0) {
					redir.addAttribute("result", "Foreign Visit Details Deleted Sucessfully");
				} else {
					redir.addAttribute("resultfail", "Foreign Visit Details Delete Unsuccessful");
				}
				redir.addFlashAttribute("Employee", empid);
				return "redirect:/PassportList.htm";
			}
			} catch (Exception e) {
				logger.error(new Date() +"Inside ForeignVisitAddEdit.htm "+Username,e); 
				e.printStackTrace();
				return "static/Error";
			}
			
			
		}
	           
	   
	   @RequestMapping(value="ForeignVisitAdd.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	   public String ForeignVisitAdd(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception{
		  
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ForeignVisitAdd.htm "+Username);  
	       String Action = (String)request.getParameter("Action");
		   try {
			    String state = request.getParameter("state").trim().replaceAll(" +", " ");
				String visitfrom = request.getParameter("VisitFrom");
				String visitto = request.getParameter("VisitTo");
				String nocno = request.getParameter("NOCNo");
				String nocdate = request.getParameter("NOCDate");
				String purpose = request.getParameter("Purpose");
				String nocissuedfrom = request.getParameter("NOCIssuedFrom");
				String remark = request.getParameter("Remark");
			
				
				String empid= request.getParameter("empid"); 

				PassportForeignVisit pfv= new PassportForeignVisit();
				
				pfv.setEmpId(empid);
				pfv.setCountryName(state);
				pfv.setVisitFromDate(DateTimeFormatUtil.dateConversionSql(visitfrom));
				pfv.setVisitToDate(DateTimeFormatUtil.dateConversionSql(visitto));
				pfv.setNocLetterNo(nocno);
				pfv.setNocLetterDate(DateTimeFormatUtil.dateConversionSql(nocdate));
				pfv.setPurpose(purpose);
				pfv.setNocIssuedFrom(nocissuedfrom);
				pfv.setRemarks(remark);
				
			if("ADD".equalsIgnoreCase(Action)) {
				pfv.setIsActive(1);
				pfv.setCreatedBy(Username);
				pfv.setCreatedDate(sdf.format(new Date()));
	        	  long result  =  service.AddForeignVisit(pfv); 
	        	 
	        	    if(result>0) {
	        	    	 redir.addAttribute("result", "Foreign Visit Details Add Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Foreign Visit Details ADD Unsuccessful");	
	        	    }
	        	    redir.addFlashAttribute("Employee", empid);
			}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside ForeignVisitAdd.htm "+Username ,e);  
				e.printStackTrace();
				return "static/Error";
			}
		   return "redirect:/PassportList.htm";
	   }
	   
	   @RequestMapping(value="ForeignVisitEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	   public String ForeignVisitEdit(HttpServletRequest request , HttpSession ses ,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)throws Exception{
		  
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ForeignVisitEdit.htm "+Username);  
	       String Action = (String)request.getParameter("Action");
		   try {
			   String state = request.getParameter("state").trim().replaceAll(" +", " ");
				String visitfrom = request.getParameter("VisitFrom");
				String visitto = request.getParameter("VisitTo");
				String nocno = request.getParameter("NOCNo");
				String nocdate = request.getParameter("NOCDate");
				String purpose = request.getParameter("Purpose");
				String nocissuedfrom = request.getParameter("NOCIssuedFrom");
				String remark = request.getParameter("Remark");
				String passportvisitid = request.getParameter("foreignvisitid");
				
				String empid= request.getParameter("empid"); 

				PassportForeignVisit pfv= new PassportForeignVisit();
				
				pfv.setPassportVisitId(Integer.parseInt(passportvisitid));
				pfv.setEmpId(empid);
				pfv.setCountryName(state);
				pfv.setVisitFromDate(DateTimeFormatUtil.dateConversionSql(visitfrom));
				pfv.setVisitToDate(DateTimeFormatUtil.dateConversionSql(visitto));
				pfv.setNocLetterNo(nocno);
				pfv.setNocLetterDate(DateTimeFormatUtil.dateConversionSql(nocdate));
				pfv.setPurpose(purpose);
				pfv.setNocIssuedFrom(nocissuedfrom);
				pfv.setRemarks(remark);
			
				
		 if ("EDIT".equalsIgnoreCase(Action)) {
				
				pfv.setPassportVisitId(Integer.parseInt(passportvisitid));
				pfv.setModifiedBy(Username);
				pfv.setModifiedDate(sdtf.format(new Date()));
				
				   String comments = (String)request.getParameter("comments");
		    	   MasterEdit masteredit  = new MasterEdit();
		    	   masteredit.setCreatedBy(Username);
		    	   masteredit.setCreatedDate(sdtf.format(new Date()));
		    	   masteredit.setTableRowId(Long.parseLong(passportvisitid));
		    	   masteredit.setComments(comments);
		    	   masteredit.setTableName("pis_passport_visit");
		    	   
		    	   MasterEditDto masterdto = new MasterEditDto();
		    	   masterdto.setFilePath(selectedFile);
		    	   masterservice.AddMasterEditComments(masteredit , masterdto);
							
	        	  long result  =  service.EditForeignVisit(pfv); 
	        	 
	        	    if(result>0) {
	        	    	 redir.addAttribute("result", "Foreign Visit Details Edit Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Foreign Visit Details Edit Unsuccessful");	
	        	    }
	        	    redir.addFlashAttribute("Employee", empid);
			}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside ForeignVisitEdit.htm "+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
		   return "redirect:/PassportList.htm";
	   }
	   
	   
	   @RequestMapping(value = "QualificationListAddEditDelete.htm" , method = {RequestMethod.GET ,RequestMethod.POST})
	   public String QualificationList(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside QualificationListAddEditDelete.htm "+Username);  
	       
		try {
			String delete =req.getParameter("DeleteQualification"); 
			String edit = req.getParameter("EditQuali");
			ses.setAttribute("SidebarActive","QualificationListAddEditDelete_htm");
			
			
			if(delete!=null){
				
				long result  =  service.DeleteEducationQualification(delete,Username); 
	        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Educational Qualification Delete Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Educational Qualification Delete Unsuccessful");	
        	    }
				return	"redirect:/QualificationListAddEditDelete.htm";
			}else if(edit!=null){
			
				String qualification = req.getParameter("qualification");
				
				long result  =  service.EditEducationQualification(edit,qualification,Username); 
	        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Educational Qualification Edit Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Educational Qualification Edit Unsuccessful");	
        	    }
				return "redirect:/QualificationListAddEditDelete.htm";
			}else{ 
				req.setAttribute("QualificationList", service.getQualificationList());
				return "pis/QualificationCodeListAddEditDelete";
			}
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside QualificationListAddEditDelete.htm "+Username ,e);
			e.printStackTrace();
			 return "static/Error";
		}
		   
	   }
	   
	   @RequestMapping(value = "AddQualification.htm" , method = {RequestMethod.GET ,RequestMethod.POST})
	   public String AddQualification(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddQualification.htm "+Username);  
	       try {
	    	   QualificationCode qc = new QualificationCode();
	    	   String qualification = req.getParameter("Addqualification");
	    	   
	    	   qc.setQuali_title(qualification);
	    	   qc.setIs_active(1);
	    	   qc.setCreated_by(Username);
	    	   qc.setModified_date(sdtf.format(new Date()));
	    	   
	    	   long result  =  service.AddEducationQualification(qc); 
	        	 
       	    if(result>0) {
       	    	 redir.addAttribute("result", "Educational Qualification Add Successfull");	
       		} else {
       			 redir.addAttribute("resultfail", "Educational Qualification Add Unsuccessful");	
       	    }
	    	   
	    	   return "redirect:/QualificationListAddEditDelete.htm";
			}catch (Exception e){
				logger.error(new Date() +"Inside AddQualification.htm "+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
	   }
	   
	   @RequestMapping(value = "DisciplineListAddEditDelete.htm" , method = {RequestMethod.GET ,RequestMethod.POST})
	   public String DisciplineList(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside DisciplineListAddEditDelete.htm "+Username);  
	       
		try {
			String delete =req.getParameter("DeleteDiscipline"); 
			String edit = req.getParameter("EditDisci");
			ses.setAttribute("SidebarActive","DisciplineListAddEditDelete_htm");
			
			
			if(delete!=null){
				
				long result  =  service.DeleteDiscipline(delete,Username); 
	        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Discipline Delete Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Discipline Delete Unsuccessful");	
        	    }
				return	"redirect:/DisciplineListAddEditDelete.htm";
			}else if(edit!=null){
			
				String discipline = req.getParameter("Discipline");
				
				long result  =  service.EditDiscipline(edit,discipline,Username); 
	        	 
        	    if(result>0) {
        	    	 redir.addAttribute("result", "Discipline Edit Successfull");	
        		} else {
        			 redir.addAttribute("resultfail", "Discipline Edit Unsuccessful");	
        	    }
				return "redirect:/DisciplineListAddEditDelete.htm";
			}else{ 
				req.setAttribute("DisciplineList", service.getDiscipline());
				return "pis/DisciplineListAddEditDelete";
			}
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DisciplineListAddEditDelete.htm "+Username ,e);
			e.printStackTrace();
			 return "static/Error";
		}
		   
	   }
	   
	   @RequestMapping(value = "AddDiscipline.htm" , method = {RequestMethod.GET ,RequestMethod.POST})
	   public String AddDiscipline(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside AddDiscipline.htm "+Username);  
	       try {
	    	   DisciplineCode qc = new DisciplineCode();
	    	   String qualification = req.getParameter("Adddiscipline");
	    	   
	    	   qc.setDisci_title(qualification);
	    	   qc.setIs_active(1);
	    	   qc.setCreated_by(Username);
	    	   qc.setModified_date(sdtf.format(new Date()));
	    	   
	    	   long result  =  service.AddDiscipline(qc); 
	        	 
       	    if(result>0) {
       	    	 redir.addAttribute("result", "Discipline Add Successfull");	
       		} else {
       			 redir.addAttribute("resultfail", "Discipline Add Unsuccessful");	
       	    }
	    	   
	    	   return "redirect:/DisciplineListAddEditDelete.htm";
			}catch (Exception e){
				logger.error(new Date() +"Inside AddDiscipline.htm "+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
	   }
	   @RequestMapping(value = "PIS.htm", method = { RequestMethod.GET,RequestMethod.POST})
		public String PisDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside Pis.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				List<Object[]> admindashboard = adminservice.HeaderSchedulesList("5" ,logintype); 
			
				ses.setAttribute("formmoduleid", "7"); 
				ses.setAttribute("SidebarActive", "PIS_htm");
				req.setAttribute("dashboard", admindashboard);
				Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
				String Name=emp[1]+"  ("+emp[2]+")";
				req.setAttribute("empname",Name);
				
				return "pis/PisDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside Pis.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
		}
	   
	   @RequestMapping(value ="GroupDetails.htm" , method = {RequestMethod.GET ,RequestMethod.POST})
	   public String GroupDetails(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside GroupDetails.htm "+Username); 
		   try {
			  
			
				String choice = request.getParameter("Mainlist");
				
				if (request.getParameter("Mainlist") != null) {

					List<Object[]> stusOrDesigOrGrpOrGenList = null;
					String GenderChoice = null;

					switch (choice) {

					case "Employee Status Wise":
						stusOrDesigOrGrpOrGenList = service.GetEmpStatusList();
						break;
					case "Group/Division Wise":
						stusOrDesigOrGrpOrGenList = service.getGroupName();
						break;
					case "Designation Wise":
						stusOrDesigOrGrpOrGenList = service.getDesignation();
						break;
					case "Gender Wise":
						GenderChoice = "GenderChoice";
						break;
						default:

					}

					request.setAttribute("stusOrDesigOrGrpOrGenList", stusOrDesigOrGrpOrGenList);
					request.setAttribute("GenderChoice", GenderChoice);
					request.setAttribute("choice", choice);

				}

				if (request.getParameter("SubList") != null) {

					

					// split Id and value ..
					String SubList = request.getParameter("SubList");
					String[] DropDownOption = SubList.split("_");
					String DropDownOption0 = DropDownOption[0];
					String DropDownOption1 = DropDownOption[1];

					List<Object[]> ResultedList = null;

					if ("All".equalsIgnoreCase(DropDownOption0)) {
						ResultedList = service.fetchAllEmployeeDetail();
					} else {

						switch (choice) {

						case "Employee Status Wise":
							ResultedList = service.getEmployeeStatusWise(DropDownOption0);
							break;

						case "Group/Division Wise":
							
							int GroupId = Integer.parseInt(DropDownOption0);
							ResultedList = service.getEmployeeDivOrGroupWise(GroupId);
							break;

						case "Designation Wise":
							int DesigId = Integer.parseInt(DropDownOption0);
							ResultedList = service.getEmployeeDesignationWise(DesigId);
							break;

						case "Gender Wise":
							ResultedList = service.getEmployeeGenderWise(DropDownOption0);
							break;
						default:
						}
					}
					request.setAttribute("ResultedList", ResultedList);
					request.setAttribute("DropDownOption1", DropDownOption1);
					request.setAttribute("DropDownOption0", DropDownOption0);
				}
			
			   ses.setAttribute("SidebarActive","GroupDetails_htm");
			   return "pis/GroupDetails";
		   }catch (Exception e) {
				logger.error(new Date() +"Inside GroupDetails.htm"+Username ,e);
				e.printStackTrace();
				 return "static/Error";
		  }
	   }
	   
	   @RequestMapping(value ="NominalRollReport.htm" , method = {RequestMethod.POST,RequestMethod.GET})
	   public String NominalRollReport(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside NominalRollReport.htm "+Username); 
		   try {
			   String catType = req.getParameter("catType");
			 
			   List<Object[]> PersonalDetailsAll=null;
			   if(catType!=null) {
				   PersonalDetailsAll = service.fetchPersonalDetailsNGOorCGO(catType);
			   }else{
				   PersonalDetailsAll = service.fetchAllPersonalDetail();
			   }
			 req.setAttribute("catType", catType);
			  req.setAttribute("personaldetailsall", PersonalDetailsAll);
			   ses.setAttribute("SidebarActive","NominalRollReport_htm");
			   return "pis/NominalRollList";
			}catch (Exception e){
				logger.error(new Date() +"Inside NominalRollReport.htm"+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
	   }
	   
	   @RequestMapping(value ="ConfigurableReport.htm" , method = {RequestMethod.POST,RequestMethod.GET})
	   public String ConfigurableReport(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ConfigurableReport.htm "+Username); 
		   try {
			   
			   if (request.getParameter("submit") != null || request.getParameter("Details") != null) {

				String name = request.getParameter("name");

				// split DesigId And Designation ..
				String DesigIdAndDesignation = request.getParameter("Designation");
				String[] DesigIdAndDesignationArray = DesigIdAndDesignation.split("#");
				String DesigId = DesigIdAndDesignationArray[0];
				String DesigName = DesigIdAndDesignationArray[1];

				// split GroupId And GroupName ..
				String GroupIdAndGroupName = request.getParameter("GroupDivision");
				String[] GroupIdAndGroupNameArray = GroupIdAndGroupName.split("#");
				String GroupId = GroupIdAndGroupNameArray[0];
				String GroupName1 = GroupIdAndGroupNameArray[1];

				// split CatId And CatClass ..
				String CatIdAndCatClass = request.getParameter("CatClass");
				String[] CatIdAndCatClassArray = CatIdAndCatClass.split("#");
				String CatId = CatIdAndCatClassArray[0];
				String CatClass1 = CatIdAndCatClassArray[1];

				// split GenderId And GenderName ..
				String GenderIdAndGenderName = request.getParameter("Gender");
				String[] GenderIdAndGenderNameArray = GenderIdAndGenderName.split("#");
				String Gender = GenderIdAndGenderNameArray[0];
				String GenderName = GenderIdAndGenderNameArray[1];

				// split CadreId And CadreName ..
				String CadreIdAndCadreName = request.getParameter("Cadre");
				String[] CadreIdAndCadreNameArray = CadreIdAndCadreName.split("#");
				String CadreId = CadreIdAndCadreNameArray[0];
				String CadreName1 = CadreIdAndCadreNameArray[1];

				String ServiceStatus = request.getParameter("ServiceStatus");

				// split CategoryId And CategoryName ..
				String CategoryIdAndCategoryName = request.getParameter("Category");
				String[] CategoryIdAndCategoryNameArray = CategoryIdAndCategoryName.split("#");
				String CategoryId = CategoryIdAndCategoryNameArray[0];
				String CategoryName = CategoryIdAndCategoryNameArray[1];

				String BG = request.getParameter("BG");

				// split modeOfRecruitId And modeOfRecruitName ..
				String modeOfRecruitIdAndmodeOfRecruitName = request.getParameter("Appointment");
				String[] modeOfRecruitIdAndmodeOfRecruitNameArray = modeOfRecruitIdAndmodeOfRecruitName.split("#");
				String modeOfRecruitId = modeOfRecruitIdAndmodeOfRecruitNameArray[0];
				String modeOfRecruitName = modeOfRecruitIdAndmodeOfRecruitNameArray[1];

				// split AwardId And AwardName..

				String AwardIdAndName = request.getParameter("Awards");
				String[] AwardIdAndNameArray = AwardIdAndName.split("#");
				String AwardId = AwardIdAndNameArray[0];
				String AwardName = AwardIdAndNameArray[1];
				
				List<Object[]> ConfigurableReportList = service.getConfigurableReportList(name, DesigId, GroupId, CatId,
						Gender, CadreId, ServiceStatus, CategoryId, BG, modeOfRecruitId, AwardId);

				request.setAttribute("ConfigurableReportList", ConfigurableReportList);

				Map<String, String> map = new HashMap<String, String>();
				map.put("name", name);
				map.put("DesigId", DesigId);
				map.put("DesigName", DesigName);
				map.put("GroupId", GroupId);
				map.put("GroupName1", GroupName1);
				map.put("CatId", CatId);
				map.put("CatClass1", CatClass1);
				map.put("Gender", Gender);
				map.put("GenderName", GenderName);
				map.put("CadreId", CadreId);
				map.put("CadreName1", CadreName1);
				map.put("ServiceStatus", ServiceStatus);
				map.put("CategoryId", CategoryId);
				map.put("CategoryName", CategoryName);
				map.put("BG", BG);
				map.put("modeOfRecruitId", modeOfRecruitId);
				map.put("modeOfRecruitName", modeOfRecruitName);
				map.put("AwardId", AwardId);
				map.put("AwardName", AwardName);
				request.setAttribute("map", map);

				if (request.getParameter("Details") != null) {

					String empid = request.getParameter("Details");
					Object[] empdata = service.GetEmpData(empid);
					Object[] employeedetails = service.GetAllEmployeeDetails(empid);	
					Object[] peraddressdetails = service.EmployeePerAddressDetails(empid);	
					List<Object[]> resaddressdetails  = service.EmployeeResAddressDetails(empid);	
					List<Object[]> familydetails = service.getFamilydetails(empid);
					 String basevalue=null;
			            if(empdata!=null && empdata[3]!=null) {
			            	basevalue=service.getimage(empdata[3].toString());
			            }
			
			            request.setAttribute("empid", empid);
			            request.setAttribute("employeedetails", employeedetails);
			            request.setAttribute("resaddressdetails", resaddressdetails);
			            request.setAttribute("peraddressdetails", peraddressdetails);
						request.setAttribute("familydetails", familydetails);
						request.setAttribute("basevalue", basevalue);
						request.setAttribute("Educationlist", service.getEducationList(empid));
						request.setAttribute("Appointmentlist", service.getAppointmentList(empid));
						request.setAttribute("Awardslist", service.getAwardsList(empid));
						request.setAttribute("Propertylist", service.getPropertyList(empid));
						request.setAttribute("Publicationlist", service.getPublicationList(empid));
						request.setAttribute("PassportVisitList", service.getPassportVisitList(empid));
						request.setAttribute("PassportList", service.getPassportList(empid));
				
				}
		   }
			    request.setAttribute("AwardsNamelist", service.getPisAwardsList());
			    request.setAttribute("modeOfRecruit", service.getRecruitment());
			    request.setAttribute("desiglist", service.DesigList());		
			    request.setAttribute("piscategorylist", service.PisCategoryList());
				request.setAttribute("piscatclasslist", service.PisCatClassList());
				request.setAttribute("piscaderlist", service.PisCaderList());
				request.setAttribute("empstatuslist", service.EmpStatusList());
				request.setAttribute("paylevellist", service.PayLevelList());	
				request.setAttribute("divisionlist", service.DivisionList());
				  ses.setAttribute("SidebarActive","ConfigurableReport_htm");
				  
			   return "pis/ConfigurableReport";
			} catch (Exception e) {
				logger.error(new Date() +"Inside ConfigurableReport.htm"+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
	   }
	   
	   @RequestMapping(value = "/Configurableselectionwise.htm" ,method = {RequestMethod.POST,RequestMethod.GET})
		public String Democonfigurablereport(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ConfigurableReport.htm "+Username); 
		   
		   try {
				
			   ses.setAttribute("SidebarActive","Configurableselectionwise_htm");
			   return "pis/ConfigurableReportSelectionWise";
			} catch (Exception e) {
				logger.error(new Date() +"Inside Configurableselectionwise.htm"+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
			

		}
	   
	   @RequestMapping(value ="configurablereportselectionwise.htm" ,method = {RequestMethod.POST,RequestMethod.GET})
		public String selectionwiseReport(HttpServletRequest request , HttpSession ses)throws Exception {
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside configurablereportselectionwise.htm "+Username); 
		   try {
			   
			    String DesignationModal = request.getParameter("DesignationModal");
				String GroupModal = request.getParameter("GroupModal");
				String CatClassModal = request.getParameter("CatClassModal");
				String GenderModal = request.getParameter("GenderModal");
				String CadreModal = request.getParameter("CadreModal");
				String ServiceStatusModal = request.getParameter("ServiceStatusModal");
				String CategoryModal = request.getParameter("CategoryModal");
				String BloodModal = request.getParameter("BloodModal");
				String ReligionModal = request.getParameter("ReligionModal");
				String QuarterModal = request.getParameter("QuarterModal");
				String HandicapModal = request.getParameter("HandicapModal");
				String PayLevelModal = request.getParameter("PayLevelModal");
				String QualificationModal = request.getParameter("QualificationModal");
				String PublicationModal = request.getParameter("PublicationModal");
				String PropertyModal = request.getParameter("PropertyModal");
				String AppointmentModal = request.getParameter("AppointmentModal");
				String AwardsModal = request.getParameter("AwardsModal");

				request.setAttribute("ServiceStatuslist", ServiceStatusModal);
				request.setAttribute("BGlist", BloodModal);
				request.setAttribute("Propertylist", PropertyModal);
				request.setAttribute("Handicap", HandicapModal);
				request.setAttribute("Religionlist", ReligionModal);
				request.setAttribute("QuarterAvailable", QuarterModal);
				request.setAttribute("Publicationlist", PublicationModal);
				// Getting Dropdown values from DB
				if (null != DesignationModal) {
					List<Object[]> Designation = service.getDesignation();
					request.setAttribute("Designation", Designation);
				}
				request.setAttribute("Genderlist", GenderModal);
				if (null != GroupModal) {
					List<Object[]> GroupName = service.getGroupName();
					request.setAttribute("GroupName", GroupName);
				}
				if (null != CatClassModal) {
					List<PisCatClass> CatClass = service.PisCatClassList();
					request.setAttribute("Catclass", CatClass);
				}
				if (null != CadreModal) {
					List<PisCadre> cadreNames = service.PisCaderList();
					request.setAttribute("CadreNames", cadreNames);
				}
				if (null != CategoryModal) {
					List<PisCategory> Category = service.PisCategoryList();
					request.setAttribute("Category", Category);
				}
				if (null != PayLevelModal) {
					List<PisPayLevel> payLevel = service.PayLevelList();
					request.setAttribute("PayLevel", payLevel);
				}
				if (null != QualificationModal) {
					List<Object[]> qulaficationList = service.getQualificationList();
					request.setAttribute("qualification", qulaficationList);
				}
				if (null != AppointmentModal) {
					List<Object[]> modeOfRecruit = service.getRecruitment();
					request.setAttribute("ModeOfRecruit", modeOfRecruit);
				}
				if (null != AwardsModal) {
					List<Object[]> awardslist = service.getPisAwardsList();
					request.setAttribute("awardslist", awardslist);
				}
				return "pis/ConfigurableReportSelectionWise";
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside Configurableselectionwise.htm"+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
		}
	   
	   @RequestMapping(value = "getconfigurablereportselectionwise.htm" ,method = {RequestMethod.POST,RequestMethod.GET})
		public String getselectionwiseCfReport(HttpServletRequest request , HttpSession ses) throws Exception {
			
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside configurablereportselectionwise.htm "+Username); 
				try {
					Map<String, String> selects = new HashMap<>();
					List<String> tblheadings = new ArrayList<String>();
					tblheadings.add("EMPID");
					tblheadings.add("NAME");

					String name = request.getParameter("name");
					String designation = request.getParameter("Designation");
					String groupDivision = request.getParameter("GroupDivision");
					String catClass = request.getParameter("CatClass");
					String gender = request.getParameter("Gender");
					String cadre = request.getParameter("Cadre");
					String serviceStatus = request.getParameter("ServiceStatus");
					String category = request.getParameter("Category");
					String BG = request.getParameter("BG");
					String religion = request.getParameter("religion");
					String quarter = request.getParameter("Quarter");
					String physicalHandicap = request.getParameter("PhysicalHandicap");
					String pay_Level = request.getParameter("PayLevel");
					String qualification = request.getParameter("qualification");
					String pubType = request.getParameter("pubType");
					String propertyType = request.getParameter("PropertyAcquiredType");
					String appointment = request.getParameter("Appointment");
					String awards = request.getParameter("Awards");

					if (name != null && !("".equals(name.trim()))) {
						selects.put("name", name);
					}

					if (null != designation && !("".equals(designation))) {
						List<Object[]> Designation = service.getDesignation();
						request.setAttribute("Designation", Designation);
						tblheadings.add("Designation");
						selects.put("desigId", designation.split("#")[0]);
						selects.put("desigName", designation.split("#")[1]);

					}
					if (null != groupDivision && !("".equals(groupDivision))) {
						List<Object[]> GroupName = service.getGroupName();
						request.setAttribute("GroupName", GroupName);
						tblheadings.add("GROUP");
						selects.put("groupId", groupDivision.split("#")[0]);
						selects.put("groupName", groupDivision.split("#")[1]);
					}

					if (null != catClass && !("".equals(catClass))) {
						List<PisCatClass> CatClass = service.PisCatClassList();
						request.setAttribute("Catclass", CatClass);
						tblheadings.add("Category Class");
						selects.put("CatId", catClass.split("#")[0]);
						selects.put("CatName", catClass.split("#")[1]);

					}
					if (gender != null && !("".equals(gender))) {
						request.setAttribute("Genderlist", "GenderModal");
						tblheadings.add("Gender");
						selects.put("genderId", gender.split("#")[0]);
						selects.put("genderName", gender.split("#")[1]);
					}

					if (null != cadre && !("".equals(cadre))) {
						
						List<PisCadre> cadreNames = service.PisCaderList();
						request.setAttribute("CadreNames", cadreNames);
						tblheadings.add("Cadre");
						selects.put("cadreId", cadre.split("#")[0]);
						selects.put("cadreName", cadre.split("#")[1]);
					}
					if (serviceStatus != null && !("".equals(serviceStatus))) {
						request.setAttribute("ServiceStatuslist", "ServiceStatusModal");
						tblheadings.add("Service Status");
						selects.put("serviceStatus", serviceStatus);
					}
					if (null != category && !("".equals(category))) {
						
						List<PisCategory> Category = service.PisCategoryList();
						request.setAttribute("Category", Category);
						tblheadings.add("Category");
						selects.put("categoryId", category.split("#")[0]);
						selects.put("categoryName", category.split("#")[1]);
					}
					if (BG != null && !("".equals(BG))) {
						request.setAttribute("BGlist", "BloodModal");
						tblheadings.add("Blood Group");
						selects.put("BG", BG);
					}
					if (religion != null && !("".equals(religion))) {
						request.setAttribute("Religionlist", "ReligionModal");
						tblheadings.add("RELIGION");
						selects.put("religion", religion);
					}
					if (quarter != null && !("".equals(quarter))) {
						request.setAttribute("QuarterAvailable", "QuarterModal");
						tblheadings.add("Govt.QUARTER");
						selects.put("quarterId", quarter.split("#")[0]);
						selects.put("quarterName", quarter.split("#")[1]);
					}
					if (physicalHandicap != null && !("".equals(physicalHandicap))) {
						request.setAttribute("Handicap", "HandicapModal");
						tblheadings.add("HANDICAPPED");
						selects.put("physicalHandicapId", physicalHandicap.split("#")[0]);
						selects.put("physicalHandicapName", physicalHandicap.split("#")[1]);
					}
					if (null != pay_Level && !("".equals(pay_Level))) {
						
						List<PisPayLevel> payLevel = service.PayLevelList();
						request.setAttribute("PayLevel", payLevel);
						tblheadings.add("PAYLEVEL");
						selects.put("pay_LevelId", pay_Level.split("#")[0]);
						selects.put("pay_LevelName", pay_Level.split("#")[1]);
					}
					if (null != qualification && !("".equals(qualification))) {
						List<Object[]> qulaficationList = service.getQualificationList();
						request.setAttribute("qualification", qulaficationList);
						tblheadings.add("QUALIFICATION");
						selects.put("qualificationId", qualification.split("#")[0]);
						selects.put("qualificationName", qualification.split("#")[1]);
					}
					if (pubType != null && !("".equals(pubType))) {
						request.setAttribute("Publicationlist", "PublicationModal");
						tblheadings.add("PUBLICATION");
						selects.put("pubType", pubType);
					}
					if (propertyType != null && !("".equals(propertyType))) {
						request.setAttribute("Propertylist", "PropertyModal");
						tblheadings.add("PROPERTY");
						selects.put("propertyType", propertyType);
					}
					if (null != appointment && !("".equals(appointment))) {
						List<Object[]> modeOfRecruit = service.getRecruitment();
						request.setAttribute("ModeOfRecruit", modeOfRecruit);
						tblheadings.add("APPOINTMENT");
						selects.put("appointmentId", appointment.split("#")[0]);
						selects.put("appointmentName", appointment.split("#")[1]);
					}
					if (null != awards && !("".equals(awards))) {
						List<Object[]> awardslist = service.getPisAwardsList();
						request.setAttribute("awardslist", awardslist);
						tblheadings.add("AWARDS");
						selects.put("awardsId", awards.split("#")[0]);
						selects.put("awardsName", awards.split("#")[1]);
					}

					List<Object[]> confreports = service.getconfigurablereportselectionwise(name, designation, groupDivision,
							catClass, gender, cadre, serviceStatus, pay_Level, qualification, propertyType, pubType, category, BG,
							quarter, physicalHandicap, religion, appointment, awards);

					request.setAttribute("confreports", confreports);
					request.setAttribute("tblheadings", tblheadings);
					request.setAttribute("selects", selects);
					if (CollectionUtils.isEmpty(confreports)) {
						String norecords = "norecords";
						request.setAttribute("norecords", norecords);
					}

					return "pis/ConfigurableReportSelectionWise";

				} catch (Exception e) {
					logger.error(new Date() +"Inside getconfigurablereportselectionwise.htm"+Username ,e);
					e.printStackTrace();
					 return "static/Error";
				}
			
		}
	   
	   @RequestMapping(value = "IndividualDetails.htm", method = {RequestMethod.POST , RequestMethod.GET})
		public String IndividualDetails(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside IndividualDetails.htm "+Username);		
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
							
				List<Object[]> employeelist =service.getAllEmployeeList();
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
	            req.setAttribute("EmployeeList", employeelist);
	            
	            req.setAttribute("Educationlist", service.getEducationList(empid));
	            req.setAttribute("Appointmentlist", service.getAppointmentList(empid));
	            req.setAttribute("Awardslist", service.getAwardsList(empid));
	            req.setAttribute("Propertylist", service.getPropertyList(empid));
	            req.setAttribute("Publicationlist", service.getPublicationList(empid));
	            req.setAttribute("PassportVisitList", service.getPassportVisitList(empid));
	            req.setAttribute("PassportList", service.getPassportList(empid));
	            
	            ses.setAttribute("SidebarActive","IndividualDetails_htm");
				return "pis/IndividualDetails";
			}catch (Exception e) {
				logger.error(new Date() +" Inside IndividualDetails.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
		}
	   
	   
	   @RequestMapping(value ="DobDorDoaDojReport.htm" , method = { RequestMethod.POST , RequestMethod.GET})
		public String showDatereport(HttpServletRequest req,HttpSession ses)throws Exception 
	   {
		   String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DobDorDoaDojReport.htm "+Username);		
				try {
					List<String> rages = new ArrayList<>();
					List<String> serviceyears = new ArrayList<>();

					Calendar now = Calendar.getInstance();
					int year = now.get(Calendar.YEAR);

					List<Object[]> reportobjs = service.getDefaultReport();

					serviceyears = AgeCalculations.getServiceYears(reportobjs);
					rages = AgeCalculations.calages(reportobjs, "DOB");

					req.setAttribute("reportobjs", reportobjs);
					req.setAttribute("msg", "DOB Report of " + year);
					req.setAttribute("rages", rages);
					req.setAttribute("serviceyears", serviceyears);
					req.setAttribute("type", "DOB");
					req.setAttribute("cyear", now.get(Calendar.YEAR));
					  ses.setAttribute("SidebarActive","DobDorDoaDojReport_htm");
					return "pis/DobDorDoaDojReport";
				} catch (Exception e) {
					logger.error(new Date() +" Inside DobDorDoaDojReport.htm "+Username, e);
					e.printStackTrace();	
					return "static/Error";
				}
		}
	   
	   @RequestMapping(value="dobdordoadojreports.htm" ,method = {RequestMethod.GET,RequestMethod.POST})
		public String getReports(HttpServletRequest req , HttpSession ses)throws Exception 
	   {
		   String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside dobdordoadojreports.htm "+Username);		
		   try {
			   String reporttype = req.getParameter("dvalue");
				String mon =  req.getParameter("month");
				String ye = req.getParameter("year");
				
				int month = Integer.valueOf(mon);
				
				int year;
				if(ye=="") {
					 year = LocalDate.now().getYear();
				}else {
					 year = Integer.valueOf(ye);
					
				}
				

				List<String> rages = new ArrayList<>();
				List<String> serviceyears = new ArrayList<>();
				List<Object[]> reportobjs = new ArrayList<>();

				if("DOB".equals(reporttype)) {

					reportobjs = service.getDobReport(year, month);

					serviceyears = AgeCalculations.getServiceYears(reportobjs);
					rages = AgeCalculations.calages(reportobjs, reporttype);
					req.setAttribute( "reportobjs", reportobjs);
					if (month > 0) {
						req.setAttribute("msg", "DOB Report of " + AgeCalculations.getMonthName(month) + " " + year);
					} else {
						req.setAttribute("msg", "DOB Report of " + year);
					}
					req.setAttribute("rages", rages);
					req.setAttribute("serviceyears", serviceyears);
					req.setAttribute("type", "DOB");

				}

				if("DOA".equals(reporttype)){

					reportobjs = service.getDoaReport(year, month);
					serviceyears = AgeCalculations.getServiceYears(reportobjs);
					rages = AgeCalculations.calages(reportobjs, reporttype);
					req.setAttribute("reportobjs", reportobjs);
					if (month > 0) {
						req.setAttribute("msg", "DOA Report of " + AgeCalculations.getMonthName(month) + " " + year);
					} else {
						req.setAttribute("msg", "DOA Report of " + year);
					}
					req.setAttribute("rages", rages);
					req.setAttribute("serviceyears", serviceyears);
					req.setAttribute("type", "DOA");
				}

				if("DOR".equals(reporttype)){

					reportobjs = service.getDorReport(year, month);
					serviceyears = AgeCalculations.getServiceYears(reportobjs);
					rages = AgeCalculations.calages(reportobjs, reporttype);
					req.setAttribute("reportobjs", reportobjs);
					if (month > 0) {
						req.setAttribute("msg", "DOR Report of " + AgeCalculations.getMonthName(month) + " " + year);
					} else {
						req.setAttribute("msg", "DOR Report of " + year);
					}
					req.setAttribute("serviceyears", serviceyears);
					req.setAttribute("rages", rages);
					req.setAttribute("type", "DOR");
				}

				if("DOJ".equals(reporttype)){

					//reportobjs = service.getDojReport(year, month);
					serviceyears = AgeCalculations.getServiceYears(reportobjs);
					rages = AgeCalculations.calages(reportobjs, reporttype);
					req.setAttribute("reportobjs", reportobjs);
					if (month > 0) {
						req.setAttribute("msg", "DOJ Report of " + AgeCalculations.getMonthName(month) + " " + year);
					} else {
						req.setAttribute("msg", "DOJ Report of " + year);
					}
					req.setAttribute("rages", rages);
					req.setAttribute("serviceyears", serviceyears);
					req.setAttribute("type", "DOJ");

				}
				req.setAttribute("cyear", year);
				req.setAttribute("cmonth", AgeCalculations.getMonthName(month));
				req.setAttribute("cmonthvalue", month);
				return "pis/DobDorDoaDojReport";
			} catch (Exception e) {
				logger.error(new Date() +" Inside dobdordoadojreports.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}

		}
	   
	   
	   @RequestMapping(value = "QuarterlyStrength.htm" ,method = {RequestMethod.GET,RequestMethod.POST})
		public String  DareEmployeeList(Model model,HttpServletRequest req , HttpSession ses)throws Exception {
		   String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside QuarterlyStrength.htm "+Username);	

			try {
				ses.setAttribute("SidebarActive","EmployeeList_htm");
				String CadreIdAndCadreName= req.getParameter("CadreId");
				
				 List<Object[]> a = service.fetchCadreNameCode();
				 
				 if(CadreIdAndCadreName==null) {
					 
					 String cadreId= "0";
					List<Object[]> EmployeeList = service.EmployeeList(cadreId);
					req.setAttribute("EmployeeList", EmployeeList);
				 }else{
					 
					 List<Object[]> EmployeeList = service.EmployeeList(CadreIdAndCadreName);
					 req.setAttribute("EmployeeList", EmployeeList);
				 }
				 req.setAttribute("cadreNames", a);
				 req.setAttribute("CadreIdAndCadreName", CadreIdAndCadreName);	
			} catch (Exception e) {
				logger.error(new Date() +" Inside QuarterlyStrength.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			return "pis/EmployeeQuarterlyStrengthReport";
		}
	   
	   @RequestMapping(value = "QuarterlyStrengthReportDownload.htm", method = {RequestMethod.POST,RequestMethod.GET})
		public void QuarterlyStrengthReportDownload(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses, RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			
			logger.info(new Date() +"Inside QuarterlyStrengthReportDownload.htm "+Username);
			try {

				String CadreIdAndCadreName= req.getParameter("CadreValue");

					Object[] labdetails = masterservice.getLabDetails();
					 if(CadreIdAndCadreName==null || CadreIdAndCadreName=="") {
						 
						 String cadreId= "0";
						List<Object[]> EmployeeList = service.EmployeeList(cadreId);
						req.setAttribute("EmployeeList", EmployeeList);
					 }else{
						 
						 List<Object[]> EmployeeList = service.EmployeeList(CadreIdAndCadreName);
						 req.setAttribute("EmployeeList", EmployeeList);
					 }
				
					 req.setAttribute("labdetails", labdetails);
					String filename="QuarterlyStrengthReport";
					String path=req.getServletContext().getRealPath("/view/temp");
					req.setAttribute("path",path);
			        
			        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
					req.getRequestDispatcher("/view/pis/QuarterlyStrengthReport.jsp").forward(req, customResponse);
					String html = customResponse.getOutput();        
			        
			        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
			         
			        res.setContentType("application/pdf");
			        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
			       
			        emsfileutils.addWatermarktoPdf2(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
			        
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
					
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside QuarterlyStrengthReportDownload.htm "+Username, e);

			}
		} 

	   @RequestMapping(value = "PrintEmployeeReportDownload.htm", method = {RequestMethod.POST,RequestMethod.GET})
			public void PrintEmployeeReportDownload(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses, RedirectAttributes redir)throws Exception
			{
				String Username = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside PrintEmployeeReportDownload.htm "+Username);
				try {

					String empid= req.getParameter("Empid");

							Object[] employeedetails = service.EmployeeDetails(empid);	
							Object[] emeaddressdetails = service.EmployeeEmeAddressDetails(empid);	
							Object[] nextaddressdetails = service.EmployeeNextAddressDetails(empid);	
							Object[] peraddressdetails = service.EmployeePerAddressDetails(empid);	
							List<Object[]> resaddressdetails  = service.EmployeeResAddressDetails(empid);	
							List<Object[]> familydetails = service.getFamilydetails(empid);
						   
						   req.setAttribute("employeedetails", employeedetails);
						   req.setAttribute("emeaddressdetails", emeaddressdetails);
						   req.setAttribute("nextaddressdetails", nextaddressdetails);
						   req.setAttribute("resaddressdetails", resaddressdetails);
						   req.setAttribute("peraddressdetails", peraddressdetails);
						   req.setAttribute("familydetails", familydetails);
						   req.setAttribute("Educationlist", service.getEducationList(empid));
					       req.setAttribute("Appointmentlist", service.getAppointmentList(empid));
					       req.setAttribute("Awardslist", service.getAwardsList(empid));
					       req.setAttribute("Propertylist", service.getPropertyList(empid));
					       req.setAttribute("Publicationlist", service.getPublicationList(empid));
					       req.setAttribute("PassportVisitList", service.getPassportVisitList(empid));
					       req.setAttribute("PassportList", service.getPassportList(empid));
					
						 req.setAttribute("labdetails", masterservice.getLabDetails());
						String filename="IndividualDetails";
						String path=req.getServletContext().getRealPath("/view/temp");
						req.setAttribute("path",path);
				        
				        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
						req.getRequestDispatcher("/view/pis/PrintEmployeeReport.jsp").forward(req, customResponse);
						String html = customResponse.getOutput();        
				        
				        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
				       
				        res.setContentType("application/pdf");
				        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
				       
				        emsfileutils.addWatermarktoPdf2(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
				        
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
						
						
				}catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside PrintEmployeeReportDownload.htm "+Username, e);
				}
			} 
	   
	   
	   @RequestMapping(value ="DownloadQuarterlyStrengthExcel.htm" , method = { RequestMethod.POST,RequestMethod.GET})
	   public void DownloadExcel(HttpServletRequest req, HttpServletResponse res, HttpSession ses )throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DownloadQuarterlyStrengthExcel.htm "+Username);
		   
		   try {
			   
			  
			   String CadreIdAndCadreName= req.getParameter("CadreValue");
			
			   List<Object[]> reportlist = null;
			   if(CadreIdAndCadreName==null || CadreIdAndCadreName=="") {
					  CadreIdAndCadreName= "0";
					 reportlist = service.EmployeeList(CadreIdAndCadreName);
					req.setAttribute("EmployeeList", reportlist);
				 }else{
					  reportlist = service.EmployeeList(CadreIdAndCadreName);
					 req.setAttribute("EmployeeList", reportlist);
				 }
			   
			    XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet =  workbook.createSheet("Quarterly Strength Report");
				XSSFRow row=sheet.createRow(0);
				
				CellStyle unlockedCellStyle = workbook.createCellStyle();
				unlockedCellStyle.setLocked(true);
				
				row.createCell(0).setCellValue("Name Of Post");
				row.createCell(1).setCellValue("RE");
				row.createCell(2).setCellValue("Corporate Pool");
				row.createCell(3).setCellValue("Authorised Transaction Trans In");
				row.createCell(4).setCellValue("Authorised Transaction Trans Out");
				row.createCell(5).setCellValue("Total RE");
				row.createCell(6).setCellValue("SC");
				row.createCell(7).setCellValue("ST");
				row.createCell(8).setCellValue("OBC");
				row.createCell(9).setCellValue("EXSM");
				row.createCell(10).setCellValue("HEL PHY HND");
				row.createCell(11).setCellValue("SPRT");
				row.createCell(12).setCellValue("OTH");
				row.createCell(13).setCellValue("Total Held");
				row.createCell(14).setCellValue("VAC");
				row.createCell(15).setCellValue("Male");
				row.createCell(16).setCellValue("FeMale");

               
             
				int r=0;
				
				for(Object[] obj : reportlist){
							row=sheet.createRow(++r);
							int TotalRE=0;
	if(obj[5]!=null){row.createCell(0).setCellValue(String.valueOf(obj[5].toString()));}else {row.createCell(0).setCellValue("");}
	if(obj[8]!=null){row.createCell(1).setCellValue(String.valueOf(obj[8].toString()));}else {row.createCell(1).setCellValue("");}
	if(obj[9]!=null){row.createCell(2).setCellValue(String.valueOf(obj[9].toString()));}else {row.createCell(2).setCellValue("");}
	if(obj[10]!=null){row.createCell(3).setCellValue(String.valueOf(obj[10].toString()));}else {row.createCell(3).setCellValue("");}
	if(obj[11]!=null){row.createCell(4).setCellValue(String.valueOf(obj[11].toString()));}else {row.createCell(4).setCellValue("");}
	if(obj[8]!=null && obj[9]!=null && obj[10]!=null && obj[11]!=null) {
		
		int value =  Integer.parseInt(obj[8].toString());
        int value2 = Integer.parseInt(obj[9].toString());
        int value3 =Integer.parseInt(obj[10].toString());
        int value4 = Integer.parseInt(obj[11].toString());
         TotalRE = value+value2+value3-value4;
        row.createCell(5).setCellValue(String.valueOf(TotalRE));
	}else {
		row.createCell(5).setCellValue("");
	}
	if(obj[13]!=null){row.createCell(6).setCellValue(String.valueOf(obj[13].toString()));}else {row.createCell(6).setCellValue("");}
	if(obj[14]!=null){row.createCell(7).setCellValue(String.valueOf(obj[14].toString()));}else {row.createCell(7).setCellValue("");}
	if(obj[15]!=null){row.createCell(8).setCellValue(String.valueOf(obj[15].toString()));}else {row.createCell(8).setCellValue("");}
	if(obj[16]!=null){row.createCell(9).setCellValue(String.valueOf(obj[16].toString()));}else {row.createCell(9).setCellValue("");}
	if(obj[17]!=null){row.createCell(10).setCellValue(String.valueOf(obj[17].toString()));}else {row.createCell(10).setCellValue("");}
	if(obj[18]!=null){row.createCell(11).setCellValue(String.valueOf(obj[18].toString()));}else {row.createCell(11).setCellValue("");}
	if(obj[19]!=null){row.createCell(12).setCellValue(String.valueOf(obj[19].toString()));}else {row.createCell(12).setCellValue("");}
	if(obj[13]!=null && obj[14]!=null && obj[15]!=null&& obj[16]!=null&& obj[17]!=null && obj[18]!=null&& obj[19]!=null){
      	int data1 =  Integer.parseInt(obj[13].toString());
       	int data2 =  Integer.parseInt(obj[14].toString());
       	int data3 =  Integer.parseInt(obj[15].toString());
       	int data4 =  Integer.parseInt(obj[16].toString());
       	int data5 =  Integer.parseInt(obj[17].toString());
    	int data6 =  Integer.parseInt(obj[18].toString());
    	int data7 =  Integer.parseInt(obj[19].toString());
    	int TotalHeld = data1+data2+data3+data4+data5+data6+data7;
    	int VAC = TotalRE-TotalHeld;
		row.createCell(13).setCellValue(String.valueOf(TotalHeld));
		row.createCell(14).setCellValue(String.valueOf(TotalHeld));
		}else {
			row.createCell(13).setCellValue("");
			row.createCell(14).setCellValue("");
		}
		if(obj[20]!=null){row.createCell(15).setCellValue(String.valueOf(obj[20].toString()));}else {row.createCell(15).setCellValue("");}
		if(obj[21]!=null){row.createCell(16).setCellValue(String.valueOf(obj[21].toString()));}else {row.createCell(16).setCellValue("");}		
					
	}
				
				
				
				 res.setContentType("application/vnd.ms-excel");
		            res.setHeader("Content-Disposition", "attachment; filename=QuarterlyStrength.xls");			           
		            workbook.write(res.getOutputStream());
		            workbook.close();
		            
		            
			
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DownloadQuarterlyStrengthExcel.htm "+Username, e);
				
			}
		   
	   }
	   
	  
	   @RequestMapping(value ="DownloadEmpdetailsExcel.htm" , method = { RequestMethod.POST,RequestMethod.GET})
	   public void DownloadEmpdetailsExcel(HttpServletRequest req, HttpServletResponse res, HttpSession ses )throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DownloadEmpdetailsExcel.htm "+Username);
		   
		   try {
			   	String empid =  req.getParameter("Empid");
			   
			   	
			    XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet =  workbook.createSheet("Quarterly Strength Report");
				sheet.setDefaultColumnWidth(12);
				int rowsnum=0;
				XSSFRow row=sheet.createRow(rowsnum);
				XSSFFont font = workbook.createFont();
				font.setFontName(XSSFFont.DEFAULT_FONT_NAME);
				font.setFontHeightInPoints((short)10);
				font.setBold(true);
				
				CellStyle unlockedCellStyle = workbook.createCellStyle();
				unlockedCellStyle.setFont(font);
				unlockedCellStyle.setLocked(true);
				unlockedCellStyle.setWrapText(true);
				
				Object[] labdata = (Object[])masterservice.getLabDetails();
				
				if(labdata!=null && labdata[1]!=null && labdata[2]!=null) {
					row.createCell(1).setCellValue(labdata[2].toString()+"("+labdata[1].toString()+")");
					sheet.addMergedRegion(CellRangeAddress.valueOf("B1:E1"));
					row.getCell(1).setCellStyle(unlockedCellStyle);
				}else {
					row.createCell(1).setCellValue("");
					sheet.addMergedRegion(CellRangeAddress.valueOf("B1:E1"));
				}
				
				++rowsnum;

				Object[] employeedetails = service.EmployeeDetails(empid);	
				
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Personal Details Of :-"+employeedetails[3].toString());
				sheet.addMergedRegion(CellRangeAddress.valueOf("A3:F3"));
				row.getCell(0).setCellStyle(unlockedCellStyle);
				     // employee details
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Designation");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[22]!=null) {row.createCell(1).setCellValue(employeedetails[22].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("Department");
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[23]!=null && employeedetails[24]!=null) {row.createCell(3).setCellValue(employeedetails[23].toString()+"("+employeedetails[24].toString()+")");}else { row.createCell(3).setCellValue("--");}				
					row.createCell(4).setCellValue("DOB");
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[5]!=null) {row.createCell(5).setCellValue(employeedetails[5].toString());}else { row.createCell(6).setCellValue("--");}
					
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Employee No");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[2]!=null) {row.createCell(1).setCellValue(employeedetails[2].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("Pay Grade");
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[33]!=null) {row.createCell(3).setCellValue(employeedetails[33].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("Basic Pay");
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[34]!=null) {row.createCell(5).setCellValue(employeedetails[34].toString());}else { row.createCell(5).setCellValue("--");}
					
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Gender");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[9]!=null) {row.createCell(1).setCellValue(employeedetails[9].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("Group");
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[25]!=null && employeedetails[26]!=null) {row.createCell(3).setCellValue(employeedetails[25].toString()+"("+employeedetails[26].toString()+")");}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("PAN");
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[13]!=null) {row.createCell(5).setCellValue(employeedetails[13].toString());}else { row.createCell(5).setCellValue("--");}
					
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("DOJ");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[6]!=null) {row.createCell(1).setCellValue(DateTimeFormatUtil.SqlToRegularDate(employeedetails[6].toString()));}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("DOR");
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[8]!=null ) {row.createCell(3).setCellValue(DateTimeFormatUtil.SqlToRegularDate(employeedetails[8].toString()));}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("Mobile No");
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[30]!=null) {row.createCell(5).setCellValue(employeedetails[30].toString());}else { row.createCell(5).setCellValue("--");}
					
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Internal Email");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[16]!=null) {row.createCell(1).setCellValue(employeedetails[16].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("Extension No");
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[32]!=null) {row.createCell(3).setCellValue(employeedetails[32].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("Marital Status");
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[11]!=null) {row.createCell(5).setCellValue(employeedetails[11].toString());}else { row.createCell(5).setCellValue("--");}
					
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("SBI AccNo.");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[20]!=null) {row.createCell(1).setCellValue(employeedetails[20].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("	Blood Group"); 
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[10]!=null ) {row.createCell(3).setCellValue(employeedetails[10].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("Home Town"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(employeedetails!=null && employeedetails[27]!=null) {row.createCell(5).setCellValue(employeedetails[27].toString());}else { row.createCell(5).setCellValue("--");}
					

				++rowsnum;
	               
				//<--------------closed employee details----------------------->
				
				
				//<--------------Permanent Address----------------------->
				
				
				Object[] peraddressdetails = service.EmployeePerAddressDetails(empid);	
				
				
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Permanent Address :-");
				sheet.addMergedRegion(CellRangeAddress.valueOf("A11:F11"));
				row.getCell(0).setCellStyle(unlockedCellStyle);
				
				if(peraddressdetails!=null) {
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Per Address");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[6]!=null) {row.createCell(1).setCellValue(peraddressdetails[6].toString());}else { row.createCell(1).setCellValue("--");}
					sheet.addMergedRegion(CellRangeAddress.valueOf("B12:D12"));
					row.createCell(4).setCellValue("City"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[1]!=null) {row.createCell(5).setCellValue(peraddressdetails[1].toString());}else { row.createCell(5).setCellValue("--");}
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Mobile");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[5]!=null) {row.createCell(1).setCellValue(peraddressdetails[5].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("State"); 
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[8]!=null ) {row.createCell(3).setCellValue(peraddressdetails[8].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("From_Per_Add"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[2]!=null) {row.createCell(5).setCellValue(DateTimeFormatUtil.SqlToRegularDate(peraddressdetails[2].toString()));}else { row.createCell(5).setCellValue("--");}
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Alt_Mobile");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[0]!=null) {row.createCell(1).setCellValue(peraddressdetails[0].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("Landline"); 
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[4]!=null ) {row.createCell(3).setCellValue(peraddressdetails[4].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("Pin"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(peraddressdetails!=null && peraddressdetails[7]!=null) {row.createCell(5).setCellValue(peraddressdetails[7].toString());}else { row.createCell(5).setCellValue("--");}
					
				}else {
					    row=sheet.createRow(++rowsnum);
						row.createCell(1).setCellValue("Permanent Address Not Added!");
						row.getCell(1).setCellStyle(unlockedCellStyle);
						sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));	
				}
				
				++rowsnum;
				//<--------------Closed Permanent Address----------------------->
				
				List<Object[]> resaddressdetails  = service.EmployeeResAddressDetails(empid);	
				
				//<------------------ Residential Address ----------------------->
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Residential Address :-");
				sheet.addMergedRegion(CellRangeAddress.valueOf("A16:F16"));
				row.getCell(0).setCellStyle(unlockedCellStyle);
				
				if(resaddressdetails!=null && resaddressdetails.size()>0) {
					for(Object[] resadd : resaddressdetails) {
						
						row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Res Address");
						row.getCell(0).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[5]!=null) {row.createCell(1).setCellValue(resadd[5].toString());}else { row.createCell(1).setCellValue("--");}
						sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));
						row.createCell(4).setCellValue("City"); 
						row.getCell(4).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[1]!=null) {row.createCell(5).setCellValue(resadd[1].toString());}else { row.createCell(5).setCellValue("--");}
						
						row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Mobile");
						row.getCell(0).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[4]!=null) {row.createCell(1).setCellValue(resadd[4].toString());}else { row.createCell(1).setCellValue("--");}
						row.createCell(2).setCellValue("State"); 
						row.getCell(2).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[7]!=null ) {row.createCell(3).setCellValue(resadd[7].toString());}else { row.createCell(3).setCellValue("--");}
						row.createCell(4).setCellValue("From_Res_Add"); 
						row.getCell(4).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[2]!=null) {row.createCell(5).setCellValue(DateTimeFormatUtil.SqlToRegularDate(peraddressdetails[2].toString()));}else { row.createCell(5).setCellValue("--");}
						
						row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Alt_Mobile");
						row.getCell(0).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[0]!=null) {row.createCell(1).setCellValue(resadd[0].toString());}else { row.createCell(1).setCellValue("--");}
						row.createCell(2).setCellValue("Landline"); 
						row.getCell(2).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[3]!=null ) {row.createCell(3).setCellValue(resadd[3].toString());}else { row.createCell(3).setCellValue("--");}
						row.createCell(4).setCellValue("Pin"); 
						row.getCell(4).setCellStyle(unlockedCellStyle);
						if(resaddressdetails!=null && resadd[6]!=null) {row.createCell(5).setCellValue(resadd[6].toString());}else { row.createCell(5).setCellValue("--");}
						
						row=sheet.createRow(++rowsnum);
						//sheet.addMergedRegion(CellRangeAddress.valueOf("A11:F11"));
					}
				}else {
					    row=sheet.createRow(++rowsnum);
						row.createCell(1).setCellValue("Residential  Address Not Added!");
						row.getCell(1).setCellStyle(unlockedCellStyle);
						sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));	
				}
				++rowsnum;

				//<------------------ Residential Address ----------------------->
				
				Object[] nextaddressdetails = service.EmployeeNextAddressDetails(empid);
				
				
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Next kin Address :-");
				sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
				
				row.getCell(0).setCellStyle(unlockedCellStyle);
				
				if(nextaddressdetails!=null) {
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Next kin Add");
				row.getCell(0).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[6]!=null) {row.createCell(1).setCellValue(nextaddressdetails[6].toString());}else { row.createCell(1).setCellValue("--");}
				
				sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));
				row.createCell(4).setCellValue("City"); 
				row.getCell(4).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[1]!=null) {row.createCell(5).setCellValue(nextaddressdetails[1].toString());}else { row.createCell(5).setCellValue("--");}
				
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Mobile");
				row.getCell(0).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[5]!=null) {row.createCell(1).setCellValue(nextaddressdetails[5].toString());}else { row.createCell(1).setCellValue("--");}
				row.createCell(2).setCellValue("State"); 
				row.getCell(2).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[8]!=null ) {row.createCell(3).setCellValue(nextaddressdetails[8].toString());}else { row.createCell(3).setCellValue("--");}
				row.createCell(4).setCellValue("From_Kin_Add"); 
				row.getCell(4).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[2]!=null) {row.createCell(5).setCellValue(DateTimeFormatUtil.SqlToRegularDate(nextaddressdetails[2].toString()));}else { row.createCell(5).setCellValue("--");}
				
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Alt_Mobile");
				row.getCell(0).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[0]!=null) {row.createCell(1).setCellValue(nextaddressdetails[0].toString());}else { row.createCell(1).setCellValue("--");}
				row.createCell(2).setCellValue("Landline"); 
				row.getCell(2).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[4]!=null ) {row.createCell(3).setCellValue(nextaddressdetails[4].toString());}else { row.createCell(3).setCellValue("--");}
				row.createCell(4).setCellValue("Pin"); 
				row.getCell(4).setCellStyle(unlockedCellStyle);
				if(nextaddressdetails!=null && nextaddressdetails[7]!=null) {row.createCell(5).setCellValue(nextaddressdetails[7].toString());}else { row.createCell(5).setCellValue("--");}
				
		   }else {
			   row=sheet.createRow(++rowsnum);
				row.createCell(1).setCellValue("Next kin  Address Not Added!");
				row.getCell(1).setCellStyle(unlockedCellStyle);
				sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));	
		   }
				++rowsnum;
				//<------------------closed Residential Address ----------------------->
			
				Object[] emeaddressdetails = service.EmployeeEmeAddressDetails(empid);
				
				//<------------------ Emergency Address ----------------------->
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Emergency Address :-");
				sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
				
				row.getCell(0).setCellStyle(unlockedCellStyle);
				
				if(emeaddressdetails!=null) {
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Emergency Add");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[6]!=null) {row.createCell(1).setCellValue(emeaddressdetails[6].toString());}else { row.createCell(1).setCellValue("--");}
					
					sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));
					row.createCell(4).setCellValue("City"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[1]!=null) {row.createCell(5).setCellValue(emeaddressdetails[1].toString());}else { row.createCell(5).setCellValue("--");}
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Mobile");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[5]!=null) {row.createCell(1).setCellValue(emeaddressdetails[5].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("State"); 
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[8]!=null ) {row.createCell(3).setCellValue(emeaddressdetails[8].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("From_Emec_Add"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[2]!=null) {row.createCell(5).setCellValue(DateTimeFormatUtil.SqlToRegularDate(emeaddressdetails[2].toString()));}else { row.createCell(5).setCellValue("--");}
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Alt_Mobile");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[0]!=null) {row.createCell(1).setCellValue(emeaddressdetails[0].toString());}else { row.createCell(1).setCellValue("--");}
					row.createCell(2).setCellValue("Landline"); 
					row.getCell(2).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[4]!=null ) {row.createCell(3).setCellValue(emeaddressdetails[4].toString());}else { row.createCell(3).setCellValue("--");}
					row.createCell(4).setCellValue("Pin"); 
					row.getCell(4).setCellStyle(unlockedCellStyle);
					if(emeaddressdetails!=null && emeaddressdetails[7]!=null) {row.createCell(5).setCellValue(emeaddressdetails[7].toString());}else { row.createCell(5).setCellValue("--");}
					
				}else {
					row=sheet.createRow(++rowsnum);
					row.createCell(1).setCellValue("Emergency Address Not Added!");
					row.getCell(1).setCellStyle(unlockedCellStyle);
					sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));	
				}
				
				
				++rowsnum;
				//<------------------Closed Emergency Address ----------------------->
				
				 List<Object[]> appointmentlist= (List<Object[]>) service.getAppointmentList(empid);
				//<------------------ Appointment Address ----------------------->
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Appointment Detail List :-");
				sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
				
				row=sheet.createRow(++rowsnum);
				row.createCell(0).setCellValue("Designation");
				row.getCell(0).setCellStyle(unlockedCellStyle);
				row.createCell(1).setCellValue("Org/Lab");
				row.getCell(1).setCellStyle(unlockedCellStyle);
				row.createCell(2).setCellValue("DRDO/Others");
				row.getCell(2).setCellStyle(unlockedCellStyle);
				row.createCell(3).setCellValue("From Date");
				row.getCell(3).setCellStyle(unlockedCellStyle);
				row.createCell(4).setCellValue("To Date");
				row.getCell(4).setCellStyle(unlockedCellStyle);
				
				  
				   
				   if(appointmentlist!=null &&  appointmentlist.size()>0) {
					   for(Object[] list:appointmentlist) {
							
							row=sheet.createRow(++rowsnum);
							if(list!=null && list[7]!=null) {row.createCell(0).setCellValue(list[7].toString());}else { row.createCell(0).setCellValue("--");}
							if(list!=null && list[2]!=null) {row.createCell(1).setCellValue(list[2].toString());}else { row.createCell(1).setCellValue("--");}
							if(list!=null && list[5]!=null) {row.createCell(2).setCellValue(list[5].toString());}else { row.createCell(2).setCellValue("--");}
							if(list!=null && list[8]!=null) {row.createCell(3).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[8].toString()));}else { row.createCell(3).setCellValue("--");}
							if(list!=null && list[9]!=null) {row.createCell(4).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[9].toString()));}else { row.createCell(4).setCellValue("--");}
						}
				   }else {
					   row=sheet.createRow(++rowsnum);
						row.createCell(1).setCellValue("Awards Details Not Added!");
						row.getCell(1).setCellStyle(unlockedCellStyle);
						sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));	
				   }
				++rowsnum;
				
				//<------------------Closed Appointment Address ----------------------->
				
				
				//<------------------ Award Address ----------------------->
				 List<Object[]> awardslist= (List<Object[]>) service.getAwardsList(empid);
				 
				 
				 row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Award Detail List :-");
					sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
					
					row=sheet.createRow(++rowsnum);
					row.createCell(0).setCellValue("Award Name");
					row.getCell(0).setCellStyle(unlockedCellStyle);
					row.createCell(1).setCellValue("Award By");
					row.getCell(1).setCellStyle(unlockedCellStyle);
					row.createCell(2).setCellValue("Details");
					row.getCell(2).setCellStyle(unlockedCellStyle);
					row.createCell(3).setCellValue("Award Date");
					row.getCell(3).setCellStyle(unlockedCellStyle);
					row.createCell(4).setCellValue("Certificate");
					row.getCell(4).setCellStyle(unlockedCellStyle);
					row.createCell(5).setCellValue("Citation");
					row.getCell(5).setCellStyle(unlockedCellStyle);
					row.createCell(6).setCellValue("Medallian");
					row.getCell(6).setCellStyle(unlockedCellStyle);
					row.createCell(7).setCellValue("Award Category");
					row.getCell(7).setCellStyle(unlockedCellStyle);
					row.createCell(8).setCellValue("Cash Ammount");
					row.getCell(8).setCellStyle(unlockedCellStyle);
				 
				 if(awardslist!=null &&  awardslist.size()>0) {
					 for(Object[] list:awardslist) {
							
							row=sheet.createRow(++rowsnum);
							if(list!=null && list[2]!=null) {row.createCell(0).setCellValue(list[2].toString());}else { row.createCell(0).setCellValue("--");}
							if(list!=null && list[3]!=null) {row.createCell(1).setCellValue(list[3].toString());}else { row.createCell(1).setCellValue("--");}
							if(list!=null && list[4]!=null) {row.createCell(2).setCellValue(list[4].toString());}else { row.createCell(2).setCellValue("--");}
							if(list!=null && list[5]!=null) {row.createCell(3).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[5].toString()));}else { row.createCell(3).setCellValue("--");}
							if(list!=null && list[7]!=null) {row.createCell(4).setCellValue(list[7].toString());}else { row.createCell(4).setCellValue("--");}
							if(list!=null && list[9]!=null) {row.createCell(5).setCellValue(list[9].toString());}else { row.createCell(5).setCellValue("--");}
							if(list!=null && list[11]!=null) {row.createCell(6).setCellValue(list[11].toString());}else { row.createCell(6).setCellValue("--");}
							if(list!=null && list[12]!=null) {row.createCell(7).setCellValue(list[12].toString());}else { row.createCell(7).setCellValue("--");}
							if(list!=null && list[15]!=null) {row.createCell(8).setCellValue(list[15].toString());}else { row.createCell(8).setCellValue("--");}
						
						}
				 }else {
					    row=sheet.createRow(++rowsnum);
						row.createCell(2).setCellValue("Awards Details Not Added!");
						row.getCell(2).setCellStyle(unlockedCellStyle);
						sheet.addMergedRegion(CellRangeAddress.valueOf("C"+(rowsnum+1)+":F"+(rowsnum+1)));
						
				 }

					++rowsnum;
					//<------------------Closed Award Address ----------------------->
					
					 List<Object[]> propertylist= (List<Object[]>) service.getPropertyList(empid);
					 
					//<------------------ Property Address ----------------------->
					 row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Property Detail List :-");
						sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
						
					
						row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Movable");
						row.getCell(0).setCellStyle(unlockedCellStyle);
						row.createCell(1).setCellValue("value");
						row.getCell(1).setCellStyle(unlockedCellStyle);
						row.createCell(2).setCellValue("Details");
						row.getCell(2).setCellStyle(unlockedCellStyle);
						row.createCell(3).setCellValue("DOP");
						row.getCell(3).setCellStyle(unlockedCellStyle);
						row.createCell(4).setCellValue("Acquired Type");
						row.getCell(4).setCellStyle(unlockedCellStyle);
						row.createCell(5).setCellValue("Noting On");
						row.getCell(5).setCellStyle(unlockedCellStyle);
						row.createCell(6).setCellValue("Remarks");
						row.getCell(6).setCellStyle(unlockedCellStyle);

						if(propertylist!=null && propertylist.size()>0) {
							for(Object[] list:propertylist) {
								
								row=sheet.createRow(++rowsnum);
								if(list!=null && list[2]!=null) {row.createCell(0).setCellValue(list[2].toString());}else { row.createCell(0).setCellValue("--");}
								if(list!=null && list[3]!=null) {row.createCell(1).setCellValue(list[3].toString());}else { row.createCell(1).setCellValue("--");}
								if(list!=null && list[4]!=null) {row.createCell(2).setCellValue(list[4].toString());}else { row.createCell(2).setCellValue("--");}
								if(list!=null && list[5]!=null) {row.createCell(3).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[5].toString()));}else { row.createCell(3).setCellValue("--");}
								if(list!=null && list[6]!=null) {row.createCell(4).setCellValue(list[6].toString());}else { row.createCell(4).setCellValue("--");}
								if(list!=null && list[7]!=null) {row.createCell(5).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[7].toString()));}else { row.createCell(5).setCellValue("--");}
								if(list!=null && list[8]!=null) {row.createCell(6).setCellValue(list[8].toString());}else { row.createCell(6).setCellValue("--");}
								
							}
						}else {
							row=sheet.createRow(++rowsnum);
							row.createCell(2).setCellValue("Property Details Not Added!");
							row.getCell(2).setCellStyle(unlockedCellStyle);
							sheet.addMergedRegion(CellRangeAddress.valueOf("C"+(rowsnum+1)+":E"+(rowsnum+1)));
							
						}

						++rowsnum;
						//<------------------Closed Property Address ----------------------->

						 List<Object[]> publicationlist= (List<Object[]>) service.getPublicationList(empid);
						//<------------------ Publication Address ----------------------->
						 
						 row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Publication Detail List :-");
						sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
						
						row=sheet.createRow(++rowsnum);
						row.createCell(0).setCellValue("Publication Type");
						row.getCell(0).setCellStyle(unlockedCellStyle);
						row.createCell(1).setCellValue("Authors");
						row.getCell(1).setCellStyle(unlockedCellStyle);
						row.createCell(2).setCellValue("Discipline");
						row.getCell(2).setCellStyle(unlockedCellStyle);
						row.createCell(3).setCellValue("Title");
						row.getCell(3).setCellStyle(unlockedCellStyle);
						row.createCell(4).setCellValue("Publication Name");
						row.getCell(4).setCellStyle(unlockedCellStyle);
						row.createCell(5).setCellValue("Publication Date");
						row.getCell(5).setCellStyle(unlockedCellStyle);
						row.createCell(6).setCellValue("Patent No");
						row.getCell(6).setCellStyle(unlockedCellStyle);
						
						if(publicationlist!=null && publicationlist.size()>0){
							for(Object[] list:publicationlist) {
								
								row=sheet.createRow(++rowsnum);
								if(list!=null && list[0]!=null) {row.createCell(0).setCellValue(list[0].toString());}else { row.createCell(0).setCellValue("--");}
								if(list!=null && list[1]!=null) {row.createCell(1).setCellValue(list[1].toString());}else { row.createCell(1).setCellValue("--");}
								if(list!=null && list[2]!=null) {row.createCell(2).setCellValue(list[2].toString());}else { row.createCell(2).setCellValue("--");}
								if(list!=null && list[3]!=null) {row.createCell(3).setCellValue(list[3].toString());}else { row.createCell(3).setCellValue("--");}
								if(list!=null && list[8]!=null) {row.createCell(4).setCellValue(list[8].toString());}else { row.createCell(4).setCellValue("--");}
								if(list!=null && list[7]!=null) {row.createCell(5).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[7].toString()));}else { row.createCell(5).setCellValue("--");}
								if(list!=null && list[9]!=null) {row.createCell(6).setCellValue(list[9].toString());}else { row.createCell(6).setCellValue("--");}
								
							}
						}else {
							row=sheet.createRow(++rowsnum);
							row.createCell(2).setCellValue("Publication Details Not Added!");
							row.getCell(2).setCellStyle(unlockedCellStyle);
							sheet.addMergedRegion(CellRangeAddress.valueOf("C"+(rowsnum+1)+":E"+(rowsnum+1)));
							
						}

						++rowsnum;
						//<------------------Closed Publication Address ----------------------->
						
					    List<Object[]> educationlist= (List<Object[]>) service.getEducationList(empid);
					  //<------------------ Qualification Address ----------------------->
						 
						 row=sheet.createRow(++rowsnum);
							row.createCell(0).setCellValue("Qualification Detail List :-");
							sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
							
							row=sheet.createRow(++rowsnum);
							row.createCell(0).setCellValue("Quali Title");
							row.getCell(0).setCellStyle(unlockedCellStyle);
							row.createCell(1).setCellValue("Disci Title");
							row.getCell(1).setCellStyle(unlockedCellStyle);
							row.createCell(2).setCellValue("University");
							row.getCell(2).setCellStyle(unlockedCellStyle);
							row.createCell(3).setCellValue("Year Of Passing");
							row.getCell(3).setCellStyle(unlockedCellStyle);
							row.createCell(4).setCellValue("CGPA");
							row.getCell(4).setCellStyle(unlockedCellStyle);
							row.createCell(5).setCellValue("Specialization");
							row.getCell(5).setCellStyle(unlockedCellStyle);
							row.createCell(6).setCellValue("Sponsored");
							row.getCell(6).setCellStyle(unlockedCellStyle);
							row.createCell(7).setCellValue("Acq_Bef_Aft");
							row.getCell(7).setCellStyle(unlockedCellStyle);
							
						 if(educationlist!=null && educationlist.size()>0) {
							 for(Object[] list:educationlist) {
									
									row=sheet.createRow(++rowsnum);
									if(list!=null && list[2]!=null) {row.createCell(0).setCellValue(list[2].toString());}else { row.createCell(0).setCellValue("--");}
									if(list!=null && list[3]!=null) {row.createCell(1).setCellValue(list[3].toString());}else { row.createCell(1).setCellValue("--");}
									if(list!=null && list[4]!=null) {row.createCell(2).setCellValue(list[4].toString());}else { row.createCell(2).setCellValue("--");}
									if(list!=null && list[5]!=null) {row.createCell(3).setCellValue(list[5].toString());}else { row.createCell(3).setCellValue("--");}
									if(list!=null && list[6]!=null) {row.createCell(4).setCellValue(list[6].toString());}else { row.createCell(4).setCellValue("--");}
									if(list!=null && list[8]!=null) {row.createCell(5).setCellValue(list[8].toString());}else { row.createCell(5).setCellValue("--");}
									if(list!=null && list[7]!=null) {row.createCell(6).setCellValue(list[7].toString());}else { row.createCell(6).setCellValue("--");}
									if(list!=null && list[9]!=null) {row.createCell(7).setCellValue(list[9].toString());}else { row.createCell(7).setCellValue("--");}
									
								}
						 }else {
							    row=sheet.createRow(++rowsnum);
								row.createCell(2).setCellValue("Qualification Details Not Added!");
								row.getCell(2).setCellStyle(unlockedCellStyle);
								sheet.addMergedRegion(CellRangeAddress.valueOf("C"+(rowsnum+1)+":F"+(rowsnum+1)));
								
						 }
							++rowsnum;
			//<------------------Closed Qualification Address ----------------------->
							
							    Object[] passportlist= (Object[]) service.getPassportList(empid);
			 //<------------------ Passport Address ----------------------->
							    
								 row=sheet.createRow(++rowsnum);
									row.createCell(0).setCellValue("Passport Detail List :-");
									sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
									
									row=sheet.createRow(++rowsnum);
									row.createCell(0).setCellValue("Passport Type");
									row.getCell(0).setCellStyle(unlockedCellStyle);
									row.createCell(1).setCellValue("Disci Title");
									row.getCell(1).setCellStyle(unlockedCellStyle);
									row.createCell(2).setCellValue("University");
									row.getCell(2).setCellStyle(unlockedCellStyle);
									row.createCell(3).setCellValue("Year Of Passing");
									row.getCell(3).setCellStyle(unlockedCellStyle);
									
									if(passportlist!=null) {
										row=sheet.createRow(++rowsnum);
										if(passportlist!=null && passportlist[1]!=null) {row.createCell(0).setCellValue(passportlist[1].toString());}else { row.createCell(0).setCellValue("--");}
										if(passportlist!=null && passportlist[2]!=null) {row.createCell(1).setCellValue(DateTimeFormatUtil.SqlToRegularDate(passportlist[2].toString()));}else { row.createCell(1).setCellValue("--");}
										if(passportlist!=null && passportlist[3]!=null) {row.createCell(2).setCellValue(DateTimeFormatUtil.SqlToRegularDate(passportlist[3].toString()));}else { row.createCell(2).setCellValue("--");}
										if(passportlist!=null && passportlist[4]!=null) {row.createCell(3).setCellValue(passportlist[4].toString());}else { row.createCell(3).setCellValue("--");}
										
									}else {
										row=sheet.createRow(++rowsnum);
										row.createCell(1).setCellValue("Passport Details Not Added!");
										row.getCell(1).setCellStyle(unlockedCellStyle);
										sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));
										
									}

									++rowsnum;
				//<------------------Closed Passport Address ----------------------->
							
			List<Object[]> passportvisitlist= (List<Object[]>) service.getPassportVisitList(empid);
			//<------------------ Foreign Visit Address ----------------------->
			
			row=sheet.createRow(++rowsnum);
			row.createCell(0).setCellValue("Foreign Visit Detail List :-");
			sheet.addMergedRegion(CellRangeAddress.valueOf("A"+(rowsnum+1)+":F"+(rowsnum+1)));
			
			row=sheet.createRow(++rowsnum);
			row.createCell(0).setCellValue("Country Name");
			row.getCell(0).setCellStyle(unlockedCellStyle);
			row.createCell(1).setCellValue("Visit From");
			row.getCell(1).setCellStyle(unlockedCellStyle);
			row.createCell(2).setCellValue("Visit To");
			row.getCell(2).setCellStyle(unlockedCellStyle);
			row.createCell(3).setCellValue("Purpose");
			row.getCell(3).setCellStyle(unlockedCellStyle);
			row.createCell(4).setCellValue("NOC Letter No");
			row.getCell(4).setCellStyle(unlockedCellStyle);
			
			if(passportvisitlist!=null && passportvisitlist.size()>0) {
				for(Object[] list:passportvisitlist) {
					row=sheet.createRow(++rowsnum);
					if(list!=null && list[2]!=null) {row.createCell(0).setCellValue(list[2].toString());}else { row.createCell(0).setCellValue("--");}
					if(list!=null && list[6]!=null) {row.createCell(1).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[6].toString()));}else { row.createCell(1).setCellValue("--");}
					if(list!=null && list[7]!=null) {row.createCell(2).setCellValue(DateTimeFormatUtil.SqlToRegularDate(list[7].toString()));}else { row.createCell(2).setCellValue("--");}
					if(list!=null && list[5]!=null) {row.createCell(3).setCellValue(list[5].toString());}else { row.createCell(3).setCellValue("--");}
					if(list!=null && list[3]!=null) {row.createCell(4).setCellValue(list[3].toString());}else { row.createCell(4).setCellValue("--");}
					
				}
			}else {
				row=sheet.createRow(++rowsnum);
				row.createCell(1).setCellValue("Foreign Visit Details Not Added!");
				row.getCell(1).setCellStyle(unlockedCellStyle);
				sheet.addMergedRegion(CellRangeAddress.valueOf("B"+(rowsnum+1)+":D"+(rowsnum+1)));
			}
			//<------------------Closed Foreign Visit Address ----------------------->
				
				 res.setContentType("application/vnd.ms-excel");
		            res.setHeader("Content-Disposition", "attachment; filename=QuarterlyStrength.xls");			           
		            workbook.write(res.getOutputStream());
		            workbook.close();
		            
		            
			
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DownloadEmpdetailsExcel.htm "+Username, e);
				
			}
		   
	   }
	   
	   @RequestMapping(value ="CheckSenirity.htm" , method = RequestMethod.GET)
	   public @ResponseBody String CheckSenirity(HttpSession ses , HttpServletRequest req , HttpServletResponse res)throws Exception
	   {
		   String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside CheckSenirity.htm " + Username);
			

			int result = 0;
			try {
				result = service.GetMaxSeniorityNo();
			} catch (Exception e) {
				logger.error(new Date() + "Inside CheckSenirity.htm " + Username,e);
				e.printStackTrace();
			}
			Gson json = new Gson();
			return json.toJson(result);
		   
	   }
	   
	   
	   @RequestMapping(value = "PropertyReport.htm", method = { RequestMethod.POST, RequestMethod.GET })
		public String propertyReport(Model model, HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir) throws Exception 
		{
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			logger.info(new Date() + "Inside PropertyReport.htm " + Username);
			try {
				ses.setAttribute("SidebarActive", "PropertyReport_htm");
				String action = req.getParameter("action");
				if ("ADD".equalsIgnoreCase(action)) {
					return "pis/PropertyDetailsAdd";
				} else if ("EDIT".equalsIgnoreCase(action)) {

					String PropertyId = req.getParameter("PropertyId");
					List<Object[]> list = service.editPropertyDetails(PropertyId);
					req.setAttribute("list", list);

					return "pis/PropertyDetailsEdit";
				} else if ("DELETE".equalsIgnoreCase(action)) {
					String PropertyId = req.getParameter("PropertyId");
					int result = service.deletePropertyDetails(PropertyId, Username);
					if (result != 0) {
						redir.addAttribute("result", "Property Details Deleted Successfully");
					} else
						redir.addAttribute("resultFail", "property Details Not Deleted");
					return "redirect:/PropertyReport.htm";
				} else {

					String year = req.getParameter("PropertyYear");
					if (year == null) {
						year = String.valueOf(LocalDate.now().minusYears(1).getYear());
					}
					List<Object[]> list = service.getPropertiesYearwise(Integer.parseInt(year), EmpId);
					req.setAttribute("list", list);
					req.setAttribute("year", year);
					String result = req.getParameter("result");
					String resultFail = req.getParameter("resultFail");

					req.setAttribute("result", result);
					req.setAttribute("resultFail", resultFail);
					return "pis/PropertyDetailsReportList";
				}
			} catch (Exception e) {
				logger.error(new Date() + "Inside PropertyReport.htm " + Username, e);
				e.printStackTrace();
			}

			return "pis/PropertyDetailsReportList";
		}

		@RequestMapping(value = "PrintPropertyReport.htm", method = { RequestMethod.POST, RequestMethod.GET })
		public void PrintReport(Model model, HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir) throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			logger.info(new Date() + "Inside PrintPropertyReport.htm " + UserId);

			try {
				int year = Integer.parseInt(req.getParameter("year"));
				List<Object[]> lablist = service.getLabDetails();
				req.setAttribute("lablist", lablist);
				List<Object[]> emplist = service.getEmpDetails(EmpId);
				req.setAttribute("emplist", emplist);
				List<Object[]> list = service.getPropertiesYearwise(year, EmpId);
				req.setAttribute("list", list);
				req.setAttribute("year", year);
				String filename = "PropertiesDetailsPrint";
				String path = req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path", path);

				CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pis/PropertiesDetailsPrint.jsp").forward(req, customResponse);
				String html = customResponse.getOutput();

				HtmlConverter.convertToPdf(html, new FileOutputStream(path + File.separator + filename + ".pdf"));

				res.setContentType("application/pdf");
				res.setHeader("Content-disposition", "inline;filename=" + filename + ".pdf");
				File f = new File(path + File.separator + filename + ".pdf");
				FileInputStream fis = new FileInputStream(f);
				DataOutputStream os = new DataOutputStream(res.getOutputStream());
				res.setHeader("Content-Length", String.valueOf(f.length()));
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) >= 0) {
					os.write(buffer, 0, len);
				}
				os.close();
				fis.close();

				Path pathOfFile = Paths.get(path + File.separator + filename + ".pdf");
				Files.delete(pathOfFile);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside PrintPropertyReport.htm " + UserId, e);
			}

		}

		@RequestMapping(value = "AddPropertyReoprt.htm", method = { RequestMethod.POST, RequestMethod.GET })
		public String AddPropertyDetailsReport(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			Long EmpId = ((Long) ses.getAttribute("EmpId"));
			logger.info(new Date() + "Inside AddPropertyReoprt.htm " + UserId);
			try {

				String description = req.getParameter("description");
				String address = req.getParameter("Address");
				Double propertyValue = Double.parseDouble(req.getParameter("propertyvalue"));
				String PartnerInfo = req.getParameter("PartnernameAndRelationship");
				String modeOfproperty = req.getParameter("propertymode");
				Double annualIncome = Double.parseDouble(req.getParameter("AnnualIncome"));
				String remarks = req.getParameter("remarks");

				PropertyDetails details = new PropertyDetails();
				details.setEmpId(EmpId);
				details.setDescription(description);
				details.setAddress(address);
				details.setPropertyValue(propertyValue);
				details.setPartnerInfo(PartnerInfo);
				details.setModeOfProperty(modeOfproperty);
				details.setAnnualIncome(annualIncome);
				details.setRemarks(remarks);
				details.setPropertyYear(LocalDate.now().minusYears(1).getYear());
				details.setCreatedBy(UserId);
				details.setCreatedDate(sdtf.format(new Date()));
				details.setIsActive(1);
				Long result = service.AddPropertyDetails(details);
				if (result != 0) {
					redir.addAttribute("result", "Property Details Added Successfully");
				} else
					redir.addAttribute("resultFail", "property Details Not Added");

			} catch (Exception e) {
				logger.error(new Date() + " Inside AddPropertyReoprt.htm " + UserId, e);
				e.printStackTrace();
			}
			return "redirect:/PropertyReport.htm";
		}

		@RequestMapping(value = "UpdatePropertyDetails.htm", method = { RequestMethod.GET, RequestMethod.POST })
		public String updatePropertyDetails(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
		{

			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside UpdatePropertyDetails.htm " + UserId);
			try {
				String PropertyId = req.getParameter("PropertyId");
				String description = req.getParameter("description");
				String address = req.getParameter("Address");
				String PropertyValue = req.getParameter("propertyvalue");
				Double propertyValue = Double.parseDouble(PropertyValue);
				String PartnerInfo = req.getParameter("PartnernameAndRelationship");
				String modeOfproperty = req.getParameter("propertymode");
				String AnnualIncome = req.getParameter("AnnualIncome");
				Double annualIncome = Double.parseDouble(AnnualIncome);
				String remarks = req.getParameter("remarks");

				PropertyDetails details = new PropertyDetails();

				details.setDescription(description);
				details.setAddress(address);
				details.setPropertyValue(propertyValue);
				details.setPartnerInfo(PartnerInfo);
				details.setModeOfProperty(modeOfproperty);
				details.setAnnualIncome(annualIncome);
				details.setRemarks(remarks);
				details.setModifiedBy(UserId);
				details.setModifiedDate(sdtf.format(new Date()));
				Long result = service.updatePropertyDetails(details, PropertyId);
				if (result != 0) {
					redir.addAttribute("result", "Property Details Updated Successfully");
				} else {
					redir.addAttribute("resultFail", "property Details Not Updated");
				}

			} catch (Exception e) {
				logger.error(new Date() + " Inside UpdatePropertyDetails.htm " + UserId, e);
				e.printStackTrace();
			}
			return "redirect:/PropertyReport.htm";

		}
		
		
		
		@RequestMapping(value ="DepDeclareFormView.htm" )
		public String depDeclareFormView(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepDeclareFormView.htm "+Username);
			try {
				String formid = req.getParameter("formid");
				String isapproval = req.getParameter("isApprooval");
				if(formid==null)  
				{
					Map md=model.asMap();
					formid=(String)md.get("formid");
					isapproval ="N";
				}
				
				if(formid==null) 
				{
					return "static/Error";
				}
				
				String empid; 
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
				req.setAttribute("relationtypes" , service.familyRelationList() );
				
				req.setAttribute("FamilymemDropdown" , service.EmpFamMembersNotMedDep(empid,formid));
				
				req.setAttribute("isApprooval" , isapproval );
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				req.setAttribute("CHSSEligibleFamList", service.familyDetailsList(empid));
				
				req.setAttribute("FwdMemberDetails",service.GetFormMembersList(formid));
				req.setAttribute("ExcMemberDetails",service.GetExcFormMembersList(formid));
				return "pis/DependentFormDecView";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepDeclareFormView.htm "+Username, e);
				return "static/Error";
			}
			
		}
		
		@RequestMapping(value ="DepDeclareFormDownload.htm" )
		public void DepDeclareFormDownload(Model model,HttpServletRequest req, HttpServletResponse res, HttpSession ses)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside DepDeclareFormDownload.htm "+Username);
			try {
				String formid = req.getParameter("formid");
				PISEmpFamilyDeclaration declare = service.getEmpFamilyDeclaration(formid);
				
				Object[] formdata = service.GetFamFormData(formid);
				if(declare==null) 
				{
					if( formdata[2].toString().equalsIgnoreCase("I"))
					{
						service.DepIncFormFreeze(req, res, formid);
						declare = service.getEmpFamilyDeclaration(formid);
					}
					else if( formdata[2].toString().equalsIgnoreCase("E"))
					{
						service.DepExcFormFreeze(req, res, formid);
						declare = service.getEmpFamilyDeclaration(formid);
					}
					else if(formdata[2].toString().equalsIgnoreCase("D"))
					{
						service.DepDeclareFormFreeze(req, res, formid);
						declare = service.getEmpFamilyDeclaration(formid);
					}
				}
				
				res.setContentType("application/pdf");
				res.setHeader("Content-disposition", "inline;filename= DependantsDeclaration.pdf");
				File f = new File(uploadpath + File.separator + declare.getFilePath());
				FileInputStream fis = new FileInputStream(f);
				DataOutputStream os = new DataOutputStream(res.getOutputStream());
				res.setHeader("Content-Length", String.valueOf(f.length()));
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) >= 0) {
					os.write(buffer, 0, len);
				}
				os.close();
				fis.close();

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside DepDeclareFormDownload.htm "+Username, e);
			}
			
		}
		
		
		
}

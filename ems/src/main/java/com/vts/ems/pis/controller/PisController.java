package com.vts.ems.pis.controller;

import java.io.File;
import java.text.SimpleDateFormat;
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
			System.out.println(empid);
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
			String dob = req.getParameter("dob");
			String doa = req.getParameter("doa");
			String doj = req.getParameter("doj");
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
			emp.setDOB(sdf.parse(dob));
			emp.setDOA(sdf.parse(doa));
			emp.setDOJL(sdf.parse(doj));
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
			
			
			
			service.EmployeeAddSubmit(emp);
			
			
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
	
	@RequestMapping(value="EmployeeEditSubmit.htm")
	public String EmployeeEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside EmployeeEditSubmit.htm "+Username);		
		try {
			String salutation = req.getParameter("salutation");
			String empname = req.getParameter("empname");
			String Designationid = req.getParameter("Designationid");
			String caderid = req.getParameter("caderid");
			String catcode = req.getParameter("catcode");
			String dob = req.getParameter("dob");
			String doa = req.getParameter("doa");
			String doj = req.getParameter("doj");
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
			String EmpId = req.getParameter("EmpId");
			

			Employee emp= new Employee();
			emp.setEmpName(empname.trim());
			emp.setDesignationId(Integer.parseInt(Designationid));
			emp.setTitle(salutation);
			emp.setDOB(sdf.parse(dob));
			emp.setDOA(sdf.parse(doa));
			emp.setDOJL(sdf.parse(doj));
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
			System.out.println(uid);
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
			emp.setModifiedBy(Username);		
			emp.setInternalNumber(internalNo);
			emp.setSubCategary(subcategory);
			//emp.setEmpStatusDate(rdf.parse(EmpStatusDate));
			emp.setEmpId(Integer.parseInt(EmpId));
			
			service.EmployeeEditSubmit(emp);
			
			
			return "redirect:/PisAdminEmpList.htm";
		}catch (Exception e) {
			logger.error(new Date() +" Inside EmployeeEditSubmit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "/requestbypunchajax", method = RequestMethod.GET)
	public @ResponseBody String PunchRequestData(HttpServletRequest req, HttpServletResponse response, HttpSession ses)
			throws Exception {
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

		@RequestMapping(value="PisImageUpload.htm",headers=("content-type=multipart/*") , method = RequestMethod.POST)
		public String PisImageUpload(HttpSession ses,RedirectAttributes redir ,HttpServletRequest req, HttpServletResponse res,@RequestParam("photo1") MultipartFile file)throws Exception{
			
			int value=0;
			String EmpId = (String)req.getParameter("employeeid");
		try { 
			System.out.println("EmpId    :"+EmpId);
			String imagename= service.PhotoPath(EmpId);
			File f = new File(uploadpath+"\\"+imagename);
			if(f.exists()) {
				f.delete();
			}
		 value= service.saveEmpImage(file,EmpId,uploadpath);
		 Object[] employeedetails = service.EmployeeDetails(EmpId);	
         String basevalue=service.getimage(EmpId);
			req.setAttribute("empid", EmpId);
			req.setAttribute("employeedetails", employeedetails);
            req.setAttribute("basevalue", basevalue);
        	if(value!=0) {
    			req.setAttribute("result","Photo Upload SUCCESSFUL");
    		}else {
    			req.setAttribute("resultfail","Photo Upload UNSUCCESSFUL");
    		}
		 }catch(Exception e){
			 e.printStackTrace();
			 req.setAttribute("resultfail","Photo Upload UNSUCCESSFUL");
		 }

		return "pis/EmpBasicDetails";
		}
	
}

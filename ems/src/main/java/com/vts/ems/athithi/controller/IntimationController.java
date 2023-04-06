package com.vts.ems.athithi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.google.gson.Gson;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.athithi.dto.ComEmp;
import com.vts.ems.athithi.dto.NewIntimation;
import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.service.IntimationService;



@Controller
public class IntimationController {

	
	@Autowired
	IntimationService service;
	
	@Autowired
	AdminService adminservice;
	
	private final String formmoduleid = "12";
	
	SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm:ss");
	private static final Logger logger = LogManager.getLogger(IntimationController.class);
	Calendar now = Calendar.getInstance();
	
	@RequestMapping(value = "NewIntimation.htm",method =RequestMethod.GET)
	public String newIntimation(HttpServletRequest req,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER AttendanceSync.htm "+UserId);		
		try {
//			ses.setAttribute("SidebarActive","NewIntimation_htm");
//			String groupId=ses.getAttribute("groupId").toString();
			
			req.setAttribute("compnyList", service.getCompnyList(""));
			req.setAttribute("officer", service.getOfficerList(""));
			
			return "athithi/NewIntimationForm";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER AttendanceSync.htm "+UserId, e);
			return "static/Error";
		}	
	}

	
	@RequestMapping(value = "searchCompny", method = RequestMethod.GET)
	public @ResponseBody String projectHead(HttpServletRequest request,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER searchCompny "+UserId);	
		List<Object[]> data = new ArrayList<>();
		try {
			String searchTerm=request.getParameter("searchTerm");
			
			data=service.getCompnyList(searchTerm+"");
			ArrayList<ComEmp> re=new ArrayList<>();
			for(Object[] obj:data) 
			{
			  ComEmp com=new ComEmp();
			  com.setId(obj[0].toString());
			  com.setText(obj[1].toString());
			  re.add(com);
			}
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER searchCompny "+UserId, e);
			return "static/Error";
		}	
			Gson json = new Gson();
			return json.toJson(data);

	}
	
	@RequestMapping(value = "addNewCompany", method = RequestMethod.GET)
	public @ResponseBody String addNewCompany(HttpServletRequest request,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER addNewCompany "+UserId);		
		try {
			String companyName=request.getParameter("project");
			String city=request.getParameter("project1");
		    String[] empName= request.getParameter("project2").split(",");	
		    String[] desig= request.getParameter("project3").split(",");	
		    String[] mobile = request.getParameter("project4").split(",");	
		    
				List<Object[]> data = new ArrayList<>();
				Company company =new Company();
				company.setCompanyName(companyName);
				company.setCompanyCity(city);
				company.setCreatedBy(ses.getAttribute("EmpId").toString());
				data=service.addNewCompany(company);
				Long compId=company.getCompanyId();
		        for(int i=0;i<empName.length;i++) {
		        	CompanyEmployee newEmp=new CompanyEmployee();
		        	newEmp.setCompanyId(compId);
		        	newEmp.setCompanyEmpName(empName[i]);
		        	newEmp.setDesignation(desig[i]);
		        	newEmp.setMobileNo(mobile[i]);
		        	newEmp.setIsActive(1);
		        	Long re=service.addNewEmployee(newEmp);
		        }
				Gson json = new Gson();
				return json.toJson(data);
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER addNewCompany "+UserId, e);
			return "static/Error";
		}	
	}
	
	
	
	@RequestMapping(value = "getCompEmp", method = RequestMethod.GET)
	public @ResponseBody String getCompEmp(HttpServletRequest request,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER getCompEmp "+UserId);		
		try {
			String companyId=request.getParameter("project");
	
			List<Object[]> data = new ArrayList<>();
			data=service.getCompEmp(companyId);
			
			Gson json = new Gson();
			return json.toJson(data);
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER getCompEmp "+UserId, e);
			return "static/Error";
		}	
	}
	
	@RequestMapping(value = "addCompEmp", method = RequestMethod.GET)
	public @ResponseBody String addCompEmp(HttpServletRequest request,HttpSession ses) throws Exception {
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER addCompEmp "+UserId);		
		try {
			String companyId=request.getParameter("project");
			String[] empName= request.getParameter("empName").split(",");	
			String[] desig= request.getParameter("design").split(",");	
		    String[] mobile = request.getParameter("mobilee").split(",");
		    String[] selected=request.getParameter("selected").split(",");
		    List<String> list = new ArrayList<String>();
		    for(String s:selected) {
		    	if(!s.equals("QQ")) {
		    		list.add(s);
		    	}
		    }
		    list.remove("QQ");
		    selected = list.toArray(new String[0]);
		    
		    for(int i=0;i<empName.length;i++) {
		    	CompanyEmployee newEmp=new CompanyEmployee();
		    	newEmp.setCompanyId(Long.parseLong(companyId));
		    	newEmp.setCompanyEmpName(empName[i]);
		    	newEmp.setDesignation(desig[i]);
		    	newEmp.setMobileNo(mobile[i]);
		    	newEmp.setIsActive(1);
		
		    	Long re=service.addNewEmployee(newEmp);
		    	list.add(re.toString());
		    }
		    
			Gson json = new Gson();
			return json.toJson(list);
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER addCompEmp "+UserId, e);
			return "static/Error";
		}	
	}
	
	@RequestMapping(value = "newIntimationSubmit",method =RequestMethod.POST)
	public String 	newIntimationSubmit(HttpServletRequest req,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER newIntimationSubmit "+UserId);		
		try {
			
			NewIntimation intimation =new NewIntimation();
			intimation.setIntimationByEmpNo(ses.getAttribute("EmpNo").toString());
			intimation.setCreateBy(ses.getAttribute("EmpId").toString());
			intimation.setCompnayId(req.getParameter("company"));
			intimation.setVisitors(req.getParameterValues("visitors"));
			intimation.setDuration(req.getParameter("duration"));
			intimation.setFdate(req.getParameter("fdate"));
			intimation.setTdate(req.getParameter("tdate"));
	        intimation.setOfficer(req.getParameter("officer"));
	        intimation.setPurpose(req.getParameter("purpose"));
			intimation.setSpermission(req.getParameter("spermission"));
			
			Long re=service.addNewIntimation(intimation);
			
			return "redirect:/IntimationList.htm";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER newIntimationSubmit "+UserId, e);
			return "static/Error";
		}	
	}
	
	@RequestMapping(value = "IntimationList.htm",method =RequestMethod.GET)
	public String IntimationList(HttpServletRequest req,HttpSession ses) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER IntimationList "+UserId);		
		try {
			String logintype = (String)ses.getAttribute("LoginType");
			ses.setAttribute("formmoduleid", "28");
			ses.setAttribute("SidebarActive","IntimationList.htm");		
			req.setAttribute("IntimationList", service.getItimationList("0"));
			return "athithi/IntimationList";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER IntimationList "+UserId, e);
			return "static/Error";
		}	
	}
	
	
	
	
}

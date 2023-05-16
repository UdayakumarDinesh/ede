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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.athithi.dto.ComEmp;
import com.vts.ems.athithi.dto.NewIntimation;
import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.VpIntimationTrans;
import com.vts.ems.athithi.service.IntimationService;
import com.vts.ems.pi.service.PIService;
import com.vts.ems.property.model.PisMovableProperty;
import com.vts.ems.utils.DateTimeFormatUtil;



@Controller
public class IntimationController {

	
	@Autowired
	IntimationService service;
	
	@Autowired
	AdminService adminservice;
	
	@Autowired
	private PIService piservice;
	
	private final String formmoduleid = "12";
	
	SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm:ss");
	private static final Logger logger = LogManager.getLogger(IntimationController.class);
	Calendar now = Calendar.getInstance();
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@RequestMapping(value = "NewIntimation.htm",method =RequestMethod.GET)
	public String newIntimation(HttpServletRequest req,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER NewIntimation.htm "+UserId);	
		String EmpNo = (String) ses.getAttribute("EmpNo");
		try {
//			ses.setAttribute("SidebarActive","NewIntimation_htm");
//			String groupId=ses.getAttribute("groupId").toString();
			req.setAttribute("EmpData", piservice.getEmpNameDesig(EmpNo));
			req.setAttribute("compnyList", service.getCompnyList(""));
			req.setAttribute("officer", service.getOfficerList(""));
			
			return "athithi/NewIntimationForm";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER NewIntimation.htm "+UserId, e);
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
		String EmpNo = (String) ses.getAttribute("EmpNo");
		try {
			
			NewIntimation intimation =new NewIntimation();
			intimation.setIntimationByEmpNo(ses.getAttribute("EmpNo").toString());
			intimation.setCreateBy(ses.getAttribute("EmpId").toString());
			intimation.setCompnayId(req.getParameter("company"));
			intimation.setVisitors(req.getParameterValues("visitors"));
			intimation.setDuration(req.getParameter("duration"));
			intimation.setExpectedTime(req.getParameter("expectedTime"));
			intimation.setFdate(req.getParameter("fdate"));
			intimation.setTdate(req.getParameter("tdate"));
	        intimation.setOfficer(req.getParameter("officer"));
	        intimation.setPurpose(req.getParameter("purpose"));
//			intimation.setSpermission(req.getParameter("spermission"));	       
	        String[] sp = req.getParameterValues("spermission");
	        
	        String sPermission ="";
	        for(int i=0; i<sp.length; i++) {
	        	if(sp[i].equalsIgnoreCase("Not Applicable")) {
	        		intimation.setVpStatus("A");
	        		intimation.setPisStatusCode("APR");
	        		intimation.setPisStatusCodeNext("APR");
	        	}else {
	        		intimation.setVpStatus("N");
	        		intimation.setPisStatusCode("INI");
	        		intimation.setPisStatusCodeNext("INI");
	        	}
	        	sPermission += sp[i];
	        	  
	        	  if(i != sp.length-1) {
	        		  sPermission += ",";
	        	  } 
	        }
	        intimation.setSpermission(sPermission);
			Long result=service.addNewIntimation(intimation);
			
			if(result>0) {
				VpIntimationTrans transaction = VpIntimationTrans.builder()
						                        .IntimationId(result)
						                        .PisStatusCode("INI")
						                        .Remarks("")
						                        .ActionBy(EmpNo)
						                        .ActionDate(sdtf.format(new Date())).build();
				service.addVpIntimationTrans(transaction);
			}
			
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
		String EmpNo = (String)ses.getAttribute("EmpNo");
		try {
			String logintype = (String)ses.getAttribute("LoginType");
			ses.setAttribute("formmoduleid", "28");
			ses.setAttribute("SidebarActive","IntimationList_htm");		
			req.setAttribute("IntimationList", service.getItimationList(EmpNo));
			
			String CEO = piservice.GetCEOEmpNo();
			List<String> DGMs = piservice.GetDGMEmpNos();
				
            req.setAttribute("CeoName", piservice.GetCeoName());
            req.setAttribute("CEOEmpNos", CEO);
            
            if(!DGMs.contains(EmpNo)) {
				req.setAttribute("DGMEmpName", piservice.GetEmpDGMEmpName(EmpNo));
			}
			req.setAttribute("DGMEmpNos", DGMs);
			req.setAttribute("EmpData", piservice.getEmpNameDesig(EmpNo));
			
			return "athithi/IntimationList";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER IntimationList "+UserId, e);
			return "static/Error";
		}	
	}
	
	@RequestMapping(value="VpIntimationApprovalSubmit.htm")
	public String movablePropFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String)ses.getAttribute("Username");
		logger.info(new Date()+"Inside VpIntimationApprovalSubmit.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		String LoginType=(String)ses.getAttribute("LoginType");
		try {
			String intimationId = req.getParameter("intimationId");
			String remarks = req.getParameter("remarks");
			String action = req.getParameter("Action");
			
			Intimation intimation = service.getIntimationById(Long.parseLong(intimationId));	
			String pisStatusCode = intimation.getPisStatusCode();
			
			long count = service.vpIntimationForward(intimationId, Username, action, remarks, EmpNo, LoginType);
			
			if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RCE"))
			{
				if(count>0) {
					redir.addAttribute("result", "Visitor Pass Sent For Verification Successfully");
				}else {
					redir.addAttribute("resultfail","Visitor Pass Application Sent For Verification Unsuccessful");
				}
				return "redirect:/IntimationList.htm";
			}
			else {
				if(count>0) {
					redir.addAttribute("result", "Visitor Pass verification Successfull");
				}else {
					redir.addAttribute("resultfail", "Visitor Pass verification Unsuccessful");
				}
				return "redirect:/VpApprovals.htm";
			}
			
		}catch (Exception e) {
			logger.info(new Date()+"Inside VpIntimationApprovalSubmit.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		} 
	}
	
	@RequestMapping(value="VpApprovals.htm")
	public String visitorPassApprovals(HttpServletRequest req,HttpSession ses) throws Exception
	{
		String Username =(String) ses.getAttribute("Username");
		logger.info(new Date()+" Inside VpApprovals.htm"+Username);
		String EmpNo = (String)ses.getAttribute("EmpNo");
		try {
			ses.setAttribute("formmoduleid", "28");
			ses.setAttribute("SidebarActive","VpApprovals_htm");
			
			req.setAttribute("ApprovalList", service.visitorPassApprovalList(EmpNo));
			return "athithi/IntimationApprovals";
		}catch (Exception e) {
			logger.info(new Date()+"Inside VpApprovals.htm"+Username,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="VpIntimationTransStatus.htm")
	public String vpIntimationTransStatus(HttpServletRequest req, HttpSession ses) throws Exception
	{
		String Username =(String) ses.getAttribute("Username");
		logger.info(new Date()+"Inside VpIntimationTransStatus.htm"+Username);
		try {
			String intimationid = req.getParameter("intimationid");
			if(intimationid!=null) {
				req.setAttribute("TransactionList", service.vpTransactionList(intimationid.trim()));
			}
			return "athithi/VpIntimationTransStatus";
		}catch (Exception e) {
			logger.error(new Date() +" Inside VpIntimationTransStatus.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
}

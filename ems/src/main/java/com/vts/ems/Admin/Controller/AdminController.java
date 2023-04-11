package com.vts.ems.Admin.Controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.Admin.model.CalendarEvents;
import com.vts.ems.Admin.model.EmployeeContract;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.master.service.MasterService;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.service.EMSMainService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class AdminController {
	
private static final Logger logger = LogManager.getLogger(AdminController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Autowired
	AdminService service;
	                      
	@Autowired
	EMSMainService emsservice;
	
	@Autowired
	private PisService pisservice;
	
	@Autowired
	private MasterService masterservice;
	
 
	
	   @RequestMapping(value = "Role.htm" )
			public String RoleFormAccess(Model model, HttpServletRequest req, HttpSession ses, RedirectAttributes redir)
					throws Exception {
				String UserId = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside Role.htm "+UserId);		
				try {
					
				
					String logintype=req.getParameter("logintype");
					Map md=model.asMap();
					if(logintype==null) {
					
						logintype=(String)md.get("logintype");
						if(logintype==null) {
							logintype="A";
						}	
					}
					
					String moduleid=req.getParameter("moduleid");
					if(moduleid==null) {
						moduleid=(String)md.get("moduleid");
						if(moduleid==null) {
							moduleid="0";
						}		
					}	
				
					req.setAttribute("LoginTypeRoles",service.LoginTypeRoles());
					req.setAttribute("FormDetailsList", service.FormDetailsList(logintype,moduleid));
					req.setAttribute("FormModulesList", service.FormModulesList());
					req.setAttribute("logintype", logintype);
					req.setAttribute("moduleid", moduleid);
					ses.setAttribute("SidebarActive","Role_htm");
				}
				catch (Exception e) {
						e.printStackTrace(); 
						logger.error(new Date() +" Inside Role.htm "+UserId, e);
				}	
				 return "Admin/RoleFormAccess";
			}
	   
	   
		@RequestMapping(value = "PisAdminDashboard.htm", method = RequestMethod.GET)
		public String PisDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside PisAdminDashboard.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				
			
				List<Object[]> admindashboard = service.HeaderSchedulesList("1" ,logintype); 
			
				req.setAttribute("dashboard", admindashboard);
				ses.setAttribute("formmoduleid", "1");
				ses.setAttribute("SidebarActive","PisAdminDashboard_htm");
				return "Admin/AdminDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside PisAdminDashboard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		@RequestMapping(value = "LeaveDashBoard.htm", method = RequestMethod.GET)
		public String LeaveDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside LeaveDashboard.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				
			
				List<Object[]> admindashboard = service.HeaderSchedulesList("5" ,logintype); 
			
				ses.setAttribute("formmoduleid", "8"); 
				ses.setAttribute("SidebarActive", "LeaveDashBoard_htm");
				req.setAttribute("dashboard", admindashboard);

				return "leave/LeaveDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside LeaveDashboard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		
		@RequestMapping(value = "NewspaperDashBoard.htm")
		public String NewspaperDashBoard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside NewspaperDashBoard.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				List<Object[]> admindashboard = service.HeaderSchedulesList("7" ,logintype); 
				ses.setAttribute("formmoduleid", "21");
				ses.setAttribute("SidebarActive", "NewspaperDashBoard_htm");
				req.setAttribute("dashboard", admindashboard); 

				return "newspaper/NewspaperDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside NewspaperDashBoard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		@RequestMapping(value = "TelephoneDashBoard.htm")
		public String TelephoneDashBoard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside TelephoneDashBoard.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				List<Object[]> admindashboard = service.HeaderSchedulesList("8" ,logintype); 
				ses.setAttribute("formmoduleid", "22"); 
				ses.setAttribute("SidebarActive", "TelephoneDashBoard_htm");
				req.setAttribute("dashboard", admindashboard);

				return "newspaper/TelephoneDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside TelephoneDashBoard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		@RequestMapping(value = "MasterDashBoard.htm", method = RequestMethod.GET)
		public String MasterDashBoard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside MasterDashBoard.htm "+Username);		
			try {
				
				String logintype = (String)ses.getAttribute("LoginType");
				List<Object[]> masterdashboard = service.HeaderSchedulesList("2" ,logintype); 
			
				ses.setAttribute("formmoduleid", "2"); 
				ses.setAttribute("SidebarActive", "MasterDashBoard_htm");
				req.setAttribute("dashboard", masterdashboard);

				return "masters/MasterDashBoard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside MasterDashBoard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		
		
		@RequestMapping(value = "HeaderOptionList.htm" , method = RequestMethod.GET)
		public @ResponseBody String HeaderOptionList(HttpServletRequest request ,HttpSession ses) throws Exception 
		{
			Gson json = new Gson();
			List<Object[]> HeaderModuleList = null;
			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside HeaderOptionList.htm "+UserId);		
			try {
				String LoginType = ((String) ses.getAttribute("LoginType"));

			    HeaderModuleList =service.FormOptionList(LoginType);
			}
			catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside HeaderOptionList.htm "+UserId, e);
			}
				return json.toJson(HeaderModuleList);	
		}
		
		
		@RequestMapping(value = "HeaderModuleDropDownList.htm" , method = RequestMethod.GET)
		public @ResponseBody String HeaderModuleDropDownList(HttpServletRequest req ,HttpSession ses) throws Exception 
		{
			Gson json = new Gson();
			List<Object[]> HeaderModuleList = null;
			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside HeaderModuleList.htm "+UserId);		
			try {
				String LoginType = ((String) ses.getAttribute("LoginType"));
				String formOptionId =  req.getParameter("formOptionId");

			    HeaderModuleList =service.HeaderModuleDropDownList(LoginType, formOptionId);
			}
			catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside HeaderModuleList.htm "+UserId, e);
			}
			return json.toJson(HeaderModuleList);	
		}
		
		@RequestMapping(value ="SidebarModuleList.htm", method = RequestMethod.GET)
		public @ResponseBody String SidebarModuleList(HttpServletRequest req, HttpSession ses) throws Exception{
			
			String UserId = (String) ses.getAttribute("Username");
			String logintype = (String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside SidebarModuleList.htm "+UserId);	
			Gson json = new Gson();
			List<Object[]> FormDetailList =null;
			try {
				
				String Formmoduleid = req.getParameter("formmoduleid");
				FormDetailList = service.HeaderSchedulesList(Formmoduleid ,logintype);
				
				
			}catch(Exception e) {
				logger.error(new Date() +" Inside SidebarModuleList.htm "+UserId, e);
				e.printStackTrace();
			}
		
			return json.toJson(FormDetailList);
			
		}
		
		
	    @RequestMapping(value = "FormRoleActive.htm", method = RequestMethod.GET)
		public @ResponseBody String FormRoleActive(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception 
		{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside FormRoleActive.htm "+UserId);
				try
				{
					String FormRoleAccessId = req.getParameter("formroleaccessid");
					service.FormRoleActive(FormRoleAccessId);
				}
				catch (Exception e) {
					logger.error(new Date() +"Inside FormRoleActive.htm "+UserId,e);
						e.printStackTrace(); 
				}

				return "Admin/RoleFormAccess";
			}
		
		    
		    @RequestMapping(value = "ChssApproval.htm" ,method= {RequestMethod.POST,RequestMethod.GET})
		    public String ChssApproval(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception{
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside ChssApproval.htm "+UserId);
		    	String action = (String)req.getParameter("Action");
		    	ses.setAttribute("SidebarActive", "ChssApproval_htm");
		    	try {
		    		if("ADD".equalsIgnoreCase(action)) {
		    			String processing   = (String)req.getParameter("processing");
		    			String verification = (String)req.getParameter("verification");
		    			String approving = (String)req.getParameter("approving");

		    		
		    				CHSSApproveAuthority approve = new CHSSApproveAuthority();
		    				approve.setPO(Long.parseLong(processing));
		    				approve.setVO(Long.parseLong(verification));
		    				approve.setAO(Long.parseLong(approving));
		    				approve.setIsActive(1);
		    				long result = service.AddApprovalAuthority(approve); 
				    		if (result != 0) {
				    			redir.addAttribute("result", "Approving Officers Saved");
							} else {
								redir.addAttribute("resultfail", "Approving Officers Failed to Save");
							}
		    			
		    	
		    		return "redirect:/ChssApproval.htm";
		    		}else {
								Object[] approvallist=service.getChssAprovalList();
								req.setAttribute("ApprovalList", approvallist);
								req.setAttribute("emplist", pisservice.GetAllEmployee());
				    	return "Admin/CHSSApproval";
		    		}
				} catch (Exception e) {
					logger.error(new Date() +"Inside ChssApproval.htm "+UserId , e);
					e.printStackTrace();
					return "static/Error";
				}
		    	
		    	
		    }
		    
		    @RequestMapping(value = "ChssApprovalEdit.htm" ,method= {RequestMethod.POST,RequestMethod.GET})
		    public String ChssApprovalEdit(HttpSession ses , HttpServletRequest req , @RequestPart("selectedFile") MultipartFile selectedFile, RedirectAttributes redir)throws Exception{
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside ChssApprovalEdit.htm "+UserId);
		    	try {		    	
		    		String processing   = (String)req.getParameter("processing");
	    			String verification = (String)req.getParameter("verification");
	    			String approving = (String)req.getParameter("approving");
	    			String id = (String)req.getParameter("AuthId");
	    			
	    				int result = service.UpdateApprovalAuth(processing,verification,approving,id,UserId);
	    					    				
	    				String comments = (String)req.getParameter("comments");
	    		    	   MasterEdit masteredit  = new MasterEdit();
	    		    	   masteredit.setCreatedBy(UserId);
	    		    	   masteredit.setCreatedDate(sdtf.format(new Date()));
	    		    	   masteredit.setTableRowId(Long.parseLong(id));
	    		    	   masteredit.setComments(comments);
	    		    	   masteredit.setTableName("chss_approve_auth");
	    		    	   
	    		    	   MasterEditDto masterdto = new MasterEditDto();
	    		    	   masterdto.setFilePath(selectedFile); 
	    		    	   masterservice.AddMasterEditComments(masteredit , masterdto);
	    				
	    				if(result != 0){
			    			redir.addAttribute("result", "Approving officers updated");
						}else{
							redir.addAttribute("resultfail", "Approving officers failed to updated");
						}
	    				return "redirect:/ChssApproval.htm";
				} catch (Exception e) {
					logger.info(new Date() +"Inside ChssApprovalEdit.htm "+UserId);
					e.printStackTrace();
					return "static/Error";
				}
		    	
		    }
    
		    
		@RequestMapping(value ="EmpRequestMsg.htm",method= {RequestMethod.GET,RequestMethod.POST})
		public String EmpRequestMessage(HttpServletRequest req,HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			long EmpId = (long)ses.getAttribute("EmpId");
			String EmpNo = (String)ses.getAttribute("EmpNo");
			logger.info(new Date() +"Inside EmpRequestMsg.htm "+UserId);
			try {
				
				String action = (String)req.getParameter("Action");
				String empname =(String)ses.getAttribute("EmpName"); 
				
				if("MESSAGE".equalsIgnoreCase(action)) {
					
					String messgae = (String)req.getParameter("message");
					EmployeeRequest reqMsg = new EmployeeRequest();
					reqMsg.setRequestMessage(messgae);
					reqMsg.setEmpId(EmpId);
					reqMsg.setRequestDate(sdtf.format(new Date()));
					reqMsg.setCreatedBy(UserId);
					reqMsg.setCreatedDate(sdtf.format(new Date()));
					reqMsg.setIsActive(1);
					long result = service.AddRequestMsg(reqMsg);
					EMSNotification notification = new EMSNotification();                
					notification.setCreatedBy(UserId);
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(EmpNo);
					notification.setNotificationMessage("Request Message from "+empname);
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("AdminReplyToReqMsg.htm");
					notification.setIsActive(1);
					long value= service.EmpRequestNotification(notification);
					
					if (result != 0) {
		    			redir.addAttribute("result", "Message Sent To Admin Successfully");
					} else {
						redir.addAttribute("resultfail", "Message Sent To Admin UnSuccessful");
					}
					return "redirect:/EmpRequestMsg.htm";
					
				}else if ("Delete".equalsIgnoreCase(action)){
					
					String requestid = (String)req.getParameter("RequestId");
					int result = service.DeleteRequestMsg(requestid ,UserId);
					if (result != 0) {
		    			redir.addAttribute("result", "Message Deleted Successfully");
					} else {
						redir.addAttribute("resultfail", "Message Delete UnSuccessful");
					}
					return "redirect:/EmpRequestMsg.htm";
				}else {
					
					List<Object[]> Reqlist = service.GetRequestMessageList(Long.toString(EmpId));
					req.setAttribute("msglist", Reqlist);				
				}
			
			} catch (Exception e) {
				logger.error(new Date() +"Inside EmpRequestMsg.htm "+UserId,e);
				e.printStackTrace();
				return "static/Error";
				
			}
			return "Admin/EmpRequestMsg";
		}
		
		@RequestMapping(value ="HandingOver.htm",method= {RequestMethod.GET,RequestMethod.POST})
		public String HadlingOver(HttpServletRequest req , HttpSession ses ,RedirectAttributes redir)throws Exception
		{
			long EmpId = (long)ses.getAttribute("EmpId");
			String UserId = (String)ses.getAttribute("Username");
			String logintype = (String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside HandingOver.htm "+UserId);
			ses.setAttribute("SidebarActive", "HandingOver_htm");
			try {
				LocalDate today= LocalDate.now();
				String start ="";
				String end="";
				int currentmonth= today.getMonthValue();
				if(currentmonth<4) 
				{
					start = String.valueOf(today.getYear()-1);
					end =String.valueOf(today.getYear());
				}else{
					start=String.valueOf(today.getYear());
					end =String.valueOf(today.getYear()+1);
				}	
				String fromdate1 ="01-04-"+start;
				//String todate   ="31-03-"+end;
				String todate1 = new Date().toString();
				
				String action = (String)req.getParameter("Action");
				if("ADD".equalsIgnoreCase(action)) {
					
					req.setAttribute("emplist",service.GetFromemployee());
					req.setAttribute("emplist1",service.GetToemployee());
					return "Admin/AddHandingOver";
					
				}else if("ADDHANDING".equalsIgnoreCase(action)){
					Date dd = new Date();

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String DateApplied = sdf.format(dd);
					req.setAttribute("emplist", pisservice.GetAllEmployee());
					String fromemp  = (String)req.getParameter("fromemp");
					String toeme    = (String)req.getParameter("toemp");
					String todate   = (String)req.getParameter("todate");
					String fromdate = (String)req.getParameter("fromdate");
				
					LeaveHandingOver ho = new LeaveHandingOver();
					ho.setApplId("0");
					ho.setDivisionId(Long.parseLong(fromemp.split("_")[1]));
					ho.setFromEmpId(fromemp.split("_")[0]);
					ho.setToEmpId(toeme.split("//")[0]);
					ho.setFromDate(DateTimeFormatUtil.dateConversionSql(fromdate));
					ho.setToDate(DateTimeFormatUtil.dateConversionSql(todate));
					ho.setStatus("A");
					ho.setLoginType(logintype);
					ho.setAppliedDate(DateTimeFormatUtil.dateConversionSql(DateApplied));
					ho.setCreatedBy(UserId);
					ho.setCreateDate(sdtf.format(new Date()));
					ho.setIsActive(1);
					
					Object[] checkAlreadyPresentForSameEmpidAndSameDates = service.checkAlreadyPresentForSameEmpidAndSameDates(fromemp, toeme.split("//")[0], fromdate, todate);
					
					if (fromemp.equalsIgnoreCase(toeme)) {
						redir.addAttribute("resultfail", "Both Employee Can Not Be Same");
					}else if(checkAlreadyPresentForSameEmpidAndSameDates!=null) {
						redir.addAttribute("resultfail", toeme.split("//")[1]+" is not available  for the related date ");
					}else{
						long result = service.AddHandingOver(ho);
						if (result != 0) {
							redir.addAttribute("result", "Handing Over officers added successfully");
						} else {
							redir.addAttribute("resultfail", "Handing Over officers added Unsuccessfull");
						}
					}
					
					return "redirect:/HandingOver.htm";
					
				}else if("REVOKE".equalsIgnoreCase(action)){
					
					String HandingOverId = req.getParameter("HandingOverId");
					int updateRevokeInHandingOver = service.updateRevokeInHandingOver(EmpId,UserId,HandingOverId);
					if (updateRevokeInHandingOver != 0) {
						redir.addAttribute("result", "Handing Over Revoked Successful");
					} else {
						redir.addAttribute("resultfail", "Handing Over Revoked UnSuccessful");
					}
					List<Object[]> handlingoverlist = service.GethandlingOverList(fromdate1,new SimpleDateFormat("dd-MM-yyyy").format(new Date()));	
					req.setAttribute("fromdate", fromdate1);
					req.setAttribute("todate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
					req.setAttribute("handlingoverlist", handlingoverlist);
					return "redirect:/HandingOver.htm";
					
				}else{ 
					String fromdate = (String)req.getParameter("fromdate");
					String todate = (String)req.getParameter("todate");
							
						if(fromdate!=null&&todate!=null){
							
							List<Object[]> handlingoverlist = service.GethandlingOverList(fromdate,todate);		
							
							req.setAttribute("handlingoverlist", handlingoverlist);
							req.setAttribute("fromdate", fromdate);
							req.setAttribute("todate", todate);
							return "Admin/HandlingOver";
							
						}else{
									                        
							List<Object[]> handlingoverlist = service.GethandlingOverList(fromdate1,new SimpleDateFormat("dd-MM-yyyy").format(new Date()));				
							
							req.setAttribute("handlingoverlist", handlingoverlist);
							req.setAttribute("fromdate", fromdate1);
							req.setAttribute("todate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
							return "Admin/HandlingOver";	
						}
			      }
			} catch (Exception e) {
				logger.error(new Date() +"Inside HandingOver.htm "+UserId , e);
				e.printStackTrace();
				return "static/Error";
			}
			
		}
				
	
		@RequestMapping(value = "AdminReplyToReqMsg.htm" , method = {RequestMethod.GET, RequestMethod.POST})
		public String ReqMsgFromUserToAdmin(HttpSession ses ,HttpServletRequest req,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			long EmpId = (long)ses.getAttribute("EmpId");
			String EmpNo = (String)ses.getAttribute("EmpNo");
			logger.info(new Date() +"Inside AdminReplyToReqMsg.htm "+UserId);
			try {
				String requestid = (String)req.getParameter("action");
				String empname = (String)ses.getAttribute("EmpName");
				if(requestid!=null) {
					String response = "response"+requestid;
					String empid = "employeeid"+requestid;
					String responsemsg = (String)req.getParameter(response);
					EMSNotification notification = new EMSNotification();                
					notification.setCreatedBy(UserId);
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setEmpNo(req.getParameter(empid));
					notification.setNotificationBy(EmpNo);
					notification.setNotificationMessage("Response Message from "+empname);
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("EmpRequestMsg.htm");
					notification.setIsActive(1);
					long value= service.EmpRequestNotification1(notification);
					
					int result  = service.UpdateAdminResponse( responsemsg ,  requestid ,UserId);
					if (result != 0) {
						 redir.addAttribute("result", "Response Sent Successfully");
					} else {
						 redir.addAttribute("resultfail", "Response Sent UnSuccessfull");
					}
					return "redirect:/AdminReplyToReqMsg.htm";	
					
				}else {
						List<Object[]> Reqlist = service.GetReqListFromUser();
						req.setAttribute("emplist", pisservice.GetAllEmployee());
						req.setAttribute("msglist", Reqlist);	
					
						req.setAttribute("fromdate", DateTimeFormatUtil.getFirstDayofCurrentMonthRegularFormat());	
						req.setAttribute("todate",DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now()));	
						return "Admin/ReplyToReqMsg";	
				}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside AdminReplyToReqMsg.htm "+UserId , e);
				e.printStackTrace();
				return "static/Error";
			}
		}
		
		
		@RequestMapping(value = "RequestMessagelist.htm" ,method=RequestMethod.POST)
		public String RequestMessagelist(HttpSession ses , HttpServletRequest req)throws Exception
		{
			
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +"Inside RequestMessagelist.htm "+UserId);
			try {
				String emp       = (String)req.getParameter("employee");
				String fromdate  = (String)req.getParameter("fromdate");
				String todate    = (String)req.getParameter("todate");
				                    				 
				List<Object[]>  list = service.GetReqResMessagelist(emp , fromdate ,todate);
				req.setAttribute("msglist", list);	
				req.setAttribute("emplist", pisservice.GetAllEmployee());
				req.setAttribute("emp", emp);
				req.setAttribute("fromdate", fromdate);
				req.setAttribute("todate", todate);
				
				return "Admin/ReplyToReqMsg";
			} catch (Exception e) {
				logger.error(new Date() +"Inside RequestMessagelist.htm "+UserId,e);
				e.printStackTrace();
				return "static/Error";
			}
	
		}
		
		@RequestMapping(value = "AllNotificationList.htm" ,method=RequestMethod.GET)
		public String EmsNotificationList(HttpSession ses , HttpServletRequest req)throws Exception
		{
			
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +"Inside AllNotificationList.htm "+UserId);
			String EmpNo = (String)ses.getAttribute("EmpNo");
			try {			
				 
				List<Object[]>  list = service.AllNotificationLists(EmpNo);
				req.setAttribute("notificationlist", list);	
			    return "Admin/NotificationList";
			} catch (Exception e) {
				logger.error(new Date() +"Inside AllNotificationList.htm "+UserId ,e);
				e.printStackTrace();
				return "static/Error";
			}
	
		}
				
			@RequestMapping(value="UpdateRoleAcess.htm" ,method = RequestMethod.GET)
			public @ResponseBody String RoleAccess(HttpSession ses, HttpServletRequest req , RedirectAttributes redir )throws Exception
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside UpdateRoleAcess.htm "+UserId);
				try {
					String formroleaccessid = (String)req.getParameter("formroleaccessid");
					String moduleid  = (String)req.getParameter("moduleid");
					String LoginType  = (String)req.getParameter("logintype");
					String detailsid  = (String)req.getParameter("detailsid");
					String isactive   = (String)req.getParameter("isactive");
			
					int result = service.updateformroleaccess(formroleaccessid,detailsid, isactive,LoginType , UserId);
					
					redir.addFlashAttribute("logintype",LoginType);
					redir.addFlashAttribute("moduleid",moduleid );
				} catch (Exception e) {
					logger.error(new Date() +"Inside UpdateRoleAcess.htm "+UserId,e);
					e.printStackTrace();
					return "static/Error";
				}
				return "redirect:/Role.htm";
			}
			
			@RequestMapping(value = "CircularDashBoard.htm", method = {RequestMethod.GET,RequestMethod.POST})
			public String CircularDashBoard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
			{
				String Username = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside CircularDashBoard.htm "+Username);		
				try {
					String logintype = (String)ses.getAttribute("LoginType");
					List<Object[]> admindashboard = service.HeaderSchedulesList("9" ,logintype); 
				    
					ses.setAttribute("formmoduleid", "6"); 
					ses.setAttribute("SidebarActive", "CircularDashBoard_tm");
					req.setAttribute("dashboard", admindashboard);
					List<Object[]> SearchList=new ArrayList<Object[]>();
					String search = req.getParameter("search");
					
					if (search != null && !search.trim().equalsIgnoreCase("")) 
					{
			        	SearchList=service.AllDepCircularSearchList(search);
					} 
					else if(search == null) 
					{
						SearchList=service.getCircularOrdersNotice();
					}
					
					req.setAttribute("SearchList", SearchList);
		        	req.setAttribute("Search", search);
		        	
					return "circular/CircularDashboard";
				}catch (Exception e) {
					logger.error(new Date() +" Inside CircularDashBoard.htm "+Username, e);
					e.printStackTrace();	
					return "static/Error";
				}
				
			}
			
			@RequestMapping(value = "OfficeOrderDashBoard.htm", method = RequestMethod.GET)
			public String OfficeOrderDashBoard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
				String Username = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside OfficeOrderDashBoard.htm "+Username);		
				try {
					String logintype = (String)ses.getAttribute("LoginType");
				
					List<Object[]> admindashboard = service.HeaderSchedulesList("10" ,logintype); 
				
					ses.setAttribute("formmoduleid", "10"); 
					req.setAttribute("dashboard", admindashboard);
					ses.setAttribute("SidebarActive", "");
					return "redirect:/OfficeOrder.htm";
				}catch (Exception e) {
					logger.error(new Date() +" Inside OfficeOrderDashBoard.htm "+Username, e);
					e.printStackTrace();	
					return "static/Error";
				}
				
			}
			@RequestMapping(value = "CalendarEvents.htm", method = {RequestMethod.POST,RequestMethod.GET})
			public String CalendarEvents(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
						String Username = (String) ses.getAttribute("Username");
						logger.info(new Date() +"Inside CalendarEvents.htm "+Username);
							try {
								ses.setAttribute("SidebarActive", "CalendarEvents_htm");
								List<Object[]> eventTypeList=service.getEventTypeList();
								req.setAttribute("EventTypeList", eventTypeList);
								String year = req.getParameter("eventYear");
								if(year==null) {
									year = String.valueOf(LocalDate.now().getYear());
								}
								List<Object[]> eventsList=service.getEventsList(year);
								req.setAttribute("year", year);
								req.setAttribute("EventsList", eventsList);
								req.setAttribute("result", req.getParameter("result"));
							} catch (Exception e) {
								logger.error(new Date() +" Inside CalendarEvents.htm "+Username, e);
							e.printStackTrace();
							}	
						
						return "Admin/CalendarEvents";
						}
			@RequestMapping(value="CalendarEventAdd.htm",method= {RequestMethod.GET,RequestMethod.POST})
			public  String CalendarEventsAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
				String Username = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside CalendarEventAdd.htm "+Username);
				try {
					String eventDate = req.getParameter("Eventdate");
					String eventType = req.getParameter("EventType");
					
					String eventName = req.getParameter("EventName");
					String eventDescription = req.getParameter("description");
					CalendarEvents events=new CalendarEvents();
					events.setEventDate(sdf.format(rdf.parse(eventDate)));
					events.setEventTypeCode(eventType);
					events.setEventName(eventName.trim());
					events.setEventDescription(eventDescription.trim());
					events.setCreatedBy(Username);
					events.setCreatedDate(sdtf.format(new Date()));
					events.setIsActive(1);					
					int Year=DateTimeFormatUtil.getYearFromRegularDate(eventDate);					
					redir.addAttribute("eventYear", Year);
					Long result=service .addCalendarEvents(events);
					if (result != 0) {
						 redir.addAttribute("result", "Event Successfully Added");
					} else {
						 redir.addAttribute("resultfail", " Event not Added Successfully");
					}
				} catch (Exception e) {
					logger.error(new Date() +" Inside CalendarEventAdd.htm "+Username, e);
					e.printStackTrace();
				}
				
				return "redirect:/CalendarEvents.htm";
			}	
			 @RequestMapping(value="calendarEventEdit.htm",method= {RequestMethod.GET,RequestMethod.POST})
			public String calendarEventEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
				String Username = (String) ses.getAttribute("Username");
				try {
					String EMSEventId = req.getParameter("EMSEventId");			
					List<Object[]> eventTypeList=service.getEventTypeList();
					req.setAttribute("EventTypeList", eventTypeList);
					Object[] event=service.editCalendarEvent(EMSEventId);
					req.setAttribute("EventsList",event);
					req.setAttribute("year", req.getParameter("eventYear"));
					
				} catch (Exception e) {
					logger.error(new Date() +" Inside calendarEventEdit.htms "+Username, e);
				e.printStackTrace();
				}
				
				return "Admin/CalendarEventEdit";
			}
			 @RequestMapping(value="UpdateCalendarEvent.htm",method= {RequestMethod.GET,RequestMethod.POST})
			 public String updateCalendarEvent(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
				 String Username = (String) ses.getAttribute("Username");
				 try {
					    String EMSEventId = req.getParameter("EMSEventId");
					    String eventDate = req.getParameter("Eventdate");
						String eventType = req.getParameter("EventType");
						String eventName = req.getParameter("EventName");
						String eventDescription = req.getParameter("description");
						
						Long result=service.updateCalendarEvent(EMSEventId,sdf.format(rdf.parse(eventDate)),eventType,eventName,eventDescription);
					   redir.addAttribute("eventYear",req.getParameter("EventYear"));
					   
					   if (result != 0) {
							 redir.addAttribute("result", "Event Successfully Updated");
						} else {
							 redir.addAttribute("resultfail", " Event not Updated Successfully");
						}
				 } catch (Exception e) {
						e.printStackTrace();
					}
				    
				 
				return "redirect:/CalendarEvents.htm"; 
			 }
			 @RequestMapping(value="CalendarEventDelete.htm",method= {RequestMethod.GET,RequestMethod.POST})
			 public String CalendarEventDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
					try {
						String Username = (String) ses.getAttribute("Username");
						String EMSEventId = req.getParameter("EMSEventId");
						String eventYear=req.getParameter("eventYear");
						
						 long result=service.deleteCalendarEvent(EMSEventId);
						 redir.addAttribute("eventYear",eventYear);
						 
						 if (result != 0) {
							 redir.addAttribute("result", "Event Successfully Deleted");
						} else {
							 redir.addAttribute("resultfail", " Event not Deleted Successfully");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				    
					return "redirect:/CalendarEvents.htm";
			 }	

			 @RequestMapping(value = "ContractEmployeeList.htm", method = {RequestMethod.POST,RequestMethod.GET})
				public String ContractEmployeeList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
							String Username = (String) ses.getAttribute("Username");
							logger.info(new Date() +"Inside ContractEmployeeList.htm "+Username);
								try {
									ses.setAttribute("SidebarActive", "ContractEmployeeList_htm");
									String action = req.getParameter("action");
									if("Add".equalsIgnoreCase(action)) {
										return"masters/ContractEmpAddEdit";
									}
									else if("Edit".equalsIgnoreCase(action)) {
										String ContractEmpId = req.getParameter("ContractEmpId");
										Object[] cemp=service.getContractEmployeeData(ContractEmpId);
										req.setAttribute("ContractEmpData", cemp);
										return"masters/ContractEmpAddEdit";
									}
									List<Object[]> list=service.getContractEmployeeList();
									req.setAttribute("ContractEmpList", list);
									req.setAttribute("result", req.getParameter("result"));
									
								} catch (Exception e) {
									e.printStackTrace();
										
								}
								return "masters/ContractEmpList";					
									
   }
			 @RequestMapping(value="ContractEmployeeAdd.htm",method= {RequestMethod.POST,RequestMethod.GET})
			 public String ContractEmployeeAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
				 String Username = (String) ses.getAttribute("Username");
					logger.info(new Date() +"Inside ContractEmployeeAdd.htm "+Username);
			 try {
				//String userName = req.getParameter("userName");
				String EmpName=req.getParameter("EmpName");
				String DOB = req.getParameter("DOB");
				String EmailId=req.getParameter("EmailId");
				String mobileNo=req.getParameter("MobileNo");
				String empNo=service.getMaxContractEmpNo();
				int EmpNo;
				if(empNo!=null) {
				 EmpNo=Integer.parseInt(empNo);
				}
				else {
					EmpNo=0;
				}
				Long MobileNo=null;
				if(!mobileNo.isEmpty()) {
					MobileNo=Long.parseLong(mobileNo);
				}
				EmployeeContract cemp=new EmployeeContract();
				//cemp.setUserName("CE-"+userName);
				cemp.setEmpName(EmpName);				
				cemp.setContractEmpNo("CE"+(++EmpNo));
				cemp.setDateOfBirth(sdf.format(rdf.parse(DOB)));
				cemp.setEmailId(EmailId);
				cemp.setMobileNo(MobileNo);
				cemp.setPassword("$2y$12$QTTMcjGKiCVKNvNa242tVu8SPi0SytTAMpT3XRscxNXHHu1nY4Kui");
				cemp.setCreatedBy(Username);
				cemp.setCreatedDate(sdtf.format(new Date()));
				cemp.setIsActive(1);
		     Long result=service.AddContractEmployeeData(cemp);
		     if(result>0) {
		    	 redir.addAttribute("result", "Contract Employee Data Added succesfully!");
		     }else {
		    	 redir.addAttribute("resultfail", "Contract Employee Data Added Unsuccesful!");
		     }
			} catch (Exception e) {
				e.printStackTrace();
			}
				 return "redirect:/ContractEmployeeList.htm"; 
			 }
			 @RequestMapping(value="ContractEmployeeEdit.htm",method= {RequestMethod.POST,RequestMethod.GET})
			 public String ContractEmployeeUpdate(HttpServletRequest req, HttpSession ses,@RequestPart("selectedFile") MultipartFile selectedFile,  RedirectAttributes redir)  throws Exception {
				 String Username = (String) ses.getAttribute("Username");
					logger.info(new Date() +"Inside ContractEmployeeAdd.htm "+Username);
			 try {
				 String ContractEmpId = req.getParameter("ContractEmpId");
			//	String userName = req.getParameter("userName");
				String EmpName=req.getParameter("EmpName");
				String DOB = req.getParameter("DOB");
				String EmailId=req.getParameter("EmailId");
				String mobileNo=req.getParameter("MobileNo");
				Long MobileNo=null;
				if(!mobileNo.isEmpty()) {
					MobileNo=Long.parseLong(mobileNo);
				}
				
				EmployeeContract cemp=new EmployeeContract();
				cemp.setContractEmpId(Long.parseLong(ContractEmpId));
				//cemp.setUserName("CE-"+userName);
				cemp.setEmpName(EmpName);
				cemp.setDateOfBirth(sdf.format(rdf.parse(DOB)));
				cemp.setEmailId(EmailId);				
				cemp.setMobileNo(MobileNo);								
				cemp.setModifiedBy(Username);
				cemp.setModifiedDate(sdtf.format(new Date()));
				cemp.setIsActive(1);
				String comments = (String)req.getParameter("comments");
		    	   MasterEdit masteredit  = new MasterEdit();
		    	   masteredit.setCreatedBy(Username);
		    	   masteredit.setCreatedDate(sdtf.format(new Date()));
		    	   masteredit.setTableRowId(Long.parseLong(ContractEmpId));
		    	   masteredit.setComments(comments);
		    	   masteredit.setTableName("employee_contract");
		    	     
		    	   MasterEditDto masterdto = new MasterEditDto();
		    	   masterdto.setFilePath(selectedFile);
		    	   
		    	   service.ContractEmpEditComments(masteredit , masterdto);
		     Long result=service.UpdateContractEmployeeData(cemp);
		     if(result>0) {
		    	 redir.addAttribute("result", "Contract Employee Data Updated succesfully!");
		     }else {
		    	 redir.addAttribute("resultfail", "Contract Employee Data Updated Unsuccesful!");
		     }
			} catch (Exception e) {
				e.printStackTrace();
			}
				 return "redirect:/ContractEmployeeList.htm"; 
	}
			 @RequestMapping(value="ContractEmpAddcheck.htm",method=RequestMethod.GET)
				public @ResponseBody String ContractEmpAddcheck(HttpSession ses,HttpServletRequest req) throws Exception{
							String UserId=(String)ses.getAttribute("Username");
							int username = 0;
							BigInteger userNameCount = null;
							logger.info(new Date() +"Inside ContractEmpAddcheck.htm"+UserId);
					try {
						
						String CuserName=req.getParameter("CUserName");
						userNameCount =service.contractEmpAddcheck("CE-"+CuserName);
						username=userNameCount.intValue();
						
					} catch (Exception e) {
						logger.error(new Date() +"Inside ContractEmpAddcheck.htm"+UserId,e);
						e.printStackTrace();
						return "static/Error";
					}
					Gson json = new Gson();
					  return json.toJson(username);
				}
			 @RequestMapping(value="ContractEmpEditcheck.htm",method=RequestMethod.GET)
				public @ResponseBody String ContractEmpEditcheck(HttpSession ses,HttpServletRequest req) throws Exception{
							String UserId=(String)ses.getAttribute("Username");
							int username = 0;
							BigInteger userNameCount = null;
							logger.info(new Date() +"Inside ContractEmpEditcheck.htm"+UserId);
					try {
						String ContractEmpId=req.getParameter("ContractEmpId");
						String CuserName=req.getParameter("CUserName");
						userNameCount =service.contractEmpEditcheck("CE-"+CuserName,ContractEmpId);
						username=userNameCount.intValue();
						
					} catch (Exception e) {
						logger.error(new Date() +"Inside ContractEmpEditcheck.htm"+UserId,e);
						e.printStackTrace();
						return "static/Error";
					}
					Gson json = new Gson();
					  return json.toJson(username);
				}		 
			 
}		

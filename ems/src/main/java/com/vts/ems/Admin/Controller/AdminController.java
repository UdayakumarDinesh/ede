package com.vts.ems.Admin.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.chss.controller.CHSSController;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class AdminController {
	
private static final Logger logger = LogManager.getLogger(CHSSController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	
	@Autowired
	AdminService service;
	                      	
	@Autowired
	private PisService pisservice;
	
	@Value("${Image_uploadpath}")
	private String uploadpath;
	
	   @RequestMapping(value = "Role.htm" )
			public String RoleFormAccess(Model model, HttpServletRequest req, HttpSession ses, RedirectAttributes redir)
					throws Exception {
				String UserId = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside RoleFormAccess.htm "+UserId);		
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
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +" Inside RoleFormAccess.htm "+UserId, e);
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
				return "pis/PisDashboard";
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
			
				req.setAttribute("dashboard", admindashboard);
				return "leave/LeaveDashboard";
			}catch (Exception e) {
				logger.error(new Date() +" Inside LeaveDashboard.htm "+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		@RequestMapping(value = "HeaderModuleList.htm" , method = RequestMethod.GET)
		public @ResponseBody String HeaderModuleList(HttpServletRequest request ,HttpSession ses) throws Exception {
			
			Gson json = new Gson();
			List<Object[]> HeaderModuleList = null;
			String UserId = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside HeaderModuleList.htm "+UserId);		
			try {
				String LoginType = ((String) ses.getAttribute("LoginType"));
				
				
			    HeaderModuleList =service.FormModuleList(LoginType);
			    request.setAttribute("ProjectInitiationList", "");    
			}
			catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside HeaderModuleList.htm "+UserId, e);
			}
				return json.toJson(HeaderModuleList);	
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
		    	try {
		    		if("EDIT".equalsIgnoreCase(action)) {
		    			String processing   = (String)req.getParameter("processing");
		    			String verification = (String)req.getParameter("verification");
		    			String approving = (String)req.getParameter("approving");
		    			String id = (String)req.getParameter("AuthId");
		    			
		    			if(!id.isEmpty()) {
		    				int result = service.UpdateApprovalAuth(processing,verification,approving,id,UserId); 
				    		if (result != 0) {
				    			redir.addAttribute("result", "Approving officers updated");
							} else {
								redir.addAttribute("resultfail", "Approving officers failed to updated");
							}
		    			}else {
		    				CHSSApproveAuthority approve = new CHSSApproveAuthority();
		    				approve.setPO(Long.parseLong(processing));
		    				approve.setVO(Long.parseLong(verification));
		    				approve.setAO(Long.parseLong(processing));
		    				approve.setIsActive(1);
		    				long result = service.AddApprovalAuthority(approve); 
				    		if (result != 0) {
				    			redir.addAttribute("result", "Approving Officers Saved");
							} else {
								redir.addAttribute("resultfail", "Approving Officers Failed to Save");
							}
		    			}
		    	
		    		return "redirect:/ChssApproval.htm";
		    		}else {
								Object[] approvallist=service.getChssAprovalList();
								req.setAttribute("ApprovalList", approvallist);
								req.setAttribute("emplist", pisservice.GetAllEmployee());
				    	return "Admin/CHSSApproval";
		    		}
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	return "Admin/CHSSApproval";
		    	
		    }
		    
	
		
		@RequestMapping(value ="EmpRequestMsg.htm",method= {RequestMethod.GET,RequestMethod.POST})
		public String EmpRequestMessage(HttpServletRequest req,HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			long EmpId = (long)ses.getAttribute("EmpId");
			logger.info(new Date() +"Inside EmpRequestMsg.htm"+UserId);
			try {
				
				String action = (String)req.getParameter("Action");
				String empname =(String)ses.getAttribute("EmpName"); 
				
				if("MESSAGE".equalsIgnoreCase(action)) {
					
					String messgae = (String)req.getParameter("message");
					EmployeeRequest reqMsg = new EmployeeRequest();
					reqMsg.setRequestMessage(messgae);
					reqMsg.setEmpId(EmpId);
					reqMsg.setCreatedBy(UserId);
					reqMsg.setCreatedDate(messgae);
					reqMsg.setCreatedDate(sdtf.format(new Date()));
					reqMsg.setIsActive(1);
					long result = service.AddRequestMsg(reqMsg);
					EMSNotification notification = new EMSNotification();                
					notification.setCreatedBy(UserId);
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(EmpId);
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
				
				e.printStackTrace();
				
			}
			return "Admin/EmpRequestMsg";
		}
		
		@RequestMapping(value ="HandingOver.htm",method= {RequestMethod.GET,RequestMethod.POST})
		public String HadlingOver(HttpServletRequest req , HttpSession ses ,RedirectAttributes redir)throws Exception
		{
			long EmpId = (long)ses.getAttribute("EmpId");
			String UserId = (String)ses.getAttribute("Username");
			String logintype = (String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside HadlingOver.htm "+UserId);
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
					
					ho.setFrom_empid(fromemp);
					ho.setTo_empid(toeme.split("//")[0]);
					ho.setFrom_date(DateTimeFormatUtil.dateConversionSql(fromdate));
					ho.setTo_date(DateTimeFormatUtil.dateConversionSql(todate));
					ho.setStatus("A");
					ho.setLogin_type(logintype);
					ho.setApplied_date(DateTimeFormatUtil.dateConversionSql(DateApplied));
					ho.setCreatedby(UserId);
					ho.setCreatedate(sdtf.format(new Date()));
					ho.setIs_active(1);
					
					Object[] checkAlreadyPresentForSameEmpidAndSameDates = service.checkAlreadyPresentForSameEmpidAndSameDates(fromemp, toeme.split("//")[0], fromdate, todate);
					
					if (fromemp.equalsIgnoreCase(toeme)) {
						redir.addAttribute("resultfail", "Both Employee Can Not Be Same");
					}else if(checkAlreadyPresentForSameEmpidAndSameDates!=null) {
						redir.addAttribute("resultfail", toeme.split("//")[1]+" is not available  for the related date ");
					}else{
						int result = service.AddHandingOver(ho);
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
				e.printStackTrace();
				req.setAttribute("resultfail", "Internal Error!");
				return "Admin/HandlingOver";
			}
			
		}
			
	
		
		@RequestMapping(value = "AdminReplyToReqMsg.htm" , method = {RequestMethod.GET, RequestMethod.POST})
		public String ReqMsgFromUserToAdmin(HttpSession ses ,HttpServletRequest req,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			long EmpId = (long)ses.getAttribute("EmpId");
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
					notification.setEmpId(Long.parseLong(req.getParameter(empid)));
					notification.setNotificationBy(EmpId);
					notification.setNotificationMessage("Response Message from "+empname);
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("EmpRequestMsg.htm");
					notification.setIsActive(1);
					long value= service.EmpRequestNotification1(notification);
					
					int result  = service.UpdateAdminResponse( responsemsg ,  requestid ,UserId);
					if (result != 0) {
						 redir.addAttribute("result", "Response Sent Successfuly");
					} else {
						 redir.addAttribute("resultfail", "Response Sent UnSuccessful");
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
				e.printStackTrace();
				return "Admin/ReplyToReqMsg";
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
				e.printStackTrace();
				return "Admin/ReplyToReqMsg";
			}
	
		}
		
		@RequestMapping(value = "AllNotificationList.htm" ,method=RequestMethod.GET)
		public String EmsNotificationList(HttpSession ses , HttpServletRequest req)throws Exception
		{
			
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +"Inside AllNotificationList.htm "+UserId);
			long EmpId = (long)ses.getAttribute("EmpId");
			try {			
				 
				List<Object[]>  list = service.AllNotificationLists(EmpId);
				req.setAttribute("notificationlist", list);	
			    return "Admin/NotificationList";
			} catch (Exception e) {
				e.printStackTrace();
				return "Admin/NotificationList";
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
					e.printStackTrace();
				}
				return "redirect:/Role.htm";
			}
		
		
			                    
			
}

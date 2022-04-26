package com.vts.ems.Admin.Controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.chss.controller.CHSSController;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestSub;
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
	
	   @RequestMapping(value = "Role.htm" )
			public String RoleFormAccess(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)
					throws Exception {
				String UserId = (String) ses.getAttribute("Username");
				logger.info(new Date() +"Inside LoginTypeList.htm "+UserId);		
				try {
			
					req.setAttribute("LoginTypeRoles",service.LoginTypeRoles());
					req.setAttribute("FormDetailsList", service.FormDetailsList(req.getParameter("logintype"),req.getParameter("moduleid")));
					req.setAttribute("FormModulesList", service.FormModulesList());
					req.setAttribute("logintype", req.getParameter("logintype"));
					req.setAttribute("moduleid", req.getParameter("moduleid"));
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +" Inside LoginTypeList.htm "+UserId, e);
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
			public @ResponseBody String FormRoleActive(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)
					throws Exception 
			{
				String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside FormRoleActive.htm "+UserId);
				try
				{
					String FormRoleAccessId = req.getParameter("formroleaccessid");
					
					
					service.FormRoleActive(FormRoleAccessId);
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +"Inside FormRoleActive.htm "+UserId,e);
				}

				return "Admin/RoleFormAccess";
			}
		    @RequestMapping(value = "OtherItems.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		    public  String Otheritem(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception
		    {
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside Otheritem.htm "+UserId);
				try {				
					List<Object[]> otheritem = service.OtherItems(); 
					req.setAttribute("itemlist", otheritem);
		       }catch(Exception e){
		    	   e.printStackTrace();
		       }
				return "Admin/OtherItems";
		     }
		    
		    @RequestMapping(value = "TestSub.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		    public  String ChssTestMain(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception
		    {
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside ChssTestMain.htm "+UserId);
				try {				 
					List<Object[]> ChssTestMain = service.ChssTestSub(); 
					req.setAttribute("ChssTestMain", ChssTestMain);
		       }catch(Exception e){
		    	   e.printStackTrace();
		       }
				return "Admin/CHSSTextSub";
		     }
		    
		    @RequestMapping(value="ChssTestSub.htm" , method=RequestMethod.POST)
		    public String ChssTestSubAddEdit(HttpSession ses, HttpServletRequest req, RedirectAttributes redir)throws Exception
		    {
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside ChssTestSub.htm "+UserId);
				try {
					 String action= (String)req.getParameter("action");
					 List<Object[]> TestMain = service.ChssTestMain(); 
					 if("ADD".equalsIgnoreCase(action)){
						
						 req.setAttribute("TestMain", TestMain);						 
						 return "Admin/CHSSTestSubADDEDIT";
						 
					 }else if ("EDIT".equalsIgnoreCase(action)){
							String subid = (String)req.getParameter("SubId");
							CHSSTestSub sub = service.testSub(subid);
							req.setAttribute("TestMain",TestMain);
							req.setAttribute("subdata",sub);
						return "Admin/CHSSTestSubADDEDIT";
						
					 }else if("ADDTEST".equalsIgnoreCase(action)){
						
						 	 String testmain =(String)req.getParameter("Main");
						 	 String testName =(String)req.getParameter("Name");
						 	 String Rate     =(String)req.getParameter("Rate");
						 	CHSSTestSub  sub = new CHSSTestSub();
						 	sub.setTestRate(Integer.parseInt(Rate)); 
						 	sub.setTestName(testName); 
						 	sub.setTestMainId(Long.parseLong(testmain));
						 	sub.setIsActive(1);						 	 
						 	sub.setCreatedDate(sdtf.format(new Date()));
						 	sub.setCreatedBy(UserId);
						 	long result = service.AddTestSub(sub);
						 	if (result != 0) {
								redir.addAttribute("result", "TEST ADDED SUCCESSFUL");
							} else {
								redir.addAttribute("resultfail", "TEST ADDED UNSUCCESSFUL");
							}
						return "redirect:/TestSub.htm";
					 }else if("EDITTEST".equalsIgnoreCase(action)){
						 String testmain =(String)req.getParameter("Main");
					 	 String testName =(String)req.getParameter("Name");
					 	 String Rate     =(String)req.getParameter("Rate");
					 	 String subid    =(String)req.getParameter("SubId");
					 	CHSSTestSub  sub = new CHSSTestSub();
					 	 	sub.setTestSubId(Long.parseLong(subid));
						 	sub.setTestRate(Integer.parseInt(Rate)); 
						 	sub.setTestName(testName); 
						 	sub.setTestMainId(Long.parseLong(testmain));				 	 
						 	sub.setModifiedDate(sdtf.format(new Date()));
						 	sub.setModifiedBy(UserId);
						 	long result = service.EditTestSub(sub);
						 	if (result != 0) {
								redir.addAttribute("result", "TEST EDITED SUCCESSFUL");
							} else {
								redir.addAttribute("resultfail", "TEST EDITED UNSUCCESSFUL");
							}
						return "redirect:/TestSub.htm";
					 	 
					 }
		       }catch(Exception e){
		    	   redir.addAttribute("resultfail", "Some Problem Occure!");
		    	   e.printStackTrace();
		    	   return "redirect:/TestSub.htm";
		       }
				return "Admin/CHSSTestSubADDEDIT";
		    }
		    
		    @RequestMapping(value = "OtherItemAddEdit.htm" ,method=RequestMethod.POST)
		    public String OtherItemAddEdit(HttpSession ses ,HttpServletRequest req , RedirectAttributes redir)throws Exception
		    {
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside ChssTestSub.htm "+UserId);
				try {
					 String action= (String)req.getParameter("action");
					
					 if("ADD".equalsIgnoreCase(action)) {
						 
						 return "Admin/OtherItemsAddEdit";
						 
					 }else if ("EDIT".equalsIgnoreCase(action)) {
						 
						String itemid = (String)req.getParameter("itemid");
						CHSSOtherItems items = service.getOtherItem(Integer.parseInt(itemid));
						req.setAttribute("Item", items);
						return "Admin/OtherItemsAddEdit";
						
					}else if ("ADDITEM".equalsIgnoreCase(action)) {
						String itemname = (String)req.getParameter("ItemName");
						String paylevel1 = (String)req.getParameter("level1");
						String paylevel2 = (String)req.getParameter("level2");
						String paylevel3 = (String)req.getParameter("level3");
						String paylevel4 = (String)req.getParameter("level4");
						CHSSOtherItems item = new CHSSOtherItems();
						
						item.setCreatedDate(sdtf.format(new Date()));
						item.setPayLevel1(Integer.parseInt(paylevel1));
						item.setPayLevel2(Integer.parseInt(paylevel2));
						item.setPayLevel3(Integer.parseInt(paylevel3));
						item.setPayLevel4(Integer.parseInt(paylevel4));
						item.setCreatedBy(UserId);
						item.setCreatedDate(sdtf.format(new Date()));
						item.setOtherItemName(itemname);
						item.setIsActive(1);
						int result = service.AddOtherItem(item);
					 	if (result != 0) {
							redir.addAttribute("result", "ITEM ADDED SUCCESSFUL");
						} else {
							redir.addAttribute("resultfail", "ITEM ADDED UNSUCCESSFUL");
						}
					 	return "redirect:/OtherItems.htm";
					 	
					}else if ("EDITITEM".equalsIgnoreCase(action)) {
						String itemname = (String)req.getParameter("ItemName");
						String itemid   = (String)req.getParameter("itemid");
						String paylevel1 = (String)req.getParameter("level1");
						String paylevel2 = (String)req.getParameter("level2");
						String paylevel3 = (String)req.getParameter("level3");
						String paylevel4 = (String)req.getParameter("level4");
						CHSSOtherItems item = new CHSSOtherItems();
						item.setPayLevel1(Integer.parseInt(paylevel1));
						item.setPayLevel2(Integer.parseInt(paylevel2));
						item.setPayLevel3(Integer.parseInt(paylevel3));
						item.setPayLevel4(Integer.parseInt(paylevel4));
						item.setOtherItemId(Integer.parseInt(itemid));
						item.setModifiedDate(sdtf.format(new Date()));
						item.setModifiedBy(UserId);
						item.setOtherItemName(itemname);
						int result = service.EditItem(item);
					 	if (result != 0) {
							redir.addAttribute("result", "ITEM EDITED SUCCESSFUL");
						} else {
							redir.addAttribute("resultfail", "ITEM EDITED UNSUCCESSFUL");
						}
						return "redirect:/OtherItems.htm";
					}
					 
				}catch (Exception e) {
					redir.addAttribute("resultfail", "Some Problem Occure!");
					e.printStackTrace();
					return "redirect:/OtherItems.htm";
				}
				return "Admin/OtherItemsAddEdit";
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
				    			redir.addAttribute("result", "APPROVAL AUTHORITY EDITED SUCCESSFUL");
							} else {
								redir.addAttribute("resultfail", "APPROVAL AUTHORITY EDITED UNSUCCESSFUL");
							}
		    			}else {
		    				CHSSApproveAuthority approve = new CHSSApproveAuthority();
		    				approve.setPO(Long.parseLong(processing));
		    				approve.setVO(Long.parseLong(verification));
		    				approve.setAO(Long.parseLong(processing));
		    				approve.setIsActive(1);
		    				long result = service.AddApprovalAuthority(approve); 
				    		if (result != 0) {
				    			redir.addAttribute("result", "APPROVAL AUTHORITY ADDED SUCCESSFUL");
							} else {
								redir.addAttribute("resultfail", "APPROVAL AUTHORITY ADDED UNSUCCESSFUL");
							}
		    			}
		    	
		    		return "redirect:/ChssApproval.htm";
		    		}else {
								Object[] approvallist=service.getChssAprovalList();
								req.setAttribute("ApprovalList", approvallist);
								req.setAttribute("emplist", pisservice.getEmpList());
				    	return "Admin/CHSSApproval";
		    		}
				} catch (Exception e) {
					e.printStackTrace();
				}
		    	return "Admin/CHSSApproval";
		    	
		    }
		    
		@RequestMapping(method= {RequestMethod.POST,RequestMethod.GET},value ="MedicineList.htm")
		public String MedicineList(HttpServletRequest req , HttpSession ses )throws Exception
		{
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside MedicineList.htm "+UserId);
			try {
				String name  = (String)req.getParameter("tratementname");		
				if(name!=null) {
				
					List<Object[]>  list = service.getMedicineListByTreatment(name);
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					req.setAttribute("MedicineList", list);
					req.setAttribute("treat", name);
				} else {
					
					List<Object[]>  list = service.getMedicineList();
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					req.setAttribute("MedicineList", list);
					req.setAttribute("treat", name);
				}
			
				return "Admin/CHSSMedicineList";
			} catch (Exception e) {
				e.printStackTrace();
				return "Admin/CHSSMedicineList";
			}
			
		}
		@RequestMapping(value="ChssMedicine.htm", method= {RequestMethod.POST,RequestMethod.GET})
		public String ADDEDITMedicine(HttpServletRequest req ,HttpSession ses ,RedirectAttributes redir)throws Exception
		{
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside MedicineList.htm "+UserId);
			try {
				
				String action = (String)req.getParameter("Action");
			
				if("ADD".equalsIgnoreCase(action)){
					
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					return "Admin/CHSSMedicineADDEDIT";
					
				}else if ("EDIT".equalsIgnoreCase(action)) {
					
					String medicineid = (String)req.getParameter("MedicineId");
					CHSSMedicineList  medicinelist=service.getCHSSMedicine(Long.parseLong(medicineid));
					List<Object[]> treatment = service.GetTreatmentType();
					req.setAttribute("treatment", treatment);
					req.setAttribute("medicinelist", medicinelist);
					return "Admin/CHSSMedicineADDEDIT";
					
				}else if ("EDITMEDICINE".equalsIgnoreCase(action)) {
					
					String medicineId = (String)req.getParameter("medicineId");
					String tratementname = (String)req.getParameter("tratementname");
					String MedicineName  = (String)req.getParameter("MedicineName");
					String IsAdmissible  = (String)req.getParameter("IsAdmissible");
					CHSSMedicineList  medicinelist = new CHSSMedicineList();
					medicinelist.setMedicineId(Long.parseLong(medicineId));
					medicinelist.setMedicineName(MedicineName);
					medicinelist.setTreatTypeId(Long.parseLong(tratementname));
					medicinelist.setIsAdmissible(IsAdmissible);
					long result =service.EditMedicine(medicinelist);;
					if (result != 0) {
		    			redir.addAttribute("result", "MEDICINE EDITED SUCCESSFUL");
					} else {
						redir.addAttribute("resultfail", "MEDICINE EDITED UNSUCCESSFUL");
					}
					return "redirect:/MedicineList.htm";
					
				}else if ("ADDMEDICINE".equalsIgnoreCase(action)) {
					
					String tratementname = (String)req.getParameter("tratementname");
					String MedicineName  = (String)req.getParameter("MedicineName");
					CHSSMedicineList  medicinelist = new CHSSMedicineList();
					if(tratementname.equalsIgnoreCase("1")) {
						medicinelist.setIsAdmissible("N");
					}else {
						medicinelist.setIsAdmissible("Y");
					}
					medicinelist.setMedicineName(MedicineName);
					medicinelist.setTreatTypeId(Long.parseLong(tratementname));
					medicinelist.setCategoryId(0l);
					medicinelist.setIsActive(1);
					long result=service.AddMedicine(medicinelist);
					if (result != 0) {
		    			redir.addAttribute("result", "MEDICINE ADDED SUCCESSFUL");
					} else {
						redir.addAttribute("resultfail", "MEDICINE ADDED UNSUCCESSFUL");
					}
					return "redirect:/MedicineList.htm";
				}	
				return "redirect:/MedicineList.htm";
			}catch (Exception e){
				e.printStackTrace();
				redir.addAttribute("resultfail", "SOME PROBLE OCCURE!");
				return "redirect:/MedicineList.htm";
			}
		}
		
		@RequestMapping(value ="DuplicateMedicine.htm",method=RequestMethod.GET)
		public @ResponseBody String CheckDuplicateMedicine(HttpSession ses , HttpServletRequest req)throws Exception
		{
			int count =0;
			Gson json = new Gson();
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside CheckDuplicateMedicine.htm()"+UserId);
			try {
				String Medicinename = (String)req.getParameter("MedicineName");
				count = service.Checkduplicate( Medicinename);
				
				 return json.toJson(count);
			}catch (Exception e){
				e.printStackTrace();
				 return json.toJson(count);
			}
		}
		
		@RequestMapping(value ="EmpRequestMsg.htm",method= {RequestMethod.GET,RequestMethod.POST})
		public String EmpRequestMessage(HttpServletRequest req,HttpSession ses,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			long EmpId = (long)ses.getAttribute("EmpId");
			logger.info(new Date() +"Inside MedicineList.htm "+UserId);
			try {
				
				String action = (String)req.getParameter("Action");
				
				
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
					notification.setNotificationMessage("Request Message from "+UserId);
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("");
					notification.setIsActive(1);
					long value= service.EmpRequestNotification(notification);
					System.out.println("value   :"+value);
					if (result != 0) {
		    			redir.addAttribute("result", "MESSAGE SENT TO ADMIN SUCCESSFULY");
					} else {
						redir.addAttribute("resultfail", "MESSAGE SENT TO ADMIN UNSUCCESSFUL");
					}
					return "redirect:/EmpRequestMsg.htm";
					
				}else if ("Delete".equalsIgnoreCase(action)){
					
					String requestid = (String)req.getParameter("RequestId");
					int result = service.DeleteRequestMsg(requestid ,UserId);
					if (result != 0) {
		    			redir.addAttribute("result", "MESSAGE DELETE SUCCESSFUL");
					} else {
						redir.addAttribute("resultfail", "MESSAGE DELETE UNSUCCESSFUL");
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
		public String HadlingOver(HttpServletRequest req , HttpSession ses)throws Exception
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
					
					req.setAttribute("emplist", pisservice.getEmpList());
					return "Admin/AddHandingOver";
					
				}else if("ADDHANDING".equalsIgnoreCase(action)){
					
					req.setAttribute("emplist", pisservice.getEmpList());
					String fromemp  = (String)req.getParameter("fromemp");
					String toeme    = (String)req.getParameter("toemp");
					String todate   = (String)req.getParameter("todate");
					String fromdate = (String)req.getParameter("fromdate");
					LeaveHandingOver ho = new LeaveHandingOver();
					ho.setFrom_empid(fromemp);
					ho.setTo_empid(toeme);
					ho.setFrom_date(DateTimeFormatUtil.dateConversionSql(fromdate));
					ho.setTo_date(DateTimeFormatUtil.dateConversionSql(todate));
					ho.setStatus("A");
					ho.setLogin_type(logintype);
					ho.setApplied_date(DateTimeFormatUtil.dateConversionSql(sdf.format(new Date())));
					ho.setCreatedby(UserId);
					ho.setCreatedate(sdtf.format(new Date()));
					ho.setIs_active(1);
					

					Object[] checkAlreadyPresentForSameEmpidAndSameDates = service.checkAlreadyPresentForSameEmpidAndSameDates(fromemp, toeme, fromdate, todate);
					
					if (fromemp.equalsIgnoreCase(toeme)) {
						req.setAttribute("resultfail", "Both Employee Can Not Be Same");
					}else if(checkAlreadyPresentForSameEmpidAndSameDates!=null) {
						req.setAttribute("resultfail", "Add Restricted Because Of Duplicacy");
					}else{
						int result = service.AddHandingOver(ho);
						if (result != 0) {
			    			req.setAttribute("result", "HANDING OVER ADDED SUCCESSFUL");
						} else {
							req.setAttribute("resultfail", "HANDING OVER ADDED UNSUCCESSFUL");
						}
					}
					
					return "Admin/AddHandingOver";
					
				}else if("REVOKE".equalsIgnoreCase(action)){
					
					String HandingOverId = req.getParameter("HandingOverId");
					int updateRevokeInHandingOver = service.updateRevokeInHandingOver(EmpId, HandingOverId);
					if (updateRevokeInHandingOver != 0) {
		    			req.setAttribute("result", "HANDING OVER REVOKED SUCCESSFUL");
					} else {
						req.setAttribute("resultfail", "HANDING OVER REVOKED UNSUCCESSFUL");
					}
					List<Object[]> handlingoverlist = service.GethandlingOverList(fromdate1,todate1);		
					req.setAttribute("handlingoverlist", handlingoverlist);
					return "Admin/HandlingOver";
					
				}else if("LIST".equalsIgnoreCase(action)){
					String fromdate = (String)req.getParameter("fromdate");
					String todate = (String)req.getParameter("todate");
					List<Object[]> handlingoverlist = service.GethandlingOverList(fromdate,todate);					
					req.setAttribute("handlingoverlist", handlingoverlist);
					return "Admin/HandlingOver";
					
				}else{
								
				List<Object[]> handlingoverlist = service.GethandlingOverList(fromdate1,todate1);				
					req.setAttribute("handlingoverlist", handlingoverlist);
					return "Admin/HandlingOver";	
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				req.setAttribute("resultfail", "SOME PROBLEM OCCURE!");
				return "Admin/HandlingOver";
			}
			
		}
			
		@RequestMapping(value = "DoctorsMaster.htm" , method= {RequestMethod.GET,RequestMethod.POST})
		public String DoctorsMasters(HttpSession ses , HttpServletRequest req ,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String action = (String)req.getParameter("Action");
				
				if("EDIT".equalsIgnoreCase(action)) {
					System.out.println();
					String DocRateid =(String)req.getParameter("DocRateid");
					List<Object[]> treatment = service.GetTreatmentType();
					CHSSDoctorRates  docrate = service.getCHSSDocRate(Long.parseLong(DocRateid));
					req.setAttribute("treatment", treatment);
					req.setAttribute("docrate", docrate);
					return "Admin/CHSSDoctorEdit";
				}else if("EDITDOCRATE".equalsIgnoreCase(action)){
					
					String Rateid = (String)req.getParameter("Rateid");
					String Treatementid = (String)req.getParameter("Treatementid");
					//String DocQualification = (String)req.getParameter("DocQualification");
					//String DocRating = (String)req.getParameter("DocRating");
					String Consultation1 = (String)req.getParameter("Consultation1");
					String Consultation2 = (String)req.getParameter("Consultation2");
					
					CHSSDoctorRates  DocRate = new CHSSDoctorRates();
					DocRate.setDocRateId(Integer.parseInt(Rateid));
					DocRate.setTreatTypeId(Integer.parseInt(Treatementid));
					//DocRate.setDocQualification(DocQualification);
					//DocRate.setDocRating(DocRating);
					DocRate.setConsultation_1(Integer.parseInt(Consultation1));
					DocRate.setConsultation_2(Integer.parseInt(Consultation2));
					DocRate.setModifiedBy(UserId);
					DocRate.setModifiedDate(sdtf.format(new Date()));
					int result = service.EditDoctorMaster(DocRate);
					if (result != 0) {
		    			redir.addAttribute("result", "DOCTOR EDIT SUCCESSFUL");
					} else {
						redir.addAttribute("resultfail", "DOCTOR EDIT UNSUCCESSFUL");
					}
					return "redirect:/DoctorsMaster.htm";
				}else {
					List<Object[]> doctorlist = service.GetDoctorList();
					req.setAttribute("doctorlist", doctorlist);
					return "Admin/CHSSDoctorsList";
				}		
			} catch (Exception e) {
				req.setAttribute("resultfail", "Some Provblem Occure!");
				e.printStackTrace();
				return "Admin/CHSSDoctorsList";
			}
			
		}                                
		
		@RequestMapping(value = "LabMaster.htm",method= {RequestMethod.POST,RequestMethod.GET})
		public String LabMaster(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String action =(String)req.getParameter("Action");
				if("EDIT".equalsIgnoreCase(action)) {
					String labmasterId = (String)req.getParameter("labmasterId");
					LabMaster lab = service.GetLabDetailsToEdit(Long.parseLong(labmasterId));
					List<Object[]> labslist=service.getLabsList();
					req.setAttribute("emplist", pisservice.getEmpList());
					req.setAttribute("labslist", labslist);
					req.setAttribute("labdetails", lab);
					       return "Admin/LadMasterEdit";
				}else if("EDITLAB".equalsIgnoreCase(action)){
					String labcode = (String)req.getParameter("LabCode");
					String labname = (String)req.getParameter("LabName");
					String labaddress = (String)req.getParameter("LabAddress");
					String labcity = (String)req.getParameter("LabCity");
					String labemail = (String)req.getParameter("LabEmail");
					String labpin = (String)req.getParameter("LabPin");
					String labunitcode = (String)req.getParameter("LabUnitCode");
					String labtelph = (String)req.getParameter("LabTelephone");
					String labfxno = (String)req.getParameter("LabFaxNo");
					String labauthority = (String)req.getParameter("LabAuthority");
					String labauthorityname = (String)req.getParameter("LabAuthorityName");
					String Lab  = (String)req.getParameter("labid");
					String LabRfp  = (String)req.getParameter("LabRFPEmail");
					String LabMasterId = (String)req.getParameter("LabMasterId");
					LabMaster lab = new LabMaster ();
					
					lab.setLabCode(labcode);
					lab.setLabName(labname);
					lab.setLabAddress(labaddress);
					lab.setLabCity(labcity);
					lab.setLabEmail(labemail);
					lab.setLabPin(labpin);
					lab.setLabUnitCode(labunitcode);
					lab.setLabTelNo(labtelph);
					lab.setLabFaxNo(labfxno);
					lab.setLabAuthority(labauthority);
					lab.setLabAuthorityId(Integer.parseInt(labauthorityname));
					lab.setLabId(Integer.parseInt(Lab));
					lab.setLabRfpEmail(LabRfp);
					lab.setLabMasterId(Long.parseLong(LabMasterId));
					lab.setModifiedBy(UserId);
					lab.setModifiedDate(sdtf.format(new Date()));
					long result = service.EditLabMaster(lab);
					if (result != 0) {
		    			redir.addAttribute("result", "LAB MASTER SUCCESSFUL");
					} else {
						redir.addAttribute("resultfail", "LAB MASTER UNSUCCESSFUL");
					}
					return "redirect:/LabMaster.htm";
				}else {
					Object[] labdetails = service.getLabDetails();
					req.setAttribute("labdetails", labdetails);
				           return "Admin/LabDetails";
				}
			} catch (Exception e) {
				req.setAttribute("resultfail", "Some Provblem Occure!");
				e.printStackTrace();
				return "Admin/LabDetails";
			}
			
		}
		
		@RequestMapping(value = "OtherItemAmount.htm" , method = {RequestMethod.POST,RequestMethod.GET})
		public String OtherItem(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				List<Object[]> otheritem = service.OtherItems(); 
				req.setAttribute("itemlist", otheritem);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Admin/OtherItemAmount";
		}
		
}

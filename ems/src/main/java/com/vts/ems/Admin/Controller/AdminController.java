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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.EmployeeDesig;
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
					logger.error(new Date() +"Inside FormRoleActive.htm "+UserId,e);
						e.printStackTrace(); 
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
					 System.out.println(action +"  req "+ req.getParameter("SubId"));
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
						
					 }else {
						 
						 if(req.getParameter("SubId")!=null){
							 //Test Edit code 
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
							 	long result =service.EditTestSub(sub);
							 	if (result != 0) {
									redir.addAttribute("result", "Test details Updated");
								} else {
									redir.addAttribute("resultfail", "Test details Failed to Update");
								}
							return "redirect:/TestSub.htm";
						 	 
						 }else {
							//Test Add code 
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
						 	long result =  service.AddTestSub(sub);
						 	if (result != 0) {
								redir.addAttribute("result", "Test details saved");
							} else {
								redir.addAttribute("resultfail", "Test details Failed to save");
							}
						return "redirect:/TestSub.htm";
						 }
					 } 
					 
					
		       }catch(Exception e){
		    	   redir.addAttribute("resultfail", "Internal Error!");
		    	   e.printStackTrace();
		    	   return "redirect:/TestSub.htm";
		       }
				
		    }
		    
		    @RequestMapping(value = "OtherItemAddEdit.htm" ,method= {RequestMethod.POST,RequestMethod.GET})
		    public String OtherItemAddEdit(HttpSession ses ,HttpServletRequest req , RedirectAttributes redir)throws Exception
		    {
	    	
		    	String UserId=(String)ses.getAttribute("Username");
				logger.info(new Date() +"Inside OtherItemAddEdit.htm "+UserId);
				try {	
				if (req.getParameter("item")!=null) {
					
							String itemid   = (String)req.getParameter("item");	
							String name = "ItemName"+itemid;
							String itemname = (String)req.getParameter(name);
							CHSSOtherItems item = new CHSSOtherItems();
						
							item.setOtherItemId(Integer.parseInt(itemid));
							item.setModifiedDate(sdtf.format(new Date()));
							item.setModifiedBy(UserId);
							item.setOtherItemName(itemname);
							int result = service.EditItem(item);
						 	if (result != 0) {
								redir.addAttribute("result", "Item Edited Successful");
							} else {
								redir.addAttribute("resultfail", "Item Edited UnSuccessful");
							}
							return "redirect:/OtherItems.htm";
					 	
					}else{
					
						String itemname = (String)req.getParameter("ItemName");
						
						CHSSOtherItems item = new CHSSOtherItems();
		
						item.setCreatedBy(UserId);
						item.setCreatedDate(sdtf.format(new Date()));
						item.setOtherItemName(itemname);
						item.setIsActive(1);
						int result = service.AddOtherItem(item);
					 	if (result != 0) {
							redir.addAttribute("result", "Item Added Successful");
						} else {
							redir.addAttribute("resultfail", "Item Added UnSuccessful");
						}
					 	return "redirect:/OtherItems.htm";
					}
					 
				}catch (Exception e) {
					redir.addAttribute("resultfail", "Internal Error!");
					e.printStackTrace();
					return "redirect:/OtherItems.htm";
				}
				
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
					String treatid = (String)req.getParameter("tratementname");
					System.out.println(treatid);
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
		    			redir.addAttribute("result", "Medicine Edited Successfully");
					} else {
						redir.addAttribute("resultfail", "Medicine Edited UnSuccessful");
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
		    			redir.addAttribute("result", "Medicine added successfully");
					} else {
						redir.addAttribute("resultfail", "Medicine added UnSuccessful");
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
				String treatid = (String)req.getParameter("Treatmentid");
				count = service.Checkduplicate( Medicinename ,treatid);
				
				 return json.toJson(count);
			}catch (Exception e){
				e.printStackTrace();
				 return json.toJson(count);
			}
		}
		
		@RequestMapping(value ="DuplicateOtherItem.htm",method=RequestMethod.GET)
		public @ResponseBody String CheckDuplicateOtherItems(HttpSession ses , HttpServletRequest req)throws Exception
		{
			int count =0;
			Gson json = new Gson();
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside CheckDuplicateOtherItems()"+UserId);
			try {
				String treatmentName = (String)req.getParameter("treatmentName");
				
				count = service.CheckduplicateItem( treatmentName );
				
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
			logger.info(new Date() +"Inside EmpRequestMsg.htm"+UserId);
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
					ho.setTo_empid(toeme);
					ho.setFrom_date(DateTimeFormatUtil.dateConversionSql(fromdate));
					ho.setTo_date(DateTimeFormatUtil.dateConversionSql(todate));
					ho.setStatus("A");
					ho.setLogin_type(logintype);
					ho.setApplied_date(DateTimeFormatUtil.dateConversionSql(DateApplied));
					ho.setCreatedby(UserId);
					ho.setCreatedate(sdtf.format(new Date()));
					ho.setIs_active(1);
					
					Object[] checkAlreadyPresentForSameEmpidAndSameDates = service.checkAlreadyPresentForSameEmpidAndSameDates(fromemp, toeme, fromdate, todate);
					
					if (fromemp.equalsIgnoreCase(toeme)) {
						redir.addAttribute("resultfail", "Both Employee Can Not Be Same");
					}else if(checkAlreadyPresentForSameEmpidAndSameDates!=null) {
						redir.addAttribute("resultfail", "Add Restricted Because Of Duplicancy");
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
					int updateRevokeInHandingOver = service.updateRevokeInHandingOver(EmpId, HandingOverId);
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
					
				}else { 
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
			
		@RequestMapping(value = "DoctorsMaster.htm" , method= {RequestMethod.GET,RequestMethod.POST})
		public String DoctorsMasters(HttpSession ses , HttpServletRequest req ,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String action = (String)req.getParameter("Action");
				
			
			if("EDITDOCRATE".equalsIgnoreCase(action)){
					
					String Rateid = (String)req.getParameter("DocRateid");
					String con1= "Consultation1"+Rateid;
					String con2= "Consultation2"+Rateid;
					String Consultation1 = (String)req.getParameter(con1);
					String Consultation2 = (String)req.getParameter(con2);
					
					CHSSDoctorRates  DocRate = new CHSSDoctorRates();
					
					DocRate.setDocRateId(Integer.parseInt(Rateid));				
					DocRate.setConsultation_1(Integer.parseInt(Consultation1));
					DocRate.setConsultation_2(Integer.parseInt(Consultation2));
					DocRate.setModifiedBy(UserId);
					DocRate.setModifiedDate(sdtf.format(new Date()));
					int result = service.EditDoctorMaster(DocRate);
					if (result != 0) {
		    			redir.addAttribute("result", "Doctor Details Updated");
					} else {
						redir.addAttribute("resultfail", "Doctor Details Failed to Update");
					}
					return "redirect:/DoctorsMaster.htm";
				}else {
					List<Object[]> doctorlist = service.GetDoctorList();
					req.setAttribute("doctorlist", doctorlist);
					return "Admin/CHSSDoctorsList";
				}		
			} catch (Exception e) {
				req.setAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "Admin/CHSSDoctorsList";
			}
			
		}                                
		
		@RequestMapping(value = "UnitMaster.htm",method= {RequestMethod.POST,RequestMethod.GET})
		public String LabMaster(HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside UnitMaster.htm "+UserId);
			try {
				String action =(String)req.getParameter("Action");
			
				if("EDIT".equalsIgnoreCase(action)) {
					String labmasterId = (String)req.getParameter("labmasterId");
					LabMaster lab = service.GetLabDetailsToEdit(Long.parseLong(labmasterId));
					List<Object[]> labslist=service.getLabsList();
					req.setAttribute("emplist", pisservice.GetAllEmployee());
					req.setAttribute("labslist", labslist);
					req.setAttribute("labdetails", lab);
					       return "Admin/LabMasterEdit";
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
		    			redir.addAttribute("result", "Unit details updated successfully");
					} else {
						redir.addAttribute("resultfail", "Unit details updated Unsuccessfull");
					}
					return "redirect:/UnitMaster.htm";
				}else {                                      
					Object[] labdetails = service.getLabDetails();
					String labmasterId =""+labdetails[0];
					LabMaster lab = service.GetLabDetailsToEdit(Long.parseLong(labmasterId));
					List<Object[]> labslist=service.getLabsList();
					req.setAttribute("emplist", pisservice.GetAllEmployee());
					req.setAttribute("labslist", labslist);
					req.setAttribute("labdetails", lab);
									
					   return "Admin/LabMasterEdit";
			           
				}
			} catch (Exception e) {
				req.setAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "Admin/LabMasterEdit";
			}
			
		}
		
		@RequestMapping(value = "OtherItemAmount.htm" , method = {RequestMethod.POST,RequestMethod.GET})
		public String OtherItem( Model model, HttpSession ses , HttpServletRequest req , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String tratementid =(String)req.getParameter("tratementid");
				
				  if(tratementid==null) {
					   Map md=model.asMap();
					   tratementid=(String)md.get("tratementid");	
				   }
				if(tratementid!=null) {
					List<Object[]> otheritem = service.OtherItems();			
					req.setAttribute("itemlist", otheritem);
					List<Object[]> list = service.GetOtherItemAmlountList(tratementid);
				
					req.setAttribute("tratementid", tratementid);
					req.setAttribute("list", list);
				}else {
				List<Object[]> otheritem = service.OtherItems();
				List<Object[]> list = service.GetOtherItemAmlountList("1");
				req.setAttribute("list", list);
				req.setAttribute("tratementid", "1");
				req.setAttribute("itemlist", otheritem);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "Admin/OtherItemAmount";
		}
		
		
		@RequestMapping(value = "AddOtherItemAmount.htm" , method = {RequestMethod.GET,RequestMethod.POST})
		public String AddOtherItemAmount(HttpSession ses  , HttpServletRequest req ,RedirectAttributes redir)throws Exception
		{

			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String action = (String)req.getParameter("Action");
				if("ADDAMT".equalsIgnoreCase(action)){
					String basicfrom = (String)req.getParameter("basicfrom");
					String basicto   = (String)req.getParameter("basicto");
					String adsamt    = (String)req.getParameter("admAmt");
					String treatid   = (String)req.getParameter("treateid");

					CHSSOtherPermitAmt other = new CHSSOtherPermitAmt();

					other.setOtherItemId(Integer.parseInt(treatid));
					other.setBasicFrom(Long.parseLong(basicfrom));
					other.setBasicTo(Long.parseLong(basicto));
					other.setItemPermitAmt(Integer.parseInt(adsamt));
					other.setIsActive(1);
					other.setCreatedBy(UserId);
					other.setCreatedDate(sdtf.format(new Date()));
					long result = service.AddOtherItemAmt(other);
					if (result != 0) {
						 redir.addAttribute("result", "Permit Amount Added Successfull");
					} else {
						 redir.addAttribute("resultfail", "Permit Amount Added Unsuccessfull");
					}
					 redir.addFlashAttribute("tratementid", treatid);
					List<Object[]> otheritem = service.OtherItems();			
					req.setAttribute("itemlist", otheritem);
					List<Object[]> list = service.GetOtherItemAmlountList(treatid);
					req.setAttribute("tratementid", treatid);
					req.setAttribute("list", list);
				}
				
				return "redirect:/OtherItemAmount.htm";
				
			} catch (Exception e) {
				 redir.addAttribute("resultfail", "Internal Error!");
				e.printStackTrace();
				return "redirect:/OtherItemAmount.htm";
			}
			
		}
		@RequestMapping(value = "EDITOtherAmt.htm" , method = RequestMethod.POST)
		public String EDITOtherAmt(HttpSession ses ,HttpServletRequest req,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String chssOtheramtid = (String)req.getParameter("chssOtheramtid");
				if(chssOtheramtid!=null) {
					String adm="admAmt1"+chssOtheramtid;
					String basicto = "basicto1"+chssOtheramtid;
					String admAmt1 = (String)req.getParameter(adm);
					String treatid = (String)req.getParameter("treateid");
					String basicto2 = (String)req.getParameter(basicto);
				
					long result = 0l;
					if(basicto2!=null) {
						 result = service.updateOtherItemAmt(chssOtheramtid ,admAmt1, UserId , basicto2);
					}else {
						 result = service.updateOtherAmt(chssOtheramtid ,admAmt1, UserId);
					}
					
					if (result != 0) {
						 redir.addAttribute("result", "Permit Amount Edited Successful");
					} else {
						 redir.addAttribute("resultfail", "Permit Amount Edited UnSuccessful");
					}
					 redir.addFlashAttribute("tratementid", treatid);
					 return "redirect:/OtherItemAmount.htm";
				}else {
					String chssother = (String) req.getParameter("otheritemid");
					String treatid = (String)req.getParameter("treateid");
					long result = 0l;
				
					result = service.DeleteOtherAmt(chssother ,UserId);
					
					
					if (result != 0) {
						 redir.addAttribute("result", "Permit Amount Deleted Successfully");
					} else {
						 redir.addAttribute("resultfail", "Permit Amount Failed to Delete");
					}
					 redir.addFlashAttribute("tratementid", treatid);
					 return "redirect:/OtherItemAmount.htm";
				}
			
			} catch (Exception e) {
				 redir.addAttribute("resultfail", "Internal Error!");
					e.printStackTrace();
					return "redirect:/OtherItemAmount.htm";
			}
			
		}
		
		@RequestMapping(value = "AdminReplyToReqMsg.htm" , method = {RequestMethod.GET, RequestMethod.POST})
		public String ReqMsgFromUserToAdmin(HttpSession ses ,HttpServletRequest req,RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");
		
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			try {
				String requestid = (String)req.getParameter("action");
				
				if(requestid!=null) {
					String response = "response"+requestid;
					String responsemsg = (String)req.getParameter(response);
					
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
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
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
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
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
		
		
		@RequestMapping(value ="DuplicateTest.htm",method=RequestMethod.GET)
		public @ResponseBody String CheckDuplicateTest(HttpSession ses , HttpServletRequest req)throws Exception
		{
			int count =0;
			Gson json = new Gson();
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside CheckDuplicateMedicine.htm()"+UserId);
			try {
				String testname = (String)req.getParameter("testName");
				
				count = service.CheckduplicateTest( testname );
				
				 return json.toJson(count);
			}catch (Exception e){
				e.printStackTrace();
				 return json.toJson(count);
			}
		}
		
		@RequestMapping(value = "Designation.htm" , method = {RequestMethod.POST,RequestMethod.GET})
		public String GetDesignation(HttpServletRequest req, HttpSession ses )throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +"Inside DoctorsMasters.htm "+UserId);
			
			try {			
				String action = (String)req.getParameter("action");
				if("ADD".equalsIgnoreCase(action)) {
					
					  return "Admin/DesignationAddEdit";
				}else if ("EDIT".equalsIgnoreCase(action)) {
					String desigid = (String)req.getParameter("desigid");
					EmployeeDesig desig =service.GetDesignationToEdit(Long.parseLong(desigid));
					req.setAttribute("desig", desig);
					  return "Admin/DesignationAddEdit";
				}else {
				  List<Object[]>  list = service.GetDesignation();
				  req.setAttribute("designation", list);				
			    return "Admin/Designation";
				}
			}catch (Exception e){
				e.printStackTrace();
				return "Admin/NotificationList";
			}
		}
		
		@RequestMapping(value = "DesignationAddEdit.htm" , method =RequestMethod.POST )
		public String DesgnationAddEdit (HttpServletRequest req, HttpSession ses , RedirectAttributes redir)throws Exception
		{
			String UserId = (String)ses.getAttribute("Username");			
			logger.info(new Date() +" Inside DesignationAddEdit.htm "+UserId);
			
			try {			
				String designationid = (String)req.getParameter("deisignationid");
				if(designationid!=null) {
					
					String code  = (String)req.getParameter("Designationcode");
					String name  = (String)req.getParameter("DesignationName");
					String limit = (String)req.getParameter("Designationlimit");
					EmployeeDesig desig = new EmployeeDesig();
					desig.setDesigId(Long.parseLong(designationid));
					desig.setDesigCode(code.toUpperCase());
					desig.setDesignation(name);
					desig.setDesigLimit(Long.parseLong(limit));
					long result =0; //service.EditDesignation(desig);
					if (result != 0) {
						 redir.addAttribute("result", "Designation Updated Successfully");
					} else {
						 redir.addAttribute("resultfail", "Designation Updated Unsuccessfull");
					}
					return "redirect:/Designation.htm";
					
				}else{
					String code  = (String)req.getParameter("Designationcode");
					String name  = (String)req.getParameter("DesignationName");
					String limit = (String)req.getParameter("Designationlimit");
					
					EmployeeDesig desig = new EmployeeDesig();
					desig.setDesigCode(code.toUpperCase());
					desig.setDesignation(name);
					desig.setDesigLimit(Long.parseLong(limit));
					
					long result =0; //service.AddDesignation(desig);
					if (result != 0) {
						 redir.addAttribute("result", "Designation Added Successfully");
					} else {
						 redir.addAttribute("resultfail", "Designation Added Unsuccessfull");
					}
					return "redirect:/Designation.htm";
				}				
				}catch (Exception e){
					e.printStackTrace();
					redir.addAttribute("resultfail", "Internal Error!");
					return "redirect:/Designation.htm";
				}
		}
		
	    @RequestMapping(value = "DesignationAddCheck.htm", method = RequestMethod.GET)
			  public @ResponseBody String DesignationAddCheck(HttpSession ses, HttpServletRequest req) throws Exception 
			  {
				String UserId=(String)ses.getAttribute("Username");
				Object[] DisDesc = null;
				logger.info(new Date() +"Inside DesignationAddCheck.htm "+UserId);
				try
				{	  
					String desigcode=req.getParameter("dcode");
					String designation=req.getParameter("dname");
					DisDesc =service.DesignationAddCheck(desigcode,designation);
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +"Inside DesignationAddCheck.htm "+UserId,e);
				}
				  Gson json = new Gson();
				  return json.toJson(DisDesc);           
				  
			}
		    
		    @RequestMapping(value = "DesignationEditCheck.htm", method = RequestMethod.GET)
			  public @ResponseBody String DesignationEditCheck(HttpSession ses, HttpServletRequest req) throws Exception 
			  {
				String UserId=(String)ses.getAttribute("Username");
				Object[] DisDesc = null;
				logger.info(new Date() +"Inside DesignationEditCheck.htm "+UserId);
				try
				{	  
					String desigcode=req.getParameter("dcode");
					String designation=req.getParameter("dname");
					String desigid=req.getParameter("desigid");
					DisDesc =service.DesignationEditCheck(desigcode,designation,desigid);
				}
				catch (Exception e) {
						e.printStackTrace(); logger.error(new Date() +"Inside DesignationEditCheck.htm "+UserId,e);
				}
				  Gson json = new Gson();
				  return json.toJson(DisDesc); 
				  
			}
		
}

package com.vts.ems.Admin.Controller;

import java.text.SimpleDateFormat;
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
import com.vts.ems.chss.controller.CHSSController;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestSub;
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
		    
		
		    
}

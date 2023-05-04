package com.vts.ems.pi.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.pi.model.PisAddressPerTrans;
import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pi.model.PisHometown;
import com.vts.ems.pi.model.PisHometownTrans;
import com.vts.ems.pi.model.PisMobileNumber;
import com.vts.ems.pi.model.PisMobileNumberTrans;
import com.vts.ems.pi.service.PIService;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;


@Controller
public class PIController {

	private static final Logger logger = LogManager.getLogger(PIController.class);
	
	@Autowired
	AdminService adservice;
	
	@Autowired
	Environment env;
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Autowired
	private PisService pisservice;
	
	@Autowired
	private PIService service;
	private final String formmoduleid="9";
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@RequestMapping(value = "PersonalIntimation.htm")
	public String PersonalIntimation(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
    	String LoginType=(String)ses.getAttribute("LoginType");
		String Username = (String) ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		logger.info(new Date() +"Inside PersonalIntimation.htm"+Username);		
		try {		
			ses.setAttribute("formmoduleid", formmoduleid);			
			ses.setAttribute("SidebarActive","PersonalIntimation_htm");
			
			String resaddressId = req.getParameter("resaddressId");
			String peraddressId = req.getParameter("peraddressId");
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			
			req.setAttribute("resAddress", service.ResAddressDetails(EmpId));	
			req.setAttribute("perAddress", service.PermanentAddressDetails(EmpId));
			
			req.setAttribute("EmployeeD", service.getEmpData(EmpId));
			
			String CEO = service.GetCEOEmpNo();
			List<String> PandAs = service.GetPandAAdminEmpNos();
			List<String> DGMs = service.GetDGMEmpNos();
			
			if(!DGMs.contains(EmpNo)) {
				req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
			}
			
			req.setAttribute("CEOEmpNos", CEO);
			req.setAttribute("PandAsEmpNos", PandAs);		
			req.setAttribute("DGMEmpNos", DGMs);
			
			req.setAttribute("CeoName", service.GetCeoName());
			req.setAttribute("PandAEmpName", service.GetPandAEmpName());
	
			if(resaddressId!=null) {
				String isApproval = req.getParameter("isApproval");
				if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
					ses.setAttribute("SidebarActive","AddressApprovals_htm");
				}
				req.setAttribute("isApproval", isApproval);
				req.setAttribute("ApprovalEmpData", service.ResAddressTransactionApprovalData(resaddressId));
				req.setAttribute("ResFormData", service.ResAddressFormData(resaddressId));
				req.setAttribute("Employee", service.getEmpData(EmpId));
                return "pi/ResAddressForm";
                            
			}
			
			else if(peraddressId!=null) {
				String isApproval = req.getParameter("isApproval");
				if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
					ses.setAttribute("SidebarActive","AddressApprovals_htm");
				}
				req.setAttribute("isApproval", isApproval);
				req.setAttribute("ApprovalEmpData", service.PerAddressTransactionApprovalData(peraddressId));
				req.setAttribute("PerFormData", service.PerAddressFormData(peraddressId));	
				req.setAttribute("Employee", service.getEmpData(EmpId));
				return "pi/PerAddressForm";
			}
								
			return "pi/PIAddressList";
		}catch (Exception e) {
			logger.error(new Date() +" Inside PersonalIntimation.htm"+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
		 
	 @RequestMapping(value ="PermanentAddEdit.htm" , method= {RequestMethod.GET,RequestMethod.POST})
	   public String PermanentAddEdit(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside PermanentAddEdit.htm "+Username);
	       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	       try {

	    	   String Action = (String) req.getParameter("Action");
	    	
	    	   	if("EDITPerAddress".equalsIgnoreCase(Action)) {
	    	   	
	    	   		AddressPer peraddress = service.getPerAddressData(req.getParameter("peraddressid"));
	    	   		
	    	   		List<Object[]> States = pisservice.getStates();
	    	   		 req.setAttribute("peraddress", peraddress);
	 	   		     req.setAttribute("States", States);
	    	   	      return "pi/PerAddressAddEdit";
	           }else{
	    	   		List<Object[]> States = pisservice.getStates();
	    	   		req.setAttribute("States", States);
	    	   	 return "pi/PerAddressAddEdit";
	    	   	}
	    	   	
		    } catch (Exception e) {
		    	 logger.error(new Date() +"Inside PermanentAddEdit.htm "+Username ,e);
		 	    e.printStackTrace();
		 	    return "static/Error";
		    }
	      
	   }
	   
	   @RequestMapping(value = "PermanentAddressAdd.htm" , method= RequestMethod.POST)
	   public String PermanentAddressAdd(HttpServletRequest req , HttpSession ses , RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside PermanentAddressAdd.htm "+Username);  
	       String Action = (String)req.getParameter("Action");
	       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	       String EmpNo = (String) ses.getAttribute("EmpNo");
	       try {
	    	   String state =(String)req.getParameter("state");
	    	   String city  =(String)req.getParameter("city");
	    	   String cityPin =(String)req.getParameter("cityPin");
	    	   String mobile =(String)req.getParameter("mobile");
	    	   String altMobile =(String)req.getParameter("altMobile");
	    	   String landineNo =(String)req.getParameter("landineNo");
	    	   String fromPer =(String)req.getParameter("fromPer");
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
	    	   peraddress.setEmpid(EmpId);
	    	   peraddress.setPer_addr(perAdd);
	    	   peraddress.setPerAdStatus("N");	
	    	   peraddress.setPisStatusCode("INI");
	    	   peraddress.setPisStatusCodeNext("INI");
	    	   
	    	   if("ADD".equalsIgnoreCase(Action)) {
	    		   peraddress.setIsActive(1);
	    		   peraddress.setCreatedBy(Username);
	        	   peraddress.setCreatedDate(sdtf.format(new Date()));
	        	  long result  =  pisservice.AddPerAddress(peraddress); 
	        	 
//	        	  Object[] toAddressId = service.PerToAddressId(EmpId);
//		        	
//	     	    	if(toAddressId!=null) {    	    		    	    		
//	     	    	long count = service.PerUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromPer) , toAddressId[0].toString());
//	     	    	}
	        	  
	        	    if(result>0) {
	        	    	
	        	    	PisAddressPerTrans transaction = PisAddressPerTrans.builder()	
								.address_per_id(peraddress.getAddress_per_id())
								.PisStatusCode(peraddress.getPisStatusCode())
								.ActionBy(EmpNo)
								.Remarks("")
								.ActionDate(sdtf.format(new Date()))
								.build();
                        service.AddressPerTransactionAdd(transaction);

	        	    	 redir.addAttribute("result", "Permanent Address Add Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Permanent Address Add Unsuccessful");	
	        	    }
	        	    redir.addAttribute("peraddressId", result);
	    	   }
	    	    
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside PermanentAddressAdd.htm "+Username,e);  
			   e.printStackTrace();
			  return "static/Error";
		  }  
	       return "redirect:/PersonalIntimation.htm";
	   }
	   
	   
	   @RequestMapping(value = "PermanentAddressEdit.htm" , method= RequestMethod.POST)
	   public String PermanentAddressEdit(HttpServletRequest req , HttpSession ses , RedirectAttributes redir)throws Exception{
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside PermanentAddressEdit.htm "+Username);  
	       String Action = (String)req.getParameter("Action");
	       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	       try {
	    	   String state =(String)req.getParameter("state");
	    	   String city  =(String)req.getParameter("city");
	    	   String cityPin =(String)req.getParameter("cityPin");
	    	   String mobile =(String)req.getParameter("mobile");
	    	   String altMobile =(String)req.getParameter("altMobile");
	    	   String landineNo =(String)req.getParameter("landineNo");
	    	   String fromPer =(String)req.getParameter("fromPer");
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
	    	   peraddress.setEmpid(EmpId);
	    	   peraddress.setPer_addr(perAdd);
	    	   
	    	 if("EDIT".equalsIgnoreCase(Action)) {
	    		   String addressid = (String)req.getParameter("addressId");
	    		   peraddress.setAddress_per_id(Long.parseLong(addressid));
	    		   peraddress.setModifiedBy(Username);
	        	   peraddress.setModifiedDate(sdtf.format(new Date()));
	        	   	        	
	        	   long result  =  pisservice.EditPerAddress(peraddress); 
	          	 
		       	    if(result>0) {
		       	    	 redir.addAttribute("result", "Permanent Address Edit Successfull");	
		       		} else {
		       			 redir.addAttribute("resultfail", "Permanent Address Edit Unsuccessful");	
		       	    }
		       	    redir.addFlashAttribute("Employee", EmpId);
	    	   }
	    	    
		   } catch (Exception e) {
			   logger.error(new Date() +"Inside PermanentAddressEdit.htm "+Username,e); 
			   e.printStackTrace();
			  return "static/Error";
		  }  
	       return "redirect:/PersonalIntimation.htm";
	   }
	   
	   @RequestMapping(value = "PerAddressFormSubmit.htm")
		public String PerAddressFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside PerAddressFormSubmit.htm"+Username);
			try {
				String perAddressId = req.getParameter("peraddressid").trim();
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				AddressPer address = service.PerAddressIntimated(perAddressId);
				String  pisStatusCode = address.getPisStatusCode();
				
				long count = service.PerAddressForward(perAddressId, Username, action,remarks,EmpNo,LoginType);
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") ) {
					if (count > 0) {
						redir.addAttribute("result", "Address application Sent for verification Successfully");
					} else {
						redir.addAttribute("resultfail", "Address application Sent for verification Unsuccessful");	
					}	
					return "redirect:/PersonalIntimation.htm";
				}
				else  
				{
					if (count > 0) {
						redir.addAttribute("result", "Address verification Successfull");
					} else {
						redir.addAttribute("resultfail", "Address verification Unsuccessful");	
					}	
					return "redirect:/IntimationApprovals.htm";
				}
				
			}catch (Exception e) {
				logger.error(new Date() +" Inside PerAddressFormSubmit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
	   
	   @RequestMapping(value = "PerAddrTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String PerAddrTransactionStatus(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside PerAddrTransactionStatus.htm "+Username);
			try {
				String peraddressid = req.getParameter("peraddressid");
				req.setAttribute("TransactionList", service.PerAddressTransactionList(peraddressid));				
				return "pi/PiTransactionStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside PerAddrTransactionStatus.htm "+Username, e);
				return "static/Error";
			}
		}
	   
	   @RequestMapping(value = "ResidentialAddEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		public String ResAddressAddEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{   
			String Username = (String) ses.getAttribute("Username");
			String Action =(String)req.getParameter("Action");
			String empid = (String)req.getParameter("empid");
			
			logger.info(new Date() +"Inside ResidentialAddEdit.htm "+Username); 
			try {
				if("ADD".equalsIgnoreCase(Action)){
					
					 List<Object[]> States = pisservice.getStates();					
		   		     req.setAttribute("States", States);
							
			}else if("EDIT".equalsIgnoreCase(Action)) {
				
				  String Addressid = (String)req.getParameter("resaddressid"); 	
				  AddressRes addres = pisservice.getResAddressData(Addressid);
				  List<Object[]> States = pisservice.getStates();
				  req.setAttribute("addres", addres);
				  req.setAttribute("States", States);
				  
			   
			}
				return "pi/ResAddressAddEdit";
			} catch (Exception e) {
				logger.error(new Date() +"Inside ResidentialAddEdit.htm "+Username,e); 
				e.printStackTrace();
				return "static/Error";
			}
			
			
		}
	 
	 @RequestMapping(value="ResidentialAddressAdd.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	   public String ResidentialAddressAdd(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception{
		  
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ResidentialAddressAdd.htm "+Username);  
	       String Action = (String)request.getParameter("Action");
	       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	       String EmpNo = ((String) ses.getAttribute("EmpNo")).toString();
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
				
				resadd.setEmpid(EmpId);
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
			    resadd.setResAdStatus("N");
			    resadd.setPisStatusCode("INI");
			    resadd.setPisStatusCodeNext("INI");
			    
				if("ADD".equalsIgnoreCase(Action)) {
					resadd.setIsActive(1);
					resadd.setCreatedBy(Username);
					resadd.setCreatedDate(sdtf.format(new Date()));
	
		        	long result  =  pisservice.AddResAddress(resadd); 
		        	
//		        	Object[] toAddressId = service.ResToAddressId(EmpId);
//		        	
//	     	    	if(toAddressId!=null) {    	    		    	    		
//	     	    	long count = service.ResUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromRes) , toAddressId[0].toString());
//	     	    	}
		        	    if(result>0) {
		        	    	
		        	    	PisAddressResTrans transaction = PisAddressResTrans.builder()	
									.address_res_id(resadd.getAddress_res_id())
									.PisStatusCode(resadd.getPisStatusCode())
									.ActionBy(EmpNo)
									.Remarks("")
									.ActionDate(sdtf.format(new Date()))
									.build();
                                  service.AddressResTransactionAdd(transaction);

		        	    	 redir.addAttribute("result", "Residential Address Add Successfull");	
		        		} else {
		        			 redir.addAttribute("resultfail", "Residential Address ADD Unsuccessful");	
		        	    }
		        	    redir.addAttribute("resaddressId", result);
				}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside ResidentialAddressAdd.htm "+Username ,e);  
				e.printStackTrace();
				return "static/Error";
			}
		   return "redirect:/PersonalIntimation.htm";
	   }
	 
	 @RequestMapping(value="ResidentialAddressEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
	   public String ResidentialAddressEdit(HttpServletRequest request , HttpSession ses ,RedirectAttributes redir)throws Exception{
		  
		   String Username = (String) ses.getAttribute("Username");
	       logger.info(new Date() +"Inside ResidentialAddressEdit.htm "+Username);  
	       String Action = (String)request.getParameter("Action");
	       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
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
				
				resadd.setEmpid(EmpId);
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
									
	        	  long result  =  pisservice.EditResAddress(resadd); 
	        	 
	        	    if(result>0) {
	        	    	
	        	    	 redir.addAttribute("result", "Residential Address Edit Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Residential Address Edit Unsuccessful");	
	        	    }
	        	    redir.addFlashAttribute("Employee", EmpId);
			}
				
			} catch (Exception e) {
				logger.error(new Date() +"Inside ResidentialAddressEdit.htm "+Username ,e);
				e.printStackTrace();
				 return "static/Error";
			}
		   return "redirect:/PersonalIntimation.htm";
	   }
	 
		@RequestMapping(value = "ResAddressFormSubmit.htm")
		public String ResAddressFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside ResAddressFormSubmit.htm"+Username);
			try {
				String resAddressId = req.getParameter("resaddressid");
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				AddressRes address = service.ResAddressIntimated(resAddressId);
				String  pisStatusCode = address.getPisStatusCode();
				
				long count = service.ResAddressForward(resAddressId, Username, action,remarks,EmpNo,LoginType);
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") ) {
					if (count > 0) {
						redir.addAttribute("result", "Address application Sent for verification Successfully");
					} else {
						redir.addAttribute("resultfail", "Address application Sent for verification Unsuccessful");	
					}	
					return "redirect:/PersonalIntimation.htm";
				}
				else  
				{
					if (count > 0) {
						redir.addAttribute("result", "Address verification Successfull");
					} else {
						redir.addAttribute("resultfail", "Address verification Unsuccessful");	
					}	
					return "redirect:/IntimationApprovals.htm";
				}
				
			}catch (Exception e) {
				logger.error(new Date() +" Inside ResAddressFormSubmit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
		@RequestMapping(value = "AddressFormDownload.htm")
		public void AddressFormDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");

			logger.info(new Date() +"Inside AddressFormDownload.htm "+UserId);
			
			try {	
				String resaddressId = req.getParameter("resaddressId");
				String peraddressId = req.getParameter("peraddressId");
				String pagePart =  req.getParameter("pagePart");
				
				String filename="";
				if(resaddressId!=null) {
					req.setAttribute("ApprovalEmpData", service.ResAddressTransactionApprovalData(resaddressId));
					req.setAttribute("ResFormData", service.ResAddressFormData(resaddressId));	
					filename="Address-Res";
				}else if(peraddressId!=null) {
					filename="Address-Per";
					req.setAttribute("ApprovalEmpData", service.PerAddressTransactionApprovalData(peraddressId));
					req.setAttribute("PerFormData", service.PerAddressFormData(peraddressId));	
				}
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("pagePart","3" );
				
				req.setAttribute("view_mode", req.getParameter("view_mode"));
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
		        req.getRequestDispatcher("/view/pi/ResAddressFormPrint.jsp").forward(req, customResponse);
//		        if(resaddressId!=null) {
//		        	req.getRequestDispatcher("/view/pi/AddressFormPrint.jsp").forward(req, customResponse);
//				}else if(peraddressId!=null) {
//					req.getRequestDispatcher("/view/pi/AddressFormPrint.jsp").forward(req, customResponse);
//				}
				
				String html = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
		         
		        res.setContentType("application/pdf");
		        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
		       
		       
		        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
		        
		        
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
		       	
			}
			catch (Exception e) {
				e.printStackTrace();  
				logger.error(new Date() +" Inside AddressFormDownload.htm "+UserId, e); 
			}

		}
		

		@RequestMapping(value = "IntimationApprovals.htm")
		public String AddressApprovals(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside IntimationApprovals.htm"+Username);		
			try {				
				
				ses.setAttribute("formmoduleid", formmoduleid);			
				ses.setAttribute("SidebarActive","IntimationApprovals_htm");	
				
//				req.setAttribute("ApprovalList", service.ResAddressApprovalsList(EmpNo, LoginType));
				req.setAttribute("ApprovalList", service.IntimationApprovalsList(EmpNo, LoginType));
				
				return "pi/IntimationApprovals";
			}catch (Exception e) {
				logger.error(new Date() +" Inside IntimationApprovals.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}	
		
//		ResAddrTransactionStatus
		
		@RequestMapping(value = "ResAddrTransactionStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String ResAddrTransactionStatus(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside ResAddrTransactionStatus.htm "+Username);
			try {
				String addressresid = req.getParameter("addressresid");
				req.setAttribute("TransactionList", service.ResAddressTransactionList(addressresid));				
				return "pi/PiTransactionStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ResAddrTransactionStatus.htm "+Username, e);
				return "static/Error";
			}
		}
		
		
		@RequestMapping(value = "PIHomeTownMobile.htm")
		public String PIHomeTown(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	    	String LoginType=(String)ses.getAttribute("LoginType");
			String Username = (String) ses.getAttribute("Username");
			String EmpNo = (String) ses.getAttribute("EmpNo").toString().trim();
			logger.info(new Date() +"Inside PIHomeTownMobile.htm"+Username);		
			try {		
				ses.setAttribute("formmoduleid", formmoduleid);			
				ses.setAttribute("SidebarActive","PIHomeTownMobile_htm");
				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
						
				String CEO = service.GetCEOEmpNo();
				List<String> PandAs = service.GetPandAAdminEmpNos();
				List<String> DGMs = service.GetDGMEmpNos();
				List<String> DHs = service.GetDHEmpNos();
				List<String> GHs = service.GetGHEmpNos();
				
				
                req.setAttribute("CeoName", service.GetCeoName());
                req.setAttribute("CEOEmpNos", CEO);
				
				req.setAttribute("PandAEmpName", service.GetPandAEmpName());
				req.setAttribute("PandAsEmpNos", PandAs);
				
				if(!DGMs.contains(EmpNo)) {
					req.setAttribute("DGMEmpName", service.GetEmpDGMEmpName(EmpNo));
				}
				req.setAttribute("DGMEmpNos", DGMs);
				
				req.setAttribute("DivisionHeadName", service.GetDivisionHeadName(EmpNo));
				req.setAttribute("DivisionHeadEmpNos", DHs);
				
				
				req.setAttribute("GroupHeadName", service.GetGroupHeadName(EmpNo));
				req.setAttribute("GroupHeadEmpNos", GHs);
				
				req.setAttribute("EmployeeD", service.getEmpData(EmpId));
				
				req.setAttribute("MobileDetails", service.MobileNumberDetails(EmpNo));
				req.setAttribute("HometownDetails", service.HometownDetails(EmpNo));
				
				req.setAttribute("ApprovalCount", service.HometownApprovalCount(EmpNo).intValue());
				return "pi/HomeTownAndMobile";
			}catch (Exception e) {
				logger.error(new Date() +" Inside PIHomeTownMobile.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}		
		 
		 @RequestMapping(value ="MobileNumberAddEdit.htm" , method= {RequestMethod.GET,RequestMethod.POST})
		 public String MobileNumberAddEdit(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
			   String Username = (String) ses.getAttribute("Username");
		       logger.info(new Date() +"Inside MobileNumberAddEdit.htm "+Username);
		       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		       try {

		    	   String Action = (String) req.getParameter("Action");
		    	
		    	   	if("EDIT".equalsIgnoreCase(Action)) {
		    	   		
		    	   		req.setAttribute("mobile",  service.getMobileNumberData(req.getParameter("mobilenumberid"))	);
		    	   		   				 		
		    	   	      return "pi/MobileNumberAddEdit";
		           }else{
                        
		    	   	 return "pi/MobileNumberAddEdit";
		    	   	}
		    	   	
			    } catch (Exception e) {
			    	 logger.error(new Date() +"Inside MobileNumberAddEdit.htm "+Username ,e);
			 	    e.printStackTrace();
			 	    return "static/Error";
			    }
		      
		}
		 
		@RequestMapping(value="MobileNumberAdd.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		public String MobileNumberAdd(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception{
			  
			   String Username = (String) ses.getAttribute("Username");
		       logger.info(new Date() +"Inside MobileNumberAdd.htm "+Username);  
		       String Action = (String)request.getParameter("Action");
		       String EmpNo = (String) ses.getAttribute("EmpNo");
			   try {

					String fromRes = request.getParameter("fromRes");
					String mobileNumber = request.getParameter("mobile").trim().replaceAll(" +", " ");
					String altMobile = request.getParameter("altMobile").trim().replaceAll(" +", " ");
														
					
					long mobileInt = 1;
					long altMobileInt = 1;	

					if (!"".equalsIgnoreCase(mobileNumber)) {
						mobileInt = Long.parseLong(mobileNumber);
					}

					if (!"".equalsIgnoreCase(altMobile)) {
						altMobileInt = Long.parseLong(altMobile);
					}
					
					PisMobileNumber mobile = new PisMobileNumber();
					
					mobile.setEmpNo(EmpNo);
					mobile.setMobileNumber(mobileNumber);
					mobile.setAltMobileNumber(altMobile);
					mobile.setMobileFrom(DateTimeFormatUtil.dateConversionSql(fromRes));
					mobile.setMobileStatus("N");
					mobile.setPisStatusCode("INI");
					mobile.setPisStatusCodeNext("INI");
								    
					if("ADD".equalsIgnoreCase(Action)) {
						mobile.setIsActive(1);
						mobile.setCreatedBy(Username);
						mobile.setCreatedDate(sdtf.format(new Date()));
		
			        	long result  =  service.addMobileNumber(mobile);
			        	

			        	    if(result>0) {
			        	    	
			        	    	PisMobileNumberTrans transaction = PisMobileNumberTrans.builder()
				                           .MobileNumberId(mobile.getMobileNumberId())
				                           .PisStatusCode(mobile.getPisStatusCode())
				                           .ActionBy(EmpNo)
				                           .Remarks("")
				                           .ActionDate(sdtf.format(new Date()))
				                           .build();
			        	    	service.MobileNumberTransactionAdd(transaction);
			        	    	
			        	    	 redir.addAttribute("result", "Mobile Number Added Successfully");	
			        		} else {
			        			 redir.addAttribute("resultfail", "Mobile Number Add Unsuccessful");	
			        	    }
			        	    redir.addAttribute("mobileNumberId", result);
					}
					
				} catch (Exception e) {
					logger.error(new Date() +"Inside MobileNumberAdd.htm "+Username ,e);  
					e.printStackTrace();
					return "static/Error";
				}
			   return "redirect:/MobileNumberPreview.htm";
		} 
		
		@RequestMapping(value="MobileNumberEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		public String MobileNumberEdit(HttpServletRequest request , HttpSession ses ,  RedirectAttributes redir)throws Exception{
			  
			   String Username = (String) ses.getAttribute("Username");
		       logger.info(new Date() +"Inside MobileNumberEdit.htm "+Username);  
		       String Action = (String)request.getParameter("Action");
		       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			   try {

					String mobileNumber = request.getParameter("mobile").trim().replaceAll(" +", " ");
					String altMobile = request.getParameter("altMobile").trim().replaceAll(" +", " ");
					String fromRes = request.getParameter("fromRes");
																	
					long mobileInt = 1;
					long altMobileInt = 1;	

					if (!"".equalsIgnoreCase(mobileNumber)) {
						mobileInt = Long.parseLong(mobileNumber);
					}

					if (!"".equalsIgnoreCase(altMobile)) {
						altMobileInt = Long.parseLong(altMobile);
					}
					
					PisMobileNumber mobile = new PisMobileNumber();
					
					mobile.setMobileNumber(mobileNumber);
					mobile.setAltMobileNumber(altMobile);
					mobile.setMobileFrom(DateTimeFormatUtil.dateConversionSql(fromRes));
								    
					if("EDIT".equalsIgnoreCase(Action)) {
						String mobileNumberId = (String)request.getParameter("mobileNumberId");
						mobile.setMobileNumberId(Long.parseLong(mobileNumberId));
						mobile.setModifiedBy(Username);
						mobile.setModifiedDate(sdtf.format(new Date()));
		
			        	long result  =  service.EditMobileNumber(mobile);
			        	

			        	    if(result>0) {
			        	    	 redir.addAttribute("result", "Mobile Number Edited Successfully");	
			        		} else {
			        			 redir.addAttribute("resultfail", "Mobile Number Edit Unsuccessful");	
			        	    }
			        	    redir.addAttribute("mobileNumberId", result);
					}
					
				} catch (Exception e) {
					logger.error(new Date() +"Inside MobileNumberEdit.htm "+Username ,e);  
					e.printStackTrace();
					return "static/Error";
				}
			   return "redirect:/PIHomeTownMobile.htm";
		}
		
		@RequestMapping(value = "MobileNumberTransStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String MobileNumberTransStatus(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside MobileNumberTransStatus.htm "+Username);
			try {
				String mobilenumberid = req.getParameter("mobilenumberid").trim();
				req.setAttribute("TransactionList", service.MobileTransactionList(mobilenumberid)) ;				
				return "pi/PiTransactionStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside MobileNumberTransStatus.htm "+Username, e);
				return "static/Error";
			}
		}
		
		@RequestMapping(value = "MobileNumberPreview.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String MobileNumberPreview(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	    	String LoginType=(String)ses.getAttribute("LoginType");
			String Username = (String) ses.getAttribute("Username");
			String EmpNo = (String) ses.getAttribute("EmpNo");
			logger.info(new Date() +"Inside MobileNumberPreview.htm "+Username);
			try {
				String mobileNumberId = req.getParameter("mobileNumberId").trim();
					
					req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
					if(mobileNumberId!=null) {
						String isApproval = req.getParameter("isApproval");
						if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
							ses.setAttribute("SidebarActive","MobileApprovals_htm");
						}
						req.setAttribute("isApproval", isApproval);
						req.setAttribute("MobFormData", service.MobileFormData(mobileNumberId));
						req.setAttribute("ApprovalEmpData", service.MobileTransactionApprovalData(mobileNumberId));
						req.setAttribute("Employee", service.getEmpData(EmpId));
		              		                            
					}
					
					return "pi/MobileNumberForm";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside MobileNumberPreview.htm "+Username, e);
				return "static/Error";
			}
		}
		
		@RequestMapping(value = "MobileFormSubmit.htm")
		public String MobileFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside MobileFormSubmit.htm"+Username);
			try {
				String mobileNumberId = req.getParameter("mobileNumberId").trim();
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
		
				PisMobileNumber mobile = service.getMobileDataById(Long.parseLong(mobileNumberId));
				String  pisStatusCode = mobile.getPisStatusCode();
				
				
				long count = service.MobileNumberForward(mobileNumberId, Username, action, remarks, EmpNo, LoginType);
								
				PisMobileNumber mobile2 = service.getMobileDataById(Long.parseLong(mobileNumberId));
				String  pisStatusCode2 = mobile2.getPisStatusCode();
				String pisStatusCodeNext2 = mobile2.getPisStatusCodeNext();
				
				if(pisStatusCode2.equalsIgnoreCase("VPA") && pisStatusCodeNext2.equalsIgnoreCase("VPA")) {									
					service.UpdateEmployeeMobileNumber(mobile2.getMobileNumber().trim(), mobile2.getAltMobileNumber().trim(), mobile2.getEmpNo());
				}
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") ) {
					if (count > 0) {
						redir.addAttribute("result", "Mobile Form Sent for verification Successfully");
					} else {
						redir.addAttribute("resultfail", "Mobile Form Sent for verification Unsuccessful");	
					}	
					return "redirect:/PIHomeTownMobile.htm";
				}
				else  
				{
					if (count > 0) {
						redir.addAttribute("result", "Address verification Successfull");
					} else {
						redir.addAttribute("resultfail", "Address verification Unsuccessful");	
					}	
					return "redirect:/IntimationApprovals.htm";
				}
				
			}catch (Exception e) {
				logger.error(new Date() +" Inside MobileFormSubmit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}
		
//		@RequestMapping(value = "MobileApprovals.htm")
//		public String MobileApprovals(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
//		{
//			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
//			String EmpNo = (String) ses.getAttribute("EmpNo");
//	    	String LoginType=(String)ses.getAttribute("LoginType");
//			String Username = (String) ses.getAttribute("Username");
//			logger.info(new Date() +"Inside MobileApprovals.htm.htm"+Username);		
//			try {				
//				
//				ses.setAttribute("formmoduleid", formmoduleid);			
//				ses.setAttribute("SidebarActive","MobileApprovals_htm");	
//				
//				req.setAttribute("MobApprovalList", service.MobileApprovalsList(EmpNo, LoginType));
//				
//				return "pi/MobileApproval";
//			}catch (Exception e) {
//				logger.error(new Date() +" Inside MobileApprovals.htm"+Username, e);
//				e.printStackTrace();	
//				return "static/Error";
//			}
//			
//		}
		
		@RequestMapping(value = "MobileFormDownload.htm")
		public void MobileNumberFormDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");

			logger.info(new Date() +"Inside MobileFormDownload.htm "+UserId);
			
			try {	
				String mobileNumberId = req.getParameter("mobilenumberId");
				String pagePart =  req.getParameter("pagePart");
				
				String filename="";
				if(mobileNumberId!=null) {
					req.setAttribute("ApprovalEmpData", service.MobileTransactionApprovalData(mobileNumberId));
					req.setAttribute("MobFormData", service.MobileFormData(mobileNumberId));	
					filename="Mobile_Number";
				}
				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("pagePart","3" );
				
				req.setAttribute("view_mode", req.getParameter("view_mode"));
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
		        req.getRequestDispatcher("/view/pi/MobileNumberFormPrint.jsp").forward(req, customResponse);

				String html = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
		         
		        res.setContentType("application/pdf");
		        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
		       
		       
		        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
		        
		        
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
		       	
			}
			catch (Exception e) {
				e.printStackTrace();  
				logger.error(new Date() +" Inside MobileFormDownload.htm "+UserId, e); 
			}

	    }

		 @RequestMapping(value ="HomeTownAddEdit.htm" , method= {RequestMethod.GET,RequestMethod.POST})
		 public String HomeTownAddEdit(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
			   String Username = (String) ses.getAttribute("Username");
		       logger.info(new Date() +"Inside HomeTownAddEdit.htm "+Username);
		       String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		       try {

		    	   String Action = req.getParameter("Action");
		    	
		    	   	if("EDITHometown".equalsIgnoreCase(Action)) {
		    	 		    	   		
		    	   		List<Object[]> States = pisservice.getStates();
		 	   		    req.setAttribute("States", States);
		 	   		    req.setAttribute("Hometown", service.getHometownById(Long.parseLong(req.getParameter("hometownid"))));
		    	   	      return "pi/HomeTownAddEdit";
		           }else{
		    	   		List<Object[]> States = pisservice.getStates();
		    	   		req.setAttribute("States", States);
		    	   	 return "pi/HomeTownAddEdit";
		    	   	}
		    	   	
			    } catch (Exception e) {
			    	 logger.error(new Date() +"Inside HomeTownAddEdit.htm "+Username ,e);
			 	    e.printStackTrace();
			 	    return "static/Error";
			    }
		      
		}
		
		 @RequestMapping(value="HomeTownAdd.htm" , method= {RequestMethod.POST,RequestMethod.GET})
			public String HometownAdd(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
				  
				   String Username = (String) ses.getAttribute("Username");
			       logger.info(new Date() +"Inside HomeTownAdd.htm "+Username);  
			       String Action = req.getParameter("Action");
			       String EmpNo = (String) ses.getAttribute("EmpNo").toString();
				   try {
						
						
						PisHometown hometown = new PisHometown();
						
						hometown.setEmpNo(EmpNo);
						hometown.setHometown(req.getParameter("hometown").trim());
						hometown.setNearestRailwayStation(req.getParameter("nearestRailwayStation").trim());
						hometown.setState(req.getParameter("state"));
						hometown.setHometownStatus("N");
						hometown.setPisStatusCode("INI");
						hometown.setPisStatusCodeNext("INI");
															    
						if("ADD".equalsIgnoreCase(Action)) {
							hometown.setIsActive(1);
							hometown.setCreatedBy(Username);
							hometown.setCreatedDate(sdtf.format(new Date()));
			
				        	long result  =  service.addHometown(hometown);
				        	

				        	    if(result>0) {
				        	    	
				        	    	PisHometownTrans transaction = PisHometownTrans.builder()
					                           .HometownId(hometown.getHometownId())
					                           .PisStatusCode(hometown.getPisStatusCode())
					                           .ActionBy(EmpNo)
					                           .Remarks("")
					                           .ActionDate(sdtf.format(new Date()))
					                           .build();
				        	    	service.HometownTransactionAdd(transaction);
				        	    	
				        	    	 redir.addAttribute("result", "Hometown Added Successfully");	
				        		} else {
				        			 redir.addAttribute("resultfail", "Hometown Add Unsuccessful");	
				        	    }
				        	    redir.addAttribute("hometownId", result);
						}
						
					} catch (Exception e) {
						logger.error(new Date() +"Inside HomeTownAdd.htm "+Username ,e);  
						e.printStackTrace();
						return "static/Error";
					}
				   return "redirect:/HometownPreview.htm";
		}  
		 
		 @RequestMapping(value="HomeTownEdit.htm" , method= {RequestMethod.POST,RequestMethod.GET})
		  public String HometownEdit(HttpServletRequest req , HttpSession ses ,  RedirectAttributes redir)throws Exception{
				  
				   String Username = (String) ses.getAttribute("Username");
			       logger.info(new Date() +"Inside HomeTownEdit.htm "+Username);  
			       String Action = (String)req.getParameter("Action");
				   try {

					   PisHometown hometown = new PisHometown();
						
						hometown.setHometown(req.getParameter("hometown").trim());
						hometown.setNearestRailwayStation(req.getParameter("nearestRailwayStation").trim());
						hometown.setState(req.getParameter("state"));
									    
						if("EDIT".equalsIgnoreCase(Action)) {
							String hometownId = req.getParameter("hometownId");
							
							hometown.setHometownId(Long.parseLong(hometownId));
							hometown.setModifiedBy(Username);
							hometown.setModifiedDate(sdtf.format(new Date()));
			
				        	long result  =  service.EditHometown(hometown);
				        	

				        	    if(result>0) {
				        	    	 redir.addAttribute("result", "Hometown Edited Successfully");	
				        		} else {
				        			 redir.addAttribute("resultfail", "Hometown Edit Unsuccessful");	
				        	    }
				        	    redir.addAttribute("hometownId", result);
						}
						
					} catch (Exception e) {
						logger.error(new Date() +"Inside HomeTownEdit.htm "+Username ,e);  
						e.printStackTrace();
						return "static/Error";
					}
				   return "redirect:/PIHomeTownMobile.htm";
		}
		 
		@RequestMapping(value = "HometownPreview.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String HometownPreview(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
			{
				String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		    	String LoginType=(String)ses.getAttribute("LoginType");
				String Username = (String) ses.getAttribute("Username");
				String EmpNo = (String) ses.getAttribute("EmpNo");
				logger.info(new Date() +"Inside HometownPreview.htm "+Username);
				try {
					String hometownId = req.getParameter("hometownId");	
					req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
					
					if(hometownId!=null) {
						String isApproval = req.getParameter("isApproval");
						if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
							req.setAttribute("SidebarActive","HometownApprovals_htm");
						}
						req.setAttribute("isApproval", isApproval);
						req.setAttribute("HomFormData", service.HometownFormData(hometownId));
						req.setAttribute("ApprovalEmpData", service.HometownTransactionApprovalData(hometownId));
						req.setAttribute("Employee", service.getEmpData(EmpId));
		              		                            
					}
					return "pi/HomeTownForm";
				}catch (Exception e) {
					e.printStackTrace();
					logger.error(new Date() +" Inside HometownPreview.htm "+Username, e);
					return "static/Error";
				} finally {
				}
	    }
		
		@RequestMapping(value = "HometownFormSubmit.htm")
		public String HometownFormSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
			
			String Username = (String) ses.getAttribute("Username");
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			logger.info(new Date() +"Inside HometownFormSubmit.htm"+Username);
			try {
				String hometownId = req.getParameter("hometownId").trim();
				String action = req.getParameter("Action");
				String remarks = req.getParameter("remarks");
				
				PisHometown hometown = service.getHometownById(Long.parseLong(hometownId) );
				String pisStatusCode = hometown.getPisStatusCode();
				
								
				long count = service.HometownForward(hometownId, Username, action,remarks,EmpNo,LoginType);
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RGI") || pisStatusCode.equalsIgnoreCase("RDI") || 
				   pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE")) {
					if (count > 0) {
						redir.addAttribute("result", "Hometown application Sent for verification Successfully");
					} else {
						redir.addAttribute("resultfail", "Hometown application Sent for verification Unsuccessful");	
					}	
					return "redirect:/PIHomeTownMobile.htm";
				}
				else  
				{
					if (count > 0) {
						redir.addAttribute("result", "Hometown verification Successfull");
					} else {
						redir.addAttribute("resultfail", "Hometown verification Unsuccessful");	
					}	
					return "redirect:/IntimationApprovals.htm";
				}
				
			}catch (Exception e) {
				logger.error(new Date() +" Inside HometownFormSubmit.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
	    }
		
//		@RequestMapping(value = "HometownApprovals.htm")
//		public String HometownApprovals(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
//		{
//			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
//			String EmpNo = (String) ses.getAttribute("EmpNo");
//	    	String LoginType=(String)ses.getAttribute("LoginType");
//			String Username = (String) ses.getAttribute("Username");
//			logger.info(new Date() +"Inside HometownApprovals.htm"+Username);		
//			try {				
//				
//				ses.setAttribute("formmoduleid", formmoduleid);			
//				ses.setAttribute("SidebarActive","HometownApprovals_htm");	
//				
//				req.setAttribute("HomApprovalList", service.HometownApprovalsList(EmpNo, LoginType));
//				
//				return "pi/HometownApproval";
//			}catch (Exception e) {
//				logger.error(new Date() +" Inside HometownApprovals.htm"+Username, e);
//				e.printStackTrace();	
//				return "static/Error";
//			}
//			
//		}
		
		@RequestMapping(value = "HometownTransStatus.htm" , method={RequestMethod.POST,RequestMethod.GET})
		public String HometownTransStatus(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside HometownTransStatus.htm "+Username);
			try {
				String hometownid = req.getParameter("hometownid").trim();
				req.setAttribute("TransactionList", service.HometownTransactionList(hometownid)) ;				
				return "pi/PiTransactionStatus";
			}catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside HometownTransStatus.htm "+Username, e);
				return "static/Error";
			}
		}
		
		@RequestMapping(value = "HometownFormDownload.htm")
		public void HometownFormDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");

			logger.info(new Date() +"Inside HometownFormDownload.htm "+UserId);
			
			try {	
				String hometownId = req.getParameter("hometownId").trim();
				String pagePart =  req.getParameter("pagePart");
				
				String filename="";
				if(hometownId!=null) {
					req.setAttribute("ApprovalEmpData", service.HometownTransactionApprovalData(hometownId));
					req.setAttribute("HomFormData", service.HometownFormData(hometownId));	
					filename="Hometown";
				}
				
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("pagePart","3" );
				
				req.setAttribute("view_mode", req.getParameter("view_mode"));
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
		        req.getRequestDispatcher("/view/pi/HometownFormPrint.jsp").forward(req, customResponse);

				String html = customResponse.getOutput();        
		        
		        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
		         
		        res.setContentType("application/pdf");
		        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
		       
		       
		        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
		        
		        
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
		       	
			}
			catch (Exception e) {
				e.printStackTrace();  
				logger.error(new Date() +" Inside HometownFormDownload.htm "+UserId, e); 
			}

	    }
}

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

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Admin.Service.AdminService;


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
		logger.info(new Date() +"Inside PersonalIntimation.htm"+Username);		
		try {		
			ses.setAttribute("formmoduleid", formmoduleid);			
			ses.setAttribute("SidebarActive","PersonalIntimation_htm");
			req.setAttribute("resAddress", service.ResAddressDetails(EmpId));	
			req.setAttribute("perAddress", service.PermanentAddressDetails(EmpId));
			String resaddressId = req.getParameter("resaddressId");
			String peraddressId = req.getParameter("peraddressId");
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			if(resaddressId!=null) {
				String isApproval = req.getParameter("isApproval");
				if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
					ses.setAttribute("SidebarActive","AddressApprovals_htm");
				}
				req.setAttribute("isApproval", isApproval);
				req.setAttribute("ResFormData", service.ResAddressFormData(resaddressId));				
                return "pi/ResAddressForm";
			}else if(peraddressId!=null) {
				String isApproval = req.getParameter("isApproval");
				if(isApproval!=null && isApproval.equalsIgnoreCase("Y")) {
					ses.setAttribute("SidebarActive","AddressApprovals_htm");
				}
				req.setAttribute("isApproval", isApproval);
				req.setAttribute("PerFormData", service.PerAddressFormData(peraddressId));				
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
	    	   
	    	   if("ADD".equalsIgnoreCase(Action)) {
	    		   peraddress.setIsActive(1);
	    		   peraddress.setCreatedBy(Username);
	        	   peraddress.setCreatedDate(sdtf.format(new Date()));
	        	  long result  =  pisservice.AddPerAddress(peraddress); 
	        	 
	        	    if(result>0) {
	        	    	 redir.addAttribute("result", "Parmanent Address Add Successfull");	
	        		} else {
	        			 redir.addAttribute("resultfail", "Parmanent Address Add Unsuccessful");	
	        	    }
	        	    redir.addFlashAttribute("Employee", EmpId);
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
		       	    	 redir.addAttribute("result", "Parmanent Address Edit Successfull");	
		       		} else {
		       			 redir.addAttribute("resultfail", "Parmanent Address Edit Unsuccessful");	
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
		        	Object[] toAddressId = service.ResToAddressId(EmpId);
		        	
	     	    	if(toAddressId!=null) {    	    		    	    		
	     	    	long count = service.ResUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromRes) , toAddressId[0].toString());
	     	    	}
		        	    if(result>0) {
		        	    	 redir.addAttribute("result", "Residential Address Add Successfull");	
		        		} else {
		        			 redir.addAttribute("resultfail", "Residential Address ADD Unsuccessful");	
		        	    }
		        	    redir.addFlashAttribute("resaddressId", result);
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
				if(pisStatusCode.equalsIgnoreCase("INI")) {
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
						redir.addAttribute("result", "Address verification Successfully");
					} else {
						redir.addAttribute("resultfail", "Address verification Unsuccessful");	
					}	
					return "redirect:/AddressApprovals.htm";
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
					req.setAttribute("ResFormData", service.ResAddressFormData(resaddressId));	
					filename="Address-Res";
				}else if(peraddressId!=null) {
					filename="Address-Per";
					req.setAttribute("PerFormData", service.PerAddressFormData(peraddressId));	
				}
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				req.setAttribute("pagePart","3" );
				
				req.setAttribute("view_mode", req.getParameter("view_mode"));
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/pi/AddressFormPrint.jsp").forward(req, customResponse);
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
		

		@RequestMapping(value = "PIHomeTown.htm")
		public String PIHomeTown(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
	    	String LoginType=(String)ses.getAttribute("LoginType");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside PIHomeTown.htm"+Username);		
			try {		
				ses.setAttribute("formmoduleid", formmoduleid);			
				ses.setAttribute("SidebarActive","PIHomeTown_htm");	
				ses.setAttribute("LoginType", LoginType);
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				return "pi/HomeTown";
			}catch (Exception e) {
				logger.error(new Date() +" Inside PIHomeTown.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}	
		
		

		@RequestMapping(value = "AddressApprovals.htm")
		public String AddressApprovals(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
			String EmpNo = (String) ses.getAttribute("EmpNo");
	    	String LoginType=(String)ses.getAttribute("LoginType");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside AddressApprovals.htm"+Username);		
			try {				
				
				ses.setAttribute("formmoduleid", formmoduleid);			
				ses.setAttribute("SidebarActive","AddressApprovals_htm");	
				System.out.println("EmpNo = "+EmpNo);
				req.setAttribute("ApprovalList", service.ResAddressApprovalsList(EmpNo, LoginType));
				
				return "pi/ResAddressApproval";
			}catch (Exception e) {
				logger.error(new Date() +" Inside AddressApprovals.htm"+Username, e);
				e.printStackTrace();	
				return "static/Error";
			}
			
		}	
		
}
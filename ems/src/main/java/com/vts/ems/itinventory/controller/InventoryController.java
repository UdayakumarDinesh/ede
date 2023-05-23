package com.vts.ems.itinventory.controller;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.font.FontProvider;
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryHistory;
import com.vts.ems.itinventory.model.ITInventoryConfiguredHistory;
import com.vts.ems.itinventory.model.ITInventoryConfigured;

import com.vts.ems.itinventory.service.InventoryService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;

@Controller
public class InventoryController {
	
	private static final Logger logger = LogManager.getLogger(InventoryController.class);
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	AdminService adminservice;
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Autowired
	InventoryService service;

    public static final String formmoduleid="32";
	@RequestMapping(value = "ITInventoryDashboard.htm")
		public String ITInventoryDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
		    ses.setAttribute("formmoduleid", "32"); 
		    ses.setAttribute("SidebarActive", "ITInventoryDashboard_htm");
			String UserId=(String)ses.getAttribute("Username");
			String LoginType=(String)ses.getAttribute("LoginType");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			List<Object[]> InventoryDashBoardCount=null;
			logger.info(new Date()+"Inside ITInventoryDashboard.htm "+UserId);
			try {
				
				 List<Object[]> admindashboard = adminservice.HeaderSchedulesList("12" ,LoginType);
				 
				 String empno=req.getParameter("empNo");
				 String Year = req.getParameter("DeclarationYear");
				    LocalDate today= LocalDate.now();
					
					if(Year== null) {
						String start ="";
						
						start = String.valueOf(today.getYear());
						Year=start;
					}
					
					
					if(empno==null && LoginType.equalsIgnoreCase("A") ) {
					   empno="0";
					   InventoryDashBoardCount=service.getInventoryDashboardCount(empno,Year);
					   req.setAttribute("empno", empno);
					}
					else if(empno!=null && LoginType.equalsIgnoreCase("A") ){
						
						   InventoryDashBoardCount=service.getInventoryDashboardCount(empno,Year);
						    req.setAttribute("empno", empno);
					}	    
					else if(EmpNo!=null && !LoginType.equalsIgnoreCase("A")){ 
						InventoryDashBoardCount=service.getInventoryDashboardCount(EmpNo,Year);
					    req.setAttribute("empno", EmpNo);
					}
					
				req.setAttribute("InventoryDashBoardCount", InventoryDashBoardCount);
				List<Object[]> EmpList=service.getEmployeeList();
				req.setAttribute("EmployeeList", EmpList);
				req.setAttribute("DeclarationYear", Year);
				req.setAttribute("LoginType", LoginType);
				req.setAttribute("dashboard", admindashboard);
						
				 return "itinventory/inventorydashboard";
						 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ITInventoryDashboard.htm "+UserId, e); 
			    return "static/Error"; 
			}
	}

	
	 @RequestMapping(value = "Inventory.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ITAsset(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			ses.setAttribute("SidebarActive","Inventory_htm");	
			ses.setAttribute("formmoduleid", formmoduleid);
			logger.info(new Date() +"Inside Inventory.htm "+UserId);
			List<Object[]> InventoryQuantityList=null;
			try {
				
				LocalDate today= LocalDate.now();
				String DeclarationYear=String.valueOf(today.getYear());
				String declarationYear="";
				declarationYear=String.valueOf(today.getYear()-1);
				InventoryQuantityList=service.getInventoryQuantityList(EmpNo,DeclarationYear );
				
				if(InventoryQuantityList!=null && InventoryQuantityList.size()>0) {
					
					System.out.println(" DeclarationYear   :"+DeclarationYear);
					InventoryQuantityList=service.getInventoryQuantityList(EmpNo,DeclarationYear );
				}else {
					
					InventoryQuantityList=service.getInventoryQuantityList(EmpNo,declarationYear);
					System.out.println(" PreviousYear   :"+declarationYear);
				}
                
                req.setAttribute("InventoryQuantityList",InventoryQuantityList );
				
				return "itinventory/inventory";
				
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside Inventory.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 @RequestMapping(value = "ITInventoryQuantityAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ITInventorydesktoplaptopAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside ITInventoryQuantityAddSubmit.htm "+UserId);
		
			try {
				ITInventory inventory=new ITInventory(); 
				LocalDate today= LocalDate.now();
				String DeclarationYear=String.valueOf(today.getYear());
				inventory.setDeclarationYear(DeclarationYear);
				inventory.setDeclaredBy(EmpNo);
				inventory.setDesktop(Integer.parseInt(req.getParameter("Desktop")));
				inventory.setDesktopIntendedBy(req.getParameter("DesktopIntendedBy"));
				inventory.setDesktopRemarks(req.getParameter("DesktopRemarks"));
				inventory.setLaptop(Integer.parseInt(req.getParameter("Laptop")));
				inventory.setLaptopIntendedBy(req.getParameter("LaptopIntendedBy"));
				inventory.setLaptopRemarks(req.getParameter("LaptopRemarks"));
				inventory.setUSBPendrive(Integer.parseInt(req.getParameter("USBPendrive")));
				inventory.setUSBPendriveIntendedBy(req.getParameter("USBPendriveIntendedBy"));
				inventory.setUSBPendriveRemarks(req.getParameter("USBPendriveRemarks"));
				inventory.setPrinter(Integer.parseInt(req.getParameter("Printer")));
				inventory.setPrinterIntendedBy(req.getParameter("PrinterIntendedBy"));
				inventory.setPrinterRemarks(req.getParameter("PrinterRemarks"));
				inventory.setTelephone(Integer.parseInt(req.getParameter("Telephone")));
				inventory.setTelephoneIntendedBy(req.getParameter("TelephoneIntendedBy"));
				inventory.setTelephoneRemarks(req.getParameter("TelephoneRemarks"));
				inventory.setFaxMachine(Integer.parseInt(req.getParameter("FaxMachine")));
				inventory.setFaxMachineIntendedBy(req.getParameter("FaxMachineIntendedBy"));
				inventory.setFaxMachineRemarks(req.getParameter("FaxMachineRemarks"));
				inventory.setScanner(Integer.parseInt(req.getParameter("Scanner")));
				inventory.setScannerIntendedBy(req.getParameter("ScannerIntendedBy"));
				inventory.setScannerRemarks(req.getParameter("ScannerRemarks"));
				inventory.setXeroxMachine(Integer.parseInt(req.getParameter("XeroxMachine")));
				inventory.setXeroxMachineIntendedBy(req.getParameter("XeroxMachineIntendedBy"));
				inventory.setXeroxMachineRemarks(req.getParameter("XeroxMachineRemarks"));
				inventory.setMiscellaneous(Integer.parseInt(req.getParameter("Miscellaneous")));
				inventory.setMiscellaneousIntendedBy(req.getParameter("MiscellaneousIntendedBy"));
				inventory.setMiscellaneousRemarks(req.getParameter("MiscellaneousRemarks"));
				inventory.setStatus("I");
				inventory.setAllowDecl("Y");
				inventory.setCreatedBy(UserId);
				inventory.setCreatedDate(sdf1.format(new Date()));
				inventory.setIsActive(1);
				long save=service.InventoryQtyAdd(inventory);
				 if(save!=0) {
					 
			  		redir.addAttribute("result", "Inventory Quantity Added Successfully");
			  	}
			  	 else {
			  		redir.addAttribute("resultfail", "Inventory Quantity Add Unsuccessful");
			  		}
				 
				return "redirect:/Inventory.htm";
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ITInventoryQuantityAddSubmit.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 @RequestMapping(value = "QuantityEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ITInventoryQuantityAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside QuantityEditSubmit.htm "+UserId);
			try {
				
			   
				String Itinventoryid=req.getParameter("Itinventoryid");
				String DeclarationYear=req.getParameter("DeclarationYear");
				ITInventory inventory=new ITInventory();
				inventory.setItInventoryId(Long.parseLong(Itinventoryid));
				inventory.setDeclarationYear(DeclarationYear.toString().substring(0,4));
				inventory.setDesktop(Integer.parseInt(req.getParameter("Desktop")));
				inventory.setDesktopIntendedBy(req.getParameter("DesktopIntendedBy"));
				inventory.setDesktopRemarks(req.getParameter("DesktopRemarks"));
				inventory.setLaptop(Integer.parseInt(req.getParameter("Laptop")));
				inventory.setLaptopIntendedBy(req.getParameter("LaptopIntendedBy"));
				inventory.setLaptopRemarks(req.getParameter("LaptopRemarks"));
			    inventory.setUSBPendrive(Integer.parseInt(req.getParameter("USBPendrive")));
			    inventory.setUSBPendriveIntendedBy(req.getParameter("USBPendriveIntendedBy"));
				inventory.setUSBPendriveRemarks(req.getParameter("USBPendriveRemarks"));
				inventory.setPrinter(Integer.parseInt(req.getParameter("Printer")));
				inventory.setPrinterIntendedBy(req.getParameter("PrinterIntendedBy"));
				inventory.setPrinterRemarks(req.getParameter("PrinterRemarks"));
				inventory.setTelephone(Integer.parseInt(req.getParameter("Telephone")));
				inventory.setTelephoneIntendedBy(req.getParameter("TelephoneIntendedBy"));
				inventory.setTelephoneRemarks(req.getParameter("TelephoneRemarks"));
				inventory.setFaxMachine(Integer.parseInt(req.getParameter("FaxMachine")));
				inventory.setFaxMachineIntendedBy(req.getParameter("FaxMachineIntendedBy"));
				inventory.setFaxMachineRemarks(req.getParameter("FaxMachineRemarks"));
				inventory.setScanner(Integer.parseInt(req.getParameter("Scanner")));
				inventory.setScannerIntendedBy(req.getParameter("ScannerIntendedBy"));
				inventory.setScannerRemarks(req.getParameter("ScannerRemarks"));
				inventory.setXeroxMachine(Integer.parseInt(req.getParameter("XeroxMachine")));
				inventory.setXeroxMachineIntendedBy(req.getParameter("XeroxMachineIntendedBy"));
				inventory.setXeroxMachineRemarks(req.getParameter("XeroxMachineremarks"));
				inventory.setMiscellaneous(Integer.parseInt(req.getParameter("Miscellaneous")));
				inventory.setMiscellaneousIntendedBy(req.getParameter("MiscellaneousIntendedBy"));
				inventory.setMiscellaneousRemarks(req.getParameter("Miscellaneousremarks"));
				inventory.setModifiedBy(UserId);
				inventory.setModifiedDate(sdf1.format(new Date()));
				long update=service.QuantityUpdate(inventory);
				
				if(update!=0)
				 {
			  	   redir.addAttribute("result", "Inventory Quantity Updated Successfully");
			  	}
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Quantity Update Unsuccessful");
			  		  }
				return "redirect:/Inventory.htm";
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside QuantityEditSubmit.htm "+UserId, e);
				return "static/Error";
			}
		
			
		}
	 
	 @RequestMapping(value = "InventoryConfigure.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String InventoryDetials(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryConfigure.htm "+UserId);
			try {
				
				String split=req.getParameter("Itinventoryid");
				if(split!=null && !split.equals("null")) {
					 String arr[]=split.split("/");
				      String inventoryid=arr[0];
				      String ItemType=arr[1];
				     
				      
			    List<Object[]> inventoryconfig=service.getInventoryConfiguration(EmpNo);
			    req.setAttribute("inventoryid",inventoryid );
				req.setAttribute("itemtype",ItemType);
				req.setAttribute("inventoryconfig",inventoryconfig);
				}
				
				return "itinventory/inventoryconfigure";
				
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside InventoryConfigure.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 @RequestMapping(value = "InventoryConfigureAddSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String InventoryDetailsAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryConfigureAddSubmit.htm "+UserId);
			try {
				LocalDate today= LocalDate.now();
				String ConfiguredYear=String.valueOf(today.getYear());
				ITInventoryConfigured  details = new  ITInventoryConfigured();
				details.setConfiguredBy(EmpNo);
				
				details.setItInventoryId(Long.parseLong(req.getParameter("Itinventoryid")));
				details.setItemType(req.getParameter("ItemType"));
				details.setConnectionType(req.getParameter("ConnectionType"));
				details.setCPU(req.getParameter("CPU"));
				details.setMonitor(req.getParameter("Monitor"));
				details.setRAM(req.getParameter("RAM"));
				details.setAdditionalRAM(req.getParameter("AdditionalRAM"));
				details.setKeyboard(req.getParameter("Keyboard"));
				details.setMouse(req.getParameter("Mouse"));
				details.setExternalharddisk(req.getParameter("Externalharddisk"));
			    details.setExtraInternalharddisk(req.getParameter("ExtraInternalhardisk"));
			    details.setOffice(req.getParameter("Office"));
			    details.setOS(req.getParameter("OS"));
			    details.setPDF(req.getParameter("PDF"));
			    details.setBrowser(req.getParameter("Browser"));
			    details.setKavach(req.getParameter("Kavach"));
				details.setCreatedBy(UserId);
				details.setCreatedDate(sdf1.format(new Date()));
				details.setIsActive(1);
				
				long save=service.InventoryDetailsAddSubmit(details);
				
				if(save!=0) {
			  		redir.addAttribute("result", "Inventory Details Added Successfully");
			  	 }else {
			  			redir.addAttribute("resultfail", "Inventory Details Add Unsuccessful");
			  	}
				 List<Object[]> inventoryconfig=service.getInventoryConfiguration(EmpNo); 
				 redir.addFlashAttribute("inventoryconfig",inventoryconfig);
				 redir.addFlashAttribute("itemtype",req.getParameter("ItemType"));
				 redir.addFlashAttribute("inventoryid",req.getParameter("Itinventoryid"));
				return "redirect:/InventoryConfigure.htm";
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside InventoryConfigureAddSubmit.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 
	 @RequestMapping(value = "InventoryConfigureEdit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String InventoryConfigureEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryConfigureEdit.htm "+UserId);
			try {
				
				String split=req.getParameter("InventoryConfigureId");
				if(split!=null && !split.equals("null")) {
					 String arr[]=split.split("/");
				      String inventoryconfigid=arr[0];
				      String ItemType=arr[1];
				      
				      
				ITInventoryConfigured configure=service.getInventoryConfigId(Long.parseLong(inventoryconfigid));
				
			    List<Object[]> inventoryconfig=service.getInventoryConfiguration(EmpNo);
			    req.setAttribute("configure",configure);
				req.setAttribute("itemtype",ItemType);
				req.setAttribute("inventoryconfig",inventoryconfig);
				
				}
			    
				return "itinventory/inventoryconfigure";
				
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside InventoryConfigureEdit.htm "+UserId, e);
				return "static/Error";
			}
		}
	 
	 @RequestMapping(value = "InventoryConfigureEditSubmit.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String InventoryConfigureEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryConfigureEditSubmit.htm "+UserId);
			try {
				
				String ConfiguredYear=req.getParameter("ConfiguredYear");
				ITInventoryConfigured details= new ITInventoryConfigured();
				details.setInventoryConfigureId(Long.parseLong(req.getParameter("InventoryConfigureId")));
				
				details.setItemType(req.getParameter("ItemType"));
				details.setConnectionType(req.getParameter("ConnectionType"));
				details.setCPU(req.getParameter("CPU"));
				details.setMonitor(req.getParameter("Monitor"));
				details.setRAM(req.getParameter("RAM"));
				details.setAdditionalRAM(req.getParameter("AdditionalRAM"));
				details.setKeyboard(req.getParameter("Keyboard"));
				details.setMouse(req.getParameter("Mouse"));
				details.setExternalharddisk(req.getParameter("Externalharddisk"));
			    details.setExtraInternalharddisk(req.getParameter("ExtraInternalhardisk"));
			    details.setOffice(req.getParameter("Office"));
			    details.setOS(req.getParameter("OS"));
			    details.setPDF(req.getParameter("PDF"));
			    details.setBrowser(req.getParameter("Browser"));
			    details.setKavach(req.getParameter("Kavach"));
			    details.setModifiedBy(UserId);
			    details.setModifiedDate(sdf1.format(new Date()));
				long update=service.ConfigureUpdate(details);
				 if(update!=0)
				 {
			  	   redir.addAttribute("result", "Inventory Configuration Updated Successfully");
			  	}
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Configuration Update Unsuccessful");
			   }
				 
				List<Object[]> inventoryconfig=service.getInventoryConfiguration(EmpNo); 
				redir.addFlashAttribute("inventoryconfig",inventoryconfig); 
				redir.addFlashAttribute("itemtype",req.getParameter("ItemType"));
				redir.addFlashAttribute("inventoryid",req.getParameter("Itinventoryid"));
				return "redirect:/InventoryConfigure.htm";
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside InventoryConfigureEditSubmit.htm "+UserId, e);
				return "static/Error";
			}
		
			
		}
	 
	 @RequestMapping(value = "InventoryFormPreview.htm", method=RequestMethod.GET)
		public void InventoryFormDownload(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryFormPreview.htm "+UserId);
			List<Object[]> InventoryQtyList=null;
			try {
				    LocalDate today= LocalDate.now();
			        String DeclarationYear=String.valueOf(today.getYear());
			        String declarationYear="";
					declarationYear=String.valueOf(today.getYear()-1);
					
					declarationYear=String.valueOf(today.getYear()-1);
					InventoryQtyList=service.getInventoryQtylist(EmpNo,DeclarationYear);
					
					if(InventoryQtyList!=null && InventoryQtyList.size()>0) {
						req.setAttribute("inventoryqty", service.getInventoryQtylist(EmpNo,DeclarationYear));
						
					}else {
						System.out.println(" declarationYear   :"+declarationYear);
						req.setAttribute("inventoryqty", service.getInventoryQtylist(EmpNo,declarationYear));
						
					}
					
				    req.setAttribute("inventoryconfigure", service.getInventoryConfiguration(EmpNo));
				    req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
				    String path = req.getServletContext().getRealPath("/view/temp");
				    CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
					req.getRequestDispatcher("/view/itinventory/inventorydownload.jsp").forward(req, customResponse);
					String html = customResponse.getOutput();
					byte[] data = html.getBytes();
					InputStream fis1 = new ByteArrayInputStream(data);
					PdfDocument pdfDoc = new PdfDocument(new PdfWriter(path + "/inventorydetails.pdf"));
					pdfDoc.setTagged();
					Document document = new Document(pdfDoc, PageSize.LEGAL);
					document.setMargins(50, 100, 150, 50);
					ConverterProperties converterProperties = new ConverterProperties();
					FontProvider dfp = new DefaultFontProvider(true, true, true);
					converterProperties.setFontProvider(dfp);
					HtmlConverter.convertToPdf(fis1, pdfDoc, converterProperties);
					/*
					 * document.close(); pdfDoc.close(); fis1.close();
					 */
					res.setContentType("application/pdf");
					res.setHeader("Content-disposition", "inline;filename=inventorydetails.pdf");
					File f = new File(path + "/inventorydetails.pdf");
					FileInputStream fis = new FileInputStream(f);
					DataOutputStream os = new DataOutputStream(res.getOutputStream());
					res.setHeader("Content-Length", String.valueOf(f.length()));
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) >= 0) {
						os.write(buffer, 0, len);
					}
					os.close();
					fis.close();
					Path pathOfFile2 = Paths.get(path + "/inventorydetails.pdf");
					Files.delete(pathOfFile2);

				} catch (Exception e) {
					logger.error(new Date() + " Getting Error From  InventoryFormPreview" + UserId, e);
				}
			
		}

	 
	 @RequestMapping(value = "InventoryFormDownload.htm",method=RequestMethod.GET)
		public void CHSSFormEmpDownload(HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryFormDownload.htm "+UserId);
			
			try {
				
				String split= req.getParameter("inventoryhistoryid");
				   if(split!=null && !split.equals("null")) {
					   String arr[]=split.split("/");
					     String invntryhistoryid=arr[0]; 
						  String ITInventoryId=arr[1];
						  
				
						
				req.setAttribute("inventoryqty", service.getApprovedForm(invntryhistoryid));
			    req.setAttribute("inventoryconfigure", service.getInventoryConfigureApprovedlist(invntryhistoryid));
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
				String filename="Inventory-"+ITInventoryId.toString().trim().replace("/", "-");
				String path=req.getServletContext().getRealPath("/view/temp");
				req.setAttribute("path",path);
		        
		        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
				req.getRequestDispatcher("/view/itinventory/inventorydownload.jsp").forward(req, customResponse);
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
				   
			}
			catch (Exception e) {
				e.printStackTrace();  
				logger.error(new Date() +" Inside InventoryFormDownload.htm "+UserId, e); 
			}

		}
		
	 @RequestMapping(value = "InventoryView.htm", method ={RequestMethod.GET,RequestMethod.POST})
	  public String InventoryPreview(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
	  { 
		  String UserId=(String)ses.getAttribute("Username"); 
		  String EmpNo =ses.getAttribute("EmpNo").toString();
		  String LoginType=ses.getAttribute("LoginType").toString();
		  ses.setAttribute("SidebarActive","InventoryView_htm");	
		  ses.setAttribute("formmoduleid", formmoduleid);
		  logger.info(new Date() +"Inside InventoryView.htm "+UserId); 
		  try {
			  
			  
			    String DeclarationYear=req.getParameter("declarationyear");
                req.setAttribute("inventoryqty", service.getInventoryQtylist(EmpNo,DeclarationYear));
			    req.setAttribute("inventoryconfigure", service.getInventoryConfiguration(EmpNo));
			    req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			    req.setAttribute("LoginType", LoginType);
			    req.setAttribute("isApproval","Y");
	
				
			    return "itinventory/inventorypreview";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryView.htm "+UserId, e); 
		 return "static/Error"; 
		 }
		
	  
	  }

	 
	  @RequestMapping(value = "InventoryDetailsForward.htm", method ={RequestMethod.GET,RequestMethod.POST})
		  public String InventoryDetailsForward(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
		  { 
			  String UserId=(String)ses.getAttribute("Username"); 
			  String EmpNo =ses.getAttribute("EmpNo").toString();
			  logger.info(new Date() +"Inside InventoryDetailsForward.htm "+UserId); 
			  try {
				    LocalDate today= LocalDate.now();
			        String DeclarationYear=String.valueOf(today.getYear());
				  
				  ITInventory inventory=new ITInventory();
				  inventory.setItInventoryId(Long.parseLong(req.getParameter("inventoryid01")));
				  inventory.setStatus("F");
				  inventory.setERemarks(req.getParameter("Remarks"));
		          inventory.setForwardedDate(sdf1.format(new Date()));
		          inventory.setDeclarationYear(DeclarationYear);
		          long save=service.ForwardDetails(inventory,UserId,EmpNo);
		          
		          if(save!=0){
		        	  redir.addAttribute("result", "Inventory Details Forwarded Successfully");
			  	   }
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Details Forward Unsuccessful");
			  		}
		          
		          return "redirect:/Inventory.htm";
		          
		 }catch (Exception e) { 
			 e.printStackTrace(); 
			 logger.error(new Date() +" Inside InventoryDetailsForward.htm "+UserId, e); 
			 return "static/Error"; 
			 }
	}
	  
	  
	  
	  
	  
	  @RequestMapping(value = "InventoryDetailsDeclared.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String InventoryDetailsForwarded(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			ses.setAttribute("SidebarActive","InventoryDetailsDeclared_htm");	
			ses.setAttribute("formmoduleid", formmoduleid);
			logger.info(new Date() +"Inside InventoryDetailsDeclared.htm "+UserId);
			try {
				
				
				     req.setAttribute("InventoryDeclaredList", service.getInventoryDeclaredList());
				     return "itinventory/inventoryDeclared";
				
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside InventoryDetailsDeclared.htm "+UserId, e);
				return "static/Error";
			}
		} 
	  
	  
	  @RequestMapping(value = "InventoryDeclaredView.htm", method ={RequestMethod.GET,RequestMethod.POST})
	  public String InventoryForwardedPreview(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
	  { 
		  String UserId=(String)ses.getAttribute("Username"); 
		 
		  String EmpName=ses.getAttribute("EmpName").toString();
		  String LoginType=ses.getAttribute("LoginType").toString();
		  logger.info(new Date() +"Inside InventoryDeclaredView.htm "+UserId); 
		  try {
			    String ITInventoryId=req.getParameter("inventoryid");
			    
			    
			    req.setAttribute("inventoryqty", service.getInventoryDeclaredListPreview(ITInventoryId));
			    req.setAttribute("inventoryconfigure", service.getInventoryConfigure(ITInventoryId));
			    req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			    req.setAttribute("LoginType", LoginType);
			    req.setAttribute("EmpName", EmpName);
			    return "itinventory/inventorypreview";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryDeclaredView.htm "+UserId, e); 
		 return "static/Error"; 
		 }
		  
	  }
		
		  @RequestMapping(value = "InventoryDetailsReturn.htm", method ={RequestMethod.GET,RequestMethod.POST})
		  public String InventoryDetailsReturn(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
		  { 
			  String UserId=(String)ses.getAttribute("Username"); 
			  String EmpNo =ses.getAttribute("EmpNo").toString();
			  String LoginType=ses.getAttribute("LoginType").toString();
			  logger.info(new Date() +"Inside InventoryDetailsReturn.htm "+UserId); 
			  try {
				    ITInventory inventory=new ITInventory();
				   String split=req.getParameter("inventoryid");
				   if(split!=null && !split.equals("null")) {
					   String arr[]=split.split("/");
					   String itid=arr[0]; 
					   String eno=arr[1];
					   String declarationyear=arr[2];
					   System.out.println("declyear-"+declarationyear);
						  
				  
				   inventory.setItInventoryId(Long.parseLong(itid));
				   inventory.setARemarks(req.getParameter("remarks"));
				   inventory.setStatus("R");
				   inventory.setReturnedBy(EmpNo);
				   
				   long save=service.inventoryDetailsReturn(inventory,EmpNo,eno,declarationyear);
				   if(save!=0){
			        	  redir.addAttribute("result", "Inventory Details Returned Successfully");
				  	   }
				  	else {
				  			redir.addAttribute("resultfail", "Inventory Details Return Unsuccessful");
				  		}
				   }
				  return "redirect:/InventoryDetailsDeclared.htm";
		          
		 }catch (Exception e) { 
			 e.printStackTrace(); 
			 logger.error(new Date() +" Inside InventoryDetailsReturn.htm "+UserId, e); 
			 return "static/Error"; 
			 }
		}
		  
	  
	 
	  @RequestMapping(value = "InventoryDetailsApprove.htm", method ={RequestMethod.GET,RequestMethod.POST})
	  public String InventoryDetailsApprove(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
	  { 
		  String UserId=(String)ses.getAttribute("Username"); 
		  String EmpNo =ses.getAttribute("EmpNo").toString();
		  String LoginType=ses.getAttribute("LoginType").toString();
		  logger.info(new Date() +"Inside InventoryDetailsApprove.htm "+UserId); 
		  try {
			  
			  ITInventory inventory = new ITInventory();
			  
			   ITInventoryHistory Aprrove=new ITInventoryHistory();
			   String split=req.getParameter("inventoryid");
			   if(split!=null && !split.equals("null")) {
				   String arr[]=split.split("/");
				     String itid=arr[0]; 
					  String eno=arr[1];
					 
					
                inventory.setItInventoryId(Long.parseLong(itid));
			    inventory.setStatus("A");
			    inventory.setAllowDecl("N");
			    inventory.setApprovedDate(sdf1.format(new Date()));
			    
				Aprrove.setItInventoryId(Long.parseLong(itid));
				Aprrove.setDesktop(Integer.parseInt(req.getParameter("Desktop")));
				Aprrove.setDesktopIntendedBy(req.getParameter("DesktopIntndBy"));
				Aprrove.setDesktopRemarks(req.getParameter("DesktopRemark"));
				Aprrove.setLaptop(Integer.parseInt(req.getParameter("Laptop")));
				Aprrove.setLaptopIntendedBy(req.getParameter("LaptopIntndBy"));
				Aprrove.setLaptopRemarks(req.getParameter("LaptopRemark"));
				Aprrove.setUSBPendrive(Integer.parseInt(req.getParameter("USBPendrive")));
				Aprrove.setUSBPendriveIntendedBy(req.getParameter("USBPendriveIntndBy"));
				Aprrove.setUSBPendriveRemarks(req.getParameter("USBPendriveRemark"));
				Aprrove.setPrinter(Integer.parseInt(req.getParameter("Printer")));
				Aprrove.setPrinterIntendedBy(req.getParameter("PrinterIntndBy"));
				Aprrove.setPrinterRemarks(req.getParameter("PrinterRemark"));
				Aprrove.setTelephone(Integer.parseInt(req.getParameter("Telephone")));
				Aprrove.setTelephoneIntendedBy(req.getParameter("TelephoneIntndBy"));
				Aprrove.setTelephoneRemarks(req.getParameter("TelephoneRemark"));
				Aprrove.setFaxMachine(Integer.parseInt(req.getParameter("Faxmachine")));
				Aprrove.setFaxMachineIntendedBy(req.getParameter("FaxmachineIntndBy"));
				Aprrove.setFaxMachineRemarks(req.getParameter("FaxmachineRemark"));
				Aprrove.setScanner(Integer.parseInt(req.getParameter("Scanner")));
				Aprrove.setScannerIntendedBy(req.getParameter("ScannerIntndBy"));
				Aprrove.setScannerRemarks(req.getParameter("ScannerRemark"));
				Aprrove.setXeroxMachine(Integer.parseInt(req.getParameter("Xeroxmachine")));
				Aprrove.setXeroxMachineIntendedBy(req.getParameter("XeroxmachineIntndBy"));
				Aprrove.setXeroxMachineRemarks(req.getParameter("XeroxmachineRemark"));
				Aprrove.setMiscellaneous(Integer.parseInt(req.getParameter("Miscellaneuos")));
				Aprrove.setMiscellaneousIntendedBy(req.getParameter("MiscellaneuosIntndBy"));
				Aprrove.setMiscellaneousRemarks(req.getParameter("MiscellaneuosRemark"));
				Aprrove.setStatus("A");
				Aprrove.setDeclaredBy(eno);
				Aprrove.setDeclarationYear(req.getParameter("DeclarationYear"));
				Aprrove.setForwardedDate(req.getParameter("ForwardedDate"));
				Aprrove.setApprovedBy(EmpNo);
				Aprrove.setApprovedDate(sdf1.format(new Date())); 
				Aprrove.setCreatedBy(UserId);
				Aprrove.setCreatedDate(sdf1.format(new Date()));
				Aprrove.setIsActive(1);
				

				
				List<Object[]> configlist=service.getInventoryConfigure(itid);
			long save=0;
				
				long InventoryHistoryId = service.MaxOfInventoryHistoryId();
				
				
			if(configlist.size()>0) {
				for(Object[] obj:configlist) {
					
				
					ITInventoryConfiguredHistory config=new ITInventoryConfiguredHistory();
					config.setConfiguredBy(obj[17].toString());
					config.setItInventoryHistoryId(InventoryHistoryId+1);
					config.setItInventoryId(Long.parseLong(obj[16].toString()));
					config.setItemType(obj[1].toString());
					config.setConnectionType(obj[2].toString());
					config.setCPU(obj[3].toString());
					config.setMonitor(obj[4].toString());
					config.setRAM(obj[5].toString());
					config.setAdditionalRAM(obj[6].toString());
					config.setKeyboard(obj[7].toString());
					config.setMouse(obj[8].toString());
					config.setExternalharddisk(obj[9].toString());
					config.setExtraInternalharddisk(obj[10].toString());
					config.setOffice(obj[11].toString());
					config.setOS(obj[12].toString());
					config.setPDF(obj[13].toString());
					config.setBrowser(obj[14].toString());
					config.setKavach(obj[15].toString());
					config.setCreatedBy(UserId);
					config.setCreatedDate(sdf1.format(new Date()));
					config.setIsActive(1);
					save=service.inventoryDetailsConfigApprove(Aprrove,inventory,config,EmpNo,eno,UserId);
				}
				long notification =service.sendNotification(eno,EmpNo,UserId);
			}
			else {
				
				save=service.inventoryDetailsApprove(Aprrove,inventory,EmpNo,eno,UserId);
				long notification =service.sendNotification(eno,EmpNo,UserId);
			}
				
				
				//long save=service.inventoryDetailsApprove(Aprrove,inventory,config,EmpNo,eno);
				
				 if(save!=0){
		        	  redir.addAttribute("result", "Inventory Details Approved Successfully");
			  	   }
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Details Approve Unsuccessful");
			  		}
			   }
			  return "redirect:/InventoryDetailsApproved.htm";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryDetailsApprove.htm "+UserId, e); 
		 return "static/Error"; 
		 }
	 }
	  
	  @RequestMapping(value = "InventoryDetailsApproved.htm", method ={RequestMethod.GET,RequestMethod.POST})
	  public String InventoryDetailsApproved(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
	  { 
		  String UserId=(String)ses.getAttribute("Username"); 
		  String EmpNo =ses.getAttribute("EmpNo").toString();
		  String LoginType=ses.getAttribute("LoginType").toString();
		  ses.setAttribute("SidebarActive","InventoryDetailsApproved_htm");	
		  ses.setAttribute("formmoduleid", formmoduleid);
		  logger.info(new Date() +"Inside InventoryDetailsApproved.htm "+UserId); 
		  try {
			   
			    String Year = req.getParameter("DeclarationYear");
			    LocalDate today= LocalDate.now();
				
				if(Year== null) {
					String start ="";
					
					start = String.valueOf(today.getYear());
					Year=start;
				}
				
				req.setAttribute("InventoryApprovedList", service.getInventoryApprovedList(Year,EmpNo,LoginType));
			    req.setAttribute("DeclarationYear", Year);
			    req.setAttribute("LoginType", LoginType);
			    return "itinventory/inventoryapprovedlist";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryDetailsApproved.htm "+UserId, e); 
		 return "static/Error"; 
		 }
	 } 
}




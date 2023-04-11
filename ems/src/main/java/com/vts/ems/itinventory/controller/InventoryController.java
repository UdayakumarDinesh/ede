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
import com.vts.ems.itinventory.model.ITInventoryConfigure;

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

    public static final String formmoduleid="12";
	@RequestMapping(value = "ITInventoryDashboard.htm")
		public String ITInventoryDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date()+"Inside ITInventoryDashboard.htm "+UserId);
			try {
				 String logintype = (String)ses.getAttribute("LoginType");
				 List<Object[]> admindashboard = adminservice.HeaderSchedulesList("12" ,logintype); 
				 req.setAttribute("dashboard", admindashboard);
				 ses.setAttribute("formmoduleid", "12"); 
				 ses.setAttribute("SidebarActive", "ITInventoryDashboard_htm");
				 return "itinventory/inventorydashboard";
				 
			} catch (Exception e) 
			 { 
				e.printStackTrace(); 
			    logger.error(new Date()+" Inside ITInventoryDashboard.htm "+UserId, e); 
			    return "static/Error"; 
			}
	}

	
	 @RequestMapping(value = "ITAsset.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String ITAsset(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside ITAsset.htm "+UserId);
			try {
				
				List<Object[]> InventoryQuantityList=service.getInventoryQuantityList(EmpNo);
				req.setAttribute("InventoryQuantityList",InventoryQuantityList );
				return "itinventory/inventoryasset";
				
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside ITAsset.htm "+UserId, e);
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
				
				LocalDate today= LocalDate.now();
				String DeclarationYear=String.valueOf(today.getYear());
				ITInventory inventory=new ITInventory(); 
				inventory.setEmpNo(EmpNo);
				inventory.setDeclarationYear(DeclarationYear);
				inventory.setDate(sdf1.format(new Date()));
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
				return "redirect:/InventoryList.htm";
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
				
				LocalDate today= LocalDate.now();
				String DeclarationYear=String.valueOf(today.getYear());
				String Itinventoryid=req.getParameter("Itinventoryid");
				ITInventory inventory=new ITInventory();
				inventory.setItInventoryId(Long.parseLong(Itinventoryid));
				inventory.setDeclarationYear(DeclarationYear);
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
				return "redirect:/ITAsset.htm";
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
			    System.out.println("Itinventoryid ---"+inventoryid);
				System.out.println("Item Type---"+ItemType);
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
				
				ITInventoryConfigure  details = new  ITInventoryConfigure();
				details.setEmpNo(EmpNo);
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
				ITInventoryConfigure configure=service.getInventoryConfigId(Long.parseLong(inventoryconfigid));
				
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
				
				
				ITInventoryConfigure details= new ITInventoryConfigure();
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
			logger.info(new Date() +"Inside InventoryFormDownload.htm "+UserId);
			
			try {
				    LocalDate today= LocalDate.now();
			        String DeclarationYear=String.valueOf(today.getYear());
				    req.setAttribute("inventoryqty", service.getInventoryQtylist(EmpNo,DeclarationYear));
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
					logger.error(new Date() + " Getting Error From  InventoryFormDownload" + UserId, e);
				}
			
		}

	 
	 @RequestMapping(value = "InventoryFormDownload.htm",method=RequestMethod.GET)
		public void CHSSFormEmpDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
		{
			String UserId = (String) ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			logger.info(new Date() +"Inside InventoryFormDownload.htm "+UserId);
			
			try {
				
				LocalDate today= LocalDate.now();
		        String DeclarationYear=String.valueOf(today.getYear());
		        String ITInventoryId=req.getParameter("inventoryid");
				req.setAttribute("inventoryqty", service.getInventoryForwardedListPreview(ITInventoryId));
			    req.setAttribute("inventoryconfigure", service.getInventoryConfigure(ITInventoryId));
				
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
		  logger.info(new Date() +"Inside InventoryPreview.htm "+UserId); 
		  try {
			    LocalDate today= LocalDate.now();
			    String DeclarationYear=String.valueOf(today.getYear());
			    req.setAttribute("inventoryqty", service.getInventoryQtylist(EmpNo,DeclarationYear));
			    req.setAttribute("inventoryconfigure", service.getInventoryConfiguration(EmpNo));
			    req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			    req.setAttribute("LoginType", LoginType);
			    return "itinventory/inventorypreview";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryPreview.htm "+UserId, e); 
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
				  
				  ITInventory inventory=new ITInventory();
				  inventory.setItInventoryId(Long.parseLong(req.getParameter("inventoryid01")));
				  inventory.setStatus("F");
				  inventory.setERemarks(req.getParameter("Remarks"));
		          inventory.setDate(sdf1.format(new Date()));
		          long save=service.ForwardDetails(inventory,UserId,EmpNo);
		          if(save!=0){
		        	  redir.addAttribute("result", "Inventory Details Forwarded Successfully");
			  	   }
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Details Forward Unsuccessful");
			  		}
		          
		          return "redirect:/InventoryList.htm";
		          
		 }catch (Exception e) { 
			 e.printStackTrace(); 
			 logger.error(new Date() +" Inside InventoryDetailsForward.htm "+UserId, e); 
			 return "static/Error"; 
			 }
	}
	  
	  @RequestMapping(value = "InventoryList.htm", method ={RequestMethod.GET,RequestMethod.POST})
	  public String InventoryForwarded(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
	  { 
		  String UserId=(String)ses.getAttribute("Username"); 
		  String EmpNo =ses.getAttribute("EmpNo").toString();
		  ses.setAttribute("SidebarActive","InventoryDetails_htm");	
		  logger.info(new Date() +"Inside InventoryList.htm "+UserId); 
		  try {
			  LocalDate today= LocalDate.now();
			  String DeclarationYear=String.valueOf(today.getYear());
			  List<Object[]> InventoryList=service.getInventoryList(EmpNo,DeclarationYear);
			  req.setAttribute("InventoryList", InventoryList);
			  return "itinventory/inventorylist";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryList.htm "+UserId, e); 
		 return "static/Error"; 
		 }
		  
	}
	  
	  @RequestMapping(value = "InventoryDetailsForwarded.htm", method = {RequestMethod.GET,RequestMethod.POST})
		public String InventoryDetailsForwarded(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			ses.setAttribute("SidebarActive","InventoryDetailsForwarded_htm");	
			ses.setAttribute("formmoduleid", formmoduleid);
			logger.info(new Date() +"Inside InventoryDetailsForwarded.htm "+UserId);
			try {
				
				List<Object[]> InventoryForwardedList=service.getInventoryForwardedList();
				req.setAttribute("InventoryForwardedList", InventoryForwardedList);
				return "itinventory/inventoryforwarded";
				
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside InventoryDetailsForwarded.htm "+UserId, e);
				return "static/Error";
			}
		} 
	  
	  
	  @RequestMapping(value = "InventoryForwardedView.htm", method ={RequestMethod.GET,RequestMethod.POST})
	  public String InventoryForwardedPreview(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception 
	  { 
		  String UserId=(String)ses.getAttribute("Username"); 
		  String EmpNo =ses.getAttribute("EmpNo").toString();
		  String LoginType=ses.getAttribute("LoginType").toString();
		  logger.info(new Date() +"Inside InventoryPreview.htm "+UserId); 
		  try {
			    String ITInventoryId=req.getParameter("inventoryid");
			    req.setAttribute("inventoryqty", service.getInventoryForwardedListPreview(ITInventoryId));
			    req.setAttribute("inventoryconfigure", service.getInventoryConfigure(ITInventoryId));
			    req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo1.png")))));
			    req.setAttribute("LoginType", LoginType);
			    return "itinventory/inventorypreview";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryPreview.htm "+UserId, e); 
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
			    ITInventory inventory=new ITInventory();
			   inventory.setItInventoryId(Long.parseLong(req.getParameter("inventoryid")));
			   inventory.setStatus("A");
			   inventory.setDate(sdf1.format(new Date()));
			   long save=service.inventoryDetailsApprove(inventory);
			   if(save!=0){
		        	  redir.addAttribute("result", "Inventory Details Approved Successfully");
			  	   }
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Details Approve Unsuccessful");
			  		}
			  return "redirect:/InventoryDetailsForwarded.htm";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryDetailsApprove.htm "+UserId, e); 
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
			   inventory.setItInventoryId(Long.parseLong(req.getParameter("inventoryid")));
			   inventory.setARemarks(req.getParameter("remarks"));
			   inventory.setStatus("R");
			   inventory.setDate(sdf1.format(new Date()));
			   long save=service.inventoryDetailsReturn(inventory);
			   if(save!=0){
		        	  redir.addAttribute("result", "Inventory Details Returned Successfully");
			  	   }
			  	else {
			  			redir.addAttribute("resultfail", "Inventory Details Return Unsuccessful");
			  		}
			  return "redirect:/InventoryDetailsForwarded.htm";
	          
	 }catch (Exception e) { 
		 e.printStackTrace(); 
		 logger.error(new Date() +" Inside InventoryDetailsReturn.htm "+UserId, e); 
		 return "static/Error"; 
		 }
	}
	  
}




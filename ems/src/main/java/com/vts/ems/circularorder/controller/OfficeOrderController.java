package com.vts.ems.circularorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.circularorder.dto.OfficeOrderUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.circularorder.service.OfficeOrderService;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.CustomEncryptDecrypt;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.Zipper;


@Controller
public class OfficeOrderController {

	private static final Logger logger = LogManager.getLogger(OfficeOrderController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Autowired
	OfficeOrderService service;

	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@RequestMapping(value = "OfficeOrder.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String officeOrderList(HttpServletRequest req, HttpSession ses) throws Exception
	{
		
		String LoginType = (String) ses.getAttribute("LoginType");
		List<Object[]> officelist = new ArrayList<Object[]>();
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside OfficeOrder.htm "+UserId);
		try {
			ses.setAttribute("SidebarActive", "CircularList_htm");
			
			String fromdate = (String)req.getParameter("FromDate");
			String todate = (String)req.getParameter("ToDate");
			if(fromdate==null && todate == null) {
				fromdate = DateTimeFormatUtil.getFinancialYearStartDateRegularFormatOffice();
				todate  = DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now());
			}
					
			officelist = service.GetOfficeOrderList(fromdate , todate );
	   		req.setAttribute("officelist", officelist);
	   		req.setAttribute("fromdate", fromdate);	
			req.setAttribute("todate",todate);
			req.setAttribute("LoginType",LoginType);
			return "officeOrder/OfficeOrderList";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside OfficeOrderAdd.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		   }
	}


	@RequestMapping(value = "OfficeOrderAdd.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String OfficeOrderAdd(HttpServletRequest req, HttpSession ses) throws Exception
	{
		 return "officeOrder/OfficeOrderAddEdit";
	
	}
	
	@RequestMapping(value = "OfficeOrderDelete.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String OfficeOrderDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
   		String UserName=(String)ses.getAttribute("Username");
       	String OrderId = (String)req.getParameter("OrderId");
       	
       	long OrdersId = Long.parseLong(OrderId);
        long getMaxOrderId = service.GetMaxOrderId();
       
        
        if(OrdersId == getMaxOrderId) {
        	int count = service.OfficeOrderDelete(Long.parseLong(OrderId),UserName);
       		if (count > 0) {
       			redir.addAttribute("result", "OfficeOrder deleted Successfully");
    		} else {
    			redir.addAttribute("resultfail", "OfficeOrder delete Unsuccessfull");
    		}
       		
        }else {
       	 redir.addAttribute("resultfail", "Only Recent OfficeOrder is allowed to Delete ");
       	
        }
        return "redirect:/OfficeOrder.htm";
	}
	@RequestMapping(value = "OfficeOrderSearch.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String OfficeOrderSearch(HttpServletRequest req, HttpSession ses) throws Exception
	{
		List<Object[]> SearchList=new ArrayList<Object[]>();
        String search=req.getParameter("search");
        if(search!=null && !search.trim().equalsIgnoreCase("")) {
        	SearchList=service.GetSearchList(search);
        }
        req.setAttribute("SearchList",SearchList);
        //System.out.println(SearchList.size());
		return "officeOrder/OfficeOrderSearch";
	
	}
	
	
	/*
	 * @RequestMapping(value = "SearchCircular.htm", method = { RequestMethod.POST
	 * ,RequestMethod.GET }) public String SearchCircular(HttpServletRequest req,
	 * HttpSession ses, RedirectAttributes redir) throws Exception { List<Object[]>
	 * SearchList=new ArrayList<Object[]>(); String
	 * search=(String)req.getParameter("search");
	 * SearchList=service.GetSearchList(search);
	 * req.setAttribute("SearchList",SearchList); System.out.println(SearchList);
	 * return "redirect:/CircularSearch";
	 * 
	 * }
	 */
	
	
	
	@RequestMapping(value = "OfficeOrderAddSubmit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String officeOrderAdd(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("FileAttach") MultipartFile FileAttach) throws Exception{
		String Username=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside OfficeOrderAddSubmit.htm "+Username);
		
		try {

			System.out.println("OfficeOrderAddSubmit.htm");			
            String OrderNo   =(String)req.getParameter("OrderNo");
			String OrderDate   =(String)req.getParameter("OrderDate");
			String OrderSubject  =(String)req.getParameter("OrderSubject");
			String AutoId = UUID.randomUUID().toString();
			
			
			OfficeOrderUploadDto uploadorderdto =new OfficeOrderUploadDto();
			uploadorderdto.setOrderNo(OrderNo.trim()); 
			uploadorderdto.setOrderDate(OrderDate);
			uploadorderdto.setOrderSubject(OrderSubject.trim());
			uploadorderdto.setOrderFileName(FileAttach.getOriginalFilename());
			uploadorderdto.setAutoId(AutoId);
			uploadorderdto.setIS(FileAttach.getInputStream());
			uploadorderdto.setCreatedBy(Username);
			uploadorderdto.setCreatedDate(sdtf.format(new Date()));

            
			long count=service.OfficeOrderAdd(uploadorderdto);
	        if (count > 0) {
				 redir.addAttribute("result", "OfficeOrder Added Successfully");
			} else {
				 redir.addAttribute("resultfail", "OfficeOrder Add Unsuccessfull");
			}
			


			

		return  "redirect:/OfficeOrder.htm";
		
	 } catch (Exception e) {
		  logger.error(new Date() +"Inside OfficeOrderAddSubmit.htm "+Username ,e);
		  e.printStackTrace();
		  return "static/Error";
	   }
		
		
		
	}
	
	
	
	
	@RequestMapping(value = "OfficeOrderEdit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String OfficeOrderEdit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception
	{
         String OrderId = (String)req.getParameter("OrderId");
         long OrdersId = Long.parseLong(OrderId);
         long GetMaxOrderId = service.GetMaxOrderId();
        System.out.println(GetMaxOrderId);
         
         if(OrdersId == GetMaxOrderId) {
        	 EMSOfficeOrder list = service.GetOrderDetailsToEdit(OrdersId);
             req.setAttribute("OfficeOrderDetails", list);
    		 return "officeOrder/OfficeOrderAddEdit";
        	 
         }else {
        	 redir.addAttribute("resultfail", "Only Recent OfficeOrder is allowed to edit ");
        	 return "redirect:/OfficeOrder.htm";
         }
         
	
	}
	

	
	@RequestMapping(value ="OfficeOrderEditSubmit.htm" , method = RequestMethod.POST)
	public String OfficeOrderEdit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("EditFileAttach") MultipartFile FileAttach) throws Exception
	{
		String Username=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside OfficeOrderEditSubmit.htm "+Username);
		
		try {
			
			String OrderId =(String)req.getParameter("OrderIdSel");
            String OrderNo   =(String)req.getParameter("OrderNo");
			String OrderDate   =(String)req.getParameter("OrderDate");
			String OrderSubject  =(String)req.getParameter("OrderSubject");
			String AutoId = UUID.randomUUID().toString();

			
			OfficeOrderUploadDto uploadorderdto =new OfficeOrderUploadDto();
			uploadorderdto.setOrderId(Long.parseLong(OrderId));
			uploadorderdto.setOrderNo(OrderNo.trim()); 
			uploadorderdto.setOrderDate(OrderDate);
			uploadorderdto.setOrderSubject(OrderSubject.trim());
			uploadorderdto.setOrderFileName(FileAttach.getOriginalFilename());
			uploadorderdto.setAutoId(AutoId);
			uploadorderdto.setIS(FileAttach.getInputStream());
			uploadorderdto.setOrderPath(FileAttach);
			uploadorderdto.setModifiedBy(Username);
			uploadorderdto.setModifiedDate(sdtf.format(new Date()));
			
			long count=service.OrderUpdate(uploadorderdto);
	        if (count > 0) {
				 redir.addAttribute("result", "OfficeOrder Updated Successfully");
			} else {
				 redir.addAttribute("resultfail", "OfficeOrder Update Unsuccessfull");
			}
		
			
			return "redirect:/OfficeOrder.htm";
		} catch (Exception e) {
			logger.error(new Date() +"Inside OfficeOrderEditSubmit.htm "+Username,e);
			e.printStackTrace();
			return "static/Error";
		}		
		
		
	}
	
	
	
	
	/*
	 * @RequestMapping(value = "OfficeOrders.htm", method = RequestMethod.GET)
	 * public String OfficeOrders(HttpServletRequest req, HttpSession ses) throws
	 * Exception { return "circular/OfficeOrders"; }
	 */
	

	

	@RequestMapping(value = "OfficeOrderDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public void OfficeOrderDownload(HttpServletResponse res ,HttpServletRequest req, HttpSession ses) throws Exception
	{
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo=(String)ses.getAttribute("EmpNo");
		String EmpName = (String)ses.getAttribute("EmpName");
		
		try {

			EmployeeDetails empDetails = service.getEmpdataData(EmpNo);
			
			String password="123";
			
			if(empDetails!=null && empDetails.getDOB()!=null) {
				password = new SimpleDateFormat("dd-MM-yy").format(empDetails.getDOB()).replace("-","");
			}
			System.out.println(password);
			
			String tempPath=req.getServletContext().getRealPath("/view/temp");
			String tempPath1=req.getServletContext().getRealPath("/view/temp2");
			
			String OrderId=req.getParameter("OrderId");
			EMSOfficeOrder Order = service.getOrderData(OrderId);
			
			//Unzip the pdf file from zip file and copy to tempPath
			Zipper zip=new Zipper();
			zip.unpack(emsfilespath+"//"+Order.getOrderPath(),tempPath,CustomEncryptDecrypt.decryption(Order.getOrderKey().toCharArray()));
			System.out.println("unpack"+zip);
			
			PdfFileEncryptionDataDto dto=new PdfFileEncryptionDataDto().builder()
													.sourcePath(tempPath+"//"+Order.getOrderFileName())
													.targetPath(tempPath1+"//"+Order.getOrderFileName())
													.watermarkText( EmpNo+" : "+EmpName)
													.password(password)
													.EmpNo(EmpNo)
													.EmpName(EmpName)
													.build();
					
			// adding watermark,Metadata to the pdf file and encrypting the file and place it in temppath1
			File my_file = service.EncryptAddWaterMarkAndMetadatatoPDF(dto);
			
			res.setHeader("Content-disposition","attachment; filename="+Order.getOrderFileName());
		    OutputStream out = res.getOutputStream();
		    FileInputStream in = new FileInputStream(my_file);
		    byte[] buffer = new byte[4096];
		    int length;
		    while ((length = in.read(buffer)) > 0)
		    {
		    	out.write(buffer, 0, length);
		    }
		    in.close();
		    out.flush();
		    
		    EMSOfficeOrderTrans  OrderTrans = new EMSOfficeOrderTrans().builder()
		    									.OrderId(Long.parseLong(OrderId))
		    									.DownloadBy(Long.parseLong(EmpId))
		    									.EmpNo(EmpNo)
		    									.DownloadDate(new Timestamp(new Date().getTime()).toString())
		    									.IPAddress(req.getRemoteAddr())
		    									.build();
		    service.OfficeOrderTransactionAdd(OrderTrans);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +"OfficeOrderDownload.htm"+UserId, e);
		}
		
	}
	

}

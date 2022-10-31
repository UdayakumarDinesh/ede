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

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.circularorder.service.CircularService;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.Zipper;


@Controller
public class CircularController {

	private static final Logger logger = LogManager.getLogger(CircularController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Autowired
	CircularService service;

	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@RequestMapping(value = "CircularList.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularList(HttpServletRequest req, HttpSession ses) throws Exception
	{
		List<Object[]> circulatlist = new ArrayList<Object[]>();
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularList.htm "+UserId);
		
		 String fromdate = (String)req.getParameter("FromDate");
			 String todate = (String)req.getParameter("ToDate");
			 
			 if(fromdate==null && todate == null) {
				 fromdate = DateTimeFormatUtil.getFinancialYearStartDateRegularFormatCircular();
				 todate  = DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now());
			 }
				
			 circulatlist = service.GetCircularList(fromdate , todate );
   		 req.setAttribute("circulatlist", circulatlist);
   		 req.setAttribute("fromdate", fromdate);	
		 req.setAttribute("todate",todate);
		return "circular/CircularList";
	}


	@RequestMapping(value = "CircularAdd.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularAdd(HttpServletRequest req, HttpSession ses) throws Exception
	{
		 return "circular/CircularAddEdit";
	
	}
	
	@RequestMapping(value = "CircularDelete.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		
       		
       		String UserName=(String)ses.getAttribute("Username");
         	String CircularId = (String)req.getParameter("circulatId");
            System.out.println("CircularIdSelected"+CircularId);
       		int count = service.CircularDelete(Long.parseLong(CircularId),UserName);
       		if (count > 0) {
				redir.addAttribute("result", "Circular deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Circulae delete Unsuccessfull");
			}
       	return  "redirect:/CircularList.htm";
	
	}
	
	
	@RequestMapping(value = "CircularAddSubmit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularAdd(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("FileAttach") MultipartFile FileAttach) throws Exception{
		String Username=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularAddSubmit.htm "+Username);
		
		try {

			System.out.println("CircularAddSubmit.htm");			
            String CircularNo   =(String)req.getParameter("circularno");
			String CircularDate   =(String)req.getParameter("circularDate");
			String CirSubject  =(String)req.getParameter("cirSubject");
			String AutoId = UUID.randomUUID().toString();
			
				
			
			CircularUploadDto uploadcirdto =new CircularUploadDto();
			uploadcirdto.setCircularNo(CircularNo.trim()); 
			uploadcirdto.setCircularDate(CircularDate);
			uploadcirdto.setCirSubject(CirSubject.trim());
			uploadcirdto.setOriginalName(FileAttach.getOriginalFilename());
			uploadcirdto.setAutoId(AutoId);
            uploadcirdto.setIS(FileAttach.getInputStream());
            uploadcirdto.setCreatedBy(Username);
            uploadcirdto.setCreatedDate(sdtf.format(new Date()));

            
			long count=service.CircularUpload(uploadcirdto);
	        if (count > 0) {
				 redir.addAttribute("result", "Circular Added Successfully");
			} else {
				 redir.addAttribute("resultfail", "Circular Add Unsuccessfull");
			}
			


			

		return  "redirect:/CircularList.htm";
		
	 } catch (Exception e) {
		  logger.error(new Date() +"Inside CircularAddSubmit.htm "+Username ,e);
		  e.printStackTrace();
		  return "static/Error";
	   }
		
		
		
	}
	
	
	
	
	@RequestMapping(value = "CircularEdit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularEdit(HttpServletRequest req, HttpSession ses) throws Exception
	{
         String CircularId = (String)req.getParameter("circulatId");
         System.out.println("CircularIdSelected"+CircularId);
         EMSCircular list = service.GetCircularDetailsToEdit(Long.parseLong(CircularId));
         req.setAttribute("circularDetails", list);
		 return "circular/CircularAddEdit";
	
	}
	

	
	@RequestMapping(value ="CircularEditSubmit.htm" , method = RequestMethod.POST)
	public String circularEdit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("FileAttach") MultipartFile FileAttach) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularEditSubmit.htm "+UserId);
		
		try {
			
			System.out.println("CircularEditSubmit.htm");	
			String CircularId =(String)req.getParameter("circularIdSel");
            String CircularNo   =(String)req.getParameter("circularno");
			String CircularDate   =(String)req.getParameter("circularDate");
			String CirSubject  =(String)req.getParameter("cirSubject");
			String AutoId = UUID.randomUUID().toString();
			
		
			
			return "redirect:/CircularList.htm";
		} catch (Exception e) {
			logger.error(new Date() +"Inside CircularEditSubmit.htm "+UserId,e);
			e.printStackTrace();
			return "static/Error";
		}		
		
		
	}
	
	
	
	
	
	
	
	@RequestMapping(value = "OfficeOrders.htm", method = RequestMethod.GET)
	public String OfficeOrders(HttpServletRequest req, HttpSession ses) throws Exception
	{
		return "circular/OfficeOrders";
	}
	

	

	@RequestMapping(value = "CircularDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public void CircularDownload(HttpServletResponse res ,HttpServletRequest req, HttpSession ses) throws Exception
	{
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo=(String)ses.getAttribute("EmpNo");
		String EmpName = (String)ses.getAttribute("EmpName");
		try {

			EmployeeDetails empDetails = service.getEmpdataData(EmpNo);
			
			String password="123";
			
			if(empDetails!=null && empDetails.getPAN()!=null && !empDetails.getPAN().equalsIgnoreCase("") && empDetails.getDOB()!=null) {
				password = empDetails.getPAN().toUpperCase()+new SimpleDateFormat("dd-MM-yyyy").format(empDetails.getDOB()).replace("-","");
			}
			System.out.println(password);
			
			String tempPath=req.getServletContext().getRealPath("/view/temp");
			String tempPath1=req.getServletContext().getRealPath("/view/temp2");
			
			String CircularId=req.getParameter("CircularId");
			EMSCircular circular = service.getCircularData(CircularId);
			
			//Unzip the pdf file from zip file and copy to tempPath
			Zipper zip=new Zipper();
			zip.unpack(emsfilespath+"//"+circular.getCircularPath(),tempPath,circular.getCircularKey());
			
			PdfFileEncryptionDataDto dto=new PdfFileEncryptionDataDto().builder()
													.sourcePath(tempPath+"//"+circular.getCirFileName())
													.targetPath(tempPath1+"//"+circular.getCirFileName())
													.watermarkText( EmpNo+" : "+EmpName)
													.password(password)
													.EmpNo(EmpNo)
													.EmpName(EmpName)
													.build();
					
			// adding watermark,Metadata to the pdf file and encrypting the file and place it in temppath1
			File my_file = service.EncryptAddWaterMarkAndMetadatatoPDF(dto);
			
			res.setHeader("Content-disposition","attachment; filename="+circular.getCirFileName());
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
		    
		    EMSCircularTrans  circularTrans = new EMSCircularTrans().builder()
		    									.CircularId(Long.parseLong(CircularId))
		    									.DownloadBy(Long.parseLong(EmpId))
		    									.EmpNo(EmpNo)
		    									.DownloadDate(new Timestamp(new Date().getTime()).toString())
		    									.IPAddress(req.getRemoteAddr())
		    									.build();
		    service.CircularTransactionAdd(circularTrans);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" CircularDownload.htm "+UserId, e);
		}
		
	}
	
}

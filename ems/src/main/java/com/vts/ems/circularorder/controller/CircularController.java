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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.dto.DepCircularDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.circularorder.service.CircularService;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.CustomEncryptDecrypt;
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
		String LoginType = (String) ses.getAttribute("LoginType");
		List<Object[]> circulatlist = new ArrayList<Object[]>();
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularList.htm "+UserId);
		try {
			ses.setAttribute("SidebarActive", "CircularList_htm");
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
			req.setAttribute("LoginType",LoginType);
			return "circular/CircularList";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside CircularAdd.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		   }
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
       	
       	long cirId = Long.parseLong(CircularId);
        long getMaxCircularId = service.GetMaxCircularId();
       
        
        if(cirId == getMaxCircularId) {
        	int count = service.CircularDelete(Long.parseLong(CircularId),UserName);
       		if (count > 0) {
       			redir.addAttribute("result", "Circular deleted Successfully");
    		} else {
    			redir.addAttribute("resultfail", "Circulae delete Unsuccessfull");
    		}
       		
        }else {
        	redir.addAttribute("resultfail", "Only Recent Circular is allowed to Delete ");
        }
        return "redirect:/CircularList.htm";
	}
	
	
	@RequestMapping(value = "CircularSearch.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularSearch(HttpServletRequest req, HttpSession ses) throws Exception
	{
		List<Object[]> SearchList=new ArrayList<Object[]>();
        String search=req.getParameter("search");
        if(search!=null && !search.trim().equalsIgnoreCase("")) {
        	SearchList=service.GetSearchList(search);
        }
        req.setAttribute("SearchList",SearchList);
		return "circular/CircularSearch";
	
	}
	
	@RequestMapping(value = "CircularAddSubmit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularAdd(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("FileAttach") MultipartFile FileAttach) throws Exception{
		String Username=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularAddSubmit.htm "+Username);
		
		try {

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

            
			long count=service.CircularAdd(uploadcirdto);
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
	public String circularEdit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir) throws Exception
	{
         String CircularId = (String)req.getParameter("circulatId");
         long cirId = Long.parseLong(CircularId);
         long getMaxCircularId = service.GetMaxCircularId();
        
         
         if(cirId == getMaxCircularId) {
        	 EMSCircular list = service.GetCircularDetailsToEdit(cirId);
             req.setAttribute("circularDetails", list);
    		 return "circular/CircularAddEdit";
        	 
         }else {
        	 redir.addAttribute("resultfail", "Only Recent Circular is allowed to edit ");
        	 return "redirect:/CircularList.htm";
         }
         
	
	}
	

	
	@RequestMapping(value ="CircularEditSubmit.htm" , method = RequestMethod.POST)
	public String circularEdit(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("EditFileAttach") MultipartFile FileAttach) throws Exception
	{
		String Username=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularEditSubmit.htm "+Username);
		
		try {
			
			String CircularId =(String)req.getParameter("circularIdSel");
            String CircularNo   =(String)req.getParameter("circularno");
			String CircularDate   =(String)req.getParameter("circularDate");
			String CirSubject  =(String)req.getParameter("cirSubject");
			String AutoId = UUID.randomUUID().toString();

			
			CircularUploadDto uploadcirdto =new CircularUploadDto();
			uploadcirdto.setCircularId(Long.parseLong(CircularId));
			uploadcirdto.setCircularNo(CircularNo.trim()); 
			uploadcirdto.setCircularDate(CircularDate);
			uploadcirdto.setCirSubject(CirSubject.trim());
			uploadcirdto.setOriginalName(FileAttach.getOriginalFilename());
			uploadcirdto.setAutoId(AutoId);
            uploadcirdto.setIS(FileAttach.getInputStream());
            uploadcirdto.setCircularPath(FileAttach);
            uploadcirdto.setModifiedBy(Username);
            uploadcirdto.setModifiedDate(sdtf.format(new Date()));
			
			long count=service.CircularUpdate(uploadcirdto);
	        if (count > 0) {
				 redir.addAttribute("result", "Circular Updated Successfully");
			} else {
				 redir.addAttribute("resultfail", "Circular Update Unsuccessfull");
			}
		
			
			return "redirect:/CircularList.htm";
		} catch (Exception e) {
			logger.error(new Date() +"Inside CircularEditSubmit.htm "+Username,e);
			e.printStackTrace();
			return "static/Error";
		}		
		
		
	}

	@RequestMapping(value = "CircularDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public void CircularDownload(HttpServletResponse res ,HttpServletRequest req, HttpSession ses) throws Exception
	{
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo=(String)ses.getAttribute("EmpNo");
		String EmpName = (String)ses.getAttribute("EmpName");
		logger.info(new Date() +"Inside CircularDownload.htm "+UserId);
		try {

			EmployeeDetails empDetails = service.getEmpdataData(EmpNo);
			
			String password="1234";
			
			if(empDetails!=null && empDetails.getDOB()!=null) {
				password = new SimpleDateFormat("dd-MM-yy").format(empDetails.getDOB()).replace("-","");
			}
			System.out.println(password);
			
			String tempPath=req.getServletContext().getRealPath("/view/temp");
			String tempPath1=req.getServletContext().getRealPath("/view/temp2");
			
			String CircularId=req.getParameter("CircularId");
			EMSCircular circular = service.getCircularData(CircularId);
			
			//Unzip the pdf file from zip file and copy to tempPath
			Zipper zip=new Zipper();
			zip.unpack(emsfilespath+"//"+circular.getCircularPath(),tempPath,CustomEncryptDecrypt.decryption(circular.getCircularKey().toCharArray()));
			
			
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
	
	
	
	@RequestMapping(value = "RunBatchFile.htm")
	public String runBatchFile(HttpServletRequest req, HttpSession ses) throws Exception
	{
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("cmd.exe", "/C",  "C:\\Users\\Admin\\Desktop\\DBBackup.bat");
//		File dir = new File("C:\\Users\\Admin\\Desktop\\");
//		processBuilder.directory(dir);
		Process process = processBuilder.start();
		return "redirect:/CircularList.htm";
	}
	
	
	@RequestMapping(value = "DepCircularList.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String DepCircularList( HttpServletRequest req, HttpSession ses,@RequestParam String id ) throws Exception
	{
		String LoginType = (String) ses.getAttribute("LoginType");
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside DepCircularList.htm "+UserId);
		try {
			Object[] DepType=service.GetEmsDepType(id);
			ses.setAttribute("SidebarActive", "DepCircularList_htm?id="+id);
			String fromdate = (String)req.getParameter("FromDate");
			String todate = (String)req.getParameter("ToDate");
				 
			if(fromdate==null || todate == null) 
			{
				LocalDate today= LocalDate.now();
				if(today.getMonthValue()<4) 
				{
					fromdate = String.valueOf(today.getYear()-1)+"-04-01";
				}
				else
				{
					fromdate=String.valueOf(today.getYear())+"-04-01";
				}
				
				todate  = LocalDate.now().toString();
			}else
			{
				fromdate=sdf.format(rdf.parse(fromdate));
				todate=sdf.format(rdf.parse(todate));
			}
					
			List<Object[]> circulatlist = service.GetDepCircularList(fromdate , todate,id );
	   		req.setAttribute("circulatlist", circulatlist);
	   		req.setAttribute("fromdate", fromdate);	
			req.setAttribute("todate",todate);
			req.setAttribute("LoginType",LoginType);
			req.setAttribute("DepType",DepType);
			return "circular/DepCircularList";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside DepCircularList.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "DepCircularAdd.htm")
	public String DepCircularAdd(HttpServletRequest req, HttpSession ses,@RequestParam(name = "id") String id) throws Exception
	{
		req.setAttribute("DepType",service.GetEmsDepType(id));
		return "circular/DepCircularAddEdit";
	}
	
	@RequestMapping(value = "DepCircularAddSubmit.htm")
	public String DepCircularAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("CircularFile") MultipartFile CircularFile) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside DepCircularAddSubmit.htm "+UserId);
		try {
			
			String DepTypeId= req.getParameter("DepTypeId");
			
			
			EMSDepCircular cir = new EMSDepCircular().builder()
									.DepTypeId(Integer.parseInt(DepTypeId))
									.DepCircularNo(req.getParameter("CircularNo"))
									.DepCircularDate(sdf.format(rdf.parse(req.getParameter("CircularDate"))))
									.DepCirSubject(req.getParameter("description"))
									.CreatedBy(UserId)
									.build();
			long count=service.DepCircularAdd(new DepCircularDto(cir, CircularFile));
	        if (count > 0) {
				 redir.addAttribute("result", "Circular Added Successfully");
			} else {
				 redir.addAttribute("resultfail", "Circular Add Unsuccessfull");
			}
			
			
			redir.addAttribute("DepTypeId",DepTypeId);
			return "redirect:/DepCircularList.htm?id="+DepTypeId;
		} catch (Exception e) {
			  logger.error(new Date() +"Inside DepCircularAddSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "DepCircularDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public void DepCircularDownload(HttpServletResponse res ,HttpServletRequest req, HttpSession ses) throws Exception
	{
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String UserId = (String) ses.getAttribute("Username");
		String EmpNo=(String)ses.getAttribute("EmpNo");
		String EmpName = (String)ses.getAttribute("EmpName");
		logger.info(new Date() +"Inside DepCircularDownload.htm "+UserId);
		try {

			EmployeeDetails empDetails = service.getEmpdataData(EmpNo);
			
			String password="123";
			
			if(empDetails!=null && empDetails.getDOB()!=null) {
				password = new SimpleDateFormat("dd-MM-yy").format(empDetails.getDOB()).replace("-","");
			}
			System.out.println(password);
			
			String tempPath=req.getServletContext().getRealPath("/view/temp");
			String tempPath1=req.getServletContext().getRealPath("/view/temp2");
			
			String CircularId=req.getParameter("CircularId");
			EMSDepCircular circular = service.getEmsDepCircular(CircularId);
			
			//Unzip the pdf file from zip file and copy to tempPath
			Zipper zip=new Zipper();
			zip.unpack(emsfilespath+"//"+circular.getDepCircularPath(),tempPath,CustomEncryptDecrypt.decryption(circular.getDepCircularKey().toCharArray()));
			
			
			PdfFileEncryptionDataDto dto=new PdfFileEncryptionDataDto().builder()
													.sourcePath(tempPath+"//"+circular.getDepCirFileName())
													.targetPath(tempPath1+"//"+circular.getDepCirFileName())
													.watermarkText( EmpNo+" : "+EmpName)
													.password(password)
													.EmpNo(EmpNo)
													.EmpName(EmpName)
													.build();
					
			// adding watermark,Metadata to the pdf file and encrypting the file and place it in temppath1
			File my_file = service.EncryptAddWaterMarkAndMetadatatoPDF(dto);
			
			res.setHeader("Content-disposition","attachment; filename="+circular.getDepCirFileName());
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
		    
//		    EMSCircularTrans  circularTrans = new EMSCircularTrans().builder()
//		    									.CircularId(Long.parseLong(CircularId))
//		    									.DownloadBy(Long.parseLong(EmpId))
//		    									.EmpNo(EmpNo)
//		    									.DownloadDate(new Timestamp(new Date().getTime()).toString())
//		    									.IPAddress(req.getRemoteAddr())
//		    									.build();
//		    service.CircularTransactionAdd(circularTrans);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" DepCircularDownload.htm "+UserId, e);
		}
		
	}
	
	
	@RequestMapping(value = "DepCircularEdit.htm")
	public String DepCircularEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestParam(name = "id") String id) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside DepCircularList.htm "+UserId);
		try {
			String circularId = req.getParameter("circularId");
			long getMaxCircularId = service.GetDepCircularMaxIdEdit(id);
			
			if(Long.parseLong(circularId) == getMaxCircularId) {
				req.setAttribute("circular", service.getEmsDepCircular(circularId));
				req.setAttribute("DepType",service.GetEmsDepType(id));
				return "circular/DepCircularAddEdit";
	        	 
	        }else {
		        redir.addAttribute("resultfail", "Only Latest Circular is allowed to edit ");
		        redir.addAttribute("id",id);
	        	return "redirect:/DepCircularList.htm";
	        }
		} catch (Exception e) {
			  logger.error(new Date() +"Inside DepCircularAddSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "DepCircularEditSubmit.htm")
	public String DepCircularEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("CircularFile") MultipartFile CircularFile) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside DepCircularEditSubmit.htm "+UserId);
		try {
			
			String circularId = req.getParameter("circularId");
			String DepTypeId= req.getParameter("DepTypeId");
			
			
			EMSDepCircular cir = new EMSDepCircular().builder()
									.DepCircularId(Long.parseLong(circularId))
									.DepTypeId(Integer.parseInt(DepTypeId))
									.DepCircularNo(req.getParameter("CircularNo"))
									.DepCircularDate(sdf.format(rdf.parse(req.getParameter("CircularDate"))))
									.DepCirSubject(req.getParameter("description"))
									.ModifiedBy(UserId)
									.build();
			long count=service.DepCircularEdit(new DepCircularDto(cir, CircularFile));
	        if (count > 0) {
				 redir.addAttribute("result", "Circular Updated Successfully");
			} else {
				 redir.addAttribute("resultfail", "Circular Update Unsuccessfull");
			}
			
			
			redir.addAttribute("DepTypeId",DepTypeId);
			return "redirect:/DepCircularList.htm?id="+DepTypeId;
		} catch (Exception e) {
			  logger.error(new Date() +"Inside DepCircularEditSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
		
	}
	
	
	@RequestMapping(value = "DepCircularDelete.htm", method = { RequestMethod.POST })
	public String DepCircularDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestParam String id) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside DepCircularDelete.htm "+UserId);
		try {
			String circularId = req.getParameter("circularId");
			long getMaxCircularId = service.GetDepCircularMaxIdEdit(id);
			
			if(Long.parseLong(circularId) == getMaxCircularId) {

				long count = service.DepCircularDelete(circularId, UserId);
				if (count > 0) {
					 redir.addAttribute("result", "Circular Deleted Successfully");
				} else {
					 redir.addAttribute("resultfail", "Circular Delete Unsuccessfull");
				}
	        	 
	        }else {
		        redir.addAttribute("resultfail", "Only Latest Circular is allowed to Delete ");
		        
	        }
			redir.addAttribute("id",id);
        	return "redirect:/DepCircularList.htm";
		}
		catch (Exception e) {
		  logger.error(new Date() +"Inside DepCircularDelete.htm "+UserId ,e);
		  e.printStackTrace();
		  return "static/Error";
		
		}
	}
	
	@RequestMapping(value = "DepCircularSearch.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String DepCircularSearch(HttpServletRequest req, HttpSession ses,@RequestParam String id) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside DepCircularSearch.htm "+UserId);
		try
		{
			List<Object[]> SearchList=new ArrayList<Object[]>();
	        String search=req.getParameter("search");
	        
	        System.out.println(search);
	        System.out.println(id);
	        
	        if(search!=null && !search.trim().equalsIgnoreCase("") && !id.trim().equalsIgnoreCase("")) {
	        	SearchList=service.DepCircularSearchList(search,id);
	        }
	        
	        req.setAttribute("Search",search);
	        req.setAttribute("DepTypeList",service.GetEmsDepType());
	        req.setAttribute("DepType",service.GetEmsDepType(id));
	        req.setAttribute("SearchList",SearchList);
	        return "circular/DepCircularSearch";
		}
		catch (Exception e) {
		  logger.error(new Date() +"Inside DepCircularSearch.htm "+UserId ,e);
		  e.printStackTrace();
		  return "static/Error";
		
		}
	
	}
	
}

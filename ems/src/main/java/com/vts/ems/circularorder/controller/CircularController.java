package com.vts.ems.circularorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.service.CircularService;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.Zipper;

@Controller
public class CircularController {

	private static final Logger logger = LogManager.getLogger(CircularController.class);
	
	@Autowired
	CircularService service;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@RequestMapping(value = "CircularList.htm", method = RequestMethod.GET)
	public String circularList(HttpServletRequest req, HttpSession ses) throws Exception
	{
		return "circular/CircularList";
	}
	
	@RequestMapping(value = "OfficeOrders.htm", method = RequestMethod.GET)
	public String OfficeOrders(HttpServletRequest req, HttpSession ses) throws Exception
	{
		return "circular/OfficeOrders";
	}
	

	

	@RequestMapping(value = "CircularDownload.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public void CircularDownload(HttpServletResponse res ,HttpServletRequest req, HttpSession ses) throws Exception
	{
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
					
			// adding watermark,Metadata to the pdf file and encrypting the file
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
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" CircularDownload.htm "+UserId, e);
		}
		
	}
	
}

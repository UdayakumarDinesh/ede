package com.vts.ems.circularorder.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.service.CircularService;
import com.vts.ems.utils.DateTimeFormatUtil;


@Controller
public class CircularController {

	private static final Logger logger = LogManager.getLogger(CircularController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Autowired
	CircularService service;
	
	@RequestMapping(value = "CircularList.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularList(HttpServletRequest req, HttpSession ses) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularList.htm "+UserId);
		
					return "circular/CircularList";
	   		
	 	
		
		
	}


	@RequestMapping(value = "CircularAdd.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularAdd(HttpServletRequest req, HttpSession ses) throws Exception
	{
		 return "circular/CircularAddEdit";
	
	}
	
	@RequestMapping(value = "CircularEdit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularEdit(HttpServletRequest req, HttpSession ses) throws Exception
	{
         //			String circularId = (String)req.getParameter("circulatId");
         //		CircularList circularDetails = service.GetCircularToEdit(Long.parseLong(circularid));
         //		req.setAttribute("circularDetails", circularDetails);
		 return "circular/CircularAddEdit";
	
	}
	
	@RequestMapping(value = "CircularDelete.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
         //			String circularId = (String)req.getParameter("circulatId");
         //		CircularList circularDetails = service.GetCircularToEdit(Long.parseLong(circularid));
         //		req.setAttribute("circularDetails", circularDetails);
		 return "redirect:/CircularList.htm";
	
	}
	
	
	@RequestMapping(value = "CircularAddSubmit.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularAdd(HttpServletRequest req, HttpSession ses,RedirectAttributes redir,@RequestPart("FileAttach") MultipartFile FileAttach) throws Exception{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside CircularAdd.htm "+UserId);
		
		try {
			System.out.println("CircularAdd.htm");
//			String action = (String)req.getParameter("action");
//			if("CircularAdd".equalsIgnoreCase(action)) {
		
			
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
            uploadcirdto.setCreatedBy(UserId);
            uploadcirdto.setCreatedDate(sdtf.format(new Date()));

            EMSCircular circular = new EMSCircular();
			long count=service.CircularUpload(uploadcirdto,circular);
	        if (count > 0) {
				 redir.addAttribute("result", "Circular Added Successfully");
			} else {
				 redir.addAttribute("resultfail", "Circular Added Unsuccessfull");
			}
			
	        System.out.println("CircularAdd.htm");
			
		return  "redirect:/CircularList.htm";
		
	 } catch (Exception e) {
		  logger.error(new Date() +"Inside CircularAdd.htm "+UserId ,e);
		  e.printStackTrace();
		  return "static/Error";
	   }
		
		
		
	}
	
	
//	@RequestMapping(value ="CircularEDIT.htm" , method = RequestMethod.POST)
//	public String CirculatEdit(HttpServletRequest req,HttpSession ses, @RequestPart("selectedFile") MultipartFile selectedFile, RedirectAttributes redir) throws Exception
//	{
//		String UserId=(String)ses.getAttribute("Username");
//		logger.info(new Date() +"Inside CircularEDIT.htm "+UserId);
//		
//		try {
//			
//			
//			
//			return "redirect:/CircularList.htm";
//		} catch (Exception e) {
//			logger.error(new Date() +"Inside CircularEDIT.htm "+UserId,e);
//			e.printStackTrace();
//			return "static/Error";
//		}		
//		
//		
//	}
	
	
	
	
}

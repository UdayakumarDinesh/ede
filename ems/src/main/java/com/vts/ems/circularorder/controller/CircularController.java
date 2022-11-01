package com.vts.ems.circularorder.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	@RequestMapping(value = "CircularSearch.htm", method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularSearch(HttpServletRequest req, HttpSession ses) throws Exception
	{
		List<Object[]> SearchList=new ArrayList<Object[]>();
        String search=req.getParameter("search");
        if(search!=null && !search.trim().equalsIgnoreCase("")) {
        	SearchList=service.GetSearchList(search);
        }
        req.setAttribute("SearchList",SearchList);
        //System.out.println(SearchList.size());
		return "circular/CircularSearch";
	
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

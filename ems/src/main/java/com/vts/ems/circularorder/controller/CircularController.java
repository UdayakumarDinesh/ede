package com.vts.ems.circularorder.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vts.ems.circularorder.service.CircularService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class CircularController {

	private static final Logger logger = LogManager.getLogger(CircularController.class);
	
	@Autowired
	CircularService service;

	@RequestMapping(value = "CircularList.htm",method = { RequestMethod.POST ,RequestMethod.GET })
	public String circularList(HttpServletRequest req, HttpSession ses) throws Exception
	{
		List<Object[]> circulatlist = new ArrayList<Object[]>();
		
		 String fromdate = (String)req.getParameter("FromDate");
		 System.out.println("fromm date"+fromdate);
			 String todate = (String)req.getParameter("ToDate");
			 
			 if(fromdate==null && todate == null) {
				 fromdate = DateTimeFormatUtil.getFinancialYearStartDateRegularFormatCircular();
				 todate  = DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now());
				 System.out.println(fromdate);
				 System.out.println(todate);
			 }
				
			 circulatlist = service.GetCircularList(fromdate , todate );
			 System.out.println(circulatlist);
   		 req.setAttribute("circulatlist", circulatlist);
   		 req.setAttribute("fromdate", fromdate);	
		 req.setAttribute("todate",todate);
		return "circular/CircularList";
	}
	
	
}

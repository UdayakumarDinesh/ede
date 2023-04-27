package com.vts.ems.Tour.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.service.TourService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class TourController {

	private static final Logger logger = LogManager.getLogger(TourController.class);
	
	@Autowired
	private TourService service;
	
	@RequestMapping(value = "TourProgram.htm", method = RequestMethod.GET)
	public String TourDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourProgram.htm "+Username);		
		try {
				ses.setAttribute("formmoduleid", "14");
				ses.setAttribute("SidebarActive","TourProgram_htm");
			return "tour/TourDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside TourProgram.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	} 
	
	@RequestMapping(value = "TourApply.htm" , method = RequestMethod.GET)
	public String TourApply(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourApply.htm "+Username);	
		try {
			List<Object[]> emplist=service.GetEmployeeList(); 
			List<Object[]> modeoftravel=service.GetModeofTravel(); 
			List<Object[]> citylist=service.GetCityList();
			req.setAttribute("ModeOfTravelList", modeoftravel);
			req.setAttribute("CityList", citylist);
			req.setAttribute("emplist", emplist);
			ses.setAttribute("SidebarActive","TourApply_htm");
			return "tour/TourApply";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApply.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "TourApplyAdd.htm" , method = RequestMethod.POST)
	public String TourApplyAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourApplyAdd.htm "+Username);	
		try {
			String empno1 = (String)ses.getAttribute("EmpNo");

			String depdate = req.getParameter("DepartureDate");
			String arrdate = req.getParameter("ArrivalDate");
			String placeofstay = req.getParameter("POS");
			String purpose = req.getParameter("Purpose");
			String empno = req.getParameter("EmpNo");
			String airtraveljust = req.getParameter("airtraveljusti");
			String reqadvamt  =req.getParameter("reqadvamt");
			String earlisttime = req.getParameter("EarliestTime");
			String earlistdate = req.getParameter("EarliestDate");
			String earlistplace  = req.getParameter("EarliestPlace");
			String remarks = req.getParameter("Remarks");
			
			System.out.println("depdate        :-"+depdate);
			System.out.println("arrdate        :-"+arrdate);
			System.out.println("placeofstay        :-"+placeofstay);
			System.out.println("purpose        :-"+purpose);
			System.out.println("empno        :-"+empno);
			System.out.println("airtraveljust        :-"+airtraveljust);
			System.out.println("reqadvamt        :-"+reqadvamt);
			System.out.println("earlisttime        :-"+earlisttime);
			System.out.println("earlistdate        :-"+earlistdate);
			System.out.println("earlistplace        :-"+earlistplace);

			String[]  tourdates = req.getParameterValues("DepDate");
			String[]  tourtimes = req.getParameterValues("tourtime");
			String[]  modeoftravel = req.getParameterValues("modeoftravel");
			String[]  fromcity = req.getParameterValues("fromcity");
			String[]  tocity = req.getParameterValues("tocity");
			
			TourApplyDto applydto = new TourApplyDto();
			applydto.setStayFrom(DateTimeFormatUtil.dateConversionSql(depdate));
			applydto.setStayTo(DateTimeFormatUtil.dateConversionSql(arrdate));
			applydto.setStayPlace(placeofstay);
			applydto.setPurpose(purpose);
			if(airtraveljust!=null){
				applydto.setAirTravJust(airtraveljust);
			}
			if(reqadvamt!=null) {
				applydto.setAdvancePropsed(Integer.parseInt(reqadvamt));
			}
			applydto.setEmpNo(empno);
			applydto.setEarliestTime(earlisttime);
			applydto.setEarliestDate(DateTimeFormatUtil.dateConversionSql(earlistdate));
			applydto.setEarliestPlace(earlistplace);
			applydto.setApplyEmpNo(empno1);
			applydto.setCreatedBy(Username);
			applydto.setCreatedDate(new Date().toString());
			applydto.setIsActive(1);
			applydto.setInitiatedDate(new Date().toString());
			applydto.setRemarks(remarks);
			
			applydto.setTourDates(tourdates);
			applydto.setTourTimes(tourtimes);
			applydto.setModeofTravel(modeoftravel);
			applydto.setFromCity(fromcity);
			applydto.setToCity(tocity);
			
			Long count  = service.AddTourApply(applydto);
			if (count != 0) {
				redir.addAttribute("result", "Tour program Initiated successfully");
			} else {
				redir.addAttribute("resultfail", "Tour program Initiated  Unsuccessfull");
			}
			
			return "redirect:/PisAdminEmpList.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApplyAdd.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "TourApplyList.htm" , method = RequestMethod.POST)
	public String TourApplyList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourApplyList.htm "+Username);	
		try {
			List<Object[]> applylist = service.GetTourApplyList();
			
			return "";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApplyList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
}


package com.vts.ems.Tour.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.service.TourService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class TourController {

	private static final Logger logger = LogManager.getLogger(TourController.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

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
			String empno = (String)ses.getAttribute("EmpNo");
			Long divisionid = Long.parseLong(ses.getAttribute("DivisionId").toString());
			String depdate = req.getParameter("DepartureDate");
			String arrdate = req.getParameter("ArrivalDate");
			String placeofstay = req.getParameter("POS");
			String purpose = req.getParameter("Purpose");
			String airtraveljust = req.getParameter("airtraveljusti");
			String reqadvamt  =req.getParameter("reqadvamt");
			String earlisttime = req.getParameter("EarliestTime");
			String earlistdate = req.getParameter("EarliestDate");
			String earlistplace  = req.getParameter("EarliestPlace");
			String remarks = req.getParameter("Remarks");

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
			applydto.setDivisionId(divisionid);
			applydto.setEarliestTime(earlisttime);
			applydto.setEarliestDate(DateTimeFormatUtil.dateConversionSql(earlistdate));
			applydto.setEarliestPlace(earlistplace);
			applydto.setCreatedBy(Username);
			applydto.setCreatedDate(sdtf.format(new Date()));
			applydto.setIsActive(1);
			applydto.setInitiatedDate(sdtf.format(new Date()));
			applydto.setRemarks(remarks);
			applydto.setTourStatusCode("INI");
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
			
			return "redirect:/TourApplyList.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApplyAdd.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "TourApplyList.htm" , method = {RequestMethod.POST, RequestMethod.GET})
	public String TourApplyList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourApplyList.htm "+Username);	
		try {
			String action = req.getParameter("Action");
			System.out.println("Action     :"+action);
			if(action!=null && action.equalsIgnoreCase("EDIT")){
				String tourid = req.getParameter("tourapplyId");
				req.setAttribute("TourApply", service.getTourApplyData(Long.parseLong(tourid)));			
				req.setAttribute("Touronwarddetails", service.getTourOnwardReturnData(Long.parseLong(tourid)));
				req.setAttribute("ModeOfTravelList", service.GetModeofTravel());
				req.setAttribute("CityList", service.GetCityList());
				return "tour/TourApplyEdit";
			}else if (action!=null && action.equalsIgnoreCase("SubmitEdit")) {
				
				String departure=req.getParameter("DepartureDate");
				String arrivaldate=req.getParameter("ArrivalDate");
				String pos = req.getParameter("POS");
				String purpose = req.getParameter("Purpose");
				String airtraveljusti = req.getParameter("airtraveljusti");
				String reqadvamt = req.getParameter("reqadvamt");
				String remarks = req.getParameter("Remarks");
				String earliesttime = req.getParameter("EarliestTime");
				String earliestdate = req.getParameter("EarliestDate");
				String earliestplace = req.getParameter("EarliestPlace");
				String tourid = req.getParameter("tourapplyid");
				
				String depdate[] = req.getParameterValues("DepDate");
				String tourtime[] = req.getParameterValues("tourtime");
				String modeoftravel[] = req.getParameterValues("modeoftravel");
				String fromcity[] = req.getParameterValues("fromcity");
				String tocity[] = req.getParameterValues("tocity");
				
				TourApplyDto applydto = new TourApplyDto();
				applydto.setTourApplyId(Long.parseLong(tourid));
				applydto.setStayFrom(DateTimeFormatUtil.dateConversionSql(departure));
				applydto.setStayTo(DateTimeFormatUtil.dateConversionSql(arrivaldate));
				applydto.setStayPlace(pos);
				applydto.setPurpose(purpose);
				if(airtraveljusti!=null){
					applydto.setAirTravJust(airtraveljusti);
				}
				if(reqadvamt!=null) {
					applydto.setAdvancePropsed(Integer.parseInt(reqadvamt));
				}
				applydto.setEarliestTime(earliesttime);
				applydto.setEarliestDate(DateTimeFormatUtil.dateConversionSql(earliestdate));
				applydto.setEarliestPlace(earliestplace);
				applydto.setModifiedBy(Username);
				applydto.setModifiedDate(sdtf.format(new Date()));
				applydto.setInitiatedDate(sdtf.format(new Date()));
				applydto.setRemarks(remarks);
				applydto.setTourDates(depdate);
				applydto.setTourTimes(tourtime);
				applydto.setModeofTravel(modeoftravel);
				applydto.setFromCity(fromcity);
				applydto.setToCity(tocity);
				
				long count = service.EditTourApply(applydto);
				if (count != 0) {
					redir.addAttribute("result", "Tour program Update successfully");
				} else {
					redir.addAttribute("resultfail", "Tour program Update Unsuccessfull");
				}
				
				return "redirect:/TourApplyList.htm";
			}else{
				List<Object[]> applylist = service.GetTourApplyList();
				List<Object[]> emplist=service.GetEmployeeList(); 
				req.setAttribute("emplist", emplist);
				req.setAttribute("applylist", applylist);
				ses.setAttribute("SidebarActive","TourApplyList_htm");
				return "tour/TourApplyList";
			}
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApplyList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "checktour.htm", method = RequestMethod.GET)
	public @ResponseBody String TdCheck(HttpServletRequest request, HttpServletResponse response ,HttpSession ses) throws IOException {
		String[] Result=null;
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside checktour.htm "+Username);	
		Gson json = new Gson();
		try {
			String empno = (String) ses.getAttribute("EmpNo");
			String DepartureDate = request.getParameter("DepartureDate");
			String ArrivalDate = request.getParameter("ArrivalDate");
			Result= service.checkTDAlreadyPresentForSameEmpidAndSameDates( empno, DepartureDate, ArrivalDate);

		} catch (Exception e) {
			logger.error(new Date() +" Inside checktour.htm "+Username, e);
			e.printStackTrace();
			Result[0]= "Some Error Occur Please Try Again Later!";
			Result[1]="Fail";
		}
		return json.toJson(Result);	
	}
	
	@RequestMapping(value = "TourForward.htm" , method = RequestMethod.POST)
	public String TourForward(HttpServletRequest req, HttpServletResponse res ,HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourForward.htm "+Username);	
		try {
			String tourapplyid = req.getParameter("tourapplyId");
			String empno = (String) ses.getAttribute("EmpNo");
			int result  = service.ForwardTour(tourapplyid,empno);
			if (result != 0) {
				redir.addAttribute("result", "Tour program Forward successfully");
			} else {
				redir.addAttribute("resultfail", "Tour program Forward Unsuccessfull");
			}
			return "redirect:/TourApplyList.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourForward.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
}


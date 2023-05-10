package com.vts.ems.Tour.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ProcessBuilder.Redirect;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.service.TourService;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.model.EMSNotification;
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
			//req.setAttribute("List", service.GetApprovalEmp(ses.getAttribute("EmpNo").toString()));
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
			String actval = req.getParameter("Action");
			String  action= null;
			if(actval!=null) {
				action=actval.split("/")[0];
			}
			
			System.out.println("action    :"+action);
			if(action!=null && action.equalsIgnoreCase("EDIT")){
				String tourid = actval.split("/")[1];
				req.setAttribute("TourApply", service.getTourApplyData(Long.parseLong(tourid)));			
				req.setAttribute("Touronwarddetails", service.getTourOnwardReturnData(Long.parseLong(tourid)));
				req.setAttribute("ModeOfTravelList", service.GetModeofTravel());
				req.setAttribute("CityList", service.GetCityList());
				return "tour/TourApplyEdit";
			}else if(action!=null && action.equalsIgnoreCase("ForwardTour")){
				String empname =(String)ses.getAttribute("EmpName"); 

				String tourapplyid = actval.split("/")[1];
				String empno = (String) ses.getAttribute("EmpNo");
				int result  = service.ForwardTour(tourapplyid,empno);
				if (result != 0) {
					EMSNotification notification = new EMSNotification();                
					notification.setCreatedBy(empno);
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setNotificationBy(empno);
					notification.setNotificationMessage("Tour Program Forwarded  from "+empname);
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("AdminReplyToReqMsg.htm");
					notification.setIsActive(1);
					//long value= service.EmpRequestNotification(notification);
					redir.addAttribute("result", "Tour program Forward successfully");
					} else {
						redir.addAttribute("resultfail", "Tour program Forward Unsuccessfull");
					}
				return "redirect:/TourApplyList.htm";
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
			}else if(action!=null && action.equalsIgnoreCase("Revoke")){
				String UserId =req.getUserPrincipal().getName();
				String tourapplyid = actval.split("/")[1];
				 ApprovalDto dto=new ApprovalDto();
			        dto.setApplId(tourapplyid);
			        dto.setStatus("REV");
			        dto.setEmpNo((String)ses.getAttribute("EmpNo"));
					dto.setUserName(UserId);
					long result=service.RevokeTour(dto);
					if(result>0) {
						redir.addAttribute("result","Tour Revoked Successfully");
					}else{
						redir.addAttribute("resultfail","Tour Revoke Unsuccessful");
					}
					
					return "redirect:/TourApplyList.htm";
			}else{
				String Empno = (String) ses.getAttribute("EmpNo");
				List<Object[]> applylist = service.GetTourApplyList(Empno);
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
	
	@RequestMapping(value = "TourApplyStatus.htm" , method = {RequestMethod.POST, RequestMethod.GET})
	public String TourApplyStatus(HttpServletRequest req, HttpServletResponse res ,HttpSession ses)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourApplyStatus.htm "+Username);	
		try {
			String Empno = (String) ses.getAttribute("EmpNo");
			String fromdate = req.getParameter("fromdate");
			String empno = req.getParameter("empno");
			String todate =  req.getParameter("todate");
			List<Object[]> applystatuslist=null;
			if(fromdate!=null && todate!=null) {
				fromdate= DateTimeFormatUtil.RegularToSqlDate(fromdate);
				todate= DateTimeFormatUtil.RegularToSqlDate(todate);
				applystatuslist = service.GetApplyStatusList(empno, fromdate,todate);
			}else{
				empno=Empno;
				fromdate = LocalDate.now().minusDays(30).toString();
				todate = LocalDate.now().toString();
				applystatuslist = service.GetApplyStatusList(empno,fromdate,todate);
			}
			List<Object[]> emplist=service.GetEmployeeList(); 
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("empno", empno);		
			req.setAttribute("emplist", emplist);
			req.setAttribute("applyStatuslist", applystatuslist);
			ses.setAttribute("SidebarActive","TourApplyStatus_htm");

		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApplyStatus.htm "+Username, e);
			e.printStackTrace();
		}
		return "tour/TourApplyStatus";
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
			System.out.println(Result.toString());
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
	
	@RequestMapping(value = "TourApprovallist.htm" , method = RequestMethod.GET)
	public String TourApprovalList(HttpServletRequest req, HttpServletResponse res ,HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourForward.htm "+Username);
		try {
			String empno  = (String)ses.getAttribute("EmpNo");
			List<Object[]> approvallist = service.GetTourApprovalList(empno);
			List<Object[]> Canceledlist = service.GetTourCancelList(empno);
			req.setAttribute("approvallist", approvallist);
			req.setAttribute("canceledlist", Canceledlist);
			ses.setAttribute("SidebarActive","TourApprovallist_htm");
			return "tour/TourApprovalList";
		}catch (Exception e){
			logger.error(new Date() +" Inside TourForward.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "DeptInchApproval.htm" ,method = {RequestMethod.POST , RequestMethod.GET})
	public String ApproveDeptInch(HttpServletRequest req, HttpServletResponse res ,HttpSession ses, RedirectAttributes redir)throws Exception
	{
			String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside DeptInchApproval.htm "+UserId);
		try {
			 ApprovalDto dto=new ApprovalDto();
	        dto.setApprove( req.getParameterValues("approve"));
	        dto.setReject( req.getParameterValues("reject"));
	        dto.setEmpNo((String)ses.getAttribute("EmpNo"));
			dto.setUserName(UserId);
			int result=service.GetDeptInchApproved(dto,req);
			if(result>0) {
				redir.addAttribute("result","Tour Transaction Successfull");
			}else{
				redir.addAttribute("resultfail","Tour Transaction Unsuccessful");
			}
			
			return "redirect:/TourApprovallist.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside DeptInchApproval.htm "+UserId, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "Tour-Status-details.htm" , method={RequestMethod.POST,RequestMethod.GET})
	public String TourStatusDetails(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside Tour-Status-details.htm "+Username);
		try {
			String chssapplyid = req.getParameter("tourapplyid");
			req.setAttribute("TourStatisDetails", service.TourStatusDetails(chssapplyid));
			
			return "tour/TourStatus";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside Tour-Status-details.htm "+Username, e);
			return "static/Error";
		}
	}
	@RequestMapping(value = "TourSanctionedlist.htm" , method = {RequestMethod.GET , RequestMethod.POST })
	public String SanctionedList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourSanctionedlist.htm "+Username);
		try {
			
			List<Object[]> sanclist = service.GetSanctionList((String)ses.getAttribute("EmpNo"));
 			
			req.setAttribute("SanctionList", sanclist);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TourSanctionedlist.htm "+Username, e);
			return "static/Error";
		}
		return "tour/TourSanctionList";
	}
	
	@RequestMapping(value = "TourCancel.htm" , method = { RequestMethod.GET,RequestMethod.POST})
	public String TourCancel(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside TourCancel.htm "+UserId);
		try {
			ses.setAttribute("SidebarActive","TourCancel_htm");

			String action= req.getParameter("Action");
			String act="";
			
			if(action!=null ) {
				act= action.split("/")[0];
			}
			
			System.out.println(action);
			if(action!=null && "CANCEL".equalsIgnoreCase(act)){
				String tourid  = action.split("/")[1].toString();
				 ApprovalDto dto=new ApprovalDto();
				 	dto.setStatus("CBU");
				 	dto.setApplId(tourid);
			        dto.setEmpNo((String)ses.getAttribute("EmpNo"));
					dto.setUserName(UserId);
					int result=service.CancelTour(dto);
					if(result>0) {
						redir.addAttribute("result","Tour Cancel Successfull");
					}else{
						redir.addAttribute("resultfail","Tour Cancel Unsuccessful");
					}
					return "redirect:/TourSanctionedlist.htm";
			}else {

				String Empno = (String) ses.getAttribute("EmpNo");
				String fromdate = req.getParameter("fromdate");
				String empno = req.getParameter("empno");
				String todate =  req.getParameter("todate");
				List<Object[]> applystatuslist=null;
				if(fromdate!=null && todate!=null) {
					fromdate= DateTimeFormatUtil.RegularToSqlDate(fromdate);
					todate= DateTimeFormatUtil.RegularToSqlDate(todate);
					applystatuslist = service.GetTourCancelList(empno, fromdate,todate);
				}else{
					empno=Empno;
					fromdate = LocalDate.now().minusDays(30).toString();
					todate = LocalDate.now().toString();
					applystatuslist = service.GetTourCancelList(empno,fromdate,todate);
				}
				List<Object[]> emplist=service.GetEmployeeList(); 
					req.setAttribute("fromdate", fromdate);
					req.setAttribute("todate", todate);
					req.setAttribute("empno", empno);		
					req.setAttribute("emplist", emplist);
					req.setAttribute("cancelStatuslist", applystatuslist);
					ses.setAttribute("SidebarActive","TourCancel_htm");

				return "tour/TourCancelStatus";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TourCancel.htm "+UserId, e);
			return "static/Error";
		}
		
	}
	@RequestMapping(value = "Tour-CancelStatus-details.htm" , method={RequestMethod.POST,RequestMethod.GET})
	public String TourCancelStatusDetails(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside Tour-CancelStatus-details.htm "+Username);
		try {
			String chssapplyid = req.getParameter("tourapplyid");
			req.setAttribute("TourStatisDetails", service.TourCancelStatusDetails(chssapplyid));
			
			return "tour/TourStatus";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside Tour-CancelStatus-details.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CancelApproval.htm" ,method = {RequestMethod.POST , RequestMethod.GET})
	public String CancelApproval(HttpServletRequest req, HttpServletResponse res ,HttpSession ses, RedirectAttributes redir)throws Exception
	{
			String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside CancelApproval.htm "+UserId);
		try {
			 ApprovalDto dto=new ApprovalDto();
	        dto.setApprove( req.getParameterValues("approve"));
	        dto.setReject( req.getParameterValues("reject"));
	        dto.setEmpNo((String)ses.getAttribute("EmpNo"));
			dto.setUserName(UserId);
			int result=service.GetCancelApproved(dto,req);
			if(result>0) {
				redir.addAttribute("result","Tour Transaction Successfull");
			}else{
				redir.addAttribute("resultfail","Tour Transaction Unsuccessful");
			}
			
			return "redirect:/TourApprovallist.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside CancelApproval.htm "+UserId, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
}


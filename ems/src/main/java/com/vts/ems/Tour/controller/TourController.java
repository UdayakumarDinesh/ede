package com.vts.ems.Tour.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.Tour.dto.TourApplyDto;
import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.service.TourService;
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.noc.service.NocService;
import com.vts.ems.pis.service.PisService;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;

@Controller
public class TourController {

	private static final Logger logger = LogManager.getLogger(TourController.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@Autowired
	private TourService service;
	@Autowired
	private PisService pisserv;
	
	@Autowired
	NocService nocservice;
	
	@Autowired
	EmsFileUtils emsfileutils ;
	
	@Value("${ProjectFiles}")
	private String LabLogoPath;
	
	
	 public String getLabLogoAsBase64() throws IOException {

			String path = LabLogoPath + "/images/lablogos/lablogo1.png";
			try {
				return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(path)));
			} catch (FileNotFoundException e) {
				System.err.println("File Not Found at Path " + path);
			}
			return "/print/.jsp";
		}
	
	@RequestMapping(value = "TourProgram.htm", method = RequestMethod.GET)
	public String TourDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourProgram.htm "+Username);		
		try {
			    req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
			req.setAttribute("ApprovalEmp", service.GetApprovalEmp(ses.getAttribute("EmpNo").toString()));
			req.setAttribute("ModeOfTravelList", modeoftravel);
			req.setAttribute("CityList", citylist);
			req.setAttribute("emplist", emplist);
		    req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
	public String TourApplyList(HttpServletResponse res ,  HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourApplyList.htm "+Username);	
		try {
			String actval = req.getParameter("Action");
			String  action= null;
			if(actval!=null) {
				action=actval.split("/")[0];
			}
			
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
			}else if(action!=null && action.equalsIgnoreCase("Preview")) {
				String tourapplyid = actval.split("/")[1];
				Object[] details = service.GetTourDetails(tourapplyid);
				req.setAttribute("tourdetails", details);
				req.setAttribute("Touronwarddetails", service.getTourOnwardReturnDetails(tourapplyid));
				req.setAttribute("ApprovalEmp", service.GetApprovalEmp(details[2].toString()));
				req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
				
				return "tour/TourDetailsPreview";
			}else{
				String Empno = (String) ses.getAttribute("EmpNo");
				List<Object[]> applylist = service.GetTourApplyList(Empno);
				List<Object[]> emplist=service.GetEmployeeList(); 
				req.setAttribute("emplist", emplist);
				req.setAttribute("applylist", applylist);
			    req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
		    req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
			String tourid = request.getParameter("tourapplyid");
			String action = request.getParameter("action");
			String empno = (String) ses.getAttribute("EmpNo");
			String DepartureDate = request.getParameter("DepartureDate");
			String ArrivalDate = request.getParameter("ArrivalDate");
			System.out.println("tourid     :"+tourid);
			System.out.println("action     :"+action);
			System.out.println("empno     :"+empno);
			System.out.println("DepartureDate     :"+DepartureDate);
			System.out.println("ArrivalDate     :"+ArrivalDate);
			Result= service.checkTDAlreadyPresentForSameEmpidAndSameDates( empno, DepartureDate, ArrivalDate , action , tourid);
			
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
			req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
			req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
			req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
			req.setAttribute("SanctionList", sanclist);
			ses.setAttribute("SidebarActive","TourSanctionedlist_htm");

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TourSanctionedlist.htm "+Username, e);
			return "static/Error";
		}
		return "tour/TourSanctionList";
	}
	
	@RequestMapping(value = "TourCancelledlist.htm" , method = {RequestMethod.GET , RequestMethod.POST })
	public String CanceledList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TourCancelledlist.htm "+Username);
		try {
			
			List<Object[]> cancellist = service.GetCancelList((String)ses.getAttribute("EmpNo"));
			ses.setAttribute("SidebarActive","TourCancelledlist_htm");
			req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
			req.setAttribute("CacncelList", cancellist);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside TourSanctionedlist.htm "+Username, e);
			return "static/Error";
		}
		return "tour/TourCancelList";
	}
	
	@RequestMapping(value = "TourCancel.htm" , method = { RequestMethod.GET,RequestMethod.POST})
	public String TourCancel(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside TourCancel.htm "+UserId);
		try {
			String action= req.getParameter("Action");
			String act="";
			
			if(action!=null ) {
				act= action.split("/")[0];
			}		
			if(action!=null && "CANCEL".equalsIgnoreCase(act)){
				String tourid  = action.split("/")[1].toString();
				 ApprovalDto dto=new ApprovalDto();
				 	dto.setValue(req.getParameter("reason"));
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
					req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));
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
	
	@RequestMapping(value = "DownloadTourProposal.htm")
	public void TourProposalDownload(Model model,HttpServletRequest req, HttpSession ses,HttpServletResponse res)throws Exception 
	{

		String UserId = (String) ses.getAttribute("Username");

		logger.info(new Date() +"Inside DownloadTourProposal.htm "+UserId);
		
		try {	
			String actval = req.getParameter("Action");
			String tourapplyid = actval.split("/")[1];
			Object[] details = service.GetTourDetails(tourapplyid);
			req.setAttribute("tourdetails", details);
			req.setAttribute("Touronwarddetails", service.getTourOnwardReturnDetails(tourapplyid));
			req.setAttribute("ApprovalEmp", service.GetApprovalEmp(details[2].toString()));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			
			String filename="TourProposal";
		
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("pagePart","3" );
			
			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
	        req.getRequestDispatcher("/view/tour/TourProposalPrint.jsp").forward(req, customResponse);

			
			String html = customResponse.getOutput();        
	        
	        HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 
	         
	        res.setContentType("application/pdf");
	        res.setHeader("Content-disposition","attachment;filename="+filename+".pdf");
	       
	       
	        emsfileutils.addWatermarktoPdf(path +File.separator+ filename+".pdf",path +File.separator+ filename+"1.pdf",(String) ses.getAttribute("LabCode"));
	        
	        
	        File f=new File(path +File.separator+ filename+".pdf");
	        FileInputStream fis = new FileInputStream(f);
	        DataOutputStream os = new DataOutputStream(res.getOutputStream());
	        res.setHeader("Content-Length",String.valueOf(f.length()));
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        while ((len = fis.read(buffer)) >= 0) {
	            os.write(buffer, 0, len);
	        } 
	        os.close();
	        fis.close();
	       
	       
	        Path pathOfFile= Paths.get( path+File.separator+filename+".pdf"); 
	        Files.delete(pathOfFile);		
	       	
		}
		catch (Exception e) {
			e.printStackTrace();  
			logger.error(new Date() +" Inside DownloadTourProposal.htm "+UserId, e); 
		}
	}
	
	@RequestMapping(value = "TourApplyReport.htm" ,method = {RequestMethod.POST , RequestMethod.GET})
	public String TourApplyReport(HttpServletRequest req, HttpServletResponse res ,HttpSession ses, RedirectAttributes redir)throws Exception
	{
			String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside TourApplyReport.htm "+UserId);
		try {
				String action = req.getParameter("tourapplyid");
				String actval = action.split("/")[0];
				String tourapplyid =  action.split("/")[1];
				req.setAttribute("Labmaster", nocservice.getLabMasterDetails().get(0));
				req.setAttribute("lablogo",getLabLogoAsBase64());
				if (action!=null &&  actval.equalsIgnoreCase("Cancel"))
				{
					Object[] details=service.GetTourDetails(tourapplyid);
					req.setAttribute("tourdetails",details );
					req.setAttribute("Touronwarddetails", service.getTourOnwardReturnDetails(tourapplyid));
					req.setAttribute("ApprovalEmp", service.GetApprovalEmp(details[2].toString()));
					
					return "tour/TourCancelMovementOrder";
					

				}else {
					Object[] details = service.GetTourDetails(tourapplyid);
					req.setAttribute("tourdetails", details);
					req.setAttribute("Touronwarddetails", service.getTourOnwardReturnDetails(tourapplyid));
					req.setAttribute("ApprovalEmp", service.GetApprovalEmp(details[2].toString()));
					return "tour/TourApplyMovementOrder";
				}
			    

		} catch (Exception e) {
			logger.error(new Date() +" Inside TourApplyReport.htm "+UserId, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "TourModify.htm" , method = { RequestMethod.POST , RequestMethod.GET})
	public String TourModify(HttpServletRequest req, HttpServletResponse res ,HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String UserId =req.getUserPrincipal().getName();
		logger.info(new Date() +"Inside TourApplyReport.htm "+UserId);
		try {
			String action = req.getParameter("tourapplyid");
			String actval = action.split("/")[0];
			String tourapplyid =  action.split("/")[1];
			System.out.println("tourapplyid     :"+tourapplyid);
			if(action!=null && actval!=null && actval.equalsIgnoreCase("Modify")) {
				
				req.setAttribute("ApprovalEmp", service.GetApprovalEmp(ses.getAttribute("EmpNo").toString()));
				req.setAttribute("TourApply", service.getTourApplyData(Long.parseLong(tourapplyid)));			
				req.setAttribute("Touronwarddetails", service.getTourOnwardReturnData(Long.parseLong(tourapplyid)));
				req.setAttribute("ModeOfTravelList", service.GetModeofTravel());
				req.setAttribute("CityList", service.GetCityList());
			    req.setAttribute("Empdata", pisserv.GetEmpData(ses.getAttribute("EmpId").toString()));

			}
			return "tour/TourModify";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TourModify.htm "+UserId, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
}


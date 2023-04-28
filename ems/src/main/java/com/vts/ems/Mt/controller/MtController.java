package com.vts.ems.Mt.controller;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.Mt.model.MtApplyTransaction;
import com.vts.ems.Mt.model.MtDirectorDuty;
import com.vts.ems.Mt.model.MtDriver;
import com.vts.ems.Mt.model.MtLinkDuty;
import com.vts.ems.Mt.model.MtTrip;
import com.vts.ems.Mt.model.MtUserApply;
import com.vts.ems.Mt.model.MtVehicle;
import com.vts.ems.Mt.service.MtService;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class MtController {

	private int year = Calendar.getInstance().get(Calendar.YEAR);
	private int month = Calendar.getInstance().get(Calendar.MONTH)+1;
	private int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	private static final Logger logger = LogManager.getLogger(MtController.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	 SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	 SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sf= DateTimeFormatUtil.getSqlDateFormat();
	
	String formmoduleid = "30";
	
	@Autowired                   
	private MtService service;
	
	@Autowired
	AdminService adminservice;
	
	@RequestMapping(value = "MtDashboard.htm", method = RequestMethod.GET)
	public String MtDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtDashboard.htm "+Username);		
		try {
			
			String logintype = (String)ses.getAttribute("LoginType");
			List<Object[]> admindashboard = adminservice.HeaderSchedulesList("5" ,logintype); 
		
			ses.setAttribute("formmoduleid", formmoduleid ); 
			ses.setAttribute("SidebarActive", "MtDashboard_htm");
			req.setAttribute("dashboard", admindashboard);
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			
			return "Mt/MtDashboard";
		}catch (Exception e) {
			logger.error(new Date() +" Inside MtDashboard.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	

	
	@RequestMapping(value ="MtRequestTrip.htm" ,method = {RequestMethod.POST,RequestMethod.GET})
	public String MtUserApply(HttpServletRequest req, HttpSession ses , RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtRequestTrip.htm "+Username);	
		try {		
			List<Object[]> typeofduty = (List<Object[]>)service.GetDutyType();
			List<Object[]> projectlist = (List<Object[]>)service.GetProjectList();
			 List<Object[]> listapply=service.VehiclePendingListDashBoard();
				
				List<Object[]> firstapply=null;
				List<Object[]> secondapply=null;
				List<Object[]> thirdapply=null;
				List<Object[]> fourthapply=null;
				List<Object[]> fifthpply=null;
			
			 firstapply=new ArrayList<Object[]>();
			 secondapply=new ArrayList<Object[]>();
			 thirdapply=new ArrayList<Object[]>();
			 fourthapply=new ArrayList<Object[]>();
			 fifthpply=new ArrayList<Object[]>();
			
			 for(Object[] list:listapply) {
				
				 	Date date1=sdf.parse(sdf.format(list[1]));
					Date date11=sdf.parse(sdf.format(new Date()));
					Date date2=sdf.parse(sdf.format(new Date(new Date().getTime()+(24*60*60*1000))));
					Date date3=sdf.parse(sdf.format(new Date(new Date().getTime()+(2*24*60*60*1000))));
					Date date4=sdf.parse(sdf.format(new Date(new Date().getTime()+(3*24*60*60*1000))));
					Date date5=sdf.parse(sdf.format(new Date(new Date().getTime()+(4*24*60*60*1000))));
				if(date1.compareTo(date11) == 0) {
					firstapply.add(list);	
				}
				if(date1.compareTo(date2) == 0) {
					secondapply.add(list);	
				}
				if(date1.compareTo(date3) == 0) {
					thirdapply.add(list);	
				}
				if(date1.compareTo(date4) == 0) {
					fourthapply.add(list);	
				}
				if(date1.compareTo(date5) == 0) {
					fifthpply.add(list);	
				}
			}

			req.setAttribute("firstapply", firstapply);
			req.setAttribute("secondapply", secondapply);
			req.setAttribute("thirdapply", thirdapply);
			req.setAttribute("fourthapply", fourthapply);
			req.setAttribute("fifthpply", fifthpply);
			req.setAttribute("linkrequestlist",service.VehicleAssignedListDashBoard());

			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			req.setAttribute("typedutylist", typeofduty);
			req.setAttribute("projectlist", projectlist);
			ses.setAttribute("SidebarActive", "MtRequestTrip_htm");
			return "Mt/MtUserApply";
		}catch (Exception e) {
			logger.error(new Date() +" Inside MtRequestTrip.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	
	}
	
	
	@RequestMapping(value ="MtUserApplyAddEdit.htm" ,method = RequestMethod.POST)
	public String GetProjectList(HttpServletRequest req, HttpSession ses , RedirectAttributes redir)throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtUserApplyAddEdit.htm "+Username);
		String logintype = (String)ses.getAttribute("LoginType");
		try {	
			
			String RecOfficer = service.GetRecOfficer((String)ses.getAttribute("EmpNo")); 
			if(logintype.equalsIgnoreCase("U")&& RecOfficer==null) {
				redir.addAttribute("resultfail","Recommanding Officer Not Assigned!");
				return "redirect:/MtList.htm";
			}
			
			String mtapplid = req.getParameter("mtapplId");			
				MtUserApply user=new MtUserApply();		
					user.setEmpId((String)ses.getAttribute("EmpNo"));
					user.setDateOfTravel(DateTimeFormatUtil.dateConversionSql(req.getParameter("Dtravel")));
					user.setEndDateOfTravel(DateTimeFormatUtil.dateConversionSql(req.getParameter("Etravel")));
					user.setStartTime(req.getParameter("Dtime"));
					user.setEndTime(req.getParameter("Rtime"));
					user.setSource(req.getParameter("Source"));
					user.setDestination(req.getParameter("Destination"));
					user.setDutyTypeId(Integer.parseInt(req.getParameter("TypeOfDuty")));
					int others=Integer.parseInt(req.getParameter("TypeOfDuty"));
					if(others==4) {user.setMtReason(req.getParameter("Reason"));}		
					user.setOneWayDistance(Integer.parseInt(req.getParameter("Distance")));
					user.setContactNo(req.getParameter("Contact"));
					user.setUserRemarks(req.getParameter("Remarks"));
					user.setProjectId(Integer.parseInt(req.getParameter("Budget")));

			
			if(mtapplid!=null && mtapplid!="")
			{
				/* Editing Mt Details */
				int count=0;
				//user.setMtReqNo(req.getParameter("ReqNo"));
				user.setMtApplId(Integer.parseInt(mtapplid));
				user.setMtStatus("A");
				user.setModifiedBy((String)ses.getAttribute("Username"));
				user.setModifiedDate(sdtf.format(new Date()));

				if(user.getDateOfTravel().equals(user.getEndDateOfTravel())||user.getEndDateOfTravel().after(user.getDateOfTravel())){
				count=service.UserApplyEdit( user);
				}
				if(count!=0) {
					MtApplyTransaction trx=new MtApplyTransaction();
					trx.setMtStatus("E");
					trx.setMtRemarks(req.getParameter("Remarks"));
					trx.setMtApplId(Integer.parseInt(req.getParameter("mtapplId")));
					trx.setActionBy((String)ses.getAttribute("EmpNo"));
					trx.setActionDate(sdtf.format(new Date()));
					service.MtApplyTranscation(trx);	
				}
				
				if(count!=0) {
					redir.addAttribute("result","Transaction Successful");
				}
				else {
					redir.addAttribute("resultfail","Transaction  UnSuccessful");					
				}

					return "redirect:/MtList.htm";
				
			}else{
				
			/* Adding Mt Details */
			int count=0;
			int no=0;
			String reqno=null;
			try {
			  reqno=service.GetLastReqNo();
			 
			}catch(Exception e){
				no=1;
			}
			if(reqno!=null) 
			{
				String [] str=reqno.split("/");			
				no=Integer.parseInt(str[1])+1;
				if(Integer.parseInt(str[0])!=year) {no=1;}			
			}
			String MtReqNo=year+"/"+no;
			
			user.setMtReqNo(MtReqNo);
			user.setCreatedBy((String)ses.getAttribute("Username"));			
			user.setCreatedDate(sdtf.format(new Date()));
			Object[]  empdesign = (Object[])service.getEmpDesig((Long)ses.getAttribute("EmpId"));
			
			user.setMtStatus("A");
			user.setIsActive(1);
			user.setApplDate(new java.sql.Date(new Date().getTime()));		
			
			if(user.getDateOfTravel().equals(user.getEndDateOfTravel())||user.getEndDateOfTravel().after(user.getDateOfTravel())){			   
				count=service.UserApply( user);
			}
			if(count!=0) {
				MtApplyTransaction trx=new MtApplyTransaction();
				if(empdesign!=null && empdesign[2]!=null && "4".equalsIgnoreCase(empdesign[2].toString()) || "5".equalsIgnoreCase(empdesign[2].toString()) || "6".equalsIgnoreCase(empdesign[2].toString()) || "7".equalsIgnoreCase(empdesign[2].toString()) || "8".equalsIgnoreCase(empdesign[2].toString()) || "9".equalsIgnoreCase(empdesign[2].toString())) {
					trx.setMtStatus("V");
				}else {
					trx.setMtStatus("A");
				}
				trx.setMtRemarks(req.getParameter("Remarks"));
				trx.setMtApplId(count);
				trx.setActionBy((String)ses.getAttribute("EmpNo"));
				trx.setActionDate(sdtf.format(new Date()));
			service.MtApplyTranscation(trx);
				
			
			
		   List<BigInteger> mtoemp = (List<BigInteger>)service.GetMtoEmployeeList();

				if(empdesign!=null && empdesign[2]!=null && "4".equalsIgnoreCase(empdesign[2].toString()) || "5".equalsIgnoreCase(empdesign[2].toString()) || "6".equalsIgnoreCase(empdesign[2].toString()) || "7".equalsIgnoreCase(empdesign[2].toString()) || "8".equalsIgnoreCase(empdesign[2].toString()) || "9".equalsIgnoreCase(empdesign[2].toString())) {
					
					for(BigInteger str:mtoemp) {
						EMSNotification notification = new EMSNotification();
						notification.setEmpNo((str.toString()));
						notification.setNotificationBy((String)ses.getAttribute("EmpNo"));
						notification.setNotificationDate(sdtf.format(new Date()));
						notification.setNotificationMessage("Trip Requested by "+ (String)ses.getAttribute("EmpName") + "is Pending for approval");
						notification.setNotificationUrl("MTTripLink.htm");
						notification.setCreatedBy(Username);
						notification.setCreatedDate(sdtf.format(new Date()));
						notification.setIsActive(1);
						service.MtEmsNotification(notification);
					}
				}else {
					EMSNotification notification = new EMSNotification();
					//String empid = service.GetRecOfficer((String)ses.getAttribute("EmpNo")); 
					notification.setEmpNo(RecOfficer);
					notification.setNotificationBy((String)ses.getAttribute("EmpNo"));
					notification.setIsActive(1);
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationUrl("MtGhApprove.htm");
					notification.setNotificationMessage(" Mt Request from "+(String)ses.getAttribute("EmpName"));
					notification.setCreatedBy((String)ses.getAttribute("Username"));
					notification.setCreatedDate(sdtf.format(new Date()));
					service.MtEmsNotification(notification);
				}
			}
			
			if(count!=0) {
				redir.addAttribute("result","Trip Request Successful");
			}else{
				redir.addAttribute("resultfail","Trip Request  UnSuccessful");
				return "redirect:/MtUserApplyC.htm?sub=add";
			}
					return "redirect:/MtList.htm";
		}
			
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtUserApplyAddEdit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}	
	
	@RequestMapping(value="MtList.htm" ,method=RequestMethod.GET)
	public String MtList(HttpServletRequest req,HttpSession ses) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtList.htm "+Username);
		try {
			ses.setAttribute("formmoduleid", formmoduleid);

			req.setAttribute("Mtapplylist",service.GetApplyDataOFApplyStatus((String)ses.getAttribute("EmpNo")));
			req.setAttribute("Mtapplysanclist",service.GetApplyDataOfSancApplyStatus((String)ses.getAttribute("EmpNo")));	
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			
			ses.setAttribute("SidebarActive", "MtList_htm");
			return "Mt/MtList";
			
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		

	}
	
	
	@RequestMapping(value="MtUserApplyC.htm",method= {RequestMethod.POST,RequestMethod.GET})
	public String UserApplyLunch(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtUserApplyC.htm "+Username);
		
		try {

			String submit=req.getParameter("sub");
			if(submit.equalsIgnoreCase("add")) {
				req.setAttribute("comp", "userapply");
				req.setAttribute("typedutylist",service.GetDutyType());
				req.setAttribute("projectlist",service.GetProjectList());
			 List<Object[]> listapply=service.VehiclePendingListDashBoard();
				
					List<Object[]> firstapply=null;
					List<Object[]> secondapply=null;
					List<Object[]> thirdapply=null;
					List<Object[]> fourthapply=null;
					List<Object[]> fifthpply=null;
				
				 firstapply=new ArrayList<Object[]>();
				 secondapply=new ArrayList<Object[]>();
				 thirdapply=new ArrayList<Object[]>();
				 fourthapply=new ArrayList<Object[]>();
				 fifthpply=new ArrayList<Object[]>();
				
				 for(Object[] list:listapply) {
					if(sdtf.format(new Date()).equalsIgnoreCase(sdtf.format(list[1]))) {
						firstapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						secondapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(2*24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						thirdapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(3*24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						fourthapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(4*24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						fifthpply.add(list);	
					}
					
				}
				req.setAttribute("firstapply", firstapply);
				req.setAttribute("secondapply", secondapply);
				req.setAttribute("thirdapply", thirdapply);
				req.setAttribute("fourthapply", fourthapply);
				req.setAttribute("fifthpply", fifthpply);
				req.setAttribute("linkrequestlist",service.VehicleAssignedListDashBoard());
				  
				  Object[] emp=service.getEmpData((String)ses.getAttribute("EmpId")); 
				  String Name=emp[1]+""+emp[2];
				  req.setAttribute("empname",Name);
				return "Mt/MtUserApply";
			
			}
			if(submit.equalsIgnoreCase("edit")){
				
				  req.setAttribute("editdata",service.getApplySingleData(Integer.parseInt(req.getParameter("MtApplyId"))));
				  req.setAttribute("comp","edit");
				  req.setAttribute("typedutylist",service.GetDutyType()); 

				  List<Object[]> listapply=service.VehiclePendingListDashBoard();
					
					List<Object[]> firstapply=null;
					List<Object[]> secondapply=null;
					List<Object[]> thirdapply=null;
					List<Object[]> fourthapply=null;
					List<Object[]> fifthpply=null;
				
				 firstapply=new ArrayList<Object[]>();
				 secondapply=new ArrayList<Object[]>();
				 thirdapply=new ArrayList<Object[]>();
				 fourthapply=new ArrayList<Object[]>();
				 fifthpply=new ArrayList<Object[]>();
				
				 for(Object[] list:listapply) {
					if(sdtf.format(new Date()).equalsIgnoreCase(sdtf.format(list[1]))) {
						firstapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						secondapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(2*24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						thirdapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(3*24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						fourthapply.add(list);	
					}
					
					if(sdtf.format(new Date(new Date().getTime()+(4*24*60*60*1000))).equalsIgnoreCase(sdtf.format(list[1]))) {
						fifthpply.add(list);	
					}
					
				}
				req.setAttribute("firstapply", firstapply);
				req.setAttribute("secondapply", secondapply);
				req.setAttribute("thirdapply", thirdapply);
				req.setAttribute("fourthapply", fourthapply);
				req.setAttribute("fifthpply", fifthpply);
				req.setAttribute("linkrequestlist",service.VehicleAssignedListDashBoard());

				  Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
				  String  Name=emp[1]+" "+emp[2];
				  req.setAttribute("empname",Name);
				  req.setAttribute("projectlist",service.GetProjectList());
				  ses.setAttribute("SidebarActive", "MtRequestTrip_htm");
					return "Mt/MtUserApply";
		
			}else {
				MtUserApply apply=new MtUserApply();
				apply.setMtApplId(Integer.parseInt(req.getParameter("MtApplyId")));
				apply.setModifiedBy((String)ses.getAttribute("Username"));
				apply.setModifiedDate(sdtf.format(new Date()));
				int count=service. UserApplyCancel(apply);
				
				 if(count!=0) { 
					 MtApplyTransaction trx=new MtApplyTransaction();
					  trx.setMtStatus("D"); 
					  trx.setMtRemarks("Delete By User");
					  trx.setMtApplId(Integer.parseInt(req.getParameter("MtApplyId")));
					  trx.setActionBy((String)ses.getAttribute("EmpNo"));
				      trx.setActionDate(sdtf.format(new Date()));
				  service.MtApplyTranscation(trx);
				  
				  } 
				if(count!=0) { 
					  redir.addAttribute("result","Transaction Successful"); 
				}else{ 
					  redir.addAttribute("resultfail","Transaction UnSuccessful"); 
				}
				 
				return "redirect:/MtList.htm";		
			}
			
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtUserApplyC.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}

	}

	@RequestMapping(value="MTHome.htm",method=RequestMethod.GET)
	public String MTHome(HttpServletRequest req,HttpSession ses) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTHome.htm "+Username);
		try {
			List<Object[]> listapply=service.getApplyList((String)ses.getAttribute("EmpNo"));
			
			List<Object[]> firstapply=null;
			List<Object[]> secondapply=null;

			 firstapply=new ArrayList<Object[]>();
			 secondapply=new ArrayList<Object[]>();

			 for(Object[] list:listapply) {
				
				if(sdtf.format(new Date()).equalsIgnoreCase(sdtf.format(list[4]))) {
					
					firstapply.add(list);
				}else{
					secondapply.add(list);
				}
	
			}
			req.setAttribute("firstapply", firstapply);
			req.setAttribute("secondapply", secondapply);
		
			req.setAttribute("AllListApp",service.getApplyList((String)ses.getAttribute("EmpNo")));
			req.setAttribute("DriverList", service.getDriverList());
			req.setAttribute("vehiclelist",service.GetVechileList());
			req.setAttribute("triplist",service.getListOfTrip());
			req.setAttribute("linkrequestlist",service.GetLinkRequest());
			
			ses.setAttribute("formmoduleid", formmoduleid); 
			ses.setAttribute("SidebarActive", "MTHome_htm");
			
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/AssignTrip";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MTHome.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value="MTTrip.htm",method=RequestMethod.POST)
	public String MTTripInsertEdit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTTrip.htm "+Username);
		try {
			String tripid = req.getParameter("TripId");
			if(tripid!=null) {
				// edit trip 
				int count=0;
				MtTrip trip=new MtTrip();
				String Hiredvechile=req.getParameter("Hired");
				trip.setTripId(Integer.parseInt(tripid));
				trip.setDriverId(Integer.parseInt(req.getParameter("Driver")));
				if(Hiredvechile!="") {
					trip.setHiredVehicle(Hiredvechile);
				}else {
					trip.setVehicleId(Integer.parseInt(req.getParameter("Vehicle")));
				}
				
				trip.setPlace(req.getParameter("Place"));
				trip.setMtoComments(req.getParameter("Comments"));
				trip.setTripDate(DateTimeFormatUtil.dateConversionSql(req.getParameter("DutyDate")));
				trip.setTripEndDate(DateTimeFormatUtil.dateConversionSql(req.getParameter("DutyEndDate")));
				trip.setStartTime(req.getParameter("STime"));
				trip.setEndTime(req.getParameter("ETime"));
				trip.setModifiedby(Username);
				trip.setModifiedDate(sdtf.format(new Date()));
				if(trip.getTripDate().equals(trip.getTripEndDate())||trip.getTripEndDate().after(trip.getTripDate())){
			    count=service.EditTrip(trip);
				}
				if(count!=0) {
					redir.addAttribute("result","Trip Edited Successfully");
				}
				else {
					redir.addAttribute("resultfail","Trip Edit UnSuccessful/End Date after or equal Start Date");
				}
				return "redirect:/MTHome.htm";
				
				
			}else {
				//add trip
			int count=0;
			MtTrip trip=new MtTrip();
			int VehicleId=Integer.parseInt(req.getParameter("Vehicle"));
			String Hiredvechile=req.getParameter("Hired");
			int no=0;
			try {
			  String reqno=service.getLastTripNo();
			  String [] str=reqno.split("/");
			  
			   no=Integer.parseInt(str[1])+1;
			  if(Integer.parseInt(str[0])!=year) 
			  { no=1;
			  }
			}catch(Exception e) {
				no=1;
			}
			
			//trip no change
			  String MtTripNo=year+"/"+no;
			trip.setTripNo(MtTripNo);
			trip.setDriverId(Integer.parseInt(req.getParameter("Driver")));
			if(VehicleId==0){
				trip.setHiredVehicle(Hiredvechile);
			
			}else{
				trip.setVehicleId(Integer.parseInt(req.getParameter("Vehicle")));
			}
			trip.setPlace(req.getParameter("Place"));
			trip.setMtoComments(req.getParameter("Comments"));
			trip.setTripDate(DateTimeFormatUtil.dateConversionSql(req.getParameter("DutyDate")));
			trip.setTripEndDate(DateTimeFormatUtil.dateConversionSql(req.getParameter("DutyEndDate")));
			trip.setStartTime(req.getParameter("STime"));
			trip.setEndTime(req.getParameter("ETime"));
			trip.setCreatedBy((String)ses.getAttribute("EmpNo"));
			trip.setCreatedDate(sdtf.format(new Date()));
			trip.setIsActive(1);
			if(trip.getTripDate().equals(trip.getTripEndDate())||trip.getTripEndDate().after(trip.getTripDate())){
			count=service.Inserttrip(trip);
			}
			if(count!=0) {
				redir.addAttribute("result","Trip Create Successful");
			}
			else {
				redir.addAttribute("resultfail","Trip Create UnSuccessful");
			}
			return "redirect:/MTHome.htm";
	
		}

		} catch (Exception e) {
			logger.error(new Date() +" Inside MTTrip.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value="MtGhApprove.htm",method=RequestMethod.GET)
	public String MtApprove(HttpServletRequest req,HttpSession ses) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtGhApprove.htm "+Username);
		try {
			req.setAttribute("GhApproveList", service.GhApproveList((String)ses.getAttribute("EmpNo")));	
			ses.setAttribute("SidebarActive", "MtGhApprove_htm");
			ses.setAttribute("formmoduleid", formmoduleid);
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/MtGhApprove";	
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtGhApprove.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value="MtGhApprove.htm",method=RequestMethod.POST)
	public String MtApproveSUBMIT(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtGhApprove.htm "+Username);
		try {
			int count=0;
			MtUserApply apply=new MtUserApply();
			String[] MtApplyId=req.getParameterValues("Approve");
	
			if(MtApplyId!=null) {
			for(String str:MtApplyId) {
				apply.setMtApplId(Integer.parseInt(str.split("-")[0]));
				apply.setForwardRemarks(req.getParameter(str.split("-")[0]));
				apply.setForwardedBy((String)ses.getAttribute("EmpNo"));
				apply.setForwardedDate(sdtf.format(new Date()));
				apply.setModifiedBy(Username);
				apply.setModifiedDate(sdtf.format(new Date()));
				count=service.StatusUpdate(apply);
				if(count!=0) {
					MtApplyTransaction trx=new MtApplyTransaction();
					trx.setMtStatus("R");
					trx.setMtRemarks(req.getParameter(str.split("-")[0]));
					trx.setMtApplId(Integer.parseInt(str.split("-")[0]));
					trx.setActionBy((String)ses.getAttribute("EmpNo"));
					trx.setActionDate(sdtf.format(new Date()));
					service.MtApplyTranscation(trx);
					
					EMSNotification notification=new EMSNotification();
					
					String empid  = service.EmpIdOfMtRequest(Integer.parseInt(str.split("-")[0]));
					notification.setEmpNo((empid));
					notification.setNotificationBy((String)ses.getAttribute("EmpNo"));
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationMessage("Trip Request Recommended By GH");
					notification.setNotificationUrl("MtList.htm");
					notification.setCreatedBy(Username);
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setIsActive(1);
					service.MtEmsNotification(notification);
				}
			}
			List<BigInteger> mtoemp = (List<BigInteger>)service.GetMtoEmployeeList();
			
			for(BigInteger str:mtoemp) {
				for(String str1:MtApplyId) {
				EMSNotification notification=new EMSNotification();
				
				String empid  = service.EmpIdOfMtRequest(Integer.parseInt(str1.split("-")[0]));
				notification.setEmpNo(str.toString());
				notification.setNotificationBy((String)ses.getAttribute("EmpNo"));
				notification.setNotificationDate(sdtf.format(new Date()));
				notification.setNotificationMessage("Trip Requested by "+ str1.split("-")[1]+ "is Pending for approval");
				notification.setNotificationUrl("MTTripLink.htm");
				notification.setCreatedBy(Username);
				notification.setCreatedDate(sdtf.format(new Date()));
				notification.setIsActive(1);
				service.MtEmsNotification(notification);
				}
			}
			
			if(count!=0) {
				redir.addAttribute("result","Transaction Successful");
			}
			else {
				redir.addAttribute("resultfail","Transaction UnSuccessful");
			}
			}
			return "redirect:/MtGhApprove.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtGhApprove.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MtTripC.htm",method=RequestMethod.POST)
	public String MtTripC(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtTripC.htm "+Username);
		
		try {
			String action=req.getParameter("action");
			int tripid=Integer.parseInt(req.getParameter("ATripid"));
			if(action.equalsIgnoreCase("edit")){
				
				req.setAttribute("TripData",service.GetTrip(tripid) );
				req.setAttribute("DriverList", service.getDriverList());
				req.setAttribute("vehiclelist",service.GetVechileList());
				
				ses.setAttribute("SidebarActive", "MTHome_htm");

				return "Mt/AssignTrip";
			}else{
				
				int count=service.DeleteTrip(tripid);
				if(count!=0) {
					redir.addAttribute("result","Trip Deleted Successful");
				}
				else {
					redir.addAttribute("resultfail","Trip Delete UnSuccessful");
				}
				return "redirect:/MTHome.htm";
			}
		}catch (Exception e){
			logger.error(new Date() +" Inside MtTripC.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}

	}
	
	@RequestMapping(value="MTTripLink.htm",method=RequestMethod.GET)
	public String TripLink(HttpServletRequest req,HttpSession ses) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTTripLink.htm "+Username);	
		try {
			req.setAttribute("AllListApp",service.getApplyList((String)ses.getAttribute("EmpNo")));
			req.setAttribute("triplist",service.getListOfTrip());
			req.setAttribute("comp","link");
			ses.setAttribute("SidebarActive", "MTTripLink_htm");
			ses.setAttribute("formmoduleid", formmoduleid);
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			
			req.setAttribute("reqDate",req.getParameter("reqDate"));
			req.setAttribute("mtRequestNo",req.getParameter("mtRequestNo"));
			req.setAttribute("appid", req.getParameter("appidRet"));
			req.setAttribute("EmpId", req.getParameter("EmpIdRet"));
			
			return "Mt/MtLink";	
		} catch (Exception e) {
			logger.error(new Date() +" Inside MTTripLink.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value="MtTripLink.htm",method=RequestMethod.POST)
	public String MtTripLink(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTTripLink.htm "+Username);	
		try {
			MtLinkDuty duty=new MtLinkDuty();
			duty.setMtApplId(Integer.parseInt(req.getParameter("appid")));
			duty.setTripId(Integer.parseInt(req.getParameter("tripid")));
			duty.setEmpId(req.getParameter("EmpId"));
			duty.setCreatedBy((String)ses.getAttribute("EmpNo"));
			duty.setCreatedDate(sdtf.format(new Date()));
			duty.setIsActive(1);
			try {
					int count=service.insertTriplink(duty);
					if(count!=0) {
						MtApplyTransaction trx=new MtApplyTransaction();
						trx.setMtStatus("S");
						if(req.getParameter("comment")==null || "".equals(req.getParameter("comment"))) {
							trx.setMtRemarks("Link By MTO");
						}
						else {
							trx.setMtRemarks(req.getParameter("comment"));
						}
						trx.setMtApplId(Integer.parseInt(req.getParameter("appid")));
						trx.setActionBy((String)ses.getAttribute("EmpNo"));
						trx.setActionDate(sdtf.format(new Date()));
						service.MtApplyTranscation(trx);
						int tripid=Integer.parseInt(req.getParameter("tripid"));
						String Msg="Vehicle Assign By MTO";
						if(tripid==0) {
							Msg="Vehicle not available";
						}
						else {
							Msg="CMTD not available";
						}
						
						EMSNotification notification=new EMSNotification();
						notification.setEmpNo(req.getParameter("Empno"));
						notification.setNotificationBy( (String)ses.getAttribute("EmpNo"));
						notification.setNotificationDate(sdtf.format(new Date()));
						notification.setNotificationMessage(Msg);
						notification.setNotificationUrl("MTUserHome.htm");
						notification.setCreatedBy(Username);
						notification.setCreatedDate(sdtf.format(new Date()));
						notification.setIsActive(0);
						service.MtEmsNotification(notification);
		
					}
					if(count!=0) {
						redir.addAttribute("result","Request Link Successful");
					}
					else {
						redir.addAttribute("resultfail","Request Link UnSuccessful");
					}
			}
			catch(Exception e) {
				redir.addAttribute("message", "One Employee Can Not Be Added To The Same Trip");
			}
			
			return "redirect:/MTTripLink.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MTTripLink.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MtPrint.htm",method= {RequestMethod.POST,RequestMethod.GET})
	public String MtPrint(HttpServletRequest req,HttpSession ses) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtPrint.htm "+Username);	
	
		try {
			String year3=req.getParameter("month");		
			
			int year1=0;
			int month1=0;
			
			if(year3!=null) {
				String[] date=year3.split("-");
				 year1=Integer.parseInt(date[1]);
				 month1=Integer.parseInt(date[0]);
			}
			
			if(year1!=0&&month1!=0) {
				
				YearMonth yearMonth = YearMonth.of(year1, month1);

				int lengthOfMonth = yearMonth.lengthOfMonth();
				
				req.setAttribute("printlist",service.PrintList(year1+"-"+month1+"-"+"01",year1+"-"+month1+"-"+lengthOfMonth));
				req.setAttribute("month", year3);
			}else {	
				Date date =new Date();
				Calendar cal=Calendar.getInstance();
				cal.setTime(date);
			    int month=cal.get(Calendar.MONTH);
		        int year=cal.get(Calendar.YEAR);
		      int m2=month+1;
		      YearMonth m1=YearMonth.of( year,m2);
				req.setAttribute("printlist",service.PrintList(year+"-"+m2+"-"+"01",year+"-"+m2+"-"+m1.lengthOfMonth()));
				req.setAttribute("month", m2+"-"+year);
			}
			
			req.setAttribute("comp","print");
			ses.setAttribute("SidebarActive", "MtPrint_htm");
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			
			return "Mt/MtPrint";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtPrint.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}	
	}
	
	@RequestMapping(value="MtPrintReport.htm",method=RequestMethod.GET)
	public String MtPrintSubmit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtPrintReport.htm "+Username);	
		
		try {
			String  sub=req.getParameter("sub");
			
			if(sub.equalsIgnoreCase("print")) {
				  String[]  print=req.getParameterValues("printdata");
				  String [] printdata=new String[2];
				  List<List<Object[]>> print1 =new ArrayList<List<Object[]>>() ;
				  for(String str:print) { 
					  printdata=str.split("&");
					  String Empid=printdata[1];
					  int TripId=Integer.parseInt(printdata[0]);
					  print1.add(service.getPrintData(Empid,TripId));
				  } 
				  List<LabMaster> labdetails= (List<LabMaster>)service.GetLabDetails();
				  req.setAttribute("labdetails", labdetails);
				  req.setAttribute("printdata",print1);
				 
				  
			return "Mt/MtTripPrint";
			}else {
				String  print=req.getParameter("printdata");
				String[] printdata=print.split("&");
				 String Empid=printdata[1];
				 int TripId=Integer.parseInt(printdata[0]);
					int count=service.DeleteTrip(TripId);
					if(count!=0) {
						redir.addAttribute("result","Trip Deleted Successful");
					}
					else {
						redir.addAttribute("resultfail","Trip Deleted UnSuccessful");
					}	
			}
			return "redirect:/MtPrint.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtPrintReport.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value="MtDriver.htm",method=RequestMethod.GET)
	public String VehicleOperator(HttpServletRequest req,HttpSession ses,RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtDriver.htm "+Username);	
		try {
			List<Object[]> mtdriver = (List<Object[]>)service.getDriverList();
			List<Object[]> driveremplist = (List<Object[]>)service.getEmployeeList();
			req.setAttribute("driveremplist", driveremplist);
			req.setAttribute("driverlist", mtdriver);
			ses.setAttribute("SidebarActive", "MtDriver_htm");
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/MtDriver";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtDriver.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "MtDriverAddEdit.htm" ,method=RequestMethod.POST)
	public String MtDriverAddEdit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir)throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtDriverAddEdit.htm "+Username);	
		try {
			String driverid = req.getParameter("driverid");
			
			if(driverid!=null && driverid!="") {
				MtDriver driver = new MtDriver();
				driver.setDriverId(Integer.parseInt( driverid));
				driver.setIsActive(0);
				driver.setModifiedby(Username);
				driver.setModifiedDate(sdtf.format(new Date()));
				
				int result = service.InActiveDriver(driver);
				if(result>0) {	
				       redir.addAttribute("result","Driver Deleted Successful");
					}else {
						redir.addAttribute("resultfail","Driver Delete UnSuccessful");
					}	
			}else {
				String empid = req.getParameter("empid");
				MtDriver driver = new MtDriver();
				driver.setEmpId(empid);
				driver.setIsActive(1);
				driver.setCreatedBy(Username);
				driver.setCreatedDate(sdtf.format(new Date()));
				
				int result = service.AddDriver(driver);
				if(result>0) {
					
					redir.addAttribute("result","Driver Added Successful");
				}else {
					redir.addAttribute("resultfail","Driver Add UnSuccessful");
				}
				
			}
			return "redirect:/MtDriver.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtDriverAddEdit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MtVehicle.htm",method=RequestMethod.GET)
	public String Vehicle(HttpServletRequest req,HttpSession ses,RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtVehicle.htm "+Username);	
		try {
			List<MtVehicle> vehiclelist = (List<MtVehicle>)service.GetVechileList();		
			req.setAttribute("vehiclelist", vehiclelist);
			ses.setAttribute("SidebarActive", "MtVehicle_htm");
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/MtVehicle";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtVehicle.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MtVehicleC.htm",method=RequestMethod.POST)
	public String VechicleAdd(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtVehicleC.htm "+Username);	
		try {
			String action=req.getParameter("action");
				if(action.equalsIgnoreCase("Edit")) {
					req.setAttribute("vehicledata",service.GetVehicleData(Integer.parseInt(req.getParameter("vehicleid"))));
					ses.setAttribute("SidebarActive", "MtVehicle_htm");
					Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
					String Name=emp[1]+"  ("+emp[2]+")";
					req.setAttribute("empname",Name);
					return "Mt/MtVehicleAddEdit";
				}else if(action.equalsIgnoreCase("Delete")) {
					MtVehicle vehicle=new MtVehicle();
					vehicle.setVehicleId(Integer.parseInt(req.getParameter("vehicleid")));
					vehicle.setModifiedby(Username);
					vehicle.setModifiedDate(sdtf.format(new Date()));
					int count=service.DeleteVehicle(vehicle);
					if(count!=0) {
						redir.addAttribute("result","Vehicle Deleted Successful");
					}
					else {
						redir.addAttribute("resultfail","Vehicle Delete UnSuccessful");
					}
					return "redirect:/MtVehicle.htm";
				}else {
					ses.setAttribute("SidebarActive", "MtVehicle_htm");
					Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
					String Name=emp[1]+"  ("+emp[2]+")";
					req.setAttribute("empname",Name);
					return "Mt/MtVehicleAddEdit";
				}
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtVehicleC.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="MtVehicleAddEdit.htm",method=RequestMethod.POST)
	public String VechicleAddSubmit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtVehicleAddEdit.htm "+Username);	
		try {
			String vehicleid= req.getParameter("vehicleid");
			if(vehicleid!=null && vehicleid!="") {
				
				MtVehicle vehicle=new MtVehicle();
				vehicle.setVehicleName(req.getParameter("VehicleName"));
				vehicle.setBaNo(req.getParameter("BANO"));
				vehicle.setDateOfPurchase(DateTimeFormatUtil.dateConversionSql(req.getParameter("DOP")));
				vehicle.setNoOfSeat(Integer.parseInt(req.getParameter("NoOfSeat")));
				vehicle.setVehicleId(Integer.parseInt(vehicleid));
				vehicle.setModifiedby(Username);
				vehicle.setModifiedDate(sdtf.format(new Date()));
				int count=service.EditVehicle(vehicle);
				if(count!=0) {
					redir.addAttribute("result","Vehicle Edit Successful");
				}
				else {
					redir.addAttribute("resultfail","Vehicle Edit UnSuccessful");
				}	
			}else{
				MtVehicle vehicle=new MtVehicle();
				vehicle.setVehicleName(req.getParameter("VehicleName"));
				vehicle.setBaNo(req.getParameter("BANO"));
				vehicle.setDateOfPurchase(DateTimeFormatUtil.dateConversionSql(req.getParameter("DOP")));
				vehicle.setNoOfSeat(Integer.parseInt(req.getParameter("NoOfSeat")));
				vehicle.setCreatedDate(sdtf.format(new Date()));
				vehicle.setCreatedBy(Username);
				vehicle.setIsActive(1);
				int count=service.AddVehicle(vehicle);
				if(count!=0) {
					redir.addAttribute("result","Vehicle Add Successful");
				}
				else {
					redir.addAttribute("resultfail","Vehicle Add UnSuccessful");
				}
			}
			return "redirect:/MtVehicle.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MtVehicleAddEdit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}	
	}
	
	@RequestMapping(value="TripReport.htm",method=RequestMethod.GET)
	public String TripReport(HttpServletRequest req,HttpSession ses) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TripReport.htm "+Username);	
		try {
			req.setAttribute("DriverList", service.getDriverList());
			req.setAttribute("vehiclelist",service.GetVechileList());
			req.setAttribute("ReportList",service.DateWiseProjectReport(new java.sql.Date(new Date().getTime()),new java.sql.Date(new Date().getTime()),-1,-1));
			ses.setAttribute("SidebarActive", "TripReport_htm");
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/MtReport";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TripReport.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
		
		
	}
	@RequestMapping(value="TripReport.htm",method=RequestMethod.POST)
	public String TripReportsubmit(HttpServletRequest req,HttpSession ses) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside TripReport.htm "+Username);	
		try {
			req.setAttribute("DriverList", service.getDriverList());
			req.setAttribute("vehiclelist",service.GetVechileList());	
			String driverid = req.getParameter("DriverId");
			String vehicleid = req.getParameter("VehicleId");
			
			List<Object[]> DateWiseProjectReport = service.DateWiseProjectReport(DateTimeFormatUtil.dateConversionSql(req.getParameter("fromdate")),DateTimeFormatUtil.dateConversionSql(req.getParameter("todate")),
					Integer.parseInt(driverid),Integer.parseInt(vehicleid));
			
			req.setAttribute("driverid", driverid);
			req.setAttribute("vehicleid", vehicleid);
			req.setAttribute("fromdate", req.getParameter("fromdate"));
			req.setAttribute("todate", req.getParameter("todate"));
			req.setAttribute("ReportList",DateWiseProjectReport);
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/MtReport";
		} catch (Exception e) {
			logger.error(new Date() +" Inside TripReport.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}


	
	@RequestMapping(value="MtUnlink.htm",method=RequestMethod.GET)
	public String MtUnlink(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtUnlink.htm "+Username);	
		try {

			MtLinkDuty duty=new MtLinkDuty();
				duty.setLinkDutyId(Integer.parseInt(req.getParameter("lid")));
				duty.setMtApplId(Integer.parseInt(req.getParameter("appid")));
				duty.setCreatedBy((String)ses.getAttribute("EmpNo"));
				duty.setCreatedDate(sdtf.format(new Date()));
			int count=service.UnLinkRequest(duty);
			
			if(count!=0) {
				
				MtApplyTransaction trx=new MtApplyTransaction();
					trx.setMtStatus("X");
					trx.setMtRemarks("UnLink By MTO");
					trx.setMtApplId(Integer.parseInt(req.getParameter("appid")));
					trx.setActionBy((String)ses.getAttribute("EmpNo"));
					trx.setActionDate(sdtf.format(new Date()));
				service.MtApplyTranscation(trx);
				
				EMSNotification notification=new EMSNotification();
					//notification.setEmpId(Long.parseLong(req.getParameter("EmpId")));
					notification.setNotificationBy((String)ses.getAttribute("EmpNo"));
					notification.setNotificationDate(sdtf.format(new Date()));
					notification.setNotificationMessage("Trip Cancle By MTO");
					notification.setNotificationUrl("MTUserHome.htm");
					notification.setCreatedBy(Username);
					notification.setCreatedDate(sdtf.format(new Date()));
					notification.setIsActive(1);
				//service.MtEmsNotification(notification);
			}
			
			if(count!=0) {
				redir.addAttribute("result","Trip UnLink Successful");	
			}else {
				redir.addAttribute("resultfail","Trip UnLink UnSuccessful");
			}
		
			return "redirect:/MTHome.htm";
		}catch (Exception e){
			logger.error(new Date() +" Inside MtUnlink.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "MtTripList.htm" , method=RequestMethod.GET)
	public String MtTripList(HttpServletRequest req,HttpSession ses)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MtTripList.htm "+Username);	
		try {
			String FromDate=req.getParameter("Fromdate");
			String ToDate=req.getParameter("Todate");
			if(FromDate!=null&&ToDate!=null) {
				
				req.setAttribute("TripList", service.TripList(DateTimeFormatUtil.dateConversionSql(FromDate),DateTimeFormatUtil.dateConversionSql(ToDate)));
				req.setAttribute("FromDate", FromDate);
				req.setAttribute("ToDate", ToDate);
			}else{
				
				//Date now = new Date();    
				//Calendar myCal = Calendar.getInstance();
				//    myCal.setTime(now);    
				//myCal.add(Calendar.MONTH, +1);    
				//now = myCal.getTime();
				
				//req.setAttribute("TripList", service.TripList(new java.sql.Date(new Date().getTime()),new java.sql.Date(now.getTime())));
				//req.setAttribute("FromDate", sdf.format(new Date().getTime()));
				//req.setAttribute("ToDate", sdf.format(now.getTime()));
				
				String fd = LocalDate.now().minusMonths(1).toString();
				String td = LocalDate.now().toString();
				FromDate=rdf.format(sf.parse(fd.toString()));
				ToDate=rdf.format(sf.parse(td.toString()));
				req.setAttribute("TripList",service.TripList(DateTimeFormatUtil.dateConversionSql(FromDate),DateTimeFormatUtil.dateConversionSql(ToDate)));
			}
			
			req.setAttribute("fromdate",FromDate );
			req.setAttribute("todate",ToDate );
			ses.setAttribute("SidebarActive", "MtTripList_htm");
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			return "Mt/MtTripList";
		}catch(Exception e){
			logger.error(new Date() +" Inside MtTripList.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	@RequestMapping(value="DirectorTrip.htm",method=RequestMethod.GET)
	public String DirectorDuty(HttpServletRequest req,HttpSession ses) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside DirectorTrip.htm "+Username);	
		try {
			req.setAttribute("DriverList", service.getDriverList());
			String FDate=req.getParameter("Fromdate");
			String TDate=req.getParameter("Todate");
			
			if(FDate!=null&&TDate!=null) {
				 req.setAttribute("dutylist",service.DirectorTripList(DateTimeFormatUtil.dateConversionSql(FDate),DateTimeFormatUtil.dateConversionSql(TDate)));
			
			}else{
				String fd = LocalDate.now().minusMonths(1).toString();
				String td = LocalDate.now().toString();
				FDate=rdf.format(sf.parse(fd.toString()));
				TDate=rdf.format(sf.parse(td.toString()));
								
				 req.setAttribute("dutylist",service.DirectorTripList(DateTimeFormatUtil.dateConversionSql(FDate),DateTimeFormatUtil.dateConversionSql(TDate)));
			}
			
			req.setAttribute("fromdate", FDate);
			req.setAttribute("todate", TDate);
			ses.setAttribute("SidebarActive", "DirectorTrip_htm");
			Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
			String Name=emp[1]+"  ("+emp[2]+")";
			req.setAttribute("empname",Name);
			
			return "Mt/MtDirectorTrip";
		} catch (Exception e) {
			logger.error(new Date() +" Inside DirectorTrip.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}	
	}
	
	@RequestMapping(value="DirectorTrip.htm",method=RequestMethod.POST)
	public String DirectorDutyAssign(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside DirectorTrip.htm "+Username);	
		try {
			
			MtDirectorDuty duty= new MtDirectorDuty();
				duty.setDriverId(Integer.parseInt(req.getParameter("DriverId")));
				duty.setFromDate(DateTimeFormatUtil.dateConversionSql(req.getParameter("fromdate")));
				duty.setToDate(DateTimeFormatUtil.dateConversionSql(req.getParameter("todate")));
				duty.setCreatedBy((String)ses.getAttribute("EmpNo"));
				duty.setCreatedDate(sdtf.format(new Date()));
				duty.setIsActive(1);
			int count=service.DirectorTripAssign(duty);
			
			if(count!=0) {
				redir.addAttribute("result","Trip Add Successful");
			}else {
				redir.addAttribute("resultfail","Trip Add UnSuccessful");
			}
			/*
			 * Calendar cal = Calendar.getInstance(); cal.add(Calendar.DAY_OF_MONTH, 30);
			 * String newDate = sdf.format(cal.getTime());
			 * req.setAttribute("dutylist",service.DirectorTripList(new java.sql.Date(new
			 * Date().getTime()),new java.sql.Date(sdf.parse(newDate).getTime())));
			 */
			return "redirect:/DirectorTrip.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside DirectorTrip.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value="MTRequestEdit.htm",method=RequestMethod.POST)
	public String MTRequestEdit(HttpServletRequest req,HttpSession ses) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTRequestEdit.htm "+Username);	
		
		try {
			String submit=req.getParameter("sub");
			String tripid=(req.getParameter("Aid"));
			if(submit.equalsIgnoreCase("edit")) {
				 req.setAttribute("requestedit",service.MtAdminReqEdit(tripid));
					Object[] emp =service.getEmpData((String)ses.getAttribute("EmpNo")); 
					String Name=emp[1]+"  ("+emp[2]+")";
					req.setAttribute("empname",Name);
				return "Mt/MtRequestEdit";
			}
			return "Mt/MtRequestEdit";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MTRequestEdit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value="MTRequestDelete.htm",method=RequestMethod.POST)
	public String MTRequestDelete(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTRequestDelete.htm "+Username);	
		
		try {
			String tripid=(req.getParameter("Aid"));
			int count = service.MtAdminReqDelete(tripid);
			if(count!=0) {
				redir.addAttribute("result","Trip Deleted Successful");
			}else {
				redir.addAttribute("resultfail","Trip Delete UnSuccessful");
			}
			return "redirect:/MTHome.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MTRequestDelete.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	
	}
	
	@RequestMapping(value="MTRequestEditSubmit.htm",method=RequestMethod.POST)
	public String MTRequestEditSubmit(HttpServletRequest req,HttpSession ses,RedirectAttributes redir) throws Exception {
		
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside MTRequestEditSubmit.htm "+Username);	
		
		try {

			MtUserApply apply=new MtUserApply();
			apply.setStartTime(req.getParameter("Dtime"));
			apply.setEndTime(req.getParameter("Rtime"));
			apply.setSource(req.getParameter("Source"));
			apply.setDestination(req.getParameter("Destination"));
		    apply.setDateOfTravel(DateTimeFormatUtil.dateConversionSql(req.getParameter("Dtravel")));
		    apply.setEndDateOfTravel(DateTimeFormatUtil.dateConversionSql(req.getParameter("Etravel")));
			apply.setMtReqNo(req.getParameter("reqid1"));
			
			apply.setModifiedBy(Username);
			apply.setModifiedDate(sdtf.format(new Date()));
				int count=service.MtAdminReqEdit(apply);
				if(count!=0){
					redir.addAttribute("result","Request Edit Successful");
				}else{
					redir.addAttribute("resultfail","Request Edit  UnSuccessful");
				}
				return "redirect:/MTHome.htm";
		} catch (Exception e) {
			logger.error(new Date() +" Inside MTRequestEditSubmit.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	
	}
	
	
}

package com.vts.ems.newspaper.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vts.ems.newspaper.service.NewPaperServiceImpl;

@Controller
public class NewspaperController {

	@Autowired
	private NewPaperServiceImpl service;

	private static final Logger logger = LogManager.getLogger(NewspaperController.class);

		@RequestMapping(value = "NewspaperList.htm")
		public String NewsPaperList(HttpServletRequest req) 
		{
			HttpSession ses = req.getSession(false);
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperList.htm " + Username);
			try {
				List<Object[]> NewspaperClaimList = service.getNewspaperClaimList(sesEmpNo);
				req.setAttribute("NewspaperClaimList", NewspaperClaimList);
				ses.setAttribute("SidebarActive", "NewspaperList_htm");
				
				return "newspaper/Newspaperlist";
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside NewspaperList.htm " + Username, e);
				return "static/Error";
			}
		}

		@RequestMapping(value = "NewspaperApprovedList.htm", method = { RequestMethod.GET, RequestMethod.POST })
		public ModelAndView NewspaperApprovedList(HttpServletRequest request)
		{
			HttpSession ses= request.getSession(false);
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperApprovedList.htm " + Username);
			try {
				ModelAndView mv = new ModelAndView("newspaper/NewspaperApprovedList");
				List<Object[]> NewspaperClaimApprovedList = service.getNewspaperClaimApprovedList();
				mv.addObject("NewspaperClaimApprovedList", NewspaperClaimApprovedList);
		
				List<Object[]> NewspaperApprovalList = service.getNewspaperApprovalList();
				mv.addObject("NewsCount", NewspaperApprovalList.size());
				ses.setAttribute("SidebarActive", "NewspaperApprovedList_htm");
				return mv;
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside NewspaperApprovedList.htm " + Username, e);
				return new ModelAndView("static/Error");
			}
		}
	
		@RequestMapping(value = "NewspaperView.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public String NewspaperView(HttpServletRequest req,HttpSession ses) throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String designation = (String) ses.getAttribute("EmpDesig");
			String Username = (String) ses.getAttribute("Username");
			String name = (String) ses.getAttribute("EmpName");
			logger.info(new Date() + "Inside NewspaperView.htm " + Username);
			try
			{
				Object[] PayLevelAndNewsRectrictAmt = service.getPayLevelAndNewsRectrictAmt(sesEmpNo);
				req.setAttribute("name", name);
				req.setAttribute("desig", designation);
				req.setAttribute("PayLevelAndNewsRectrictAmt", PayLevelAndNewsRectrictAmt);
				req.setAttribute("LabDetails", service.getLabDetails());
				return "newspaper/NewspaperClaim";
			}
			catch (Exception e)
			{
				e.printStackTrace();
				logger.error(new Date() + " Inside NewspaperView.htm " + Username, e);
				return "static/Error";
			}
		}

		@RequestMapping(value = "NewspaperAdd.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperAdd(HttpServletRequest req,HttpSession ses) throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperAdd.htm " + Username);
			try
			{
				ModelAndView mv = new ModelAndView();
				String ClaimMonth = req.getParameter("ClaimMonth");
				String ClaimYear = req.getParameter("ClaimYear");
				String ClaimAmount = req.getParameter("ClaimAmount");
				String RestrictedAmount = req.getParameter("RestrictedAmount");
				String PayLevelId = req.getParameter("PayLevelId");
				if (Double.parseDouble(ClaimAmount) > 0) 
				{
					Object[] CheckPeriodAlreadyPresentOrNot = service.getCheckPeriodOfNewsAlreadyPresentOrNot(sesEmpNo, ClaimMonth, ClaimYear);
					if (CheckPeriodAlreadyPresentOrNot != null)
					{
						mv.addObject("resultfail", "You Already Applied For "+ ClaimMonth + "/" + ClaimYear);
					} 
					else 
					{
						long AddNewspaperClaimResult = service.AddNewspaperClaim(sesEmpNo, ClaimMonth, ClaimYear,ClaimAmount, RestrictedAmount, PayLevelId);
						if(AddNewspaperClaimResult>0) 
						{
							mv.addObject("result", "Claim Added Successfully");
						}
						else
						{
							mv.addObject("resultfail", "Error While Adding Claim");
						}
					}
				} 
				else 
				{
					mv.addObject("resultfail", "Please Enter Balance Correctly");
				}
				
				List<Object[]> NewspaperClaimList = service.getNewspaperClaimList(sesEmpNo);
				mv.addObject("NewspaperClaimList", NewspaperClaimList);
				mv.setViewName("redirect:/NewspaperList.htm");
				return mv;
			}
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside NewspaperAdd.htm " + Username, e);
				return new ModelAndView("static/Error");
			}
		}
	
	
	
		@RequestMapping(value = "NewspaperEdit.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperEdit(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperEdit.htm " + Username);
			
				ModelAndView mv = new ModelAndView();
			
				String ClaimAmount = request.getParameter("ClaimAmount");
				String RestrictedAmount = request.getParameter("RestrictedAmount");
				String NewspaperId = request.getParameter("NewspaperId");
			
				try {
					if (Double.parseDouble(ClaimAmount) > 0) {
						int EditNewspaperClaimResult = service.EditNewspaperClaim(sesEmpNo, ClaimAmount,NewspaperId, RestrictedAmount);
						if(EditNewspaperClaimResult>0) 
						{
							mv.addObject("result", "Claim Updated Successfully");
						}
						else 
						{
							mv.addObject("resultfail", "Claim Updat Unsuccessfully");
						}
						
					} else {
						mv.addObject("resultfail", "Please Enter Balance Correctly");
					}
					
					mv.setViewName("redirect:/NewspaperList.htm");
					return mv;
					
				}catch (Exception e) {
					logger.error(new Date() + " Inside NewspaperEdit.htm " + Username, e);
					e.printStackTrace();
					return new ModelAndView("static/Error");
				}
			
		}
	
		@RequestMapping(value = "NewspaperEditView.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperEditSubmit(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String name = (String) ses.getAttribute("EmpName");
			String designation = (String) ses.getAttribute("EmpDesig");
			logger.info(new Date() + "Inside NewspaperEditSubmit.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			
			try {
				String NewspaperId = request.getParameter("NewspaperId");
				Object[] CheckApproveOrNot = service.getCheckNewspaperApproveOrNot(NewspaperId);
				if (CheckApproveOrNot != null) {
					Object[] NewspaperEditDetails = service.getNewspaperEditDetails(NewspaperId);
					mv.addObject("NewspaperEditDetails", NewspaperEditDetails);
					mv.addObject("name", name);
					mv.addObject("desig", designation);
					mv.addObject("LabDetails", service.getLabDetails());
					mv.setViewName("newspaper/NewspaperClaim");
				} else {
					mv.addObject("resultfail", "Claim Already Approved You Cannot Edit");
					mv.setViewName("redirect:/NewspaperList.htm");
				}
				
				return mv;
				
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewspaperEditView.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
		}
	
		@RequestMapping(value = "NewspaperPrint.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperPrint(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String name = (String) ses.getAttribute("EmpName");
			String designation = (String) ses.getAttribute("EmpDesig");
			logger.info(new Date() + "Inside NewspaperPrint.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {

				String NewspaperId = request.getParameter("NewspaperId");
				Object[] NewspaperUserPrintData = service.getNewspaperUserPrintData(NewspaperId);
				mv.addObject("name", name);
				mv.addObject("desig", designation);
				mv.addObject("NewspaperUserPrintData", NewspaperUserPrintData);
				mv.addObject("LabDetails", service.getLabDetails());
				mv.setViewName("newspaper/NewspaperUserClaimPrint");
				return mv;
				
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewspaperPrint.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
		
		@RequestMapping(value = "NewspaperDelete.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperDelete(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperPrint.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {

				String NewspaperId = request.getParameter("NewspaperId");

				Object[] CheckApproveOrNot = service.getCheckNewspaperApproveOrNot(NewspaperId);

				if (CheckApproveOrNot != null) 
				{
					int DeleteNewspaperResult = service.DeleteNewspaperClaim(NewspaperId);
					if(DeleteNewspaperResult>0) {
						mv.addObject("result", "Claim Delete Successfull");
					}else {
						mv.addObject("result", "Claim Delete Unsuccessfull");
					}
				} else {
					mv.addObject("resultfail", "Claim Already Approved You Cannot Delete");
				}

				mv.setViewName("redirect:/NewspaperList.htm");
				return mv;

			} catch (Exception e) {
				logger.error(new Date() + " Inside NewspaperDelete.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
	
	
		
		@RequestMapping(value = "NewsApprovalPeriodEdit.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewsApprovalPeriodEdit(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewsApprovalPeriodEdit.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {

				  String NewspaperBillId=request.getParameter("NewspaperBillId");
				  System.out.println(NewspaperBillId);
				  Object[]  NewspaperApprovalPeriodEditDetails=service.getNewspaperApprovalPeriodEditDetails(NewspaperBillId);
				  
				  mv.addObject("NewspaperApprovalPeriodEditDetails",NewspaperApprovalPeriodEditDetails);
				  
				  mv.setViewName("newspaper/NewsTeleEditApprovalPeriod");
				
				return mv;
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewsApprovalPeriodEdit.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
		
		@RequestMapping(value = "NewsApprovalPeriodEditSubmit.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewsApprovalPeriodEditSubmit(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			logger.info(new Date() + "Inside NewsApprovalPeriodEditSubmit.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {

				String NewspaperBillId = request.getParameter("NewspaperBillId");
				String FromDate = request.getParameter("FromDate");
				String ToDate = request.getParameter("ToDate");

				int UpdateNewsPeriod = service.UpdateNewsPeriod(sesEmpNo, NewspaperBillId, FromDate, ToDate);

				if(UpdateNewsPeriod>0) 
				{
					mv.addObject("result", "Approval Period Update Successful");
				}
				else
				{
					mv.addObject("resultfail", "Approval Period Update Unsuccessful");
				}
				mv.setViewName("redirect:/NewspaperApprovedList.htm");
				
				return mv;
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewsApprovalPeriodEditSubmit.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
		
		@RequestMapping(value = "NewspaperReportPrint.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperReportPrint(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperReportPrint.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {
				String NewspaperBillId = request.getParameter("NewspaperBillId");

				List<Object[]> NewspaperReportPrintData = service.getNewspaperReportPrintData(NewspaperBillId);
				mv.addObject("NewspaperReportPrintData", NewspaperReportPrintData);

				mv.setViewName("newspaper/NewspaperReportPrint");
				
				return mv;
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewspaperReportPrint.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
		
		@RequestMapping(value = "NewspaperContingentBillPrint.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperContingentBillPrint(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperContingentBillPrint.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {
				String NewspaperBillId = request.getParameter("NewspaperBillId");
				Object[] NewspaperContingentBillPrint = service.getNewspaperContingentBillPrintData(NewspaperBillId);
				mv.addObject("NewspaperContingentBillPrint", NewspaperContingentBillPrint);

				mv.setViewName("newspaper/NewspaperContingentBillPrint");
				
				
				return mv;
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewspaperContingentBillPrint.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
		
		@RequestMapping(value = "NewsPaperExpSancReport.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewsPaperExpSancReport(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewsPaperExpSancReport.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {
				String NewspaperBillId = request.getParameter("NewspaperBillId");
				Object[] NewsPaperExpSancReport = service.getNewspaperContingentBillPrintData(NewspaperBillId);
				mv.addObject("NewsPaperExpSancReport", NewsPaperExpSancReport);

				mv.setViewName("newspaper/NewspaperExpSancReport");
				
				return mv;
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewsPaperExpSancReport.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
		
		

	
		@RequestMapping(value = "NewspaperApprove.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
		public ModelAndView NewspaperApprove(HttpServletRequest request,HttpSession ses)  throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperApprove.htm " + Username);
			
			ModelAndView mv = new ModelAndView();
			try {
				
				String[] NewspaperApproveAction = request.getParameterValues("NewspaperApproveAction");
				String FromDate = request.getParameter("FromDate");
				String ToDate = request.getParameter("ToDate");
	
				if(NewspaperApproveAction == null || NewspaperApproveAction.length==0)
				{
					mv.addObject("resultfail", "Please Select At least One Claim");
					mv.setViewName("redirect:/NewspaperApproval.htm");
					return mv;
				}
				
				Map<String, String> map = new LinkedHashMap<>();
	
				for (String NewspaperId : NewspaperApproveAction) 
				{
					String Textcomments = "::" + request.getParameter("remark" + NewspaperId);
					String PayableAmount = request.getParameter("PayableAmount" + NewspaperId);
					map.put(NewspaperId, PayableAmount + "_" + Textcomments);
				}
	
				int ApprovalResult = service.NewsApproval(map, FromDate, ToDate, sesEmpNo);
				mv.addObject("ApprovalResult", ApprovalResult);
				if(ApprovalResult>0) 
				{
					mv.addObject("result", "Transaction Successful");
				}
				else
				{
					mv.addObject("resultfail", "Transaction Unsuccessful");
				}
	
				mv.setViewName("redirect:/NewspaperApprovedList.htm");
				return mv;
			} catch (Exception e) {
				logger.error(new Date() + " Inside NewspaperApprove.htm " + Username, e);
				e.printStackTrace();
				return new ModelAndView("static/Error");
			}
			
		}
	
	
		@RequestMapping(value = "NewspaperApproval.htm")
		public ModelAndView newspaperapproval(HttpServletRequest request)  throws Exception
		{
			HttpSession ses= request.getSession(false);
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() + "Inside NewspaperApproval.htm " + Username);
			try {
				ModelAndView mv = new ModelAndView("newspaper/NewspaperApproval");
				
				List<Object[]> NewspaperApprovalList = service.getNewspaperApprovalList();
				mv.addObject("NewspaperApprovalList", NewspaperApprovalList);
				ses.setAttribute("SidebarActive", "NewspaperApproval_htm");
				return mv;
			} 
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside NewspaperApproval.htm " + Username, e);
				return new ModelAndView("static/Error");
			}
	
		}



		
		
////////////////////////////////////////////////////////////////////// Telephone code ///////////////////////////////////////////////////////////////////
		
		
		        
		  		@RequestMapping(value="TelephoneList.htm")
		  		public ModelAndView telephoneList(HttpServletRequest request,HttpSession ses) throws Exception
		  		{
		  			    ModelAndView mv=new ModelAndView("newspaper/TelephoneList");

		  				String sesEmpNo = (String) ses.getAttribute("EmpNo");
		  				
		  				List<Object[]> TeleClaimList=service.getTeleClaimList(sesEmpNo);
		  				request.setAttribute("TeleClaimList",TeleClaimList);
		  				
		  				
		  				List<Object[]> TeleDeviceList=service.getTeleDeviceList(sesEmpNo);
		  				request.setAttribute("TeleDeviceList",TeleDeviceList);
		  				
		  				List<Object[]> SendBackData=service.getTelephoneSendbackData(sesEmpNo);
		  				request.setAttribute("Sendbackdata",SendBackData);
		  				
		  		 return mv;
		  		} 
		  		
		  		
		  		
		  		
		@RequestMapping(value="TelephoneDeviceList.htm")
		public ModelAndView TelephoneDeviceList(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			ModelAndView mv = new ModelAndView();
			logger.info(new Date() + "Inside TelephoneDeviceList.htm " + Username);
			try {
			  				
				if(request.getParameter("EditDevice")!=null)
				{
					String TeleUsersId  = request.getParameter("TeleUsersId");
			  				
					Object[] TeleDeviceEditDetails=service.getTeleDeviceEditDetails(sesEmpNo,TeleUsersId);
					request.setAttribute("TeleDeviceEditDetails",TeleDeviceEditDetails);
					
				}
				
				List<Object[]> DeviceList=service.getDeviceList(sesEmpNo);
				List<Object[]> TeleDeviceList=service.getTeleDeviceList(sesEmpNo);
			  				
				mv.addObject("DeviceList", DeviceList);
				mv.addObject("TeleDeviceList",TeleDeviceList);
			  				
				mv.setViewName("newspaper/TeleDeviceAddEditDelete");
				return mv;
			} 
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside TelephoneDeviceList.htm " + Username, e);
				return new ModelAndView("static/Error");
			}
			
		}
		
		
//		@RequestMapping(value="TelephoneDeviceList.htm",method=RequestMethod.POST)
//		public ModelAndView TelephoneDeviceList(HttpServletRequest request,HttpSession ses) throws Exception
//		{
//			String sesEmpNo = (String) ses.getAttribute("EmpNo");
//			String Username = (String) ses.getAttribute("Username");
//			ModelAndView mv = new ModelAndView();
//			logger.info(new Date() + "Inside TelephoneDeviceList.htm " + Username);
//			try {
//				
//			} 
//			catch (Exception e) {
//				e.printStackTrace();
//				logger.error(new Date() + " Inside TelephoneDeviceList.htm " + Username, e);
//				return new ModelAndView("static/Error");
//			}
//			
//		}
		
		
		
		@RequestMapping(value="TelephoneDeviceSave.htm",method=RequestMethod.POST)
		public ModelAndView TelephoneDeviceSave(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			ModelAndView mv = new ModelAndView();
			logger.info(new Date() + "Inside TelephoneDeviceSave.htm " + Username);
			try {
				String deviceId  = request.getParameter("DeviceId");
				String deviceNo  = request.getParameter("DeviceNo");
		  				
				int AddDeviceResult=service.AddTeleUsers(sesEmpNo, deviceId, deviceNo);
				if(AddDeviceResult>0) 
				{
					mv.addObject("result", "Device Adding Successful");
				}
				else
				{
					mv.addObject("resultfail", "Device Adding Unsuccessful");
				}
				mv.setViewName("redirect:/TelephoneDeviceList.htm");
				return mv;
			} 
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside TelephoneDeviceSave.htm " + Username, e);
				return new ModelAndView("static/Error");
			}
			
		}
		
		@RequestMapping(value="TelephoneDeviceEdit.htm",method=RequestMethod.POST)
		public ModelAndView TelephoneDeviceEdit(HttpServletRequest request,HttpSession ses) throws Exception
		{
			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String Username = (String) ses.getAttribute("Username");
			ModelAndView mv = new ModelAndView();
			logger.info(new Date() + "Inside TelephoneDeviceEdit.htm " + Username);
			try {
				String TeleUsersId  = request.getParameter("TeleUsersId");
				String deviceNo  = request.getParameter("DeviceNo");
				int EditDeviceResult=service.UpdateTeleUser(sesEmpNo,TeleUsersId,deviceNo);
				System.out.println(EditDeviceResult);
				if(EditDeviceResult>0) 
				{
					mv.addObject("result", "Device Update Unsuccessful");
				}
				else
				{
					mv.addObject("resultfail", "Device Update Unsuccessful");
				}
				mv.setViewName("redirect:/TelephoneDeviceList.htm");
				return mv;
			} 
			catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() + " Inside TelephoneDeviceEdit.htm " + Username, e);
				return new ModelAndView("static/Error");
			}
			
		}
		
		
		
		
		
		
		
		
		@RequestMapping(value={"/telephoneadd","/telephoneedit","/telephonedelete","/telephonedevicelist","/telephonedeviceadd","/telephonedeviceedit","/telephonedevicedelete","/telephone-approval","/telephone-period-edit","/telephone-claim-forward","/telephoneprint","/telephone-sendback",},method=RequestMethod.POST)
		public ModelAndView telephoneAddEditDeletePrintApproval(HttpServletRequest request,HttpSession ses) throws Exception
		{
			ModelAndView mv = new ModelAndView();

			String sesEmpNo = (String) ses.getAttribute("EmpNo");
			String designation = (String) ses.getAttribute("EmpDesig");
			String name = (String) ses.getAttribute("EmpName");

			if (request.getParameter("AddTelephone") != null) {

				if (request.getParameterValues("ChooseDeviceFromlist") != null) {

					String ChooseDeviceFromlist[] = request.getParameterValues("ChooseDeviceFromlist");
					Map<String, String> map = new LinkedHashMap<>();

					for (String TeleUsersId : ChooseDeviceFromlist) {

						String devicename = request.getParameter("devicename" + TeleUsersId);
						String devicenumber = request.getParameter("devicenumber" + TeleUsersId);

						map.put(TeleUsersId, devicename + "_" + devicenumber);

					}

					Object[] PayLevelAndTeleRectrictAmt = service.getPayLevelAndTeleRectrictAmt(sesEmpNo);
					Object[] TeleSpecialpermission = service.getTeleSpecialpermission(sesEmpNo);

					request.setAttribute("MapResultofDevices", map);
					request.setAttribute("name", name);
					request.setAttribute("desig", designation);
					request.setAttribute("TeleSpecialpermission", TeleSpecialpermission);
					request.setAttribute("PayLevelAndTeleRectrictAmt", PayLevelAndTeleRectrictAmt);

					mv.setViewName("newspaper/TelephoneAdd");

				} else {
					request.setAttribute("PleaseSelectAtLeastOneOption", "PleaseSelectAtLeastOneOption");

					List<Object[]> TeleClaimList = service.getTeleClaimList(sesEmpNo);
					request.setAttribute("TeleClaimList", TeleClaimList);

					List<Object[]> TeleDeviceList = service.getTeleDeviceList(sesEmpNo);
					request.setAttribute("TeleDeviceList", TeleDeviceList);

					List<Object[]> SendBackData = service.getTelephoneSendbackData(sesEmpNo);
					request.setAttribute("Sendbackdata", SendBackData);
					mv.setViewName("newspaper/TelephoneList");

				}

			}

			if (request.getParameter("EditTeleClaim") != null) {

						// split TeleId and TeleForwardId ..
				String TeleId_TeleForwardId = request.getParameter("TeleId_TeleForwardId");
				String[] TeleId_TeleForwardIdArray = TeleId_TeleForwardId.split("_");
				String TeleId = TeleId_TeleForwardIdArray[0];
				String TeleForwardId = TeleId_TeleForwardIdArray[1];

				Object[] CheckApproveOrNot = service.getCheckTeleApproveOrNot(TeleId);

				if (CheckApproveOrNot != null) {
					List<Object[]> TeleClaimEditDetails = service.getTeleClaimEditDetails(TeleId);
					request.setAttribute("TeleClaimEditDetails", TeleClaimEditDetails);
					request.setAttribute("name", name);
					request.setAttribute("desig", designation);

					mv.setViewName("newspaper/TelephoneEdit");

				} else {

					request.setAttribute("EditRestricted", "EditRestricted");

					List<Object[]> TeleClaimList = service.getTeleClaimList(sesEmpNo);
					request.setAttribute("TeleClaimList", TeleClaimList);

					List<Object[]> TeleDeviceList = service.getTeleDeviceList(sesEmpNo);
					request.setAttribute("TeleDeviceList", TeleDeviceList);

					List<Object[]> SendBackData = service.getTelephoneSendbackData(sesEmpNo);
					request.setAttribute("Sendbackdata", SendBackData);
					mv.setViewName("newspaper/TelephoneList");

				}

			}

			if (request.getParameter("TeleAddClaimSave") != null || request.getParameter("EditTeleClaimSave") != null) {

				String BasicAmount[] = request.getParameterValues("BasicAmount");

				try {

					int length = BasicAmount.length;
					int count = 0;

					for (String s : BasicAmount) {
						if (Double.parseDouble(s) > 0) {
							++count;
						}

					}

					if (length == count) {
						String TeleFromDate[] = request.getParameterValues("FromDate");
						String TeleToDate[] = request.getParameterValues("ToDate");

						String TeleUsersId[] = request.getParameterValues("TeleUsersId");
						String TeleBillNo[] = request.getParameterValues("BillNo");
						String TeleBillDate[] = request.getParameterValues("BillDate");

						String TaxAmount[] = request.getParameterValues("TaxAmount");
						String TotalAmount[] = request.getParameterValues("TotalAmount");

						String RestrictedAmount = request.getParameter("RestrictedAmount");
						String TotalBasic = request.getParameter("TotalBasic");
						String TotalTax = request.getParameter("TotalTax");
						String GrossTotal = request.getParameter("GrossTotal");
						String PayLevelId = request.getParameter("PayLevelId");

						String ClaimMonth = request.getParameter("ClaimMonth");
						String ClaimYear = request.getParameter("ClaimYear");
						String IsBroadBand;
						if (request.getParameter("IsBroadBand") != null) {
							IsBroadBand = "Y";
						} else {
							IsBroadBand = "N";
						}

						if (request.getParameter("TeleAddClaimSave") != null) 
						{
							Object[] CheckPeriodAlreadyPresentOrNot = service
											.getCheckPeriodOfTeleAlreadyPresentOrNot(sesEmpNo, ClaimMonth, ClaimYear);
									if (CheckPeriodAlreadyPresentOrNot != null) {
										request.setAttribute("SamePeriodRestricted", ClaimMonth + "/" + ClaimYear);
									} else {
										int AddTelephoneClaimResult = service.AddTelephoneClaim(sesEmpNo, TotalBasic,
												TotalTax, GrossTotal, RestrictedAmount, TeleFromDate, TeleToDate,
												TeleUsersId, TeleBillNo, TeleBillDate, BasicAmount, TaxAmount,
												TotalAmount, PayLevelId, ClaimMonth, ClaimYear, IsBroadBand);
										request.setAttribute("AddTelephoneClaimResult", AddTelephoneClaimResult);
									}

								}

								if (request.getParameter("EditTeleClaimSave") != null) {
									String TeleDId[] = request.getParameterValues("TeleDId");
									String TeleId = request.getParameter("TeleId");
									String UserRemark = request.getParameter("UserRemark");
									String id = request.getParameter("ForwardId");
									int EditTelephoneResult = service.EditTelephoneResult(sesEmpNo, TeleId, TotalBasic,
											TotalTax, GrossTotal, RestrictedAmount, TeleDId, TeleFromDate, TeleToDate,
											TeleBillNo, TeleBillDate, BasicAmount, TaxAmount, TotalAmount, IsBroadBand,
											UserRemark, id);
									request.setAttribute("EditTelephoneResult", EditTelephoneResult);
								}
							} else {
								request.setAttribute("EnterBalanceCorrectly", "EnterBalanceCorrectly");
							}
						} catch (Exception e) {
							request.setAttribute("EnterBalanceCorrectly", "EnterBalanceCorrectly");
						}

						List<Object[]> TeleClaimList = service.getTeleClaimList(sesEmpNo);
						request.setAttribute("TeleClaimList", TeleClaimList);

						List<Object[]> TeleDeviceList = service.getTeleDeviceList(sesEmpNo);
						request.setAttribute("TeleDeviceList", TeleDeviceList);

						List<Object[]> SendBackData = service.getTelephoneSendbackData(sesEmpNo);
						request.setAttribute("Sendbackdata", SendBackData);

						mv.setViewName("newspaper/TelephoneList");

					}

					if (request.getParameter("DeleteTelephone") != null) {

						// split TeleId and TeleForwardId ..
						String TeleId_TeleForwardId = request.getParameter("TeleId_TeleForwardId");
						String[] TeleId_TeleForwardIdArray = TeleId_TeleForwardId.split("_");
						String TeleId = TeleId_TeleForwardIdArray[0];
						String TeleForwardId = TeleId_TeleForwardIdArray[1];

						Object[] CheckApproveForwardOrNot = service.getCheckTeleApproveForwardOrNot(TeleId);

						if (CheckApproveForwardOrNot != null) {
							int DeleteTelephoneResult = service.DeleteTelephone(TeleId, sesEmpNo);
							request.setAttribute("DeleteTelephoneResult", DeleteTelephoneResult);
						} else {
							request.setAttribute("DeleteRestricted", "DeleteRestricted");
						}

						List<Object[]> TeleClaimList = service.getTeleClaimList(sesEmpNo);
						request.setAttribute("TeleClaimList", TeleClaimList);

						List<Object[]> TeleDeviceList = service.getTeleDeviceList(sesEmpNo);
						request.setAttribute("TeleDeviceList", TeleDeviceList);

						List<Object[]> SendBackData = service.getTelephoneSendbackData(sesEmpNo);
						request.setAttribute("Sendbackdata", SendBackData);

						mv.setViewName("newspaper/TelephoneList");

					}

					if (request.getParameter("TeleClaimForward") != null) {

						if (request.getParameterValues("TeleId") != null) {

							String TeleId[] = request.getParameterValues("TeleId");

							if (TeleId.length >= 3) {

								int UpdateTeleByTeleForwardId = service.UpdateTeleByTeleForwardId(sesEmpNo, TeleId);
								request.setAttribute("ForwardToAdminResult", UpdateTeleByTeleForwardId);

							} else {
								request.setAttribute("PleaseSelectAtLeastThreeMonthClaim",
										"PleaseSelectAtLeastThreeMonthClaim");

							}

						} else {
							request.setAttribute("PleaseSelectAtLeastThreeMonthClaim",
									"PleaseSelectAtLeastThreeMonthClaim");

						}

						List<Object[]> TeleClaimList = service.getTeleClaimList(sesEmpNo);
						request.setAttribute("TeleClaimList", TeleClaimList);

						List<Object[]> TeleDeviceList = service.getTeleDeviceList(sesEmpNo);
						request.setAttribute("TeleDeviceList", TeleDeviceList);

						List<Object[]> SendBackData = service.getTelephoneSendbackData(sesEmpNo);
						request.setAttribute("Sendbackdata", SendBackData);

						mv.setViewName("newspaper/TelephoneList");

					}

					if (request.getParameter("TeleClaimPrint") != null) {

						// split TeleId and TeleForwardId ..
						String TeleId_TeleForwardId = request.getParameter("TeleId_TeleForwardId");
						String[] TeleId_TeleForwardIdArray = TeleId_TeleForwardId.split("_");
						String TeleId = TeleId_TeleForwardIdArray[0];
						String TeleForwardId = TeleId_TeleForwardIdArray[1];

						// Object[] CheckApproveOrNot=service.getCheckTeleApproveOrNot(TeleId);

						if (!("0".equalsIgnoreCase(TeleForwardId))) {
							List<Object[]> TelephoneUserPrintSingleData = service
									.getTelephoneUserPrintSingleData(TeleForwardId);
							List<Object[]> TelephoneUserPrintMultiData = service
									.getTelephoneUserPrintMultiData(TeleForwardId);

							request.setAttribute("TelephoneUserPrintSingleData", TelephoneUserPrintSingleData);
							request.setAttribute("TelephoneUserPrintMultiData", TelephoneUserPrintMultiData);

							mv.setViewName("newspaper/TeleClaimPrint");

						} else {
							request.setAttribute("PrintRestricted", "PrintRestricted");
							mv.setViewName("newspaper/PrintError");

						}

					}

					if (request.getParameter("TelephoneApproval") != null) {

						List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
						request.setAttribute("TelephoneApprovalList", TelephoneApprovalList);

						mv.setViewName("newspaper/TelephoneApproval");

					}

					if (request.getParameter("ApprovalSubmit") != null
							&& request.getParameterValues("TeleApproveAction") == null) {

						request.setAttribute("PleaseSelectAtLeastOneOption", "PleaseSelectAtLeastOneOption");
						List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
						request.setAttribute("TelephoneApprovalList", TelephoneApprovalList);

						mv.setViewName("newspaper/TelephoneApproval");

					}

					if (request.getParameter("ApprovalSubmit") != null
							&& request.getParameterValues("TeleApproveAction") != null) {

						String[] TeleApproveAction = request.getParameterValues("TeleApproveAction");
						String FromDate = request.getParameter("FromDate");
						String ToDate = request.getParameter("ToDate");

						Map<String, String> map = new LinkedHashMap<>();

						for (String TeleForwardId : TeleApproveAction) {
							String Textcomments = "::" + request.getParameter("remark" + TeleForwardId);
							String PayableAmount = request.getParameter("PayableAmount" + TeleForwardId);
							map.put(TeleForwardId, PayableAmount + "_" + Textcomments);

						}

						int TeleApprovalResult = service.TeleApproval(map, FromDate, ToDate, sesEmpNo);
						request.setAttribute("TeleApprovalResult", TeleApprovalResult);

						List<Object[]> TelephoneClaimApprovedList = service.getTelephoneClaimApprovedList();
						request.setAttribute("TelephoneClaimApprovedList", TelephoneClaimApprovedList);

						List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
						request.setAttribute("TeleCount", TelephoneApprovalList.size());
						mv.setViewName("newspaper/TelephoneApprovedList");

					}

					if (request.getParameter("TelephoneSendback") != null) {

						List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
						request.setAttribute("TelephoneApprovalList", TelephoneApprovalList);

						mv.setViewName("newspaper/TelephoneSendBack");

					}

					if (request.getParameter("SendbackSubmit") != null
							&& request.getParameterValues("TeleApproveAction") != null) {

						String[] TeleApproveAction = request.getParameterValues("TeleApproveAction");
						String FromDate = request.getParameter("FromDate");
						String ToDate = request.getParameter("ToDate");

						Map<String, String> map = new LinkedHashMap<>();

						for (String TeleForwardId : TeleApproveAction) {
							String Textcomments = "::" + request.getParameter("remark" + TeleForwardId);
							map.put(TeleForwardId, Textcomments);

						}

						int TeleApprovalResult = service.TeleSendback(map, FromDate, ToDate, sesEmpNo);
						request.setAttribute("TeleApprovalResult", TeleApprovalResult);

						List<Object[]> TelephoneClaimApprovedList = service.getTelephoneClaimApprovedList();
						request.setAttribute("TelephoneClaimApprovedList", TelephoneClaimApprovedList);

						List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
						request.setAttribute("TeleCount", TelephoneApprovalList.size());
						mv.setViewName("newspaper/TelephoneApprovedList");

					}

					if (request.getParameter("TelephoneApprovalPeriodEdit") != null) {

						String TeleBillId = request.getParameter("TeleBillId");

						Object[] TelephoneApprovalPeriodEditDetails = service
								.getTelephoneApprovalPeriodEditDetails(TeleBillId);

						request.setAttribute("TeleApprovalPeriodEditDetails", TelephoneApprovalPeriodEditDetails);

						mv.setViewName("newspaper/NewsTeleEditApprovalPeriod");

					}

					if (request.getParameter("TelephoneApprovalPeriodEditSave") != null) {

						String TeleBillId = request.getParameter("TeleBillId");
						String FromDate = request.getParameter("FromDate");
						String ToDate = request.getParameter("ToDate");

						int UpdateTelePeriod = service.UpdateTelePeriod(sesEmpNo, TeleBillId, FromDate, ToDate);

						request.setAttribute("UpdateTelePeriod", UpdateTelePeriod);

						List<Object[]> TelephoneClaimApprovedList = service.getTelephoneClaimApprovedList();
						request.setAttribute("TelephoneClaimApprovedList", TelephoneClaimApprovedList);

						List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
						request.setAttribute("TeleCount", TelephoneApprovalList.size());
						mv.setViewName("newspaper/TelephoneApprovedList");
					}

					if (request.getParameter("TelephonePrintReport") != null) {
						String TeleBillId = request.getParameter("TeleBillId");

						List<Object[]> TelephonePrintReportSingleData = service
								.getTelephonePrintReportSingleData(TeleBillId);
						List<Object[]> TelephonePrintReportMultiData = service
								.getTelephonePrintReportMultiData(TeleBillId);

						request.setAttribute("TelephonePrintReportSingleData", TelephonePrintReportSingleData);
						request.setAttribute("TelephonePrintReportMultiData", TelephonePrintReportMultiData);

						mv.setViewName("newspaper/TelephoneReportPrint");

					}

					if (request.getParameter("TelephoneContingentBill") != null) {

						String TeleBillId = request.getParameter("TeleBillId");
						Object[] TelephoneContingentBillPrint = service.getTelephoneContingentBillPrintData(TeleBillId);
						request.setAttribute("TelephoneContingentBillPrint", TelephoneContingentBillPrint);

						mv.setViewName("newspaper/TelephoneContingentBill");

					}

					if (request.getParameter("TelephoneExpenditureSanction") != null) {

						String TeleBillId = request.getParameter("TeleBillId");
						Object[] TeleExpSancReport = service.getTelephoneContingentBillPrintData(TeleBillId);
						request.setAttribute("TeleExpSancReport", TeleExpSancReport);

						mv.setViewName("newspaper/TeleExpSancReport");

					}

					if (request.getParameter("TeleApprovedReportYearWise") != null) {

						mv.setViewName("newspaper/TeleApprovalReportYearWise");

					}

					return mv;
				} 
		        
		  		@RequestMapping(value="/telephone-approved-list",method=RequestMethod.POST)
		  		public ModelAndView telephoneApprovedList(HttpServletRequest request)
		  		{
		  			 ModelAndView mv=new ModelAndView("newspaper/TelephoneApprovedList");
		  			 List<Object[]> TelephoneClaimApprovedList=service.getTelephoneClaimApprovedList();
		  			 request.setAttribute("TelephoneClaimApprovedList", TelephoneClaimApprovedList);
		  			
		  			
		  			List<Object[]> TelephoneApprovalList=service.getTelephoneApprovalList();
		       	    request.setAttribute("TeleCount",TelephoneApprovalList.size());
		       	  	
		  			
		  		 return mv;
		  		} 
		        
		  		@RequestMapping(value="/telephone-approval-individualclaim-details")
		  		public ModelAndView telephoneIndividualClaimDetailsPrint(HttpServletRequest request)
		  		{
		  			  ModelAndView mv=new ModelAndView();
		  		
		  			  if(request.getParameter("TeleForwardId")!=null)
		  			{
		  			

		  				String TeleForwardId=request.getParameter("TeleForwardId");	
		  			   
		  				List<Object[]> TelephoneUserPrintSingleData=service.getTelephoneUserPrintSingleData(TeleForwardId);
		  				List<Object[]> TelephoneUserPrintMultiData=service.getTelephoneUserPrintMultiData(TeleForwardId);
		  				  
		  				request.setAttribute("TelephoneUserPrintSingleData", TelephoneUserPrintSingleData);
		  			    request.setAttribute("TelephoneUserPrintMultiData", TelephoneUserPrintMultiData);
		  				
		  			    mv.setViewName("newspaper/TeleClaimPrint");
		  				
		  				
		  			}
		  			else
		  			{
		  				request.setAttribute("PrintRestricted","PrintRestricted");
					    mv.setViewName("newspaper/PrintError");
		  				
		  			}
		      	  
		      	  
		  			    
		  		 return mv;
		  		} 
		    
	     /////////////////////*Telephone*////////////////////////////
		
		
		
		
		
		
		
		
		
		
		

}

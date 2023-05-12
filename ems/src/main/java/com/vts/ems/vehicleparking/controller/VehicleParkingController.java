package com.vts.ems.vehicleparking.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;
import com.vts.ems.vehicleparking.model.VehicleParkingApplications;
import com.vts.ems.vehicleparking.service.VehicleParkingService;

@Controller
public class VehicleParkingController {
	private static final Logger logger = LogManager.getLogger(VehicleParkingController.class);

	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@Autowired
	public VehicleParkingService service;

	@Autowired
	EmsFileUtils emsfileutils ;

	private final String formmoduleid="29";

	@RequestMapping("VehicleParking.htm")
	public String vehicleParkHome(HttpServletRequest req, HttpSession ses){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			ses.setAttribute("formmoduleid", formmoduleid);
			ses.setAttribute("SidebarActive", "VehicleParking_htm");
			//			String fromdate = req.getParameter("fromdate");
			//			String todate   =req.getParameter("todate");
			//			if(fromdate != null && todate != null ) {
			//				req.setAttribute("fromdate", fromdate);
			//				req.setAttribute("todate", todate);	
			//			} else {
			//				req.setAttribute("fromdate", DateTimeFormatUtil.getFirstDayofCurrentMonthRegularFormat());
			//				req.setAttribute("todate",DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now()));	
			//			}

			req.setAttribute("VehicleParkList", service.findVehicleParkListByEmpNO(EmpNo));
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside VehicleParking.htm "+ Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
		return "vehicleparking/VehicleParkingList";
	}

	@RequestMapping("GetVehicleParkingForm.htm")
	public String vehicleParkForm(HttpServletRequest req, HttpSession ses){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {

			String vehicleAppId =req.getParameter("vehicleAppId");
			String isApproval =req.getParameter("isApproval");
			Object[] oneVehicle=service.findByVehicleId(Long.parseLong(vehicleAppId));
			req.setAttribute("oneVehicle", oneVehicle);
			req.setAttribute("isApproval", isApproval);
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside GetVehicleParkingForm.htm"+ Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
		return "vehicleparking/VehicleParkingReqForm";
	}

	@RequestMapping(value="VehicleParkFormSubmitAndPrint.htm",  method = {RequestMethod.POST,RequestMethod.GET})
	public String vehicleParkFormSubmitAndPrint(HttpServletRequest req, HttpSession ses, HttpServletResponse res){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {

			String vehicleAppId =req.getParameter("vehicleAppId");
			Object[] oneVehicle=service.findByVehicleId(Long.parseLong(vehicleAppId));
			req.setAttribute("oneVehicle", oneVehicle);

			String filename="";
			if( true) {

				filename="Vehicle_Park_application";
			}

			req.setAttribute("pagePart","3" );

			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));

			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);

			CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/vehicleparking/VehicleParkingReqFormPrint.jsp").forward(req, customResponse);

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

			return "redirect:/VehicleParking.htm";
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside VehicleParkFormSubmitAndPrint.htm "+Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping(value="VehicleParkAddEdit.htm")
	public String VehicleParkAddEdit(HttpServletRequest req, HttpSession ses) throws Exception {

		String UserId=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		logger.info(new Date() +"Inside VehicleParkAddEdit.htm "+UserId);

		try {

			String action =req.getParameter("Action");

			if("Add".equalsIgnoreCase(action)) {

				return "vehicleparking/VehicleParkingAddEdit";
			}
			else if("Edit".equalsIgnoreCase(action)) {
				String vehicleAppId =req.getParameter("vehicleAppId");
				Object[] oneVehicle=service.findByVehicleId(Long.parseLong(vehicleAppId));
				req.setAttribute("oneVehicle", oneVehicle);
				return "vehicleparking/VehicleParkingAddEdit";
			}else {

				return "redirect:/VehicleParking.htm";
			}		   		 
		} catch (Exception e) {
			logger.error(new Date() +"Inside VehicleParkAddEdit.htm "+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping(value="VehicleParkAddEditSave.htm")
	public String VehicleParkAddEditSave(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {

		String UserName=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String Action = (String)req.getParameter("Action");
		logger.info(new Date() +"Inside VehicleParkAddEditSave.htm "+UserName);

		try {
			String vehicleNo=req.getParameter("vehicleNo");
			String fromDateTime=req.getParameter("FromDateTime");
			String toDateTime=req.getParameter("ToDateTime");
			String vehicleAppId =req.getParameter("vehicleAppId") ;


			if(Action.equalsIgnoreCase("Add")) {
				VehicleParkingApplications vehicleParkingApplications=new VehicleParkingApplications();
				vehicleParkingApplications.setEmpNo(EmpNo);
				vehicleParkingApplications.setVehicleNo(vehicleNo);
				vehicleParkingApplications.setFromDateAndTime(fromDateTime);
				vehicleParkingApplications.setToDateAndTime(toDateTime);
				vehicleParkingApplications.setApplicStatus("N");
				vehicleParkingApplications.setApplicStatusCode("INI");
				vehicleParkingApplications.setCreatedBy(UserName);
				vehicleParkingApplications.setCreatedDate(sdtf.format(new Date()));
				vehicleParkingApplications.setIsActive(1);
				long result=service.addVehiclePark(vehicleParkingApplications);


				if(result>0) {
					redir.addAttribute("result", "Vehicle Parking Application Added Successfully");	
				} else {
					redir.addAttribute("resultfail", "Vehicle Parking Application Add Unsuccessful");	
				}
			}
			else if(Action.equalsIgnoreCase("Edit")) {
				VehicleParkingApplications vehicleParkingApplications=service.findVehicleById(Long.parseLong(vehicleAppId));
				vehicleParkingApplications.setVehicleNo(vehicleNo);
				vehicleParkingApplications.setFromDateAndTime(fromDateTime);
				vehicleParkingApplications.setToDateAndTime(toDateTime);
				vehicleParkingApplications.setModifiedBy(UserName);
				vehicleParkingApplications.setModifiedDate(sdtf.format(new Date()));
				long result = service.editVehiclePark(vehicleParkingApplications);
				if(result>0) {
					redir.addAttribute("result", "Vehicle Parking Application Edited Successfully");	
				} else {
					redir.addAttribute("resultfail", "Vehicle Parking Application Edit Unsuccessful");	
				}
			}
			return "redirect:/VehicleParking.htm";

		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAddSave.htm"+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping("VehicleParkinglApproval.htm")
	public String VehicleParkinglApproval(HttpServletRequest req, HttpSession ses){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			ses.setAttribute("formmoduleid", formmoduleid);
			ses.setAttribute("SidebarActive", "VehicleParkinglApproval_htm");
			//			String fromdate = req.getParameter("fromdate");
			//			String todate   =req.getParameter("todate");
			//			if(fromdate != null && todate != null ) {
			//				req.setAttribute("fromdate", fromdate);
			//				req.setAttribute("todate", todate);	
			//			} else {
			//				req.setAttribute("fromdate", DateTimeFormatUtil.getFirstDayofCurrentMonthRegularFormat());
			//				req.setAttribute("todate",DateTimeFormatUtil.SqlToRegularDate( ""+LocalDate.now()));	
			//			}



			req.setAttribute("VehicleParkList", service.getSecurityVehicleParkList(Username));
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside VehicleParkinglApproval.htm "+ Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
		return "vehicleparking/VehicleParkinglApprovalList";
	}


	@RequestMapping("VehicleParkingForward.htm")
	public String VehicleParkingForward(HttpServletRequest req, HttpSession ses, RedirectAttributes redir){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			String vehicleAppId =req.getParameter("vehicleAppId") ;
			String remarks =req.getParameter("remarks");
			String Action = req.getParameter("Action");
			if(Action.equals("FWD")) {
				long result=service.forwardVehicle(Long.parseLong(vehicleAppId), EmpNo, remarks, Username , Action);

				if (result > 0) {
					redir.addAttribute("result", "Vehicle Parking application Sent Successfully");
				} else {
					redir.addAttribute("resultfail", "Vehicle Parking application Sent Unsuccessful");
				}
			} else if(Action.equals("A")) {
				long result=service.forwardVehicle(Long.parseLong(vehicleAppId), EmpNo, remarks, Username , Action);

				if (result > 0) {
					redir.addAttribute("result", "Vehicle Parking application Approved Successfully");
				} else {
					redir.addAttribute("resultfail", "Vehicle Parking application Approve Unsuccessful");
				}
			}
			else if(Action.equals("R")){
				long result=service.forwardVehicle(Long.parseLong(vehicleAppId), EmpNo, remarks, Username , Action);

				if (result > 0) {
					redir.addAttribute("result", "Vehicle Parking application Rejected Successfully");
				} else {
					redir.addAttribute("resultfail", "Vehicle Parking application Rejection Unsuccessful");
				}
			}
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside VehicleParkingForward.htm "+ Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
		return "redirect:/VehicleParking.htm";
	}


}

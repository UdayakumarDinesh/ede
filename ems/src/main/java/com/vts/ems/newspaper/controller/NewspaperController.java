package com.vts.ems.newspaper.controller;

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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.chss.dto.CHSSContingentDto;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.newspaper.model.NewspaperContingent;
import com.vts.ems.newspaper.service.NewPaperServiceImpl;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;

// newspaper and Telephone claim controller

@Controller
public class NewspaperController {

	@Value("${NewspaperAuthority}")
	private String NewspaperAuthority;

	@Value("${TelephoneAuthority}")
	private String TelephoneAuthority;

	@Value("${PublicFundNo}")
	private String PublicFundNo;

	@Value("${NewsContingentVoucherNo}")
	private String NewsContingentVoucherNo;

	@Value("${TeleContingentVoucherNo}")
	private String TeleContingentVoucherNo;

	@Value("${NewsClaimHeader}")
	private String NewsClaimHeader;

	@Value("${NewsExpSacGOI}")
	private String NewsExpSacGOI;

	@Value("${TeleExpSncFileNo}")
	private String TeleExpSncFileNo;

	@Value("${ProjectFiles}")
	private String LabLogoPath;


	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();

	public String getLabLogoAsBase64() throws IOException {

		String path = LabLogoPath + "/images/lablogos/lablogo1.png";
		try {
			return Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(path)));
		} catch (FileNotFoundException e) {
			System.err.println("File Not Found at Path " + path);
		}
		return "/print/.jsp";
	}

	@Autowired
	private NewPaperServiceImpl service;

	@Autowired
	EmsFileUtils emsfileutils ;

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
			req.setAttribute("DGM", service.findEmpByEmpNo(service.GetDGMEmpNo(sesEmpNo)).getEmpName());
			req.setAttribute("PO", service.empOnLogintype("K")[1]);
			req.setAttribute("AO", service.empOnLogintype("W")[1]);
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
		Date date=new Date();
		String todayDate=rdf.format(date);
		int TodayMonth=DateTimeFormatUtil.getMonthFromRegularDate(todayDate);
		int TodayDate = Integer.parseInt(todayDate.substring(0, 2));
		try
		{
			Object[] PayLevelAndNewsRectrictAmt = service.getPayLevelAndNewsRectrictAmt(sesEmpNo);
			req.setAttribute("name", name);
			req.setAttribute("desig", designation);
			req.setAttribute("PayLevelAndNewsRectrictAmt", PayLevelAndNewsRectrictAmt);
			req.setAttribute("LabDetails", service.getLabDetails());
			req.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));
			req.setAttribute("NewsClaimHeader",NewsClaimHeader);
			req.setAttribute("todaymonth", TodayMonth);
			req.setAttribute("TodayDate", TodayDate);
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
					long AddNewspaperClaimResult = service.AddNewspaperClaim(sesEmpNo, ClaimMonth, ClaimYear,ClaimAmount, RestrictedAmount, PayLevelId, Username);
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
					mv.addObject("resultfail", "Claim Update Unsuccessfully");
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
		logger.info(new Date() + "Inside NewspaperEditView.htm " + Username);

		ModelAndView mv = new ModelAndView();

		try {
			String NewspaperId = request.getParameter("NewspaperId");
			Object[] CheckApproveOrNot = service.getCheckNewspaperApproveOrNot(NewspaperId);
			if (CheckApproveOrNot != null) 
			{
				Object[] NewspaperEditDetails = service.getNewspaperEditDetails(NewspaperId);
				mv.addObject("NewspaperEditDetails", NewspaperEditDetails);
				mv.addObject("name", name);
				mv.addObject("desig", designation);
				mv.addObject("LabDetails", service.getLabDetails());
				mv.addObject("LabCode",(String) ses.getAttribute("LabCode"));
				mv.setViewName("newspaper/NewspaperClaim");
			} 
			else 
			{
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

	@RequestMapping(value = "NewspaperClaimPreview.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
	public String NewspaperClaimPreview(HttpServletRequest request,HttpSession ses, HttpServletResponse res) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");


		String empNo=(String)ses.getAttribute("EmpNo");//--me added
		logger.info(new Date() + "Inside NewspaperClaimPreview.htm " + Username);

		//		ModelAndView mv = new ModelAndView();
		try {
			String NewspaperId = request.getParameter("NewspaperId");
			Object[] NewspaperUserPrintData = service.getNewspaperUserPrintData(NewspaperId);

			//			mv.addObject("name", name);
			//			mv.addObject("empNo", empNo);//-me added
			//			mv.addObject("desig", designation);
			//			mv.addObject("NewspaperUserPrintData", NewspaperUserPrintData);
			//			mv.addObject("LabDetails", service.getLabDetails());
			//			mv.addObject("LabCode",(String) ses.getAttribute("LabCode"));
			//			mv.addObject("NewsClaimHeader",NewsClaimHeader);
			//			mv.setViewName("newspaper/NewspaperUserClaimPrint");




			request.setAttribute("NewspaperUserPrintData", NewspaperUserPrintData);
			request.setAttribute("isApproval", request.getParameter("isApproval"));

			request.setAttribute("LabDetails", service.getLabDetails());
			request.setAttribute("LabCode", ses.getAttribute("LabCode"));
			request.setAttribute("NewsClaimHeader", NewsClaimHeader);


			request.setAttribute("LabLogo", getLabLogoAsBase64());


			return "newspaper/NewspaperUserClaimPreview";


		} 
		catch (Exception e) 
		{
			logger.error(new Date() + " Inside NewspaperClaimPreview.htm " + Username, e);
			e.printStackTrace();
			return "static/Error";
		}

	}



	@RequestMapping(value = "NewspaperPrint.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
	public String NewspaperPrint(HttpServletRequest request,HttpSession ses, HttpServletResponse res) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String name = (String) ses.getAttribute("EmpName");
		String designation = (String) ses.getAttribute("EmpDesig");

		String empNo=(String)ses.getAttribute("EmpNo");//--me added
		logger.info(new Date() + "Inside NewspaperPrint.htm " + Username);

		//		ModelAndView mv = new ModelAndView();
		try {
			String NewspaperId = request.getParameter("NewspaperId");
			Object[] NewspaperUserPrintData = service.getNewspaperUserPrintData(NewspaperId);

			//			mv.addObject("name", name);
			//			mv.addObject("empNo", empNo);//-me added
			//			mv.addObject("desig", designation);
			//			mv.addObject("NewspaperUserPrintData", NewspaperUserPrintData);
			//			mv.addObject("LabDetails", service.getLabDetails());
			//			mv.addObject("LabCode",(String) ses.getAttribute("LabCode"));
			//			mv.addObject("NewsClaimHeader",NewsClaimHeader);
			//			mv.setViewName("newspaper/NewspaperUserClaimPrint");



			String filename="";
			if( NewspaperId != null ) {
				request.setAttribute("NewspaperUserPrintData", NewspaperUserPrintData);
				filename="Newspaper_Claim_print";
			}

			request.setAttribute("name", name);
			request.setAttribute("empNo", empNo);
			request.setAttribute("designation", designation);
			request.setAttribute("LabDetails", service.getLabDetails());
			request.setAttribute("LabCode", ses.getAttribute("LabCode"));
			request.setAttribute("NewsClaimHeader", NewsClaimHeader);


			request.setAttribute("pagePart","3" );

			request.setAttribute("view_mode", request.getParameter("view_mode"));
			request.setAttribute("LabLogo", getLabLogoAsBase64());

			String path=request.getServletContext().getRealPath("/view/temp");
			request.setAttribute("path",path);

			CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			request.getRequestDispatcher("/view/newspaper/NewspaperUserClaimPrint.jsp").forward(request, customResponse);

			String html = customResponse.getOutput();        

			HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 

			res.setContentType("application/pdf");
			res.setHeader("Content-disposition","inline;filename="+filename+".pdf");


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

			return "redirect:/NewspaperList.htm";


		} 
		catch (Exception e) 
		{
			logger.error(new Date() + " Inside NewspaperPrint.htm " + Username, e);
			e.printStackTrace();
			return "static/Error";
		}

	}

	@RequestMapping(value = "NewspaperContingentAppro.htm", method = { RequestMethod.GET, RequestMethod.POST }) 
	public ModelAndView PrepNewspaperContingentBill(HttpServletRequest req,HttpSession ses) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		
		logger.info(new Date() + "Inside NewspaperContingentAppro.htm " + Username);

		ModelAndView mv = new ModelAndView();
		try {
			ses.setAttribute("SidebarActive", "NewspaperContingentAppro_htm");
			
			String fromdate = req.getParameter("fromdate");
			String todate = req.getParameter("todate");

			LocalDate today=LocalDate.now();

			if(fromdate==null) 
			{
				if(today.getMonthValue()<4) 
				{
					fromdate = String.valueOf(today.getYear()-1);
					todate=String.valueOf(today.getYear());

				}else{
					fromdate = String.valueOf(today.getYear());
					todate=String.valueOf(today.getYear()+1);
				}
				fromdate +="-04-01"; 
				todate +="-03-31";
			}else
			{
				fromdate=DateTimeFormatUtil.RegularToSqlDate(fromdate);
				todate=DateTimeFormatUtil.RegularToSqlDate(todate);
			}


//			req.setAttribute("ContingentList", service.getNewspaperApprovalList());
			req.setAttribute("fromdate", fromdate);
			req.setAttribute("todate", todate);
			req.setAttribute("logintype", LoginType);
			req.setAttribute("ContingentList", service.getNewspaperContingentList(LoginType, fromdate, todate));
			
			mv.setViewName("newspaper/NewspaperContingentBill");
			return mv;
		} catch (Exception e) {
			logger.error(new Date() + " Inside NewspaperContingentAppro.htm" + Username, e);
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
			mv.addObject("LabDetails", service.getLabDetails());
			mv.addObject("LabCode",(String) ses.getAttribute("LabCode"));
			mv.setViewName("newspaper/NewspaperReportPrint");

			return mv;
		} catch (Exception e) {
			logger.error(new Date() + " Inside NewspaperReportPrint.htm" + Username, e);
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
			mv.addObject("LabDetails", service.getLabDetails());
			mv.addObject("LabCode",(String) ses.getAttribute("LabCode"));
			mv.addObject("NewspaperAuthority",NewspaperAuthority);
			mv.addObject("PublicFundNo",PublicFundNo);
			mv.addObject("NewsContingentVoucherNo",NewsContingentVoucherNo);
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
			mv.addObject("LabDetails", service.getLabDetails());
			mv.addObject("LabCode",(String) ses.getAttribute("LabCode"));
			mv.addObject("NewsExpSacGOI",NewsExpSacGOI);
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


	//	@RequestMapping(value = "NewspaperApproval.htm")
	//	public ModelAndView newspaperapproval(HttpServletRequest request)  throws Exception
	//	{
	//		HttpSession ses= request.getSession(false);
	//		String Username = (String) ses.getAttribute("Username");
	//		logger.info(new Date() + "Inside NewspaperApproval.htm " + Username);
	//		try {
	//			ModelAndView mv = new ModelAndView("newspaper/NewspaperApproval");
	//
	//			List<Object[]> NewspaperApprovalList = service.getNewspaperApprovalList();
	//			mv.addObject("NewspaperApprovalList", NewspaperApprovalList);
	//			ses.setAttribute("SidebarActive", "NewspaperApproval_htm");
	//			return mv;
	//		} 
	//		catch (Exception e) {
	//			e.printStackTrace();
	//			logger.error(new Date() + " Inside NewspaperApproval.htm " + Username, e);
	//			return new ModelAndView("static/Error");
	//		}
	//
	//	}
	@RequestMapping(value = "NewspaperApproval.htm")
	public String newspaperapproval(HttpServletRequest req)  throws Exception
	{
		HttpSession ses= req.getSession(false);
		String Username = (String) ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String LoginType = (String) ses.getAttribute("LoginType");

		logger.info(new Date() + "Inside NewspaperApproval.htm " + Username);


		try {
			ses.setAttribute("SidebarActive", "NewspaperApproval_htm");
			List<String> DGMs = service.GetEmpDGMEmpNo();
			String poEmpno=(String) service.empOnLogintype("K")[0];
			String aoEmpno=(String) service.empOnLogintype("W")[0];
			
			if(aoEmpno.equalsIgnoreCase(EmpNo)) {
				req.setAttribute("NewspapepApproList", service.findAONewspaperList());
				return "newspaper/NewspaperApprovalList";
			} else if( DGMs.contains(EmpNo) || poEmpno.equalsIgnoreCase(EmpNo)) {
				req.setAttribute("NewspapepApproList", service.findDGMNewspaperList(EmpNo, LoginType));
				return "newspaper/NewspaperApprovalList";
			}  else {
				return "newspaper/NewspaperApprovalList";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + " Inside NewspaperApproval.htm " + Username, e);
			return "static/Error";
		}

	}
	
	@RequestMapping(value = "NewspaperListToBill.htm")
	public String newspaperapproved(HttpServletRequest req)  throws Exception
	{
		HttpSession ses= req.getSession(false);
		String Username = (String) ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		logger.info(new Date() + "Inside NewspaperListToBill.htm " + Username);


		try {
			String poEmpno=(String) service.empOnLogintype("K")[0];
			String todate = req.getParameter("todate");
			
			if(todate == null) 
			{
				if(LocalDate.now().getDayOfMonth()<20) {
					todate = LocalDate.now().minusMonths(1).withDayOfMonth(20).toString();
				}else
				{
					todate = LocalDate.now().withDayOfMonth(20).toString();
				}
				
			}else {
				todate = sdf.format(rdf.parse(todate));				
			}
			if(poEmpno.equalsIgnoreCase(EmpNo)) {
				req.setAttribute("todate", todate);
				req.setAttribute("NewspapepListToBill", service.findApprovedNewspaperList(todate));
				
			}
			
			return "newspaper/NewspaperListToBill";
		} 
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + " Inside NewspaperListToBill.htm " + Username, e);
			return "static/Error";
		}

	}
	
	@RequestMapping(value = "NewspaperContingentGenerate.htm", method = RequestMethod.POST)
	public String NewspaperContingentGenerate(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		logger.info(new Date() +"Inside NewspaperContingentGenerate.htm "+Username);
		try {

			String NewspaperId[] = req.getParameterValues("NewspaperId");

			String genTilldate = req.getParameter("genTilldate");		
			
			long count= service.ContingentGenerate(NewspaperId, Username, LoginType, EmpNo, genTilldate);

			
				if (count > 0) {
					redir.addAttribute("result", "Contingent Bill Generated Successfully");
				} else {
					redir.addAttribute("resultfail", "Contingent Bill Generation Unsuccessful");	
				}	
			redir.addFlashAttribute("contingentid",String.valueOf(count));
			
			return "redirect:/NewspaperContingentAppro.htm";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside NewspaperContingentGenerate.htm "+Username, e);
			return "static/Error";
		}
		
	}

	@RequestMapping(value = "NewspaperForward.htm" ) 
	public String NewspaperForward(HttpServletRequest req,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");

		logger.info(new Date() + "Inside NewspaperForward.htm " + Username) ;
		try
		{
			String NewspaperId=req.getParameter("NewspaperId");
			String remarks =req.getParameter("remarks");
			String action = req.getParameter("Action");
			if(action.equalsIgnoreCase("FWD") ) {
				long res = service.forwardNewspaper(Long.parseLong(NewspaperId), EmpNo, remarks, Username, action);

				if (res > 0) {
					redir.addAttribute("result", "Newspaper Claim application Sent for Processing  Successfully");
				} else {
					redir.addAttribute("resultfail", "Newspaper Claim application Sent for Processing Unsuccessful");	
				}	

				return "redirect:/NewspaperList.htm";
			} else if(action.equalsIgnoreCase("DGM-A") || action.equalsIgnoreCase("PO-A") || action.equalsIgnoreCase("AO-A")) {
				long res = service.forwardNewspaper(Long.parseLong(NewspaperId), EmpNo, remarks, Username, action);

				if (res > 0) {
					redir.addAttribute("result", "Newspaper Claim application Forwarded Successfully");
				} else {
					redir.addAttribute("resultfail", "Newspaper Claim application Forwarding Unsuccessful");	
				}

				return "redirect:/NewspaperApproval.htm";
			}else if( action.equalsIgnoreCase("PO-R") || action.equalsIgnoreCase("DGM-R") || action.equalsIgnoreCase("AO-R")) {
				long res = service.forwardNewspaper(Long.parseLong(NewspaperId), EmpNo, remarks, Username, action);

				if (res > 0) {
					redir.addAttribute("result", "Newspaper Claim application(s) Returned Successfully");
				} else {
					redir.addAttribute("resultfail", "Newspaper Claim application(s) Return Unsuccessful");	
				}	

				return "redirect:/NewspaperApproval.htm";
			} else {
				return "redirect:/NewspaperApproval.htm";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside NewspaperForward.htm " + Username, e);
			return "static/Error";
		}
	}

	
	@RequestMapping(value = "NewspaperContingetBill.htm", method = {RequestMethod.POST,RequestMethod.GET})
	public String NewspaperContingetBill(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String LoginType = (String) ses.getAttribute("LoginType");
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NewspaperContingetBill.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			String claim_view_mode=req.getParameter("claim_view_mode");
			
			if(contingentid==null) {
				Map md=model.asMap();
				contingentid=(String)md.get("contingentid");
				claim_view_mode=(String)md.get("claim_view_mode");
			}
			if(claim_view_mode==null) {
				claim_view_mode="A";
			}
			
			req.setAttribute("ContingentList", service.NewspaperContingentClaimList(contingentid));
			req.setAttribute("contingentdata", service.newspaperContingentData(contingentid));
			req.setAttribute("ApprovalAuth", "");
			req.setAttribute("LabLogo", getLabLogoAsBase64());
			req.setAttribute("labdata", "");
			req.setAttribute("contingentremarks", "");
			req.setAttribute("onlyview","Y");
			req.setAttribute("logintype",LoginType);
			req.setAttribute("view_mode",claim_view_mode);

			return "newspaper/NewspaperContingentBillView";
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside NewspaperContingetBill.htm "+Username, e);
			return "static/Error";
		}
	}

	@RequestMapping(value = "NewspaperApplyTransacStatus.htm" )
	public String newspaperTransactionStatus(Model model,HttpServletRequest req, HttpSession ses)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NewspaperApplyTransacStatus.htm "+ Username);
		try {
			String NewspaperId = req.getParameter("NewspaperId");
			req.setAttribute("TransactionList", service.newspaperTransaById(Long.parseLong(NewspaperId)));
			return "newspaper/NewspaperTransactionStatus";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside NewspaperApplyTransacStatus.htm "+ Username, e);
			return "static/Error";
		}
	}

	@RequestMapping(value = "NewspaperContingentClaimDrop.htm", method = RequestMethod.POST)
	public String ContingentFormDrop(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NewspaperContingetBill.htm "+Username);
		try {
			String[] NewspaperIds = req.getParameterValues("NewspaperId");
			String contingentid = req.getParameter("contingentid");
			
			long count = service.ContingentClaimDrop(NewspaperIds, Username);
			
			if (count > 0) {
				redir.addAttribute("result", "Contingent Bill Updated Successfully");
			} else {
				redir.addAttribute("resultfail", "Contingent Bill Update Unsuccessful");	
			}	
			
			redir.addFlashAttribute("contingentid", String.valueOf(contingentid));
			return "redirect:/NewspaperContingetBill.htm";		
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside NewspaperContingetBill.htm "+Username, e);
			return "static/Error";
		}
		
	}

	
	@RequestMapping(value = "NewspaperContingentDelete.htm", method = RequestMethod.POST)
	public String newspaperContingentDelete(HttpServletRequest req, HttpServletResponse response, HttpSession ses,RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside NewspaperContingentDelete.htm "+Username);
		long count=0;
		try {
			String contingentid = req.getParameter("contingentid");
			count = service.NewspaperContingentDelete(contingentid, Username);
			
			
			if (count > 0) {
				redir.addAttribute("result", "Bill Deleted Successfully");
			} else {
				redir.addAttribute("resultfail", "Bill Delete Unsuccessful");	
			}	
			
			
			return "redirect:/NewspaperContingentAppro.htm";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside NewspaperContingentDelete.htm "+Username, e);
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "NewspaperContingentApprove.htm", method = RequestMethod.POST)
	public String newspaperClaimsApprove(HttpServletRequest req, HttpServletResponse response, HttpSession ses, RedirectAttributes redir) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String LoginType = (String) ses.getAttribute("LoginType");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		String EmpNo =  ses.getAttribute("EmpNo").toString();
		logger.info(new Date() +"Inside NewspaperContingentApprove.htm "+Username);
		try {
			String contingentid = req.getParameter("contingentid");
			String action = req.getParameter("action");
			String remarks = req.getParameter("remarks");
			
						
			long count= service.newspaperClaimsApprove(contingentid, Username, action,remarks,LoginType, EmpNo, EmpId);
			
			if(action.equalsIgnoreCase("F") || action.equalsIgnoreCase("VF") || action.equalsIgnoreCase("AF") || action.equalsIgnoreCase("CF")) 
			{
				NewspaperContingent contingent = service.findNewspaperContingent(contingentid);
				if(contingent.getContingentStatusCode().equalsIgnoreCase("CSD"))
				{
					if (count > 0) {
						redir.addAttribute("result", "Contingent Bill Status Updated Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Status Update Unsuccessful");	
					}
					
					return "redirect:/ApprovedBills.htm";
					
				}
				else if(LoginType.equalsIgnoreCase("Z")) 
				{
					if (count > 0) {
						redir.addAttribute("result", "Contingent Bill Approved Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Approved Unsuccessful");	
					}
					
				}else if(LoginType.equalsIgnoreCase("W"))
				{
					if (count > 0) {
						redir.addAttribute("result", "Contingent Bill Recommended Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Recommend Unsuccessful");	
					}
				}
				else
				{
					if (count > 0)
					{
						redir.addAttribute("result", "Contingent Bill Forwarded Successfully");
					} else {
						redir.addAttribute("resultfail", "Contingent Bill Forward Unsuccessful");	
					}
				}
				
			}
			if(action.equalsIgnoreCase("R")) {
				if (count > 0) {
					redir.addAttribute("result", "Claim application(s) Returned Successfully");
				} else {
					redir.addAttribute("resultfail", "Claim application(s) Return Unsuccessful");	
				}	
			}
			
			return "redirect:/NewspaperContingentAppro.htm";
		} catch (Exception e) {
			
			e.printStackTrace();
			logger.error(new Date() +" Inside NewspaperContingentApprove.htm "+Username, e);
			return "static/Error";
		}
	}


	////////////////////////////////////////////////////////////////////// Telephone code ///////////////////////////////////////////////////////////////////



	@RequestMapping(value="TelephoneList.htm")
	public ModelAndView telephoneList(HttpServletRequest request,HttpSession ses) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside telephoneList.htm " + Username);
		ModelAndView mv=new ModelAndView("newspaper/TelephoneList");
		String sesEmpNo = (String) ses.getAttribute("EmpNo");

		List<Object[]> TeleClaimList=service.getTeleClaimList(sesEmpNo);
		request.setAttribute("TeleClaimList",TeleClaimList);


		List<Object[]> TeleDeviceList=service.getTeleDeviceList(sesEmpNo);
		request.setAttribute("TeleDeviceList",TeleDeviceList);

		List<Object[]> SendBackData=service.getTelephoneSendbackData(sesEmpNo);
		request.setAttribute("Sendbackdata",SendBackData);

		ses.setAttribute("SidebarActive", "TelephoneList_htm");

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



	@RequestMapping(value="TeleAddEditClaimSave.htm",method=RequestMethod.POST)
	public String TeleAddClaimSave(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside TeleAddEditClaimSave.htm " + Username);
		try {
			String BasicAmount[] = request.getParameterValues("BasicAmount");

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
						redir.addAttribute("resultfail", "You Already Applied Claim For "+ClaimMonth + "/" + ClaimYear);
					} 
					else 
					{
						int AddTelephoneClaimResult = service.AddTelephoneClaim(sesEmpNo, TotalBasic, TotalTax,
								GrossTotal, RestrictedAmount, TeleFromDate, TeleToDate, TeleUsersId, TeleBillNo,
								TeleBillDate, BasicAmount, TaxAmount, TotalAmount, PayLevelId, ClaimMonth,
								ClaimYear, IsBroadBand);

						if(AddTelephoneClaimResult>0) 
						{
							redir.addAttribute("result", "Claim adding Successful");
						}
						else
						{
							redir.addAttribute("resultfail", "Claim adding Unsuccessful");
						}

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

					if(EditTelephoneResult>0) 
					{
						redir.addAttribute("result", "Claim Update Successful");
					}
					else
					{
						redir.addAttribute("resultfail", "Claim Update Unsuccessful");
					}
				}
			} else {
				redir.addAttribute("resultfail", "Please Enter Balance Properly");
			}


			return "redirect:/TelephoneList.htm";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TeleAddEditClaimSave.htm " + Username, e);
			return "static/Error";
		}

	}


	@RequestMapping(value="TeleClaimDelete.htm",method=RequestMethod.POST)
	public String TeleClaimDelete(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside TeleClaimDelete.htm " + Username);
		try {
			// split TeleId and TeleForwardId ..
			String TeleId_TeleForwardId = request.getParameter("TeleId_TeleForwardId");
			String[] TeleId_TeleForwardIdArray = TeleId_TeleForwardId.split("_");
			String TeleId = TeleId_TeleForwardIdArray[0];

			Object[] CheckApproveForwardOrNot = service.getCheckTeleApproveForwardOrNot(TeleId);

			if (CheckApproveForwardOrNot != null) {
				int DeleteTelephoneResult = service.DeleteTelephone(TeleId, sesEmpNo);
				if(DeleteTelephoneResult>0) 
				{
					redir.addAttribute("result", "Claim Delete Successful");
				}
				else
				{
					redir.addAttribute("resultfail", "Claim Delete Unsuccessful");
				}
			} else {
				redir.addAttribute("resultfail", "Delete Restricted");
			}

			return "redirect:/TelephoneList.htm";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TeleClaimDelete.htm " + Username, e);
			return "static/Error";
		}
	}


	@RequestMapping(value="TelephoneClaimForward.htm",method=RequestMethod.POST)
	public ModelAndView TelephoneClaimForward(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneClaimForward.htm " + Username);
		try {

			if (request.getParameterValues("TeleId") != null) {

				String TeleId[] = request.getParameterValues("TeleId");

				if (TeleId.length >= 3) 
				{

					int UpdateTeleByTeleForwardId = service.UpdateTeleByTeleForwardId(sesEmpNo, TeleId);

					if(UpdateTeleByTeleForwardId>0) 
					{
						redir.addAttribute("result", "Claim Forward Successful");
					}
					else
					{
						redir.addAttribute("resultfail", "Claim Forward Unsuccessful");
					}


				} else {
					redir.addAttribute("resultfail", "Please Select At Least Three Months Claim");

				}

			} else {
				redir.addAttribute("resultfail", "Please Select At Least Three Months Claim");
			}

			mv.setViewName("redirect:/TelephoneList.htm");
			return mv;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneClaimForward.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}



	@RequestMapping(value="TelephoneApproval.htm")
	public ModelAndView TelephoneApproval(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneApproval.htm" + Username);
		try {
			ses.setAttribute("SidebarActive", "TelephoneApproval_htm");
			List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
			request.setAttribute("TelephoneApprovalList", TelephoneApprovalList);
			mv.setViewName("newspaper/TelephoneApproval");
			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneApproval.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}

	@RequestMapping(value="TelephoneApprovalSubmit.htm", method = RequestMethod.POST )
	public ModelAndView TelephoneApprovalSubmit(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneApprovalSubmit.htm" + Username);
		try {

			if (request.getParameter("TelephoneApproval") != null) {

				List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
				request.setAttribute("TelephoneApprovalList", TelephoneApprovalList);

				mv.setViewName("newspaper/TelephoneApproval");
				return mv;
			}
			if ( request.getParameterValues("TeleApproveAction") == null) 
			{
				redir.addAttribute("resultfail", "Please Select At Least One Option");
				redir.addAttribute("TelephoneApproval","TelephoneApproval");
				mv.setViewName("redirect:/TelephoneApproval.htm");
				return mv;
			}
			else 
			{

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

				if(TeleApprovalResult>0) 
				{
					redir.addAttribute("result", "Claims Approval Successful");
				}
				else
				{
					redir.addAttribute("resultfail", "Claims Approval Unsuccessful");
				}

				mv.setViewName("redirect:/TelephoneApprovedList.htm");
				return mv;
			}

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneApprovalSubmit.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}

	//telephone-period-edit

	@RequestMapping(value="TelephonePeriodEdit.htm",method=RequestMethod.POST)
	public ModelAndView TelephonePeriodEdit(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephonePeriodEdit.htm " + Username);
		try {
			String TeleBillId = request.getParameter("TeleBillId");
			Object[] TelephoneApprovalPeriodEditDetails = service.getTelephoneApprovalPeriodEditDetails(TeleBillId);
			request.setAttribute("TeleApprovalPeriodEditDetails", TelephoneApprovalPeriodEditDetails);
			mv.setViewName("newspaper/NewsTeleEditApprovalPeriod");
			return mv;

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephonePeriodEdit.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}

	@RequestMapping(value="TelephonePeriodEditSave.htm",method=RequestMethod.POST)
	public ModelAndView TelephonePeriodEditSave(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephonePeriodEditSave.htm " + Username);
		try {
			String TeleBillId = request.getParameter("TeleBillId");
			String FromDate = request.getParameter("FromDate");
			String ToDate = request.getParameter("ToDate");

			int UpdateTelePeriod = service.UpdateTelePeriod(sesEmpNo, TeleBillId, FromDate, ToDate);

			if(UpdateTelePeriod>0) 
			{
				redir.addAttribute("result", "Telephone Period Update Successful");
			}
			else
			{
				redir.addAttribute("resultfail", "Telephone Period Update Unsuccessful");
			}

			mv.setViewName("redirect:/TelephoneApprovedList.htm");

			return mv;

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephonePeriodEditSave.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}




	@RequestMapping(value="TelephoneAdd.htm",method=RequestMethod.POST)
	public ModelAndView TelephoneAdd(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		String designation = (String) ses.getAttribute("EmpDesig");
		String name = (String) ses.getAttribute("EmpName");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneAdd.htm " + Username);
		try {

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

				request.setAttribute("LabDetails", service.getLabDetails());
				request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));

				mv.setViewName("newspaper/TelephoneAdd");

			} else {
				request.setAttribute("resultfail", "Please Select At Least One Option");

				mv.setViewName("redirect:/TelephoneList.htm");

			}

			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneAdd.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}


	@RequestMapping(value="TelephoneEdit.htm",method=RequestMethod.POST)
	public ModelAndView TelephoneEdit(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String designation = (String) ses.getAttribute("EmpDesig");
		String name = (String) ses.getAttribute("EmpName");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneEdit.htm " + Username);
		try {
			// split TeleId and TeleForwardId ..
			String TeleId_TeleForwardId = request.getParameter("TeleId_TeleForwardId");
			String[] TeleId_TeleForwardIdArray = TeleId_TeleForwardId.split("_");
			String TeleId = TeleId_TeleForwardIdArray[0];

			Object[] CheckApproveOrNot = service.getCheckTeleApproveOrNot(TeleId);

			if (CheckApproveOrNot != null) {
				List<Object[]> TeleClaimEditDetails = service.getTeleClaimEditDetails(TeleId);
				request.setAttribute("TeleClaimEditDetails", TeleClaimEditDetails);
				request.setAttribute("name", name);
				request.setAttribute("desig", designation);

				request.setAttribute("LabDetails", service.getLabDetails());
				request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));


				mv.setViewName("newspaper/TelephoneEdit");

			} else 
			{
				redir.addAttribute("resultfail", "Editing this Claim is Restricted");
				mv.setViewName("redirect:/TelephoneList.htm");
			}
			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneEdit.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}

	@RequestMapping(value="TeleClaimPrint.htm",method=RequestMethod.POST)
	public ModelAndView TeleClaimPrint(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TeleClaimPrint.htm " + Username);
		try {
			// split TeleId and TeleForwardId ..
			String TeleId_TeleForwardId = request.getParameter("TeleId_TeleForwardId");
			String[] TeleId_TeleForwardIdArray = TeleId_TeleForwardId.split("_");
			String TeleForwardId = TeleId_TeleForwardIdArray[1];

			String TeleIdd = TeleId_TeleForwardIdArray[0];  //me added

			String emp = (String) ses.getAttribute("EmpNo");
			/*if (!("0".equalsIgnoreCase(TeleForwardId))) {
					List<Object[]> TelephoneUserPrintSingleData = service
							.getTelephoneUserPrintSingleData(TeleForwardId);
					List<Object[]> TelephoneUserPrintMultiData = service
							.getTelephoneUserPrintMultiData(TeleForwardId);*/

			if (!("0".equalsIgnoreCase(TeleForwardId))) {
				List<Object[]> TelephoneUserPrintSingleData = service
						.getTelephoneUserPrintSingleDataByMonth(TeleForwardId,TeleIdd);
				List<Object[]> TelephoneUserPrintMultiData = service
						.getTelephoneUserPrintMultiData(TeleForwardId);

				request.setAttribute("TelephoneUserPrintSingleData", TelephoneUserPrintSingleData);
				request.setAttribute("TelephoneUserPrintMultiData", TelephoneUserPrintMultiData);

				request.setAttribute("LabDetails", service.getLabDetails());
				request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));

				mv.setViewName("newspaper/TeleClaimPrint");

			} else {
				redir.addAttribute("resultfail", "Print Restricted");
				mv.setViewName("redirect:/TelephoneList.htm");

			}
			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TeleClaimPrint.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}


	@RequestMapping(value="TelephonePrintReport.htm",method=RequestMethod.POST)
	public ModelAndView TelephonePrintReport(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephonePrintReport.htm " + Username);
		try {
			String TeleBillId = request.getParameter("TeleBillId");

			List<Object[]> TelephonePrintReportSingleData = service.getTelephonePrintReportSingleData(TeleBillId);
			List<Object[]> TelephonePrintReportMultiData = service.getTelephonePrintReportMultiData(TeleBillId);

			request.setAttribute("TelephonePrintReportSingleData", TelephonePrintReportSingleData);
			request.setAttribute("TelephonePrintReportMultiData", TelephonePrintReportMultiData);
			request.setAttribute("LabDetails", service.getLabDetails());
			request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));

			mv.setViewName("newspaper/TelephoneReportPrint");

			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephonePrintReport.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}

	@RequestMapping(value="TelephoneContingentBill.htm", method=RequestMethod.POST)
	public ModelAndView TelephoneContingentBill(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneContingentBill.htm " + Username);
		try {
			String TeleBillId = request.getParameter("TeleBillId");
			Object[] TelephoneContingentBillPrint = service.getTelephoneContingentBillPrintData(TeleBillId);
			request.setAttribute("TelephoneContingentBillPrint", TelephoneContingentBillPrint);
			request.setAttribute("LabDetails", service.getLabDetails());
			request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));
			request.setAttribute("TeleContingentVoucherNo", TeleContingentVoucherNo);
			request.setAttribute("TelephoneAuthority", TelephoneAuthority);
			request.setAttribute("PublicFundNo",PublicFundNo);

			mv.setViewName("newspaper/TelephoneContingentBill");

			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneContingentBill.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}


	@RequestMapping(value="TelephoneSendback.htm",method=RequestMethod.POST)
	public ModelAndView TelephoneSendback(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneSendback.htm " + Username);
		try {
			List<Object[]> TelephoneApprovalList = service.getTelephoneApprovalList();
			request.setAttribute("TelephoneApprovalList", TelephoneApprovalList);
			mv.setViewName("newspaper/TelephoneSendBack");
			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneSendback.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}


	@RequestMapping(value="TelephoneSendbackSubmit.htm",method=RequestMethod.POST)
	public ModelAndView TelephoneSendbackSubmit(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneSendbackSubmit.htm " + Username);
		try {
			String[] TeleApproveAction = request.getParameterValues("TeleApproveAction");
			String FromDate = request.getParameter("FromDate");
			String ToDate = request.getParameter("ToDate");

			Map<String, String> map = new LinkedHashMap<>();

			for (String TeleForwardId : TeleApproveAction) {
				String Textcomments = "::" + request.getParameter("remark" + TeleForwardId);
				map.put(TeleForwardId, Textcomments);

			}

			int TeleApprovalResult = service.TeleSendback(map, FromDate, ToDate, sesEmpNo);
			if(TeleApprovalResult>0) 
			{
				redir.addAttribute("result", "Telephone Claim send Back Successful");
			}
			else
			{
				redir.addAttribute("resultfail", "Telephone Claim send Back Unsuccessful");
			}

			mv.setViewName("redirect:/TelephoneApprovedList.htm");

			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneSendbackSubmit.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}


	@RequestMapping(value={"/telephonedeviceadd","/telephonedeviceedit","/telephonedevicedelete","/telephone-claim-forward","/telephone-sendback",},method=RequestMethod.POST)
	public ModelAndView telephoneAddEditDeletePrintApproval(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside telephonedeviceadd.htm " + Username);
		ModelAndView mv = new ModelAndView();
		if (request.getParameter("TeleApprovedReportYearWise") != null) 
		{
			mv.setViewName("newspaper/TeleApprovalReportYearWise");
		}
		return mv;
	} 

	@RequestMapping(value="TelephoneExpenditureSanction.htm",method=RequestMethod.POST)
	public ModelAndView TelephoneExpenditureSanction(HttpServletRequest request,HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		ModelAndView mv = new ModelAndView();
		logger.info(new Date() + "Inside TelephoneExpenditureSanction.htm " + Username);
		try {
			String TeleBillId = request.getParameter("TeleBillId");
			Object[] TeleExpSancReport = service.getTelephoneContingentBillPrintData(TeleBillId);
			request.setAttribute("TeleExpSancReport", TeleExpSancReport);
			request.setAttribute("LabDetails", service.getLabDetails());
			request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));
			request.setAttribute("TeleExpSncFileNo",TeleExpSncFileNo);
			request.setAttribute("TelephoneAuthority",TelephoneAuthority);
			mv.setViewName("newspaper/TeleExpSancReport");

			return mv;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			logger.error(new Date() + " Inside TelephoneExpenditureSanction.htm " + Username, e);
			return new ModelAndView("static/Error");
		}

	}

	@RequestMapping(value="TelephoneApprovedList.htm")
	public ModelAndView telephoneApprovedList(HttpServletRequest request,HttpSession ses)
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside TelephoneApprovedList.htm " + Username);
		ModelAndView mv=new ModelAndView("newspaper/TelephoneApprovedList");
		List<Object[]> TelephoneClaimApprovedList=service.getTelephoneClaimApprovedList();
		request.setAttribute("TelephoneClaimApprovedList", TelephoneClaimApprovedList);

		List<Object[]> TelephoneApprovalList=service.getTelephoneApprovalList();
		request.setAttribute("TeleCount",TelephoneApprovalList.size());

		ses.setAttribute("SidebarActive", "TelephoneApprovedList_htm");

		return mv;
	} 

	@RequestMapping(value="/telephone-approval-individualclaim-details")
	public ModelAndView telephoneIndividualClaimDetailsPrint(HttpServletRequest request,HttpSession ses) throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() + "Inside telephone-approval-individualclaim-details " + Username);
		ModelAndView mv=new ModelAndView();

		if(request.getParameter("TeleForwardId")!=null)
		{
			String TeleForwardId=request.getParameter("TeleForwardId");	

			List<Object[]> TelephoneUserPrintSingleData=service.getTelephoneUserPrintSingleData(TeleForwardId);
			List<Object[]> TelephoneUserPrintMultiData=service.getTelephoneUserPrintMultiData(TeleForwardId);

			request.setAttribute("TelephoneUserPrintSingleData", TelephoneUserPrintSingleData);
			request.setAttribute("TelephoneUserPrintMultiData", TelephoneUserPrintMultiData);

			request.setAttribute("LabDetails", service.getLabDetails());
			request.setAttribute("LabCode",(String) ses.getAttribute("LabCode"));

			mv.setViewName("newspaper/TeleClaimPrint");

		}
		else
		{
			request.setAttribute("PrintRestricted","PrintRestricted");
			mv.setViewName("newspaper/PrintError");

		}

		return mv;
	} 

	@RequestMapping(value="NewsPaperFinalAppPrint.htm",  method = {RequestMethod.POST,RequestMethod.GET})
	public String NewsPaperFinalPrint(HttpServletRequest req, HttpSession ses, HttpServletResponse res){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			String ClaimMonth = req.getParameter("ClaimMonth");
			String ClaimYear = req.getParameter("ClaimYear");
			String contingentid = req.getParameter("contingentid");


			String filename="";
			if( ClaimMonth != null && ClaimYear!= null ) {
				req.setAttribute("newsPaperFinalAppro", service.NewspaperAllApprovedOrNot(ClaimMonth, ClaimYear));
				filename="NewsPaper_Final_Approval";
			} else {
				req.setAttribute("newsPaperFinalAppro", service.newspapersByContingentBill(Long.parseLong(contingentid)));
				req.setAttribute("ContingentBillTrans", service.newspaperContningentApprTransById(Long.parseLong(contingentid)));
				req.setAttribute("Bill", "YES");
				filename="NewsPaper_Contingent_Bill";
			}

			
			req.setAttribute("pagePart","3" );

			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));

			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);

			CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/newspaper/NewsPaperFinalApprovalPrint.jsp").forward(req, customResponse);

			String html = customResponse.getOutput();        

			HtmlConverter.convertToPdf(html,new FileOutputStream(path+File.separator+filename+".pdf")) ; 

			res.setContentType("application/pdf");
			res.setHeader("Content-disposition","inline;filename="+filename+".pdf");


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

			return "redirect:/NewspaperApprovedList.htm";
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside NewsPaperFinalApp.htm "+Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping(value="TelePhoneFinalAppPrint.htm",  method = {RequestMethod.POST,RequestMethod.GET})
	public String TelePhineFinalPrint(HttpServletRequest req, HttpSession ses, HttpServletResponse res){
		String Username=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			String ClaimMonth = req.getParameter("ClaimMonth");
			String ClaimYear = req.getParameter("ClaimYear");


			String filename="";
			if( ClaimMonth != null && ClaimYear!= null ) {
				req.setAttribute("TelePhoneFinalAppro", service.TelePhoneAllApprovedOrNot(ClaimMonth, ClaimYear));
				filename="Telephone_Final_Approval";
			}

			req.setAttribute("pagePart","3" );

			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo", getLabLogoAsBase64());

			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);

			CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/newspaper/TelePhoneFinalApprovalPrint.jsp").forward(req, customResponse);

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

			return "redirect:/TelephoneApprovedList.htm";
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside TelePhoneFinalAppPrint.htm "+Username ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

}

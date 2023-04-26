package com.vts.ems.athithi.controller;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.athithi.service.PassService;
import com.vts.ems.utils.CharArrayWriterResponse;

@Controller
public class PassController {

	@Autowired
	PassService service;
	
	private static final Logger logger = LogManager.getLogger(PassController.class);
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	@RequestMapping(value = "pendingIntimations.htm",method =RequestMethod.GET)
	public String pendingIntimations(HttpServletRequest req,HttpSession ses) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER pendingIntimations_htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "pendingIntimations_htm");
			req.setAttribute("pendingList", service.pendingIntimations());
			return "athithi/pendingIntimations";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER pendingIntimations.htm "+UserId, e);
			return "static/Error";
		}	
	}
	
	
	@RequestMapping(value = "intimationDetails.htm",method =RequestMethod.POST)
	public String intimationDetails(HttpServletRequest req,HttpSession ses) throws Exception {
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER intimationDetails.htm "+UserId);		
		try {
			String intimationId=req.getParameter("intimationId");
			req.setAttribute("intimationDetail", service.intimationDetail(intimationId));
			req.setAttribute("intimationVisitor", service.intimationVisitor(intimationId));
			
			return "athithi/intimationDetails";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER intimationDetails.htm "+UserId, e);
			return "static/Error";
		}
	}
	
	private static final String UPLOAD_DIRECTORY = "D:\\visitorPhoto\\";
	@RequestMapping(value = "uploadPhoto", method = RequestMethod.POST)
	public @ResponseBody String uploadPhoto(@RequestParam("project") String imageValue, HttpServletRequest request,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER uploadPhoto "+UserId);		
	
		String visitorId=request.getParameter("vistoriId");
		String photoName=visitorId+"."+"jpg";
		service.updatePhoto(visitorId,photoName);
		try
        {
            byte[] imageByte=org.apache.tomcat.util.codec.binary.Base64.decodeBase64(imageValue);
            ServletContext context= ses.getServletContext();
            String path=context.getRealPath(UPLOAD_DIRECTORY)+"/"+photoName;
            new FileOutputStream(UPLOAD_DIRECTORY+photoName+"\\").write(imageByte);
            return "success ";
        }
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER uploadPhoto "+UserId, e);
			return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "createPassSubmit",method =RequestMethod.POST)
	public String createPassSubmit(HttpServletRequest req,HttpSession ses,HttpServletResponse res,RedirectAttributes redir) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER createPassSubmit "+UserId);		
		try {
			
			String intimationId=req.getParameter("IntimationId");
			String empId=ses.getAttribute("EmpId").toString();
			String[] visitorId=req.getParameterValues("visitorIds[]");
			String[] visitorBatchId=req.getParameterValues("visitorBatch[]");
			visitorId=visitorId[0].split(",");
			visitorBatchId=visitorBatchId[0].split(",");
			
			Long result =service.createPass(empId,intimationId,visitorId,visitorBatchId);
			redir.addFlashAttribute("intimationId",intimationId);		
			redir.addFlashAttribute("passID",result+"");	
			return "redirect:/createdPass.htm";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER createPassSubmit "+UserId, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "passPrint",method=RequestMethod.GET)
	public void passPrint(HttpServletRequest req,HttpSession ses,HttpServletResponse res) throws Exception 
	{
		
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER passPrint "+UserId);		
		try {
		 
	        String intimationId=req.getParameter("intimationId");
	        String result=req.getParameter("passId");
			
			List<Object[]> intimation=service.getIntimationDetails(intimationId,result+"");
	        List<Object[]> visitorList=service.getPassVisitorList(intimationId,result+"");
	        req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
	        req.setAttribute("LabDetails",service.LabInfo());
			req.setAttribute("passDetails",intimation );
			req.setAttribute("visitorList",visitorList );
			String path=req.getServletContext().getRealPath("/view/temp");
			CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
			req.getRequestDispatcher("/view/athithi/PrintPass.jsp").forward(req, customResponse);
			String html = customResponse.getOutput();
		         
		    String filename = "PrintPass";
		    HtmlConverter.convertToPdf(html, new FileOutputStream(path + File.separator + filename + ".pdf"));

			res.setContentType("application/pdf");
			res.setHeader("Content-disposition", "inline;filename=" + filename + ".pdf");
			File f = new File(path + File.separator + filename + ".pdf");
			FileInputStream fis = new FileInputStream(f);
			DataOutputStream os = new DataOutputStream(res.getOutputStream());
			res.setHeader("Content-Length", String.valueOf(f.length()));
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
			os.close();
			fis.close();

			Path pathOfFile = Paths.get(path + File.separator + filename + ".pdf");
			Files.delete(pathOfFile);
	
			
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER passPrint "+UserId, e);
//			return "static/Error";
		}
	}
	
	@RequestMapping(value = "createdPass.htm",method= {RequestMethod.GET, RequestMethod.POST })
	public String createdPass(HttpServletRequest req,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER createdPass.htm "+UserId);		
		try {
			ses.setAttribute("SidebarActive", "createdPass_htm");

			String fDate=req.getParameter("fdate");
			String tDate=req.getParameter("tdate");
			
			  DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			  Calendar cal = Calendar.getInstance();
		 	  String to=dateFormat.format(cal.getTime()).toString();
				/* cal.add(Calendar.MONTH, -1); */
		 	  String from =dateFormat.format(cal.getTime()).toString();
			
			if(fDate!=null) {
				req.setAttribute("createdPassList", service.getCreatedPassList(new java.sql.Date(sdf.parse(fDate).getTime()).toString(), new java.sql.Date(sdf.parse(tDate).getTime()).toString()));
				req.setAttribute("fdate",fDate);
				req.setAttribute("tdate",tDate);
			}else{
			   req.setAttribute("createdPassList", service.getCreatedPassList(new java.sql.Date(sdf.parse(from).getTime()).toString(), new java.sql.Date(sdf.parse(to).getTime()).toString()));
			   req.setAttribute("fdate",from);
			   req.setAttribute("tdate",to);
			}
		
			
			return "athithi/createdPass";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER createdPass.htm "+UserId, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "passVisitorList", method = RequestMethod.GET)
	public @ResponseBody String passVisitorList(HttpServletRequest request,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER passVisitorList "+UserId);
		List<Object[]> passVisitorList  = new ArrayList<>();
		try {
			String passId=request.getParameter("passId");
			 passVisitorList=service.getPassVisitorList(passId);
			
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER passVisitorList "+UserId, e);
			return "static/Error";
		}
		Gson json = new Gson();
		return json.toJson(passVisitorList);
	}
	
	@RequestMapping(value = "passVisitorSubmit", method = RequestMethod.GET)
	public @ResponseBody String passVisitorSubmit(HttpServletRequest request,HttpSession ses) throws Exception 
	{
		String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER passVisitorList "+UserId);
		int passVisitorList  = 0;
		try {
			String passId=request.getParameter("passEmpId");
			 passVisitorList=service.passVisitorSubmit(passId);
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER passVisitorList "+UserId, e);
			return "static/Error";
		}
		Gson json = new Gson();
		return json.toJson(passVisitorList);
	}
	
    @RequestMapping(value="passReport.htm",method = {RequestMethod.GET, RequestMethod.POST })
    public String passReport(HttpServletRequest req, HttpSession ses ) throws ParseException, Exception 
    {
    	String UserId = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CONTROLLER passReport.htm "+UserId);
		try {
			ses.setAttribute("SidebarActive", "passReport_htm");

	    	String fDate=req.getParameter("fdate");
			String tDate=req.getParameter("tdate");
		
			  DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			  Calendar cal = Calendar.getInstance();
		 	  String to=dateFormat.format(cal.getTime()).toString();
			 cal.add(Calendar.MONTH, -1); 
		 	  String from =dateFormat.format(cal.getTime()).toString();
			
			if(fDate!=null) {
				req.setAttribute("passReportList", service.getPassReport(new java.sql.Date(sdf.parse(fDate).getTime()).toString(), new java.sql.Date(sdf.parse(tDate).getTime()).toString()));
				req.setAttribute("fdate",fDate);
				req.setAttribute("tdate",tDate);
			}else{
			   req.setAttribute("passReportList", service.getPassReport(new java.sql.Date(sdf.parse(from).getTime()).toString(), new java.sql.Date(sdf.parse(to).getTime()).toString()));
			   req.setAttribute("fdate",from);
				req.setAttribute("tdate",to);
			}
			return "athithi/passReport";
		}
		catch (Exception e) {
			e.printStackTrace(); 
			logger.error(new Date() +"Inside CONTROLLER passReport.htm "+UserId, e);
			return "static/Error";
		}
    }
 
 
	
}

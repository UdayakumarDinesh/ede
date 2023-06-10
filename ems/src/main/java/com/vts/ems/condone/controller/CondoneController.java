package com.vts.ems.condone.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.User;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.jsf.FacesContextUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.condone.model.SEIPCondone;
import com.vts.ems.condone.service.CondoneServiceImpl;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class CondoneController {


	@Autowired
	CondoneServiceImpl service;

	private static final Logger logger = LogManager.getLogger(CondoneController.class);
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();

	
	@RequestMapping(value = "CondonationDashboard.htm")
	public String CondonationDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondonationDashboard.htm "+Username);
		try {
			
			ses.setAttribute("formmoduleid", "36");
			ses.setAttribute("SidebarActive", "CondonationDashboard_htm");
			
			
			return "condone/CondonationDashboard";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondonationDashboard.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "EmpCondonation.htm")
	public String EmpCondonation(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String EmpNo = (String) ses.getAttribute("EmpNo");
		logger.info(new Date() +"Inside EmpCondonation.htm "+Username);
		try {
			
			ses.setAttribute("formmoduleid", "36");
			ses.setAttribute("SidebarActive", "EmpCondonation_htm");
			
			req.setAttribute("EmpCondoneList", service.EmpCondoneList(EmpNo));
			
			return "condone/EmpCondonationList";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EmpCondonation.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CondoneAdd.htm")
	public String CondoneAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
//		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
		String EmpNo = (String) ses.getAttribute("EmpNo");
		logger.info(new Date() +"Inside CondoneAdd.htm "+Username);
		try {
			
			req.setAttribute("EmpNo", EmpNo);
			req.setAttribute("CondoneTypesList", service.CondoneTypesList());
			req.setAttribute("LabMaster", service.getLabInfo());
			req.setAttribute("EmployeeInfo", service.getEmployeeInfo(EmpNo) );
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));

			return "condone/CondonationAdd";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneAdd.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CondoneAddSubmit.htm")
	public String CondoneAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir
			,@RequestParam(name="condone_attach", required =false)MultipartFile[] condone_attach )  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondoneAddSubmit.htm "+Username);
		try {
			
			String FormEmpNo = req.getParameter("EmpNo");
			String subject = req.getParameter("condone_subject");
			String condone_type = req.getParameter("condone_type");
			String content = req.getParameter("condone_content");
			
			SEIPCondone condone = new SEIPCondone().builder()
													.EmpNo(FormEmpNo)
													.Subject(subject)
													.CondoneTypeId(Integer.parseInt(condone_type))
													.MainContent(content)
													.CondoneDate(LocalDate.now().toString())
													.CondoneStatusCode("INI")
													.CondoneNextStatus("INI")
													.IsAccepted("N")
													.CreatedBy(Username)
													.CreatedDate(sdtf.format(new Date()))
													.IsActive(1)
													.build();
			long ret= service.CondoneAddSubmit(condone,condone_attach);
			
			if(ret != 0){
    			redir.addAttribute("result", "Condonation Saved SuccessFully");
			}else{
				redir.addAttribute("resultfail", "Condonation Saving Unsuccessfull");
			}
			
			return "redirect:/EmpCondonation.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneAddSubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CondoneEdit.htm")
	public String CondoneEdit(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondoneEdit.htm "+Username);
		try {
			
			String CondoneId = req.getParameter("CondoneId");
			if (CondoneId == null) 
			{
				Map md=model.asMap();
				CondoneId=(String)md.get("CondoneId");
			}	
			
			Object[] condoneData =service.getCondoneData(CondoneId);
			String EmpNo=condoneData[1].toString();
			
			req.setAttribute("condoneData", condoneData);
			req.setAttribute("CondoneTypesList", service.CondoneTypesList());
			req.setAttribute("LabMaster", service.getLabInfo());
			req.setAttribute("EmployeeInfo", service.getEmployeeInfo(EmpNo) );
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("CondoneAttachList", service.getCondoneAttachList(CondoneId));
			return "condone/CondonationEdit";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneEdit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CondoneEditSubmit.htm")
	public String CondoneEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir )  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondoneEditSubmit.htm "+Username);
		try {
			
			String CondoneId = req.getParameter("CondoneId");

			String subject = req.getParameter("condone_subject");
			String condone_type = req.getParameter("condone_type");
			String content = req.getParameter("condone_content");
			
			SEIPCondone condone = service.getSEIPCondone(CondoneId);
			
			condone.setSubject(subject);
			condone.setCondoneTypeId(Integer.parseInt(condone_type));
			condone.setMainContent(content);
//			condone.setCondoneDate(LocalDate.now().toString());
			condone.setModifiedBy(Username);
			condone.setModifiedDate(sdtf.format(new Date()));
													
			long ret= service.CondoneEditSubmit(condone);
			
			if(ret != 0){
    			redir.addAttribute("result", "Condonation Updated SuccessFully");
			}else{
				redir.addAttribute("resultfail", "Condonation Update Unsuccessfull");
			}
			
			return "redirect:/EmpCondonation.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneEditSubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CondoneAttachSubmit.htm")
	public String CondoneAttachSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,
			@RequestParam(name="condone_attach", required =false)MultipartFile[] condone_attach)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondoneAttachSubmit.htm "+Username);
		try {
			
			String CondoneId = req.getParameter("CondoneId");

			long ret= service.CondoneAttachSubmit(CondoneId,condone_attach);
			
			if(ret != 0){
    			redir.addAttribute("result", "Attachment Added SuccessFully");
			}else{
				redir.addAttribute("resultfail", "Attachment Add Unsuccessfull");
			}
			
			redir.addFlashAttribute("CondoneId",CondoneId);
			return "redirect:/CondoneEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneEditSubmit.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CondoneAttachDelete.htm")
	public String CondoneAttachDelete(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,
			@RequestParam(name="condone_attach", required =false)MultipartFile[] condone_attach)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondoneAttachDelete.htm "+Username);
		try {
			
			String CondoneId = req.getParameter("CondoneId");
			String CondoneAttachId = req.getParameter("condone_attach_id");

			long ret= service.CondoneAttachDelete(CondoneAttachId,Username,sdtf.format(new Date()));
			
			if(ret != 0){
    			redir.addAttribute("result", "Attachment Deleted SuccessFully");
			}else{
				redir.addAttribute("resultfail", "Attachment Delete Unsuccessfull");
			}
			redir.addFlashAttribute("CondoneId",CondoneId);
			return "redirect:/CondoneEdit.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneAttachDelete.htm "+Username, e);
			return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "CondonePreview.htm")
	public String CondonePreview(Model model, HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CondonePreview.htm "+Username);
		try {
			
			String CondoneId = req.getParameter("CondoneId");
			String isApproval = req.getParameter("isApproval");
			if (CondoneId == null) 
			{
				Map md=model.asMap();
				CondoneId=(String)md.get("CondoneId");
			}	
			
			Object[] condoneData =service.getCondoneData(CondoneId);
			String EmpNo=condoneData[1].toString();
			
			

			req.setAttribute("isApproval", isApproval);
			req.setAttribute("condoneData", condoneData);
			req.setAttribute("CondoneTypesList", service.CondoneTypesList());
			req.setAttribute("LabMaster", service.getLabInfo());
			req.setAttribute("EmployeeInfo", service.getEmployeeInfo(EmpNo) );
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			req.setAttribute("CondoneAttachList", service.getCondoneAttachList(CondoneId));
			return "condone/CondonationPreview";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondonePreview.htm "+Username, e);
			return "static/Error";
		}
	}
	
	@RequestMapping(value = "CondoneForward.htm")
	public String CondoneForward(Model model, HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");
		logger.info(new Date() +"Inside CondoneForward.htm "+Username);
		try {
			
			String CondoneId = req.getParameter("CondoneId");
			String action = req.getParameter("action");
			String remarks = req.getParameter("remarks");
			service.condonationForward(CondoneId, action, remarks,EmpNo);
			
			return "redirect:/EmpCondonation.htm";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside CondoneForward.htm "+Username, e);
			return "static/Error";
		}
	}
	
}

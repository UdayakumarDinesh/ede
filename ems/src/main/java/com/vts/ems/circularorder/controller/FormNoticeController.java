package com.vts.ems.circularorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.circularorder.dto.FormUploadDto;
import com.vts.ems.circularorder.dto.NoticeUploadDto;
import com.vts.ems.circularorder.model.EMSForms;
import com.vts.ems.circularorder.model.EMSNotice;
import com.vts.ems.circularorder.service.FormNoticeService;
import com.vts.ems.utils.DateTimeFormatUtil;


@Controller
public class FormNoticeController {

	private static final Logger logger = LogManager.getLogger(FormNoticeController.class);
	
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Autowired
	FormNoticeService service;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	@RequestMapping(value = "EMSForms.htm")
	public String SEIPForms( HttpServletRequest req, HttpSession ses) throws Exception
	{
		String LoginType=(String)ses.getAttribute("LoginType");
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSForms.htm "+UserId);
		try {
			String DepTypeId=req.getParameter("DepTypeId");
			if(DepTypeId==null)
			{
				DepTypeId="A";
			}
			
			req.setAttribute("DepTypeList",service.GetEmsDepTypes());
			req.setAttribute("FormsList", service.getEmsFormsList(DepTypeId));
			
			req.setAttribute("DepTypeId",DepTypeId);
			req.setAttribute("LoginType",LoginType);
			
			
			ses.setAttribute("SidebarActive", "EMSForms_htm");
			return "circular/EMSFormsList";
			
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSForms.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "EMSFormAdd.htm")
	public String EMSFormAdd( HttpServletRequest req, HttpSession ses) throws Exception
	{
		req.setAttribute("DepTypeId",req.getParameter("DepTypeId"));
		req.setAttribute("DepTypeList",service.GetEmsDepTypes());
		return "circular/EMSFormAdd";
	}
	
	
	@RequestMapping(value = "EMSFormAddSubmit.htm")
	public String EMSFormAddSubmit( HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("FormFile") MultipartFile FormFile) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSFormAddSubmit.htm "+UserId);
		try {
			
			String DepTypeId=req.getParameter("DepTypeId");
			String FormNo=req.getParameter("FormNo");
			String description=req.getParameter("description");
			
			
			FormUploadDto dto= FormUploadDto.builder()
					.DepTypeId(DepTypeId)
					.FormFile(FormFile)
					.FormNo(FormNo)
					.Description(description)
					.CreatedBy(UserId)
					.build();
			long count = service.EmsFormAdd(dto);
			
			if (count != 0) {
				redir.addAttribute("result", "Form Upload Successsfull");
			} else {
				redir.addAttribute("resultfail", "Form Upload UnSuccesssful");
			}
			redir.addAttribute("DepTypeId", DepTypeId);
			return "redirect:/EMSForms.htm";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSFormAddSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "EMSFormDownload.htm")
    public void EMSFormDownload(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
	{				
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSFormDownload.htm "+UserId);
		try {
			String EMSFormId=req.getParameter("EMSFormId");
			
			EMSForms form=service.GetEMSForm(EMSFormId);
			
			res.setContentType("Application/octet-stream");	
			File my_file=new File(emsfilespath+form.getFormPath());
			res.setHeader("Content-disposition","attachment;filename="+form.getFormName());
			OutputStream out=res.getOutputStream();
			
			FileInputStream in=new FileInputStream(my_file);
			byte[] buffer=new byte[4096];
			int length;
			while((length=in.read(buffer))>0){
			out.write(buffer,0,length);
			}
			in.close();
			out.flush();
			out.close();
		}catch(Exception e) {
			logger.error(new Date() +"Inside EMSFormDownload.htm "+UserId,e);
			e.printStackTrace();
		}
    }

	@RequestMapping(value = "FormNoCheckAjax.htm", method = RequestMethod.GET)
	public @ResponseBody String FormNoCheckAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside CHSSMedicinesListAjax.htm "+Username);
		long count =0;
		try {
			String formNo = req.getParameter("FormNo");
			count = service.getFormNoCount(formNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside FormNoCheckAjax.htm "+Username, e);
		}
		Gson json = new Gson();
		return json.toJson(count);
	}
	
	
	@RequestMapping(value = "EMSFormDelete.htm")
	public String EMSFormDelete( HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSFormDelete.htm "+UserId);
		try {
			String EMSFormId=req.getParameter("EMSFormId");
						
			long count = service.EMSFormDelete(EMSFormId,UserId);
			
			if (count != 0) {
				redir.addAttribute("result", "Form Deleted Successsfull");
			} else {
				redir.addAttribute("resultfail", "Form Delete UnSuccesssful");
			}
			return "redirect:/EMSForms.htm";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSFormDelete.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "EMSNotices.htm")
	public String EMSNotices(Model model, HttpServletRequest req, HttpSession ses) throws Exception
	{
		String LoginType=(String)ses.getAttribute("LoginType");
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSNotices.htm "+UserId);
		try {
			ses.setAttribute("formmoduleid", "7"); 
			ses.setAttribute("SidebarActive", "EMSNotices_htm");
			
			String FromDate=req.getParameter("FromDate");
			String ToDate=req.getParameter("ToDate");

			if(FromDate==null)
			{
				FromDate=LocalDate.now().withDayOfMonth(1).toString();
				ToDate = LocalDate.now().toString();
			}else
			{
				FromDate=sdf.format(rdf.parse(FromDate));
				ToDate=sdf.format(rdf.parse(ToDate));
			}
			
			req.setAttribute("FromDate", FromDate);	
			req.setAttribute("ToDate",ToDate);
			req.setAttribute("NoticeList", service.getEmsNoticeList(FromDate, ToDate));
			req.setAttribute("LoginType",LoginType);
			
			ses.setAttribute("SidebarActive", "EMSNotices_htm");
			return "circular/EMSNoticeList";
			
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSNotices.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "EMSNoticeAdd.htm")
	public String EMSNoticeAdd( HttpServletRequest req, HttpSession ses) throws Exception
	{
		return "circular/EMSNoticeAddEdit";
	}
	
	
	@RequestMapping(value = "EMSNoticeAddSubmit.htm")
	public String EMSNoticeAddSubmit( HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("NoticeFile") MultipartFile NoticeFile) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSNoticeAddSubmit.htm "+UserId);
		try {
			
			String NoticeDate=req.getParameter("NoticeDate");
			String NoticeToDate=req.getParameter("NoticeToDate");
			String description=req.getParameter("description");
			String NoticeNo =req.getParameter("ReferenceNo");
			
			NoticeUploadDto dto =   NoticeUploadDto.builder()
									.NoticeDate(sdf.format(rdf.parse(NoticeDate)))
									.ToDate(sdf.format(rdf.parse(NoticeToDate)))
									.NoticeFile(NoticeFile)
									.CreatedBy(UserId)
									.Description(description)
									.ReferenceNo(NoticeNo)
									.build();
		
			long count = service.EmsNoticeAdd(dto);
			
			if (count != 0) {
				redir.addAttribute("result", "Notice Upload Successsfull");
			} else {
				redir.addAttribute("resultfail", "Notice Upload UnSuccesssful");
			}
			
			return "redirect:/EMSNotices.htm";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSNoticeAddSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "EMSNoticeDownload.htm")
    public void EMSNoticeDownload(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
	{				
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSNoticeDownload.htm "+UserId);
		try {
			
			String NoticeId=req.getParameter("NoticeId");
			EMSNotice form=service.GetEMSNotice(NoticeId);
			
			res.setContentType("Application/octet-stream");	
			File my_file=new File(emsfilespath+form.getNoticePath());
			res.setHeader("Content-disposition","attachment;filename="+form.getFileOriginalName());
			OutputStream out=res.getOutputStream();
			
			FileInputStream in=new FileInputStream(my_file);
			byte[] buffer=new byte[4096];
			int length;
			while((length=in.read(buffer))>0){
				out.write(buffer,0,length);
			}
			in.close();
			out.flush();
			out.close();
		}catch(Exception e) {
			logger.error(new Date() +"Inside EMSNoticeDownload.htm "+UserId,e);
			e.printStackTrace();
		}
    }
	
	
	@RequestMapping(value = "EMSNoticeEdit.htm")
	public String EMSNoticeEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSNoticeEdit.htm "+UserId);
		try {
			String NoticeId = req.getParameter("NoticeId");
			EMSNotice Notice = service.GetEMSNotice(NoticeId);
			
			String FromDate=req.getParameter("FromDate");
			String ToDate=req.getParameter("ToDate");

			req.setAttribute("FromDate",FromDate);	
			req.setAttribute("ToDate",ToDate);
		
			req.setAttribute("EMSNotice", Notice);
			return "circular/EMSNoticeAddEdit";
	        	 
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSNoticeEdit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "EMSNoticeEditSubmit.htm")
	public String EMSNoticeEditSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("NoticeFile") MultipartFile NoticeFile) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSNoticeEditSubmit.htm "+UserId);
		try {
			
			String NoticeId=req.getParameter("NoticeId");
			String NoticeDate=req.getParameter("NoticeDate");
			String NoticeToDate=req.getParameter("NoticeToDate");
			String description=req.getParameter("description");
			String NoticeNo =req.getParameter("ReferenceNo");
			
			NoticeUploadDto dto =   NoticeUploadDto.builder()
									.NoticeDate(sdf.format(rdf.parse(NoticeDate)))
									.ToDate(sdf.format(rdf.parse(NoticeToDate)))
									.NoticeFile(NoticeFile)
									.Description(description)
									.ReferenceNo(NoticeNo)
									.NoticeId(NoticeId)
									.ModifiedBy(UserId)
									.build();
		
			long count=service.EMSNoticeEdit(dto);
	        if (count > 0) {
				 redir.addAttribute("result", "Notice Updated Successfully");
			} else {
				 redir.addAttribute("resultfail", "Notice Update Unsuccessfull");
			}
	        
	        redir.addAttribute("FromDate", rdf.format(sdf.parse(req.getParameter("FromDate"))));	
	        redir.addAttribute("ToDate", rdf.format(sdf.parse(req.getParameter("ToDate"))));
			
	        return "redirect:/EMSNotices.htm";
	        
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSNoticeEditSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
		
	}
	
	@RequestMapping(value = "EMSNoticeDelete.htm")
	public String EMSNoticeDelete( HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSNoticeDelete.htm "+UserId);
		try {
			String NoticeId=req.getParameter("NoticeId");
						
			long count = service.EMSNoticeDelete(NoticeId,UserId);
			
			if (count != 0) {
				redir.addAttribute("result", "Notice Deleted Successsfull");
			} else {
				redir.addAttribute("resultfail", "Notice Delete UnSuccesssful");
			}
			
		    redir.addAttribute("FromDate", rdf.format(sdf.parse(req.getParameter("FromDate"))));	
	        redir.addAttribute("ToDate",rdf.format(sdf.parse(req.getParameter("ToDate"))));
			
			return "redirect:/EMSNotices.htm";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSNoticeDelete.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	
	@RequestMapping(value = "EMSTodaysNotices.htm")
	public String EMSTodaysNotices(Model model, HttpServletRequest req, HttpSession ses) throws Exception
	{
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSTodaysNotices.htm "+UserId);
		try 
		{
			req.setAttribute("NoticeList", service.getEmsTodayNotices());
			return "circular/EMSTodaysNotices";
		} 
		catch (Exception e) {
			  logger.error(new Date() +"Inside EMSTodaysNotices.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	
	
}

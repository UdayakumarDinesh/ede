package com.vts.ems.circularorder.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.circularorder.dto.FormUploadDto;
import com.vts.ems.circularorder.model.EMSForms;
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
			
			
			FormUploadDto dto=new FormUploadDto().builder()
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
			
			return "redirect:/EMSForms.htm";
		} catch (Exception e) {
			  logger.error(new Date() +"Inside EMSFormAddSubmit.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
		}
	}
	
	@RequestMapping(value = "EMSFormDownload.htm",method = {RequestMethod.GET,RequestMethod.POST})
    public void EMSFormDownload(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
	{				
		String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside EMSFormDownload.htm "+UserId);
		try {
			String EMSFormId=req.getParameter("EMSFormId");
			
			EMSForms form=service.GetEMSForm(EMSFormId);
			
			res.setContentType("Application/octet-stream");	
			File my_file=new File(form.getFormPath());
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
	
}

package com.vts.ems.bankdetail.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.bankdetail.service.BankDetailServiceImpl;
import com.vts.ems.master.controller.MasterController;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.utils.DateTimeFormatUtil;



@Controller
public class BankDetailController {
	private static final Logger logger = LogManager.getLogger(BankDetailController.class);

	@Autowired
	private BankDetailServiceImpl service;

	SimpleDateFormat sdtf=DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@RequestMapping(value="BankDetails.htm")
	public ModelAndView bankDetailsHome(HttpServletRequest req, HttpSession ses) throws Exception {
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		
		ModelAndView mv= new ModelAndView();

		List<Object[]> BankDetailList=service.findAll(sesEmpNo);

		req.setAttribute("BankDetailList", BankDetailList);
		mv.setViewName("Bankdetail/BankDetail");
		return mv;
	}


	@RequestMapping(value="BankDetailAddEdit.htm")
	public String bankDetailsAdd(HttpServletRequest req, HttpSession ses) throws Exception {

		String UserId=(String)ses.getAttribute("Username");
		String sesEmpNo = (String) ses.getAttribute("EmpNo");

		logger.info(new Date() +"Inside BankDetailAdd.htm "+UserId);

		List<Object[]> BankDetailList=service.findAll(sesEmpNo);

		try {

			String action = (String)req.getParameter("Action");
			if("Add".equalsIgnoreCase(action)) {

				return "Bankdetail/BankDetailAddEdit";
			}
			else if("Edit".equalsIgnoreCase(action)) {
				String bankId = (String)req.getParameter("bankId");
				BankDertails oneBankDeatil=service.findById(Long.parseLong(bankId));
				req.setAttribute("oneBankDeatil", oneBankDeatil);
				return "Bankdetail/BankDetailAddEdit";
			}else {

				return "redirect:/BankDetails.htm";
			}		   		 
		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAdd.htm "+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping(value="BankDetailAddSave.htm")
	public String bankDetailsAddSave(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {

		String UserName=(String)ses.getAttribute("Username");
		String sesEmpNo = (String) ses.getAttribute("EmpNo");
		String Action = (String)req.getParameter("Action");
		logger.info(new Date() +"Inside BankDetailAddSave.htm "+UserName);
		
		//BankDertails oneBankDeatil = (BankDertails) req.getAttribute("oneBankDeatil");


		try {
			String BankName =(String)req.getParameter("BankName");
			String BranchName  =(String)req.getParameter("BranchName");
			String IFSC =(String)req.getParameter("IFSC");
			String accNo =(String)req.getParameter("accNo");

			BankDertails bankDertails=new BankDertails();
			bankDertails.setEmpNo(sesEmpNo);
			bankDertails.setBankName(BankName);
			bankDertails.setBranch(BranchName);
			bankDertails.setIFSC(IFSC);
			bankDertails.setAccountNo(accNo);
			bankDertails.setIsActive(1);
			bankDertails.setCreatedBy(UserName);
			bankDertails.setCreatedDate(sdtf.format(new Date()));
			long result=service.addBankdetail(bankDertails);
			
			
			if(result>0) {
				redir.addAttribute("result", "Bank Details Added Successfully");	
			} else {
				redir.addAttribute("resultfail", "Bank Details Add Unsuccessful");	
			}

			return "redirect:/BankDetails.htm";

		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAdd.htm "+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}
	
	@RequestMapping(value="BankDetailEditSave.htm")
	public String bankDetailsEditSave(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {

		
		String UserName=(String)ses.getAttribute("Username"); 
		String sesEmpNo =(String) ses.getAttribute("EmpNo");
		 
		String Action = (String)req.getParameter("Action");
		String bankId = (String)req.getParameter("bankId");
		logger.info(new Date() +"Inside BankDetailAddSave.htm "+UserName);


		try {
			String BankName =req.getParameter("BankName");
			String BranchName  =req.getParameter("BranchName");
			String IFSC =req.getParameter("IFSC");
			String accNo =req.getParameter("accNo");

			BankDertails bankDertails=new BankDertails();
			/* bankDertails.setEmpNo(sesEmpNo); */
			bankDertails.setBankName(BankName);
			bankDertails.setBranch(BranchName);
			bankDertails.setIFSC(IFSC);
			bankDertails.setAccountNo(accNo);
			/* bankDertails.setIsActive(1); */
			bankDertails.setModifiedBy(UserName);
			bankDertails.setModifiedDate(sdtf.format(new Date()));
			long result=service.editBankdetail(bankDertails, Long.parseLong(bankId));

			if(result>0) {
				redir.addAttribute("result", "Bank Details Edited Successfully");	
			} else {
				redir.addAttribute("resultfail", "Bank Details Edit Unsuccessful");	
			}

			return "redirect:/BankDetails.htm";

		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAdd.htm "+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}

		
	@RequestMapping(value="BankDetailRemove.htm")
	public String bankDetailsRemove(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {

		
		String UserName=(String)ses.getAttribute("Username"); 
		 
		String bankId = (String)req.getParameter("bankId");
		logger.info(new Date() +"Inside BankDetailAddSave.htm "+UserName);


		try {
			BankDertails bankDertails=new BankDertails();
			
			long result=service.removeById(Long.parseLong(bankId));
			
			
			if(result>0) {
				redir.addAttribute("result", "Bank Details Removed Successfully");	
			} else {
				redir.addAttribute("resultfail", "Bank Details Remove Unsuccessful");	
			}

			return "redirect:/BankDetails.htm";

		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAdd.htm "+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}

}

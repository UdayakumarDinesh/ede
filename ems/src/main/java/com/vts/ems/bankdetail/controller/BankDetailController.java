package com.vts.ems.bankdetail.controller;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.itextpdf.html2pdf.HtmlConverter;
import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.bankdetail.model.BankDetChangeTransa;
import com.vts.ems.bankdetail.service.BankDetailServiceImpl;
import com.vts.ems.master.controller.MasterController;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.utils.CharArrayWriterResponse;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.utils.EmsFileUtils;



@Controller
public class BankDetailController {
	private static final Logger logger = LogManager.getLogger(BankDetailController.class);

	@Autowired
	private BankDetailServiceImpl service;

	@Autowired
	EmsFileUtils emsfileutils ;
	
	
	SimpleDateFormat sdtf=DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	private final String formmoduleid="23";

	@RequestMapping(value="BankDetails.htm")
	public String bankDetailsHome(HttpServletRequest req, HttpSession ses) throws Exception {
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String UserId=(String)ses.getAttribute("Username");
		
		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive", "BankDetails_htm");
		try {
			List<String> DGMs=service.GetEmpDGMEmpNo();
			List<String> PAndAs=service.GetPandAAdminEmpNos();
			

			String DGM = service.GetDGMEmpNo(EmpNo);
			List<Object[]> BankDetailList=service.findAll(EmpNo);
			
			req.setAttribute("EmpNo", EmpNo);
			req.setAttribute("DGMs", DGMs);
			req.setAttribute("PAndAs", PAndAs);
			req.setAttribute("BankDetailList", BankDetailList);
			req.setAttribute("EmpName", service.findEmpByEmpNo(EmpNo).getEmpName());
			req.setAttribute("DGMName", service.findEmpByEmpNo(DGM).getEmpName());
			req.setAttribute("PANDAName", service.findEmpByEmpNo(PAndAs.get(0)).getEmpName());
			
			req.setAttribute("empNameAndDesi", service.getEmpNameAndDesi(EmpNo));
			
			return "Bankdetail/BankDetail";
		}catch (Exception e) {
			logger.error(new Date() +"Inside BankDetails.htm"+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}

	@RequestMapping(value="AllActiveBankDetail.htm")
	public String AllActiveBankDetail(HttpServletRequest req, HttpSession ses) throws Exception {
		String EmpNo = (String) ses.getAttribute("EmpNo");
		String UserId=(String)ses.getAttribute("Username");
		
		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive", "AllActiveBankDetail_htm");
		try {
		
			List<Object[]> allActivBankDetList=service.allActiveBank();
	

			req.setAttribute("empNameAndDesi", service.getEmpNameAndDesi(EmpNo));
			
			req.setAttribute("allActivBankDetList", allActivBankDetList);
	
			return "Bankdetail/AllActiveBankDetail";
		}catch (Exception e) {
			logger.error(new Date() +"Inside AllActiveBankDetail.htm"+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}


	@RequestMapping(value="BankDetailAddEdit.htm")
	public String bankDetailsAdd(HttpServletRequest req, HttpSession ses) throws Exception {

		String UserId=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		logger.info(new Date() +"Inside BankDetailAdd.htm "+UserId);

		try {

			String action = (String)req.getParameter("Action");
			if("Add".equalsIgnoreCase(action)) {

				req.setAttribute("empNameAndDesi", service.getEmpNameAndDesi(EmpNo));
				
				return "Bankdetail/BankDetailAddEdit";
			}
			else if("Edit".equalsIgnoreCase(action)) {
				String bankId = (String)req.getParameter("bankId");
				Object[] oneBankDeatil=service.findById(Long.parseLong(bankId));
				req.setAttribute("empNameAndDesi", service.getEmpNameAndDesi(EmpNo));
				req.setAttribute("oneBankDeatil", oneBankDeatil);
				return "Bankdetail/BankDetailAddEdit";
			}else {

				return "redirect:/BankDetails.htm";
			}		   		 
		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAddEdit.htm "+UserId ,e);
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



		try {
			String BankName =req.getParameter("BankName");
			String BranchName  =req.getParameter("BranchName");
			String IFSC =req.getParameter("IFSC");
			String accNo =req.getParameter("accNo");
			String ValidFrom =req.getParameter("ValidFrom");
			

			BankDertails bankDertails=new BankDertails();
			bankDertails.setEmpNo(sesEmpNo);
			bankDertails.setBankName(BankName);
			bankDertails.setBranch(BranchName);
			bankDertails.setIFSC(IFSC);
			bankDertails.setAccountNo(accNo);
			bankDertails.setIsActive(1);
			bankDertails.setBankStatus("N");
			bankDertails.setBankStatusCode("INI");
			bankDertails.setCreatedBy(UserName);
			bankDertails.setCreatedDate(sdtf.format(new Date()));
			bankDertails.setValidFrom(DateTimeFormatUtil.dateConversionSql(ValidFrom).toString());
			long result=service.addBankdetail(bankDertails);


			if(result>0) {
				redir.addAttribute("result", "Bank Details Added Successfully");	
			} else {
				redir.addAttribute("resultfail", "Bank Details Add Unsuccessful");	
			}

			return "redirect:/BankDetails.htm";

		} catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailAddSave.htm"+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping(value="BankDetailEditSave.htm")
	public String bankDetailsEditSave(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {


		String UserName=(String)ses.getAttribute("Username"); 
		String sesEmpNo =(String) ses.getAttribute("EmpNo");

		String Action = req.getParameter("Action");
		String bankId =req.getParameter("bankId");
		logger.info(new Date() +"Inside BankDetailAddSave.htm "+UserName);


		try {
			String BankName =req.getParameter("BankName");
			String BranchName  =req.getParameter("BranchName");
			String IFSC =req.getParameter("IFSC");
			String accNo =req.getParameter("accNo");
			String ValidFrom =req.getParameter("ValidFrom");

			BankDertails bankDertails=new BankDertails();
			bankDertails.setBankName(BankName);
			bankDertails.setBranch(BranchName);
			bankDertails.setIFSC(IFSC);
			bankDertails.setAccountNo(accNo);
			bankDertails.setValidFrom(DateTimeFormatUtil.dateConversionSql(ValidFrom).toString());
			System.out.println("date---------============="+ValidFrom);
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
			logger.error(new Date() +"Inside BankDetailEditSave.htm "+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}


	@RequestMapping(value="GetBankDetailForm.htm")
	public String getBankDetailForm(HttpServletRequest req, HttpSession ses) throws Exception {
		String sesEmpNo =  (String) ses.getAttribute("EmpNo");
		String UserId=(String)ses.getAttribute("Username");


		try {
			String bankId = req.getParameter("bankId");
			String isApproval =req.getParameter("isApproval");
			String AllActive =req.getParameter("AllActive");
			req.setAttribute("AllActive", AllActive);
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			Object[] oneBankDeatil=service.findById(Long.parseLong(bankId));
			req.setAttribute("oneBankDeatil", oneBankDeatil);
			req.setAttribute("isApproval", isApproval);
			return "Bankdetail/BankDetailForm";
		}catch (Exception e) {
			logger.error(new Date() +"Inside GetBankDetailForm.htm "+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}


	@RequestMapping(value="ForwodApp.htm")
	public String fprwodApp(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
		String UserName=(String)ses.getAttribute("Username");
		String empNo = (String) ses.getAttribute("EmpNo");


		try {
			String bankId =req.getParameter("bankId");
			String remarks =req.getParameter("remarks");

			long result=service.updateForword(Long.parseLong(bankId), empNo,remarks ,UserName);

			if (result > 0) {
				redir.addAttribute("result", "Bank Detail application Sent for verification Successfully");
			} else {
				redir.addAttribute("resultfail", "Bank Detail application Sent for verification Unsuccessful");
			}
			return "redirect:/BankDetails.htm";
		}catch (Exception e) {
			logger.error(new Date() +"Inside ForwodApp.htm "+UserName ,e) ;
			e.printStackTrace();
			return "static/Error";
		}

	}


	@RequestMapping(value="BankDetailapproval.htm")
	public String getBankApprovals(HttpServletRequest req, HttpSession ses) throws Exception {
		String UserId=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		ses.setAttribute("formmoduleid", formmoduleid);
		ses.setAttribute("SidebarActive", "BankDetailapproval_htm");
		try {

			List<String> DGMs=service.GetEmpDGMEmpNo();
			List<String> PAndAs=service.GetPandAAdminEmpNos();
			req.setAttribute("empNameAndDesi", service.getEmpNameAndDesi(EmpNo));

			if(PAndAs.contains(EmpNo)) {
				List<Object[]> PAndAAllBanks=service.findPAndBankList();
				req.setAttribute("DGMAllBanks", PAndAAllBanks);
				return "Bankdetail/getBankAppro";
			}
			else if(DGMs.contains(EmpNo)) {
				List<Object[]> DGMAllBanks=service.findDGMBankList(EmpNo);
				req.setAttribute("DGMAllBanks", DGMAllBanks);
				return "Bankdetail/getBankAppro";
			}

			else {

				return "Bankdetail/getBankAppro";
			}
		}catch (Exception e) {
			logger.error(new Date() +"Inside BankDetailapproval.htm "+UserId ,e);
			e.printStackTrace();
			return "static/Error";
		}

	}


	@RequestMapping(value="BankFormDGMSubmit.htm")
	public String bankFormDGMSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
		String UserName=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			String action = req.getParameter("Action");
			String bankId =req.getParameter("bankId");
			String remarks =req.getParameter("remarks");
			if(action.equalsIgnoreCase("A")) {
				long result=service.DGMAccept(Long.parseLong(bankId), EmpNo, remarks, UserName);
				if (result > 0) {
					redir.addAttribute("result", "Bank Detail verification Successfull");
				} else {
					redir.addAttribute("resultfail", "Bank Detail verification Unsuccessful");
				}	
				return "redirect:/BankDetailapproval.htm";
			}
			else {
				long result=service.DGMReject(Long.parseLong(bankId), EmpNo, remarks, UserName);
				if (result > 0) {
					redir.addAttribute("result", "Bank Detail application Rejected Successfully");
				} else {
					redir.addAttribute("resultfail", "Bank Detail application Rejection Unsuccessful");
				}	
				return "redirect:/BankDetailapproval.htm";
			}
		}catch (Exception e) {
			logger.error(new Date() +"Inside BankFormDGMSubmit.htm "+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}

	@RequestMapping(value="BankFormPAndASubmit.htm")
	public String bankFormPAndASubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
		String UserName=(String)ses.getAttribute("Username");
		String EmpNo = (String) ses.getAttribute("EmpNo");

		try {
			String action = req.getParameter("Action");
			String bankId =req.getParameter("bankId");
			String remarks =req.getParameter("remarks");
			if(action.equalsIgnoreCase("A")) {
				long result=service.PAndAAccept(Long.parseLong(bankId), EmpNo, remarks,UserName);
				if (result > 0) {
					redir.addAttribute("result", "Bank Detail Verification Successfull");
				} else {
					redir.addAttribute("resultfail", "Bank Detail Verification Unsuccessful");
				}	
				return "redirect:/BankDetailapproval.htm";
			}
			else {
				long result=service.PAndAReject(Long.parseLong(bankId), EmpNo, remarks,UserName);
				if (result > 0) {
					redir.addAttribute("result", "Bank Detail application Rejected Successfully");
				} else {
					redir.addAttribute("resultfail", "Bank Detail application Rejection Unsuccessful");
				}	
				return "redirect:/BankDetailapproval.htm";
			}
		}catch (Exception e) {
			logger.error(new Date() +"Inside BankFormPAndASubmit.htm "+UserName ,e);
			e.printStackTrace();
			return "static/Error";
		}
	}
	
	@RequestMapping(value="BankFormDownload.htm")
	public void BankFormDownload(Model model, HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception {
		String UserId = (String) ses.getAttribute("Username");

		
		try {	
			String bankId = req.getParameter("bankId");
			String isApproval = req.getParameter("isApproval");
			
			String filename="";
			if(bankId!=null) {
				Object[] oneBankDeatil=service.findById(Long.parseLong(bankId));
				req.setAttribute("oneBankDeatil", oneBankDeatil);
				req.setAttribute("isApproval", isApproval);	
				filename="Bank_Detail";
			}
			
			req.setAttribute("pagePart","3" );
			
			req.setAttribute("view_mode", req.getParameter("view_mode"));
			req.setAttribute("LabLogo",Base64.getEncoder().encodeToString(FileUtils.readFileToByteArray(new File(req.getServletContext().getRealPath("view\\images\\lablogo.png")))));
			
			String path=req.getServletContext().getRealPath("/view/temp");
			req.setAttribute("path",path);
	        
	        CharArrayWriterResponse customResponse = new CharArrayWriterResponse(res);
	        req.getRequestDispatcher("/view/Bankdetail/BankDetailFormPrint.jsp").forward(req, customResponse);

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
			logger.error(new Date() +" Inside BankFormDownload.htm "+UserId, e); 
		}

	}
	
	@RequestMapping(value = "BankDetTransacStatus.htm" )
	public String PerAddrTransactionStatus(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside BankDetTransacStatus.htm "+Username);
		try {
			String bankId = req.getParameter("bankId");
			req.setAttribute("TransactionList", service.bankTransaById(Long.parseLong(bankId)));
			return "Bankdetail/BankDetTransactionStatus";
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside BankDetTransacStatus.htm "+Username, e);
			return "static/Error";
		}
	}
}

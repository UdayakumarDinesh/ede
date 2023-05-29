package com.vts.ems.bankdetail.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.bankdetail.dao.BankDetailDao;
import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.bankdetail.model.BankDetChangeTransa;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.newspaper.service.NewPaperServiceImpl;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class BankDetailServiceImpl implements BankDetailService{

	private static final Logger logger = LogManager.getLogger(BankDetailServiceImpl.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@Autowired
	private BankDetailDao dao;

	@Override
	public List<Object[]> findAll(String empNo) throws Exception{
		return dao.findAll(empNo);
	}

	@Override
	public long addBankdetail(BankDertails bankDertail) throws Exception{
		return dao.addBankdetail(bankDertail);
	}

	@Override
	public Object[] findById(long bankId) throws Exception{
		return dao.findById(bankId);
	}

	@Override
	public long editBankdetail(BankDertails bankDertail,long bankId) throws Exception{
		return dao.editBankdetail(bankDertail, bankId);
	}

	@Override
	public long updateForword(long bankId,String empNo, String remarks, String username) throws Exception{
		try {
			List<String> DGMs=dao.GetEmpDGMEmpNo();
			List<String> PAndAs=dao.GetPandAAdminEmpNos();
			String DGM=dao.GetDGMEmpNo(empNo);
			BankDertails bankDertails=dao.findBankById(bankId);
			Employee emp=dao.findEmpByEmpNo(bankDertails.getEmpNo());
			bankDertails.setRemarks(remarks);
			

			BankDetChangeTransa bankDetChangeTransa=new BankDetChangeTransa();
			if(DGMs.contains(empNo)) {
				bankDertails.setBankStatus("N");
				bankDertails.setBankStatusCode("VDG");
				bankDetChangeTransa.setBankStatusCode("VDG");
			}
			else if(PAndAs.contains(empNo)) {
				bankDertails.setBankStatus("A");
				bankDertails.setBankStatusCode("VPA");
				bankDetChangeTransa.setBankStatusCode("VPA");
			}
			else {
				bankDertails.setBankStatusCode("FWD");
				bankDetChangeTransa.setBankStatusCode("FWD");
			}
			bankDetChangeTransa.setActionBy(empNo);
			bankDetChangeTransa.setRemarks(remarks);
			bankDetChangeTransa.setActionDate(sdtf.format(new Date()));
			bankDetChangeTransa.setBankDetailId(bankDertails.getBankDetailId());
			
			

			dao.editBankdetail(bankDertails);
			dao.addtBankDetChangeTransa(bankDetChangeTransa);
			
			EMSNotification notification = new EMSNotification();
			notification.setEmpNo(DGM);
			notification.setNotificationUrl("BankDetailapproval.htm");
			notification.setNotificationMessage("Received Bank Detail Change Request From <br>"+emp.getEmpName());
			notification.setNotificationBy(empNo);
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
			dao.addNotification(notification);
			
			return bankDertails.getBankDetailId();
		}catch (Exception e) {
			logger.error(new Date() +" Inside updateForword "+ e);
			e.printStackTrace();
			return 0;
		}	
		/* return dao.updateForword(bankId, empNo,remarks); */
	}

	@Override
	public List<Object[]> findDGMBankList(String empNo) throws Exception{
		return dao.findDGMBankList(empNo);
	}

	@Override
	public BankDertails findBankById(long bankId) throws Exception{
		return dao.findBankById(bankId);
	}

	@Override
	public long DGMAccept(long bankId,String EmpNo, String remarks, String username) throws Exception{
		
		try {
		String PAndA=dao.GetPandAAdminEmpNos().get(0);
		
		
		BankDertails bankDertails = dao.findBankById(bankId);
		bankDertails.setBankStatusCode("VDG");
		bankDertails.setBankStatus("N");
		bankDertails.setRemarks(remarks);
		dao.editBankdetail(bankDertails);
		
		Employee emp=dao.findEmpByEmpNo(bankDertails.getEmpNo());
		
		BankDetChangeTransa bankDetChangeTransa=new BankDetChangeTransa();
		bankDetChangeTransa.setActionBy(EmpNo);
		bankDetChangeTransa.setBankDetailId(bankDertails.getBankDetailId());
		bankDetChangeTransa.setBankStatusCode("VDG");
		bankDetChangeTransa.setActionDate(sdtf.format(new Date()));
		bankDetChangeTransa.setRemarks(remarks);
		dao.addtBankDetChangeTransa(bankDetChangeTransa);
		
		EMSNotification notification = new EMSNotification();
		notification.setEmpNo(PAndA);
		notification.setNotificationUrl("BankDetailapproval.htm");
		notification.setNotificationMessage("Received Bank Detail Change Request From <br>"+emp.getEmpName());
		notification.setNotificationBy(EmpNo);
		notification.setNotificationDate(LocalDate.now().toString());
		notification.setIsActive(1);
		notification.setCreatedBy(username);
		notification.setCreatedDate(sdtf.format(new Date()));
		dao.addNotification(notification);
		
		return bankDertails.getBankDetailId();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DGMAccept "+ e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long DGMReject(long bankId,String EmpNo, String remarks, String username) throws Exception{
		try {
			BankDertails bankDertails = dao.findBankById(bankId);
			bankDertails.setBankStatusCode("RDG");
			bankDertails.setBankStatus("N");
			bankDertails.setRemarks(remarks);
			dao.editBankdetail(bankDertails);
			
			BankDetChangeTransa bankDetChangeTransa=new BankDetChangeTransa();
			bankDetChangeTransa.setActionBy(EmpNo);
			bankDetChangeTransa.setBankDetailId(bankDertails.getBankDetailId());
			bankDetChangeTransa.setBankStatusCode("RDG");
			bankDetChangeTransa.setActionDate(sdtf.format(new Date()));
			bankDetChangeTransa.setRemarks(remarks);
			dao.addtBankDetChangeTransa(bankDetChangeTransa);
			
			EMSNotification notification = new EMSNotification();
			notification.setEmpNo(bankDertails.getEmpNo());
			notification.setNotificationUrl("BankDetails.htm");
			notification.setNotificationMessage("Bank Detail Change Request Returned");
			notification.setNotificationBy(EmpNo);
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
			dao.addNotification(notification);
			
			return bankDertails.getBankDetailId();
			}catch (Exception e) {
				logger.error(new Date() +" Inside DGMReject "+ e);
				e.printStackTrace();
				return 0;
			}
		
	}

	@Override
	public List<String> GetPandAAdminEmpNos() throws Exception{
		return dao.GetPandAAdminEmpNos();
	}

	@Override
	public List<String> GetEmpDGMEmpNo() throws Exception{
		return dao.GetEmpDGMEmpNo();
	}

	@Override
	public List<Object[]> findPAndBankList() throws Exception{
		return dao.findPAndBankList();
	}

	@Override
	public long PAndAAccept(long bankId,String EmpNo, String remarks, String username) throws Exception{
		try {
			BankDertails bankDertails=dao.findBankById(bankId);
			String fromDate = DateTimeFormatUtil.SqlToRegularDate(bankDertails.getValidFrom());
			dao.expireBankDet(bankDertails.getEmpNo(), DateTimeFormatUtil.getMinusOneDay(fromDate));
			bankDertails.setBankStatus("A");
			bankDertails.setBankStatusCode("VPA");
			bankDertails.setRemarks(remarks);
			
			
			dao.editBankdetail(bankDertails);

			BankDetChangeTransa bankDetChangeTransa=new BankDetChangeTransa();
			bankDetChangeTransa.setActionBy(EmpNo);
			bankDetChangeTransa.setBankDetailId(bankDertails.getBankDetailId());
			bankDetChangeTransa.setBankStatusCode("VPA");
			bankDetChangeTransa.setActionDate(sdtf.format(new Date()));
			bankDetChangeTransa.setRemarks(remarks);
			dao.addtBankDetChangeTransa(bankDetChangeTransa);

			EMSNotification notification = new EMSNotification();
			notification.setEmpNo(bankDertails.getEmpNo());
			notification.setNotificationUrl("BankDetails.htm");
			notification.setNotificationMessage("Bank Detail Change Request Approved");
			notification.setNotificationBy(EmpNo);
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
			dao.addNotification(notification);
			
			return bankDertails.getBankDetailId();
		}catch (Exception e) {
			logger.error(new Date() +" Inside PAndAAccept "+ e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long PAndAReject(long bankId,String EmpNo, String remarks, String username) throws Exception{
		try {
			BankDertails bankDertails=dao.findBankById(bankId);
			bankDertails.setBankStatus("N");
			bankDertails.setBankStatusCode("RPA");
			bankDertails.setRemarks(remarks);
			dao.editBankdetail(bankDertails);

			BankDetChangeTransa bankDetChangeTransa=new BankDetChangeTransa();
			bankDetChangeTransa.setActionBy(EmpNo);
			bankDetChangeTransa.setBankDetailId(bankDertails.getBankDetailId());
			bankDetChangeTransa.setBankStatusCode("RPA");
			bankDetChangeTransa.setActionDate(sdtf.format(new Date()));
			bankDetChangeTransa.setRemarks(remarks);
			dao.addtBankDetChangeTransa(bankDetChangeTransa);

			EMSNotification notification = new EMSNotification();
			notification.setEmpNo(bankDertails.getEmpNo());
			notification.setNotificationUrl("BankDetails.htm");
			notification.setNotificationMessage("Bank Detail Change Request Returned");
			notification.setNotificationBy(EmpNo);
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
			dao.addNotification(notification);
			
			return bankDertails.getBankDetailId();
		}catch (Exception e) {
			logger.error(new Date() +" Inside PAndAAccept "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public List<Object[]> bankTransaById(long bankId) throws Exception{
		return dao.bankTransaById(bankId);
	}
	
	@Override
	public String GetDGMEmpNo(String empno) throws Exception{
		return dao.GetDGMEmpNo(empno);
	}
	
	@Override
	public Employee findEmpByEmpNo(String empNo) throws Exception{
		return dao.findEmpByEmpNo(empNo);
	}
	
	@Override
	public List<Object[]> allActiveBank() throws Exception{
		return dao.allActiveBank();
	}
	
	public Object[] getEmpNameAndDesi(String empNo) throws Exception{
		return dao.getEmpNameAndDesi(empNo);
	}
}

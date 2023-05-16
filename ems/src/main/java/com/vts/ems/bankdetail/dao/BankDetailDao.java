package com.vts.ems.bankdetail.dao;

import java.util.Date;
import java.util.List;

import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.bankdetail.model.BankDetChangeTransa;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;

public interface BankDetailDao {
	
	public List<Object[]> findAll(String empNo) throws Exception;
	public long addBankdetail(BankDertails bankDertail) throws Exception;
	public Object[] findById(long bankId) throws Exception;
	public long editBankdetail(BankDertails bankDertail,long bankId) throws Exception;
	public List<Object[]> findDGMBankList(String empNo) throws Exception;
	public BankDertails findBankById(long bankId) throws Exception;
	public List<String> GetPandAAdminEmpNos() throws Exception;
	public List<String> GetEmpDGMEmpNo() throws Exception;
	public List<Object[]> findPAndBankList() throws Exception;
	public long editBankdetail(BankDertails bankDertail) throws Exception;
	public long addtBankDetChangeTransa(BankDetChangeTransa bankDetChangeTransa) throws Exception;
	public long edittBankDetChangeTransa(BankDetChangeTransa bankDetChangeTransa) throws Exception;
	public long expireBankDet(String empNo,Date validToDate) throws Exception;
	public List<Object[]> bankTransaById(long bankId) throws Exception;
	public String GetDGMEmpNo(String empno) throws Exception;
	public long addNotification(EMSNotification notification) throws Exception;
	public Employee findEmpByEmpNo(String empNo) throws Exception;
	public List<Object[]> allActiveBank() throws Exception;
	public Object[] getEmpNameAndDesi(String empNo) throws Exception;
	
}

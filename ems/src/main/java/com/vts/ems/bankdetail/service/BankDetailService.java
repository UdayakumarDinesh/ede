package com.vts.ems.bankdetail.service;

import java.util.List;

import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.bankdetail.model.BankDetChangeTransa;
import com.vts.ems.pis.model.Employee;

public interface BankDetailService {

	public List<Object[]> findAll(String empNo) throws Exception;
	public long addBankdetail(BankDertails bankDertail) throws Exception;
	public Object[] findById(long bankId) throws Exception;
	public long editBankdetail(BankDertails bankDertail,long bankId) throws Exception;
	public long updateForword(long bankId,String empNo, String remarks, String username) throws Exception;
	public List<Object[]> findDGMBankList(String empNo) throws Exception;
	public BankDertails findBankById(long bankId) throws Exception;
	public long DGMAccept(long bankId,String EmpNo, String remarks, String username) throws Exception;
	public long DGMReject(long bankId,String EmpNo, String remarks, String username) throws Exception;
	public List<String> GetPandAAdminEmpNos() throws Exception;
	public List<String> GetEmpDGMEmpNo() throws Exception;
	public List<Object[]> findPAndBankList() throws Exception;
	public long PAndAAccept(long bankId,String EmpNo, String remarks, String username) throws Exception;
	public long PAndAReject(long bankId,String EmpNo, String remarks, String username) throws Exception;
	public List<Object[]> bankTransaById(long bankId) throws Exception;
	public String GetDGMEmpNo(String empno) throws Exception;
	public Employee findEmpByEmpNo(String empNo) throws Exception;
	public List<Object[]> allActiveBank() throws Exception;
	public Object[] getEmpNameAndDesi(String empNo) throws Exception;
	
}

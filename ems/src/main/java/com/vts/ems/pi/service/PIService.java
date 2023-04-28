package com.vts.ems.pi.service;

import java.util.Date;
import java.util.List;

import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;

public interface PIService {
	
	public List<Object[]> ResAddressDetails(String EmpId) throws Exception;
	public Object[] ResAddressFormData(String addressResId)throws Exception;
	public List<Object[]> PermanentAddressDetails(String EmpId)throws Exception;
	public AddressPer getPerAddressData(String addressperid) throws Exception;
	public Object[] ResToAddressId(String EmpId) throws Exception;
	public long ResUpdatetoDate(Date toDate, String resAddressId) throws Exception;
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception;
	public long ResAddressForward(String resAddressId, String username, String action, String remarks, String ApprEmpNo,String LoginType) throws Exception;
	public Object[] PerAddressFormData(String addressPerId) throws Exception;
	public Employee getEmpData(String empid)throws Exception;
	public String GetCEOEmpNo() throws Exception;
	public List<String> GetDGMEmpNos() throws Exception;
	public List<String> GetDHEmpNos() throws Exception;
	public List<String> GetGHEmpNos() throws Exception;
	public String GetEmpGHEmpNo(String empno) throws Exception;
	public String GetEmpDHEmpNo(String empno) throws Exception;
	public String GetEmpDGMEmpNo(String empno) throws Exception;
	public List<Object[]> ResAddressApprovalsList(String EmpNo,String LoginType) throws Exception;
	public DivisionMaster GetDivisionData(long DivisionId) throws Exception;
	public List<Object[]> ResAddressTransactionList(String addressresid) throws Exception;
	public List<Object[]> ResAddressTransactionApprovalData(String addressresid) throws Exception;
	public Object[] GetEmpDGMEmpName(String empno) throws Exception;
	public Object[] GetPandAEmpName() throws Exception;
	public List<Object[]> PerAddressTransactionApprovalData(String peraddressId) throws Exception;
	public AddressPer PerAddressIntimated(String peraddressid) throws Exception;
	public long PerAddressForward(String perAddressId, String username, String action, String remarks, String empNo,String loginType) throws Exception;
	public Object[] PerToAddressId(String empId) throws Exception;
	public long PerUpdatetoDate(Date toDate, String perAddressId) throws Exception;
	public List<Object[]> PerAddressTransactionList(String addressperid) throws Exception;
}

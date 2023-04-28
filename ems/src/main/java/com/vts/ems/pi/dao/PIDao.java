package com.vts.ems.pi.dao;

import java.util.Date;
import java.util.List;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.model.PisAddressPerTrans;
import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;

public interface PIDao {

	public List<Object[]> ResAddressDetails(String EmpId) throws Exception;
	public Object[] ResAddressFormData(String addressResId) throws Exception;
	public List<Object[]> PermanentAddressDetails(String EmpId) throws Exception;
	public AddressPer getPerAddressData(String addressperid) throws Exception;
	public Object[] ResToAddressId(String EmpId) throws Exception;
	public long ResUpdatetoDate(Date toDate, String resAddressId) throws Exception;
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception;
	 AddressRes getResAddressDet(String resaddressid) throws Exception;
	public Object[] AddressIntimationAuth(String Logintype) throws Exception;
	public long NotificationAdd(EMSNotification notify) throws Exception;
	public long AddressResEdit(AddressRes addressRes) throws Exception;
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
	public long AddressResTransactionAdd(PisAddressResTrans transaction) throws Exception;
	public List<Object[]> ResAddressTransactionList(String addressresid) throws Exception;
	public List<Object[]> ResAddressTransactionApprovalData(String addressresid) throws Exception;
	public long AddNotifications(EMSNotification notification) throws Exception;
	public List<String> GetPandAAdminEmpNos() throws Exception;
	public Object[] GetEmpDGMEmpName(String empno) throws Exception;
	public Object[] GetPandAEmpName() throws Exception;
	public List<Object[]> PerAddressTransactionApprovalData(String peraddressId) throws Exception;
	public AddressPer PerAddressIntimated(String peraddressid) throws Exception;
	public Object[] PerToAddressId(String EmpId) throws Exception;
	public long PerUpdatetoDate(Date toDate, String perAddressId) throws Exception;
	public long AddressPerEdit(AddressPer address) throws Exception;
//	public List<Object[]> PerAddressApprovalsList(String EmpNo, String LoginType) throws Exception;
	public long AddressPerTransactionAdd(PisAddressPerTrans transaction) throws Exception;
	public List<Object[]> PerAddressTransactionList(String addressperid) throws Exception;
//	public long ResAddrUpdate(String empid) throws Exception;

}

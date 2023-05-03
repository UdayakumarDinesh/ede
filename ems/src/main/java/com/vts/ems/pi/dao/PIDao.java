package com.vts.ems.pi.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.model.PisAddressPerTrans;
import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pi.model.PisHometown;
import com.vts.ems.pi.model.PisHometownTrans;
import com.vts.ems.pi.model.PisMobileNumber;
import com.vts.ems.pi.model.PisMobileNumberTrans;
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
	public List<Object[]> MobileNumberDetails(String EmpId) throws Exception;
	public Long addMobileNumber(PisMobileNumber mobile) throws Exception;
	public PisMobileNumber getMobileNumberData(String mobileNumberId) throws Exception;
	public PisMobileNumber getMobileDataById(long mobilenumberid) throws Exception;
	public Long EditMobileNumber(PisMobileNumber mobile) throws Exception;
	public List<Object[]> MobileTransactionList(String mobilenumberid) throws Exception;
	public List<Object[]> MobileTransactionApprovalData(String mobileNumberId) throws Exception;
	public Object[] MobileFormData(String mobileNumberId) throws Exception;
	public List<Object[]> MobileApprovalsList(String EmpNo, String LoginType) throws Exception;
	public long MobileNumberTransactionAdd(PisMobileNumberTrans transaction) throws Exception;
	public long MobileStatusUpdate(String EmpId) throws Exception;
	public Object[] GetMobileNumberId(String EmpId) throws Exception;
	public long MobileNumberUpdatetoDate(Date MobileTo, String MobileNumberId) throws Exception;
	public long UpdateEmployeeMobileNumber(String MobileNumber, String AltMobileNumber, String EmpNo) throws Exception;
	public Employee getEmpDataByEmpNo(String empNo) throws Exception;
	public Long addHometown(PisHometown hometown) throws Exception;
	public Long EditHometown(PisHometown hometown) throws Exception;
	public PisHometown getHometownById(long hometownId) throws Exception;
	public List<Object[]> HometownDetails(String EmpNo) throws Exception;
	public Object[] GetGroupHeadName(String EmpNo) throws Exception;
	public Object[] GetDivisionHeadName(String EmpNo) throws Exception;
	public Object[] GetCeoName() throws Exception;
	public Object[] HometownFormData(String hometownId) throws Exception;
	public List<Object[]> HometownTransactionApprovalData(String hometownId) throws Exception;
	public long HometownTransactionAdd(PisHometownTrans transaction) throws Exception;
	public List<Object[]> HometownApprovalsList(String EmpNo, String LoginType) throws Exception;
	public List<Object[]> HometownTransactionList(String hometownid) throws Exception;
	public BigInteger HometownApprovalCount(String EmpNo) throws Exception;
	public long HometownStatusUpdate(String EmpNo) throws Exception;
	public List<Object[]> IntimationApprovalsList(String EmpNo, String LoginType) throws Exception;

}

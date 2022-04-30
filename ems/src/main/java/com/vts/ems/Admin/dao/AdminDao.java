package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;

public interface AdminDao {

	
	public List<Object[]> LoginTypeRoles() throws Exception ;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception ;
	public List<Object[]> FormModuleList(String LoginType) throws Exception ;
	public List<BigInteger> FormRoleActiveList(String formroleaccessid) throws Exception;
	public Long FormRoleActive(String formroleaccessid, Long Value) throws Exception;
	public List<Object[]> OtherItems() throws Exception ;
	public List<Object[]> ChssTestMain() throws Exception;
	public List<Object[]> ChssTestSub() throws Exception;
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception;
	public CHSSTestSub testSub(String TestSubId)throws Exception;
	public CHSSTestSub getTestSub(long subid) throws Exception;
	public long EditTestSub(CHSSTestSub test) throws Exception;
	public CHSSOtherItems getOtherItem(int itemid) throws Exception;
	public int AddOtherItem(CHSSOtherItems item)throws Exception;
	public int EditItem(CHSSOtherItems item) throws Exception;
	public Object[] getChssAprovalList() throws Exception;
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id ,String userid)throws Exception;
	public long AddApprovalAuthority(CHSSApproveAuthority approva)throws Exception;
	public List<Object[]>  getMedicineList()throws Exception;
	public List<Object[]>  getMedicineListByTreatment(String treatmentname)throws Exception;
	public List<Object[]> GetTreatmentType()throws Exception;
	public int Checkduplicate(String medicinename,String treatid)throws Exception;
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception ;
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception;
	public Long EditMedicine(CHSSMedicineList item) throws Exception;
	public List<Object[]> GetRequestMessageList(String empid)throws Exception;
	public int DeleteRequestMsg(String requestid ,String id)throws Exception;
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception;
	public long AddRequestMsgNotification(EMSNotification notification)throws Exception;
	public List<Object[]> CHSSApprovalAuth2(String Logintype) throws Exception;
	public List<Object[]> GethandlingOverList(LocalDate FromDate, LocalDate Todate)throws Exception;
	public List<Object[]> GetDoctorList()throws Exception;
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception ;
	public int EditDoctorMaster(CHSSDoctorRates Docrate) throws Exception;
	public Object[] getLabDetails()throws Exception;
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception;
	public List<Object[]> getLabsList()throws Exception;
	public long EditLabMaster(LabMaster labmatster) throws Exception;
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception;
	public int AddHandingOver(LeaveHandingOver handinfover)throws Exception;
	public int updateRevokeInHandingOver(long empid , String HandingOverId)throws Exception;
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception;
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception;
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception;
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId,String basicto)throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
}

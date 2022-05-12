package com.vts.ems.Admin.Service;

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
import com.vts.ems.pis.model.EmployeeDesig;

public interface AdminService {

	
	
	public List<Object[]> LoginTypeRoles() throws Exception;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception ;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String Logintype,String FormModuleId) throws Exception;
	public List<Object[]> FormModuleList(String LoginType)throws Exception ;
	public Long FormRoleActive(String formroleaccessid) throws Exception;
	public List<Object[]> OtherItems() throws Exception;
	public List<Object[]> ChssTestMain() throws Exception;
	public List<Object[]> ChssTestSub() throws Exception;
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception;
	public CHSSTestSub testSub(String subid)throws Exception;
	public Long EditTestSub(CHSSTestSub TestSub)throws Exception;
	public CHSSOtherItems getOtherItem(int itemid) throws Exception;
	public int AddOtherItem(CHSSOtherItems item)throws Exception;
	public int EditItem(CHSSOtherItems item)throws Exception;
	public Object[] getChssAprovalList()throws Exception;
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id,String userid)throws Exception;
	public long AddApprovalAuthority(CHSSApproveAuthority approve)throws Exception;
	public List<Object[]> getMedicineList()throws Exception;
	public List<Object[]> getMedicineListByTreatment(String treatmentname)throws Exception;
	public List<Object[]> GetTreatmentType()throws Exception;
	public int Checkduplicate(String medicinename,String treatid)throws Exception;
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception ;
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception;
	public Long EditMedicine(CHSSMedicineList medicine)throws Exception;
	public List<Object[]> GetRequestMessageList(String empid)throws Exception;
	public int DeleteRequestMsg(String requestid ,String id)throws Exception;
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception;
	public long EmpRequestNotification(EMSNotification notification)throws Exception;
	public List<Object[]> GethandlingOverList(String fromdate , String todate)throws Exception;
	public List<Object[]> GetDoctorList()throws Exception;
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception;
	public int EditDoctorMaster(CHSSDoctorRates docrate) throws Exception;
	public Object[] getLabDetails()throws Exception;
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception;
	public List<Object[]> getLabsList()throws Exception;
	public long EditLabMaster(LabMaster labmater)throws Exception;
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception;
	public int AddHandingOver(LeaveHandingOver addhanding)throws Exception;
	public int updateRevokeInHandingOver(long empid , String HandingOverId)throws Exception;
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception;
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception;
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception;
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId,String basicto)throws Exception;
	public List<Object[]> GetReqListFromUser()throws Exception;
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception;
	public List<Object[]> GetReqResMessagelist(String emp , String fromdate , String todate )throws Exception;
	public List<Object[]> AllNotificationLists(long  emp  )throws Exception;
	public int CheckduplicateItem(String treatmentname)throws Exception;
	public int CheckduplicateTest(String testname)throws Exception;
	public long DeleteOtherAmt(String chssOtheramtid, String userid)throws Exception;
	public List<Object[]> GetDesignation()throws Exception;
	public long AddDesignation(EmployeeDesig desig)throws Exception;
	public EmployeeDesig GetDesignationToEdit(long desigid)throws Exception;
	public long EditDesignation(EmployeeDesig desig)throws Exception;
	public Object[] DesignationAddCheck(String desigcode,String designation) throws Exception;
	public Object[] DesignationEditCheck(String desigcode,String designation,String desigid) throws Exception;
	public List<Object[]> GetFromemployee()throws Exception;
	public List<Object[]> GetToemployee()throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

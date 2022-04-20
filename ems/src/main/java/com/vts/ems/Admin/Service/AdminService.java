package com.vts.ems.Admin.Service;

import java.util.List;

import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestSub;

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
	public int Checkduplicate(String medicinename)throws Exception;
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception ;
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception;
	public Long EditMedicine(CHSSMedicineList medicine)throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

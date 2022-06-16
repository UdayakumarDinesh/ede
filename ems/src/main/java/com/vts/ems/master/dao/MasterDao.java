package com.vts.ems.master.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.master.model.CHSSEmpanelledHospital;
import com.vts.ems.master.model.CircularList;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.pis.model.EmployeeDesig;

public interface MasterDao {

	
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
	public List<Object[]>  getMedicineList()throws Exception;
	public List<Object[]>  getMedicineListByTreatment(String treatmentname)throws Exception;
	public List<Object[]> GetTreatmentType()throws Exception;
	public int Checkduplicate(String medicinename,String treatid)throws Exception;
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception ;
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception;
	public Long EditMedicine(CHSSMedicineList item) throws Exception;
	public List<Object[]> GetDoctorList()throws Exception;
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception ;
	public int EditDoctorMaster(CHSSDoctorRates Docrate) throws Exception;
	public Object[] getLabDetails()throws Exception;
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception;
	public List<Object[]> getLabsList()throws Exception;
	public long EditLabMaster(LabMaster labmatster) throws Exception;
	public List<Object[]> GetOtherItemAmlountList(String id)throws Exception;
	public long AddOtherItemAmt(CHSSOtherPermitAmt otheramt)throws Exception;
	public long updateOtherAmt(String chssOtheramtid, String admAmt, String UserId)throws Exception;
	public long updateOtherItemAmt(String chssOtheramtid, String admAmt, String UserId,String basicto)throws Exception;
	public int CheckduplicateItem(String treatmentname)throws Exception;
	public int CheckduplicateTest(String testname)throws Exception;
	public long DeleteOtherAmt(String chssOtheramtid, String userid)throws Exception;
	public List<Object[]> GetDesignation()throws Exception;
	public long AddDesignation(EmployeeDesig desig)throws Exception;
	public EmployeeDesig GetDesignationToEdit(long desigid)throws Exception;
	public long EditDesignation(EmployeeDesig desig) throws Exception;
	public Object[] DesignationCodeCheck(String desigcode)throws Exception;
	public Object[] DesignationCheck(String designation)throws Exception;
	public Object[] DesignationCodeEditCheck(String desigcode,String desigid )throws Exception;
	public Object[] DesignationEditCheck(String designation,String desigid)throws Exception;
	public int GetMaxMedNo(String treatmenttype)throws Exception;
	public int CheckduplicateTestCode(String testcode)throws Exception;
    public DoctorList GetDoctor(Long  doctorid)throws Exception;
	public long DoctorsAdd(DoctorList doctor)throws Exception;
	public long DoctorsEdit(DoctorList doctor)throws Exception;
	public long CircularListAdd(CircularList circular)throws Exception;
	public CircularList GetCircularToEdit(Long  circularid)throws Exception;
	public long EditCircular(CircularList circular) throws Exception;
	public long GetCircularMaxId()throws Exception;
	public List<Object[]> GetCircularList(LocalDate fromdate , LocalDate todate) throws Exception;
	public long EmpanelledHospitalAdd(CHSSEmpanelledHospital hospital)throws Exception;
	public CHSSEmpanelledHospital GetEmpanelled(Long  empanelledid)throws Exception;
	public long EmpanelledHospitalEdit(CHSSEmpanelledHospital hospital)throws Exception;
	public Long AddMasterEditComments(MasterEdit masteredit)throws Exception;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

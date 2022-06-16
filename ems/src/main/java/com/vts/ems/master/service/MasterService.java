package com.vts.ems.master.service;

import java.util.List;

import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicineList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.master.dto.CircularListDto;
import com.vts.ems.master.model.CHSSEmpanelledHospital;
import com.vts.ems.master.model.CircularList;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.pis.model.EmployeeDesig;

public interface MasterService {

	
	
	public List<Object[]> OtherItems() throws Exception;
	public List<Object[]> ChssTestMain() throws Exception;
	public List<Object[]> ChssTestSub() throws Exception;
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception;
	public CHSSTestSub testSub(String subid)throws Exception;
	public Long EditTestSub(CHSSTestSub TestSub)throws Exception;
	public CHSSOtherItems getOtherItem(int itemid) throws Exception;
	public int AddOtherItem(CHSSOtherItems item)throws Exception;
	public int EditItem(CHSSOtherItems item)throws Exception;
	public List<Object[]> getMedicineList()throws Exception;
	public List<Object[]> getMedicineListByTreatment(String treatmentname)throws Exception;
	public List<Object[]> GetTreatmentType()throws Exception;
	public int Checkduplicate(String medicinename,String treatid)throws Exception;
	public CHSSMedicineList getCHSSMedicine(long medicineid) throws Exception ;
	public Long AddMedicine(CHSSMedicineList medicine)throws Exception;
	public Long EditMedicine(CHSSMedicineList medicine)throws Exception;
	public List<Object[]> GetDoctorList()throws Exception;
	public CHSSDoctorRates getCHSSDocRate(long docrateid) throws Exception;
	public int EditDoctorMaster(CHSSDoctorRates docrate) throws Exception;
	public Object[] getLabDetails()throws Exception;
	public LabMaster GetLabDetailsToEdit(long labid)throws Exception;
	public List<Object[]> getLabsList()throws Exception;
	public long EditLabMaster(LabMaster labmater)throws Exception;
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
	public long EditDesignation(EmployeeDesig desig)throws Exception;
	public Object[] DesignationAddCheck(String desigcode,String designation) throws Exception;
	public Object[] DesignationEditCheck(String desigcode,String designation,String desigid) throws Exception;
	public DoctorList GetDoctor(long doctorId)throws Exception;
	public long DoctorsAdd(DoctorList doctor)throws Exception;
    public long DoctorsEdit(DoctorList doctor)throws Exception;
	public int GetMaxMedNo(String treatmenttype)throws Exception;
	public int CheckduplicateTestCode(String testcode)throws Exception;
	public long CircularListAdd(CircularList circular , CircularListDto filecircular)throws Exception;
	public CircularList GetCircularToEdit(Long circularid)throws Exception;
	public long CircularListEdit(CircularList circular ,CircularListDto filecirculardto)throws Exception;
	public List<Object[]> GetCircularList(String fromdate , String todate)throws Exception;
	public long EmpanelledHospitalAdd(CHSSEmpanelledHospital hospital)throws Exception;
	public long EmpanelledHospitalEdit(CHSSEmpanelledHospital hospital)throws Exception;
	public CHSSEmpanelledHospital GetEmpanelled(Long  empanelledid)throws Exception;
	public Long AddMasterEditComments(MasterEdit masteredit)throws Exception;


















}
package com.vts.ems.master.service;

import java.math.BigInteger;
import java.util.List;

import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.CHSSDoctorRates;
import com.vts.ems.master.model.CHSSEmpanelledHospital;
import com.vts.ems.master.model.DivisionGroup;
import com.vts.ems.master.model.DoctorList;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.pis.model.DivisionMaster;
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
	public CHSSMedicinesList getCHSSMedicine(long medicineid) throws Exception ;
	public CHSSDoctorRates getCHSSDoctorRates(int DocRateId) throws Exception;
	public Long AddMedicine(CHSSMedicinesList medicine)throws Exception;
	public int AddDocQualification(CHSSDoctorRates  DocRate)throws Exception;
	public Long EditMedicine(CHSSMedicinesList medicine)throws Exception;
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
	public int DuplicateDocQualification(String treatment,String qualification)throws Exception;
	public long EmpanelledHospitalAdd(CHSSEmpanelledHospital hospital)throws Exception;
	public long EmpanelledHospitalEdit(CHSSEmpanelledHospital hospital)throws Exception;
	public CHSSEmpanelledHospital GetEmpanelled(Long  empanelledid)throws Exception;
	public Long AddMasterEditComments(MasterEdit masteredit ,MasterEditDto masterdto)throws Exception;
	public Long EditDelete(CHSSMedicinesList medicine) throws Exception;
	public List<Object[]> GetDoctorEmpanelledList() throws Exception;
	public List<Object[]> GetEmpanelledHostpitalList() throws Exception;
	public List<Object[]> getDepartmentslist() throws Exception;
	public List<Object[]> getEmpList() throws Exception;
	public int DepartmentAdd(DivisionMaster dep)throws Exception;
	public Object[] departmentEdit(String deptId) throws Exception;
	public int UpdateDepartment(DivisionMaster dep)throws Exception;
	public BigInteger DepartmentAddcheck(String depCode)throws Exception;
	public Long AddDeptEditComments(MasterEdit masteredit, MasterEditDto masterdto)throws Exception;
	public BigInteger DepartmentEditcheck(String depCode, String deptId)throws Exception;
	public int addDivisionGroup(DivisionGroup divisionGroup) throws Exception;
	public int editDivisionGroup(DivisionGroup divisionGroup) throws Exception;
	public DivisionGroup getDivisionGroupById(int id) throws Exception;
	public List<Object[]> getDivisionGroupList() throws Exception;
	public BigInteger checkAddDuplicate(String groupCode) throws Exception;
	public BigInteger getDuplicateCountEdit(String groupId,String groupCode) throws Exception;
	public List<Object[]> getEmployeeList() throws Exception;
	public List<Object[]> getGroupList() throws Exception;
	public List<Object[]> getQualificationList() throws Exception;
	public List<Object[]> getDGMList()throws Exception;
















}

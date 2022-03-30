package com.vts.ems.pis.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.login.Login;
import com.vts.ems.pis.dto.UserManageAdd;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisPayLevel;

public interface PisService {

	public List<Object[]> EmployeeDetailsList(String LoginType, String Empid) throws Exception;
	public Object[] EmployeeDetails(String empid) throws Exception;
	public List<DivisionMaster> DivisionList() throws Exception;
	public List<PisPayLevel> PayLevelList() throws Exception;
	public List<EmployeeDesig> DesigList() throws Exception;
	public List<PisCategory> PisCategoryList() throws Exception;
	public List<PisCatClass> PisCatClassList() throws Exception;
	public List<PisCadre> PisCaderList() throws Exception;
	public List<EmpStatus> EmpStatusList() throws Exception;
	public long EmployeeAddSubmit(Employee emp) throws Exception;
	public long EmployeeEditSubmit(Employee emp) throws Exception;
	public Employee getEmployee(String empid) throws Exception;
	public int PunchcardList(String value)throws Exception;
	public String PhotoPath(String EmpId) throws Exception ;
	public int PhotoPathUpdate(String Path, String EmpId) throws Exception;
	public int saveEmpImage(MultipartFile file ,String empid,String uploadpath)throws Exception;
	public String getimage(String empid)throws Exception;
	public List<Object[]> LoginMasterList(String LoginType,String Empid) throws Exception;
	public List<Object[]> getEmpList()throws Exception;
	public List<Object[]> getLoginTypeList()throws Exception;
	public int UserManagerDelete(String username,String loginid)throws Exception;
	public int UserNamePresentCount(String username)throws Exception;
	public Long UserManagerAdd(UserManageAdd useradd)throws Exception;
	public Login getLoginEditData(String LoginId)throws Exception;
	public int UserMangerEdit(String empid , String logintype , String username,String loginid)throws Exception;
	public List<Object[]> getFamilyMembersList(String empid)throws Exception;
	public Object[] GetEmpData(String empid)throws Exception;
	public List<Object[]> getFamilyRelation()throws Exception;
	public List<Object[]> getFamilyStatus()throws Exception;
	public Long AddFamilyDetails(EmpFamilyDetails Details)throws Exception;
	public int DeleteMeber(String familyid,String Username)throws Exception;
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception;
	public Long EditFamilyDetails(EmpFamilyDetails Details)throws Exception;
	public Object[]  getPerAddress(String empid)throws Exception;
	public List<Object[]> getStates ()throws Exception;
	public Long AddPerAddress(AddressPer peraddress)throws Exception;
	public AddressPer getPerAddressData(String empid)throws Exception;
	public Long EditPerAddress(AddressPer address)throws Exception;
	public List<Object[]> getResAddress(String empid)throws Exception;
	public Object[]  getKinAddress(String empid)throws Exception;
	public Object[]  getEmeAddress(String empid)throws Exception;
}

package com.vts.ems.pis.dao;

import java.util.List;

import com.vts.ems.login.Login;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpFamilyDetails;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisPayLevel;

public interface PisDao {

	public List<Object[]> EmployeeDetailsList(String LoginType, String Empid) throws Exception;
	public Object[] EmployeeDetails(String empid) throws Exception;
	public List<DivisionMaster> DivisionList() throws Exception;
	public List<EmployeeDesig> DesigList() throws Exception;
	public List<PisPayLevel> PayLevelList() throws Exception;
	public List<PisCadre> PisCaderList() throws Exception;
	public List<PisCatClass> PisCatClassList() throws Exception;
	public List<PisCategory> PisCategoryList() throws Exception;
	public List<EmpStatus> EmpStatusList() throws Exception;
	public long EmployeeAddSubmit(Employee emp) throws Exception;
	public long EmployeeEditSubmit(Employee emp) throws Exception;
	public Employee getEmployee(String empid) throws Exception;
	public int PunchcardList(String value)throws Exception;
	public long getempno()throws Exception;
	public String PhotoPath(String empid)throws Exception;
	public int PhotoPathUpdate(String Path, String EmpId) throws Exception;
	public List<Object[]> LoginMasterList(String LoginType,String Empid) throws Exception;
	public List<Object[]> getEmpList()throws Exception;
	public List<Object[]> getLoginTypeList()throws Exception;
	public int UserManagerDelete(String username , String loginid)throws Exception;
	public int UserNamePresentCount(String UserName)throws Exception;
	public Long UserManagerAdd(Login login) throws Exception;
	public Login getLoginEditData(Long LoginId)throws Exception;
	public int UserManagerEdit(Login login)throws Exception;
	public List<Object[]> getFamilyMembersList(String empid)throws Exception;
	public Object[] GetEmpData(String empid)throws Exception;
	public List<Object[]> getFamilyRelation()throws Exception;
	public List<Object[]> getFamilyStatus()throws Exception;
	public Long AddFamilyDetails(EmpFamilyDetails Details) throws Exception;
	public int DeleteMeber(String familyid,String Username)throws Exception;
	public EmpFamilyDetails	getMemberDetails(String familyid)throws Exception;
}

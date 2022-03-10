package com.vts.ems.pis.service;

import java.util.List;

import com.vts.ems.pis.model.DivisionMaster;
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
	public Employee getEmployee(String empid) throws Exception;

}

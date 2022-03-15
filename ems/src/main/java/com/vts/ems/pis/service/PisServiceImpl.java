package com.vts.ems.pis.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.DateTimeFormatUtil;
import com.vts.ems.pis.dao.PisDao;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.EmpStatus;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;
import com.vts.ems.pis.model.PisCadre;
import com.vts.ems.pis.model.PisCatClass;
import com.vts.ems.pis.model.PisCategory;
import com.vts.ems.pis.model.PisPayLevel;

@Service
public class PisServiceImpl implements PisService 
{
	private static final Logger logger = LogManager.getLogger(PisServiceImpl.class);

	DateTimeFormatUtil util= new DateTimeFormatUtil();
	SimpleDateFormat sdtf= util.getSqlDateAndTimeFormat();
	@Autowired
	private PisDao dao;
	
	@Override
	public List<Object[]> EmployeeDetailsList(String LoginType,String Empid) throws Exception
	{
		return dao.EmployeeDetailsList(LoginType, Empid);
	}
	
	@Override
	public Object[] EmployeeDetails(String empid) throws Exception
	{
		return dao.EmployeeDetails(empid);
	}
	
	@Override
	public List<DivisionMaster> DivisionList() throws Exception
	{
		return dao.DivisionList();
	}
	
	@Override
	public List<EmployeeDesig> DesigList() throws Exception
	{
		return dao.DesigList();
	}

	@Override
	public List<PisPayLevel> PayLevelList() throws Exception
	{
		return dao.PayLevelList();
	}
	
	@Override
	public List<PisCategory> PisCategoryList() throws Exception
	{
		return dao.PisCategoryList();
	}
	
	@Override
	public List<PisCatClass> PisCatClassList() throws Exception
	{
		return dao.PisCatClassList();
	}
	
	@Override
	public List<PisCadre> PisCaderList() throws Exception
	{
		return dao.PisCaderList();
	}
	
	@Override
	public List<EmpStatus> EmpStatusList() throws Exception
	{
		return dao.EmpStatusList();
	}
	
	@Override
	public long EmployeeAddSubmit(Employee emp) throws Exception
	{
		emp.setCreatedDate(sdtf.format(new Date()));
		long empno = dao.getempno();
		long intemp = empno + 1;
		String empid2 = String.valueOf(intemp);
		String empid = StringUtils.leftPad(empid2, 7, "0");
		emp.setEmpNo(empid);
		return dao.EmployeeAddSubmit(emp);
	}
	
	@Override
	public long EmployeeEditSubmit(Employee emp) throws Exception
	{
		System.out.println(emp.getEmpId());
		Employee employee = dao.getEmployee(String.valueOf(emp.getEmpId()));
	
		employee.setEmpName(emp.getEmpName());
		employee.setDesignationId(emp.getDesignationId());
		employee.setTitle(emp.getTitle());
		employee.setDOB(emp.getDOB());
		employee.setDOA(emp.getDOA());
		employee.setDOJL(emp.getDOJL());
		employee.setCategoryId(emp.getCategoryId());
		//employee.setGroupId(0);
		employee.setDivisionId(emp.getDivisionId());
		employee.setCadreId(emp.getCadreId());
		employee.setCatId(emp.getCatId());
		employee.setGender(emp.getGender());
		employee.setBloodGroup(emp.getBloodGroup());
		employee.setMaritalStatus(emp.getMaritalStatus());
		employee.setReligion(emp.getReligion());
		employee.setEmpStatus(emp.getEmpStatus());
		employee.setGPFNo(emp.getGPFNo());
		employee.setPAN(emp.getPAN());
		employee.setPINNo(emp.getPINNo());
		employee.setPunchCard(emp.getPunchCard());		
		employee.setUID(emp.getUID());
		employee.setQuarters(emp.getQuarters());
		employee.setPH(emp.getPH());
		employee.setEmail(emp.getEmail());
		employee.setHomeTown(emp.getHomeTown());
		employee.setServiceStatus(emp.getServiceStatus());
		employee.setPayLevelId(emp.getPayLevelId());
		employee.setSBIAccNo(emp.getSBIAccNo());
		employee.setIdMark(emp.getIdMark());
		employee.setHeight(emp.getHeight());
		employee.setModifiedBy(emp.getModifiedBy());		
		employee.setInternalNumber(emp.getInternalNumber());
		employee.setSubCategary(emp.getSubCategary());
		employee.setEmpStatusDate(emp.getEmpStatusDate());	
		
		
		return dao.EmployeeEditSubmit(employee);
	}
	
	
	@Override
	public Employee getEmployee(String empid) throws Exception
	{
		return dao.getEmployee(empid);
	}
	@Override
	public int PunchcardList(String puchcard)throws Exception{
		return dao.PunchcardList(puchcard);
	}
	
}

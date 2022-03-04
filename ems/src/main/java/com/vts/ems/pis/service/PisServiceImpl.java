package com.vts.ems.pis.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return dao.EmployeeAddSubmit(emp);
	}
}

package com.vts.ems.athithi.dao;

import java.util.List;

import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.IntimationEmp;

public interface IntimationDao {

	public List<Object[]> getCompnyList(String trem) throws Exception;
	public List<Object[]> addNewCompany(Company company)throws Exception;
	public List<Object[]> getCompEmp(String companyId)throws Exception;
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception;
	public List<Object[]> getOfficerList(String groupId)throws Exception;
	public Long addNewIntimation(Intimation inti)throws Exception;
	public void addVisitor(IntimationEmp newVisitor)throws Exception;
	public int todayIntionCount()throws Exception;
	public List<Object[]> getItimationList(String groupId)throws Exception;
}

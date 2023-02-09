package com.vts.ems.athithi.service;

import java.util.List;

import com.vts.ems.athithi.dto.NewIntimation;
import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;

public interface IntimationService {

	public List<Object[]> getCompnyList(String string) throws Exception;
	public List<Object[]> addNewCompany(Company company)throws Exception;
    public List<Object[]> getCompEmp(String companyId)throws Exception;
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception;
	public List<Object[]> getOfficerList(String groupId)throws Exception;
	public Long addNewIntimation(NewIntimation intimation)throws Exception;
	public List<Object[]> getItimationList(String groupId)throws Exception;

	
	

}

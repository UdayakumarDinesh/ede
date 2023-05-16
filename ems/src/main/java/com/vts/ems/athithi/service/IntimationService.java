package com.vts.ems.athithi.service;

import java.util.List;

import com.vts.ems.athithi.dto.NewIntimation;
import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.VpIntimationTrans;

public interface IntimationService {

	public List<Object[]> getCompnyList(String string) throws Exception;
	public List<Object[]> addNewCompany(Company company)throws Exception;
    public List<Object[]> getCompEmp(String companyId)throws Exception;
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception;
	public List<Object[]> getOfficerList(String groupId)throws Exception;
	public Long addNewIntimation(NewIntimation intimation)throws Exception;
	public List<Object[]> getItimationList(String EmpNo)throws Exception;
	public Long addVpIntimationTrans(VpIntimationTrans transaction) throws Exception;
	public Intimation getIntimationById(Long intimationId) throws Exception;
	public Long vpIntimationForward(String intimationId, String username, String action, String remarks, String empNo,String loginType);
	public List<Object[]> visitorPassApprovalList(String EmpNo) throws Exception;
	public List<Object[]> vpTransactionList(String IntimationId) throws Exception;
	
}

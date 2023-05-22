package com.vts.ems.athithi.dao;

import java.util.List;

import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.IntimationEmp;
import com.vts.ems.athithi.model.VpIntimationTrans;

public interface IntimationDao {

	public List<Object[]> getCompnyList(String trem) throws Exception;
	public List<Object[]> addNewCompany(Company company)throws Exception;
	public List<Object[]> getCompEmp(String companyId)throws Exception;
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception;
	public List<Object[]> getOfficerList(String groupId)throws Exception;
	public Long addNewIntimation(Intimation inti)throws Exception;
	public void addVisitor(IntimationEmp newVisitor)throws Exception;
	public int todayIntionCount()throws Exception;
	public List<Object[]> getItimationList(String EmpNo)throws Exception;
	public Long addVpIntimationTrans(VpIntimationTrans transaction) throws Exception;
	public Intimation getIntimationById(Long intimationId) throws Exception;
	public Long editNewIntimation(Intimation intimation) throws Exception;
	public String GetReceptionistEmpNo() throws Exception;
	public List<Object[]> visitorPassPendingList(String EmpNo) throws Exception;
	public List<Object[]> vpTransactionList(String IntimationId) throws Exception;
	public List<Object[]> visitorPassApprovedList(String EmpNo, String FromDate, String ToDate) throws Exception;
	public List<Object[]> visitorPassTransactionApprovalData(String IntimationId) throws Exception;
	public List<Object[]> visitorPassRemarksHistory(String IntimationId) throws Exception;
	public Object[] visitorPassFormData(String IntimationId) throws Exception;
	public List<Object[]> visitorPassVisitorsForm(String IntimationId) throws Exception;
}

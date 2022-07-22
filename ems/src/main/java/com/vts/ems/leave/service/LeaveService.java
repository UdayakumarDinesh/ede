package com.vts.ems.leave.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.leave.dto.LeaveApplyDto;
import com.vts.ems.leave.model.LeaveRaSa;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.pis.model.Employee;

public interface LeaveService {

	public List<Object[]> PisHolidayList(String year) throws Exception;	
	public List<Object[]> LeaveCreditList(String month,String year) throws Exception;	
	public List<Employee> EmpList() throws Exception;	
	public List<Object[]> CreditList(String month) throws Exception;	
	public List<Object[]> LeaveCreditPreview(String month,String year,String emmNo) throws Exception;
	public long LeaveCredited(String month,String year,String emmNo) throws Exception;
	public List<Object[]> LeaveCreditInd(String month, String year,String emmNo) throws Exception;
	public List<Object[]> LeaveCreditById(String registerId)throws Exception;
	public long LeaveCreditedAddUpdate(LeaveRegister register,String type) throws Exception;
	public List<Object[]>   GetHolidays(String Type)throws Exception;
	public List<Object[]>   EmpDetails(String EmpNo)throws Exception;
	public List<Object[]>   OfficerDetails(String EmpNo)throws Exception;
	public List<Object[]>   EmployeeList()throws Exception;
	public List<Object[]> LeaveCode(String EmpNo) throws Exception ;
	public List<Object[]>   purposeList()throws Exception;
	public String[] LeaveCheck(LeaveApplyDto dto)throws Exception;
	public LeaveRegister getRegister(String EmpNo,String yr) throws Exception ;
	public String[] applyLeaveAdd(LeaveApplyDto dto)throws Exception;
	public List<Object[]> getAppliedLeave(String EmpNo)throws Exception;
	public List<LeaveRegister>  LeaveRegisterList(String EmpNo,String yr)throws Exception;
	public LeaveRegister RegisterOpening(String EmpNo, String yr) throws Exception;
	public  Object[] getEmployee(String empno) throws Exception;
	public List<Object[]> LeaveApprovalGh(String empNo)throws Exception;
	public List<Object[]> LeaveTransaction(String applid) throws Exception;
	public Object[] LeavePrint(String applid) throws Exception;
	public Object[] getLabCode() throws Exception;
	public int getApproved(ApprovalDto dto,HttpServletRequest req)throws Exception;
	public List<Object[]> getSanctionedLeave(String EmpNo)throws Exception;
	public int deleteLeave(ApprovalDto dto)throws Exception;
	public Object[] getLeaveData(String applid) throws Exception;
	public int getUpdateAppl(ApprovalDto dto)throws Exception;
	public int getUpdateRegister(ApprovalDto dto)throws Exception;	
	public int getCancelLeave(ApprovalDto dto)throws Exception;
	public int laeveNotModified(String empno) throws Exception; 
	public int deleteLeaveRegiHo(String applid) throws Exception;
	public List<Object[]> LeaveStatusList(String empNo)throws Exception;
	public List<Object[]> LeaveApprovalDir(String empNo)throws Exception;
	public List<Object[]> LeaveApprovalDirRecc(String empNo)throws Exception;
	public List<Object[]> LeaveApprovalDirNR(String empNo)throws Exception;
	public List<Object[]> LeaveApprovalAdm(String empNo)throws Exception;
	public List<Object[]> AssignReccSanc() throws Exception;
	public List<Object[]> getReccSanc(String empNo) throws Exception ;
	public LeaveRaSa getReccSancById(String Id) throws Exception ;
	public List<Object[]> getRaSaStatus() throws Exception;
	public long saveRaSa(LeaveRaSa raSa) throws Exception;
	public List<Object[]> UploadMcFc(String EmpId,String Year) throws Exception;
}

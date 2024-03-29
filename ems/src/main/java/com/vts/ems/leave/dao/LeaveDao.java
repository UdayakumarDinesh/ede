package com.vts.ems.leave.dao;

import java.util.List;

import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.leave.model.LeaveAppl;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.leave.model.LeaveRaSa;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.model.LeaveTransaction;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.pis.model.Employee;

public interface LeaveDao {
	public List<Object[]> PisHolidayList(String year) throws Exception;	
	public List<Object[]> LeaveCreditList(String month,String year) throws Exception;	
	public  List<Object[]> EmpList() throws Exception;	
	public List<Object[]> CreditList(String month) throws Exception;	
	public List<Object[]> LeaveCreditPreview(String month,String year,String emmNo) throws Exception;
	public long LeaveCreditInsert(LeaveRegister register)throws Exception;
	public List<Object[]> LeaveCreditInd(String month, String year,String emmNo) throws Exception;
	public List<Object[]> LeaveCreditById(String registerId)throws Exception;
	public long LeaveCreditAddById(LeaveRegister register)throws Exception;
	public long LeaveCreditUpdateById(LeaveRegister register)throws Exception;
	public List<Object[]>   GetHolidays(String Type)throws Exception;
	public List<Object[]>   EmpDetails(String EmpNo)throws Exception;
	public List<Object[]>   OfficerDetails(String EmpNo)throws Exception;
	public List<Object[]>   EmployeeList()throws Exception;
	public List<Object[]> LeaveCode(String EmpNo) throws Exception;
	public List<Object[]>   purposeList()throws Exception;
	public List<LabMaster> getLabDetails() throws Exception; 
	public List<Object[]> getRegister(String EmpNo, String yr)throws Exception;
	public Object[] checkLeave(String EmpNo,String fromDate,String inDate) throws Exception;
	public long checkHoliday(String inDate,String inType) throws Exception;
	public long getCountHandingOver(String EmpNo,String fromDate,String ToDate)throws Exception;
	public long LeaveApplInsert(LeaveAppl appl)throws Exception;
	public long LeaveTransInsert(LeaveTransaction transaction)throws Exception;
	public long getLeaveApplId(int Year)throws Exception;
	public List<Object[]> getAppliedLeave(String EmpNo)throws Exception;
	public LeaveRegister getOpeningBalance(String EmpNo,String yr) throws Exception;
	public  Object[] getEmployee(String empno) throws Exception;
	public List<Object[]> getRegisterByYear(String EmpNo, String yr)throws Exception;
	public List<Object[]> checkLeaveEl(String EmpNo,String fromDate,String toDate) throws Exception;
	public List<Object[]> LeaveApprovalGh(String empNo)throws Exception;
	public List<Object[]> LeaveTransaction(String applid) throws Exception;
	public Object[] LeavePrint(String applid) throws Exception;
	public Object[] getLabCode() throws Exception;
	public int getUpdateAppl(ApprovalDto dto)throws Exception;
	public int getUpdateRegister(ApprovalDto dto)throws Exception;
	public List<Object[]> getSanctionedLeave(String EmpNo)throws Exception;
	public int deleteLeave(ApprovalDto dto)throws Exception;
	public Object[] getLeaveData(String applid) throws Exception;
	public long AddHandingOver(LeaveHandingOver handinfover)throws Exception;
	public int laeveNotModified(String empno) throws Exception; 
	public int deleteLeaveRegiHo(String applid) throws Exception; 
	public int updateTransaction(String applid,String applIdModified) throws Exception; 
	public List<Object[]> LeaveStatusList(String empNo)throws Exception;
	public List<String> getRegisterYrs(String EmpNo, String yr) throws Exception;
	public List<Object[]> LeaveApprovalDirRecc(String empNo)throws Exception;
	public List<Object[]> LeaveApprovalDirNR(String empNo)throws Exception;
	public List<Object[]> LeaveApprovalAdm(String empNo)throws Exception;
	public List<Object[]> AssignReccSanc() throws Exception ;
	public List<Object[]> getReccSanc(String empNo) throws Exception ;
	public List<Object[]> getRaSaStatus() throws Exception;
	public List<Object[]> UploadMcFc(String EmpId,String Year) throws Exception;
	public LeaveRaSa getLeaveRASADAta(String empno) throws Exception;
}

package com.vts.ems.leave.dao;

import java.util.List;

import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.leave.model.LeaveAppl;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.model.LeaveTransaction;
import com.vts.ems.pis.model.Employee;

public interface LeaveDao {
	public List<Object[]> PisHolidayList(String year) throws Exception;	
	public List<Object[]> LeaveCreditList(String month,String year) throws Exception;	
	public List<Employee> EmpList() throws Exception;	
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
	public List<Object[]> getRegister(String EmpNo)throws Exception;
	public Object[] checkLeave(String EmpNo,String fromDate,String inDate) throws Exception;
	public long checkHoliday(String inDate,String inType) throws Exception;
	public long getCountHandingOver(String EmpNo,String fromDate,String ToDate)throws Exception;
	public long LeaveApplInsert(LeaveAppl appl)throws Exception;
	public long LeaveTransInsert(LeaveTransaction transaction)throws Exception;
	public long getLeaveApplId(int Year)throws Exception;
	
}

package com.vts.ems.leave.service;

import java.util.List;

import com.vts.ems.leave.dto.LeaveApplyDto;
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
	public LeaveRegister getRegister(String EmpNo) throws Exception ;
	public String[] applyLeaveAdd(LeaveApplyDto dto)throws Exception;
	
}

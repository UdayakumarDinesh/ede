package com.vts.ems.leave.service;

import java.util.List;

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
}

package com.vts.ems.service;

import com.vts.ems.modal.AuditStamping;
import com.vts.ems.modal.Employee;

public interface EMSMainService {
	
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
	public int  LoginStampingUpdate(String Logid,String LogoutType)throws Exception;
	public Employee EmployeeInfo(long EmpId) throws Exception;
	public Object[] EmployeeData(String EmpId) throws Exception;
}

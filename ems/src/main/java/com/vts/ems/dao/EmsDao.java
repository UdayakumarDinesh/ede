package com.vts.ems.dao;

import com.vts.ems.model.AuditStamping;
import com.vts.ems.pis.model.Employee;

public interface EmsDao
{
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
	public int LoginStampingUpdate(AuditStamping Stamping) throws Exception;
	public Long LastLoginStampingId(String LoginId) throws Exception;
	public Employee EmployeeInfo(long EmpId) throws Exception;
	public Object[] EmployeeData(String EmpId) throws Exception;
}

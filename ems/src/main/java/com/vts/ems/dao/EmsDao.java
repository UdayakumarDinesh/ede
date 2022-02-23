package com.vts.ems.dao;

<<<<<<< HEAD
import com.vts.ems.modal.AuditStamping;
import com.vts.ems.modal.Employee;
=======
import com.vts.ems.model.AuditStamping;
>>>>>>> branch 'master' of https://dineshvedts@bitbucket.org/susant-vedts/ems.git

public interface EmsDao
{
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
	public int LoginStampingUpdate(AuditStamping Stamping) throws Exception;
	public Long LastLoginStampingId(String LoginId) throws Exception;
	public Employee EmployeeInfo(long EmpId) throws Exception;
	public Object[] EmployeeData(String EmpId) throws Exception;
}

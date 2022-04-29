package com.vts.ems.service;

import java.util.List;

import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;

public interface EMSMainService {
	
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
	public int  LoginStampingUpdate(String Logid,String LogoutType)throws Exception;
	public Employee EmployeeInfo(long EmpId) throws Exception;
	public Object[] EmployeeData(String EmpId) throws Exception;
	public List<EMSNotification> NotificationList(long EmpId) throws Exception;
	public int NotificationUpdate(String NotificationId) throws Exception;
	public Object[] LoginExistCheck(String username) throws Exception;
}

package com.vts.ems.service;

import java.util.List;

import com.vts.ems.Admin.model.CircularList;
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
	public String getPasswordResetOTP(String loginid) throws Exception;
	public int userResetPassword(String loginid, String password) throws Exception;
	public int SendOtpMail(String loginid) throws Exception;
	public String reSendResetOTP(String loginid) throws Exception;
	public Object[] LoginEmpInfo(String loginid) throws Exception;
	public List<Object[]> EmpHandOverLoginTypeList(String empid) throws Exception;
	public List<Object[]> CirculatList(String fromdate , String todate) throws Exception;
	public Object[] MainDashboardCountData(String EmpId, String FromDate, String ToDate,String IsSelf) throws Exception;
	public List<Object[]> MainDashboardGraphData(String EmpId, String FromDate, String ToDate) throws Exception;
	public Object[] MainDashboardAmountData(String EmpId, String FromDate, String ToDate,String IsSelf) throws Exception;
	public List<Object[]> MainDashboardIndividualAmountData(String EmpId, String FromDate, String ToDate) throws Exception;

}

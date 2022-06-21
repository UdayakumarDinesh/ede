package com.vts.ems.dao;

import java.util.List;

import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;

public interface EmsDao
{
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
	public int LoginStampingUpdate(AuditStamping Stamping) throws Exception;
	public Long LastLoginStampingId(String LoginId) throws Exception;
	public Employee EmployeeInfo(long EmpId) throws Exception;
	public Object[] EmployeeData(String EmpId) throws Exception;
	public List<EMSNotification> NotificationList(long EmpId) throws Exception;
	public int NotificationUpdate(String NotificationId) throws Exception;
	public Object[] LoginExistCheck(String username) throws Exception;
	public Object[] getResetOtp(String loginid) throws Exception;
	public int UpdateResetOtp(String loginid, String otp) throws Exception;
	public int userResetPassword(String loginid, String password) throws Exception;
	public Object[] LoginEmpInfo(String loginid) throws Exception;
	public List<Object[]> EmpHandOverLoginTypeList(String empid) throws Exception;
	public List<Object[]> CirculatList() throws Exception;
	public Object[] MainDashboardCountData(String EmpId, String FromDate, String ToDate,String IsSelf) throws Exception;
	public List<Object[]> MainDashboardGraphData(String EmpId, String FromDate, String ToDate) throws Exception;
	public Object[] MainDashboardAmountData(String EmpId, String FromDate, String ToDate,String IsSelf) throws Exception;
	public List<Object[]> MainDashboardIndividualAmountData(String EmpId, String FromDate, String ToDate) throws Exception;
	public List<Object[]> GetDoctorList()throws Exception;
	public Object[] MonthlyWiseDashboardData(String FromDate, String ToDate, int Month) throws Exception;
	public long PasswordChangeHystoryCount(String loginid) throws Exception;
	public long loginHisAddSubmit(LoginPasswordHistory model) throws Exception;
	public List<Object[]> GetEmpanelledHostpitalList()throws Exception;
	public List<Object[]> GetCircularList()throws Exception;
	public List<Object[]> AllowedLoginTypesList(String loginid) throws Exception;
	public List<Object[]> LoginLoginType(String loginid) throws Exception;
}

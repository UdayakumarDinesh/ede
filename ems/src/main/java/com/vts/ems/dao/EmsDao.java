package com.vts.ems.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.EmployeeDesig;

public interface EmsDao
{
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception;
	public int LoginStampingUpdate(AuditStamping Stamping) throws Exception;
	public Long LastLoginStampingId(String LoginId) throws Exception;
	public Employee EmployeeInfo(long EmpId) throws Exception;
	public Object[] EmployeeData(String EmpId) throws Exception;
	public List<EMSNotification> NotificationList(String EmpNo) throws Exception;
	public int NotificationUpdate(String NotificationId) throws Exception;
	public Object[] LoginExistCheck(String username) throws Exception;
	public Object[] getResetOtp(String loginid) throws Exception;
	public int UpdateResetOtp(String loginid, String otp) throws Exception;
	public int userResetPassword(String loginid, String password) throws Exception;
	public Object[] LoginEmpInfo(String loginid) throws Exception;
	public List<Object[]> EmpHandOverLoginTypeList(String empid) throws Exception;
	public long PasswordChangeHystoryCount(String loginid) throws Exception;
	public long loginHisAddSubmit(LoginPasswordHistory model) throws Exception;
	public List<Object[]> AllowedLoginTypesList(String loginid) throws Exception;
	public List<Object[]> LoginLoginType(String loginid) throws Exception;
	public LabMaster getLabDetails() throws Exception;
	public EmployeeDesig DesignationInfo(long DesigId) throws Exception;
	public long AllNoticeCount() throws Exception;
	public List<Object[]> getCircularOrdersNotice() throws Exception;
	public List<Object[]> calendarEvents(String eventyear) throws Exception;
	public List<Object[]> calendarEventTypes() throws Exception;
	public Object[] getEmpCountThirdSes(String date)throws Exception;
	public Object[]getEmpCountSecondSes(String date)throws Exception;
	public Object[] getEmpCountFourthSes(String date)throws Exception;
	public Object[] getEmpCountFifthSes(String date)throws Exception;
	public int getTotalNoOfEmployees()throws Exception;
	public Object[] getEmpCountFirstSes(String date)throws Exception;
}

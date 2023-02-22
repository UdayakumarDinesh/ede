package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import com.vts.ems.Admin.model.CalendarEvents;
import com.vts.ems.Admin.model.ContractEmployeeData;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.model.EMSNotification;

public interface AdminDao {

	
	public List<Object[]> LoginTypeRoles() throws Exception ;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception ;
	public List<Object[]> FormModuleList(String LoginType) throws Exception ;
	public List<BigInteger> FormRoleActiveList(String formroleaccessid) throws Exception;
	public Long FormRoleActive(String formroleaccessid, Long Value) throws Exception;
	public Object[] getChssAprovalList() throws Exception;
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id ,String userid)throws Exception;
	public long AddApprovalAuthority(CHSSApproveAuthority approva)throws Exception;
	public List<Object[]> GetRequestMessageList(String empid)throws Exception;
	public int DeleteRequestMsg(String requestid ,String id)throws Exception;
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception;
	public long AddRequestMsgNotification(EMSNotification notification)throws Exception;
	public List<Object[]> CHSSApprovalAuth2(String Logintype) throws Exception;
	public List<Object[]> GethandlingOverList(LocalDate FromDate, LocalDate Todate)throws Exception;
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception;
	public long AddHandingOver(LeaveHandingOver handinfover)throws Exception;
	public int updateRevokeInHandingOver(long empid ,String UserId , String HandingOverId)throws Exception;
    public List<Object[]> GetReqListFromUser()throws Exception;
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception;
	public List<Object[]> GetReqResMessagelist(String emp ,LocalDate FromDate, LocalDate Todate)throws Exception;
	public List<Object[]> AllNotificationLists(long empid)throws Exception;
	public List<Object[]> GetFromemployee()throws Exception;
	public List<Object[]> GetToemployee()throws Exception;
	public int checkavaibility(String logintype,String detailsid)throws Exception;
	public int updateformroleaccess(String formroleid,String active,String auth)throws Exception;
	public Long insertformroleaccess(FormRoleAccess main) throws Exception;
	public List<Object[]> AllDepCircularSearchList(String search) throws Exception;
	public List<Object[]> getCircularOrdersNotice() throws Exception;
	public List<Object[]> getEventTypeList() throws Exception;
	public Long addCalendarEvents(CalendarEvents events) throws Exception;
	public List<Object[]> getEventsList(String year) throws Exception;
	public Object[] editCalendarEvent(String eMSEventId) throws Exception;
	public long deleteCalendarEvent(String eMSEventId) throws Exception;
	public Long updateCalendarEvent(String eMSEventId, String eventDate, String eventType, String eventName,String eventDescription)throws Exception;
	public Long AddContractEmployeeData(ContractEmployeeData cemp)throws Exception;
	public List<Object[]> getContractEmployeeList()throws Exception;
	public Object[] getContractEmployeeData(String contractEmpId)throws Exception;
	public Long UpdateContractEmployeeData(ContractEmployeeData emp)throws Exception;
	public ContractEmployeeData getContractEmpDetails(Long contractEmpId)throws Exception;
	public String getMaxContractEmpNo()throws Exception;
	public BigInteger contractEmpAddcheck(String cuserName)throws Exception;
	public BigInteger contractEmpEditcheck(String username, String contractEmpId)throws Exception;
	public Long ContractEmpEditComments(MasterEdit masteredit)throws Exception;
}

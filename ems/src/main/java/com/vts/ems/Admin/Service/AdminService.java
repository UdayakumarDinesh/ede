package com.vts.ems.Admin.Service;

import java.util.List;

import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;

public interface AdminService {

	
	
	public List<Object[]> LoginTypeRoles() throws Exception;
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception ;
	public List<Object[]> FormModulesList() throws Exception;
	public List<Object[]> HeaderSchedulesList(String Logintype,String FormModuleId) throws Exception;
	public List<Object[]> FormModuleList(String LoginType)throws Exception ;
	public Long FormRoleActive(String formroleaccessid) throws Exception;	
	public Object[] getChssAprovalList()throws Exception;
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id,String userid)throws Exception;
	public long AddApprovalAuthority(CHSSApproveAuthority approve)throws Exception;	
	public List<Object[]> GetRequestMessageList(String empid)throws Exception;
	public int DeleteRequestMsg(String requestid ,String id)throws Exception;
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception;
	public long EmpRequestNotification(EMSNotification notification)throws Exception;
	public long EmpRequestNotification1(EMSNotification notification)throws Exception;
	public List<Object[]> GethandlingOverList(String fromdate , String todate)throws Exception;
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception;
	public long AddHandingOver(LeaveHandingOver addhanding)throws Exception;
	public int updateRevokeInHandingOver(long empid,String userid , String HandingOverId)throws Exception;
	public List<Object[]> GetReqListFromUser()throws Exception;
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception;
	public List<Object[]> GetReqResMessagelist(String emp , String fromdate , String todate )throws Exception;
	public List<Object[]> AllNotificationLists(long  emp  )throws Exception;
	public List<Object[]> GetFromemployee()throws Exception;
	public List<Object[]> GetToemployee()throws Exception;
	public int updateformroleaccess(String formroleaccessid,String detailsid,String isactive,String logintype, String UserId)throws Exception;
 
	
	
	
	
	
}

package com.vts.ems.Admin.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vts.ems.Admin.dao.AdminDao;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;
@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	AdminDao dao;
	@Autowired
	CHSSDao chssdao;
	
	
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception 
	{
		return dao.LoginTypeRoles();
	}
	
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception 
	{		

		return dao.FormDetailsList(LoginType,ModuleId);
	}
	
	@Override
	public List<Object[]> FormModulesList() throws Exception 
	{
		return dao.FormModulesList();
	}
	
	@Override
	public List<Object[]> HeaderSchedulesList(String Logintype,String FormModuleId) throws Exception
	{
		return dao.HeaderSchedulesList(Logintype,FormModuleId);
	}
	
	@Override
	public List<Object[]> FormModuleList(String LoginType)throws Exception 
	{
		return dao.FormModuleList(LoginType);
	}
	
	@Override
	public Long FormRoleActive(String formroleaccessid) throws Exception 
	{
		

		List<BigInteger> FormRoleActiveList=dao.FormRoleActiveList(formroleaccessid);
		
		Long Value=null;
		
		for(int i=0; i<FormRoleActiveList.size();i++ ) {
			 Value=FormRoleActiveList.get(i).longValue();	
		}

		long ret=dao.FormRoleActive(formroleaccessid,Value);
			
		return ret;
	}
	
	

	
	@Override
	public Object[] getChssAprovalList()throws Exception
	{
		Object[] result= dao.getChssAprovalList();
		
		return result;
	}
	
	@Override
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id,String userid)throws Exception{
		return dao.UpdateApprovalAuth(processing,verification,approving,id,userid); 
	}
	
	@Override
	public long AddApprovalAuthority(CHSSApproveAuthority approve)throws Exception
	{
		return dao.AddApprovalAuthority(approve);
	}
	
	
	@Override
	public List<Object[]> GetRequestMessageList(String empid)throws Exception
	{
	return dao.GetRequestMessageList(empid);	
	}
	@Override
	public int DeleteRequestMsg(String requestid ,String id)throws Exception
	{
		return dao.DeleteRequestMsg(requestid, id);
	}
	@Override
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception
	{
		return dao.AddRequestMsg(reqmsg);
	}
	@Override
	public long EmpRequestNotification(EMSNotification notification)throws Exception
	{
		
		List<Object[]> adminlist =dao.CHSSApprovalAuth2("P");
	
		if(adminlist.size()>0) {
			long id=0;
			for(Object[] obj:adminlist) {
				EMSNotification notifi = new EMSNotification ();
				notifi.setCreatedBy(notification.getCreatedBy());
				notifi.setCreatedDate(notification.getCreatedDate());
				notifi.setIsActive(notification.getIsActive());
				notifi.setNotificationBy(notification.getNotificationBy());
				notifi.setNotificationDate(notification.getNotificationDate());
				notifi.setNotificationMessage(notification.getNotificationMessage());
				notifi.setNotificationValue(notification.getNotificationValue());
				notifi.setNotificationUrl(notification.getNotificationUrl());
				notifi.setEmpId(Long.parseLong(obj[0].toString()));
				long result = dao.AddRequestMsgNotification(notifi);
				if(result>0) {
					id++;
				}
			}
		return id;	
		}else {
			return 0l;
		}
		
	}
	@Override
	public long EmpRequestNotification1(EMSNotification notification)throws Exception
	{
		return  dao.AddRequestMsgNotification(notification);
	}
	
	@Override
	public List<Object[]> GethandlingOverList(String fromdate , String todate)throws Exception
	{	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GethandlingOverList(Fromdate,ToDate);
	}
	
	@Override
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception
	{ 
		
		return dao.checkAlreadyPresentForSameEmpidAndSameDates(FromEmpid, ToEmpid, FromDate, ToDate);
	}
	
	
	@Override
	public int AddHandingOver(LeaveHandingOver addhanding)throws Exception
	{
		return dao.AddHandingOver(addhanding);
	}
	
	
	@Override
	public int updateRevokeInHandingOver(long empid ,String UserId , String HandingOverId)throws Exception
	{
		return dao.updateRevokeInHandingOver(empid,UserId,HandingOverId);
	}
	

	
	@Override
	public List<Object[]> GetReqListFromUser()throws Exception
	{
		return dao.GetReqListFromUser();
	}
	
	@Override
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception
	{
		return dao.UpdateAdminResponse( responsemsg ,  requestid ,UserId);
	}
	
	
	@Override
	public List<Object[]> GetReqResMessagelist(String emp , String fromdate , String todate )throws Exception
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(fromdate,formatter);
		LocalDate ToDate= LocalDate.parse(todate, formatter);
		return dao.GetReqResMessagelist(emp,Fromdate,ToDate);
	}
	@Override
	public List<Object[]> AllNotificationLists(long emp)throws Exception
	{
		return dao.AllNotificationLists(emp);
	}
	
	
	
	@Override
	public List<Object[]> GetFromemployee()throws Exception
	{
		return dao.GetFromemployee();
	}
	
	@Override
	public List<Object[]> GetToemployee()throws Exception
	{
		return dao.GetToemployee();
	}

	@Override
	public int updateformroleaccess(String formroleaccessid,String detailsid,String isactive,String logintype, String UserId)throws Exception{
		
			if(isactive!=null && isactive.equals("0")){
				isactive="1";
			}else {
				isactive="0";
			}
		int result = dao.checkavaibility(logintype,detailsid);
		
		if(result == 0) {
			FormRoleAccess formrole = new FormRoleAccess();
			formrole.setLoginType(logintype);
			formrole.setFormDetailId(Long.parseLong(detailsid));
			formrole.setIsActive(1);
			formrole.setCreatedBy(UserId);
			formrole.setCreatedDate(sdf1.format(new Date()));	
			Long value=dao.insertformroleaccess(formrole);
			return value.intValue();
		}else {
		
			return dao.updateformroleaccess(formroleaccessid,isactive,UserId);
		}
		
	}

	
}
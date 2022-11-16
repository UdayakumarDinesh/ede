package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.chss.dao.CHSSDaoImpl;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;
@Transactional
@Repository
public class AdminDaoImpl implements AdminDao{

	private static final Logger logger = LogManager.getLogger(AdminDaoImpl.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	@PersistenceContext
	EntityManager manager;
	
	
	
	private static final String LOGINTYPEROLES="SELECT LoginTypeId,LoginType,LoginDesc FROM login_type";
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception {

		Query query=manager.createNativeQuery(LOGINTYPEROLES);
		List<Object[]> LoginTypeRoles=(List<Object[]>)query.getResultList();
		
		return LoginTypeRoles;
	}
	
	private static final String FORMDETAILSLIST="SELECT b.formroleaccessid,a.formdetailid,a.formmoduleid,a.formname,b.isactive , b.logintype FROM  (SELECT fd.formdetailid,fd.formmoduleid,fd.formname FROM form_detail fd WHERE fd.isactive=1 AND  CASE WHEN :formmoduleid <> '0' THEN fd.formmoduleid =:formmoduleid ELSE 1=1 END) AS a LEFT JOIN  (SELECT b.formroleaccessid,b.formdetailid AS 'detailid' ,b.logintype,b.isactive FROM form_detail a ,form_role_access b  WHERE a.formdetailid=b.formdetailid AND b.logintype=:logintype AND CASE WHEN :formmoduleid <> '0' THEN a.formmoduleid =:formmoduleid ELSE 1=1 END ) AS b ON a.formdetailid = b.detailid";
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception {

		Query query=manager.createNativeQuery(FORMDETAILSLIST);
		query.setParameter("logintype", LoginType);
		query.setParameter("formmoduleid", ModuleId);
		List<Object[]> FormDetailsList=(List<Object[]>)query.getResultList();
		
		return FormDetailsList;
	}
	private static final String FORMMODULELIST="SELECT FormModuleId,FormModuleName,ModuleUrl,Isactive,IsActive FROM form_module WHERE isactive=1";
	@Override
	public List<Object[]> FormModulesList() throws Exception {
		
		Query query=manager.createNativeQuery(FORMMODULELIST);
		List<Object[]> FormModulesList=(List<Object[]>)query.getResultList();
		
		return FormModulesList;
	}
	private static final String HEADERSCHEDULELIST="SELECT a.formdispname,a.formurl,a.formdetailid,a.formserialno FROM form_detail a , form_role_access b WHERE a.formdetailid=b.formdetailid AND a.formmoduleid=:formmoduleid AND  b.isactive='1'AND b.logintype=:logintype AND a.isactive=1 ORDER BY formserialno ";
	@Override
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception {

		Query query=manager.createNativeQuery(HEADERSCHEDULELIST);
		query.setParameter("logintype",Logintype);
		query.setParameter("formmoduleid", FormModuleId);
		List<Object[]> HeaderSchedulesList=(List<Object[]>)query.getResultList();		
			
		return HeaderSchedulesList;
	}
	
	private static final String FROMMODULELIST = "SELECT DISTINCT a.formmoduleid , a.formmodulename  , a.moduleurl,a.isactive ,a.moduleicon FROM form_module a, form_detail b, form_role_access c WHERE a.isactive='1' AND a.formmoduleid=b.formmoduleid AND b.formdetailid=c.formdetailid AND c.logintype=:LOGINTYPE AND c.isactive=1 ORDER BY a.formmoduleid";
	@Override
	public List<Object[]> FormModuleList(String LoginType) throws Exception {
		Query query = manager.createNativeQuery(FROMMODULELIST);
		query.setParameter("LOGINTYPE", LoginType);
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	}
	private static final String FORMROLEACTIVELIST="SELECT isactive FROM form_role_access WHERE formroleaccessid=:formroleaccessid";
	@Override
	public List<BigInteger> FormRoleActiveList(String formroleaccessid) throws Exception {
		
		Query query=manager.createNativeQuery(FORMROLEACTIVELIST);
		query.setParameter("formroleaccessid", formroleaccessid);
		List<BigInteger> FormRoleActiveList=(List<BigInteger>)query.getResultList();
		
		return FormRoleActiveList;
	}
	

	private static final String FORMROLEACTIVE0="UPDATE form_role_access SET isactive=:isactive WHERE formroleaccessid=:formroleaccessid";
	private static final String FORMROLEACTIVE1="UPDATE form_role_access SET isactive=:isactive WHERE formroleaccessid=:formroleaccessid";
	
	@Override
	public Long FormRoleActive(String formroleaccessid, Long Value) throws Exception {

		int count=0;
		
		System.out.println("Inside UPDATE " + formroleaccessid + Value);
		
		if(Value.equals(1L)) {
			
			System.out.println("Inside if " + formroleaccessid + Value);
			
			Query query=manager.createNativeQuery(FORMROLEACTIVE0);
			query.setParameter("formroleaccessid", formroleaccessid);
			query.setParameter("isactive", "0");
			count=query.executeUpdate();
		}
		if(Value.equals(0L)) {
			Query query=manager.createNativeQuery(FORMROLEACTIVE1);
			query.setParameter("formroleaccessid", formroleaccessid);
			query.setParameter("isactive", "1");
			count=query.executeUpdate();
		}
		
		return (long) count;
	}
	
	

	
	private static final String CHSSAPPROVAL="SELECT a.approveauthlistid ,a.po , a.vo, a.ao FROM chss_approve_auth a ,employee b, employee c ,employee d WHERE a.po=b.empid AND a.vo=c.empid AND a.ao=d.empid AND a.isactive='1'";
	@Override
	public Object[]   getChssAprovalList() throws Exception
	{
		 try {
			 Query query = manager.createNativeQuery(CHSSAPPROVAL);
			 
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO getChssAprovalList "+ e);
				e.printStackTrace();
				return null;
			}	
    }
	
	private static final String APPROVALAUTH="UPDATE chss_approve_auth SET po=:processing , vo=:verification , ao=:approving , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE ApproveAuthListId=:id";
	@Override
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id ,String userid)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(APPROVALAUTH);
			
			query.setParameter("modifiedby", userid);
			query.setParameter("modifieddate",sdtf.format(new Date()) );
			query.setParameter("processing",processing);
			query.setParameter("verification",verification );
			query.setParameter("approving",approving);
			query.setParameter("id",id);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO UpdateApprovalAuth "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddApprovalAuthority(CHSSApproveAuthority approva)throws Exception
	{
		try {
			manager.persist(approva);
			manager.flush();
			return approva.getApproveAuthListId();
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO AddApprovalAuthority "+ e);
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	private static final String REQUESTMSGLIST = "SELECT emprequestid, requestmessage ,responsemessage , DATE_FORMAT(requestdate,'%Y-%m-%d'  ) AS 'requestdate' ,DATE_FORMAT(requestdate, '%H:%i') AS 'requesttime',DATE_FORMAT(responsedate,'%Y-%m-%d'  ) AS 'responsedate' ,DATE_FORMAT(responsedate, '%H:%i') AS 'responsetime' FROM ems_emp_request WHERE empid=:empid and isactive='1'";
	@Override
	public List<Object[]> GetRequestMessageList(String empid) throws Exception {
		try {
			Query query = manager.createNativeQuery(REQUESTMSGLIST);
			query.setParameter("empid", empid);
			List<Object[]> MsgList= query.getResultList();
			return MsgList;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetRequestMessageList "+ e);
			e.printStackTrace();
			return null;
		}
	
	}
	private static final String DELETEREQUESTMSG="UPDATE ems_emp_request SET IsActive=:isactive ,ModifiedBy=:modifiedby ,ModifiedDate=:modifieddate WHERE EmpRequestId=:requestid";
	@Override
	public int DeleteRequestMsg(String requestid ,String modifiedby)throws Exception
	{
		
		try {
			Query query = manager.createNativeQuery(DELETEREQUESTMSG);			
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate",sdtf.format(new Date()) );			
			query.setParameter("isactive","0");
			query.setParameter("requestid",requestid);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO DeleteRequestMsg "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception
	{
		try {
			manager.persist(reqmsg);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO AddRequestMsg "+ e);
			e.printStackTrace();
		}
		return reqmsg.getEmpRequestId();
	}
	
	@Override
	public long AddRequestMsgNotification(EMSNotification notification)throws Exception
	{
		try {
			manager.persist(notification);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO AddRequestMsgNotification "+ e);
			e.printStackTrace();
		}
		return notification.getNotificationId();
	}
	
	
	private static final String CHSSAPPROVALAUTH2  ="SELECT e.empid,e.empname,ed.Designation, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.DesigId = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType  ";
	@Override
	public List<Object[]> CHSSApprovalAuth2(String Logintype) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(CHSSAPPROVALAUTH2);
			query.setParameter("loginType", Logintype);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			logger.error(new Date() +"Inside DAO CHSSApprovalAuth2 "+ e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String HANDLINGOVERLIST="SELECT a.handingoverid , b.empname as 'fromemp' ,c.empname as 'toemp',a.fromdate, a.todate , a.applieddate , a.status FROM leave_ra_sa_handingover a , employee b ,employee c WHERE a.fromempid=b.empid AND a.toempid=c.empid AND a.isactive='1' AND(( a.fromdate BETWEEN  :fromdate AND  :todate ) OR( a.todate BETWEEN  :fromdate AND  :todate )OR ( a.fromdate > :fromdate AND a.todate < :todate )) ORDER BY a.handingoverid DESC";
	@Override
	public List<Object[]> GethandlingOverList(LocalDate FromDate, LocalDate Todate)throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(HANDLINGOVERLIST);
			query.setParameter("fromdate",FromDate );
			query.setParameter("todate", Todate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			logger.error(new Date() +"Inside DAO GethandlingOverList "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String  CHECKHANDINGDATA="SELECT logintype, fromempid FROM leave_ra_sa_handingover  WHERE  toempid=:toemp AND STATUS='A' AND (:fromDate BETWEEN fromdate  AND todate  OR :toDate BETWEEN fromdate  AND todate  OR fromdate BETWEEN :fromDate AND :toDate) AND isactive='1'";
	@Override
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(CHECKHANDINGDATA);				
			//query.setParameter("fromemp", FromEmpid);
			query.setParameter("toemp", ToEmpid);
			query.setParameter("fromDate", DateTimeFormatUtil.dateConversionSql(FromDate));
			query.setParameter("toDate",DateTimeFormatUtil.dateConversionSql(ToDate) );
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			
		}catch (Exception e) {
			logger.error(new Date() +"Inside DAO checkAlreadyPresentForSameEmpidAndSameDates "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public long AddHandingOver(LeaveHandingOver handinfover)throws Exception
	{
		try {
			manager.persist(handinfover);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO AddHandingOver "+ e);
			e.printStackTrace();
		}
		return handinfover.getHandingoverId();
	}
	
	private static final String REVOKEHANDINGOVER="UPDATE leave_ra_sa_handingover SET revokedate=:revokedate , revokeempid=:revokeempId , revokestatus=:revokestatus ,status=:status ,modifiedby=:modifiedby , modifieddate=:modifieddate WHERE handingoverid=:HandingOverId";
	@Override
	public int updateRevokeInHandingOver(long empid ,String UserId, String HandingOverId)throws Exception
	{
		int count=0;
		try {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Query query= manager.createNativeQuery(REVOKEHANDINGOVER);				
			query.setParameter("revokeempId", empid);
			query.setParameter("HandingOverId", HandingOverId);
			query.setParameter("revokestatus","Y");
			query.setParameter("status","R");
			query.setParameter("revokedate",sdf.format(d));
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO updateRevokeInHandingOver "+ e);
			e.printStackTrace();
			return count;
		}
		
	}
	


	
	
	private static final String GETREQLIST="SELECT a.emprequestid , b.empname , a.requestmessage ,a.responsemessage ,a.empid , DATE_FORMAT(a.requestdate,'%Y-%m-%d'  ) AS 'requestdate' ,DATE_FORMAT(a.requestdate, '%H:%i') AS 'requesttime',DATE_FORMAT(a.responsedate,'%Y-%m-%d'  ) AS 'responsedate' ,DATE_FORMAT(a.responsedate, '%H:%i') AS 'responsetime' FROM ems_emp_request a , employee b WHERE a.empid=b.empid AND a.isactive='1' ORDER BY a.emprequestid DESC";
	@Override
	public List<Object[]> GetReqListFromUser()throws Exception
	{

		
		try {
			Query query= manager.createNativeQuery(GETREQLIST);			
		
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO GetReqListFromUser "+ e);
				e.printStackTrace();
				return null;
			}
	}
	
	private static final String UPDATEADMINREPLY="UPDATE ems_emp_request SET modifiedby=:modifiedby , modifieddate=:modifieddate , responsemessage=:responsemsg ,ResponseDate=:responsedate  WHERE emprequestid=:emprequestid";
	@Override
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception
	{
		int count=0;
		try {
			
			Query query= manager.createNativeQuery(UPDATEADMINREPLY);				
			query.setParameter("responsemsg", responsemsg);
			query.setParameter("emprequestid",requestid);
			query.setParameter("modifiedby", UserId);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			query.setParameter("responsedate", sdtf.format(new Date()));
			count = query.executeUpdate();
			return count;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO UpdateAdminResponse "+ e);
				e.printStackTrace();
				return count;
			}
	}
	private static final String GETREQRESMESSAGELIST="SELECT a.emprequestid , b.empname , a.requestmessage  , a.responsemessage , a.empid , DATE_FORMAT(a.requestdate,'%Y-%m-%d'  ) AS 'requestdate' ,DATE_FORMAT(a.requestdate, '%H:%i') AS 'requesttime',DATE_FORMAT(a.responsedate,'%Y-%m-%d'  ) AS 'responsedate' ,DATE_FORMAT(a.responsedate, '%H:%i') AS 'responsetime' FROM ems_emp_request a , employee b WHERE a.empid=b.empid AND a.empid=:emp AND a.isactive='1' AND a.requestdate BETWEEN :FromDate AND :Todate  ORDER BY a.requestdate DESC";
	
	@Override
	public List<Object[]> GetReqResMessagelist(String emp ,LocalDate FromDate, LocalDate Todate)throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(GETREQRESMESSAGELIST);			
			query.setParameter("emp", emp);
			query.setParameter("FromDate",FromDate);
			query.setParameter("Todate", Todate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO GetReqResMessagelist "+ e);
				e.printStackTrace();
				return null;
		   }
	}
	
	private static final String ALLNOTIFICATION="SELECT notificationdate,notificationmessage,notificationurl,notificationid FROM ems_notification WHERE empid=:empid AND isactive=1";
	public List<Object[]> AllNotificationLists(long empid)throws Exception
	{
		try {
			Query query= manager.createNativeQuery(ALLNOTIFICATION);			
			query.setParameter("empid", empid);
		
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				logger.error(new Date() +"Inside DAO AllNotificationLists "+ e);
				e.printStackTrace();
				return null;
		   }
	}
	
	
	private static final String FROMEMPLOYEE="SELECT a.empid,a.empname,b.designation,a.empno,a.divisionid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT po FROM `chss_approve_auth` WHERE isactive='1') UNION  SELECT a.empid,a.empname,b.designation,a.empno,a.divisionid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT vo FROM `chss_approve_auth` WHERE isactive='1') UNION SELECT a.empid,a.empname,b.designation,a.empno,a.divisionid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT ao FROM `chss_approve_auth` WHERE isactive='1')";
	
	@Override
	public List<Object[]> GetFromemployee()throws Exception
	{
		Query query = manager.createNativeQuery(FROMEMPLOYEE);
		List<Object[]> list = (List<Object[]>)query.getResultList();	
		return list; 
	}
	
	private static final String TOEMPLOYEE="SELECT a.empid,a.empname,b.designation,a.empno FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid NOT IN (SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT po FROM `chss_approve_auth` WHERE isactive='1') UNION  SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT vo FROM `chss_approve_auth` WHERE isactive='1') UNION SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT ao FROM `chss_approve_auth` WHERE isactive='1'))";
	@Override
	public List<Object[]> GetToemployee()throws Exception
	{
		Query query = manager.createNativeQuery(TOEMPLOYEE);
		List<Object[]> list = (List<Object[]>)query.getResultList();
		
		return list; 
	}

	
	@Override
	public int checkavaibility(String logintype,String detailsid)throws Exception{
		Query query = manager.createNativeQuery("SELECT COUNT(formroleaccessid)  FROM `form_role_access` WHERE logintype=:logintype  AND  formdetailid=:detailsid");
		query.setParameter("logintype", logintype);
		query.setParameter("detailsid",detailsid );
		
		BigInteger result = (BigInteger) query.getSingleResult();
		return result.intValue();
	}
	
	@Override
	public int updateformroleaccess(String formroleid,String active,String auth)throws Exception{
		Query query = manager.createNativeQuery("UPDATE form_role_access SET isactive=:isactive , modifieddate=:modifieddate , modifiedby=:modifiedby WHERE formroleaccessid=:formroleaccessid");
		query.setParameter("formroleaccessid", formroleid);
		query.setParameter("isactive", active);
		query.setParameter("modifieddate",sdtf.format(new Date()));
		query.setParameter("modifiedby", auth);
		
		return query.executeUpdate();
	}
	
	@Override
	public Long insertformroleaccess(FormRoleAccess main) throws Exception {
		try {
			manager.persist(main);
			manager.flush();
			return (long)main.getFormRoleAccessId();
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO insertformroleaccess "+ e);
			e.printStackTrace();
			return 0l;
		}
	
	}
	

	 
}

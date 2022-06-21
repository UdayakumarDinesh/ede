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

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
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
	
	private static final String FORMDETAILSLIST="SELECT b.formroleaccessid,a.formdetailid,a.formmoduleid,a.formdispname,b.isactive , b.logintype FROM  (SELECT fd.formdetailid,fd.formmoduleid,fd.formdispname FROM form_detail fd WHERE fd.isactive=1 AND  CASE WHEN :formmoduleid <> '0' THEN fd.formmoduleid =:formmoduleid ELSE 1=1 END) AS a LEFT JOIN  (SELECT b.formroleaccessid,b.formdetailid AS 'detailid' ,b.logintype,b.isactive FROM form_detail a ,form_role_access b  WHERE a.formdetailid=b.formdetailid AND b.logintype=:logintype AND CASE WHEN :formmoduleid <> '0' THEN a.formmoduleid =:formmoduleid ELSE 1=1 END ) AS b ON a.formdetailid = b.detailid";
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
	private static final String HEADERSCHEDULELIST="SELECT a.formname,a.formurl,a.formdetailid FROM form_detail a , form_role_access b WHERE a.formdetailid=b.formdetailid AND a.formmoduleid=:formmoduleid AND  b.isactive='1'AND b.logintype=:logintype";
	@Override
	public List<Object[]> HeaderSchedulesList(String FormModuleId,String Logintype) throws Exception {

		logger.info(new Date() +"Inside HeaderSchedulesList");
		Query query=manager.createNativeQuery(HEADERSCHEDULELIST);
		query.setParameter("logintype",Logintype);
		query.setParameter("formmoduleid", FormModuleId);
		List<Object[]> HeaderSchedulesList=(List<Object[]>)query.getResultList();		
			
		return HeaderSchedulesList;
	}
	
	private static final String FROMMODULELIST = "SELECT DISTINCT a.formmoduleid , a.formmodulename  , a.moduleurl,a.isactive ,a.moduleicon FROM form_module a, form_detail b, form_role_access c WHERE a.isactive='1' AND a.formmoduleid=b.formmoduleid AND b.formdetailid=c.formdetailid AND c.logintype=:LOGINTYPE AND c.isactive=1 ORDER BY a.formmoduleid";
	@Override
	public List<Object[]> FormModuleList(String LoginType) throws Exception {
		logger.info(new Date() +"Inside FormModuleList");	
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

		logger.info(new java.util.Date() +"Inside FormRoleActive");
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
		 logger.info(new Date() +"Inside getChssAprovalList()");	
		 try {
			 Query query = manager.createNativeQuery(CHSSAPPROVAL);
			 
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}	
    }
	
	private static final String APPROVALAUTH="UPDATE chss_approve_auth SET po=:processing , vo=:verification , ao=:approving , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE ApproveAuthListId=:id";
	@Override
	public int UpdateApprovalAuth(String processing,String verification,String approving,String id ,String userid)throws Exception
	{
         logger.info(new Date() + "Inside UpdateApprovalAuth()");
		
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
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddApprovalAuthority(CHSSApproveAuthority approva)throws Exception
	{
		logger.info(new Date() + "Inside AddApprovalAuthority()");
		try {
			manager.persist(approva);
			manager.flush();
			return approva.getApproveAuthListId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	private static final String REQUESTMSGLIST = "SELECT emprequestid, requestmessage ,responsemessage , DATE_FORMAT(requestdate,'%Y-%m-%d'  ) AS 'requestdate' ,DATE_FORMAT(requestdate, '%H:%i') AS 'requesttime',DATE_FORMAT(responsedate,'%Y-%m-%d'  ) AS 'responsedate' ,DATE_FORMAT(responsedate, '%H:%i') AS 'responsetime' FROM ems_emp_request WHERE empid=:empid and isactive='1'";
	@Override
	public List<Object[]> GetRequestMessageList(String empid) throws Exception {
		logger.info(new Date() +"Inside GetRequestMessageList()");	
		try {
			Query query = manager.createNativeQuery(REQUESTMSGLIST);
			query.setParameter("empid", empid);
			List<Object[]> MsgList= query.getResultList();
			return MsgList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	private static final String DELETEREQUESTMSG="UPDATE ems_emp_request SET IsActive=:isactive ,ModifiedBy=:modifiedby ,ModifiedDate=:modifieddate WHERE EmpRequestId=:requestid";
	@Override
	public int DeleteRequestMsg(String requestid ,String modifiedby)throws Exception
	{
         logger.info(new Date() + "Inside DeleteRequestMsg()");
		
		try {
			Query query = manager.createNativeQuery(DELETEREQUESTMSG);			
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate",sdtf.format(new Date()) );			
			query.setParameter("isactive","0");
			query.setParameter("requestid",requestid);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddRequestMsg(EmployeeRequest reqmsg)throws Exception
	{
		logger.info(new Date() + "Inside AddRequestMsg()");
		try {
			manager.persist(reqmsg);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqmsg.getEmpRequestId();
	}
	
	@Override
	public long AddRequestMsgNotification(EMSNotification notification)throws Exception
	{
		logger.info(new Date() + "Inside AdDRequestMsgNotification()");
		try {
			manager.persist(notification);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return notification.getNotificationId();
	}
	
	
	private static final String CHSSAPPROVALAUTH2  ="SELECT e.empid,e.empname,ed.Designation, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.DesigId = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType  ";
	@Override
	public List<Object[]> CHSSApprovalAuth2(String Logintype) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApprovalAuth2()");
		try {
			
			Query query= manager.createNativeQuery(CHSSAPPROVALAUTH2);
			query.setParameter("loginType", Logintype);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String HANDLINGOVERLIST="SELECT a.handingover_id , b.empname as 'fromemp' ,c.empname as 'toemp',a.from_date, a.to_date , a.applied_date , a.status FROM leave_ra_sa_handingover a , employee b ,employee c WHERE a.from_empid=b.empid AND a.to_empid=c.empid AND is_active='1' AND(( a.from_date BETWEEN  :fromdate AND  :todate ) OR( a.to_date BETWEEN  :fromdate AND  :todate )OR ( a.from_date > :fromdate AND a.to_date < :todate )) ORDER BY a.handingover_id DESC";
	@Override
	public List<Object[]> GethandlingOverList(LocalDate FromDate, LocalDate Todate)throws Exception
	{
		logger.info(new Date() +"Inside DAO GethandlingOverList()");
		try {
			
			Query query= manager.createNativeQuery(HANDLINGOVERLIST);
			query.setParameter("fromdate",FromDate );
			query.setParameter("todate", Todate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String  CHECKHANDINGDATA="SELECT login_type, from_empid FROM leave_ra_sa_handingover  WHERE  to_empid=:toemp AND STATUS='A' AND (:fromDate BETWEEN from_date  AND to_date  OR :toDate BETWEEN from_date  AND to_date  OR from_date BETWEEN :fromDate AND :toDate) AND is_active='1'";
	@Override
	public Object[] checkAlreadyPresentForSameEmpidAndSameDates(String FromEmpid, String ToEmpid, String FromDate,String ToDate)throws Exception
	{
		logger.info(new Date() +"Inside DAO checkAlreadyPresentForSameEmpidAndSameDates()");
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
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public int AddHandingOver(LeaveHandingOver handinfover)throws Exception
	{
		logger.info(new Date() + "Inside AddHandingOver()");
		try {
			manager.persist(handinfover);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return handinfover.getHandingover_id();
	}
	
	private static final String REVOKEHANDINGOVER="UPDATE leave_ra_sa_handingover SET revokedate=:revokedate , revokeempid=:revokeempId , revokestatus=:revokestatus ,status=:status ,modifiedby=:modifiedby , modifieddate=:modifieddate WHERE handingover_id=:HandingOverId";
	@Override
	public int updateRevokeInHandingOver(long empid ,String UserId, String HandingOverId)throws Exception
	{
		logger.info(new Date() + "Inside updateRevokeInHandingOver()");
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
			e.printStackTrace();
			return count;
		}
		
	}
	


	
	
	private static final String GETREQLIST="SELECT a.emprequestid , b.empname , a.requestmessage ,a.responsemessage ,a.empid , DATE_FORMAT(a.requestdate,'%Y-%m-%d'  ) AS 'requestdate' ,DATE_FORMAT(a.requestdate, '%H:%i') AS 'requesttime',DATE_FORMAT(a.responsedate,'%Y-%m-%d'  ) AS 'responsedate' ,DATE_FORMAT(a.responsedate, '%H:%i') AS 'responsetime' FROM ems_emp_request a , employee b WHERE a.empid=b.empid AND a.isactive='1' ORDER BY a.emprequestid DESC";
	@Override
	public List<Object[]> GetReqListFromUser()throws Exception
	{

		logger.info(new Date() + "Inside GetReqListFromUser()");
		
		try {
			Query query= manager.createNativeQuery(GETREQLIST);			
		
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	}
	
	private static final String UPDATEADMINREPLY="UPDATE ems_emp_request SET modifiedby=:modifiedby , modifieddate=:modifieddate , responsemessage=:responsemsg ,ResponseDate=:responsedate  WHERE emprequestid=:emprequestid";
	@Override
	public int UpdateAdminResponse(String  responsemsg , String requestid, String UserId)throws Exception
	{
		logger.info(new Date() + "Inside UpdateAdminResponse()");
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
				e.printStackTrace();
				return count;
			}
	}
	private static final String GETREQRESMESSAGELIST="SELECT a.emprequestid , b.empname , a.requestmessage  , a.responsemessage , a.empid , DATE_FORMAT(a.requestdate,'%Y-%m-%d'  ) AS 'requestdate' ,DATE_FORMAT(a.requestdate, '%H:%i') AS 'requesttime',DATE_FORMAT(a.responsedate,'%Y-%m-%d'  ) AS 'responsedate' ,DATE_FORMAT(a.responsedate, '%H:%i') AS 'responsetime' FROM ems_emp_request a , employee b WHERE a.empid=b.empid AND a.empid=:emp AND a.isactive='1' AND a.requestdate BETWEEN :FromDate AND :Todate  ORDER BY a.requestdate DESC";
	
	@Override
	public List<Object[]> GetReqResMessagelist(String emp ,LocalDate FromDate, LocalDate Todate)throws Exception
	{
		logger.info(new Date() + "Inside GetReqResMessagelist()");
		
		try {
			Query query= manager.createNativeQuery(GETREQRESMESSAGELIST);			
			query.setParameter("emp", emp);
			query.setParameter("FromDate",FromDate);
			query.setParameter("Todate", Todate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
		   }
	}
	
	private static final String ALLNOTIFICATION="SELECT notificationdate,notificationmessage,notificationurl,notificationid FROM ems_notification WHERE empid=:empid AND isactive=1";
	public List<Object[]> AllNotificationLists(long empid)throws Exception
	{
		logger.info(new Date() + "Inside AllNotificationLists()");
		try {
			Query query= manager.createNativeQuery(ALLNOTIFICATION);			
			query.setParameter("empid", empid);
		
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
		   }
	}
	
	
	private static final String FROMEMPLOYEE="SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT po FROM `chss_approve_auth` WHERE isactive='1') UNION  SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT vo FROM `chss_approve_auth` WHERE isactive='1') UNION SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT ao FROM `chss_approve_auth` WHERE isactive='1')";
	
	@Override
	public List<Object[]> GetFromemployee()throws Exception
	{
		Query query = manager.createNativeQuery(FROMEMPLOYEE);
		List<Object[]> list = (List<Object[]>)query.getResultList();	
		return list; 
	}
	
	private static final String TOEMPLOYEE="SELECT a.empid,a.empname,b.designation FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid NOT IN (SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT po FROM `chss_approve_auth` WHERE isactive='1') UNION  SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT vo FROM `chss_approve_auth` WHERE isactive='1') UNION SELECT a.empid FROM employee a,employee_desig b WHERE a.isactive='1' AND a.DesigId=b.DesigId AND a.empid  IN (SELECT ao FROM `chss_approve_auth` WHERE isactive='1'))";
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
		logger.info(new Date() + "Inside insertformroleaccess()");
		try {
			manager.persist(main);
			manager.flush();
			return (long)main.getFormRoleAccessId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	
	}
	

	 
}

package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.Admin.model.CalendarEvents;
import com.vts.ems.Admin.model.EmployeeContract;
import com.vts.ems.Admin.model.EmployeeRequest;
import com.vts.ems.Admin.model.FormRoleAccess;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.leave.model.LeaveHandingOver;
import com.vts.ems.master.model.MasterEdit;
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
	
	
	
	private static final String FORMOPTIONLIST = "SELECT fo.formoptionid,fo.Formoption FROM form_option fo WHERE fo.formoptionid IN (SELECT DISTINCT fm.formoptionid FROM form_module fm WHERE fm.formmoduleid IN ( SELECT DISTINCT fd.formmoduleid FROM form_detail fd WHERE fd.formdetailid IN (SELECT DISTINCT fra.formdetailid FROM form_role_access fra WHERE fra.logintype=:LOGINTYPE AND fra.isactive=1) AND fd.isactive = 1 )) AND fo.isactive=1";
	@Override
	public List<Object[]> FormOptionList(String LoginType) throws Exception {
		Query query = manager.createNativeQuery(FORMOPTIONLIST);
		query.setParameter("LOGINTYPE", LoginType);
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	}
	
	
	private static final String HEADERMODULEDROPDOWNLIST = "SELECT DISTINCT fm.formmodulename,fm.moduleurl,fm.formmoduleid FROM form_module fm WHERE fm.formmoduleid IN ( SELECT DISTINCT fd.formmoduleid FROM form_detail fd WHERE fd.formdetailid IN (SELECT DISTINCT fra.formdetailid FROM form_role_access fra WHERE fra.logintype=:LOGINTYPE AND fra.isactive=1) AND fd.isactive = 1 ) AND fm.FormOptionId = :FormOptionId";
	@Override
	public List<Object[]> HeaderModuleDropDownList(String LoginType,String FormOptionId) throws Exception {
		Query query = manager.createNativeQuery(HEADERMODULEDROPDOWNLIST);
		query.setParameter("LOGINTYPE", LoginType);
		query.setParameter("FormOptionId", FormOptionId);
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
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
		
		
		if(Value.equals(1L)) {
			
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
	
	private static final String ALLNOTIFICATION="SELECT notificationdate,notificationmessage,notificationurl,notificationid FROM ems_notification WHERE EmpNo=:empno AND isactive=1 ORDER BY notificationid DESC";
	public List<Object[]> AllNotificationLists(String EmpNo)throws Exception
	{
		try {
			Query query= manager.createNativeQuery(ALLNOTIFICATION);			
			query.setParameter("empno", EmpNo);
		
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
	
	private static final String ALLDEPSEARCHCIRCULARLIST="SELECT a.DepCircularId,a.DepCircularNo,DATE_FORMAT(a.DepCircularDate,'%d-%m-%Y') as DepCircularDate ,a.DepCirSubject,a.DepTypeId,b.DepShorrtName FROM ems_dep_circular a, ems_dep_type b  WHERE a.DepTypeId=b.DepTypeId AND a.IsActive=1 AND  (DepCircularNo LIKE :Search  OR DepCirsubject LIKE :Search  ) order by a.DepCircularDate desc"; 
	@Override
	public List<Object[]> AllDepCircularSearchList(String search) throws Exception {
		Query query = manager.createNativeQuery(ALLDEPSEARCHCIRCULARLIST);
		query.setParameter("Search", "%"+search+"%");
		List<Object[]> SearchList= (List<Object[]>)query.getResultList();
		return SearchList;
	}
	
	private static final String RECENTCIRCULARSLIST="SELECT a.DepCircularId,a.DepCircularNo,DATE_FORMAT(a.DepCircularDate,'%d-%m-%Y') as DepCircularDate ,a.DepCirSubject,a.DepTypeId,b.DepShorrtName FROM ems_dep_circular  AS a ,ems_dep_type AS b WHERE a.DepTypeId=b.DepTypeId AND CURDATE() BETWEEN depcirculardate AND DATE_ADD(depcirculardate,INTERVAL 7 DAY) AND a.isactive=1 order by a.DepCircularDate desc ";
	@Override
	public List<Object[]> getCircularOrdersNotice() throws Exception {
	try {
		Query query = manager.createNativeQuery(RECENTCIRCULARSLIST);
		return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	private static final String EventTypeList="select EMSEventTypeId,EventTypeCode,EventType from ems_calendar_eventtypes where IsActive=1";
	@Override
	public List<Object[]> getEventTypeList() throws Exception {
		 List<Object[]> list=null;
	  try {
		Query query = manager.createNativeQuery(EventTypeList);
		 list = (List<Object[]>)query.getResultList();
	} catch (Exception e) {
		
	}
		return list;
	}

	@Override
	public Long addCalendarEvents(CalendarEvents events) throws Exception {
	 try {
		manager.persist(events);
		manager.flush();
	} catch (Exception e) {
	 e.printStackTrace();
	}
		return (Long)events.getEMSEventId();
	}
private static final String Eventslist="select a.EMSEventId,a.EventDate,b.EventType,a.EventName,a.EventDescription from ems_calendar_events as a,ems_calendar_eventtypes as b where a.EventTypeCode=b.EventTypeCode and a.IsActive=1 and extract(year from a.EventDate)=:year order by a.EventDate";
	@Override
	public List<Object[]> getEventsList(String year) throws Exception {
		List<Object[]> list=null;
	try {
			Query query = manager.createNativeQuery(Eventslist);
			query.setParameter("year", year);
			  list= (List<Object[]>)query.getResultList();			 
			  manager.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
		return list;
	}
 private static final String EditEvent="select a.EMSEventId,DATE_FORMAT(a.EventDate,'%d-%m-%Y') as EventDate,b.EventTypeCode,b.EventType,a.EventName,a.EventDescription from ems_calendar_events as a,ems_calendar_eventtypes as b where a.EventTypeCode=b.EventTypeCode and a.IsActive=1  and a.EMSEventId=:EMSEventId";
	@Override
	public Object[] editCalendarEvent(String eMSEventId) throws Exception {
		Object[] events=null;
		try {
			Query query = manager.createNativeQuery(EditEvent);
			query.setParameter("EMSEventId", eMSEventId);
			List<Object[]> list= (List<Object[]>)query.getResultList();
			if(list!=null && list.size()>0) {
				events=list.get(0);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return events;
	}
private static final String  DeletecalendarEvent=" update ems_calendar_events  set IsActive=0 where EMSEventId=:EMSEventId";
	@Override
	public long deleteCalendarEvent(String eMSEventId) throws Exception {
		long result =0;
		try {
			Query query = manager.createNativeQuery(DeletecalendarEvent);
		    query.setParameter("EMSEventId", eMSEventId);
		     result = query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return result ;
	}
private static final String UpdateCalendarEvent="update ems_calendar_events set EventDate=:EventDate,EventTypeCode=:EventTypeCode,EventName=:EventName,EventDescription=:description where EMSEventId=:EMSEventId";
	@Override
	public Long updateCalendarEvent(String eMSEventId, String eventDate, String eventType, String eventName,
			String eventDescription) throws Exception {
		Long result=(long) 0;
	try {
		Query query = manager.createNativeQuery(UpdateCalendarEvent);
		query.setParameter("EventDate", eventDate);
		query.setParameter("EventTypeCode", eventType);
		query.setParameter("EventName", eventName);
		query.setParameter("description", eventDescription);
		query.setParameter("EMSEventId", eMSEventId);
		 result = (long) query.executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		return result;
	}

	@Override
	public Long AddContractEmployeeData(EmployeeContract cemp) throws Exception {
		try {
			manager.persist(cemp);
			manager.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +"Inside DAO AddContractEmployeeData "+ e);
		}
		return(long)cemp.getContractEmpId();
	}
private static final String CONTRACTEMPLIST="SELECT ContractEmpId,EmpName,ContractEmpNo,UserName,DateOfBirth,MobileNo FROM employee_contract WHERE IsActive=1";
	@Override
	public List<Object[]> getContractEmployeeList() throws Exception {
		List<Object[]> list=null;
		try {
			Query query = manager.createNativeQuery(CONTRACTEMPLIST);
			list=(List<Object[]>)query.getResultList();
			return list;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO getContractEmployeeList "+ e);
			e.printStackTrace();
			return null;
		}
		
		
		
	}
private static final String CONTRACTEMPDATA="SELECT ContractEmpId,SUBSTRING(UserName,4),EmpName,DateOfBirth,EmailId,MobileNo FROM employee_contract WHERE ContractEmpId=:EmpId"; 

	@Override
	public Object[] getContractEmployeeData(String contractEmpId) throws Exception {
		Object[] cemp=null;
		try {
			Query query = manager.createNativeQuery(CONTRACTEMPDATA);
			query.setParameter("EmpId", contractEmpId);
		List<Object[]>list=(List<Object[]>)query.getResultList();
		if(list!=null && list.size()>0) {
			cemp=list.get(0);
		}	
			return cemp;
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO getContractEmployeeData "+ e);
			e.printStackTrace();
			return null;
		}
		
	}

private static final String CONTRACTEMPEDIT="SELECT ContractEmpId,UserName,EmpName,DateOfBirth,EmailId,MobileNo FROM employee_contract WHERE ContractEmpId=:EmpId";
	@Override
	public EmployeeContract getContractEmpDetails(Long contractEmpId) throws Exception {
		try {
		return manager.find(EmployeeContract.class,contractEmpId);
		} catch (Exception e) {
			 logger.error(new Date()+" Inside DAO getContractEmpDetails "+ e);
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public Long UpdateContractEmployeeData(EmployeeContract emp) throws Exception {
	try {
		manager.merge(emp);
		manager.flush();
		return emp.getContractEmpId();
	} catch (Exception e) {
		 logger.error(new Date()+" Inside DAO UpdateContractEmployeeData "+ e);
		e.printStackTrace();
		return null;
	}
		
	}
private static final String GETMAXEMPNO="SELECT substring(ContractEmpNo,3) FROM employee_contract  WHERE ContractEmpId=(SELECT MAX(ContractEmpId) FROM employee_contract)";
	@Override
	public String getMaxContractEmpNo() throws Exception {
		try {
			Query query = manager.createNativeQuery(GETMAXEMPNO);
		String EmpNo=(String)query.getSingleResult();
		return EmpNo;
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error(new Date()+" Inside DAO getMaxContractEmpNo "+ e);
			 return null;
		}
		
	}
private static final String USERNAMECOUNT="SELECT COUNT(ContractEmpId) FROM employee_contract WHERE UserName=:userName";
	@Override
	public BigInteger contractEmpAddcheck(String cuserName) throws Exception {
		try {
			
			Query query = manager.createNativeQuery(USERNAMECOUNT);
			query.setParameter("userName", cuserName);
			BigInteger count=(BigInteger)query.getSingleResult();
			
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			 logger.error(new Date()+" Inside DAO contractEmpAddcheck "+ e);
			 return null;
		}
		
	}
private static final String USERNAMECOUNTINEDIT="SELECT COUNT(ContractEmpId) FROM employee_contract WHERE UserName=:UserName AND ContractEmpId!=:ContractEmpId";
	@Override
	public BigInteger contractEmpEditcheck(String username, String contractEmpId) throws Exception {
		try {
			Query query = manager.createNativeQuery(USERNAMECOUNTINEDIT);
			query.setParameter("UserName", username);
			query.setParameter("ContractEmpId", contractEmpId);
			BigInteger count=(BigInteger)query.getSingleResult();
			return count;
		} catch (Exception e) {
			logger.error(new Date()+" Inside DAO contractEmpEditcheck "+ e);
			e.printStackTrace();
			return null;
			 
		}
		
	}

	@Override
	public Long ContractEmpEditComments(MasterEdit masteredit) throws Exception {
		try {
			manager.persist(masteredit);
			manager.flush();
			return (long)masteredit.getMasterEditId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ContractEmpEditComments "+e);
			e.printStackTrace();
			return 0l;
		}
	}

	private static final String REVOKEADMIN ="UPDATE pis_admins SET ModifiedBy=:ModifiedBy,ModifiedDate=:ModifiedDate,IsActive='0' WHERE AdminsId=:AdminsId";
	@Override
	public Long revokeAdmin(String AdminsId, String ModifiedBy, String ModifiedDate) throws Exception {
		try {
			Query query = manager.createNativeQuery(REVOKEADMIN);
			query.setParameter("ModifiedBy", ModifiedBy);
			query.setParameter("ModifiedDate", ModifiedDate);
			query.setParameter("AdminsId", Long.parseLong(AdminsId));
			
			return (long)query.executeUpdate();
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO revokeAdmin "+e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	
}

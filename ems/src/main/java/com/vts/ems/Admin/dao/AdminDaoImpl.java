package com.vts.ems.Admin.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.chss.dao.CHSSDaoImpl;
import com.vts.ems.chss.model.CHSSApproveAuthority;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.utils.DateTimeFormatUtil;
@Transactional
@Repository
public class AdminDaoImpl implements AdminDao{

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	@PersistenceContext
	EntityManager manager;
	
	
	
	private static final String LOGINTYPEROLES="SELECT LoginTypeId,LoginType,LoginDesc FROM login_type";
	@Override
	public List<Object[]> LoginTypeRoles() throws Exception {

		Query query=manager.createNativeQuery(LOGINTYPEROLES);
		List<Object[]> LoginTypeRoles=(List<Object[]>)query.getResultList();
		
		return LoginTypeRoles;
	}
	
	private static final String FORMDETAILSLIST="SELECT a.formroleaccessid,a.logintype,b.formname,a.isactive FROM form_role_access a,form_detail b WHERE a.logintype=:logintype AND a.formdetailid=b.formdetailid AND CASE WHEN :moduleid <> 'A' THEN b.formmoduleid=:moduleid ELSE 1=1 END";
	@Override
	public List<Object[]> FormDetailsList(String LoginType,String ModuleId) throws Exception {

		Query query=manager.createNativeQuery(FORMDETAILSLIST);
		query.setParameter("logintype", LoginType);
		query.setParameter("moduleid", ModuleId);
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
	private static final String HEADERSCHEDULELIST="SELECT a.formname,a.formurl FROM form_detail a , form_role_access b WHERE a.formdetailid=b.formdetailid AND a.formmoduleid=:formmoduleid AND  b.isactive='1'AND b.logintype=:logintype";
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
		
		System.out.println("Inside update " + formroleaccessid + Value);
		
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
	
	

	private static final String OTHERITEM = "SELECT  otheritemid , otheritemname FROM chss_other_items";
	@Override
	public List<Object[]> OtherItems() throws Exception {
		logger.info(new Date() +"Inside OtherItems");	
		Query query = manager.createNativeQuery(OTHERITEM);
		
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	}
	
	private static final String TESTMAIN = "SELECT a.testsubid ,a.testname , a.testrate ,b.testmainname FROM chss_test_sub a , chss_test_main b WHERE isactive='1' AND a.testmainid=b.testmainid";
	@Override
	public List<Object[]> ChssTestSub() throws Exception
	{
		 logger.info(new Date() +"Inside ChssTestMain");	
		 Query query = manager.createNativeQuery(TESTMAIN);
		 List<Object[]> FormModuleList= query.getResultList();
	return FormModuleList;
    } 
	private static final String TESTSUB="SELECT testmainid ,testmainname ,testmaintype FROM chss_test_main";
	@Override
	public List<Object[]>ChssTestMain () throws Exception
	{
		 logger.info(new Date() +"Inside ChssTestSub");	
		 Query query = manager.createNativeQuery(TESTSUB);
		 List<Object[]> FormModuleList= query.getResultList();
	return FormModuleList;
    }
	
	@Override
	public Long AddTestSub(CHSSTestSub TestSub)throws Exception
	{
		logger.info(new Date() + "Inside AddTestSub()");
		try {
			manager.persist(TestSub);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TestSub.getTestSubId();
	}
	
	private static final String GETTESTSUB="FROM CHSSTestSub WHERE TestSubId=:TestSubId";
	
	@Override
	public CHSSTestSub testSub(String TestSubId)throws Exception
	{
		logger.info(new Date() + "Inside testSub()");
		
		try {
			return manager.find(CHSSTestSub.class, Long.parseLong(TestSubId));		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
		
	}
	
	@Override
	public CHSSTestSub getTestSub(long subid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getTestSub()");
		try {
			return manager.find(CHSSTestSub.class, subid);
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public long EditTestSub(CHSSTestSub test) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditTestSub()");
		try {
			manager.merge(test);
			manager.flush();
			
			return test.getTestSubId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
	}
	@Override
	public CHSSOtherItems getOtherItem(int itemid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getOtherItem()");
		try {
			return manager.find(CHSSOtherItems.class, itemid);			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public int AddOtherItem(CHSSOtherItems item)throws Exception
	{
		logger.info(new Date() + "Inside AddOtherItem()");
		try {
			manager.persist(item);
			manager.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return item.getOtherItemId();
	}
	@Override
	public int EditItem(CHSSOtherItems item) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditItem()");
		try {
			manager.merge(item);
			manager.flush();		
			return item.getOtherItemId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}		
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

		} catch (Exception e) {
			e.printStackTrace();
		}
		return approva.getApproveAuthListId();
	}
}

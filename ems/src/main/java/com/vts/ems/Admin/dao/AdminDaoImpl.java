package com.vts.ems.Admin.dao;

import java.math.BigInteger;
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
@Transactional
@Repository
public class AdminDaoImpl implements AdminDao{

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
	
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
	
}

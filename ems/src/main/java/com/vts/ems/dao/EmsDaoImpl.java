package com.vts.ems.dao;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vts.ems.modal.Employee;
import com.vts.ems.model.AuditStamping;

@Transactional
@Repository
public class EmsDaoImpl implements EmsDao 
{
	private static final Logger logger=LogManager.getLogger(EmsDaoImpl.class);
	
	private static final String LOGINSTAMPINGUPDATE="update audit_stamping set logouttype=:logouttype,logoutdatetime=:logoutdatetime where auditstampingid=:auditstampingid";
	private static final String LASTLOGINEMPID = "select a.auditstampingid from  audit_stamping a where a.auditstampingid=(select max(b.auditstampingid) from audit_stamping b WHERE b.loginid=:loginid)";
	
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public Long LoginStampingInsert(AuditStamping Stamping) throws Exception 
	{
		manager.persist(Stamping);
		manager.flush();
		return Stamping.getAuditStampingId();
	}
	
	@Override
	public int LoginStampingUpdate(AuditStamping Stamping) throws Exception 
	{
		Query query = manager.createNativeQuery(LOGINSTAMPINGUPDATE);
		query.setParameter("logouttype", Stamping.getLogOutType());
		query.setParameter("logoutdatetime", Stamping.getLogOutDateTime());
		query.setParameter("auditstampingid", Stamping.getAuditStampingId());		
		int LoginStampingUpdate = (int) query.executeUpdate();
		return  LoginStampingUpdate;
	}
	
	@Override
	public Long LastLoginStampingId(String LoginId) throws Exception {
		Query query = manager.createNativeQuery(LASTLOGINEMPID);
		query.setParameter("loginid", LoginId);
		BigInteger LastLoginStampingId = (BigInteger) query.getSingleResult();
		return LastLoginStampingId.longValue();
	}
	
	
	@Override
	public Employee EmployeeInfo(long EmpId)throws Exception
	{
		logger.info(new Date() +"Inside EmployeeInfo");
		try {
			Employee emloyee = null;
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<Employee> cq= cb.createQuery(Employee.class);
			Root<Employee> root= cq.from(Employee.class);
			Predicate p1 = cb.equal(root.get("EmpId"), EmpId);			
			cq=cq.select(root).where(p1);
			TypedQuery<Employee> allQuery = manager.createQuery(cq);
			emloyee = allQuery.getSingleResult();
			return emloyee;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String EMPLOYEEDATA = "SELECT e.empid,e.empname,ed.designation FROM employee e, employee_desig ed WHERE e.desigid=ed.desigid AND e.empid=:empid";
	
	@Override
	public Object[] EmployeeData(String EmpId)throws Exception
	{
		logger.info(new Date() +"Inside EmployeeData");
		try {
			Query query = manager.createNativeQuery(EMPLOYEEDATA);
			query.setParameter("empid", EmpId);				
			return (Object[])query.getResultList().get(0);			
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

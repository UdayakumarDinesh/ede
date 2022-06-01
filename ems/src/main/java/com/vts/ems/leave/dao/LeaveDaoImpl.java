package com.vts.ems.leave.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.pis.model.Employee;



@Repository
public class LeaveDaoImpl implements LeaveDao{
	
	private static final Logger logger = LogManager.getLogger(LeaveDaoImpl.class);


	@PersistenceContext
    EntityManager manager;

	private static final String HOLIDAYLIST="SELECT * FROM leave_holiday_workingday WHERE DATE_FORMAT(holidate,'%Y')=:holiyear and isactive='1'";
    private static final String CREDITLIST="SELECT a.registerid, b.empname,c.designation ,a.cl,a.el,a.hpl,a.cml,a.rh,a.month,a.year FROM Leave_Register a, employee b, employee_desig c  WHERE a.empid=b.empno and b.designationid=c.desigid and CASE WHEN 'A'=:yr THEN Year=year(sysdate()) ELSE Year=:yr and Month=:mnth END AND Status='K'  ";
    private static final String EMPLIST="FROM Employee WHERE IsActive='1'";
    private static final String CREDIT="select * from leave_credit where month=:mnth";
    private static final String CREDITPREVIEW="select b.empno,b.empname,c.designation,a.cl,a.el,a.hpl,a.cml,a.rh,a.phcl,b.ph FROM Leave_credit a, employee b, employee_desig c  WHERE a.month=:mnth and b.designationid=c.desigid and b.empno not in(select empid from leave_register where month=:mnth and year=:yr and status='K') and case when 'A'=:empNo then 1=1 else  b.empno=:empNo end ";
    private static final String CREDITIND="CALL leave_credit_individual(:mnth,:yr,:empNo)";
    private static final String CREDITBYID="SELECT b.empno,b.empname,c.designation,a.cl,a.el,a.hpl,a.cml,a.rh,a.cl AS phcl,b.ph,a.ccl AS ccl,a.sl AS sl,a.month,a.year,b.gender,a.ml,a.pl   FROM leave_register a, employee b, employee_desig c WHERE  a.registerid=:id AND b.designationid=c.desigid AND b.empno=a.empid";
    
	@Override
	public List<Object[]> PisHolidayList(String year) throws Exception {
		logger.info(new Date() +"Inside PisHolidayList");	
		Query query = manager.createNativeQuery(HOLIDAYLIST);
		query.setParameter("holiyear", year);
		List<Object[]> FormModuleList= query.getResultList();
		return FormModuleList;
	
	}


	@Override
	public List<Object[]> LeaveCreditList(String month, String year) throws Exception {
		logger.info(new Date() +"Inside LeaveCreditList");	
		Query query = manager.createNativeQuery(CREDITLIST);
		query.setParameter("yr", year);
		query.setParameter("mnth", month);
		List<Object[]> LeaveCreditList= query.getResultList();
		return LeaveCreditList;
	
	}


	@Override
	public List<Employee> EmpList() throws Exception {
		logger.info(new Date() +"Inside EmpList");	
		Query query = manager.createQuery(EMPLIST);
		List<Employee> EmpList= query.getResultList();
		return EmpList;
	
	}


	@Override
	public List<Object[]> CreditList(String month) throws Exception {
		logger.info(new Date() +"Inside CreditList");	
		Query query = manager.createNativeQuery(CREDIT);
		query.setParameter("mnth", month);
		List<Object[]> CreditList= query.getResultList();
		return CreditList;
	
	}


	@Override
	public List<Object[]> LeaveCreditPreview(String month, String year,String emmNo) throws Exception {
		logger.info(new Date() +"Inside LeaveCreditPreview");	
		Query query = manager.createNativeQuery(CREDITPREVIEW);
		query.setParameter("yr", year);
		query.setParameter("mnth", month);
		query.setParameter("empNo", emmNo);
		List<Object[]> LeaveCreditPreview= query.getResultList();
		return LeaveCreditPreview;
	
	}


	@Transactional
	@Override
	public long LeaveCreditInsert(LeaveRegister register) throws Exception {
		logger.info(new Date() +"Inside LeaveCreditInsert");	
		manager.persist(register);
		manager.flush();
		return register.getRegisterId();
	}
	
	@Override
	public List<Object[]> LeaveCreditInd(String month, String year,String emmNo) throws Exception {
		logger.info(new Date() +"Inside LeaveCreditInd");	
		Query query = manager.createNativeQuery(CREDITIND);
		query.setParameter("yr", year);
		query.setParameter("mnth", month);
		query.setParameter("empNo", emmNo);
		List<Object[]> LeaveCreditInd= query.getResultList();
		return LeaveCreditInd;
	
	}


	@Override
	public List<Object[]> LeaveCreditById(String registerId) throws Exception {
		logger.info(new Date() +"Inside LeaveCreditInd");	
		Query query = manager.createNativeQuery(CREDITBYID);
		query.setParameter("id", registerId);
		List<Object[]> LeaveCreditInd= query.getResultList();
		return LeaveCreditInd;
	}
	
}

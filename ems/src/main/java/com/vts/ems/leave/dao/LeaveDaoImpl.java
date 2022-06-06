package com.vts.ems.leave.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.pis.model.Employee;



@Repository
public class LeaveDaoImpl implements LeaveDao{
	
	private static final Logger logger = LogManager.getLogger(LeaveDaoImpl.class);


	@PersistenceContext
    EntityManager manager;
	
	@Autowired
	LeaveRegiRepo regirepo;

	private static final String HOLIDAYLIST="SELECT * FROM leave_holiday_workingday WHERE DATE_FORMAT(holidate,'%Y')=:holiyear and isactive='1'";
    private static final String CREDITLIST="SELECT a.registerid, b.empname,c.designation ,a.cl,a.el,a.hpl,a.cml,a.rh,a.month,a.year FROM Leave_Register a, employee b, employee_desig c  WHERE a.empid=b.empno and b.desigid=c.desigid and CASE WHEN 'A'=:yr THEN Year=year(sysdate()) ELSE Year=:yr and Month=:mnth END AND Status='LKU'  ";
    private static final String EMPLIST="FROM Employee WHERE IsActive='1'";
    private static final String CREDIT="select * from leave_credit where month=:mnth";
    private static final String CREDITPREVIEW="select b.empno,b.empname,c.designation,a.cl,a.el,a.hpl,a.cml,a.rh,a.phcl,d.ph FROM Leave_credit a, employee b, employee_desig c,employee_details d   WHERE a.month=:mnth AND d.empno=b.empno and  b.desigid=c.desigid and b.empno not in(select empid from leave_register where month=:mnth and year=:yr and status='LKU') and case when 'A'=:empNo then 1=1 else  b.empno=:empNo end ";
    private static final String CREDITIND="CALL leave_credit_individual(:mnth,:yr,:empNo)";
    private static final String CREDITBYID="SELECT b.empno,b.empname,c.designation,a.cl,a.el,a.hpl,a.cml,a.rh,a.cl AS phcl,d.ph,a.ccl AS ccl,a.sl AS sl,a.month,a.year,d.gender,a.ml,a.pl   FROM leave_register a, employee b, employee_desig c,employee_details d  WHERE  a.registerid=:id AND d.empno=b.empno AND b.desigid=c.desigid AND b.empno=a.empid";
    private static final String HOLIDAYS="select holidate,holiname from leave_holiday_workingday where isactive='1' and case when 'U'=:type then holidate>=DATE(SYSDATE()) else holitype=:type end and year(holidate)=YEAR(CURDATE()) ORDER BY holidate";
    private static final String OFFICERDETAILS="SELECT d.empno,d.empname,e.designation,a.empname AS name1,b.empname AS name2 FROM employee a, employee b, leave_sa_ra c,employee d,employee_desig e WHERE a.empno=c.ra AND b.empno=c.sa AND c.empid=:empNo AND c.empid=d.empno AND e.desigid=d.desigid";
    private static final String EMPDETAILS="SELECT d.empno,d.empname,e.designation FROM employee d,employee_desig e WHERE  :empNo=d.empno AND e.desigid=d.desigid";
    private static final String EMPLOYEELIST="SELECT d.empno,d.empname,e.designation from  employee d,employee_desig e WHERE  e.desigid=d.desigid";
    private static final String LEAVECODE="SELECT a.leave_code,a.type_of_leave FROM Leave_Code a, employee b, employee_details c   WHERE  b.empno=:empno AND c.empno=b.empno and CASE WHEN c.gender='M' THEN  a.leave_code NOT IN ('0006','0007') ELSE  a.leave_code NOT IN ('0010') END  ";
    private static final String PUROPSELIST="select id,reasons from Leave_Purpose";
    
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


	
    @Transactional
    @Override
    public long LeaveCreditAddById(LeaveRegister register) throws Exception {
	 logger.info(new Date() +"Inside LeaveCreditInsertById");	
	 manager.persist(register);
	 manager.flush();
	 return register.getRegisterId();
	}


	@Override
	public long LeaveCreditUpdateById(LeaveRegister register) throws Exception {
		logger.info(new Date() +"Inside LeaveCreditUpdateById");
		regirepo.save(register);
		return register.getRegisterId();
	}


	@Override
	public List<Object[]> GetHolidays(String Type) throws Exception {
		logger.info(new Date() +"Inside GetHolidays");	
		Query query = manager.createNativeQuery(HOLIDAYS);
		query.setParameter("type", Type);
		List<Object[]> GetHolidays= query.getResultList();
		return GetHolidays;
	}


	@Override
	public List<Object[]> EmpDetails(String EmpNo) throws Exception {
		logger.info(new Date() +"Inside EmpDetails");	
		Query query = manager.createNativeQuery(EMPDETAILS);
		query.setParameter("empNo", EmpNo);
		List<Object[]> EmpDetails= query.getResultList();
		return EmpDetails;
	}


	@Override
	public List<Object[]> EmployeeList() throws Exception {
		logger.info(new Date() +"Inside EmployeeList");	
		Query query = manager.createNativeQuery(EMPLOYEELIST);
		List<Object[]> EmpDetails= query.getResultList();
		return EmpDetails;
	}
	
	@Override
	public List<Object[]> LeaveCode(String EmpNo) throws Exception {
		logger.info(new Date() +"Inside LeaveCode");	
		Query query = manager.createNativeQuery(LEAVECODE);
		query.setParameter("empno", EmpNo);
		List<Object[]> EmpDetails= query.getResultList();
		return EmpDetails;
	}


	@Override
	public List<Object[]> purposeList() throws Exception {
		logger.info(new Date() +"Inside purposeList");	
		Query query = manager.createNativeQuery(PUROPSELIST);
		List<Object[]> purposeList= query.getResultList();
		return purposeList;
	}


	@Override
	public List<Object[]> OfficerDetails(String EmpNo) throws Exception {
		logger.info(new Date() +"Inside EmpDetails");	
		Query query = manager.createNativeQuery(OFFICERDETAILS);
		query.setParameter("empNo", EmpNo);
		List<Object[]> EmpDetails= query.getResultList();
		return EmpDetails;
	}
}

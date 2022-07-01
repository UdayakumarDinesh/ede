package com.vts.ems.leave.dao;

import java.math.BigInteger;
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

import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.leave.model.LeaveAppl;
import com.vts.ems.leave.model.LeaveRegister;
import com.vts.ems.leave.model.LeaveTransaction;
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
    private static final String EMPDETAILS="SELECT d.empno,d.empname,e.designation,d.divisionid FROM employee d,employee_desig e WHERE  :empNo=d.empno AND e.desigid=d.desigid";
    private static final String EMPLOYEELIST="SELECT d.empno,d.empname,e.designation from  employee d,employee_desig e WHERE  e.desigid=d.desigid";
    private static final String LEAVECODE="SELECT a.leave_code,a.type_of_leave FROM Leave_Code a, employee b, employee_details c   WHERE  b.empno=:empno AND c.empno=b.empno and CASE WHEN c.gender='M' THEN  a.leave_code NOT IN ('0006','0007') ELSE  a.leave_code NOT IN ('0010') END  ";
    private static final String PUROPSELIST="select id,reasons from Leave_Purpose";
    private static final String LABMASTER="FROM LabMaster";
    private static final String REGISTER="SELECT a.registerid,a.empid,a.cl,a.el,a.hpl,a.cml,a.rh,a.ccl,a.sl,a.ml,a.pl,a.year,a.month,MONTH(STR_TO_DATE(a.month,'%M')) AS monthid,a.status,b.oldstatus,a.from_date,a.to_date,a.appl_id,a.remarks  FROM leave_register a, leave_status_desc b WHERE  a.STATUS=b.status AND :yr>=a.year and  a.empid=:empNo ORDER BY a.year ASC,monthid ASC , b.sortpriority ASC,a.registerid ASC";
    private static final String CHECKDAY="SELECT COUNT(*) FROM leave_holiday_workingday WHERE holidate=:inDate  AND holitype=:inType";
    private static final String CHECKLEAVE="SELECT a.applid, a.leavecode, a.fnan FROM leave_appl a WHERE a.empid=:empno AND a.leaveyear in(YEAR(:fromDate),YEAR(:fromDate)+1)  AND :inDate BETWEEN a.fromdate AND a.todate";   
    private static final String CHECKHANDOVER="select count(*) from";
    private static final String GETAPPLID="SELECT MAX(SUBSTR(applid,6)) FROM leave_appl where leaveyear=:year";
    private static final String LEAVEAPPLIED="Select a.leaveapplid,a.empid,b.leave_name,a.fromdate,a.todate,a.status,a.purleave,a.createdby,a.leaveamend from leave_appl a , leave_code b where b.leave_code=a.leavecode  and a.status in('LAU') and a.empid=:empNo order by a.leaveapplid desc";
    private static final String OPENINGBALANCE="FROM LeaveRegister WHERE STATUS='LOB' AND YEAR=:yr AND EMPID=:EmpNo";
    private static final String REGISTERBYYEAR="SELECT a.registerid,a.empid,a.cl,a.el,a.hpl,a.cml,a.rh,a.ccl,a.sl,a.ml,a.pl,a.year,a.month,MONTH(STR_TO_DATE(a.month,'%M')) AS monthid,a.status,b.oldstatus,a.from_date,a.to_date,a.appl_id,a.remarks  FROM leave_register a, leave_status_desc b WHERE  a.STATUS=b.status AND :yr=a.year and  a.empid=:empNo ORDER BY a.year ASC,monthid ASC , b.sortpriority ASC,a.registerid ASC";
    private static final String CHECKLEAVEEL="SELECT a.applid, a.leavecode, a.fnan FROM leave_appl a WHERE a.empid=:empno AND a.leaveyear in(YEAR(:fromDate),YEAR(:fromDate)+1)  AND (:fromDate BETWEEN a.fromdate AND a.todate or :toDate BETWEEN a.fromdate AND a.todate)";   

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
	
	@Override
	public List<LabMaster> getLabDetails() throws Exception {
		logger.info(new Date() +"Inside getLabDetails");	
		Query query = manager.createQuery(LABMASTER);
		List<LabMaster> EmpList= query.getResultList();
		return EmpList;
	
	}


	@Override
	public List<Object[]> getRegister(String EmpNo, String yr) throws Exception {
		logger.info(new Date() +"Inside getRegister");	
		Query query = manager.createNativeQuery(REGISTER);
		query.setParameter("empNo", EmpNo);
		query.setParameter("yr", yr);
		List<Object[]> getRegister= query.getResultList();
		return getRegister;
	}
	
	@Override
	public List<Object[]> getRegisterByYear(String EmpNo, String yr) throws Exception {
		logger.info(new Date() +"Inside getRegisterByYear");	
		Query query = manager.createNativeQuery(REGISTERBYYEAR);
		query.setParameter("empNo", EmpNo);
		query.setParameter("yr", yr);
		List<Object[]> getRegister= query.getResultList();
		return getRegister;
	}
	
	@Override
	public long checkHoliday(String inDate,String inType) throws Exception {
		logger.info(new Date() +"Inside checkHoliday");	
		Query query = manager.createNativeQuery(CHECKDAY);
		query.setParameter("inType", inType);
		query.setParameter("inDate", inDate);
		BigInteger checkHoliday=(BigInteger)query.getSingleResult();
		return checkHoliday.longValue();
	}
	

	@Override
	public Object[] checkLeave(String EmpNo,String fromDate,String inDate) throws Exception {
		logger.info(new Date() +"Inside checkLeave");	
		Query query = manager.createNativeQuery(CHECKLEAVE);
		query.setParameter("empno", EmpNo);
		query.setParameter("fromDate", fromDate);
		query.setParameter("inDate", inDate);
		Object[] checkLeave=null;
		try {
		checkLeave=(Object[])query.getSingleResult();
		}
		catch (Exception e) {
			
		}
		return checkLeave;
	}


	@Override
	public long getCountHandingOver(String EmpNo, String fromDate, String ToDate) throws Exception {
		logger.info(new Date() +"Inside checkHoliday");	
		Query query = manager.createNativeQuery(CHECKHANDOVER);
		query.setParameter("empno", EmpNo);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", ToDate);
		BigInteger checkHoliday=(BigInteger)query.getSingleResult();
		return checkHoliday.longValue();
	}
	
	@Transactional
	@Override
	public long LeaveApplInsert(LeaveAppl appl) throws Exception {
		logger.info(new Date() +"Inside LeaveApplInsert");	
		manager.persist(appl);
		manager.flush();
		return appl.getLeaveApplId();
	}
	
	@Transactional
	@Override
	public long LeaveTransInsert(LeaveTransaction leaveTransaction) throws Exception {
		logger.info(new Date() +"Inside LeaveTransInsert");	
		manager.persist(leaveTransaction);
		manager.flush();
		return leaveTransaction.getLeaveTransactionId();
	}


	@Override
	public long getLeaveApplId(int Year) throws Exception {
		logger.info(new Date() +"Inside getLeaveApplId");	
		Query query = manager.createNativeQuery(GETAPPLID);
		query.setParameter("year", Year);
		long id=0;
		try {
		String checkHoliday=(String)query.getSingleResult();
		id=Long.parseLong(checkHoliday);
		}catch (Exception e) {
		e.printStackTrace();
		}
		return id;
	}


	@Override
	public List<Object[]> getAppliedLeave(String EmpNo) throws Exception {
		logger.info(new Date() +"Inside getAppliedLeave");	
		Query query = manager.createNativeQuery(LEAVEAPPLIED);
		query.setParameter("empNo", EmpNo);
		List<Object[]> getAppliedLeave= query.getResultList();
		return getAppliedLeave;
	}
	
	@Override
	public LeaveRegister getOpeningBalance(String EmpNo,String yr) throws Exception {
		logger.info(new Date() +"Inside getOpeningBalance");	
		Query query = manager.createQuery(OPENINGBALANCE);
		query.setParameter("EmpNo", EmpNo);
		query.setParameter("yr", yr);
		LeaveRegister getAppliedLeave=(LeaveRegister) query.getSingleResult();
		return getAppliedLeave;
	}
	
    private static final String EMPLOYEE="Select a.empid,a.empno,a.empname,a.desigid,b.basicpay,b.gender,b.bloodgroup,a.email,b.phoneno,b.paylevelid,b.dob from employee a, employee_details b where a.empno=b.empno and a.isactive='1' and a.empno=:empno ";
	
	@Override
	public  Object[] getEmployee(String empno) throws Exception
	{
		logger.info(new Date() +"Inside DAO getEmployee");
		Query query =manager.createNativeQuery(EMPLOYEE);
		Object[] result = null;
		query.setParameter("empno", empno);
		
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public List<Object[]> checkLeaveEl(String EmpNo,String fromDate,String toDate) throws Exception {
		logger.info(new Date() +"Inside checkLeaveEl");	
		Query query = manager.createNativeQuery(CHECKLEAVEEL);
		query.setParameter("empno", EmpNo);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		List<Object[]> checkLeave=(List<Object[]>)query.getResultList();
		return checkLeave;
	}

    private static final String LEAVEAPPGH="CALL leave_sa_ra_gh(:empNo)";
	
	@Override
	public List<Object[]> LeaveApprovalGh(String empNo) throws Exception {
		logger.info(new Date() +"Inside checkLeaveEl");	
		Query query = manager.createNativeQuery(LEAVEAPPGH);
		query.setParameter("empno", empNo);
		List<Object[]> checkLeave=(List<Object[]>)query.getResultList();
		return checkLeave;
	}
	
}

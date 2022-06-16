package com.vts.ems.dao;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.vts.ems.Admin.model.LoginPasswordHistory;
import com.vts.ems.model.AuditStamping;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;


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
		logger.info(new Date() +"Inside DAO EmployeeInfo");
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
		logger.info(new Date() +"Inside DAO EmployeeData");
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
	
	private static final String PASSWORDCHANGEHYSTORYCOUNT  ="SELECT COUNT(loginid),'passwordCount' FROM login_password_history WHERE loginid=:loginid";
	@Override
	public long PasswordChangeHystoryCount(String loginid) throws Exception
	{
		logger.info(new Date() +"Inside DAO PasswordChangeHystoryCount");
		try {
			Query query = manager.createNativeQuery(PASSWORDCHANGEHYSTORYCOUNT);
			query.setParameter("loginid", loginid);		
			Object[] result = (Object[])query.getResultList().get(0);
			
			return Long.parseLong(result[0].toString());
					
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public List<EMSNotification> NotificationList(long EmpId)throws Exception
	{
		logger.info(new Date() +"Inside DAO NotificationList");
		try {
			List<EMSNotification> notylist = null;
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EMSNotification> cq= cb.createQuery(EMSNotification.class);
			Root<EMSNotification> root= cq.from(EMSNotification.class);
			Predicate p1 = cb.equal(root.get("EmpId"), EmpId);			
			Predicate p2 = cb.equal(root.get("IsActive"), 1);			
			cq=cq.select(root).where(p1,p2);
			TypedQuery<EMSNotification> allQuery = manager.createQuery(cq);
			notylist = allQuery.getResultList();
			return notylist;
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<EMSNotification>();
		}
	}
	
	private static final String NOTIFICATIONUPDATE="update ems_notification set isactive='0' where notificationid=:notificationid ";
	@Override
	public int NotificationUpdate(String NotificationId) throws Exception
	{
		
		logger.info(new Date() +"Inside DAO NotificationUpdate");	
		Query query = manager.createNativeQuery(NOTIFICATIONUPDATE);
		
		query.setParameter("notificationid", NotificationId);
		
		int count= (int)query.executeUpdate();
		return count;
	}
	
	
	private static final String LOGINEXISTCHECK="SELECT  l.LoginId, l.UserName, l.EmpId, e.EmpName, e.Email, l.ResetOTP FROM  login l, employee e WHERE l.EmpId = e.EmpId  AND l.IsActive=1 AND l.UserName =:username ";
	@Override
	public Object[] LoginExistCheck(String username) throws Exception
	{		
		logger.info(new Date() +"Inside DAO LoginExistCheck");	
		try {
			Query query = manager.createNativeQuery(LOGINEXISTCHECK);
			
			query.setParameter("username", username);
			return (Object[])query.getSingleResult();
		}
		catch (Exception e) {
			logger.error(new Date() +" Inside DAO LoginExistCheck "+ e);
			return null;
		}
	}
	
	private static final String UPDATERESETOTP = "UPDATE login SET ResetOTP = :otp WHERE loginid= :loginid";
			
	@Override
	public int UpdateResetOtp(String loginid,String otp) throws Exception
	{		
		logger.info(new Date() +"Inside DAO UpdateResetOtp");	
		try {
			Query query = manager.createNativeQuery(UPDATERESETOTP);
			
			query.setParameter("loginid", loginid);
			query.setParameter("otp", otp);
			
			
			return query.executeUpdate();
		}
		catch (Exception e) {
			logger.error(new Date() +" Inside DAO UpdateResetOtp "+ e);
			return 0;
		}
	}
	
	private static final String GETRESETOTP = "SELECT resetotp,'otp' FROM login WHERE loginid =:loginid ";
	
	@Override
	public Object[] getResetOtp(String loginid) throws Exception
	{		
		logger.info(new Date() +"Inside DAO getResetOtp");	
		try {
			Query query = manager.createNativeQuery(GETRESETOTP);
			
			query.setParameter("loginid", loginid);
			
			return (Object[])query.getSingleResult();
		}
		catch (Exception e) {
			logger.error(new Date() +" Inside DAO getResetOtp "+ e);
			return null;
		}
	}
	
	
	private static final String USERRESETPASSWORD = "UPDATE login SET password = :password , ResetOTP =NULL  WHERE loginid=:loginid";
	
	@Override
	public int userResetPassword(String loginid,String password) throws Exception
	{		
		logger.info(new Date() +"Inside DAO userResetPassword");	
		try {
			Query query = manager.createNativeQuery(USERRESETPASSWORD);
			
			query.setParameter("loginid", loginid);
			query.setParameter("password", password);
			
			
			return query.executeUpdate();
		}
		catch (Exception e) {
			logger.error(new Date() +" Inside DAO userResetPassword"+ e);
			return 0;
		}
	}
	
	
	
	
	
	private static final String LOGINEMPINFO="SELECT  l.LoginId, l.UserName, l.EmpId, e.EmpName, e.Email, l.ResetOTP FROM  login l, employee e WHERE l.EmpId = e.EmpId  AND l.IsActive=1 AND l.LoginId =:loginid ";
	
	@Override
	public Object[] LoginEmpInfo(String loginid) throws Exception
	{		
		logger.info(new Date() +"Inside DAO LoginEmpInfo");	
		try {
			Query query = manager.createNativeQuery(LOGINEMPINFO);
			
			query.setParameter("loginid", loginid);
			return (Object[])query.getSingleResult();
		}
		catch (Exception e) {
			logger.error(new Date() +" Inside DAO LoginEmpInfo "+ e);
			return null;
		}
	}
	
	private static final String EMPHANDOVERLOGINTYPELIST="SELECT  l.logintype,  lt.LoginDesc FROM  login l,  login_type lt WHERE   l.isactive=1 AND l.LoginType=lt.LoginType AND l.empid=:empid UNION SELECT l.logintype,  lt.LoginDesc FROM  leave_ra_sa_handingover ho,  login l,  login_type lt WHERE ho.is_active = 1  AND l.isactive = 1  AND l.logintype=lt.LoginType  AND ho.from_empid = l.empid  AND ho.status = 'A'  AND (CURDATE() BETWEEN ho.from_date AND ho.to_date) AND ho.to_empid = :empid ";
	
	@Override
	public List<Object[]> EmpHandOverLoginTypeList(String empid) throws Exception
	{		
		logger.info(new Date() +"Inside DAO EmpHandOverLoginTypeList");	
		try {
			Query query = manager.createNativeQuery(EMPHANDOVERLOGINTYPELIST);
			
			query.setParameter("empid", empid);
			return (List<Object[]>)query.getResultList();
		}
		catch (Exception e) {
			logger.error(new Date() +" Inside DAO EmpHandOverLoginTypeList "+ e);
			return null;
		}
	}
	
	private static final String MAINDASHBOARDCOUNTDATA = "CALL Chss_MainDashboard_Count (:empid, :fromdate,  :todate, :isself) ";
	
	@Override 
	public Object[] MainDashboardCountData(String Empid, String FromDate, String ToDate,String IsSelf) throws Exception{
	
		logger.info(new Date() + "Inside DAO MainDashboardCountData");
		
		try {
			
			Query query = manager.createNativeQuery(MAINDASHBOARDCOUNTDATA);
			query.setParameter("empid", Empid);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", ToDate);
			query.setParameter("isself", IsSelf);
			
			return (Object[])query.getSingleResult();
			
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside Dao MainDashboardCountData " + e);
			return null;
		}
		
	}
	
	private static final String MAINDASHBOARDGRAPHDATA = "CALL Chss_MainDashboard_GraphData (:empid, :fromdate,  :todate) ";
	
	@Override 
	public List<Object[]> MainDashboardGraphData(String Empid, String FromDate, String ToDate) throws Exception{
	
		logger.info(new Date() + "Inside DAO MainDashboardGraphData");
		try {
			
			Query query = manager.createNativeQuery(MAINDASHBOARDGRAPHDATA);
			query.setParameter("empid", Empid);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", ToDate);
			
			return (List<Object[]> )query.getResultList();
			
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside Dao MainDashboardGraphData " + e);
			return null;
		}
		
	}
	
	private static final String CIRCULARLIST = "SELECT circularid , description , path , circulardate  FROM chss_circular_list  WHERE CURDATE() BETWEEN circulardate AND todate ORDER BY circulardate DESC";
	@Override
	 public List<Object[]> CirculatList() throws Exception
	 {
		 logger.info(new Date() +"Inside DAO CirculatList()");	
		 try {
				Query query =  manager.createNativeQuery(CIRCULARLIST);
				
				return (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO CirculatList "+ e);
			e.printStackTrace();
			return null;
		}
	
	 }
	 
	 private static final String MAINDASHBOARDAMOUNTDATA = "CALL Chss_MainDashboard_Total_Amnt (:empid, :fromdate,  :todate, :isself) ;";
	 
	 @Override
	 public Object[] MainDashboardAmountData(String EmpId, String FromDate, String ToDate,String IsSelf) throws Exception{
		 		 
		 logger.info(new Date() + "Inside DAO MainDashboardAmountData");
			try {
				
				Query query = manager.createNativeQuery(MAINDASHBOARDAMOUNTDATA);
				query.setParameter("empid", EmpId);
				query.setParameter("fromdate", FromDate);
				query.setParameter("todate", ToDate);
				query.setParameter("isself", IsSelf);
				
				return (Object[])query.getSingleResult();
			}
			catch(Exception e) {
				logger.error(new Date()  + "Inside Dao MainDashboardAmountData " + e);
				return null;
			}
	 }
	 
	 private static final String MAINDASHBOARDAMOUNTINDIVIDUALDATA = "CALL Chss_MainDashboard_Individual_Amnt (:empid, :fromdate,  :todate) ";

	 @Override 
		public List<Object[]> MainDashboardIndividualAmountData(String Empid, String FromDate, String ToDate) throws Exception{
		
			logger.info(new Date() + "Inside DAO MainDashboardIndividualAmountData");
			try {
				
				Query query = manager.createNativeQuery(MAINDASHBOARDAMOUNTINDIVIDUALDATA);
				query.setParameter("empid", Empid);
				query.setParameter("fromdate", FromDate);
				query.setParameter("todate", ToDate);
				
				return (List<Object[]> )query.getResultList();
			}
			catch(Exception e) {
				logger.error(new Date()  + "Inside Dao MainDashboardIndividualAmountData " + e);
				return null;
			}
		}
		
		
		private static final String DASHBOARDMONTHDATA="CALL Chss_MainDashboard_Monthly_Data (:fromdate, :todate, :month)";
		
		@Override
		 public Object[] MonthlyWiseDashboardData(String FromDate, String ToDate,int Month) throws Exception{
			 		 
			 logger.info(new Date() + "Inside DAO MonthlyWiseDashboardData");
				try {
					
					Query query = manager.createNativeQuery(DASHBOARDMONTHDATA);
					query.setParameter("fromdate", FromDate);
					query.setParameter("todate", ToDate);
					query.setParameter("month", Month);
					
					return (Object[])query.getSingleResult();
				}
				catch(Exception e) {
					logger.error(new Date()  + "Inside Dao MonthlyWiseDashboardData " + e);
					return null;
				}
		 }
		

		private static final String DOCTORSLIST="SELECT doctorid ,doctorname , qualification, address, phoneno FROM chss_doctor_list ORDER BY doctorid DESC";
		@Override
		public List<Object[]> GetDoctorList()throws Exception
		{
			 logger.info(new Date() +"Inside DAO GetDoctorList()");	
			 try {
					Query query =  manager.createNativeQuery(DOCTORSLIST);
					
					return (List<Object[]>)query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO GetDoctorList "+ e);
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public long loginHisAddSubmit(LoginPasswordHistory model) throws Exception
		{
			logger.info(new Date() +"Inside DAO loginHisAddSubmit");

			try {
				manager.persist(model);
				manager.flush();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return model.getPasswordHistoryId(); 
		}
		private static final String EMPANELLEDHOSPITALLIST="SELECT  EmpanelledHospitalId ,HospitalName, hospitaladdress FROM chss_empanelledhospital WHERE isactive='1'";
		@Override
		public List<Object[]> GetEmpanelledHostpitalList()throws Exception
		{
			 logger.info(new Date() +"Inside DAO GetEmpanelledHostpitalList()");	
			 try {
					Query query =  manager.createNativeQuery(EMPANELLEDHOSPITALLIST);
					
					return (List<Object[]>)query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO GetEmpanelledHostpitalList "+ e);
				e.printStackTrace();
				return null;
			}
		}
		private static final String GETCIRCULAR="SELECT circularid , description , path , CircularDate  ,OriginalName,todate FROM chss_circular_list ORDER BY circularid DESC";
		@Override
		public List<Object[]> GetCircularList()throws Exception
		{
			 logger.info(new Date() +"Inside DAO GetCircularList()");	
			 try {
					Query query =  manager.createNativeQuery(GETCIRCULAR);
					
					return (List<Object[]>)query.getResultList();
			} catch (Exception e) {
				logger.error(new Date() +" Inside DAO GetCircularList "+ e);
				e.printStackTrace();
				return null;
			}
		}
	
}

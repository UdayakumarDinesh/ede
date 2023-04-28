package com.vts.ems.Tour.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.vts.ems.Tour.model.TourApply;
import com.vts.ems.Tour.model.TourOnwardReturn;
import com.vts.ems.Tour.model.TourTransaction;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class TourDaoImpl implements TourDao {
	
	private static final Logger logger = LogManager.getLogger(TourDaoImpl.class);

	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	@PersistenceContext
	EntityManager manager;

		private static final String EMPLOYEEDETAILSLIST="SELECT e.empid,e.empno,e.empname, ed.designation  ,eds.dob , pl.paylevel , e.mobileno , e.email FROM employee e, employee_desig ed,employee_details eds ,pis_pay_level pl WHERE  e.empno=eds.empno AND  e.isactive=1 AND e.desigid=ed.desigid AND eds.paylevelid = pl.paylevelid ";
		@Override
		public List<Object[]> GetEmployeeList() throws Exception {
			Query query = manager.createNativeQuery(EMPLOYEEDETAILSLIST);
			return (List<Object[]>) query.getResultList();
		}
		
		private static final String GETMODEOFTRAVELLIST="SELECT modeid , modename FROM tour_mode_of_travel";
		@Override
		public List<Object[]> GetModeofTravel() throws Exception {
			Query query = manager.createNativeQuery(GETMODEOFTRAVELLIST);
			return (List<Object[]>) query.getResultList();
		}
		
		private static final String CITYLIST="SELECT tourcityid , cityname , cityid , cityclass FROM tour_city";
		@Override
		public List<Object[]> GetCityList() throws Exception {
			Query query = manager.createNativeQuery(CITYLIST);
			return (List<Object[]>) query.getResultList();
		}
		
		@Override
		public Long AddTourapply(TourApply apply)throws Exception
		{
			try {
				manager.persist(apply);
				manager.flush();
				return apply.getTourApplyId();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO AddTourapply() "+e);
				e.printStackTrace();
				return 0l;
			}
		}

		@Override
		public Long AddTourOnwardReturn(TourOnwardReturn tourdetails)throws Exception
		{
			try {
				manager.persist(tourdetails);
				manager.flush();
				return tourdetails.getTourOnwardReturnId();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO AddTourOnwardReturn() "+e);
				e.printStackTrace();
				return 0l;
			}
		}

		@Override
		public Long AddTourTransaction(TourTransaction transaction)throws Exception
		{
			try {
				manager.persist(transaction);
				manager.flush();
				return transaction.getTourTransactionId();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO AddTourTransaction() "+e);
				e.printStackTrace();
				return 0l;
			}
		}
		
		private static final String GETAPPLYLIST="SELECT b.empname ,a.stayfrom , a.stayto ,CAST(a.initiateddate AS DATE) AS 'initiateddate' , a.purpose  , a.stayplace  , a.tourstatuscode , a.tourapplyid FROM tour_apply a , employee b WHERE a.empno=b.empno ORDER BY a.tourapplyid DESC";
		@Override
		public List<Object[]> GetTourApplyList()throws Exception
		{
			Query query = manager.createNativeQuery(GETAPPLYLIST);
			return (List<Object[]>) query.getResultList();
		}
	
		private static final String CHECKDATE="SELECT a.Empno,a.divisionId FROM tour_apply a WHERE Empno=:empno AND a.tourstatuscode IN ('INI','FWD','RDH','VDH','RDG') AND (:fromdate BETWEEN a.stayfrom  AND a.stayto OR  :todate BETWEEN a.stayfrom  AND a.stayto  OR a.stayfrom BETWEEN :fromdate AND :todate) AND a.IsActive='1'";
		@Override
		public Long  checkTDAlreadyPresentForSameEmpidAndSameDates(String empno,String DepartureDate,String ArrivalDate)
	   	{
			  try {

					Query query = manager.createNativeQuery(CHECKDATE);
						query.setParameter("empno",empno);
		   		        query.setParameter("fromdate",DepartureDate);
		   			    query.setParameter("todate",ArrivalDate);
					List<Object[]> list =(List<Object[]>)query.getResultList();
					
					Long result=0l;
					if(list!=null && list.size()>0) {
						result=(long) list.size();
					}
					return result;
				} catch (Exception e) {
					logger.error(new Date() + "Inside DAO EmployeeDetails "+e);
					e.printStackTrace();
					return 0l;
				}  
	   		}
		
		@Override
		public TourApply  getTourApplyData(Long tourid) throws Exception
		{
			TourApply apply = null;
			try {
				
				Query query = manager.createQuery("FROM TourApply WHERE TourApplyId=:TourApplyid AND IsActive=1");
				query.setParameter("TourApplyid",tourid);
				apply =(TourApply)query.getSingleResult();
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO getTourApplyData "+e);
				e.printStackTrace();
			}
			return apply;
		}
		
		@Override
		public List<TourOnwardReturn> getTourOnwardReturnData(Long tourid)throws Exception
		{
			List<TourOnwardReturn> details = null;
			try{
				Query query = manager.createQuery("FROM TourOnwardReturn WHERE TourApplyId=:TourApplyid");
				query.setParameter("TourApplyid", tourid);
				details =(List<TourOnwardReturn>)query.getResultList();
			}catch(Exception e){
				logger.error(new Date() + "Inside DAO getTourOnwardReturnData "+e);
				e.printStackTrace();
			}
			return details;
		}
		
		@Override
		public Long  UpdateTourApply(TourApply apply) throws Exception
		{
			try {
				manager.merge(apply);
				manager.flush();

			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO UpdateTourApply "+e);
				e.printStackTrace();
			}
			return apply.getTourApplyId();
		}
		
		private static final String DELETEDETAILS="DELETE FROM tour_onwardreturn WHERE tourapplyid=:tourid";
		@Override
		public int DeleteOnwardReturnData(Long tourid) throws Exception
		{
			try {
				Query query = manager.createNativeQuery(DELETEDETAILS);
				query.setParameter("tourid", tourid);
				int count = (int) query.executeUpdate();
				return count;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO DeleteOnwardReturnData "+e);
				e.printStackTrace();
				return 0;
			}
		}
		
		private static final String FORWARDTOUR = "UPDATE tour_apply SET tourstatuscode=:status , modifiedby=:empno , modifieddate=:modifieddate where tourapplyid=:tourid";
		@Override
		public int ForwardTour(String tourapplyid , String empno)throws Exception
		{
			try {
				Query query = manager.createNativeQuery(FORWARDTOUR);
				query.setParameter("status", "FWD");
				query.setParameter("modifieddate", sdtf.format(new Date()));
				query.setParameter("modifiedby", empno);
				query.setParameter("tourid", tourapplyid);
				int count = (int) query.executeUpdate();
				return count;
			} catch (Exception e) {
				logger.error(new Date() + "Inside DAO ForwardTour "+e);
				e.printStackTrace();
				return 0;
			}
		}
	

}

package com.vts.ems.Tour.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.vts.ems.leave.dto.ApprovalDto;
import com.vts.ems.model.EMSNotification;
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
		private static final String GETAPPLYLIST="SELECT b.empname ,a.stayfrom , a.stayto ,CAST(a.initiateddate AS DATE) AS 'initiateddate' , a.purpose  , a.stayplace  , a.tourstatuscode , a.tourapplyid , c.statusdesc ,c.statuscolor FROM tour_apply a , employee b , tour_status c WHERE a.empno=b.empno AND a.tourstatuscode=c.tourstatuscode AND a.empno=:empno  ORDER BY a.tourapplyid DESC";
		@Override
		public List<Object[]> GetTourApplyList(String empno)throws Exception
		{
			Query query = manager.createNativeQuery(GETAPPLYLIST);
			query.setParameter("empno", empno);
			return (List<Object[]>) query.getResultList();
		
		}
		private static final String GETAPPLYSTATUSLIST="SELECT b.empname ,a.stayfrom , a.stayto ,CAST(a.initiateddate AS DATE) AS 'initiateddate' , a.purpose  , a.stayplace  , a.tourstatuscode , a.tourapplyid , c.statusdesc ,c.statuscolor FROM tour_apply a , employee b , tour_status c WHERE a.empno=b.empno AND a.tourstatuscode=c.tourstatuscode AND a.tourstatuscode IN ('INI','FWD','RDH','VDH','RDG','ABD','RBF','ABF','RBP','ABP','RBC','ABC') AND a.empno=:empno  AND a.initiateddate BETWEEN :fromdate AND :todate ORDER BY a.tourapplyid DESC";
		@Override
		public List<Object[]> GetApplyStatusList(String empno ,  String fromdate , String todate)throws Exception
		{
			Query query = manager.createNativeQuery(GETAPPLYSTATUSLIST);
			query.setParameter("empno", empno);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
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
		
		private static final String FORWARDTOUR = "UPDATE tour_apply SET tourstatuscode=:status , modifiedby=:modifiedby , modifieddate=:modifieddate where tourapplyid=:tourid";
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
		private static final String GETTOURAPPROVALLIST="call tour_approvallist(:empno)";
	@Override
	public List<Object[]> GetTourApprovalList(String empno)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETTOURAPPROVALLIST);
			query.setParameter("empno", empno);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private static final String CANCELLIST="call tour_cancelapprovallist(:empno)";
	@Override
	public List<Object[]> GetTourCancelList(String empno)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(CANCELLIST);
			query.setParameter("empno", empno);
			List<Object[]> List=(List<Object[]>) query.getResultList();
			return List;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String UPDATEAPPL ="UPDATE tour_apply SET tourstatuscode=:statuscode , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE tourapplyid=:applid ";
	private static final String UPDATEFAAPPL ="UPDATE tour_apply SET tourstatuscode=:statuscode , fundsavailable=:funds, modifiedby=:modifiedby , modifieddate=:modifieddate WHERE tourapplyid=:applid ";

	@Transactional
	@Override
	public int getTourUpdate(ApprovalDto dto) throws Exception {
		
		try {
			int count=0;
			if(dto.getStatus().equalsIgnoreCase("ABF")) {
				Query query = manager.createNativeQuery(UPDATEFAAPPL);
				query.setParameter("applid",dto.getApplId());
				query.setParameter("statuscode",dto.getStatus());
				query.setParameter("funds", dto.getFunds());
				query.setParameter("modifiedby", dto.getUserName());
				query.setParameter("modifieddate", sdtf.format(new Date()));
				count = (int) query.executeUpdate();
			}else {
				Query query = manager.createNativeQuery(UPDATEAPPL);
				query.setParameter("applid",dto.getApplId());
				query.setParameter("statuscode",dto.getStatus());
				query.setParameter("modifiedby", dto.getUserName());
				query.setParameter("modifieddate", sdtf.format(new Date()));
				count = (int) query.executeUpdate();
			}
			return count;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO getUpdateAppl "+e);
			e.printStackTrace();
			return 0;
		}	
	}
	
	private static final String UPDATEAPPLFROMPandA ="UPDATE tour_apply SET PandARemarks=:remarks  , tourstatuscode=:statuscode , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE tourapplyid=:applid ";

	@Override
	public int TourUpdateFromPandA (ApprovalDto dto , String remarks)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(UPDATEAPPLFROMPandA);
			query.setParameter("remarks", remarks);
			query.setParameter("applid",dto.getApplId());
			query.setParameter("statuscode",dto.getStatus());
			query.setParameter("modifiedby", dto.getUserName());
			query.setParameter("modifieddate", sdtf.format(new Date()));
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ForwardTour "+e);
			e.printStackTrace();
			return 0;
		}
	
		
	}
	
	private static final String UPDATEAPPLFROMCEO ="UPDATE tour_apply SET ApprovedDate=:approveddate , tourno=:tourno , tourstatuscode=:statuscode , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE tourapplyid=:applid ";

	@Override
	public int TourUpdateFromCEO (ApprovalDto dto , String Tourno)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(UPDATEAPPLFROMCEO);
			query.setParameter("tourno", Tourno);
			query.setParameter("applid",dto.getApplId());
			query.setParameter("statuscode",dto.getStatus());
			query.setParameter("approveddate", sdtf.format(new Date()));
			query.setParameter("modifiedby", dto.getUserName());
			query.setParameter("modifieddate", sdtf.format(new Date()));
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ForwardTour "+e);
			e.printStackTrace();
			return 0;
		}	
	}
	private static final String TOURSTATUSDETAILS="SELECT a.tourtransactionid,f.empno,c.empname,d.designation,e.divisionname,a.actiondate,a.tourremarks,b.statusdesc,b.statuscolor  FROM tour_transaction a, tour_status b,employee c,employee_desig d,division_master e ,tour_apply f WHERE a.tourstatuscode=b.tourstatuscode AND f.tourapplyid=a.tourapplyid AND a.actionby=c.empno  AND c.desigid=d.desigid AND c.divisionid=e.divisionid AND a.tourstatuscode IN ('INI','FWD','RDH','VDH','RDG','ABD','RBF','ABF','RBP','ABP','RBC','ABC') AND a.tourapplyid=:tourapplyid ORDER BY a.tourtransactionid";
	@Override
	public List<Object[]> TourStatusDetails(String tourapplyid)throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(TOURSTATUSDETAILS);
			query.setParameter("tourapplyid", tourapplyid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSStatusDetails " + e);
			return new ArrayList<Object[]>();
		}
	}
	private static final String TOURCANCELSTATUSDETAILS="SELECT a.tourtransactionid,f.empno,c.empname,d.designation,e.divisionname,a.actiondate,a.tourremarks,b.statusdesc,b.statuscolor  FROM tour_transaction a, tour_status b,employee c,employee_desig d,division_master e ,tour_apply f WHERE a.tourstatuscode=b.tourstatuscode AND f.tourapplyid=a.tourapplyid AND a.actionby=c.empno  AND c.desigid=d.desigid AND c.divisionid=e.divisionid and a.tourstatuscode IN ('CBU','CAD','CAA','CDG','CAG','CAF','CDF','CAP','CDP','CAC','CDC')  AND a.tourapplyid=:tourapplyid ORDER BY a.tourtransactionid";
	@Override
	public List<Object[]> TourCancelStatusDetails(String tourapplyid)throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(TOURCANCELSTATUSDETAILS);
			query.setParameter("tourapplyid", tourapplyid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSStatusDetails " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	
	private static final String GETAPPROVALEMP="CALL tour_approvalflow(:empno)";
	@Override
	public Object[] GetApprovalEmp(String empno)throws Exception
	{
		try {
			 Query query = manager.createNativeQuery(GETAPPROVALEMP);
			 query.setParameter("empno",empno);
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
		}catch (Exception e){
				logger.error(new Date() +"Inside DAO GetApprovalEmp "+ e);
				e.printStackTrace();
				return null;
		}	
    }
	private static final String GETALLTOURNO="SELECT TourApplyId,TourNo FROM tour_apply WHERE SUBSTRING(tourno,1,12)=:finyear  GROUP BY TourApplyId";
	@Override
	public List<Object[]> getFinancialyearWiseTourMONoList(String Financialyear)throws Exception
	{
		try {
			Query query= manager.createNativeQuery(GETALLTOURNO);
			query.setParameter("finyear", Financialyear);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSStatusDetails " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	@Override
	public Long EmpNotificationForTour(EMSNotification notification)throws Exception
	{
		try {
			manager.persist(notification);
			manager.flush();
			return notification.getNotificationId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmpNotificationForTour() "+e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	private static final String REVOKETOUR ="UPDATE tour_apply SET  tourstatuscode=:statuscode , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE tourapplyid=:applid ";

	
	@Override
	public Long RevokeTour(ApprovalDto dto)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(REVOKETOUR);
			query.setParameter("applid",dto.getApplId());
			query.setParameter("statuscode",dto.getStatus());
			query.setParameter("modifiedby", dto.getUserName());
			query.setParameter("modifieddate", sdtf.format(new Date()));
			Long count = (long) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ForwardTour "+e);
			e.printStackTrace();
			return 0l;
		}	
	}
	
	private  static final String GETSANCLIST=" SELECT b.empname ,a.stayfrom , a.stayto ,CAST(a.initiateddate AS DATE) AS 'initiateddate' , a.purpose , a.stayplace  , a.tourstatuscode , a.tourapplyid , c.statusdesc ,c.statuscolor FROM tour_apply a , employee b , tour_status c WHERE a.empno=b.empno AND a.tourstatuscode=c.tourstatuscode  AND a.empno=:empno AND a.tourstatuscode='ABC'"; 
	@Override
	public List<Object[]> GetSanctionList(String empno)throws Exception
	{
		try {
			Query query= manager.createNativeQuery(GETSANCLIST);
			query.setParameter("empno", empno);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetSanctionList " + e);
			return new ArrayList<Object[]>();
		}
	}

	private static final String GETFAPA="SELECT a.AdminsId, a.pandaadmin , fandaadmin , a.RevisedOn FROM pis_admins a WHERE a.IsActive=1 ";
	@Override
	public Object[] GetPAFADetails()throws Exception
	{
		try {
			 Query query = manager.createNativeQuery(GETFAPA);
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
		}catch (Exception e){
				logger.error(new Date() +"Inside DAO GetPAFADetails "+ e);
				e.printStackTrace();
				return null;
		}	
   }
	private static final String CANCELTOUR="UPDATE tour_apply SET CancelReason=:reason  , tourstatuscode=:statuscode , modifiedby=:modifiedby , modifieddate=:modifieddate WHERE tourapplyid=:applid ";
   @Override
   public int CancelTour(ApprovalDto dto)throws Exception
   {
	   try{
			Query query = manager.createNativeQuery(CANCELTOUR);
			query.setParameter("reason", dto.getValue());
			query.setParameter("applid",dto.getApplId());
			query.setParameter("statuscode",dto.getStatus());
			query.setParameter("modifiedby", dto.getUserName());
			query.setParameter("modifieddate", sdtf.format(new Date()));
			int count = (int) query.executeUpdate();
			return count;
		}catch(Exception e){
			logger.error(new Date() +"Inside DAO CancelTour "+ e);
			e.printStackTrace();
			return 0;
		}
   }
   private static final String TOURCANCELSTATUS="SELECT b.empname ,a.stayfrom , a.stayto ,CAST(a.initiateddate AS DATE) AS 'initiateddate' , a.purpose  , a.stayplace  , a.tourstatuscode , a.tourapplyid , c.statusdesc ,c.statuscolor  FROM tour_apply a , employee b , tour_status c WHERE a.empno=b.empno AND a.tourstatuscode=c.tourstatuscode  AND a.tourstatuscode IN ('CBU','CAD','CAA','CDG','CAG','CAF','CDF','CAP','CDP','CAC','CDC') AND a.empno=:empno  AND a.initiateddate BETWEEN :fromdate AND :todate ORDER BY a.tourapplyid DESC";
   @Override
   public List<Object[]> GetTourCancelList(String empno ,  String fromdate , String todate) throws Exception {
	
		Query query = manager.createNativeQuery(TOURCANCELSTATUS);
		query.setParameter("empno", empno);
		query.setParameter("fromdate", fromdate);
		query.setParameter("todate", todate);
		return (List<Object[]>) query.getResultList();
	}
}

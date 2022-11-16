package com.vts.ems.Mt.dao;

import java.math.BigInteger;
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.Mt.model.MtApplyTransaction;
import com.vts.ems.Mt.model.MtDirectorDuty;
import com.vts.ems.Mt.model.MtDriver;
import com.vts.ems.Mt.model.MtLinkDuty;
import com.vts.ems.Mt.model.MtTrip;
import com.vts.ems.Mt.model.MtUserApply;
import com.vts.ems.Mt.model.MtVehicle;
import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;

@Transactional
@Repository
public class MtDaoImpl implements MtDao {

	private static final Logger logger = LogManager.getLogger(MtDaoImpl.class);


	@PersistenceContext
    EntityManager manager;
	
	
	private static final String DUTYTYPE="SELECT dutytypeid , typeofduty FROM mt_duty_type WHERE isactive=1";
	private static final String PROJECTLIST="SELECT projectid , projectcode FROM project_master WHERE isactive=1"; 
	private static final String LASTREQNO=" SELECT mtreqno from mt_appl WHERE mtapplid=(select max(mtapplid)from mt_appl)  ";
	@Override
	public List<Object[]> GetDutyType() throws Exception{
		Query query = manager.createNativeQuery(DUTYTYPE);
		List<Object[]> list= query.getResultList();
		return list;
	}
	
	@Override
	public List<Object[]> GetProjectList()throws Exception 
	{
		Query query = manager.createNativeQuery(PROJECTLIST);
		List<Object[]> list= query.getResultList();
		return list;
	}
	
	@Override
	public String GetLastReqNo() throws Exception {

		Query query= manager.createNativeQuery(LASTREQNO);
		String result= (String)query.getSingleResult();		
		return result;
		
	}
	
	
	@Override
	public int UserApply(MtUserApply user) throws Exception {

		try {
			manager.persist(user);
			manager.flush();
			return (int)user.getMtApplId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserApply() "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int MtApplyTranscation(MtApplyTransaction mttra)throws Exception
	{
		try {
			manager.persist(mttra);
			manager.flush();
			return (int)mttra.getMtApplTransactionId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO MtApplyTranscation() "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public MtUserApply GetMtUserApply(int  mtapplid)throws Exception
	{
		MtUserApply list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<MtUserApply> cq = cb.createQuery(MtUserApply.class);
			Root<MtUserApply> root = cq.from(MtUserApply.class);
			Predicate p1 = cb.equal(root.get("MtApplId"), mtapplid);
			cq = cq.select(root).where(p1);
			TypedQuery<MtUserApply> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
			return list;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetMtUserApply() "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int UserApplyEdit(MtUserApply mtuserapply)throws Exception
	{
		try {
			manager.merge(mtuserapply);
			manager.flush();
			return (int)mtuserapply.getMtApplId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserApplyEdit() "+e);
			e.printStackTrace();
			return 0;
		}
	}
	
	private static final String USERAPYLISTAPPLYSTATUS="SELECT MtApplId, MtReqNo, EmpId, DateOfTravel, EndDateOfTravel,StartTime, EndTime, Source, Destination, DutyTypeId, MtReason,OneWayDistance, ContactNo,UserRemarks, ProjectId, MtStatus, ApplDate FROM mt_appl WHERE EMPID=:empid AND ISACTIVE=:isActive AND DATEOFTRAVEL>=CURDATE() AND MTSTATUS IN ('A','M','V','N')";
	
	@Override
	public List<Object[]> GetApplyDataOFApplyStatus(String Empno)throws Exception 
	{
		Query query = manager.createNativeQuery(USERAPYLISTAPPLYSTATUS);
		query.setParameter("empid", Empno);
		query.setParameter("isActive", "1");
		List<Object[]> list= query.getResultList();
		return list;
	}
	
	
	@Override
	public List<Object[]> GetApplyDataOfSancApplyStatus(String EmpNo )throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("call mt_trip_sanc_list(:empid);");		
			query.setParameter("empid", EmpNo );
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetApplyDataOfSancApplyStatus() "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<Object[]> VehiclePendingListDashBoard()throws Exception 
	{

		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("CALL mt_vehicle_pending();");				
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO VehiclePendingListDashBoard() "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	@Override
	public List<Object[]> VehicleAssignedListDashBoard()throws Exception 
	{

		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("CALL mt_vehicle_assigned();");				
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO VehicleAssignedListDashBoard() "+e);
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	private static final String USERAPYLDATA="FROM MtUserApply WHERE MTAPPLID=:mtapplid";
	@Override
	public MtUserApply getApplySingleData(int MtApplId)throws Exception 
	{
		MtUserApply mtuserapply =null;
		try {
			Query query = manager.createQuery(USERAPYLDATA);
			query.setParameter("mtapplid", MtApplId);
			mtuserapply = (MtUserApply) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getApplySingleData() "+e);
			e.printStackTrace();
		}
		return mtuserapply;
	}
	
	@Override
	public Object[] getEmpData(String EmpNo) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		Object[] empdata=null;
		try {
			Query query = manager.createNativeQuery("CALL mt_employeedata(:empno);");
			query.setParameter("empno", EmpNo);
			list = (List<Object[]>)query.getResultList();
		
			if(list!=null && list.size()>0) {
				empdata=list.get(0);
			}
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getEmpData() "+e);
			e.printStackTrace();
		}
		return empdata;
	}
	
	private static final String USERAPPLYDELETE="UPDATE mt_appl SET ISACTIVE=:isActive,MODIFIEDBY=:modifiedby,MODIFIEDDATE=:modifieddate WHERE MTAPPLID=:mtapplid";
	
	@Override
	public int UserApplyCancel(MtUserApply apply) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(USERAPPLYDELETE);
			query.setParameter("isActive", "0");
			query.setParameter("mtapplid",apply.getMtApplId());
			query.setParameter("modifiedby", apply.getModifiedBy());
			query.setParameter("modifieddate", apply.getModifiedDate());
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO UserApplyCancel() "+e);
			e.printStackTrace();
			return  0;
		}
		
		
	}
	
	@Override
	public List<Object[]> getApplyList(String EmpId) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		
		try {
			Query query = manager.createNativeQuery("CALL mt_approve_mto(:empid)");
			query.setParameter("empid", EmpId);
			list = (List<Object[]>)query.getResultList();
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getApplyList() "+e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Object[]> getDriverList() throws Exception {
		List<Object[]> driverlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_driverlist()");
			driverlist = (List<Object[]>)query.getResultList();			
			return driverlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getDriverList() "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String VEHICLELIST="FROM MtVehicle WHERE ISACTIVE=:isActive order by VehicleId desc";
	@Override
	public List<MtVehicle> GetVechileList() throws Exception
	{
		try {			
				Query query= manager.createQuery(VEHICLELIST);
				query.setParameter("isActive", "1");
				return  query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetVechileList() "+e);
			e.printStackTrace();
			return null;
		}				
	}
	
	@Override
	public List<Object[]> getListOfTrip() throws Exception {
		List<Object[]> driverlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_triplist()");
			driverlist = (List<Object[]>)query.getResultList();			
			return driverlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getListOfTrip() "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Object[]> GetLinkRequest() throws Exception {
		List<Object[]> driverlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_reqlinklist()");
			driverlist = (List<Object[]>)query.getResultList();			
			return driverlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO GetLinkRequest() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String LASTTRIPNO=" SELECT TripNo FROM mt_trip WHERE TripId=(SELECT MAX(TripId) FROM mt_trip)  ";
	public String getLastTripNo() throws Exception
	{
		Query query= manager.createNativeQuery(LASTTRIPNO);
		String result= (String)query.getSingleResult();		
		return result;
	}
	

	
	@Override
	public int Inserttrip(MtTrip trip) throws Exception {

		try {
			manager.persist(trip);
			manager.flush();
			return (int)trip.getTripId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO Inserttrip() "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	private static final String GETRECOFFICER="SELECT  empid FROM employee WHERE empno = (SELECT ra FROM leave_sa_ra WHERE empid=:empid)";
	@Override
	public String GetRecOfficer(String empid) throws Exception {

		Query query= manager.createNativeQuery(GETRECOFFICER);
		query.setParameter("empid", empid);
		BigInteger result= (BigInteger)query.getSingleResult();
		
		return String.valueOf(result) ;
		
	}
	
	@Override
	public Long MtEmsNotification(EMSNotification notification)throws Exception
	{
		try {
			manager.persist(notification);
			manager.flush();
			return notification.getNotificationId();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO MtEmsNotification() "+ e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	@Override
	public List<Object[]> GhApproveList(String EmpId) throws Exception {
		List<Object[]> driverlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_approve_gh(:EmpId)");
			query.setParameter("EmpId", EmpId);
			driverlist = (List<Object[]>)query.getResultList();			
			return driverlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO GhApproveList() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMPIDMTAPPL="select empid from mt_appl where mtapplid=:mtapplid";
	public String EmpIdOfMtRequest(int MtApplId) throws Exception 
	{
		Query query= manager.createNativeQuery(EMPIDMTAPPL);
		query.setParameter("mtapplid", MtApplId);
		return (String)query.getSingleResult(); 
	}
	
	private static final String USERAPPLYGHAPPROVE="UPDATE mt_appl SET MTSTATUS=:mtstatus,MODIFIEDBY=:modifiedby,MODIFIEDDATE=:modifieddate,FORWARDEDBY=:forwardedby,FORWARDEDDATE=:forwardeddate,FORWARDREMARKS=:forwardremarks WHERE MTAPPLID=:mtapplid";

	public int StatusUpdate(MtUserApply apply) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(USERAPPLYGHAPPROVE);
			query.setParameter("mtstatus","V");
			query.setParameter("mtapplid",apply.getMtApplId());
			query.setParameter("modifiedby",apply.getModifiedBy());
			query.setParameter("modifieddate", apply.getModifiedDate());
			query.setParameter("forwardedby", apply.getForwardedBy());
			query.setParameter("forwardeddate", apply.getForwardedDate());
			query.setParameter("forwardremarks", apply.getForwardRemarks());
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO StatusUpdate() "+ e);
			e.printStackTrace();
			return  0;
		}
		
	
	}
	
	
	private static final String TRIPDATA="FROM MtTrip WHERE TRIPID=:tripid";
	
	
	@Override
	public MtTrip GetTrip(int TripId) throws Exception {
		
		try {			
				Query query= manager.createQuery(TRIPDATA);
				query.setParameter("tripid", TripId);
				MtTrip trip=(MtTrip)query.getSingleResult();
			return trip;
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetTrip() "+ e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String DELETETRIP="UPDATE mt_trip SET ISACTIVE=:isactive WHERE TRIPID=:tripid";

	
	public int DeleteTrip(int tripid) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(DELETETRIP);
			query.setParameter("isactive", 0);
			query.setParameter("tripid", tripid);
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteTrip()"+e);
			e.printStackTrace();
			return  0;
		}
	}
	@Override
	public int EditTrip(MtTrip trip) throws Exception 
	{
		try {
			manager.merge(trip);
			manager.flush();
			return trip.getTripId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditTrip() "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	private static final String USERAPPLYAPPROVE="UPDATE mt_appl SET MTSTATUS=:mtstatus,MODIFIEDBY=:modifiedby,MODIFIEDDATE=:modifieddate WHERE MTAPPLID=:mtapplid";
	@Override
	public int insertTriplink(MtLinkDuty linkduty) throws Exception {
		
		int count;
		if(linkduty.getTripId()==0) {
			
			Query query=manager.createNativeQuery(USERAPPLYAPPROVE);
			query.setParameter("mtstatus","N");
			query.setParameter("mtapplid",linkduty.getMtApplId());
			query.setParameter("modifiedby",linkduty.getCreatedBy());
			query.setParameter("modifieddate", linkduty.getCreatedDate());
			
			 count=query.executeUpdate();
			 
		}else if(linkduty.getTripId()==-1) {
			
			Query query=manager.createNativeQuery(USERAPPLYAPPROVE);
			query.setParameter("mtstatus","M");
			query.setParameter("mtapplid",linkduty.getMtApplId());
			query.setParameter("modifiedby",linkduty.getCreatedBy());
			query.setParameter("modifieddate", linkduty.getCreatedDate());
			count=query.executeUpdate();	
		}
		else {
		
			manager.persist(linkduty);
			manager.flush();
		
			Query query=manager.createNativeQuery(USERAPPLYAPPROVE);
			query.setParameter("mtstatus","S");
			query.setParameter("mtapplid",linkduty.getMtApplId());
			query.setParameter("modifiedby",linkduty.getCreatedBy());
			query.setParameter("modifieddate", linkduty.getCreatedDate());
			count=query.executeUpdate();
		}
		return count;
	}
	
	@Override
	public List<Object[]> PrintList(String fromDate, String toDate) throws Exception
	{
		List<Object[]> driverlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_printlist(:formdate,:todate)");
			query.setParameter("formdate", fromDate);
			query.setParameter("todate", toDate);
			driverlist=(List<Object[]>)query.getResultList();
			return driverlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO PrintList() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Object[]> getPrintData(String EmpId, int TripId) throws Exception
	{
		List<Object[]> list=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_print(:empid,:tripid)");
			query.setParameter("empid", EmpId);
			query.setParameter("tripid", TripId);
			list=(List<Object[]>)query.getResultList();
			return list;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getPrintData() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Object[]> getEmployeeList() throws Exception {
		List<Object[]> driverlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_employeelist()");
			driverlist = (List<Object[]>)query.getResultList();			
			return driverlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO getEmployeeList() "+ e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public int AddDriver(MtDriver driver) throws Exception {

		try {
			manager.persist(driver);
			manager.flush();
			return (int)driver.getDriverId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddDriver() "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	private static final String INACTIVEDRIVER="UPDATE mt_driver SET ISACTIVE=:isactive , modifiedby=:modifiedby ,modifieddate=:modifieddate WHERE driverId=:driverid";

	@Override
	public int InActiveDriver(MtDriver driver) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(INACTIVEDRIVER);
			query.setParameter("modifiedby", driver.getModifiedby());
			query.setParameter("modifieddate", driver.getModifiedDate());
			query.setParameter("isactive", driver.getIsActive());
			query.setParameter("driverid", driver.getDriverId());
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO InActiveDriver() "+ e);
			e.printStackTrace();
			return  0;
		}
	}
	private static final String VEHICLE="FROM MtVehicle WHERE VehicleId=:vehicleid";
	@Override
	public MtVehicle GetVehicleData(int vehicleid) throws Exception 
	{
		try {			
			return manager.find(MtVehicle.class, vehicleid);	
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetVehicleData() "+ e);
			e.printStackTrace();
			return null;
		}	
	}
	
	private static final String DELETE_VEHICLE="UPDATE mt_vehicle SET ISACTIVE=:isActive,MODIFIEDBY=:modifiedby,MODIFIEDDATE=:modifieddate WHERE VEHICLEID=:vehicleid";

	@Override
	public int DeleteVehicle(MtVehicle vehicle) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(DELETE_VEHICLE);
			query.setParameter("isActive", 0);
			query.setParameter("vehicleid", vehicle.getVehicleId());
			query.setParameter("modifiedby",vehicle.getModifiedby());
			query.setParameter("modifieddate", vehicle.getModifiedDate());
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO DeleteVehicle() "+ e);
			e.printStackTrace();
			return  0;
		}
	}
	

	@Override
	public int AddVehicle(MtVehicle vehicle)throws Exception{

		try {
			manager.persist(vehicle);
			manager.flush();
			return (int)vehicle.getVehicleId();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO AddVehicle() "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	private static final String EDITVEHICLE="UPDATE mt_vehicle SET NoOfSeat=:noofseat , DateOfPurchase=:dateofpurchase ,BaNo=:bano , VehicleName=:vehiclename,MODIFIEDBY=:modifiedby,MODIFIEDDATE=:modifieddate WHERE VEHICLEID=:vehicleid";
	@Override
	public int EditVehicle(MtVehicle vehicle) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(EDITVEHICLE);
			query.setParameter("noofseat", vehicle.getNoOfSeat());
			query.setParameter("dateofpurchase", vehicle.getDateOfPurchase());
			query.setParameter("bano", vehicle.getBaNo());
			query.setParameter("vehiclename", vehicle.getVehicleName());
			query.setParameter("vehicleid", vehicle.getVehicleId());
			query.setParameter("modifiedby",vehicle.getModifiedby());
			query.setParameter("modifieddate", vehicle.getModifiedDate());
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditVehicle() "+ e);
			e.printStackTrace();
			return  0;
		}
	}
	
	@Override
	public List<LabMaster> GetLabDetails()throws Exception
	{
		try {			
				Query query= manager.createQuery("FROM LabMaster");
				List<LabMaster> labdetails=(List<LabMaster>)query.getResultList();
			return labdetails;
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetLabDetails() "+ e);
			e.printStackTrace();
			return null;
		}	
	}

	@Override
	public List<Object[]> DateWiseProjectReport(Date FromDate, Date ToDate, int DriverId, int VehicleId) throws Exception {
		List<Object[]> reportlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_datewisereport(:FromDate,:ToDate,:DriverId,:VehicleId)");
			query.setParameter("FromDate", FromDate);
			query.setParameter("ToDate", ToDate);
			query.setParameter("DriverId", DriverId);
			query.setParameter("VehicleId", VehicleId);
			
			reportlist = (List<Object[]>)query.getResultList();			
			return reportlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO DateWiseProjectReport() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMPDESIG="SELECT a.empname , b.designation , a.desigid FROM employee a , employee_desig b WHERE a.desigid = b.desigid AND a.empid=:Empid";
	@Override
	public Object[] getEmpDesig(Long Empid)throws Exception
	{
		Query query= manager.createNativeQuery(EMPDESIG);
		query.setParameter("Empid", Empid);
		
		  List<Object[]> list  =(List<Object[]> )query.getResultList();
			
			if(list.size()>0) {
				return list.get(0);				
			}
			return null;
	}
	
	private static final String MTOEMP="SELECT a.empid FROM employee a , leave_sa_ra b WHERE a.empno=b.empid AND b.mt_status='M'";
	@Override
	public List<BigInteger> GetMtoEmployeeList()throws Exception
	{
		Query query= manager.createNativeQuery(MTOEMP);
		List<BigInteger> list  =(List<BigInteger> )query.getResultList();
		return list;
	}
	
	
	
	private static final String UNLINKREQ="DELETE MtLinkDuty WHERE LINKDUTYID=:linkdutyid";
	private static final String USERAPPLY="UPDATE MtUserApply SET MTSTATUS=:mtstatus,MODIFIEDBY=:modifiedby,MODIFIEDDATE=:modifieddate WHERE MTAPPLID=:mtapplid";
	
	@Override 
	public int UnLinkRequest(MtLinkDuty duty) throws Exception 
	{
		Query query=manager.createQuery(UNLINKREQ);
		Query query1=manager.createQuery(USERAPPLY);
		query1.setParameter("mtstatus","U");
		query1.setParameter("mtapplid",duty.getMtApplId());
		query1.setParameter("modifiedby",duty.getCreatedBy());
		query1.setParameter("modifieddate", duty.getCreatedDate());
		int count1=query1.executeUpdate();
		//query.setParameter("isactive", 0);
		query.setParameter("linkdutyid", duty.getLinkDutyId());
		//query.setParameter("modifiedby",duty.getCreatedBy());
		//query.setParameter("modifieddate",  duty.getCreatedDate());
		int count=query.executeUpdate();

		return count;
	}
	
	@Override
	public List<Object[]> TripList(Date FromDate, Date ToDate) throws Exception {
		List<Object[]> reportlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_triplist_mto(:FromDate,:ToDate)");
			query.setParameter("FromDate", FromDate);
			query.setParameter("ToDate", ToDate);
			reportlist = (List<Object[]>)query.getResultList();			
			return reportlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO TripList() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public List<Object[]> DirectorTripList(Date FromDate, Date ToDate) throws Exception {
		List<Object[]> reportlist=null;
		
		try {
			Query query=manager.createNativeQuery("CALL mt_director_triplist(:FormDate,:ToDate)");
			query.setParameter("FormDate", FromDate);
			query.setParameter("ToDate", ToDate);
			reportlist =(List<Object[]>)query.getResultList();		
			return reportlist;
	
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO DirectorTripList() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public int DirectorTripAssign(MtDirectorDuty Dduty) throws Exception
	{
		try {
			
			manager.persist(Dduty);
			manager.flush();
			return Dduty.getTripId();
			
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO DirectorTripAssign() "+ e);
			e.printStackTrace();
			return 0;
		}
	}
	
	 private  static final  String DELETEMTREQ="UPDATE mt_appl set IsActive=:ISACTIVE WHERE MtReqNo=:mtreqno ";
	@Override
	public int MtAdminReqDelete(String tripid)throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(DELETEMTREQ);
			query.setParameter("ISACTIVE", 0);
			query.setParameter("mtreqno", tripid);
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO MtAdminReqDelete() "+ e);
			e.printStackTrace();
			return  0;
		}
		
	}
	
	private static final String REQUESTEDIT="SELECT mtreqno ,dateoftravel, starttime, endtime, source, destination,enddateoftravel FROM  mt_appl WHERE mtreqno=:tripid";

	@Override
	public Object[] MtAdminReqEdit(String tripid) throws Exception 
	{
		Object[] list=null;
		
		try {
			Query query=manager.createNativeQuery(REQUESTEDIT);
			query.setParameter("tripid", tripid);
			List<Object[]> reportlist =(List<Object[]>)query.getResultList();		
			if( reportlist!=null && reportlist.size()>0) {
				list=reportlist.get(0);
			}
	
			return list;
		}catch (Exception e){
			logger.error(new Date() + "Inside DAO MtAdminReqEdit() "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String MTADMINEDIT="UPDATE mt_appl set DateOfTravel=:date, StartTime=:starttime,EndTime=:endtime,Source=:source,Destination=:destination,ModifiedBy=:modifiedby,ModifiedDate=:modifieddate,EndDateOfTravel=:Edate WHERE MtReqNo=:mtreqno ";

	
	@Override
	public int MtAdminReqEdit(MtUserApply apply) throws Exception
	{
		try {
			Query query=manager.createNativeQuery(MTADMINEDIT);
			query.setParameter("starttime", apply.getStartTime());
			query.setParameter("endtime", apply.getEndTime());
			query.setParameter("source", apply.getSource());
			query.setParameter("destination", apply.getDestination());
			query.setParameter("mtreqno", apply.getMtReqNo());
			query.setParameter("date",apply.getDateOfTravel());
			query.setParameter("Edate",apply.getEndDateOfTravel());
			query.setParameter("modifiedby", apply.getModifiedBy());
			query.setParameter("modifieddate", apply.getModifiedDate());
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO MtAdminReqEdit() "+ e);
			e.printStackTrace();
			return  0;
		}
		
	}
}

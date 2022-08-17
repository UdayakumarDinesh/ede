package com.vts.ems.Mt.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.Mt.dao.MtDao;
import com.vts.ems.Mt.model.MtApplyTransaction;
import com.vts.ems.Mt.model.MtDirectorDuty;
import com.vts.ems.Mt.model.MtDriver;
import com.vts.ems.Mt.model.MtLinkDuty;
import com.vts.ems.Mt.model.MtTrip;
import com.vts.ems.Mt.model.MtUserApply;
import com.vts.ems.Mt.model.MtVehicle;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class MtServiceImpl implements MtService {

	private static final Logger logger = LogManager.getLogger(MtServiceImpl.class);
	DateTimeFormatUtil sdf=new DateTimeFormatUtil();
	
	@Autowired
	private MtDao dao;
	
	
	
	@Override
	public List<Object[]> GetDutyType() throws Exception 
	{		
		return dao.GetDutyType();
	}
	@Override
	public List<Object[]> GetProjectList()throws Exception 
	{
		return dao.GetProjectList();
	}	
	@Override
	public String GetLastReqNo() throws Exception 
	{
		return dao.GetLastReqNo();
	}
	
	@Override
	public int UserApply(MtUserApply user) throws Exception 
	{
		return dao.UserApply(user);
	}
	
	@Override
	public int UserApplyEdit(MtUserApply user) throws Exception {
		
		MtUserApply userapply = dao.GetMtUserApply(user.getMtApplId());
		userapply.setUserRemarks(user.getUserRemarks());
		userapply.setDestination(user.getDestination());
		userapply.setStartTime( user.getStartTime());
		userapply.setEndTime(user.getEndTime());
		userapply.setSource(user.getSource());
		userapply.setOneWayDistance(user.getOneWayDistance());
		userapply.setDateOfTravel(user.getDateOfTravel());		
		userapply.setDutyTypeId(user.getDutyTypeId());
		userapply.setMtApplId(user.getMtApplId());		
		userapply.setMtReason(user.getMtReason());
		userapply.setContactNo(user.getContactNo()); 
		userapply.setMtStatus(user.getMtStatus());
		userapply.setModifiedBy(user.getModifiedBy());
		userapply.setModifiedDate(user.getModifiedDate());
		userapply.setProjectId(user.getProjectId());
		
		return dao.UserApplyEdit(userapply);
	
	}
	
	@Override
	public int MtApplyTranscation(MtApplyTransaction mttra) throws Exception 
	{	
		return dao.MtApplyTranscation(mttra);
	}
	
	@Override
	public List<Object[]> GetApplyDataOFApplyStatus(String EmpNo) throws Exception {	
		return dao.GetApplyDataOFApplyStatus(EmpNo);
	}
	
	@Override
	public List<Object[]> GetApplyDataOfSancApplyStatus(String EmpNo) throws Exception {	
		return dao.GetApplyDataOfSancApplyStatus(EmpNo);
	}
	
	@Override
	public List<Object[]> VehiclePendingListDashBoard() throws Exception {
		
		return dao.VehiclePendingListDashBoard();
	}
	
	@Override
	public List<Object[]> VehicleAssignedListDashBoard() throws Exception {
		
		return dao.VehicleAssignedListDashBoard();
	}
	
	@Override
	public MtUserApply getApplySingleData(int MtApplId) throws Exception {
	
		return dao.getApplySingleData(MtApplId);
	}
	
	@Override
	public Object[] getEmpData(Long EmpId) throws Exception {
	
		return dao.getEmpData(EmpId);
	}
	
	@Override
	public int UserApplyCancel(MtUserApply apply) throws Exception {
		
		return dao.UserApplyCancel(apply);
	}
	
	@Override
	public List<Object[]> getApplyList(String EmpId) throws Exception {
		
		return dao.getApplyList(EmpId);
	}
	
	@Override
	public List<Object[]> getDriverList() throws Exception{
		
		return dao.getDriverList();
	}
	
	@Override
	public List<MtVehicle> GetVechileList() throws Exception {
	
		return dao.GetVechileList();
	}
	
	@Override
	public List<Object[]> getListOfTrip() throws Exception {
	
		return dao.getListOfTrip();
	}
	
	@Override
	public List<Object[]> GetLinkRequest() throws Exception {
	
		return dao.GetLinkRequest();
	}
	
	@Override
	public String getLastTripNo() throws Exception {
		
		return dao.getLastTripNo();
	}
	@Override
	public int Inserttrip(MtTrip trip) throws Exception {
		
		return dao.Inserttrip(trip);
	}
	
	@Override
	public String GetRecOfficer(String empid) throws Exception {
			return dao.GetRecOfficer(empid);
	}
	
	@Override
	public Long MtEmsNotification(EMSNotification notification) throws Exception 
	{	
		return dao.MtEmsNotification(notification);
	}
	
	@Override
	public List<Object[]> GhApproveList(String EmpId) throws Exception {
		
		return dao.GhApproveList(EmpId);
	}
	
	@Override
	public String EmpIdOfMtRequest(int MtApplId) throws Exception {
		
		return dao.EmpIdOfMtRequest(MtApplId);
	}
	
	@Override
	public int StatusUpdate(MtUserApply apply) throws Exception {
	
		return dao.StatusUpdate(apply);
	}
	@Override
	public MtTrip GetTrip(int TripId) throws Exception {
		
		return dao.GetTrip(TripId);
	}
	@Override
	public int DeleteTrip(int tripid) throws Exception {
	
		return dao.DeleteTrip(tripid);
	}
	
	@Override
	public int EditTrip(MtTrip trip) throws Exception {
		
		MtTrip edittrip= dao.GetTrip(trip.getTripId());
		edittrip.setDriverId(trip.getDriverId());
		edittrip.setEndTime(trip.getEndTime());
		edittrip.setHiredVehicle(trip.getHiredVehicle());
		edittrip.setMtoComments(trip.getMtoComments());
		edittrip.setPlace(trip.getPlace());
		edittrip.setStartTime(trip.getStartTime());
		edittrip.setTripDate(trip.getTripDate());
		edittrip.setTripEndDate(trip.getTripEndDate());
		edittrip.setTripId(trip.getTripId());
		edittrip.setTripNo(trip.getTripNo());
		edittrip.setVehicleId(trip.getVehicleId());
		edittrip.setModifiedby(trip.getModifiedby());
		edittrip.setModifiedDate(trip.getModifiedDate());
		return dao.EditTrip(edittrip);
	}
	
	@Override
	public int insertTriplink(MtLinkDuty linkduty) throws Exception {
		
		return dao.insertTriplink(linkduty);
	}
	
	@Override
	public List<Object[]> PrintList(String fromDate, String toDate) throws Exception
	{
		return dao.PrintList(fromDate, toDate);
	}
	@Override
	public List<Object[]> getPrintData(String EmpId, int TripId) throws Exception {
		
		return dao.getPrintData(EmpId, TripId);
	}
	@Override
	public List<Object[]> getEmployeeList() throws Exception {
		
		return dao.getEmployeeList();
	}
	
	@Override
	public int AddDriver(MtDriver driver) throws Exception {
		
		return dao.AddDriver(driver);
	}
	
	@Override
    public int InActiveDriver(MtDriver driver) throws Exception {
		
		return dao.InActiveDriver(driver);
	}

	@Override
	public MtVehicle GetVehicleData(int vehicleid) throws Exception {
		
		return dao.GetVehicleData(vehicleid);
	}
	@Override
	public int DeleteVehicle(MtVehicle vehicle) throws Exception {
		
		return dao.DeleteVehicle(vehicle);
	}
	@Override
	public int AddVehicle(MtVehicle vehicle) throws Exception {
		
		return dao.AddVehicle(vehicle);
	}
	
	@Override
	public int EditVehicle(MtVehicle vehicle) throws Exception {
		
		return dao.EditVehicle(vehicle);
	}
	
	@Override
	public List<LabMaster> GetLabDetails()throws Exception
	{
		return dao.GetLabDetails();
	}
	@Override
	public List<Object[]> DateWiseProjectReport(Date FromDate, Date ToDate, int DriverId, int VehicleId)
			throws Exception {
		
		return dao.DateWiseProjectReport(FromDate, ToDate, DriverId, VehicleId);
	}
	
	@Override
	public Object[] getEmpDesig(Long Empid)throws Exception
	{
		return dao.getEmpDesig(Empid);
	}
	
	@Override
	public List<BigInteger> GetMtoEmployeeList()throws Exception
	{
		return dao.GetMtoEmployeeList();
	}
	
	@Override
	public int UnLinkRequest(MtLinkDuty duty) throws Exception {
		
		return dao.UnLinkRequest(duty);
	}
	
	@Override
	public List<Object[]> TripList(Date FromDate, Date ToDate) throws Exception {
	
		return dao.TripList(FromDate, ToDate);
	}
	
	@Override
	public List<Object[]> DirectorTripList(Date FromDate,Date ToDate) throws Exception {
		
		return dao.DirectorTripList(FromDate,ToDate);
	}
	@Override
	public int DirectorTripAssign(MtDirectorDuty Dduty) throws Exception {
		
		return dao.DirectorTripAssign(Dduty);
	}
}

package com.vts.ems.Mt.service;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;

import com.vts.ems.Admin.model.LabMaster;
import com.vts.ems.Mt.model.MtApplyTransaction;
import com.vts.ems.Mt.model.MtDirectorDuty;
import com.vts.ems.Mt.model.MtDriver;
import com.vts.ems.Mt.model.MtLinkDuty;
import com.vts.ems.Mt.model.MtTrip;
import com.vts.ems.Mt.model.MtUserApply;
import com.vts.ems.Mt.model.MtVehicle;
import com.vts.ems.model.EMSNotification;

public interface MtService {

	
	public List<Object[]> GetDutyType() throws Exception;
	public List<Object[]> GetProjectList()throws Exception;
	public String GetLastReqNo() throws Exception;
	public int UserApply(MtUserApply user) throws Exception;
	public int MtApplyTranscation(MtApplyTransaction mttra) throws Exception;
	public int UserApplyEdit(MtUserApply user) throws Exception;
	public List<Object[]> GetApplyDataOFApplyStatus(String EmpNo) throws Exception;
	public List<Object[]> GetApplyDataOfSancApplyStatus(String EmpNo) throws Exception;
	public List<Object[]> VehiclePendingListDashBoard() throws Exception;
	public List<Object[]> VehicleAssignedListDashBoard() throws Exception;
	public MtUserApply getApplySingleData(int MtApplId) throws Exception;
	public Object[] getEmpData(String EmpNo) throws Exception;
	public int UserApplyCancel(MtUserApply apply) throws Exception;
	public List<Object[]> getApplyList(String EmpId)throws Exception;
	public List<Object[]> getDriverList() throws Exception;
	public List<MtVehicle> GetVechileList() throws Exception;
	public List<Object[]> getListOfTrip() throws Exception;
	public List<Object[]> GetLinkRequest() throws Exception;
	public String getLastTripNo() throws Exception ;
	public int Inserttrip(MtTrip trip) throws Exception;
	public String GetRecOfficer(String empid) throws Exception ;
	public Long MtEmsNotification(EMSNotification notification) throws Exception ;
	public List<Object[]> GhApproveList(String EmpId) throws Exception;
	public String EmpIdOfMtRequest(int MtApplId) throws Exception ;
	public int StatusUpdate(MtUserApply apply) throws Exception;
	public MtTrip GetTrip(int TripId) throws Exception;
	public int DeleteTrip(int tripid) throws Exception;
	public int EditTrip(MtTrip trip) throws Exception;
	public int insertTriplink(MtLinkDuty linkduty) throws Exception;
	public List<Object[]> PrintList(String fromDate, String toDate) throws Exception;
	public List<Object[]> getPrintData(String EmpId, int TripId) throws Exception;
	public List<Object[]> getEmployeeList() throws Exception ;
	public int AddDriver(MtDriver driver) throws Exception ;
	public int InActiveDriver(MtDriver driver) throws Exception ;
	public MtVehicle GetVehicleData(int vehicleid) throws Exception;
	public int DeleteVehicle(MtVehicle vehicle) throws Exception;
	public int AddVehicle(MtVehicle vehicle)throws Exception  ;
	public int EditVehicle(MtVehicle vehicle) throws Exception;
	public List<LabMaster> GetLabDetails()throws Exception;
	public List<Object[]> DateWiseProjectReport(Date FromDate, Date ToDate, int DriverId, int VehicleId)throws Exception; 
	public Object[] getEmpDesig(Long Empid)throws Exception;
	public List<BigInteger> GetMtoEmployeeList()throws Exception;
	public int UnLinkRequest(MtLinkDuty duty) throws Exception;
	public List<Object[]> TripList(Date FromDate, Date ToDate) throws Exception;
	public List<Object[]> DirectorTripList(Date FromDate,Date ToDate) throws Exception;
	public int DirectorTripAssign(MtDirectorDuty Dduty) throws Exception;
	public int MtAdminReqDelete(String tripid)throws Exception;
	public Object[] MtAdminReqEdit(String tripid) throws Exception ;
	public int MtAdminReqEdit(MtUserApply apply) throws Exception ;
}

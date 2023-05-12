package com.vts.ems.vehicleparking.service;

import java.util.List;

import com.vts.ems.vehicleparking.model.VehicleParkingApplications;

public interface VehicleParkingService {
	public Object[] empNameAndDesi(String empNo) throws Exception;
	public long addVehiclePark(String empNo, String vehicleNo, String fromDateAndTime, String toDateAndTime, String Username) throws Exception;
	public List<Object[]> findVehicleParkList() throws Exception ;
	public List<Object[]> findVehicleParkListByEmpNO(String empNo) throws Exception;
	public Object[] findByVehicleId(long appId) throws Exception ;
	public long addVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception;
	public long editVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception;
	public VehicleParkingApplications findVehicleById(long appId) throws Exception;
	public List<Object[]> getSecurityVehicleParkList(String userName) throws Exception ;
	public long forwardVehicle(long vehicleAppId, String empNo, String remarks, String userName, String Action) throws Exception;
	
}

package com.vts.ems.vehicleparking.dao;

import java.util.List;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.vehicleparking.model.VehicleParkingApplications;
import com.vts.ems.vehicleparking.model.VehicleParkingTransa;

public interface VehicleParkingDao {

	public Object[] empNameAndDesi(String empNo) throws Exception ;
	public long addVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception;
	public List<Object[]> findVehicleParkList() throws Exception ;
	public List<Object[]> findVehicleParkListByEmpNO(String empNo) throws Exception ;
	public Object[] findByVehicleId(long appId) throws Exception ;
	public long editVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception ;
	public VehicleParkingApplications findVehicleById(long appId) throws Exception ;
	public List<Object[]> getSecurityVehicleParkList(String userName) throws Exception ;
	public Employee findEmpByEmpNo(String empNo) throws Exception ;
	public List<String> securityOfficers() throws Exception;
	public long addNotification(EMSNotification notification) throws Exception;
	public long addVehicleParkingTransa(VehicleParkingTransa VehicleParkingTransa) throws Exception;
	
}

package com.vts.ems.vehicleparking.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;
import com.vts.ems.vehicleparking.dao.VehicleParkingDao;
import com.vts.ems.vehicleparking.model.VehicleParkingApplications;
import com.vts.ems.vehicleparking.model.VehicleParkingTransa;


@Service
public class VehicleParkingServicImpl implements VehicleParkingService{

	private static final Logger logger = LogManager.getLogger(VehicleParkingServicImpl.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();

	@Autowired
	private VehicleParkingDao dao;


	public Object[] empNameAndDesi(String empNo) throws Exception{
		return dao.empNameAndDesi(empNo);
	}


	public long addVehiclePark(String empNo, String vehicleNo, String fromDateAndTime, String toDateAndTime, String Username) throws Exception{
		try {
			VehicleParkingApplications vehicleParkingApplications=new VehicleParkingApplications();
			vehicleParkingApplications.setEmpNo(empNo);
			vehicleParkingApplications.setVehicleNo(vehicleNo);
			vehicleParkingApplications.setFromDateAndTime(fromDateAndTime);
			vehicleParkingApplications.setToDateAndTime(toDateAndTime);
			vehicleParkingApplications.setCreatedBy(Username);
			vehicleParkingApplications.setCreatedDate(sdtf.format(new Date()));
			vehicleParkingApplications.setIsActive(1);
			dao.addVehiclePark(vehicleParkingApplications);

			return vehicleParkingApplications.getAppId();
		} catch (Exception e) {
			logger.error(new Date() +" Inside addVehiclePark "+ e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<Object[]> findVehicleParkList() throws Exception{
		return dao.findVehicleParkList();
	}

	@Override
	public List<Object[]> findVehicleParkListByEmpNO(String empNo) throws Exception{
		return dao.findVehicleParkListByEmpNO(empNo);
	}

	@Override
	public Object[] findByVehicleId(long appId) throws Exception {
		return dao.findByVehicleId(appId);
	}

	@Override
	public long addVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception{
		return dao.addVehiclePark(vehicleParkingApplication);
	}

	@Override
	public long editVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception{
		return dao.editVehiclePark(vehicleParkingApplication);
	}

	@Override
	public VehicleParkingApplications findVehicleById(long appId) throws Exception{
		return dao.findVehicleById(appId);
	}

	@Override
	public List<Object[]> getSecurityVehicleParkList(String userName) throws Exception {
		return dao.getSecurityVehicleParkList(userName);
	}

	@Override
	public long forwardVehicle(long vehicleAppId, String empNo, String remarks, String userName, String Action) throws Exception{
		long l=0;
		try {
			Employee emp=dao.findEmpByEmpNo(empNo);
			List<String> SOS=dao.securityOfficers();
			VehicleParkingApplications vehicleParkingApplications=dao.findVehicleById(vehicleAppId);
			VehicleParkingTransa VehicleParkingTransa=new VehicleParkingTransa();
			EMSNotification notification = new EMSNotification();


			if(Action.equals("FWD")) {
				if(SOS.contains(empNo)) {
					vehicleParkingApplications.setApplicStatus("A");
					vehicleParkingApplications.setApplicStatusCode("APR");
					
					VehicleParkingTransa.setApplicStatusCode("APR");
					VehicleParkingTransa.setActionBy(empNo);
					VehicleParkingTransa.setRemarks(remarks);
				} else {
					vehicleParkingApplications.setApplicStatus("N");
					vehicleParkingApplications.setApplicStatusCode("FWD");

					VehicleParkingTransa.setApplicStatusCode("FWD");
					VehicleParkingTransa.setActionBy(empNo);
					VehicleParkingTransa.setRemarks(remarks);

					notification.setEmpNo(SOS.get(0));
					notification.setNotificationUrl("VehicleParkinglApproval.htm");
					notification.setNotificationMessage("Recieved Vehicle Parking Request From <br>"+emp.getEmpName());
				}

			}else if(Action.equals("R")) {
				vehicleParkingApplications.setApplicStatus("N");
				vehicleParkingApplications.setApplicStatusCode("RSO");

				VehicleParkingTransa.setApplicStatusCode("RSO");
				VehicleParkingTransa.setActionBy(empNo);
				VehicleParkingTransa.setRemarks(remarks);

				notification.setEmpNo(vehicleParkingApplications.getEmpNo());
				notification.setNotificationUrl("VehicleParking.htm");
				notification.setNotificationMessage("Vehicle Parking Request Returned");

			}else if(Action.equals("A")) {	
				vehicleParkingApplications.setApplicStatus("A");
				vehicleParkingApplications.setApplicStatusCode("APR");

				VehicleParkingTransa.setApplicStatusCode("APR");
				VehicleParkingTransa.setActionBy(empNo);
				VehicleParkingTransa.setRemarks(remarks);

				notification.setEmpNo(vehicleParkingApplications.getEmpNo());
				notification.setNotificationUrl("VehicleParking.htm");
				notification.setNotificationMessage("Vehicle Parking Request Approved");

			}

			vehicleParkingApplications.setRemarks(remarks);
			dao.editVehiclePark(vehicleParkingApplications);

			VehicleParkingTransa.setVeh_Park_App_id(vehicleParkingApplications);
			VehicleParkingTransa.setActionDate(sdtf.format(new Date()));
			dao.addVehicleParkingTransa(VehicleParkingTransa);

			notification.setNotificationBy(empNo);
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(userName);
			notification.setCreatedDate(sdtf.format(new Date()));
			dao.addNotification(notification);

			l=vehicleParkingApplications.getAppId();
		} catch (Exception e) {
			logger.error(new Date() +" Inside forwardVehicle "+ e);
			e.printStackTrace();
			return 0;
		}
		return l;
	}

	@Override
	public Object[] getEmpNameAndDesi(String empNo) throws Exception{
		return dao.empNameAndDesi(empNo);
	}

	@Override
	public List<Object[]> getSecVehicleParkApprdList(String userName) throws Exception {
		return dao.getSecVehicleParkApprdList(userName);
	}
	
	@Override
	public List<Object[]> vehTransaById(long vehAppId) throws Exception{
		return dao.vehTransaById(vehAppId);
	}
}

package com.vts.ems.vehicleparking.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.vehicleparking.model.VehicleParkingApplications;
import com.vts.ems.vehicleparking.model.VehicleParkingTransa;


@Repository
@Transactional
public class VehicleParkingDaoImpl implements VehicleParkingDao{

	private static final Logger logger = LogManager.getLogger(VehicleParkingDaoImpl.class);

	@PersistenceContext
	private EntityManager manager;

	public static final String EMPNAMEANDDESIGN="SELECT em.EmpName, des.Designation FROM employee em, employee_desig des WHERE em.DesigId=des.DesigId AND em.EmpNo=:empNo";

	@Override
	public Object[] empNameAndDesi(String empNo) throws Exception{
		Object[] emp=null;
		try {
			Query query = manager.createNativeQuery(EMPNAMEANDDESIGN);
			query.setParameter("empNo", empNo);
			emp =(Object[]) query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO empNameAndDesi "+  e);
			e.printStackTrace();
		}
		return emp;
	}


	@Override
	public long addVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception {

		try {
			manager.persist(vehicleParkingApplication);
			manager.flush();
			return vehicleParkingApplication.getAppId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addVehiclePark "+  e);
			e.printStackTrace();
			return 0;
		}

	}


	public static final String FINDVEHICLEPARKLIST="SELECT a.appId, a.EmpNo, em.EmpName  , a.VehicleNo, a.FromDateAndTime, a.ToDateAndTime, a.ApplicStatus, a.ApplicStatusCode, a.Remarks FROM vehicle_park_appli a, employee em WHERE a.EmpNo=em.EmpNo ORDER BY a.appId DESC";

	@Override
	public List<Object[]> findVehicleParkList() throws Exception {
		List<Object[]> list=null;
		try {
			Query query=manager.createNativeQuery(FINDVEHICLEPARKLIST);
			list= query.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findVehicleParkList "+  e);
			e.printStackTrace();
		}
		return list;
	}

	public static final String FINDVEHICLEPARKLISTBYEMPNO="SELECT a.appId, a.EmpNo, em.EmpName  , a.VehicleNo, a.FromDateAndTime, a.ToDateAndTime, a.ApplicStatus, a.ApplicStatusCode, a.Remarks, b.VehStatus  FROM vehicle_park_appli a, employee em, veh_park_approval_status b\r\n"
			+ "WHERE a.EmpNo=em.EmpNo AND a.ApplicStatusCode=b.VehStatusCode AND a.EmpNo=:empNo ORDER BY a.appId DESC";

	@Override
	public List<Object[]> findVehicleParkListByEmpNO(String empNo) throws Exception {
		List<Object[]> list=null;
		try {
			Query query=manager.createNativeQuery(FINDVEHICLEPARKLISTBYEMPNO);
			query.setParameter("empNo", empNo);
			list= query.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findVehicleParkListByEmpNO "+  e);
			e.printStackTrace();
		}
		return list;
	}

	public static final String FINDBYID="SELECT a.appId, a.EmpNo, em.EmpName  , a.VehicleNo, a.FromDateAndTime, a.ToDateAndTime, a.ApplicStatus, des.Designation , a.ApplicStatusCode, a.Remarks FROM vehicle_park_appli a, employee em, employee_desig des WHERE a.EmpNo=em.EmpNo AND em.DesigId=des.DesigId AND a.appId=:appId ";

	@Override
	public Object[] findByVehicleId(long appId) throws Exception {
		Object[] list=null;
		try {
			Query query=manager.createNativeQuery(FINDBYID);
			query.setParameter("appId", appId);
			list= (Object[]) query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findByVehicleId "+  e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public long editVehiclePark(VehicleParkingApplications vehicleParkingApplication) throws Exception {

		try {
			manager.merge(vehicleParkingApplication);
			manager.flush();
			return vehicleParkingApplication.getAppId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO editVehiclePark "+  e);
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public VehicleParkingApplications findVehicleById(long appId) throws Exception {
		VehicleParkingApplications vehicleParkingApplications=null;
		try {
			vehicleParkingApplications= manager.find(VehicleParkingApplications.class, appId);
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findVehicleById "+  e);
			e.printStackTrace();

		}
		return vehicleParkingApplications;
	}

	public static final String GETSECURITYVEHICLEPARKLIST="SELECT a.appId, a.EmpNo, em.EmpName  , a.VehicleNo, a.FromDateAndTime, a.ToDateAndTime, a.ApplicStatus, a.ApplicStatusCode, a.Remarks FROM vehicle_park_appli a, employee em, login b WHERE a.EmpNo=em.EmpNo AND a.ApplicStatusCode='FWD' AND b.UserName=:userName AND b.LoginType='S' ORDER BY a.appId DESC";

	@Override
	public List<Object[]> getSecurityVehicleParkList(String userName) throws Exception {
		List<Object[]> list=null;
		try {
			Query query=manager.createNativeQuery(GETSECURITYVEHICLEPARKLIST);
			query.setParameter("userName", userName);
			list= query.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO getSecurityVehicleParkList "+  e);
			e.printStackTrace();
		}
		return list;
	}

	private static final String FINDEMPBYEMPNO  ="FROM Employee WHERE EmpNo=:EmpNo";
	@Override
	public Employee findEmpByEmpNo(String empNo) throws Exception{
		Employee employee=null;
		try {			
			Query query= manager.createQuery(FINDEMPBYEMPNO);
			query.setParameter("EmpNo", empNo);
			employee= (Employee) query.getSingleResult();

		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO findEmpByEmpNo " + e);
			e.printStackTrace();

		}		
		return employee;
	}

	private static final String SECURITYOFFICERS="SELECT em.EmpNo FROM login a, employee em  WHERE a.EmpId = em.EmpId AND LoginType='S'";
	@Override
	public List<String> securityOfficers() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(SECURITYOFFICERS);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}
		catch (Exception e) 
		{
			logger.error(new Date()  + "Inside DAO securityOfficers " + e);
			e.printStackTrace();
			return null;
		}
	}

	public long addNotification(EMSNotification notification) throws Exception{
		try {
			manager.persist(notification);
			manager.flush();

			return notification.getNotificationId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addNotification "+e);
			e.printStackTrace();
		}
		return 0;
	}

	public long addVehicleParkingTransa(VehicleParkingTransa VehicleParkingTransa) throws Exception{
		try {
			manager.persist(VehicleParkingTransa);
			manager.flush();

			return VehicleParkingTransa.getVehParkkTransId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addVehicleParkingTransa "+e);
			e.printStackTrace();
		}
		return 0;
	}

	public static final String GETEMPNAMEANDDESI="SELECT em.EmpName, des.Designation FROM employee em, employee_desig des WHERE em.DesigId=des.DesigId AND em.EmpNo=:empNo";

	@Override
	public Object[] getEmpNameAndDesi(String empNo) throws Exception {
		Object[] list=null;
		try {
			Query query=manager.createNativeQuery(GETEMPNAMEANDDESI);
			query.setParameter("empNo", empNo);
			list= (Object[]) query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO getEmpNameAndDesi "+  e);
			e.printStackTrace();
		}
		return list;
	}

	public static final String GETSECVEHICLEPARKAPPRDLIST="SELECT a.appId, a.EmpNo, em.EmpName  , a.VehicleNo, a.FromDateAndTime, a.ToDateAndTime, a.ApplicStatus, a.ApplicStatusCode, a.Remarks FROM vehicle_park_appli a, employee em, login b WHERE a.EmpNo=em.EmpNo AND a.ApplicStatusCode='APR' AND b.UserName=:userName AND b.LoginType='S' ORDER BY a.appId DESC";

	@Override
	public List<Object[]> getSecVehicleParkApprdList(String userName) throws Exception  {
		List<Object[]> list=null;
		try {
			Query query=manager.createNativeQuery(GETSECVEHICLEPARKAPPRDLIST);
			query.setParameter("userName", userName);
			list= query.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO getSecVehicleParkApprdList "+  e);
			e.printStackTrace();
		}
		return list;

	}
	private static final String VEHTRANSABYID="SELECT tra.VehParkkTransId,emp.empno,emp.empname,des.designation,tra.ActionDate,tra.Remarks,sta.VehStatus,sta.VehStatusColor  \r\n"
			+ "FROM veh_park_transa tra, veh_park_approval_status sta,employee emp,employee_desig des,vehicle_park_appli veh\r\n"
			+ "WHERE veh.appId = tra.Veh_Park_App_id_appId AND tra.ApplicStatusCode = sta.VehStatusCode AND tra.ActionBy=emp.empno AND emp.desigid=des.desigid AND veh.appId =:vehAppId ORDER BY actiondate";
	@Override
	public List<Object[]> vehTransaById(long vehAppId) throws Exception
	{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery(VEHTRANSABYID);
			query.setParameter("vehAppId", vehAppId);
			bankDetailList= query.getResultList();
		}catch(Exception e) {
			logger.error(new Date() +"Inside DAO vehTransaById "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}

}

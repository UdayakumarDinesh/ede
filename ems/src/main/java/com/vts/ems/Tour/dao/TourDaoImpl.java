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
	private static final String GETAPPLYLIST="";
	@Override
	public List<Object[]> GetTourApplyList()throws Exception
	{
		Query query = manager.createNativeQuery(GETAPPLYLIST);
		return (List<Object[]>) query.getResultList();
	}

	
}

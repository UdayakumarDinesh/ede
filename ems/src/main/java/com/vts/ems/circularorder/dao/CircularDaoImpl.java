package com.vts.ems.circularorder.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class CircularDaoImpl implements CircularDao 
{
	private static final Logger logger = LogManager.getLogger(CircularDaoImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	
	@Autowired
	EntityManager manager;
	
	private static final String GETCIRCULARDATA ="from ems_circular where CircularId =:CircularId ";
	@Override
	public EMSCircular getCircularData(String CircularId) throws Exception
	{
		Query query=manager.createQuery(GETCIRCULARDATA);
		query.setParameter("CircularId", Long.parseLong(CircularId));				
		try {
			return (EMSCircular)query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private static final String GETEMPDATADATA ="from EmployeeDetails where EmpNo =:empNo ";
	@Override
	public EmployeeDetails getEmpdataData(String empNo) throws Exception
	{
		Query query=manager.createQuery(GETEMPDATADATA);
		query.setParameter("empNo", empNo);				
		try {
			return (EmployeeDetails)query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	private static final String SELECTALLLIST="SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject FROM ems_circular";

	@Override
	public List<Object[]> selectAllList() throws Exception {

		Query query = manager.createNativeQuery(SELECTALLLIST);
		List<Object[]> CircularList= query.getResultList();
		System.out.println(CircularList);
		return CircularList;
	}

	private static final String CIRCULARLIST = "SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject,CircularId FROM ems_circular WHERE ( CircularDate>=:fromdate AND CircularDate <=:todate )  ORDER BY CircularDate DESC";
	
	@Override
	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception {
	 
		System.out.println(fromdate);
		System.out.println(toDate);
		Query query =  manager.createNativeQuery(CIRCULARLIST);
		 query.setParameter("fromdate", fromdate);
		 query.setParameter("todate", toDate);
		 List<Object[]> CircularList=query.getResultList();
		 System.out.println(CircularList);
		 return CircularList;
		
	}


	@Override
	public long GetCircularMaxId() throws Exception {
		logger.info(new Date() +"Inside DAO GetCircularMaxId()");
		try {
			Query query = manager.createNativeQuery("SELECT MAX(CircularId)  FROM ems_circular");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long CircularAdd(EMSCircular circular) throws Exception {
		
		logger.info(new Date() + "Inside CircularAdd()");
		try {
			manager.persist(circular);
			manager.flush();
			return (long)circular.getCircularId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	
	
	
}

package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


import org.springframework.stereotype.Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vts.ems.chss.dao.CHSSDaoImpl;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.utils.DateTimeFormatUtil;



@Repository
@Transactional
public class CircularDaoImpl implements CircularDao {
	

	private static final Logger logger = LogManager.getLogger(CircularDaoImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	
	@PersistenceContext
    EntityManager manager;
	
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

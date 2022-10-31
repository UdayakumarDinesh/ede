package com.vts.ems.circularorder.dao;

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

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@PersistenceContext
	EntityManager manager;
	
	

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

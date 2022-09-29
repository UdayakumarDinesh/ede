package com.vts.ems.dopart.dao;

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

@Transactional
@Repository
public class DoPartDaoImpl implements DoPartDao {

	private static final Logger logger = LogManager.getLogger(DoPartDaoImpl.class);


	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<Object[]> GetDOList() throws Exception {
		
		logger.info(new Date() +"Inside DAO GetDOList()");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("call dop_edit();");		
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Object[]> RetriveContent(int DoNumber,String year)throws Exception
	{
		logger.info(new Date() +"Inside DAO GetApplyDataOfSancApplyStatus()");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("CALL dop_content(:DoPartNumber,:DoPartYear)");
			
			query.setParameter("DoPartNumber", DoNumber);
			query.setParameter("DoPartYear", year);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

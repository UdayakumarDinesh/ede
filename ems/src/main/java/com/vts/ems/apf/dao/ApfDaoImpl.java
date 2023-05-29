package com.vts.ems.apf.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Transactional
public class ApfDaoImpl implements ApfDao {
	
	private static final Logger logger = LogManager.getLogger(ApfDaoImpl.class);
	
	@PersistenceContext
	private EntityManager manager;
	
	public static final String ALLAPFAPPLY="SELECT a.APFApplyId, a.EmpNo, a.APFFromDate, a.APFToDate, a.ApfAmount FROM apf_apply a WHERE a.EmpNo=:empNo";
	
	@Override
	public List<Object[]> allAPFApply(String empNo) throws Exception{
		 List<Object[]> all=null;
		 try {
			 Query query = manager.createNativeQuery(ALLAPFAPPLY);
				query.setParameter("empNo", empNo);
				all = query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO allAPFApply "+  e);
			e.printStackTrace();
		}
		return all;
	}
	
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

}

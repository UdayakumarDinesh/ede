package com.vts.ems.circularorder.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
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
			logger.error(new Date() +"Inside getEmpdataData "+ e);
			e.printStackTrace();
			throw e;
		}
	}


	@Override
	public long GetDepCircularMaxId() throws Exception {
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(DepCircularId),0) AS 'count'  FROM ems_dep_circular");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside GetDepCircularMaxId "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception 
	{
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(DepCircularId),0) AS 'count'  FROM ems_dep_circular WHERE deptypeid=:DepTypeId");
			query.setParameter("DepTypeId", DepTypeId);
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside GetDepCircularMaxIdEdit "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public long DepCircularTransactionAdd(DepEMSCircularTrans cirTrans) throws Exception 
	{
		manager.persist(cirTrans);
		manager.flush();
		return (long)cirTrans.getDepCircularTransId();
	}

	private static final String DEPCIRCULARLIST = "SELECT DepCircularId,DepTypeId,DepCircularNo,DepCirSubject,DepCircularDate FROM ems_dep_circular WHERE IsActive=1 AND  DepTypeId=:DepTypeId AND ( DepCircularDate>=:fromdate AND DepCircularDate <=:todate )  ORDER BY CreatedDate DESC";
	@Override
	public List<Object[]> GetDepCircularList(String fromdate, String toDate,String DepTypeId) throws Exception 
	{
		Query query =  manager.createNativeQuery(DEPCIRCULARLIST);
		query.setParameter("DepTypeId", DepTypeId);
		query.setParameter("fromdate", fromdate);
		query.setParameter("todate", toDate);
		List<Object[]> CircularList=query.getResultList();
		return CircularList;
		
	}
	
	private static final String GETEMSDEPTYPE = "SELECT DepTypeId,DepName,DepShorrtName FROM ems_dep_type WHERE DepTypeId=:DepTypeId";
	@Override
	public Object[] GetEmsDepType(String DepTypeId) throws Exception 
	{
		try {
			Query query =  manager.createNativeQuery(GETEMSDEPTYPE);
			query.setParameter("DepTypeId", DepTypeId);
			Object[] GetEmsDepType=(Object[] )query.getSingleResult();
			return GetEmsDepType;
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside GetEmsDepType "+ e);
			return null;
		}
		
	}
	
	@Override
	public long EmsDepCircularAdd(EMSDepCircular circular) throws Exception 
	{
		manager.persist(circular);
		manager.flush();
		return circular.getDepCircularId();
		
	}
	
	@Override
	public long EmsDepCircularEdit(EMSDepCircular circular) throws Exception 
	{
		manager.merge(circular);
		manager.flush();
		return circular.getDepCircularId();
		
	}
	
	
	@Override
	public EMSDepCircular getEmsDepCircular(String circularId) throws Exception 
	{
		EMSDepCircular circular= manager.find(EMSDepCircular.class,Long.parseLong(circularId));
		return circular;
		
	}
	
	private static final String DEPCIRCULARSEARCHLIST="SELECT DepCircularId,DepCircularNo,DATE_FORMAT(DepCircularDate,'%d-%m-%Y'),DepCirSubject,DepTypeId FROM ems_dep_circular  WHERE IsActive=1 AND  (DepCircularNo LIKE :Search  OR DepCirsubject LIKE :Search  ) AND DepTypeId=:DepTypeId ";
	@Override
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception 
	{
		Query query = manager.createNativeQuery(DEPCIRCULARSEARCHLIST);
		query.setParameter("Search", "%"+search+"%");
		query.setParameter("DepTypeId", id);
		List<Object[]> SearchList= query.getResultList();
		return SearchList;
	}
	
	private static final String DEPCIRCULARSEARCHLISTALL="SELECT DepCircularId,DepCircularNo,DATE_FORMAT(DepCircularDate,'%d-%m-%Y'),DepCirSubject,DepTypeId FROM ems_dep_circular  WHERE IsActive=1 AND  (DepCircularNo LIKE :Search  OR DepCirsubject LIKE :Search  )";
	@Override
	public List<Object[]> DepCircularSearchList(String search) throws Exception 
	{
		Query query = manager.createNativeQuery(DEPCIRCULARSEARCHLISTALL);
		query.setParameter("Search", "%"+search+"%");
		List<Object[]> SearchList= query.getResultList();
		return SearchList;
	}
	
	
	private static final String GETEMSDEPTYPELIST = "SELECT DepTypeId,DepName,DepShorrtName FROM ems_dep_type WHERE IsActive=1";
	@Override
	public List<Object[]> GetEmsDepType() throws Exception 
	{
		try {
			Query query =  manager.createNativeQuery(GETEMSDEPTYPELIST);
			List<Object[]> GetEmsDepType=(List<Object[]>)query.getResultList();
			return GetEmsDepType;
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside GetEmsDepType "+ e);
			return new ArrayList<Object[]>();
		}
		
	}
}

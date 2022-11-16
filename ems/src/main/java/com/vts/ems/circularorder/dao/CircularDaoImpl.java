package com.vts.ems.circularorder.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
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
			logger.error(new Date() +"Inside DAO getCircularData "+ e);
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
			logger.error(new Date() +"Inside DAO getEmpdataData "+ e);
			e.printStackTrace();
			throw e;
		}
	}
	
	private static final String SELECTALLLIST="SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject FROM ems_circular";
	@Override
	public List<Object[]> selectAllList() throws Exception {

		Query query = manager.createNativeQuery(SELECTALLLIST);
		List<Object[]> CircularList= query.getResultList();
		return CircularList;
	}

	
	private static final String CIRCULARLIST = "SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject,CircularId FROM ems_circular WHERE IsActive=1 AND  ( CircularDate>=:fromdate AND CircularDate <=:todate )  ORDER BY CreatedDate DESC";
	@Override
	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception {
	
		Query query =  manager.createNativeQuery(CIRCULARLIST);
		 query.setParameter("fromdate", fromdate);
		 query.setParameter("todate", toDate);
		 List<Object[]> CircularList=query.getResultList();
		 return CircularList;
		
	}


	@Override
	public long GetCircularMaxId() throws Exception {
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(CircularId),0) AS 'count'  FROM ems_circular");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetCircularMaxId "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	

	@Override
	public long GetDepCircularMaxId() throws Exception {
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(DepCircularId),0) AS 'count'  FROM ems_dep_circular");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetDepCircularMaxId "+ e);
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
			logger.error(new Date() +"Inside DAO GetDepCircularMaxIdEdit "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long AddCircular(EMSCircular circular) throws Exception {
		
		try {
			manager.persist(circular);
			manager.flush();
			return (long)circular.getCircularId();
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO AddCircular "+ e);
			e.printStackTrace();
			return 0l;
		}
	}

	private static final String SEARCHLIST="SELECT CircularId,CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject FROM ems_circular  WHERE IsActive=1 AND  (CircularNo LIKE :Search  OR Cirsubject LIKE :Search)";
	@Override
	public List<Object[]> GetSearchList(String search) throws Exception 
	{
		Query query = manager.createNativeQuery(SEARCHLIST);
		query.setParameter("Search", "%"+search+"%");
		List<Object[]> SearchList= query.getResultList();
		return SearchList;
	}


	private static final String DELETECIRCULAR="UPDATE ems_circular SET IsActive=:isactive , ModifiedBy=:modifiedby , ModifiedDate=:modifieddate WHERE CircularId=:circularid";
	@Override
	public int CircularDelete(Long CircularId, String Username)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DELETECIRCULAR);
			query.setParameter("circularid", CircularId);
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			query.setParameter("isactive", 0);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO CircularDelete "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public EMSCircular GetCircularDetailsToEdit(Long  CircularId)throws Exception
	{
		EMSCircular list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EMSCircular> cq = cb.createQuery(EMSCircular.class);
			Root<EMSCircular> root = cq.from(EMSCircular.class);
			Predicate p1 = cb.equal(root.get("CircularId"), CircularId);
			cq = cq.select(root).where(p1);
			TypedQuery<EMSCircular> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
			return list;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetCircularDetailsToEdit "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long EditCircular(EMSCircular circular) throws Exception
	{
		try {
			manager.merge(circular);
			manager.flush();		
			return circular.getCircularId();
		}catch (Exception e) {
			logger.error(new Date() +"Inside DAO EditCircular "+ e);
			e.printStackTrace();
			return 0;
		}		
	}
	
	
	
	

	@Override
	public long CircularTransactionAdd(EMSCircularTrans cirTrans) throws Exception 
	{
		manager.persist(cirTrans);
		manager.flush();
		return (long)cirTrans.getCircularTransId();
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
			logger.error(new Date() +"Inside DAO GetEmsDepType "+ e);
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
			logger.error(new Date() +"Inside DAO GetEmsDepType "+ e);
			return new ArrayList<Object[]>();
		}
		
	}
}

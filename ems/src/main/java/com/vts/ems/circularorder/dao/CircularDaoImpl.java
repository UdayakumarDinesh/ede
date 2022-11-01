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

import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.master.model.CircularList;
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
		return CircularList;
	}

	
	private static final String CIRCULARLIST = "SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject,CircularId FROM ems_circular WHERE IsActive=1 AND  ( CircularDate>=:fromdate AND CircularDate <=:todate )  ORDER BY CircularDate DESC";
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
		logger.info(new Date() +"Inside DAO GetCircularMaxId()");
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(CircularId),0) AS 'count'  FROM ems_circular");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long AddCircular(EMSCircular circular) throws Exception {
		
		logger.info(new Date() + "Inside DAO CircularAdd()");
		try {
			manager.persist(circular);
			manager.flush();
			return (long)circular.getCircularId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}

	private static final String SEARCHLIST="SELECT CircularId,CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject FROM ems_circular "
			+ "WHERE CircularNo LIKE :Search  OR Cirsubject LIKE :Search";
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
		logger.info(new Date() + "Inside DAO CircularDelete()");
		
		try {
			Query query = manager.createNativeQuery(DELETECIRCULAR);
			query.setParameter("circularid", CircularId);
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			query.setParameter("isactive", 0);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public EMSCircular GetCircularDetailsToEdit(Long  CircularId)throws Exception
	{
		logger.info(new Date() + "Inside GetCircularDetailsToEdit()");
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
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long EditCircular(EMSCircular circular) throws Exception
	{
		logger.info(new Date() +"Inside DAO EditCircular()");
		try {
			manager.merge(circular);
			manager.flush();		
			return circular.getCircularId();
		}catch (Exception e) {
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

	
	
	
}

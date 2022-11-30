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
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.circularorder.model.EMSGovtOrders;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class CircularOrderDaoImpl implements CircularOrderDao 
{
	private static final Logger logger = LogManager.getLogger(CircularOrderDaoImpl.class);
	
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
			logger.error(new Date() +"Inside DAO getEmpdataData "+ e);
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
	public List<Object[]> GetEmsDepTypeList() throws Exception 
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
	
	private static final String GETCIRCULARDATA ="from ems_office_order where OrderId =:OrderId";
	@Override
	public EMSOfficeOrder getOrderData(String OrderId) throws Exception
	{
		Query query=manager.createQuery(GETCIRCULARDATA);
		query.setParameter("OrderId", Long.parseLong(OrderId));				
		try {
			return (EMSOfficeOrder)query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			logger.error(new Date() +"Inside DAO getOrderData "+ e);
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

	
	private static final String OFFICEORDERLIST = "SELECT OrderId,OrderNo,DATE_FORMAT(OrderDate,'%d-%m-%Y'),OrderSubject FROM ems_office_order \r\n"
			+ "WHERE IsActive=1 AND  (OrderDate>=:fromdate AND OrderDate <=:todate)  ORDER BY CreatedDate DESC";
	
	public List<Object[]> GetOfficeOrderList(LocalDate fromdate, LocalDate todate) throws Exception {
	
		Query query =  manager.createNativeQuery(OFFICEORDERLIST);
		 query.setParameter("fromdate", fromdate);
		 query.setParameter("todate", todate);
		 List<Object[]> OfficeList=query.getResultList();
		 return OfficeList;
		
	}


	@Override
	public long GetOrderMaxId() throws Exception {
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(OrderId),0) AS 'count'  FROM ems_office_order");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetOrderMaxId "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long AddOfficeOrder(EMSOfficeOrder order) throws Exception {
		
		try {
			manager.persist(order);
			manager.flush();
			return (long)order.getOrderId();
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO AddOfficeOrder "+ e);
			e.printStackTrace();
			return 0l;
		}
	}

	private static final String SEARCHLIST="SELECT OrderId,OrderNo,DATE_FORMAT(OrderDate,'%d-%m-%Y'),OrderSubject FROM ems_office_order "
			+ "WHERE OrderNo LIKE :Search  OR OrderSubject LIKE :Search";
	@Override
	public List<Object[]> GetSearchList(String search) throws Exception 
	{
		Query query = manager.createNativeQuery(SEARCHLIST);
		query.setParameter("Search", "%"+search+"%");
		List<Object[]> SearchList= query.getResultList();
		return SearchList;
	}


	private static final String DELETECIRCULAR="UPDATE ems_office_order SET IsActive=:isactive , ModifiedBy=:modifiedby , ModifiedDate=:modifieddate WHERE Orderid=:Orderid";
	@Override
	public int OfficeOrderDelete(Long OrdersId, String Username)throws Exception{
		
		try {
			Query query = manager.createNativeQuery(DELETECIRCULAR);
			query.setParameter("Orderid", OrdersId);
			query.setParameter("modifiedby", Username);
			query.setParameter("modifieddate", sdtf.format(new Date()));
			query.setParameter("isactive", 0);
			int count = (int) query.executeUpdate();
			return count;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO OfficeOrderDelete "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public EMSOfficeOrder GetOrderDetailsToEdit(Long  OrdersId)throws Exception
	{
		EMSOfficeOrder list = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<EMSOfficeOrder> cq = cb.createQuery(EMSOfficeOrder.class);
			Root<EMSOfficeOrder> root = cq.from(EMSOfficeOrder.class);
			Predicate p1 = cb.equal(root.get("OrderId"), OrdersId);
			cq = cq.select(root).where(p1);
			TypedQuery<EMSOfficeOrder> allquery = manager.createQuery(cq);
			list = allquery.getResultList().get(0);
			return list;
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetOrderDetailsToEdit "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long EditOrder(EMSOfficeOrder Order) throws Exception
	{
		try {
			manager.merge(Order);
			manager.flush();		
			return Order.getOrderId();
		}catch (Exception e) {
			logger.error(new Date() +"Inside DAO EditOrder "+ e);
			e.printStackTrace();
			return 0;
		}		
	}
	
	
	
	

	@Override
	public long OfficeOrderTransactionAdd(EMSOfficeOrderTrans  OrderTrans) throws Exception 
	{
		manager.persist(OrderTrans);
		manager.flush();
		return (long)OrderTrans.getOrderTransId();
	}


	private static final String GETGOVTORDERSLIST = "SELECT GovtOrderId,DepTypeId,OrderNo,Description,OrderDate FROM ems_govt_orders WHERE IsActive=1 AND CASE WHEN :DepTypeId ='A' THEN 1=1 ELSE DepTypeId=:DepTypeId END AND ( OrderDate>=:fromdate AND OrderDate <=:todate )  ORDER BY CreatedDate DESC";
	@Override
	public List<Object[]> GetGovtOrdersList(String fromdate, String toDate,String DepTypeId) throws Exception 
	{
		Query query =  manager.createNativeQuery(GETGOVTORDERSLIST);
		
		query.setParameter("DepTypeId", DepTypeId);
		query.setParameter("fromdate", fromdate);
		query.setParameter("todate", toDate);
		List<Object[]> OrdersList=query.getResultList();
		return OrdersList;
		
	}
	
	
	@Override
	public long EmsGovtOrderAdd(EMSGovtOrders order) throws Exception 
	{
		manager.persist(order);
		manager.flush();
		return order.getGovtOrderId();
		
	}
	
	@Override
	public long EmsGovtOrderEdit(EMSGovtOrders order) throws Exception 
	{
		manager.merge(order);
		manager.flush();
		return order.getGovtOrderId();
		
	}

	@Override
	public EMSGovtOrders getEMSGovtOrder(String OrderId) throws Exception 
	{
		EMSGovtOrders Order= manager.find(EMSGovtOrders.class,Long.parseLong(OrderId));
		return Order;
		
	}
	
	@Override
	public long GetGovtOrderMaxId() throws Exception {
		try {
			Query query = manager.createNativeQuery("SELECT IFNULL(MAX(GovtOrderId),0) AS 'count'  FROM ems_govt_orders");	
			BigInteger result = (BigInteger) query.getSingleResult();
			return result.longValue();
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside DAO GetGovtOrderMaxId "+ e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String GOVTORDERSEARCHLIST="SELECT GovtOrderId,OrderNo,DATE_FORMAT(OrderDate,'%d-%m-%Y'),Description,DepTypeId FROM ems_govt_orders  WHERE    (OrderNo LIKE :Search  OR Description LIKE :Search  ) AND CASE WHEN :DepTypeId = 'A' THEN 1=1 ELSE DepTypeId=:DepTypeId END AND IsActive=1 ";
	@Override
	public List<Object[]> GovtOrderSearchList(String search,String id) throws Exception 
	{
		Query query = manager.createNativeQuery(GOVTORDERSEARCHLIST);
		query.setParameter("Search", "%"+search+"%");
		query.setParameter("DepTypeId", id);
		List<Object[]> SearchList= query.getResultList();
		return SearchList;
	}
}

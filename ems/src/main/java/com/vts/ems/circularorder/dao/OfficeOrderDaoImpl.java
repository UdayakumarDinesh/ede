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
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.master.model.CircularList;
import com.vts.ems.pis.model.EmployeeDetails;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class OfficeOrderDaoImpl implements OfficeOrderDao 
{
	private static final Logger logger = LogManager.getLogger(OfficeOrderDaoImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	
	@Autowired
	EntityManager manager;
	
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
			logger.error(new Date() +"Inside getOrderData "+ e);
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
			logger.error(new Date() +"Inside getEmpdataData "+ e);
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
			logger.error(new Date() +"Inside GetOrderMaxId "+ e);
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
			logger.error(new Date() +"Inside AddOfficeOrder "+ e);
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
			logger.error(new Date() +"Inside OfficeOrderDelete "+ e);
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
			logger.error(new Date() +"Inside GetOrderDetailsToEdit "+ e);
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
			logger.error(new Date() +"Inside EditOrder "+ e);
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


	
	
	
	
}

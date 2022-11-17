package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;

public interface CircularOrderDao {
	

	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public List<Object[]> GetDepCircularList(String fromdate, String toDate, String DepTypeId) throws Exception;
	public Object[] GetEmsDepType(String DepTypeId) throws Exception;
	public long EmsDepCircularAdd(EMSDepCircular circular) throws Exception;
	public EMSDepCircular getEmsDepCircular(String circularId) throws Exception;
	public long GetDepCircularMaxId() throws Exception;
	public long EmsDepCircularEdit(EMSDepCircular circular) throws Exception;
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception;
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception;
	public List<Object[]> DepCircularSearchList(String search) throws Exception;
	public List<Object[]> GetEmsDepType() throws Exception;
	public long DepCircularTransactionAdd(DepEMSCircularTrans cirTrans) throws Exception ;
	
	public EMSOfficeOrder getOrderData(String OrderId) throws Exception;
	public long AddOfficeOrder(EMSOfficeOrder order)throws Exception;
	public long EditOrder(EMSOfficeOrder Order)throws Exception;
	public long GetOrderMaxId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetOfficeOrderList(LocalDate fromdate, LocalDate todate) throws Exception;
	public List<Object[]> GetSearchList(String search) throws Exception;
	public int OfficeOrderDelete(Long OrdersId, String Username)throws Exception;
	public EMSOfficeOrder GetOrderDetailsToEdit(Long  OrdersId)throws Exception;
	public long OfficeOrderTransactionAdd(EMSOfficeOrderTrans  OrderTrans) throws Exception;
}

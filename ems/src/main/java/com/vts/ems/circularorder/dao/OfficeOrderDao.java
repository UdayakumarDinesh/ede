package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;

public interface OfficeOrderDao {
	

	public EMSOfficeOrder getOrderData(String OrderId) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
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

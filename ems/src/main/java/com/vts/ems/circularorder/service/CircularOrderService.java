package com.vts.ems.circularorder.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.circularorder.dto.DepCircularDto;
import com.vts.ems.circularorder.dto.OfficeOrderUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.circularorder.model.EMSGovtOrders;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;



public interface CircularOrderService {
	

	public File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public List<Object[]> GetDepCircularList(String fromdate, String toDate, String DepTypeId) throws Exception;
	public Object[] GetEmsDepType(String DepTypeId) throws Exception;
	public long DepCircularAdd(DepCircularDto dto) throws Exception;
	public EMSDepCircular getEmsDepCircular(String circularId) throws Exception;
	public long DepCircularEdit(DepCircularDto dto) throws Exception;
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception;
	public long DepCircularDelete(String circularId, String Username) throws Exception;
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception;
	public long DepCircularTransactionAdd(DepEMSCircularTrans cirTrans) throws Exception ;
	
	public long OfficeOrderAdd(OfficeOrderUploadDto uploadorderdto)throws Exception;
	public long OrderUpdate(OfficeOrderUploadDto uploadorderdto)throws Exception;
	public long GetMaxOrderId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetOfficeOrderList(String fromdate, String todate) throws Exception;
	public List<Object[]> GetSearchList(String search) throws Exception;
	public int OfficeOrderDelete(Long OrdersId, String Username)throws Exception;
	public EMSOfficeOrder GetOrderDetailsToEdit(Long OrdersId)throws Exception;
	public EMSOfficeOrder getOrderData(String OrderId) throws Exception;
	public long OfficeOrderTransactionAdd(EMSOfficeOrderTrans  OrderTrans) throws Exception;
	
	public List<Object[]> GetGovtOrdersList(String fromdate, String toDate, String DepTypeId) throws Exception;
	public List<Object[]> GetEmsDepTypeList() throws Exception;
	public long GovtOrderAdd(EMSGovtOrders order, MultipartFile OrderFile) throws Exception;
	public EMSGovtOrders getEMSGovtOrder(String OrderId) throws Exception;
	public long GovtOrderEdit(EMSGovtOrders order, MultipartFile OrderFile) throws Exception;
	public long GovtOrderDelete(String OrderId, String Username) throws Exception;
	public List<Object[]> GovtOrderSearchList(String search,String id) throws Exception; 
}

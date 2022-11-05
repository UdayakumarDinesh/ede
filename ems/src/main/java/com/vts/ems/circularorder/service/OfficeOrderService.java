package com.vts.ems.circularorder.service;

import java.io.File;
import java.util.List;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.dto.OfficeOrderUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;

import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.circularorder.model.EMSOfficeOrder;
import com.vts.ems.circularorder.model.EMSOfficeOrderTrans;
import com.vts.ems.pis.model.EmployeeDetails;



public interface OfficeOrderService {
	

	public long OfficeOrderAdd(OfficeOrderUploadDto uploadorderdto)throws Exception;
	public long OrderUpdate(OfficeOrderUploadDto uploadorderdto)throws Exception;
	public long GetMaxOrderId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetOfficeOrderList(String fromdate, String todate) throws Exception;
	public List<Object[]> GetSearchList(String search) throws Exception;
	public int OfficeOrderDelete(Long OrdersId, String Username)throws Exception;
	public EMSOfficeOrder GetOrderDetailsToEdit(Long OrdersId)throws Exception;
	public EMSOfficeOrder getOrderData(String OrderId) throws Exception;
	public File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public long OfficeOrderTransactionAdd(EMSOfficeOrderTrans  OrderTrans) throws Exception;


}

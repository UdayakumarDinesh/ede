package com.vts.ems.circularorder.service;

import java.io.File;
import java.util.List;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.dto.DepCircularDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.pis.model.EmployeeDetails;



public interface CircularService {
	

	public long CircularAdd(CircularUploadDto uploadcirdto)throws Exception;
	public long CircularUpdate(CircularUploadDto uploadcirdto)throws Exception;
	public long GetMaxCircularId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetCircularList(String fromdate, String todate) throws Exception;
	public List<Object[]> GetSearchList(String search) throws Exception;
	public int CircularDelete(Long CircularId, String Username)throws Exception;
	public EMSCircular GetCircularDetailsToEdit(Long CircularId)throws Exception;
	public EMSCircular getCircularData(String CircularId) throws Exception;
	public File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public long CircularTransactionAdd(EMSCircularTrans cirTrans) throws Exception;
	public List<Object[]> GetDepCircularList(String fromdate, String toDate, String DepTypeId) throws Exception;
	public Object[] GetEmsDepType(String DepTypeId) throws Exception;
	public long DepCircularAdd(DepCircularDto dto) throws Exception;
	public EMSDepCircular getEmsDepCircular(String circularId) throws Exception;
	public long DepCircularEdit(DepCircularDto dto) throws Exception;
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception;
	public long DepCircularDelete(String circularId, String Username) throws Exception;
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception;
	public List<Object[]> GetEmsDepType() throws Exception;
	public long DepCircularTransactionAdd(DepEMSCircularTrans cirTrans) throws Exception ;

}

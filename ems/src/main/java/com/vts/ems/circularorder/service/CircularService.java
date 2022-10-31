package com.vts.ems.circularorder.service;

import java.io.File;
import java.util.List;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.pis.model.EmployeeDetails;


public interface CircularService {
	

	public long CircularUpload(CircularUploadDto uploadcirdto)throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetCircularList(String fromdate, String todate) throws Exception;
	public EMSCircular getCircularData(String CircularId) throws Exception;
	public File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public long CircularTransactionAdd(EMSCircularTrans cirTrans) throws Exception;

}

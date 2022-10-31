package com.vts.ems.circularorder.service;

import java.io.File;

import com.vts.ems.circularorder.dto.PdfFileEncryptionDataDto;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.pis.model.EmployeeDetails;

public interface CircularService {

	EMSCircular getCircularData(String CircularId) throws Exception;
	File EncryptAddWaterMarkAndMetadatatoPDF(PdfFileEncryptionDataDto dto) throws Exception;
	EmployeeDetails getEmpdataData(String empNo) throws Exception;

}

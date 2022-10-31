package com.vts.ems.circularorder.service;

import com.vts.ems.circularorder.dto.CircularUploadDto;
import com.vts.ems.circularorder.model.EMSCircular;


public interface CircularService {
	

	public long CircularUpload(CircularUploadDto uploadcirdto ,EMSCircular circular)throws Exception;


}

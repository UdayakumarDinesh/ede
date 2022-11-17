package com.vts.ems.circularorder.service;

import java.util.List;

import com.vts.ems.circularorder.dto.FormUploadDto;
import com.vts.ems.circularorder.model.EMSForms;

public interface FormNoticeService {
	
	public List<Object[]> getEmsFormsList(String DepTypeId) throws Exception;
	public List<Object[]> GetEmsDepTypes() throws Exception;
	public long EmsFormAdd(FormUploadDto formDto) throws Exception;
	public EMSForms GetEMSForm(String formId) throws Exception;
	public long getFormNoCount(String formNo) throws Exception;


}

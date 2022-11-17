package com.vts.ems.circularorder.dao;

import java.util.List;

import com.vts.ems.circularorder.model.EMSForms;

public interface FormNoticeDao {

	public List<Object[]> getEmsFormsList(String DepTypeId) throws Exception;
	public List<Object[]> GetEmsDepTypes() throws Exception;
	public long MaxOfEmsFormId() throws Exception;
	public long EMSFormAdd(EMSForms form) throws Exception;
	public EMSForms GetEMSForm(String formId) throws Exception;
	public long getFormNoCount(String formNo) throws Exception;

	
}

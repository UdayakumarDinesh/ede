package com.vts.ems.circularorder.dao;

import java.util.List;

import com.vts.ems.circularorder.model.EMSForms;
import com.vts.ems.circularorder.model.EMSNotice;

public interface FormNoticeDao {

	public List<Object[]> getEmsFormsList(String DepTypeId) throws Exception;
	public List<Object[]> GetEmsDepTypes() throws Exception;
	public long MaxOfEmsFormId() throws Exception;
	public long EMSFormAdd(EMSForms form) throws Exception;
	public EMSForms GetEMSForm(String formId) throws Exception;
	public long getFormNoCount(String formNo) throws Exception;
	
	public List<Object[]> getEmsNoticeList(String FromDate, String ToDate) throws Exception;
	public long EMSNoticeAdd(EMSNotice Notice) throws Exception;
	public long MaxOfEmsNoticeId() throws Exception;
	public EMSNotice GetEMSNotice(String NoticeId) throws Exception;
	public long EMSNoticeEdit(EMSNotice Notice) throws Exception;
	public long EMSFormEdit(EMSForms form) throws Exception;
	public List<Object[]> getEmsTodayNotices() throws Exception;
	
}

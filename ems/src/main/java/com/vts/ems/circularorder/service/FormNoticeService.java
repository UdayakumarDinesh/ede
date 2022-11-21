package com.vts.ems.circularorder.service;

import java.util.List;

import com.vts.ems.circularorder.dto.FormUploadDto;
import com.vts.ems.circularorder.dto.NoticeUploadDto;
import com.vts.ems.circularorder.model.EMSForms;
import com.vts.ems.circularorder.model.EMSNotice;

public interface FormNoticeService {
	
	public List<Object[]> getEmsFormsList(String DepTypeId) throws Exception;
	public List<Object[]> GetEmsDepTypes() throws Exception;
	public long EmsFormAdd(FormUploadDto formDto) throws Exception;
	public EMSForms GetEMSForm(String formId) throws Exception;
	public long getFormNoCount(String formNo) throws Exception;
	
	public List<Object[]> getEmsNoticeList(String FromDate, String ToDate) throws Exception;
	public long EmsNoticeAdd(NoticeUploadDto formDto) throws Exception;
	public EMSNotice GetEMSNotice(String NoticeId) throws Exception;
	public long EMSNoticeEdit(NoticeUploadDto noticeDto) throws Exception;
	public long EMSFormDelete(String EMSFormId, String userId)throws Exception;
	public long EMSNoticeDelete(String NoticeId, String userId) throws Exception;
	public List<Object[]> getEmsTodayNotices() throws Exception;

}

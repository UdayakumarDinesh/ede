package com.vts.ems.athithi.service;

import java.util.List;

public interface PassService {

	public List<Object[]> pendingIntimations() throws Exception;
	public List<Object[]> intimationDetail(String intimationId)throws Exception;
	public List<Object[]> intimationVisitor(String intimationId) throws Exception;
	public int updatePhoto(String visitorId, String photoName)throws Exception;
	public Long createPass(String empId, String intimationId, String[] visitorId, String[] visitorBatchId)throws Exception;
	public List<Object[]> getIntimationDetails(String intimationId,String passId)throws Exception;
	public List<Object[]> getPassVisitorList(String intimationId, String passId)throws Exception;
	public List<Object[]> getCreatedPassList(String fDate, String tDate)throws Exception;
	public List<Object[]> getPassVisitorList(String passId)throws Exception;
	public int passVisitorSubmit(String passId)throws Exception;
	public List<Object[]> getPassReport(String fdate, String tdate)throws Exception;
	public int changepassword(String OldPwd,String pwd,String empid)throws Exception;
	public Object[] LabInfo() throws Exception;

     
}

package com.vts.ems.athithi.dao;

import java.util.List;

import com.vts.ems.athithi.model.Pass;
import com.vts.ems.athithi.model.PassEmp;

public interface PassDao {

	public List<Object[]> pendingIntimations() throws Exception;
	public List<Object[]> intimationDetail(String intimationId) throws Exception;
	public List<Object[]> intimationVisitor(String intimationId) throws Exception;
	public int updatePhoto(String visitorId, String photoName)throws Exception;
	public int todayPassCount()throws Exception;
	public Long createPass(Pass pass)throws Exception;
	public Long addVisitor(PassEmp visitor)throws Exception;
	public List<Object[]> getIntimationDetails(String intimationId, String passId)throws Exception;
	public List<Object[]> getPassVisitorList(String intimationId, String passId)throws Exception;
	public int updateIntimationStatus(String intimationId)throws Exception;
	public List<Object[]> getCreatedPassList(String fDate, String tDate)throws Exception;
	public List<Object[]> getPassVisitorList(String passId)throws Exception;
	public int passVisitorSubmit(String passId)throws Exception;
	public List<Object[]> getPassReport(String fdate, String tdate)throws Exception;
	public int changepassword(String pwd,String empid,String Modifieddate)throws Exception;
	public String OldPassword(String UserId) throws Exception ;
	public Object[] LabInfo() throws Exception;
}

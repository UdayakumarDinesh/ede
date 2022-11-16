package com.vts.ems.circularorder.dao;

import java.util.List;

import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.pis.model.EmployeeDetails;

public interface CircularDao {
	

	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public List<Object[]> GetDepCircularList(String fromdate, String toDate, String DepTypeId) throws Exception;
	public Object[] GetEmsDepType(String DepTypeId) throws Exception;
	public long EmsDepCircularAdd(EMSDepCircular circular) throws Exception;
	public EMSDepCircular getEmsDepCircular(String circularId) throws Exception;
	public long GetDepCircularMaxId() throws Exception;
	public long EmsDepCircularEdit(EMSDepCircular circular) throws Exception;
	public long GetDepCircularMaxIdEdit(String DepTypeId) throws Exception;
	public List<Object[]> DepCircularSearchList(String search,String id) throws Exception;
	public List<Object[]> DepCircularSearchList(String search) throws Exception;
	public List<Object[]> GetEmsDepType() throws Exception;
	public long DepCircularTransactionAdd(DepEMSCircularTrans cirTrans) throws Exception ;
}

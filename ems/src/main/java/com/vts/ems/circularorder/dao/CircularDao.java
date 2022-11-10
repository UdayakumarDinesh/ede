package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.circularorder.model.DepEMSCircularTrans;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.circularorder.model.EMSDepCircular;
import com.vts.ems.pis.model.EmployeeDetails;

public interface CircularDao {
	

	public EMSCircular getCircularData(String CircularId) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public long AddCircular(EMSCircular circular)throws Exception;
	public long EditCircular(EMSCircular circular)throws Exception;
	public long GetCircularMaxId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception;
	public List<Object[]> GetSearchList(String search) throws Exception;
	public int CircularDelete(Long CircularId, String Username)throws Exception;
	public EMSCircular GetCircularDetailsToEdit(Long  CircularId)throws Exception;
	public long CircularTransactionAdd(EMSCircularTrans cirTrans) throws Exception;
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

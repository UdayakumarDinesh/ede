package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.circularorder.model.EMSCircularTrans;
import com.vts.ems.pis.model.EmployeeDetails;

public interface CircularDao {
	

	public EMSCircular getCircularData(String CircularId) throws Exception;
	public EmployeeDetails getEmpdataData(String empNo) throws Exception;
	public long CircularAdd(EMSCircular circular)throws Exception;
	public long GetCircularMaxId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception;
	public long CircularTransactionAdd(EMSCircularTrans cirTrans) throws Exception;
}

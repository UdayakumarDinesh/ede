package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.List;
import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.master.model.CircularList;

public interface CircularDao {
	
	public long CircularAdd(EMSCircular circular)throws Exception;
	public long GetCircularMaxId()throws Exception;
	public List<Object[]> selectAllList() throws Exception;
	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception;
	public int CircularDelete(Long CircularId, String Username)throws Exception;
	public EMSCircular GetCircularDetailsToEdit(Long  CircularId)throws Exception;
	
}

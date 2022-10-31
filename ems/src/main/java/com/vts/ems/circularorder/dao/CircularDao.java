package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.List;

public interface CircularDao {
    
	public List<Object[]> selectAllList() throws Exception;

	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception;
}

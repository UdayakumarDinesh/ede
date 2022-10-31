package com.vts.ems.circularorder.dao;

import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.master.model.CircularList;

public interface CircularDao {
	
	public long CircularAdd(EMSCircular circular)throws Exception;
	public long GetCircularMaxId()throws Exception;

}

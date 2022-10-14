package com.vts.ems.dopart.dao;

import java.util.List;

public interface DoPartDao {

	
	public List<Object[]> GetDOList()throws Exception;
	public List<Object[]> RetriveContent(int DoNumber,String year)throws Exception;
}

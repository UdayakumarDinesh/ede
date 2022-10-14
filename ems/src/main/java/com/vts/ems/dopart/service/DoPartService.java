package com.vts.ems.dopart.service;

import java.util.List;

public interface DoPartService {

	
	public List<Object[]> GetDOList()throws Exception;
	public List<Object[]> RetriveContent(int DoNumber,String year)throws Exception;
}

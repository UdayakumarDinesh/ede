package com.vts.ems.apf.dao;

import java.util.List;

public interface ApfDao {

	public List<Object[]> allAPFApply(String empNo) throws Exception;
	public Object[] empNameAndDesi(String empNo) throws Exception;
	
}

package com.vts.ems.noc.dao;

import java.util.List;

public interface NocDao {
	

	 public List<Object[]> getNocEmpList(String empNo) throws Exception;
}

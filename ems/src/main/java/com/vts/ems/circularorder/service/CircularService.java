package com.vts.ems.circularorder.service;

import java.util.List;

public interface CircularService {

	public List<Object[]> selectAllList() throws Exception;

	public List<Object[]> GetCircularList(String fromdate, String todate) throws Exception;

}

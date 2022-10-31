package com.vts.ems.circularorder.dao;

import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.pis.model.EmployeeDetails;

public interface CircularDao {

	EMSCircular getCircularData(String CircularId) throws Exception;
	EmployeeDetails getEmpdataData(String empNo) throws Exception;

}

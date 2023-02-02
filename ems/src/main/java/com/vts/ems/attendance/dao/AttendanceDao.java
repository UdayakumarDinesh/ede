package com.vts.ems.attendance.dao;

import java.util.List;

import com.vts.ems.attendance.dto.PunchInfoSQLDto;
import com.vts.ems.attendance.model.AttendancePunchData;

public interface AttendanceDao {

	List<PunchInfoSQLDto> getPunchInfo(String Date) throws Exception;
	long insertPunchInfo(AttendancePunchData punch) throws Exception;
	
}

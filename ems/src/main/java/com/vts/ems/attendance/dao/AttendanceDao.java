package com.vts.ems.attendance.dao;

import java.util.List;

import com.vts.ems.attendance.dto.PunchInfoSQLDto;
import com.vts.ems.attendance.model.AttendancePunchData;

public interface AttendanceDao {

	List<PunchInfoSQLDto> getPunchInfo(String Date) throws Exception;
	List<AttendancePunchData> AttendPunchInfo(String AttendanceDate) throws Exception;
	long DeletePunchInfo(String AttendanceDate) throws Exception;
	long PunchListSize() throws Exception;
	List<PunchInfoSQLDto> getPunchInfoAll() throws Exception;
	long insertPunchInfo(List<AttendancePunchData> punchlist) throws Exception;
	
}

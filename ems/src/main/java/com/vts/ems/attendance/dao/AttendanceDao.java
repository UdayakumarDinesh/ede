package com.vts.ems.attendance.dao;

import java.util.List;

import com.vts.ems.attendance.dto.PunchInfoSQLDto;
import com.vts.ems.attendance.model.AttendancePunchData;

public interface AttendanceDao {

	public List<PunchInfoSQLDto> getPunchInfo(String Date) throws Exception;
	public List<AttendancePunchData> AttendPunchInfo(String AttendanceDate) throws Exception;
	public long DeletePunchInfo(String AttendanceDate) throws Exception;
	public long PunchListSize() throws Exception;
	public List<PunchInfoSQLDto> getPunchInfoAllAfter(String after) throws Exception;
	public long insertPunchInfo(List<AttendancePunchData> punchlist) throws Exception;
	
	public List<Object[]> getAttendanceDetails(String empNo, String fromDate, String toDate) throws Exception;
	public List<Object[]> EmployeeList() throws Exception;
	public Object getlastSyncDateTime() throws Exception;
}

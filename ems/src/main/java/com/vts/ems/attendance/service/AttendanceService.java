package com.vts.ems.attendance.service;

import java.util.List;

public interface AttendanceService {

	public long syncAttendancePunchData()throws Exception;
	public List<Object[]> getAttendanceDetails(String empNo, String fromDate, String toDate) throws Exception;
	public List<Object[]> EmployeeList() throws Exception;
	public Object getlastSyncDateTime() throws Exception;
}

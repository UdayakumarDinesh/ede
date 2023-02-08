package com.vts.ems.attendance.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ems.attendance.model.AttendancePunchData;

public interface AttendancePunchDataRepo extends JpaRepository<AttendancePunchData, Long> {

}

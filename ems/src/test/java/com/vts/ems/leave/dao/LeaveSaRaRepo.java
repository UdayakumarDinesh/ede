package com.vts.ems.leave.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vts.ems.leave.model.LeaveRaSa;

@Repository
public interface LeaveSaRaRepo 
  extends JpaRepository<LeaveRaSa, Long> {
	LeaveRaSa  findByEMPID(String EMPID);
}

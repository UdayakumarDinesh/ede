package com.vts.ems.leave.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vts.ems.leave.model.LeaveMC_FC;
import com.vts.ems.leave.model.LeaveRaSa;

@Repository
public interface McFcRepo 
  extends JpaRepository<LeaveMC_FC, Long> {
	LeaveMC_FC   findByApplId(String applId);
}
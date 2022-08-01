package com.vts.ems.leave.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vts.ems.leave.model.LeaveAppl;


@Repository
public interface LeaveApplRepo 
	  extends JpaRepository<LeaveAppl, Long> {
			LeaveAppl findByApplId(String ApplId);
	}

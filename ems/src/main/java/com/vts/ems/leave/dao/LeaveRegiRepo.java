package com.vts.ems.leave.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vts.ems.leave.model.LeaveRegister;



	@Repository
	public interface LeaveRegiRepo 
	  extends JpaRepository<LeaveRegister, Long> {
		
	}

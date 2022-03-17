package com.vts.ems.leave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.leave.dao.LeaveDaoImpl;


@Service
public class LeaveServiceImpl implements LeaveService{


	
	
	@Autowired
	private LeaveDaoImpl dao;
	
	

	
	}

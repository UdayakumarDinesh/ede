package com.vts.ems.leave.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.vts.ems.leave.service.LeaveService;

@Controller
public class LeaveController {

	
	
	@Autowired
	private LeaveService service;
	

}

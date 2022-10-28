package com.vts.ems.circularorder.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vts.ems.circularorder.service.CircularService;

@Controller
public class CircularController {

	private static final Logger logger = LogManager.getLogger(CircularController.class);
	
	@Autowired
	CircularService service;
	
	@RequestMapping(value = "CircularList.htm", method = RequestMethod.GET)
	public String circularList(HttpServletRequest req, HttpSession ses) throws Exception
	{
		
		return "circular/CircularList";
	}
	
	
}

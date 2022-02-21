package com.vts.ems.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class EmsController {
	    @RequestMapping(value = {"/login","/"}, method = RequestMethod.GET)
	    public String login(HttpServletRequest req,HttpSession ses) {
	 

	        
	    	
	 
	        return "static/login";
	    }

}

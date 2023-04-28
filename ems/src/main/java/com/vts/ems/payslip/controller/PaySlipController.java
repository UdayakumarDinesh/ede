package com.vts.ems.payslip.controller;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vts.ems.config.MsAccessDBConnectionConfig;
import com.vts.ems.payslip.service.PaySlipService;



@Controller
public class PaySlipController {

	private static final Logger logger = LogManager.getLogger(PaySlipController.class);
	@Autowired
	PaySlipService service;
	
	@Autowired	
	private MsAccessDBConnectionConfig AccessConnProvider;
	
	@RequestMapping(value = "SyncPaySlipData.htm" )
	public void SyncPaySlipData(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
	{
		String Username = (String) ses.getAttribute("Username");
		String EmpId = ((Long) ses.getAttribute("EmpId")).toString();
//		logger.info(new Date() +"Inside SyncPaySlipData.htm "+Username);
		try {
			
			
//			service.MonthWisePaySlipSyncList(0, 0);
			

			
				Connection conn =AccessConnProvider.getMSAccessConnection();
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("UPDATE Acts_M SET EmpName = 'SOMAYAJULU                DSRS' WHERE EmpNo=9 ");
				stmt.close();
				conn.close();
					
//			return "";
		}catch (Exception e) {
			e.printStackTrace();
//			logger.error(new Date() +" Inside SyncPaySlipData.htm "+Username, e);
//			return "static/Error";
		}
	}
	
	
	
}

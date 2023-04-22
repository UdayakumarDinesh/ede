package com.vts.ems.payslip.service;

import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.config.MsAccessDBConnectionConfig;
import com.vts.ems.payslip.dao.PaySlipDao;

@Service
public class PaySlipServiceImpl implements PaySlipService {

	private static final Logger logger = LogManager.getLogger(PaySlipServiceImpl.class);
	
	@Autowired
	PaySlipDao dao;
	
	

	
	@Override
	public ResultSet MonthWisePaySlipSyncList(int month,int year) throws Exception {
		ResultSet res = dao.MonthWisePaySlipSyncList(month, year);
		
		while(res.next()) {
			res.getString(1);
			
		}
		
		return res;
	}
}

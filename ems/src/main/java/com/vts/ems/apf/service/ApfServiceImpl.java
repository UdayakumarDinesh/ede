package com.vts.ems.apf.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.apf.dao.ApfDao;
import com.vts.ems.newspaper.dao.NewsPaperDao;
import com.vts.ems.newspaper.service.NewPaperServiceImpl;

@Service
public class ApfServiceImpl implements ApfService{

	private static final Logger logger = LogManager.getLogger(ApfServiceImpl.class);
	
	@Autowired
	private ApfDao dao;
	
	@Override
	public List<Object[]> allAPFApply(String empNo) throws Exception{
		return dao.allAPFApply(empNo);
	}
	
	
	@Override
	public Object[] empNameAndDesi(String empNo) throws Exception{
		return dao.empNameAndDesi(empNo);
	}
	
	
}

package com.vts.ems.chss.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.chss.dao.CHSSDao;

@Service
public class CHSSServiceImpl implements CHSSService {

	private static final Logger logger = LogManager.getLogger(CHSSServiceImpl.class);
	
	@Autowired
	CHSSDao dao;
}

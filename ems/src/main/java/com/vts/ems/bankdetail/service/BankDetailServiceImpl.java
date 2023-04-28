package com.vts.ems.bankdetail.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.bankdetail.dao.BankDetailDao;
import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.newspaper.service.NewPaperServiceImpl;

@Service
public class BankDetailServiceImpl implements BankDetailService{
	
	private static final Logger logger = LogManager.getLogger(BankDetailServiceImpl.class);
	
	@Autowired
	private BankDetailDao dao;
	
	public List<Object[]> findAll(String empNo) throws Exception{
		return dao.findAll(empNo);
	}
	
	public long addBankdetail(BankDertails bankDertail) throws Exception{
		return dao.addBankdetail(bankDertail);
	}
	
	public BankDertails findById(long bankId) throws Exception{
		return dao.findById(bankId);
	}
	
	public long editBankdetail(BankDertails bankDertail,long bankId) throws Exception{
		return dao.editBankdetail(bankDertail, bankId);
	}
	
	public long removeById(long bankId) throws Exception{
		return dao.removeById(bankId);
	}
}

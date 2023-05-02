package com.vts.ems.bankdetail.service;

import java.util.List;

import com.vts.ems.bankdetail.model.BankDertails;

public interface BankDetailService {

	public List<Object[]> findAll(String empNo) throws Exception;
	public long addBankdetail(BankDertails bankDertail) throws Exception;
	public BankDertails findById(long bankId) throws Exception;
	public long editBankdetail(BankDertails bankDertail,long bankId) throws Exception;
	public long removeById(long bankId) throws Exception;
}

package com.vts.ems.bankdetail.dao;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.bankdetail.model.BankDertails;
import com.vts.ems.newspaper.dao.NewsPaperDaoImpl;

@Repository
@Transactional
public class BankDetailDaoImp implements BankDetailDao{

	private static final Logger logger = LogManager.getLogger(BankDetailDaoImp.class);

	@PersistenceContext
	private EntityManager manager;

	private static final String allBank="select BankId, EmpNo, BankName, Branch, IFSC, AccountNo from bank_detail where EmpNo=:empNo AND IsActive=1";

	public List<Object[]> findAll(String empNo) throws Exception{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(allBank);
			q.setParameter("empNo", empNo);
			bankDetailList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO BankDetailDaoImp "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}

	public long addBankdetail(BankDertails bankDertail) throws Exception{
		try {
			manager.persist(bankDertail);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO BankDetailDaoImp "+e);
			e.printStackTrace();
		}
		return bankDertail.getBankId();
	}

	/* private static final StringoneBank="select BankId, EmpNo, BankName, Branch, IFSC, AccountNo from bank_detail where BankId=:bankId"; */

	public BankDertails findById(long bankId) throws Exception{
		BankDertails  bankDertails= null;
		try {
			BankDertails BankD =(BankDertails) manager.find(BankDertails.class, bankId);
			bankDertails=BankD;

		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO BankDetailDaoImp "+e);
			e.printStackTrace();
		}
		return bankDertails;
	}


	public long editBankdetail(BankDertails bankDertail, long bankId) throws Exception{
		long l=0;
		try {
			BankDertails BankD =(BankDertails) manager.find(BankDertails.class, bankId);
			BankD.setBankName(bankDertail.getBankName());
			BankD.setBranch(bankDertail.getBranch());
			BankD.setIFSC(bankDertail.getIFSC());
			BankD.setAccountNo(bankDertail.getAccountNo());
			BankD.setModifiedBy(bankDertail.getModifiedBy());
			BankD.setModifiedDate(bankDertail.getModifiedDate());
			manager.merge(BankD);
			l=BankD.getBankId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO BankDetailDaoImp "+e);
			e.printStackTrace();
		}

		return l;
	}
	
	public long removeById(long bankId) throws Exception{
		long l=0;
		try {
			BankDertails BankD =(BankDertails) manager.find(BankDertails.class, bankId);
			BankD.setIsActive(0);
			manager.merge(BankD);
			l=BankD.getBankId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO BankDetailDaoImp "+e);
			e.printStackTrace();
		}
		return l;
	}
}

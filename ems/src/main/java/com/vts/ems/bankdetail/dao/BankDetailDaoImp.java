package com.vts.ems.bankdetail.dao;



import java.text.SimpleDateFormat;
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
import com.vts.ems.bankdetail.model.BankDetChangeTransa;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.newspaper.dao.NewsPaperDaoImpl;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;

@Repository
@Transactional
public class BankDetailDaoImp implements BankDetailDao{

	private static final Logger logger = LogManager.getLogger(BankDetailDaoImp.class);

	@PersistenceContext
	private EntityManager manager;

	SimpleDateFormat sdtf=DateTimeFormatUtil.getSqlDateAndTimeFormat();

	private static final String allBank="select a.BankDetailId, a.EmpNo, a.BankName, a.Branch, a.IFSC, a.AccountNo, a.ValidFrom, a.ValidTo, a.BankStatus, em.EmpName, c.PISStatus, a.BankStatusCode from bank_detail a, employee em, pis_approval_status c where a.EmpNo=:empNo AND a.IsActive=1 AND a.EmpNo=em.EmpNo AND a.BankStatusCode=c.PISStatuscode ORDER BY a.BankDetailId DESC";

	public List<Object[]> findAll(String empNo) throws Exception{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(allBank);
			q.setParameter("empNo", empNo);
			bankDetailList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findAll "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}


	public long addBankdetail(BankDertails bankDertail) throws Exception{
		try {

			manager.persist(bankDertail);
			manager.flush();

			//			BankDertails bankD=manager.find(BankDertails.class, bankDertail.getBankId()-1);
			//			bankD.setIsActive(0);
			//			bankD.setValidTo(bankDertail.getValidFrom());
			//			manager.merge(bankD);

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO addBankdetail "+e);
			e.printStackTrace();
		}
		return bankDertail.getBankDetailId();
	}



	private static final String ONEBANK="SELECT a.BankDetailId, a.EmpNo, a.BankName, a.Branch, a.IFSC, a.AccountNo, a.ValidFrom, a.ValidTo, a.BankStatus, em.EmpName,a.BankStatusCode, a.Remarks, (SELECT ActionDate FROM bank_details_p_transa WHERE BankTransactionId=(SELECT MAX(BankTransactionId) FROM bank_details_p_transa WHERE BankStatusCode='FWD' AND BankDetailId=:bankId)) AS ForwordDate, (SELECT ActionDate FROM bank_details_p_transa WHERE BankTransactionId=(SELECT MAX(BankTransactionId) FROM bank_details_p_transa WHERE BankStatusCode='VPA' AND BankDetailId=:bankId)) AS PAVeriDate, (SELECT EmpName FROM employee WHERE EmpNo=(SELECT ActionBy FROM bank_details_p_transa WHERE BankTransactionId=(SELECT MAX(BankTransactionId) FROM bank_details_p_transa WHERE BankStatusCode='VPA' AND BankDetailId=:bankId))) AS PAName FROM bank_detail a,employee em WHERE a.BankDetailId=:bankId  AND a.EmpNo=em.EmpNo";

	public Object[] findById(long bankId) throws Exception{
		Object[] oneBank=null;
		try {
			Query query = manager.createNativeQuery(ONEBANK);
			query.setParameter("bankId", bankId);
			oneBank =(Object[]) query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findById "+e);
			e.printStackTrace();
		}
		return oneBank;
	}

	public BankDertails findBankById(long bankId) throws Exception{
		BankDertails oneBank=null;
		try {
			oneBank=manager.find(BankDertails.class, bankId);
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findBankById "+e);
			e.printStackTrace();
		}
		return oneBank;
	}

	public long editBankdetail(BankDertails bankDertail, long bankId) throws Exception{
		long l=0;
		try {
			BankDertails BankD = manager.find(BankDertails.class, bankId);
			BankD.setBankName(bankDertail.getBankName());
			BankD.setBranch(bankDertail.getBranch());
			BankD.setIFSC(bankDertail.getIFSC());
			BankD.setAccountNo(bankDertail.getAccountNo());
			BankD.setModifiedBy(bankDertail.getModifiedBy());
			BankD.setModifiedDate(bankDertail.getModifiedDate());
			BankD.setValidFrom(bankDertail.getValidFrom());
			manager.merge(BankD);
			l=BankD.getBankDetailId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO editBankdetail "+e);
			e.printStackTrace();
		}

		return l;
	}




	private static final String DGMALLBANK="SELECT a.BankDetailId, a.EmpNo, a.BankStatus, em.EmpName, a.BankStatusCode, c.PISStatus FROM bank_detail a, employee em, division_master b, pis_approval_status c ,dgm_master d WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND a.BankStatusCode='FWD' AND em.DivisionId=b.DivisionId AND b.DGMId=d.DGMId AND d.DGMEmpNo=:empNo AND a.BankStatusCode=c.PisStatusCode";

	public List<Object[]> findDGMBankList(String empNo) throws Exception{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(DGMALLBANK);
			q.setParameter("empNo", empNo);
			bankDetailList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findDGMBankList "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}



	private static final String GETPANDAADMINEMPNOS="SELECT DISTINCT a.EmpNo FROM employee a, pis_admins b WHERE a.EmpNo=b.PandAAdmin AND b.IsActive=1 LIMIT 1";
	@Override
	public List<String> GetPandAAdminEmpNos()throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETPANDAADMINEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}
		catch (Exception e) 
		{
			logger.error(new Date()  + "Inside DAO GetPandAAdminEmpNos " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}

	private static final String GETEMPDGMEMPNO  ="SELECT dgmempno FROM dgm_master";
	@Override
	public List<String> GetEmpDGMEmpNo() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDGMEMPNO);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list;
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}

	private static final String ALLBANKS="SELECT a.BankDetailId, a.EmpNo, a.BankStatus, em.EmpName, a.BankStatusCode, c.PISStatus FROM bank_detail a, employee em,  pis_approval_status c WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND a.BankStatusCode='VDG' AND a.BankStatusCode=c.PisStatusCode";

	public List<Object[]> findPAndBankList() throws Exception{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(ALLBANKS);
			bankDetailList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO findDGMBankList "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}


	public long editBankdetail(BankDertails bankDertail) throws Exception{
		try {
			manager.merge(bankDertail);
			manager.flush();

			return bankDertail.getBankDetailId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO editBankdetail "+e);
			e.printStackTrace();
		}
		return 0;
	}


	public long addtBankDetChangeTransa(BankDetChangeTransa bankDetChangeTransa) throws Exception{
		try {
			manager.persist(bankDetChangeTransa);
			manager.flush();

			return bankDetChangeTransa.getBankTransactionId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addtBankDetChangeTransa "+e);
			e.printStackTrace();
		}
		return 0;
	}

	public long edittBankDetChangeTransa(BankDetChangeTransa bankDetChangeTransa) throws Exception{
		try {
			manager.merge(bankDetChangeTransa);
			manager.flush();

			return bankDetChangeTransa.getBankTransactionId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO edittBankDetChangeTransa "+e);
			e.printStackTrace();
		}
		return 0;
	}

	private static final String EXPIREBANKDET="UPDATE bank_detail SET BankStatus ='E',ValidTo=:validToDate WHERE empno=:empNo AND ValidTo IS NULL AND BankStatus='A' AND IsActive=1";
	@Override
	public long expireBankDet(String empNo,Date validToDate) throws Exception{
		try {
			Query query = manager.createNativeQuery(EXPIREBANKDET);
			query.setParameter("empNo", empNo);
			query.setParameter("validToDate", validToDate);
			return query.executeUpdate();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO expireBankDet "+e);
			e.printStackTrace();
		}
		return 0;
	}

	private static final String BANKTRANSABYID="SELECT tra.BankTransactionId,emp.empno,emp.empname,des.designation,tra.ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor  FROM bank_details_p_transa tra, pis_approval_status sta,employee emp,employee_desig des,bank_detail ba WHERE ba.BankDetailId = tra.BankDetailId AND tra.BankStatusCode = sta.PisStatusCode AND tra.ActionBy=emp.empno AND emp.desigid=des.desigid AND ba.BankDetailId =:bankId ORDER BY actiondate";
	@Override
	public List<Object[]> bankTransaById(long bankId) throws Exception
	{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery(BANKTRANSABYID);
			query.setParameter("bankId", bankId);
			bankDetailList= query.getResultList();
		}catch(Exception e) {
			logger.error(new Date() +"Inside DAO bankTransaById "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}

	private static final String GETDGMEMPNO  ="SELECT dgm.dgmempno FROM employee e, division_master dm,dgm_master dgm WHERE e.divisionid=dm.divisionid AND dm.dgmid=dgm.dgmid AND e.empno=:empno";

	@Override
	public String GetDGMEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETDGMEMPNO);
			query.setParameter("empno", empno);
			return (String) query.getSingleResult();
						
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDGMEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}

	public long addNotification(EMSNotification notification) throws Exception{
		try {
			manager.persist(notification);
			manager.flush();

			return notification.getNotificationId();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO addNotification "+e);
			e.printStackTrace();
		}
		return 0;
	}

	private static final String FINDEMPBYEMPNO  ="FROM Employee WHERE EmpNo=:EmpNo";
	@Override
	public Employee findEmpByEmpNo(String empNo) throws Exception{
		Employee employee=null;
		try {			
			Query query= manager.createQuery(FINDEMPBYEMPNO);
			query.setParameter("EmpNo", empNo);
			employee= (Employee) query.getSingleResult();

		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO findEmpByEmpNo " + e);
			e.printStackTrace();

		}		
		return employee;
	}
	
	private static final String ALLACTIVEBANKS="SELECT a.BankDetailId, a.EmpNo, em.EmpName, a.BankName, a.Branch, a.IFSC, a.AccountNo, a.ValidFrom FROM bank_detail a, employee em,  pis_approval_status c WHERE a.IsActive=1 AND a.EmpNo=em.EmpNo AND a.BankStatus='A'  AND a.BankStatusCode='VPA' AND a.BankStatusCode=c.PisStatusCode";
	
	@Override
	public List<Object[]> allActiveBank() throws Exception{
		List<Object[]> bankDetailList=new ArrayList<Object[]>();
		try {
			Query q = manager.createNativeQuery(ALLACTIVEBANKS);
			bankDetailList=(List<Object[]>) q.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date() +"Inside DAO allActiveBank "+e);
			e.printStackTrace();
		}
		return bankDetailList;
	}
	
	private static String GETEMPNAMEANDDESI="SELECT em.EmpName, des.Designation FROM employee em, employee_desig des WHERE em.DesigId=des.DesigId AND em.EmpNo=:empNo";
	
	public Object[] getEmpNameAndDesi(String empNo) throws Exception{
		Object[] emp=null;
		try {
			Query q=manager.createNativeQuery(GETEMPNAMEANDDESI);
			q.setParameter("empNo", empNo);
			return (Object[])q.getSingleResult();
		}
		catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmpNameAndDesi "+  e);
		}
		return emp;
	}
}

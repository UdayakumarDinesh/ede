package com.vts.ems.pi.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.master.model.PisAdmins;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.model.PisAddressPerTrans;
import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pi.model.PisHometown;
import com.vts.ems.pi.model.PisHometownTrans;
import com.vts.ems.pi.model.PisMobileNumber;
import com.vts.ems.pi.model.PisMobileNumberTrans;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;

import net.bytebuddy.implementation.bytecode.Division;


@Transactional
@Repository
public class PIDaoImp implements PIDao{

	private static final Logger logger = LogManager.getLogger(PIDaoImp.class);
	
	@PersistenceContext
	EntityManager manager;
	
	
	private static final String RESADDRESSDETAILS = "SELECT a.empid,a.address_res_id,a.res_addr,a.from_res_addr,to_res_addr,a.mobile,a.QtrType,a.EmailOfficial,a.ext,a.state,a.city,a.pin,b.EmpName,b.EmpNo,a.ResAdStatus,c.PISStatus,c.PisStatusColor,c.PISStatusId,c.pisstatuscode FROM pis_address_res a,employee b,pis_approval_status c WHERE  a.IsActive=1 AND a.EmpId=b.EmpId AND a.PisStatuscode=c.PisStatuscode AND a.EmpId=:EmpId ORDER BY a.address_res_id DESC";
	@Override
	public List<Object[]> ResAddressDetails(String EmpId)throws Exception{
		List<Object[]> empData=null;
		try {
			Query query = manager.createNativeQuery(RESADDRESSDETAILS);
			query.setParameter("EmpId", EmpId);
			empData =(List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ResAddressDetails "+e);
	
		}
		return empData;
	}
	
	
	
	private static final String RESADDRESSDATA = "SELECT a.empid,a.address_res_id,a.res_addr,a.from_res_addr,a.mobile,a.QtrType,a.EmailOfficial,a.ext,a.state,a.city,a.pin,b.EmpName,b.EmpNo,a.ResAdStatus,a.PISStatusCode,a.remarks FROM pis_address_res a,employee b WHERE a.address_res_id=:addressResId AND a.IsActive=1 AND a.EmpId=b.EmpId";
	@Override
	public Object[] ResAddressFormData(String addressResId)throws Exception{
		Object[] empData=null;
		try {
			Query query = manager.createNativeQuery(RESADDRESSDATA);
			query.setParameter("addressResId", addressResId);
			empData =(Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ResAddressFormData "+e);
	
		}
		return empData;
	}
	
	private static final String PERADDRESSDETAILS = "SELECT a.empid,a.address_per_id,a.per_addr,a.from_per_addr,to_per_addr,a.mobile,a.state,a.city,a.pin,b.EmpName,b.EmpNo,a.PerAdStatus,c.PISStatus,c.pisStatusColor,c.PISStatusId,c.pisstatuscode FROM pis_address_per a,employee b,pis_approval_status c WHERE a.EmpId=:EmpId AND a.IsActive=1 AND a.EmpId=b.EmpId AND a.PISStatusCode=c.PISStatuscode ORDER BY a.address_per_id DESC";
	@Override
	public List<Object[]> PermanentAddressDetails(String EmpId)throws Exception{
		List<Object[]> empData=null;
		try {
			Query query = manager.createNativeQuery(PERADDRESSDETAILS);
			query.setParameter("EmpId", EmpId);
			empData =(List<Object[]>) query.getResultList();
			return empData;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PermanentAddressDetails "+e);
			return null;
		}
	}
	
	private static final String PERADDRESSDATA = "SELECT a.empid,a.address_per_id,a.per_addr,a.city,a.state,a.pin,a.from_per_addr,a.mobile,b.EmpName,b.EmpNo,a.PerAdStatus,a.PISStatuscode,a.Remarks FROM pis_address_per a,employee b WHERE a.address_per_id=:addressPerId AND a.IsActive=1 AND a.EmpId=b.EmpId";
	@Override
	public Object[] PerAddressFormData(String addressPerId)throws Exception{
		Object[] empData=null;
		try {
			Query query = manager.createNativeQuery(PERADDRESSDATA);
			query.setParameter("addressPerId", addressPerId);
			empData =(Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PerAddressFormData "+e);
	
		}
		return empData;
	}
	
	private static final String PERADDRESS="FROM AddressPer WHERE address_per_id=:address_per_id";
	@Override
	public AddressPer getPerAddressData(String addressperid)throws Exception{
		AddressPer addres=null;
		try {
			Query query = manager.createQuery(PERADDRESS);
			query.setParameter("address_per_id", Long.parseLong(addressperid));
			addres = (AddressPer) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getPerAddressData "+e);
			e.printStackTrace();
		}		
		return addres;
	}
	
	private static final String TOADDRESSDATEID = "SELECT address_res_id,empid FROM pis_address_res WHERE empid=:EmpId AND to_res_addr IS NULL AND ResAdStatus='E' "; 
	@Override
	public Object[] ResToAddressId(String EmpId)throws Exception{
		Object[] IdData=null;
		try {
			Query query = manager.createNativeQuery(TOADDRESSDATEID);
			query.setParameter("EmpId", EmpId);
			IdData =(Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO ResToAddressId "+e);
	
		}
		return IdData;
	}
	
	private static final String UPDATERESTODATE = "UPDATE pis_address_res SET to_res_addr=:to_res_addr WHERE address_res_id=:address_res_id";

	@Override
	public long ResUpdatetoDate(Date toDate,String resAddressId) throws Exception{
		try {
	
			Query query = manager.createNativeQuery(UPDATERESTODATE);
			query.setParameter("to_res_addr", toDate);
			query.setParameter("address_res_id", resAddressId);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO ResUpdatetoDate "+e);
			e.printStackTrace();
		}	
		return 0L;
		
	}
	
	@Override
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception
	{
		AddressRes intimate= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<AddressRes> cq= cb.createQuery(AddressRes.class);
			Root<AddressRes> root= cq.from(AddressRes.class);					
			Predicate p1=cb.equal(root.get("address_res_id") , Long.parseLong(resaddressid));
			cq=cq.select(root).where(p1);
			TypedQuery<AddressRes> allquery = manager.createQuery(cq);
			intimate= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO ResAddressIntimated " + e);
		}
		return intimate;
	}
	
	@Override
	public AddressRes getResAddressDet(String resaddressid) throws Exception
	{
		try {
			return manager.find(AddressRes.class, Long.parseLong(resaddressid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getResAddressDet " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String ADDRESSAPPROVALAUTH  ="SELECT e.empno,e.empname,ed.desigid, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.desigid = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType";
	@Override
	public Object[] AddressIntimationAuth(String Logintype) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(ADDRESSAPPROVALAUTH);
			query.setParameter("loginType", Logintype);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO AddressIntimationAuth " + e);
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public long NotificationAdd(EMSNotification notification ) throws Exception
	{
		try {
			manager.persist(notification);
			manager.flush();
			
			return notification.getNotificationId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO NotificationAdd " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long AddressResEdit(AddressRes addressRes) throws Exception
	{
		try {
			manager.merge(addressRes);
			manager.flush();
			
			return addressRes.getAddress_res_id();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO AddressResEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String GETEMPDATA="FROM Employee WHERE EmpId=:EmpId";
	@Override
	public Employee getEmpData(String EmpId)throws Exception
	{
		Employee emp=null;
		try {
			Query query = manager.createQuery(GETEMPDATA);
			query.setParameter("EmpId", Long.parseLong(EmpId));
			emp = (Employee) query.getSingleResult();
			return emp;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmpData "+e);
			e.printStackTrace();
			return null;
		}				
	}
	
	private static final String GETCEOEMPNO  ="SELECT empno FROM lab_master, employee WHERE labauthorityid=empid";
	@Override
	public String GetCEOEmpNo() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETCEOEMPNO);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetCEOEmpNo " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String GETDGMEMPNOS  ="SELECT dgmempno FROM dgm_master WHERE isactive=1";
	@Override
	public List<String> GetDGMEmpNos() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETDGMEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDGMEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
		
	}
	
	
	private static final String GETDHEMPNOS  ="SELECT divisionheadid FROM division_master WHERE isactive=1";
	@Override
	public List<String> GetDHEmpNos() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETDHEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDHEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}
		
	}
	
	private static final String GETGHEMPNOS  ="SELECT Groupheadid FROM division_Group WHERE isactive=1;";
	@Override
	public List<String> GetGHEmpNos() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETGHEMPNOS);
			List<String> list =  (List<String>)query.getResultList();
			return list;			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetGHEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}		
	}
	
	private static final String GETEMPGHEMPNO  ="SELECT dg.groupheadid FROM employee e, division_group dg WHERE e.groupid=dg.groupid AND e.empno=:empno";
	@Override
	public String GetEmpGHEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPGHEMPNO);
			query.setParameter("empno", empno);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpGHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	
	private static final String GETEMPDHEMPNO  ="SELECT dm.divisionheadid FROM employee e, division_master dm WHERE e.divisionid=dm.divisionid AND e.empno=:empno";
	@Override
	public String GetEmpDHEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDHEMPNO);
			query.setParameter("empno", empno);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	
	private static final String GETEMPDGMEMPNO  ="SELECT dgm.dgmempno FROM employee e, division_master dm,dgm_master dgm WHERE e.divisionid=dm.divisionid AND dm.dgmid=dgm.dgmid AND e.empno=:empno";
	@Override
	public String GetEmpDGMEmpNo(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDGMEMPNO);
			query.setParameter("empno", empno);
			List<String> list =  (List<String>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDHEmpNo " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String RESADDRESSAPPROVALSLIST  ="CALL Pis_ResAddress_approval (:EmpNo,:LoginType);";
	@Override
	public List<Object[]> ResAddressApprovalsList(String EmpNo,String LoginType) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(RESADDRESSAPPROVALSLIST);
			query.setParameter("EmpNo", EmpNo);
			query.setParameter("LoginType", LoginType);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ResAddressApprovalsList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	@Override
	public DivisionMaster GetDivisionData(long DivisionId) throws Exception
	{
		return manager.find(DivisionMaster.class, DivisionId);
	}
	
	@Override
	public long AddressResTransactionAdd(PisAddressResTrans transaction) throws Exception
	{
		manager.persist(transaction);
		manager.flush();
		return transaction.getResTransactionId();
	}
	
	
	
	private static final String RESADDRESSTRANSACTIONLIST="SELECT tra.ResTransactionId,emp.empno,emp.empname,des.designation,tra.actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor  FROM pis_address_res_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_address_res par WHERE par.address_res_id = tra.address_res_id AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND par.address_res_id =:addressresid ORDER BY actiondate";
	@Override
	public List<Object[]> ResAddressTransactionList(String addressresid)throws Exception
	{
		Query query = manager.createNativeQuery(RESADDRESSTRANSACTIONLIST);
		query.setParameter("addressresid", Long.parseLong(addressresid));
		return (List<Object[]>) query.getResultList();
	}
	
	private static final String RESADDRESSTRANSACTIONAPPROVALDATA="SELECT tra.ResTransactionId,emp.empno,emp.empname,des.designation,MAX(tra.actiondate) AS actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor ,sta.pisstatuscode FROM pis_address_res_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_address_res par WHERE par.address_res_id = tra.address_res_id AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND sta.pisstatuscode IN ('FWD','VDG','VPA')AND par.address_res_id = :addressresid GROUP BY sta.pisstatuscode ORDER BY actiondate ASC";
	@Override
	public List<Object[]> ResAddressTransactionApprovalData(String addressresid)throws Exception
	{
		Query query = manager.createNativeQuery(RESADDRESSTRANSACTIONAPPROVALDATA);
		query.setParameter("addressresid", Long.parseLong(addressresid));
		return (List<Object[]>) query.getResultList();
	}
	
	@Override
	public long AddNotifications(EMSNotification notification) throws Exception
	{
		manager.persist(notification);
		manager.flush();
		return notification.getNotificationId();
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
	
	private static final String GETEMPDGMPANDAEMPNO  ="SELECT dgm.dgmempno , e2.empname FROM employee e, division_master dm,dgm_master dgm , employee e2 WHERE e.divisionid=dm.divisionid AND dm.dgmid=dgm.dgmid AND dgm.dgmempno=e2.empno AND e.empno=:empno";
	@Override
	public Object[] GetEmpDGMEmpName(String empno) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETEMPDGMPANDAEMPNO);
			query.setParameter("empno", empno);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetEmpDGMEmpName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String GETPANDAEMPNAME  ="SELECT DISTINCT a.EmpNo,a.EmpName FROM employee a, pis_admins b WHERE a.EmpNo=b.PandAAdmin AND b.IsActive=1 LIMIT 1";
	@Override
	public Object[] GetPandAEmpName() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GETPANDAEMPNAME);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetPandAEmpName " + e);
			e.printStackTrace();
			return null;
		}		
	}

	private static final String PERADDRESSTRANSACTIONAPPROVALDATA="SELECT tra.PerTransactionId,emp.empno,emp.empname,des.designation,MAX(tra.actiondate) AS actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor ,sta.pisstatuscode FROM pis_address_per_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_address_per par WHERE par.address_per_id = tra.address_per_id AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND sta.pisstatuscode IN ('FWD','VDG','VPA')AND par.address_per_id =:peraddressId GROUP BY sta.pisstatuscode ORDER BY actiondate ASC";
	@Override
	public List<Object[]> PerAddressTransactionApprovalData(String peraddressId) throws Exception {
		
		Query query = manager.createNativeQuery(PERADDRESSTRANSACTIONAPPROVALDATA);
		query.setParameter("peraddressId", Long.parseLong(peraddressId));
		return (List<Object[]>) query.getResultList();
	}
	
	@Override
	public AddressPer PerAddressIntimated(String peraddressid) throws Exception
	{
		AddressPer intimate= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<AddressPer> cq= cb.createQuery(AddressPer.class);
			Root<AddressPer> root= cq.from(AddressPer.class);					
			Predicate p1=cb.equal(root.get("address_per_id") , Long.parseLong(peraddressid));
			cq=cq.select(root).where(p1);
			TypedQuery<AddressPer> allquery = manager.createQuery(cq);
			intimate= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO PerAddressIntimated " + e);
		}
		return intimate;
	}
	
	private static final String PERTOADDRESSDATEID = "SELECT address_per_id,empid FROM pis_address_per WHERE empid=:EmpId AND to_per_addr IS NULL AND PerAdStatus='E' "; 
	@Override
	public Object[] PerToAddressId(String EmpId) throws Exception {
		
		Object[] IdData=null;
		try {
			Query query = manager.createNativeQuery(PERTOADDRESSDATEID);
			query.setParameter("EmpId", EmpId);
			IdData =(Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO PerToAddressId "+e);
	
		}
		return IdData;
	}

	private static final String UPDATEPERTODATE = "UPDATE pis_address_per SET to_per_addr=:to_per_addr WHERE address_per_id=:perAddressId";
	@Override
	public long PerUpdatetoDate(Date toDate, String perAddressId) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(UPDATEPERTODATE);
			query.setParameter("to_per_addr", toDate);
			query.setParameter("perAddressId", perAddressId);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO PerUpdatetoDate "+e);
			e.printStackTrace();
		}	
		return 0L;
	}

	@Override
	public long AddressPerEdit(AddressPer address) throws Exception {
		
		try {
			manager.merge(address);
			manager.flush();
			
			return address.getAddress_per_id();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO AddressPerEdit " + e);
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public long AddressPerTransactionAdd(PisAddressPerTrans transaction) throws Exception
	{
		manager.persist(transaction);
		manager.flush();
		return transaction.getPerTransactionId();
	}
	
	private static final String PERADDRESSTRANSACTIONLIST="SELECT tra.PerTransactionId,emp.empno,emp.empname,des.designation,tra.actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor  FROM pis_address_per_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_address_per par WHERE par.address_per_id = tra.address_per_id AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND par.address_per_id =:addressperid ORDER BY actiondate";
	@Override
	public List<Object[]> PerAddressTransactionList(String addressperid)throws Exception
	{
		Query query = manager.createNativeQuery(PERADDRESSTRANSACTIONLIST);
		query.setParameter("addressperid", Long.parseLong(addressperid));
		return (List<Object[]>) query.getResultList();
	}
	
	private static final String MOBILENUMBERDETAILS = "SELECT a.MobileNumberId,a.EmpNo,a.MobileNumber,a.AltMobileNumber,a.MobileFrom,a.MobileTo,a.MobileStatus,a.Remarks,b.EmpName,c.PISStatus,c.PisStatusColor,c.PISStatusId,c.pisstatuscode FROM pis_mobile_number a,employee b,pis_approval_status c WHERE  a.IsActive=1 AND a.EmpNo=b.EmpNo AND a.PisStatuscode=c.PisStatuscode AND a.EmpNo=:EmpNo ORDER BY a.MobileNumberId DESC";
	@Override
	public List<Object[]> MobileNumberDetails(String EmpNo)throws Exception{
		List<Object[]> mbdata=null;
		try {
			Query query = manager.createNativeQuery(MOBILENUMBERDETAILS);
			query.setParameter("EmpNo",EmpNo);
			mbdata =(List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + "Inside DAO MobileNumberDetails "+e);
	
		}
		return mbdata;
	}
	
	@Override
	public Long addMobileNumber(PisMobileNumber mobile)throws Exception{
		try {
			manager.persist(mobile);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO addMobileNumber "+e);
			e.printStackTrace();
		}
		return mobile.getMobileNumberId();
	}
	
	private static final String MOBILENUMBERDATA="FROM PisMobileNumber WHERE MobileNumberId=:mobileNumberId";

	@Override
	public PisMobileNumber getMobileNumberData(String mobileNumberId)throws Exception{
		PisMobileNumber mobile=null;
		try {
			Query query = manager.createQuery(MOBILENUMBERDATA);
			query.setParameter("mobileNumberId", Long.parseLong(mobileNumberId));
			mobile = (PisMobileNumber) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getMobileNumberData "+e);
			e.printStackTrace();
		}		
		return mobile;
	}
	
	@Override
	public PisMobileNumber getMobileDataById(long mobilenumberid) throws Exception {
		PisMobileNumber number = null;
		try {
			CriteriaBuilder cb = manager.getCriteriaBuilder();
			CriteriaQuery<PisMobileNumber> cq = cb.createQuery(PisMobileNumber.class);
			Root<PisMobileNumber> root = cq.from(PisMobileNumber.class);
			Predicate p1 = cb.equal(root.get("MobileNumberId"), mobilenumberid);
			cq = cq.select(root).where(p1);
			TypedQuery<PisMobileNumber> allquery = manager.createQuery(cq);
			number = allquery.getResultList().get(0);

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getMobileDataById "+e);
			e.printStackTrace();
		}
		return number;
	}
	
	@Override
	public Long EditMobileNumber(PisMobileNumber mobile) throws Exception {
	
		try {
			manager.merge(mobile);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditResAddress "+e);
			e.printStackTrace();
		}
		return mobile.getMobileNumberId();
	}
	
	private static final String MOBILEFORMDATA = "SELECT a.MobileNumberId,a.EmpNo,a.MobileNumber,a.AltMobileNumber,a.MobileFrom,a.MobileStatus,a.Remarks,a.PisStatusCode,b.EmpName FROM pis_mobile_number a,employee b WHERE a.MobileNumberId=:mobileNumberId AND a.IsActive=1 AND a.EmpNo=b.EmpNo";
	@Override
	public Object[] MobileFormData(String mobileNumberId)throws Exception{
		Object[] mbData=null;
		try {
			Query query = manager.createNativeQuery(MOBILEFORMDATA);
			query.setParameter("mobileNumberId", mobileNumberId);
			mbData =(Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO MobileFormData "+e);
	
		}
		return mbData;
	}
	
	private static final String MOBILETRANSACTIONLIST="SELECT tra.MobTransactionId,emp.empno,emp.empname,des.designation,tra.actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor  FROM pis_mobile_number_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_mobile_number par WHERE par.MobileNumberId = tra.MobileNumberId AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND par.MobileNumberId =:mobilenumberid ORDER BY actiondate";
	@Override
	public List<Object[]> MobileTransactionList(String mobilenumberid)throws Exception
	{
		Query query = manager.createNativeQuery(MOBILETRANSACTIONLIST);
		query.setParameter("mobilenumberid", Long.parseLong(mobilenumberid));
		return (List<Object[]>) query.getResultList();
	}
	
	private static final String MOBILETRANSACTIONAPPROVALDATA="SELECT tra.MobTransactionId,emp.empno,emp.empname,des.designation,MAX(tra.actiondate) AS actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor ,sta.pisstatuscode FROM pis_mobile_number_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_mobile_number par WHERE par.MobileNumberId = tra.MobileNumberId AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND sta.pisstatuscode IN ('FWD','VDG','VPA')AND par.MobileNumberId =:mobileNumberId GROUP BY sta.pisstatuscode ORDER BY actiondate ASC";
	@Override
	public List<Object[]> MobileTransactionApprovalData(String mobileNumberId) throws Exception {
		
		Query query = manager.createNativeQuery(MOBILETRANSACTIONAPPROVALDATA);
		query.setParameter("mobileNumberId", Long.parseLong(mobileNumberId));
		return (List<Object[]>) query.getResultList();
	}
	
	private static final String MOBILEAPPROVALSLIST  =" CALL Pis_MobileNumber_Approval (:EmpNo,:LoginType)";
	@Override
	public List<Object[]> MobileApprovalsList(String EmpNo,String LoginType) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(MOBILEAPPROVALSLIST);
			query.setParameter("EmpNo", EmpNo);
			query.setParameter("LoginType", LoginType);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO MobileApprovalsList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	@Override
	public long MobileNumberTransactionAdd(PisMobileNumberTrans transaction) throws Exception
	{
		manager.persist(transaction);
		manager.flush();
		return transaction.getMobTransactionId();
	}
	
	private static final String UPDATEMOBILESTATUS = "UPDATE pis_mobile_number SET MobileStatus ='E' WHERE EmpNo=:EmpNo AND MobileTo IS NULL AND MobileStatus='A' AND IsActive=1";
	@Override
	public long MobileStatusUpdate(String EmpNo) throws Exception{
		try {
			Query query = manager.createNativeQuery(UPDATEMOBILESTATUS);
			query.setParameter("EmpNo", EmpNo);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO MobileStatusUpdate "+e);
			e.printStackTrace();
		}	
		return 0L;
		
	}
	
	private static final String MOBILENUMBERID = "SELECT MobileNumberId,EmpNo FROM pis_mobile_number WHERE EmpNo=:EmpNo AND MobileTo IS NULL AND MobileStatus='E'"; 
	@Override
	public Object[] GetMobileNumberId(String EmpNo) throws Exception {
		
		Object[] IdData=null;
		try {
			Query query = manager.createNativeQuery(MOBILENUMBERID);
			query.setParameter("EmpNo", EmpNo);
			IdData =(Object[]) query.getSingleResult();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetMobileNumberId "+e);
	
		}
		return IdData;
	}
	
	private static final String UPDATEMOBILETODATE = "UPDATE pis_mobile_number SET MobileTo=:MobileTo WHERE MobileNumberId=:MobileNumberId";
	@Override
	public long MobileNumberUpdatetoDate(Date MobileTo, String MobileNumberId) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(UPDATEMOBILETODATE);
			query.setParameter("MobileTo", MobileTo);
			query.setParameter("MobileNumberId", MobileNumberId);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO MobileNumberUpdatetoDate "+e);
			e.printStackTrace();
		}	
		return 0L;
	}
	
	private static final String UPDATEEMPLOYEEMOBILENUMBER = "UPDATE employee_details SET PhoneNo=:MobileNumber,AltPhoneNo=:AltMobileNumber WHERE EmpNo=:EmpNo";
	@Override
	public long UpdateEmployeeMobileNumber(String MobileNumber,String AltMobileNumber, String EmpNo) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(UPDATEEMPLOYEEMOBILENUMBER);
			query.setParameter("MobileNumber", MobileNumber);
			query.setParameter("AltMobileNumber", AltMobileNumber);
			query.setParameter("EmpNo", EmpNo);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO UpdateEmployeeMobileNumber "+e);
			e.printStackTrace();
		}	
		return 0L;
	}

	private static final String GETEMPDATABYEMPNO="FROM Employee WHERE EmpNo=:EmpNo";
	@Override
	public Employee getEmpDataByEmpNo(String EmpNo) throws Exception {
		
		Employee emp=null;
		try {
			Query query = manager.createQuery(GETEMPDATABYEMPNO);
			query.setParameter("EmpNo", EmpNo);
			emp = (Employee) query.getSingleResult();
			return emp;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getEmpDataByEmpNo "+e);
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public Long addHometown(PisHometown hometown)throws Exception{
		try {
			manager.persist(hometown);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO addHometown "+e);
			e.printStackTrace();
		}
		return hometown.getHometownId();
	}
	
	@Override
	public Long EditHometown(PisHometown hometown) throws Exception {
	
		try {
			manager.merge(hometown);
			manager.flush();

		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EditHometown "+e);
			e.printStackTrace();
		}
		return hometown.getHometownId();
	}
	
	@Override
	public PisHometown getHometownById(long hometownId) throws Exception {
		
		try {
			return manager.find(PisHometown.class, hometownId);	
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getHometownById "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String HOMETOWNDETAILS = "SELECT a.HometownId,a.EmpNo,a.Hometown,a.NearestRailwayStation,a.State,a.HometownStatus,a.Remarks,b.EmpName,c.PISStatus,c.PisStatusColor,c.PISStatusId,c.pisstatuscode,d.HomeAllowed FROM pis_hometown a,employee b,pis_approval_status c,employee_details d WHERE  a.IsActive=1 AND a.EmpNo=b.EmpNo AND a.PisStatuscode=c.PisStatuscode AND a.EmpNo=:EmpNo AND d.EmpNo=a.EmpNo ORDER BY a.HometownId DESC";
	@Override
	public List<Object[]> HometownDetails(String EmpNo)throws Exception{
		List<Object[]> hometowndata=null;
		try {
			Query query = manager.createNativeQuery(HOMETOWNDETAILS);
			query.setParameter("EmpNo",EmpNo);
			hometowndata =(List<Object[]>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + "Inside DAO HometownDetails "+e);
	
		}
		return hometowndata;
	}
	
	private static final String GROUPHEADEMPNO  ="SELECT emp.EmpNo,emp.EmpName FROM employee emp WHERE emp.EmpNo=(SELECT b.GroupHeadId FROM division_group b WHERE b.GroupId = (SELECT a.GroupId FROM employee a WHERE  a.EmpNo=:EmpNo) )";
	@Override
	public Object[] GetGroupHeadName(String EmpNo) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(GROUPHEADEMPNO);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetGroupHeadName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String DIVISIONHEADEMPNO  ="SELECT emp.EmpNo,emp.EmpName FROM employee emp WHERE emp.EmpNo=(SELECT d.DivisionHeadId FROM division_master d WHERE d.DivisionId = (SELECT a.DivisionId FROM employee a WHERE a.EmpNo=:EmpNo))";
	@Override
	public Object[] GetDivisionHeadName(String EmpNo) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(DIVISIONHEADEMPNO);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetDivisionHeadName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String CEOEMPNO  ="SELECT empno,empname FROM lab_master, employee WHERE labauthorityid=empid";
	@Override
	public Object[] GetCeoName() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(CEOEMPNO);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetCeoName " + e);
			e.printStackTrace();
			return null;
		}		
	}
	
	private static final String HOMETOWNFORMDATA = "SELECT a.HometownId,a.EmpNo,a.Hometown,a.NearestRailwayStation,a.State,a.HometownStatus,a.Remarks,a.PisStatusCode,b.EmpName,c.Designation FROM pis_hometown a,employee b,employee_desig c WHERE a.HometownId=:hometownId AND a.IsActive=1 AND a.EmpNo=b.EmpNo AND b.DesigId=c.DesigId";
	@Override
	public Object[] HometownFormData(String hometownId)throws Exception{

		try {
			Query query = manager.createNativeQuery(HOMETOWNFORMDATA);
			query.setParameter("hometownId", hometownId);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}	
			
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO HometownFormData "+e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String HOMETOWNTRANSACTIONAPPROVALDATA="SELECT tra.HomTransactionId,emp.EmpNo,emp.EmpName,des.Designation,MAX(tra.ActionDate) AS ActionDate,tra.Remarks,sta.PisStatus,sta.PisStatusColor,sta.PisStatusCode FROM pis_hometown_trans tra,pis_approval_status sta,employee emp,employee_desig des,pis_hometown par WHERE par.HometownId=tra.HometownId AND tra.PisStatusCode =sta.PisStatusCode AND tra.Actionby=emp.EmpNo AND emp.DesigId=des.DesigId AND sta.PisStatusCode IN ('FWD','VGI','VDI','VDG','VPA','APR') AND par.HometownId=:hometownId GROUP BY sta.PisStatusCode ORDER BY ActionDate ASC";
	@Override
	public List<Object[]> HometownTransactionApprovalData(String hometownId) throws Exception {
		
		Query query = manager.createNativeQuery(HOMETOWNTRANSACTIONAPPROVALDATA);
		query.setParameter("hometownId", Long.parseLong(hometownId));
		return (List<Object[]>) query.getResultList();
	}
	
	@Override
	public long HometownTransactionAdd(PisHometownTrans transaction) throws Exception
	{
		manager.persist(transaction);
		manager.flush();
		return transaction.getHomTransactionId();
	}
	
	private static final String HOMETOWNAPPROVALSLIST  =" CALL Pis_Hometown_Approval(:EmpNo,:LoginType)";
	@Override
	public List<Object[]> HometownApprovalsList(String EmpNo,String LoginType) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(HOMETOWNAPPROVALSLIST);
			query.setParameter("EmpNo", EmpNo);
			query.setParameter("LoginType", LoginType);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO HometownApprovalsList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String HOMETOWNTRANSACTIONLIST="SELECT tra.HomTransactionId,emp.empno,emp.empname,des.designation,tra.actiondate,tra.remarks,sta.PisStatus,sta.PisStatusColor  FROM pis_hometown_trans tra, pis_approval_status sta,employee emp,employee_desig des,pis_hometown par WHERE par.HometownId = tra.HometownId AND tra.PisStatusCode = sta.PisStatusCode AND tra.actionby=emp.empno AND emp.desigid=des.desigid AND par.HometownId =:hometownid ORDER BY actiondate";
	@Override
	public List<Object[]> HometownTransactionList(String hometownid)throws Exception
	{
		Query query = manager.createNativeQuery(HOMETOWNTRANSACTIONLIST);
		query.setParameter("hometownid", Long.parseLong(hometownid));
		return (List<Object[]>) query.getResultList();
	}
	
	public static final String HOMETOWNAPPROVECOUNT = "SELECT COUNT(a.HometownId) FROM pis_hometown a WHERE (a.HometownStatus='A' OR a.HometownStatus='E' ) AND a.EmpNo=:EmpNo";
	@Override
	public BigInteger HometownApprovalCount(String EmpNo) throws Exception {
		try {
			Query query =manager.createNativeQuery(HOMETOWNAPPROVECOUNT);
			query.setParameter("EmpNo", EmpNo);
			 return (BigInteger)query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO HometownApprovalCount "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String UPDATEHOMETOWNSTATUS = "UPDATE pis_hometown SET HometownStatus ='E' WHERE EmpNo=:EmpNo AND HometownStatus='A' AND IsActive=1";
	@Override
	public long HometownStatusUpdate(String EmpNo) throws Exception{
		try {
			Query query = manager.createNativeQuery(UPDATEHOMETOWNSTATUS);
			query.setParameter("EmpNo", EmpNo);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO HometownStatusUpdate "+e);
			e.printStackTrace();
		}	
		return 0L;
		
	}
	
	private static final String INTIMATIONPENDINGLIST  ="CALL Intimation_Approval (:EmpNo);";
	@Override
	public List<Object[]> IntimationPendingList(String EmpNo) throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(INTIMATIONPENDINGLIST);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IntimationPendingList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String HOMETOWNALLOWEDEMPNO  ="SELECT DISTINCT EmpNo FROM employee_details WHERE HomeAllowed='Y'";
	@Override
	public List<String> GetHometownAllowedEmpNo() throws Exception
	{
		try {			
			Query query= manager.createNativeQuery(HOMETOWNALLOWEDEMPNO);
			List<String> list =  (List<String>)query.getResultList();
			return list;
		}
		catch (Exception e) 
		{
			logger.error(new Date()  + "Inside DAO GetHometownAllowedEmpNo " + e);
			e.printStackTrace();
			return new ArrayList<String>();
		}	
	}
	
	private static final String NAMEANDDESIG = "SELECT DISTINCT a.EmpNo,a.EmpName,b.Designation,d.PayLevel,c.BasicPay,c.Title FROM employee a,employee_desig b,employee_details c,pis_pay_level d WHERE a.DesigId=b.DesigId AND a.IsActive=1 AND a.EmpNo=c.EmpNo AND d.PayLevelId=c.PayLevelId AND a.EmpNo=:EmpNo LIMIT 1";
	@Override
	public Object[] getEmpNameDesig(String EmpNo) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(NAMEANDDESIG);
			query.setParameter("EmpNo", EmpNo);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getEmpNameDesig " + e);
			e.printStackTrace();
			return null;
		}		
	}

	private static final String INTIMATIONAPPROVEDLIST  ="CALL Intimation_Approved_List (:EmpNo,:FromDate,:ToDate);";
	@Override
	public List<Object[]> IntimationApprovedList(String EmpNo,String FromDate,String ToDate) throws Exception {
		
		try {			
			Query query= manager.createNativeQuery(INTIMATIONAPPROVEDLIST);
			query.setParameter("EmpNo", EmpNo);
			query.setParameter("FromDate", FromDate);
			query.setParameter("ToDate", ToDate);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IntimationApprovedList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String RESADDRESSREMARKSHISTORY  ="SELECT cat.address_res_id,cat.Remarks,cs.PisStatusCode,e.EmpName,ed.Designation FROM pis_approval_status cs,pis_address_res_trans cat,pis_address_res ca,employee e,employee_desig ed WHERE cat.ActionBy = e.EmpNo AND e.DesigId = ed.DesigId AND cs.PisStatusCode = cat.PisStatusCode AND ca.address_res_id = cat.address_res_id AND TRIM(cat.Remarks)<>'' AND ca.address_res_id=:resaddressid ORDER BY cat.ActionDate ASC";
	@Override
	public List<Object[]> resAddressRemarksHistory(String resaddressid) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(RESADDRESSREMARKSHISTORY);
			query.setParameter("resaddressid", resaddressid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO resAddressRemarksHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String PERADDRESSREMARKSHISTORY  ="SELECT cat.address_per_id,cat.Remarks,cs.PisStatusCode,e.EmpName,ed.Designation FROM pis_approval_status cs,pis_address_per_trans cat,pis_address_per ca,employee e,employee_desig ed WHERE cat.ActionBy = e.EmpNo AND e.DesigId = ed.DesigId AND cs.PisStatusCode = cat.PisStatusCode AND ca.address_per_id = cat.address_per_id AND TRIM(cat.Remarks)<>'' AND ca.address_per_id=:peraddressid ORDER BY cat.ActionDate ASC";
	@Override
	public List<Object[]> perAddressRemarksHistory(String peraddressid) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(PERADDRESSREMARKSHISTORY);
			query.setParameter("peraddressid", peraddressid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO perAddressRemarksHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String MOBILEREMARKSHISTORY  ="SELECT cat.MobileNumberId,cat.Remarks,cs.PisStatusCode,e.EmpName,ed.Designation FROM pis_approval_status cs,pis_mobile_number_trans cat,pis_mobile_number ca,employee e,employee_desig ed WHERE cat.ActionBy = e.EmpNo AND e.DesigId = ed.DesigId AND cs.PisStatusCode = cat.PisStatusCode AND ca.MobileNumberId = cat.MobileNumberId AND TRIM(cat.Remarks)<>'' AND ca.MobileNumberId=:MobileNumberId ORDER BY cat.ActionDate ASC";
	@Override
	public List<Object[]> mobNumberRemarksHistory(String MobileNumberId) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(MOBILEREMARKSHISTORY);
			query.setParameter("MobileNumberId", MobileNumberId);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO mobNumberRemarksHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String HOMETOWNREMARKSHISTORY  ="SELECT cat.HometownId,cat.Remarks,cs.PisStatusCode,e.EmpName,ed.Designation FROM pis_approval_status cs,pis_hometown_trans cat,pis_hometown ca,employee e,employee_desig ed WHERE cat.ActionBy = e.EmpNo AND e.DesigId = ed.DesigId AND cs.PisStatusCode = cat.PisStatusCode AND ca.HometownId = cat.HometownId AND TRIM(cat.Remarks)<>'' AND ca.HometownId=:HometownId ORDER BY cat.ActionDate ASC";
	@Override
	public List<Object[]> hometownRemarksHistory(String HometownId) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(HOMETOWNREMARKSHISTORY);
			query.setParameter("HometownId", HometownId);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO hometownRemarksHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String GETAPPROVALEMP="CALL tour_approvalflow(:empno)";
	@Override
	public Object[] GetApprovalFlowEmp(String empno)throws Exception
	{
		try {
			 Query query = manager.createNativeQuery(GETAPPROVALEMP);
			 query.setParameter("empno",empno);
			 List<Object[]> list = (List<Object[]>)query.getResultList();
			 if(list.size()>0) {
				 return list.get(0);
			 }
				return null;
		}catch (Exception e){
				logger.error(new Date() +"Inside DAO GetApprovalFlowEmp "+ e);
				e.printStackTrace();
				return null;
		}	
    }
}

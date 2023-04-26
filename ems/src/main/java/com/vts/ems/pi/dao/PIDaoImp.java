package com.vts.ems.pi.dao;

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
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;


@Transactional
@Repository
public class PIDaoImp implements PIDao{

	private static final Logger logger = LogManager.getLogger(PIDaoImp.class);
	
	@PersistenceContext
	EntityManager manager;
	
	
	private static final String RESADDRESSDETAILS = "SELECT a.empid,a.address_res_id,a.res_addr,a.from_res_addr,to_res_addr,a.mobile,a.QtrType,a.EmailOfficial,a.ext,a.state,a.city,a.pin,b.EmpName,b.EmpNo,a.ResAdStatus,a.submittedOn,a.VerifiedOn,a.ReceivedOn,a.ApprovedOn,c.PISStatus,c.StatusColor,a.PISStatusId FROM pis_address_res a,employee b,pis_status c WHERE a.EmpId=:EmpId AND a.IsActive=1 AND a.EmpId=b.EmpId AND a.PISStatusId=c.PISStatusId ORDER BY a.address_res_id DESC";
	@Override
	public List<Object[]> EmployeeAddressDetails(String EmpId)throws Exception{
		List<Object[]> empData=null;
		try {
			Query query = manager.createNativeQuery(RESADDRESSDETAILS);
			query.setParameter("EmpId", EmpId);
			empData =(List<Object[]>) query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeAddressDetails "+e);
	
		}
		return empData;
	}
	
	
	
	private static final String RESADDRESSDATA = "SELECT a.empid,a.address_res_id,a.res_addr,a.from_res_addr,a.mobile,a.QtrType,a.EmailOfficial,a.ext,a.state,a.city,a.pin,b.EmpName,b.EmpNo,a.ResAdStatus,a.submittedOn,a.VerifiedOn,a.ReceivedOn,a.ApprovedOn,a.PISStatusId FROM pis_address_res a,employee b WHERE a.address_res_id=:addressResId AND a.IsActive=1 AND a.EmpId=b.EmpId";
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
	
	private static final String PERADDRESSDETAILS = "SELECT a.empid,a.address_per_id,a.per_addr,a.from_per_addr,to_per_addr,a.mobile,a.state,a.city,a.pin,b.EmpName,b.EmpNo,a.PerAdStatus,a.submittedOn,a.VerifiedOn,a.ReceivedOn,a.ApprovedOn,c.PISStatus,c.StatusColor,a.PISStatusId FROM pis_address_per a,employee b,pis_status c WHERE a.EmpId=:EmpId AND a.IsActive=1 AND a.EmpId=b.EmpId AND a.PISStatusId=c.PISStatusId ORDER BY a.address_per_id DESC";
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
	
	private static final String PERADDRESSDATA = "SELECT a.empid,a.address_per_id,a.per_addr,a.city,a.state,a.pin,a.from_per_addr,a.mobile,b.EmpName,b.EmpNo,a.PerAdStatus,a.submittedOn,a.VerifiedOn,a.ReceivedOn,a.ApprovedOn,a.PISStatusId FROM pis_address_per a,employee b WHERE a.address_per_id=:addressPerId AND a.IsActive=1 AND a.EmpId=b.EmpId";
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
	
	private static final String TOADDRESSDATEID = "SELECT address_res_id,empid FROM pis_address_res WHERE empid=:EmpId AND to_res_addr IS NULL AND ApprovedOn IS NOT NULL"; 
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
}

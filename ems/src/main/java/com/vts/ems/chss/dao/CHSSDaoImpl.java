package com.vts.ems.chss.dao;

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
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.pis.model.Employee;


@Transactional
@Repository
public class CHSSDaoImpl implements CHSSDao {

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
			
	@PersistenceContext
	EntityManager manager;
	
	private static final String FAMILYDETAILSLIST = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.IsActive = 1 AND fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND fs.family_status_id IN (1, 2) AND empid = :empid ";
	
	@Override
	public List<Object[]> familyDetailsList(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO familyDetailsList");
		Query query =manager.createNativeQuery(FAMILYDETAILSLIST);
		List<Object[]> resultList = new ArrayList<Object[]>();		
		query.setParameter("empid", empid);
		resultList = (List<Object[]>)query.getResultList();				
		return resultList;
	}
	
	private static final String FAMILYMEMBERDATA = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.IsActive = 1 AND fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND fs.family_status_id IN (1, 2) AND family_details_id = :familydetailsid ";
	
	@Override
	public Object[] familyMemberData(String familydetailsid) throws Exception
	{
		logger.info(new Date() +"Inside DAO familyMemberData");
		Query query =manager.createNativeQuery(FAMILYMEMBERDATA);
		Object[] result = null;
		query.setParameter("familydetailsid", familydetailsid);
		
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	@Override
	public Employee getEmployee(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getEmployee");
		Employee employee= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<Employee> cq= cb.createQuery(Employee.class);
			Root<Employee> root= cq.from(Employee.class);					
			Predicate p1=cb.equal(root.get("EmpId") , Long.parseLong(empid));
			cq=cq.select(root).where(p1);
			TypedQuery<Employee> allquery = manager.createQuery(cq);
			employee= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return employee;
	}
	
	@Override
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTreatTypeList");
		List<CHSSTreatType> list= new ArrayList<CHSSTreatType>(); 
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTreatType> cq= cb.createQuery(CHSSTreatType.class);
			Root<CHSSTreatType> root= cq.from(CHSSTreatType.class);
			
			TypedQuery<CHSSTreatType> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public long CHSSApplyAdd(CHSSApply apply ) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApplyAdd");
		manager.persist(apply);
		manager.flush();
		
		return apply.getCHSSApplyId();
	}
	
	@Override
	public long CHSSBillAdd(CHSSBill bill) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBillAdd");
		manager.persist(bill);
		manager.flush();
		
		return bill.getBillId();
	}
	
	@Override
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApplied");
		CHSSApply apply= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSApply> cq= cb.createQuery(CHSSApply.class);
			Root<CHSSApply> root= cq.from(CHSSApply.class);					
			Predicate p1=cb.equal(root.get("CHSSApplyId") , Long.parseLong(chssapplyid));
			cq=cq.select(root).where(p1);
			TypedQuery<CHSSApply> allquery = manager.createQuery(cq);
			apply= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return apply;
	}
	
	private static final String CHSSAPPLIEDDATA = "SELECT ca.chssapplyid,  ca.EmpId,  ca.patientid,  ca.isself,  ca.FollowUp,  ca.chssnewid,  ca.chsstype,  ca.TreatTypeId,  ca.noenclosures,  ca.chssstatus,   ct.TreatmentName,  ca.isactive,  fd.member_name,  fd.relation_id,  fr.relation_name,ca.CHSSApplyDate FROM  chss_apply ca,  pis_emp_family_details fd,  pis_emp_family_relation fr,  chss_treattype ct WHERE ca.TreatTypeId = ct.TreatTypeId AND ca.IsSelf = 'N'  AND ca.PatientId = fd.family_details_id  AND fd.relation_id = fr.relation_id  AND ca.CHSSApplyId = :CHSSApplyId UNION SELECT   ca.chssapplyid,  ca.EmpId,  ca.patientid,  ca.isself,  ca.FollowUp,  ca.chssnewid,  ca.chsstype,  ca.TreatTypeId,  ca.noenclosures,  ca.chssstatus,   ct.TreatmentName,  ca.isactive,   e.EmpName,  '0' AS 'relation_id',  'SELF' AS 'relation_name' ,ca.CHSSApplyDate FROM chss_apply ca,  employee e,  chss_treattype ct WHERE ca.TreatTypeId = ct.TreatTypeId AND ca.IsSelf = 'Y'  AND ca.PatientId = e.EmpId  AND ca.CHSSApplyId = :CHSSApplyId";
	
	@Override
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSAppliedData");
		try {
			Query query = manager.createNativeQuery(CHSSAPPLIEDDATA);
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CHSSBILLSLIST = "SELECT  cb.BillId, cb.CHSSApplyId,cb. BillNo,cb.CenterName,cb.BillDate,cb.BillAmount  FROM chss_bill cb WHERE cb.isactive=1 and cb.CHSSApplyId = :CHSSApplyId";
	
	@Override
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBillsList");
		try {
			Query query = manager.createNativeQuery(CHSSBILLSLIST);
			query.setParameter("CHSSApplyId", chssapplyid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	
	private static final String EMPCHSSLIST = "SELECT ca.chssapplyid,  ca.EmpId,  ca.patientid,  ca.isself,  ca.FollowUp,  ca.chssnewid,  ca.chsstype,  ca.TreatTypeId,  ca.noenclosures,  ca.chssstatus,   ct.TreatmentName,  ca.isactive,  fd.member_name,  fd.relation_id,  fr.relation_name, ca.CHSSApplyDate FROM  chss_apply ca,  pis_emp_family_details fd,  pis_emp_family_relation fr,  chss_treattype ct WHERE ca.TreatTypeId = ct.TreatTypeId AND ca.IsSelf = 'N'  AND ca.PatientId = fd.family_details_id  AND fd.relation_id = fr.relation_id  AND ca.EmpId=:empid UNION  SELECT   ca.chssapplyid,  ca.EmpId,  ca.patientid,  ca.isself,  ca.FollowUp,  ca.chssnewid,  ca.chsstype,  ca.TreatTypeId,  ca.noenclosures,  ca.chssstatus,   ct.TreatmentName,  ca.isactive,   e.EmpName,  '0' AS 'relation_id',  'SELF' AS 'relation_name',ca.CHSSApplyDate FROM chss_apply ca,  employee e,  chss_treattype ct WHERE ca.TreatTypeId = ct.TreatTypeId AND ca.IsSelf = 'Y'  AND ca.PatientId = e.EmpId  AND ca.EmpId = :empid";
	
	@Override
	public List<Object[]> empCHSSList(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO empCHSSList");
		try {
			Query query = manager.createNativeQuery(EMPCHSSLIST);
			query.setParameter("empid", empid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	@Override
	public CHSSBill getCHSSBill(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSBill");
		try {
			return manager.find(CHSSBill.class, Long.parseLong(billid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long CHSSBillEdit(CHSSBill bill) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBillEdit");
		try {
			manager.merge(bill);
			manager.flush();
			
			return bill.getBillId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public CHSSApply getCHSSApply(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSApply");
		try {
			return manager.find(CHSSApply.class, Long.parseLong(chssapplyid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long CHSSApplyEdit(CHSSApply apply) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApplyEdit");
		try {
			manager.merge(apply);
			manager.flush();
			
			return apply.getCHSSApplyId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
}

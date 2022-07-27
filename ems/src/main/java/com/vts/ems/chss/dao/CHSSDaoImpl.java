package com.vts.ems.chss.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSApplyTransaction;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSBillMedicine;
import com.vts.ems.chss.model.CHSSBillTests;
import com.vts.ems.chss.model.CHSSConsultMain;
import com.vts.ems.chss.model.CHSSBillConsultation;
import com.vts.ems.chss.model.CHSSBillIPDheads;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.chss.model.CHSSContingentTransaction;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSIPDClaimsInfo;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSBillMisc;
import com.vts.ems.chss.model.CHSSBillOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.model.EMSNotification;


@Transactional
@Repository
public class CHSSDaoImpl implements CHSSDao {

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
			
	@PersistenceContext
	EntityManager manager;
	
	private static final String FAMILYDETAILSLIST = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name,fd.gender,fd.med_dep_from FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.IsActive = 1 AND fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND fs.family_status_id IN (1, 2) AND fd.med_dep ='Y' AND empid = :empid ORDER BY fr.SerialNo ASC ";
	
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
	
	private static final String FAMILYMEMBERDATA = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name ,fd.med_dep_from FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND family_details_id = :familydetailsid ";
	
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
	
	private static final String EMPLOYEE="SELECT a.empid,a.empno,a.empname,a.desigid,b.basicpay,b.gender,b.bloodgroup,a.email,b.phoneno,b.paylevelid,b.dob,b.BasicPay,  ed.Designation,epl.paygrade FROM employee a, employee_details b,employee_desig ed, pis_pay_level epl WHERE a.empno=b.empno AND b.paylevelid=epl.paylevelid AND a.DesigId = ed.DesigId AND a.isactive='1' AND a.empid=:empid ";
	
	@Override
	public  Object[] getEmployee(String empid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getEmployee");
		Query query =manager.createNativeQuery(EMPLOYEE);
		Object[] result = null;
		query.setParameter("empid", empid);
		
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	@Override
	public CHSSOtherItems getCHSSOtherItems(String otheritemid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSPaybandRemlist");
		CHSSOtherItems remamountlist= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSOtherItems> cq= cb.createQuery(CHSSOtherItems.class);
			Root<CHSSOtherItems> root= cq.from(CHSSOtherItems.class);					
			Predicate p1=cb.equal(root.get("OtherItemId") , Long.parseLong(otheritemid));
			cq=cq.select(root).where(p1);
			TypedQuery<CHSSOtherItems> allquery = manager.createQuery(cq);
			remamountlist= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return remamountlist;
	}
	
	
	@Override
	public CHSSOtherPermitAmt getCHSSOtherPermitAmt(String otheritemid,long  basicpay) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSOtherPermitAmt");
		CHSSOtherPermitAmt list= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSOtherPermitAmt> cq= cb.createQuery(CHSSOtherPermitAmt.class);
			Root<CHSSOtherPermitAmt> root= cq.from(CHSSOtherPermitAmt.class);		
			
			
			Predicate p1=cb.equal(root.get("OtherItemId") , Integer.parseInt(otheritemid));
			Predicate p2=cb.lessThanOrEqualTo(root.get("BasicFrom") , basicpay);
			Predicate p3=cb.greaterThanOrEqualTo(root.get("BasicTo") , basicpay);
			Predicate p4=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2,p3,p4);
			
			TypedQuery<CHSSOtherPermitAmt> allquery = manager.createQuery(cq);
			list= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	@Override
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTreatTypeList");
		List<CHSSTreatType> list= new ArrayList<CHSSTreatType>(); 
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTreatType> cq= cb.createQuery(CHSSTreatType.class);
			cq.from(CHSSTreatType.class);			
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
	public long CHSSConsultMainAdd(CHSSConsultMain consultmain) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultMainAdd");
		manager.persist(consultmain);
		manager.flush();
		
		return consultmain.getCHSSConsultMainId();
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
	
	
	@Override
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSAppliedData");
		try {
			Query query = manager.createNativeQuery("CALL chss_claim_data(:CHSSApplyId);");
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public CHSSDoctorRates getDocterRate(String rateid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getDocterRate");
		try {
			return manager.find(CHSSDoctorRates.class, Integer.parseInt(rateid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBillsList");
		try {
			Query query = manager.createNativeQuery("CALL chss_claim_bills (:CHSSApplyId);");
			query.setParameter("CHSSApplyId", chssapplyid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}

	@Override
	public List<Object[]> CHSSConsultMainBillsList(String consultmainid, String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultMainBillsList");
		try {
			Query query = manager.createNativeQuery("CALL chss_consult_bills (:chssapplyid ,:consultmainid );");
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("consultmainid", consultmainid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CLAIMCONSULTATIONSCOUNT = "SELECT COUNT(cc.consultationid),'count' FROM chss_bill cb, chss_bill_consultation cc WHERE cb.billid=cc.BillId AND cc.isactive=1 AND cb.chssapplyid=:CHSSApplyId";
	
	@Override
	public Object[] claimConsultationsCount(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO claimConsultationsCount");
		try {
			Query query = manager.createNativeQuery(CLAIMCONSULTATIONSCOUNT);
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getResultList().get(0);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CLAIMMEDICINESCOUNT = "SELECT COUNT(chssmedicineid),'count' FROM chss_bill cb, chss_bill_medicine cm WHERE cb.billid=cm.BillId AND cm.isactive=1 AND cb.chssapplyid=:CHSSApplyId";
	
	@Override
	public Object[] claimMedicinesCount(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO claimMedicinesCount");
		try {
			Query query = manager.createNativeQuery(CLAIMMEDICINESCOUNT);
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getResultList().get(0);
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String EMPCHSSLIST = "CALL chss_emp_claims(:empid,:patientid,:fromdate,:todate,:isself)";
	
	@Override
	public List<Object[]> empCHSSList(String empid,String PatientId, LocalDate FromDate, LocalDate Todate, String IsSelf) throws Exception
	{
		logger.info(new Date() +"Inside DAO empCHSSList");
		try {
			Query query = manager.createNativeQuery(EMPCHSSLIST);
			query.setParameter("empid", empid);
			query.setParameter("patientid", PatientId);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", Todate);
			query.setParameter("isself", IsSelf);
			
//			System.out.println(empid+", "+PatientId+", "+FromDate+", "+Todate+", "+IsSelf);
			
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	@Override
	public CHSSConsultMain getCHSSConsultMain(String ConsultMainId) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSConsultMainEdit");
		try {
			return manager.find(CHSSConsultMain.class, Long.parseLong(ConsultMainId));
		}catch (Exception e) {
			e.printStackTrace();
			return null;
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
	public Object[] CHSSBill(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBill");
		try {
			Query query = manager.createNativeQuery("CALL chss_bill_data(:billid)");
			query.setParameter("billid", billid);
			return (Object[])query.getResultList().get(0);
			
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
	public int CHSSConsultMainDelete(String  consultmainid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultMainEdit");
		try {
			CriteriaBuilder criteriaBuilder  = manager.getCriteriaBuilder();
			CriteriaDelete<CHSSConsultMain> query = criteriaBuilder.createCriteriaDelete(CHSSConsultMain.class);
			Root<CHSSConsultMain> root = query.from(CHSSConsultMain.class);
			query.where(root.get("CHSSConsultMainId").in(Long.parseLong(consultmainid)));

			int result = manager.createQuery(query).executeUpdate();
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
		
	@Override
	public long CHSSConsultMainEdit(CHSSConsultMain consultmain) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultMainEdit");
		try {
			manager.merge(consultmain);
			manager.flush();
			
			return consultmain.getCHSSConsultMainId();
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
	public CHSSBillConsultation getCHSSConsultation(String consultationid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSConsultation");
		try {
			return manager.find(CHSSBillConsultation.class, Long.parseLong(consultationid));
			
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
	
	@Override
	public List<CHSSTestMain> CHSSTestMainList() throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTestMainList");
		List<CHSSTestMain> testmainlist= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTestMain> cq= cb.createQuery(CHSSTestMain.class);
			cq.from(CHSSTestMain.class);					
			TypedQuery<CHSSTestMain> allquery = manager.createQuery(cq);
			testmainlist= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return testmainlist;
	}
	
	@Override
	public List<CHSSTestSub> CHSSTestSubList() throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTestSubList");
		List<CHSSTestSub> testsublist= new ArrayList<CHSSTestSub>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTestSub> cq= cb.createQuery(CHSSTestSub.class);
			
			Root<CHSSTestSub> root=cq.from(CHSSTestSub.class);								
			Predicate p1=cb.notEqual(root.get("TestMainId") , 0);
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSTestSub> allquery = manager.createQuery(cq);
			testsublist= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return testsublist;
	}
	
	@Override
	public List<CHSSTestSub> CHSSTestSubListWithAyur() throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTestSubList");
		List<CHSSTestSub> testsublist= new ArrayList<CHSSTestSub>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTestSub> cq= cb.createQuery(CHSSTestSub.class);
			
			Root<CHSSTestSub> root=cq.from(CHSSTestSub.class);								
			Predicate p1=cb.equal(root.get("TestMainId") , 0);
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSTestSub> allquery = manager.createQuery(cq);
			testsublist= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return testsublist;
	}
	
	
	@Override
	public CHSSTestSub getCHSSTestSub(String testsubid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTestSubList");
		CHSSTestSub testsub= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTestSub> cq= cb.createQuery(CHSSTestSub.class);
			
			Root<CHSSTestSub> root=cq.from(CHSSTestSub.class);								
			Predicate p1=cb.equal(root.get("TestSubId") , Long.parseLong(testsubid));
			
			cq=cq.select(root).where(p1);
			
			
			TypedQuery<CHSSTestSub> allquery = manager.createQuery(cq);
			testsub= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return testsub;
	}
	
	@Override
	public long ConsultationBillAdd(CHSSBillConsultation consult) throws Exception
	{
		logger.info(new Date() +"Inside DAO ConsultationBillAdd");
		manager.persist(consult);
		manager.flush();
		
		return consult.getConsultationId();
	}
	
	@Override
	public List<CHSSBillConsultation> CHSSConsultationList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultationList");
		List<CHSSBillConsultation> list= new ArrayList<CHSSBillConsultation>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillConsultation> cq= cb.createQuery(CHSSBillConsultation.class);
			
			Root<CHSSBillConsultation> root=cq.from(CHSSBillConsultation.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSBillConsultation> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long ConsultationBillEdit(CHSSBillConsultation consult) throws Exception
	{
		logger.info(new Date() +"Inside DAO ConsultationBillEdit");
		try {
			manager.merge(consult);
			manager.flush();
			
			return consult.getConsultationId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long MedicinesBillAdd(CHSSBillMedicine medicine) throws Exception
	{
		logger.info(new Date() +"Inside DAO MedicinesBillAdd");
		manager.persist(medicine);
		manager.flush();
		
		return medicine.getCHSSMedicineId();
	}
	
	@Override
	public List<CHSSBillMedicine> CHSSMedicineList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMedicineList");
		List<CHSSBillMedicine> list= new ArrayList<CHSSBillMedicine>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillMedicine> cq= cb.createQuery(CHSSBillMedicine.class);
			
			Root<CHSSBillMedicine> root=cq.from(CHSSBillMedicine.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSBillMedicine> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public CHSSBillMedicine getCHSSMedicine(String CHSSMedicineId) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSMedicine");
		try {
			return manager.find(CHSSBillMedicine.class, Long.parseLong(CHSSMedicineId));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public CHSSBillTests getCHSSTest(String chsstestid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSTest");
		try {
			return manager.find(CHSSBillTests.class, Long.parseLong(chsstestid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long MedicineBillEdit(CHSSBillMedicine medicine) throws Exception
	{
		logger.info(new Date() +"Inside DAO MedicineBillEdit");
		try {
			manager.merge(medicine);
			manager.flush();
			
			return medicine.getCHSSMedicineId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	

	@Override
	public long TestsBillAdd(CHSSBillTests test) throws Exception
	{
		logger.info(new Date() +"Inside DAO TestsBillAdd");
		manager.persist(test);
		manager.flush();
		
		return test.getCHSSTestId();
	}
	
	@Override
	public List<CHSSBillTests> CHSSTestsList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMedicineList");
		List<CHSSBillTests> list= new ArrayList<CHSSBillTests>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillTests> cq= cb.createQuery(CHSSBillTests.class);
			
			Root<CHSSBillTests> root=cq.from(CHSSBillTests.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSBillTests> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public long TestBillEdit(CHSSBillTests test) throws Exception
	{
		logger.info(new Date() +"Inside DAO TestBillEdit");
		try {
			manager.merge(test);
			manager.flush();
			
			return test.getCHSSTestId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	
	
	
	
	@Override
	public long MiscBillAdd(CHSSBillMisc misc) throws Exception
	{
		logger.info(new Date() +"Inside DAO MiscBillAdd");
		manager.persist(misc);
		manager.flush();
		
		return misc.getChssMiscId();
	}
	@Override
	public CHSSBillMisc getCHSSMisc(String miscid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSMisc");
		try {
			return manager.find(CHSSBillMisc.class, Long.parseLong(miscid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<CHSSBillMisc> CHSSMiscList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMiscList");
		List<CHSSBillMisc> list= new ArrayList<CHSSBillMisc>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillMisc> cq= cb.createQuery(CHSSBillMisc.class);
			
			Root<CHSSBillMisc> root=cq.from(CHSSBillMisc.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSBillMisc> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long MiscBillEdit(CHSSBillMisc misc) throws Exception
	{
		logger.info(new Date() +"Inside DAO MiscBillEdit");
		try {
			manager.merge(misc);
			manager.flush();
			
			return misc.getChssMiscId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSDoctorRates> getCHSSDoctorRates(String treattypeid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSDoctorRates");
		List<CHSSDoctorRates> list= new ArrayList<CHSSDoctorRates>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSDoctorRates> cq= cb.createQuery(CHSSDoctorRates.class);
			
			Root<CHSSDoctorRates> root=cq.from(CHSSDoctorRates.class);	
			
			Predicate p1=cb.equal(root.get("IsActive") , 1);
			Predicate p2=cb.equal(root.get("TreatTypeId") , Integer.parseInt(treattypeid));
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSDoctorRates> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CHSSOtherItems> OtherItemsList() throws Exception
	{
		logger.info(new Date() +"Inside DAO OtherItemsList");
		List<CHSSOtherItems> list= new ArrayList<CHSSOtherItems>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSOtherItems> cq= cb.createQuery(CHSSOtherItems.class);
			
			Root<CHSSOtherItems> root=cq.from(CHSSOtherItems.class);	
			
			Predicate p1=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1);
			
			TypedQuery<CHSSOtherItems> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<CHSSBillOther> CHSSOtherList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSOtherList");
		List<CHSSBillOther> list= new ArrayList<CHSSBillOther>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillOther> cq= cb.createQuery(CHSSBillOther.class);
			
			Root<CHSSBillOther> root=cq.from(CHSSBillOther.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSBillOther> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public long OtherBillAdd(CHSSBillOther other) throws Exception
	{
		logger.info(new Date() +"Inside DAO OtherBillAdd");
		manager.persist(other);
		manager.flush();
		
		return other.getCHSSOtherId();
	}
	
	@Override
	public long OtherBillEdit(CHSSBillOther other) throws Exception
	{
		logger.info(new Date() +"Inside DAO OtherBillEdit");
		try {
			manager.merge(other);
			manager.flush();
			
			return other.getCHSSOtherId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public CHSSBillOther getCHSSOther(String otherid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSOther");
		try {
			return manager.find(CHSSBillOther.class, Long.parseLong(otherid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	private static final String CHSSCONSULTDATALIST = "SELECT   cc.ConsultationId,  cc.BillId,  cc.ConsultType,  cc.DocName,  cc.DocQualification,  cc.ConsultDate,  cc.ConsultCharge,  cc.ConsultRemAmount, cb.BillNo,  cb.BillDate, cc.Comments , cdr.docQualification AS 'Qualification'  FROM  chss_bill_consultation cc, chss_bill cb ,chss_doctor_rates cdr WHERE cc.isactive = 1 AND cb.isactive=1 AND cb.BillId = cc.BillId AND cc.docQualification=cdr.docrateid  AND cb.CHSSApplyId = :CHSSApplyId ";

	@Override
	public List<Object[]> CHSSConsultDataList(String CHSSApplyId) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultDataList");
		try {
			Query query= manager.createNativeQuery(CHSSCONSULTDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CHSSTESTSDATALIST = "SELECT   ct.CHSSTestId, ct.BillId,  ct.TestMainId,  ct.TestSubId,  ct.TestCost,ctm.TestMainName, cts.TestName,ct.TestRemAmount ,cb.BillNo,  cb.BillDate, cts.TestCode, ct.Comments FROM  chss_bill_tests ct,  chss_test_main ctm,  chss_test_sub cts,  chss_bill cb WHERE ct.isactive = 1 AND cb.isactive=1 AND ct.TestMainId = ctm.TestMainId  AND ct.TestSubId = cts.TestSubId  AND cb.BillId = ct.BillId  AND cb.CHSSApplyId = :CHSSApplyId";
	
	@Override
	public List<Object[]> CHSSTestsDataList(String CHSSApplyId) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTestsDataList");
		try {
			Query query= manager.createNativeQuery(CHSSTESTSDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSMEDICINEDATALIST = "SELECT   cm.CHSSMedicineId,   cm.BillId,  cm.MedicineName,  cm.MedicineCost, cm.MedQuantity,cm.presQuantity,cm.MedsRemAmount ,cb.BillNo,  cb.BillDate, cm.Comments FROM   chss_bill_medicine cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

	@Override
	public List<Object[]> CHSSMedicineDataList(String CHSSApplyId) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMedicineDataList");
		try {
			Query query= manager.createNativeQuery(CHSSMEDICINEDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSOTHERDATALIST = "SELECT co.CHSSOtherId,   co.BillId,  co.OtherItemId,  co.OtherItemCost,  coi.OtherItemName,co.OtherRemAmount  ,cb.BillNo,  cb.BillDate, co.Comments  FROM chss_bill_other co,chss_other_items coi, chss_bill cb WHERE co.isactive = 1 AND cb.isactive=1 AND  co.OtherItemId = coi.OtherItemId AND cb.BillId = co.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

	@Override
	public List<Object[]> CHSSOtherDataList(String CHSSApplyId) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSOtherDataList");
		try {
			Query query= manager.createNativeQuery(CHSSOTHERDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CHSSMISCDATALIST = "SELECT  cm.ChssMiscId,  cm.BillId,  cm.MiscItemName,  cm.MiscItemCost,cm.MiscRemAmount  ,cb.BillNo,  cb.BillDate, cm.Comments, cm.MiscCount  FROM  chss_bill_misc cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId =:CHSSApplyId";

	@Override
	public List<Object[]> CHSSMiscDataList(String CHSSApplyId) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSMiscDataList");
		try {
			Query query= manager.createNativeQuery(CHSSMISCDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String CHSSAPPLYNOCOUNT = "SELECT COUNT(chssapplyid),'count' AS 'count' FROM chss_apply WHERE chssapplyno LIKE :finYear ";

	@Override
	public String CHSSApplyNoCount(String finYear) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSMiscDataList");
		try {
			Query query= manager.createNativeQuery(CHSSAPPLYNOCOUNT);
			query.setParameter("finYear", finYear+"%");
			Object[] result= (Object[])query.getSingleResult();
			return result[0].toString();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	

	@Override
	public List<Object[]> CHSSApproveClaimList(String logintype,String empid) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSApproveClaimList");
		try {
			Query query= manager.createNativeQuery("CALL chss_claims_pending (:logintype, :empid);");
			query.setParameter("logintype", logintype);
			query.setParameter("empid", empid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Object[]> CHSSClaimListRep(String type, String fromdate, String todate) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSClaimListRep");
		try {
			Query query= manager.createNativeQuery("CALL chss_claims_rep (:fromdate,:todate,:type);");
			query.setParameter("type", type);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Object[]> CHSSBatchApproval(String logintype,String fromdate, String todate,String contingentid)throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBatchApproval");
		try {
			Query query = manager.createNativeQuery("CALL chss_claims_approve(:logintype, :fromdate , :todate,:contingentid);");
			query.setParameter("logintype", logintype);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			query.setParameter("contingentid", contingentid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSCONTINGENTNOCOUNT = "SELECT COUNT(ContingentId),'count' AS 'count' FROM chss_contingent WHERE ContingentBillNo LIKE :finYear ";

	@Override
	public String CHSSContingentNoCount(String finYear) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSContingentNoCount");
		try {
			Query query= manager.createNativeQuery(CHSSCONTINGENTNOCOUNT);
			query.setParameter("finYear", "CHSS"+finYear+"%");
			Object[] result= (Object[])query.getSingleResult();
			return result[0].toString();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long ContingentAdd(CHSSContingent contingent) throws Exception
	{
		logger.info(new Date() +"Inside DAO ContingentAdd");
		manager.persist(contingent);
		manager.flush();
		
		return contingent.getContingentId();
	}
	
	@Override
	public long CHSSContingentEdit(CHSSContingent contingent) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSContingentEdit");
		try {
			
			manager.merge(contingent);
			manager.flush();
			
			return contingent.getContingentId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	

	
	@Override
	public CHSSContingent getCHSSContingent(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSContingent");
		try {
			return manager.find(CHSSContingent.class, Long.parseLong(contingentid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public long CHSSApplyTransactionAdd(CHSSApplyTransaction transaction ) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApplyEdit");
		try {
			manager.persist(transaction);
			manager.flush();
			
			return transaction.getCHSSTransactionId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	@Override
	public long CHSSContingentTransactionAdd(CHSSContingentTransaction transaction ) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApplyEdit");
		try {
			manager.persist(transaction);
			manager.flush();
			
			return transaction.getContinTransactionId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
//	private static final String GETCHSSCONTINGENTLIST  ="SELECT cc.contingentid,cc.ContingentBillNo,cc.ContingentDate,ClaimsCount,cc.BillsCount,cc.ContingentStatusId,cc.Remarks ,cs.chssstatus FROM chss_contingent cc , chss_status cs WHERE cc.isactive=1 AND cc.ContingentStatusId = cs.chssstatusid ORDER BY cc.ContingentStatusId ASC";
	
	@Override
	public List<Object[]> getCHSSContingentList(String logintype,String fromdate,String todate) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSContingentList");
		
		try {
			Query query= manager.createNativeQuery("call chss_contingent_bills_list(:logintype,:fromdate,:todate)");
			query.setParameter("logintype", logintype);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String CONTINGENTAPPLYIDS  ="SELECT chssapplyid FROM chss_apply WHERE isactive=1 AND contingentid=:contingentid";
	
	@Override
	public List<Object> ContingentApplyIds(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ContingentApplyIds");
		
		try {
			Query query= manager.createNativeQuery(CONTINGENTAPPLYIDS);
			query.setParameter("contingentid", contingentid);
			return (List<Object>)query.getResultList();
			
		}catch (Exception e) {
			return new ArrayList<Object>();
		}
	}
	
	private static final String CHSSCONTINGENTDATA  ="SELECT cc.contingentid,cc.ContingentBillNo,cc.ContingentDate,ClaimsCount,cc.BillsCount,cc.ContingentStatusId,cc.Remarks ,cs.chssstatus,cc.billcontent, cc.ApprovalDate FROM chss_contingent cc , chss_status cs WHERE  cc.ContingentStatusId = cs.chssstatusid AND cc.contingentid= :contingentid ORDER BY cc.ContingentStatusId ASC";
	@Override
	public Object[] CHSSContingentData(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSContingentData");
		
		try {
			Query query= manager.createNativeQuery(CHSSCONTINGENTDATA);
			query.setParameter("contingentid", contingentid);
			return (Object[])query.getResultList().get(0);
			
		}catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<Object[]> CHSSContingentClaimList(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSContingentClaimList");
		
		try {
			Query query= manager.createNativeQuery("CALL chss_contingent_claims (:contingentid);");
			query.setParameter("contingentid", contingentid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			return new ArrayList<Object[]>();
		}
	}
	private static final String CHSSSTAUSDETAILS="SELECT a.chsstransactionid,f.empid,c.empname,d.designation,e.divisionname,a.actiondate,a.remark,b.chssstatus FROM chss_apply_transaction a, chss_status b,employee c,employee_desig d,division_master e ,chss_apply f WHERE a.chssstatusid=b.chssstatusid  AND f.chssapplyid=a.chssapplyid AND a.actionby=c.empid  AND c.desigid=d.desigid AND c.divisionid=e.divisionid AND a.chssapplyid=:chssapplyid";
	@Override
	public List<Object[]> CHSSStatusDetails(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSStatusDetails");
		
		try {
			Query query= manager.createNativeQuery(CHSSSTAUSDETAILS);
			query.setParameter("chssapplyid", chssapplyid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			return new ArrayList<Object[]>();
		}
	}
	private static final String BILLVALUE="SELECT COUNT(contingentid) FROM chss_contingent WHERE contingentbillno LIKE '=:bill%'";
	@Override
	public int getdata(String bill)throws Exception
	{
		try {
			Query query = manager.createNativeQuery("SELECT COUNT(contingentid) FROM chss_contingent WHERE contingentbillno LIKE '"+ bill +"%'");
			
			Object o = query.getSingleResult();

			Integer value = Integer.parseInt(o.toString());
			int result = value;

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	

	@Override
	public List<CHSSMedicinesList> getCHSSMedicinesList(String treattypeid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMedicineList");
		List<CHSSMedicinesList> list= new ArrayList<CHSSMedicinesList>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSMedicinesList> cq= cb.createQuery(CHSSMedicinesList.class);
			
			Root<CHSSMedicinesList> root=cq.from(CHSSMedicinesList.class);								
			Predicate p1=cb.equal(root.get("TreatTypeId"), Long.parseLong(treattypeid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSMedicinesList> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	private static final String GETCHSSCONSULTMAINLIST = "SELECT   ccm.CHSSConsultMainId,ccm.CHSSApplyId, ccm.DocName, ccm.ConsultDate,ccm.DocQualification, (SELECT COUNT(cb1.BillId) FROM chss_bill cb1 WHERE cb1.isactive=1 AND  ccm.CHSSConsultMainId = cb1.CHSSConsultMainId AND cb1.CHSSApplyId=:applyid ) AS 'billscount' FROM   chss_consult_main ccm WHERE ccm.IsActive = 1 AND  ccm.CHSSConsultMainId IN ( SELECT cb.CHSSConsultMainId FROM chss_bill cb WHERE cb.IsActive=1 AND cb.CHSSApplyId = :applyid ) AND  ccm.CHSSApplyId <> :applyid UNION SELECT ccm.CHSSConsultMainId, ccm.CHSSApplyId, ccm.DocName, ccm.ConsultDate,ccm.DocQualification, (SELECT COUNT(cb1.BillId) FROM chss_bill cb1 WHERE cb1.isactive=1 AND  ccm.CHSSConsultMainId = cb1.CHSSConsultMainId AND cb1.CHSSApplyId=:applyid ) AS 'billscount' FROM  chss_consult_main ccm WHERE ccm.IsActive = 1 AND  ccm.CHSSApplyId = :applyid ";
	
	@Override
	public List<Object[]> getCHSSConsultMainList(String applyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSConsultMainList");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(GETCHSSCONSULTMAINLIST);
			query.setParameter("applyid", applyid);
			list =  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;		
	}
	
	
	
	private static final String CHSSAPPROVALAUTHLIST  ="SELECT e.empid,  'PO',  e.EmpName,  ed.DesigCode,  ed.designation FROM  chss_contingent cc,  employee e,  employee_desig ed WHERE cc.PO = e.EmpId AND e.desigid = ed.DesigId  AND cc.ContingentId = :contingentid \r\n"
			+ "UNION \r\n"
			+ "SELECT e.empid,  'VO',  e.EmpName,  ed.DesigCode,  ed.designation FROM  chss_contingent cc,  employee e,   employee_desig ed WHERE cc.VO = e.EmpId AND e.desigid = ed.DesigId AND cc.ContingentId = :contingentid \r\n"
			+ "UNION \r\n"
			+ "SELECT e.empid,  'AO',  e.EmpName,  ed.DesigCode,  ed.designation FROM  chss_contingent cc,  employee e,  employee_desig ed WHERE cc.AO = e.EmpId AND e.desigid = ed.DesigId  AND cc.ContingentId = :contingentid \r\n"  
			+ "UNION \r\n"
			+ "SELECT e.empid,  'CEO',  e.EmpName,  ed.DesigCode,  ed.designation FROM  chss_contingent cc,  employee e,   employee_desig ed WHERE cc.CEO = e.EmpId AND e.desigid = ed.DesigId AND cc.ContingentId = :contingentid \r\n"
			+ "UNION \r\n"
			+ "SELECT e.empid,  'CEO-L',  e.EmpName,  ed.DesigCode,  ed.designation FROM  login l,  employee e,   employee_desig ed WHERE l.empid = e.EmpId AND e.desigid = ed.DesigId AND l.isactive=1 AND l.logintype='Z' LIMIT 1";
	@Override
	public List<Object[]> CHSSApprovalAuthList(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApprovalAuthList");
		try {
			
			Query query= manager.createNativeQuery(CHSSAPPROVALAUTHLIST);
			query.setParameter("contingentid", contingentid);
			return (List<Object[]>)query.getResultList();
			
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CHSSAPPROVALAUTH  ="SELECT e.empid,e.empname,ed.desigid, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.desigid = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType  ";
	@Override
	public Object[] CHSSApprovalAuth(String Logintype) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApprovalAuth");
		try {
			
			Query query= manager.createNativeQuery(CHSSAPPROVALAUTH);
			query.setParameter("loginType", Logintype);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long NotificationAdd(EMSNotification notification ) throws Exception
	{
		logger.info(new Date() +"Inside DAO NotificationAdd");
		try {
			manager.persist(notification);
			manager.flush();
			
			return notification.getNotificationId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String CONSULTATIONHISTORY  ="SELECT cc.ConsultationId,ca.CHSSApplyNo,cb.Billno, cc.ConsultType, cc.DocName, cdr.DocQualification, cc.ConsultDate, cc.ConsultCharge, cc.ConsultRemAmount FROM chss_apply ca, chss_apply ca1, chss_bill cb, chss_bill_consultation cc, chss_doctor_rates cdr WHERE ca.IsActive = 1 AND cb.isactive=1 AND cc.isactive=1 AND  cb.BillId = cc.BillId AND cb.CHSSApplyId = ca.CHSSApplyId AND cc.DocQualification=cdr.DocRateId AND ca.empid=ca1.empid AND ca.PatientId=ca1.PatientId AND ca.IsSelf = ca1.IsSelf AND ca.chssapplyid < ca1.chssapplyid AND ca1.TreatTypeId = ca.TreatTypeId AND ca.CHSSStatusId = 14 AND ca1.chssapplyid=:chssapplyid ";
	@Override
	public List<Object[]> ConsultationHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ConsultationHistory");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CONSULTATIONHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
	
	
	private static final String TESTSHISTORY  ="SELECT   ct.CHSSTestId,  ca.CHSSApplyNo,  cb.Billno,  ct.TestSubId,  cts.TestName, cts.TestCode,  ct.TestCost,  ct.TestRemAmount FROM  chss_apply ca,  chss_apply ca1,  chss_bill cb,  chss_bill_tests ct,  chss_test_sub cts  WHERE ca.IsActive = 1  AND cb.isactive = 1  AND ct.isactive = 1  AND cb.BillId = ct.BillId AND cb.CHSSApplyId = ca.CHSSApplyId  AND ct.TestSubId = cts.TestSubId  AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf AND ca.CHSSStatusId =14 AND ca.chssapplyid < ca1.chssapplyid AND ca1.chssapplyid = :chssapplyid";
	@Override
	public List<Object[]> TestsHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO TestsHistory");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(TESTSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String MEDICINESHISTORY  ="SELECT   cm.CHSSMedicineId ,  ca.CHSSApplyNo,  cb.Billno,  cm.MedicineName,  cm.PresQuantity,  cm.MedQuantity,  cm.MedicineCost,  cm.MedsRemAmount, cb.BillDate FROM   chss_apply ca,  chss_apply ca1,  chss_bill cb,  chss_bill_medicine cm   WHERE ca.IsActive = 1  AND cb.isactive = 1   AND cm.isactive = 1  AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = ca.CHSSApplyId   AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf  AND ca.CHSSStatusId =14 AND ca.chssapplyid < ca1.chssapplyid  AND ca1.TreatTypeId = ca.TreatTypeId  AND ca1.chssapplyid = :chssapplyid ";
	@Override
	public List<Object[]> MedicinesHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO MedicinesHistory");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(MEDICINESHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String OTHERSHISTORY  ="SELECT   co.CHSSOtherId,  ca.CHSSApplyNo,  cb.Billno,  co.OtherItemId,  coi.OtherItemName,  co.OtherItemCost,  co.OtherRemAmount FROM  chss_apply ca,  chss_apply ca1,  chss_bill cb,  chss_bill_other co,  chss_other_items coi WHERE ca.IsActive = 1  AND cb.isactive = 1  AND co.isactive = 1  AND cb.BillId = co.BillId  AND co.OtherItemId =coi.OtherItemId  AND cb.CHSSApplyId = ca.CHSSApplyId  AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf  AND ca.CHSSStatusId =14 AND ca.chssapplyid < ca1.chssapplyid  AND ca1.chssapplyid = :chssapplyid";
	@Override
	public List<Object[]> OthersHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO OthersHistory");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(OTHERSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String MISCITEMSHISTORY  ="SELECT   cmi.ChssMiscId,  ca.CHSSApplyNo,  cb.Billno,  cmi.MiscItemName,  cmi.MiscItemCost,  cmi.MiscRemAmount, cmi.MiscCount FROM  chss_apply ca,  chss_apply ca1,  chss_bill cb,   chss_bill_misc cmi WHERE ca.IsActive = 1  AND cb.isactive = 1  AND cmi.isactive = 1  AND cb.BillId = cmi.BillId    AND cb.CHSSApplyId = ca.CHSSApplyId  AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf   AND ca.CHSSStatusId = 14 AND ca.chssapplyid < ca1.chssapplyid  AND ca1.chssapplyid = :chssapplyid";
	@Override
	public List<Object[]> MiscItemsHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO MiscItemsHistory");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(MISCITEMSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String CONSULTBILLSCONSULTCOUNT  ="SELECT COUNT(cc.ConsultationId) AS 'consult count' , 'count' AS 'Count' FROM chss_bill_consultation cc, chss_bill cb WHERE cb.IsActive=1 AND cc.IsActive =1 AND cb.BillId = cc.BillId AND cb.BillId = :billid AND cb.CHSSConsultMainId =:chssconsultmainid AND cb.CHSSApplyId = :chssapplyid";
	@Override
	public Object[] ConsultBillsConsultCount(String consultmainid, String chssapplyid,String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ConsultBillsConsultCount");
		Object[] list = null;
		try {
			
			Query query= manager.createNativeQuery(CONSULTBILLSCONSULTCOUNT);
			query.setParameter("chssconsultmainid", consultmainid);
			query.setParameter("chssapplyid", chssapplyid);
			
			list=  (Object[])query.getSingleResult();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String CONSULTBILLSDELETE  ="UPDATE chss_bill SET isactive=0 WHERE CHSSConsultMainId =:consultmainid";
	@Override
	public int ConsultBillsDelete(String consultmainid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ConsultBillsDelete");
		try {
			Query query= manager.createNativeQuery(CONSULTBILLSDELETE);
			query.setParameter("consultmainid", consultmainid);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String PATIENTCONSULTHISTORY  ="SELECT  ccm.CHSSConsultMainId, ccm.CHSSApplyId, ccm.ConsultDate, ccm.DocName, ca.Ailment FROM  chss_apply ca,chss_apply ca1,  chss_consult_main ccm WHERE ca.CHSSApplyId = ccm.CHSSApplyId  AND ccm.isactive = 1  AND ca.isactive = 1  AND ccm.CHSSConsultMainId NOT IN  (SELECT     ccm1.CHSSConsultMainId  FROM    chss_consult_main ccm1  WHERE ccm1.CHSSApplyId = :chssapplyid) AND ca.TreatTypeId = ca1.TreatTypeId   AND ca.EmpId=ca1.EmpId AND ca.PatientId = ca1.PatientId AND ca.IsSelf = ca1.IsSelf  AND ca1.CHSSApplyId =:chssapplyid";   /*  AND ca.CHSSStatusId =14   */
	@Override
	public List<Object[]> PatientConsultHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO PatientConsultHistory");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(PATIENTCONSULTHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
	}
		
	private static final String OLDCONSULTMEDSLIST  ="SELECT cb.CHSSApplyId, cb.BillId, cm.CHSSMedicineId, cm.MedicineName, cm.PresQuantity, cm.MedQuantity FROM chss_bill cb, chss_bill_medicine cm WHERE cb.IsActive =1 AND cm.IsActive =1 AND cb.BillId = cm.BillId AND cb.CHSSApplyId = (SELECT MIN(cb1.CHSSApplyId) FROM chss_bill cb1 WHERE  cb1.IsActive = 1 AND cb1.CHSSConsultMainId=:CHSSConsultMainId) AND cb.CHSSConsultMainId =:CHSSConsultMainId AND cb.CHSSApplyId <> :chssapplyid ";
	@Override
	public List<Object[]> OldConsultMedsList(String CHSSConsultMainId, String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO OldConsultMedsList");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(OLDCONSULTMEDSLIST);
			query.setParameter("CHSSConsultMainId", CHSSConsultMainId);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
	}
	
	
//	private static final String MEDADMISSIBLECHECK  ="SELECT   MedicineId, MedNo, TreatTypeId, CategoryId, MedicineName FROM chss_medicines_list WHERE IsAdmissible='N' AND IsActive=1 AND MedicineName LIKE :medicinename ";
	@Override
	public List<Object[]> MedAdmissibleCheck(String medicinename) throws Exception
	{
		logger.info(new Date() +"Inside DAO MedAdmissibleCheck");
		 List<Object[]> list = null;
		try {
			String MEDADMISSIBLECHECK  ="SELECT   MedicineId, MedNo, TreatTypeId, CategoryId, MedicineName FROM chss_medicines_list WHERE IsAdmissible='N' AND IsActive=1 AND MedicineName LIKE '"+medicinename.trim()+"%' ;";
			Query query= manager.createNativeQuery(MEDADMISSIBLECHECK);
//			query.setParameter("medicinename", medicinename.trim());
			
			list=  ( List<Object[]>)query.getResultList();
			
		}catch (NoResultException e) {
			System.err.println ("No Result Exception");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return  list;
	}
	
	private static final String MEDADMISSIBLELIST  ="SELECT   MedicineId, MedNo, TreatTypeId, CategoryId, MedicineName,IsAdmissible FROM chss_medicines_list WHERE IsActive=1  AND TreatTypeId=:treattype AND IsActive=1 AND MedicineName LIKE :medicinename ";
	@Override
	public List<Object[]> MedAdmissibleList(String medicinename, String treattype)throws Exception
	{
		logger.info(new Date() +"Inside DAO MedAdmissibleList");
		List<Object[]> list = null;
		try {
			
			Query query= manager.createNativeQuery(MEDADMISSIBLELIST);
			query.setParameter("medicinename", medicinename.trim()+"%");
			query.setParameter("treattype", treattype);
			
			
			list=  ( List<Object[]>)query.getResultList();
			
		}catch (NoResultException e) {
			System.err.println ("No Result Exception");
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return  list;
	}
	
	private static final String POACKNOWLDGEDUPDATE  ="UPDATE chss_apply SET POAcknowledge = :poacknowledge WHERE chssapplyid=:chssapplyid";
	@Override
	public int POAcknowldgedUpdate(String chssapplyid,String poacknowledge)throws Exception
	{
		logger.info(new Date() +"Inside DAO POAcknowldgedUpdate");
		try {
			
			Query query= manager.createNativeQuery(POACKNOWLDGEDUPDATE);
			query.setParameter("chssapplyid",chssapplyid);
			query.setParameter("poacknowledge", poacknowledge);
			
			return query.executeUpdate();
		}		
		catch (Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		
	}

	private static final String CLAIMAPPROVEDPOVODATA  ="SELECT e.empid,'PO' ,e.EmpName,ed.DesigCode, ed.designation FROM chss_apply ca, employee e, employee_desig ed WHERE ca.POId = e.EmpId AND e.desigid = ed.DesigId AND ca.CHSSApplyId = :chssapplyid UNION SELECT e.empid,'VO' ,e.EmpName,ed.DesigCode, ed.designation FROM chss_apply ca, employee e, employee_desig ed WHERE ca.VOId = e.EmpId AND e.desigid = ed.DesigId AND ca.CHSSApplyId = :chssapplyid";
	@Override
	public List<Object[]> ClaimApprovedPOVOData(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ClaimApprovedPOVOData");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CLAIMAPPROVEDPOVODATA);
			query.setParameter("chssapplyid", chssapplyid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String CLAIMREMARKSHISTORY  ="SELECT cat.CHSSStatusId,cat.Remark, cs.CHSSStatus,  e.EmpName,  ed.designation FROM  chss_status cs,  chss_apply_transaction cat,  chss_apply ca,  employee e,  employee_desig ed WHERE cat.ActionBy = e.EmpId AND e.desigid = ed.DesigId  AND cs.CHSSStatusId = cat.CHSSStatusId AND ca.chssapplyid = cat.chssapplyid AND cs.CHSSStatusId<=6 AND TRIM(cat.Remark)<>'' AND ca.chssapplyid =:chssapplyid  ORDER BY cat.ActionDate ASC";
	@Override
	public List<Object[]> ClaimRemarksHistory(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ClaimRemarksHistory");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CLAIMREMARKSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String GETLABCODE  ="SELECT labcode,LabName FROM lab_master";
	@Override
	public Object[] getLabCode() throws Exception
	{

		logger.info(new Date() +"Inside DAO getLabCode");
		try {
			
			Query query= manager.createNativeQuery(GETLABCODE);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	private static final String CONTINGENTHISTORY  ="SELECT cct.ContinTransactionId,  DATE (cct.ActionDate),  ccs.CHSSStatus,  e.EmpName  FROM  chss_contingent_transaction cct,  chss_contingent_status ccs,  employee e WHERE cct.StatusId = ccs.CHSSContinStatusId  AND cct.ActionBy = e.EmpId  AND cct.ContingentId = :contingentid  ORDER BY cct.ActionDate";
	@Override
	public List<Object[]> ContingentBillHistory(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ContingentHistory");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CONTINGENTHISTORY);
			query.setParameter("contingentid", contingentid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String CONTINGENTBILLREMARKHISTORY  ="SELECT cct.ContinTransactionId,  DATE (cct.ActionDate),  ccs.CHSSStatus,  e.EmpName,  cct.Remarks FROM  chss_contingent_transaction cct,  chss_contingent_status ccs,  employee e WHERE cct.StatusId = ccs.CHSSContinStatusId  AND TRIM(cct.Remarks) <> ''  AND cct.ActionBy = e.EmpId  AND cct.ContingentId =:contingentid ORDER BY cct.ActionDate ASC";
	@Override
	public List<Object[]> ContingentBillRemarkHistory(String contingentid) throws Exception
	{
		logger.info(new Date() +"Inside DAO ContingentBillRemarkHistory");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CONTINGENTBILLREMARKHISTORY);
			query.setParameter("contingentid", contingentid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	

	@Override
	public List<Object[]> GetClaimsList(String fromdate , String todate ,  String empid)throws Exception
	{
		logger.info(new Date() +"Inside DAO GetClaimsList");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("call chss_all_claims(:empid , :fromdate , :todate );");
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			query.setParameter("empid", empid);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	private static final String EMPLOYEESLIST = "SELECT e.empid,e.empname,ed.Designation,e.desigid FROM employee e, employee_desig ed WHERE e.DesigId = ed.DesigId ORDER BY e.srno DESC";
	@Override
	public List<Object[]> EmployeesList()throws Exception
	{
		logger.info(new Date() +"Inside DAO EmployeesList");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery(EMPLOYEESLIST);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<Object[]> GetClaimsReport(String fromdate , String todate ,  String empid)throws Exception
	{
		logger.info(new Date() +"Inside DAO GetClaimsReport");
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("call chss_claims_report(:empid , :fromdate , :todate );");
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			query.setParameter("empid", empid);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	private static final String CLAIMCONSULTMAINLIST = "SELECT DISTINCT ccm.chssconsultmainid,ccm.docname,ccm.docQualification,ccm.consultdate,cdr.docqualification as 'Qualification' FROM chss_apply ca, chss_bill cb,chss_consult_main ccm,chss_doctor_rates cdr WHERE cb.isactive=1 AND cb.chssconsultmainid = ccm.chssconsultmainid AND ca.chssapplyid=cb.chssapplyid AND ccm.docqualification = cdr.docrateid AND ca.chssapplyid= :CHSSApplyId";

	@Override
	public List<Object[]> ClaimConsultMainList(String CHSSApplyId) throws Exception
	{
		logger.info(new Date() +"Inside DAO ClaimConsultMainList");
		try {
			Query query= manager.createNativeQuery(CLAIMCONSULTMAINLIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CLAIMCONSULTMAINDELETEALL  ="UPDATE chss_consult_main SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE chssapplyid=:chssapplyid";
	@Override
	public int claimConsultMainDeleteAll(String chssapplyid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO claimConsultMainDeleteAll");
		try {
			Query query= manager.createNativeQuery(CLAIMCONSULTMAINDELETEALL);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String BILLCONSULTDELETEALL  ="UPDATE chss_bill_consultation SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billConsultDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO billConsultDeleteAll");
		try {
			Query query= manager.createNativeQuery(BILLCONSULTDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String BILLTESTSDELETEALL  ="UPDATE chss_bill_tests SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billTestsDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO billTestsDeleteAll");
		try {
			Query query= manager.createNativeQuery(BILLTESTSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLMEDSDELETEALL  ="UPDATE chss_bill_medicine SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billMedsDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO billMedsDeleteAll");
		try {
			Query query= manager.createNativeQuery(BILLMEDSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLOTHERSDELETEALL  ="UPDATE chss_bill_other SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billOthersDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO billOthersDeleteAll");
		try {
			Query query= manager.createNativeQuery(BILLOTHERSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLMISCDELETEALL  ="UPDATE chss_bill_misc SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billMiscDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO billMiscDeleteAll");
		try {
			Query query= manager.createNativeQuery(BILLMISCDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String CLAIMBILLDELETEALL  ="UPDATE chss_bill SET isactive=0, modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE chssapplyid=:chssapplyid";
	@Override
	public int claimBillDeleteAll(String chssapplyid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO claimBillDeleteAll");
		try {
			Query query= manager.createNativeQuery(CLAIMBILLDELETEALL);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String CLAIMDELETE  ="UPDATE chss_apply SET isactive=0, modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE chssapplyid=:chssapplyid";
	@Override
	public int claimDelete(String chssapplyid,String modifiedby,String modifieddate) throws Exception
	{
		logger.info(new Date() +"Inside DAO claimDelete");
		try {
			Query query= manager.createNativeQuery(CLAIMDELETE);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
	}
	
	private static final String UPDATEBILLADMISSIBLEAMT  ="UPDATE chss_bill SET AdmissibleTotal = :admissibleAmt WHERE billid= :billid";
	@Override
	public int UpdateBillAdmissibleTotal(String billid,String admissibleAmt) throws Exception
	{
		logger.info(new Date() +"Inside DAO UpdateBillAdmissibleAmt");
		try {
			Query query= manager.createNativeQuery(UPDATEBILLADMISSIBLEAMT);
			query.setParameter("billid", billid);
			query.setParameter("admissibleAmt", admissibleAmt);
			
			return  query.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  0;
		
	}

	@Override
	public CHSSIPDClaimsInfo IpdClaimInfo(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside DAO IpdClaimInfo");
		CHSSIPDClaimsInfo returnlist= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSIPDClaimsInfo> cq= cb.createQuery(CHSSIPDClaimsInfo.class);
			Root<CHSSIPDClaimsInfo> root= cq.from(CHSSIPDClaimsInfo.class);					
			Predicate p1=cb.equal(root.get("CHSSApplyId") , Long.parseLong(chssapplyid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			cq=cq.select(root).where(p1,p2);
			TypedQuery<CHSSIPDClaimsInfo> claiminfo = manager.createQuery(cq);
			returnlist = claiminfo.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return returnlist;
	}
	
	@Override
	public long CHSSIPDBasicInfoAdd(CHSSIPDClaimsInfo model ) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSIPDBasicInfoAdd");
		try {
			manager.persist(model);
			manager.flush();
			
			return model.getIPDClaimInfoId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public CHSSIPDClaimsInfo getIpcClaimInfo(String ipdclaiminfoid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getIpcClaimInfo");
		try {
			return manager.find(CHSSIPDClaimsInfo.class, Long.parseLong(ipdclaiminfoid));
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long CHSSIPDBasicInfoEdit(CHSSIPDClaimsInfo claimsinfo) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultMainEdit");
		try {
			manager.merge(claimsinfo);
			manager.flush();
			
			return claimsinfo.getIPDClaimInfoId();
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String MAXMEDNO="SELECT MAX(medno) FROM chss_medicines_list WHERE treattypeid=:treattype";
	@Override
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		logger.info(new Date() + "Inside DAO GetMaxMedNo()");
		try {
			Query query = manager.createNativeQuery(MAXMEDNO);
			query.setParameter("treattype", treatmenttype);
			Integer result = (Integer) query.getSingleResult();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public Long AddMedicine(CHSSMedicinesList medicine)throws Exception
	{
		logger.info(new Date() + "Inside DAO AddMedicine()");
		try {
			manager.persist(medicine);
			manager.flush();
			return medicine.getMedicineId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	
	@Override
	public Long AddMasterEditComments(MasterEdit masteredit)throws Exception
	{
		logger.info(new Date() + "Inside DAO AddMasterEditComments()");
		try {
			manager.persist(masteredit);
			manager.flush();
			return (long)masteredit.getMasterEditId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0l;
		}
	}
	
	private static final String  IPDBILLPACKAGEITEMS = "SELECT * FROM (SELECT ipdbillheadid, billheadname,ismultiple, ispackage FROM chss_ipd_billheads WHERE isactive=1 AND ispackage='Y' ORDER BY orderno ) a LEFT JOIN (SELECT  chssitemheadid,billid,ipdbillheadid AS 'billheadid',billheadcost,amountpaid,billheadremamt, comments  FROM chss_bill_ipdheads WHERE billid=:billid)  b ON b.billheadid = a.ipdbillheadid";
	
	@Override
	public List<Object[]> IPDBillPackageItems(String billid)throws Exception
	{
		logger.info(new Date() + "Inside DAO IPDBillPackageItems()");
		try {
			Query query = manager.createNativeQuery(IPDBILLPACKAGEITEMS);
			query.setParameter("billid", billid);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	private static final String  IPDBILLNONPACKAGEITEMS = "SELECT * FROM (SELECT ipdbillheadid, billheadname,ismultiple, ispackage FROM chss_ipd_billheads WHERE isactive=1 AND ispackage='N' AND ismultiple ='N' ORDER BY orderno ) a LEFT JOIN (SELECT  chssitemheadid,billid,ipdbillheadid AS 'billheadid',billheadcost,amountpaid,billheadremamt, comments  FROM chss_bill_ipdheads WHERE billid=:billid)  b ON b.billheadid = a.ipdbillheadid ;";
	
	@Override
	public List<Object[]> IPDBillNonPackageItems(String billid)throws Exception
	{
		logger.info(new Date() + "Inside DAO IPDBillNonPackageItems");
		try {
			Query query = manager.createNativeQuery(IPDBILLNONPACKAGEITEMS);
			query.setParameter("billid", billid);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	@Override
	public CHSSBillIPDheads getCHSSBillIPDheads(long ItemHeadId,long billid,int billheadid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSPaybandRemlist");
		CHSSBillIPDheads Bhead= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillIPDheads> cq= cb.createQuery(CHSSBillIPDheads.class);
			Root<CHSSBillIPDheads> root= cq.from(CHSSBillIPDheads.class);	
			
			if(ItemHeadId == 0) {
				Predicate p1=cb.equal(root.get("BillId") , billid);
				Predicate p2=cb.equal(root.get("IPDBillHeadId") , billheadid);
				Predicate p3=cb.equal(root.get("IsActive") , 1);
				cq=cq.select(root).where(p1,p2,p3);
			}else if(ItemHeadId > 0) {
			
				Predicate p1=cb.equal(root.get("CHSSItemHeadId") , ItemHeadId);
				cq=cq.select(root).where(p1);
			}
			
			TypedQuery<CHSSBillIPDheads> allquery = manager.createQuery(cq);
			Bhead= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return Bhead;
	}
	
	
	
	@Override
	public long CHSSBillIPDheadsAdd(CHSSBillIPDheads bhead ) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBillIPDheadsAdd");
		manager.persist(bhead);
		manager.flush();
		
		return bhead.getCHSSItemHeadId();
	}
	
	@Override
	public long CHSSBillIPDheadsEdit(CHSSBillIPDheads bhead ) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSBillIPDheadsEdit");
		manager.merge(bhead);
		manager.flush();
		
		return bhead.getCHSSItemHeadId();
	}
	
	
}

package com.vts.ems.chss.dao;

import java.time.LocalDate;
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
import com.vts.ems.chss.model.CHSSApplyTransaction;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.Employee;


@Transactional
@Repository
public class CHSSDaoImpl implements CHSSDao {

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
			
	@PersistenceContext
	EntityManager manager;
	
	private static final String FAMILYDETAILSLIST = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.IsActive = 1 AND fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND fs.family_status_id IN (1, 2) AND empid = :empid ORDER BY relation_id  ";
	
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
			Query query = manager.createNativeQuery("CALL chss_bills_list (:CHSSApplyId);");
			query.setParameter("CHSSApplyId", chssapplyid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CLAIMCONSULTATIONSCOUNT = "SELECT COUNT(cc.consultationid),'count' FROM chss_bill cb, chss_consultation cc WHERE cb.billid=cc.BillId AND cc.isactive=1 AND cb.chssapplyid=:CHSSApplyId";
	
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
	
	
	private static final String CLAIMMEDICINESCOUNT = "SELECT COUNT(chssmedicineid),'count' FROM chss_bill cb, chss_medicine cm WHERE cb.billid=cm.BillId AND cm.isactive=1 AND cb.chssapplyid=:CHSSApplyId";
	
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
	public CHSSConsultation getCHSSConsultation(String consultationid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSConsultation");
		try {
			return manager.find(CHSSConsultation.class, Long.parseLong(consultationid));
			
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
	public List<CHSSTestSub> CHSSTestSubList(String testmainid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSTestSubList");
		List<CHSSTestSub> testsublist= new ArrayList<CHSSTestSub>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTestSub> cq= cb.createQuery(CHSSTestSub.class);
			
			Root<CHSSTestSub> root=cq.from(CHSSTestSub.class);								
//			Predicate p1=cb.equal(root.get("TestMainId") , Long.parseLong(testmainid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p2);
			
			
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
	public long ConsultationBillAdd(CHSSConsultation consult) throws Exception
	{
		logger.info(new Date() +"Inside DAO ConsultationBillAdd");
		manager.persist(consult);
		manager.flush();
		
		return consult.getConsultationId();
	}
	
	@Override
	public List<CHSSConsultation> CHSSConsultationList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSConsultationList");
		List<CHSSConsultation> list= new ArrayList<CHSSConsultation>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSConsultation> cq= cb.createQuery(CHSSConsultation.class);
			
			Root<CHSSConsultation> root=cq.from(CHSSConsultation.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSConsultation> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long ConsultationBillEdit(CHSSConsultation consult) throws Exception
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
	public long MedicinesBillAdd(CHSSMedicine medicine) throws Exception
	{
		logger.info(new Date() +"Inside DAO MedicinesBillAdd");
		manager.persist(medicine);
		manager.flush();
		
		return medicine.getCHSSMedicineId();
	}
	
	@Override
	public List<CHSSMedicine> CHSSMedicineList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMedicineList");
		List<CHSSMedicine> list= new ArrayList<CHSSMedicine>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSMedicine> cq= cb.createQuery(CHSSMedicine.class);
			
			Root<CHSSMedicine> root=cq.from(CHSSMedicine.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSMedicine> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public CHSSMedicine getCHSSMedicine(String CHSSMedicineId) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSMedicine");
		try {
			return manager.find(CHSSMedicine.class, Long.parseLong(CHSSMedicineId));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public CHSSTests getCHSSTest(String chsstestid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSTest");
		try {
			return manager.find(CHSSTests.class, Long.parseLong(chsstestid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long MedicineBillEdit(CHSSMedicine medicine) throws Exception
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
	public long TestsBillAdd(CHSSTests test) throws Exception
	{
		logger.info(new Date() +"Inside DAO TestsBillAdd");
		manager.persist(test);
		manager.flush();
		
		return test.getCHSSTestId();
	}
	
	@Override
	public List<CHSSTests> CHSSTestsList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMedicineList");
		List<CHSSTests> list= new ArrayList<CHSSTests>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTests> cq= cb.createQuery(CHSSTests.class);
			
			Root<CHSSTests> root=cq.from(CHSSTests.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSTests> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public long TestBillEdit(CHSSTests test) throws Exception
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
	public long MiscBillAdd(CHSSMisc misc) throws Exception
	{
		logger.info(new Date() +"Inside DAO MiscBillAdd");
		manager.persist(misc);
		manager.flush();
		
		return misc.getChssMiscId();
	}
	@Override
	public CHSSMisc getCHSSMisc(String miscid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSMisc");
		try {
			return manager.find(CHSSMisc.class, Long.parseLong(miscid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<CHSSMisc> CHSSMiscList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSMiscList");
		List<CHSSMisc> list= new ArrayList<CHSSMisc>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSMisc> cq= cb.createQuery(CHSSMisc.class);
			
			Root<CHSSMisc> root=cq.from(CHSSMisc.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSMisc> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long MiscBillEdit(CHSSMisc misc) throws Exception
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
	public List<CHSSOther> CHSSOtherList(String billid) throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSOtherList");
		List<CHSSOther> list= new ArrayList<CHSSOther>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSOther> cq= cb.createQuery(CHSSOther.class);
			
			Root<CHSSOther> root=cq.from(CHSSOther.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSOther> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public long OtherBillAdd(CHSSOther other) throws Exception
	{
		logger.info(new Date() +"Inside DAO OtherBillAdd");
		manager.persist(other);
		manager.flush();
		
		return other.getCHSSOtherId();
	}
	
	@Override
	public long OtherBillEdit(CHSSOther other) throws Exception
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
	public CHSSOther getCHSSOther(String otherid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSOther");
		try {
			return manager.find(CHSSOther.class, Long.parseLong(otherid));
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSTESTSDATALIST = "SELECT   ct.CHSSTestId, ct.BillId,  ct.TestMainId,  ct.TestSubId,  ct.TestCost,ctm.TestMainName, cts.TestName,ct.TestRemAmount ,cb.BillNo,  cb.BillDate, cts.TestCode FROM  chss_tests ct,  chss_test_main ctm,  chss_test_sub cts,  chss_bill cb WHERE ct.isactive = 1 AND cb.isactive=1 AND ct.TestMainId = ctm.TestMainId  AND ct.TestSubId = cts.TestSubId  AND cb.BillId = ct.BillId  AND cb.CHSSApplyId = :CHSSApplyId";
	
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
	
	
	private static final String CHSSCONSULTDATALIST = "SELECT   cc.ConsultationId,  cc.BillId,  cc.ConsultType,  cc.DocName,  cc.DocQualification,  cc.ConsultDate,  cc.ConsultCharge,  cc.ConsultRemAmount, cb.BillNo,  cb.BillDate  FROM  chss_consultation cc,   chss_bill cb WHERE cc.isactive = 1 AND cb.isactive=1 AND cb.BillId = cc.BillId  AND cb.CHSSApplyId = :CHSSApplyId ";

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
	
	private static final String CHSSMEDICINEDATALIST = "SELECT   cm.CHSSMedicineId,   cm.BillId,  cm.MedicineName,  cm.MedicineCost, cm.MedQuantity,cm.presQuantity,cm.MedsRemAmount ,cb.BillNo,  cb.BillDate FROM   chss_medicine cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

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
	
	private static final String CHSSOTHERDATALIST = "SELECT co.CHSSOtherId,   co.BillId,  co.OtherItemId,  co.OtherItemCost,  coi.OtherItemName,co.OtherRemAmount  ,cb.BillNo,  cb.BillDate FROM chss_other co,chss_other_items coi, chss_bill cb WHERE co.isactive = 1 AND cb.isactive=1 AND  co.OtherItemId = coi.OtherItemId AND cb.BillId = co.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

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
	
	
	private static final String CHSSMISCDATALIST = "SELECT  cm.ChssMiscId,  cm.BillId,  cm.MiscItemName,  cm.MiscItemCost,cm.MiscRemAmount  ,cb.BillNo,  cb.BillDate FROM  chss_misc cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId =:CHSSApplyId";

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
	public List<Object[]> CHSSApproveClaimList(String logintype) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSApproveClaimList");
		try {
			Query query= manager.createNativeQuery("CALL chss_claims_verify (:logintype);");
			query.setParameter("logintype", logintype);
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
	
//	private static final String GETCHSSCONTINGENTLIST  ="SELECT cc.contingentid,cc.ContingentBillNo,cc.ContingentDate,ClaimsCount,cc.BillsCount,cc.ContingentStatusId,cc.Remarks ,cs.chssstatus FROM chss_contingent cc , chss_status cs WHERE cc.isactive=1 AND cc.ContingentStatusId = cs.chssstatusid ORDER BY cc.ContingentStatusId ASC";
	
	@Override
	public List<Object[]> getCHSSContingentList(String logintype) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSContingentList");
		
		try {
			Query query= manager.createNativeQuery("call chss_contingent_bills_list(:logintype)");
			query.setParameter("logintype", logintype);
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
	
	private static final String CHSSCONTINGENTDATA  ="SELECT cc.contingentid,cc.ContingentBillNo,cc.ContingentDate,ClaimsCount,cc.BillsCount,cc.ContingentStatusId,cc.Remarks ,cs.chssstatus,cc.billcontent FROM chss_contingent cc , chss_status cs WHERE  cc.ContingentStatusId = cs.chssstatusid AND cc.contingentid= :contingentid ORDER BY cc.ContingentStatusId ASC";
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
	private static final String CHSSSTAUSDETAILS="SELECT a.chsstransactionid,f.empid,c.empname,d.designation,e.divisionname,a.actiondate,a.remark,b.chssstatus FROM chss_apply_transaction a, chss_status b,employee c,employee_desig d,division_master e ,chss_apply f WHERE a.chssstatusid=b.chssstatusid  AND f.chssapplyid=a.chssapplyid AND a.actionby=c.empid  AND c.designationid=d.desigid AND c.divisionid=e.divisionid AND a.chssapplyid=:chssapplyid";
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
	public List<Object[]> GetApprovedBills(String bill)throws Exception
	{
          logger.info(new Date() +"Inside DAO GetApprovedBills()");
		
		try {
			Query query= manager.createNativeQuery("CALL chss_contingent_bills_list(:bill)");
			query.setParameter("bill", bill);
			return (List<Object[]>)query.getResultList();		
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
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
	
	private static final String CHSSAPPROVALAUTHLIST  ="SELECT e.empid,e.empname,ed.Designation, l.LoginType,lt.LoginDesc FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.DesignationId = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType IN ('K','V','W','Z')  ";
	@Override
	public List<Object[]> CHSSApprovalAuthList() throws Exception
	{
		logger.info(new Date() +"Inside DAO CHSSApprovalAuthList");
		try {
			
			Query query= manager.createNativeQuery(CHSSAPPROVALAUTHLIST);
			return (List<Object[]>)query.getResultList();
			
			
		}catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CHSSAPPROVALAUTH  ="SELECT e.empid,e.empname,ed.Designation, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.DesignationId = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType  ";
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
	
}

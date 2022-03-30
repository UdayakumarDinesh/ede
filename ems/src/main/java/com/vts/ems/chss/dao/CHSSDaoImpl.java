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
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSPaybandRemlist;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
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
	public CHSSPaybandRemlist getCHSSPaybandRemlist(String otheritemid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSPaybandRemlist");
		CHSSPaybandRemlist remamountlist= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSPaybandRemlist> cq= cb.createQuery(CHSSPaybandRemlist.class);
			Root<CHSSPaybandRemlist> root= cq.from(CHSSPaybandRemlist.class);					
			Predicate p1=cb.equal(root.get("OtherItemId") , Long.parseLong(otheritemid));
			cq=cq.select(root).where(p1);
			TypedQuery<CHSSPaybandRemlist> allquery = manager.createQuery(cq);
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
	
	private static final String CHSSAPPLIEDDATA = "SELECT ca.chssapplyid,  ca.EmpId,  ca.patientid,  ca.isself,  ca.FollowUp,  ca.chssnewid,  ca.chsstype,  ca.TreatTypeId,  ca.noenclosures,  ca.chssstatusid,   ct.TreatmentName,  ca.isactive,  fd.member_name,  fd.relation_id,  fr.relation_name,ca.CHSSApplyDate, ca.chssapplyno, ca.ailment FROM  chss_apply ca,  pis_emp_family_details fd,  pis_emp_family_relation fr,  chss_treattype ct WHERE ca.TreatTypeId = ct.TreatTypeId AND ca.IsSelf = 'N'  AND ca.PatientId = fd.family_details_id  AND fd.relation_id = fr.relation_id  AND ca.CHSSApplyId = :CHSSApplyId UNION SELECT   ca.chssapplyid,  ca.EmpId,  ca.patientid,  ca.isself,  ca.FollowUp,  ca.chssnewid,  ca.chsstype,  ca.TreatTypeId,  ca.noenclosures,  ca.chssstatusid,   ct.TreatmentName,  ca.isactive,   e.EmpName,  '0' AS 'relation_id',  'SELF' AS 'relation_name' ,ca.CHSSApplyDate, ca.chssapplyno, ca.ailment  FROM chss_apply ca,  employee e,  chss_treattype ct WHERE ca.TreatTypeId = ct.TreatTypeId AND ca.IsSelf = 'Y'  AND ca.PatientId = e.EmpId  AND ca.CHSSApplyId = :CHSSApplyId";
	
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
	
	
	private static final String EMPCHSSLIST = "CALL chss_emp_claimlist(:empid)";
	
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
			Predicate p1=cb.equal(root.get("TestMainId") , Long.parseLong(testmainid));
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
		
		return medicine.getMedicineId();
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
	public CHSSMedicine getCHSSMedicine(String medicineid) throws Exception
	{
		logger.info(new Date() +"Inside DAO getCHSSMedicine");
		try {
			return manager.find(CHSSMedicine.class, Long.parseLong(medicineid));
			
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
			
			return medicine.getMedicineId();
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
	
	private static final String CHSSTESTSDATALIST = "SELECT   ct.CHSSTestId, ct.BillId,  ct.TestMainId,  ct.TestSubId,  ct.TestCost,ctm.TestMainName, cts.TestName,ct.`TestRemAmount` ,cb.BillNo,  cb.BillDate FROM  chss_tests ct,  chss_test_main ctm,  chss_test_sub cts,  chss_bill cb WHERE ct.isactive = 1  AND ct.TestMainId = ctm.TestMainId  AND ct.TestSubId = cts.TestSubId  AND cb.BillId = ct.BillId  AND cb.CHSSApplyId = :CHSSApplyId";
	
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
	
	
	private static final String CHSSCONSULTDATALIST = "SELECT   cc.ConsultationId,  cc.BillId,  cc.ConsultType,  cc.DocName,  cc.DocQualification,  cc.ConsultDate,  cc.ConsultCharge,  cc.ConsultRemAmount, cb.BillNo,  cb.BillDate  FROM  chss_consultation cc,   chss_bill cb WHERE cc.isactive = 1  AND cb.BillId = cc.BillId  AND cb.CHSSApplyId = :CHSSApplyId ";

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
	
	private static final String CHSSMEDICINEDATALIST = "SELECT   cm.MedicineId,   cm.BillId,  cm.MedicineName,  cm.MedicineDate,  cm.MedicineCost, cm.MedQuantity,cm.MedsRemAmount  ,cb.BillNo,  cb.BillDate FROM   chss_medicine cm,  chss_bill cb WHERE cm.isactive = 1  AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

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
	
	private static final String CHSSOTHERDATALIST = "SELECT co.CHSSOtherId,   co.BillId,  co.OtherItemId,  co.OtherItemCost,  coi.OtherItemName,co.OtherRemAmount  ,cb.BillNo,  cb.BillDate FROM chss_other co,chss_other_items coi, chss_bill cb WHERE co.isactive = 1 AND  co.OtherItemId = coi.OtherItemId AND cb.BillId = co.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

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
	
	
	private static final String CHSSMISCDATALIST = "SELECT  cm.ChssMiscId,  cm.BillId,  cm.MiscItemName,  cm.MiscItemCost,cm.MiscRemAmount  ,cb.BillNo,  cb.BillDate FROM  chss_misc cm,  chss_bill cb WHERE cm.isactive = 1  AND cb.BillId = cm.BillId  AND cb.CHSSApplyId =:CHSSApplyId";

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
	public List<Object[]> CHSSApproveClaimList(String logintype, String fromdate, String todate) throws Exception 
	{
		logger.info(new Date() +"Inside DAO CHSSApproveClaimList");
		try {
			Query query= manager.createNativeQuery("CALL chss_claims (:logintype,:fromdate,:todate);");
			query.setParameter("logintype", logintype);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}

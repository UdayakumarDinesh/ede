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
import com.vts.ems.chss.model.CHSSApplyDispute;
import com.vts.ems.chss.model.CHSSApplyTransaction;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSBillConsultation;
import com.vts.ems.chss.model.CHSSBillEquipment;
import com.vts.ems.chss.model.CHSSBillImplants;
import com.vts.ems.chss.model.CHSSBillMedicine;
import com.vts.ems.chss.model.CHSSBillMisc;
import com.vts.ems.chss.model.CHSSBillOther;
import com.vts.ems.chss.model.CHSSBillPkg;
import com.vts.ems.chss.model.CHSSBillPkgItems;
import com.vts.ems.chss.model.CHSSBillTests;
import com.vts.ems.chss.model.CHSSConsultMain;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.chss.model.CHSSContingentTransaction;
import com.vts.ems.chss.model.CHSSIPDAttachments;
import com.vts.ems.chss.model.CHSSIPDClaimsInfo;
import com.vts.ems.chss.model.CHSSIPDPkgItems;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.master.model.CHSSDoctorRates;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.model.EMSNotification;


@Transactional
@Repository
public class CHSSDaoImpl implements CHSSDao {

	private static final Logger logger = LogManager.getLogger(CHSSDaoImpl.class);
			
	@PersistenceContext
	EntityManager manager;
	
	
	
	private static final String CHSSDASHBOARDCOUNTDATA = "CALL Chss_MainDashboard_Count (:empid, :fromdate,  :todate, :isself) ";
	
	@Override 
	public Object[] CHSSDashboardCountData(String Empid, String FromDate, String ToDate,String IsSelf) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(CHSSDASHBOARDCOUNTDATA);
			query.setParameter("empid", Empid);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", ToDate);
			query.setParameter("isself", IsSelf);
			
			return (Object[])query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSDashboardCountData " + e);
			return null;
		}
		
	}
	
	private static final String CHSSDASHBOARDGRAPHDATA = "CALL Chss_MainDashboard_GraphData (:empid, :fromdate,  :todate) ";
	
	@Override 
	public List<Object[]> CHSSDashboardGraphData(String Empid, String FromDate, String ToDate) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(CHSSDASHBOARDGRAPHDATA);
			query.setParameter("empid", Empid);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", ToDate);
			
			return (List<Object[]> )query.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSDashboardGraphData " + e);
			return null;
		}
		
	}
	
	private static final String CHSSDASHBOARDAMOUNTDATA = "CALL Chss_MainDashboard_Total_Amnt (:empid, :fromdate,  :todate, :isself) ;";
	 
	@Override
	public Object[] CHSSDashboardAmountData(String EmpId, String FromDate, String ToDate,String IsSelf) throws Exception
	{
		try {
				
			Query query = manager.createNativeQuery(CHSSDASHBOARDAMOUNTDATA);
			query.setParameter("empid", EmpId);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", ToDate);
			query.setParameter("isself", IsSelf);
			
			return (Object[])query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSDashboardAmountData " + e);
			return null;
		}
	 }
	
	 private static final String CHSSDASHBOARDINDIVIDUALAMOUNTDATA = "CALL Chss_MainDashboard_Individual_Amnt (:empid, :fromdate,  :todate) ";

	@Override 
	public List<Object[]> CHSSDashboardIndividualAmountData(String Empid, String FromDate, String ToDate) throws Exception
	{
		
		try {
			
			Query query = manager.createNativeQuery(CHSSDASHBOARDINDIVIDUALAMOUNTDATA);
			query.setParameter("empid", Empid);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", ToDate);
			
			return (List<Object[]> )query.getResultList();
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSDashboardIndividualAmountData " +e);
			return null;
		}
	}
		
	
	
		private static final String MONTHLYWISEDASHBOARDDATA="CALL Chss_MainDashboard_Monthly_Data (:fromdate, :todate, :month)";
		
		@Override
		public Object[] MonthlyWiseDashboardData(String FromDate, String ToDate,int Month) throws Exception
		{
			 		 
				try {
					
					Query query = manager.createNativeQuery(MONTHLYWISEDASHBOARDDATA);
					query.setParameter("fromdate", FromDate);
					query.setParameter("todate", ToDate);
					query.setParameter("month", Month);
					
					return (Object[])query.getSingleResult();
				}
				catch(Exception e) {
					logger.error(new Date()  + "Inside DAO MonthlyWiseDashboardData " + e);
					return null;
				}
		 }
	
	
	
	
	
	
	
	
	
	private static final String FAMILYDETAILSLIST = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name,fd.gender,fd.med_dep_from FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.IsActive = 1 AND fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND fs.family_status_id IN (1, 2) AND fd.med_dep ='Y' AND empid = :empid ORDER BY fr.SerialNo ASC ";
	
	@Override
	public List<Object[]> familyDetailsList(String empid) throws Exception
	{
		Query query =manager.createNativeQuery(FAMILYDETAILSLIST);
		List<Object[]> resultList = new ArrayList<Object[]>();		
		query.setParameter("empid", empid);
		resultList = (List<Object[]>)query.getResultList();				
		return resultList;
	}
	
	private static final String FAMILYMEMBERDATA = "SELECT fd.family_details_id, fd.member_name, fd.relation_id, fd.dob, fd.family_status_id, fd.status_from, fd.blood_group, fr.relation_name ,fd.med_dep_from, fd.gender FROM pis_emp_family_details fd,  pis_emp_family_relation fr, pis_emp_family_status fs WHERE fd.relation_id = fr.relation_id   AND fd.family_status_id = fs.family_status_id AND family_details_id = :familydetailsid ";
	
	@Override
	public Object[] familyMemberData(String familydetailsid) throws Exception
	{
		Query query =manager.createNativeQuery(FAMILYMEMBERDATA);
		Object[] result = null;
		query.setParameter("familydetailsid", familydetailsid);
		
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO familyMemberData " + e);
		}
		
		return result;
	}
	
	private static final String EMPLOYEE="SELECT a.empid,a.empno,a.empname,a.desigid,b.basicpay,b.gender,b.bloodgroup,a.email,b.phoneno,b.paylevelid,b.dob,b.BasicPay,  ed.Designation,epl.paygrade,a.extno FROM employee a, employee_details b,employee_desig ed, pis_pay_level epl WHERE a.empno=b.empno AND b.paylevelid=epl.paylevelid AND a.DesigId = ed.DesigId AND a.isactive='1' AND a.empid=:empid ";
	
	@Override
	public  Object[] getEmployee(String empid) throws Exception
	{
		Query query =manager.createNativeQuery(EMPLOYEE);
		Object[] result = null;
		query.setParameter("empid", empid);
		try {
			result = (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getEmployee " + e);
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	@Override
	public CHSSOtherItems getCHSSOtherItems(String otheritemid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO getCHSSOtherItems " + e);
			e.printStackTrace();
		}
		return remamountlist;
	}
	
	
	@Override
	public CHSSOtherPermitAmt getCHSSOtherPermitAmt(String otheritemid,long  basicpay) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO getCHSSOtherPermitAmt " + e);
		}
		return list;
	}
	
	
	
	
	
	
	
	@Override
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception
	{
		List<CHSSTreatType> list= new ArrayList<CHSSTreatType>(); 
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTreatType> cq= cb.createQuery(CHSSTreatType.class);
			cq.from(CHSSTreatType.class);			
			TypedQuery<CHSSTreatType> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO CHSSTreatTypeList " + e);
		}
		return list;
	}
	
	
	@Override
	public long CHSSApplyAdd(CHSSApply apply ) throws Exception
	{
		manager.persist(apply);
		manager.flush();
		
		return apply.getCHSSApplyId();
	}
	
	@Override
	public long CHSSBillAdd(CHSSBill bill) throws Exception
	{
		manager.persist(bill);
		manager.flush();
		
		return bill.getBillId();
	}
	
	
	@Override
	public long CHSSConsultMainAdd(CHSSConsultMain consultmain) throws Exception
	{
		manager.persist(consultmain);
		manager.flush();		
		return consultmain.getCHSSConsultMainId();
	}
	
	@Override
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSApplied " + e);
		}
		return apply;
	}
	
	
	@Override
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery("CALL chss_claim_data(:CHSSApplyId);");
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSAppliedData " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public CHSSDoctorRates getDocterRate(String rateid) throws Exception
	{
		try {
			return manager.find(CHSSDoctorRates.class, Integer.parseInt(rateid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getDocterRate " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery("CALL chss_claim_bills (:CHSSApplyId);");
			query.setParameter("CHSSApplyId", chssapplyid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO CHSSBillsList " + e);
			return new ArrayList<Object[]>();
		}
		
	}

	@Override
	public List<Object[]> CHSSConsultMainBillsList(String consultmainid, String chssapplyid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery("CALL chss_consult_bills (:chssapplyid ,:consultmainid );");
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("consultmainid", consultmainid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date()  + "Inside DAO CHSSConsultMainBillsList " + e);
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CLAIMCONSULTATIONSCOUNT = "SELECT COUNT(cc.consultationid),'count' FROM chss_bill cb, chss_bill_consultation cc WHERE cb.billid=cc.BillId AND cc.isactive=1 AND cb.chssapplyid=:CHSSApplyId";
	
	@Override
	public Object[] claimConsultationsCount(String chssapplyid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(CLAIMCONSULTATIONSCOUNT);
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getResultList().get(0);
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO claimConsultationsCount " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CLAIMMEDICINESCOUNT = "SELECT COUNT(chssmedicineid),'count' FROM chss_bill cb, chss_bill_medicine cm WHERE cb.billid=cm.BillId AND cm.isactive=1 AND cb.chssapplyid=:CHSSApplyId";
	
	@Override
	public Object[] claimMedicinesCount(String chssapplyid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(CLAIMMEDICINESCOUNT);
			query.setParameter("CHSSApplyId", chssapplyid);
			return (Object[])query.getResultList().get(0);
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO claimMedicinesCount " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String EMPCHSSLIST = "CALL chss_emp_claims(:empid,:patientid,:fromdate,:todate,:isself)";
	
	@Override
	public List<Object[]> empCHSSList(String empid,String PatientId, LocalDate FromDate, LocalDate Todate, String IsSelf) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(EMPCHSSLIST);
			query.setParameter("empid", empid);
			query.setParameter("patientid", PatientId);
			query.setParameter("fromdate", FromDate);
			query.setParameter("todate", Todate);
			query.setParameter("isself", IsSelf);
			
			
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO empCHSSList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	@Override
	public CHSSConsultMain getCHSSConsultMain(String ConsultMainId) throws Exception
	{
		try {
			return manager.find(CHSSConsultMain.class, Long.parseLong(ConsultMainId));
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSConsultMain " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public CHSSBill getCHSSBill(String billid) throws Exception
	{
		try {
			return manager.find(CHSSBill.class, Long.parseLong(billid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSBill " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public Object[] CHSSBill(String billid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery("CALL chss_bill_data(:billid)");
			query.setParameter("billid", billid);
			return (Object[])query.getResultList().get(0);
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSBill " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public long CHSSBillEdit(CHSSBill bill) throws Exception
	{
		try {
			manager.merge(bill);
			manager.flush();
			
			return bill.getBillId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public int CHSSConsultMainDelete(String  consultmainid) throws Exception
	{
		try {
			CriteriaBuilder criteriaBuilder  = manager.getCriteriaBuilder();
			CriteriaDelete<CHSSConsultMain> query = criteriaBuilder.createCriteriaDelete(CHSSConsultMain.class);
			Root<CHSSConsultMain> root = query.from(CHSSConsultMain.class);
			query.where(root.get("CHSSConsultMainId").in(Long.parseLong(consultmainid)));

			int result = manager.createQuery(query).executeUpdate();
			return result;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSConsultMainDelete " + e);
			e.printStackTrace();
			return 0;
		}
	}
		
	@Override
	public long CHSSConsultMainEdit(CHSSConsultMain consultmain) throws Exception
	{
		try {
			manager.merge(consultmain);
			manager.flush();
			
			return consultmain.getCHSSConsultMainId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSConsultMainEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public CHSSApply getCHSSApply(String chssapplyid) throws Exception
	{
		try {
			return manager.find(CHSSApply.class, Long.parseLong(chssapplyid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSApply " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public CHSSApplyDispute getCHSSApplyDispute(String chssapplyid) throws Exception
	{
		CHSSApplyDispute Dispute= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSApplyDispute> cq= cb.createQuery(CHSSApplyDispute.class);
			
			Root<CHSSApplyDispute> root=cq.from(CHSSApplyDispute.class);								
			Predicate p1=cb.equal(root.get("CHSSApplyId") , Long.parseLong(chssapplyid));
			
			cq=cq.select(root).where(p1);
			
			
			TypedQuery<CHSSApplyDispute> allquery = manager.createQuery(cq);
			Dispute= allquery.getResultList().get(0);
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSApplyDispute " + e);
			e.printStackTrace();
		}
		return Dispute;
	}
	
	
	
	@Override
	public CHSSBillConsultation getCHSSConsultation(String consultationid) throws Exception
	{
		try {
			return manager.find(CHSSBillConsultation.class, Long.parseLong(consultationid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSConsultation " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long CHSSApplyEdit(CHSSApply apply) throws Exception
	{
		try {
			manager.merge(apply);
			manager.flush();
			
			return apply.getCHSSApplyId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSApplyEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSTestMain> CHSSTestMainList() throws Exception
	{
		List<CHSSTestMain> testmainlist= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSTestMain> cq= cb.createQuery(CHSSTestMain.class);
			cq.from(CHSSTestMain.class);					
			TypedQuery<CHSSTestMain> allquery = manager.createQuery(cq);
			testmainlist= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSTestMainList " + e);
			e.printStackTrace();
		}
		return testmainlist;
	}
	
	@Override
	public List<CHSSTestSub> CHSSTestSubList() throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSTestSubList " + e);
			e.printStackTrace();
		}
		return testsublist;
	}
	
	@Override
	public List<CHSSTestSub> CHSSTestSubListWithAyur() throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSTestSubListWithAyur " + e);
			e.printStackTrace();
		}
		return testsublist;
	}
	
	
	@Override
	public CHSSTestSub getCHSSTestSub(String testsubid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO getCHSSTestSub " + e);
			e.printStackTrace();
		}
		return testsub;
	}
	
	@Override
	public long ConsultationBillAdd(CHSSBillConsultation consult) throws Exception
	{
		manager.persist(consult);
		manager.flush();
		
		return consult.getConsultationId();
	}
	
	@Override
	public List<CHSSBillConsultation> CHSSConsultationList(String billid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO ConsultationBillAdd " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long ConsultationBillEdit(CHSSBillConsultation consult) throws Exception
	{
		try {
			manager.merge(consult);
			manager.flush();
			
			return consult.getConsultationId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ConsultationBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long MedicinesBillAdd(CHSSBillMedicine medicine) throws Exception
	{
		manager.persist(medicine);
		manager.flush();
		
		return medicine.getCHSSMedicineId();
	}
	
	@Override
	public List<CHSSBillMedicine> CHSSMedicineList(String billid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO MedicinesBillAdd " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public CHSSBillMedicine getCHSSMedicine(String CHSSMedicineId) throws Exception
	{
		try {
			return manager.find(CHSSBillMedicine.class, Long.parseLong(CHSSMedicineId));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSMedicine " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public CHSSBillTests getCHSSTest(String chsstestid) throws Exception
	{
		try {
			return manager.find(CHSSBillTests.class, Long.parseLong(chsstestid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSTest " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long MedicineBillEdit(CHSSBillMedicine medicine) throws Exception
	{
		try {
			manager.merge(medicine);
			manager.flush();
			
			return medicine.getCHSSMedicineId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO MedicineBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	

	@Override
	public long TestsBillAdd(CHSSBillTests test) throws Exception
	{
		manager.persist(test);
		manager.flush();
		
		return test.getCHSSTestId();
	}
	
	@Override
	public List<CHSSBillTests> CHSSTestsList(String billid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSTestsList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public long TestBillEdit(CHSSBillTests test) throws Exception
	{
		try {
			manager.merge(test);
			manager.flush();
			
			return test.getCHSSTestId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO TestBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	
	
	
	
	@Override
	public long MiscBillAdd(CHSSBillMisc misc) throws Exception
	{
		manager.persist(misc);
		manager.flush();
		
		return misc.getChssMiscId();
	}
	@Override
	public CHSSBillMisc getCHSSMisc(String miscid) throws Exception
	{
		try {
			return manager.find(CHSSBillMisc.class, Long.parseLong(miscid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSMisc " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public List<CHSSBillMisc> CHSSMiscList(String billid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSMiscList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long MiscBillEdit(CHSSBillMisc misc) throws Exception
	{
		try {
			manager.merge(misc);
			manager.flush();
			
			return misc.getChssMiscId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO MiscBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public List<CHSSDoctorRates> getCHSSDoctorRates(String treattypeid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO getCHSSDoctorRates " + e);
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CHSSOtherItems> OtherItemsList() throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO OtherItemsList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<CHSSBillOther> CHSSOtherList(String billid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSOtherList " + e);
		}
		return list;
	}
	
	@Override
	public List<CHSSBillPkg> CHSSBillPkgList(String billid) throws Exception
	{
		List<CHSSBillPkg> list= new ArrayList<CHSSBillPkg>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillPkg> cq= cb.createQuery(CHSSBillPkg.class);
			
			Root<CHSSBillPkg> root=cq.from(CHSSBillPkg.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSBillPkg> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSBillPkgList " + e);
		}
		return list;
	}
	
	@Override
	public long OtherBillAdd(CHSSBillOther other) throws Exception
	{
		manager.persist(other);
		manager.flush();
		
		return other.getCHSSOtherId();
	}
	
	@Override
	public long OtherBillEdit(CHSSBillOther other) throws Exception
	{
		try {
			manager.merge(other);
			manager.flush();
			
			return other.getCHSSOtherId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO OtherBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public CHSSBillOther getCHSSOther(String otherid) throws Exception
	{
		try {
			return manager.find(CHSSBillOther.class, Long.parseLong(otherid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSOther " + e);
			e.printStackTrace();
			return null;
		}
		
	}

	
	private static final String CHSSCONSULTDATALIST = "SELECT   cc.ConsultationId,  cc.BillId,  cc.ConsultType,  cc.DocName,  cc.DocQualification,  cc.ConsultDate,  cc.ConsultCharge,  cc.ConsultRemAmount, cb.BillNo,  cb.BillDate, cc.Comments , cdr.docQualification AS 'Qualification', cc.UpdateByEmpId, cc.UpdateByRole  FROM  chss_bill_consultation cc, chss_bill cb ,chss_doctor_rates cdr WHERE cc.isactive = 1 AND cb.isactive=1 AND cb.BillId = cc.BillId AND cc.docQualification=cdr.docrateid  AND cb.CHSSApplyId = :CHSSApplyId ORDER BY cc.ConsultDate ASC";

	@Override
	public List<Object[]> CHSSConsultDataList(String CHSSApplyId) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CHSSCONSULTDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSConsultDataList " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CHSSTESTSDATALIST = "SELECT   ct.CHSSTestId, ct.BillId,  ct.TestMainId,  ct.TestSubId,  ct.TestCost,ctm.TestMainName, cts.TestName,ct.TestRemAmount ,cb.BillNo,  cb.BillDate, cts.TestCode, ct.Comments, ct.UpdateByEmpId, ct.UpdateByRole  FROM  chss_bill_tests ct,  chss_test_main ctm,  chss_test_sub cts,  chss_bill cb WHERE ct.isactive = 1 AND cb.isactive=1 AND ct.TestMainId = ctm.TestMainId  AND ct.TestSubId = cts.TestSubId  AND cb.BillId = ct.BillId  AND cb.CHSSApplyId = :CHSSApplyId";
	
	@Override
	public List<Object[]> CHSSTestsDataList(String CHSSApplyId) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CHSSTESTSDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSTestsDataList " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSMEDICINEDATALIST = "SELECT   cm.CHSSMedicineId,   cm.BillId,  cm.MedicineName,  cm.MedicineCost, cm.MedQuantity,cm.presQuantity,cm.MedsRemAmount ,cb.BillNo,  cb.BillDate, cm.Comments, cm.UpdateByEmpId, cm.UpdateByRole  FROM   chss_bill_medicine cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

	@Override
	public List<Object[]> CHSSMedicineDataList(String CHSSApplyId) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CHSSMEDICINEDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSMedicineDataList " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSOTHERDATALIST = "SELECT co.CHSSOtherId,   co.BillId,  co.OtherItemId,  co.OtherItemCost,  coi.OtherItemName,co.OtherRemAmount  ,cb.BillNo,  cb.BillDate, co.Comments, co.UpdateByEmpId, co.UpdateByRole   FROM chss_bill_other co,chss_other_items coi, chss_bill cb WHERE co.isactive = 1 AND cb.isactive=1 AND  co.OtherItemId = coi.OtherItemId AND cb.BillId = co.BillId  AND cb.CHSSApplyId = :CHSSApplyId";

	@Override
	public List<Object[]> CHSSOtherDataList(String CHSSApplyId) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CHSSOTHERDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSOtherDataList " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	private static final String CHSSMISCDATALIST = "SELECT  cm.ChssMiscId,  cm.BillId,  cm.MiscItemName,  cm.MiscItemCost,cm.MiscRemAmount  ,cb.BillNo,  cb.BillDate, cm.Comments, cm.MiscCount  , cm.UpdateByEmpId, cm.UpdateByRole  FROM  chss_bill_misc cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId =:CHSSApplyId";

	@Override
	public List<Object[]> CHSSMiscDataList(String CHSSApplyId) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CHSSMISCDATALIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSMiscDataList " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	private static final String CHSSAPPLYNOCOUNT = "SELECT COUNT(chssapplyid),'count' AS 'count' FROM chss_apply WHERE chssapplyno LIKE :finYear ";

	@Override
	public String CHSSApplyNoCount(String finYear) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CHSSAPPLYNOCOUNT);
			query.setParameter("finYear", finYear+"%");
			Object[] result= (Object[])query.getSingleResult();
			return result[0].toString();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSApplyNoCount " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	

	@Override
	public List<Object[]> CHSSApproveClaimList(String logintype,String empid,String claimtype) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery("CALL chss_claims_pending (:logintype, :empid, :claimtype);");
			query.setParameter("logintype", logintype);
			query.setParameter("empid", empid);
			query.setParameter("claimtype", claimtype);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSApproveClaimList " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Object[]> CHSSClaimListRep(String type, String fromdate, String todate) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery("CALL chss_claims_rep (:fromdate,:todate,:type);");
			query.setParameter("type", type);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSClaimListRep " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<Object[]> CHSSBatchApproval(String logintype, String todate,String contingentid,String ClaimType)throws Exception
	{
		try {
			Query query = manager.createNativeQuery("CALL chss_claims_approve(:logintype, :todate,:contingentid, :ClaimType);");
			query.setParameter("logintype", logintype);
			query.setParameter("todate", todate);
			query.setParameter("contingentid", contingentid);
			query.setParameter("ClaimType", ClaimType);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSBatchApproval " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String CHSSCONTINGENTNOCOUNT = "SELECT COUNT(ContingentId),'count' AS 'count' FROM chss_contingent WHERE ContingentBillNo LIKE :finYear ";

	@Override
	public String CHSSContingentNoCount(String finYear) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CHSSCONTINGENTNOCOUNT);
			query.setParameter("finYear", "CHSS"+finYear+"%");
			Object[] result= (Object[])query.getSingleResult();
			return result[0].toString();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSContingentNoCount " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public long ContingentAdd(CHSSContingent contingent) throws Exception
	{
		manager.persist(contingent);
		manager.flush();
		
		return contingent.getContingentId();
	}
	
	@Override
	public long CHSSContingentEdit(CHSSContingent contingent) throws Exception
	{
		try {
			
			manager.merge(contingent);
			manager.flush();
			
			return contingent.getContingentId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSContingentEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	

	
	@Override
	public CHSSContingent getCHSSContingent(String contingentid) throws Exception
	{
		try {
			return manager.find(CHSSContingent.class, Long.parseLong(contingentid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSContingent " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	@Override
	public long CHSSApplyTransactionAdd(CHSSApplyTransaction transaction ) throws Exception
	{
		try {
			manager.persist(transaction);
			manager.flush();
			
			return transaction.getCHSSTransactionId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSApplyTransactionAdd " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	@Override
	public long CHSSContingentTransactionAdd(CHSSContingentTransaction transaction ) throws Exception
	{
		try {
			manager.persist(transaction);
			manager.flush();
			
			return transaction.getContinTransactionId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSContingentTransactionAdd " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	
	@Override
	public List<Object[]> getCHSSContingentList(String logintype,String fromdate,String todate) throws Exception
	{
		
		try {
			
			Query query= manager.createNativeQuery("call chss_contingent_bills_list(:logintype,:fromdate,:todate)");
			query.setParameter("logintype", logintype);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSContingentList " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String CONTINGENTAPPLYIDS  ="SELECT chssapplyid FROM chss_apply WHERE isactive=1 AND contingentid=:contingentid";
	
	@Override
	public List<Object> ContingentApplyIds(String contingentid) throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(CONTINGENTAPPLYIDS);
			query.setParameter("contingentid", contingentid);
			return (List<Object>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ContingentApplyIds " + e);
			return new ArrayList<Object>();
		}
	}
	
	private static final String CHSSCONTINGENTDATA  ="SELECT cc.contingentid,cc.ContingentBillNo,cc.ContingentDate,ClaimsCount,cc.BillsCount,cc.ContingentStatusId,cc.Remarks ,cs.chssstatus,cc.billcontent, cc.ApprovalDate,cc.gentilldate FROM chss_contingent cc , chss_status cs WHERE  cc.ContingentStatusId = cs.chssstatusid AND cc.contingentid= :contingentid ";
	@Override
	public Object[] CHSSContingentData(String contingentid) throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(CHSSCONTINGENTDATA);
			query.setParameter("contingentid", contingentid);
			return (Object[])query.getResultList().get(0);
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSContingentData " + e);
			return null;
		}
	}
	
	@Override
	public List<Object[]> CHSSContingentClaimList(String contingentid) throws Exception
	{
		try {
			Query query= manager.createNativeQuery("CALL chss_contingent_claims (:contingentid);");
			query.setParameter("contingentid", contingentid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSContingentClaimList " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	
	
	private static final String CHSSSTAUSDETAILS="SELECT a.chsstransactionid,f.empid,c.empname,d.designation,e.divisionname,a.actiondate,a.remark,b.chssstatus,b.statuscolor  FROM chss_apply_transaction a, chss_status b,employee c,employee_desig d,division_master e ,chss_apply f WHERE a.chssstatusid=b.chssstatusid  AND f.chssapplyid=a.chssapplyid AND a.actionby=c.empid  AND c.desigid=d.desigid AND c.divisionid=e.divisionid AND a.chssapplyid=:chssapplyid";
	@Override
	public List<Object[]> CHSSStatusDetails(String chssapplyid) throws Exception
	{
		
		try {
			Query query= manager.createNativeQuery(CHSSSTAUSDETAILS);
			query.setParameter("chssapplyid", chssapplyid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSStatusDetails " + e);
			return new ArrayList<Object[]>();
		}
	}
	
	
	@Override
	public List<CHSSApplyTransaction> claimTransactionObjects(String chssapplyid) throws Exception
	{
		List<CHSSApplyTransaction> list= new ArrayList<CHSSApplyTransaction>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSApplyTransaction> cq= cb.createQuery(CHSSApplyTransaction.class);
			
			Root<CHSSApplyTransaction> root=cq.from(CHSSApplyTransaction.class);								
			Predicate p1=cb.equal(root.get("CHSSApplyId"), Long.parseLong(chssapplyid));
			
			cq=cq.select(root).where(p1);
			
			
			TypedQuery<CHSSApplyTransaction> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO claimTransactionObjects " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	private static final String CONTINGENTTRANSACTIONS="SELECT a.contintransactionid,c.empname,d.designation,a.actiondate,a.remarks,b.chssstatus,b.continstatuscolor FROM chss_contingent_transaction a, chss_contingent_status b,employee c,employee_desig d,chss_contingent f WHERE a.StatusId=b.CHSSContinStatusId  AND f.contingentid=a.contingentid AND a.actionby=c.empid  AND c.desigid=d.desigid AND a.contingentid=:contingentid ORDER BY actiondate ASC";
	@Override
	public List<Object[]> ContingentTransactions(String contingentid) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CONTINGENTTRANSACTIONS);
			query.setParameter("contingentid", contingentid);
			return (List<Object[]>)query.getResultList();
		}
		catch (Exception e) 
		{
			logger.error(new Date()  + "Inside DAO ContingentTransactions " + e);
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
			logger.error(new Date()  + "Inside DAO getdata " + e);
			e.printStackTrace();
			return 0;
		}
	}
	
	
	

	@Override
	public List<CHSSMedicinesList> getCHSSMedicinesList(String treattypeid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO getCHSSMedicinesList " + e);
			e.printStackTrace();
		}
		return list;
	}
	private static final String GETCHSSCONSULTMAINLIST = "SELECT   ccm.CHSSConsultMainId,ccm.CHSSApplyId, ccm.DocName, ccm.DocQualification, (SELECT COUNT(cb1.BillId) FROM chss_bill cb1 WHERE cb1.isactive=1 AND  ccm.CHSSConsultMainId = cb1.CHSSConsultMainId AND cb1.CHSSApplyId=:applyid ) AS 'billscount' FROM   chss_consult_main ccm WHERE ccm.IsActive = 1 AND  ccm.CHSSConsultMainId IN ( SELECT cb.CHSSConsultMainId FROM chss_bill cb WHERE cb.IsActive=1 AND cb.CHSSApplyId = :applyid ) AND  ccm.CHSSApplyId <> :applyid UNION SELECT ccm.CHSSConsultMainId, ccm.CHSSApplyId, ccm.DocName,ccm.DocQualification, (SELECT COUNT(cb1.BillId) FROM chss_bill cb1 WHERE cb1.isactive=1 AND  ccm.CHSSConsultMainId = cb1.CHSSConsultMainId AND cb1.CHSSApplyId=:applyid ) AS 'billscount' FROM  chss_consult_main ccm WHERE ccm.IsActive = 1 AND  ccm.CHSSApplyId = :applyid ";
	
	@Override
	public List<Object[]> getCHSSConsultMainList(String applyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(GETCHSSCONSULTMAINLIST);
			query.setParameter("applyid", applyid);
			list =  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSConsultMainList " + e);
			e.printStackTrace();
		}
		return  list;		
	}
	
	
	
	@Override
	public List<Object[]> CHSSApprovalAuthList(String contingentid) throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery("call chss_contingent_approve_stamp(:contingentid); ");
			query.setParameter("contingentid", contingentid);
			return (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSApprovalAuthList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	private static final String CHSSAPPROVALAUTH  ="SELECT e.empno,e.empname,ed.desigid, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.desigid = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType";
	@Override
	public Object[] CHSSApprovalAuth(String Logintype) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO CHSSApprovalAuth " + e);
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
	
	private static final String CONSULTATIONHISTORY  ="SELECT cc.ConsultationId,ca.CHSSApplyNo,cb.Billno, cc.ConsultType, cc.DocName, cdr.DocQualification, cc.ConsultDate, cc.ConsultCharge, cc.ConsultRemAmount , cc.consultdate,cb.billdate FROM chss_apply ca, chss_apply ca1, chss_bill cb, chss_bill_consultation cc, chss_doctor_rates cdr WHERE ca.IsActive = 1 AND cb.isactive=1 AND cc.isactive=1 AND  cb.BillId = cc.BillId AND cb.CHSSApplyId = ca.CHSSApplyId AND cc.DocQualification=cdr.DocRateId AND ca.empid=ca1.empid AND ca.PatientId=ca1.PatientId AND ca.IsSelf = ca1.IsSelf AND ca.chssapplyid < ca1.chssapplyid AND ca1.TreatTypeId = ca.TreatTypeId AND ca.CHSSStatusId >3 AND ca1.chssapplyid=:chssapplyid ";
	@Override
	public List<Object[]> ConsultationHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CONSULTATIONHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ConsultationHistory " + e);
			e.printStackTrace();
		}
		return  list;
	}
	
	
	private static final String TESTSHISTORY  ="SELECT   ct.CHSSTestId,  ca.CHSSApplyNo,  cb.Billno,  ct.TestSubId,  cts.TestName, cts.TestCode,  ct.TestCost,  ct.TestRemAmount , cb.billdate FROM  chss_apply ca,  chss_apply ca1,  chss_bill cb,  chss_bill_tests ct,  chss_test_sub cts  WHERE ca.IsActive = 1  AND cb.isactive = 1  AND ct.isactive = 1  AND cb.BillId = ct.BillId AND cb.CHSSApplyId = ca.CHSSApplyId  AND ct.TestSubId = cts.TestSubId  AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf AND ca.CHSSStatusId >3 AND ca.chssapplyid < ca1.chssapplyid AND ca1.chssapplyid = :chssapplyid";
	@Override
	public List<Object[]> TestsHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(TESTSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO TestsHistory " + e);
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String MEDICINESHISTORY  ="SELECT   cm.CHSSMedicineId ,  ca.CHSSApplyNo,  cb.Billno,  cm.MedicineName,  cm.PresQuantity,  cm.MedQuantity,  cm.MedicineCost,  cm.MedsRemAmount, cb.BillDate FROM   chss_apply ca,  chss_apply ca1,  chss_bill cb,  chss_bill_medicine cm   WHERE ca.IsActive = 1  AND cb.isactive = 1   AND cm.isactive = 1  AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = ca.CHSSApplyId   AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf  AND ca.CHSSStatusId >3 AND ca.chssapplyid < ca1.chssapplyid  AND ca1.TreatTypeId = ca.TreatTypeId  AND ca1.chssapplyid = :chssapplyid ";
	@Override
	public List<Object[]> MedicinesHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(MEDICINESHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO MedicinesHistory " + e);
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String OTHERSHISTORY  ="SELECT   co.CHSSOtherId,  ca.CHSSApplyNo,  cb.Billno,  co.OtherItemId,  coi.OtherItemName,  co.OtherItemCost,  co.OtherRemAmount FROM  chss_apply ca,  chss_apply ca1,  chss_bill cb,  chss_bill_other co,  chss_other_items coi WHERE ca.IsActive = 1  AND cb.isactive = 1  AND co.isactive = 1  AND cb.BillId = co.BillId  AND co.OtherItemId =coi.OtherItemId  AND cb.CHSSApplyId = ca.CHSSApplyId  AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf  AND ca.CHSSStatusId >3 AND ca.chssapplyid < ca1.chssapplyid  AND ca1.chssapplyid = :chssapplyid";
	@Override
	public List<Object[]> OthersHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(OTHERSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO OthersHistory " + e);
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String MISCITEMSHISTORY  ="SELECT   cmi.ChssMiscId,  ca.CHSSApplyNo,  cb.Billno,  cmi.MiscItemName,  cmi.MiscItemCost,  cmi.MiscRemAmount, cmi.MiscCount FROM  chss_apply ca,  chss_apply ca1,  chss_bill cb,   chss_bill_misc cmi WHERE ca.IsActive = 1  AND cb.isactive = 1  AND cmi.isactive = 1  AND cb.BillId = cmi.BillId    AND cb.CHSSApplyId = ca.CHSSApplyId  AND ca.empid = ca1.empid  AND ca.PatientId = ca1.PatientId  AND ca.IsSelf = ca1.IsSelf   AND ca.CHSSStatusId >3 AND ca.chssapplyid < ca1.chssapplyid  AND ca1.chssapplyid = :chssapplyid";
	@Override
	public List<Object[]> MiscItemsHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(MISCITEMSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO MiscItemsHistory " + e);
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String CONSULTBILLSCONSULTCOUNT  ="SELECT COUNT(cc.ConsultationId) AS 'consult count' , 'count' AS 'Count' FROM chss_bill_consultation cc, chss_bill cb WHERE cb.IsActive=1 AND cc.IsActive =1 AND cb.BillId = cc.BillId AND cb.BillId = :billid AND cb.CHSSConsultMainId =:chssconsultmainid AND cb.CHSSApplyId = :chssapplyid";
	@Override
	public Object[] ConsultBillsConsultCount(String consultmainid, String chssapplyid,String billid) throws Exception
	{
		Object[] list = null;
		try {
			
			Query query= manager.createNativeQuery(CONSULTBILLSCONSULTCOUNT);
			query.setParameter("chssconsultmainid", consultmainid);
			query.setParameter("chssapplyid", chssapplyid);
			
			list=  (Object[])query.getSingleResult();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ConsultBillsConsultCount " + e);
			e.printStackTrace();
		}
		return  list;
		
	}
	
	private static final String CONSULTBILLSDELETE  ="UPDATE chss_bill SET isactive=0 WHERE CHSSConsultMainId =:consultmainid";
	@Override
	public int ConsultBillsDelete(String consultmainid) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CONSULTBILLSDELETE);
			query.setParameter("consultmainid", consultmainid);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ConsultBillsDelete " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String PATIENTCONSULTHISTORY  ="SELECT  ccm.CHSSConsultMainId, ccm.CHSSApplyId, ccm.DocName, ca.Ailment FROM  chss_apply ca,chss_apply ca1,  chss_consult_main ccm WHERE ca.CHSSApplyId = ccm.CHSSApplyId  AND ccm.isactive = 1  AND ca.isactive = 1  AND ccm.CHSSConsultMainId NOT IN  (SELECT     ccm1.CHSSConsultMainId  FROM    chss_consult_main ccm1  WHERE ccm1.CHSSApplyId = :chssapplyid) AND ca.TreatTypeId = ca1.TreatTypeId   AND ca.EmpId=ca1.EmpId AND ca.PatientId = ca1.PatientId AND ca.IsSelf = ca1.IsSelf  AND ca1.CHSSApplyId =:chssapplyid";   /*  AND ca.CHSSStatusId =14   */
	@Override
	public List<Object[]> PatientConsultHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			
			Query query= manager.createNativeQuery(PATIENTCONSULTHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO PatientConsultHistory " + e);
			e.printStackTrace();
		}
		return  list;
		
	}
		
	private static final String OLDCONSULTMEDSLIST  ="SELECT cb.CHSSApplyId, cb.BillId, cm.CHSSMedicineId, cm.MedicineName, cm.PresQuantity, cm.MedQuantity FROM chss_bill cb, chss_bill_medicine cm WHERE cb.IsActive =1 AND cm.IsActive =1 AND cb.BillId = cm.BillId AND cb.CHSSApplyId = (SELECT MIN(cb1.CHSSApplyId) FROM chss_bill cb1 WHERE  cb1.IsActive = 1 AND cb1.CHSSConsultMainId=:CHSSConsultMainId) AND cb.CHSSConsultMainId =:CHSSConsultMainId AND cb.CHSSApplyId <> :chssapplyid ";
	@Override
	public List<Object[]> OldConsultMedsList(String CHSSConsultMainId, String chssapplyid) throws Exception
	{
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(OLDCONSULTMEDSLIST);
			query.setParameter("CHSSConsultMainId", CHSSConsultMainId);
			query.setParameter("chssapplyid", chssapplyid);
			list=  (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO OldConsultMedsList " + e);
			e.printStackTrace();
		}
		return  list;
	}
	
	
	@Override
	public List<Object[]> MedAdmissibleCheck(String medicinename) throws Exception
	{
		 List<Object[]> list = null;
		try {
			String MEDADMISSIBLECHECK  ="SELECT   MedicineId, MedNo, TreatTypeId, CategoryId, MedicineName FROM chss_medicines_list WHERE IsAdmissible='N' AND IsActive=1 AND MedicineName LIKE '%"+medicinename.trim()+"%' ;";
			Query query= manager.createNativeQuery(MEDADMISSIBLECHECK);
			list=  ( List<Object[]>)query.getResultList();
		}catch (NoResultException e) {
			System.err.println ("No Result Exception");
		}catch (Exception e)
		{
			logger.error(new Date()  + "Inside DAO MedAdmissibleCheck " + e);
			e.printStackTrace();
		}
		return  list;
	}
	
	private static final String MEDADMISSIBLELIST  ="SELECT   MedicineId, MedNo, TreatTypeId, CategoryId, MedicineName,IsAdmissible FROM chss_medicines_list WHERE IsActive=1  AND TreatTypeId=:treattype AND IsActive=1 AND MedicineName LIKE :medicinename ";
	@Override
	public List<Object[]> MedAdmissibleList(String medicinename, String treattype)throws Exception
	{
		List<Object[]> list = null;
		try {
			
			Query query= manager.createNativeQuery(MEDADMISSIBLELIST);
			query.setParameter("medicinename","%"+medicinename.trim()+"%");
			query.setParameter("treattype", treattype);
			
			
			list=  ( List<Object[]>)query.getResultList();
			
		}catch (NoResultException e) {
			System.err.println ("No Result Exception");
		}catch (Exception e)
		{
			logger.error(new Date()  + "Inside DAO MedAdmissibleList " + e);
			e.printStackTrace();
		}
		return  list;
	}
	
	private static final String POACKNOWLDGEDUPDATE  ="UPDATE chss_apply SET POAcknowledge = :poacknowledge WHERE chssapplyid=:chssapplyid";
	@Override
	public int POAcknowldgedUpdate(String chssapplyid,String poacknowledge)throws Exception
	{
		try {
			
			Query query= manager.createNativeQuery(POACKNOWLDGEDUPDATE);
			query.setParameter("chssapplyid",chssapplyid);
			query.setParameter("poacknowledge", poacknowledge);
			
			return query.executeUpdate();
		}		
		catch (Exception e)
		{
			logger.error(new Date()  + "Inside DAO POAcknowldgedUpdate " + e);
			e.printStackTrace();
			return 0;
		}
		
	}

	private static final String CLAIMAPPROVEDPOVODATA  ="SELECT e.empid,'PO' ,e.EmpName,ed.DesigCode, ed.designation FROM chss_apply ca, employee e, employee_desig ed WHERE ca.POId = e.EmpId AND e.desigid = ed.DesigId AND ca.CHSSApplyId = :chssapplyid UNION SELECT e.empid,'VO' ,e.EmpName,ed.DesigCode, ed.designation FROM chss_apply ca, employee e, employee_desig ed WHERE ca.VOId = e.EmpId AND e.desigid = ed.DesigId AND ca.CHSSApplyId = :chssapplyid";
	@Override
	public List<Object[]> ClaimApprovedPOVOData(String chssapplyid) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(" CALL chss_claim_approve_stamp(:chssapplyid);");
			query.setParameter("chssapplyid", chssapplyid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimApprovedPOVOData " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String CLAIMREMARKSHISTORY  ="SELECT cat.CHSSStatusId,cat.Remark, cs.CHSSStatus,  e.EmpName,  ed.designation FROM  chss_status cs,  chss_apply_transaction cat,  chss_apply ca,  employee e,  employee_desig ed WHERE cat.ActionBy = e.EmpId AND e.desigid = ed.DesigId  AND cs.CHSSStatusId = cat.CHSSStatusId AND ca.chssapplyid = cat.chssapplyid AND cs.CHSSStatusId<=6 AND TRIM(cat.Remark)<>'' AND ca.chssapplyid =:chssapplyid  ORDER BY cat.ActionDate ASC";
	@Override
	public List<Object[]> ClaimRemarksHistory(String chssapplyid) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CLAIMREMARKSHISTORY);
			query.setParameter("chssapplyid", chssapplyid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimRemarksHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String GETLABCODE  ="SELECT labcode,LabName FROM lab_master";
	@Override
	public Object[] getLabCode() throws Exception
	{

		try {
			
			Query query= manager.createNativeQuery(GETLABCODE);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			if(list.size()>0) {
				return list.get(0);
			}else {
				return null;
			}
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getLabCode " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	private static final String CONTINGENTHISTORY  ="SELECT cct.ContinTransactionId,  DATE (cct.ActionDate),  ccs.CHSSStatus,  e.EmpName  FROM  chss_contingent_transaction cct,  chss_contingent_status ccs,  employee e WHERE cct.StatusId = ccs.CHSSContinStatusId  AND cct.ActionBy = e.EmpId  AND cct.ContingentId = :contingentid  ORDER BY cct.ActionDate";
	@Override
	public List<Object[]> ContingentBillHistory(String contingentid) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CONTINGENTHISTORY);
			query.setParameter("contingentid", contingentid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ContingentBillHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String CONTINGENTBILLREMARKHISTORY  ="SELECT cct.ContinTransactionId,  DATE (cct.ActionDate),  ccs.CHSSStatus,  e.EmpName,  cct.Remarks FROM  chss_contingent_transaction cct,  chss_contingent_status ccs,  employee e WHERE cct.StatusId = ccs.CHSSContinStatusId  AND TRIM(cct.Remarks) <> ''  AND cct.ActionBy = e.EmpId  AND cct.ContingentId =:contingentid ORDER BY cct.ActionDate ASC";
	@Override
	public List<Object[]> ContingentBillRemarkHistory(String contingentid) throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query= manager.createNativeQuery(CONTINGENTBILLREMARKHISTORY);
			query.setParameter("contingentid", contingentid);
			list= (List<Object[]>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ContingentBillRemarkHistory " + e);
			e.printStackTrace();
		}
		return list;
	}
	

	@Override
	public List<Object[]> GetClaimsList(String fromdate , String todate ,  String empid,String status)throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("call chss_all_claims(:empid , :fromdate , :todate , :status);");
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			query.setParameter("empid", empid);
			query.setParameter("status", status);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetClaimsList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	private static final String EMPLOYEESLIST = "SELECT e.empid,e.empname,ed.Designation,e.desigid FROM employee e, employee_desig ed,employee_details edt WHERE e.DesigId = ed.DesigId AND e.EmpNo=edt.EmpNo AND edt.EmpStatus='P' ORDER BY e.srno DESC;";
	@Override
	public List<Object[]> EmployeesList()throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery(EMPLOYEESLIST);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO EmployeesList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public List<Object[]> GetClaimsReportList( String empid,String fromdate , String todate , String claimtype, String status)throws Exception
	{
		List<Object[]> list =new ArrayList<Object[]>();
		try {
			Query query = manager.createNativeQuery("CALL chss_claims_report(:empid , :fromdate , :todate ,:claimtype, :status);");
			query.setParameter("empid", empid);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			query.setParameter("claimtype", claimtype);
			query.setParameter("status", status);
			list = (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetClaimsReportList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String CLAIMCONSULTMAINLIST = "SELECT DISTINCT ccm.chssconsultmainid,ccm.docname,ccm.docQualification,cdr.docqualification as 'Qualification' FROM chss_apply ca, chss_bill cb,chss_consult_main ccm,chss_doctor_rates cdr WHERE cb.isactive=1 AND cb.chssconsultmainid = ccm.chssconsultmainid AND ca.chssapplyid=cb.chssapplyid AND ccm.docqualification = cdr.docrateid AND ca.chssapplyid= :CHSSApplyId";

	@Override
	public List<Object[]> ClaimConsultMainList(String CHSSApplyId) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CLAIMCONSULTMAINLIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimConsultMainList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
		
	}
	
	@Override
	public long ClaimDisputeAdd(CHSSApplyDispute dispute) throws Exception
	{
		try {
			manager.persist(dispute);
			manager.flush();
			
			return dispute.getCHSSDisputeId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimDisputeAdd " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long ClaimDisputeEdit(CHSSApplyDispute dispute) throws Exception
	{
		try {
			manager.merge(dispute);
			manager.flush();
			
			return dispute.getCHSSDisputeId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimDisputeEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String CLAIMCONSULTMAINDELETEALL  ="UPDATE chss_consult_main SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE chssapplyid=:chssapplyid";
	@Override
	public int claimConsultMainDeleteAll(String chssapplyid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CLAIMCONSULTMAINDELETEALL);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO claimConsultMainDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String BILLCONSULTDELETEALL  ="UPDATE chss_bill_consultation SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billConsultDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLCONSULTDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billConsultDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String BILLTESTSDELETEALL  ="UPDATE chss_bill_tests SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billTestsDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLTESTSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billTestsDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLMEDSDELETEALL  ="UPDATE chss_bill_medicine SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billMedsDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLMEDSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billMedsDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLOTHERSDELETEALL  ="UPDATE chss_bill_other SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billOthersDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLOTHERSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billOthersDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLMISCDELETEALL  ="UPDATE chss_bill_misc SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billMiscDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLMISCDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billMiscDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLEQUIPMENTDELETEALL  ="UPDATE chss_bill_equipment SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billEquipmentDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLEQUIPMENTDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billEquipmentDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLIMPLANTDELETEALL  ="UPDATE chss_bill_implants SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billImplantDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLIMPLANTDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billImplantDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLPACKAGEDELETEALL  ="UPDATE chss_bill_pkg SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billPackageDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLPACKAGEDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billPackageDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	private static final String BILLPACKAGEITEMSDELETEALL  ="UPDATE chss_bill_pkgitems SET isactive=0 , modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE billid=:billid";
	@Override
	public int billPackageItemsDeleteAll(String billid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(BILLPACKAGEITEMSDELETEALL);
			query.setParameter("billid", billid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO billPackageItemsDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String CLAIMBILLDELETEALL  ="UPDATE chss_bill SET isactive=0, modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE chssapplyid=:chssapplyid";
	@Override
	public int claimBillDeleteAll(String chssapplyid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CLAIMBILLDELETEALL);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO claimBillDeleteAll " + e);
			e.printStackTrace();
		}
		return  0;
		
	}
	
	
	private static final String CLAIMDELETE  ="UPDATE chss_apply SET isactive=0, modifiedby=:modifiedby ,modifieddate=:modifieddate  WHERE chssapplyid=:chssapplyid";
	@Override
	public int claimDelete(String chssapplyid,String modifiedby,String modifieddate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CLAIMDELETE);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifieddate", modifieddate);
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO claimDelete " + e);
			e.printStackTrace();
		}
		return  0;
	}
	
	private static final String UPDATEBILLADMISSIBLEAMT  ="UPDATE chss_bill SET AdmissibleTotal = :admissibleAmt WHERE billid= :billid";
	@Override
	public int UpdateBillAdmissibleTotal(String billid,String admissibleAmt) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(UPDATEBILLADMISSIBLEAMT);
			query.setParameter("billid", billid);
			query.setParameter("admissibleAmt", admissibleAmt);
			
			return  query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO UpdateBillAdmissibleTotal " + e);
			e.printStackTrace();
		}
		return  0;
		
	}

	@Override
	public CHSSIPDClaimsInfo IpdClaimInfo(String chssapplyid) throws Exception
	{
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
			logger.error(new Date()  + "Inside DAO IpdClaimInfo " + e);
			return null;
		}
		return returnlist;
	}
	
	@Override
	public long CHSSIPDBasicInfoAdd(CHSSIPDClaimsInfo model ) throws Exception
	{
		try {
			manager.persist(model);
			manager.flush();
			
			return model.getIPDClaimInfoId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSIPDBasicInfoAdd " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public CHSSIPDClaimsInfo getIpcClaimInfo(String ipdclaiminfoid) throws Exception
	{
		try {
			return manager.find(CHSSIPDClaimsInfo.class, Long.parseLong(ipdclaiminfoid));
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getIpcClaimInfo " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	@Override
	public long CHSSIPDBasicInfoEdit(CHSSIPDClaimsInfo claimsinfo) throws Exception
	{
		try {
			manager.merge(claimsinfo);
			manager.flush();
			
			return claimsinfo.getIPDClaimInfoId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSIPDBasicInfoEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String MAXMEDNO="SELECT MAX(medno) FROM chss_medicines_list WHERE treattypeid=:treattype";
	@Override
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(MAXMEDNO);
			query.setParameter("treattype", treatmenttype);
			Integer result = (Integer) query.getSingleResult();
			return result;
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO GetMaxMedNo " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public Long AddMedicine(CHSSMedicinesList medicine)throws Exception
	{
		try {
			manager.persist(medicine);
			manager.flush();
			return medicine.getMedicineId();
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO AddMedicine " + e);
			e.printStackTrace();
			return 0l;
		}
		
	}
	
	
	@Override
	public Long AddMasterEditComments(MasterEdit masteredit)throws Exception
	{
		try {
			manager.persist(masteredit);
			manager.flush();
			return (long)masteredit.getMasterEditId();
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO AddMasterEditComments " + e);
			e.printStackTrace();
			return 0l;
		}
	}
	
	
	
	private static final String  IPDBILLOTHERITEMS = "SELECT coi.OtherItemId, coi.OtherItemname,cbo.CHSSOtherId, cbo.Billid,cbo.otheritemcost,cbo.otherremamount,cbo.comments,cbo.UpdateByEmpId,cbo.UpdateByRole  FROM (SELECT * FROM chss_other_items WHERE isactive=1) coi LEFT JOIN (SELECT * FROM chss_bill_other WHERE isactive=1 AND billid=:billid ) cbo ON (cbo.OtherItemId = coi.OtherItemId) ORDER BY coi.OtherItemId;";
	
	@Override
	public List<Object[]> IPDBillOtherItems(String billid)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(IPDBILLOTHERITEMS);
			query.setParameter("billid", billid);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IPDBillOtherItems " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	@Override
	public CHSSBillOther getCHSSIPDOther(long CHSSOtherId,long billid,int OtherItemId) throws Exception
	{
		CHSSBillOther Bhead= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillOther> cq= cb.createQuery(CHSSBillOther.class);
			Root<CHSSBillOther> root= cq.from(CHSSBillOther.class);	
			
			if(CHSSOtherId == 0) {
				Predicate p1=cb.equal(root.get("BillId") , billid);
				Predicate p2=cb.equal(root.get("OtherItemId") , OtherItemId);
				Predicate p3=cb.equal(root.get("IsActive") , 1);
				cq=cq.select(root).where(p1,p2,p3);
			}else if(CHSSOtherId > 0) {
			
				Predicate p1=cb.equal(root.get("CHSSOtherId") , CHSSOtherId);
				cq=cq.select(root).where(p1);
			}
			
			TypedQuery<CHSSBillOther> allquery = manager.createQuery(cq);
			Bhead= allquery.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSIPDOther " + e);
			e.printStackTrace();
			return null;
		}
		return Bhead;
	}
	
	
	

	private static final String  CONSULTMAINBILLIDS = "SELECT billid,chssconsultmainid FROM chss_bill cb WHERE chssconsultmainid =:consultmainid";
	
	@Override
	public List<Object[]> consultMainBillIds(String consultmainid)throws Exception
	{
		try {
			Query query = manager.createNativeQuery(CONSULTMAINBILLIDS);
			query.setParameter("consultmainid", consultmainid);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO consultMainBillIds " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	private static final String  CHECKPREVCONSULTINFO = "SELECT cb.billid,cb.chssconsultmainid,cc.consultationid,cc.consulttype,cc.DocQualification,cc.consultdate,cb.billdate FROM chss_bill_consultation cc, chss_bill cb , chss_apply ca  WHERE cb.billid=cc.billid AND cc.isactive=1 AND cb.isactive=1 AND ca.chssapplyid=cb.chssapplyid AND ca.chssstatusid > 1  AND cc.consultRemAmount > 0 AND cc.consultationid <> :consultationid  AND cb.chssconsultmainid= :consultmainid AND  (cc.consultdate BETWEEN :fromdate AND :todate ) ";
	
	@Override
	public List<Object[]> CheckPrevConsultInfo(String consultationid,long consultmainid,String fromdate,String todate)throws Exception
	{
		try {
		
			Query query = manager.createNativeQuery(CHECKPREVCONSULTINFO);
			query.setParameter("consultmainid",consultmainid);
			query.setParameter("consultationid", consultationid);
			query.setParameter("fromdate", fromdate);
			query.setParameter("todate", todate);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CheckPrevConsultInfo " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	private static final String GETCLAIMDISPUTEDATA="SELECT cad.CHSSDisputeId,cad.CHSSApplyId,cad.DisputeMsg,cad.ResponseMsg,cad.RaisedTime,cad.ResponderEmpid,cad.ResponseTime,cad.DispStatus,e.empname AS 'Responder'  FROM   chss_apply_dispute cad LEFT JOIN employee e ON e.empid = cad.ResponderEmpId   WHERE cad.isactive=1  AND cad.chssapplyid=:chssapplyid ;";
	
	@Override
	public Object[] getClaimDisputeData(String chssapplyid) throws Exception
	{
		try {
			Query query = manager.createNativeQuery(GETCLAIMDISPUTEDATA);
			query.setParameter("chssapplyid",chssapplyid);
			return (Object[]) query.getResultList().get(0);
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getClaimDisputeData " + e);
			return null;
		}
	}
	
	private static final String  CLAIMDISPUTELIST = "SELECT cad.CHSSDisputeId,cad.CHSSApplyId,cad.DisputeMsg,cad.ResponseMsg,DATE(cad.RaisedTime),cad.ResponderEmpid,DATE(cad.ResponseTime),cad.DispStatus,e.empname AS 'Responder', e1.empname AS 'raisedby',ca.chssapplyno,ca.empid  FROM   chss_apply_dispute cad LEFT JOIN employee e ON e.empid = cad.ResponderEmpId , chss_apply ca, employee e1 WHERE cad.chssapplyid = ca.chssapplyid AND ca.empid=e1.empid AND cad.isactive=1 AND cad.DispStatus ='A' ORDER BY cad.ResponseMsg,cad.raisedtime ASC ";
	
	@Override
	public List<Object[]> ClaimDisputeList(String fromdate,String todate)throws Exception
	{
		try {
			
			Query query = manager.createNativeQuery(CLAIMDISPUTELIST);
			
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimDisputeList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
private static final String  CLAIMDISPUTECLOSEDLIST = "SELECT cad.CHSSDisputeId,cad.CHSSApplyId,cad.DisputeMsg,cad.ResponseMsg,DATE(cad.RaisedTime),cad.ResponderEmpid,DATE(cad.ResponseTime),cad.DispStatus,e.empname AS 'Responder', e1.empname AS 'raisedby',ca.chssapplyno,ca.empid  FROM   chss_apply_dispute cad LEFT JOIN employee e ON e.empid = cad.ResponderEmpId , chss_apply ca, employee e1  WHERE cad.chssapplyid = ca.chssapplyid AND ca.empid=e1.empid AND cad.isactive=1 AND cad.DispStatus ='C' AND (DATE(cad.RaisedTime) BETWEEN :fromdate AND :todate )  ORDER BY cad.ResponseMsg,cad.raisedtime ASC ";
	
	@Override
	public List<Object[]> ClaimDisputeClosedList(String fromdate,String todate)throws Exception
	{
		try {
			
			Query query = manager.createNativeQuery(CLAIMDISPUTECLOSEDLIST);
			query.setParameter("fromdate",fromdate);
			query.setParameter("todate",todate);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimDisputeClosedList " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	@Override
	public long EquipmentBillAdd(CHSSBillEquipment equipment) throws Exception
	{
		manager.persist(equipment);
		manager.flush();
		
		return equipment.getCHSSEquipmentId();
	}
	
	@Override
	public CHSSBillEquipment getCHSSEquipment(String equipid) throws Exception
	{
		try {
			return manager.find(CHSSBillEquipment.class, Long.parseLong(equipid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSEquipment " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<CHSSBillEquipment> CHSSEquipmentList(String billid) throws Exception
	{
		List<CHSSBillEquipment> list= new ArrayList<CHSSBillEquipment>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillEquipment> cq= cb.createQuery(CHSSBillEquipment.class);
			
			Root<CHSSBillEquipment> root=cq.from(CHSSBillEquipment.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSBillEquipment> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSEquipmentList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long EquipmentBillEdit(CHSSBillEquipment equipment) throws Exception
	{
		try {
			manager.merge(equipment);
			manager.flush();
			
			return equipment.getCHSSEquipmentId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO EquipmentBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public long ImplantBillAdd(CHSSBillImplants equipment) throws Exception
	{
		manager.persist(equipment);
		manager.flush();
		
		return equipment.getCHSSImplantId();
	}
	
	@Override
	public CHSSBillImplants getCHSSImplant(String implantid) throws Exception
	{
		try {
			return manager.find(CHSSBillImplants.class, Long.parseLong(implantid));
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSImplant " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public List<CHSSBillImplants> CHSSImplantList(String billid) throws Exception
	{
		List<CHSSBillImplants> list= new ArrayList<CHSSBillImplants>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillImplants> cq= cb.createQuery(CHSSBillImplants.class);
			
			Root<CHSSBillImplants> root=cq.from(CHSSBillImplants.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			
			TypedQuery<CHSSBillImplants> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSImplantList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long ImplantBillEdit(CHSSBillImplants implant) throws Exception
	{
		try {
			manager.merge(implant);
			manager.flush();
			
			return implant.getCHSSImplantId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ImplantBillEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String  IPDCLAIMATTACHMENTS = "SELECT a.IPDAttachTypeId,a.Attachmenttype,b.ipdattachid,b.chssapplyid,b.copyattached FROM  (SELECT iat.IPDAttachTypeId, iat.Attachmenttype,iat.isactive FROM chss_ipd_attachtypes iat WHERE iat.isactive=1) a LEFT JOIN (SELECT ia.ipdattachid,ia.ipdattachtypeid, ia.chssapplyid,ia.copyattached,ia.isactive FROM chss_ipd_attachments ia WHERE ia.isactive=1 AND ia.chssapplyid=:chssapplyid )b ON a.IPDAttachTypeId=b.IPDAttachTypeId ORDER BY a.IPDAttachTypeId";
	
	@Override
	public List<Object[]> IPDClaimAttachments(String chssapplyid)throws Exception
	{
		try {
			
			Query query = manager.createNativeQuery(IPDCLAIMATTACHMENTS);
			query.setParameter("chssapplyid", chssapplyid);
			return (List<Object[]>) query.getResultList();
			
		} catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IPDClaimAttachments " + e);
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	
	
	private static final String GETIPDCLAIMATTACH="FROM chss_ipd_attachments WHERE chssapplyid=:chssapplyid AND IPDAttachtypeid=:attachtypeid  ";
	@Override
	public CHSSIPDAttachments getIPDClaimAttach(String chssapplyid,String attachtypeid)throws Exception
	{
		try {
			Query query = manager.createQuery(GETIPDCLAIMATTACH);
			query.setParameter("chssapplyid", chssapplyid);
			query.setParameter("attachtypeid", attachtypeid);
			return (CHSSIPDAttachments) query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getIPDClaimAttach " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public long IPDClaimAttachAdd(CHSSIPDAttachments Attach) throws Exception
	{
		try {
			manager.persist(Attach);
			manager.flush();
			
			return Attach.getIPDAttachId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IPDClaimAttachAdd " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	@Override
	public long IPDClaimAttachEdit(CHSSIPDAttachments Attach) throws Exception
	{
		try {
			manager.merge(Attach);
			manager.flush();
			
			return Attach.getIPDAttachId();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO IPDClaimAttachEdit " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	private static final String CLAIMPACKAGESLIST = "SELECT bp.chssbillpkgid,bp.billid,bp.testsubid,cts.testname,bp.packagecost,bp.pkgRemAmt,bp.comments,bp.UpdateByEmpId,bp.UpdateByRole,cts.testcode FROM chss_bill_pkg bp, chss_test_sub cts WHERE bp.isactive=1 AND bp.testsubid=cts.testsubid AND bp.billid=:billid";

	@Override
	public List<Object[]> ClaimPackagesList(String billid) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CLAIMPACKAGESLIST);
			query.setParameter("billid", billid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimPackagesList " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String CLAIMALLPACKAGEITEMSLIST = "SELECT bpi.BillPkgItemId,bpi.billid,bpi.CHSSBillPkgId,ipi.pkgItemName,bpi.pkgItemcost,bpi.PkgItemRemAmt,bpi.comments,bpi.UpdateByEmpId,bpi.UpdateByRole FROM chss_bill_pkgItems bpi, chss_ipd_pkgitems ipi WHERE bpi.isactive=1 AND bpi.IPDPkgItemId=ipi.IPDPkgItemId AND bpi.billid=:billid ORDER BY bpi.chssbillpkgid,bpi.Ipdpkgitemid;";

	@Override
	public List<Object[]> ClaimAllPackageItemsList(String billid) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CLAIMALLPACKAGEITEMSLIST);
			query.setParameter("billid", billid);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimAllPackageItemsList " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String CLAIMEQUIPMENTLIST = "SELECT  cm.CHSSEquipmentId,  cm.BillId,  cm.EquipmentName,  cm.EquipmentCost,cm.EquipmentRemAmt  ,cb.BillNo,  cb.BillDate, cm.Comments  , cm.UpdateByEmpId, cm.UpdateByRole  FROM  chss_bill_equipment cm,  chss_bill cb WHERE cm.isactive = 1 AND cb.isactive=1 AND cb.BillId = cm.BillId  AND cb.CHSSApplyId = :CHSSApplyId ; ";

	@Override
	public List<Object[]> ClaimEquipmentList(String CHSSApplyId) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CLAIMEQUIPMENTLIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimEquipmentList " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String CLAIMIMPLANTLIST = "SELECT  ci.CHSSImplantId,  ci.BillId,  ci.ImplantName,  ci.ImplantCost,ci.ImplantRemAmt  ,cb.BillNo,  cb.BillDate, ci.Comments  , ci.UpdateByEmpId, ci.UpdateByRole FROM  chss_bill_implants ci,  chss_bill cb WHERE ci.isactive = 1 AND cb.isactive=1 AND cb.BillId = ci.BillId  AND cb.CHSSApplyId = :CHSSApplyId ;";

	@Override
	public List<Object[]> ClaimImplantList(String CHSSApplyId) throws Exception 
	{
		try {
			Query query= manager.createNativeQuery(CLAIMIMPLANTLIST);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (List<Object[]>)query.getResultList();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimImplantList " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public List<CHSSBillEquipment> BillEquipmentList(String billid) throws Exception
	{
		List<CHSSBillEquipment> list= new ArrayList<CHSSBillEquipment>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillEquipment> cq= cb.createQuery(CHSSBillEquipment.class);
			
			Root<CHSSBillEquipment> root=cq.from(CHSSBillEquipment.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSBillEquipment> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO BillEquipmentList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<CHSSBillImplants> BillImplantsList(String billid) throws Exception
	{
		List<CHSSBillImplants> list= new ArrayList<CHSSBillImplants>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillImplants> cq= cb.createQuery(CHSSBillImplants.class);
			
			Root<CHSSBillImplants> root=cq.from(CHSSBillImplants.class);								
			Predicate p1=cb.equal(root.get("BillId") , Long.parseLong(billid));
			Predicate p2=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSBillImplants> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO BillImplantsList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<CHSSIPDPkgItems> getCHSSIPDPkgItemsList() throws Exception
	{
		List<CHSSIPDPkgItems> list= new ArrayList<CHSSIPDPkgItems>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSIPDPkgItems> cq= cb.createQuery(CHSSIPDPkgItems.class);
			
			Root<CHSSIPDPkgItems> root=cq.from(CHSSIPDPkgItems.class);	
			
			Predicate p1=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1);
			
			TypedQuery<CHSSIPDPkgItems> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSIPDPkgItemsList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long CHSSIPDBillPackageAdd(CHSSBillPkg billpkg ) throws Exception
	{
		manager.persist(billpkg);
		manager.flush();
		
		return billpkg.getCHSSBillPkgId();
	}
	
	@Override
	public long CHSSIPDBillPackageItemAdd(CHSSBillPkgItems billpkgItem ) throws Exception
	{
		manager.persist(billpkgItem);
		manager.flush();
		
		return billpkgItem.getBillPkgItemId();
	}
	
	@Override
	public CHSSBillPkg getCHSSBillPkg(String billpkgId) throws Exception
	{
		CHSSBillPkg pkg=null;
		try {
			
			pkg=manager.find(CHSSBillPkg.class, Long.parseLong(billpkgId));
		
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSBillPkg " + e);
			return null;
		}
		return pkg;
	}
	
	@Override
	public CHSSBillPkgItems getCHSSIPDPkgItem(String chssbillpkgid,String ipdpkgitemid ) throws Exception
	{
		CHSSBillPkgItems list= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillPkgItems> cq= cb.createQuery(CHSSBillPkgItems.class);
			
			Root<CHSSBillPkgItems> root=cq.from(CHSSBillPkgItems.class);	
			
			Predicate p1=cb.equal(root.get("IsActive") , 1);
			Predicate p2=cb.equal(root.get("CHSSBillPkgId") , Long.parseLong(chssbillpkgid));
			Predicate p3=cb.equal(root.get("IPDPkgItemId") , Integer.parseInt(ipdpkgitemid));
			
			cq=cq.select(root).where(p1,p2,p3);
			
			TypedQuery<CHSSBillPkgItems> allquery = manager.createQuery(cq);
			list= allquery.getSingleResult();
			
		}catch (NoResultException e) {
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSIPDPkgItem " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<CHSSBillPkgItems> getCHSSIPDPkgItemsList(String chssbillpkgid ) throws Exception
	{
		List<CHSSBillPkgItems> list= new ArrayList<CHSSBillPkgItems>();
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSBillPkgItems> cq= cb.createQuery(CHSSBillPkgItems.class);
			
			Root<CHSSBillPkgItems> root=cq.from(CHSSBillPkgItems.class);	
			
			Predicate p1=cb.equal(root.get("IsActive") , 1);
			Predicate p2=cb.equal(root.get("CHSSBillPkgId") , Long.parseLong(chssbillpkgid));
			
			cq=cq.select(root).where(p1,p2);
			
			TypedQuery<CHSSBillPkgItems> allquery = manager.createQuery(cq);
			list= allquery.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO getCHSSIPDPkgItemsList " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long CHSSBillPkgEdit(CHSSBillPkg billpkg) throws Exception
	{
		try {
			manager.merge(billpkg);
			manager.flush();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSBillPkgEdit " + e);
			return 0;
		}
		return billpkg.getCHSSBillPkgId();
	}
	
	@Override
	public long CHSSBillPkgItemEdit(CHSSBillPkgItems  billpkgItem) throws Exception
	{
		try {
			manager.merge(billpkgItem);
			manager.flush();
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO CHSSBillPkgItemEdit " + e);
			return 0;
		}
		return billpkgItem.getBillPkgItemId();
	}
	
	private static final String CLAIMPKGITEMSADDEDAJAX = "FROM chss_bill_pkgitems WHERE isactive=1 AND BillId=:billid AND CHSSBillPkgId=:billpkgid";
	@Override
	public List<CHSSIPDPkgItems> ClaimPkgItemsAddedAjax(String billid, String billpkgid) throws Exception
	{
		List<CHSSIPDPkgItems> list= new ArrayList<CHSSIPDPkgItems>();
		try {
			Query query= manager.createQuery(CLAIMPKGITEMSADDEDAJAX);
			query.setParameter("billid", Long.parseLong(billid));
			query.setParameter("billpkgid", Long.parseLong(billpkgid));
			return (List<CHSSIPDPkgItems>)query.getResultList();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimPkgItemsAddedAjax " + e);
			e.printStackTrace();
		}
		return list;
	}
	
	private static final String CLAIMPKGITEMSDELETE = "UPDATE chss_bill_pkgitems SET isactive=0, modifiedby=:modifiedby, modifiedDate =:modifiedDate WHERE CHSSBillPkgId = :chssbillpkgid ";
	@Override
	public int ClaimPkgItemsDelete(String chssbillpkgid,String modifiedby,String modifiedDate) throws Exception
	{
		try {
			Query query= manager.createNativeQuery(CLAIMPKGITEMSDELETE);
			query.setParameter("chssbillpkgid", chssbillpkgid);
			query.setParameter("modifiedby", modifiedby);
			query.setParameter("modifiedDate", modifiedDate);
			return query.executeUpdate();
			
		}catch (Exception e) {
			logger.error(new Date()  + "Inside DAO ClaimPkgItemsDelete " + e);
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
	@Override
	public CHSSIPDClaimsInfo getCHSSIPDClaimsInfo(String chssapplyid) throws Exception
	{
		CHSSIPDClaimsInfo list= null;
		try {
			CriteriaBuilder cb= manager.getCriteriaBuilder();
			CriteriaQuery<CHSSIPDClaimsInfo> cq= cb.createQuery(CHSSIPDClaimsInfo.class);
			Root<CHSSIPDClaimsInfo> root= cq.from(CHSSIPDClaimsInfo.class);		
			
			
			Predicate p1=cb.equal(root.get("IsActive") , 1);
			
			cq=cq.select(root).where(p1);
			
			TypedQuery<CHSSIPDClaimsInfo> allquery = manager.createQuery(cq);
			list= allquery.getResultList().get(0);
			
		}catch (NoResultException e) {
			System.err.println ("No Result Exception");
		}catch (Exception e)
		{
			logger.error(new Date()  + "Inside DAO getCHSSIPDClaimsInfo " + e);
			e.printStackTrace();
		}
		return list;
	}

	private static final String EMPANELLEDHOSPITALLIST="SELECT EmpanelledHospitalId,HospitalName,HospitalAddress  FROM chss_empanelledhospital WHERE Isactive=1 ORDER BY EmpanelledHospitalId DESC";
	@Override
	public List<Object[]> GetEmpanelledHostpitalList()throws Exception
	{
		 try {
				Query query =  manager.createNativeQuery(EMPANELLEDHOSPITALLIST);
				
				return (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO GetEmpanelledHostpitalList "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String GETDOCTOREMPANELLEDLIST="SELECT doctorid ,doctorname , qualification, address, phoneno FROM chss_doctor_list WHERE Isactive=1 ORDER BY doctorid DESC";
	@Override
	public List<Object[]> GetDoctorEmpanelledList()throws Exception
	{
		try {
				Query query =  manager.createNativeQuery(GETDOCTOREMPANELLEDLIST);
				
				return (List<Object[]>)query.getResultList();
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO GetDoctorEmpanelledList "+ e);
			e.printStackTrace();
			return null;
		}
	}



private static final String EMPLIST="select e.EmpId,e.EmpName from employee e,employee_details ed where e.EmpNo=ed.EmpNo and ed.EmpStatus='P' and e.isactive=1";
	@Override
	public List<Object[]> getEmpList() throws Exception {
		try {
			Query query = manager.createNativeQuery(EMPLIST);
			List<Object[]> list =(List<Object[]>) query.getResultList();
			manager.flush();
			return list;
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO getEmpList "+ e);
			e.printStackTrace();
			;
		}	
		return null;
	}


private static final String AllDEPENDANTLIST="SELECT a.EmpNo,a.EmpName ,b.member_name,b.dob,b.med_dep_from ,c.relation_name FROM employee a,pis_emp_family_details b,pis_emp_family_relation c \r\n"
		+ "WHERE a.EmpId=b.empid AND b.relation_id=c.relation_id AND b.med_dep ='y' AND b.isactive=1 ORDER BY b.empid ASC";
private static final String DEPENDANTLIST="SELECT a.EmpNo,a.EmpName ,b.member_name,b.dob,b.med_dep_from ,c.relation_name FROM employee a,pis_emp_family_details b,pis_emp_family_relation c \r\n"
		+ "WHERE a.EmpId=b.empid AND b.relation_id=c.relation_id AND b.med_dep ='y' AND b.isactive=1 AND b.empid=:empid";
	@Override
	public List<Object[]> getDependantsList(String empNo) throws Exception {
		List<Object[]> list=null;
	try {
		if(empNo.equals("A")) {
		Query query = manager.createNativeQuery(AllDEPENDANTLIST);
		
		 list =(List<Object[]>) query.getResultList();
		}else {
			Query qury = manager.createNativeQuery(DEPENDANTLIST);	
			qury.setParameter("empid", empNo);
			 list =(List<Object[]>) qury.getResultList();
		manager.flush();
		}
		return list;
		
	} catch (Exception e) {
		logger.error(new Date() +" Inside DAO getDependantsList "+ e);
		e.printStackTrace();
		return null;
	}
		
	}


	private static final String GETEMPNO = "SELECT EmpNo,EmpName FROM employee WHERE EmpId=:EmpId";
	@Override
	public Object[] getEmpNo(String EmpId) throws Exception {
		try {
			Query query = manager.createNativeQuery(GETEMPNO);
			query.setParameter("EmpId", EmpId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO getEmpNo "+ e);
			e.printStackTrace();
			return null;
		}
	}

	private static final String DISPUTELIST="CALL chss_dispute_list(:EmpId)";
	@Override
	public List<Object[]> DisputeList(long EmpId) throws Exception {
		List<Object[]> list=null;
		try {
			Query query= manager.createNativeQuery(DISPUTELIST);
			query.setParameter("EmpId", EmpId);
			list= (List<Object[]>)query.getResultList();
			manager.flush();
			return list;
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO DisputeList "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String CHSSREAPPLYDETAILS = "SELECT CHSSApplyNo,Ailment,EmpId,PatientId,IsSelf,CHSSType,TreatTypeId,NoEnclosures,CreatedBy FROM chss_apply WHERE CHSSApplyId=:CHSSApplyId";
	@Override
	public Object[] CHSSReApplyDetails(String CHSSApplyId) throws Exception{
		try {
			Query query = manager.createNativeQuery(CHSSREAPPLYDETAILS);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyDetails "+ e);
			e.printStackTrace();
			return null;
		}
	}
		
	private static final String REAPPLYBILL="SELECT CHSSApplyId,CHSSConsultMainId,BillNo,BillDate,CenterName,Discount,DiscountPercent,FinalBillAmt FROM chss_bill WHERE BillId=:BillId";
	@Override
	public Object[] CHSSReApplyBill(String BillId) throws Exception{
		try {
			Query query = manager.createNativeQuery(REAPPLYBILL);
			query.setParameter("BillId", BillId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyBill "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String REAPPLYCONSULTMAIN="  SELECT CHSSApplyId,DocName,DocQualification FROM chss_consult_main WHERE CHSSconsultMainId=:consultaionMainId";
	@Override
	public Object[] CHSSReApplyConsultMain(String consultaionMainId) throws Exception{
		try {
			Query query = manager.createNativeQuery(REAPPLYCONSULTMAIN);
			query.setParameter("consultaionMainId", consultaionMainId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyConsultMain "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String REAPPLYCONSULT="SELECT BillId,ConsultType,DocName,DocQualification,ConsultCharge,AmountPaid,ConsultRemAmount,Comments,ConsultDate FROM chss_bill_consultation WHERE ConsultationId=:ConsultationId";
	@Override
	public Object[] CHSSReApplyConsult(String ConsultationId) throws Exception{
		try {
			Query query = manager.createNativeQuery(REAPPLYCONSULT);
			query.setParameter("ConsultationId", ConsultationId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyConsult "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String REAPPLYTEST="SELECT BillId,TestMainId,TestSubId,TestCost,AmountPaid,TestRemAmount,Comments FROM chss_bill_tests WHERE CHSSTestId=:CHSSTestId";
	@Override
	public Object[] CHSSReApplyTest(String CHSSTestId) throws Exception{
		try {
			Query query = manager.createNativeQuery(REAPPLYTEST);
			query.setParameter("CHSSTestId", CHSSTestId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyTest "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String REAPPLYMEDICINE="SELECT BillId,MedicineName,MedQuantity,PresQuantity,MedicineCost,AmountPaid,MedsRemAmount,Comments FROM chss_bill_medicine WHERE CHSSMedicineId=:CHSSTMedicineId";
	@Override
	public Object[] CHSSReApplyMedicine(String CHSSTMedicineId) throws Exception {
		try {
			Query query = manager.createNativeQuery(REAPPLYMEDICINE);
			query.setParameter("CHSSTMedicineId", CHSSTMedicineId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyMedicine "+ e);
			e.printStackTrace();
			return null;
		}
	}

	private static final String REAPPLYMISC="SELECT BillId,MiscItemName,MiscItemCost,AmountPaid,MiscRemAmount,MiscCount,Comments FROM chss_bill_misc WHERE ChssMiscId=:CHSSMiscId";
	@Override
	public Object[] CHSSReApplyMisc(String CHSSMiscId) throws Exception {
		try {
			Query query = manager.createNativeQuery(REAPPLYMISC);
			query.setParameter("CHSSMiscId", CHSSMiscId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyMisc "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String REAPPLYBILLIDS="SELECT NewBillId,OldBillId FROM chss_bill_reapply WHERE OldBillId=:OldBillId";
	@Override
	public Object[] CHSSReApplyBillIds(String OldBillId) throws Exception {
		Object[] BillId=null;
		try {
			Query query = manager.createNativeQuery(REAPPLYBILLIDS);
			query.setParameter("OldBillId", OldBillId);
			BillId=(Object[])query.getSingleResult();
			return BillId;
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSReApplyBillIds "+ e);
			return null;
		}
		
	}
	

	
	private static final String UPDATEDISPUTE="UPDATE chss_apply_dispute SET DispReapplyStatus='S' WHERE CHSSApplyId=:CHSSApplyId";
	@Override
	public long UpdateCHSSDispute(String CHSSApplyId) throws Exception{
		try {
			Query query = manager.createNativeQuery(UPDATEDISPUTE);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (long)query.executeUpdate();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO UpdateCHSSDispute "+ e);
			e.printStackTrace();
			return 0L;
		}
	}
		
	private static final String DISPREAPPLYSTATUS = "SELECT a.Action,a.DispReapplyStatus FROM chss_apply_dispute a WHERE CHSSApplyId =:CHSSApplyId";
	@Override
	public Object[] CHSSDispReApplyStatus(String CHSSApplyId) throws Exception {
		try {
			Query query = manager.createNativeQuery(DISPREAPPLYSTATUS);
			query.setParameter("CHSSApplyId", CHSSApplyId);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO CHSSDispReApplyStatus "+ e);
			e.printStackTrace();
			return null;
		}
	}
	
	private static final String OLDCHSSAPPLYDETAILS = "SELECT CHSSApplyId,CHSSApplyNo FROM chss_apply WHERE CHSSApplyNo=:CHSSApplyNo";
	@Override
	public Object[] OldCHSSApplyDetails(String CHSSApplyNo) throws Exception {
		try {
			Query query = manager.createNativeQuery(OLDCHSSAPPLYDETAILS);
			query.setParameter("CHSSApplyNo", CHSSApplyNo);
			return (Object[])query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO OldCHSSApplyDetails "+ e);
			e.printStackTrace();
			return null;
		}
	}
}

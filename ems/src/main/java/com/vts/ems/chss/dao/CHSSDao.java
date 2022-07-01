package com.vts.ems.chss.dao;

import java.time.LocalDate;
import java.util.List;

import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSApplyTransaction;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultMain;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.chss.model.CHSSContingentTransaction;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMedicinesList;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSOtherPermitAmt;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.model.EMSNotification;

public interface CHSSDao {

	public List<Object[]> familyDetailsList(String empid) throws Exception;
	public Object[] getEmployee(String empid) throws Exception;
	public Object[] familyMemberData(String familydetailsid) throws Exception;
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception;
	public long CHSSApplyAdd(CHSSApply apply) throws Exception;
	public long CHSSBillAdd(CHSSBill bill) throws Exception;
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception;
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception;
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception;
	public List<Object[]> empCHSSList(String empid,String PatientId, LocalDate FromDate, LocalDate Todate, String IsSelf) throws Exception;
	public CHSSBill getCHSSBill(String billid) throws Exception;
	public long CHSSBillEdit(CHSSBill bill) throws Exception;
	public CHSSApply getCHSSApply(String chssapplyid) throws Exception;
	public long CHSSApplyEdit(CHSSApply apply) throws Exception;
	public List<CHSSTestMain> CHSSTestMainList() throws Exception;
	public List<CHSSTestSub> CHSSTestSubList() throws Exception;
	public long ConsultationBillAdd(CHSSConsultation consult) throws Exception;
	public List<CHSSConsultation> CHSSConsultationList(String billid) throws Exception;
	public CHSSConsultation getCHSSConsultation(String consultationid) throws Exception;
	public long ConsultationBillEdit(CHSSConsultation consult) throws Exception;
	public long MedicinesBillAdd(CHSSMedicine medicine) throws Exception;
	public List<CHSSMedicine> CHSSMedicineList(String billid) throws Exception;
	public CHSSMedicine getCHSSMedicine(String medicineid) throws Exception;
	public long MedicineBillEdit(CHSSMedicine medicine) throws Exception;
	public long TestsBillAdd(CHSSTests test) throws Exception;
	public List<CHSSTests> CHSSTestsList(String billid) throws Exception;
	public long MiscBillAdd(CHSSMisc misc) throws Exception;
	public List<CHSSMisc> CHSSMiscList(String billid) throws Exception;
	public long MiscBillEdit(CHSSMisc Misc) throws Exception;
	public CHSSMisc getCHSSMisc(String chssMiscid) throws Exception;
	public Object[] CHSSBill(String billid) throws Exception;
	public List<CHSSOtherItems> OtherItemsList() throws Exception;
	public List<CHSSOther> CHSSOtherList(String billid) throws Exception;
	public long OtherBillAdd(CHSSOther other) throws Exception;
	public long OtherBillEdit(CHSSOther other) throws Exception;
	public CHSSOther getCHSSOther(String otherid) throws Exception;
	public long TestBillEdit(CHSSTests test) throws Exception;
	public CHSSTests getCHSSTest(String chsstestid) throws Exception;
	public List<Object[]> CHSSTestsDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSConsultDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSMedicineDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSOtherDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSMiscDataList(String CHSSApplyId) throws Exception;
	public String CHSSApplyNoCount(String finYear) throws Exception;
	public List<Object[]> CHSSApproveClaimList(String logintype,String empid) throws Exception;
	public CHSSTestSub getCHSSTestSub(String testsubid) throws Exception;
	public CHSSOtherItems getCHSSOtherItems(String otheritemid) throws Exception;
	public List<Object[]> CHSSClaimListRep(String type, String fromdate, String todate) throws Exception;
	public List<Object[]> CHSSBatchApproval(String logintype,String fromdate, String todate,String contingentid) throws Exception;
	public String CHSSContingentNoCount(String finYear) throws Exception;
	public long ContingentAdd(CHSSContingent other) throws Exception;
	public long CHSSContingentEdit(CHSSContingent contingent) throws Exception;
	public CHSSContingent getCHSSContingent(String contingentid) throws Exception;
	public long CHSSApplyTransactionAdd(CHSSApplyTransaction Transaction) throws Exception;
	public List<Object[]> getCHSSContingentList(String logintype,String fromdate,String todate ) throws Exception;
	public List<Object[]> CHSSContingentClaimList(String contingentid) throws Exception;
	public Object[] CHSSContingentData(String contingentid) throws Exception;
	public List<Object[]> CHSSStatusDetails(String chssapplyid) throws Exception;
	public List<CHSSDoctorRates> getCHSSDoctorRates(String treattypeid) throws Exception;
	public CHSSDoctorRates getDocterRate(String rateid) throws Exception;
	public Object[] claimConsultationsCount(String chssapplyid) throws Exception;
	public Object[] claimMedicinesCount(String chssapplyid) throws Exception;
	public int getdata(String bill)throws Exception;
	public List<Object> ContingentApplyIds(String contingentid) throws Exception;
	public List<CHSSMedicinesList> getCHSSMedicinesList(String treattypeid) throws Exception;
	public List<Object[]> CHSSApprovalAuthList(String contingentid) throws Exception;
	public Object[] CHSSApprovalAuth(String Logintype) throws Exception;
	public long NotificationAdd(EMSNotification notification) throws Exception;
	public List<Object[]> ConsultationHistory(String chssapplyid) throws Exception;
	public List<Object[]> TestsHistory(String chssapplyid) throws Exception;
	public List<Object[]> MedicinesHistory(String chssapplyid) throws Exception;
	public List<Object[]> OthersHistory(String chssapplyid) throws Exception;
	public List<Object[]> MiscItemsHistory(String chssapplyid) throws Exception;
	public CHSSOtherPermitAmt getCHSSOtherPermitAmt(String otheritemid, long basicpay) throws Exception;
	public long CHSSConsultMainAdd(CHSSConsultMain consultmain) throws Exception;
	public List<Object[]> getCHSSConsultMainList(String applyid) throws Exception;
	public long CHSSConsultMainEdit(CHSSConsultMain consultmain) throws Exception;
	public CHSSConsultMain getCHSSConsultMain(String ConsultMainId) throws Exception;
	public List<Object[]> CHSSConsultMainBillsList(String consultmainid, String chssapplyid) throws Exception;
	public Object[] ConsultBillsConsultCount(String consultmainid, String chssapplyid,String billid) throws Exception;
	public int ConsultBillsDelete(String consultmainid) throws Exception;
	public int CHSSConsultMainDelete(String consultmainid) throws Exception;
	public List<Object[]> PatientConsultHistory(String chssapplyid) throws Exception;
	public List<Object[]> OldConsultMedsList(String CHSSConsultMainId, String chssapplyid) throws Exception;
	public List<Object[]> MedAdmissibleCheck(String medicinename) throws Exception;
	public List<Object[]> MedAdmissibleList(String medicinename, String treattype) throws Exception;
	public List<CHSSTestSub> CHSSTestSubListWithAyur() throws Exception;
	public int POAcknowldgedUpdate(String chssapplyid, String poacknowledge) throws Exception;
	public List<Object[]> ClaimApprovedPOVOData(String chssapplyid) throws Exception;
	public List<Object[]> ClaimRemarksHistory(String chssapplyid) throws Exception;
	public Object[] getLabCode() throws Exception;
	public long CHSSContingentTransactionAdd(CHSSContingentTransaction transaction) throws Exception;
	public List<Object[]> ContingentBillHistory(String contingentid) throws Exception;
	public List<Object[]> ContingentBillRemarkHistory(String contingentid) throws Exception;
	public List<Object[]> GetClaimsList(String fromdate , String todate ,  String empid)throws Exception;
	public List<Object[]> EmployeesList() throws Exception;
	public List<Object[]> GetClaimsReport(String fromdate, String todate, String empid) throws Exception;
	
	
}

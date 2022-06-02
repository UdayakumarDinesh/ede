package com.vts.ems.chss.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSContingentDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSMiscDto;
import com.vts.ems.chss.Dto.CHSSOtherDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
import com.vts.ems.chss.Dto.ChssBillsDto;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultMain;
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
import com.vts.ems.pis.model.Employee;

public interface CHSSService 
{

	public List<Object[]> familyDetailsList(String empid) throws Exception;
	public Employee getEmployee(String empid) throws Exception;
	public Object[] familyMemberData(String familydetailsid) throws Exception;
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception;
	public long CHSSApplySubmit(CHSSApplyDto dto) throws Exception;
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception;
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception;
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception;
	public List<Object[]> empCHSSList(String empid,String PatientId, String FromDate, String Todate, String IsSelfs) throws Exception;
	public CHSSBill getCHSSBill(String billid) throws Exception;
	public long CHSSBillEdit(CHSSBill bill) throws Exception;
	public long CHSSBillDelete(String billid, String modifiedby) throws Exception;
	public long CHSSApplyEdit(CHSSApplyDto dto) throws Exception;
	public List<CHSSTestSub> CHSSTestSubList(String treattypeid) throws Exception;
	public List<CHSSTestMain> CHSSTestMainList() throws Exception;
	public long ConsultationBillAdd(CHSSConsultationDto dto,String chssapplyid, String consultmainidold) throws Exception;
	public List<CHSSConsultation> CHSSConsultationList(String billid) throws Exception;
	public long ConsultationBillEdit(CHSSConsultation modal,String chssapplyid, String consultmainidold) throws Exception;
	public long ConsultationBillDelete(String consultationid, String modifiedby) throws Exception;
	public long MedicinesBillAdd(CHSSMedicineDto dto, String chssapplyid) throws Exception;
	public List<CHSSMedicine> CHSSMedicineList(String billid) throws Exception;
	public long MedicineBillDelete(String medicineid, String modifiedby) throws Exception;
	public long MedicineBillEdit(CHSSMedicine modal, String chssapplyid) throws Exception;
	public long TestsBillAdd(CHSSTestsDto dto) throws Exception;
	public List<CHSSTests> CHSSTestsList(String billid) throws Exception;
	public long MiscBillAdd(CHSSMiscDto dto) throws Exception;
	public List<CHSSMisc> CHSSMiscList(String billid) throws Exception;
	public long MiscBillEdit(CHSSMisc modal) throws Exception;
	public long MiscBillDelete(String chssotherid, String modifiedby) throws Exception;
	public Object[] CHSSBill(String billid) throws Exception;
	public List<CHSSOtherItems> OtherItemsList() throws Exception;
	public List<CHSSOther> CHSSOtherList(String billid) throws Exception;
	public long OtherBillAdd(CHSSOtherDto dto) throws Exception;
	public long OtherBillEdit(CHSSOther modal,String  Empid) throws Exception;
	public long OtherBillDelete(String chssotherid, String modifiedby) throws Exception;
	public long TestBillEdit(CHSSTests modal) throws Exception;
	public long TestBillDelete(String chsstestid, String modifiedby) throws Exception;
	public List<Object[]> CHSSTestsDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSConsultDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSMedicineDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSOtherDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSMiscDataList(String CHSSApplyId) throws Exception;
	public long CHSSUserForward(String CHSSApplyId, String Username, String action,String remarks, String EmpId,String LoginType) throws Exception;
	public List<Object[]> CHSSApproveClaimList(String logintype,String empid) throws Exception;
	public long ConsultRemAmountEdit(CHSSConsultation modal) throws Exception;
	public long TestRemAmountEdit(CHSSTests modal) throws Exception;
	public long OtherRemAmountEdit(CHSSOther modal) throws Exception;
	public long MedRemAmountEdit(CHSSMedicine modal) throws Exception;
	public long MiscRemAmountEdit(CHSSMisc modal) throws Exception;
	public List<Object[]> CHSSClaimListRep(String type, String fromdate, String todate) throws Exception;
	public List<Object[]> CHSSBatchApproval(String logintype,String fromdate, String todate,String contingentid) throws Exception;
	public long CHSSClaimsApprove(CHSSContingentDto dto)throws Exception;
	public CHSSContingent getCHSSContingent(String contingentid) throws Exception;
	public List<Object[]> getCHSSContingentList(String logintype,String fromdate,String todate) throws Exception;
	public HashMap<Long, ArrayList<Object[]>> CHSSContingentClaimList(String contingentid) throws Exception;
	public Object[] CHSSContingentData(String contingentid) throws Exception;
	public List<Object[]> CHSSStatusDetails(String chssapplyid) throws Exception;
	public List<CHSSDoctorRates> getCHSSDoctorRates(String treattypeid) throws Exception;
	public Object[] claimMedicinesCount(String chssapplyid) throws Exception;
	public Object[] claimConsultationsCount(String chssapplyid) throws Exception;
	public long CHSSApplyEncCountEdit(CHSSApplyDto dto) throws Exception;
	public long ContingentGenerate(String[] CHSSApplyId, String Username, String action, String billcontent, String logintype,String EmpId) throws Exception;
	public List<CHSSMedicinesList> getCHSSMedicinesList(String treattypeid) throws Exception;
	public List<Object[]> CHSSApprovalAuthList(String contingentid) throws Exception;
	public List<Object[]> ConsultationHistory(String chssapplyid) throws Exception;
	public List<Object[]> TestsHistory(String chssapplyid) throws Exception;
	public List<Object[]> MedicinesHistory(String chssapplyid, String treattypeid) throws Exception;
	public List<Object[]> OthersHistory(String chssapplyid) throws Exception;
	public List<Object[]> MiscItemsHistory(String chssapplyid) throws Exception;
	public List<Object[]> getCHSSConsultMainList(String applyid) throws Exception;
	public long CHSSConsultMainEdit(CHSSConsultMain consultmain) throws Exception;
	public long CHSSConsultMainDelete(String consultmainid, String modifiedby) throws Exception;
	public CHSSConsultMain getCHSSConsultMain(String ConsultMainId) throws Exception;
	public long CHSSConsultBillsAdd(ChssBillsDto dto) throws Exception;
	public List<Object[]> CHSSConsultMainBillsList(String consultmainid, String chssapplyid) throws Exception;
	public Object[] ConsultBillsConsultCount(String consultmainid, String chssapplyid,String billid) throws Exception;
	public List<Object[]> PatientConsultHistory(String chssapplyid) throws Exception;
	public List<Object[]> OldConsultMedsList(String CHSSConsultMainId, String chssapplyid) throws Exception;
	public List<Object[]> MedAdmissibleCheck(String medicinename) throws Exception;
	public List<Object[]> MedAdmissibleList(String medicinename, String treattype) throws Exception;
	public int POAcknowldgedUpdate(String chssapplyid, String poacknowledge) throws Exception;
	public long CHSSUserRevoke(String CHSSApplyId, String Username, String EmpId) throws Exception;
	public List<Object[]> ClaimApprovedPOVOData(String chssapplyid) throws Exception;
	public List<Object[]> ClaimRemarksHistory(String chssapplyid) throws Exception;
	public Object[] getLabCode() throws Exception;
	public List<Object[]> ContingentBillHistory(String contingentid) throws Exception;
	public List<Object[]> ContingentBillRemarkHistory(String contingentid) throws Exception;
	public long CHSSContingentDelete(String contingentid, String Username) throws Exception;

	
}

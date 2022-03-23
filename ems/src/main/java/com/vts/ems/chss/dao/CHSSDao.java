package com.vts.ems.chss.dao;

import java.util.List;

import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.pis.model.Employee;

public interface CHSSDao {

	public List<Object[]> familyDetailsList(String empid) throws Exception;
	public Employee getEmployee(String empid) throws Exception;
	public Object[] familyMemberData(String familydetailsid) throws Exception;
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception;
	public long CHSSApplyAdd(CHSSApply apply) throws Exception;
	public long CHSSBillAdd(CHSSBill bill) throws Exception;
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception;
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception;
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception;
	public List<Object[]> empCHSSList(String empid) throws Exception;
	public CHSSBill getCHSSBill(String billid) throws Exception;
	public long CHSSBillEdit(CHSSBill bill) throws Exception;
	public CHSSApply getCHSSApply(String chssapplyid) throws Exception;
	public long CHSSApplyEdit(CHSSApply apply) throws Exception;
	public List<CHSSTestMain> CHSSTestMainList() throws Exception;
	public List<CHSSTestSub> CHSSTestSubList(String testmainid) throws Exception;
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

}

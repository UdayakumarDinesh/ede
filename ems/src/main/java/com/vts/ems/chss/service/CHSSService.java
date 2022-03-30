package com.vts.ems.chss.service;

import java.util.List;

import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSMiscDto;
import com.vts.ems.chss.Dto.CHSSOtherDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
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

public interface CHSSService {

	public List<Object[]> familyDetailsList(String empid) throws Exception;
	public Employee getEmployee(String empid) throws Exception;
	public Object[] familyMemberData(String familydetailsid) throws Exception;
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception;
	public long CHSSApplySubmit(CHSSApplyDto dto) throws Exception;
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception;
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception;
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception;
	public List<Object[]> empCHSSList(String empid) throws Exception;
	public CHSSBill getCHSSBill(String billid) throws Exception;
	public long CHSSBillEdit(CHSSBill bill) throws Exception;
	public long CHSSBillDelete(String billid, String modifiedby) throws Exception;
	public long CHSSApplyEdit(CHSSApplyDto dto) throws Exception;
	public List<CHSSTestSub> CHSSTestSubList(String testmainid) throws Exception;
	public List<CHSSTestMain> CHSSTestMainList() throws Exception;
	public long ConsultationBillAdd(CHSSConsultationDto dto) throws Exception;
	public List<CHSSConsultation> CHSSConsultationList(String billid) throws Exception;
	public long ConsultationBillEdit(CHSSConsultation modal) throws Exception;
	public long ConsultationBillDelete(String consultationid, String modifiedby) throws Exception;
	public long MedicinesBillAdd(CHSSMedicineDto dto) throws Exception;
	public List<CHSSMedicine> CHSSMedicineList(String billid) throws Exception;
	public long MedicineBillDelete(String medicineid, String modifiedby) throws Exception;
	public long MedicineBillEdit(CHSSMedicine modal) throws Exception;
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
	public long OtherBillEdit(CHSSOther modal) throws Exception;
	public long OtherBillDelete(String chssotherid, String modifiedby) throws Exception;
	public long TestBillEdit(CHSSTests modal) throws Exception;
	public long TestBillDelete(String chsstestid, String modifiedby) throws Exception;
	public List<Object[]> CHSSTestsDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSConsultDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSMedicineDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSOtherDataList(String CHSSApplyId) throws Exception;
	public List<Object[]> CHSSMiscDataList(String CHSSApplyId) throws Exception;
	public long CHSSUserForward(String CHSSApplyId, String Username, String action) throws Exception;
	public List<Object[]> CHSSApproveClaimList(String logintype, String fromdate, String todate) throws Exception;
	public long ConsultRemAmountEdit(CHSSConsultation modal) throws Exception;
	
	

}

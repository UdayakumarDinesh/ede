package com.vts.ems.chss.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSMiscDto;
import com.vts.ems.chss.Dto.CHSSOtherDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSApplyTransaction;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSContingent;
import com.vts.ems.chss.model.CHSSDoctorRates;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSMisc;
import com.vts.ems.chss.model.CHSSOther;
import com.vts.ems.chss.model.CHSSOtherItems;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class CHSSServiceImpl implements CHSSService {

	private static final Logger logger = LogManager.getLogger(CHSSServiceImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Autowired
	CHSSDao dao;
	
	@Override
	public List<Object[]> familyDetailsList(String empid) throws Exception
	{
		return dao.familyDetailsList(empid);
	}
	
	@Override
	public Employee getEmployee(String empid) throws Exception
	{
		return dao.getEmployee(empid);
	}
	
	@Override
	public Object[] familyMemberData(String familydetailsid) throws Exception
	{
		return dao.familyMemberData(familydetailsid);
	}
	
	@Override
	public List<CHSSTreatType> CHSSTreatTypeList() throws Exception
	{
		return dao.CHSSTreatTypeList();
	}
	
	@Override
	public Object[] CHSSBill(String billid) throws Exception
	{
		return dao.CHSSBill(billid);
	}
	
	
	@Override
	public long CHSSApplySubmit(CHSSApplyDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSApplySubmit");		
		try {
			long applyid=0;
			if(dto.getCHSSApplyId()==null) {
				CHSSApply apply= new CHSSApply();
				apply.setEmpId(Long.parseLong(dto.getEmpId()));
				apply.setPatientId(Long.parseLong(dto.getPatientId()));
				
				if(dto.getRelationId().equalsIgnoreCase("0")) {
					apply.setIsSelf("Y");
				}else {
					apply.setIsSelf("N");
				}
				
				apply.setFollowUp("N");
				apply.setCHSSNewId(0L);
				apply.setCHSSType(dto.getCHSSType());
				apply.setTreatTypeId(Integer.parseInt(dto.getTreatTypeId()));
				apply.setNoEnclosures(Integer.parseInt(dto.getNoEnclosures()));
				apply.setAilment(dto.getAilment());
				apply.setCHSSStatusId(1);
				apply.setIsActive(1);
				apply.setCreatedBy(dto.getCreatedBy());
				apply.setCreatedDate(sdtf.format(new Date()));
				apply.setCHSSApplyDate(sdf.format(new Date()));
				apply.setRemarks(dto.getRemarks());
				apply.setCHSSApplyNo(GenerateCHSSClaimNo());
				apply.setContingentId(0L);
				applyid=dao.CHSSApplyAdd(apply);
				
				CHSSApplyTransaction transac =new CHSSApplyTransaction();
				transac.setCHSSApplyId(applyid);
				transac.setCHSSStatusId(1);
				transac.setRemark("");
				transac.setActionBy(Long.parseLong(dto.getEmpId()));
				transac.setActionDate(sdtf.format(new Date()));
				dao.CHSSApplyTransactionAdd(transac);
				
			}else
			{
				applyid=Long.parseLong(dto.getCHSSApplyId());
			}
			
			long billid =0;
			for(int i=0 ; i<dto.getCenterName().length && applyid>0 ; i++)
			{
				CHSSBill bill = new CHSSBill();
				bill.setCHSSApplyId(applyid);
				bill.setCenterName(dto.getCenterName()[i].trim());
				bill.setBillNo(dto.getBillNo()[i]);
				bill.setBillDate(sdf.format(rdf.parse(dto.getBillDate()[i])));
				bill.setIsActive(1);
				bill.setCreatedBy(dto.getCreatedBy());
				bill.setCreatedDate(sdtf.format(new Date()));
				
				billid = dao.CHSSBillAdd(bill);
			}
			
			if(dto.getCHSSApplyId()==null) {
				return applyid;
			}
			
			return billid;
			
			
						
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE CHSSApplySubmit");
			return 0;
		}
		
	}
	
	public String GenerateCHSSClaimNo() throws Exception
	{
		LocalDate today= LocalDate.now();
		String start ="";
		String end="";
		
		int currentmonth= today.getMonthValue();
		if(currentmonth<4) 
		{
			start = String.valueOf(today.getYear()-1).substring(2);
			end =String.valueOf(today.getYear()).substring(2);
		}
		else
		{
			start=String.valueOf(today.getYear()).substring(2);
			end =String.valueOf(today.getYear()+1).substring(2);
		}				
		
		String applynocount=dao.CHSSApplyNoCount(start+end);
		String CNo = start+end+(Long.parseLong(applynocount)+1);
		
		return CNo;
	}
	
	@Override
	public CHSSApply CHSSApplied(String chssapplyid) throws Exception
	{
		return dao.CHSSApplied(chssapplyid);
	}
	
	@Override
	public Object[] CHSSAppliedData(String chssapplyid) throws Exception
	{
		return dao.CHSSAppliedData(chssapplyid);
	}
	@Override
	public List<Object[]> CHSSBillsList(String chssapplyid) throws Exception
	{
		return dao.CHSSBillsList(chssapplyid);
	}
	@Override
	public List<Object[]> empCHSSList(String empid) throws Exception
	{
		List<Object[]> empCHSSList =  dao.empCHSSList(empid);
		return empCHSSList;
	}
	@Override
	public CHSSBill getCHSSBill(String billid) throws Exception
	{
		return dao.getCHSSBill(billid);
	}
	
	@Override
	public Object[] claimMedicinesCount(String chssapplyid) throws Exception
	{
		return dao.claimMedicinesCount(chssapplyid);
	}
	
	@Override
	public Object[] claimConsultationsCount(String chssapplyid) throws Exception
	{
		return dao.claimConsultationsCount(chssapplyid);
	}
	
	
	@Override
	public long CHSSBillEdit(CHSSBill bill) throws Exception
	{
		
		CHSSBill fetch = dao.getCHSSBill(String.valueOf(bill.getBillId()));
		
		fetch.setCenterName(bill.getCenterName());
		fetch.setBillNo(bill.getBillNo());
		fetch.setBillDate(bill.getBillDate());
		fetch.setModifiedBy(bill.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		
		return dao.CHSSBillEdit(fetch);
				
	}
	
	@Override
	public long CHSSBillDelete(String billid, String modifiedby) throws Exception
	{
		
		CHSSBill fetch = dao.getCHSSBill(billid);
		
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		return dao.CHSSBillEdit(fetch);
				
	}
	
	@Override
	public long CHSSApplyEdit(CHSSApplyDto dto) throws Exception
	{
		
		CHSSApply fetch = dao.getCHSSApply(dto.getCHSSApplyId());
		fetch.setTreatTypeId(Integer.parseInt(dto.getTreatTypeId()));
//		fetch.setNoEnclosures(Integer.parseInt(dto.getNoEnclosures()));
		fetch.setAilment(dto.getAilment());
		fetch.setModifiedBy(dto.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
			
		return dao.CHSSApplyEdit(fetch);
	}
	
	@Override
	public long CHSSApplyEncCountEdit(CHSSApplyDto dto) throws Exception
	{
		CHSSApply fetch = dao.getCHSSApply(dto.getCHSSApplyId());
		fetch.setNoEnclosures(Integer.parseInt(dto.getNoEnclosures()));
		return dao.CHSSApplyEdit(fetch);
	}
	
	
	
	@Override
	public List<CHSSTestSub> CHSSTestSubList(String testmainid) throws Exception
	{
		return dao.CHSSTestSubList(testmainid);
	}
	
	@Override
	public List<CHSSTestMain> CHSSTestMainList() throws Exception
	{
		return dao.CHSSTestMainList();
	}
	
	@Override
	public List<CHSSConsultation> CHSSConsultationList(String billid) throws Exception
	{
		return dao.CHSSConsultationList(billid);
	}
	
	@Override
	public long ConsultationBillAdd(CHSSConsultationDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ConsultationBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getDocName().length ; i++)
			{
				CHSSConsultation consult = new CHSSConsultation();
				
				consult.setBillId(Long.parseLong(dto.getBillId()));
				consult.setConsultType(dto.getConsultType()[i]);
				consult.setDocName(dto.getDocName()[i]);
				consult.setDocQualification(dto.getDocQualification()[i]);
				consult.setConsultDate(sdf.format(rdf.parse(dto.getConsultDate()[i])));
				consult.setConsultCharge(Integer.parseInt(dto.getConsultCharge()[i]));
				
				consult.setConsultRemAmount(getConsultEligibleAmount(consult.getConsultCharge(),consult.getDocQualification(),consult.getConsultType()));
				
				consult.setIsActive(1);
//				consult.setCreatedBy(dto.getCreatedBy());
//				consult.setCreatedDate(sdtf.format(new Date()));				
				count = dao.ConsultationBillAdd(consult);
					
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE ConsultationBillAdd");
			return 0;
		}
		
	}
	
	public Integer getConsultEligibleAmount(int applyamount,String speciality,String  isfresh) throws Exception 
	{
		CHSSDoctorRates rate  = dao.getDocterRate(speciality);
		int allowedamt=0;
		
		if(isfresh.equalsIgnoreCase("Fresh")) 
		{
			allowedamt = rate.getConsultation_1();
		}else
		{
			allowedamt = rate.getConsultation_2();
		}
		
		
		if(allowedamt<=applyamount) {
			return allowedamt;
		}else {
			return applyamount;
		}
		
		
		
//		if(isfresh.equalsIgnoreCase("Fresh")) 
//		{
//			if(speciality.equalsIgnoreCase("Specialist")) 
//			{
//				if(applyamount>=350) {
//					return 350;
//				}else {
//					return applyamount;
//				}
//			}
//			else if(speciality.equalsIgnoreCase("Super Specialist")) 
//			{
//				if(applyamount>=450) {
//					return 450;
//				}else {
//					return applyamount;
//				}
//			}
//			else if(speciality.equalsIgnoreCase("Dental Surgeons")) 
//			{
//				if(applyamount>=100) {
//					return 100;
//				}else {
//					return applyamount;
//				}
//			}
//		}
//		else if(isfresh.equalsIgnoreCase("FollowUp")) 
//		{
//			if(speciality.equalsIgnoreCase("Specialist")) 
//			{
//				if(applyamount>=300) {
//					return 300;
//				}else {
//					return applyamount;
//				}
//			}
//			else if(speciality.equalsIgnoreCase("Super Specialist")) 
//			{
//				if(applyamount>=350) {
//					return 350;
//				}else {
//					return applyamount;
//				}
//			}
//			else if(speciality.equalsIgnoreCase("Dental Surgeons")) 
//			{
//				if(applyamount>=100) {
//					return 100;
//				}else {
//					return applyamount;
//				}
//			}
//		}else
//		{
//			if(applyamount>=300) {
//				return 300;
//			}else
//			{
//				return applyamount;
//			}
//		}
//		

	}
	
	
	
	@Override
	public long ConsultationBillEdit(CHSSConsultation modal) throws Exception
	{
		CHSSConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultType(modal.getConsultType());
		fetch.setDocName(modal.getDocName());
		fetch.setDocQualification(modal.getDocQualification());
		fetch.setConsultDate(modal.getConsultDate());
		fetch.setConsultCharge(modal.getConsultCharge());
//		fetch.setModifiedBy(modal.getModifiedBy());
//		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setConsultRemAmount(getConsultEligibleAmount(modal.getConsultCharge(),modal.getDocQualification(),modal.getConsultType()));

		
		return dao.ConsultationBillEdit(fetch);
	}
	
	@Override
	public long ConsultationBillDelete(String consultationid, String modifiedby ) throws Exception
	{
		CHSSConsultation fetch = dao.getCHSSConsultation(consultationid);
		fetch.setIsActive(0);
//		fetch.setModifiedBy(modifiedby);
//		fetch.setModifiedDate(sdtf.format(new Date()));
		return dao.ConsultationBillEdit(fetch);
	}
	
	
	
	
	
	@Override
	public long MedicinesBillAdd(CHSSMedicineDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MedicinesBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getMedicineName().length ; i++)
			{
				CHSSMedicine  meds = new CHSSMedicine();
				
				meds.setBillId(Long.parseLong(dto.getBillId()));
				meds.setMedicineName(dto.getMedicineName()[i]);
//				meds.setMedicineDate(sdf.format(rdf.parse(dto.getMedicineDate()[i])));
				meds.setPresQuantity(Integer.parseInt(dto.getPresQuantity()[i]));
				meds.setMedQuantity(Integer.parseInt(dto.getMedQuantity()[i]));
				meds.setMedicineCost(Integer.parseInt(dto.getMedicineCost()[i]));
				meds.setMedsRemAmount(Integer.parseInt(dto.getMedicineCost()[i]));
				meds.setIsActive(1);
				count = dao.MedicinesBillAdd(meds);
				
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE MedicinesBillAdd");
			return 0;
		}
		
	}
	
	
	
	
	@Override
	public List<CHSSMedicine> CHSSMedicineList(String billid) throws Exception
	{
		return dao.CHSSMedicineList(billid);
	}
	
	@Override
	public long MedicineBillEdit(CHSSMedicine modal) throws Exception
	{
		CHSSMedicine fetch = dao.getCHSSMedicine(String.valueOf(modal.getMedicineId()));
		fetch.setMedicineName(modal.getMedicineName());
//		fetch.setMedicineDate(modal.getMedicineDate());
		fetch.setMedicineCost(modal.getMedicineCost());
		fetch.setMedQuantity(modal.getMedQuantity());
		fetch.setMedsRemAmount(modal.getMedicineCost());
		fetch.setPresQuantity(modal.getPresQuantity());
		return dao.MedicineBillEdit(fetch);
	}
	
	@Override
	public long MedicineBillDelete(String medicineid, String modifiedby ) throws Exception
	{
		CHSSMedicine fetch = dao.getCHSSMedicine(medicineid);
		fetch.setIsActive(0);
		return dao.MedicineBillEdit(fetch);
	}
	
	
	@Override
	public long TestsBillAdd(CHSSTestsDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE TestsBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getTestSubId().length; i++)
			{
				CHSSTests  test = new CHSSTests();
				
				test.setBillId(Long.parseLong(dto.getBillId()));
				test.setTestMainId(Long.parseLong(dto.getTestSubId()[i].split("_")[0]));
				test.setTestSubId(Long.parseLong(dto.getTestSubId()[i].split("_")[1]));
				test.setTestCost(Integer.parseInt(dto.getTestCost()[i]));
				test.setIsActive(1);
				test.setTestRemAmount(getTestEligibleAmount(test.getTestCost(),dto.getTestSubId()[i].toString().split("_")[1]));
				count = dao.TestsBillAdd(test);
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE TestsBillAdd");
			return 0;
		}
		
	}
	
	
	public int getTestEligibleAmount(int applyamount,String testsubid) throws Exception 
	{
		int testsubamount = dao.getCHSSTestSub(testsubid).getTestRate();
		
		if(testsubamount<= applyamount)
		{
			return testsubamount;
		}
		else
		{
			return applyamount;
		}
	}
	
	
	
	@Override
	public List<CHSSTests> CHSSTestsList(String billid) throws Exception
	{
		return dao.CHSSTestsList(billid);
	}
	
	
	@Override
	public long TestBillEdit(CHSSTests modal) throws Exception
	{
		CHSSTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestMainId(modal.getTestMainId());
		fetch.setTestSubId(modal.getTestSubId());
		fetch.setTestCost(modal.getTestCost());
		fetch.setTestRemAmount(getTestEligibleAmount(modal.getTestCost(),String.valueOf(modal.getTestSubId())));
		return dao.TestBillEdit(fetch);
	}
	
	@Override
	public long TestBillDelete(String chsstestid, String modifiedby ) throws Exception
	{
		CHSSTests fetch = dao.getCHSSTest(chsstestid);
		fetch.setIsActive(0);
		return dao.TestBillEdit(fetch);
	}
	
	
	@Override
	public long MiscBillAdd(CHSSMiscDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MiscBillAdd");		
		try {
			long count=0;
			for(int i=0 ; i<dto.getMiscItemName().length ; i++)
			{
				CHSSMisc  misc = new CHSSMisc();
				
				misc.setBillId(Long.parseLong(dto.getBillId()));
				misc.setMiscItemName(dto.getMiscItemName()[i]);
				misc.setMiscItemCost(Integer.parseInt(dto.getMiscItemCost()[i]));
				misc.setMiscRemAmount(0);
				misc.setIsActive(1);
				count = dao.MiscBillAdd(misc);
			}
			return count;
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE MiscBillAdd");
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSMisc> CHSSMiscList(String billid) throws Exception
	{
		return dao.CHSSMiscList(billid);
	}
	
	@Override
	public long MiscBillEdit(CHSSMisc modal) throws Exception
	{
		CHSSMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		fetch.setMiscItemName(modal.getMiscItemName());
		fetch.setMiscItemCost(modal.getMiscItemCost());
		return dao.MiscBillEdit(fetch);
	}
	
	@Override
	public long MiscBillDelete(String chssMiscid, String modifiedby ) throws Exception
	{
		CHSSMisc fetch = dao.getCHSSMisc(chssMiscid);
		fetch.setIsActive(0);
		return dao.MiscBillEdit(fetch);
	}
	
	@Override
	public List<CHSSOtherItems> OtherItemsList() throws Exception
	{
		return dao.OtherItemsList();
	}
	
	@Override
	public List<CHSSDoctorRates> getCHSSDoctorRates(String treattypeid) throws Exception
	{
		return dao.getCHSSDoctorRates(treattypeid);
	}
	
	@Override
	public List<CHSSOther> CHSSOtherList(String billid) throws Exception
	{
		return dao.CHSSOtherList(billid);
	}
	
	@Override
	public long OtherBillAdd(CHSSOtherDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE OtherBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getOtherItemId().length ; i++)
			{
				CHSSOther  other = new CHSSOther();
				
				other.setBillId(Long.parseLong(dto.getBillId()));
				other.setOtherItemId(Integer.parseInt(dto.getOtherItemId()[i]));
				other.setOtherItemCost(Integer.parseInt(dto.getOtherItemCost()[i]));
				other.setIsActive(1);
				
				other.setOtherRemAmount(getOtherItemRemAmount(dto.getEmpid(),dto.getOtherItemId()[i],Integer.parseInt(dto.getOtherItemCost()[i]) ));
				
				count = dao.OtherBillAdd(other);
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE OtherBillAdd");
			return 0;
		}
		
	}
	
	
	public int getOtherItemRemAmount(String empid,String otheritemid,int itemcost) throws Exception
	{
		Employee emp =dao.getEmployee(empid);
		CHSSOtherItems remlist = dao.getCHSSOtherItems(otheritemid);
		int rembamt = 0;
		if(emp.getPayLevelId()>=-1 && emp.getPayLevelId()<=6) 
		{
			rembamt = remlist.getPayLevel1();
		}
		else if(emp.getPayLevelId()>=7 && emp.getPayLevelId()<=10) 
		{
			rembamt = remlist.getPayLevel2();
		}
		else if(emp.getPayLevelId()>=11 && emp.getPayLevelId()<=14) 
		{
			rembamt = remlist.getPayLevel3();
		}
		else if(emp.getPayLevelId()>=15 && emp.getPayLevelId()<=18) 
		{
			rembamt = remlist.getPayLevel4();
		}
		
		
		if(itemcost<= rembamt)
		{
			return itemcost;
		}
		else
		{
			return rembamt;
		}
			
	}
	
	
	@Override
	public long OtherBillEdit(CHSSOther modal) throws Exception
	{
		CHSSOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherItemId(modal.getOtherItemId());
		fetch.setOtherItemCost(modal.getOtherItemCost());
		return dao.OtherBillEdit(fetch);
	}
	
	@Override
	public long OtherBillDelete(String chssotherid, String modifiedby ) throws Exception
	{
		CHSSOther fetch = dao.getCHSSOther(chssotherid);
		fetch.setIsActive(0);
		return dao.OtherBillEdit(fetch);
	}
	
	@Override
	public List<Object[]> CHSSTestsDataList(String CHSSApplyId) throws Exception
	{
		return  dao.CHSSTestsDataList(CHSSApplyId);
	}
	
	@Override
	public List<Object[]> CHSSMiscDataList(String CHSSApplyId) throws Exception 
	{
		return  dao.CHSSMiscDataList(CHSSApplyId);
	}

	@Override
	public List<Object[]> CHSSConsultDataList(String CHSSApplyId) throws Exception {
		
		return dao.CHSSConsultDataList(CHSSApplyId);
	}

	@Override
	public List<Object[]> CHSSMedicineDataList(String CHSSApplyId) throws Exception {
		
		return dao.CHSSMedicineDataList(CHSSApplyId);
	}

	@Override
	public List<Object[]> CHSSOtherDataList(String CHSSApplyId) throws Exception {
		
		return dao.CHSSOtherDataList(CHSSApplyId);
	}
	
	
	@Override
	public long CHSSUserForward(String CHSSApplyId,String Username, String action,String remarks, String EmpId) throws Exception 
	{
		CHSSApply claim = dao.getCHSSApply(CHSSApplyId);
		int claimstatus = claim.getCHSSStatusId();
		if(action.equalsIgnoreCase("F")) 
		{
			if(claimstatus==1 || claimstatus==3 ) 
			{
				claim.setCHSSStatusId(2);
				claim.setCHSSApplyDate(LocalDate.now().toString());
			}
			else if(claimstatus==2 || claimstatus==5 ) 
			{
				claim.setCHSSStatusId(4);
			}
			else if(claimstatus==4) 
			{
				claim.setCHSSStatusId(6);
			}			
		}
		
		if(action.equalsIgnoreCase("R")) 
		{
			if(claimstatus==2 || claimstatus==5 || claimstatus==6 || claimstatus==9 ) 
			{
				claim.setCHSSStatusId(3);
			}
			else if(claimstatus==4) 
			{
				claim.setCHSSStatusId(5);
			}		
			claim.setContingentId(0L);
		}
		
		claim.setRemarks(remarks);
		claim.setModifiedBy(Username);
		claim.setModifiedDate(sdf.format(new Date()));
		
		CHSSApplyTransaction transac =new CHSSApplyTransaction();
		transac.setCHSSApplyId(claim.getCHSSApplyId());
		transac.setCHSSStatusId(claim.getCHSSStatusId());
		transac.setRemark("");
		transac.setActionBy(Long.parseLong(EmpId));
		transac.setActionDate(sdtf.format(new Date()));
		dao.CHSSApplyTransactionAdd(transac);
		
		return dao.CHSSApplyEdit(claim);
	}
		
	@Override
	public List<Object[]> CHSSApproveClaimList(String logintype) throws Exception 
	{
		
		return dao.CHSSApproveClaimList(logintype);
	}
	
	@Override
	public long ConsultRemAmountEdit(CHSSConsultation modal) throws Exception
	{
		CHSSConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultRemAmount(modal.getConsultRemAmount());
		
		return dao.ConsultationBillEdit(fetch);
	}
	
	@Override
	public long TestRemAmountEdit(CHSSTests modal) throws Exception
	{
		CHSSTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestRemAmount(modal.getTestRemAmount());
		return dao.TestBillEdit(fetch);
	}
	
	@Override
	public long OtherRemAmountEdit(CHSSOther modal) throws Exception
	{
		CHSSOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherRemAmount(modal.getOtherRemAmount());
		return dao.OtherBillEdit(fetch);
	}
	
	@Override
	public long MedRemAmountEdit(CHSSMedicine modal) throws Exception
	{
		CHSSMedicine fetch = dao.getCHSSMedicine(String.valueOf(modal.getMedicineId()));
		fetch.setMedsRemAmount(modal.getMedsRemAmount());
		return dao.MedicineBillEdit(fetch);
	}
	
	@Override
	public long MiscRemAmountEdit(CHSSMisc modal) throws Exception
	{
		CHSSMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		fetch.setMiscRemAmount(modal.getMiscRemAmount());
		return dao.MiscBillEdit(fetch);
	}
	
	@Override
	public List<Object[]> CHSSClaimListRep(String type, String fromdate, String todate) throws Exception 
	{
		return dao.CHSSClaimListRep(type, fromdate, todate);
	}
	
	@Override
	public List<Object[]> CHSSBatchApproval(String logintype,String fromdate, String todate,String contingentid) throws Exception
	{
		return dao.CHSSBatchApproval(logintype, fromdate, todate,contingentid);
	}
	
	@Override
	public CHSSContingent getCHSSContingent(String contingentid) throws Exception
	{
		return dao.getCHSSContingent(contingentid);
	}
	
	
	@Override
	public long ContingentGenerate(String CHSSApplyId[],String Username, String action,String billcontent,String logintype,String EmpId) throws Exception 
	{	
		CHSSContingent continnew =new CHSSContingent();
		long contingentid=0;
		continnew.setContingentBillNo(GenerateContingentNo());
//		continnew.setContingentDate(LocalDate.now().toString()); 
		continnew.setClaimsCount(CHSSApplyId.length);
		continnew.setContingentStatusId(1);
//		continnew.setRemarks(billcontent);
		continnew.setIsActive(1);
		continnew.setCreatedBy(Username);
		continnew.setCreatedDate(sdf.format(new Date()));
		continnew.setBillContent(billcontent);
		contingentid = dao.ContingentAdd(continnew);
		
		long count=0;
		for(String claimid  : CHSSApplyId)
		{
			CHSSApply claim = dao.getCHSSApply(claimid);
			
//			claim.setCHSSStatusId(8);
			claim.setContingentId(contingentid);
			claim.setModifiedBy(Username);
			claim.setModifiedDate(sdf.format(new Date()));

//			CHSSApplyTransaction transac =new CHSSApplyTransaction();
//			transac.setCHSSApplyId(claim.getCHSSApplyId());
//			transac.setCHSSStatusId(claim.getCHSSStatusId());
//			transac.setRemark("");
//			transac.setActionBy(Long.parseLong(EmpId));
//			transac.setActionDate(sdtf.format(new Date()));
//			dao.CHSSApplyTransactionAdd(transac);
			
			count= dao.CHSSApplyEdit(claim);
		}
			
		
		return contingentid;
	}
	
	
	
	
	public String GenerateContingentNo() throws Exception
	{
		try {
			String value="STARC/F&A/Med-Regular/";
			int result = -1;
		   
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(new Date());
		        result = cal.get(Calendar.YEAR);
		     String month=  LocalDate.now().getMonth().minus(1).toString();	 
			value+=String.valueOf(result-1)+"-"+String.valueOf(result-2000)+"/"+month+"-"+result+"/";
			 int data= dao.getdata(value);
			
			 value+=++data;
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@Override
	public long CHSSClaimsApprove(String contingentid,String Username, String action,String remarks,String logintype,String EmpId) throws Exception 
	{
		long continid=0;		
		CHSSContingent contingent = dao.getCHSSContingent(contingentid);
		int continstatus = contingent.getContingentStatusId();
				
		if(action.equalsIgnoreCase("F")) 
		{
			if(continstatus==1  || continstatus==9 || continstatus==11 || continstatus==13 ) 
			{
				continstatus=8;
				contingent.setContingentDate(LocalDate.now().toString());
			}
			else if(continstatus==8  ) 
			{
				continstatus=10;
			}
			else if(continstatus==10  ) 
			{
				continstatus=12;
			}	
			else if(continstatus==12 ) 
			{
				continstatus=14;
			}	
		}
				
		if(action.equalsIgnoreCase("R")) 
		{
			if(continstatus==8 || continstatus==11 ) 
			{
				continstatus=9;
			}
			else if(continstatus==10 || continstatus==13 ) 
			{
				continstatus=11;
			}	
			else if(continstatus==12 ) 
			{
				continstatus=13;
			}	
		}
					
		contingent.setRemarks(remarks);
		contingent.setContingentStatusId(continstatus);
		contingent.setModifiedBy(Username);
		contingent.setModifiedDate(sdf.format(new Date()));
		continid=dao.CHSSContingentEdit(contingent);
		
		
		List<Object> CHSSApplyId  =dao.ContingentApplyIds(contingentid);
		
		for(Object claimid  : CHSSApplyId)
		{
			CHSSApply claim = dao.getCHSSApply(claimid.toString());
			
			claim.setCHSSStatusId(continstatus);
			claim.setModifiedBy(Username);
			claim.setModifiedDate(sdf.format(new Date()));

			CHSSApplyTransaction transac =new CHSSApplyTransaction();
			transac.setCHSSApplyId(claim.getCHSSApplyId());
			transac.setCHSSStatusId(claim.getCHSSStatusId());
			transac.setRemark("");
			transac.setActionBy(Long.parseLong(EmpId));
			transac.setActionDate(sdtf.format(new Date()));
			dao.CHSSApplyTransactionAdd(transac);
			
			continid= dao.CHSSApplyEdit(claim);
		}
		
		return continid;
		
	}
	
	@Override
	public List<Object[]> getCHSSContingentList(String logintype) throws Exception
	{
		return dao.getCHSSContingentList(logintype);
	}
	
	@Override
	public HashMap<Long, ArrayList<Object[]>> CHSSContingentClaimList(String contingentid) throws Exception
	{
		List<Object[]> claims = dao.CHSSContingentClaimList(contingentid);
		
		HashMap<Long, ArrayList<Object[]>> sortedclaims  = new HashMap<Long, ArrayList<Object[]>>();
		if(claims.size()>0) 
		{
			ArrayList<String> empstrs = new ArrayList<String>();
			for(int i=0;i<claims.size();i++)
			{
				if(!empstrs.contains(claims.get(i)[1].toString())) {
					empstrs.add(claims.get(i)[1].toString());
				}
			}
			
			ArrayList<Object[]> empclaims= new ArrayList<Object[]>();
			for(String empstr:empstrs) 
			{
				for(int i=0;i<claims.size();i++)
				{				
					if(empstr.equalsIgnoreCase(claims.get(i)[1].toString()) )
					{
						empclaims.add(claims.get(i));
						
					}
				}
				sortedclaims.put(Long.parseLong(empstr), empclaims);
				empclaims= new ArrayList<Object[]>();
			}
		}
		
		
		return sortedclaims;
	}
		
	public Object[] CHSSContingentData(String contingentid) throws Exception
	{
		return dao.CHSSContingentData(contingentid);
	}
	
	@Override
	public List<Object[]> CHSSStatusDetails(String chssapplyid) throws Exception
	{
		List<Object[]> claims = dao.CHSSStatusDetails(chssapplyid);
		
		return claims;
	}
	
	@Override
	public List<Object[]> GetApprovedBills(String bill)throws Exception
	{
		return dao.GetApprovedBills(bill);
	}
}

package com.vts.ems.chss.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSContingentDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSMiscDto;
import com.vts.ems.chss.Dto.CHSSOtherDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
import com.vts.ems.chss.Dto.ChssBillsDto;
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSApplyTransaction;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSBillMedicine;
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
import com.vts.ems.chss.model.CHSSBillTests;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class CHSSServiceImpl implements CHSSService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	
	private static final DecimalFormat df = new DecimalFormat("#.##");
	 
	private static final Logger logger = LogManager.getLogger(CHSSServiceImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Autowired
	CHSSDao dao;
	
	public double CropTo2Decimal(String Amount)throws Exception
	{
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		return Double.parseDouble(decimalformat.format(Double.parseDouble(Amount)));
	}
		
	@Override
	public List<Object[]> familyDetailsList(String empid) throws Exception
	{
		return dao.familyDetailsList(empid);
	}
	
	@Override
	public  Object[] getEmployee(String empid) throws Exception
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
	public List<Object[]> getCHSSConsultMainList(String applyid) throws Exception
	{
		return dao.getCHSSConsultMainList(applyid);
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
				apply.setAilment(WordUtils.capitalize(dto.getAilment()).trim());
				apply.setCHSSStatusId(1);
				apply.setIsActive(1);
				apply.setCreatedBy(dto.getCreatedBy());
				apply.setCreatedDate(sdtf.format(new Date()));
				apply.setCHSSApplyDate(sdf.format(new Date()));
				apply.setRemarks(dto.getRemarks());
				apply.setCHSSApplyNo("-");
				apply.setPOAcknowledge(0);
				apply.setPOId(0L);
				apply.setVOId(0L);
				apply.setContingentId(0L);
				apply.setAmountClaimed(0.0);
				apply.setAmountClaimed(0.00);
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
			
			long conmainid =0;
			for(int i=0 ; dto.getDocName()!=null && i<dto.getDocName().length && applyid>0 ; i++)
			{
				CHSSConsultMain conmain = new CHSSConsultMain();
//				conmain.setConsultType(dto.getConsulttype()[i]);
				conmain.setDocQualification(Integer.parseInt(dto.getDocQualification()[i]));
				conmain.setCHSSApplyId(applyid);
				conmain.setConsultDate(sdf.format(rdf.parse(dto.getConsultDate()[i])));
				conmain.setDocName(WordUtils.capitalize(dto.getDocName()[i]).trim());
				conmain.setCreatedBy(dto.getCreatedBy());
				conmain.setCreatedDate(sdtf.format(new Date()));
				conmain.setIsActive(1);
				conmainid = dao.CHSSConsultMainAdd(conmain);
			}
			
			if(dto.getCHSSApplyId()==null) {
				return applyid;
			}
			
			return conmainid;
			
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
		String CNo = start+end+"/"+today.getMonth().toString().toUpperCase().substring(0,3)+"/";
		String applynocount=dao.CHSSApplyNoCount(CNo);
		CNo=CNo+(Long.parseLong(applynocount)+1);
		
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
	public List<Object[]> CHSSConsultMainBillsList(String consultmainid, String chssapplyid) throws Exception
	{
		return dao.CHSSConsultMainBillsList(consultmainid,chssapplyid);
	}
	
	@Override
	public List<Object[]> empCHSSList(String empid,String PatientId, String FromDate, String Todate, String IsSelf) throws Exception
	{
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MM-yyyy");
		LocalDate Fromdate= LocalDate.parse(FromDate,formatter);
		LocalDate ToDate= LocalDate.parse(Todate, formatter);
		
		List<Object[]> empCHSSList =  dao.empCHSSList(empid,PatientId, Fromdate, ToDate, IsSelf);
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
	public long CHSSConsultMainEdit(CHSSConsultMain consultmain) throws Exception
	{
		CHSSConsultMain fetch = dao.getCHSSConsultMain(String.valueOf(consultmain.getCHSSConsultMainId()));
		
		fetch.setDocName(WordUtils.capitalize(consultmain.getDocName()).trim());
		fetch.setConsultDate(consultmain.getConsultDate());
		fetch.setDocQualification(consultmain.getDocQualification());
		fetch.setModifiedBy(consultmain.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		return dao.CHSSConsultMainEdit(fetch);
	}
		
	@Override
	public CHSSConsultMain getCHSSConsultMain(String ConsultMainId) throws Exception
	{
		return dao.getCHSSConsultMain(ConsultMainId);
	}
	
	@Override
	public long CHSSConsultMainDelete(String consultmainid, String modifiedby) throws Exception
	{
	
		dao.ConsultBillsDelete(consultmainid);
		return dao.CHSSConsultMainDelete(consultmainid);
	}
	
	@Override
	public long CHSSBillEdit(CHSSBill bill) throws Exception
	{
		CHSSBill fetch = dao.getCHSSBill(String.valueOf(bill.getBillId()));
		
		fetch.setCenterName(WordUtils.capitalize(bill.getCenterName()).trim());
		fetch.setBillNo(bill.getBillNo().trim().toUpperCase());
		fetch.setBillDate(bill.getBillDate());
		fetch.setGSTAmount(bill.getGSTAmount());
		fetch.setDiscount(bill.getDiscount());
		fetch.setDiscountPercent(bill.getDiscountPercent());
		fetch.setFinalBillAmt(bill.getFinalBillAmt());
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
		deleteBillItems(billid, modifiedby);
		return dao.CHSSBillEdit(fetch);
	}
	
	public int deleteBillItems(String billid,String username) throws Exception 
	{
		int count=0;
		try {
			
			count += dao.billConsultDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billTestsDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billMedsDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billOthersDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billMiscDeleteAll(billid,username,sdtf.format(new Date()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	
	@Override
	public long CHSSApplyEdit(CHSSApplyDto dto) throws Exception
	{
		CHSSApply fetch = dao.getCHSSApply(dto.getCHSSApplyId());
		if(dto.getTreatTypeId()!=null) {
			fetch.setTreatTypeId(Integer.parseInt(dto.getTreatTypeId()));
		}
		fetch.setAilment(WordUtils.capitalize(dto.getAilment()).trim());
		fetch.setCHSSType(dto.getCHSSType());
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
	public List<CHSSTestSub> CHSSTestSubList(String treattypeid) throws Exception
	{
		List<CHSSTestSub> list= dao.CHSSTestSubList();
		if(Integer.parseInt(treattypeid)==2) {
			list.addAll (0,dao.CHSSTestSubListWithAyur());
		}
		return list;
		
	}
	
	@Override
	public List<CHSSTestMain> CHSSTestMainList() throws Exception
	{
		return dao.CHSSTestMainList();
	}
	
	@Override
	public List<CHSSBillConsultation> CHSSConsultationList(String billid) throws Exception
	{
		return dao.CHSSConsultationList(billid);
	}
	
//	@Override
	public int UpdateBillAdmissibleTotal(String billid) throws Exception
	{
		Object[] bill = dao.CHSSBill(billid);
		return dao.UpdateBillAdmissibleTotal(bill[0].toString(), bill[11].toString());
	}
	
	
	@Override
	public long ConsultationBillAdd(CHSSConsultationDto dto,String chssapplyid, String consultmainidold) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ConsultationBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getDocName().length ; i++)
			{
				CHSSBillConsultation consult = new CHSSBillConsultation();
				
				consult.setBillId(Long.parseLong(dto.getBillId()));
//				consult.setConsultType(dto.getConsultType()[i]);
				consult.setDocName(WordUtils.capitalize(dto.getDocName()[i]).trim());
				consult.setDocQualification(Integer.parseInt(dto.getDocQualification()[i]));
				consult.setConsultDate(sdf.format(rdf.parse(dto.getConsultDate()[i])));
				consult.setConsultCharge(Double.parseDouble(dto.getConsultCharge()[i]));
				
				consult.setIsActive(1);
				consult.setCreatedBy(dto.getCreatedBy());
				consult.setCreatedDate(sdtf.format(new Date()));		
				
				
				getConsultEligibleAmount(consult,chssapplyid,consultmainidold,dto.getBillId());
				consult.setComments(df.format(consult.getConsultRemAmount())+" is admitted.");
				count = dao.ConsultationBillAdd(consult);
					
			}
			UpdateBillAdmissibleTotal(dto.getBillId());
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE ConsultationBillAdd");
			return 0;
		}
		
	}
	
	public double getConsultEligibleAmount(CHSSBillConsultation consult,String chssapplyid, String consultmainidold,String billid) throws Exception 
	{		
		CHSSBill bill = dao.getCHSSBill(billid);
		String isfresh="";
		double applyamount= consult.getConsultCharge()-(consult.getConsultCharge()*(bill.getDiscountPercent()/100));
		consult.setAmountPaid(applyamount);
		int speciality=consult.getDocQualification();
		System.out.println(consult.getDocQualification());
		String consultdate=consult.getConsultDate();
		CHSSDoctorRates rate  = dao.getDocterRate(String.valueOf(speciality));
		int allowedamt=0;
		
		CHSSConsultMain OldConsultMain = dao.getCHSSConsultMain(consultmainidold);
		
		LocalDate olddate= LocalDate.parse(OldConsultMain.getConsultDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate newdate= LocalDate.parse(consultdate.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		
		if(olddate.equals(newdate)) {
			isfresh="Fresh" ;
		}
		else if(newdate.isAfter(olddate.plusDays(7)) && newdate.isBefore(olddate.plusDays(14))  ) {
			isfresh="FollowUp" ;
		}
		else if( newdate.isAfter(olddate.plusDays(14))  ) {
			isfresh="Fresh" ;
		}
		
		
		if(isfresh.equalsIgnoreCase("Fresh")) 
		{
			allowedamt = rate.getConsultation_1();
			consult.setConsultType(isfresh);
		}
		else if(isfresh.equalsIgnoreCase("FollowUp")) 
		{
			allowedamt = rate.getConsultation_2();
			consult.setConsultType(isfresh);
			consult.setComments("Followup Consultation with in 2 weeks ");
		}
		else if(isfresh.equalsIgnoreCase("")) 
		{
			allowedamt = 0;
			consult.setConsultType("FollowUp");
			consult.setComments("Followup Consultation with in a week ");
		}
		
		
		if (allowedamt <= applyamount) {
			consult.setConsultRemAmount(allowedamt);
			return allowedamt;
		} else {
			consult.setConsultRemAmount(applyamount);
			return applyamount;
		}
		

	}
	
	
	
	@Override
	public long ConsultationBillEdit(CHSSBillConsultation modal,String chssapplyid, String consultmainidold) throws Exception
	{
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultType(modal.getConsultType());
		fetch.setDocName(WordUtils.capitalize(modal.getDocName()).trim());
//		fetch.setDocQualification(modal.getDocQualification());
		System.out.println(fetch.getDocQualification());
		fetch.setConsultDate(modal.getConsultDate());
		fetch.setConsultCharge(modal.getConsultCharge());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setConsultRemAmount(getConsultEligibleAmount(fetch,chssapplyid,consultmainidold,String.valueOf(fetch.getBillId())));      
		fetch.setComments(df.format(fetch.getConsultRemAmount())+" is admitted.");
		
		long count= dao.ConsultationBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
	@Override
	public long ConsultationBillDelete(String consultationid, String modifiedby ) throws Exception
	{
		CHSSBillConsultation fetch = dao.getCHSSConsultation(consultationid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count= dao.ConsultationBillEdit(fetch);
		
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
	
	
	@Override
	public long MedicinesBillAdd(CHSSMedicineDto dto, String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MedicinesBillAdd");		
		try {
			
			long count=0;
			Object[] chssapplydata = dao.CHSSAppliedData(chssapplyid);
			String treattypeid= chssapplydata[7].toString();
			for(int i=0 ; i<dto.getMedicineName().length ; i++)
			{
				if(dto.getMedicineName()[i]!=null && dto.getMedicineCost()[i]!=null && !dto.getMedicineCost()[i].trim().equals("") && Integer.parseInt(dto.getMedQuantity()[i])>0 ) 
				{
					CHSSBillMedicine  meds = new CHSSBillMedicine();
					
					meds.setBillId(Long.parseLong(dto.getBillId()));
					meds.setMedicineName(WordUtils.capitalize(dto.getMedicineName()[i]).trim());
					meds.setPresQuantity(Integer.parseInt(dto.getPresQuantity()[i]));
					meds.setMedQuantity(Integer.parseInt(dto.getMedQuantity()[i]));
					meds.setMedicineCost(Double.parseDouble(dto.getMedicineCost()[i]));
					meds.setMedsRemAmount(Double.parseDouble(dto.getMedicineCost()[i]));
					calculateMedAmount(meds,dto.getBillId());
					
					meds.setIsActive(1);
					meds.setCreatedBy(dto.getCreatedBy());
					meds.setCreatedDate(sdtf.format(new Date()));
					
					if(treattypeid.toString().equalsIgnoreCase("1")) {
						checkMedAdmissibility(meds);
					}					
					if(meds.getMedsRemAmount()>0) {
						meds.setComments(df.format(meds.getMedsRemAmount())+" is admitted.");
					}					
					count += dao.MedicinesBillAdd(meds);
				}
			}
			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE MedicinesBillAdd");
			return 0;
		}
	}
	
	public void checkMedAdmissibility(CHSSBillMedicine  meds ) throws Exception
	{
		List<Object[]> medicinedata = dao.MedAdmissibleCheck(WordUtils.capitalize(meds.getMedicineName()).trim());
		if(medicinedata!=null && medicinedata.size()>0)
		{
			meds.setMedsRemAmount(0);
			meds.setComments("Found in inadmissible List : MedicineNo: "+medicinedata.get(0)[1]);
		}
	}
	
	public void calculateMedAmount(CHSSBillMedicine  meds, String billid ) throws Exception
	{
		CHSSBill bill = dao.getCHSSBill(billid);
		int purs = meds.getMedQuantity();
		int pres = meds.getPresQuantity();
		double cost = meds.getMedicineCost()-(meds.getMedicineCost()*(bill.getDiscountPercent()/100));
		meds.setMedsRemAmount(cost);
		meds.setAmountPaid(cost);
		if(purs>pres) 
		{
			cost=cost/(double)purs;
			meds.setMedsRemAmount(Math.round((cost*pres) * 100.0) / 100.0); 
		}		
		
	}
	
	
	@Override
	public long MedicineBillEdit(CHSSBillMedicine modal, String chssapplyid) throws Exception
	{
		Object[] chssapplydata = dao.CHSSAppliedData(chssapplyid);
		String treattypeid= chssapplydata[7].toString();
		CHSSBillMedicine fetch = dao.getCHSSMedicine(String.valueOf(modal.getCHSSMedicineId()));
		if(treattypeid.toString().equalsIgnoreCase("1")) 
		{
			fetch.setMedicineName(WordUtils.capitalize(modal.getMedicineName()).trim());
		}
		else
		{
			fetch.setMedicineName(modal.getMedicineName());
		}
		fetch.setMedicineCost(modal.getMedicineCost());
		fetch.setMedQuantity(modal.getMedQuantity());
		fetch.setPresQuantity(modal.getPresQuantity());
		fetch.setMedsRemAmount(modal.getMedicineCost());
		
		calculateMedAmount(fetch,String.valueOf(modal.getBillId()));
		
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(treattypeid.toString().equalsIgnoreCase("1")) {
			checkMedAdmissibility(fetch);
		}
		if(fetch.getMedsRemAmount()>0) {
			fetch.setComments(df.format(fetch.getMedsRemAmount())+" is admitted.");
		}
		
		long count =dao.MedicineBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
	}
	
	
	
	
	@Override
	public List<CHSSBillMedicine> CHSSMedicineList(String billid) throws Exception
	{
		return dao.CHSSMedicineList(billid);
	}
	
	
	
	@Override
	public long MedicineBillDelete(String medicineid, String modifiedby ) throws Exception
	{
		CHSSBillMedicine fetch = dao.getCHSSMedicine(medicineid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		long count =dao.MedicineBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
	
	@Override
	public long TestsBillAdd(CHSSTestsDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE TestsBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getTestSubId().length; i++)
			{
				if(dto.getTestSubId()[i]!=null && dto.getTestCost()[i]!=null && !dto.getTestCost()[i].trim().equals("")) 
				{
					CHSSBillTests  test = new CHSSBillTests();
					
					test.setBillId(Long.parseLong(dto.getBillId()));
					test.setTestMainId(Long.parseLong(dto.getTestSubId()[i].split("_")[0]));
					test.setTestSubId(Long.parseLong(dto.getTestSubId()[i].split("_")[1]));
					test.setTestCost(Double.parseDouble(dto.getTestCost()[i]));
					test.setIsActive(1);
					getTestEligibleAmount(test);
					test.setComments(df.format(test.getTestRemAmount())+" is admitted.");
					test.setCreatedBy(dto.getCreatedBy());
					test.setCreatedDate(sdtf.format(new Date()));
					
					
					count = dao.TestsBillAdd(test);
				}
			}
					
			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE TestsBillAdd");
			return 0;
		}
		
	}
	
	
	public void getTestEligibleAmount(CHSSBillTests testsub) throws Exception 
	{
		CHSSBill bill = dao.getCHSSBill(String.valueOf(testsub.getBillId()));
		double applyamount = testsub.getTestCost()-(testsub.getTestCost() * (bill.getDiscountPercent()/100));
		testsub.setAmountPaid(applyamount);
		int testsubamount = dao.getCHSSTestSub(String.valueOf(testsub.getTestSubId())).getTestRate();
		
		if(testsubamount<= applyamount)
		{
			testsub.setTestRemAmount(testsubamount);
		}
		else
		{
			testsub.setTestRemAmount(applyamount);
		}
	}
	
	
	
	@Override
	public List<CHSSBillTests> CHSSTestsList(String billid) throws Exception
	{
		return dao.CHSSTestsList(billid);
	}
	
	
	@Override
	public long TestBillEdit(CHSSBillTests modal) throws Exception
	{
		CHSSBillTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestMainId(modal.getTestMainId());
		fetch.setTestSubId(modal.getTestSubId());
		fetch.setTestCost(modal.getTestCost());
		getTestEligibleAmount(fetch);
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setComments(df.format(fetch.getTestRemAmount())+" is admitted.");
		
		
		long count =dao.TestBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
		
	}
	
	@Override
	public long TestBillDelete(String chsstestid, String modifiedby ) throws Exception
	{
		CHSSBillTests fetch = dao.getCHSSTest(chsstestid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.TestBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
	
	@Override
	public long MiscBillAdd(CHSSMiscDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MiscBillAdd");		
		try {
			long count=0;
			for(int i=0 ; i<dto.getMiscItemName().length ; i++)
			{
				CHSSBillMisc  misc = new CHSSBillMisc();
				CHSSBill bill = dao.getCHSSBill(dto.getBillId());
				misc.setBillId(Long.parseLong(dto.getBillId()));
				misc.setMiscItemName(WordUtils.capitalize(dto.getMiscItemName()[i]).trim());
				misc.setMiscItemCost(Double.parseDouble(dto.getMiscItemCost()[i]));
				misc.setAmountPaid(misc.getMiscItemCost()-(misc.getMiscItemCost() *(bill.getDiscountPercent()/100)));
				misc.setMiscCount(Integer.parseInt(dto.getMiscCount()[i]));
				misc.setMiscRemAmount(0);
				misc.setIsActive(1);
				misc.setCreatedBy(dto.getCreatedBy());
				misc.setCreatedDate(sdtf.format(new Date()));
				count = dao.MiscBillAdd(misc);
			}
			
			
			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE MiscBillAdd");
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSBillMisc> CHSSMiscList(String billid) throws Exception
	{
		return dao.CHSSMiscList(billid);
	}
	
	@Override
	public long MiscBillEdit(CHSSBillMisc modal) throws Exception
	{
		CHSSBillMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		CHSSBill bill = dao.getCHSSBill(String.valueOf(fetch.getBillId()));
		fetch.setMiscItemName(WordUtils.capitalize(modal.getMiscItemName()).trim());
		fetch.setMiscItemCost(modal.getMiscItemCost());
		fetch.setAmountPaid(modal.getMiscItemCost()-(modal.getMiscItemCost() *(bill.getDiscountPercent()/100)));
		fetch.setMiscCount(modal.getMiscCount());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.MiscBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
	}
	
	@Override
	public long MiscBillDelete(String chssMiscid, String modifiedby ) throws Exception
	{
		CHSSBillMisc fetch = dao.getCHSSMisc(chssMiscid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.MiscBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
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
	public List<CHSSBillOther> CHSSOtherList(String billid) throws Exception
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
				CHSSBillOther  other = new CHSSBillOther();
				
				other.setBillId(Long.parseLong(dto.getBillId()));
				other.setOtherItemId(Integer.parseInt(dto.getOtherItemId()[i]));
				other.setOtherItemCost(Double.parseDouble(dto.getOtherItemCost()[i]));
				other.setIsActive(1);
				
				getOtherItemRemAmount(dto.getEmpid(),other);
				other.setCreatedBy(dto.getCreatedBy());
				other.setCreatedDate(sdtf.format(new Date()));
				other.setComments(df.format(other.getOtherRemAmount())+" is admitted.");
				count = dao.OtherBillAdd(other);
			}
					
			
			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE OtherBillAdd");
			return 0;
		}
		
	}
	
	
	public void getOtherItemRemAmount(String empid,CHSSBillOther other) throws Exception
	{
		CHSSBill bill = dao.getCHSSBill(String.valueOf(other.getBillId()));
		double itemcost = other.getOtherItemCost()-(other.getOtherItemCost() * (bill.getDiscountPercent()/100));
		other.setAmountPaid(itemcost);
		Object[] emp =dao.getEmployee(empid);
//		CHSSOtherItems remlist = dao.getCHSSOtherItems(otheritemid);
		long basicpay=0;
		if(emp[4]!=null) {
			 basicpay=Long.parseLong(emp[4].toString());
		}
		
		CHSSOtherPermitAmt chssremamt=dao.getCHSSOtherPermitAmt(String.valueOf(other.getOtherItemId()),basicpay);
		
		int rembamt=0;
		if(chssremamt!=null && chssremamt.getItemPermitAmt()!=null) {
			rembamt=chssremamt.getItemPermitAmt();
		}
		
		if(itemcost<= rembamt)
		{
			other.setOtherRemAmount(itemcost);
		}
		else
		{
			other.setOtherRemAmount(rembamt);
		}
			
	}
	
	
	@Override
	public long OtherBillEdit(CHSSBillOther modal,String empid) throws Exception
	{
		CHSSBillOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherItemId(modal.getOtherItemId());
		fetch.setOtherItemCost(modal.getOtherItemCost());
		getOtherItemRemAmount(String.valueOf(empid),fetch);
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setComments(df.format(fetch.getOtherRemAmount())+" is admitted.");
		
		long count =dao.OtherBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
		
	}
	
	@Override
	public long OtherBillDelete(String chssotherid, String modifiedby ) throws Exception
	{
		CHSSBillOther fetch = dao.getCHSSOther(chssotherid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.OtherBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
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
	public long CHSSUserForward(String CHSSApplyId,String Username, String action,String remarks, String EmpId,String LoginType) throws Exception 
	{
		CHSSApply claim = dao.getCHSSApply(CHSSApplyId);
		int claimstatus = claim.getCHSSStatusId();
		EMSNotification notify = new EMSNotification();
		String mailbody = "";
		String Email="";
		if(action.equalsIgnoreCase("F")) 
		{
			notify.setNotificationUrl("CHSSApprovalsList.htm");
			notify.setNotificationMessage("Medical Claim Application Received");
			
			if(claimstatus==1 || claimstatus==3 ) 
			{
				dao.POAcknowldgedUpdate(CHSSApplyId,"0");
				
				claim.setCHSSStatusId(2);
				if(claimstatus==1)
				{
					claim.setCHSSApplyDate(LocalDate.now().toString());
					claim.setCHSSForwardDate(LocalDate.now().toString());
				}else if(claimstatus==1)
				{
					claim.setCHSSForwardDate(LocalDate.now().toString());
				}
				claim.setPOAcknowledge(0);
				Object[] notifyto = dao.CHSSApprovalAuth("K");
				if(notifyto==null) {
					notify.setEmpId(0L);
				}else {
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					if(notifyto[5]!=null) { 	Email = notifyto[5].toString();		}
				}
				
				if(claimstatus==1 && claim.getCHSSApplyNo().trim().equals("-")) {
					
					claim.setCHSSApplyNo(GenerateCHSSClaimNo());
				}
			}
			else if(claimstatus==2 || claimstatus==5 ) 
			{
				claim.setCHSSStatusId(4);	
				
				Object[] notifyto = dao.CHSSApprovalAuth("V");
				if(notifyto==null) {
					notify.setEmpId(0L);
				}else {
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					if(notifyto[5]!=null) { 	Email = notifyto[5].toString();		}
				}
				claim.setPOId(Long.parseLong(EmpId));
				
			}
			else if(claimstatus==4) 
			{
				claim.setCHSSStatusId(6);
				claim.setVOId(Long.parseLong(EmpId));
			}
			
			
			mailbody = "Medical Claim Application ("+claim.getCHSSApplyNo()+") Received for Verification";
						
		}
		
		if(action.equalsIgnoreCase("R")) 
		{
			notify.setNotificationMessage("Medical Claim Application Returned");
			mailbody = "Medical Claim Application ("+claim.getCHSSApplyNo()+") is Returned";

			if(claimstatus==2 || claimstatus==5 || claimstatus==6 || claimstatus==9 || claimstatus==11 || claimstatus==13) 
			{
				claim.setCHSSStatusId(3);
			
				notify.setEmpId(claim.getEmpId());
				 Object[] emp= dao.getEmployee(claim.getEmpId().toString());				
				if( emp[7]!=null) { 	Email =  emp[7].toString();		}
				notify.setNotificationUrl("CHSSDashboard.htm");
				claim.setPOId(0L);
				claim.setVOId(0L);
				
			}
			else if(claimstatus==4) 
			{
				claim.setCHSSStatusId(5);
				Object[] notifyto = dao.CHSSApprovalAuth("K");
				if(notifyto==null) {
					notify.setEmpId(0L);
				}else {
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					if(notifyto[5]!=null) { 	Email = notifyto[5].toString();		}
				}
				notify.setNotificationUrl("CHSSApprovalsList.htm");
			}
			claim.setVOId(0L);
			claim.setContingentId(0L);
		}
		
		claim.setRemarks(remarks);
		claim.setModifiedBy(Username);
		claim.setModifiedDate(sdtf.format(new Date()));
		
		
		
		CHSSApplyTransaction transac =new CHSSApplyTransaction();
		transac.setCHSSApplyId(claim.getCHSSApplyId());
		transac.setCHSSStatusId(claim.getCHSSStatusId());
		transac.setRemark(remarks);
		transac.setActionBy(Long.parseLong(EmpId));
		transac.setActionDate(sdtf.format(new Date()));
		dao.CHSSApplyTransactionAdd(transac);
		
		if(claim.getCHSSStatusId()!=6 && notify.getEmpId()>0)
		{
		
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy(Long.parseLong(EmpId));
			notify.setIsActive(1);
			notify.setCreatedBy(Username);
			notify.setCreatedDate(sdtf.format(new Date()));
			dao.NotificationAdd(notify);
		}
		
		long count= dao.CHSSApplyEdit(claim);
		
		
//		if(Email!=null && !Email.equalsIgnoreCase("") && !mailbody.equalsIgnoreCase(""))
//		{
//			
//			MimeMessage msg = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//			
//			helper.setTo(Email);	
//			helper.setSubject( "Medical Claim Application");
//			helper.setText( mailbody , true);
//			try {
//				javaMailSender.send(msg); 
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		
		return count;
	}
		
	@Override
	public List<Object[]> CHSSApproveClaimList(String logintype,String empid ) throws Exception 
	{
		return dao.CHSSApproveClaimList(logintype,empid);
	}
	
	@Override
	public long ConsultRemAmountEdit(CHSSBillConsultation modal) throws Exception
	{
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultRemAmount(modal.getConsultRemAmount());
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.ConsultationBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long TestRemAmountEdit(CHSSBillTests modal) throws Exception
	{
		CHSSBillTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestRemAmount(modal.getTestRemAmount());
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.TestBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long OtherRemAmountEdit(CHSSBillOther modal) throws Exception
	{
		CHSSBillOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherRemAmount(modal.getOtherRemAmount());
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.OtherBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long MedRemAmountEdit(CHSSBillMedicine modal) throws Exception
	{
		CHSSBillMedicine fetch = dao.getCHSSMedicine(String.valueOf(modal.getCHSSMedicineId()));
		fetch.setMedsRemAmount(modal.getMedsRemAmount());
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.MedicineBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long MiscRemAmountEdit(CHSSBillMisc modal) throws Exception
	{
		CHSSBillMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		fetch.setMiscRemAmount(modal.getMiscRemAmount());
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.MiscBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
	}
	
	@Override
	public List<Object[]> CHSSClaimListRep(String type, String fromdate, String todate) throws Exception 
	{
		return dao.CHSSClaimListRep(type, fromdate, todate);
	}
	
	@Override
	public List<Object[]> CHSSBatchApproval(String logintype, String todate,String contingentid) throws Exception
	{
		return dao.CHSSBatchApproval(logintype,  todate,contingentid);
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
		continnew.setPO(0L);
		continnew.setVO(0L);
		continnew.setAO(0L);
		continnew.setCEO(0L);
		contingentid = dao.ContingentAdd(continnew);
		
		long count=0;
		for(String claimid  : CHSSApplyId)
		{
			CHSSApply claim = dao.getCHSSApply(claimid);
			
//			claim.setCHSSStatusId(8);
			claim.setContingentId(contingentid);
			claim.setModifiedBy(Username);
			claim.setModifiedDate(sdtf.format(new Date()));
			count= dao.CHSSApplyEdit(claim);
		}
			
		CHSSContingentTransaction transac =new CHSSContingentTransaction();
		transac.setContingentId(contingentid);
		transac.setStatusId(1);
		transac.setRemarks("");
		transac.setActionBy(Long.parseLong(EmpId));
		transac.setActionDate(sdtf.format(new Date()));
		dao.CHSSContingentTransactionAdd(transac);
		
		return contingentid;
	}
	
	
	
	
	public String GenerateContingentNo() throws Exception
	{
		try {
			String value="STARC/F&A/Med-Regular/";
						
			LocalDate today= LocalDate.now();
			String start ="";
			String end="";
			int currentmonth= today.getMonthValue();
			if(currentmonth<4) 
			{
				start = String.valueOf(today.getYear()-1);
				end =String.valueOf(today.getYear()).substring(2);
			}
			else
			{
				start=String.valueOf(today.getYear());
				end =String.valueOf(today.getYear()+1).substring(2);
			}		
			value = value+start+"-"+end+"/"+today.getMonth().toString().toUpperCase()+"-"+String.valueOf(today.getYear()).substring(2);;
			
			int count=dao.getdata(value);
			
			if(count==0) {
				return value;
			}else 
			{
				return  value+"/VOL-"+(count+1);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public long CHSSClaimsApprove(CHSSContingentDto dto) throws Exception 
	{
		long continid=0;		
		CHSSContingent contingent = dao.getCHSSContingent(dto.getContingentid());
		int continstatus = contingent.getContingentStatusId();
		ArrayList<String> Emaillist = new ArrayList<String>();
		String mailbody="";
		EMSNotification notify = new EMSNotification();
		
		notify.setNotificationUrl("ContingentApprovals.htm");
		notify.setNotificationDate(LocalDate.now().toString());
		notify.setNotificationBy(Long.parseLong(dto.getEmpId()));
		notify.setIsActive(1);
		notify.setCreatedBy(dto.getUsername());
		notify.setCreatedDate(sdtf.format(new Date()));
		
//		Object[] notifyto = dao.CHSSApprovalAuth("V");
				
		if(dto.getAction().equalsIgnoreCase("F")) 
		{
			
			notify.setNotificationMessage("Medical Claim Contingent Bill Received");
			if(continstatus==1  || continstatus==9 || continstatus==11 || continstatus==13 ) 
			{
				continstatus=8;
				contingent.setContingentDate(LocalDate.now().toString());
				contingent.setBillContent(dto.getBillcontent());
								
				Object[] notifyto = dao.CHSSApprovalAuth("V");
				if(notifyto==null) 
				{
					notify.setEmpId(0L);
				}else 
				{
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					
					if(notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) { 	Emaillist.add(notifyto[5].toString());		}
				}
				contingent.setPO(Long.parseLong(dto.getEmpId()));
				
			}
			else if(continstatus==8  ) 
			{
				continstatus=10;
				
				Object[] notifyto = dao.CHSSApprovalAuth("W");
				if(notifyto==null) 
				{
					notify.setEmpId(0L);
				}else 
				{
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					
					if(notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) { 	Emaillist.add(notifyto[5].toString());		}
				}
				contingent.setVO(Long.parseLong(dto.getEmpId()));
				
			}
			else if(continstatus==10  ) 
			{
				continstatus=12;
				
				Object[] notifyto = dao.CHSSApprovalAuth("Z");
				if(notifyto==null) 
				{
					notify.setEmpId(0L);
				}else 
				{
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					
					if(notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) { 	Emaillist.add(notifyto[5].toString());		}
				}
				contingent.setAO(Long.parseLong(dto.getEmpId()));
				
			}	
			else if(continstatus==12 ) 
			{
				continstatus=14;
				
				notify.setNotificationMessage("Medical Claim Contingent Bill Approved");
				notify.setNotificationUrl("ApprovedBills.htm");
				Object[] notifyto = dao.CHSSApprovalAuth("K");
				if(notifyto==null) 
				{
					notify.setEmpId(0L);
				}else 
				{
					notify.setEmpId(Long.parseLong(notifyto[0].toString()));
					
					if(notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) { 	Emaillist.add(notifyto[5].toString());		}
				}
				contingent.setApprovalDate(LocalDate.now().toString());
				contingent.setCEO(Long.parseLong(dto.getEmpId()));
				
			}	
		}
		else if(dto.getAction().equalsIgnoreCase("R")) 
		{
			notify.setNotificationMessage("Medical Claims Contingent Bill Returned");
			if(continstatus==8 || continstatus==11 ) 
			{
				continstatus=9;
				
			}
			else if(continstatus==10 || continstatus==13 ) 
			{
				continstatus=11;
				Object[] notifyto = dao.CHSSApprovalAuth("V");
				if(notifyto!=null && notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) 
				{
						Emaillist.add(notifyto[5].toString());		
				}
				
			}	
			else if(continstatus==12 ) 
			{
				continstatus=13;
				notify.setNotificationUrl("ContingentApprovals.htm");
												
				Object[] notifyto = dao.CHSSApprovalAuth("V");
				if(notifyto!=null && notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) 
				{
						Emaillist.add(notifyto[5].toString());		
				}
				
				notifyto = dao.CHSSApprovalAuth("W");
				if(notifyto!=null && notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) 
				{
					Emaillist.add(notifyto[5].toString());		
				}
				
			}	
			
			Object[] notifyto = dao.CHSSApprovalAuth("K");
			if(notifyto==null) 
			{
				notify.setEmpId(0L);
			}else 
			{
				notify.setEmpId(Long.parseLong(notifyto[0].toString()));
				if(notifyto[5]!=null &&  !notifyto[5].toString().equalsIgnoreCase("")) { 	Emaillist.add(notifyto[5].toString());		}
			}
			
			contingent.setPO(0L);
			contingent.setVO(0L);
			contingent.setAO(0L);
			contingent.setCEO(0L);
			
		}
					
		contingent.setRemarks(dto.getRemarks());
		contingent.setContingentStatusId(continstatus);
		contingent.setModifiedBy(dto.getUsername());
		contingent.setModifiedDate(sdtf.format(new Date()));
		continid=dao.CHSSContingentEdit(contingent);
//		List<Object> CHSSApplyId  =dao.ContingentApplyIds(dto.getContingentid());
		
		List<Object[]> claimslist = dao.CHSSContingentClaimList(dto.getContingentid());
		
		int i=0;
		for(Object[] continclaim  : claimslist)
		{
			CHSSApply claim = dao.getCHSSApply(continclaim[0].toString());
			
			// update claimed and settled amount in each claim of this bill
			if(continstatus==14) 
			{
				claim.setAmountClaimed(Double.parseDouble(continclaim[27].toString()) );
				claim.setAmountSettled(Double.parseDouble(continclaim[28].toString()) );
			}
			
			claim.setCHSSStatusId(continstatus);
			claim.setModifiedBy(dto.getUsername());
			claim.setModifiedDate(sdtf.format(new Date()));

			CHSSApplyTransaction transac =new CHSSApplyTransaction();
			transac.setCHSSApplyId(claim.getCHSSApplyId());
			transac.setCHSSStatusId(claim.getCHSSStatusId());
			transac.setRemark("");
			transac.setActionBy(Long.parseLong(dto.getEmpId()));
			transac.setActionDate(sdtf.format(new Date()));
			dao.CHSSApplyTransactionAdd(transac);
			
			continid= dao.CHSSApplyEdit(claim);
			i++;
		}
		
		
		
		CHSSContingentTransaction transac =new CHSSContingentTransaction();
		transac.setContingentId(Long.parseLong(dto.getContingentid()));
		transac.setStatusId(contingent.getContingentStatusId());
		transac.setRemarks(dto.getRemarks());
		transac.setActionBy(Long.parseLong(dto.getEmpId()));
		transac.setActionDate(sdtf.format(new Date()));
		dao.CHSSContingentTransactionAdd(transac);
		
		
		
		if(notify.getEmpId()>0) {
			dao.NotificationAdd(notify);
		}
		
		String[] Email = new String[Emaillist.size()];
		Email = Emaillist.toArray(Email);
		
		mailbody = notify.getNotificationMessage();
//		if(Email!=null && Email.length>0 && !mailbody.equalsIgnoreCase(""))
//		{
//			
//			MimeMessage msg = javaMailSender.createMimeMessage();
//			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//			
//			helper.setTo(Email);	
//			helper.setSubject( "Medical Claims Contingent Bill");
//			helper.setText( mailbody , true);
//			try {
//				javaMailSender.send(msg); 
//			}catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		
		
		return continid;
		
	}
	
	
	@Override
	public List<Object[]> getCHSSContingentList(String logintype,String fromdate,String todate ) throws Exception
	{
		return dao.getCHSSContingentList( logintype, fromdate, todate );
	}
	
	@Override
	public LinkedHashMap<Long, ArrayList<Object[]>> CHSSContingentClaimList(String contingentid) throws Exception
	{
		List<Object[]> claims = dao.CHSSContingentClaimList(contingentid);
		
		LinkedHashMap<Long, ArrayList<Object[]>> sortedclaims  = new LinkedHashMap<Long, ArrayList<Object[]>>();
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
	public List<CHSSMedicinesList> getCHSSMedicinesList(String treattypeid) throws Exception
	{
		return dao.getCHSSMedicinesList(treattypeid);
	}
	
	@Override
	public List<Object[]> CHSSApprovalAuthList(String contingentid) throws Exception
	{
		return dao.CHSSApprovalAuthList(contingentid);
	}
	@Override
	public List<Object[]> ConsultationHistory(String chssapplyid) throws Exception
	{
		return dao.ConsultationHistory(chssapplyid);
	}
	
	
	@Override
	public List<Object[]> TestsHistory(String chssapplyid) throws Exception
	{
		return dao.TestsHistory(chssapplyid);
	}
	
	@Override
	public List<Object[]> MedicinesHistory(String chssapplyid) throws Exception
	{
		return dao.MedicinesHistory(chssapplyid);
	}
	
	@Override
	public List<Object[]> OthersHistory(String chssapplyid) throws Exception
	{
		return dao.OthersHistory(chssapplyid);
	}
	
	@Override
	public List<Object[]> MiscItemsHistory(String chssapplyid) throws Exception
	{
		return dao.MiscItemsHistory(chssapplyid);
	}
	
	@Override
	public long CHSSConsultBillsAdd(ChssBillsDto dto) throws Exception
	{
		long billid =0;
		for(int i=0 ; i<dto.getBillNo().length ; i++)
		{
			CHSSBill bill = new CHSSBill();
			bill.setCHSSApplyId(Long.parseLong(dto.getCHSSApplyId()));
			bill.setCHSSConsultMainId(Long.parseLong(dto.getCHSSConsultMainId()));
			bill.setBillNo(dto.getBillNo()[i].trim().toUpperCase());
			bill.setCenterName(WordUtils.capitalize(dto.getCenterName()[i]).trim());
			bill.setBillDate(sdf.format(rdf.parse(dto.getBillDate()[i])));
			bill.setAdmissibleTotal(0.00);
			bill.setGSTAmount(0.00);
			bill.setDiscount(CropTo2Decimal(dto.getDiscount()[i]));
			bill.setDiscountPercent(Double.parseDouble(dto.getDiscountPer()[i]));
			bill.setFinalBillAmt(CropTo2Decimal(dto.getFinalbillamount()[i]));
			bill.setIsActive(1);
			bill.setCreatedBy(dto.getCreatedBy());
			bill.setCreatedDate(sdtf.format(new Date()));
			
			billid=dao.CHSSBillAdd(bill);
		}
		return billid;
	}
	
	@Override
	public Object[] ConsultBillsConsultCount(String consultmainid, String chssapplyid,String billid) throws Exception
	{
		return dao.ConsultBillsConsultCount(consultmainid,chssapplyid,billid);
	}
	
	@Override
	public List<Object[]> PatientConsultHistory(String chssapplyid) throws Exception
	{
		return dao.PatientConsultHistory(chssapplyid);
	}
	
	@Override
	public List<Object[]> OldConsultMedsList(String CHSSConsultMainId, String chssapplyid) throws Exception
	{
		return dao.OldConsultMedsList(CHSSConsultMainId,chssapplyid);
	}
	
	@Override
	public List<Object[]> MedAdmissibleCheck(String medicinename) throws Exception
	{
		return dao.MedAdmissibleCheck(medicinename);
	}
	@Override
	public List<Object[]> MedAdmissibleList(String medicinename, String treattype) throws Exception
	{
		return dao.MedAdmissibleList(medicinename,treattype);
		
	}
	@Override
	public int POAcknowldgedUpdate(String chssapplyid, String poacknowledge)throws Exception
	{
		return dao.POAcknowldgedUpdate(chssapplyid,poacknowledge);
	}
	@Override
	public long CHSSUserRevoke(String CHSSApplyId,String Username,String EmpId)throws Exception
	{
		CHSSApply claim = dao.getCHSSApply(CHSSApplyId);
		
		if(claim.getPOAcknowledge()==0) 
		{
		
			claim.setCHSSStatusId(1);
			claim.setModifiedBy(Username);
			claim.setModifiedDate(sdtf.format(new Date()));
			
			
			
			CHSSApplyTransaction transac =new CHSSApplyTransaction();
			transac.setCHSSApplyId(claim.getCHSSApplyId());
			transac.setCHSSStatusId(100);
			transac.setActionBy(Long.parseLong(EmpId));
			transac.setActionDate(sdtf.format(new Date()));
			dao.CHSSApplyTransactionAdd(transac);
			
			long count= dao.CHSSApplyEdit(claim);
			
			return count;
		}else {
			return -1;
		}
	}
	
	@Override
	public List<Object[]> ClaimApprovedPOVOData(String chssapplyid) throws Exception
	{
		return dao.ClaimApprovedPOVOData(chssapplyid);
	}
	
	
	@Override
	public List<Object[]> ClaimRemarksHistory(String chssapplyid) throws Exception
	{
		return dao.ClaimRemarksHistory(chssapplyid);
	}
	
	@Override
	public Object[] getLabCode() throws Exception
	{
		return dao.getLabCode();
	}
	
	@Override
	public List<Object[]> ContingentBillHistory(String contingentid) throws Exception
	{
		return dao.ContingentBillHistory(contingentid);
	}
	
	@Override
	public List<Object[]> ContingentBillRemarkHistory(String contingentid) throws Exception
	{
		return dao.ContingentBillRemarkHistory(contingentid);
	}
	
	@Override
	public long CHSSContingentDelete(String contingentid,String Username) throws Exception
	{
		CHSSContingent contingent = dao.getCHSSContingent(contingentid);
		contingent.setIsActive(0);
		contingent.setModifiedBy(contingentid);
		contingent.setModifiedDate(sdtf.format(new Date()));
		return dao.CHSSContingentEdit(contingent);
	}
	
	@Override
	public List<Object[]> GetClaimsList(String fromdate , String todate ,  String empid,String status)throws Exception
	{
		return dao.GetClaimsList(fromdate , todate , empid,status);
	}
	
	@Override
	public List<Object[]> EmployeesList()throws Exception
	{
		return dao.EmployeesList();
	}
	
	@Override
	public List<Object[]> GetClaimsReport(String fromdate , String todate ,  String empid)throws Exception
	{
		return dao.GetClaimsReport(fromdate, todate, empid);
	}
	
	@Override
	public List<Object[]> ClaimConsultMainList(String CHSSApplyId) throws Exception
	{
		return dao.ClaimConsultMainList(CHSSApplyId);
	}
	
//	@Override
//	public int DeleteClaimData(String chssapplyid) throws Exception
//	{
//		return 1;
//	}
//	@Override
//	public int claimBillDeleteAll(String chssapplyid) throws Exception
//	{
//		return dao.claimBillDeleteAll(chssapplyid);
//	}
//	
//	@Override
//	public int billMiscDeleteAll(String billid) throws Exception
//	{
//		return dao.billMiscDeleteAll(billid);
//	}
//	
//	@Override
//	public int billMedsDeleteAll(String billid) throws Exception
//	{
//		return dao.billMedsDeleteAll(billid);
//	}
//	
//	@Override
//	public int billTestsDeleteAll(String billid) throws Exception
//	{
//		return dao.billTestsDeleteAll(billid);
//	}
//	
//	@Override
//	public int billConsultDeleteAll(String billid) throws Exception
//	{
//		return dao.billConsultDeleteAll(billid);
//	}
//	
//	@Override
//	public int claimConsultMainDeleteAll(String chssapplyid) throws Exception
//	{
//		return dao.claimConsultMainDeleteAll(chssapplyid);
//	}
//	
//	@Override
//	public int billOthersDeleteAll(String billid) throws Exception
//	{
//		return dao.billOthersDeleteAll(billid);
//	}

	@Override
	public int DeleteClaimData(String chssapplyid,String username) throws Exception 
	{
		
		List<Object[]> billslist = dao.CHSSBillsList(chssapplyid);
		for(Object[] obj : billslist)
		{
			CHSSBillDelete(chssapplyid, username);
		}
		dao.claimConsultMainDeleteAll(chssapplyid, username, sdtf.format(new Date()));
		
		return dao.claimDelete(chssapplyid, username, sdtf.format(new Date()));
	}


	@Override
	public CHSSIPDClaimsInfo IpdClaimInfo(String chssapplyid) throws Exception
	{
		return dao.IpdClaimInfo(chssapplyid);
		
	}

	@Override
	public long CHSSIPDBasicInfoAdd(CHSSIPDClaimsInfo model ) throws Exception
	{
		return dao.CHSSIPDBasicInfoAdd(model);
	}

	
	@Override
	public long CHSSIPDBasicInfoEdit(CHSSIPDClaimsInfo model) throws Exception
	{
		CHSSIPDClaimsInfo fetch = dao.getIpcClaimInfo(String.valueOf(model.getIPDClaimInfoId()));
		
		fetch.setHospitalName(model.getHospitalName());
		fetch.setRoomType(model.getRoomType());
		fetch.setAdmissionDate(DateTimeFormatUtil.RegularToSqlDate(model.getAdmissionDate()) );
		fetch.setAdmissionTime(model.getAdmissionTime());
		fetch.setDischargeDate(DateTimeFormatUtil.RegularToSqlDate(model.getDischargeDate()));
		fetch.setDischargeTime(model.getDischargeTime());
		fetch.setDomiciliaryHosp(model.getDomiciliaryHosp());
		fetch.setDayCare(model.getDayCare());
		fetch.setExtCareRehab(model.getExtCareRehab());
		fetch.setModifiedBy(model.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		return dao.CHSSIPDBasicInfoEdit(fetch);
	}
	
	
	@Override
	public int GetMaxMedNo(String treatmenttype)throws Exception
	{
		return dao.GetMaxMedNo(treatmenttype);
	}
	
	@Override
	public Long AddMedicine(CHSSMedicinesList medicine)throws Exception
	{
		return dao.AddMedicine(medicine);
	}

	 public static void saveFile(String CircularFilePath, String fileName, MultipartFile multipartFile) throws IOException 
	   {
	        Path uploadPath = Paths.get(CircularFilePath);
	          
	        if (!Files.exists(uploadPath)) {
	            Files.createDirectories(uploadPath);
	        }
	        
	        try (InputStream inputStream = multipartFile.getInputStream()) {
	            Path filePath = uploadPath.resolve(fileName);
	            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
	        } catch (IOException ioe) {       
	            throw new IOException("Could not save image file: " + fileName, ioe);
	        }     
	    }
	
	@Override
	public Long AddMasterEditComments(MasterEdit masteredit , MasterEditDto masterdto )throws Exception
	{
		Timestamp instant= Timestamp.from(Instant.now());
		String timestampstr = instant.toString().replace(" ","").replace(":", "").replace("-", "").replace(".","");
		
		   if(masterdto.getFilePath()!=null && !masterdto.getFilePath().isEmpty()) {
				String name =masterdto.getFilePath().getOriginalFilename();
				String filename= "MasterEditFile-"+timestampstr +"."+FilenameUtils.getExtension(masterdto.getFilePath().getOriginalFilename());
				String filepath=emsfilespath+"MastersEditFilePath";
						
				masteredit.setFilePath(filepath+File.separator+filename);
				masteredit.setOriginalName(name);
			    saveFile(filepath , filename, masterdto.getFilePath());
				
			}	
		return dao.AddMasterEditComments(masteredit);
	}
	
	
	@Override
	public List<Object[]> IPDBillPackageItems(String billid)throws Exception
	{
		return dao.IPDBillPackageItems(billid);
	}
	
	@Override
	public List<Object[]> IPDBillNonPackageItems(String billid)throws Exception
	{
		return dao.IPDBillNonPackageItems(billid);
	}
	
	
	@Override
	public long IPDBillHeadDataAddEdit(CHSSBillIPDheads billhead)throws Exception
	{
		CHSSBillIPDheads fetch = dao.getCHSSBillIPDheads(0l,billhead.getBillId(),billhead.getIPDBillHeadId());
		if(fetch==null) 
		{
			billhead.setAmountPaid(billhead.getBillHeadCost());
			billhead.setBillHeadRemAmt(billhead.getBillHeadCost());
			billhead.setComments("");
			billhead.setIsActive(1);
			billhead.setCreatedDate(sdtf.format(new Date()));
			return dao.CHSSBillIPDheadsAdd(billhead);
		}
		else
		{
			fetch.setBillHeadCost(billhead.getBillHeadCost());
			fetch.setAmountPaid(billhead.getBillHeadCost());
			fetch.setBillHeadRemAmt(billhead.getBillHeadCost());
			fetch.setModifiedDate(sdtf.format(new Date()));
			fetch.setModifiedBy(billhead.getCreatedBy());
			return dao.CHSSBillIPDheadsEdit(fetch);
		}
		
	}
	
	@Override
	public long IPDConsultAdd(CHSSConsultationDto dto,String chssapplyid, String consultmainidold) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDConsultAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getDocName().length ; i++)
			{
				CHSSBillConsultation consult = new CHSSBillConsultation();
				
				consult.setBillId(Long.parseLong(dto.getBillId()));
				consult.setConsultType("Fresh");
				consult.setDocName(WordUtils.capitalize(dto.getDocName()[i]).trim());
				consult.setDocQualification(Integer.parseInt(dto.getDocQualification()[i]));
				consult.setConsultDate(sdf.format(rdf.parse(dto.getConsultDate()[i])));
				consult.setConsultCharge(Double.parseDouble(dto.getConsultCharge()[i]));
				consult.setAmountPaid(Double.parseDouble(dto.getConsultCharge()[i]));
				
				consult.setConsultRemAmount(Double.parseDouble(dto.getConsultCharge()[i]));
				
				consult.setIsActive(1);
				consult.setCreatedBy(dto.getCreatedBy());
				consult.setCreatedDate(sdtf.format(new Date()));		
				
				consult.setComments(df.format(consult.getConsultRemAmount())+" is admitted.");
				count = dao.ConsultationBillAdd(consult);
					
			}
			UpdateBillAdmissibleTotal(dto.getBillId());
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE IPDConsultAdd");
			return 0;
		}
		
	}
	
	@Override
	public long IPDConsultEdit(CHSSBillConsultation modal,String chssapplyid, String consultmainidold) throws Exception
	{
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultType(modal.getConsultType());
		fetch.setDocName(WordUtils.capitalize(modal.getDocName()).trim());
		fetch.setDocQualification(modal.getDocQualification());
		fetch.setConsultDate(modal.getConsultDate());
		fetch.setConsultCharge(modal.getConsultCharge());
		fetch.setAmountPaid(modal.getConsultCharge());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setConsultRemAmount(modal.getConsultCharge());      
		fetch.setComments(df.format(fetch.getConsultRemAmount())+" is admitted.");
		
		long count= dao.ConsultationBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
}

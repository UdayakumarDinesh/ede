package com.vts.ems.chss.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.WordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.dto.CHSSApplyDto;
import com.vts.ems.chss.dto.CHSSConsultationDto;
import com.vts.ems.chss.dto.CHSSContingentDto;
import com.vts.ems.chss.dto.CHSSEquipDto;
import com.vts.ems.chss.dto.CHSSIPDPackageDto;
import com.vts.ems.chss.dto.CHSSImplantDto;
import com.vts.ems.chss.dto.CHSSMedicineDto;
import com.vts.ems.chss.dto.CHSSMiscDto;
import com.vts.ems.chss.dto.CHSSOtherDto;
import com.vts.ems.chss.dto.CHSSTestsDto;
import com.vts.ems.chss.dto.ChssBillsDto;
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
import com.vts.ems.master.dto.MasterEditDto;
import com.vts.ems.master.model.CHSSDoctorRates;
import com.vts.ems.master.model.MasterEdit;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class CHSSServiceImpl implements CHSSService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	MathContext MC0 = new MathContext(0, RoundingMode.HALF_UP);
	MathContext MC2 = new MathContext(2, RoundingMode.HALF_UP);
	MathContext MC6 = new MathContext(6, RoundingMode.HALF_UP);
	
	private static final DecimalFormat df = new DecimalFormat("#.##");
	 
	private static final Logger logger = LogManager.getLogger(CHSSServiceImpl.class);
	
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@Autowired
	CHSSDao dao;
	
	public double CropTo2Decimal(String Amount)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CropTo2Decimal ");
		double Invalue = Double.parseDouble(Amount) ;
		return RoundTo2Decimal(Invalue);
	}
	
	public double CropTo6Decimal(String Amount)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CropTo6Decimal ");
		double Invalue = Double.parseDouble(Amount) ;
		BigDecimal returnVal= new BigDecimal(Invalue).setScale(6, BigDecimal.ROUND_HALF_UP);
		return returnVal.doubleValue();
	}

	public double RoundTo2Decimal(double Invalue)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE RoundTo2Decimal ");
		BigDecimal returnVal= new BigDecimal(Invalue).setScale(2, BigDecimal.ROUND_HALF_UP);
		return returnVal.doubleValue();
	}
	
	@Override 
	public Object[] CHSSDashboardCountData(String Empid, String FromDate, String ToDate,String IsSelf) throws Exception
	{
		return dao.CHSSDashboardCountData(Empid, FromDate, ToDate, IsSelf);
	}
	
	
	
	@Override
	public List<Object[]> MonthlyWiseDashboardData(String FromDate, String ToDate) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MonthlyWiseDashboardData ");
		ArrayList<Object[]> Newlist = new ArrayList<Object[]>();
		
		for(int i=1; i<=12; i++ ) {
			
			Object[] EachMonth = dao.MonthlyWiseDashboardData(FromDate, ToDate, i );
			Newlist.add(EachMonth);
	
		}

		return Newlist;
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
		
		logger.info(new Date() +"Inside SERVICE CHSSApplySubmit ");		
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
				apply.setAmountClaimed(new BigDecimal(0.00));
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
//				conmain.setConsultDate(sdf.format(rdf.parse(dto.getConsultDate()[i])));
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
			logger.error(new Date() +" Inside SERVICE CHSSApplySubmit ");
			return 0;
		}
		
	}
	
	
	public String GenerateCHSSClaimNo() throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GenerateCHSSClaimNo ");
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
		logger.info(new Date() +"Inside SERVICE empCHSSList ");
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
		logger.info(new Date() +"Inside SERVICE CHSSConsultMainEdit ");
		CHSSConsultMain fetch = dao.getCHSSConsultMain(String.valueOf(consultmain.getCHSSConsultMainId()));
		
		fetch.setDocName(WordUtils.capitalize(consultmain.getDocName()).trim());
//		fetch.setConsultDate(consultmain.getConsultDate());
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
		logger.info(new Date() +"Inside SERVICE CHSSConsultMainDelete ");
		List<Object[]> billids = dao.consultMainBillIds(consultmainid);
		
		for(Object[] obj : billids)
		{
			CHSSBillDelete(obj[0].toString(), modifiedby);
		}
		
		return dao.CHSSConsultMainDelete(consultmainid);
	}
	
	
	
	@Override
	public long CHSSApplyEdit(CHSSApplyDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSApplyEdit ");
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
		logger.info(new Date() +"Inside SERVICE CHSSApplyEncCountEdit ");
		CHSSApply fetch = dao.getCHSSApply(dto.getCHSSApplyId());
		fetch.setNoEnclosures(Integer.parseInt(dto.getNoEnclosures()));
		return dao.CHSSApplyEdit(fetch);
	}
	
	@Override
	public List<CHSSTestSub> CHSSTestSubList(String treattypeid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSTestSubList ");
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
	

	
	@Override
	public long ConsultationBillAdd(CHSSConsultationDto dto,String chssapplyid, String consultmainidold) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ConsultationBillAdd ");		
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
				
				CalculateConsultEligibleAmt(consult, chssapplyid, dto.getBillId());
				
				count = dao.ConsultationBillAdd(consult);
					
			}
//			UpdateBillAdmissibleTotal(dto.getBillId());
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE ConsultationBillAdd ");
			return 0;
		}
		
	}
	
	
	
	
	@Override
	public long ConsultationBillEdit(CHSSBillConsultation modal,String chssapplyid, String consultmainidold) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ConsultationBillEdit ");
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultType(modal.getConsultType());
		fetch.setDocName(WordUtils.capitalize(modal.getDocName()).trim());
		fetch.setDocQualification(modal.getDocQualification());
		fetch.setConsultDate(modal.getConsultDate());
		fetch.setConsultCharge(modal.getConsultCharge());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		CalculateConsultEligibleAmt(fetch, chssapplyid, String.valueOf(fetch.getBillId()));      
		
		
		long count= dao.ConsultationBillEdit(fetch);
//		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
	@Override
	public long ConsultationBillDelete(String consultationid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ConsultationBillDelete ");
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
		logger.info(new Date() +"Inside SERVICE MedicinesBillAdd ");		
		try {
			
			long count=0;
			for(int i=0 ; i<dto.getMedicineName().length ; i++)
			{
				if(dto.getMedicineName()[i]!=null && !dto.getMedicineName()[i].toString().trim().equalsIgnoreCase("") && dto.getMedicineCost()[i]!=null && !dto.getMedicineCost()[i].trim().equals("") && Integer.parseInt(dto.getMedQuantity()[i])>0 ) 
				{
					CHSSBillMedicine  meds = new CHSSBillMedicine();
					
					meds.setBillId(Long.parseLong(dto.getBillId()));
					meds.setMedicineName(WordUtils.capitalize(dto.getMedicineName()[i]).trim());
					meds.setPresQuantity(Integer.parseInt(dto.getPresQuantity()[i]));
					meds.setMedQuantity(Integer.parseInt(dto.getMedQuantity()[i]));
					meds.setMedicineCost(Double.parseDouble(dto.getMedicineCost()[i]));
					meds.setMedsRemAmount(Double.parseDouble(dto.getMedicineCost()[i]));
					meds.setIsActive(1);
					meds.setCreatedBy(dto.getCreatedBy());
					meds.setCreatedDate(sdtf.format(new Date()));
					
//					calculateMedAmount(meds,dto.getBillId(),treattypeid);
					
									
					count += dao.MedicinesBillAdd(meds);
				}
			}
//			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE MedicinesBillAdd ");
			return 0;
		}
	}
	
	public void checkMedAdmissibility(CHSSBillMedicine  meds) throws Exception
	{
		logger.info(new Date() +"Inside checkMedAdmissibility");
		List<Object[]> medicinedata = dao.MedAdmissibleCheck(WordUtils.capitalize(meds.getMedicineName()).trim());
		if(medicinedata!=null && medicinedata.size()>0)
		{
			meds.setMedsRemAmount(0);
			meds.setComments("Found in inadmissible List : MedicineNo: "+medicinedata.get(0)[1]);
		}
	}
	
	
	
	@Override
	public long MedicineBillEdit(CHSSBillMedicine modal, String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MedicineBillEdit ");
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
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
//		calculateMedAmount(fetch,String.valueOf(modal.getBillId()),treattypeid);
				
		long count =dao.MedicineBillEdit(fetch);
//		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
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
		logger.info(new Date() +"Inside SERVICE MedicineBillDelete ");
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
		logger.info(new Date() +"Inside SERVICE TestsBillAdd ");		
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
//					getTestEligibleAmount(test);					
					test.setCreatedBy(dto.getCreatedBy());
					test.setCreatedDate(sdtf.format(new Date()));
					
					
					count = dao.TestsBillAdd(test);
				}
			}
					
//			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE TestsBillAdd ");
			return 0;
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
		logger.info(new Date() +"Inside SERVICE TestBillEdit ");
		CHSSBillTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestMainId(modal.getTestMainId());
		fetch.setTestSubId(modal.getTestSubId());
		fetch.setTestCost(modal.getTestCost());
//		getTestEligibleAmount(fetch);
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.TestBillEdit(fetch);
//		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
		
	}
	
	@Override
	public long TestBillDelete(String chsstestid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside v TestBillDelete ");
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
		logger.info(new Date() +"Inside SERVICE MiscBillAdd ");		
		try {
			long count=0;
			for(int i=0 ; i<dto.getMiscItemName().length ; i++)
			{
				CHSSBillMisc  misc = new CHSSBillMisc();
				CHSSBill bill = dao.getCHSSBill(dto.getBillId());
				misc.setBillId(Long.parseLong(dto.getBillId()));
				misc.setMiscItemName(WordUtils.capitalize(dto.getMiscItemName()[i]).trim());
				misc.setMiscItemCost(Double.parseDouble(dto.getMiscItemCost()[i]));
				misc.setAmountPaid(RoundTo2Decimal(misc.getMiscItemCost()-(misc.getMiscItemCost() *(bill.getDiscountPercent()/100)))); 
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
			logger.error(new Date() +" Inside SERVICE MiscBillAdd ");
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
		logger.info(new Date() +"Inside SERVICE MiscBillEdit ");
		CHSSBillMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		CHSSBill bill = dao.getCHSSBill(String.valueOf(fetch.getBillId()));
		fetch.setMiscItemName(WordUtils.capitalize(modal.getMiscItemName()).trim());
		fetch.setMiscItemCost(modal.getMiscItemCost());
		fetch.setAmountPaid(RoundTo2Decimal(modal.getMiscItemCost()-(modal.getMiscItemCost() *(bill.getDiscountPercent()/100))));
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
		logger.info(new Date() +"Inside SERVICE MiscBillDelete ");
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
		logger.info(new Date() +"Inside SERVICE OtherBillAdd ");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getOtherItemId().length ; i++)
			{
				CHSSBillOther  other = new CHSSBillOther();
				
				other.setBillId(Long.parseLong(dto.getBillId()));
				other.setOtherItemId(Integer.parseInt(dto.getOtherItemId()[i]));
				other.setOtherItemCost(Double.parseDouble(dto.getOtherItemCost()[i]));
				other.setIsActive(1);
				other.setCreatedBy(dto.getCreatedBy());
				other.setCreatedDate(sdtf.format(new Date()));
				
//				getOtherItemRemAmount(dto.getEmpid(),other);				
				count = dao.OtherBillAdd(other);
			}
					
			
//			UpdateBillAdmissibleTotal(dto.getBillId());
			
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE OtherBillAdd ");
			return 0;
		}
		
	}
	
	
	
	
	
	@Override
	public long OtherBillEdit(CHSSBillOther modal,String empid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE OtherBillEdit ");
		CHSSBillOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherItemId(modal.getOtherItemId());
		fetch.setOtherItemCost(modal.getOtherItemCost());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
//		getOtherItemRemAmount(String.valueOf(empid),fetch);		
		long count =dao.OtherBillEdit(fetch);
//		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
		
	}
	
	@Override
	public long OtherBillDelete(String chssotherid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE OtherBillDelete ");
		CHSSBillOther fetch = dao.getCHSSOther(chssotherid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.OtherBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
	}
	
	public void CalculateConsultEligibleAmt(CHSSBillConsultation consult,String chssapplyid,String billid) throws Exception 
	{		
		logger.info(new Date() +"Inside SERVICE CalculateConsultEligibleAmt ");
		CHSSBill bill = dao.getCHSSBill(billid);
		
		Double applyamount= consult.getConsultCharge()-(consult.getConsultCharge()*(bill.getDiscountPercent()/100));
		consult.setAmountPaid(RoundTo2Decimal(applyamount));
		int speciality=consult.getDocQualification();
		String consultdate=consult.getConsultDate();
		CHSSDoctorRates rate  = dao.getDocterRate(String.valueOf(speciality));
		int allowedamt=rate.getConsultation_1();
		
		
		String fromdate = LocalDate.parse(consultdate).minusDays(6).toString();
		
		List<Object[]> consultlist = dao.CheckPrevConsultInfo(String.valueOf(consult.getConsultationId()), bill.getCHSSConsultMainId(), fromdate, consultdate);
		
		if(consultlist.size()>0) 
		{
			allowedamt=0;
			consult.setConsultType("FollowUp");
			consult.setComments("FollowUp Consultation with in a week ");
		}
		else
		{
			fromdate = LocalDate.parse(consultdate).minusDays(13).toString();
			String todate = LocalDate.parse(consultdate).minusDays(6).toString();
			consultlist = dao.CheckPrevConsultInfo(String.valueOf(consult.getConsultationId()), bill.getCHSSConsultMainId(), fromdate, todate);
			if(consultlist.size()>0) 
			{
				String consulttype = consultlist.get(0)[3].toString();
				
				if(consulttype.trim().equalsIgnoreCase("Fresh")) 
				{
					allowedamt=rate.getConsultation_2();
					consult.setConsultType("FollowUp");
					consult.setComments("FollowUp Consultation with in 2 weeks ");
				}else if(consulttype.trim().equalsIgnoreCase("FollowUp"))
				{
					allowedamt=rate.getConsultation_1();
					consult.setConsultType("Fresh");
					if (allowedamt <= applyamount) {
						consult.setComments(df.format(allowedamt)+" is admitted.");
					} else {
						consult.setComments(df.format(applyamount)+" is admitted.");
					}
				}
				
			}
			else
			{
				allowedamt=rate.getConsultation_1();
				consult.setConsultType("Fresh");
				if (allowedamt <= applyamount) {
					consult.setComments(df.format(allowedamt)+" is admitted.");
				} else {
					consult.setComments(df.format(applyamount)+" is admitted.");
				}
				
			}
		}
		
		if (allowedamt <= applyamount) {			
			consult.setConsultRemAmount(RoundTo2Decimal(allowedamt));
		} else {
			consult.setConsultRemAmount(RoundTo2Decimal(applyamount));
		}
		
	}
	public void getTestEligibleAmount(CHSSBillTests testsub) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE getTestEligibleAmount ");
		CHSSBill bill = dao.getCHSSBill(String.valueOf(testsub.getBillId()));
		double applyamount = testsub.getTestCost()-(testsub.getTestCost() * (bill.getDiscountPercent()/100));
		testsub.setAmountPaid(RoundTo2Decimal(applyamount));
		int testsubamount = dao.getCHSSTestSub(String.valueOf(testsub.getTestSubId())).getTestRate();
		
		if(testsubamount<= applyamount)
		{
			testsub.setTestRemAmount(RoundTo2Decimal(testsubamount));
		}
		else
		{
			testsub.setTestRemAmount(RoundTo2Decimal(applyamount));
		}
		testsub.setComments(df.format(testsub.getTestRemAmount())+" is admitted.");
	}
	
	public void calculateMedAmount(CHSSBillMedicine  meds, String billid,String treattypeid  ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE calculateMedAmount ");
		CHSSBill bill = dao.getCHSSBill(billid);
		int purs = meds.getMedQuantity();
		int pres = meds.getPresQuantity();
		double cost = meds.getMedicineCost()-(meds.getMedicineCost()*(bill.getDiscountPercent()/100));
		meds.setMedsRemAmount(cost);
		meds.setAmountPaid(RoundTo2Decimal(cost));
		if(purs>pres) 
		{
			cost=cost/(double)purs;
			meds.setMedsRemAmount(Math.round((cost*pres) * 100.0) / 100.0); 
		}		
		
		if(treattypeid.toString().equalsIgnoreCase("1")) {
			checkMedAdmissibility(meds);
		}					
		if(meds.getMedsRemAmount()>0) {
			meds.setMedsRemAmount(RoundTo2Decimal(meds.getMedsRemAmount()));
			meds.setComments(df.format(meds.getMedsRemAmount())+" is admitted.");
		}	
		
		
	}
	
	public void getOtherItemRemAmount(String empid,CHSSBillOther other) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE getOtherItemRemAmount ");
		CHSSBill bill = dao.getCHSSBill(String.valueOf(other.getBillId()));
		double itemcost = other.getOtherItemCost()-(other.getOtherItemCost() * (bill.getDiscountPercent()/100));
		other.setAmountPaid(RoundTo2Decimal(itemcost));
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
			other.setOtherRemAmount(RoundTo2Decimal(itemcost));
		}
		else
		{
			other.setOtherRemAmount(RoundTo2Decimal(rembamt));
		}
		other.setComments(df.format(other.getOtherRemAmount())+" is admitted.");
	}
	
	public int UpdateBillAdmissibleTotal(String billid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE UpdateBillAdmissibleTotal ");
		Object[] bill = dao.CHSSBill(billid);
		return dao.UpdateBillAdmissibleTotal(bill[0].toString(), bill[11].toString());
	}
	
	
	public void UpdateOPDClaimItemsRemAmount(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE UpdateOPDClaimItemsRemAmount ");
		CHSSApply chssapply =dao.getCHSSApply(chssapplyid);
		String treattypeid= chssapply.getTreatTypeId().toString();
		long empid =  chssapply.getEmpId();
		List<Object[]> CHSSbillsList = dao.CHSSBillsList(chssapplyid);
		
		for(Object[] billdata : CHSSbillsList)
		{
			BigDecimal billRemAmt = new BigDecimal(0);
			String billid =billdata[0].toString();
			List<CHSSBillConsultation> consultationList = dao.CHSSConsultationList(billid);
			List<CHSSBillTests> testList  = dao.CHSSTestsList(billid);
			List<CHSSBillMedicine> medicineList  = dao.CHSSMedicineList(billid);
			List<CHSSBillOther> otherList  = dao.CHSSOtherList(billid);
			List<CHSSBillMisc> miscellaneouList  = dao.CHSSMiscList(billid);
			CHSSBill bill = dao.getCHSSBill(billid);
			
			for(CHSSBillConsultation consult : consultationList)
			{
				CalculateConsultEligibleAmt(consult, chssapplyid, billid);				
				consult.setAmountPaid(RoundTo2Decimal(consult.getConsultCharge()-(consult.getConsultCharge()*(bill.getDiscountPercent()/100))));
				long count= dao.ConsultationBillEdit(consult);
				billRemAmt = billRemAmt.add (new BigDecimal(consult.getConsultRemAmount()));
			}
			
			for(CHSSBillTests test:testList)
			{
				getTestEligibleAmount(test);
				test.setAmountPaid(RoundTo2Decimal(test.getTestCost()-(test.getTestCost()*(bill.getDiscountPercent()/100))));
				long count =dao.TestBillEdit(test);
				billRemAmt = billRemAmt.add (new BigDecimal(test.getTestRemAmount()));
			}
			
			
			for(CHSSBillMedicine medicine :medicineList)
			{
				calculateMedAmount(medicine,billid,treattypeid);
				medicine.setAmountPaid(RoundTo2Decimal(medicine.getMedicineCost()-(medicine.getMedicineCost()*(bill.getDiscountPercent()/100))));
				long count =dao.MedicineBillEdit(medicine);
				billRemAmt = billRemAmt.add (new BigDecimal(medicine.getMedsRemAmount()));
			}
			
			for(CHSSBillOther other :otherList)
			{
				getOtherItemRemAmount(String.valueOf(empid),other);		
				other.setAmountPaid(RoundTo2Decimal(other.getOtherItemCost()-(other.getOtherItemCost()*(bill.getDiscountPercent()/100))));
				long count =dao.OtherBillEdit(other);
				billRemAmt = billRemAmt.add (new BigDecimal(other.getOtherRemAmount()));
			}
			
			for(CHSSBillMisc misc :miscellaneouList)
			{
				misc.setMiscRemAmount(0.0);
				misc.setAmountPaid(RoundTo2Decimal(misc.getMiscItemCost()-(misc.getMiscItemCost()*(bill.getDiscountPercent()/100))));
				long count =dao.MiscBillEdit(misc);
				billRemAmt = billRemAmt.add (new BigDecimal(misc.getMiscRemAmount()));
			}
			
			UpdateBillAdmissibleTotal(billid);
			
		}
		
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
		logger.info(new Date() +"Inside SERVICE CHSSUserForward ");
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
				
				if(claimstatus==1 && claim.getCHSSApplyNo().trim().equals("-")) 
				{
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
				notify.setNotificationUrl("CHSSApplyDashboard.htm");
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
		
		if(claim.getCHSSStatusId()==2) 
		{
			UpdateOPDClaimItemsRemAmount(CHSSApplyId);
		}
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
	public List<Object[]> CHSSApproveClaimList(String logintype,String empid,String claimtype ) throws Exception 
	{
		return dao.CHSSApproveClaimList(logintype,empid,claimtype);
	}
	
	@Override
	public long ConsultRemAmountEdit(CHSSBillConsultation modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ConsultRemAmountEdit ");
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultRemAmount(RoundTo2Decimal(modal.getConsultRemAmount()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		if(modal.getConsultType()!=null) {
			fetch.setConsultType(modal.getConsultType());
		}
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.ConsultationBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long TestRemAmountEdit(CHSSBillTests modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE TestRemAmountEdit ");
		CHSSBillTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestRemAmount(RoundTo2Decimal(modal.getTestRemAmount()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.TestBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long OtherRemAmountEdit(CHSSBillOther modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE OtherRemAmountEdit ");
		CHSSBillOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherRemAmount(RoundTo2Decimal(modal.getOtherRemAmount()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.OtherBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long MedRemAmountEdit(CHSSBillMedicine modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MedRemAmountEdit ");
		CHSSBillMedicine fetch = dao.getCHSSMedicine(String.valueOf(modal.getCHSSMedicineId()));
		fetch.setMedsRemAmount(RoundTo2Decimal(modal.getMedsRemAmount()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.MedicineBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long MiscRemAmountEdit(CHSSBillMisc modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE MiscRemAmountEdit ");
		CHSSBillMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		fetch.setMiscRemAmount(RoundTo2Decimal(modal.getMiscRemAmount()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
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
	public List<Object[]> CHSSBatchApproval(String logintype, String todate,String contingentid,String ClaimType) throws Exception
	{
		return dao.CHSSBatchApproval(logintype,  todate,contingentid, ClaimType);
	}
	
	@Override
	public CHSSContingent getCHSSContingent(String contingentid) throws Exception
	{
		return dao.getCHSSContingent(contingentid);
	}
	
	@Override
	public List<CHSSBillEquipment> CHSSEquipmentList(String billid) throws Exception
	{
		return dao.CHSSEquipmentList(billid);
	}
	
	@Override
	public long ContingentGenerate(String CHSSApplyId[],String Username, String action,String billcontent,String logintype,String EmpId,String genTilldate,String claims_type) throws Exception 
	{	
		logger.info(new Date() +"Inside SERVICE ContingentGenerate ");
		CHSSContingent continnew =new CHSSContingent();
		long contingentid=0;
		if(claims_type.equalsIgnoreCase("OPD")) 
		{
			continnew.setContingentBillNo(GenerateOPDContingentNo());
		}
		else if(claims_type.equalsIgnoreCase("IPD")) 
		{
			continnew.setContingentBillNo(GenerateIPDContingentNo());			
		}
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
		continnew.setGenTillDate(genTilldate);
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
	
	
	
	
	public String GenerateOPDContingentNo() throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GenerateOPDContingentNo ");
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
			logger.error(new Date() +"Inside SERVICE GenerateOPDContingentNo " +e);
			e.printStackTrace();
			return "";
		}
	}
	
	public String GenerateIPDContingentNo() throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GenerateIPDContingentNo ");
		try {
			String value="STARC/F&A/Med-IPD/";
						
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
			value = value+start+"-"+end+"/"+today.getMonth().toString().toUpperCase()+"-"+String.valueOf(today.getYear()).substring(2)+"/IPD";
			
			int count=dao.getdata(value);
			
			if(count==0) {
				return value;
			}
			else 
			{
				return  value+"/VOL-"+(count+1);
			}
			
			
		} catch (Exception e) {
			logger.error(new Date() +"Inside SERVICE GenerateIPDContingentNo "+e);
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public long CHSSClaimsApprove(CHSSContingentDto dto) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE CHSSClaimsApprove ");
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
			else if(continstatus==14 ) 
			{
				continstatus=15;
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
				claim.setAmountClaimed(new BigDecimal( claimslist.get(i)[1].toString()) );
				claim.setAmountSettled(new BigDecimal( claimslist.get(i)[2].toString()) );
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
		
		
		
		if(notify.getEmpId()!=null && notify.getEmpId()>0) {
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
		logger.info(new Date() +"Inside SERVICE CHSSContingentClaimList ");
		List<Object[]> claims = dao.CHSSContingentClaimList(contingentid);
		
		LinkedHashMap<Long, ArrayList<Object[]>> sortedclaims  = new LinkedHashMap<Long, ArrayList<Object[]>>();
		if(claims.size()>0) 
		{
			ArrayList<String> empstrs = new ArrayList<String>();
			for(int i=0;i<claims.size();i++)
			{
				if(!empstrs.contains(claims.get(i)[4].toString())) {
					empstrs.add(claims.get(i)[4].toString());
				}
			}
			
			ArrayList<Object[]> empclaims= new ArrayList<Object[]>();
			for(String empstr:empstrs) 
			{
				for(int i=0;i<claims.size();i++)
				{				
					if(empstr.equalsIgnoreCase(claims.get(i)[4].toString()) )
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
	public List<Object[]> ContingentTransactions(String contingentid) throws Exception
	{
		return dao.ContingentTransactions(contingentid);
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
		logger.info(new Date() +"Inside SERVICE CHSSConsultBillsAdd ");
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
			bill.setDiscountPercent(CropTo6Decimal(dto.getDiscountPer()[i]));
			bill.setFinalBillAmt(CropTo2Decimal(dto.getFinalbillamount()[i]));
			bill.setIsActive(1);
			bill.setCreatedBy(dto.getCreatedBy());
			bill.setCreatedDate(sdtf.format(new Date()));
			
			billid=dao.CHSSBillAdd(bill);
		}
		return billid;
	}
	
	@Override
	public long CHSSIPDBillsAdd(ChssBillsDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSIPDBillsAdd ");
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
			bill.setDiscount(0.00);
			bill.setDiscountPercent(0.00);
			bill.setFinalBillAmt(CropTo2Decimal(dto.getFinalbillamount()[i]));
			bill.setIsActive(1);
			bill.setCreatedBy(dto.getCreatedBy());
			bill.setCreatedDate(sdtf.format(new Date()));
			
			billid=dao.CHSSBillAdd(bill);
		}
		return billid;
	}
	
	
	@Override
	public long CHSSBillEdit(CHSSBill bill) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSBillEdit ");
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
	public long CHSSIPDBillEdit(CHSSBill bill) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSIPDBillEdit ");
		CHSSBill fetch = dao.getCHSSBill(String.valueOf(bill.getBillId()));
		
		fetch.setCenterName(WordUtils.capitalize(bill.getCenterName()).trim());
		fetch.setBillNo(bill.getBillNo().trim().toUpperCase());
		fetch.setBillDate(bill.getBillDate());
		fetch.setGSTAmount(bill.getGSTAmount());
//		fetch.setDiscount(0.0);
//		fetch.setDiscountPercent(0.0);
		fetch.setFinalBillAmt(bill.getFinalBillAmt());
		fetch.setModifiedBy(bill.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		return dao.CHSSBillEdit(fetch);
	}
	
	
	@Override
	public long CHSSBillDelete(String billid, String modifiedby) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CHSSBillDelete ");
		CHSSBill fetch = dao.getCHSSBill(billid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		deleteBillItems(billid, modifiedby);
		return dao.CHSSBillEdit(fetch);
	}
	
	
	public int deleteBillItems(String billid,String username) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE deleteBillItems ");
		int count=0;
		try {
			
			count += dao.billConsultDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billTestsDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billMedsDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billOthersDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billMiscDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billEquipmentDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billImplantDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billPackageDeleteAll(billid, username, sdtf.format(new Date()));
			count += dao.billPackageItemsDeleteAll(billid, username, sdtf.format(new Date()));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return count;
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
		logger.info(new Date() +"Inside SERVICE CHSSUserRevoke ");
		CHSSApply claim = dao.getCHSSApply(CHSSApplyId);
		
		if(claim.getPOAcknowledge()==0) 
		{
			claim.setCHSSStatusId(1);
			
			List<CHSSApplyTransaction> transachistory = dao.claimTransactionObjects(CHSSApplyId);
			for(CHSSApplyTransaction transaction : transachistory) {
				if(transaction.getCHSSStatusId()==3) {
					claim.setCHSSStatusId(3);
					break;
				}
			}
		
			
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
		logger.info(new Date() +"Inside SERVICE CHSSContingentDelete ");
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
	public List<Object[]> GetClaimsReportList( String empid, String fromdate, String todate, String claimtype, String status)throws Exception
	{
		return dao.GetClaimsReportList( empid, fromdate , todate ,claimtype,status);
	}
		
	@Override
	public List<Object[]> ClaimConsultMainList(String CHSSApplyId) throws Exception
	{
		return dao.ClaimConsultMainList(CHSSApplyId);
	}
	
	@Override
	public long ClaimDisputeSubmit(CHSSApplyDispute dispute,HttpSession ses) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ClaimDisputeSubmit ");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		String Username = (String) ses.getAttribute("Username");
		dispute.setRaisedTime(sdtf.format(new Date()));
		dispute.setIsActive(1);
		dispute.setDispStatus("A");		
		dispute.setCreatedBy(Username);
		dispute.setCreatedDate(sdtf.format(new Date()));
		long count= dao.ClaimDisputeAdd(dispute);
		
		if(count>0) {
			EMSNotification notify = new EMSNotification();
			CHSSApply apply = dao.getCHSSApply(String.valueOf(dispute.getCHSSApplyId()));
			notify.setNotificationUrl("ClaimDisputeList.htm");
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy(EmpId);
			notify.setIsActive(1);
			notify.setCreatedBy(Username);
			notify.setCreatedDate(sdtf.format(new Date()));
			notify.setNotificationMessage("Dispute over the Claim "+apply.getCHSSApplyNo()+" Raised");
			
			Object[] notifyto = dao.CHSSApprovalAuth("K");
			if(notifyto==null) {
				notify.setEmpId(0L);
			}else {
				notify.setEmpId(Long.parseLong(notifyto[0].toString()));
				
			}
			
			if(notify.getEmpId()!=null && notify.getEmpId()>0) {
				dao.NotificationAdd(notify);
			}
		}
		
		return count;
	}
	
	@Override
	public long ClaimDisputeResponseSubmit(CHSSApplyDispute modal,HttpSession ses) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ClaimDisputeResponseSubmit ");
		Long EmpId = ((Long) ses.getAttribute("EmpId"));
		String Username = (String) ses.getAttribute("Username");
		
		CHSSApplyDispute dispute = dao.getCHSSApplyDispute(String.valueOf(modal.getCHSSApplyId()));
		dispute.setResponseMsg(modal.getResponseMsg());
		dispute.setResponderEmpid(EmpId);
		dispute.setResponseTime(sdtf.format(new Date()));
		dispute.setDispStatus("C");
		dispute.setModifiedBy(Username);
		dispute.setModifiedDate(sdtf.format(new Date()));
		long count= dao.ClaimDisputeAdd(dispute);
		
		if(count>0) 
		{
			EMSNotification notify = new EMSNotification();
			CHSSApply apply = dao.getCHSSApply(String.valueOf(dispute.getCHSSApplyId()));
			notify.setNotificationUrl("CHSSApplyDashboard.htm");
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy(EmpId);
			notify.setIsActive(1);
			notify.setCreatedBy(Username);
			notify.setCreatedDate(sdtf.format(new Date()));
			notify.setNotificationMessage("Response For Dispute Over Claim "+apply.getCHSSApplyNo()+"<br> Recieved");
			
			notify.setEmpId(apply.getEmpId());
			
			dao.NotificationAdd(notify);
		}
		
		return count;
	}
	
	@Override
	public List<Object[]> ClaimDisputeClosedList(String fromdate,String todate)throws Exception
	{
		return dao.ClaimDisputeClosedList(fromdate, todate);
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
		logger.info(new Date() +"Inside SERVICE DeleteClaimData ");
		List<Object[]> billslist = dao.CHSSBillsList(chssapplyid);
				
		for(Object[] obj : billslist)
		{
			CHSSBillDelete(obj[0].toString(), username);
		}
		dao.claimConsultMainDeleteAll(chssapplyid, username, sdtf.format(new Date()));
		
		CHSSIPDClaimsInfo fetch = dao.getCHSSIPDClaimsInfo(chssapplyid);
		dao.CHSSIPDBasicInfoEdit(fetch);
		
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
		logger.info(new Date() +"Inside SERVICE CHSSIPDBasicInfoEdit ");
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
		 logger.info(new Date() +"Inside SERVICE saveFile ");
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
		logger.info(new Date() +"Inside SERVICE AddMasterEditComments ");
		Timestamp instant= Timestamp.from(Instant.now());
		String timestampstr = instant.toString().replace(" ","").replace(":", "").replace("-", "").replace(".","");
		
		   if(masterdto.getFilePath()!=null && !masterdto.getFilePath().isEmpty()) {
				String name =masterdto.getFilePath().getOriginalFilename();
				String filename= "MasterEditFile-"+timestampstr +"."+FilenameUtils.getExtension(masterdto.getFilePath().getOriginalFilename());
				String filepath=emsfilespath+"EMS/MastersEditFilePath";
						
				masteredit.setFilePath(filepath+File.separator+filename);
				masteredit.setOriginalName(name);
			    saveFile(filepath , filename, masterdto.getFilePath());
				
			}	
		return dao.AddMasterEditComments(masteredit);
	}
	
	

	@Override
	public List<Object[]> IPDBillOtherItems(String billid)throws Exception
	{
		return dao.IPDBillOtherItems(billid);
	}
	
	
	@Override
	public long IPDBillHeadDataAddEdit(CHSSBillOther other)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDBillHeadDataAddEdit ");
		CHSSBillOther fetch = dao.getCHSSIPDOther(0l,other.getBillId(),other.getOtherItemId());
		long count=0;
		if(fetch==null && other.getOtherItemCost()>0) 
		{
			other.setAmountPaid(RoundTo2Decimal(other.getOtherItemCost()));
			other.setComments("");
			other.setIsActive(1);
			other.setCreatedDate(sdtf.format(new Date()));
			count= dao.OtherBillAdd(other);
		}
		else 
		{
			fetch.setOtherItemCost(other.getOtherItemCost());
			fetch.setAmountPaid(RoundTo2Decimal(other.getOtherItemCost()));
			fetch.setModifiedDate(sdtf.format(new Date()));
			fetch.setModifiedBy(other.getCreatedBy());
			count= dao.OtherBillEdit(fetch);
		}
		
		return count;
	}
	
	@Override
	public long IPDConsultAdd(CHSSConsultationDto dto,String chssapplyid, String consultmainidold) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDConsultAdd ");		
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
//				consult.setAmountPaid(RoundTo2Decimal(Double.parseDouble(dto.getConsultCharge()[i]));				
				consult.setConsultRemAmount(Double.parseDouble(dto.getConsultCharge()[i]));
				consult.setIsActive(1);
				consult.setCreatedBy(dto.getCreatedBy());
				consult.setCreatedDate(sdtf.format(new Date()));		
				
				consult.setComments(df.format(consult.getConsultRemAmount())+" is admitted.");
				count = dao.ConsultationBillAdd(consult);
					
			}
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE IPDConsultAdd ");
			return 0;
		}
		
	}
	
	@Override
	public long IPDConsultEdit(CHSSBillConsultation modal,String chssapplyid, String consultmainidold) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDConsultEdit ");
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultType(modal.getConsultType());
		fetch.setDocName(WordUtils.capitalize(modal.getDocName()).trim());
		fetch.setDocQualification(modal.getDocQualification());
		fetch.setConsultDate(modal.getConsultDate());
		fetch.setConsultCharge(modal.getConsultCharge());
		fetch.setAmountPaid(RoundTo2Decimal(modal.getConsultCharge()));
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		fetch.setConsultRemAmount(modal.getConsultCharge());      
		fetch.setComments(df.format(fetch.getConsultRemAmount())+" is admitted.");
		
		long count= dao.ConsultationBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		
		return count;
	}
	
	
	
	@Override
	public long IPDTestsBillAdd(CHSSTestsDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDTestsBillAdd ");		
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
					test.setTestRemAmount(Double.parseDouble(dto.getTestCost()[i]));
					test.setIsActive(1);
//					getTestEligibleAmount(test);					
					test.setCreatedBy(dto.getCreatedBy());
					test.setCreatedDate(sdtf.format(new Date()));
					
					
					count = dao.TestsBillAdd(test);
				}
			}
					
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE IPDTestsBillAdd ");
			return 0;
		}
		
	}
	
	@Override
	public long IPDTestBillEdit(CHSSBillTests modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDTestBillEdit ");
		CHSSBillTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestMainId(modal.getTestMainId());
		fetch.setTestSubId(modal.getTestSubId());
		fetch.setTestCost(modal.getTestCost());
		fetch.setTestRemAmount(fetch.getTestCost());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.TestBillEdit(fetch);
		return count;
	}
	
	@Override
	public long IPDTestBillDelete(String chsstestid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDTestBillDelete ");
		CHSSBillTests fetch = dao.getCHSSTest(chsstestid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.TestBillEdit(fetch);
		
		return count;
	}
	
	@Override
	public long IPDMiscBillAdd(CHSSMiscDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDMiscBillAdd ");		
		try {
			long count=0;
			for(int i=0 ; i<dto.getMiscItemName().length ; i++)
			{
				CHSSBillMisc  misc = new CHSSBillMisc();
				CHSSBill bill = dao.getCHSSBill(dto.getBillId());
				misc.setBillId(Long.parseLong(dto.getBillId()));
				misc.setMiscItemName(WordUtils.capitalize(dto.getMiscItemName()[i]).trim());
				misc.setMiscItemCost(Double.parseDouble(dto.getMiscItemCost()[i]));
				misc.setAmountPaid(RoundTo2Decimal(misc.getMiscItemCost()-(misc.getMiscItemCost() *(bill.getDiscountPercent()/100))));
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
			logger.error(new Date() +" Inside SERVICE MiscBillAdd ");
			return 0;
		}
		
	}
	
	@Override
	public long IPDMiscBillEdit(CHSSBillMisc modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDMiscBillEdit ");
		CHSSBillMisc fetch = dao.getCHSSMisc(String.valueOf(modal.getChssMiscId()));
		CHSSBill bill = dao.getCHSSBill(String.valueOf(fetch.getBillId()));
		fetch.setMiscItemName(WordUtils.capitalize(modal.getMiscItemName()).trim());
		fetch.setMiscItemCost(modal.getMiscItemCost());
		fetch.setAmountPaid(RoundTo2Decimal(modal.getMiscItemCost()-(modal.getMiscItemCost() *(bill.getDiscountPercent()/100))));
		fetch.setMiscCount(modal.getMiscCount());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.MiscBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
	}
	
	@Override
	public long IPDMiscBillDelete(String chssMiscid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDMiscBillDelete ");
		CHSSBillMisc fetch = dao.getCHSSMisc(chssMiscid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.MiscBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
	}
	
	@Override
	public Object[] getClaimDisputeData(String chssapplyid) throws Exception
	{
		return dao.getClaimDisputeData(chssapplyid);
	}
	
	@Override
	public List<Object[]> ClaimDisputeList(String fromdate,String todate)throws Exception
	{
		return dao.ClaimDisputeList(fromdate, todate);
	}
	
	
	@Override
	public long EquipmentItemAdd(CHSSEquipDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EquipmentItemAdd ");		
		try {
			long count=0;
			for(int i=0 ; i<dto.getEquipName().length ; i++)
			{
				CHSSBillEquipment  equip = new CHSSBillEquipment();
				CHSSBill bill = dao.getCHSSBill(dto.getBillId());
				equip.setBillId(Long.parseLong(dto.getBillId()));
				equip.setEquipmentName(WordUtils.capitalize(dto.getEquipName()[i]).trim());
				equip.setEquipmentCost(Double.parseDouble(dto.getEquipCost()[i]));
				equip.setAmountPaid(RoundTo2Decimal(equip.getEquipmentCost()-(equip.getEquipmentCost() *(bill.getDiscountPercent()/100))));
//				equip.setEquipmentRemAmt(equip.getEquipmentCost()-(equip.getEquipmentCost() *(bill.getDiscountPercent()/100)));
				equip.setEquipmentRemAmt(0);
				equip.setIsActive(1);
				equip.setCreatedBy(dto.getCreatedBy());
				equip.setCreatedDate(sdtf.format(new Date()));
				count = dao.EquipmentBillAdd(equip);
			}
			
			return count;
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE EquipmentItemAdd "+e);
			return 0;
		}
		
	}
	
	@Override
	public long EquipmentItemEdit(CHSSBillEquipment modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EquipmentItemEdit ");
		CHSSBillEquipment fetch = dao.getCHSSEquipment(String.valueOf(modal.getCHSSEquipmentId()));
		CHSSBill bill = dao.getCHSSBill(String.valueOf(fetch.getBillId()));
		fetch.setEquipmentName(WordUtils.capitalize(modal.getEquipmentName()).trim());
		fetch.setEquipmentCost(modal.getEquipmentCost());
		fetch.setAmountPaid(RoundTo2Decimal(modal.getEquipmentCost()-(modal.getEquipmentCost() *(bill.getDiscountPercent()/100))));
//		fetch.setEquipmentRemAmt(modal.getEquipmentCost()-(modal.getEquipmentCost() *(bill.getDiscountPercent()/100)));
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.EquipmentBillEdit(fetch);
		
		return count;
	}
	
	@Override
	public long EquipmentBillDelete(String chssequipid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE EquipmentBillDelete ");
		CHSSBillEquipment fetch = dao.getCHSSEquipment(chssequipid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.EquipmentBillEdit(fetch);
		
		return count;
	}
	
	
	
	
	@Override
	public long ImplantItemAdd(CHSSImplantDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ImplantItemAdd ");		
		try {
			long count=0;
			for(int i=0 ; i<dto.getImplantName().length ; i++)
			{
				CHSSBillImplants  implant = new CHSSBillImplants();
				CHSSBill bill = dao.getCHSSBill(dto.getBillId());
				implant.setBillId(Long.parseLong(dto.getBillId()));
				implant.setImplantName(WordUtils.capitalize(dto.getImplantName()[i]).trim());
				implant.setImplantCost(Double.parseDouble(dto.getImplantCost()[i]));
				implant.setAmountPaid(RoundTo2Decimal(implant.getImplantCost()-(implant.getImplantCost() *(bill.getDiscountPercent()/100))));
//				implant.setImplantRemAmount(implant.getImplantCost()-(implant.getImplantCost() *(bill.getDiscountPercent()/100)));
				implant.setImplantRemAmt(0);
				implant.setIsActive(1);
				implant.setCreatedBy(dto.getCreatedBy());
				implant.setCreatedDate(sdtf.format(new Date()));
				count = dao.ImplantBillAdd(implant);
			}
			
			return count;
			
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE ImplantItemAdd ");
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSBillImplants> CHSSImplantList(String billid) throws Exception
	{
		return dao.CHSSImplantList(billid);
	}
	@Override
	public long ImplantItemEdit(CHSSBillImplants modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ImplantItemEdit ");
		CHSSBillImplants fetch = dao.getCHSSImplant(String.valueOf(modal.getCHSSImplantId()));
		CHSSBill bill = dao.getCHSSBill(String.valueOf(fetch.getBillId()));
		fetch.setImplantName(WordUtils.capitalize(modal.getImplantName()).trim());
		fetch.setImplantCost(modal.getImplantCost());
		fetch.setAmountPaid(RoundTo2Decimal(modal.getImplantCost()-(modal.getImplantCost() *(bill.getDiscountPercent()/100))));
//		fetch.setImplantRemAmount(modal.getImplantCost()-(modal.getImplantCost() *(bill.getDiscountPercent()/100)));
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.ImplantBillEdit(fetch);
		
		return count;
	}
	
	@Override
	public long ImplantBillDelete(String chssImplantid, String modifiedby ) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE ImplantBillDelete ");
		CHSSBillImplants fetch = dao.getCHSSImplant(chssImplantid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.ImplantBillEdit(fetch);
		
		return count;
	}
	
	
	@Override
	public long IPDPackageAddService(CHSSIPDPackageDto pkgDto)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDPackageAddService ");
		CHSSBillPkg billPkg = new CHSSBillPkg();
		billPkg.setBillId(Long.parseLong(pkgDto.getBillid()));
		billPkg.setTestSubId(Long.parseLong(pkgDto.getPkg_id()));
		billPkg.setPackageCost(CropTo2Decimal(pkgDto.getPkg_total_cost() ));
		billPkg.setAmountPaid(CropTo2Decimal(pkgDto.getPkg_total_cost() ));
		billPkg.setIsActive(1);
		billPkg.setCreatedBy(pkgDto.getCreatedBy());
		billPkg.setCreatedDate(sdtf.format(new Date()));
		long billPackageId = dao.CHSSIPDBillPackageAdd(billPkg);
		
		String[] pkgitem_id =pkgDto.getPkgitem_id();
		String[] Pkgitem_cost =pkgDto.getPkgitem_cost();
		
		
		if(billPackageId>0) 
		{
			for(int i=0;i<pkgitem_id.length;i++)
			{
				if(Pkgitem_cost[i]!=null && !Pkgitem_cost[i].trim().equalsIgnoreCase("") && Double.parseDouble(Pkgitem_cost[i])>0 ) 
				{
					CHSSBillPkgItems pkgItem =new CHSSBillPkgItems();
					pkgItem.setBillId(Long.parseLong(pkgDto.getBillid()));
					pkgItem.setCHSSBillPkgId(billPackageId);
					pkgItem.setIPDPkgItemId(Integer.parseInt(pkgitem_id[i]));
					pkgItem.setPkgItemCost(CropTo2Decimal(Pkgitem_cost[i]));
					pkgItem.setAmountPaid(CropTo2Decimal(Pkgitem_cost[i]));
					pkgItem.setIsActive(1);
					pkgItem.setCreatedBy(pkgDto.getCreatedBy());
					pkgItem.setCreatedDate(sdtf.format(new Date()));
					
					dao.CHSSIPDBillPackageItemAdd(pkgItem);
				}
			}
		}
		
		return billPackageId;
	}
	
	
	@Override
	public long IPDPackageEditService(CHSSIPDPackageDto pkgDto)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDPackageEditService ");
		CHSSBillPkg billPkg = dao.getCHSSBillPkg(pkgDto.getCHSSBillPkgId());
		billPkg.setTestSubId(Long.parseLong(pkgDto.getPkg_id()));
		billPkg.setPackageCost(CropTo2Decimal(pkgDto.getPkg_total_cost()));
		billPkg.setAmountPaid(CropTo2Decimal(pkgDto.getPkg_total_cost()));
		billPkg.setModifiedBy(pkgDto.getModifiedBy());
		billPkg.setModifiedDate(sdtf.format(new Date()));
		long tempbillPackageId = dao.CHSSBillPkgEdit(billPkg);
		
		String[] pkgitem_id =pkgDto.getPkgitem_id();
		String[] Pkgitem_cost =pkgDto.getPkgitem_cost();
		
		if(tempbillPackageId>0) 
		{
			for(int i=0;i<pkgitem_id.length;i++)
			{
				CHSSBillPkgItems pkgItem = dao.getCHSSIPDPkgItem(pkgDto.getCHSSBillPkgId(), pkgitem_id[i] );
				if(pkgItem==null && !Pkgitem_cost[i].trim().equalsIgnoreCase("") && Double.parseDouble(Pkgitem_cost[i])>0 ) 
				{
					pkgItem =new CHSSBillPkgItems();
					pkgItem.setBillId(Long.parseLong(pkgDto.getBillid()));
					pkgItem.setCHSSBillPkgId(tempbillPackageId);
					pkgItem.setIPDPkgItemId(Integer.parseInt(pkgitem_id[i]));
					pkgItem.setPkgItemCost(CropTo2Decimal(Pkgitem_cost[i]));
					pkgItem.setAmountPaid(CropTo2Decimal(Pkgitem_cost[i]));
					pkgItem.setIsActive(1);
					pkgItem.setCreatedBy(pkgDto.getModifiedBy());
					pkgItem.setCreatedDate(sdtf.format(new Date()));
					
					dao.CHSSIPDBillPackageItemAdd(pkgItem);
				}else if(pkgItem!=null && (Pkgitem_cost[i].trim().equalsIgnoreCase("") || Double.parseDouble(Pkgitem_cost[i])==0) ) 
				{
					pkgItem.setPkgItemCost(0);
					pkgItem.setAmountPaid(0);
					pkgItem.setIsActive(0);
					pkgItem.setModifiedBy(pkgDto.getModifiedBy());
					pkgItem.setModifiedDate(sdtf.format(new Date()));
					dao.CHSSBillPkgItemEdit(pkgItem);
				}else if(pkgItem!=null && (!Pkgitem_cost[i].trim().equalsIgnoreCase("") || Double.parseDouble(Pkgitem_cost[i])>0) ) 
				{
					pkgItem.setIPDPkgItemId(Integer.parseInt(pkgitem_id[i]));
					pkgItem.setPkgItemCost(CropTo2Decimal(Pkgitem_cost[i]));
					pkgItem.setAmountPaid(CropTo2Decimal(Pkgitem_cost[i]));
					pkgItem.setModifiedBy(pkgDto.getModifiedBy());
					pkgItem.setModifiedDate(sdtf.format(new Date()));
					dao.CHSSBillPkgItemEdit(pkgItem);
				}
			}
		}
		
		return tempbillPackageId;
	}
	
	@Override
	public long IPDPackageDeleteService(CHSSBillPkg pkg)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDPackageDeleteService ");
		int count = dao.ClaimPkgItemsDelete(String.valueOf(pkg.getCHSSBillPkgId()),pkg.getModifiedBy(),LocalDateTime.now().toString());
		
		if(count>0) {
			CHSSBillPkg billPkg = dao.getCHSSBillPkg(String.valueOf(pkg.getCHSSBillPkgId()));
			billPkg.setIsActive(0);
			billPkg.setModifiedBy(pkg.getModifiedBy());
			billPkg.setModifiedDate(LocalDateTime.now().toString());
			return  dao.CHSSBillPkgEdit(billPkg);
		}
		return 0l;
	}
	@Override
	public List<CHSSIPDPkgItems> ClaimPkgItemsAddedAjax(String billid, String billpkgid) throws Exception
	{
		return dao.ClaimPkgItemsAddedAjax(billid, billpkgid);
	}
	
	
	@Override
	public List<Object[]> IPDClaimAttachments(String chssapplyid)throws Exception
	{
		return dao.IPDClaimAttachments(chssapplyid);
	}
	
	@Override
	public long IPDAttachmentsUpdate(String chssapplyid,String attachtypeid, String value)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDAttachmentsUpdate ");
		CHSSIPDAttachments attachment = dao.getIPDClaimAttach(chssapplyid, attachtypeid);
		
		if(attachment==null)
		{
			attachment=new CHSSIPDAttachments();
			attachment.setCHSSApplyId(Long.parseLong(chssapplyid));
			attachment.setIPDAttachTypeId(Integer.parseInt(attachtypeid));
			attachment.setCopyAttached(value);
			attachment.setIsActive(1);
			dao.IPDClaimAttachAdd(attachment);
		}else
		{
			attachment.setCopyAttached(value);
			attachment.setIsActive(1);
			dao.IPDClaimAttachEdit(attachment);
		}
		
		return 0;
	}
	
	@Override
	public long IPDClaimBillDelete(String billid, String modifiedby) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDClaimBillDelete ");
		CHSSBill fetch = dao.getCHSSBill(billid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
		IPDBillItemsDelete(billid, modifiedby);
		return dao.CHSSBillEdit(fetch);
	}
	
	public int IPDBillItemsDelete(String billid,String username) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE IPDBillItemsDelete ");
		int count=0;
		try {
			
			count += dao.billConsultDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billTestsDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billMedsDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billOthersDeleteAll(billid,username,sdtf.format(new Date()));
			count += dao.billMiscDeleteAll(billid,username,sdtf.format(new Date()));
		}catch (Exception e) {
			logger.error(new Date() +"Inside SERVICE FormRoleActive "+ e);
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public List<Object[]> ClaimPackagesList(String billid) throws Exception 
	{
		return dao.ClaimPackagesList(billid);
	}
	
	@Override
	public List<Object[]> ClaimAllPackageItemsList(String billid) throws Exception 
	{
		return dao.ClaimAllPackageItemsList(billid);
				
	}
	@Override
	public List<Object[]> ClaimEquipmentList(String CHSSApplyId) throws Exception 
	{
		return dao.ClaimEquipmentList(CHSSApplyId);
	}
	
	@Override
	public List<Object[]> ClaimImplantList(String CHSSApplyId) throws Exception 
	{
		return dao.ClaimImplantList(CHSSApplyId);
	}
	
	public String GenerateCHSSIPDClaimNo() throws Exception
	{
		logger.info(new Date() +"Inside SERVICE GenerateCHSSIPDClaimNo ");
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
		String CNo = start+end+"/"+today.getMonth().toString().toUpperCase().substring(0,3)+"/IPD/";
		String applynocount=dao.CHSSApplyNoCount(CNo);
		CNo=CNo+(Long.parseLong(applynocount)+1);
		
		return CNo;
	}
	
	@Override
	public long CHSSUserIPDForward(String CHSSApplyId,String Username, String action,String remarks, String EmpId,String LoginType) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE CHSSUserIPDForward ");
		CHSSApply claim = dao.getCHSSApply(CHSSApplyId);
		int claimstatus = claim.getCHSSStatusId();
		EMSNotification notify = new EMSNotification();
		String mailbody = "";
		String Email="";
		if(action.equalsIgnoreCase("F")) 
		{
			notify.setNotificationUrl("CHSSIPDApprovalsList.htm");
			notify.setNotificationMessage("IPD Medical Claim Application Received");
			
			if(claimstatus==1 || claimstatus==3 ) 
			{
				dao.POAcknowldgedUpdate(CHSSApplyId,"0");			
				claim.setCHSSStatusId(2);
				if(claimstatus==1)
				{
					claim.setCHSSApplyDate(LocalDate.now().toString());
					claim.setCHSSForwardDate(LocalDate.now().toString());
				}
				else if(claimstatus==1)
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
				
				if(claimstatus==1 && claim.getCHSSApplyNo().trim().equals("-")) 
				{
					claim.setCHSSApplyNo(GenerateCHSSIPDClaimNo());
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
			
			
			mailbody = "IPD Medical Claim Application ("+claim.getCHSSApplyNo()+") Received for Verification";
						
		}
		
		if(action.equalsIgnoreCase("R")) 
		{
			notify.setNotificationMessage("IPD Medical Claim Application Returned");
			mailbody = "Medical Claim Application ("+claim.getCHSSApplyNo()+") is Returned";

			if(claimstatus==2 || claimstatus==5 || claimstatus==6 || claimstatus==9 || claimstatus==11 || claimstatus==13) 
			{
				claim.setCHSSStatusId(3);
			
				notify.setEmpId(claim.getEmpId());
				 Object[] emp= dao.getEmployee(claim.getEmpId().toString());				
				if( emp[7]!=null) { 	Email =  emp[7].toString();		}
				notify.setNotificationUrl("CHSSApplyDashboard.htm");
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
				notify.setNotificationUrl("CHSSIPDApprovalsList.htm");
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
		
		if(claim.getCHSSStatusId()==2) 
		{
			UpdateIPDClaimItemsRemAmount(CHSSApplyId);
		}
		
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
	
	public void CalculateConsultEligibleAmtIPD(CHSSBillConsultation consult,String billid) throws Exception 
	{		
		logger.info(new Date() +"Inside SERVICE CalculateConsultEligibleAmtIPD ");
		CHSSBill bill = dao.getCHSSBill(billid);
		
		Double applyamount= consult.getConsultCharge()-(consult.getConsultCharge()*(bill.getDiscountPercent()/100));
		consult.setAmountPaid(RoundTo2Decimal(applyamount));
		int speciality=consult.getDocQualification();
		CHSSDoctorRates rate  = dao.getDocterRate(String.valueOf(speciality));
		int allowedamt=rate.getConsultation_1();
		consult.setConsultType("Fresh");
		if (allowedamt <= applyamount) {
			consult.setConsultRemAmount(allowedamt);
		} else {
			consult.setConsultRemAmount(applyamount);
		}
		
		
	}
	
	public double getNonPkgItemRemAmount(String empid,CHSSBillOther other) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE getNonPkgItemRemAmount ");
		CHSSBill bill = dao.getCHSSBill(String.valueOf(other.getBillId()));
		
		Object[] emp =dao.getEmployee(empid);
//		CHSSOtherItems remlist = dao.getCHSSOtherItems(otheritemid);
		long basicpay=0;
		if(emp[4]!=null) {
			 basicpay=Long.parseLong(emp[4].toString());
		}		
		CHSSOtherPermitAmt chssremamt=dao.getCHSSOtherPermitAmt(String.valueOf(other.getOtherItemId()),basicpay);
		
		CHSSIPDClaimsInfo info = dao.getCHSSIPDClaimsInfo(String.valueOf(bill.getCHSSApplyId()));
		long hours=ChronoUnit.HOURS.between(LocalDateTime.parse(info.getAdmissionDate()+"T"+info.getAdmissionTime()), LocalDateTime.parse(info.getDischargeDate()+"T"+info.getDischargeTime()));
		long reminder= hours%(24);
		long days = hours/(24);
		if(reminder>0) {
			++days;
		}
		double rembamt=0;
		if(chssremamt!=null && chssremamt.getItemPermitAmt()!=null) {
			rembamt=chssremamt.getItemPermitAmt()*days;
		}
		
		// calculate item cost if 
		double itemcost=0;
		if(other.getOtherItemId()==2) // if the item is nursing 
		{
			itemcost = 0;
		}
		else if(other.getOtherItemId()==1) // if the item is ward/ room / bed charges it includes nursing charges 
		{
			itemcost = other.getOtherItemCost()-(other.getOtherItemCost() * (bill.getDiscountPercent()/100));
			CHSSBillOther nursing =dao.getCHSSIPDOther(0,bill.getBillId(),2);
			if(nursing != null) 
			{
				itemcost += nursing.getOtherItemCost()-(nursing.getOtherItemCost() * (bill.getDiscountPercent()/100));
			}
		}
		else
		{
			itemcost = other.getOtherItemCost()-(other.getOtherItemCost() * (bill.getDiscountPercent()/100));
		}
		
		
		if(itemcost <= rembamt)
		{
			return RoundTo2Decimal(itemcost);
		}
		else
		{
			return RoundTo2Decimal(rembamt);
		}
	}
	
	public double getPackageEligibleAmount(CHSSBillPkg pkg) throws Exception 
	{
		logger.info(new Date() +"Inside SERVICE getPackageEligibleAmount ");
		CHSSBill bill = dao.getCHSSBill(String.valueOf(pkg.getBillId()));
		double applyamount = pkg.getPackageCost()-(pkg.getPackageCost() * (bill.getDiscountPercent()/100));
		int testsubamount = dao.getCHSSTestSub(String.valueOf(pkg.getTestSubId())).getTestRate();
		
		if(testsubamount<= applyamount)
		{
			return RoundTo2Decimal(testsubamount);
		}
		else
		{
			return RoundTo2Decimal(applyamount);
		}
		
	}
	
	public void UpdateIPDClaimItemsRemAmount(String chssapplyid) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE UpdateIPDClaimItemsRemAmount ");
		CHSSApply chssapply =dao.getCHSSApply(chssapplyid);
		
		long empid =  chssapply.getEmpId();
		
		List<Object[]> CHSSbillsList = dao.CHSSBillsList(chssapplyid);
		
		
		for(Object[] billdata : CHSSbillsList)
		{
			String billid =billdata[0].toString();
			List<CHSSBillPkg> packages = dao.CHSSBillPkgList(billid);
			List<CHSSBillConsultation> consultationList = dao.CHSSConsultationList(billid);
			List<CHSSBillTests> testList  = dao.CHSSTestsList(billid);
			List<CHSSBillMisc> miscellaneouList  = dao.CHSSMiscList(billid);
			List<CHSSBillEquipment> Equipments = dao.BillEquipmentList(billid);
			List<CHSSBillOther> nonPkgItems  = dao.CHSSOtherList(billid);
			List<CHSSBillImplants> implants  = dao.BillImplantsList( billid);
			
			CHSSBill bill = dao.getCHSSBill(billid);
			
			long count=0;
			
			
			for(CHSSBillPkg pkg :packages)
			{
				pkg.setPkgRemAmt(getPackageEligibleAmount(pkg));		
				pkg.setAmountPaid(RoundTo2Decimal(pkg.getPackageCost()-(pkg.getPackageCost()*(bill.getDiscountPercent()/100))));
				pkg.setComments(df.format(pkg.getPkgRemAmt())+" is admitted.");
				count =dao.CHSSBillPkgEdit(pkg);
			}
			
			
			for(CHSSBillOther item :nonPkgItems)
			{
				item.setOtherRemAmount(getNonPkgItemRemAmount(String.valueOf(empid),item));		
				item.setAmountPaid(RoundTo2Decimal(item.getOtherItemCost()-(item.getOtherItemCost()*(bill.getDiscountPercent()/100))));
				item.setComments(df.format(item.getOtherRemAmount())+" is admitted.");
				count =dao.OtherBillEdit(item);
			}
			
			for(CHSSBillConsultation consult : consultationList)
			{
				consult.setAmountPaid(RoundTo2Decimal(consult.getConsultCharge()-(consult.getConsultCharge()*(bill.getDiscountPercent()/100))));
				consult.setConsultRemAmount(RoundTo2Decimal(consult.getConsultCharge()-(consult.getConsultCharge()*(bill.getDiscountPercent()/100))));
				CalculateConsultEligibleAmtIPD(consult,  billid);
				consult.setComments(df.format(consult.getConsultRemAmount())+" is admitted.");
				count += dao.ConsultationBillEdit(consult);
			}
			
			for(CHSSBillTests test:testList)
			{
				getTestEligibleAmount(test);
				test.setAmountPaid(RoundTo2Decimal(test.getTestCost()-(test.getTestCost()*(bill.getDiscountPercent()/100))));
				test.setComments(df.format(test.getTestRemAmount())+" is admitted.");
				count += dao.TestBillEdit(test);
			}
			
			for(CHSSBillEquipment equip :Equipments)
			{
				equip.setAmountPaid(RoundTo2Decimal(equip.getEquipmentCost()-(equip.getEquipmentCost()*(bill.getDiscountPercent()/100))));
				equip.setEquipmentRemAmt(RoundTo2Decimal(equip.getEquipmentCost()-(equip.getEquipmentCost()*(bill.getDiscountPercent()/100))));
				equip.setComments(df.format(equip.getEquipmentRemAmt())+" is admitted.");
				count += dao.EquipmentBillEdit(equip);
			}
			
			for(CHSSBillImplants imp :implants)
			{
				imp.setAmountPaid(RoundTo2Decimal(imp.getImplantCost()-(imp.getImplantCost()*(bill.getDiscountPercent()/100))));
				imp.setImplantRemAmt(RoundTo2Decimal(imp.getImplantCost()-(imp.getImplantCost()*(bill.getDiscountPercent()/100))));
				imp.setComments(df.format(imp.getImplantRemAmt())+" is admitted.");
				count += dao.ImplantBillEdit(imp);
			}
			
			
			for(CHSSBillMisc misc :miscellaneouList)
			{
				misc.setMiscRemAmount(0.0);
				misc.setAmountPaid(RoundTo2Decimal(misc.getMiscItemCost()-(misc.getMiscItemCost()*(bill.getDiscountPercent()/100))));
				misc.setComments(df.format(misc.getMiscRemAmount())+" is admitted.");
				count += dao.MiscBillEdit(misc);
			}
			
			UpdateBillAdmissibleTotal(billid);
			
		}
		
	}
	
	
	@Override
	public List<CHSSBillEquipment> BillEquipmentList(String billid) throws Exception
	{
		return dao.BillEquipmentList(billid);
	}
	
	@Override
	public List<CHSSBillImplants> BillImplantsList(String billid) throws Exception
	{
		return dao.BillImplantsList(billid);
	}
	
	
	@Override
	public long IPDConsultRemAmountEdit(CHSSBillConsultation modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDConsultRemAmountEdit SERVICE");
		CHSSBillConsultation fetch = dao.getCHSSConsultation(String.valueOf(modal.getConsultationId()));
		fetch.setConsultRemAmount(modal.getConsultRemAmount());
		fetch.setComments(modal.getComments());
		
		if(modal.getConsultType()!=null) {
			fetch.setConsultType(modal.getConsultType());
		}
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.ConsultationBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	
	@Override
	public long IPDTestRemAmountEdit(CHSSBillTests modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDTestRemAmountEdit ");
		CHSSBillTests fetch = dao.getCHSSTest(String.valueOf(modal.getCHSSTestId()));
		fetch.setTestRemAmount(modal.getTestRemAmount());
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.TestBillEdit(fetch);
		UpdateBillAdmissibleTotal(fetch.getBillId().toString());
		return count;
		
	}
	
	@Override
	public long IPDEquipmentRemEdit(CHSSBillEquipment modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDEquipmentRemEdit ");
		CHSSBillEquipment fetch = dao.getCHSSEquipment(String.valueOf(modal.getCHSSEquipmentId()));
		fetch.setEquipmentRemAmt(RoundTo2Decimal(modal.getEquipmentRemAmt()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.EquipmentBillEdit(fetch);
		UpdateBillAdmissibleTotal(String.valueOf(fetch.getBillId()));
		return count;
		
	}
	
	@Override
	public long IPDImplantRemEdit(CHSSBillImplants modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDImplantRemEdit ");
		CHSSBillImplants fetch = dao.getCHSSImplant(String.valueOf(modal.getCHSSImplantId()));
		fetch.setImplantRemAmt(RoundTo2Decimal(modal.getImplantRemAmt()));
		fetch.setComments(modal.getComments());
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		
		long count =dao.ImplantBillEdit(fetch);
		UpdateBillAdmissibleTotal(String.valueOf(fetch.getBillId()));
		return count;
		
	}
	
	@Override
	public long IPDOtherRemAmountEdit(CHSSBillOther modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDOtherRemAmountEdit ");
		CHSSBillOther fetch = dao.getCHSSOther(String.valueOf(modal.getCHSSOtherId()));
		fetch.setOtherRemAmount(RoundTo2Decimal(modal.getOtherRemAmount()));
		fetch.setComments(modal.getComments());

		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.OtherBillEdit(fetch);
		UpdateBillAdmissibleTotal(String.valueOf(fetch.getBillId()));
		return count;
		
	}
	
	
	@Override
	public long IPDPkgRemAmountEdit(CHSSBillPkg modal) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE IPDPkgRemAmountEdit ");
		CHSSBillPkg fetch = dao.getCHSSBillPkg(String.valueOf(modal.getCHSSBillPkgId() ));
		fetch.setPkgRemAmt(RoundTo2Decimal(modal.getPkgRemAmt()));
		fetch.setComments(modal.getComments());

		if(modal.getUpdateByEmpId()!=null && modal.getUpdateByEmpId()>0)
		{
			fetch.setUpdateByEmpId(modal.getUpdateByEmpId());
			fetch.setUpdateByRole(modal.getUpdateByRole());
		}
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		
		long count =dao.CHSSBillPkgEdit(fetch);
		UpdateBillAdmissibleTotal(String.valueOf(fetch.getBillId()));
		return count;
		
	}
	
	@Override
	public List<CHSSIPDPkgItems> getCHSSIPDPkgItemsList() throws Exception
	{
		return dao.getCHSSIPDPkgItemsList(); 
	}
	
	@Override
	public CHSSOtherPermitAmt getCHSSOtherPermitAmt(String otheritemid,long  basicpay) throws Exception
	{
		return dao.getCHSSOtherPermitAmt(otheritemid, basicpay);
	}

	@Override
	public List<Object[]> CHSSDashboardGraphData(String EmpId, String FromDate, String ToDate) throws Exception 
	{
		return dao.CHSSDashboardGraphData(EmpId, FromDate, ToDate);
	}

	@Override
	public Object[] CHSSDashboardAmountData(String EmpId, String FromDate, String ToDate, String IsSelf) throws Exception 
	{
		return dao.CHSSDashboardAmountData(EmpId, FromDate, ToDate, IsSelf);
	}

	@Override
	public List<Object[]> CHSSDashboardIndividualAmountData(String EmpId, String FromDate, String ToDate) throws Exception 
	{
		return dao.CHSSDashboardIndividualAmountData(EmpId, FromDate, ToDate);
	}
	
	@Override
	public long ContingentClaimDrop(String[] chssapplyids,String Username) throws Exception 
	{
		long count=0;
		for(String applyid :  chssapplyids)
		{
			CHSSApply apply=dao.getCHSSApply(applyid);
			apply.setContingentId(0L);
			apply.setCHSSStatusId(6);
			apply.setModifiedBy(Username);
			apply.setModifiedDate(sdtf.format(new Date()));
			
			count +=dao.CHSSApplyEdit(apply);
		}
		return count;
	}
	
	@Override
	public List<Object[]> GetDoctorEmpanelledList() throws Exception
	{
		return dao.GetDoctorEmpanelledList();
	}
	@Override
	public List<Object[]> GetEmpanelledHostpitalList() throws Exception
	{
		return dao.GetEmpanelledHostpitalList();
	}

	
}

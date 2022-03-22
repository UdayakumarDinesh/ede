package com.vts.ems.chss.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.DateTimeFormatUtil;
import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.Dto.CHSSConsultationDto;
import com.vts.ems.chss.Dto.CHSSMedicineDto;
import com.vts.ems.chss.Dto.CHSSOthersDto;
import com.vts.ems.chss.Dto.CHSSTestsDto;
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSBill;
import com.vts.ems.chss.model.CHSSConsultation;
import com.vts.ems.chss.model.CHSSMedicine;
import com.vts.ems.chss.model.CHSSOthers;
import com.vts.ems.chss.model.CHSSTestMain;
import com.vts.ems.chss.model.CHSSTestSub;
import com.vts.ems.chss.model.CHSSTests;
import com.vts.ems.chss.model.CHSSTreatType;
import com.vts.ems.pis.model.Employee;

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
				apply.setCHSSStatus(1);
				apply.setIsActive(1);
				apply.setCreatedBy(dto.getCreatedBy());
				apply.setCreatedDate(sdtf.format(new Date()));
				apply.setCHSSApplyDate(sdf.format(new Date()));
				
				applyid=dao.CHSSApplyAdd(apply);
				
			}else
			{
				applyid=Long.parseLong(dto.getCHSSApplyId());
			}
			
			
			for(int i=0 ; i<dto.getCenterName().length && applyid>0 ; i++)
			{
				CHSSBill bill = new CHSSBill();
				bill.setCHSSApplyId(applyid);
				bill.setCenterName(dto.getCenterName()[i].trim());
				bill.setBillNo(dto.getBillNo()[i]);
				bill.setBillDate(sdf.format(rdf.parse(dto.getBillDate()[i])));
//				bill.setBillAmount(Integer.parseInt(dto.getBillAmount()[i]));
				bill.setIsActive(1);
				bill.setCreatedBy(dto.getCreatedBy());
				bill.setCreatedDate(sdtf.format(new Date()));				
				applyid = dao.CHSSBillAdd(bill);
			}
						
			return applyid;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE CHSSApplySubmit");
			return 0;
		}
		
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
		return dao.empCHSSList(empid);
	}
	@Override
	public CHSSBill getCHSSBill(String billid) throws Exception
	{
		return dao.getCHSSBill(billid);
	}
	
	
	
	@Override
	public long CHSSBillEdit(CHSSBill bill) throws Exception
	{
		
		CHSSBill fetch = dao.getCHSSBill(String.valueOf(bill.getBillId()));
		
		fetch.setCenterName(bill.getCenterName());
		fetch.setBillNo(bill.getBillNo());
		fetch.setBillDate(bill.getBillDate());
//		fetch.setBillAmount(bill.getBillAmount());
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
		fetch.setNoEnclosures(Integer.parseInt(dto.getNoEnclosures()));
		fetch.setModifiedBy(dto.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
			
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
				
				consult.setIsActive(1);
				consult.setCreatedBy(dto.getCreatedBy());
				consult.setCreatedDate(sdtf.format(new Date()));				
				count = dao.ConsultationBillAdd(consult);
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE ConsultationBillAdd");
			return 0;
		}
		
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
		fetch.setModifiedBy(modal.getModifiedBy());
		fetch.setModifiedDate(sdtf.format(new Date()));
		return dao.ConsultationBillEdit(fetch);
	}
	
	
	@Override
	public long ConsultationBillDelete(String consultationid, String modifiedby ) throws Exception
	{
		CHSSConsultation fetch = dao.getCHSSConsultation(consultationid);
		fetch.setIsActive(0);
		fetch.setModifiedBy(modifiedby);
		fetch.setModifiedDate(sdtf.format(new Date()));
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
				meds.setMedicineDate(sdf.format(rdf.parse(dto.getMedicineDate()[i])));
				meds.setMedicineCost(Integer.parseInt(dto.getMedicineCost()[i]));
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
		fetch.setMedicineDate(modal.getMedicineDate());
		fetch.setMedicineCost(modal.getMedicineCost());
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
			
			for(int i=0 ; i<dto.getTestMainId().length; i++)
			{
				CHSSTests  meds = new CHSSTests();
				
				meds.setBillId(Long.parseLong(dto.getBillId()));
				meds.setTestMainId(Long.parseLong(dto.getTestMainId()[i]));
				meds.setTestSubId(Long.parseLong(dto.getTestSubId()[i]));
				meds.setTestCost(Integer.parseInt(dto.getTestCost()[i]));
				meds.setIsActive(1);
				count = dao.TestsBillAdd(meds);
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE TestsBillAdd");
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSTests> CHSSTestsList(String billid) throws Exception
	{
		return dao.CHSSTestsList(billid);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public long OtherBillAdd(CHSSOthersDto dto) throws Exception
	{
		logger.info(new Date() +"Inside SERVICE OtherBillAdd");		
		try {
			
			long count=0;
			
			for(int i=0 ; i<dto.getOtherItemName().length ; i++)
			{
				CHSSOthers  meds = new CHSSOthers();
				
				meds.setBillId(Long.parseLong(dto.getBillId()));
				meds.setOtherItemName(dto.getOtherItemName()[i]);
				meds.setOtherItemCost(Integer.parseInt(dto.getOtherItemCost()[i]));
				meds.setIsActive(1);
				count = dao.OtherBillAdd(meds);
			}
						
			return count;
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside SERVICE OtherBillAdd");
			return 0;
		}
		
	}
	
	@Override
	public List<CHSSOthers> CHSSOtherList(String billid) throws Exception
	{
		return dao.CHSSOthersList(billid);
	}
	
	@Override
	public long OtherBillEdit(CHSSOthers modal) throws Exception
	{
		CHSSOthers fetch = dao.getCHSSOther(String.valueOf(modal.getChssOthersId()));
		fetch.setOtherItemName(modal.getOtherItemName());
		fetch.setOtherItemCost(modal.getOtherItemCost());
		return dao.OthersBillEdit(fetch);
	}
	
	@Override
	public long OtherBillDelete(String chssotherid, String modifiedby ) throws Exception
	{
		CHSSOthers fetch = dao.getCHSSOther(chssotherid);
		fetch.setIsActive(0);
		return dao.OthersBillEdit(fetch);
	}
	
}

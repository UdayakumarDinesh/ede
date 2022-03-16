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
import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSBill;
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
				bill.setBillAmount(Integer.parseInt(dto.getBillAmount()[i]));
				bill.setIsActive(1);
				bill.setCreatedBy(dto.getCreatedBy());
				bill.setCreatedDate(sdtf.format(new Date()));				
				dao.CHSSBillAdd(bill);
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
		fetch.setBillAmount(bill.getBillAmount());
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
	
}

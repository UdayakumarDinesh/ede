package com.vts.ems.chss.service;

import java.util.List;

import com.vts.ems.chss.Dto.CHSSApplyDto;
import com.vts.ems.chss.model.CHSSApply;
import com.vts.ems.chss.model.CHSSBill;
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
	
	

}

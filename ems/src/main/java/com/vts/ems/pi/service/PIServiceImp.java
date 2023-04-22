package com.vts.ems.pi.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.chss.dao.CHSSDao;
import com.vts.ems.chss.service.CHSSServiceImpl;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.dao.PIDao;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class PIServiceImp implements PIService{

	@Autowired
	PIDao dao;
	
	@Autowired
	CHSSDao chssdao;
	
	private static final Logger logger = LogManager.getLogger(PIService.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Override
	public List<Object[]> ResAddressDetails(String EmpId) throws Exception {
		
		return dao.EmployeeAddressDetails(EmpId);
	}

	@Override
	public Object[] ResAddressFormData(String addressResId) throws Exception {
		
		return dao.ResAddressFormData(addressResId);
	}

	@Override
	public List<Object[]> PermanentAddressDetails(String EmpId) throws Exception {
	
		return dao.PermanentAddressDetails(EmpId);
	}

	@Override
	public AddressPer getPerAddressData(String addressperid) throws Exception {
		
		return dao.getPerAddressData(addressperid);
	}

	@Override
	public Object[] ResToAddressId(String EmpId) throws Exception {

		return dao.ResToAddressId(EmpId);
	}

	@Override
	public long ResUpdatetoDate(Date toDate, String resAddressId) throws Exception {
		
		return dao.ResUpdatetoDate(toDate, resAddressId);
	}

	@Override
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception {
		
		return dao.ResAddressIntimated(resaddressid);
	}

	@Override
	public long ResAddressForward(String resAddressId, String username, String action, String remarks, String ApprEmpNo,String LoginType) throws Exception
	{
		try {
			AddressRes address = dao.ResAddressIntimated(resAddressId);
			Employee emp = dao.getEmpData(address.getEmpid());
			String formempno = emp.getEmpNo();
			String  pisStatusCode = address.getPisStatusCode();
			List<String> DGMs = dao.GetDGMEmpNos();
			List<String> DHs = dao.GetDHEmpNos();
			List<String> GHs = dao.GetGHEmpNos();
			
			String CEO = dao.GetCEOEmpNo();
			String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
			String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
			String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
			DivisionMaster formEmpDivisionMaster = dao.GetDivisionData(emp.getDivisionId());
			String pisStatusCodeNext = address.getPisStatusCodeNext();
			
			if(action.equalsIgnoreCase("A"))
			{
				// first time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI")) 
				{
					address.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno) || LoginType.equalsIgnoreCase("P")) 
					{
						address.setPisStatusCode("APR");
						address.setPisStatusCodeNext("APR");
						address.setIsActive(1);
						address.setResAdStatus("A");				
					}
					else if(DGMs.contains(formempno)) 
					{
						address.setPisStatusCodeNext("VPA");
					}else 
					{
						address.setPisStatusCodeNext("VDG");
					}
					
				}
				//approving	flow 
				else
				{
					address.setPisStatusCode(pisStatusCodeNext);
					if(pisStatusCodeNext.equalsIgnoreCase("VDG")) {
						address.setPisStatusCodeNext("VPA");
					}else if(pisStatusCodeNext.equalsIgnoreCase("VPA")) {
						address.setIsActive(1);
						address.setResAdStatus("A");
					}
				}		
				dao.AddressResEdit(address);
			}
			else if(action.equalsIgnoreCase("R")) 
			{
				if(pisStatusCodeNext.equalsIgnoreCase("VDG")) {
					address.setPisStatusCode("RDG");	
				}else if(pisStatusCodeNext.equalsIgnoreCase("VPA")) {
					address.setPisStatusCode("RPA");	
				}
			}
			return 1;
		}catch (Exception e) {
			logger.error(new Date() +" Inside ResAddressForward "+ e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public Object[] PerAddressFormData(String addressPerId) throws Exception {
		
		return dao.PerAddressFormData(addressPerId);
	}

	@Override
	public Employee getEmpData(String empid) throws Exception {
		return dao.getEmpData(empid);		
	}

	@Override
	public String GetCEOEmpNo() throws Exception {
		return dao.GetCEOEmpNo();		
	}

	@Override
	public List<String> GetDGMEmpNos() throws Exception {
		return dao.GetDGMEmpNos();		
	}

	@Override
	public List<String> GetDHEmpNos() throws Exception {
		return dao.GetDHEmpNos();		
	}

	@Override
	public List<String> GetGHEmpNos() throws Exception {
		return dao.GetGHEmpNos();		
	}

	@Override
	public String GetEmpGHEmpNo(String empno) throws Exception {
		return dao.GetEmpGHEmpNo(empno);		
	}

	@Override
	public String GetEmpDHEmpNo(String empno) throws Exception {
		return dao.GetEmpDHEmpNo(empno);
	}

	@Override
	public String GetEmpDGMEmpNo(String empno) throws Exception {
		return dao.GetEmpDGMEmpNo(empno);
	}
	
	@Override
	public List<Object[]> ResAddressApprovalsList(String EmpNo,String LoginType) throws Exception
	{
		return dao.ResAddressApprovalsList(EmpNo, LoginType);
	}
	@Override
	public DivisionMaster GetDivisionData(long DivisionId) throws Exception
	{
		return dao.GetDivisionData(DivisionId);
	}
}

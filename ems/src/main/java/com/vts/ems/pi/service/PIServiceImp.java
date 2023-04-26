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
import com.vts.ems.pi.model.PisAddressResTrans;
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
	public long ResAddressForward(String resAddressId, String username, String action, String remarks, String ForwardingEmpNo,String LoginType) throws Exception
	{
		try {
			AddressRes address = dao.ResAddressIntimated(resAddressId);
			Employee emp = dao.getEmpData(address.getEmpid());
			String formempno = emp.getEmpNo();
			String  pisStatusCode = address.getPisStatusCode();
			String pisStatusCodeNext = address.getPisStatusCodeNext();
			List<String> DGMs = dao.GetDGMEmpNos();
//			List<String> DHs = dao.GetDHEmpNos();
//			List<String> GHs = dao.GetGHEmpNos();
			List<String> PandAs = dao.GetPandAAdminEmpNos();
			String CEO = dao.GetCEOEmpNo();
			
			DivisionMaster formEmpDivisionMaster = dao.GetDivisionData(emp.getDivisionId());
			
			if(action.equalsIgnoreCase("A"))
			{
				// first time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") ) 
				{
					address.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno) || LoginType.equalsIgnoreCase("P")) 
					{
						address.setPisStatusCode("VPA");
						address.setPisStatusCodeNext("VPA");
						address.setIsActive(1);
						address.setResAdStatus("A");				
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						address.setPisStatusCodeNext("VPA");
					}
					else 
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
				address.setRemarks(remarks);
				dao.AddressResEdit(address);
				
			}
			else if(action.equalsIgnoreCase("R")) 
			{
				if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					address.setPisStatusCode("RDG");	
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VPA")) 
				{
					address.setPisStatusCode("RPA");	
				}
				
				
				if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					address.setPisStatusCodeNext("VPA");
				}
				else 
				{
					address.setPisStatusCodeNext("VDG");
				}
				address.setRemarks(remarks);
				dao.AddressResEdit(address);
			}
			
			PisAddressResTrans transaction = PisAddressResTrans.builder()	
												.address_res_id(address.getAddress_res_id())
												.PisStatusCode(address.getPisStatusCode())
												.ActionBy(ForwardingEmpNo)
												.Remarks(remarks)
												.ActionDate(sdtf.format(new Date()))
												.build();
			dao.AddressResTransactionAdd(transaction);
			
			
			String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
//			String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
//			String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
			
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && address.getResAdStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("PersonalIntimation.htm");
				notification.setNotificationMessage("Residential Address Change Request Approved");
				notification.setNotificationBy(ForwardingEmpNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( address.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if(address.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
				{
					notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
				}
				notification.setNotificationUrl("AddressApprovals.htm");
				notification.setNotificationMessage("Recieved Residential Address Change Request From "+emp.getEmpName());
				notification.setNotificationBy(ForwardingEmpNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("PersonalIntimation.htm");
				notification.setNotificationMessage("Residential Address Change Request Returned");
				notification.setNotificationBy(ForwardingEmpNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
		
			dao.AddNotifications(notification);		
			
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
	
	@Override
	public List<Object[]> ResAddressTransactionList(String addressresid)throws Exception
	{
		return dao.ResAddressTransactionList(addressresid);
	}
	
	@Override
	public List<Object[]> ResAddressTransactionApprovalData(String addressresid)throws Exception
	{
		return dao.ResAddressTransactionApprovalData(addressresid);
	}
	
	@Override
	public Object[] GetEmpDGMEmpName(String empno) throws Exception
	{
		return dao.GetEmpDGMEmpName(empno);
	}
	@Override
	public Object[] GetPandAEmpName() throws Exception
	{
		return dao.GetPandAEmpName();
	}
}

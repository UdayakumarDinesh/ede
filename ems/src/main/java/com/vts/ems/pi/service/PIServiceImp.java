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
import com.vts.ems.pi.model.PisAddressPerTrans;
import com.vts.ems.pi.model.PisAddressResTrans;
import com.vts.ems.pi.model.PisHometown;
import com.vts.ems.pi.model.PisMobileNumber;
import com.vts.ems.pi.model.PisMobileNumberTrans;
import com.vts.ems.pis.dao.PisDao;
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
	private PisDao pisdao;
	
	private static final Logger logger = LogManager.getLogger(PIService.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	
	@Override
	public List<Object[]> ResAddressDetails(String EmpId) throws Exception {
		
		return dao.ResAddressDetails(EmpId);
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
						pisdao.ResAddrUpdate(address.getEmpid());
						Object[] resToAddressId = dao.ResToAddressId(address.getEmpid());
						String fromDate = DateTimeFormatUtil.SqlToRegularDate(address.getFrom_res_addr().toString());
						if(resToAddressId!=null) {						
							dao.ResUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromDate) ,resToAddressId[0].toString());								

						}
						
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
						pisdao.ResAddrUpdate(address.getEmpid());
						Object[] resToAddressId = dao.ResToAddressId(address.getEmpid());
						String fromDate = DateTimeFormatUtil.SqlToRegularDate(address.getFrom_res_addr().toString());
						if(resToAddressId!=null) {						
							dao.ResUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromDate) ,resToAddressId[0].toString());								

						}
						
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
				notification.setNotificationMessage("Recieved Residential Address Change Request From <br>"+emp.getEmpName());
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
	public Employee getEmpData(String EmpId) throws Exception {
		return dao.getEmpData(EmpId);		
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

	@Override
	public List<Object[]> PerAddressTransactionApprovalData(String peraddressId) throws Exception{
		
		return dao.PerAddressTransactionApprovalData(peraddressId);
	}

	@Override
	public AddressPer PerAddressIntimated(String peraddressid) throws Exception {
		
		return dao.PerAddressIntimated(peraddressid);
	}

	@Override
	public long PerAddressForward(String perAddressId, String username, String action, String remarks, String ForwardingEmpNo,String LoginType) throws Exception {
		try {
			AddressPer address = dao.PerAddressIntimated(perAddressId);
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
//				if( (pisStatusCode.equalsIgnoreCase("VDG") && pisStatusCodeNext.equalsIgnoreCase("VPA") ) || 
//				     (pisStatusCode.equalsIgnoreCase("FWD") && pisStatusCodeNext.equalsIgnoreCase("VPA") ) ){
//					
//				}
				// first time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") ) 
				{
					address.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno) || LoginType.equalsIgnoreCase("P")) 
					{
						pisdao.PerAddrUpdate(address.getEmpid());
	                    Object[] perToAddressId = dao.PerToAddressId(address.getEmpid());
	                    String fromDate = DateTimeFormatUtil.SqlToRegularDate(address.getFrom_per_addr().toString());
	                    
	                    if(perToAddressId!=null) {
	                    
	    					dao.PerUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromDate) ,perToAddressId[0].toString() );
	                    }
	              
						address.setPisStatusCode("VPA");
						address.setPisStatusCodeNext("VPA");
						address.setIsActive(1);
						address.setPerAdStatus("A");				
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
						pisdao.PerAddrUpdate(address.getEmpid());
	                    Object[] perToAddressId = dao.PerToAddressId(address.getEmpid());
	                    String fromDate = DateTimeFormatUtil.SqlToRegularDate(address.getFrom_per_addr().toString());
	                    
	                    if(perToAddressId!=null) {
	                    
	    					dao.PerUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromDate) ,perToAddressId[0].toString() );
	                    }
						address.setIsActive(1);
						address.setPerAdStatus("A");
					}
				}		
				address.setRemarks(remarks);
				dao.AddressPerEdit(address);
				
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
				dao.AddressPerEdit(address);
			}
			
			PisAddressPerTrans transaction = PisAddressPerTrans.builder()	
												.address_per_id(address.getAddress_per_id())
												.PisStatusCode(address.getPisStatusCode())
												.ActionBy(ForwardingEmpNo)
												.Remarks(remarks)
												.ActionDate(sdtf.format(new Date()))
												.build();
			dao.AddressPerTransactionAdd(transaction);
			
			
			String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
//			String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
//			String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
			
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && address.getPerAdStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("PersonalIntimation.htm");
				notification.setNotificationMessage("Permanent Address Change Request Approved");
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
					notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0).toString():null);
				}
				notification.setNotificationUrl("AddressApprovals.htm");
				notification.setNotificationMessage("Recieved Permanent Address Change Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(ForwardingEmpNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("PersonalIntimation.htm");
				notification.setNotificationMessage("Permanent Address Change Request Returned");
				notification.setNotificationBy(ForwardingEmpNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
		
			dao.AddNotifications(notification);		
			
			return 1;
		}catch (Exception e) {
			logger.error(new Date() +" Inside PerAddressForward "+ e);
			e.printStackTrace();
			return 0;
		}		
	}

	@Override
	public Object[] PerToAddressId(String empId) throws Exception {
		
		return dao.PerToAddressId(empId);
	}

	@Override
	public long PerUpdatetoDate(Date toDate, String perAddressId) throws Exception {
		
	    return dao.PerUpdatetoDate(toDate, perAddressId);
	}
	
	@Override
	public List<Object[]> PerAddressTransactionList(String addressperid)throws Exception
	{
		return dao.PerAddressTransactionList(addressperid);
	}

	@Override
	public List<Object[]> MobileNumberDetails(String EmpNo) throws Exception {
		
		return dao.MobileNumberDetails(EmpNo);
	}

	@Override
	public Long addMobileNumber(PisMobileNumber mobile) throws Exception {
		
		return dao.addMobileNumber(mobile);
	}

	@Override
	public PisMobileNumber getMobileNumberData(String mobileNumberId) throws Exception {
		
		return dao.getMobileNumberData(mobileNumberId);
	}

	@Override
	public long EditMobileNumber(PisMobileNumber mobile) throws Exception {
		PisMobileNumber mob = dao.getMobileDataById(mobile.getMobileNumberId());
		
		mob.setMobileNumber(mobile.getMobileNumber());
		mob.setAltMobileNumber(mobile.getAltMobileNumber());
		mob.setMobileFrom(mobile.getMobileFrom());
		mob.setModifiedBy(mobile.getModifiedBy());
		mob.setModifiedDate(mobile.getModifiedDate());
		return dao.EditMobileNumber(mob);
	}

	@Override
	public List<Object[]> MobileTransactionList(String mobilenumberid) throws Exception {
		
		return dao.MobileTransactionList(mobilenumberid);
	}

	@Override
	public List<Object[]> MobileTransactionApprovalData(String mobileNumberId) throws Exception {
		
		return dao.MobileTransactionApprovalData(mobileNumberId);
	}

	@Override
	public Object[] MobileFormData(String mobileNumberId) throws Exception {
		
		return dao.MobileFormData(mobileNumberId);
	}

	@Override
	public List<Object[]> MobileApprovalsList(String EmpNo, String LoginType) throws Exception {
		
		return dao.MobileApprovalsList(EmpNo, LoginType);
	}

	@Override
	public PisMobileNumber getMobileDataById(long mobilenumberid) throws Exception {
		
		return dao.getMobileDataById(mobilenumberid);
	}

	@Override
	public long MobileNumberForward(String mobileNumberId, String username, String action, String remarks,String ApprEmpNo, String LoginType) throws Exception {
		
		try {
			
			PisMobileNumber mobile = dao.getMobileDataById(Long.parseLong(mobileNumberId) );
			Employee emp = dao.getEmpDataByEmpNo(mobile.getEmpNo());
			String formempno = emp.getEmpNo();
			String  pisStatusCode = mobile.getPisStatusCode();
			String pisStatusCodeNext = mobile.getPisStatusCodeNext();
			List<String> DGMs = dao.GetDGMEmpNos();
//			List<String> DHs = dao.GetDHEmpNos();
//			List<String> GHs = dao.GetGHEmpNos();
			List<String> PandAs = dao.GetPandAAdminEmpNos();
			String CEO = dao.GetCEOEmpNo();
			
			DivisionMaster formEmpDivisionMaster = dao.GetDivisionData(emp.getDivisionId());
			
			if(action.equalsIgnoreCase("A"))
			{
//			
				// first time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RPA") ) 
				{
					mobile.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno) || LoginType.equalsIgnoreCase("P")) 
					{
					    
					    dao.MobileStatusUpdate(mobile.getEmpNo());
					    Object[] mobileNumber = dao.GetMobileNumberId(mobile.getEmpNo());
	              
	                    String fromDate = DateTimeFormatUtil.SqlToRegularDate(mobile.getMobileFrom().toString());
	                    
	                    if(mobileNumber!=null) {
	                    
	                    	dao.MobileNumberUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromDate), mobileNumber[0].toString());	    					
	                    }
	                    
						mobile.setPisStatusCode("VPA");
						mobile.setPisStatusCodeNext("VPA");
						mobile.setIsActive(1);
						mobile.setMobileStatus("A");				
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						mobile.setPisStatusCodeNext("VPA");
					}
					else 
					{
						mobile.setPisStatusCodeNext("VDG");
					}					
				}
				//approving	flow 
				else
				{
				   
					mobile.setPisStatusCode(pisStatusCodeNext);
					
					if(pisStatusCodeNext.equalsIgnoreCase("VDG")) {
						mobile.setPisStatusCodeNext("VPA");
					}else if(pisStatusCodeNext.equalsIgnoreCase("VPA")) {
						
						 dao.MobileStatusUpdate(mobile.getEmpNo());
						    Object[] mobileNumber = dao.GetMobileNumberId(mobile.getEmpNo());
				              
		                    String fromDate = DateTimeFormatUtil.SqlToRegularDate(mobile.getMobileFrom().toString());
		                    
		                    if(mobileNumber!=null) {
		                    
		                    	dao.MobileNumberUpdatetoDate(DateTimeFormatUtil.getMinusOneDay(fromDate), mobileNumber[0].toString());	    					
		                    }
		                    
						mobile.setIsActive(1);
						mobile.setMobileStatus("A");
					}
				}		
				mobile.setRemarks(remarks);
				dao.EditMobileNumber(mobile);
						
			}
			else if(action.equalsIgnoreCase("R")) 
			{
				if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					mobile.setPisStatusCode("RDG");	
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VPA")) 
				{
					mobile.setPisStatusCode("RPA");	
				}
				
				
				if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					mobile.setPisStatusCodeNext("VPA");
				}
				else 
				{
					mobile.setPisStatusCodeNext("VDG");
				}
				mobile.setRemarks(remarks);
				dao.EditMobileNumber(mobile);
			}
			
			PisMobileNumberTrans transaction = PisMobileNumberTrans.builder()
					                           .MobileNumberId(mobile.getMobileNumberId())
					                           .PisStatusCode(mobile.getPisStatusCode())
					                           .ActionBy(ApprEmpNo)
					                           .Remarks(remarks)
					                           .ActionDate(sdtf.format(new Date()))
					                           .build();
			
			dao.MobileNumberTransactionAdd(transaction);
							
			String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
//			String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
//			String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
			
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && mobile.getMobileStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("PIHomeTownMobile.htm");
				notification.setNotificationMessage("Mobile Number Change Request Approved");
				notification.setNotificationBy(ApprEmpNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( mobile.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if(mobile.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
				{
					notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
				}
				notification.setNotificationUrl("MobileApprovals.htm");
				notification.setNotificationMessage("Recieved Mobile Number Change Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(ApprEmpNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("PIHomeTownMobile.htm");
				notification.setNotificationMessage("Mobile Number Change Request Returned");
				notification.setNotificationBy(ApprEmpNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
		
			dao.AddNotifications(notification);		
			
			return 1;
		}catch (Exception e) {
			logger.error(new Date() +" Inside MobileNumberForward "+ e);
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public long UpdateEmployeeMobileNumber(String MobileNumber, String AltMobileNumber, String EmpNo) throws Exception {
		
		return dao.UpdateEmployeeMobileNumber(MobileNumber, AltMobileNumber, EmpNo);
	}

	@Override
	public Long addHometown(PisHometown hometown) throws Exception {
		
		return dao.addHometown(hometown);
	}

	@Override
	public Long EditHometown(PisHometown hometown) throws Exception {
		PisHometown home = dao.getHometownById(hometown.getHometownId());
		
		home.setHometown(hometown.getHometown());
		home.setNearestRailwayStation(hometown.getNearestRailwayStation());
		home.setState(hometown.getState());
		home.setModifiedBy(hometown.getModifiedBy());
		home.setModifiedDate(hometown.getModifiedDate());
		
		return dao.EditHometown(home);
	}

	@Override
	public PisHometown getHometownById(long hometownId) throws Exception {
		
		return dao.getHometownById(hometownId);
	}

	@Override
	public List<Object[]> HometownDetails(String EmpNo) throws Exception {
		
		return dao.HometownDetails(EmpNo);
	}

	@Override
	public Object[] GetGroupHeadName(String EmpNo) throws Exception {
		
		return dao.GetGroupHeadName(EmpNo);
	}

	@Override
	public Object[] GetDivisionHeadName(String EmpNo) throws Exception {
		
		return dao.GetDivisionHeadName(EmpNo);
	}

	@Override
	public Object[] GetCeoName() throws Exception {
		
		return dao.GetCeoName();
	}
}

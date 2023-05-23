package com.vts.ems.property.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.dao.PIDao;
import com.vts.ems.pis.dao.PisDao;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.property.dao.PropertyDao;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.model.PisMovableProperty;
import com.vts.ems.property.model.PisMovablePropertyTrans;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class PropertyServiceImp implements PropertyService{

	@Autowired
	PropertyDao dao;

	@Autowired
	PIDao pidao;
	
	@Autowired
	private PisDao pisdao;
	
	private static final Logger logger = LogManager.getLogger(PropertyServiceImp.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Override
	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception {
		
		return dao.ImmPropDetails(EmpNo);
	}

	@Override
	public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception {
		
		return dao.getImmovablePropertyById(ImmPropertyId);
	}

	@Override
	public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		return dao.addImmovableProperty(immovable);
	}

	@Override
	public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		PisImmovableProperty imm = dao.getImmovablePropertyById(immovable.getImmPropertyId());
		
		imm.setPurpose(immovable.getPurpose());
		imm.setTransState(immovable.getTransState());
		imm.setTransDate(immovable.getTransDate());		
		imm.setMode(immovable.getMode());
		imm.setLocation(immovable.getLocation());
		imm.setDistrict(immovable.getDistrict());
		imm.setState(immovable.getState());
		imm.setPincode(immovable.getPincode());
		imm.setOwnership(immovable.getOwnership());
		imm.setDescription(immovable.getDescription());
		imm.setApplicantInterest(immovable.getApplicantInterest());
		imm.setPartialInterest(immovable.getPartialInterest());
		imm.setOsParticulars(immovable.getOsParticulars());
		imm.setOsShare(immovable.getOsShare());
		imm.setPrice(immovable.getPrice());
		imm.setFinanceSource(immovable.getFinanceSource());
		imm.setRequisiteSanction(immovable.getRequisiteSanction());
		imm.setPartyName(immovable.getPartyName());
		imm.setPartyAddress(immovable.getPartyAddress());
		imm.setTransArrangement(immovable.getTransArrangement());
		imm.setPartyRelated(immovable.getPartyRelated());
		imm.setRelationship(immovable.getRelationship());
		imm.setFutureDealings(immovable.getFutureDealings());
		imm.setDealingNature(immovable.getDealingNature());
		imm.setSanctionRequired(immovable.getSanctionRequired());
		imm.setRelavantFact(immovable.getRelavantFact());
		imm.setModifiedBy(immovable.getModifiedBy());
		imm.setModifiedDate(immovable.getModifiedDate());
		
		return dao.editImmovableProperty(imm);
	}

	@Override
	public Long addImmovablePropertyTransaction(PisImmovablePropertyTrans transaction) throws Exception {
		
		return dao.addImmovablePropertyTransaction(transaction);
	}

	@Override
	public List<Object[]> immmovablePropertyTransList(String immPropertyId) throws Exception {
		
		return dao.immmovablePropertyTransList(immPropertyId);
	}

	@Override
	public Object[] getEmpNameDesig(String EmpNo) throws Exception {
		
		return dao.getEmpNameDesig(EmpNo);
	}

	@Override
	public List<Object[]> immPropTransactionApprovalData(String ImmPropertyId) throws Exception {
		
		return dao.immPropTransactionApprovalData(ImmPropertyId);
	}

	@Override
	public Long immovablePropForward(String immPropertyId, String username, String action, String remarks,String apprEmpNo, String loginType) throws Exception {
		
		try {
			PisImmovableProperty immovable = dao.getImmovablePropertyById(Long.parseLong(immPropertyId));
			Employee emp = pidao.getEmpDataByEmpNo(immovable.getEmpNo());
			String formempno = emp.getEmpNo();
			String pisStatusCode = immovable.getPisStatusCode();
			String pisStatusCodeNext = immovable.getPisStatusCodeNext();
			
			List<String> PandAs = pidao.GetPandAAdminEmpNos();
			String CEO = pidao.GetCEOEmpNo();
			List<String> DGMs = pidao.GetDGMEmpNos();
			
			DivisionMaster formEmpDivisionMaster = pidao.GetDivisionData(emp.getDivisionId());
			
			if(action.equalsIgnoreCase("A"))
			{
				// First time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || 
				   pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE") )
				{
					immovable.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno)) 
					{
						immovable.setPisStatusCode("APR");
						immovable.setPisStatusCodeNext("APR");					
						immovable.setIsActive(1);
						immovable.setImmStatus("A");
					}
					else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
					{
						immovable.setPisStatusCodeNext("APR");
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						immovable.setPisStatusCodeNext("VPA");
					}
					else
					{
						immovable.setPisStatusCodeNext("VDG");
					}
				}
				
				//Approving Flow
				else
				{
					immovable.setPisStatusCode(pisStatusCodeNext);					
					if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
					{
						immovable.setPisStatusCodeNext("VPA");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
					{
						immovable.setPisStatusCodeNext("APR");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("APR"))
					{
						immovable.setIsActive(1);
						immovable.setImmStatus("A");
					}
					
				}
				immovable.setRemarks(remarks);
				dao.editImmovableProperty(immovable);								
			}
			
			//Returned
			else if(action.equalsIgnoreCase("R"))
			{
				// Setting PisStatusCode
				if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					immovable.setPisStatusCode("RDG");	
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
				{
					immovable.setPisStatusCode("RPA");
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("APR")) 
				{
					immovable.setPisStatusCode("RCE");	
				}
				
				//Setting PisStatusCodeNext
				if(CEO.equalsIgnoreCase(formempno) || PandAs.contains(formempno) || loginType.equalsIgnoreCase("P") ) 
				{ 
					immovable.setPisStatusCodeNext("APR");					
				}		
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					immovable.setPisStatusCodeNext("VPA");
				}else
				{
					immovable.setPisStatusCodeNext("VDG");
				}
				immovable.setRemarks(remarks);
				dao.editImmovableProperty(immovable);
			}
			
			// Transaction
			PisImmovablePropertyTrans transaction = PisImmovablePropertyTrans.builder()
					                                .ImmPropertyId(immovable.getImmPropertyId())
					                                .PisStatusCode(immovable.getPisStatusCode())					                                
					                                .ActionBy(apprEmpNo)
					                                .Remarks(remarks)
					                                .ActionDate(sdtf.format(new Date()))
					                                .build();
			dao.addImmovablePropertyTransaction(transaction);
			
			String DGMEmpNo = pidao.GetEmpDGMEmpNo(formempno);
			
			//Notification
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && immovable.getImmStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Immovable Property Request Approved");
				notification.setNotificationBy(apprEmpNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( immovable.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if(immovable.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
				{
					notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
				}
				else if( immovable.getPisStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);					
				}
				
				notification.setNotificationUrl("PropertyApprovals.htm");
				notification.setNotificationMessage("Recieved Immovable Property Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(apprEmpNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Immovable Property Request Returned");
				notification.setNotificationBy(apprEmpNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
		
			pidao.AddNotifications(notification);	
			
			return 1L;
		}catch (Exception e) {
			logger.info(new Date()+"Inside immovablePropForward"+username,e);
			e.printStackTrace();
			return 0L;
		}
		
	}

	@Override
	public List<Object[]> propertyApprovalList(String EmpNo) throws Exception {
		
		return dao.propertyApprovalList(EmpNo);
	}

	@Override
	public List<Object[]> movPropDetails(String empNo) throws Exception {
		
		return dao.movPropDetails(empNo);
	}

	@Override
	public PisMovableProperty getMovablePropertyById(Long movPropertyId) throws Exception {
		
		return dao.getMovablePropertyById(movPropertyId);
	}

	@Override
	public Long addMovableProperty(PisMovableProperty movable) throws Exception {
		
		return dao.addMovableProperty(movable);
	}

	@Override
	public Long editMovableProperty(PisMovableProperty movable) throws Exception {
		
		return dao.editMovableProperty(movable);
	}

	@Override
	public List<Object[]> movablePropertyTransList(String movPropertyId) throws Exception {
		
		return dao.immmovablePropertyTransList(movPropertyId);
	}

	@Override
	public List<Object[]> movPropTransactionApprovalData(String movPropertyId) {
		
		return dao.movPropTransactionApprovalData(movPropertyId);
	}

	@Override
	public Long movablePropForward(String movPropertyId, String username, String action, String remarks,String apprEmpNo, String loginType) throws Exception {
		
		try {
			PisMovableProperty movable = dao.getMovablePropertyById(Long.parseLong(movPropertyId.trim()));
			Employee emp = pidao.getEmpDataByEmpNo(movable.getEmpNo());
			String formempno = emp.getEmpNo();
			String pisStatusCode = movable.getPisStatusCode();
			String pisStatusCodeNext = movable.getPisStatusCodeNext();
			
			List<String> PandAs = pidao.GetPandAAdminEmpNos();
			String CEO = pidao.GetCEOEmpNo();
			
			if(action.equalsIgnoreCase("A"))
			{
				// First time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE") )
				{
					movable.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno)) 
					{
						movable.setPisStatusCode("APR");
						movable.setPisStatusCodeNext("APR");					
						movable.setIsActive(1);
						movable.setMovStatus("A");
					}
					else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
					{
						movable.setPisStatusCodeNext("APR");
					}
					else
					{
						movable.setPisStatusCodeNext("VPA");
					}
				}
				
				//Approving Flow
				else
				{
					movable.setPisStatusCode(pisStatusCodeNext);					
					if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
					{
						movable.setPisStatusCodeNext("APR");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("APR"))
					{
						movable.setIsActive(1);
						movable.setMovStatus("A");
					}
					
				}
				movable.setRemarks(remarks);
				dao.editMovableProperty(movable);								
			}
			
			//Returned
			else if(action.equalsIgnoreCase("R"))
			{
				// Setting PisStatusCode
				if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
				{
					movable.setPisStatusCode("RPA");
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("APR")) 
				{
					movable.setPisStatusCode("RCE");	
				}
				
				//Setting PisStatusCodeNext
				if(CEO.equalsIgnoreCase(formempno) || PandAs.contains(formempno) || loginType.equalsIgnoreCase("P") ) 
				{ 
					movable.setPisStatusCodeNext("APR");					
				}		
				else
				{
					movable.setPisStatusCodeNext("VPA");
				}
				movable.setRemarks(remarks);
				dao.editMovableProperty(movable);
			}
			
			// Transaction		
			PisMovablePropertyTrans transaction = PisMovablePropertyTrans.builder()
					                              .MovPropertyId(movable.getMovPropertyId())
					                              .PisStatusCode(movable.getPisStatusCode())
					                              .ActionBy(apprEmpNo)
					                              .Remarks(remarks)
					                              .ActionDate(sdtf.format(new Date()))
					                              .build();
			dao.addMovablePropertyTransaction(transaction);
			
			//Notification
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && movable.getMovStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Movable Property Request Approved");
				notification.setNotificationBy(apprEmpNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if(movable.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
				{
					notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
				}
				else if( movable.getPisStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);					
				}
				
				notification.setNotificationUrl("PropertyApprovals.htm");
				notification.setNotificationMessage("Recieved Movable Property Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(apprEmpNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Movable Property Request Returned");
				notification.setNotificationBy(apprEmpNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
		
			pidao.AddNotifications(notification);	
			
			return 1L;
		}catch (Exception e) {
			logger.info(new Date()+"Inside movablePropForward"+username,e);
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public List<Object[]> immPropertyRemarksHistory(String ImmPropertyId) throws Exception {
		
		return dao.immPropertyRemarksHistory(ImmPropertyId);
	}

	@Override
	public List<Object[]> propertyApprovedList(String EmpNo, String FromDate, String ToDate) throws Exception {
		
		return dao.propertyApprovedList(EmpNo, FromDate, ToDate);
	}
}

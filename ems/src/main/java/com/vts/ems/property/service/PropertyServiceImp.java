package com.vts.ems.property.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.master.model.LabMaster;
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
import com.vts.ems.property.model.PisPropertyConstruction;
import com.vts.ems.property.model.PisPropertyConstructionTrans;
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
		imm.setExtentInNameOf(immovable.getExtentInNameOf());
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
			
			String CEO = pidao.GetCEOEmpNo();
			List<String> PandAs = pidao.GetPandAAdminEmpNos();
            List<String> SOs = pidao.GetSOEmpNos();
			List<String> DGMs = pidao.GetDGMEmpNos();
			
			DivisionMaster formEmpDivisionMaster = pidao.GetDivisionData(emp.getDivisionId());
			
			if(action.equalsIgnoreCase("A"))
			{
				// First time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG") || pisStatusCode.equalsIgnoreCase("RSO") || 
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
					else if(SOs.contains(formempno) ) 
					{
						immovable.setPisStatusCodeNext("VPA");
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						immovable.setPisStatusCodeNext("VSO");
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
						immovable.setPisStatusCodeNext("VSO");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("VSO"))
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
				else if(pisStatusCodeNext.equalsIgnoreCase("VSO"))
				{
					immovable.setPisStatusCode("RSO");
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
				else if(SOs.contains(formempno) ) 
				{
					immovable.setPisStatusCodeNext("VPA");
				}
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					immovable.setPisStatusCodeNext("VSO");
				}
				else
				{
					immovable.setPisStatusCodeNext("VDG");
				}
				immovable.setRemarks(remarks);
				dao.editImmovableProperty(immovable);
			}
			// Disapproved By CEO
			else if(action.equalsIgnoreCase("D")) {
				
				immovable.setPisStatusCode("DPR");
				immovable.setPisStatusCodeNext("DPR");					
				immovable.setIsActive(1);
				immovable.setImmStatus("D");	
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
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( immovable.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if( immovable.getPisStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);					
				}
				
				notification.setNotificationUrl("PropertyApprovals.htm");
				notification.setNotificationMessage("Recieved Immovable Property Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Immovable Property Request Returned");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("D"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Immovable Property Request Disapproved");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
								
			if(action.equalsIgnoreCase("A") )
			{
				 if(immovable.getPisStatusCodeNext().equalsIgnoreCase("VSO")) 
					{
					 if(SOs.size()>0) {
					  for(String SOEMpNo : SOs) {
						  EMSNotification notification1 = new EMSNotification();
						  notification1.setEmpNo(SOEMpNo);
						  notification1.setNotificationUrl("PropertyApprovals.htm");
						  notification1.setNotificationMessage("Recieved Immovable Property Request From <br>"+emp.getEmpName());
						  notification1.setNotificationBy(apprEmpNo);
						  notification1.setNotificationDate(LocalDate.now().toString());
						  notification1.setIsActive(1);
						  notification1.setCreatedBy(username);
						  notification1.setCreatedDate(sdtf.format(new Date()));
									
						  pidao.AddNotifications(notification1);  
					 
					   }
					  }	    
										
					}
				   if(immovable.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
					{
					   if(PandAs.size()>0) {
					   for(String PandAEmpNo : PandAs) {
						   EMSNotification notification1 = new EMSNotification();
						   notification1.setEmpNo(PandAEmpNo);
						   notification1.setNotificationUrl("PropertyApprovals.htm");
						   notification1.setNotificationMessage("Recieved Immovable Property Request From <br>"+emp.getEmpName());
						   notification1.setNotificationBy(apprEmpNo);
						   notification1.setNotificationDate(LocalDate.now().toString());
						   notification1.setIsActive(1);
						   notification1.setCreatedBy(username);
						   notification1.setCreatedDate(sdtf.format(new Date()));
									
							pidao.AddNotifications(notification1);  
					      }
					   }
				     }
			}	
			
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
		
       PisMovableProperty mov = dao.getMovablePropertyById(movable.getMovPropertyId());
		
		mov.setPurpose(movable.getPurpose());
		mov.setTransState(movable.getTransState());
		mov.setTransDate(movable.getTransDate());		
		mov.setDescription(movable.getDescription());
		mov.setMakeAndModel(movable.getMakeAndModel());
		mov.setMode(movable.getMode());	
		mov.setPrice(movable.getPrice());
		mov.setFinanceSource(movable.getFinanceSource());
		mov.setRequisiteSanction(movable.getRequisiteSanction());
		mov.setPartyName(movable.getPartyName());
		mov.setPartyAddress(movable.getPartyAddress());
		mov.setTransArrangement(movable.getTransArrangement());
		mov.setPartyRelated(movable.getPartyRelated());
		mov.setRelationship(movable.getRelationship());
		mov.setFutureDealings(movable.getFutureDealings());
		mov.setDealingNature(movable.getDealingNature());
		mov.setSanctionRequired(movable.getSanctionRequired());
		mov.setRelavantFacts(movable.getRelavantFacts());
		mov.setModifiedBy(movable.getModifiedBy());
		mov.setModifiedDate(movable.getModifiedDate());
		
		return dao.editMovableProperty(mov);
	}

	@Override
	public List<Object[]> movablePropertyTransList(String movPropertyId) throws Exception {
		
		return dao.movablePropertyTransList(movPropertyId);
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
			
			String CEO = pidao.GetCEOEmpNo();
			List<String> PandAs = pidao.GetPandAAdminEmpNos();
			List<String> SOs = pidao.GetSOEmpNos();
            List<String> DGMs = pidao.GetDGMEmpNos();
			
			DivisionMaster formEmpDivisionMaster = pidao.GetDivisionData(emp.getDivisionId());
			
			if(action.equalsIgnoreCase("A"))
			{
				// First time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG")|| pisStatusCode.equalsIgnoreCase("RSO")||
				   pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE") )
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
					else if(SOs.contains(formempno) ) 
					{
						movable.setPisStatusCodeNext("VPA");
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						movable.setPisStatusCodeNext("VSO");
					}
					else
					{
						movable.setPisStatusCodeNext("VDG");
					}
				}
				
				//Approving Flow
				else
				{
					movable.setPisStatusCode(pisStatusCodeNext);					
					if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
					{
						movable.setPisStatusCodeNext("VSO");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("VSO"))
					{
						movable.setPisStatusCodeNext("VPA");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
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
				if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					movable.setPisStatusCode("RDG");	
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VSO"))
				{
					movable.setPisStatusCode("RSO");
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
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
				else if(SOs.contains(formempno) ) 
				{
					movable.setPisStatusCodeNext("VPA");
				}
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					movable.setPisStatusCodeNext("VSO");
				}
				else
				{
					movable.setPisStatusCodeNext("VDG");
				}
				movable.setRemarks(remarks);
				dao.editMovableProperty(movable);
			}
			// Disapproved By CEO
						else if(action.equalsIgnoreCase("D")) {
							
							movable.setPisStatusCode("DPR");
							movable.setPisStatusCodeNext("DPR");					
							movable.setIsActive(1);
							movable.setMovStatus("D");	
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
			
			String DGMEmpNo = pidao.GetEmpDGMEmpNo(formempno);
			
			//Notification
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && movable.getMovStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Movable Property Request Approved");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( movable.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if( movable.getPisStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);					
				}
				
				notification.setNotificationUrl("PropertyApprovals.htm");
				notification.setNotificationMessage("Recieved Movable Property Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Movable Property Request Returned");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("D"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("AcquiringDisposing.htm");
				notification.setNotificationMessage("Movable Property Request Disapproved");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
											
			if(action.equalsIgnoreCase("A") )
			{
				 if(movable.getPisStatusCodeNext().equalsIgnoreCase("VSO")) 
					{
					 if(SOs.size()>0) {
					  for(String SOEMpNo : SOs) {
						  EMSNotification notification1 = new EMSNotification();
						  notification1.setEmpNo(SOEMpNo);
						  notification1.setNotificationUrl("PropertyApprovals.htm");
						  notification1.setNotificationMessage("Recieved Movable Property Request From <br>"+emp.getEmpName());
						  notification1.setNotificationBy(apprEmpNo);
						  notification1.setNotificationDate(LocalDate.now().toString());
						  notification1.setIsActive(1);
						  notification1.setCreatedBy(username);
						  notification1.setCreatedDate(sdtf.format(new Date()));
									
						  pidao.AddNotifications(notification1);  
					 
					   }
					  }	    
										
					}
				   if(movable.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
					{
					   if(PandAs.size()>0) {
					   for(String PandAEmpNo : PandAs) {
						   EMSNotification notification1 = new EMSNotification();
						   notification1.setEmpNo(PandAEmpNo);
						   notification1.setNotificationUrl("PropertyApprovals.htm");
						   notification1.setNotificationMessage("Recieved Movable Property Request From <br>"+emp.getEmpName());
						   notification1.setNotificationBy(apprEmpNo);
						   notification1.setNotificationDate(LocalDate.now().toString());
						   notification1.setIsActive(1);
						   notification1.setCreatedBy(username);
						   notification1.setCreatedDate(sdtf.format(new Date()));
									
							pidao.AddNotifications(notification1);  
					      }
					   }
				     }
			}		
			
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

	@Override
	public Long addMovablePropertyTransaction(PisMovablePropertyTrans transaction) throws Exception {
		
		return dao.addMovablePropertyTransaction(transaction);
	}

	@Override
	public List<Object[]> movPropertyRemarksHistory(String movPropertyId) throws Exception {
		
		return dao.movPropertyRemarksHistory(movPropertyId);
	}

	@Override
	public List<Object[]> constructionRenovationDetails(String EmpNo) throws Exception {
		
		return dao.constructionRenovationDetails(EmpNo);
	}

	@Override
	public PisPropertyConstruction getConstructionById(Long ConstructionId) throws Exception {
		
		return dao.getConstructionById(ConstructionId);
	}

	@Override
	public Long addPropertyConstruction(PisPropertyConstruction construction) throws Exception {
		
		return dao.addPropertyConstruction(construction);
	}

	@Override
	public Long editPropertyConstruction(PisPropertyConstruction construction) throws Exception {
		
		PisPropertyConstruction con = dao.getConstructionById(construction.getConstructionId());
		con.setTransactionState(construction.getTransactionState());
		con.setEstimatedCost(construction.getEstimatedCost());
		con.setConstrCompletedBy(construction.getConstrCompletedBy());
		con.setSupervisedBy(construction.getSupervisedBy());
		con.setContractorDealings(construction.getContractorDealings());
		con.setNatureOfDealings(construction.getNatureOfDealings());
		con.setContractorName(construction.getContractorName());
		con.setContractorPlace(construction.getContractorPlace());
		con.setOwnSavings(construction.getOwnSavings());
		con.setLoans(construction.getLoans());
		con.setOtherSources(construction.getOtherSources());
		con.setProposedCost(construction.getProposedCost());
		con.setOwnSavingsDetails(construction.getOwnSavingsDetails());
		con.setLoansDetails(construction.getLoansDetails());
		con.setOtherSourcesDetails(construction.getOtherSourcesDetails());
		con.setComments(construction.getComments());
		con.setModifiedBy(construction.getModifiedBy());
		con.setModifiedDate(construction.getModifiedDate());
		return dao.editPropertyConstruction(con);
	}

	@Override
	public Long addPropertyConstructionTransaction(PisPropertyConstructionTrans transaction) throws Exception {
		
		return dao.addPropertyConstructionTransaction(transaction);
	}

	@Override
	public List<Object[]> constructionTransList(String ConstructionId) throws Exception {
		
		return dao.constructionTransList(ConstructionId);
	}

	@Override
	public List<Object[]> constructionTransactionApprovalData(String ConstructionId) {
		
		return dao.constructionTransactionApprovalData(ConstructionId);
	}

	@Override
	public List<Object[]> constructionRemarksHistory(String ConstructionId) throws Exception {
	
		return dao.constructionRemarksHistory(ConstructionId);
	}

	@Override
	public long constructionForward(String constructionId, String username, String action, String remarks, String apprEmpNo,String loginType) throws Exception 
	{
		
		try {
			PisPropertyConstruction construction = dao.getConstructionById(Long.parseLong(constructionId));	
			Employee emp = pidao.getEmpDataByEmpNo(construction.getEmpNo());
			String formempno = emp.getEmpNo();
			String pisStatusCode = construction.getPisStatusCode();
			String pisStatusCodeNext = construction.getPisStatusCodeNext();
			String transactionState = construction.getTransactionState();
			if(transactionState.equalsIgnoreCase("C")) {
				transactionState="Construction of house";
			}else if(transactionState.equalsIgnoreCase("A")) {
				transactionState="Addition of exisiting house";
			}else {
				transactionState="Renovation of exisiting house";
			}
			String CEO = pidao.GetCEOEmpNo();
			List<String> PandAs = pidao.GetPandAAdminEmpNos();
			List<String> SOs = pidao.GetSOEmpNos();
            List<String> DGMs = pidao.GetDGMEmpNos();
			
			DivisionMaster formEmpDivisionMaster = pidao.GetDivisionData(emp.getDivisionId());
			
			if(action.equalsIgnoreCase("A"))
			{
				// First time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RDG")|| pisStatusCode.equalsIgnoreCase("RSO")||
				   pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE") )
				{
					construction.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno)) 
					{
						construction.setPisStatusCode("APR");
						construction.setPisStatusCodeNext("APR");					
						construction.setIsActive(1);
						construction.setConstrStatus("A");
					}
					else if(PandAs.contains(formempno)) 
					{
						construction.setPisStatusCodeNext("APR");
					}
					else if(SOs.contains(formempno) ) 
					{
						construction.setPisStatusCodeNext("VPA");
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						construction.setPisStatusCodeNext("VSO");
					}
					else
					{
						construction.setPisStatusCodeNext("VDG");
					}
				}
				
				//Approving Flow
				else
				{
					construction.setPisStatusCode(pisStatusCodeNext);					
					if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
					{
						construction.setPisStatusCodeNext("VSO");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("VSO"))
					{
						construction.setPisStatusCodeNext("VPA");
						construction.setSOEmpNo(apprEmpNo);
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
					{
						construction.setPisStatusCodeNext("APR");
						construction.setPandAEmpNo(apprEmpNo);
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("APR"))
					{
						construction.setIsActive(1);
						construction.setConstrStatus("A");
						
						LocalDate today= LocalDate.now();
//						String year="";
//						year=String.valueOf(today.getYear()).substring(2, 4);
											
						long constructionid = dao.getMaxConstructionId();
						String letterNo="";
						letterNo="STARC/P&A/PERS-"+constructionid+"/"+today.getYear();
						construction.setLetterNo(letterNo);
						construction.setLetterDate(sdf.format(new Date()));
					}
					
				}
				construction.setRemarks(remarks);
				dao.editPropertyConstruction(construction);								
			}
			
			//Returned
			else if(action.equalsIgnoreCase("R"))
			{
				// Setting PisStatusCode
				if(pisStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					construction.setPisStatusCode("RDG");	
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VSO"))
				{
					construction.setPisStatusCode("RSO");
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("VPA"))
				{
					construction.setPisStatusCode("RPA");
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("APR")) 
				{
					construction.setPisStatusCode("RCE");	
				}
				
				//Setting PisStatusCodeNext
				if(CEO.equalsIgnoreCase(formempno) || PandAs.contains(formempno) || loginType.equalsIgnoreCase("P") ) 
				{ 
					construction.setPisStatusCodeNext("APR");					
				}
				else if(SOs.contains(formempno) ) 
				{
					construction.setPisStatusCodeNext("VPA");
				}
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					construction.setPisStatusCodeNext("VSO");
				}
				else
				{
					construction.setPisStatusCodeNext("VDG");
				}
				construction.setRemarks(remarks);
				dao.editPropertyConstruction(construction);
			}
			// Disapproved By CEO
			else if(action.equalsIgnoreCase("D")) {
							
				construction.setPisStatusCode("DPR");
				construction.setPisStatusCodeNext("DPR");					
				construction.setIsActive(1);
				construction.setConstrStatus("D");	
				construction.setRemarks(remarks);
				dao.editPropertyConstruction(construction);	
			}
			
			// Transaction		
			PisPropertyConstructionTrans transaction = PisPropertyConstructionTrans.builder()
					                              .ConstructionId(construction.getConstructionId())
					                              .PisStatusCode(construction.getPisStatusCode())
					                              .ActionBy(apprEmpNo)
					                              .Remarks(remarks)
					                              .ActionDate(sdtf.format(new Date()))
					                              .build();
			dao.addPropertyConstructionTransaction(transaction);
			
			String DGMEmpNo = pidao.GetEmpDGMEmpNo(formempno);
			
			//Notification
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && construction.getConstrStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("ConstructionRenovation.htm");
				notification.setNotificationMessage(transactionState+" Request Approved");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
				
				if(PandAs.size()>0) {
					   for(String PandAEmpNo : PandAs) {
						   EMSNotification notification1 = new EMSNotification();
						   notification1.setEmpNo(PandAEmpNo);
						   notification1.setNotificationUrl("PropertyApprovals.htm");
						   notification1.setNotificationMessage(transactionState+" Request Approved for <br>"+emp.getEmpName());
						   notification1.setNotificationBy(apprEmpNo);
						   notification1.setNotificationDate(LocalDate.now().toString());
						   notification1.setIsActive(1);
						   notification1.setCreatedBy(username);
						   notification1.setCreatedDate(sdtf.format(new Date()));
									
							pidao.AddNotifications(notification1);  
					      }
					   }
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( construction.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if( construction.getPisStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);					
				}
				
				notification.setNotificationUrl("PropertyApprovals.htm");
				notification.setNotificationMessage("Recieved "+transactionState+" Request<br>From "+emp.getEmpName());
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("ConstructionRenovation.htm");
				notification.setNotificationMessage(transactionState+" Request Returned");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
			else if(action.equalsIgnoreCase("D"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("ConstructionRenovation.htm");
				notification.setNotificationMessage(transactionState+" Request Disapproved");
				notification.setNotificationBy(apprEmpNo);
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
			
				pidao.AddNotifications(notification);
			}
											
			if(action.equalsIgnoreCase("A") )
			{
				 if(construction.getPisStatusCodeNext().equalsIgnoreCase("VSO")) 
					{
					 if(SOs.size()>0) {
					  for(String SOEMpNo : SOs) {
						  EMSNotification notification1 = new EMSNotification();
						  notification1.setEmpNo(SOEMpNo);
						  notification1.setNotificationUrl("PropertyApprovals.htm");
						  notification1.setNotificationMessage("Recieved "+transactionState+" Request<br> From "+emp.getEmpName());
						  notification1.setNotificationBy(apprEmpNo);
						  notification1.setNotificationDate(LocalDate.now().toString());
						  notification1.setIsActive(1);
						  notification1.setCreatedBy(username);
						  notification1.setCreatedDate(sdtf.format(new Date()));
									
						  pidao.AddNotifications(notification1);  
					 
					   }
					  }	    
										
					}
				   if(construction.getPisStatusCodeNext().equalsIgnoreCase("VPA")) 
					{
					   if(PandAs.size()>0) {
					   for(String PandAEmpNo : PandAs) {
						   EMSNotification notification1 = new EMSNotification();
						   notification1.setEmpNo(PandAEmpNo);
						   notification1.setNotificationUrl("PropertyApprovals.htm");
						   notification1.setNotificationMessage("Recieved "+transactionState+" Request<br> From "+emp.getEmpName());
						   notification1.setNotificationBy(apprEmpNo);
						   notification1.setNotificationDate(LocalDate.now().toString());
						   notification1.setIsActive(1);
						   notification1.setCreatedBy(username);
						   notification1.setCreatedDate(sdtf.format(new Date()));
									
							pidao.AddNotifications(notification1);  
					      }
					   }
				     }
			}		
			
			return 1L;
		}catch (Exception e) {
			logger.info(new Date()+"Inside constructionForward"+username,e);
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public List<Object[]> propertyConstructionApprovalList(String EmpNo) throws Exception {
		
		return dao.propertyConstructionApprovalList(EmpNo);
	}

	@Override
	public List<LabMaster> getLabMasterDetails() throws Exception {
		
		return dao.getLabMasterDetails();
	}
}

package com.vts.ems.athithi.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.athithi.dao.IntimationDao;
import com.vts.ems.athithi.dto.NewIntimation;
import com.vts.ems.athithi.model.Company;
import com.vts.ems.athithi.model.CompanyEmployee;
import com.vts.ems.athithi.model.Intimation;
import com.vts.ems.athithi.model.IntimationEmp;
import com.vts.ems.athithi.model.VpIntimationTrans;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.pi.dao.PIDao;
import com.vts.ems.pis.dao.PisDao;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.service.PropertyServiceImp;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class IntimationServiceImpl implements IntimationService {

//	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private  SimpleDateFormat sdf4=new SimpleDateFormat("dd-MMM-yyyy");
	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf3 = new SimpleDateFormat("hh:mm:ss");
	
	Calendar now = Calendar.getInstance();
	
	
	@Autowired
	IntimationDao dao;
	
	@Autowired
	PIDao pidao;
	
	@Autowired
	private PisDao pisdao;
	
	private static final Logger logger = LogManager.getLogger(IntimationServiceImpl.class);
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Override
	public List<Object[]> getCompnyList(String trem) throws Exception {
		
		return dao.getCompnyList(trem);
	}

	@Override
	public List<Object[]> addNewCompany(Company company) throws Exception {
	
		return dao.addNewCompany(company);
	}
	
	@Override
	public List<Object[]> getCompEmp(String companyId)throws Exception{
		
		return dao.getCompEmp(companyId);
	}
	public Long addNewEmployee(CompanyEmployee newEmp)throws Exception{
		
		return dao.addNewEmployee(newEmp);
	}
	
	public List<Object[]> getOfficerList(String groupId)throws Exception{
		return dao.getOfficerList(groupId);
	}

	@Override
	public Long addNewIntimation(NewIntimation intimation) throws Exception {
	     	java.util.Date a= new  java.util.Date(); 
		    String todayDate = sdf.format(a.getTime());
		
	        Intimation inti = new Intimation();
	        inti.setIntimationByEmpNo(intimation.getIntimationByEmpNo());
	        inti.setIntimationDate(new java.sql.Date(sdf.parse(todayDate).getTime()));
	        inti.setCompanyId( Long.parseLong(intimation.getCompnayId()));
	        inti.setFromDate(DateTimeFormatUtil.dateConversionSql(intimation.getFdate()));
	        inti.setToDate(DateTimeFormatUtil.dateConversionSql(intimation.getTdate()));
	        inti.setDuration(intimation.getDuration());
	        inti.setExpectedTime(intimation.getExpectedTime());
	        inti.setPurpose(intimation.getPurpose());
	        inti.setSpecialPermission(intimation.getSpermission());
	        inti.setOfficerEmpId(intimation.getOfficer());
	        inti.setVpStatus(intimation.getVpStatus());
	        inti.setRemarks("");
	        inti.setPisStatusCode(intimation.getPisStatusCode());
	        inti.setPisStatusCodeNext(intimation.getPisStatusCodeNext());
	        inti.setCreatedBy(intimation.getCreateBy());
	        inti.setCreatedDate(sdf1.format(new  java.util.Date()));
		    inti.setIsActive(1);
	        inti.setIntimationNo(genIntimationNo());
		    Long newIntimationId=dao.addNewIntimation(inti);
        		    
		    String[] visitors= intimation.getVisitors();
		    
		    for(String visitor:visitors) {
		    	IntimationEmp newVisitor =new IntimationEmp();
		    	newVisitor.setCompanyEmpId(Long.parseLong(visitor));
		    	newVisitor.setInitmationId(newIntimationId);
		    	newVisitor.setCreatedBy(intimation.getCreateBy());
		    	newVisitor.setCreatedDate(sdf1.format(new  java.util.Date())); 
		    	newVisitor.setIsActive(1);
		    	dao.addVisitor(newVisitor);
		    }
			return newIntimationId;
	}

	

	private String genIntimationNo() throws Exception {
		java.util.Date a= new  java.util.Date(); 
	    String todayDate = sdf.format(a.getTime());
		Date ScheduledDate= (new java.sql.Date(sdf.parse(todayDate).getTime()));
	    String number= sdf4.format(ScheduledDate).toString().toUpperCase().replace("-", "")+"/";
	    
		int IntimationCount=dao.todayIntionCount();
         if(IntimationCount==0) {
        	 number=number+1;
         }else {
        	 number=number+ ++IntimationCount; 
         }
		
		return number;
	}
	
	@Override
	public List<Object[]> getItimationList(String EmpNo)throws Exception{
		
		return dao.getItimationList(EmpNo);
	}

	@Override
	public Long addVpIntimationTrans(VpIntimationTrans transaction) throws Exception {
		
		return dao.addVpIntimationTrans(transaction);
	}

	@Override
	public Intimation getIntimationById(Long intimationId) throws Exception {
		
		return dao.getIntimationById(intimationId);
	}

	@Override
	public Long vpIntimationForward(String intimationId, String username, String action, String remarks, String empNo,String loginType) {
		
		try {
			Intimation intimation = dao.getIntimationById(Long.parseLong(intimationId));
			Employee emp = pidao.getEmpDataByEmpNo(intimation.getIntimationByEmpNo());
			String formempno = emp.getEmpNo();
			String pisStatusCode = intimation.getPisStatusCode();
			String pisStatusCodeNext = intimation.getPisStatusCodeNext();
				
			String CEO = pidao.GetCEOEmpNo();
			List<String> DGMs = pidao.GetDGMEmpNos();
			DivisionMaster formEmpDivisionMaster = pidao.GetDivisionData(emp.getDivisionId());
			if(action.equalsIgnoreCase("A"))
			{
				// First time forwarding
				if(pisStatusCode.equalsIgnoreCase("INI") || pisStatusCode.equalsIgnoreCase("RPA") || pisStatusCode.equalsIgnoreCase("RCE") )
				{
					intimation.setPisStatusCode("FWD");
					if(CEO.equalsIgnoreCase(formempno)) 
					{
						intimation.setPisStatusCode("APR");
						intimation.setPisStatusCodeNext("APR");					
						intimation.setIsActive(1);
						intimation.setVpStatus("A");
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						intimation.setPisStatusCodeNext("APR");
					}
					else
					{
						intimation.setPisStatusCodeNext("VDG");
					}
				}
				
				//Approving Flow
				else
				{
					intimation.setPisStatusCode(pisStatusCodeNext);					
					if(pisStatusCodeNext.equalsIgnoreCase("VDG"))
					{
						intimation.setPisStatusCodeNext("APR");
					}
					else if(pisStatusCodeNext.equalsIgnoreCase("APR"))
					{
						intimation.setIsActive(1);
						intimation.setVpStatus("A");
					}
					
				}
				intimation.setRemarks(remarks);
				dao.editNewIntimation(intimation);								
			}
			
			//Returned
			else if(action.equalsIgnoreCase("R"))
			{
				// Setting PisStatusCode
				if(pisStatusCodeNext.equalsIgnoreCase("VDG"))
				{
					intimation.setPisStatusCode("RDG");
				}
				else if(pisStatusCodeNext.equalsIgnoreCase("APR")) 
				{
					intimation.setPisStatusCode("RCE");	
				}
				
				//Setting PisStatusCodeNext
				if(CEO.equalsIgnoreCase(formempno) || (DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) ) 
				{ 
					intimation.setPisStatusCodeNext("APR");					
				}				
				else
				{
					intimation.setPisStatusCodeNext("VDG");
				}
				intimation.setRemarks(remarks);
				dao.editNewIntimation(intimation);
			}
		
			// Transaction
			VpIntimationTrans transaction = VpIntimationTrans.builder()
                                            .IntimationId(intimation.getIntimationId())
                                            .PisStatusCode(intimation.getPisStatusCode())
                                            .Remarks(remarks)
                                            .ActionBy(empNo)
                                            .ActionDate(sdtf.format(new java.util.Date()) ).build();
             dao.addVpIntimationTrans(transaction);
			
             String DGMEmpNo = pidao.GetEmpDGMEmpNo(formempno);
             String RecEmpNo = dao.GetReceptionistEmpNo();
             
			//Notification
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && intimation.getVpStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(RecEmpNo);
				notification.setNotificationUrl("pendingIntimations.htm");
				notification.setNotificationMessage("Visitor Pass Request Approved for<br>"+emp.getEmpName());
				notification.setNotificationBy(empNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				 if( intimation.getPisStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if( intimation.getPisStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);					
				}
				
				notification.setNotificationUrl("VpApprovals.htm");
				notification.setNotificationMessage("Visitor Pass Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(empNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("IntimationList.htm");
				notification.setNotificationMessage("Visitor Pass Request Returned");
				notification.setNotificationBy(empNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new java.util.Date()));
		
			pidao.AddNotifications(notification);	
			
			return 1L;
		}catch (Exception e) {
			logger.info(new java.util.Date()+"Inside immovablePropForward"+username,e);
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public List<Object[]> visitorPassApprovalList(String EmpNo) throws Exception {
		
		return dao.visitorPassApprovalList(EmpNo);
	}

	@Override
	public List<Object[]> vpTransactionList(String IntimationId) throws Exception {
		 
		return dao.vpTransactionList(IntimationId);
	}
	

}

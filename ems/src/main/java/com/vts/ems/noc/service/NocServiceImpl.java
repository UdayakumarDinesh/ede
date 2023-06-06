package com.vts.ems.noc.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.noc.dao.NocDao;
import com.vts.ems.noc.model.ExamIntimation;
import com.vts.ems.noc.model.ExamIntimationDto;
import com.vts.ems.noc.model.ExamIntimationTrans;
import com.vts.ems.noc.model.NocHigherEducation;
import com.vts.ems.noc.model.NocHigherEducationDto;
import com.vts.ems.noc.model.NocHigherEducationTrans;
import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportDto;
import com.vts.ems.noc.model.NocPassportTrans;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadDto;
import com.vts.ems.noc.model.NocProceedingAbroadTrans;
import com.vts.ems.pi.model.PisAddressPerTrans;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.DivisionMaster;
import com.vts.ems.pis.model.Employee;
import com.vts.ems.pis.model.Passport;
import com.vts.ems.utils.DateTimeFormatUtil;

@Service
public class NocServiceImpl implements NocService {

	private static final Logger logger = LogManager.getLogger(NocService.class);
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	
	@Value("${EMSFilesPath}")
    private String FilePath;
	
	@Autowired
	NocDao dao;
	
	public double CropTo2Decimal(String Amount)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE CropTo2Decimal ");
		double Invalue = Double.parseDouble(Amount) ;
		return RoundTo2Decimal(Invalue);
	}
	
	public double RoundTo2Decimal(double Invalue)throws Exception
	{
		logger.info(new Date() +"Inside SERVICE RoundTo2Decimal ");
		BigDecimal returnVal= new BigDecimal(Invalue).setScale(2, BigDecimal.ROUND_HALF_UP);
		return returnVal.doubleValue();
	}
	
	@Override
	 public Object[] getNocEmpList(String EmpId) throws Exception {
		
		return dao.getNocEmpList(EmpId);
	}

	@Override
	public Object[] getEmpPassportData(String empId) throws Exception {
		
		return dao.getEmpPassportData(empId);
	}

	@Override
	public long NocPassportAdd(NocPassportDto dto,String UserId,Passport pport) throws Exception {
		
		LocalDate today= LocalDate.now();
		String year="";
		year=String.valueOf(today.getYear()).substring(2, 4);
		
		
		long nocpassportid = dao.MaxOfNocPassportId()+1;
		String NocPassportNo="";
		NocPassportNo="NOC/P-"+year+"/"+nocpassportid;
		
		
		NocPassportTrans transaction = NocPassportTrans.builder()	
				.NocPassportId(nocpassportid)
				.NocStatusCode("INI")
				.ActionBy(dto.getEmpNo())
				.ActionDate(sdtf.format(new Date()))
				.build();
		
          dao.NocPassportTransactionAdd(transaction);
          long count = dao.GetPassportCount(pport.getEmpId());
          if(count==0 && !pport.getPassportNo().isEmpty()) {
        	  
        	  dao.AddPassport(pport);
          }
          
        
		NocPassport noc =NocPassport.builder()
				.NocPassportNo(NocPassportNo)
				.EmpNo(dto.getEmpNo())
				.PassportExist(dto.getPassportExist())
				.RelationType(dto.getRelationType())
				.RelationName(dto.getRelationName())
				.RelationAddress(dto.getRelationAddress())
				.RelationOccupation(dto.getRelationOccupation())
				.RelationAbroad(dto.getRelationAbroad())
				.EmployementDetails(dto.getEmployementDetails())
				.LostPassport(dto.getLostPassport())
				.PassportType(dto.getPassportType())
				.ContractualObligation(dto.getContractualObligation())
				.FromDate(dto.getFromDate())
				.ToDate(dto.getToDate())
				.PassportStatus("N")
				.NocStatusCode("INI")
				.NocStatusCodeNext("INI")
				.InitiatedDate(sdf1.format(new Date()))
				.CreatedBy(UserId)
				.CreatedDate(sdf1.format(new Date()))
				.isActive(1)
				.build();
		
		return dao.NocPassportAdd(noc);
	}

	@Override
	public List<Object[]> getnocPassportList(String empNo) throws Exception {
		
		return dao.getnocPassportList(empNo);
	}

	@Override
	public NocPassport getNocPassportId(long NocPassportId) throws Exception {
		
		return dao.getNocPassportId(NocPassportId);
	}

	@Override
	public long NOCPassportUpdate(NocPassportDto dto, String userId,Passport pport) throws Exception {
		
		long count = dao.GetPassportCount(pport.getEmpId());
        if(count==0 && !pport.getPassportNo().isEmpty()) {
      	  
      	  dao.AddPassport(pport);
        }
		
		
		
		NocPassport noc =dao.getNocPassportId(dto.getNocPassportId());
		
		noc.setPassportExist(dto.getPassportExist());
		noc.setRelationType(dto.getRelationType());
		noc.setRelationName(dto.getRelationName());
		noc.setRelationAddress(dto.getRelationAddress());
		noc.setRelationOccupation(dto.getRelationOccupation());
		noc.setRelationAbroad(dto.getRelationAbroad());
		noc.setEmployementDetails(dto.getEmployementDetails());
		noc.setLostPassport(dto.getLostPassport());
		noc.setPassportType(dto.getPassportType());
		noc.setContractualObligation(dto.getContractualObligation());
		noc.setFromDate(dto.getFromDate());
		noc.setToDate(dto.getToDate());
		noc.setPassportStatus("I");
		noc.setNocStatusCode("INI");
		noc.setModifiedBy(userId);
		noc.setModifiedDate(sdf1.format(new Date()));
				
				
		return dao.NOCPassportUpdate(noc);
	}

	@Override
	public Object[] getPassportFormDetails(String passportid) throws Exception {
		
		return dao.getPassportFormDetails(passportid);
	}

	@Override
	public List<Object[]> NOCPassportTransactionList(String passportid) throws Exception {
		
		return dao.NOCPassportTransactionList(passportid);

  }
    
	@Override
	public String GetCEOEmpNo() throws Exception {
		return dao.GetCEOEmpNo();		
	}

	@Override
	public List<String> GetPandAAdminEmpNos() throws Exception {
		
		return dao.GetPandAAdminEmpNos();
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
	public Object[] GetCeoName() throws Exception {
		
		return dao.GetCeoName();
	}
	
	@Override
	public Object[] GetPandAEmpName() throws Exception
	{
		return dao.GetPandAEmpName();
	}
	
	@Override
	public Object[] GetEmpDGMEmpName(String empno) throws Exception
	{
		return dao.GetEmpDGMEmpName(empno);
	}
	

	@Override
	public Object[] GetDivisionHeadName(String EmpNo) throws Exception {
		
		return dao.GetDivisionHeadName(EmpNo);
	}
	
	@Override
	public Object[] GetGroupHeadName(String EmpNo) throws Exception {
		
		return dao.GetGroupHeadName(EmpNo);
	}

	@Override
	public Employee getEmpData(String EmpId) throws Exception {
		return dao.getEmpData(EmpId);		
	}
	
	@Override
	public long NOCPassportForward(String passportid, String userId,String action,String remarks,String EmpNo,String loginType) throws Exception {
		
	try {
		
			
		NocPassport noc =dao.getNocPassportId(Long.parseLong(passportid));
		
		Employee emp = dao.getEmpDataByEmpNo(noc.getEmpNo());
		String formempno = emp.getEmpNo();
		String  NocStatusCode = noc.getNocStatusCode();
		String NocStatusCodeNext = noc.getNocStatusCodeNext();
		
		String CEO = dao.GetCEOEmpNo();
		List<String> PandAs = dao.GetPandAAdminEmpNos();
		List<String> DGMs = dao.GetDGMEmpNos();
		List<String> DHs = dao.GetDHEmpNos();
		List<String> GHs = dao.GetGHEmpNos();
		
		DivisionMaster formEmpDivisionMaster = dao.GetDivisionData(emp.getDivisionId());
		
		// Accepted
		if(action.equalsIgnoreCase("A"))
		{
//		
			// first time forwarding
			if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
					NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE") ) 
			{
				noc.setNocStatusCode("FWD");
				noc.setForwardedDate(sdf1.format(new Date()));
				if(CEO.equalsIgnoreCase(formempno) ) 
				{ 
				    //dao.HometownStatusUpdate(noc.getEmpNo());

					noc.setNocStatusCode("APR");
					noc.setNocStatusCodeNext("APR");
					noc.setIsActive(1);
					noc.setPassportStatus("A");	
					noc.setApprovedDate(sdf1.format(new Date()));
				}
				
				else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
				{
					noc.setNocStatusCodeNext("APR");
				}
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					noc.setNocStatusCodeNext("VPA");
				}
				else if(DHs.contains(formempno) || emp.getDivisionId()==0)
				{
					noc.setNocStatusCodeNext("VDG");
				}
				else if(GHs.contains(formempno) || emp.getGroupId()==0)
				{
					noc.setNocStatusCodeNext("VDI");
				}
				else 
				{
					noc.setNocStatusCodeNext("VGI");
				}
				
			}
			
			//approving	flow 
			else
			{
			   
				noc.setNocStatusCode(NocStatusCodeNext);
				
				if(NocStatusCodeNext.equalsIgnoreCase("VGI"))
				{
					noc.setNocStatusCodeNext("VDI");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VDI")) 
				{
					noc.setNocStatusCodeNext("VDG");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					noc.setNocStatusCodeNext("VPA");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VPA")) 
				{
					noc.setNocStatusCodeNext("APR");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("APR")) {	
					//dao.HometownStatusUpdate(noc.getEmpNo());
					noc.setIsActive(1);
					noc.setPassportStatus("A");
					noc.setApprovedDate(sdf1.format(new Date()));
				}
			}
			
			noc.setRemarks(remarks);
			dao.EditNoc(noc);
					
		}
		
		//Returned
		else if(action.equalsIgnoreCase("R")) 
		{
			// Setting PisStatusCode
			if(NocStatusCodeNext.equalsIgnoreCase("VGI")) 
			{
				noc.setNocStatusCode(NocStatusCodeNext);
			}
		    else if(NocStatusCodeNext.equalsIgnoreCase("VDI")) 
			{
		    	noc.setNocStatusCode("RDI");	
			}
			else if(NocStatusCodeNext.equalsIgnoreCase("VDG")) 
			{
				noc.setNocStatusCode("RDG");	
			}
			else if(NocStatusCodeNext.equalsIgnoreCase("VPA")) 
			{
				noc.setNocStatusCode("RPA");	
			}
			else if(NocStatusCodeNext.equalsIgnoreCase("APR")) 
			{
				noc.setNocStatusCode("RCE");	
			}
			
			//Setting PisStatusCodeNext
			if(CEO.equalsIgnoreCase(formempno) ) 
			{ 
				noc.setNocStatusCodeNext("APR");					
			}
			else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
			{
				noc.setNocStatusCodeNext("APR");
			}
			else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
			{
				noc.setNocStatusCodeNext("VPA");
			}
			else if(DHs.contains(formempno) || emp.getDivisionId()==0)
			{
				noc.setNocStatusCodeNext("VDG");
			}
			else if(GHs.contains(formempno)  || emp.getGroupId()==0)
			{
				noc.setNocStatusCodeNext("VDI");
			}
			else 
			{
				noc.setNocStatusCodeNext("VGI");
			}
			noc.setReturnedDate(sdf1.format(new Date()));
			noc.setRemarks(remarks);
			dao.EditNoc(noc);
		}
		
		NocPassportTrans transaction = NocPassportTrans.builder()	
				                           .NocPassportId(noc.getNocPassportId())
				                           .NocStatusCode(noc.getNocStatusCode())
				                           .ActionBy(EmpNo)
				                           .Remarks(remarks)
				                           .ActionDate(sdtf.format(new Date()))
				                           .build();
		
		 dao.NocPassportTransactionAdd(transaction);
						
		    String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
			String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
			String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
	
		//Notification
			EMSNotification notification1 = new EMSNotification();
			if(action.equalsIgnoreCase("A") && noc.getPassportStatus().equalsIgnoreCase("A"))
			{
				notification1.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
				notification1.setNotificationUrl("NocApproval.htm?tab=closed");
				notification1.setNotificationMessage("NOC Passpaort Request Approved For <br>"+emp.getEmpName());
				notification1.setNotificationBy(EmpNo);
				
				notification1.setNotificationDate(LocalDate.now().toString());
				notification1.setIsActive(1);
				notification1.setCreatedBy(userId);
				notification1.setCreatedDate(sdtf.format(new Date()));
			
				dao.AddNotifications(notification1);		
				
			}
			
			
		EMSNotification notification = new EMSNotification();
		if(action.equalsIgnoreCase("A") && noc.getPassportStatus().equalsIgnoreCase("A"))
		{
			notification.setEmpNo(emp.getEmpNo());
			notification.setNotificationUrl("Passport.htm");
			notification.setNotificationMessage("NOC Passpaort Request Approved");
			notification.setNotificationBy(EmpNo);
		}
		
		
		else if(action.equalsIgnoreCase("A") )
		{
			if( noc.getNocStatusCodeNext().equalsIgnoreCase("VGI")) 
			{
				notification.setEmpNo(GIEmpNo);					
			}
			else if( noc.getNocStatusCodeNext().equalsIgnoreCase("VDI")) 
			{
				notification.setEmpNo(DIEmpNo);					
			}
			else if( noc.getNocStatusCodeNext().equalsIgnoreCase("VDG")) 
			{
				notification.setEmpNo(DGMEmpNo);					
			}
			else if(noc.getNocStatusCodeNext().equalsIgnoreCase("VPA")) 
			{
				notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
			}
			else if(noc.getNocStatusCodeNext().equalsIgnoreCase("APR")) 
			{
				notification.setEmpNo(CEO);
			}
			
			notification.setNotificationUrl("NocApproval.htm");
			notification.setNotificationMessage("Recieved NOC Passport Change Request From <br>"+emp.getEmpName());
			notification.setNotificationBy(EmpNo);
		}
		else if(action.equalsIgnoreCase("R"))
		{
			notification.setEmpNo(emp.getEmpNo());
			notification.setNotificationUrl("Passport.htm");
			notification.setNotificationMessage("NOC Passport Request Returned");
			notification.setNotificationBy(EmpNo);
		}
		
		notification.setNotificationDate(LocalDate.now().toString());
		notification.setIsActive(1);
		notification.setCreatedBy(userId);
		notification.setCreatedDate(sdtf.format(new Date()));
	
		dao.AddNotifications(notification);		
		
		return 1;
	}catch (Exception e) {
		logger.error(new Date() +" Inside NOCPassportForward "+ e);
		e.printStackTrace();
		return 0;
	}
}

	@Override
	public NocPassport getNocPassportById(long passportid) throws Exception {
		
		return dao.getNocPassportId(passportid);
	}

	@Override
	public List<Object[]> NocApprovalsList(String empNo) throws Exception {
		
		return dao.NocApprovalsList(empNo);
	}

	@Override
	public long PandAFromUpdate(NocPassportDto dto, String userId) throws Exception {
		
		NocPassport noc =dao.getNocPassportId(dto.getNocPassportId());
		
		noc.setPassportEntries(dto.getPassportEntries());
		noc.setPassportEntriesDetails(dto.getPassportEntriesDetails());
		noc.setEmployeeSuspensed(dto.getEmployeeSuspensed());
		noc.setEmployeeInvolvement(dto.getEmployeeInvolvement());
		noc.setEmployeeCaseDetails(dto.getEmployeeCaseDetails());
		noc.setContractualObligation(dto.getContractualObligation());
		noc.setFromDate(dto.getFromDate());
		noc.setToDate(dto.getToDate());
		noc.setPandAModifiedBy(userId);
		noc.setPassportStatus("N");
		noc.setPandAModifiedDate(sdf1.format(new Date()));
		return dao.PandAFromUpdate(noc);
	}

	@Override
	public long NocProcAbroadAdd(NocProceedingAbroadDto dto, String userId) throws Exception {
		
		
		LocalDate today= LocalDate.now();
		String year="";
		year=String.valueOf(today.getYear()).substring(2, 4);
		long maxNocProcId = dao.getMaxOfProcAbroadId()+1;
		
		String NocProcAbroadNo="";
		NocProcAbroadNo="NOC/PA-"+year+"/"+maxNocProcId;
		
		NocProceedingAbroadTrans transaction = NocProceedingAbroadTrans.builder()	
				.NocProcId(maxNocProcId)
				.NocStatusCode("INI")
				.ActionBy(dto.getEmpNo())
				.ActionDate(sdtf.format(new Date()))
				.build();
		
		 dao.NocProcAbroadTransactionAdd(transaction);
		
		NocProceedingAbroad nocpa=NocProceedingAbroad.builder()
				
				        .EmpNo(dto.getEmpNo())
				      
				        .NocProcAbroadNo(NocProcAbroadNo)
				        .RelationType(dto.getRelationType())
				        .RelationName(dto.getRelationName())
				        .RelationAddress(dto.getRelationAddress())
				        .RelationOccupation(dto.getRelationOccupation())
				        .RelationAbroad(dto.getRelationAbroad())
				        .EmployementDetails(dto.getEmployementDetails())
				        .EmployeeInvolvement(dto.getEmployeeInvolvement())
				        .PropertyFiled(dto.getPropertyFiled())
				        .ForeignVisit(dto.getForeignVisit())
				        .ForeignVisitDetails(dto.getForeignVisitDetails())
				        .CountriesProposed(dto.getCountriesProposed())
				        .DepartureDate(dto.getDepartureDate())
				        .VisitPurpose(dto.getVisitPurpose())
				        .StayDuration(dto.getStayDuration())
				        .ReturnDate(dto.getReturnDate())
				        .Going(dto.getGoing())
				        .FamilyDetails(dto.getFamilyDetails())
				        .ExpectedAmount(CropTo2Decimal(dto.getExpectedAmount()))
				        .FinancedBy(dto.getFinancedBy())
				        .AmountSource(dto.getAmountSource())
				        .Name(dto.getName())
				        .Nationality(dto.getNationality())
				        .Relationship(dto.getRelationship())
				        .RelationshipAddress(dto.getRelationshipAddress())
				        .LostPassport(dto.getLostPassport())
				        .ContractualObligation(dto.getContractualObligation())
				        .FromDate(dto.getFromDate())
				        .ToDate(dto.getToDate())
				        .Hospatility(dto.getHospatility())
				        .ProcAbroadStatus("N")
				        .NocStatusCode("INI")
						.NocStatusCodeNext("INI")
						.InitiatedDate(sdf1.format(new Date()))
						.isActive(1)
						.CreatedBy(userId)
						.CreatedDate(sdf1.format(new Date()))
		                .build();
		
		return dao.NocProcAbroadAdd(nocpa);
		
	}


	@Override
	public List<Object[]> getProcAbroadList(String empNo) throws Exception {
		
		
		return dao.getProcAbroadList(empNo);
	}

	@Override
	public List<Object[]> NOCProcAbroadTransactionList(String procAbrId) throws Exception {
		
		return dao.NOCProcAbroadTransactionList(procAbrId);
	}

	@Override
	public NocProceedingAbroad getNocProceedingAbroadById(long ProcAbrId) throws Exception {
		
		return dao.getNocProceedingAbroadById(ProcAbrId);
	}

	@Override
	public long NocProcAbroadUpdate(NocProceedingAbroadDto dto, String userId) throws Exception {
		
		
		
		NocProceedingAbroad nocpa=dao.getNocProceedingAbroadById(dto.getNocProcId());
		
		
	
		long maxNocProcId = dao.getMaxOfProcAbroadId();
		
		
		    nocpa.setRelationType(dto.getRelationType());
		    nocpa.setRelationName(dto.getRelationName());
		    nocpa.setRelationAddress(dto.getRelationAddress());
		    nocpa.setRelationOccupation(dto.getRelationOccupation());
		    nocpa.setRelationAbroad(dto.getRelationAbroad());
		    nocpa.setEmployementDetails(dto.getEmployementDetails());
		    nocpa.setEmployeeInvolvement(dto.getEmployeeInvolvement());
		    nocpa.setPropertyFiled(dto.getPropertyFiled());
		    nocpa.setForeignVisit(dto.getForeignVisit());
		    nocpa.setForeignVisitDetails(dto.getForeignVisitDetails());
		    nocpa.setCountriesProposed(dto.getCountriesProposed());
		    nocpa.setDepartureDate(dto.getDepartureDate());
		    nocpa.setVisitPurpose(dto.getVisitPurpose());
		    nocpa.setStayDuration(dto.getStayDuration());
		    nocpa.setReturnDate(dto.getReturnDate());
		    nocpa.setGoing(dto.getGoing());
		    nocpa.setFamilyDetails(dto.getFamilyDetails());
		    nocpa.setExpectedAmount(CropTo2Decimal(dto.getExpectedAmount()));
		    nocpa.setFinancedBy(dto.getFinancedBy());
		    nocpa.setAmountSource(dto.getAmountSource());
		    nocpa.setName(dto.getName());
		    nocpa.setNationality(dto.getNationality());
		    nocpa.setRelationship(dto.getRelationship());
		    nocpa.setRelationshipAddress(dto.getRelationshipAddress());
		    nocpa.setLostPassport(dto.getLostPassport());
		    nocpa.setContractualObligation(dto.getContractualObligation());
		    nocpa.setFromDate(dto.getFromDate());
		    nocpa.setToDate(dto.getToDate());
		    nocpa.setHospatility(dto.getHospatility());
		    nocpa.setProcAbroadStatus("N");
		    nocpa.setNocStatusCode("INI");
		    nocpa.setNocStatusCodeNext("INI");
		    nocpa.setIsActive(1);
			nocpa.setModifiedBy(userId);
			nocpa.setModifiedDate(sdf1.format(new Date()));
           
		
		return dao.NocProcAbroadUpdate(nocpa);
	}

	@Override
	public Object[] getNocProcAbroadDetails(String procAbrId) throws Exception {
		
		return dao.getNocProcAbroadDetails(procAbrId);
	}

	@Override
	public long NOCProcAbraodForward(String procAbroadId, String userId, String action, String remarks, String empNo,String loginType) {
		
		try {
			
		
        NocProceedingAbroad noc =dao.getNocProceedingAbroadById(Long.parseLong(procAbroadId));
		Employee emp = dao.getEmpDataByEmpNo(noc.getEmpNo());
		String formempno = emp.getEmpNo();
		String  NocStatusCode = noc.getNocStatusCode();
		String NocStatusCodeNext = noc.getNocStatusCodeNext();
		
		String CEO = dao.GetCEOEmpNo();
		List<String> PandAs = dao.GetPandAAdminEmpNos();
		List<String> DGMs = dao.GetDGMEmpNos();
		List<String> DHs = dao.GetDHEmpNos();
		List<String> GHs = dao.GetGHEmpNos();
		
		DivisionMaster formEmpDivisionMaster = dao.GetDivisionData(emp.getDivisionId());
		
		// Accepted
		if(action.equalsIgnoreCase("A"))
		{
//		
			// first time forwarding
			if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
					NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE") ) 
			{
				noc.setNocStatusCode("FWD");
				noc.setForwardedDate(sdf1.format(new Date()));
				if(CEO.equalsIgnoreCase(formempno) ) 
				{ 
				    //dao.HometownStatusUpdate(noc.getEmpNo());

					noc.setNocStatusCode("APR");
					noc.setNocStatusCodeNext("APR");
					noc.setIsActive(1);
					noc.setProcAbroadStatus("A");	
					noc.setApprovedDate(sdf1.format(new Date()));
				}
				
				else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
				{
					noc.setNocStatusCodeNext("APR");
				}
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					noc.setNocStatusCodeNext("VPA");
				}
				else if(DHs.contains(formempno) || emp.getDivisionId()==0)
				{
					noc.setNocStatusCodeNext("VDG");
				}
				else if(GHs.contains(formempno) || emp.getGroupId()==0)
				{
					noc.setNocStatusCodeNext("VDI");
				}
				else 
				{
					noc.setNocStatusCodeNext("VGI");
				}
				
			}
			
			//approving	flow 
			else
			{
			   
				noc.setNocStatusCode(NocStatusCodeNext);
				
				if(NocStatusCodeNext.equalsIgnoreCase("VGI"))
				{
					noc.setNocStatusCodeNext("VDI");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VDI")) 
				{
					noc.setNocStatusCodeNext("VDG");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					noc.setNocStatusCodeNext("VPA");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VPA")) 
				{
					noc.setNocStatusCodeNext("APR");
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("APR")) {	
					//dao.HometownStatusUpdate(noc.getEmpNo());
					noc.setIsActive(1);
					noc.setProcAbroadStatus("A");
					noc.setApprovedDate(sdf1.format(new Date()));
				}
			}
			
			noc.setRemarks(remarks);
			dao.EditNocpa(noc);
					
		}
		
		//Returned
		else if(action.equalsIgnoreCase("R")) 
		{
			// Setting PisStatusCode
			if(NocStatusCodeNext.equalsIgnoreCase("VGI")) 
			{
				noc.setNocStatusCode(NocStatusCodeNext);
			}
		    else if(NocStatusCodeNext.equalsIgnoreCase("VDI")) 
			{
		    	noc.setNocStatusCode("RDI");	
			}
			else if(NocStatusCodeNext.equalsIgnoreCase("VDG")) 
			{
				noc.setNocStatusCode("RDG");	
			}
			else if(NocStatusCodeNext.equalsIgnoreCase("VPA")) 
			{
				noc.setNocStatusCode("RPA");	
			}
			else if(NocStatusCodeNext.equalsIgnoreCase("APR")) 
			{
				noc.setNocStatusCode("RCE");	
			}
			
			//Setting PisStatusCodeNext
			if(CEO.equalsIgnoreCase(formempno) ) 
			{ 
				noc.setNocStatusCodeNext("APR");					
			}
			else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
			{
				noc.setNocStatusCodeNext("APR");
			}
			else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
			{
				noc.setNocStatusCodeNext("VPA");
			}
			else if(DHs.contains(formempno) || emp.getDivisionId()==0)
			{
				noc.setNocStatusCodeNext("VDG");
			}
			else if(GHs.contains(formempno)  || emp.getGroupId()==0)
			{
				noc.setNocStatusCodeNext("VDI");
			}
			else 
			{
				noc.setNocStatusCodeNext("VGI");
			}
			noc.setReturnedDate(sdf1.format(new Date()));
			noc.setRemarks(remarks);
			dao.EditNocpa(noc);
		}
		
		NocProceedingAbroadTrans trans = NocProceedingAbroadTrans.builder()	
				                           .NocProcId(noc.getNocProcId())
				                           .NocStatusCode(noc.getNocStatusCode())
				                           .ActionBy(empNo)
				                           .Remarks(remarks)
				                           .ActionDate(sdtf.format(new Date()))
				                           .build();
		
		 dao.NocProcAbroadTransactionAdd(trans);
						
		    String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
			String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
			
			String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
	
		//Notification
			
			EMSNotification notification1 = new EMSNotification();
			if(action.equalsIgnoreCase("A") && noc.getProcAbroadStatus().equalsIgnoreCase("A"))
			{
				notification1.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
				notification1.setNotificationUrl("NocApproval.htm?tab=closed");
				notification1.setNotificationMessage("NOC Passpaort Request Approved For <br>"+emp.getEmpName());
				notification1.setNotificationBy(empNo);
				
				notification1.setNotificationDate(LocalDate.now().toString());
				notification1.setIsActive(1);
				notification1.setCreatedBy(userId);
				notification1.setCreatedDate(sdtf.format(new Date()));
			
				dao.AddNotifications(notification1);		
				
			}
			
			
		EMSNotification notification = new EMSNotification();
		if(action.equalsIgnoreCase("A") && noc.getProcAbroadStatus().equalsIgnoreCase("A"))
		{
			notification.setEmpNo(emp.getEmpNo());
			notification.setNotificationUrl("ProceedingAbroad.htm");
			notification.setNotificationMessage("NOC Proceeding Abroad Request Approved");
			notification.setNotificationBy(empNo);
		}
		else if(action.equalsIgnoreCase("A") )
		{
			if( noc.getNocStatusCodeNext().equalsIgnoreCase("VGI")) 
			{
				notification.setEmpNo(GIEmpNo);					
			}
			else if( noc.getNocStatusCodeNext().equalsIgnoreCase("VDI")) 
			{
				notification.setEmpNo(DIEmpNo);					
			}
			else if( noc.getNocStatusCodeNext().equalsIgnoreCase("VDG")) 
			{
				notification.setEmpNo(DGMEmpNo);					
			}
			else if(noc.getNocStatusCodeNext().equalsIgnoreCase("VPA")) 
			{
				notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0):null);
			}
			else if(noc.getNocStatusCodeNext().equalsIgnoreCase("APR")) 
			{
				notification.setEmpNo(CEO);
			}
			
			notification.setNotificationUrl("NocApproval.htm");
			notification.setNotificationMessage("Recieved NOC Proceeding Abroad Change Request <br> From "+emp.getEmpName());
			notification.setNotificationBy(empNo);
		}
		else if(action.equalsIgnoreCase("R"))
		{
			notification.setEmpNo(emp.getEmpNo());
			notification.setNotificationUrl("ProceedingAbroad.htm");
			notification.setNotificationMessage("NOC Proceeding Abroad Request Returned");
			notification.setNotificationBy(empNo);
		}
		
		notification.setNotificationDate(LocalDate.now().toString());
		notification.setIsActive(1);
		notification.setCreatedBy(userId);
		notification.setCreatedDate(sdtf.format(new Date()));
	
		dao.AddNotifications(notification);	
		
		return 1;
	}catch (Exception e) {
		logger.error(new Date() +" Inside NOCPassportForward "+ e);
		e.printStackTrace();
		return 0;
	
     }
	}

	@Override
	public List<LabMaster> getLabMasterDetails() throws Exception {
	
		return dao.getLabMasterDetails();
	}

	@Override
	public List<Object[]> getPassportRemarksHistory(String passportid) throws Exception {
		
		return dao.getPassportRemarksHistory(passportid);
	}

	@Override
	public List<Object[]> NocApprovedList(String empNo, String fromdate, String todate) throws Exception {
	
		return dao.getNocApprovedList(empNo,fromdate,todate);
	}
	
	@Override
	public Object[] getEmpNameDesig(String EmpNo) throws Exception {
		
		return dao.getEmpNameDesig(EmpNo);
	}

	@Override
	public Object[] getEmpTitleDetails(String passportid) throws Exception {
		
		return dao.getEmpTitleDetails(passportid) ;
	}

	@Override
	public List<Object[]> getProceedinAbraodRemarksHistory(String procAbrId) throws Exception {
		
		return dao.getProceedinAbraodRemarksHistory(procAbrId);
	}

	@Override
	public long DeptDetailsUpdate(NocProceedingAbroadDto dto, String userId) throws Exception {
		
		NocProceedingAbroad nocpa=dao.getNocProceedingAbroadById(dto.getNocProcId());
		
		nocpa.setWorkHandled(dto.getWorkHandled());
		nocpa.setVisitRecommended(dto.getVisitRecommended());
		nocpa.setLeaveGranted(dto.getLeaveGranted());
		
		return dao.DeptDetailsUpdate(nocpa);
	}

	@Override
	public long ProcAbroadPandAFromUpdate(NocProceedingAbroadDto dto, String userId) throws Exception {
		
		NocProceedingAbroad nocpa=dao.getNocProceedingAbroadById(dto.getNocProcId());
		
		nocpa.setProcAbroadEntries(dto.getProcAbroadEntries());
		nocpa.setProcAbroadEntriesDetails(dto.getProcAbroadEntriesDetails());
		nocpa.setEmployeeInvolvement(dto.getEmployeeInvolvement());
		nocpa.setEmployeeCaseDetails(dto.getEmployeeCaseDetails());
		nocpa.setEmployeeDues(dto.getEmployeeDues());
		nocpa.setContractualObligation(dto.getContractualObligation());
		nocpa.setFromDate(dto.getFromDate());
		nocpa.setToDate(dto.getToDate());
		nocpa.setPandAModifiedBy(userId);
		nocpa.setProcAbroadStatus("N");
		nocpa.setPandAModifiedDate(sdf1.format(new Date()));
		return dao.ProcAbroadPandAFromUpdate(nocpa);
		
	}

	@Override
	public Object[] getEmpGender(String procAbrId) throws Exception {
		
		return dao.getEmpGender(procAbrId);
	}

	@Override
	public long ExamDetailsAdd(ExamIntimationDto dto, String userId) throws Exception {
		
//		ExamIntimationTrans transaction = ExamIntimationTrans.builder()	
//				.ExamId(dto.getExamId())
//				.IntimateStatusCode("INI")
//				.ActionBy(dto.getEmpNo())
//				.ActionDate(sdtf.format(new Date()))
//				.build();
//		
//		 dao.ExamIntimationTransAdd(transaction);
		
		
		ExamIntimation exam = new ExamIntimation();
		
		exam.setEmpNo(dto.getEmpNo());
		exam.setExamName(dto.getExamName());
		exam.setProbableDate(dto.getProbableDate());
		exam.setInitiatedDate(sdf1.format(new Date()));
		exam.setIntimationStatus("N");
		exam.setIntimateStatusCode("INI");
		exam.setInitimateStatusCodeNext("INI");
		exam.setCreatedBy(userId);
		exam.setCreatedDate(sdf1.format(new Date()));
		exam.setIsActive(1);
		dao.ExamDetailsAdd(exam);
		
		ExamIntimationTrans transaction = ExamIntimationTrans.builder()	
				.ExamId(exam.getExamId())
				.IntimateStatusCode("INI")
				.ActionBy(dto.getEmpNo())
				.ActionDate(sdtf.format(new Date()))
				.build();
		
		return  dao.ExamIntimationTransAdd(transaction);
		
		
	}

	@Override
	public List<Object[]> getExamIntimationDetails(String empNo) throws Exception {
		
		return dao.getExamIntimationDetails(empNo);
	}

	@Override
	public long ExamDetailsUpdate(ExamIntimationDto dto, String userId) throws Exception {
	
		ExamIntimation exam =dao.getExamId(dto.getExamId());
		
		exam.setExamId(dto.getExamId());
		exam.setExamName(dto.getExamName());
		exam.setProbableDate(dto.getProbableDate());
		exam.setInitiatedDate(sdf1.format(new Date()));
		exam.setIntimationStatus("N");
		exam.setIntimateStatusCode("INI");
		exam.setInitimateStatusCodeNext("INI");
		exam.setModifiedBy(userId);
		exam.setModifiedDate(sdf1.format(new Date()));
		exam.setIsActive(1);
		
		return dao.ExamDetailsUpdate(exam);
	}

	@Override
	public List<Object[]> ExamIntimationTransactionList(String examId) throws Exception {
		
		return dao.getExamIntimationTransactionList(examId);
	}

	@Override
	public Object[] getIntimationData(String examId) throws Exception {
		
		return dao.getIntimationData(examId);
	}

	@Override
	public ExamIntimation IntimatedExam(String examId) throws Exception {
		
		return dao.IntimatedExam(examId);
	}

	@Override
	public long IntimationForExamForward(String examId, String username, String action, String remarks, String empNo,
			String loginType) throws Exception {
		
		try {
			ExamIntimation exam = dao.IntimatedExam(examId);
			Employee emp = dao.getEmpData(exam.getEmpNo());
			String formempno = emp.getEmpNo();
			
		    
			String  IntimateStatusCode = exam.getIntimateStatusCode();
			String InitimateStatusCodeNext = exam.getInitimateStatusCodeNext();
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
				if(IntimateStatusCode.equalsIgnoreCase("INI") || IntimateStatusCode.equalsIgnoreCase("RDG") || IntimateStatusCode.equalsIgnoreCase("RPA") ) 
				{
					exam.setIntimateStatusCode("FWD");
					exam.setForwardedDate(sdf1.format(new Date()));
					if(PandAs.contains(formempno) || CEO.equalsIgnoreCase(formempno) || loginType.equalsIgnoreCase("P")) 
					{
						
	              
	                    exam.setIntimateStatusCode("VPA");
	                    exam.setInitimateStatusCodeNext("VPA");
	                    exam.setIsActive(1);
	                    exam.setIntimationStatus("A");				
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						exam.setInitimateStatusCodeNext("VPA");
					}
					else 
					{
						exam.setInitimateStatusCodeNext("VDG");
					}					
				}
				//approving	flow 
				else
				{
					
                    			    					
					exam.setIntimateStatusCode(InitimateStatusCodeNext);
					if(InitimateStatusCodeNext.equalsIgnoreCase("VDG")) {
						exam.setInitimateStatusCodeNext("VPA");
					}else if(InitimateStatusCodeNext.equalsIgnoreCase("VPA")) {
						
	                    exam.setIsActive(1);
	                    exam.setIntimationStatus("A");
	                    exam.setApprovedDate(sdf1.format(new Date()));
					}
				}
				
				exam.setRemarks(remarks);
				dao.IntimationDataEdit(exam);
				
			}
			else if(action.equalsIgnoreCase("R")) 
			{
				if(InitimateStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					exam.setIntimateStatusCode("RDG");	
				}
				else if(InitimateStatusCodeNext.equalsIgnoreCase("VPA")) 
				{
					exam.setIntimateStatusCode("RPA");	
				}
				
				
				if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					exam.setInitimateStatusCodeNext("VPA");
				}
				else 
				{
					exam.setInitimateStatusCodeNext("VDG");
				}
				exam.setRemarks(remarks);
				dao.IntimationDataEdit(exam);
			}
			
			ExamIntimationTrans transaction = ExamIntimationTrans.builder()	
					
					.ExamId(exam.getExamId())
					.IntimateStatusCode(exam.getIntimateStatusCode())
					.Remarks(remarks)
					.ActionBy(empNo)
					.ActionDate(sdtf.format(new Date()))
					.build();
			
			 dao.ExamIntimationTransAdd(transaction);
			
			
			String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);

			
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && exam.getIntimationStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("IntimateExam.htm");
				notification.setNotificationMessage("Intimation For Exam Request Approved");
				notification.setNotificationBy(empNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( exam.getInitimateStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if(exam.getInitimateStatusCodeNext().equalsIgnoreCase("VPA")) 
				{
					notification.setEmpNo( PandAs.size()>0 ? PandAs.get(0).toString():null);
				}
				notification.setNotificationUrl("NocApproval.htm");
				notification.setNotificationMessage("Recieved Intimation For Exam  Request From <br>"+emp.getEmpName());
				notification.setNotificationBy(empNo);
			}
			else if(action.equalsIgnoreCase("R"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("IntimateExam.htm");
				notification.setNotificationMessage("Pntimation For Exam Request Returned");
				notification.setNotificationBy(empNo);
			}
			
			notification.setNotificationDate(LocalDate.now().toString());
			notification.setIsActive(1);
			notification.setCreatedBy(username);
			notification.setCreatedDate(sdtf.format(new Date()));
		
			dao.AddNotifications(notification);		
			
			return 1;
		}catch (Exception e) {
			logger.error(new Date() +" Inside IntimationForExamForward "+ e);
			e.printStackTrace();
			return 0;
		}		
	}

	@Override
	public List<Object[]> getIntimationRemarks(String examId) throws Exception {
	
		return dao.getIntimationRemarks(examId);
	}

	@Override
	public long HigherEducationAdd(NocHigherEducationDto dto, String userId) throws Exception {
	
		LocalDate today= LocalDate.now();
		String year="";
		year=String.valueOf(today.getYear()).substring(2, 4);
		long maxNocEduId = dao.getMaxOfNocEducId()+1;
		
		String NocHigherEducNo="";
		NocHigherEducNo="NOC/HE-"+year+"/"+maxNocEduId;
		
		NocHigherEducationTrans transaction = NocHigherEducationTrans.builder()	
				.NocEducationId(maxNocEduId)
				.NocStatusCode("INI")
				.ActionBy(dto.getEmpNo())
				.ActionDate(sdtf.format(new Date()))
				.build();
		
		 dao.NocHigherEducTransactionAdd(transaction);
		
		NocHigherEducation edu= new NocHigherEducation();
		
		
		edu.setNocEducationNo(NocHigherEducNo);
		edu.setEmpNo(dto.getEmpNo());
		edu.setInstitutionType(dto.getInstitutionType());
		edu.setInstitutionName(dto.getInstitutionName());
		edu.setAcademicYear(dto.getAcademicYear());
		edu.setCourse(dto.getCourse());
		edu.setSpecialization(dto.getSpecialization());
		edu.setEducationType(dto.getEducationType());
		edu.setQualifiactionRequired(dto.getQualifiactionRequired());
		edu.setHigherEducationStatus("N");
		edu.setNocStatusCode("INI");
		edu.setNocStatusCodeNext("INI");
		edu.setInitiatedDate(sdf1.format(new Date()));
		edu.setCreatedBy(userId);
		edu.setCreatedDate(sdf1.format(new Date()));
		edu.setIsActive(1);
		
		return dao.HigherEducationAdd(edu);
	}

	@Override
	public List<Object[]> getNOCHigherEducationList(String empNo) throws Exception {
		
		return dao.getNOCHigherEducationList(empNo);
	}

	@Override
	public List<Object[]> HigherEducationTransactionList(String nOCHigherEducId) throws Exception {
		
		return dao.getHigherEducationTransactionList(nOCHigherEducId);
	}

	@Override
	public NocHigherEducation getNocHigherEducationById(long NOCHigherEducId) throws Exception {
		
		return dao.getNocHigherEducationById(NOCHigherEducId);
	}

	@Override
	public long NOCHigherEducationUpdate(NocHigherEducationDto dto, String userId) throws Exception {
	
		NocHigherEducation edu =dao.getNocHigherEducationById(dto.getNocEducationId());
		
		edu.setNocEducationId(dto.getNocEducationId());
		edu.setInstitutionType(dto.getInstitutionType());
		edu.setInstitutionName(dto.getInstitutionName());
		edu.setAcademicYear(dto.getAcademicYear());
		edu.setCourse(dto.getCourse());
		edu.setSpecialization(dto.getSpecialization());
		edu.setEducationType(dto.getEducationType());
		edu.setQualifiactionRequired(dto.getQualifiactionRequired());
		edu.setHigherEducationStatus("N");
		edu.setNocStatusCode("INI");
		edu.setNocStatusCodeNext("INI");
		edu.setModifiedBy(userId);
		edu.setModifiedDate(sdf1.format(new Date()));
		edu.setIsActive(1);
		
		
		return dao.HigherEducationUpdate(edu);
	}

	@Override
	public Object[] getHigherEducationDetails(String NOCHigherEducId) throws Exception {
		
		return dao.getHigherEducationDetails(NOCHigherEducId);
	}

	@Override
	public List<Object[]> getNocHigherEducationRemarks(String NOCHigherEducId) throws Exception {
		
		return dao.getNocHigherEducationRemarks(NOCHigherEducId);
	}

	@Override
	public NocHigherEducation HigherEducation(String nOCHigherEducId) throws Exception {
		
		return dao.HigherEducation(nOCHigherEducId);
	}

	@Override
	public long NOCHigherEducationForward(String NOCHigherEducId, String username, String action, String remarks,
			String empNo, String loginType) throws Exception {
		
		try {
			
			NocHigherEducation noc =dao.HigherEducation(NOCHigherEducId);
			Employee emp = dao.getEmpDataByEmpNo(noc.getEmpNo());
			String formempno = emp.getEmpNo();
			String  NocStatusCode = noc.getNocStatusCode();
			String NocStatusCodeNext = noc.getNocStatusCodeNext();
			
			String CEO = dao.GetCEOEmpNo();
			List<String> PandAs = dao.GetPandAAdminEmpNos();
			List<String> SOs = dao.GetSOEmpNos();	
			List<String> DGMs = dao.GetDGMEmpNos();
			List<String> DHs = dao.GetDHEmpNos();
			List<String> GHs = dao.GetGHEmpNos();
			
			
			DivisionMaster formEmpDivisionMaster = dao.GetDivisionData(emp.getDivisionId());
			
			
			// Accepted
			if(action.equalsIgnoreCase("A"))
			{
//			
				// first time forwarding
				if(NocStatusCode.equalsIgnoreCase("INI") || NocStatusCode.equalsIgnoreCase("RGI") || NocStatusCode.equalsIgnoreCase("RDI") || 
						NocStatusCode.equalsIgnoreCase("RSO")	|| NocStatusCode.equalsIgnoreCase("RDG") || NocStatusCode.equalsIgnoreCase("RPA") || NocStatusCode.equalsIgnoreCase("RCE") ) 
				{
					noc.setNocStatusCode("FWD");
					noc.setForwardedDate(sdf1.format(new Date()));
					if(CEO.equalsIgnoreCase(formempno) ) 
					{ 
					    //dao.HometownStatusUpdate(noc.getEmpNo());

						noc.setNocStatusCode("APR");
						noc.setNocStatusCodeNext("APR");
						noc.setIsActive(1);
						noc.setHigherEducationStatus("A");	
						noc.setApprovedDate(sdf1.format(new Date()));
					}
					else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
					{
						noc.setNocStatusCodeNext("APR");
					}
					else if(SOs.contains(formempno)) 
					{
						noc.setNocStatusCodeNext("VPA");
					}
					else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
					{
						noc.setNocStatusCodeNext("VSO");
					}
					else if(DHs.contains(formempno) || emp.getDivisionId()==0)
					{
						noc.setNocStatusCodeNext("VDG");
					}
					else if(GHs.contains(formempno) || emp.getGroupId()==0)
					{
						noc.setNocStatusCodeNext("VDI");
					}
					else 
					{
						noc.setNocStatusCodeNext("VGI");
					}
					
				}
				
				//approving	flow 
				else
				{
				   
					noc.setNocStatusCode(NocStatusCodeNext);
					
					if(NocStatusCodeNext.equalsIgnoreCase("VGI"))
					{
						noc.setNocStatusCodeNext("VDI");
					}
					else if(NocStatusCodeNext.equalsIgnoreCase("VDI")) 
					{
						noc.setNocStatusCodeNext("VDG");
					}
					
					else if(NocStatusCodeNext.equalsIgnoreCase("VDG")) 
					{
						noc.setNocStatusCodeNext("VSO");
					}
					
					else if(NocStatusCodeNext.equalsIgnoreCase("VSO")) 
					{
						noc.setNocStatusCodeNext("VPA");
					}
					
					else if(NocStatusCodeNext.equalsIgnoreCase("VPA")) 
					{
						noc.setNocStatusCodeNext("APR");
					}
					else if(NocStatusCodeNext.equalsIgnoreCase("APR")) {	
						//dao.HometownStatusUpdate(noc.getEmpNo());
						noc.setIsActive(1);
						noc.setHigherEducationStatus("A");
						noc.setApprovedDate(sdf1.format(new Date()));
					}
				}
				
				noc.setRemarks(remarks);
				dao.EditNocHe(noc);
						
			}
			
			//Returned
			else if(action.equalsIgnoreCase("R")) 
			{
				// Setting PisStatusCode
				if(NocStatusCodeNext.equalsIgnoreCase("VGI")) 
				{
					noc.setNocStatusCode(NocStatusCodeNext);
				}
			    else if(NocStatusCodeNext.equalsIgnoreCase("VDI")) 
				{
			    	noc.setNocStatusCode("RDI");	
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VDG")) 
				{
					noc.setNocStatusCode("RDG");	
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VSO")) 
				{
					noc.setNocStatusCode("RSO");	
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("VPA")) 
				{
					noc.setNocStatusCode("RPA");	
				}
				else if(NocStatusCodeNext.equalsIgnoreCase("APR")) 
				{
					noc.setNocStatusCode("RCE");	
				}
				
				//Setting PisStatusCodeNext
				if(CEO.equalsIgnoreCase(formempno) ) 
				{ 
					noc.setNocStatusCodeNext("APR");					
				}
				else if(PandAs.contains(formempno) || loginType.equalsIgnoreCase("P")) 
				{
					noc.setNocStatusCodeNext("APR");
				}
				if(SOs.contains(formempno)) 
				{
					noc.setNocStatusCodeNext("VPA");
				}
				else if(DGMs.contains(formempno) || formEmpDivisionMaster.getDGMId()==0) 
				{
					noc.setNocStatusCodeNext("VSO");
				}
				else if(DHs.contains(formempno) || emp.getDivisionId()==0)
				{
					noc.setNocStatusCodeNext("VDG");
				}
				else if(GHs.contains(formempno)  || emp.getGroupId()==0)
				{
					noc.setNocStatusCodeNext("VDI");
				}
				else 
				{
					noc.setNocStatusCodeNext("VGI");
				}
				
				noc.setRemarks(remarks);
				dao.EditNocHe(noc);
			}
			
			else if(action.equalsIgnoreCase("D")) {
				
				noc.setNocStatusCode("DPR");
				noc.setNocStatusCodeNext("DPR");
				noc.setRemarks(remarks);
				dao.EditNocHe(noc);
			}
			
			NocHigherEducationTrans transaction = NocHigherEducationTrans.builder()	
					.NocEducationId(noc.getNocEducationId())
					.NocStatusCode(noc.getNocStatusCode())
					.Remarks(remarks)
					.ActionBy(empNo)
					.ActionDate(sdtf.format(new Date()))
					.build();
			
		 dao.NocHigherEducationTransAdd(transaction);
							
			    String DGMEmpNo = dao.GetEmpDGMEmpNo(formempno);
				String DIEmpNo = dao.GetEmpDHEmpNo(formempno);
				String GIEmpNo = dao.GetEmpGHEmpNo(formempno);
				
				
		
			//Notification
				
				
				if(action.equalsIgnoreCase("A") && noc.getHigherEducationStatus().equalsIgnoreCase("A"))
				{
					
					
					if(PandAs.size()>0) {
						   for(String PandAEmpNo : PandAs) {
							   EMSNotification notification1 = new EMSNotification();
							   notification1.setEmpNo(PandAEmpNo);
							   notification1.setNotificationUrl("NocApproval.htm?tab=closed");
								notification1.setNotificationMessage("NOC Higher Education Request Approved For <br>"+emp.getEmpName());
								notification1.setNotificationBy(empNo);
							   notification1.setNotificationDate(LocalDate.now().toString());
							   notification1.setIsActive(1);
							   notification1.setCreatedBy(username);
							   notification1.setCreatedDate(sdtf.format(new Date()));
										
								dao.AddNotifications(notification1);  
						      }
						   }
						
					
				}
				
				
			EMSNotification notification = new EMSNotification();
			if(action.equalsIgnoreCase("A") && noc.getHigherEducationStatus().equalsIgnoreCase("A"))
			{
				notification.setEmpNo(emp.getEmpNo());
				notification.setNotificationUrl("HigherEducation.htm");
				notification.setNotificationMessage("NOC Higher Education Request Approved");
				notification.setNotificationBy(empNo);
			}
			else if(action.equalsIgnoreCase("A") )
			{
				if( noc.getNocStatusCodeNext().equalsIgnoreCase("VGI")) 
				{
					notification.setEmpNo(GIEmpNo);					
				}
				else if( noc.getNocStatusCodeNext().equalsIgnoreCase("VDI")) 
				{
					notification.setEmpNo(DIEmpNo);					
				}
				else if( noc.getNocStatusCodeNext().equalsIgnoreCase("VDG")) 
				{
					notification.setEmpNo(DGMEmpNo);					
				}
				else if(noc.getNocStatusCodeNext().equalsIgnoreCase("APR")) 
				{
					notification.setEmpNo(CEO);
				}
				
				notification.setNotificationUrl("NocApproval.htm");
				notification.setNotificationMessage("Recieved NOC Higher Education Change Request <br> From "+emp.getEmpName());
				notification.setNotificationBy(empNo);
			}
				else if(action.equalsIgnoreCase("R"))
				{
					notification.setEmpNo(emp.getEmpNo());
					notification.setNotificationUrl("HigherEducation.htm");
					notification.setNotificationMessage("NOC Higher Education Request Returned");
					notification.setNotificationBy(empNo);
				}
			
				else if(action.equalsIgnoreCase("D"))
				{
					notification.setEmpNo(emp.getEmpNo());
					notification.setNotificationUrl("HigherEducation.htm");
					notification.setNotificationMessage("NOC Higher Education Request DisApproved");
					notification.setNotificationBy(empNo);
				}
				
				notification.setNotificationDate(LocalDate.now().toString());
				notification.setIsActive(1);
				notification.setCreatedBy(username);
				notification.setCreatedDate(sdtf.format(new Date()));
				
				
				if( !noc.getNocStatusCodeNext().equalsIgnoreCase("VSO") && !noc.getNocStatusCodeNext().equalsIgnoreCase("VPA")  ) {
					dao.AddNotifications(notification);
				}
				
				
				
			 if( noc.getNocStatusCodeNext().equalsIgnoreCase("VSO")) 
				{
					
				if(SOs.size()>0) {
					  for(String SOEMpNo : SOs) {
						  EMSNotification notification2 = new EMSNotification();
						  notification2.setEmpNo(SOEMpNo);
						  notification2.setNotificationUrl("NocApproval.htm");
						  notification2.setNotificationMessage("Recieved NOC Higher Education Change Request <br> From "+emp.getEmpName());
						  notification2.setNotificationBy(empNo);
						  notification2.setNotificationDate(LocalDate.now().toString());
						  notification2.setIsActive(1);
						  notification2.setCreatedBy(username);
						  notification2.setCreatedDate(sdtf.format(new Date()));
									
						  dao.AddNotifications(notification2);  
					 
					   }
				}
				
        		}
			 
				if(noc.getNocStatusCodeNext().equalsIgnoreCase("VPA")) 
				{
					{
						   if(PandAs.size()>0) {
						   for(String PandAEmpNo : PandAs) {
							   EMSNotification notification3 = new EMSNotification();
							   notification3.setEmpNo(PandAEmpNo);
							   notification3.setNotificationUrl("NocApproval.htm");
							   notification3.setNotificationMessage("Recieved NOC Higher Education Change Request <br> From"+emp.getEmpName());
							   notification3.setNotificationBy(empNo);
							   notification3.setNotificationDate(LocalDate.now().toString());
							   notification3.setIsActive(1);
							   notification3.setCreatedBy(username);
							   notification3.setCreatedDate(sdtf.format(new Date()));
										
								dao.AddNotifications(notification3);  
						      }
						   }
					     }
					
				}
				
				
			
			return 1;
			
		}catch (Exception e) {
			logger.error(new Date() +" Inside NOCHigherEducationForward "+ e);
			e.printStackTrace();
			return 0;
		
		}
		
	
	}

	@Override
	public List<Object[]> getHigherEducationApprovalData(String nOCHigherEducId) throws Exception {
		
		return dao.getHigherEducationApprovalData(nOCHigherEducId);
	}

	@Override
	public List<String> GetSOsEmpNos() throws Exception {
		
		return dao.GetSOEmpNos();	
	}

	@Override
	public List<String> GetSosEmpNos() throws Exception {
		
		return dao.GetSOEmpNos();
	}

	@Override
	public Object[] getNocApprovalFlow(String empNo) throws Exception {
		
		return dao.getNocApprovalFlow(empNo);
	}

	@Override
	public Object[] getPandAName(String NOCHigherEducId) throws Exception {
	
		return dao.getPandAName(NOCHigherEducId);
	}

	@Override
	public Object[] getEmpQualification(String empNo) throws Exception {
		
		return dao.getEmpQualification(empNo);
	}

	
}


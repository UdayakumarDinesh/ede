package com.vts.ems.noc.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.noc.dao.NocDao;
import com.vts.ems.noc.model.NocPassport;
import com.vts.ems.noc.model.NocPassportDto;
import com.vts.ems.noc.model.NocPassportTrans;
import com.vts.ems.noc.model.NocProceedingAbroad;
import com.vts.ems.noc.model.NocProceedingAbroadDto;
import com.vts.ems.noc.model.NocProceedingAbroadTrans;
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
		
		MultipartFile FormFile = dto.getFormFile();
		LocalDate today= LocalDate.now();
		String year="";
		year=String.valueOf(today.getYear()).substring(2, 4);
		long maxNocProcId = dao.getMaxOfProcAbroadId()+1;
		
		String NocProcAbroadNo="";
		String storagePath="";
		try {
			while(new File(FilePath+"//NocAttachments//ProcAbroad-"+maxNocProcId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename())).exists())
			{
				maxNocProcId++;
			}
			if(!FormFile.getOriginalFilename().toString().equals("")) {
			saveFile(FilePath+"//NocAttachments//","ProcAbroad-"+maxNocProcId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);}
			if(!FormFile.getOriginalFilename().toString().equals(""))
			{	
				storagePath="//NocAttachments//ProcAbroad-"+maxNocProcId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
			}
			NocProcAbroadNo="NOC/PA-"+year+"/"+maxNocProcId;
			}
		
		    catch (Exception e) {
			e.printStackTrace();
			return 0;
		   }
		
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
				        .ExpectedAmount(dto.getExpectedAmount())
				        .FinancedBy(dto.getFinancedBy())
				        .AmountSpend(dto.getAmountSpend())
				        .NameNationality(dto.getNameNationality())
				        .Relationship(dto.getRelationship())
				        .RelationshipAddress(dto.getRelationshipAddress())
				        .FileName(FormFile.getOriginalFilename())
				        .FilePath(storagePath)
				        .LostPassport(dto.getLostPassport())
				        .PassportType(dto.getPassportType())
				        .ContractualObligation(dto.getContractualObligation())
				        .FromDate(dto.getFromDate())
				        .ToDate(dto.getToDate())
				        .Hospatility(dto.getHospatility())
				        .ProcAbroadStatus("N")
				        .NocStatusCode("INI")
						.NocStatusCodeNext("INI")
						.isActive(1)
						.CreatedBy(userId)
						.CreatedDate(sdf1.format(new Date()))
		                .build();
		
		return dao.NocProcAbroadAdd(nocpa);
		
	}




  private static void saveFile(String FilePath, String FileName, MultipartFile multiparfile) throws IOException {
	
	logger.info(new Date() +"Inside SERVICE saveFile ");
    Path uploadPath = Paths.get(FilePath);
    if (!Files.exists(uploadPath)) {
    	Files.createDirectories(uploadPath);
    }
       
    try (InputStream inputStream = multiparfile.getInputStream()) {
    	Path filePath = uploadPath.resolve(FileName);
    	Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {       
    	throw new IOException("Could not save image file: " + FileName, ioe);
    }     
	
	
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
		
		MultipartFile FormFile = dto.getFormFile();
		
		NocProceedingAbroad nocpa=dao.getNocProceedingAbroadById(dto.getNocProcId());
		
		
		String storagePath=nocpa.getFilePath();
		long maxNocProcId = dao.getMaxOfProcAbroadId();
		if(!FormFile.isEmpty()) {
			try {
				String path= FilePath+nocpa.getFilePath();
				File file = new File(path);
				if(file.exists()){
					file.delete();
				}
				
				saveFile(FilePath+"//NocAttachments//","ProcAbroad-"+maxNocProcId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);
				storagePath="//NocAttachments//ProcAbroad-"+maxNocProcId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
//				saveFile(
//						FilePath+"//Ticketfiles//"+FilenameUtils.getPath(storagePath), 
//						FilenameUtils.getBaseName(storagePath)+ "." + FilenameUtils.getExtension(FormFile.getOriginalFilename()),
//						FormFile);
				nocpa.setFileName(FormFile.getOriginalFilename());
			}
			catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			
		}
		
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
		    nocpa.setExpectedAmount(dto.getExpectedAmount());
		    nocpa.setFinancedBy(dto.getFinancedBy());
		    nocpa.setAmountSpend(dto.getAmountSpend());
		    nocpa.setNameNationality(dto.getNameNationality());
		    nocpa.setRelationship(dto.getRelationship());
		    nocpa.setRelationshipAddress(dto.getRelationshipAddress());
		    
		    nocpa.setFilePath(storagePath);
		    nocpa.setLostPassport(dto.getLostPassport());
		    nocpa.setPassportType(dto.getPassportType());
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
			notification.setNotificationMessage("Recieved NOC Proceeding Abroad Change Request From <br>"+emp.getEmpName());
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

}


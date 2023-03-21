package com.vts.ems.ithelpdesk.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
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

import com.vts.ems.ithelpdesk.dao.helpdeskDao;
import com.vts.ems.ithelpdesk.dto.itheldeskdto;
import com.vts.ems.ithelpdesk.model.HelpDeskEmployee;
import com.vts.ems.ithelpdesk.model.HelpdeskAttachments;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskSubCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;


@Service
public class helpdeskServiceImpl implements helpdeskService {
	
	@Autowired
	helpdeskDao dao;
	
	@Value("${EMSFilesPath}")
    private String FilePath;
	
	private static final Logger logger = LogManager.getLogger(helpdeskServiceImpl.class);
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf= DateTimeFormatUtil.getRegularDateFormat();
	
	@Override
	public List<Object[]> getHelpDeskList(String empno,String fromDate, String toDate) throws Exception {
		
		return dao.getHelpDeskList(empno,sdf.format(rdf.parse(fromDate)),sdf.format(rdf.parse(toDate)));
	}
	
    @Override
	public long saveTicket(itheldeskdto dto, String userId,String EmpNo) throws Exception {
		
		MultipartFile FormFile = dto.getFormFile();
		LocalDate today= LocalDate.now();
		String year="";
		year=String.valueOf(today.getYear());
		
		long maxTicketId = dao.MaxOfEmsTicketId()+1;
		String storagePath="";
		String TicketNumber="";
		try {
		while(new File(FilePath+"//Ticketfiles//Ticket-"+maxTicketId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename())).exists())
		{
			maxTicketId++;
		}
		if(!FormFile.getOriginalFilename().toString().equals("")) {
		saveFile(FilePath+"//Ticketfiles//","Ticket-"+maxTicketId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);}
		if(!FormFile.getOriginalFilename().toString().equals(""))
		{	
			storagePath="//Ticketfiles//Ticket-"+maxTicketId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
		}
		TicketNumber="IT"+year+"-"+maxTicketId;
		}
		catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		HelpdeskTicket desk =HelpdeskTicket.builder()
				.TicketCategoryId(Integer.parseInt(dto.getTicketCategoryId()))
				.TicketNo(TicketNumber)
				.TicketSubCategoryId(Integer.parseInt(dto.getTicketSubCategoryId()))
				.TicketDesc(dto.getTicketDesc())
				.RaisedBy((dto.getEmpNo()))
				.RaisedDate(sdf1.format(new Date()))
				.TicketStatus(dto.getTicketStatus())
				.FileName(FormFile.getOriginalFilename())
				.FeedBackRequired("N")
				.isActive(1)
				.FilePath(storagePath)
				.CreatedBy(userId)
				.CreatedDate(sdf1.format(new Date()))
				.build();
			
		
		List <Object[]> notifyto = dao.SendNotification("A");
		int i=0;
	    for( Object[] obj : notifyto ){
			
		EMSNotification notify = new EMSNotification();
		notify.setEmpNo((obj[i].toString()));
	    notify.setNotificationUrl("TicketPending.htm");
		notify.setNotificationMessage("Ticket Raised");
		
		if(desk.getTicketStatus()=="I" & notify.getEmpNo()!=null)
		{
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy((EmpNo));
			notify.setIsActive(1);
			notify.setCreatedBy(userId);
			notify.setCreatedDate(sdf1.format(new Date()));
			dao.NotificationAdd(notify);
		}
	
	}
		return  dao.saveTicket(desk);
		
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
	public HelpdeskTicket GetTicketId(String ticketId) throws Exception {
		
		return dao.GetTicketId(ticketId);
	}

	@Override
	public HelpdeskTicket GetTicket(String ticketId) throws Exception {
		
		return dao.GetTicket(ticketId);
		
	}

	@Override
	public long TicketDelete(String ticketId) throws Exception {
		
		
		HelpdeskTicket ticketdelete=new HelpdeskTicket();
		ticketdelete.setTicketId(Long.parseLong(ticketId));
		
		return dao.TicketDelete(ticketdelete);
	}

	@Override
	public long ticketUpdate(itheldeskdto dto) throws Exception {
		
		MultipartFile FormFile = dto.getFormFile();
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		String storagePath=desk.getFilePath();
		long maxTicketId = dao.MaxOfEmsTicketId();
		System.out.println("FilePath"+storagePath);
		if(!FormFile.isEmpty()) {
			try {
				String path= FilePath+desk.getFilePath();
				File file = new File(path);
				if(file.exists()){
					file.delete();
				}
				
				saveFile(FilePath+"//Ticketfiles//","Ticket-"+maxTicketId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);
				storagePath="//Ticketfiles//Ticket-"+maxTicketId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
//				saveFile(
//						FilePath+"//Ticketfiles//"+FilenameUtils.getPath(storagePath), 
//						FilenameUtils.getBaseName(storagePath)+ "." + FilenameUtils.getExtension(FormFile.getOriginalFilename()),
//						FormFile);
				desk.setFileName(FormFile.getOriginalFilename());
			}
			catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			
		}
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setTicketDesc(dto.getTicketDesc());
		desk.setTicketCategoryId(Integer.parseInt(dto.getTicketCategoryId()));
		desk.setTicketSubCategoryId(Integer.parseInt(dto.getTicketSubCategoryId()));
		desk.setModifiedBy(dto.getModifiedBy());
		desk.setFilePath(storagePath);
        desk.setModifiedDate(sdf1.format(new Date()));
		return dao.ticketUpdate(desk);
	}

	@Override
	public List<Object[]> getPendingList() throws Exception {
		
		return dao.getTicketPending();
	}

	@Override
	public List<Object[]> getTicketList(String ticketId) throws Exception {
		
		return dao.getTicketList(ticketId);
		
	}

	@Override
	public List<Object[]> getCaseWorkerList() throws Exception {
		
		return dao.getCaseWorkerList();
	}

	@Override
	public long assignedTicket(itheldeskdto dto,String userId,String EmpNo,String s2) throws Exception {
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		 
		        desk.setTicketId(Long.parseLong(dto.getTicketId()));
				desk.setAssignedTo(dto.getAssignedTo());
				desk.setPriority(dto.getPriority());
				desk.setAssignedDate(sdf1.format(new Date()));
				desk.setTicketStatus("A");
			    desk.setAssignedBy(dto.getAssignedBy());
			    
			    EMSNotification notify = new EMSNotification();
				List<Object[]> notifyto = dao.SendNotification(s2);
				
			    notify.setEmpNo(dto.getAssignedTo());
				notify.setNotificationUrl("TicketRecieved.htm");
				notify.setNotificationMessage("Ticket Assigned");
				
			   if(desk.getTicketStatus()=="A" & notify.getEmpNo()!=null)
				{
					notify.setNotificationDate(LocalDate.now().toString());
					notify.setNotificationBy((EmpNo));
					notify.setIsActive(1);
					notify.setCreatedBy(userId);
					notify.setCreatedDate(sdf1.format(new Date()));
					dao.NotificationAdd(notify);
				}
				
		return dao.assignedTicket(desk);
	}

	@Override
	public List<Object[]> getAssignedList() throws Exception {
		
		return dao.getAssignedList();
	}

	@Override
	public List<Object[]> getTicketAssignedList(String ticketId) throws Exception {
		
		return dao.getTicketAssignedList(ticketId);
	}

	@Override
	public long reAssignedTicket(itheldeskdto dto,String userId,String EmpNo,String s2) throws Exception {
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setAssignedTo(dto.getAssignedTo());
		desk.setAssignedDate(sdf1.format(new Date()));
		desk.setPriority(dto.getPriority());
		desk.setTicketStatus("A");
	    desk.setAssignedBy(dto.getAssignedBy());
	
	    EMSNotification notify = new EMSNotification();
		List<Object[]> notifyto = dao.SendNotification(s2);
		notify.setEmpNo(dto.getAssignedTo());
		notify.setNotificationUrl("TicketRecieved.htm");
		notify.setNotificationMessage("Ticket Assigned");
		
		if(desk.getTicketStatus()=="A" & notify.getEmpNo()!=null)
		{
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy(EmpNo);
			notify.setIsActive(1);
			notify.setCreatedBy(userId);
			notify.setCreatedDate(sdf1.format(new Date()));
			dao.NotificationAdd(notify);
		}
	   return dao.reAssignedTicket(desk);
	}

	@Override
	public List<Object[]> getRecievedList(String empno) throws Exception {
		
		return dao.getRecievedList(empno);
	}

	@Override
	public long ForwardTicket(itheldeskdto dto,String userId,String EmpNo) throws Exception {
		
		MultipartFile FormFile = dto.getFormFile();
		long maxattachId = dao.MaxOfAttachmentId()+1;
		String storagePath="";
		try {
			while(new File(FilePath+"//TicketForwardedAttachments//TicketForwarded-"+maxattachId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename())).exists())
			{
				maxattachId++;
			}
			if(!FormFile.getOriginalFilename().toString().equals("")) {
			saveFile(FilePath+"//TicketForwardedAttachments//","TicketForwarded-"+maxattachId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename()),FormFile);}
			if(!FormFile.getOriginalFilename().toString().equals(""))
			{	
				storagePath="//TicketForwardedAttachments//TicketForwarded-"+maxattachId+"."+FilenameUtils.getExtension(FormFile.getOriginalFilename());
			}
			System.out.println("storagepath---"+storagePath);
			
			}
			catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setCWRemarks(dto.getCWRemarks());
		desk.setForwardedDate(sdf1.format(new Date()));
		desk.setTicketStatus("F");
		
		if(desk.getTicketStatus()=="F" ) {
			HelpdeskAttachments attach= HelpdeskAttachments.builder()
		            .TicketId(Long.parseLong(dto.getTicketId()))
		            .EmpNo(EmpNo)
					.FileName(FormFile.getOriginalFilename())
					.isActive(1)
					.FilePath(storagePath)
					.CreatedBy(userId)
					.CreatedDate(sdf1.format(new Date()))
					.build();
			 dao.ForwardAttachment(attach);
		}
		
		 List <Object[]> notifyto = dao.SendNotification("A");
			int i=0;
		    for( Object[] obj : notifyto ){
				
			EMSNotification notify = new EMSNotification();
			
			notify.setEmpNo(obj[i].toString());
		    notify.setNotificationUrl("TicketForwarded.htm");
			notify.setNotificationMessage("Ticket Forwarded");
			
			if(desk.getTicketStatus()=="F" & notify.getEmpNo()!=null)
			{
				notify.setNotificationDate(LocalDate.now().toString());
				notify.setNotificationBy((EmpNo));
				notify.setIsActive(1);
				notify.setCreatedBy(userId);
				notify.setCreatedDate(sdf1.format(new Date()));
				dao.NotificationAdd(notify);
			}
		 }
		 return dao.ForwardTicket(desk);
	}

	@Override
	public List<Object[]> getForwardedList() throws Exception {
		
		return dao.getForwardedList();
	}

	@Override
	public long ReturnTicket(itheldeskdto dto,String userId,String EmpNo,String s2) throws Exception {
		
		 HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
			desk.setTicketId(Long.parseLong(dto.getTicketId()));
			desk.setARemarks(dto.getARemarks());
			desk.setReturneddate(sdf1.format(new Date()));
			desk.setAssignedBy(dto.getAssignedBy());
			desk.setTicketStatus("R");
			 
			 EMSNotification notify = new EMSNotification();
				List<Object[]> notifyto = dao.SendNotification(s2);
				 notify.setEmpNo(s2);
				notify.setNotificationUrl("TicketRecieved.htm");
				notify.setNotificationMessage("Ticket Returned");
				
				 if(desk.getTicketStatus()=="R" & notify.getEmpNo()!=null)
				{
					notify.setNotificationDate(LocalDate.now().toString());
					notify.setNotificationBy((EmpNo));
					notify.setIsActive(1);
					notify.setCreatedBy(userId);
					notify.setCreatedDate(sdf1.format(new Date()));
					dao.NotificationAdd(notify);
				}
			 return dao.ReturnTicket(desk);
	}

	@Override
	public List<Object[]> getReturnedList() throws Exception {
		
		return dao.getReturnedList();
	}
    @Override
	public List<Object[]> getCategoryList() throws Exception {
		
		return dao.getCategoryList();
	}

   @Override
	public List<Object[]> getSubCategoryList(String CategoryId) throws Exception {
		
		return dao.getSubCategoryList(CategoryId);
	}
    @Override
	public long closeTicket(itheldeskdto dto,String userId,String EmpNo,String s2) throws Exception {
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setFeedBackRequired(dto.getFeedBackRequired());
		desk.setClosedDate(sdf1.format(new Date()));
		desk.setClosedBy(dto.getClosedBy());
		desk.setTicketStatus("C");
		 
		EMSNotification notify = new EMSNotification();
		List<Object[]> notifyto = dao.SendNotification("U");
		notify.setEmpNo((s2));
		notify.setNotificationUrl("ITTicketList.htm");
		notify.setNotificationMessage("Ticket Closed");
		
		if(desk.getTicketStatus()=="C" & notify.getEmpNo()!=null)
		{
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy(EmpNo);
			notify.setIsActive(1);
			notify.setCreatedBy(userId);
			notify.setCreatedDate(sdf1.format(new Date()));
			dao.NotificationAdd(notify);
		}
		 return dao.closeTicket(desk);
	}


	@Override
	public List<Object[]> getClosedList(String fromDate, String toDate) throws Exception {
		
		return dao.getClosedList(sdf.format(rdf.parse(fromDate)),sdf.format(rdf.parse(toDate)));
	}
	
   @Override
	public List<Object[]> getTicketClosedList(String ticketId) throws Exception {
		
		return dao.getTicketClosedList(ticketId);
	}

  @Override
  public long savefeedback(itheldeskdto dto,String userId,String EmpNo,String s2) throws Exception {
	
	  HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		desk.setFeedback(dto.getFeedback());
		desk.setFeedBackType(dto.getFeedBackType());
		 desk.setTicketStatus("C");
		 
		 EMSNotification notify = new EMSNotification();
			List<Object[]> notifyto = dao.SendNotification("A");
			
		    notify.setEmpNo(s2);
			notify.setNotificationUrl("TicketClosed.htm");
			notify.setNotificationMessage("Feedback Submitted");
			desk.setTicketId(Long.parseLong(dto.getTicketId()));
			if(desk.getTicketStatus()=="C" & notify.getEmpNo()!=null)
			{
				notify.setNotificationDate(LocalDate.now().toString());
				notify.setNotificationBy(EmpNo);
				notify.setIsActive(1);
				notify.setCreatedBy(userId);
				notify.setCreatedDate(sdf1.format(new Date()));
				dao.NotificationAdd(notify);
			}
		 
	     return dao.savefeedback(desk);
  }

     
     @Override
     public List<Object[]> getTicketCategoryList() throws Exception {
     	
     	return dao.getTicketCategoryList();
     }
     @Override
     public Long TicketCategoryAdd(HelpdeskCategory helpdeskCategory) throws Exception {
     	
     	return dao.TicketCategoryAdd(helpdeskCategory);
     }
     @Override
     public Long TicketCategoryEdit(HelpdeskCategory helpdeskCategory) throws Exception {
    	 HelpdeskCategory hc = dao.getTicketCategoryById(helpdeskCategory.getTicketCategoryId());
    	 hc.setTicketCategoryId(helpdeskCategory.getTicketCategoryId());
    	 hc.setTicketCategory(helpdeskCategory.getTicketCategory());
    	 hc.setModifiedBy(helpdeskCategory.getModifiedBy());
    	 hc.setModifiedDate(helpdeskCategory.getModifiedDate());
    	 
     	return dao.TicketCategoryEdit(hc);
     }
    @Override
     public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception {
     	
     	return dao.getTicketCategoryById(tcId);
     }

	@Override
	public List<Object[]> getSubCategoryList() throws Exception {
		
		return dao.getSubCategoryList();
	}
    
	@Override
	 public BigInteger ticketCategoryDuplicateAddCheck(String ticketCategory) throws Exception {
		
		return dao.ticketCategoryDuplicateAddCheck(ticketCategory);
	 }
    
    @Override
	public BigInteger ticketCategoryDuplicateEditCheck(String ticketCategoryId, String ticketCategory)throws Exception {
		
		return dao.ticketCategoryDuplicateEditCheck(ticketCategoryId, ticketCategory);
	}
    
	@Override
	public Long TicketSubCategoryAdd(HelpdeskSubCategory helpdeskSubCategory) throws Exception {
		return dao.TicketSubCategoryAdd(helpdeskSubCategory);
	}
    @Override
	public Long TicketSubCategoryEdit(HelpdeskSubCategory helpdeskSubCategory) throws Exception {
		HelpdeskSubCategory hsc = dao.getTicketSubCategoryById(helpdeskSubCategory.getTicketSubCategoryId());
		hsc.setTicketSubCategory(helpdeskSubCategory.getTicketSubCategory());
		hsc.setTicketCategoryId(helpdeskSubCategory.getTicketCategoryId());
		hsc.setModifiedBy(helpdeskSubCategory.getModifiedBy());
		hsc.setModifiedDate(helpdeskSubCategory.getModifiedDate());
		return dao.TicketSubCategoryEdit(hsc);
	}
    @Override
	public HelpdeskSubCategory getTicketSubCategoryById(Long tscId) throws Exception {
		
		return dao.getTicketSubCategoryById(tscId);
	}
    @Override
	public List<Object[]> getTicketSubCategoryList() throws Exception {
		
		return dao.getTicketSubCategoryList();
	}
    @Override
	public BigInteger ticketSubCategoryDuplicateAddCheck(String ticketCategoryId,String ticketCategory) throws Exception {
		
		return dao.ticketSubCategoryDuplicateAddCheck(ticketCategoryId,ticketCategory);
	}
    @Override
	public BigInteger ticketSubCategoryDuplicateEditCheck(String ticketSubCategoryId,String ticketCategoryId, String ticketCategory)throws Exception {
		
		return dao.ticketSubCategoryDuplicateEditCheck(ticketSubCategoryId,ticketCategoryId, ticketCategory);
	}

    @Override
	public List<Object[]> getEmployeeList() throws Exception {
		
		return dao.getEmployeeList();
	}
    @Override
	public List<Object[]> getContractEmployee() throws Exception {
		
		return dao.getContractEmployee();
	}
    @Override
	public List<Object[]> getHelpDeskEmployeeList() throws Exception {
		
		return dao.getHelpDeskEmployeeList();
	}
    @Override
	public long EmployeeAddSubmit(HelpDeskEmployee employee) throws Exception {
		
		return dao.EmployeeAddSubmit(employee);
	}
   @Override
	public long EmployeeDelete(String helpDeskEmpId) throws Exception {
		
		return dao.EmployeeDelete(helpDeskEmpId);
	}
   @Override
	public long Employeeupdate(HelpDeskEmployee employee) throws Exception {
	
	 HelpDeskEmployee he=dao.getHelpDeskEmployeeList(employee.getHelpDeskEmpId());
		he.setHelpDeskEmpId(employee.getHelpDeskEmpId());
   	    he.setValidTill(employee.getValidTill());
   	    he.setModifiedBy(employee.getModifiedBy());
   	    he.setModifiedDate(employee.getModifiedDate());
		return dao.getEmployeeupdate(he);
	}

   @Override
	public List<Object[]> getTicketReturnedList(String ticketId) throws Exception {
		
		return dao.getTicketReturnedList(ticketId);
	}

	@Override
	public Object[] IThelpdeskDashboardCountData(String empNo,String logintype,String fromDate, String toDate ) throws Exception {
		
		return dao.IThelpdeskDashboardCountData(empNo,logintype,sdf.format(rdf.parse(fromDate)), sdf.format(rdf.parse(toDate)));
	  }
   
    @Override
	public List<Object[]> IThelpdeskDashboardGraphData(String fromDate, String toDate) throws Exception {
		
		return dao.IThelpdeskDashboardGraphData(sdf.format(rdf.parse(fromDate)),sdf.format(rdf.parse(toDate)));
	}

	@Override
	public List<Object[]> IThelpdeskDashboardPieChartData(String fromDate, String toDate) throws Exception {
		
		return dao.IThelpdeskDashboardPieChartData(sdf.format(rdf.parse(fromDate)),sdf.format(rdf.parse(toDate)));
	}

	@Override
	public HelpdeskAttachments  getattachId(String AttachmentId) throws Exception {
		
		return dao. getattachId(AttachmentId);
	}

	@Override
	public List<Object[]> getRevokedList(String fromDate, String toDate) throws Exception {
		
		return dao.getRevokedList(sdf.format(rdf.parse(fromDate)),sdf.format(rdf.parse(toDate)));
	}

}

package com.vts.ems.ithelpdesk.service;

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

import com.vts.ems.ithelpdesk.dao.helpdeskDao;
import com.vts.ems.ithelpdesk.dto.itheldeskdto;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
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
	public List<Object[]> getHelpDeskList(String empno) throws Exception {
		
		return dao.getHelpDeskList(empno);
	}

	


	@Override
	public long saveTicket(itheldeskdto dto, String userId) throws Exception {
		
		MultipartFile FormFile = dto.getFormFile();
		System.out.println("path"+FormFile);
		
		LocalDate today= LocalDate.now();
		String start="";
		start=String.valueOf(today.getYear());
		System.out.println("yearrrr"+start);
		
		
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
		
		
		TicketNumber="IT"+start+"-"+maxTicketId;
		System.out.println("ticketnumber-------"+TicketNumber);
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
			
				
				
		
		return  dao.saveTicket(desk);
		
	}

	private static void saveFile(String FilePath, String FileName, MultipartFile multiparfile) throws IOException {
		
		logger.info(new Date() +"Inside SERVICE saveFile ");
	    Path uploadPath = Paths.get(FilePath);
	    System.out.println("path"+uploadPath);
	          
	    if (!Files.exists(uploadPath)) {
	    	Files.createDirectories(uploadPath);
	    	
	    }
	       
	    try (InputStream inputStream = multiparfile.getInputStream()) {
	    	Path filePath = uploadPath.resolve(FileName);
	    	
	    	System.out.println("savepath"+filePath);
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
	public long assignedTicket(itheldeskdto dto) throws Exception {
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
				
				desk.setTicketId(Long.parseLong(dto.getTicketId()));
				desk.setAssignedTo(dto.getAssignedTo());
				desk.setPriority(dto.getPriority());
				desk.setAssignedDate(sdf1.format(new Date()));
				desk.setTicketStatus("A");
			   desk.setAssignedBy(dto.getAssignedBy());
			
				
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
	public long reAssignedTicket(itheldeskdto dto) throws Exception {
		
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setAssignedTo(dto.getAssignedTo());
		desk.setAssignedDate(sdf1.format(new Date()));
		desk.setPriority(dto.getPriority());
		desk.setTicketStatus("A");
	    desk.setAssignedBy(dto.getAssignedBy());
	
		
       return dao.reAssignedTicket(desk);
		
		
	}

	@Override
	public List<Object[]> getRecievedList(String empno) throws Exception {
		
		return dao.getRecievedList(empno);
	}

	

	@Override
	public long ForwardTicket(itheldeskdto dto) throws Exception {
		
       HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setCWRemarks(dto.getCWRemarks());
		desk.setForwardedDate(sdf1.format(new Date()));
		 desk.setTicketStatus("F");
		 
	     return dao.ForwardTicket(desk);
	}

	@Override
	public List<Object[]> getForwardedList() throws Exception {
		
		return dao.getForwardedList();
	}

	@Override
	public long ReturnTicket(itheldeskdto dto) throws Exception {
		
		 HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
			
			desk.setTicketId(Long.parseLong(dto.getTicketId()));
			desk.setARemarks(dto.getARemarks());
			desk.setReturneddate(sdf1.format(new Date()));
			desk.setAssignedBy(dto.getAssignedBy());
			 desk.setTicketStatus("R");
			 
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
	public long closeTicket(itheldeskdto dto) throws Exception {
		
		HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		desk.setFeedBackRequired(dto.getFeedBackRequired());
		desk.setClosedDate(sdf1.format(new Date()));
		desk.setClosedBy(dto.getClosedBy());
		 desk.setTicketStatus("C");
		 
	     return dao.closeTicket(desk);
	}


	@Override
	public List<Object[]> getClosedList() throws Exception {
		
		return dao.getClosedList();
	}
	
   @Override
	public List<Object[]> getTicketClosedList(String ticketId) throws Exception {
		
		return dao.getTicketClosedList(ticketId);
	}

  @Override
  public long savefeedback(itheldeskdto dto) throws Exception {
	
	  HelpdeskTicket desk=dao.GetTicket(dto.getTicketId());
		
		desk.setTicketId(Long.parseLong(dto.getTicketId()));
		
		desk.setFeedback(dto.getFeedback());
		 desk.setTicketStatus("C");
		 
	     return dao.savefeedback(desk);
  }




     @Override
     public List<Object[]> getTicketRaisedDetails(String empNo, String fromDate, String toDate) throws Exception {
	
	return dao.getTicketRaisedDetails(empNo,sdf.format(rdf.parse(fromDate)),sdf.format(rdf.parse(toDate)));
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
     	
     	return dao.TicketCategoryEdit(helpdeskCategory);
     }




     @Override
     public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception {
     	
     	return dao.getTicketCategoryById(tcId);
     }


 }

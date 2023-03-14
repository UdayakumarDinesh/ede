package com.vts.ems.ithelpdesk.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.vts.ems.ithelpdesk.dto.ItHeldeskDto;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskSubCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
import com.vts.ems.ithelpdesk.service.HelpdeskService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class HelpdeskController {
	
	private static final Logger logger = LogManager.getLogger(HelpdeskController.class);
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	//private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	HelpdeskService service;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	
	
	
	@RequestMapping(value = "ITTicketList.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String TicketList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String UserId=(String)ses.getAttribute("Username");
		
		List<Object[]> TicketRaisedlist=null;
		logger.info(new Date() +"Inside ITTicketList.htm "+UserId);
		try {
			
			ses.setAttribute("SidebarActive","ITTicketList_htm");	
			String fromDate=req.getParameter("FromDate");
			String toDate=req.getParameter("ToDate");	
			
			String EmpNo=ses.getAttribute("EmpNo").toString();
			System.out.println("Employee No"+EmpNo);
			
			List<Object[]> CategoryList=service.getCategoryList();
			List<Object[]> HelpDeskUserList=(List<Object[]>) service.getHelpDeskList(EmpNo);
			
			
			if(fromDate==null  && toDate==null) 
			{
				String fd = LocalDate.now().minusMonths(1).toString();
				String td = LocalDate.now().toString();
				fromDate=rdf.format(sdf.parse(fd.toString()));
				toDate=rdf.format(sdf.parse(td.toString()));
			}
			
			if(EmpNo!=null && fromDate!=null && fromDate!=null) {
				 TicketRaisedlist=service.getTicketRaisedDetails(EmpNo,fromDate,toDate);				
				req.setAttribute("EmpNo", EmpNo);
			}
			
			
			req.setAttribute("frmDt", fromDate);
			req.setAttribute("toDt",  toDate);
			req.setAttribute("HelpDeskUserList",HelpDeskUserList );
			req.setAttribute("CategoryList",CategoryList );
			
			
		 
		return "ithelpdesk/TicketList";
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ITTicketList.htm "+UserId, e);
			return "static/Error";
		}
	
	}
	
	 @RequestMapping(value = "GetSubCategoryListAjax.htm", method = RequestMethod.GET)
		public @ResponseBody String GetSubCategoryListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
		{
		    Gson json = new Gson();
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside GetSubCategoryListAjax.htm "+Username);
			List<Object[]> SubCategoryList=null;
			
			
			try {
				
				String CategoryId=req.getParameter("selectedValue");
				SubCategoryList =  service.getSubCategoryList(CategoryId);
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetSubCategoryListAjax.htm"+Username, e);
				SubCategoryList=new ArrayList<>();
			}
			
			
			return json.toJson(SubCategoryList);
		}
	
      @RequestMapping(value="ITHelpDeskAddSubmit.htm",method= {RequestMethod.GET,RequestMethod.POST})
       public String TicketAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("FormFile") MultipartFile FormFile) {
	
    	  String UserId=(String)ses.getAttribute("Username");
    	  String EmpNo =  ses.getAttribute("EmpNo").toString();
    	  logger.info(new Date() +"Inside ITHelpDeskAddSubmit.htm "+UserId);
    	  try {
    		  
  		       ItHeldeskDto dto= ItHeldeskDto.builder()
    				  .EmpNo(EmpNo)
    				  .FormFile(FormFile)
    				  .FileName(EmpNo)
    				  .TicketCategoryId(req.getParameter("Category"))
    				  .TicketSubCategoryId(req.getParameter("SubCategory"))
    				  .TicketDesc(req.getParameter("Description"))
    				  .TicketStatus("I")
    				  .build();
    		  
    			
    		  Long save=service.saveTicket(dto,UserId);
    		  
    		  if (save > 0) {
  				
                  redir.addAttribute("result", "Ticket Raised Successfully ");

 			} else {
                  redir.addAttribute("resultfail", "Ticket Raise Unsuccessful");
 			}
    		  redir.addAttribute("EmpNo",EmpNo );
    		  return "redirect:/ITTicketList.htm"; 
    		  
    	  }catch (Exception e) {
  			e.printStackTrace();
  			logger.error(new Date() +" Inside ITTicketList.htm "+UserId, e);
  			return "static/Error";
  		}
      }
    	  
    	  @RequestMapping(value = "TicketFormDownload.htm")
    	    public void TicketFormDownload(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
    		{				
    			String UserId=(String)ses.getAttribute("Username");
    			logger.info(new Date() +"Inside TicketFormDownload.htm "+UserId);
    			try {
    				
    				String TicketId=req.getParameter("TicketId");
    				System.out.println("TicketId"+TicketId);
    				HelpdeskTicket form=service.GetTicketId(TicketId);
    				
    				res.setContentType("Application/octet-stream");	
    				File my_file=new File(emsfilespath+form.getFilePath());
    				res.setHeader("Content-disposition","attachment;filename="+form.getFileName());
    				OutputStream out=res.getOutputStream();
    				
    				FileInputStream in=new FileInputStream(my_file);
    				byte[] buffer=new byte[4096];
    				int length;
    				while((length=in.read(buffer))>0){
    				out.write(buffer,0,length);
    				}
    				in.close();
    				out.flush();
    				out.close();
    			}catch(Exception e) {
    				logger.error(new Date() +"Inside TicketFormDownload.htm "+UserId,e);
    				e.printStackTrace();
    			}
    	    
      }

    	  @RequestMapping(value = "TicketEdit.htm",method= {RequestMethod.GET,RequestMethod.POST} )
    		public String TicketEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
    		{
   			String UserId=(String)ses.getAttribute("Username");
    			logger.info(new Date() +"Inside TicketEdit.htm "+UserId);
   			try {
   				
   				String TicketId = req.getParameter("TicketId");
   				System.out.println("Ticket Id...."+TicketId);
   				HelpdeskTicket desk =  service.GetTicket(TicketId);
   				List<Object[]> category=service.getCategoryList();
   				String CategoryId=req.getParameter("CategoryId1");
   				System.out.println(CategoryId);
   				List<Object[]> subcategory=service.getSubCategoryList(CategoryId);
   				
   				    req.setAttribute("category", category);
   				    req.setAttribute("subcategory", subcategory);
    				req.setAttribute("desk", desk);
    				return "ithelpdesk/TicketEdit";
    		        	 
   			} catch (Exception e) {
    				  logger.error(new Date() +"Inside TicketEdit.htm "+UserId ,e);
  				  e.printStackTrace();
   				  return "static/Error";
   			}
   		}
    	  
    	  @RequestMapping(value = "TicketUpdate.htm")
    		public String TicketUpdate(HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("FormFile") MultipartFile FormFile) throws Exception
    		{
    			String UserId=(String)ses.getAttribute("Username");
    			 String EmpId =  ses.getAttribute("EmpId").toString();
    			logger.info(new Date() +"Inside TicketUpdate.htm "+UserId);
    			try {
    				
    				String TicketId = req.getParameter("TicketId");
    				
    				ItHeldeskDto dto =   ItHeldeskDto.builder()
    										.TicketId(TicketId)
    										.FormFile(FormFile)
    										.TicketDesc(req.getParameter("Description"))
    										.TicketCategoryId(req.getParameter("Category"))
    					    				.TicketSubCategoryId(req.getParameter("SubCategory"))
    					    				.ModifiedBy(UserId)
    					    			     .build();
    			
    				long count=service.ticketUpdate(dto);
    		        if (count > 0) {
    					 redir.addAttribute("result", "Ticket Updated Successfully");
    				} else {
    					 redir.addAttribute("resultfail", "Ticket Update Unsuccessfull");
    				}
    		        
    		        
    		        return "redirect:/ITTicketList.htm";
    		        
    			} catch (Exception e) {
    				  logger.error(new Date() +"Inside TicketUpdate.htm "+UserId ,e);
    				  e.printStackTrace();
    				  return "static/Error";
    			}
    		}
    	  
   
    
    	  
    	  @RequestMapping(value = "TicketDelete.htm")
    		public String EMSNoticeDelete( HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
    		{
    			String UserId=(String)ses.getAttribute("Username");
    			logger.info(new Date() +"Inside TicketDelete.htm "+UserId);
    			try {
    				String TicketId=req.getParameter("TicketId");
    				
    							
    				long count = service.TicketDelete(TicketId);
    				
    				if (count != 0) {
    					redir.addAttribute("result", "Ticket Deleted Successsfull");
    				} else {
    					redir.addAttribute("resultfail", "Ticket Delete UnSuccesssful");
    				}
    				
    			   
    				return "redirect:/ITTicketList.htm";
    			} catch (Exception e) {
    				  logger.error(new Date() +"Inside TicketDelete.htm "+UserId ,e);
    				  e.printStackTrace();
    				  return "static/Error";
    			}
    	  
}
    	  @RequestMapping(value = "TicketPending.htm", method = {RequestMethod.GET,RequestMethod.POST})
    		public String TicketApproval(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
    		{
    			String UserId=(String)ses.getAttribute("Username");
    			logger.info(new Date() +"Inside TicketPending.htm "+UserId);
    			try {
    				ses.setAttribute("SidebarActive","TicketPending_htm");	
    				
    				List<Object[]> TicketPendList=(List<Object[]>) service.getPendingList();
    			    req.setAttribute("TicketPendList",TicketPendList );
    				
    				
				return "ithelpdesk/TicketPending";
    			
             }
    			 catch (Exception e) {
   				  logger.error(new Date() +"Inside TicketPending.htm "+UserId ,e);
   				  e.printStackTrace();
   				  return "static/Error";
     }
  }
    	  
    	  @RequestMapping(value = "GetTicketDataAjax.htm", method = RequestMethod.GET)
    		public @ResponseBody String GetTicketDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
    		{
    		    Gson json = new Gson();
    			String Username = (String) ses.getAttribute("Username");
    			logger.info(new Date() +"Inside GetTicketDataAjax.htm "+Username);
    			List<Object[]> TicketList=null;
    			
    			List<Object[]> CaseworkerList=null;
    			try {
    				String TicketId=req.getParameter("TicketId");
    				
    				TicketList =  service.getTicketList(TicketId);
    				
    				System.out.println("List------"+CaseworkerList);
    				req.setAttribute("CaseworkerList", CaseworkerList);
    			} catch (Exception e) {
    				e.printStackTrace();
    				logger.error(new Date() +" Inside GetTicketDataAjax.htm "+Username, e);
    				TicketList=new ArrayList<>();
    			}
    			
    			
    			return json.toJson(TicketList);
    		}
    	  
    	  
    	  @RequestMapping(value = "GetCaseWorkerListAjax.htm", method = RequestMethod.GET)
    		public @ResponseBody String GetTestMainListAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
    		{
    		     Gson json = new Gson();
    			String Username = (String) ses.getAttribute("Username");
    			logger.info(new Date() +"Inside GetCaseWorkerListAjax.htm "+Username);
    			List<Object[]> CaseworkerList=null;
    			try {
    				
    				CaseworkerList = service.getCaseWorkerList();
    				System.out.println("List------"+CaseworkerList);
    				
    			} catch (Exception e) {
    				e.printStackTrace();
    				logger.error(new Date() +" Inside GetCaseWorkerListAjax.htm "+Username, e);
    			}
    			
    			return json.toJson(CaseworkerList);
    		}

    	  
    	  
    	  @RequestMapping(value = "TicketAssign.htm",method = {RequestMethod.GET,RequestMethod.POST} )
    		public String TicketAssign( HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
    		{
    			String UserId=(String)ses.getAttribute("Username");
    			String EmpNo =  ses.getAttribute("EmpNo").toString();
    			
    			logger.info(new Date() +"Inside TicketAssign.htm "+UserId);
    			try {
    				
    				String TicketId=req.getParameter("TicketId1");
    				System.out.println("Ticket id--------"+TicketId);
    				ses.setAttribute("SidebarActive","TicketAssign_htm");	
    				 System.out.println("asignto--------"+req.getParameter("AssignTo"));
    				 
    				
    					
    					
    				ItHeldeskDto dto= ItHeldeskDto.builder()
    						  .TicketId(TicketId)
    	    				  //.EmpId(EmpId)
    						 
    	    				  .AssignedTo((req.getParameter("AssignTo")))
    	    				  .Priority(req.getParameter("Priority"))
    	    				  .AssignedBy(EmpNo)
    	    				  .build();
    				
    				
    				 long save=service.assignedTicket(dto);
    	    		  
    	    		  if (save > 0) {
    	  				
    	                  redir.addAttribute("result", "Ticket Assigned Successfully ");

    	 			} else {
    	                  redir.addAttribute("resultfail", "Ticket Assigned Unsuccessful");
    	 			}
    				
    	    		  redir.addAttribute("EmpNo",EmpNo );
					/* List<Object[]> TicketPendList=(List<Object[]>) service.getPendingList(); */
    				
    				
    				
					/* req.setAttribute("TicketPendList",TicketPendList ); */
    				
    				
    			 
    			return "redirect:/TicketPending.htm";
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    				logger.error(new Date() +" Inside TicketAssign.htm "+UserId, e);
    				return "static/Error";
    			}
    	

    		}
    	  
    	  @RequestMapping(value = "TicketAssigned.htm", method = {RequestMethod.GET,RequestMethod.POST})
  		 public String TicketAssigned(Model model,HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
  		{
  			String UserId=(String)ses.getAttribute("Username");
  			String EmpId =  ses.getAttribute("EmpId").toString();
  			
  			
  			logger.info(new Date() +"Inside TicketAssigned.htm "+UserId);
  			try {
  				ses.setAttribute("SidebarActive","TicketAssigned_htm");
  				
               
				
  				List<Object[]> TicketAssignedList=(List<Object[]>)service.getAssignedList();
  				
  				req.setAttribute("TicketAssignedList", TicketAssignedList);
  				req.setAttribute("EmpId",EmpId);
  			
				return "ithelpdesk/TicketAssigned";
  			
           }
  			 catch (Exception e) {
 				  logger.error(new Date() +"Inside TicketAssigned.htm "+UserId ,e);
 				  e.printStackTrace();
 				  return "static/Error";
   }
}
    	  
    	  @RequestMapping(value = "GetTicketAssignedDataAjax.htm", method = RequestMethod.GET)
  		public @ResponseBody String GetTicketAssignedDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
  		{
  		    Gson json = new Gson();
  			String Username = (String) ses.getAttribute("Username");
  			logger.info(new Date() +"Inside GetTicketAssignedDataAjax.htm "+Username);
  			List<Object[]> TicketAssignedList=null;
  			
  			
  			try {
  				String TicketId=req.getParameter("TicketId");
  				
  				TicketAssignedList =  service.getTicketAssignedList(TicketId);
  				
  				
  				
  			} catch (Exception e) {
  				e.printStackTrace();
  				logger.error(new Date() +" Inside GetTicketAssignedDataAjax.htm"+Username, e);
  				TicketAssignedList=new ArrayList<>();
  			}
  			
  			
  			return json.toJson(TicketAssignedList);
  		}
    	  
    	  
    	  
    	  @RequestMapping(value = "TicketReAssigned.htm",method = {RequestMethod.GET,RequestMethod.POST} )
  		public String TicketReAssign( HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
  		{
  			String UserId=(String)ses.getAttribute("Username");
  			String EmpNo =  ses.getAttribute("EmpNo").toString();
  			
  			logger.info(new Date() +"Inside TicketReAssigned.htm "+UserId);
  			try {
  				
  				String TicketId=req.getParameter("TicketId1");
  				System.out.println("Ticket id--------"+TicketId);
  				//ses.setAttribute("SidebarActive","TicketAssigned_htm");	
  				 System.out.println("asignto--------"+req.getParameter("AssignTo"));
  				 
  				ItHeldeskDto dto= ItHeldeskDto.builder()
  						  .TicketId(TicketId)
  	    				  //.EmpId(EmpId)
  						 
  	    				  .AssignedTo((req.getParameter("AssignTo")))
  	    				  .Priority(req.getParameter("Priority"))
  	    				  .AssignedBy(EmpNo)
  	    				  .build();
  				
  				
  				 long save=service.reAssignedTicket(dto);
  	    		  
  	    		  if (save > 0) {
  	  				
  	                  redir.addAttribute("result", "Ticket ReAssigned Successfully ");

  	 			} else {
  	                  redir.addAttribute("resultfail", "Ticket ReAssigned Unsuccessful");
  	 			}
  				
  	    		  redir.addAttribute("EmpNo",EmpNo );
					
  				
  				
  			 
  			return "redirect:/TicketAssigned.htm";
  			}
  			catch (Exception e) {
  				e.printStackTrace();
  				logger.error(new Date() +" Inside TicketReAssigned.htm"+UserId, e);
  				return "static/Error";
  			}
  	
  
    	  
      }
    	  
    	  
    	  @RequestMapping(value = "TicketRecieved.htm", method = {RequestMethod.GET,RequestMethod.POST})
   		 public String TicketRecieved(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
   		{
   			String UserId=(String)ses.getAttribute("Username");
   			
   			String EmpNo=ses.getAttribute("EmpNo").toString();
			System.out.println("Employee No"+EmpNo);
   			
   			logger.info(new Date() +"Inside TicketRecieved.htm "+UserId);
   			try {
   				ses.setAttribute("SidebarActive","TicketRecieved_htm");
   				
                System.out.println("Empid---"+EmpNo);
   				List<Object[]> TicketRecievedList=service.getRecievedList(EmpNo);
   	
   				req.setAttribute("TicketRecievedList", TicketRecievedList);
   				req.setAttribute("EmpId",EmpNo);
   		
 				return "ithelpdesk/TicketRecieved";
   			
            }
   			 catch (Exception e) {
  				  logger.error(new Date() +"Inside TicketRecieved.htm "+UserId ,e);
  				  e.printStackTrace();
  				  return "static/Error";
    }
    	  
    	  
   }
    	  
    	  
     
    	  @RequestMapping(value = "TicketForward.htm",method = {RequestMethod.GET,RequestMethod.POST} )
    		public String TicketForward( HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
    		{
    			String UserId=(String)ses.getAttribute("Username");
    			String EmpId =  ses.getAttribute("EmpId").toString();
    			
    			logger.info(new Date() +"Inside TicketForward.htm "+UserId);
    			try {
    				
    				String TicketId=req.getParameter("TicketId1");
    				System.out.println("Ticket id--------"+TicketId);
    				ItHeldeskDto dto= ItHeldeskDto.builder()
    						  .TicketId(TicketId)
    	    				
    						 .CWRemarks(req.getParameter("Remarks"))
    	    				  
    	    				  .build();
    				
    				
    				 long save=service.ForwardTicket(dto);
    	    		  
    	    		  if (save > 0) {
    	  				
    	                  redir.addAttribute("result", "Ticket Forwarded Successfully ");

    	 			} else {
    	                  redir.addAttribute("resultfail", "Ticket Forward Unsuccessful");
    	 			}
    				
    	    		  redir.addAttribute("EmpId",EmpId );
  				
    			return "redirect:/TicketRecieved.htm";
    			}
    			catch (Exception e) {
    				e.printStackTrace();
    				logger.error(new Date() +" Inside TicketForward.htm"+UserId, e);
    				return "static/Error";
    			}
    	
           }
    	 
    
    	  
    	  @RequestMapping(value = "TicketForwarded.htm", method = {RequestMethod.GET,RequestMethod.POST})
 		 public String TicketForwarded(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
 		{
 			String UserId=(String)ses.getAttribute("Username");
 			
 			
 			
 			logger.info(new Date() +"Inside TicketForwarded.htm"+UserId);
 			try {
 				ses.setAttribute("SidebarActive","TicketForwarded_htm");
 				
              
 				List<Object[]> TicketForwardedList=service.getForwardedList();
 	
 				req.setAttribute("TicketForwardedList", TicketForwardedList);
 				
 		
				return "ithelpdesk/TicketForwarded";
 			
          }
 			 catch (Exception e) {
				  logger.error(new Date() +"Inside TicketForwarded.htm "+UserId ,e);
				  e.printStackTrace();
				  return "static/Error";
  }
 	  
 } 
    	  
    	  @RequestMapping(value = "TicketReturn.htm", method = {RequestMethod.GET,RequestMethod.POST})
    		 public String Ticketreturn(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
    		{
    		  String UserId=(String)ses.getAttribute("Username");
  			String EmpNo =  ses.getAttribute("EmpNo").toString();
  			
  			logger.info(new Date() +"Inside TicketReturn.htm "+UserId);
  			try {
  				
  				String TicketId=req.getParameter("TicketId2");
  				System.out.println("Ticket id-++------"+TicketId);
  				ItHeldeskDto dto= ItHeldeskDto.builder()
  						  .TicketId(TicketId)
  						  .ARemarks(req.getParameter("Remarks"))
  						  .AssignedBy(EmpNo)
  	    				  .build();
  				
  				
  				 long save=service.ReturnTicket(dto);
  	    		  
  	    		  if (save > 0) {
  	  				
  	                  redir.addAttribute("result", "Ticket Returned Successfully ");

  	 			} else {
  	                  redir.addAttribute("resultfail", "Ticket Return Unsuccessful");
  	 			}
  				
  	    		  redir.addAttribute("EmpNo",EmpNo );
				
  			return "redirect:/TicketForwarded.htm";
    			
             }
    			 catch (Exception e) {
   				  logger.error(new Date() +"Inside TicketReturn.htm "+UserId ,e);
   				  e.printStackTrace();
   				  return "static/Error";
     }
    	  
    	  
   }
    	  
   	  @RequestMapping(value = "TicketClose.htm", method = {RequestMethod.GET,RequestMethod.POST})
  		 public String TicketReturned(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
 		{
 			String UserId=(String)ses.getAttribute("Username");
 			String EmpNo =  ses.getAttribute("EmpNo").toString();
 			
  			
  			logger.info(new Date() +"Inside TicketClose.htm "+UserId);
  			try {
 				
  				String TicketId=req.getParameter("TicketId3");
				System.out.println("Ticket id--------"+TicketId);
				ItHeldeskDto dto= ItHeldeskDto.builder()
						  .TicketId(TicketId)
	    				  .ClosedBy(EmpNo)
						  .FeedBackRequired(req.getParameter("Feedback"))
	    				  .build();
				
				
				 long save=service.closeTicket(dto);
	    		  
	    		  if (save > 0) {
	  				
	                  redir.addAttribute("result", "Ticket Closed Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "Ticket Closed Unsuccessful");
	 			}
				
	    		  redir.addAttribute("EmpNo",EmpNo );
				return "redirect:/TicketClosed.htm";
  			
           }
 			 catch (Exception e) {
 				  logger.error(new Date() +"Inside TicketClose.htm "+UserId ,e);
				  e.printStackTrace();
				  return "static/Error";
   }
    	  
    	  
    	  
  }
   	  
   	  
   	 @RequestMapping(value = "TicketClosed.htm", method = {RequestMethod.GET,RequestMethod.POST})
		 public String TicketClosed(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String fromDate=req.getParameter("FromDate");
			String toDate=req.getParameter("ToDate");	
			
			
			logger.info(new Date() +"Inside TicketClosed.htm"+UserId);
			try {
				ses.setAttribute("SidebarActive","TicketClosed_htm");
				
				if(fromDate==null  && toDate==null) 
				{
					String fd = LocalDate.now().minusMonths(1).toString();
					String td = LocalDate.now().toString();
					fromDate=rdf.format(sdf.parse(fd.toString()));
					toDate=rdf.format(sdf.parse(td.toString()));
				}
				
				
				req.setAttribute("frmDt", fromDate);
				req.setAttribute("toDt",  toDate);
          
				List<Object[]> TicketClosedList=service.getClosedList();
	
				req.setAttribute("TicketClosedList", TicketClosedList);
				
		
			return "ithelpdesk/TicketClosed";
			
      }
			 catch (Exception e) {
			  logger.error(new Date() +"Inside TicketClosed.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
  }
	  
    
   }
   	 
   	 
   	 @RequestMapping(value = "GetTicketClosedDataAjax.htm", method = RequestMethod.GET)
		public @ResponseBody String GetTicketClosedDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
		{
		    Gson json = new Gson();
			String Username = (String) ses.getAttribute("Username");
			logger.info(new Date() +"Inside GetTicketClosedDataAjax.htm "+Username);
			List<Object[]> TicketClosedList=null;
			
			
			try {
				
				String TicketId=req.getParameter("TicketId");
				
				TicketClosedList =  service.getTicketClosedList(TicketId);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(new Date() +" Inside GetTicketClosedDataAjax.htm  "+Username, e);
				TicketClosedList=new ArrayList<>();
			}
			
			
			return json.toJson(TicketClosedList);
		}
   	 
   	 
   	 
     @RequestMapping(value = "TicketClosedFeedback.htm", method = {RequestMethod.GET,RequestMethod.POST})
		 public String TicketClosedFeedback(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
		{
			String UserId=(String)ses.getAttribute("Username");
			String EmpNo =  ses.getAttribute("EmpNo").toString();
			
			
			logger.info(new Date() +"Inside TicketClosedFeedback.htm "+UserId);
			try {
				
				String TicketId=req.getParameter("TicketId2");
			System.out.println("Ticket id--------"+TicketId);
			ItHeldeskDto dto= ItHeldeskDto.builder()
					  .TicketId(TicketId)
    				  .Feedback(req.getParameter("Feedback"))
    				  .build();
			
			
			 long save=service.savefeedback(dto);
    		  
    		  if (save > 0) {
  				
                  redir.addAttribute("result", "FeedBack Submitted Successfully ");

 			} else {
                  redir.addAttribute("resultfail", "FeedBack Submit Unsuccessful");
 			}
			
    		  redir.addAttribute("EmpNo",EmpNo );
			return "redirect:/ITTicketList.htm";
			
       }
			 catch (Exception e) {
				  logger.error(new Date() +"Inside TicketClosedFeedback.htm "+UserId ,e);
			  e.printStackTrace();
			  return "static/Error";
        }
	  
	  
	  
}

     @RequestMapping(value="TicketCategory.htm",method= {RequestMethod.POST,RequestMethod.GET})
     public String ticketCategory(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
     {
   	  String UserId=(String)ses.getAttribute("Username");
 		logger.info(new Date() +"Inside TicketCategory.htm"+UserId);
 		try {
 			String action=req.getParameter("action");
 			
 			if("ADD".equalsIgnoreCase(action)) {
 				
 				return "ithelpdesk/TicketCategoryAddEdit";
 			}
 			else if("EDIT".equalsIgnoreCase(action)) {
 				String tcId = req.getParameter("TicketCategoryId");
 				HelpdeskCategory category = service.getTicketCategoryById(Long.parseLong(tcId));
 				req.setAttribute("ticketcategory", category);
 				return "ithelpdesk/TicketCategoryAddEdit";
 			}
 			else {
 				List<Object[]> list = service.getTicketCategoryList();
 				req.setAttribute("ticketCategoryList", list);
 				
 				return "ithelpdesk/TicketCategory";
 			}
   	  
 		
 		} catch (Exception e) {
			logger.error(new Date() +"Inside TicketCategory.htm"+UserId,e);
			e.printStackTrace();
			return "static/Error";
		
 		}
     }
     
     @RequestMapping(value="TicketCategoryAdd.htm", method=RequestMethod.POST)
     public String ticketCategoryAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
   	    String UserId=(String)ses.getAttribute("Username");
 		logger.info(new Date() +"Inside TicketCategoryAdd.htm "+UserId);
 		try {
 			
 			
 				String ticketCategory = req.getParameter("ticketCategory");
 				
 				HelpdeskCategory category = new HelpdeskCategory();
 				category.setTicketCategory(ticketCategory.trim());
 				category.setCreatedBy(UserId);
 				category.setCreatedDate(sdtf.format(new Date()));
 				category.setIsActive(1);
 				Long result = service.TicketCategoryAdd(category);
 				if (result != 0) {
 					 redir.addAttribute("result", "Ticket Category Added Successfully");
 				} else {
 					 redir.addAttribute("resultfail", "Ticket Category Added Unsuccessfull");
 				}
 			
 			return "redirect:/TicketCategory.htm";
 		}catch (Exception e) {
			logger.error(new Date() +"Inside TicketCategoryAdd.htm"+UserId,e);
			e.printStackTrace();
			return "static/Error";
		
 		}
     }
     
     @RequestMapping(value="TicketCategoryEdit.htm", method=RequestMethod.POST)
     public String ticketCategoryEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
    	 String UserId=(String)ses.getAttribute("Username");
  		logger.info(new Date() +"Inside TicketCategoryEdit.htm"+UserId);
  		try {		
  				String ticketCategory = req.getParameter("ticketCategory");
  				String ticketCategoryId = req.getParameter("TicketCategoryId");
  				
  				HelpdeskCategory category = new HelpdeskCategory();
  				category.setTicketCategoryId(Long.parseLong(ticketCategoryId));
  				category.setTicketCategory(ticketCategory.trim());
  				category.setModifiedBy(UserId);
  				category.setModifiedDate(sdtf.format(new Date()));
  			
  				Long result = service.TicketCategoryEdit(category);
  				if (result != 0) {
  					 redir.addAttribute("result", "Ticket Category Updated Successfully");
  				} else {
  					 redir.addAttribute("resultfail", "Ticket Category Update Unsuccessfull");
  				}
  			
  			return "redirect:/TicketCategory.htm";
  		}catch (Exception e) {
 			logger.error(new Date() +"Inside TicketCategoryAdd.htm"+UserId,e);
 			e.printStackTrace();
 			return "static/Error";
 		
  		}
     }
     
     @RequestMapping(value="TicketCategoryAddcheck.htm",method=RequestMethod.GET)
     public @ResponseBody String ticketCategoryDuplicateAddCheck(HttpSession ses, HttpServletRequest req) throws Exception {
    	 Gson json = new Gson();
    	 String UserId=(String)ses.getAttribute("Username");
 		BigInteger duplicate=null;
 		logger.info(new Date() +"Inside TicketCategoryAddcheck.htm"+UserId);
 		try
 		{	  
 			String ticketCategory = req.getParameter("ticketCategory");
 			 duplicate = service.ticketCategoryDuplicateAddCheck(ticketCategory);
 			
 		}catch (Exception e) {
 			logger.error(new Date() +"Inside TicketCategoryAddcheck.htm"+UserId ,e);
 				e.printStackTrace(); 
 		}
 		  return json.toJson(duplicate);
 	}
     
     @RequestMapping(value="TicketCategoryEditcheck.htm",method=RequestMethod.GET)
     public @ResponseBody String ticketCategoryDuplicateEditCheck(HttpSession ses, HttpServletRequest req) throws Exception {
    	 Gson json = new Gson();
    	 String UserId=(String)ses.getAttribute("Username");
 		BigInteger duplicate=null;
 		logger.info(new Date() +"Inside TicketCategoryEditcheck.htm"+UserId);
 		try
 		{	  
 			String ticketCategory = req.getParameter("ticketCategory");
 			String ticketCategoryId = req.getParameter("ticketCategoryId");
 			 duplicate = service.ticketCategoryDuplicateEditCheck(ticketCategoryId,ticketCategory);
 			
 		}catch (Exception e) {
 			logger.error(new Date() +"Inside TicketCategoryEditcheck.htm"+UserId ,e);
 				e.printStackTrace(); 
 		}
 		  return json.toJson(duplicate);
 	}

     
     @RequestMapping(value="TicketSubCategory.htm", method= {RequestMethod.POST, RequestMethod.GET})
     public String ticketSubCategory(HttpServletRequest req, HttpSession ses) throws Exception {
    	
    	 String UserId=(String)ses.getAttribute("Username");
  		logger.info(new Date() +"Inside TicketSubCategory.htm"+UserId);
  		List<Object[]> list1 = service.getTicketCategoryList();
			
  		try {
  			String action=req.getParameter("action");
  			
  			if("ADD".equalsIgnoreCase(action)) {
  				req.setAttribute("ticketCategoryList", list1);
  				return "ithelpdesk/TicketSubCategoryAddEdit";
  			}
  			else if("EDIT".equalsIgnoreCase(action)) {
  				String tscId = req.getParameter("TicketSubCategoryId");
  				HelpdeskSubCategory category = service.getTicketSubCategoryById(Long.parseLong(tscId));
  				req.setAttribute("ticketsubcategory", category);
  				req.setAttribute("ticketCategoryList", list1);
  				return "ithelpdesk/TicketSubCategoryAddEdit";
  			}
  			else {
  				List<Object[]> list = service.getTicketSubCategoryList();
  				req.setAttribute("tscList", list);
  				
  				return "ithelpdesk/TicketSubCategory";
  			}
    	  
  		
  		} catch (Exception e) {
 			logger.error(new Date() +"Inside TicketSubCategory.htm"+UserId,e);
 			e.printStackTrace();
 			return "static/Error";
 		
  		}
     }
     
     @RequestMapping(value="TicketSubCategoryAdd.htm", method=RequestMethod.POST)
     public String ticketSubCategoryAdd(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
   	    String UserId=(String)ses.getAttribute("Username");
 		logger.info(new Date() +"Inside TicketSubCategoryAdd.htm "+UserId);
 		try {
 			
 			
 				String ticketSubCategory = req.getParameter("ticketSubCategory");
 				String ticketCategoryId = req.getParameter("ticketCategory");
 				
 				HelpdeskSubCategory category = new HelpdeskSubCategory();
 				category.setTicketCategoryId(Long.parseLong(ticketCategoryId));
 				category.setTicketSubCategory(ticketSubCategory.trim());
 				category.setCreatedBy(UserId);
 				category.setCreatedDate(sdtf.format(new Date()));
 				category.setIsActive(1);
 				Long result = service.TicketSubCategoryAdd(category);
 				if (result != 0) {
 					 redir.addAttribute("result", "Sub-Category Added Successfully");
 				} else {
 					 redir.addAttribute("resultfail", "Sub-Category Added Unsuccessfull");
 				}
 			
 			return "redirect:/TicketSubCategory.htm";
 		}catch (Exception e) {
			logger.error(new Date() +"Inside TicketSubCategoryAdd.htm"+UserId,e);
			e.printStackTrace();
			return "static/Error";
		
 		}
     }
     
     @RequestMapping(value="TicketSubCategoryEdit.htm", method=RequestMethod.POST)
     public String ticketSubCategoryEdit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception {
   	    
    	 String UserId=(String)ses.getAttribute("Username");
 		logger.info(new Date() +"Inside TicketSubCategoryEdit.htm "+UserId);
 		try {
 				String ticketSubCategory = req.getParameter("ticketSubCategory");
 				String ticketCategoryId = req.getParameter("ticketCategory");
 				String ticketSubCategoryId = req.getParameter("TicketSubCategoryId");
 				
 				HelpdeskSubCategory category = new HelpdeskSubCategory();
 				category.setTicketSubCategoryId(Long.parseLong(ticketSubCategoryId));
 				category.setTicketCategoryId(Long.parseLong(ticketCategoryId));
 				category.setTicketSubCategory(ticketSubCategory.trim());
 				category.setModifiedBy(UserId);
 				category.setModifiedDate(sdtf.format(new Date()));
 				
 				Long result = service.TicketSubCategoryEdit(category);
 				if (result != 0) {
 					 redir.addAttribute("result", "Sub-Category Updated Successfully");
 				} else {
 					 redir.addAttribute("resultfail", "Sub-Category Update Unsuccessfull");
 				}
 			
 			return "redirect:/TicketSubCategory.htm";
 		}catch (Exception e) {
			logger.error(new Date() +"Inside TicketSubCategoryEdit.htm"+UserId,e);
			e.printStackTrace();
			return "static/Error";
		
 		}
     }
     
     @RequestMapping(value="TicketSubCategoryAddcheck.htm",method=RequestMethod.GET)
     public @ResponseBody String ticketSubCategoryDuplicateAddCheck(HttpSession ses, HttpServletRequest req) throws Exception {
    	 Gson json = new Gson();
    	 String UserId=(String)ses.getAttribute("Username");
 		BigInteger duplicate=null;
 		logger.info(new Date() +"Inside TicketCategoryAddcheck.htm"+UserId);
 		try
 		{	  
 			String ticketSubCategory = req.getParameter("ticketSubCategory");
 			String ticketCategoryId = req.getParameter("ticketCategoryId");
 			 duplicate = service.ticketSubCategoryDuplicateAddCheck(ticketCategoryId,ticketSubCategory);
 		}catch (Exception e) {
 			logger.error(new Date() +"Inside TicketCategoryAddcheck.htm"+UserId ,e);
 				e.printStackTrace(); 
 		}
 		  return json.toJson(duplicate);
 	}
     
     @RequestMapping(value="TicketSubCategoryEditcheck.htm",method=RequestMethod.GET)
     public @ResponseBody String ticketSubCategoryDuplicateEditCheck(HttpSession ses, HttpServletRequest req) throws Exception {
    	 Gson json = new Gson();
    	 String UserId=(String)ses.getAttribute("Username");
 		BigInteger duplicate=null;
 		logger.info(new Date() +"Inside TicketCategoryAddcheck.htm"+UserId);
 		try
 		{	  
 			String ticketSubCategory = req.getParameter("ticketSubCategory");
 			String ticketCategoryId = req.getParameter("ticketCategoryId");
 			String ticketSubCategoryId = req.getParameter("ticketSubCategoryId");
 			
 			 duplicate = service.ticketSubCategoryDuplicateEditCheck(ticketSubCategoryId,ticketCategoryId,ticketSubCategory);
 		
 		}catch (Exception e) {
 			logger.error(new Date() +"Inside TicketCategoryAddcheck.htm"+UserId ,e);
 				e.printStackTrace(); 
 		}
 		  return json.toJson(duplicate);
 	}
     
}
    	  

    	  
    	  
    	  


	


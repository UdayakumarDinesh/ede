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
import com.vts.ems.Admin.Service.AdminService;
import com.vts.ems.ithelpdesk.dto.itheldeskdto;
import com.vts.ems.ithelpdesk.model.HelpDeskEmployee;
import com.vts.ems.ithelpdesk.model.HelpdeskAttachments;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskSubCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
import com.vts.ems.ithelpdesk.service.helpdeskService;
import com.vts.ems.utils.DateTimeFormatUtil;

@Controller
public class helpdeskcontroller {
	
	private static final Logger logger = LogManager.getLogger(helpdeskcontroller.class);
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	helpdeskService service;
	
	@Autowired
	AdminService adminservice;
	
	@Value("${EMSFilesPath}")
	private String emsfilespath;
	
	private static final String formmoduleid="31";
	@RequestMapping(value = "ITDashboard.htm")
	public String ITDashboard(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception {
		String Username = (String) ses.getAttribute("Username");
		logger.info(new Date() +"Inside ITDashboard.htm "+Username);		
		try {
			ses.setAttribute("formmoduleid", "31"); 
			ses.setAttribute("SidebarActive", "ITDashboard_htm");
			
			String logintype = (String)ses.getAttribute("LoginType");
		    String EmpNo = (String)ses.getAttribute("EmpNo");
		    List<Object[]> admindashboard = adminservice.HeaderSchedulesList("11" ,logintype); 
		    String fromDate=req.getParameter("FromDate");
			String toDate=req.getParameter("ToDate");	
			
			if(fromDate==null  && toDate==null) 
			{
				String fd = LocalDate.now().minusMonths(1).toString();
				String td = LocalDate.now().toString();
				fromDate=rdf.format(sdf.parse(fd.toString()));
				toDate=rdf.format(sdf.parse(td.toString()));
			}
			
			req.setAttribute("FromDate", fromDate);
			req.setAttribute("ToDate",  toDate);
			req.setAttribute("countdata", service.IThelpdeskDashboardCountData(EmpNo,logintype,fromDate,toDate));
			req.setAttribute("graphdata", service.IThelpdeskDashboardGraphData(fromDate,toDate));
			req.setAttribute("piechartdata", service.IThelpdeskDashboardPieChartData(fromDate,toDate));
			req.setAttribute("dashboard", admindashboard);
			ses.setAttribute("SidebarActive", "ITDashboard_htm");
			req.setAttribute("LoginType", logintype);
			return "ithelpdesk/ITDashboard";
		} catch (Exception e) {
			logger.error(new Date() +" Inside ITDashboard.htm "+Username, e);
			e.printStackTrace();	
			return "static/Error";
		}
	}
	

    @RequestMapping(value = "ITTicketList.htm", method = {RequestMethod.GET,RequestMethod.POST})
	public String TicketList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
	{
		String UserId=(String)ses.getAttribute("Username");
		ses.setAttribute("SidebarActive","ITTicketList_htm");	
		ses.setAttribute("formmoduleid", formmoduleid);
		logger.info(new Date() +"Inside ITTicketList.htm "+UserId);
		try {
			
			String fromDate=req.getParameter("FromDate");
			String toDate=req.getParameter("ToDate");	
			String EmpNo=ses.getAttribute("EmpNo").toString();
			List<Object[]> CategoryList=service.getCategoryList();
			
			if(fromDate==null  && toDate==null) 
			{
				String fd = LocalDate.now().minusMonths(1).toString();
				String td = LocalDate.now().toString();
				fromDate=rdf.format(sdf.parse(fd.toString()));
				toDate=rdf.format(sdf.parse(td.toString()));
			}
			List<Object[]> HelpDeskUserList=(List<Object[]>) service.getHelpDeskList(EmpNo,fromDate,toDate);
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
    		  
  		       itheldeskdto dto= itheldeskdto.builder()
    				  .EmpNo(EmpNo)
    				  .FormFile(FormFile)
    				  .FileName(EmpNo)
    				  .TicketCategoryId(req.getParameter("Category"))
    				  .TicketSubCategoryId(req.getParameter("SubCategory"))
    				  .TicketDesc(req.getParameter("Description"))
    				  .TicketStatus("I")
    				  .build();
    		  
    		  Long save=service.saveTicket(dto,UserId,EmpNo);
    		  
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
    				
    				String TicketId=req.getParameter("TICKETID");
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
    			
    			 @RequestMapping(value = "TicketForwardAttachmentDownload.htm")
    	    	    public void TicketForwardAttachment(HttpServletRequest req, HttpSession ses, HttpServletResponse res) throws Exception 
    	    		{				
    	    			String UserId=(String)ses.getAttribute("Username");
    	    			logger.info(new Date() +"Inside TicketForwardAttachment.htm "+UserId);
    	    			try {
    	    				
    	    				String AttachmentId=req.getParameter("AttachmentId");
    	    				HelpdeskAttachments attach=service.getattachId(AttachmentId);
    	    				res.setContentType("Application/octet-stream");	
    	    				File my_file=new File(emsfilespath+attach.getFilePath());
    	    				res.setHeader("Content-disposition","attachment;filename="+attach.getFileName());
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
    	    				logger.error(new Date() +"Inside TicketForwardAttachment.htm "+UserId,e);
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
   				HelpdeskTicket desk =  service.GetTicket(TicketId);
   				List<Object[]> category=service.getCategoryList();
   				List<Object[]> subcategory=service.getSubCategoryList();
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
    				itheldeskdto dto =   itheldeskdto.builder()
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
    			ses.setAttribute("SidebarActive","TicketPending_htm");	
    			ses.setAttribute("formmoduleid", formmoduleid);
    			try {
    				
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
    				req.setAttribute("CaseworkerList", CaseworkerList);
    			} catch (Exception e) {
    				e.printStackTrace();
    				logger.error(new Date() +" Inside GetTicketDataAjax.htm "+Username, e);
    				TicketList=new ArrayList<>();
    			}
    			
    			return json.toJson(TicketList);
    		}
    	  
    	  @RequestMapping(value = "GetReturnTicketDataAjax.htm", method = RequestMethod.GET)
  		public @ResponseBody String GetReturnTicketDataAjax(HttpServletRequest req, HttpServletResponse response, HttpSession ses) throws Exception 
  		{
  		    Gson json = new Gson();
  			String Username = (String) ses.getAttribute("Username");
  			logger.info(new Date() +"Inside GetReturnTicketDataAjax.htm "+Username);
  			List<Object[]> TicketList=null;
  			try {
  				String TicketId=req.getParameter("TicketId");
  				TicketList =  service.getTicketReturnedList(TicketId);
  				
  			} catch (Exception e) {
  				e.printStackTrace();
  				logger.error(new Date() +" Inside GetReturnTicketDataAjax.htm "+Username, e);
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
    			ses.setAttribute("SidebarActive","TicketAssign_htm");
    			logger.info(new Date() +"Inside TicketAssign.htm "+UserId);
    			try {
    				
    				String TicketId=req.getParameter("TicketId1");
    				String split= req.getParameter("AssignTo");
    				 if(split!=null && !split.equals("null")) {
    					 String s[]=split.split("/");
    					  String s1=s[0];
    					  String s2=s[1];
    				  
    				itheldeskdto dto= itheldeskdto.builder()
    						  .TicketId(TicketId)
    	    				  .AssignedTo(s1)
    	    				  .Priority(req.getParameter("Priority"))
    	    				  .AssignedBy(EmpNo)
    	    				  .build();
    				
    				 long save=service.assignedTicket(dto,UserId,EmpNo,s2);
    				
    	    		  if (save > 0) {
    	  				redir.addAttribute("result", "Ticket Assigned Successfully ");
                      }
    	    		  else {
    	                  redir.addAttribute("resultfail", "Ticket Assigned Unsuccessful");
    	 			}
    				 }
    				 redir.addAttribute("EmpNo",EmpNo );
    				  
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
  			logger.info(new Date() +"Inside TicketAssigned.htm "+UserId);
  			try {
  				ses.setAttribute("SidebarActive","TicketAssigned_htm");
  				List<Object[]> TicketAssignedList=(List<Object[]>)service.getAssignedList();
  				req.setAttribute("TicketAssignedList", TicketAssignedList);
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
  				String split= req.getParameter("AssignTo");
				 if(split!=null && !split.equals("null")) {
					 String s[]=split.split("/");
					  String s1=s[0];
					  String s2=s[1];
				  
  				itheldeskdto dto= itheldeskdto.builder()
  						  .TicketId(TicketId)
  	    				  .AssignedTo(s1)
  	    				  .Priority(req.getParameter("Priority"))
  	    				  .AssignedBy(EmpNo)
  	    				  .build();
  				 long save=service.reAssignedTicket(dto,UserId,EmpNo,s2);
  	    		  
  	    		  if (save > 0) {
  	  				 redir.addAttribute("result", "Ticket ReAssigned Successfully ");

  	 			} else {
  	                  redir.addAttribute("resultfail", "Ticket ReAssigned Unsuccessful");
  	 			}
  				
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
   			ses.setAttribute("SidebarActive","TicketRecieved_htm");
   			ses.setAttribute("formmoduleid", formmoduleid);
   			logger.info(new Date() +"Inside TicketRecieved.htm "+UserId);
   			try {
   				  
   				  List<Object[]> TicketRecievedList=service.getRecievedList(EmpNo);
   	              req.setAttribute("TicketRecievedList", TicketRecievedList);
   				  return "ithelpdesk/TicketRecieved";
   			}
   			 catch (Exception e) {
  				  logger.error(new Date() +"Inside TicketRecieved.htm "+UserId ,e);
  				  e.printStackTrace();
  				  return "static/Error";
    }
    	  
  }
    	 @RequestMapping(value = "TicketForward.htm",method = {RequestMethod.GET,RequestMethod.POST} )
    		public String TicketForward( HttpServletRequest req, HttpSession ses, RedirectAttributes redir,@RequestPart("FormFile") MultipartFile FormFile)  throws Exception 
    		{
    			String UserId=(String)ses.getAttribute("Username");
    			String EmpId =  ses.getAttribute("EmpId").toString();
    			String EmpNo=ses.getAttribute("EmpNo").toString();
    			
    			logger.info(new Date() +"Inside TicketForward.htm "+UserId);
    			try {
    				
    				String TicketId=req.getParameter("TicketId1");
    				itheldeskdto dto= itheldeskdto.builder()
    						  .TicketId(TicketId)
    	    				  .CWRemarks(req.getParameter("Remarks"))
    	    				  .FormFile(FormFile)
    	    				  .FileName(EmpNo)
    	    				  .build();
    				 long save=service.ForwardTicket(dto,UserId,EmpNo);
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
 			ses.setAttribute("SidebarActive","TicketForwarded_htm");
 			ses.setAttribute("formmoduleid", formmoduleid);
 			
 			logger.info(new Date() +"Inside TicketForwarded.htm"+UserId);
 			try {
 				
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
  			String EmpId =  ses.getAttribute("EmpId").toString();
  			
  			logger.info(new Date() +"Inside TicketReturn.htm "+UserId);
  			try {
  				
  				String split=req.getParameter("TicketId2");
  				if(split!=null && !split.equals("null")) {
					  String arr[]=split.split("/");
				      String s1=arr[0]; 
					  String s2=req.getParameter("ReturnTo");	  
				 
  				itheldeskdto dto= itheldeskdto.builder()
  						  .TicketId(s1)
  						  .ARemarks(req.getParameter("Remarks"))
  						  .AssignedBy(EmpNo)
  	    				  .build();
  				
  				 long save=service.ReturnTicket(dto,UserId,EmpNo,s2);
  	    		  if (save > 0) {
  	  				 redir.addAttribute("result", "Ticket Returned Successfully ");
                 } else {
  	                  redir.addAttribute("resultfail", "Ticket Return Unsuccessful");
  	 			}
  				
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
  		 public String TicketClose(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
 		{
 			String UserId=(String)ses.getAttribute("Username");
 			String EmpNo =  ses.getAttribute("EmpNo").toString();
 			logger.info(new Date() +"Inside TicketClose.htm "+UserId);
  			try {
 				
  				String split=req.getParameter("TicketId3");
  				 if(split!=null && !split.equals("null")) {
					  String arr[]=split.split("/");
					  String s1=arr[0]; 
					  String s2=req.getParameter("EmpNo");	  
				itheldeskdto dto= itheldeskdto.builder()
						  .TicketId(s1)
	    				  .ClosedBy(EmpNo)
						  .FeedBackRequired(req.getParameter("Feedback"))
	    				  .build();
				 long save=service.closeTicket(dto,UserId,EmpNo,s2);
	    		  if (save > 0) {
	  				 redir.addAttribute("result", "Ticket Closed Successfully ");

	 			} else {
	                  redir.addAttribute("resultfail", "Ticket Closed Unsuccessful");
	 			}
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
			ses.setAttribute("SidebarActive","TicketClosed_htm");
   			ses.setAttribute("formmoduleid", formmoduleid);
			String fromDate=req.getParameter("FromDate");
			String toDate=req.getParameter("ToDate");	
			logger.info(new Date() +"Inside TicketClosed.htm"+UserId);
			try {
				
				if(fromDate==null  && toDate==null) 
				{
					String fd = LocalDate.now().minusMonths(1).toString();
					String td = LocalDate.now().toString();
					fromDate=rdf.format(sdf.parse(fd.toString()));
					toDate=rdf.format(sdf.parse(td.toString()));
				}
				req.setAttribute("frmDt", fromDate);
				req.setAttribute("toDt",  toDate);
                List<Object[]> TicketClosedList=service.getClosedList(fromDate,toDate);
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
				
				String split=req.getParameter("TicketId2");
				if(split!=null && !split.equals("null")) {
					  String arr[]=split.split("/");
					  String s1=arr[0]; 
					  String s2=req.getParameter("EmpNo"); //assignedby empno
			    itheldeskdto dto= itheldeskdto.builder()
					  .TicketId(s1)
    				  .Feedback(req.getParameter("Feedback"))
    				  .FeedBackType(req.getParameter("FeedbackType"))
    				  .build();
			
			 long save=service.savefeedback(dto,UserId,EmpNo,s2);
    		  if (save > 0) {
  				
                  redir.addAttribute("result", "FeedBack Submitted Successfully ");

 			} else {
                  redir.addAttribute("resultfail", "FeedBack Submit Unsuccessful");
 			}
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
	  
			  @RequestMapping(value = "TicketRevokeList.htm" ,method= {RequestMethod.GET,RequestMethod.POST})
				public String TicketRevokeList(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
				{
					String UserId=(String)ses.getAttribute("Username");
					ses.setAttribute("SidebarActive","TicketRevokeList_htm");
					String fromDate=req.getParameter("FromDate");
					String toDate=req.getParameter("ToDate");	
					logger.info(new Date() +"Inside TicketRevokeList.htm "+UserId);
					try {
						if(fromDate==null  && toDate==null) 
						{
							String fd = LocalDate.now().minusMonths(1).toString();
							String td = LocalDate.now().toString();
							fromDate=rdf.format(sdf.parse(fd.toString()));
							toDate=rdf.format(sdf.parse(td.toString()));
						}
						req.setAttribute("frmDt", fromDate);
						req.setAttribute("toDt",  toDate);
						List<Object[]> revokedlist=service.getRevokedList(fromDate,toDate);
						req.setAttribute("revokedlist",  revokedlist);
					    return "ithelpdesk/TicketRevoked";
				        
					} catch (Exception e) {
						  logger.error(new Date() +"Inside TicketRevokeList.htm "+UserId ,e);
						  e.printStackTrace();
						  return "static/Error";
					}
				}	
			
	@RequestMapping(value="TicketCategory.htm",method= {RequestMethod.POST,RequestMethod.GET})
    public String ticketCategory(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)throws Exception
    {
  	  String UserId=(String)ses.getAttribute("Username");
		logger.info(new Date() +"Inside TicketCategory.htm"+UserId);
		ses.setAttribute("SidebarActive","TicketCategory_htm");
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
 			logger.error(new Date() +"Inside TicketCategoryEdit.htm"+UserId,e);
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
  		ses.setAttribute("SidebarActive","TicketSubCategory_htm");
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
     
     @RequestMapping(value = "HelpdeskEmployee.htm", method = {RequestMethod.GET,RequestMethod.POST})
  	public String HelpdeskEmployee(HttpServletRequest req, HttpSession ses, RedirectAttributes redir)  throws Exception 
  	{
  		String UserId=(String)ses.getAttribute("Username");
  		logger.info(new Date() +"Inside HelpdeskEmployee.htm "+UserId);
  		ses.setAttribute("SidebarActive","HelpdeskEmployee_htm");	
  		try {
  			
  			
  			String ValidTill=req.getParameter("ValidTill");	
  			String CurrentDate=req.getParameter("CurrentDate");	
  			String EmpNo=ses.getAttribute("EmpNo").toString();
  		    List<Object[]> EmployeeList=service.getEmployeeList();
  			List<Object[]> ContractEmployeeList=(List<Object[]>) service.getContractEmployee();
  			List<Object[]> HelpDeskEmployeeList=service.getHelpDeskEmployeeList();
  			
  			String fd = LocalDate.now().plusMonths(12).toString();
  			ValidTill=rdf.format(sdf.parse(fd.toString()));
  			String cd=LocalDate.now().toString();
  			CurrentDate=rdf.format(sdf.parse(cd.toString()));
 			
 			req.setAttribute("CurrentDate", CurrentDate);
  			req.setAttribute("ValidTill", ValidTill);
  			req.setAttribute("EmployeeList",EmployeeList );
  			req.setAttribute("ContractEmployeeList",ContractEmployeeList );
  			req.setAttribute("HelpDeskEmployeeList",HelpDeskEmployeeList );
  			
  		return "ithelpdesk/HelpDeskEmployee";
  		}
  		catch (Exception e) {
  			e.printStackTrace();
  			logger.error(new Date() +" Inside HelpdeskEmployee.htm "+UserId, e);
  			return "static/Error";
  		}
      
    } 
     
     @RequestMapping(value="EmployeeAddSubmit.htm",method= {RequestMethod.GET,RequestMethod.POST})
     public String EmployeeAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) {
	
  	  String UserId=(String)ses.getAttribute("Username");
  	  logger.info(new Date() +"Inside EmployeeAddSubmit.htm "+UserId);
  	  try {
  		       HelpDeskEmployee Employee=new HelpDeskEmployee();
		       Employee.setEmpNo(req.getParameter("Employee"));
		       Employee.setEmpType("P");
  		       Employee.setValidTill(sdf.format(rdf.parse(req.getParameter("ValidTill"))));
  		       Employee.setCreatedBy(UserId);
  		       Employee.setCreatedDate(sdf1.format(new Date()));
  		       Employee.setIsActive(1);
  			
  		  long save=service.EmployeeAddSubmit(Employee);
  		  
  		  if(save!=0) {
  			redir.addAttribute("result", "Employee Added Successfully");
  		  }
  		  else {
  			 redir.addAttribute("resultfail", "Employee Add Unsuccessful");
  		  }
		return "redirect:/HelpdeskEmployee.htm"; 
  		  
  	  }catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside EmployeeAddSubmit.htm "+UserId, e);
			return "static/Error";
		}
  
   }
     
     @RequestMapping(value="ContractEmployeeAddSubmit.htm",method= {RequestMethod.GET,RequestMethod.POST})
     public String CaseworkerAddSubmit(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) {
	
  	  String UserId=(String)ses.getAttribute("Username");
  	  
  	  logger.info(new Date() +"Inside ContractEmployeeAddSubmit.htm "+UserId);
  	  try {
  		  
		       HelpDeskEmployee Employee=new HelpDeskEmployee();
		       Employee.setEmpNo(req.getParameter("Caseworker"));
		       Employee.setEmpType("C");
  		       Employee.setValidTill(sdf.format(rdf.parse(req.getParameter("ValidTill"))));
  		       Employee.setCreatedBy(UserId);
  		       Employee.setCreatedDate(sdf1.format(new Date()));
  		       Employee.setIsActive(1);
  			
  		  long save=service.EmployeeAddSubmit(Employee);
  		  
  		  if(save!=0) {
  			redir.addAttribute("result", "ContractEmployee Added Successfully");
  		  }
  		  else {
  			 redir.addAttribute("resultfail", "ContractEmployee Add Unsuccessful");
  		  }
		 return "redirect:/HelpdeskEmployee.htm"; 
  		  
  	  }catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() +" Inside ContractEmployeeAddSubmit.htm "+UserId, e);
			return "static/Error";
		}
  
   }
     
     @RequestMapping(value = "EmployeeUpdate.htm")
		public String EmployeeUpdate(HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
		{
			String UserId=(String)ses.getAttribute("Username");
			 
			logger.info(new Date() +"Inside EmployeeUpdate.htm "+UserId);
			try {
				
				String HelpDeskEmpId=req.getParameter("HelpDeskEmpId");
				HelpDeskEmployee Employee=new HelpDeskEmployee();
			    Employee.setHelpDeskEmpId(Long.parseLong(HelpDeskEmpId));
				Employee.setValidTill(sdf.format(rdf.parse(req.getParameter("ValidTill"))));
				Employee.setModifiedBy(UserId);
				Employee.setModifiedDate(sdf1.format(new Date()));
			    long count=service.Employeeupdate(Employee);
				if (count > 0) {
					 redir.addAttribute("result", "Employee Updated Successfully");
				} else {
					 redir.addAttribute("resultfail", "Employee Update Unsuccessfull");
				}
		        return "redirect:/HelpdeskEmployee.htm";
		        
			} catch (Exception e) {
				  logger.error(new Date() +"Inside EmployeeUpdate.htm "+UserId ,e);
				  e.printStackTrace();
				  return "static/Error";
			}
		}
     
      @RequestMapping(value = "EmployeeDelete.htm")
		public String EmployeeDelete( HttpServletRequest req, HttpSession ses, RedirectAttributes redir) throws Exception
		{
			String UserId=(String)ses.getAttribute("Username");
			logger.info(new Date() +"Inside EmployeeDelete.htm "+UserId);
			try {
				String HelpDeskEmpId=req.getParameter("HelpDeskEmpId");
				long count = service.EmployeeDelete(HelpDeskEmpId);
				if (count != 0) {
					redir.addAttribute("result", "Employee Deleted Successsfull");
				} else {
					redir.addAttribute("resultfail", "Employee Delete UnSuccesssful");
				}
				return "redirect:/HelpdeskEmployee.htm";
			} catch (Exception e) {
				  logger.error(new Date() +"Inside EmployeeDelete.htm "+UserId ,e);
				  e.printStackTrace();
				  return "static/Error";
			} 
     
     }
    
}


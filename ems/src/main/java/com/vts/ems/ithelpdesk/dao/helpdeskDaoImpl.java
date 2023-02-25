package com.vts.ems.ithelpdesk.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
import com.vts.ems.utils.DateTimeFormatUtil;


@Transactional
@Repository
public class helpdeskDaoImpl implements helpdeskDao {
	
	private static final Logger logger = LogManager.getLogger(helpdeskDaoImpl.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@PersistenceContext
	EntityManager manager;
	
	private static final String HELPDESKLIST="SELECT b.TicketId,a.EmpNo,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.TicketStatus,c.TicketCategory,d.TicketSubCategory,b.FileName,b.FeedBackRequired,b.Feedback FROM helpdesk_tickets b,employee a ,helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1'AND c.TicketCategoryId=b.TicketCategoryId  AND d.TicketSubCategoryId=b.TicketSubCategoryId AND a.EmpNo=b.RaisedBy AND a.EmpNo=:EmpNo ORDER BY RaisedDate DESC";
	@Override
	public List<Object[]> getHelpDeskList(String empno) throws Exception {
		
		Query query=manager.createNativeQuery(HELPDESKLIST);
		query.setParameter("EmpNo", empno);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		
		return list;
	}
	
	
	@Override
	public long saveTicket(HelpdeskTicket desk) throws Exception {
		
	    manager.persist(desk);
		manager.flush();
		
		return desk.getTicketId();
	}
	
	@Override
	public HelpdeskTicket GetTicketId(String ticketId) throws Exception {
		try {
			HelpdeskTicket form = manager.find(HelpdeskTicket.class, Long.parseLong(ticketId));
			return form;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetTicketId() "+e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	private static final String MAXOFEMSTICKETID = "SELECT IFNULL(MAX(TicketId),0) as 'MAX' FROM helpdesk_tickets ";
	@Override
	public long MaxOfEmsTicketId() throws Exception 
	{
		try {
			Query query =  manager.createNativeQuery(MAXOFEMSTICKETID);
			BigInteger ticktid=(BigInteger)query.getSingleResult();
			return ticktid.longValue();
		}catch ( NoResultException e ) {
			logger.error(new Date() +"Inside DAO MaxOfEmsFormId "+ e);
			return 0;
		}
	}
		
	
	//public final static String TICKETEDIT="SELECT TicketDesc,TicketCategoryId,Priority,FilePath,TicketId FROM helpdesk_tickets   WHERE TicketId=:ticketid";
	@Override
	public HelpdeskTicket GetTicket(String ticketId) throws Exception {
		
		try {
			HelpdeskTicket desk = manager.find(HelpdeskTicket.class, Long.parseLong(ticketId));
			return desk;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetTicket() "+e);
			e.printStackTrace();
			return null;
		}	
	
	}
	
	
	public final static String TICKETDELETE="UPDATE helpdesk_tickets SET IsActive=:isactive WHERE TicketId=:ticketid";
	@Override
	public long TicketDelete(HelpdeskTicket ticketdelete) throws Exception {
	
		
		Query query=manager.createNativeQuery(TICKETDELETE);
		query.setParameter("isactive", 0);
		query.setParameter("ticketid", ticketdelete.getTicketId());
		long count= (long) query.executeUpdate();
		
		return count;
	}
	
	@Override
	public long ticketUpdate(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}
	public final static String TICKETPENDINGLIST= "SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,c.TicketCategory,d.TicketSubCategory,b.FileName,b.TicketStatus FROM helpdesk_tickets b,employee a , helpdesk_category c ,helpdesk_sub_category d WHERE b.isActive='1' AND c.TicketCategoryId=b.TicketCategoryId AND d.TicketSubCategoryId=b.TicketSubCategoryId  AND b.TicketStatus IN ('I','R') AND a.EmpNo=b.RaisedBy ORDER BY RaisedDate DESC ";
			
	@Override
	public List<Object[]> getTicketPending() throws Exception {
		
		Query query=manager.createNativeQuery(TICKETPENDINGLIST);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}
	public final static String TICKETLISTFORMODAL="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,c.TicketCategory,d.TicketSubCategory,b.FileName FROM helpdesk_tickets b,employee a,helpdesk_category c ,helpdesk_sub_category d WHERE b.isActive='1' AND c.TicketCategoryId=b.TicketCategoryId AND d.TicketSubCategoryId=b.TicketSubCategoryId  AND a.EmpNo=b.RaisedBy AND b.TicketId=:TicketId";
	@Override
	public List<Object[]> getTicketList(String ticketId) throws Exception {
		
		Query query=manager.createNativeQuery(TICKETLISTFORMODAL);
		query.setParameter("TicketId",ticketId );
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}
	public final static String CASEWORKERLIST="SELECT a.EmpNo,a.EmpName  FROM employee a,login b WHERE a.EmpId=b.EmpId AND b.LoginType='C'";
	@Override
	public List<Object[]> getCaseWorkerList() throws Exception {
		
		Query query=manager.createNativeQuery(CASEWORKERLIST);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}
	@Override
	public long assignedTicket(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}
	public final static String ASSIGNEDLIST="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.Priority ,c.TicketCategory,d.TicketSubCategory,b.FileName,b.AssignedDate,b.AssignedTo,b.TicketStatus,a1.EmpName AS caseworkername,a2.Empname AS assignedbyname,b.AssignedBy,a2.EmpNo AS adminid FROM helpdesk_tickets b,employee a ,employee a1,employee a2, helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1'AND c.TicketCategoryId=b.TicketCategoryId AND d.TicketSubCategoryId=b.TicketSubCategoryId AND a.EmpNo=b.RaisedBy AND b.TicketStatus  IN ('A') AND b.assignedTo=a1.EmpNo AND b.assignedBy=a2.EmpNo  ORDER BY AssignedDate DESC";
	@Override
	public List<Object[]> getAssignedList() throws Exception {
		
			Query query=manager.createNativeQuery(ASSIGNEDLIST);
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
		}
	
	public final static String TICKETASSIGNEDLIST="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,c.TicketCategory,d.TicketSubCategory,b.Priority,b.AssignedTo FROM helpdesk_tickets b,employee a,helpdesk_category c ,helpdesk_sub_category d WHERE b.isActive='1' AND c.TicketCategoryId=b.TicketCategoryId AND d.TicketSubCategoryId=b.TicketSubCategoryId  AND a.EmpNo=b.RaisedBy AND b.TicketId=:TicketId ";
	@Override
	public List<Object[]> getTicketAssignedList(String ticketId) throws Exception {
		Query query=manager.createNativeQuery(TICKETASSIGNEDLIST);
		query.setParameter("TicketId",ticketId );
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
		
	}
	@Override
	public long reAssignedTicket(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}
	public final static String TICKETRECIEVEDLIST="SELECT b.TicketId,a.EmpNo,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.Priority ,c.TicketCategory,d.TicketSubCategory,b.FileName,b.AssignedDate,b.Returneddate,b.ARemarks,b.AssignedTo,b.TicketStatus,a1.EmpName AS caseworkername,a2.Empname AS assignedbyname,b.AssignedBy FROM helpdesk_tickets b,employee a ,employee a1,employee a2, helpdesk_category c ,helpdesk_sub_category d WHERE b.isActive='1'AND c.TicketCategoryId=b.TicketCategoryId AND d.TicketSubCategoryId=b.TicketSubCategoryId AND a.EmpNo=b.RaisedBy AND b.TicketStatus  IN ('A','R') AND b.assignedTo=a1.EmpNo AND b.assignedBy=a2.EmpNo AND a1.EmpNo=:EmpNo ORDER BY AssignedDate DESC";
	@Override
	public List<Object[]> getRecievedList(String empno) throws Exception {
		
		Query query=manager.createNativeQuery(TICKETRECIEVEDLIST);
		query.setParameter("EmpNo", empno);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		
		return list;
	}
	
	@Override
	public long ForwardTicket(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}
	public final static String TICKETFORWARDEDLIST="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.Priority ,c.TicketCategory,d.TicketSubCategory,b.FileName,b.AssignedDate,b.AssignedTo,b.TicketStatus,a1.EmpName AS caseworkername,a2.Empname AS assignedbyname,b.AssignedBy,b.CWRemarks,b.ForwardedDate FROM helpdesk_tickets b,employee a ,employee a1,employee a2, helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1' AND c.TicketCategoryId=b.TicketCategoryId AND d.ticketSubCategoryId=b.TicketSubCategoryId  AND a.EmpNo=b.RaisedBy AND b.TicketStatus  IN ('F') AND b.assignedTo=a1.EmpNo AND b.assignedBy=a2.EmpNo ORDER BY AssignedDate DESC";
	@Override
	public List<Object[]> getForwardedList() throws Exception {
		
		Query query=manager.createNativeQuery(TICKETFORWARDEDLIST);
		//query.setParameter("EmpId", empId);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		
		return list;
	}
	@Override
	public long ReturnTicket(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}
	public final static String TICKETRETURNEDLIST="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.Priority ,c.TicketCategory,d.TicketSubCategory,b.FileName,b.AssignedDate,b.AssignedTo,b.TicketStatus,a1.EmpName AS caseworkername,a2.Empname AS assignedbyname,b.AssignedBy,b.ARemarks,b.ReturnedDate FROM helpdesk_tickets b,employee a ,employee a1,employee a2,helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1' AND c.TicketCategoryId=b.TicketCategoryId  AND d.TicketSubCategoryId=b.TicketSubCategoryId AND a.EmpNo=b.RaisedBy AND b.TicketStatus  IN ('R') AND b.assignedTo=a1.EmpNo AND b.assignedBy=a2.EmpNo ORDER BY AssignedDate DESC";
	@Override
	public List<Object[]> getReturnedList() throws Exception {
		
		Query query=manager.createNativeQuery(TICKETRETURNEDLIST);
		//query.setParameter("EmpId", empId);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		
		return list;
	}

    public static final String CATEGORYLIST="SELECT TicketCategoryId,TicketCategory FROM helpdesk_category WHERE isActive='1'";
	@Override
	public List<Object[]> getCategoryList() throws Exception {
		
		Query query=manager.createNativeQuery(CATEGORYLIST);
		
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

	public static final String SUBCATEGORYLIST="SELECT TicketSubCategoryId,TicketCategoryId,TicketSubCategory FROM helpdesk_sub_category WHERE isActive='1' AND TicketCategoryId=:CategoryId";
	@Override
	public List<Object[]> getSubCategoryList(String CategoryId) throws Exception {
		
		
        Query query=manager.createNativeQuery(SUBCATEGORYLIST);
		query.setParameter("CategoryId", CategoryId);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}


	@Override
	public long closeTicket(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}

	public static final String CLOSEDTICKETLIST="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.RaisedDate,b.FileName,b.TicketStatus,b.Closedby,b.ClosedDate ,a2.EmpName AS closedByname FROM helpdesk_tickets b,employee a , employee a1,employee a2 WHERE b.isActive='1' AND a.EmpNo=b.RaisedBy AND b.TicketStatus IN ('C') AND b.Closedby=a1.EmpNo AND a2.EmpNo=b.Closedby ORDER BY ClosedDate DESC";
	@Override
	public List<Object[]> getClosedList() throws Exception {
	
		
		Query query=manager.createNativeQuery(CLOSEDTICKETLIST);
		
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

	public static final String TICKETCLOSEDEFORMODAL=" SELECT b.TicketId,a.EmpNo ,a.EmpName,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.Priority ,c.TicketCategory,d.TicketSubCategory,b.FileName,b.AssignedDate,b.AssignedTo,b.TicketStatus,b.ClosedBy,b.ClosedDate ,a1.EmpName AS caseworkername,a2.Empname AS assignedbyname,a3.EmpName AS closedbyname,b.AssignedBy,b.Feedback FROM helpdesk_tickets b,employee a ,employee a1,employee a2,employee a3, helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1' AND c.TicketCategoryId=b.TicketCategoryId AND d.ticketSubCategoryId=b.TicketSubCategoryId  AND a.EmpNo=b.RaisedBy AND b.TicketStatus IN ('C') AND b.AssignedTo=a1.EmpNo AND b.AssignedBy=a2.EmpNo AND b.ClosedBy=a3.EmpNo  AND TicketId=:TicketId ORDER BY ClosedDate";
	@Override
	public List<Object[]> getTicketClosedList(String ticketId) {
		
		Query query=manager.createNativeQuery(TICKETCLOSEDEFORMODAL);
		query.setParameter("TicketId",ticketId );
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}


	@Override
	public long savefeedback(HelpdeskTicket desk) throws Exception {
		
		manager.merge(desk);
		manager.flush();
		return desk.getTicketId();
	}

	public static final String TICKETRAISEDLIST="SELECT b.TicketId,a.EmpNo,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.TicketStatus,c.TicketCategory,d.TicketSubCategory,b.FileName,b.FeedBackRequired,b.Feedback FROM helpdesk_tickets b,employee a ,helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1'AND c.TicketCategoryId=b.TicketCategoryId  AND d.TicketSubCategoryId=b.TicketSubCategoryId AND a.EmpNo=b.RaisedBy AND RaisedDate>=:FromDate AND RaisedDate<=:ToDate  AND a.EmpNo=:EmpNo ORDER BY RaisedDate DESC";
	@Override
	public List<Object[]> getTicketRaisedDetails(String empNo,String fromDate, String toDate) {
		
		List<Object[]> list=null;			
		try {
			
			Query query = manager.createNativeQuery(TICKETRAISEDLIST);				
			query.setParameter("FromDate",fromDate);
			query.setParameter("ToDate", toDate);
			query.setParameter("EmpNo", empNo);				
		 list = query.getResultList();				
		} catch (Exception e) {
			logger.error(new Date() +" Inside DAO getTicketRaisedDetails "+ e);
			e.printStackTrace();
		}
		return list;
	
	}

	public static final String TICKETCATEGORYLIST = "SELECT TicketCategoryId,TicketCategory from helpdesk_category";
	@Override
	public List<Object[]> getTicketCategoryList() throws Exception {
		List<Object[]> ticketlist=null;
		 try { 
			 Query query =manager.createNativeQuery(TICKETCATEGORYLIST);
			 ticketlist=(List<Object[]>)query.getResultList();
		     return ticketlist; 
		  } catch (Exception e) {
			  logger.error(new Date()+" Inside DAO getEmpListList "+ e); 
			  e.printStackTrace(); 
			  return null; 
	    }
	}


	@Override
	public Long TicketCategoryAdd(HelpdeskCategory helpdeskCategory) throws Exception {
		try {
			manager.persist(helpdeskCategory);
			manager.flush();		
			return helpdeskCategory.getTicketCategoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO divsionGroupEdit() "+e);
			e.printStackTrace();
			return 0L;
		}
	}


	@Override
	public Long TicketCategoryEdit(HelpdeskCategory helpdeskCategory) throws Exception {
		try {
			manager.merge(helpdeskCategory);
			manager.flush();		
			return helpdeskCategory.getTicketCategoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO divsionGroupEdit() "+e);
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception {
		
		try {
			return manager.find(HelpdeskCategory.class, tcId);	
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO TicketCategoryAdd() "+e);
			e.printStackTrace();
			return null;
		}
	}

		
}
	


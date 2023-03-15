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

import com.vts.ems.ithelpdesk.model.HelpDeskEmployee;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskSubCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;


@Transactional
@Repository
public class helpdeskDaoImpl implements helpdeskDao {
	
	private static final Logger logger = LogManager.getLogger(helpdeskDaoImpl.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	@PersistenceContext
	EntityManager manager;
	
	private static final String HELPDESKLIST="SELECT b.TicketId,a.EmpNo,b.TicketCategoryId,b.TicketSubCategoryId,b.TicketDesc,b.RaisedDate,b.TicketStatus,c.TicketCategory,d.TicketSubCategory,b.FileName,b.FeedBackRequired,b.Feedback,b.AssignedBy FROM helpdesk_tickets b,employee a ,helpdesk_category c,helpdesk_sub_category d WHERE b.isActive='1'AND c.TicketCategoryId=b.TicketCategoryId  AND d.TicketSubCategoryId=b.TicketSubCategoryId AND a.EmpNo=b.RaisedBy AND a.EmpNo=:EmpNo AND DATE(b.RaisedDate) BETWEEN :FromDate AND :ToDate  ORDER BY RaisedDate DESC";
	@Override
	public List<Object[]> getHelpDeskList(String empno,String fromDate, String toDate) throws Exception {
		
		Query query=manager.createNativeQuery(HELPDESKLIST);
		query.setParameter("EmpNo", empno);
		query.setParameter("FromDate",fromDate );
		query.setParameter("ToDate", toDate);
		
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
	
	public final static String CASEWORKERLIST= "SELECT a.EmpNo,'CE' AS logintype,a.EmpType ,c.EmpName,a.ValidTill ,(SELECT COUNT(*) FROM helpdesk_tickets ht WHERE ht.TicketStatus IN ('A','R') AND ht.IsActive=1 AND ht.AssignedTo=a.EmpNo) AS 'Tickets Count'FROM helpdesk_employee a ,employee_contract c WHERE a.isActive='1' AND a.EmpNo=c.contractEmpNo  AND a.EmpNo NOT IN (SELECT a.EmpNo FROM helpdesk_employee a  WHERE a.ValidTill < CURDATE()) UNION SELECT a.EmpNo,'U' AS logintype,a.EmpType,b.EmpName,a.ValidTill ,(SELECT COUNT(*) FROM helpdesk_tickets ht WHERE ht.TicketStatus IN ('A','R') AND ht.IsActive=1 AND ht.AssignedTo=a.EmpNo) AS 'Tickets Count' FROM helpdesk_employee a,employee b WHERE a.isActive='1' AND a.EmpNo=b.EmpNo AND  a.EmpNo NOT IN (SELECT a.EmpNo FROM helpdesk_employee a  WHERE a.ValidTill < CURDATE())";
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
	
	public final static String ASSIGNEDLIST="CALL ticket_assigned_list()";
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
	
	public final static String TICKETRECIEVEDLIST="CALL ticket_recieved_list(:EmpNo)";
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
	
	public final static String TICKETFORWARDEDLIST="CALL ticket_forwarded_list();";
	@Override
	public List<Object[]> getForwardedList() throws Exception {
		
		Query query=manager.createNativeQuery(TICKETFORWARDEDLIST);
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

	public static final String CLOSEDTICKETLIST="SELECT b.TicketId,a.EmpNo ,a.EmpName,b.RaisedDate,b.FileName,b.TicketStatus,b.Closedby,b.ClosedDate ,a2.EmpName AS closedByname FROM helpdesk_tickets b,employee a ,employee a1,employee a2 WHERE b.isActive='1' AND a.EmpNo=b.RaisedBy AND b.TicketStatus IN ('C') AND b.Closedby=a1.EmpNo AND a2.EmpNo=b.Closedby AND DATE (b.ClosedDate) BETWEEN  :FromDate AND :ToDate ORDER BY ClosedDate DESC";
	@Override
	public List<Object[]> getClosedList(String fromDate, String toDate) throws Exception {
	
		Query query=manager.createNativeQuery(CLOSEDTICKETLIST);
		query.setParameter("FromDate",fromDate );
		query.setParameter("ToDate", toDate);
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

	public static final String TICKETCLOSEDEFORMODAL=" CALL ticket_closed_list(:TicketId)";
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
			logger.error(new Date() + "Inside DAO TicketCategoryAdd() "+e);
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
			logger.error(new Date() + "Inside DAO TicketCategoryEdit() "+e);
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
	public static final String SUBCATEGORYEDITLIST="SELECT TicketSubCategoryId,TicketCategoryId,TicketSubCategory FROM helpdesk_sub_category WHERE isActive='1'";
	@Override
	public List<Object[]> getSubCategoryList() throws Exception {
		
		 Query query=manager.createNativeQuery(SUBCATEGORYEDITLIST);
			
			List<Object[]> list =  (List<Object[]>)query.getResultList();
			return list;
	}
	public static final String DUPLICATETICKETCATEGORY="SELECT COUNT(TicketCategory) FROM helpdesk_category WHERE TicketCategory=:ticketCategory";
	@Override
	public BigInteger ticketCategoryDuplicateAddCheck(String ticketCategory) throws Exception {
		try {
		Query query =manager.createNativeQuery(DUPLICATETICKETCATEGORY);
		query.setParameter("ticketCategory", ticketCategory);
		return (BigInteger)query.getSingleResult();
	}catch (Exception e) {
		logger.error(new Date() + "Inside DAO ticketCategoryDuplicateCheck() "+e);
		e.printStackTrace();
		return null;
	}
	}
	
	public static final String DUPLICATETICKETCATEGORYEDIT = "SELECT COUNT(TicketCategory) FROM helpdesk_category WHERE TicketCategoryId!=:ticketCategoryId AND TicketCategory=:ticketCategory";
	@Override
	public BigInteger ticketCategoryDuplicateEditCheck(String ticketCategoryId,String ticketCategory) throws Exception {
		try {
			Query query =manager.createNativeQuery(DUPLICATETICKETCATEGORYEDIT);
			query.setParameter("ticketCategoryId", ticketCategoryId);
			query.setParameter("ticketCategory", ticketCategory);
			return (BigInteger)query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO ticketCategoryDuplicateCheck() "+e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long TicketSubCategoryAdd(HelpdeskSubCategory helpdeskSubCategory) throws Exception {
		try {
			manager.persist(helpdeskSubCategory);
			manager.flush();		
			return helpdeskSubCategory.getTicketSubCategoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO TicketSubCategoryAdd() "+e);
			e.printStackTrace();
			return 0L;
		}
	}


	@Override
	public Long TicketSubCategoryEdit(HelpdeskSubCategory helpdeskSubCategory) throws Exception {
		try {
			manager.merge(helpdeskSubCategory);
			manager.flush();		
			return helpdeskSubCategory.getTicketSubCategoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO TicketSubCategoryEdit() "+e);
			e.printStackTrace();
			return 0L;
		}
	}


	@Override
	public HelpdeskSubCategory getTicketSubCategoryById(Long tscId) throws Exception {
		try {
			return manager.find(HelpdeskSubCategory.class, tscId);	
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO getTicketSubCategoryById() "+e);
			e.printStackTrace();
			return null;
		}
	}

    public static final String TICKETSUBCATEGORYLIST = "SELECT a.TicketSubCategoryId,b.TicketCategory,a.TicketSubCategory FROM  helpdesk_sub_category AS a,helpdesk_category AS b WHERE a.TicketCategoryId=b.TicketCategoryId ORDER BY a.TicketCategoryId DESC ";
	@Override
	public List<Object[]> getTicketSubCategoryList() throws Exception {
		
		List <Object[]>list=null;
		try {
		Query query = manager.createNativeQuery(TICKETSUBCATEGORYLIST);
		list = (List <Object[]>)query.getResultList();
		return list;
		}catch (Exception e) {
			logger.error(new Date() +" Inside DAO getTicketSubCategoryList() "+ e);
			e.printStackTrace();
			return null;
		}
	}

    public static final String DUPLICATESUBCATEGORYADD = "SELECT COUNT(a.TicketSubCategory) FROM helpdesk_sub_category AS a, helpdesk_category AS b WHERE b.TicketCategoryId=:ticketCategoryId AND a.TicketSubCategory=:ticketSubCategory";
	@Override
	public BigInteger ticketSubCategoryDuplicateAddCheck(String ticketCategoryId,String ticketCategory) throws Exception {
		try {
			Query query =manager.createNativeQuery(DUPLICATESUBCATEGORYADD);
			query.setParameter("ticketCategoryId", ticketCategoryId);
			query.setParameter("ticketSubCategory", ticketCategory);
			return (BigInteger)query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO ticketCategoryDuplicateCheck() "+e);
			e.printStackTrace();
			return null;
		}
	}

	public static final String DUPLICATESUBCATEGORYEDIT = "SELECT COUNT(a.TicketSubCategory) FROM helpdesk_sub_category AS a, helpdesk_category AS b WHERE b.TicketCategoryId=:ticketCategoryId AND a.TicketSubCategoryId!=:ticketSubCategoryId AND a.TicketSubCategory=:ticketSubCategory";
	@Override
	public BigInteger ticketSubCategoryDuplicateEditCheck(String ticketSubCategoryId,String ticketCategoryId, String ticketCategory) throws Exception {
		try {
			Query query =manager.createNativeQuery(DUPLICATESUBCATEGORYEDIT);
			query.setParameter("ticketCategoryId", ticketCategoryId);
			query.setParameter("ticketSubCategoryId", ticketSubCategoryId);
			query.setParameter("ticketSubCategory", ticketCategory);
			return (BigInteger)query.getSingleResult();
		}catch (Exception e) {
			logger.error(new Date() + "Inside DAO ticketCategoryDuplicateCheck() "+e);
			e.printStackTrace();
			return null;
		}
	}
   public static final String EMPLOYEELIST="SELECT EmpNo,EmpName FROM employee WHERE isActive='1' AND EmpNo NOT IN(SELECT EmpNo FROM helpdesk_employee WHERE EmpType='P' AND isActive='1')";
	@Override
	public List<Object[]> getEmployeeList() throws Exception {
		
		Query query=manager.createNativeQuery(EMPLOYEELIST);
        List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

	public static final String CONTRACTEMPLOYEELIST="SELECT ContractEmpNo,EmpName FROM employee_contract WHERE isActive='1' AND ContractEmpNo NOT IN(SELECT EmpNo FROM helpdesk_employee  WHERE EmpType='C' AND isActive='1')";
	@Override
	public List<Object[]> getContractEmployee() throws Exception {
		
		Query query=manager.createNativeQuery(CONTRACTEMPLOYEELIST);
        List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

	public static final String HELPDESKEMPLOYEELIST="SELECT a.helpDeskEmpId,a.EmpNo,a.EmpType ,c.EmpName,a.ValidTill FROM helpdesk_employee a ,employee_contract c WHERE a.isActive='1' AND a.EmpNo=c.contractEmpNo UNION SELECT a.helpDeskEmpId,a.EmpNo,a.EmpType,b.EmpName ,a.ValidTill FROM helpdesk_employee a,employee b WHERE a.isActive='1' AND a.EmpNo=b.EmpNo ORDER BY helpDeskEmpId DESC";
	@Override
	public List<Object[]> getHelpDeskEmployeeList() throws Exception {
		
		Query query=manager.createNativeQuery(HELPDESKEMPLOYEELIST);
        List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

    @Override
	public long EmployeeAddSubmit(HelpDeskEmployee employee) throws Exception {
		
		try {
			manager.persist(employee);
			manager.flush();		
			return employee.getHelpDeskEmpId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO EmployeeAddSubmit() "+e);
			e.printStackTrace();
			return 0L;
		}
	}

	public static final String EMPLOYEDELETE="UPDATE helpdesk_employee SET IsActive=:isactive WHERE HelpDeskEmpId=:HelpDeskEmpId";
	@Override
	public long EmployeeDelete(String helpDeskEmpId) throws Exception {
		
		Query query=manager.createNativeQuery(EMPLOYEDELETE);
		query.setParameter("isactive", 0);
		query.setParameter("HelpDeskEmpId", helpDeskEmpId);
		long count= (long) query.executeUpdate();
		
		return count;
	}

    @Override
	public HelpDeskEmployee getHelpDeskEmployeeList(Long helpDeskEmpId) throws Exception {
		
		try {
			HelpDeskEmployee emp = manager.find(HelpDeskEmployee.class, (helpDeskEmpId));
			return emp;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO GetTicket() "+e);
			e.printStackTrace();
			return null;
		}	
	
	}

   @Override
	public long getEmployeeupdate(HelpDeskEmployee he) {
		
		try {
			manager.merge(he);
			manager.flush();		
			return he.getHelpDeskEmpId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO divsionGroupEdit() "+e);
			e.printStackTrace();
			return 0L;
		}
	}

	

	public static final String TICKETRETURNEDLISTFORMODAL="CALL ticket_returned_list(:TicketId) ";
	@Override
	public List<Object[]> getTicketReturnedList(String ticketId) throws Exception {
		
		Query query=manager.createNativeQuery(TICKETRETURNEDLISTFORMODAL);
		query.setParameter("TicketId",ticketId );
		List<Object[]> list =  (List<Object[]>)query.getResultList();
		return list;
	}

	public static final String ITHELPDESKDASHBOARDCOUNTDATA="CALL ItHelpdesk_Dashboard_Count(:EmpNo,:LoginType,:FromDate,:ToDate)";
	@Override
	public Object[] IThelpdeskDashboardCountData(String empNo,String logintype,String fromDate, String toDate) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(ITHELPDESKDASHBOARDCOUNTDATA);
			query.setParameter("EmpNo", empNo);
			query.setParameter("LoginType", logintype);
			query.setParameter("FromDate",fromDate );
			query.setParameter("ToDate", toDate);
			return (Object[])query.getSingleResult();
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside DAO IThelpdeskDashboardCountData " + e);
			return null;
		}
	}
	
   public static final String ITHELPDESKDASHBOARDGRAPHDATA="CALL IThelpDesk_Dashboard_GraphData(:FromDate,:ToDate)";
    @Override
   public List<Object[]> IThelpdeskDashboardGraphData(String fromDate, String toDate) throws Exception {
   try {
		Query query = manager.createNativeQuery(ITHELPDESKDASHBOARDGRAPHDATA);
		query.setParameter("FromDate",fromDate );
		query.setParameter("ToDate", toDate);
		return (List<Object[]>)query.getResultList();
		
	}
	catch(Exception e) {
		logger.error(new Date()  + "Inside DAO IThelpdeskDashboardGraphData " + e);
		return null;
	}
 }

   public static final String ITHELPDESKDASHBOARDPIECHARTDATA="CALL IThelpDesk_Dashboard_PieChart(:FromDate,:ToDate)";
	@Override
	public List<Object[]> IThelpdeskDashboardPieChartData(String fromDate, String toDate) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(ITHELPDESKDASHBOARDPIECHARTDATA);
			query.setParameter("FromDate",fromDate );
			query.setParameter("ToDate", toDate);
			return (List<Object[]>)query.getResultList();
			
		}
		catch(Exception e) {
			logger.error(new Date()  + "Inside DAO IThelpdeskDashboardPieChartData " + e);
			return null;
		}
  }
    @Override
	public long NotificationAdd(EMSNotification notification) throws Exception {
		{
			try {
				manager.persist(notification);
				manager.flush();
				
				return notification.getNotificationId();
			}catch (Exception e) {
				logger.error(new Date()  + "Inside DAO NotificationAdd " + e);
				e.printStackTrace();
				return 0;
			}
		}
	}

	private static final String EMSTICKETNOTIFICATION  ="SELECT e.empno,e.empname,ed.desigid, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.desigid = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType";
	@Override
	public List<Object[]> SendNotification(String Logintype) throws Exception {
		try {
				
				Query query= manager.createNativeQuery(EMSTICKETNOTIFICATION);
				query.setParameter("loginType", Logintype);
				List<Object[]> list =  (List<Object[]>)query.getResultList();
				return list;
			}catch (Exception e) {
				logger.error(new Date()  + "Inside DAO SendNotification " + e);
				e.printStackTrace();
				return null;
			}
			
		}
	
	
	
	}


	

	


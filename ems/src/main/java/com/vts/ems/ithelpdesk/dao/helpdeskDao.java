package com.vts.ems.ithelpdesk.dao;

import java.math.BigInteger;
import java.util.List;

import com.vts.ems.ithelpdesk.model.HelpDeskEmployee;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskSubCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;
import com.vts.ems.model.EMSNotification;


public interface helpdeskDao {

	    public List<Object[]> getHelpDeskList(String empno,String fromDate, String toDate) throws Exception;
        long saveTicket(HelpdeskTicket desk) throws Exception;
        public HelpdeskTicket GetTicketId(String ticketId) throws Exception;
        public HelpdeskTicket GetTicket(String ticketId) throws Exception;
        public long TicketDelete(HelpdeskTicket ticketdelete) throws Exception;
        public long ticketUpdate(HelpdeskTicket desk) throws Exception;
        public long MaxOfEmsTicketId() throws Exception;
        public List<Object[]> getTicketPending() throws Exception;
        public List<Object[]> getTicketList(String ticketId) throws Exception;
        public List<Object[]> getCaseWorkerList() throws Exception;
        public long assignedTicket(HelpdeskTicket desk) throws Exception;
        public List<Object[]> getAssignedList() throws Exception;
        public List<Object[]> getTicketAssignedList(String ticketId)throws Exception;
        public long reAssignedTicket(HelpdeskTicket desk)throws Exception;
        public List<Object[]> getRecievedList(String empno) throws Exception;
        public long ForwardTicket(HelpdeskTicket desk) throws Exception;
        public List<Object[]> getForwardedList() throws Exception;
        public long ReturnTicket(HelpdeskTicket desk)throws Exception;
        public List<Object[]> getReturnedList() throws Exception;
        public List<Object[]> getCategoryList() throws Exception;
        public List<Object[]> getSubCategoryList(String categoryId)throws Exception;
        public long closeTicket(HelpdeskTicket desk) throws Exception;
        public List<Object[]> getClosedList(String fromDate, String toDate) throws Exception;
        public List<Object[]> getTicketClosedList(String ticketId) throws Exception;
        public long savefeedback(HelpdeskTicket desk) throws Exception;
        public Long TicketCategoryAdd(HelpdeskCategory helpdeskCategory) throws Exception;
        public Long TicketCategoryEdit(HelpdeskCategory helpdeskCategory) throws Exception;
        public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception;
        public List<Object[]> getTicketCategoryList() throws Exception;
        public BigInteger ticketCategoryDuplicateAddCheck(String ticketCategory) throws Exception;
	    public BigInteger ticketCategoryDuplicateEditCheck(String ticketCategoryId,String ticketCategory) throws Exception;
	    public Long TicketSubCategoryAdd(HelpdeskSubCategory helpdeskSubCategory) throws Exception;
	    public Long TicketSubCategoryEdit(HelpdeskSubCategory helpdeskSubCategory) throws Exception;
	    public HelpdeskSubCategory getTicketSubCategoryById(Long tscId) throws Exception;
	    public List<Object[]> getTicketSubCategoryList() throws Exception;
	    public BigInteger ticketSubCategoryDuplicateAddCheck(String ticketCategoryId,String ticketCategory) throws Exception;
	    public BigInteger ticketSubCategoryDuplicateEditCheck(String ticketSubCategoryId,String ticketCategoryId,String ticketCategory) throws Exception;
        public List<Object[]> getEmployeeList()throws Exception;
		public List<Object[]> getContractEmployee() throws Exception;
		public List<Object[]> getHelpDeskEmployeeList() throws Exception;
		public long EmployeeAddSubmit(HelpDeskEmployee employee)throws Exception;
		public long EmployeeDelete(String helpDeskEmpId) throws Exception;
		public HelpDeskEmployee getHelpDeskEmployeeList(Long helpDeskEmpId) throws Exception;
		public long getEmployeeupdate(HelpDeskEmployee he);
		public List<Object[]> getSubCategoryList() throws Exception;
		public List<Object[]> getTicketReturnedList(String ticketId) throws Exception;
		public Object[] IThelpdeskDashboardCountData(String empNo,String logintype,String fromDate, String toDate) throws Exception;
		public List<Object[]> IThelpdeskDashboardGraphData(String fromDate, String toDate) throws Exception;
		public List<Object[]> IThelpdeskDashboardPieChartData(String fromDate, String toDate)throws Exception;
		public long NotificationAdd(EMSNotification notification) throws Exception;
		public List<Object[]> SendNotification(String Logintype) throws Exception;
	        
}

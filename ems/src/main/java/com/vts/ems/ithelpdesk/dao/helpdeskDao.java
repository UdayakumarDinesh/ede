package com.vts.ems.ithelpdesk.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;


public interface helpdeskDao {

	    List<Object[]> getHelpDeskList(String empno) throws Exception;
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
        public List<Object[]> getClosedList() throws Exception;
        public List<Object[]> getTicketClosedList(String ticketId) throws Exception;
        public long savefeedback(HelpdeskTicket desk) throws Exception;
        public List<Object[]> getTicketRaisedDetails(String empNo, String fromDate, String toDate);
        public Long TicketCategoryAdd(HelpdeskCategory helpdeskCategory) throws Exception;
        public Long TicketCategoryEdit(HelpdeskCategory helpdeskCategory) throws Exception;
        public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception;
        public List<Object[]> getTicketCategoryList() throws Exception;
	


}

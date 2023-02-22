package com.vts.ems.ithelpdesk.service;

import java.util.List;



import com.vts.ems.ithelpdesk.dto.itheldeskdto;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;


public interface helpdeskService {

	List<Object[]> getHelpDeskList(String empno) throws Exception;
	long saveTicket(itheldeskdto dto, String userId) throws Exception;
    public HelpdeskTicket GetTicketId(String ticketId) throws Exception;
    public  HelpdeskTicket GetTicket(String ticketId) throws Exception;
    public long TicketDelete(String ticketId) throws Exception;
    public long ticketUpdate(itheldeskdto dto) throws Exception;
    public List<Object[]> getPendingList() throws Exception;
    public List<Object[]> getTicketList(String ticketId) throws Exception;
    public List<Object[]> getCaseWorkerList() throws Exception;
    public long assignedTicket(itheldeskdto dto) throws Exception;
    public List<Object[]> getAssignedList() throws Exception;
    public List<Object[]> getTicketAssignedList(String ticketId) throws Exception;
    public List<Object[]> getRecievedList(String empno) throws Exception;
	public long ForwardTicket(itheldeskdto dto) throws Exception;
    public List<Object[]> getForwardedList() throws Exception;
    public long ReturnTicket(itheldeskdto dto) throws Exception;
    public List<Object[]> getReturnedList() throws Exception;
    public List<Object[]> getCategoryList() throws Exception;
    public List<Object[]> getSubCategoryList(String categoryId) throws Exception;
    public long closeTicket(itheldeskdto dto)throws Exception;
    public List<Object[]> getClosedList() throws Exception ;
    public long reAssignedTicket(itheldeskdto dto) throws Exception;
	public List<Object[]> getTicketClosedList(String ticketId) throws Exception;
	public long savefeedback(itheldeskdto dto) throws Exception;
	public List<Object[]> getTicketRaisedDetails(String empNo, String fromDate, String toDate) throws Exception;
	public List<Object[]> getTicketCategoryList() throws Exception;
	public Long TicketCategoryAdd(HelpdeskCategory helpdeskCategory) throws Exception;
	public Long TicketCategoryEdit(HelpdeskCategory helpdeskCategory) throws Exception;
	public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception;
	


}

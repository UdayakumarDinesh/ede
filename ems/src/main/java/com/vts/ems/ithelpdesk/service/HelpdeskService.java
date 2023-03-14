package com.vts.ems.ithelpdesk.service;

import java.math.BigInteger;
import java.util.List;



import com.vts.ems.ithelpdesk.dto.ItHeldeskDto;
import com.vts.ems.ithelpdesk.model.HelpdeskCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskSubCategory;
import com.vts.ems.ithelpdesk.model.HelpdeskTicket;


public interface HelpdeskService {

	List<Object[]> getHelpDeskList(String empno) throws Exception;
	long saveTicket(ItHeldeskDto dto, String userId) throws Exception;
    public HelpdeskTicket GetTicketId(String ticketId) throws Exception;
    public  HelpdeskTicket GetTicket(String ticketId) throws Exception;
    public long TicketDelete(String ticketId) throws Exception;
    public long ticketUpdate(ItHeldeskDto dto) throws Exception;
    public List<Object[]> getPendingList() throws Exception;
    public List<Object[]> getTicketList(String ticketId) throws Exception;
    public List<Object[]> getCaseWorkerList() throws Exception;
    public long assignedTicket(ItHeldeskDto dto) throws Exception;
    public List<Object[]> getAssignedList() throws Exception;
    public List<Object[]> getTicketAssignedList(String ticketId) throws Exception;
    public List<Object[]> getRecievedList(String empno) throws Exception;
	public long ForwardTicket(ItHeldeskDto dto) throws Exception;
    public List<Object[]> getForwardedList() throws Exception;
    public long ReturnTicket(ItHeldeskDto dto) throws Exception;
    public List<Object[]> getReturnedList() throws Exception;
    public List<Object[]> getCategoryList() throws Exception;
    public List<Object[]> getSubCategoryList(String categoryId) throws Exception;
    public long closeTicket(ItHeldeskDto dto)throws Exception;
    public List<Object[]> getClosedList() throws Exception ;
    public long reAssignedTicket(ItHeldeskDto dto) throws Exception;
	public List<Object[]> getTicketClosedList(String ticketId) throws Exception;
	public long savefeedback(ItHeldeskDto dto) throws Exception;
	public List<Object[]> getTicketRaisedDetails(String empNo, String fromDate, String toDate) throws Exception;
	
	public List<Object[]> getTicketCategoryList() throws Exception;
	public Long TicketCategoryAdd(HelpdeskCategory helpdeskCategory) throws Exception;
	public Long TicketCategoryEdit(HelpdeskCategory helpdeskCategory) throws Exception;
	public HelpdeskCategory getTicketCategoryById(Long tcId) throws Exception;
	public BigInteger ticketCategoryDuplicateAddCheck(String ticketCategory) throws Exception;
	public BigInteger ticketCategoryDuplicateEditCheck(String ticketCategoryId,String ticketCategory) throws Exception;
	
	 public Long TicketSubCategoryAdd(HelpdeskSubCategory helpdeskSubCategory) throws Exception;
     public Long TicketSubCategoryEdit(HelpdeskSubCategory helpdeskSubCategory) throws Exception;
     public HelpdeskSubCategory getTicketSubCategoryById(Long tscId) throws Exception;
     public List<Object[]> getTicketSubCategoryList() throws Exception;
     public BigInteger ticketSubCategoryDuplicateAddCheck(String ticketCategoryId, String ticketCategory) throws Exception;
     public BigInteger ticketSubCategoryDuplicateEditCheck(String ticketSubCategoryId,String ticketCategoryId,String ticketCategory) throws Exception;
     


}

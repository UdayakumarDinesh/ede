package com.vts.ems.ithelpdesk.model;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name="helpdesk_tickets")
public class HelpdeskTicket implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private long TicketId;
	//private int EmpId;
	private String TicketNo;
	private  String RaisedBy;
	private String  RaisedDate;
	private int  TicketCategoryId;
	private int TicketSubCategoryId;
	private String  Priority;
	private String  TicketDesc;
	private String  TicketStatus;
	private String  FileName;
	private String  FilePath;
	private String  AssignedTo;
	private String  AssignedBy;
	private String  AssignedDate;
	private String  CWRemarks;
	private String  ARemarks;
	private String ForwardedDate;
	private String Returneddate;
	private String  ClosedDate;
	private String ClosedBy;
	private String FeedBackRequired;
	private String Feedback;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int isActive;
	
	
	
}

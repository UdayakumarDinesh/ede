package com.vts.ems.ithelpdesk.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;

import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Builder


public class itheldeskdto {
	
	
	//private int    TicketId;
	private String  TicketId;
	private String  EmpNo;
	private String LoginType;
	private String  TicketCategoryId;
	private String  TicketSubCategoryId;
	private String     RaisedBy;
	private String  RaisedDate;
	private String FilePath;
	private String FileName;
	private MultipartFile FormFile;
	private String  Priority;
	private String  TicketDesc;
	private String  TicketStatus;
	private String AssignedTo;
	private String  AssignedBy;
	private String  AssignedDate;
	private String  ForwardedDate;
	private String  Returneddate;
	private String  CWRemarks;
	private String  ARemarks;
	private String  CloseDate;
	private String ClosedBy;
	private String FeedBackRequired;
	private String Feedback;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int     isActive;
	
}

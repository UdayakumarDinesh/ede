package com.vts.ems.ithelpdesk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table (name="helpdesk_attachments")
public class HelpdeskAttachments {
	
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private long AttachmentId;
	private long TicketId;
	private String EmpNo;
	private String FilePath;
	private String FileName;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int isActive;
	

}

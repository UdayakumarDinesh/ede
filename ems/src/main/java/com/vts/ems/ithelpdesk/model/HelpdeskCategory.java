package com.vts.ems.ithelpdesk.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="helpdesk_category")
 public class HelpdeskCategory {
	
		@Id
		@GeneratedValue(strategy  =  GenerationType.IDENTITY)
		private long TicketCategoryId;
		private String TicketCategory;
		private String  CreatedBy;
		private Date  CreatedDate;
		private String  ModifiedBy;
		private Date  ModifiedDate;
		private int isActive;
		
	

}

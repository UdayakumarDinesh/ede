package com.vts.ems.ithelpdesk.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="helpdesk_category")
 public class HelpdeskCategory {
	
		@Id
		@GeneratedValue(strategy  =  GenerationType.IDENTITY)
		private long TicketCategoryId;
		private String TicketCategory;
		private String  CreatedBy;
		private String  CreatedDate;
		private String  ModifiedBy;
		private String  ModifiedDate;
		private int isActive;
	
}

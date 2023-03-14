package com.vts.ems.ithelpdesk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name="helpdesk_sub_category")
public class HelpdeskSubCategory {
	
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private long    TicketSubCategoryId;
	private long  TicketCategoryId;
	private String  TicketSubCategory;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int isActive;

}

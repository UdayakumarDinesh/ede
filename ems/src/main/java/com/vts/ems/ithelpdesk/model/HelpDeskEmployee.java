package com.vts.ems.ithelpdesk.model;

import java.sql.Date;

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
@Table (name="helpdesk_employee")
public class HelpDeskEmployee {
	
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private Long HelpDeskEmpId;
	private String EmpNo;
	private String EmpType;
	private String ValidTill;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int isActive;
	
}

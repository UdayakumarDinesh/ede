package com.vts.ems.Mt.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

//@Entity
//@Table(name="mt_link_duty")
public class MtLinkDuty implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int LinkDutyId;
	private int TripId;
	private int MtApplId;
	private String EmpId;
	private String CreatedBy;
	private String CreatedDate;
	private String Modifiedby;
	private String ModifiedDate;
	private int IsActive;
	
	
	
	
	
}

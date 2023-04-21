package com.vts.ems.pis.model;

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
@Table(name="division_master")
public class DivisionMaster 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long DivisionId;
	private String DivisionCode;
	private String DivisionName;
	private String DivisionHeadId;
	private String DGMId;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	 
}

package com.vts.ems.pis.model;

import java.io.Serializable;

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
@Table(name="pis_property_details")
public class PropertyDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long PropertyId;
	private  Integer PropertyYear; 
	private Long EmpId;
	private String Description;
	private String Address;
	private Double  PropertyValue;
	private String PartnerInfo;
	private String ModeOfProperty;
	private Double AnnualIncome;
	private String Remarks;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private Integer IsActive;
}
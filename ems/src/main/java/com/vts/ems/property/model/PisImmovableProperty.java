package com.vts.ems.property.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="pis_immovable_property")
public class PisImmovableProperty implements Serializable{

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long ImmPropertyId;
	private String EmpNo;
	private String Purpose;
	private String TransState;
	private Date TransDate;
	private String Mode;
	private String Location;
	private String District;
	private String State;
	private String Pincode;
	private String Description;
	private String Ownership;
	private String ApplicantInterest;
	private String PartialInterest;
	private String OsParticulars;
	private String OsShare;
	private Double Price;
	private String FinanceSource;
	private String RequisiteSanction;
	private String PartyName;
	private String PartyAddress;
	private String TransArrangement;
	private String PartyRelated;
	private String Relationship;
	private String FutureDealings;
	private String DealingNature;
	private String SanctionRequired;
	private String RelavantFact;
	private String ImmStatus;
	private String Remarks;
	private String PisStatusCode;
	private String PisStatusCodeNext;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

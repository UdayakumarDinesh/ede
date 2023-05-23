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
@Table(name="pis_movable_property")
public class PisMovableProperty implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long MovPropertyId;
	private String MovIntimationDate;
	private String EmpNo;
	private String Purpose;
	private String TransState;
	private Date TransDate;
	private String Description;
	private String MakeAndModel;
	private String Mode;
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
	private String RelavantFacts;
	private String MovStatus;
	private String Remarks;
	private String PisStatusCode;
	private String PisStatusCodeNext;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

package com.vts.ems.pis.model;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name="pis_passport_visit")
public class PassportForeignVisit implements Serializable{

	private static final long serialVersionUID = 1L;

	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private int PassportVisitId;
	 private String EmpId;
	 private String CountryName;
	 private Date VisitFromDate;
	 private Date VisitToDate;
	 private String NocLetterNo;
	 private Date NocLetterDate;
	 private String NocIssuedFrom;
	 private String Purpose;
	 private String Remarks;
	 private int IsActive;
	 private String CreatedBy;
	 private String CreatedDate;
	 private String ModifiedBy;
	 private String ModifiedDate;
}

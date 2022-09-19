package com.vts.ems.newspaper.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pis_newspaper")
public class Newspaper {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long NewspaperId;
	private int NewspaperBillId;
	private String EmpNo;
	private String ClaimMonth;
	private String ClaimYear;
	private double ClaimAmount;
	private double RestrictedAmount;
	private double PayableAmount;
	private int PayLevelId;
	private String Remark;
	private Date NewsAppliedDate;
	private int IsActive;
	private String SubmitBy;
	private String SubmitDate;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
	
}

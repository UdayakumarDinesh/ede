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
@Table(name="pis_tele")
public class Telephone {
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int TeleId;
	private int TeleBillId;
	private String EmpId;
	private String ClaimMonth;
	private String ClaimYear;
	private double TotalBasic;
	private double TotalTax;
	private double GrossTotal;
	private double RestrictedAmt;
	private double PayableAmount;
	private Date TeleAppliedDate;
	private int  PayLevelId;
	private int IsActive;
	private String IsBroadBand;
	private String Remarks;
	private String SubmitBy;
	private Date SubmitDate;
	private int TeleForwardId;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private String SendBackBy;
	private Date SendBackDate;
	private String ApprovalRemark;
	private String Status;
	
	
}

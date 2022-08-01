package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chss_ipd_claimsinfo")
public class CHSSIPDClaimsInfo implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long IPDClaimInfoId;
	private long CHSSApplyId;
	private String HospitalName;
	private String RoomType;
	private String AdmissionDate;
	private String AdmissionTime;
	private String DischargeDate;
	private String DischargeTime;
	private String AdwanceRefNo;
	private double AdvanceAvailed;
	private double AdvancePaid;
	private int DomiciliaryHosp;
	private int DayCare;
	private int ExtCareRehab;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
}

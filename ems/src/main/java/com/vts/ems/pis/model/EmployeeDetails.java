package com.vts.ems.pis.model;

import java.util.Date;

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
@Table(name="employee_details")
public class EmployeeDetails {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long   EmpDetailsId;
	private String EmpNo;
	private String Title;
	private String ReportingAuth;
	private Date DOB;
	private Date DOA;
	private Date DOJL;	
	private Date DOR;
	private String CatId;
	private int CategoryId;
	private int GroupId;
	private int CadreId;
	private String Gender;
	private String BloodGroup;
	private String MaritalStatus;
	private String Religion;
	private String EmpStatus;
	private String GPFNo;
	private String PAN;
	private String UANNo;
	private String PINNo;
	private String PRANNo;
	private String LabEmpId;
	private String PunchCard;
	private long UID;
	private String Quarters;
	private String PH;
	private String Photo;	
	private String HomeTown;
	private String HomeAllowed;
	private String ServiceStatus;
	private Date ResigDate;
	private int PayLevelId;
	private String SBIAccNo;
	private String OtherAccNo; 
	private String SubCategary ;
	private String  InternalNumber ;
	private String IdMark;
	private String Height;
	private Long BasicPay;
	private Date EmpStatusDate;
	private String PhoneNo;
	private String AltPhoneNo;
	private String PerPassNo;
	private String ExServiceMan; 
	private Date DOP;
	private String CHSSNo;
	private String DCMAFNo;
	private String ITICreditSocNo;
	private String BenovelentFundNo;
	private String AnnualDec;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
}

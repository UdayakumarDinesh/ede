package com.vts.ems.leave.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="leave_appl")
@Data
@NoArgsConstructor
public class LeaveAppl {
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private long LeaveApplId;
	 private String EmpId;
	 private String PurLeave;
	 private String LeaveAddress;
	 private Date   FromDate;
	 private Date   ToDate;
	 private double TotalDays;
	 private String DivId;
	 private String LeaveCode;
	 private String FnAn;
	 private String ApplId;
	 private String Ltc;
	 private String Status;
	 private String Remarks;
	 private int LeaveAmend;
	 private String DopNo;
	 private Date   DopDate;
	 private String LeaveYear;
	 private String CreatedBy;
	 private String CreatedDate;

	 

}
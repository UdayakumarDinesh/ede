package com.vts.ems.Mt.model;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name="mt_appl")
public class MtUserApply implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int MtApplId;
	private String MtReqNo;
	private String EmpId;
	private Date DateOfTravel;
	private Date EndDateOfTravel;
	private String StartTime;
	private String EndTime;
	private String Source;
	private String Destination;
	private int DutyTypeId;
	private String MtReason;
	private int OneWayDistance;
	private String ContactNo;
	private String UserRemarks;
	private int ProjectId;
	private String MtStatus;
	private Date ApplDate;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private String ForwardedBy;
	private String ForwardedDate;
	private String ForwardRemarks;
	private String SanctionBy;
	private String SanctionDate;
	private String SanctionRemarks;

}

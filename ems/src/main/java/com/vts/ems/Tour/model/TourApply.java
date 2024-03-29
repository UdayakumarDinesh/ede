package com.vts.ems.Tour.model;

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
@Table(name = "tour_apply")
public class TourApply {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long TourApplyId;
	private String TourNo;
	private Long TourApplPreviousId;
	private String EmpNo;
	private Long DivisionId;
	private String Purpose;
	private Date IssueDate;
	private Date EarliestDate;
	private String EarliestTime;
	private String EarliestPlace;
	private Date StayFrom;
	private Date StayTo;
	private String StayPlace;
	private Date EJPFrom;
	private Date EJPTo;
	private String AirTravJust;
	private String AdvancePropsed;
	private int AdvanceIssued;
	private String TourStatusCode;
	private String FundsAvailable;
	private String PandARemarks;
	private String InitiatedDate;
	private String ApprovedDate;
	private String Remarks;
	private String CancelReason;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

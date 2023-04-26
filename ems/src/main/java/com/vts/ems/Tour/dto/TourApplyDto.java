package com.vts.ems.Tour.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class TourApplyDto {

	
	
	private Long TourApplyId;
	private String TourNo;
	private String EmpNo;
	private String ApplyEmpNo;
	private Long DivisionId;
	private String Purpose;
	private Date IssueDate;
	private Date EarliestDate;
	private String EarliestTime;
	private String EarliestPlace;
	private Date StayFrom;
	private Date StayTo;
	private String StayPlace;
	private String AirTravJust;
	private int AdvancePropsed;
	private int AdvanceIssued;
	private String TourStatusCode;
	private String FundsAvailable;
	private String PandARemarks;
	private String InitiatedDate;
	private String ApprovedDate;
	private String Remarks;
	private String[] TourDates;
	private String[] TourTimes;
	private String[] ModeofTravel;
	private String[] FromCity;
	private String[] ToCity;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

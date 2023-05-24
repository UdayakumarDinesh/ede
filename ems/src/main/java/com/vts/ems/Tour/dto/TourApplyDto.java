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
	private String AdvancePropsed;
	private String AdvanceIssued;
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
	
	//tour Advance
	private Long TourAdvanceId;
	private int TourFare;
	private int BoardingDays;
	private int BoardingPerDay;
	private int PerDayAllowance;
	private int AllowanceDays;
	private Date AllowanceFromDate;
	private Date AllowanceToDate;
	private Date TourfareFrom;
	private Date TourfareTo;
	
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

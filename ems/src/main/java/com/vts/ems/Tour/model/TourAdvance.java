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
@Table(name = "tour_advance")
public class TourAdvance {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long TourAdvanceId;
	private Long TourApplyId;
	private int TourFare;
	private int BoardingDays;
	private int BoardingPerDay;
	private int PerDayAllowance;
	private int AllowanceDays;
	private Date AllowanceFromDate;
	private Date AllowanceToDate;
	private Date TourfareFrom;
	private Date TourfareTo;
	private String Status;
	private int TotalProposedAmt;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate; 
}

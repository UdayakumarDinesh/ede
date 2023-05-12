package com.vts.ems.vehicleparking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="Vehicle_Park_appli")
@Entity
public class VehicleParkingApplications {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long appId;
	private String EmpNo;
	private String VehicleNo;
	private String FromDateAndTime;
	private String ToDateAndTime;
	private String ApplicStatus;
	private String ApplicStatusCode;
	private String Remarks;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
}

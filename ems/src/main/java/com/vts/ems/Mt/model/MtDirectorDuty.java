package com.vts.ems.Mt.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//@Entity
//@Table(name="mt_director_duty")
public class MtDirectorDuty implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int TripId;
	private int DriverId;
	
	private Date FromDate;
	private Date ToDate;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String Modifiedby;
	private String ModifiedDate;
	public int getTripId() {
		return TripId;
	}
	public void setTripId(int tripId) {
		TripId = tripId;
	}
	
	public int getDriverId() {
		return DriverId;
	}
	public void setDriverId(int driverId) {
		DriverId = driverId;
	}
	
	
	public Date getFromDate() {
		return FromDate;
	}
	public void setFromDate(Date fromDate) {
		FromDate = fromDate;
	}
	public Date getToDate() {
		return ToDate;
	}
	public void setToDate(Date toDate) {
		ToDate = toDate;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	public String getModifiedby() {
		return Modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		Modifiedby = modifiedby;
	}
	public String getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	
	
	
	
}

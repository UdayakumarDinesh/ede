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

@Setter
@Getter
@Entity
@Table(name="mt_trip")
public class MtTrip implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int TripId;
	private String TripNo;
	private int DriverId;
	private int VehicleId;
	private String HiredVehicle;
	private String Place;
	private String MtoComments;
	private Date TripDate;
	private Date TripEndDate;
	private String StartTime;
	private String EndTime;
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
	public String getTripNo() {
		return TripNo;
	}
	public void setTripNo(String tripNo) {
		TripNo = tripNo;
	}
	public int getDriverId() {
		return DriverId;
	}
	public void setDriverId(int driverId) {
		DriverId = driverId;
	}
	public int getVehicleId() {
		return VehicleId;
	}
	public void setVehicleId(int vehicleId) {
		VehicleId = vehicleId;
	}
	public String getHiredVehicle() {
		return HiredVehicle;
	}
	public void setHiredVehicle(String hiredVehicle) {
		HiredVehicle = hiredVehicle;
	}
	public String getPlace() {
		return Place;
	}
	public void setPlace(String place) {
		Place = place;
	}
	public String getMtoComments() {
		return MtoComments;
	}
	public void setMtoComments(String mtoComments) {
		MtoComments = mtoComments;
	}
	public Date getTripDate() {
		return TripDate;
	}
	public void setTripDate(Date tripDate) {
		TripDate = tripDate;
	}
	
	public Date getTripEndDate() {
		return TripEndDate;
	}
	public void setTripEndDate(Date tripEndDate) {
		TripEndDate = tripEndDate;
	}
	
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getEndTime() {
		return EndTime;
	}
	public void setEndTime(String endTime) {
		EndTime = endTime;
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

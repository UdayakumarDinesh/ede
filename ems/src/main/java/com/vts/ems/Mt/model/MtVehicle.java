package com.vts.ems.Mt.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="mt_vehicle")
public class MtVehicle implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int VehicleId;
	private String VehicleName;
	private String BaNo;
	private Date DateOfPurchase;
	private int NoOfSeat;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String Modifiedby;
	private String ModifiedDate;
	public int getVehicleId() {
		return VehicleId;
	}
	public void setVehicleId(int vehicleId) {
		VehicleId = vehicleId;
	}
	public String getVehicleName() {
		return VehicleName;
	}
	public void setVehicleName(String vehicleName) {
		VehicleName = vehicleName;
	}
	public String getBaNo() {
		return BaNo;
	}
	public void setBaNo(String baNo) {
		BaNo = baNo;
	}
	public Date getDateOfPurchase() {
		return DateOfPurchase;
	}
	public void setDateOfPurchase(Date dateOfPurchase) {
		DateOfPurchase = dateOfPurchase;
	}
	public int getNoOfSeat() {
		return NoOfSeat;
	}
	public void setNoOfSeat(int noOfSeat) {
		NoOfSeat = noOfSeat;
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

package com.vts.ems.athithi.dto;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewIntimation {
	
	private String IntimationByEmpNo;
	private String createBy;
	private String compnayId;
	private String[] visitors;
	private String fdate;
	private String tdate;
	private String duration;
	private String VisitExpectedTime;
	private String officer;
	private String purpose;
	private String spermission;
	private String Remarks;
	private String PisStatusCode;
	private String PisStatusCodeNext;
	private String VpStatus;
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCompnayId() {
		return compnayId;
	}
	
	public void setCompnayId(String compnayId) {
		this.compnayId = compnayId;
	}
	public String[] getVisitors() {
		return visitors;
	}
	public void setVisitors(String[] visitors) {
		this.visitors = visitors;
	}
	public String getFdate() {
		return fdate;
	}
	public void setFdate(String fdate) {
		this.fdate = fdate;
	}
	public String getTdate() {
		return tdate;
	}
	public void setTdate(String tdate) {
		this.tdate = tdate;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getExpectedTime() {
		return VisitExpectedTime;
	}
	public void setExpectedTime(String expectedTime) {
		VisitExpectedTime = expectedTime;
	}
	public String getSpermission() {
		return spermission;
	}
	public void setSpermission(String spermission) {
		this.spermission = spermission;
	}
	public String getOfficer() {
		return officer;
	}
	public void setOfficer(String officer) {
		this.officer = officer;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public String getIntimationByEmpNo() {
		return IntimationByEmpNo;
	}
	public void setIntimationByEmpNo(String intmationByEmpNo) {
		IntimationByEmpNo = intmationByEmpNo;
	}
	@Override
	public String toString() {
		return "NewIntimation [IntimationByEmpNo=" + IntimationByEmpNo + ", createBy=" + createBy + ", compnayId="
				+ compnayId + ", visitors=" + Arrays.toString(visitors) + ", fdate=" + fdate + ", tdate=" + tdate
				+ ", duration=" + duration + ", ExpectedTime=" + VisitExpectedTime + ", officer=" + officer + ", purpose="
				+ purpose + ", spermission=" + spermission + "]";
	}
}

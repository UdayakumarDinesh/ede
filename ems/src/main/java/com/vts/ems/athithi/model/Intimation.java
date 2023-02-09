package com.vts.ems.athithi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vp_intimation")
public class Intimation {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
      private Long IntimationId;
	  private String IntimationByEmpNo;
	  private Date IntimationDate;
      private String IntimationNo;
      private Long CompanyId;
      private Date FromDate;
      private Date ToDate;
      private String Duration;
      private String Purpose;
      private String SpecialPermission;
      private String OfficerEmpId;
      private String VpStatus;
      private String CreatedBy;
      private String CreatedDate;
      private String ModifiedBy;
      private String ModifiedDate;
      private int IsActive;
      
      
	public Long getIntimationId() {
		return IntimationId;
	}
	public void setIntimationByEmpNo(Long intimationId) {
		IntimationId = intimationId;
	}
	public String getIntmationByEmpNo() {
		return IntimationByEmpNo;
	}
	public void setIntimationByEmpNo(String intmationByEmpNo) {
		IntimationByEmpNo = intmationByEmpNo;
	}
	public Date getIntimationDate() {
		return IntimationDate;
	}
	public void setIntimationDate(Date intimationDate) {
		IntimationDate = intimationDate;
	}
	public String getIntimationNo() {
		return IntimationNo;
	}
	public void setIntimationNo(String intimationNo) {
		IntimationNo = intimationNo;
	}
	public Long getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(Long companyId) {
		CompanyId = companyId;
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
	
	public String getDuration() {
		return Duration;
	}
	public void setDuration(String duration) {
		Duration = duration;
	}
	public String getPurpose() {
		return Purpose;
	}
	public void setPurpose(String purpose) {
		Purpose = purpose;
	}
	public String getSpecialPermission() {
		return SpecialPermission;
	}
	public void setSpecialPermission(String specialPermission) {
		SpecialPermission = specialPermission;
	}
	public String getOfficerEmpId() {
		return OfficerEmpId;
	}
	public void setOfficerEmpId(String officerEmpId) {
		OfficerEmpId = officerEmpId;
	}
	public String getVpStatus() {
		return VpStatus;
	}
	public void setVpStatus(String vpStatus) {
		VpStatus = vpStatus;
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
	public String getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public String getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
      
      
      
      
}

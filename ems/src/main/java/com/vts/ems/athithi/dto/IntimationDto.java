package com.vts.ems.athithi.dto;

public class IntimationDto {
	
	private String IntimationId;
	private String IntimationDate;
	private String IntimationNo;
	private String CompanyId;
	private String FromDate;
	private String ToDate;
	private String AnticipatedTime;
	private String Duration;
	private String Purpose;
	private String SpecialPermission;
	private String OfficerEmpId;
	private String VpStatus;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private String IsActive;
	public String getIntimationId() {
		return IntimationId;
	}
	public void setIntimationId(String intimationId) {
		IntimationId = intimationId;
	}
	public String getIntimationDate() {
		return IntimationDate;
	}
	public void setIntimationDate(String intimationDate) {
		IntimationDate = intimationDate;
	}
	public String getIntimationNo() {
		return IntimationNo;
	}
	public void setIntimationNo(String intimationNo) {
		IntimationNo = intimationNo;
	}
	public String getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}
	public String getFromDate() {
		return FromDate;
	}
	public void setFromDate(String fromDate) {
		FromDate = fromDate;
	}
	public String getToDate() {
		return ToDate;
	}
	public void setToDate(String toDate) {
		ToDate = toDate;
	}
	public String getAnticipatedTime() {
		return AnticipatedTime;
	}
	public void setAnticipatedTime(String anticipatedTime) {
		AnticipatedTime = anticipatedTime;
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
	public String getIsActive() {
		return IsActive;
	}
	public void setIsActive(String isActive) {
		IsActive = isActive;
	}
	
	
	@Override
	public String toString() {
		return "IntimationDto [IntimationId=" + IntimationId + ", IntimationDate=" + IntimationDate + ", IntimationNo="
				+ IntimationNo + ", CompanyId=" + CompanyId + ", FromDate=" + FromDate + ", ToDate=" + ToDate
				+ ", AnticipatedTime=" + AnticipatedTime + ", Duration=" + Duration + ", Purpose=" + Purpose
				+ ", SpecialPermission=" + SpecialPermission + ", OfficerEmpId=" + OfficerEmpId + ", VpStatus="
				+ VpStatus + ", CreatedBy=" + CreatedBy + ", CreatedDate=" + CreatedDate + ", ModifiedBy=" + ModifiedBy
				+ ", ModifiedDate=" + ModifiedDate + ", IsActive=" + IsActive + "]";
	}	

}

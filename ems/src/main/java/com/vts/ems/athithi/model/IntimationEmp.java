package com.vts.ems.athithi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vp_intimation_emp")
public class IntimationEmp {

	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Long IntimationEmpId;
	  private Long InitmationId;
	  private Long CompanyEmpId;
	  private String CreatedBy;
	  private String CreatedDate;
	  private String ModifiedBy;
	  private String ModifiedDate;
	  private int IsActive;
	public Long getIntimationEmpId() {
		return IntimationEmpId;
	}
	public void setIntimationEmpId(Long intimationEmpId) {
		IntimationEmpId = intimationEmpId;
	}
	public Long getInitmationId() {
		return InitmationId;
	}
	public void setInitmationId(Long initmationId) {
		InitmationId = initmationId;
	}
	public Long getCompanyEmpId() {
		return CompanyEmpId;
	}
	public void setCompanyEmpId(Long companyEmpId) {
		CompanyEmpId = companyEmpId;
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

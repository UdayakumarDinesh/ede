package com.vts.ems.athithi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vp_company_emp")
public class CompanyEmployee {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Long CompanyEmpId;
	  private Long CompanyId;
	  private String CompanyEmpName;
	  private String Designation;
	  private String MobileNo;
	  private String EmpPhoto;
	  private String  CreatedBy;
	  private Date CreatedDate;
	  private Date ModifiedBy;
	  private Date ModifiedDate;
	  private int IsActive;
	  
	  
	public Long getCompanyEmpId() {
		return CompanyEmpId;
	}
	public void setCompanyEmpId(Long companyEmpId) {
		CompanyEmpId = companyEmpId;
	}
	public Long getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(Long companyId) {
		CompanyId = companyId;
	}
	public String getCompanyEmpName() {
		return CompanyEmpName;
	}
	public void setCompanyEmpName(String companyEmpName) {
		CompanyEmpName = companyEmpName;
	}
	public String getDesignation() {
		return Designation;
	}
	public void setDesignation(String designation) {
		Designation = designation;
	}
	public String getMobileNo() {
		return MobileNo;
	}
	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}
	public String getEmpPhoto() {
		return EmpPhoto;
	}
	public void setEmpPhoto(String empPhoto) {
		EmpPhoto = empPhoto;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	public Date getModifiedBy() {
		return ModifiedBy;
	}
	public void setModifiedBy(Date modifiedBy) {
		ModifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return ModifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
	  
	  

}

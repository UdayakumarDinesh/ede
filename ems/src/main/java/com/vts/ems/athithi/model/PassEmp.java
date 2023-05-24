package com.vts.ems.athithi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vp_pass_emp")
public class PassEmp {

	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Long PassEmpId;
	  private Long PassId;
	  private Long CompanyEmpId;
	  private String BadgeNo;
	  private String TimeIn;
	  private String TimeOut;
	  private Date PassDate;
	  private int IsActive;
	  
    public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
	private String CreatedBy;
	  private String CreatedDate;
	  private String ModifiedBy;
	  private String ModifiedDate;
	  
	  
	public Long getPassEmpId() {
		return PassEmpId;
	}
	public void setPassEmpId(Long passEmpId) {
		PassEmpId = passEmpId;
	}
	public Long getPassId() {
		return PassId;
	}
	public void setPassId(Long passId) {
		PassId = passId;
	}
	public Long getCompanyEmpId() {
		return CompanyEmpId;
	}
	public void setCompanyEmpId(Long companyEmpId) {
		CompanyEmpId = companyEmpId;
	}
	public String getBadgeNo() {
		return BadgeNo;
	}
	public void setBadgeNo(String badgeNo) {
		BadgeNo = badgeNo;
	}
	public String getTimeIn() {
		return TimeIn;
	}
	public void setTimeIn(String timeIn) {
		TimeIn = timeIn;
	}
	public String getTimeOut() {
		return TimeOut;
	}
	public void setTimeOut(String timeOut) {
		TimeOut = timeOut;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	 public Date getPassDate() {
			return PassDate;
		}
		public void setPassDate(Date passDate) {
			PassDate = passDate;
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
}

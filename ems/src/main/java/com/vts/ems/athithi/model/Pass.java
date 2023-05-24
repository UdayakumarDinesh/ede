package com.vts.ems.athithi.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vp_pass")
public class Pass {

	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Long PassId;
	  private Long InitimationId;
	  private String PassNo;
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
	  
	  
	public Long getPassId() {
		return PassId;
	}
	public void setPassId(Long passId) {
		PassId = passId;
	}
	public Long getInitimationId() {
		return InitimationId;
	}
	public void setInitimationId(Long initimationId) {
		InitimationId = initimationId;
	}
	public String getPassNo() {
		return PassNo;
	}
	public void setPassNo(String passNo) {
		PassNo = passNo;
	}
	public Date getPassDate() {
		return PassDate;
	}
	public void setPassDate(Date passDate) {
		PassDate = passDate;
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
	  
	  
	  
}

package com.vts.ems.athithi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vp_company")
public class Company {
	
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
      private Long CompanyId;
	  private String CompanyName;
	  private String CompanyCity;
	  private String CreatedBy;

	  
	  public Long getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(Long companyId) {
		CompanyId = companyId;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyCity() {
		return CompanyCity;
	}
	public void setCompanyCity(String companyCity) {
		CompanyCity = companyCity;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
}	

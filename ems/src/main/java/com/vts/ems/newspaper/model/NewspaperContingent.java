package com.vts.ems.newspaper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "pis_news_contingent")
public class NewspaperContingent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ContingentId;
	private String ContingentBillNo;
	private String ContingentDate;
	private Integer ClaimsCount;
	private String ContingentStatusCode;
	private String Remarks;
	private Long PO;
	private Long VO;
	private Long AO;
	private Long CEO;
	private String ApprovalDate;
	private String GenTillDate;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

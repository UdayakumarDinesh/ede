package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "chss_contingent")
public class CHSSContingent implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ContingentId;
	private String ContingentBillNo;
	private String ContingentDate;
	private Integer ClaimsCount;
	private Integer BillsCount;
	private Integer ContingentStatusId;
	private String Remarks;
	private String BillContent;
	private Long PO;
	private Long VO;
	private Long AO;
	private Long CEO;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	

}

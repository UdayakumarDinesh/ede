package com.vts.ems.chss.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chss_apply")
public class CHSSApply implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long CHSSApplyId;
	private String CHSSApplyNo;
	private String ParentCHSSApplyNo;
	private String Ailment;
	private Long EmpId; 
	private Long PatientId;
	private String IsSelf;
	private String FollowUp;
	private Long CHSSNewId;
	private String CHSSType;
	private Integer TreatTypeId;
	private Integer NoEnclosures;
	private String CHSSApplyDate;
	private String CHSSForwardDate;
	private Integer CHSSStatusId;
	private String Remarks;
	private Long ContingentId;
	private Integer POAcknowledge;
	private Long POId;
	private Long VOId;
	private BigDecimal AmountClaimed;
	private BigDecimal AmountSettled;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	

}

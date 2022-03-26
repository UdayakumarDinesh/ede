package com.vts.ems.chss.model;

import java.io.Serializable;

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
	private Integer CHSSStatus;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	

}

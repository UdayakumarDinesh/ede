package com.vts.ems.apf.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
@Entity
@Table(name="APF_apply")
public class ApfModel {	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long APFApplyId;
	private String EmpNo;
	private String APFFromDate;
	private String APFToDate;
	private double ApfAmount;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private byte IsActive;
	
}

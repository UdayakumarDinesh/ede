package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@Entity
@Table(name="employee")
public class Employee {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long EmpId;
	private Long SrNo;
	private String EmpNo;
	private String EmpName;
	private Long DesigId;
	private String ExtNo;
	private String Email;
	private Long DivisionId;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

	
}

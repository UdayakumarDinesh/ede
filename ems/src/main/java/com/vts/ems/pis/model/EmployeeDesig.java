package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "employee_desig")
public class EmployeeDesig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long DesigId;
	private String DesigCode;
	private String Designation;
	private long DesigLimit;
}

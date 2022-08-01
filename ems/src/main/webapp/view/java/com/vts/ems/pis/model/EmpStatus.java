package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="emp_status")
public class EmpStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int emp_status_id;
	private String emp_status;
	private String emp_status_name;
}

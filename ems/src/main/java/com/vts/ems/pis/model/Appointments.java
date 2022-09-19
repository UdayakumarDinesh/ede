package com.vts.ems.pis.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

@Entity
@Table(name="pis_appointments")
public class Appointments implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int appointment_id;
	private String empid;
	private Date from_date;
	private Date to_date;
	private int desig_id;
	private String org_lab;
	private String drdo_others;
	private String mode_recruitment;	
	private String ceptam_cycle;
	private String vacancy_year;
	private String recruitment_year;
	private int is_active;
	private String createdby;
	private String createddate;
	private String modifiedby;
	private String modifieddate;
}

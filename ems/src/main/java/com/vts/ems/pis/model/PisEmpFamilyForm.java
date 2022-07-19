package com.vts.ems.pis.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "pis_emp_family_form")
public class PisEmpFamilyForm implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private	long FamilyFormId;	
	private long Empid;
	private String FormType;
	private String FormStatus;
	private String ForwardedDateTime;
	private long ApprovedBy;
	private String ApprovedDateTime;
	private String Remarks;
	private int IsActive;
}

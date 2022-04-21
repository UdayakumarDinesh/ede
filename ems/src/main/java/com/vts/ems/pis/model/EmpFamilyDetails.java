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
@Table(name = "pis_emp_family_details")
public class EmpFamilyDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private	long family_details_id;
	
	private String empid;
	private String member_name;
	private int relation_id;
	private Date dob;
	private int family_status_id;
	private Date status_from;
	private String med_dep;
	private Date med_dep_from;
	private String ltc_dep;
	private Date ltc_dep_from;
	private String blood_group;
	private String remarks;
	private String labempid;
	private String PH;
	private String Gender;
	private String cghs_ben_id;
	private String emp_unemp;
	private String mar_unmarried;
	private int  IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private String EmpStatus;
}

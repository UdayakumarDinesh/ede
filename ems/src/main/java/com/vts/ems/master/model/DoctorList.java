package com.vts.ems.master.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity( name="chss_doctor_list" )
public class DoctorList implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long DoctorId;
	private String DoctorName;
	private String Qualification;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

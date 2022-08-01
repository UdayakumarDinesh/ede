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
@Entity(name = "chss_doctor_rates")
public class CHSSDoctorRates implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Integer DocRateId;
	private Integer TreatTypeId;
	private String DocQualification;
	private Integer consultation_1;
	private Integer consultation_2;
	private String DocRating;
	private int IsActive;
	private String ModifiedBy;
	private String ModifiedDate;
}

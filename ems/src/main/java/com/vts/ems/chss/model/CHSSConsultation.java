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
@Entity(name = "chss_consultation")
public class CHSSConsultation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long ConsultationId;
	private Long BillId;
	private String ConsultType; 
	private String DocName;
	private String DocQualification;
	private String ConsultDate;
	private Integer ConsultCharge;
	private Integer ConsultRemAmount;
	private Integer IsActive;
	
}

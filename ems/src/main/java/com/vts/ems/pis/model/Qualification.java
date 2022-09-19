package com.vts.ems.pis.model;

import java.io.Serializable;

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
@Table(name="pis_qualification")
public class Qualification implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int qualification_id;
	private String empid;
	private int quali_id;
	private int disci_id;
	private String university;
	private String yearofpassing;
	private String cgpa;
	private String division;
	private String specialization;
	private String sponsored;
	private String acq_bef_aft;
	private String hindi_prof;
	private String honours;
	private int is_active;
	private String createdby;
	private String createddate;
	private String modifiedby;
	private String modifieddate;
}

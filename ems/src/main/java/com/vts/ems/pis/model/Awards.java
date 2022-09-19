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
@Table(name="pis_awards")
public class Awards implements Serializable{

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int awards_id;
	private String empid;
	private int AwardListId;
	private String award_name;
	private String award_by;
	private String details;
	private Date award_date;
	private String certificate;
	private String citation;
	private String medallion;
	private String award_cat;
	private String cash;
	private String cash_amt;
	private String award_year;
	private int is_active;
	private String createdby;
	private String createddate;
	private String modifiedby;
	private String modifieddate;
}

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
@Table(name="pis_passport")
public class Passport implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int PassportId;
	private String  EmpId;
	private String PassportType;
	private Date ValidFrom;
	private Date ValidTo;
	private String PassportNo;
	private String status;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

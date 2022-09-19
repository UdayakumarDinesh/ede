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
@Table(name="pis_property")
public class Property implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int property_id;
	private String empid;
	private String movable;
	private String value;
	private String details;
	private Date dop;
	private String acquired_type;
	private Date noting_on;
	private String permission_info;
	private String remarks;
	private int is_active;
	private String createdby;
	private String createddate;
	private String modifiedby;
	private String modifieddate;
}

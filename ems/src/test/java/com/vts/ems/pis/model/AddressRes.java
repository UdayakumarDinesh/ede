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
@Getter
@Setter
@Entity
@Table(name="pis_address_res")
public class AddressRes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long address_res_id;
	private String empid;
	private String res_addr;
    private String landline;
	private String mobile;
	private String alt_mobile;
	private String ext;
    private Date from_res_addr;
    private String QtrNo;
	private String QtrType;
	private String QtrDetails;
	private String EmailDrona;
	private String EmailOfficial;
	private String EmailPersonal;
	private String EmailOutlook;
	
	private String state;
	private String city;
	private String pin;
	
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

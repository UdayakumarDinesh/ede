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
@Table(name="pis_address_per")
public class AddressPer implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long address_per_id;
	private String empid;	
	private String PerIntimationDate;	
    private String per_addr;
	private String landline;
	private String mobile;
	private String alt_mobile;
    private String hometown;
    private Date from_per_addr;
    private Date to_per_addr;
    private String state;
    private String city;
    
    private String pin;
    private String PerAdStatus;
	private String Remarks;
	private String PisStatusCode;
	private String PisStatusCodeNext;
    private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	
}

package com.vts.ems.newspaper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pis_tele_forward")
public class TelephoneForward {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int TeleForwardId;
	private String TeleUserFlag;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private String UserRemark;
	private String AdminRemark;
	private String SendBack;
	
	
	
}

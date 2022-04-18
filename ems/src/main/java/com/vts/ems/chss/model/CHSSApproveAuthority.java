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
@Entity(name = "chss_approve_auth")
public class CHSSApproveAuthority implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long ApproveAuthListId;
	private Long PO; 
	private Long VO; 
	private Long AO;	
	private Integer IsActive;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
}

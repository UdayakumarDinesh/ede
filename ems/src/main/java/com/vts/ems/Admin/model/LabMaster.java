package com.vts.ems.Admin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="lab_master")
public class LabMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long LabMasterId;
	private String LabCode;
	private String LabName;
	private String LabUnitCode;
	private String LabAddress;
	private String LabCity;
	private String LabPin;
	private String LabTelNo;
	private String LabFaxNo;
	private String LabEmail;
	private String LabAuthority;
	private Integer LabAuthorityId;
	private String LabRfpEmail;	
	private Integer LabId;
	private Integer ClusterId;
	private String LabLogo;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
	
}

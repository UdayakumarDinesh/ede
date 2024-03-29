package com.vts.ems.master.model;

import java.io.Serializable;

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
@Table(name="lab_master")
public class LabMaster implements Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long LabMasterId;
	private String LabCode;
	private String LabCodeH;
	private String LabName;
	private String LabNameH;
	private String LabUnitCode;
	private String LabAddress;
	private String LabAddressH;
	private String LabCity;
	private String LabCityH;
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

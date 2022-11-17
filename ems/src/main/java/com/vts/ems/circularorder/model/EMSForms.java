package com.vts.ems.circularorder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ems_forms")
public class EMSForms {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long EMSFormId;
	private int DepTypeId;
	private String FormNo;
	private String Description;
	private String FormName;
	private String FormPath;
	private String FormUploadDate;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	
}

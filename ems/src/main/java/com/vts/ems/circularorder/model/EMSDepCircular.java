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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ems_dep_circular")
public class EMSDepCircular {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long DepCircularId;
	private int DepTypeId;
	private String DepCircularNo;
	private String DepCircularDate; 
	private String DepCircularKey;
	private String DepCirSubject;
	private String DepCirFileName;
	private String DepCircularPath;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	
}

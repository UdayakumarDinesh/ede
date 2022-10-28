package com.vts.ems.circularorder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ems_circular")
public class EMSCircular {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long CircularId;
	private String CircularNo;
	private String CircularDate;
	private String CirSubject;
	private String CirFileName;
	private String CircularPath;
	private String CircularKey;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	

}

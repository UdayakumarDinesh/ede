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
@Table(name = "ems_dep_circular_trans")
public class DepEMSCircularTrans {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long DepCircularTransId;
	private long DepCircularId;
	private String EmpNo;
	private long DownloadBy;
	private String DownloadDate;
	private String IPAddress;
	private String MacAddress;
}

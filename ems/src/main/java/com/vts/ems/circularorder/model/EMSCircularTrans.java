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
@Table(name = "ems_circular_trans")
public class EMSCircularTrans {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long CircularTransId;
	private long CircularId;
	private String EmpNo;
	private long DownloadBy;
	private String DownloadDate;
	private String IPAddress;
	private String MacAddress;
}

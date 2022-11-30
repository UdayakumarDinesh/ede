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
@Table(name="ems_govt_orders")
public class EMSGovtOrders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long GovtOrderId;
	private int DepTypeId;
	private String OrderNo;
	private String OrderDate;
	private String Description;
	private String OrderFileName;
	private String OrderFilePath;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	
}

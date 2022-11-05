package com.vts.ems.circularorder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Qualifier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ems_office_order")
public class EMSOfficeOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long OrderId;
	private String OrderNo;
	private String OrderDate;
	private String OrderSubject;
	private String OrderFileName;
	private String OrderPath;
	private String OrderKey;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	

}

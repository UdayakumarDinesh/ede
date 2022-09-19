package com.vts.ems.newspaper.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pis_newspaper_bill")
public class NewspaperBill {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int NewspaperBillId;
	private Date FromDate;
	private Date ToDate;
	private double TotalAmount;
	private String SubmitBy;
	private String SubmitDate;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
	
	
}



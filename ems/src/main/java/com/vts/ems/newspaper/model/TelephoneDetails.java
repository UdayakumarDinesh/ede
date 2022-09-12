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
@Table(name="pis_tele_d")
public class TelephoneDetails {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int TeleDId;
	private int TeleId;
	private Date TeleFromDate;
	private Date TeleToDate;
	private String TeleMonth;
	private String TeleYear;
	private int TeleUsersId;
	private String TeleBillNo;
	private Date TeleBillDate;
	private double BasicAmount;
	private double TaxAmount;
	private double TotalAmount;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

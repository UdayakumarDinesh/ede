package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chss_bill")
public class CHSSBill implements Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long BillId;
	private long CHSSApplyId; 
	private long CHSSConsultMainId;
	private String BillNo; 
	private String CenterName;
	private String BillDate;
	private Double ItemsTotalAmt;
	private Double GSTAmount;
	private Double Discount;
	private Double DiscountPercent;
	private Double FinalBillAmt;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

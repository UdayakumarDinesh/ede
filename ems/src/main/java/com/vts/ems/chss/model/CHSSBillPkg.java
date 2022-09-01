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
@Entity(name = "chss_bill_pkg")
public class CHSSBillPkg implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long CHSSBillPkgId;
	private long BillId;
	private long TestSubId;
	private double PackageCost;
	private double AmountPaid;
	private double PkgRemAmt;
	private String Comments;	
	private Long UpdateByEmpId;
	private String UpdateByRole;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate; 
	
}

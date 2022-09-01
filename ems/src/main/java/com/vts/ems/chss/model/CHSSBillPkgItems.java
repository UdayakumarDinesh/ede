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
@Entity(name = "chss_bill_pkgitems")
public class CHSSBillPkgItems implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long BillPkgItemId;
	private long BillId;
	private long CHSSBillPkgId;
	private int IPDPkgItemId;
	private double PkgItemCost;
	private double AmountPaid;
	private double PkgItemRemAmt;
	private long UpdateByEmpId;
	private String UpdateByRole;
	private String Comments;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

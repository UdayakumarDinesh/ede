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
@Entity(name = "chss_bill_ipdheads")
public class CHSSBillIPDheads implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long CHSSItemHeadId;
	private long BillId;
	private int IPDBillHeadId;
	private double BillHeadCost;
	private double AmountPaid;
	private double BillHeadRemAmt;
	private String Comments;
	private Long UpdateByEmpId;
	private String UpdateByRole;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

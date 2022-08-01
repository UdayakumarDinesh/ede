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
@Entity(name = "chss_bill_misc")
public class CHSSBillMisc implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long ChssMiscId;
	private Long BillId;
	private String MiscItemName;
	private Integer MiscCount;
	private double MiscItemCost;
	private double AmountPaid;
	private double MiscRemAmount;
	private String Comments;
	private Long UpdateByEmpId;
	private String UpdateByRole;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

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
@Entity(name = "chss_bill_equipment")
public class CHSSBillEquipment implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long CHSSEquipmentId;
	private long BillId;
	private String EquipmentName;
	private double EquipmentCost;
	private double AmountPaid;
	private double EquipmentRemAmt;
	private Long UpdateByEmpId;
	private String UpdateByRole;
	private String Comments;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

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
@Entity(name = "chss_bill_medicine")
public class CHSSBillMedicine implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long CHSSMedicineId;
	private Long BillId;
	private String MedicineName;
	private Integer PresQuantity;
	private Integer MedQuantity;
	private double MedicineCost;
	private double AmountPaid;
	private double MedsRemAmount;
	private String Comments;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

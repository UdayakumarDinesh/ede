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
@Entity(name = "chss_medicine")
public class CHSSMedicine implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long MedicineId;
	private Long BillId;
	private String MedicineName;
	private Integer MedicineCost;
	private Integer PresQuantity;
	private Integer MedQuantity;
//	private String MedicineDate;
	private Integer MedsRemAmount;
	private Integer IsActive;

}

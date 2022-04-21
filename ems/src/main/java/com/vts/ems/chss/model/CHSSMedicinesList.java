package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "chss_medicines_list")
public class CHSSMedicinesList implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long MedicineId;
	private Integer TreatTypeId;
	private Integer CategoryId;
	private String MedicineName;
	private String IsAdmissible;
	private Integer IsActive;
}
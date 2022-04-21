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
@Entity(name = "chss_medicines_list")
public class CHSSMedicineList implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy =  GenerationType.IDENTITY)
		private Long MedicineId;
		private Long TreatTypeId;
		private Long CategoryId;
		private String MedicineName;
		private String IsAdmissible;
		private Integer IsActive;

	

}

package com.vts.ems.chss.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CHSSMedicineDto {

	private String MedicineId;
	private String BillId;
	private String[] MedicineName;
	private String[] MedicineCost;
	private String[] MedicineDate;
	private String[] MedQuantity;
	private String Isactive;
}
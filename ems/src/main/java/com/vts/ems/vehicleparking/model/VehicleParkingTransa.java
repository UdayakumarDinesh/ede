package com.vts.ems.vehicleparking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	@Entity
	@Table(name = "Veh_Park_transa")
	public class VehicleParkingTransa {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long VehParkkTransId;
		@OneToOne
		private VehicleParkingApplications Veh_Park_App_id;
		private String ApplicStatusCode;
		private String Remarks;
		private String ActionBy;
		private String ActionDate;

}


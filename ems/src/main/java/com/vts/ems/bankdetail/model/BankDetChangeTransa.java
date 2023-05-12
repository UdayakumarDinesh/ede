package com.vts.ems.bankdetail.model;

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
	@Table(name = "bank_details_p_transa")
	public class BankDetChangeTransa {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long BankTransactionId;
		@OneToOne
		private BankDertails Bank_detail_id;
		private String BankStatusCode;
		private String Remarks;
		private String ActionBy;
		private String ActionDate;

}

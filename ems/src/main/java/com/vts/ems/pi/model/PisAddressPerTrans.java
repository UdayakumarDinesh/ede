package com.vts.ems.pi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "pis_address_per_trans")
public class PisAddressPerTrans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long PerTransactionId;
	private long address_per_id;
	private String PisStatusCode;
	private String Remarks;
	private String ActionBy;
	private String ActionDate;
}

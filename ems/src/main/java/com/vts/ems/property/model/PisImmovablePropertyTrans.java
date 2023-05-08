package com.vts.ems.property.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vts.ems.pi.model.PisHometownTrans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pis_immovable_property_trans")
public class PisImmovablePropertyTrans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ImmTransactionId;
	private Long ImmPropertyId;
	private String PisStatusCode;
	private String Remarks;
	private String ActionBy;
	private String ActionDate;
}

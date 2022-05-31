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
@Table(name = "chss_contingent_transaction")
public class CHSSContingentTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ContinTransactionId;
	private long ContingentId;
	private Integer StatusId;
	private String Remarks;
	private Long ActionBy;
	private String ActionDate;
}

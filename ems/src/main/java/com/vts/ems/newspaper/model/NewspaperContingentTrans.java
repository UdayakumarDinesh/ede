package com.vts.ems.newspaper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;


@Data
@Entity
@Table(name = "pis_newspaper_contingent_trans")
public class NewspaperContingentTrans {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ContinTransactionId;
	private long ContingentId;
	private String StatusCode;
	private String Remarks;
	private String ActionBy;
	private String ActionDate;
}

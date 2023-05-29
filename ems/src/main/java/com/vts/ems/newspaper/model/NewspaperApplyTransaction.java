package com.vts.ems.newspaper.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

	@Data
	@Entity(name = "pis_newspaperApply_trans")
	public class NewspaperApplyTransaction implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy =  GenerationType.IDENTITY)
		private Long NewspaperTransactionId;
		private long NewspaperApplyId;
		private String NewspapertatusCode;
		private String Remark;
		private String ActionBy;
		private String ActionDate;
	}



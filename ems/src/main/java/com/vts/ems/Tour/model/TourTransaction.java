package com.vts.ems.Tour.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity

@Table(name = "tour_transaction")
public class TourTransaction {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long TourTransactionId;
	private Long TourApplyId;
	private String TourStatusCode;
	private String ActionBy;
	private String ActionDate;
	private String TourRemarks;
}

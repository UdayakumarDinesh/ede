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
@Table(name = "chss_other")
public class CHSSOther implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CHSSOtherId;
	private Long BillId;
	private Integer OtherItemId;
	private Integer OtherItemCost;
	private Integer OtherRemAmount;
	private Integer IsActive;
}
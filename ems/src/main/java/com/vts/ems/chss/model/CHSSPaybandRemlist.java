package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "chss_payband_remlist")
public class CHSSPaybandRemlist implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer PaybandRemAmountId;
	private Integer OtherItemId;
	private Integer Level1;
	private Integer Level2;
	private Integer Level3;
	private Integer Level4;
	private Integer IsActive;
	
}

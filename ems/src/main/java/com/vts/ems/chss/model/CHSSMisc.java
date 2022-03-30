package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chss_misc")
public class CHSSMisc implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long ChssMiscId;
	private Long BillId;
	private String MiscItemName;
	private Integer MiscItemCost;
	private Integer MiscRemAmount;
	private Integer IsActive;
}
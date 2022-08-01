package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@Entity
@Table(name="pis_pay_level")
public class PisPayLevel 
{	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int PayLevelId;
	private String PayLevel;
	private double NewsRestrictAmt;
	private double TeleRestrictAmt;
	
}

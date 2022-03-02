package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="pis_cadre")
public class PisCadre {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int CadreId;
	private String Cadre;
	private String CadreDetail;
	private int IsActive;
}

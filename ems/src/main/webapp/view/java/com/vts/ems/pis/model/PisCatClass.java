package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="pis_cat_class")
public class PisCatClass {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cat_class_id;
	private String cat_id;
	private String cat_name;
	private String cat_ngo_cgo;
}

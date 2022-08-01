package com.vts.ems.pis.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="pis_category")
public class PisCategory {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int category_id;
	private String category_type;
	private String category_desc;

}

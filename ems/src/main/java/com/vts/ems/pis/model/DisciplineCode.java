package com.vts.ems.pis.model;

import java.io.Serializable;

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
@Table(name="pis_disci_code")
public class DisciplineCode implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int disci_id;
	private String disci_title;
    private int is_active;
	private String created_by;
	private String created_date;
	private String modified_by;
	private String modified_date;
	
}

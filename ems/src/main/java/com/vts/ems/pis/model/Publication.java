package com.vts.ems.pis.model;

import java.io.Serializable;
import java.sql.Date;

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
@Table(name="pis_publication")
public class Publication implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int publication_id;
	private String empid;
	private String pub_type;
	private String authors;
	private String discipline;
	private String title;
	private String pub_name_vno_pno;
	private Date pub_date;
	private String patent_no;
	private String country;
	private int is_active;
	private String createdby;
	private String createddate;
	private String modifiedby;
	private String modifieddate;
}

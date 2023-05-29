package com.vts.ems.master.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name="pis_admins")
public class PisAdmins implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long AdminsId;
	private String PandAAdmin;
	private String FandAAdmin;
	private String AdminFrom;
	private String AdminTo;
	private String RevisedOn;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

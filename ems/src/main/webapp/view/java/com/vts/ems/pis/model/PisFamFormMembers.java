package com.vts.ems.pis.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pis_fam_form_members")
public class PisFamFormMembers implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private	long FormMemberId;
	private	long FamilyFormId;
	private	long FamilyDetailsId;
	private	String Comments;
	private	String IncExcDate;
	private	String AttachFilePath;
	private	int IsActive;
	private	String CreatedBy;
	private	String CreatedDate;
	
}

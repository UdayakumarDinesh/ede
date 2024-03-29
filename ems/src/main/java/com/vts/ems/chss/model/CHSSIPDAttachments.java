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
@Entity(name = "chss_ipd_attachments")
public class CHSSIPDAttachments implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private long IPDAttachId;
	private long CHSSApplyId;
	private int IPDAttachTypeId;
	private String CopyAttached;
	private Integer IsActive;

}

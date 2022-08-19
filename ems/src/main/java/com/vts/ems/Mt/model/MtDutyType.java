package com.vts.ems.Mt.model;

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
//@Entity
//@Table(name="mt_duty_type")
public class MtDutyType implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int DutyTypeId;
	private String TypeofDuty;
	private int IsActive;
	
	
}

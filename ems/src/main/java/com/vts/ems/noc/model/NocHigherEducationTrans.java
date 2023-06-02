package com.vts.ems.noc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity(name="noc_higher_education_trans")
public class NocHigherEducationTrans {
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	
	private long EducationTransId;
	private long NocEducationId;
	private String NocStatusCode;
	private String Remarks;
	private String ActionBy;
	private String ActionDate;

}

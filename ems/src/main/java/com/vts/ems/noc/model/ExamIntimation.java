package com.vts.ems.noc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="intimation_exam")
public class ExamIntimation {
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	
	private long ExamId;
	private String EmpNo;
	private String ExamName;
	private String ProbableDate;
	private String InitiatedDate;
	private String ForwardedDate;
	private String ApprovedDate;
	private String Remarks;
	private String IntimationStatus;
	private String IntimateStatusCode;
	private String InitimateStatusCodeNext;
    private String CreatedBy; 
    private String CreatedDate; 
    private String ModifiedBy; 
    private String ModifiedDate;
    private int isActive;
  
}

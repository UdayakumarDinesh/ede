package com.vts.ems.noc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="noc_higher_education")
public class NocHigherEducation {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	
	private long NocEducationId;
	private String NocEducationNo;
	private String EmpNo;
	private String InstitutionType;
	private String AcademicYear;
	private String Course;
	private String Specialization; 
    private String EducationType;
    private String QualifiactionRequired;
    private String Declaration; 
    private String Remarks; 
    private String HigherEducationStatus;
    private String NocStatusCode;
    private String NocStatusCodeNext;
    private String InitiatedDate;
    private String ForwardedDate; 
    private String ApprovedDate;
    private String CreatedBy; 
    private String CreatedDate; 
    private String ModifiedBy; 
    private String ModifiedDate;
    private int isActive;
    
   
}

package com.vts.ems.noc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(name="noc_passport")
public class NocPassport {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	
	          private long NocPassportId;
	          private String  EmpNo;
	          private String RelationType; 
	          private String RelationName; 
	          private String RelationOccupation;
	          private String RelationAddress;
	          private String RelationAbroad; 
	          private String EmployementDetails;
	          private String LostPassport;
	          private String PassportType;
	          private String Status;
	          private String ForwardedDate;
	          private String ReturnedDate;
	          private String ApprovedDate ;
	          private String GroupInCharge; 
	          private String DeptInCharge;
	          private String DGMInCharge; 
	          private String PAInCharge; 
	          private String CEO;
	          private String PassportEntries; 
	          private String EmployeeSuspensed;
	          private String EmployeeInvolvement;
	          private String EmployeeObligation;
	          private String CreatedBy; 
	          private String CreatedDate; 
	          private String ModifiedBy; 
	          private String ModifiedDate;
	          private String isActive;
			  

}

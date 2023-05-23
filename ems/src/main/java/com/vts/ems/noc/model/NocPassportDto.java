package com.vts.ems.noc.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NocPassportDto {
	
	
	 private long NocPassportId;
     private String NocPassportNo;
     private String  EmpNo;
     private String PassportExist;
     private String RelationType; 
     private String RelationName; 
     private String RelationOccupation;
     private String RelationAddress;
     private String RelationAbroad; 
     private String EmployementDetails;
     private String LostPassport;
     private String PassportType;
     private String ContractualObligation;
     private String FromDate;
     private String ToDate;
     private String PassportStatus;
     private String NocStatusCode;
     private String NocStatusCodeNext;
     private String Remarks;
     private String InitiatedDate;
     private String ForwardedDate;
     private String ReturnedDate;
     private String ApprovedDate ;
     private String PassportEntries; 
     private String PassportEntriesDetails;
     private String EmployeeSuspensed;
     private String EmployeeInvolvement;
     private String EmployeeCaseDetails;
     private String PandAModifiedBy;
     private String PandAModifiedDate;
     private String CreatedBy; 
     private String CreatedDate; 
     private String ModifiedBy; 
     private String ModifiedDate;
     private int isActive;

}

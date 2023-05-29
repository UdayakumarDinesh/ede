package com.vts.ems.noc.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NocProceedingAbroadDto {

	
	private long NocProcId;
	private String NocProcAbroadNo;
    private String EmpNo;
    private String PassportExist;
    private String RelationType; 
    private String RelationName; 
    private String RelationOccupation;
    private String RelationAddress;
    private String RelationAbroad; 
    private String EmployementDetails;
    private String EmployeeInvolvement;
    private String PropertyFiled;
    private String ForeignVisit;
    private String ForeignVisitDetails;
    private String CountriesProposed;
    private String DepartureDate;
    private String VisitPurpose;
    private String StayDuration;
    private String ReturnDate;
    private String Going;
    private String FamilyDetails;
    private String ExpectedAmount;
    private String FinancedBy;
    private String AmountSource;
	private String Name;
    private String Nationality;
    private String Relationship;
    private String RelationshipAddress;
    private String LostPassport;
    private String PassportType;
    private String ContractualObligation;
    private String Hospatility;
    private String FromDate;
    private String ToDate;
    private String ProcAbroadStatus;
    private String NocStatusCode;
    private String Remarks;
    private String NocStatusCodeNext;
    private String WorkHandled;
    private String VisitRecommended;
    private String LeaveGranted;
    private String InitiatedDate;
    private String ForwardedDate;
    private String ReturnedDate;
    private String ApprovedDate ;
    private String ProcAbroadEntries;
    private String ProcAbroadEntriesDetails;
    private String EmployeeCaseDetails;
    private String EmployeeDues;
    private String PandAModifiedBy;
    private String PandAModifiedDate;
    private String CreatedBy; 
    private String CreatedDate; 
    private String ModifiedBy; 
    private String ModifiedDate;
    private int isActive;
    
}

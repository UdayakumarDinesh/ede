package com.vts.ems.noc.model;

import org.springframework.web.multipart.MultipartFile;

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
    private long StayDuration;
    private String ReturnDate;
    private String Going;
    private String FamilyDetails;
    private long ExpectedAmount;
    private String FinancedBy;
    private long AmountSpend;
	private MultipartFile FormFile;
    private String FilePath;
    private String FileName;
    private String NameNationality;
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
    private String ForwardedDate;
    private String ReturnedDate;
    private String ApprovedDate ;
    private String CreatedBy; 
    private String CreatedDate; 
    private String ModifiedBy; 
    private String ModifiedDate;
    private int isActive;
    
}

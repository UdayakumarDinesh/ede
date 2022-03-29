package com.vts.ems.chss.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CHSSApplyDto {

	private String CHSSApplyId;
	private String CHSSApplyNo;
	private String EmpId; 
	private String PatientId;
	private String RelationId;
	private String IsSelf;
	private String FollowUp;
	private String CHSSNewId;
	private String CHSSType;
	private String TreatTypeId;
	private String NoEnclosures;
	private String CHSSStatus;
	private String Remarks;
	private String IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private String Ailment;
	
	private String[] BillNo; 
	private String[] CenterName;
	private String[] BillDate;
//	private String[] BillAmount;
	
}

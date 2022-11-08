package com.vts.ems.chss.dto;

import lombok.Data;

@Data
public class CHSSOtherDto {

	private String CHSSOtherId;
	private String BillId;
	private String OtherItemId[];
	private String OtherItemCost[];
	
	private String empid;
	private String Comments;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

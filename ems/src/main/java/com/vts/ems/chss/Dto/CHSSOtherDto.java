package com.vts.ems.chss.Dto;

import lombok.Data;

@Data
public class CHSSOtherDto {

	private String CHSSOtherId;
	private String BillId;
	private String OtherItemId[];
	private String OtherItemCost[];
	
	private String empid;
	
}

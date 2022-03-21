package com.vts.ems.chss.Dto;

import lombok.Data;

@Data
public class CHSSTestsDto{

	
	private String CHSSTestId;
	private String BillId;
	private String TestMainId[];
	private String TestSubId[];
	private String TestCost[];
	private String CreatedBy;
}

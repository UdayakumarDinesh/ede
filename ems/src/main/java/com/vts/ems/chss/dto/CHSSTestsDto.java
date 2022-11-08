package com.vts.ems.chss.dto;

import lombok.Data;

@Data
public class CHSSTestsDto{

	
	private String CHSSTestId;
	private String BillId;
	private String TestMainId[];
	private String TestSubId[];
	private String TestCost[];
	private String Comments;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

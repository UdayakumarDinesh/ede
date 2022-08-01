package com.vts.ems.chss.Dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChssBillsDto implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	private String CHSSApplyId;
	private String CHSSConsultMainId;
	private String[] BillNo; 
	private String[] CenterName;
	private String[] BillDate;
	private String[] GSTAmount;
	private String[] Discount;
	private String[] DiscountPer;
	private String[] finalbillamount;
	
	private String IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;


}

package com.vts.ems.chss.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CHSSMiscDto {

	private String ChssMiscId;
	private String BillId;
	private String[] MiscItemName;
	private String[] MiscItemCost;
	private String Isactive;
}
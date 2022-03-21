package com.vts.ems.chss.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CHSSOthersDto {

	private String ChssOthersId;
	private String BillId;
	private String[] OtherItemName;
	private String[] OtherItemCost;
	private String Isactive;
}

package com.vts.ems.pis.dto;

import lombok.Data;

@Data
public class CHSSExclusionFormDto {

	private String FamilyDetailsId;
	private long ExcFormId;
	private String ExcDate;
	private String Comment;
	private String ExcFilePath;
	
	@Override
	public String toString() {
		return "CHSSExclusionFormDto [FamilyDetailsId=" + FamilyDetailsId + ", ExcFormId=" + ExcFormId + ", ExcDate="
				+ ExcDate + ", Comment=" + Comment + ", ExcFilePath=" + ExcFilePath + "]";
	}

}

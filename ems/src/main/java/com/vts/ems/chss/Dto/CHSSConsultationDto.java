package com.vts.ems.chss.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CHSSConsultationDto {

	private String ConsultationId;
	private String BillId;
	private String[] ConsultType; 
	private String[] DocName;
	private String[] DocQualification;
	private String[] ConsultDate;
	private String[] ConsultCharge;
	private String Comments;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}
package com.vts.ems.master.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class CircularListDto {

	
	private Long CircularId;
	private String Description;
	private MultipartFile Path;
	private String OriginalName;
	private String FromDate;
	private String ToDate;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

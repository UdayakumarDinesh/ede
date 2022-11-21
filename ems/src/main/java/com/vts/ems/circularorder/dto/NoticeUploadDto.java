package com.vts.ems.circularorder.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeUploadDto {
	
	private String NoticeId;
	private String ReferenceNo;
	private String Description;
	private String NoticeDate;
	private String ToDate;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

	private MultipartFile NoticeFile;
}

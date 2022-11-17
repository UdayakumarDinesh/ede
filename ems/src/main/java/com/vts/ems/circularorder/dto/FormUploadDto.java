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
public class FormUploadDto {
	
	private Long FormId;
	private String DepTypeId;
	private String FormNo;
	private String Description;
	private MultipartFile FormFile;
    private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

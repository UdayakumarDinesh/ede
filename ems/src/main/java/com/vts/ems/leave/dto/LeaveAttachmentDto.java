package com.vts.ems.leave.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class LeaveAttachmentDto {

	private String ApplId;
	private String FilePath;
	private String FileName;
	private String Type;
	private String CreatedBy;
	private String CreatedDate;
	private MultipartFile File;
    private String McFc;
	
}

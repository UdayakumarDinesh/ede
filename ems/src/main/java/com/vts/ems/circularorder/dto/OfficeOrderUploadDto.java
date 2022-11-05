package com.vts.ems.circularorder.dto;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OfficeOrderUploadDto {
	
	private Long OrderId;
	private String OrderNo;
	private String OrderDate;
	private String OrderSubject;
	private String OrderFileName;
	private MultipartFile OrderPath;
    private String AutoId;
	private InputStream IS;
    private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

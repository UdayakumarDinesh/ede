package com.vts.ems.circularorder.dto;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CircularUploadDto {
	
	private Long CircularId;
	private String CircularNo;
	private String CircularDate;
	private String CirSubject;
	private String OriginalName;
	private MultipartFile CircularPath;
    private String AutoId;
	private InputStream IS;
    private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

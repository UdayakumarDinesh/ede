package com.vts.ems.circularorder.dto;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CircularUploadDto {
	
	private String CircularNo;
	private String CircularDate;
	private String CirSubject;
	private String OriginalName;
	private String PathName;
    private String AutoId;
	private String CirFileName;
	private InputStream IS;
	private String FileNamePath;
    private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

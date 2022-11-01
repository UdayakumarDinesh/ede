package com.vts.ems.circularorder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdfFileEncryptionDataDto {

	private String sourcePath;
	private String targetPath;
	private String password;
	private String watermarkText;
	private String EmpNo;
	private String EmpName;
	
}

package com.vts.ems.master.dto;

import org.springframework.web.multipart.MultipartFile;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MasterEditDto {
	
	private Long MasterEditId;
	private String TableName;
	private Long TableRowId;
	private String Comments;
	private MultipartFile FilePath;
	private String OriginalName;
	private String CreatedBy;
	private String CreatedDate;
}

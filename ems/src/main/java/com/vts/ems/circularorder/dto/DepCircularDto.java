package com.vts.ems.circularorder.dto;

import org.springframework.web.multipart.MultipartFile;

import com.vts.ems.circularorder.model.EMSDepCircular;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepCircularDto {

	private EMSDepCircular Circular;
	private MultipartFile cirFile;
}

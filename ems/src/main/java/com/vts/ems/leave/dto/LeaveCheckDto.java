package com.vts.ems.leave.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaveCheckDto {
   
	   private String EmpNo;
	   private String LeaveType;
	   private String ElCash;
	   private String FromDate;
	   private String ToDate;
	   private String Hours;
	   private String HalfOrFull;
	   private String UserId;
	
	
}

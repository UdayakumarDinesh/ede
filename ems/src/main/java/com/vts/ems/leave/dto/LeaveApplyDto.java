package com.vts.ems.leave.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaveApplyDto {
	   private String EmpNo;
	   private String LeaveType;
	   private String AnFN;
	   private String FromDate;
	   private String ToDate;
	   private String Hours;
	   private String HalfOrFull;
	   private String PurLeave;
	   private String LeaveAddress;
	   private String LTC;
	   private String Remarks;
	   private String HandingOverEmpid;
	   private String UserId;
	   private String ActEmpNo;
}

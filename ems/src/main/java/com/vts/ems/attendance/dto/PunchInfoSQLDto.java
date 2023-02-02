package com.vts.ems.attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PunchInfoSQLDto {

	private long Punchid;
	private String Half;
	private String Status;
	private String UserId;
	private String PunchDate;
	private String PunchInTime;
	private String PunchOutTime;
	private String WorkTime;
	
}

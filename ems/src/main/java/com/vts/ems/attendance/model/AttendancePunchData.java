package com.vts.ems.attendance.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ems_attend_punch_data")
public class AttendancePunchData implements Serializable
{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long AttendPunchId;
	private String EmpNo;
	private String Status;
	private String Half;
	private String AttendanceDate;
	private String PunchInTime;
	private String PunchOutTime;
	private String WorkTime;
	private String CreatedDate;
}

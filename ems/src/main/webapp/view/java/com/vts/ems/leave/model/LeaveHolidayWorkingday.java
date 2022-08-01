package com.vts.ems.leave.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="leave_holiday_workingday")
@Data
@NoArgsConstructor
public class LeaveHolidayWorkingday implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long HolidayId;
	private Date HoliDate;
	private String HoliName;
	private String HoliType;

	
}

package com.vts.ems.Admin.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name="ems_calandar_events")
public class CalendarEvents  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long EMSEventId;
	private String EventTypeCode;
	private String EventDate;
	private String EventName;
	private String EventDescription;
	private String CreatedBy;
	private String CreatedDate;
	private Integer IsActive;

}

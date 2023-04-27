package com.vts.ems.Tour.model;

import java.sql.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Table(name = "tour_onwardreturn")
public class TourOnwardReturn {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long TourOnwardReturnId;
	private Long TourApplyId;
	private Date TourDate;
	private String TourTime;
	private Long ModeId;
	private Long FromCityId;
	private Long ToCityId;
}

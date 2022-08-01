package com.vts.ems.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "ems_notification")

public class EMSNotification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long NotificationId;
	private Long EmpId;
	private Long NotificationBy;
	private String NotificationDate;
	private String NotificationMessage;
	private String NotificationUrl;
	private String NotificationValue;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	
	
	
}

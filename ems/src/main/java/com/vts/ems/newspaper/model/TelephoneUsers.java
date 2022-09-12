package com.vts.ems.newspaper.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pis_tele_users")
public class TelephoneUsers {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long TeleUsersId;
	private String EmpId;
	private String DeviceId;
	private String DeviceNo;
	private int  IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
	
	

}

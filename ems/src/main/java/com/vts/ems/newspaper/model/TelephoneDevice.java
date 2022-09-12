package com.vts.ems.newspaper.model;

import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pis_tele_device")
public class TelephoneDevice {

	
	@Id
	private String DeviceId;
	private String DeviceName;
	
}

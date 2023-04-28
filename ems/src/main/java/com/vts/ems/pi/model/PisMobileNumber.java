package com.vts.ems.pi.model;

import java.io.Serializable;
import java.util.Date;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pis_mobile_number")
public class PisMobileNumber implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long MobileNumberId;
	private String EmpId;
	private String MobileNumber;
	private String AltMobileNumber;
	private Date MobileFrom;
	private Date MobileTo;
	private String MobileStatus;
	private String Remarks;
	private String PisStatusCode;
	private String PisStatusCodeNext;
	
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

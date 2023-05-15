package com.vts.ems.bankdetail.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name="bank_detail")
public class BankDertails implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long BankDetailId;
	private String EmpNo;
	private String BankName;
	private String Branch;
	private String AccountNo;
	private String IFSC;
	private String ValidFrom;
	private String ValidTo;
	private String BankStatus;
	private String BankStatusCode;
	private String Remarks;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

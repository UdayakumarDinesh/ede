package com.vts.ems.bankdetail.model;

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
public class BankDertails {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long BankId;
	private String EmpNo;
	private String BankName;
	private String Branch;
	private String AccountNo;
	private String IFSC;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

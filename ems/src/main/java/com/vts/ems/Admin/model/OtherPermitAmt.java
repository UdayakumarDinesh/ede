package com.vts.ems.Admin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chss_other_perm_amt")
public class OtherPermitAmt  implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long CHSSOtherAmtId;
	private Integer OtherItemId;
	private Long BasicFrom; 
	private Long BasicTo;
	private Integer ItemPermitAmt;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

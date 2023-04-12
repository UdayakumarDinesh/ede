package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "chss_apply_dispute")
public class CHSSApplyDispute implements Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private long CHSSDisputeId;
	private long CHSSApplyId;
	private String DisputeMsg; 
	private String ResponseMsg;
	private String RaisedTime;
	private long ResponderEmpid;
	private String ResponseTime;
	private String DispStatus;
	private String DispReapplyStatus;
	private String Action;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	
}

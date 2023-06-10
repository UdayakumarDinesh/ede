package com.vts.ems.condone.model;

import java.io.Serializable;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "seip_condone")
public class SEIPCondone implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long CondoneId;
	private String EmpNo;
	private String CondonationNo;
	private String CondoneStatusCode;
	private int CondoneTypeId;
	private String Subject;
	private String MainContent;
	private String CondoneDate;
	private String ForwardedDate;
	private String IsAccepted;
	private String Remark;
	private String CondoneNextStatus;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;	
	
}

package com.vts.ems.chss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "chss_apply_transaction")
public class CHSSApplyTransaction implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long CHSSTransactionId;
	private Long CHSSApplyId;
	private Integer CHSSStatusId;
	private String Remark;
	private Long ActionBy;
	private String ActionDate;
}

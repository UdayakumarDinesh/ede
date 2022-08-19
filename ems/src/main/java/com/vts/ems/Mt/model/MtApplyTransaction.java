package com.vts.ems.Mt.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//@Entity
//@Table(name="mt_appl_transaction")
public class MtApplyTransaction implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int MtApplTransactionId;
	private int MtApplId;
	private String MtStatus;
	private String MtRemarks;
	private String ActionBy;
	private String ActionDate;
	public int getMtApplTransactionId() {
		return MtApplTransactionId;
	}
	public void setMtApplTransactionId(int mtApplTransactionId) {
		MtApplTransactionId = mtApplTransactionId;
	}
	
	public int getMtApplId() {
		return MtApplId;
	}
	public void setMtApplId(int mtApplId) {
		MtApplId = mtApplId;
	}
	public String getMtStatus() {
		return MtStatus;
	}
	public void setMtStatus(String mtStatus) {
		MtStatus = mtStatus;
	}
	public String getMtRemarks() {
		return MtRemarks;
	}
	public void setMtRemarks(String mtRemarks) {
		MtRemarks = mtRemarks;
	}
	public String getActionBy() {
		return ActionBy;
	}
	public void setActionBy(String actionBy) {
		ActionBy = actionBy;
	}
	public String getActionDate() {
		return ActionDate;
	}
	public void setActionDate(String actionDate) {
		ActionDate = actionDate;
	}
	
	
	
}

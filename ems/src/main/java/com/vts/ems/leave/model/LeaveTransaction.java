package com.vts.ems.leave.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_transaction")
public class LeaveTransaction {

	
	
	
	
	   @Id	
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   private int	LeaveTransactionId;
	   private String  LeaveApplId;
	   private Date LeaveTransacDate;
	   private String ActionBy;
	   private String ActionDate;
	   private String LeaveStatus;
	   private String LeaveRemarks;
	   private String AdmId;
	   private String AdmDate;
	   private String RaId;
	   private String RaDate;
	   private String SaId;
	   private String SaDate;
	   private int IsModify;
	   
	   
	public int getLeaveTransactionId() {
		return LeaveTransactionId;
	}
	public void setLeaveTransactionId(int leaveTransactionId) {
		LeaveTransactionId = leaveTransactionId;
	}
	
	public String getLeaveApplId() {
		return LeaveApplId;
	}
	public void setLeaveApplId(String leaveApplId) {
		LeaveApplId = leaveApplId;
	}
	public Date getLeaveTransacDate() {
		return LeaveTransacDate;
	}
	public void setLeaveTransacDate(Date leaveTransacDate) {
		LeaveTransacDate = leaveTransacDate;
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
	public String getLeaveStatus() {
		return LeaveStatus;
	}
	public void setLeaveStatus(String leaveStatus) {
		LeaveStatus = leaveStatus;
	}
	public String getLeaveRemarks() {
		return LeaveRemarks;
	}
	public void setLeaveRemarks(String leaveRemarks) {
		LeaveRemarks = leaveRemarks;
	}
	public String getAdmId() {
		return AdmId;
	}
	public void setAdmId(String admId) {
		AdmId = admId;
	}
	public String getAdmDate() {
		return AdmDate;
	}
	public void setAdmDate(String admDate) {
		AdmDate = admDate;
	}
	public String getRaId() {
		return RaId;
	}
	public void setRaId(String raId) {
		RaId = raId;
	}
	public String getRaDate() {
		return RaDate;
	}
	public void setRaDate(String raDate) {
		RaDate = raDate;
	}
	public String getSaId() {
		return SaId;
	}
	public void setSaId(String saId) {
		SaId = saId;
	}
	public String getSaDate() {
		return SaDate;
	}
	public void setSaDate(String saDate) {
		SaDate = saDate;
	}
	public int getIsModify() {
		return IsModify;
	}
	public void setIsModify(int isModify) {
		IsModify = isModify;
	}
		
	    
	
	
	
	
	
}

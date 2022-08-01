package com.vts.ems.leave.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ltc_deduction")
public class LtcDeduction {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long LtcDeductionId;
	
	
	 private String EmpId;
	 private int ElDeducted;
	 private String DeductedBy;
	 private Date   DeductedDate;
	 private String RevokeBy;
	 private Date   RevokeDate;
	 private String Status;
	 private String Appl_Id;
	 private String CreatedBy;
	 private String CreatedDate;
	 private int IsActive;
	public long getLtcDeductionId() {
		return LtcDeductionId;
	}
	public void setLtcDeductionId(long ltcDeductionId) {
		LtcDeductionId = ltcDeductionId;
	}
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	
	public int getElDeducted() {
		return ElDeducted;
	}
	public void setElDeducted(int elDeducted) {
		ElDeducted = elDeducted;
	}
	public String getDeductedBy() {
		return DeductedBy;
	}
	public void setDeductedBy(String deductedBy) {
		DeductedBy = deductedBy;
	}
	public Date getDeductedDate() {
		return DeductedDate;
	}
	public void setDeductedDate(Date deductedDate) {
		DeductedDate = deductedDate;
	}
	public String getRevokeBy() {
		return RevokeBy;
	}
	public void setRevokeBy(String revokeBy) {
		RevokeBy = revokeBy;
	}
	public Date getRevokeDate() {
		return RevokeDate;
	}
	public void setRevokeDate(Date revokeDate) {
		RevokeDate = revokeDate;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getAppl_Id() {
		return Appl_Id;
	}
	public void setAppl_Id(String appl_Id) {
		Appl_Id = appl_Id;
	}
	public String getCreatedBy() {
		return CreatedBy;
	}
	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}
	public String getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}
	public int getIsActive() {
		return IsActive;
	}
	public void setIsActive(int isActive) {
		IsActive = isActive;
	}
	
	 
	 
	
}

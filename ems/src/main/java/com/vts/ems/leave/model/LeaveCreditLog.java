package com.vts.ems.leave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_credit_log")
public class LeaveCreditLog {
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int LeaveCreditLogId;
    private String EmpId;
	private	double CL;
	private int EL;
	private int HPL;
	private int CML;
	private int RH;
	private int ML;
	private int PL;
	private int CCL;
	private int SL;
	private int EOL;
	private String Status;
	private String ActionBy;
	private String ActionDate;
	
	
	public int getLeaveCreditLogId() {
		return LeaveCreditLogId;
	}
	public void setLeaveCreditLogId(int leaveCreditLogId) {
		LeaveCreditLogId = leaveCreditLogId;
	}
	public String getEmpId() {
		return EmpId;
	}
	public void setEmpId(String empId) {
		EmpId = empId;
	}
	public double getCL() {
		return CL;
	}
	public void setCL(double cL) {
		CL = cL;
	}
	public int getEL() {
		return EL;
	}
	public void setEL(int eL) {
		EL = eL;
	}
	public int getHPL() {
		return HPL;
	}
	public void setHPL(int hPL) {
		HPL = hPL;
	}
	public int getCML() {
		return CML;
	}
	public void setCML(int cML) {
		CML = cML;
	}
	public int getRH() {
		return RH;
	}
	public void setRH(int rH) {
		RH = rH;
	}
	public int getML() {
		return ML;
	}
	public void setML(int mL) {
		ML = mL;
	}
	public int getPL() {
		return PL;
	}
	public void setPL(int pL) {
		PL = pL;
	}
	public int getCCL() {
		return CCL;
	}
	public void setCCL(int cCL) {
		CCL = cCL;
	}
	public int getSL() {
		return SL;
	}
	public void setSL(int sL) {
		SL = sL;
	}
	public int getEOL() {
		return EOL;
	}
	public void setEOL(int eOL) {
		EOL = eOL;
	}
	
	
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
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

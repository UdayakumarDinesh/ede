package com.vts.ems.leave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_sa_ra")
public class LeaveRaSa {
	
	
	@Id
	//private int LeaveSaRaId;
	private String EMPID;
	private String RA;
	private String SA;
	private String STATUS;
	private String TD_RA;
	private String TD_SA;
	private String TD_STATUS;
	private String MT_STATUS;
	private String HR_STATUS;
	private String MMG_STATUS;
	private String PIS_STATUS;
	private String DO_STATUS;
	private String ATTND_STATUS;
	public String getATTND_STATUS() {
		return ATTND_STATUS;
	}

	public void setATTND_STATUS(String aTTND_STATUS) {
		ATTND_STATUS = aTTND_STATUS;
	}

	public String getMT_STATUS() {
		return MT_STATUS;
	}

	public void setMT_STATUS(String mT_STATUS) {
		MT_STATUS = mT_STATUS;
	}

	public String getHR_STATUS() {
		return HR_STATUS;
	}

	public void setHR_STATUS(String hR_STATUS) {
		HR_STATUS = hR_STATUS;
	}

	public String getMMG_STATUS() {
		return MMG_STATUS;
	}

	public void setMMG_STATUS(String mMG_STATUS) {
		MMG_STATUS = mMG_STATUS;
	}

	public String getPIS_STATUS() {
		return PIS_STATUS;
	}

	public void setPIS_STATUS(String pIS_STATUS) {
		PIS_STATUS = pIS_STATUS;
	}

	public String getDO_STATUS() {
		return DO_STATUS;
	}

	public void setDO_STATUS(String dO_STATUS) {
		DO_STATUS = dO_STATUS;
	}

	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	

	
	
	
	public String getEMPID() {
		return EMPID;
	}
	
	public void setEMPID(String eMPID) {
		EMPID = eMPID;
	}
	public String getRA() {
		return RA;
	}
	public void setRA(String rA) {
		RA = rA;
	}
	public String getSA() {
		return SA;
	}
	public void setSA(String sA) {
		SA = sA;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	
	public String getTD_STATUS() {
		return TD_STATUS;
	}
	public void setTD_STATUS(String tD_STATUS) {
		TD_STATUS = tD_STATUS;
	}


	public String getTD_RA() {
		return TD_RA;
	}

	public void setTD_RA(String tD_RA) {
		TD_RA = tD_RA;
	}

	public String getTD_SA() {
		return TD_SA;
	}

	public void setTD_SA(String tD_SA) {
		TD_SA = tD_SA;
	}

//	public String getLTC_RA() {
//		return LTC_RA;
//	}
//
//	public void setLTC_RA(String lTC_RA) {
//		LTC_RA = lTC_RA;
//	}
//
//	public String getLTC_SA() {
//		return LTC_SA;
//	}
//
//	public void setLTC_SA(String lTC_SA) {
//		LTC_SA = lTC_SA;
//	}
//
//	public String getLTC_STATUS() {
//		return LTC_STATUS;
//	}
//
//	public void setLTC_STATUS(String lTC_STATUS) {
//		LTC_STATUS = lTC_STATUS;
//	}

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

	public String getModifiedBy() {
		return ModifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		ModifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return ModifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		ModifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "LeaveRaSa [EMPID=" + EMPID + ", RA=" + RA + ", SA=" + SA + "]";
	}




}

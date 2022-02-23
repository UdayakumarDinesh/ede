package com.vts.ems.leave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_mc_fc")
public class LeaveMC_FC {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int mc_fc_id;
	private String empid;
	private String appl_id;
	private String MC;
	private String FC;
	private String NC;
	private String MC_file;
	private String FC_file;
	
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	public int getMc_fc_id() {
		return mc_fc_id;
	}
	public void setMc_fc_id(int mc_fc_id) {
		this.mc_fc_id = mc_fc_id;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getAppl_id() {
		return appl_id;
	}
	public void setAppl_id(String appl_id) {
		this.appl_id = appl_id;
	}
	public String getMC() {
		return MC;
	}
	public void setMC(String mC) {
		MC = mC;
	}
	public String getFC() {
		return FC;
	}
	public void setFC(String fC) {
		FC = fC;
	}
	public String getNC() {
		return NC;
	}
	public void setNC(String nC) {
		NC = nC;
	}
	public String getMC_file() {
		return MC_file;
	}
	public void setMC_file(String mC_file) {
		MC_file = mC_file;
	}
	public String getFC_file() {
		return FC_file;
	}
	public void setFC_file(String fC_file) {
		FC_file = fC_file;
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
		return "LeaveMC_FC [mc_fc_id=" + mc_fc_id + ", empid=" + empid + ", appl_id=" + appl_id + ", MC=" + MC + ", FC="
				+ FC + ", NC=" + NC + ", MC_file=" + MC_file + ", FC_file=" + FC_file + ", CreatedBy=" + CreatedBy
				+ ", CreatedDate=" + CreatedDate + ", ModifiedBy=" + ModifiedBy + ", ModifiedDate=" + ModifiedDate
				+ "]";
	}
	
	
	
}

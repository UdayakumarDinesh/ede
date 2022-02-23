package com.vts.ems.leave.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_ra_sa_handingover")
public class LeaveHandingOver{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int handingover_id;
	private String from_empid;
	private String to_empid;
	private Date applied_date;
	private Date from_date;
	private Date to_date;
	private String status;
	private String appl_id;
	private String login_type;
	private String createdby;
	private String createdate;
	private String modifiedby;
	private String modifieddate;
	private int is_active;
	private String group_id;
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	private String RevokeStatus;
	private String RevokeEmpid;
	private String RevokeDate;
	
	
	public int getHandingover_id() {
		return handingover_id;
	}
	public void setHandingover_id(int handingover_id) {
		this.handingover_id = handingover_id;
	}
	public String getFrom_empid() {
		return from_empid;
	}
	public void setFrom_empid(String from_empid) {
		this.from_empid = from_empid;
	}
	public String getTo_empid() {
		return to_empid;
	}
	public void setTo_empid(String to_empid) {
		this.to_empid = to_empid;
	}
	public Date getApplied_date() {
		return applied_date;
	}
	public void setApplied_date(Date applied_date) {
		this.applied_date = applied_date;
	}
	public Date getFrom_date() {
		return from_date;
	}
	public void setFrom_date(Date from_date) {
		this.from_date = from_date;
	}
	public Date getTo_date() {
		return to_date;
	}
	public void setTo_date(Date to_date) {
		this.to_date = to_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLogin_type() {
		return login_type;
	}
	public void setLogin_type(String login_type) {
		this.login_type = login_type;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getModifieddate() {
		return modifieddate;
	}
	public void setModifieddate(String modifieddate) {
		this.modifieddate = modifieddate;
	}
	public int getIs_active() {
		return is_active;
	}
	public void setIs_active(int is_active) {
		this.is_active = is_active;
	}
	
	
	
	public String getAppl_id() {
		return appl_id;
	}
	public void setAppl_id(String appl_id) {
		this.appl_id = appl_id;
	}
	
	
	
	
	
	
	public String getRevokeStatus() {
		return RevokeStatus;
	}
	public void setRevokeStatus(String revokeStatus) {
		RevokeStatus = revokeStatus;
	}
	public String getRevokeEmpid() {
		return RevokeEmpid;
	}
	public void setRevokeEmpid(String revokeEmpid) {
		RevokeEmpid = revokeEmpid;
	}
	public String getRevokeDate() {
		return RevokeDate;
	}
	public void setRevokeDate(String revokeDate) {
		RevokeDate = revokeDate;
	}
	@Override
	public String toString() {
		return "LeaveHandingOver [handingover_id=" + handingover_id + ", from_empid=" + from_empid + ", status="
				+ status + ", login_type=" + login_type + "]";
	}


}



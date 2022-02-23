package com.vts.ems.leave.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_purpose")
public class LeavePurpose {
     
	@Id
	private int id;
	private String reasons;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReasons() {
		return reasons;
	}
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	
	
	@Override
	public String toString() {
		return "LeavePurpose [id=" + id + ", reasons=" + reasons + "]";
	}
	
	
	
	
	
}

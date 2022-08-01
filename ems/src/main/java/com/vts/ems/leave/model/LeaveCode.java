package com.vts.ems.leave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="leave_code")
public class LeaveCode {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int leave_code_id;
	private String leave_code;
	private String type_of_leave;
	private String leave_name;
	
	
	public String getLeave_code() {
		return leave_code;
	}
	public void setLeave_code(String leave_code) {
		this.leave_code = leave_code;
	}
	public String getType_of_leave() {
		return type_of_leave;
	}
	public void setType_of_leave(String type_of_leave) {
		this.type_of_leave = type_of_leave;
	}
	public String getLeave_name() {
		return leave_name;
	}
	public void setLeave_name(String leave_name) {
		this.leave_name = leave_name;
	}
	
	
	
	public int getLeave_code_id() {
		return leave_code_id;
	}
	public void setLeave_code_id(int leave_code_id) {
		this.leave_code_id = leave_code_id;
	}
	@Override
	public String toString() {
		return "LeaveCode [leave_code=" + leave_code + ", type_of_leave=" + type_of_leave + "]";
	}
	
	

 

}

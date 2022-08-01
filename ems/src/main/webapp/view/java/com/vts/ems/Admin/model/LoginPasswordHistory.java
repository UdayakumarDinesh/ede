package com.vts.ems.Admin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "login_password_history")
public class LoginPasswordHistory   {

//	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long PasswordHistoryId;
	private Long LoginId;
	private String Password;
	private String ActionType;
	private String ActionBy;
	private String ActionDate;

}

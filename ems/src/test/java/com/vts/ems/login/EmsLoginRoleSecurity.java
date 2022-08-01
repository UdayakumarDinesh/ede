package com.vts.ems.login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ems_login_role_security")
public class EmsLoginRoleSecurity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long LoginRoleSecurityId ;
	private Long LoginId;
	private Long RoleId;
	
	public Long getLoginRoleSecurityId() {
		return LoginRoleSecurityId;
	}
	public void setLoginRoleSecurityId(Long loginRoleSecurityId) {
		LoginRoleSecurityId = loginRoleSecurityId;
	}
	public Long getLoginId() {
		return LoginId;
	}
	public void setLoginId(Long loginId) {
		LoginId = loginId;
	}
	public Long getRoleId() {
		return RoleId;
	}
	public void setRoleId(Long roleId) {
		RoleId = roleId;
	}

}

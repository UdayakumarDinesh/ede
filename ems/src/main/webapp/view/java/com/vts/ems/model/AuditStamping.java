package com.vts.ems.model;


import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "audit_stamping")
public class AuditStamping {
	 @Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AuditStampingId;
	private Long LoginId;
    public Long getLoginId() {
		return LoginId;
	}
	public void setLoginId(Long loginId) {
		LoginId = loginId;
	}
	private String Username;
    private Date LoginDate;
    private String LoginDateTime;
    private String IpAddress;
    private String MacAddress;
    private String LogOutType;
    private String LogOutDateTime;
	public Long getAuditStampingId() {
		return AuditStampingId;
	}
	public void setAuditStampingId(Long auditStampingId) {
		AuditStampingId = auditStampingId;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public Date getLoginDate() {
		return LoginDate;
	}
	public void setLoginDate(Date loginDate) {
		LoginDate = loginDate;
	}
	public String getLoginDateTime() {
		return LoginDateTime;
	}
	public void setLoginDateTime(String loginDateTime) {
		LoginDateTime = loginDateTime;
	}
	public String getIpAddress() {
		return IpAddress;
	}
	public void setIpAddress(String ipAddress) {
		IpAddress = ipAddress;
	}
	public String getMacAddress() {
		return MacAddress;
	}
	public void setMacAddress(String macAddress) {
		MacAddress = macAddress;
	}
	public String getLogOutType() {
		return LogOutType;
	}
	public void setLogOutType(String logOutType) {
		LogOutType = logOutType;
	}
	public String getLogOutDateTime() {
		return LogOutDateTime;
	}
	public void setLogOutDateTime(String logOutDateTime) {
		LogOutDateTime = logOutDateTime;
	}
   
   
    
   
}

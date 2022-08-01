package com.vts.ems.leave.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name="leave_ra_sa_handingover")
public class LeaveHandingOver{


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long HandingoverId;
	private String FromEmpId;
	private String ToEmpId;
	private Date AppliedDate;
	private Date FromDate;
	private Date ToDate;
	private String Status;
	private String ApplId;
	private String LoginType;
	private String CreatedBy;
	private String CreateDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	private Long DivisionId;
	private String RevokeStatus;
	private String RevokeEmpId;
	private String RevokeDate;
	
	

}



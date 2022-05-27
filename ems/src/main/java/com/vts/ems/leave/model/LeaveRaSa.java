package com.vts.ems.leave.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="leave_sa_ra")
@Data
@NoArgsConstructor
public class LeaveRaSa {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long LeaveSaRaId;
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
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	

	
	



}

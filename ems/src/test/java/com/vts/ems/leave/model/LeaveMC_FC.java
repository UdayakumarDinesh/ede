package com.vts.ems.leave.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="leave_mc_fc")
public class LeaveMC_FC {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long McFcId;
	private String applId;
	private String MC_file;
	private String FC_file;
	
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
    private int IsActive;
	
	
}

package com.vts.ems.circularorder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "ems_notice")
public class EMSNotice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long NoticeId;
	private String ReferenceNo;
	private String Description;
	private String NoticePath;
	private String FileOriginalName;
	private String NoticeDate;
	private String ToDate;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	private int IsActive;
	
}

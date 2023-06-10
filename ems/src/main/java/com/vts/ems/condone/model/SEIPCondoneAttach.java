package com.vts.ems.condone.model;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "seip_condone_attach")
public class SEIPCondoneAttach {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long CondoneAttachId;
	private Long CondoneId;
	private String AttachPath;
	private String FileName;
	private String OriginalFileName;
	private int IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
	

}

package com.vts.ems.noc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

@Entity(name="noc_passport_trans")
public class NocPassportTrans {
	
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private long NocPassportTransId;
	private long NocPassportId;
	private String NocStatusCode;
	private String Remarks;
	private String ActionBy;
	private String ActionDate;
	
	
	
	

}

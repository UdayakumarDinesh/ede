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
@Table(name="leave_register")
@Data
@NoArgsConstructor
public class LeaveRegisterModel {
	
	
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long RegisterId;
    private String EMPID;
	private	double CL;
	private int EL;
	private int EL_LAPSE;
	private int HPL;
	private int CML;
	private int RH;
	private int ML;
	private int PL;
	private int CCL;
	private int SL;
	private int ADV_EL;
	private int ADV_HPL;
	private int EOL;
	private Date FROM_DATE;
	private Date TO_DATE;
	private String MONTH;
	private String YEAR;
	private String STATUS;
	private String REMARKS;
	private String APPL_ID;
	private String CREDITED_ON;
	private String CREDITED_BY;

	
	
	
	

}

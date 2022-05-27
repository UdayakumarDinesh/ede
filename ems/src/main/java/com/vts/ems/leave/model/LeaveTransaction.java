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
@Table(name="leave_transaction")
@Data
@NoArgsConstructor
public class LeaveTransaction {

	   @Id	
	   @GeneratedValue(strategy=GenerationType.IDENTITY)
	   private int	LeaveTransactionId;
	   private String  LeaveApplId;
	   private String ActionBy;
	   private String ActionDate;
	   private String LeaveStatus;
	   private String LeaveRemarks;


	    
	
	
	
	
	
}

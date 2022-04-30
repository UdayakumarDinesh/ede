package com.vts.ems.chss.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="chss_tests")
public class CHSSTests implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long CHSSTestId;
	private Long BillId;
	private Long TestMainId;
	private Long TestSubId;
	private Integer TestCost;
	private Integer TestRemAmount;
	private String Comments;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;
}

package com.vts.ems.property.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pis_property_construction")
public class PisPropertyConstruction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ConstructionId;
	private String EmpNo;
	private String PermissionDate;
	private String LetterNo;
	private String LetterDate;
	private String TransactionState;
	private Double EstimatedCost;
	private String SupervisedBy;
	private Date ConstrCompletedBy;
	private String ContractorDealings;
	private String NatureOfDealings;
	private String ContractorName;
	private String ContractorPlace;
	private Double OwnSavings;
	private String OwnSavingsDetails;
	private Double Loans;
	private String LoansDetails;
	private Double OtherSources;
	private String OtherSourcesDetails;
	private String Comments;
	private Double ProposedCost;
	private String ConstrStatus;
	private String Remarks;
	private String PisStatusCode;
	private String PisStatusCodeNext;
	private String SOEmpNo;
	private String PandAEmpNo;
	private Integer IsActive;
	private String CreatedBy;
	private String CreatedDate;
	private String ModifiedBy;
	private String ModifiedDate;

}

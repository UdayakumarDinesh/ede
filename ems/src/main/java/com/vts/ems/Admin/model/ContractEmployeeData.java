package com.vts.ems.Admin.model;

import java.io.Serializable;


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
@Table(name="employee_contract")

public class ContractEmployeeData implements Serializable {
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long ContractEmpId;
private String EmpName;
private String ContractEmpNo;
private String UserName;
private String DateOfBirth;
private String Password;
private Long MobileNo;
private String EmailId;
private Integer IsActive;
private String CreatedBy;
private String CreatedDate;
private String ModifiedBy;
private String ModifiedDate;

}

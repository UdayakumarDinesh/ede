package com.vts.ems.login;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vts.ems.Admin.model.EmployeeContract;

public interface EmpContractRepo extends JpaRepository<EmployeeContract, Long> {
	EmployeeContract findByContractEmpNo(String ContractEmpNo);
}

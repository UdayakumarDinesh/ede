package com.vts.ems.noc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Transactional
@Repository
public class NocDaoImpl implements NocDao {

	
	@PersistenceContext
	EntityManager manager;

	private static final String NOCEMPLIST="SELECT e.EmpName,ed.Designation ,dm.DivisionName ,ad.res_addr FROM employee e,employee_desig ed,division_master dm,pis_address_res  ad WHERE ed.DesigId=e.DesigId AND e.divisionid=dm.divisionid AND ad.EmpId=e.EmpNo AND  e.EmpNo=:EmpNo";
	@Override
	public List<Object[]> getNocEmpList(String empNo) throws Exception {
		
		
		Query query=manager.createNativeQuery(NOCEMPLIST);
		query.setParameter("EmpNo", empNo);
		return (List<Object[]>)query.getResultList();
		
	}

}

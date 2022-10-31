package com.vts.ems.circularorder.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.vts.ems.circularorder.model.EMSCircular;
import com.vts.ems.pis.model.EmployeeDetails;

@Repository
@Transactional
public class CircularDaoImpl implements CircularDao 
{
	@Autowired
	EntityManager manager;
	
	private static final String GETCIRCULARDATA ="from ems_circular where CircularId =:CircularId ";
	@Override
	public EMSCircular getCircularData(String CircularId) throws Exception
	{
		Query query=manager.createQuery(GETCIRCULARDATA);
		query.setParameter("CircularId", Long.parseLong(CircularId));				
		try {
			return (EMSCircular)query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	private static final String GETEMPDATADATA ="from EmployeeDetails where EmpNo =:empNo ";
	@Override
	public EmployeeDetails getEmpdataData(String empNo) throws Exception
	{
		Query query=manager.createQuery(GETEMPDATADATA);
		query.setParameter("empNo", empNo);				
		try {
			return (EmployeeDetails)query.getSingleResult();
		}catch (NoResultException e) {
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}

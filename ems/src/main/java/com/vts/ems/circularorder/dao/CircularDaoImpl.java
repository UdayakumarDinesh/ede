package com.vts.ems.circularorder.dao;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;


import org.springframework.stereotype.Repository;



@Repository
@Transactional
public class CircularDaoImpl implements CircularDao {
	
	@PersistenceContext
    EntityManager manager;
	
	private static final String SELECTALLLIST="SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject FROM ems_circular";

	@Override
	public List<Object[]> selectAllList() throws Exception {

		Query query = manager.createNativeQuery(SELECTALLLIST);
		List<Object[]> CircularList= query.getResultList();
		System.out.println(CircularList);
		return CircularList;
	}

	private static final String CIRCULARLIST = "SELECT CircularNo,DATE_FORMAT(CircularDate,'%d-%m-%Y'),CirSubject,CircularId FROM ems_circular WHERE ( CircularDate>=:fromdate AND CircularDate <=:todate )  ORDER BY CircularDate DESC";
	
	@Override
	public List<Object[]> GetCircularList(LocalDate fromdate, LocalDate toDate) throws Exception {
	 
		System.out.println(fromdate);
		System.out.println(toDate);
		Query query =  manager.createNativeQuery(CIRCULARLIST);
		 query.setParameter("fromdate", fromdate);
		 query.setParameter("todate", toDate);
		 List<Object[]> CircularList=query.getResultList();
		 System.out.println(CircularList);
		 return CircularList;
		
	}

}

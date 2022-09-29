package com.vts.ems.dopart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.dopart.dao.DoPartDao;

@Service
public class DoPartServiceImpl implements DoPartService {

	
	@Autowired
	DoPartDao dao;
	
	
	@Override
	public List<Object[]> GetDOList() throws Exception {
		
		return dao.GetDOList();
	}
	
	@Override
	public List<Object[]> RetriveContent(int DoNumber,String year)throws Exception
	{
		return dao.RetriveContent(DoNumber,year);
	}

}

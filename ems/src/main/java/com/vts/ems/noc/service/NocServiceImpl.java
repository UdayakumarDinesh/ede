package com.vts.ems.noc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.vts.ems.noc.dao.NocDao;

@Service
public class NocServiceImpl implements NocService {

	@Autowired
	NocDao dao;
	
	@Override
	 public List<Object[]> getNocEmpList(String empNo) throws Exception {
		
		return dao.getNocEmpList(empNo);
	}

}

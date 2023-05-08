package com.vts.ems.property.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.property.dao.PropertyDao;
import com.vts.ems.property.model.PisImmovableProperty;

@Service
public class PropertyServiceImp implements PropertyService{

	@Autowired
	PropertyDao dao;

	@Override
	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception {
		
		return dao.ImmPropDetails(EmpNo);
	}

	@Override
	public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception {
		
		return dao.getImmovablePropertyById(ImmPropertyId);
	}

	@Override
	public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		return dao.addImmovableProperty(immovable);
	}

	@Override
	public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception {
		
		return dao.addImmovableProperty(immovable);
	}
}

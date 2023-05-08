package com.vts.ems.property.dao;

import java.util.List;

import com.vts.ems.property.model.PisImmovableProperty;

public interface PropertyDao {
	
	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception;
    public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception;
    public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception;
}

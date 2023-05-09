package com.vts.ems.property.service;

import java.util.List;

import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;

public interface PropertyService {

	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception;
	public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception;
	public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long addImmovablePropertyTransaction(PisImmovablePropertyTrans transaction) throws Exception;
    public List<Object[]> immmovablePropertyTransList(String immPropertyId) throws Exception;
}

package com.vts.ems.property.dao;

import java.util.List;

import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.model.PisMovableProperty;
import com.vts.ems.property.model.PisMovablePropertyTrans;

public interface PropertyDao {
	
	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception;
    public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception;
    public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long addImmovablePropertyTransaction(PisImmovablePropertyTrans transaction) throws Exception;
    public List<Object[]> immmovablePropertyTransList(String immPropertyId) throws Exception;
    public Object[] getEmpNameDesig(String EmpNo) throws Exception;
    public List<Object[]> immPropTransactionApprovalData(String ImmPropertyId) throws Exception;
    public List<Object[]> propertyApprovalList(String EmpNo) throws Exception;
    public List<Object[]> movPropDetails(String empNo) throws Exception;
    public PisMovableProperty getMovablePropertyById(Long movPropertyId) throws Exception;
    public Long addMovableProperty(PisMovableProperty movable) throws Exception;
    public Long editMovableProperty(PisMovableProperty movable) throws Exception;
    public Long addMovablePropertyTransaction(PisMovablePropertyTrans transaction) throws Exception;
    public List<Object[]> movablePropertyTransList(String movPropertyId) throws Exception;
    public List<Object[]> movPropTransactionApprovalData(String movPropertyId);
    public List<Object[]> immPropertyRemarksHistory(String ImmPropertyId) throws Exception;
    public List<Object[]> propertyApprovedList(String EmpNo,String FromDate,String ToDate) throws Exception;
    
}

package com.vts.ems.property.service;

import java.util.List;

import com.vts.ems.master.model.LabMaster;
import com.vts.ems.property.model.PisImmovableProperty;
import com.vts.ems.property.model.PisImmovablePropertyTrans;
import com.vts.ems.property.model.PisMovableProperty;
import com.vts.ems.property.model.PisMovablePropertyTrans;
import com.vts.ems.property.model.PisPropertyConstruction;
import com.vts.ems.property.model.PisPropertyConstructionTrans;

public interface PropertyService {

	public List<Object[]> ImmPropDetails(String EmpNo) throws Exception;
	public PisImmovableProperty getImmovablePropertyById(Long ImmPropertyId) throws Exception;
	public Long addImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long editImmovableProperty(PisImmovableProperty immovable) throws Exception;
    public Long addImmovablePropertyTransaction(PisImmovablePropertyTrans transaction) throws Exception;
    public List<Object[]> immmovablePropertyTransList(String immPropertyId) throws Exception;
    public Object[] getEmpNameDesig(String EmpNo) throws Exception;
    public List<Object[]> immPropTransactionApprovalData(String ImmPropertyId) throws Exception;
    public Long immovablePropForward(String immPropertyId,String username, String action, String remarks, String apprEmpNo,String loginType ) throws Exception;
    public List<Object[]> propertyApprovalList(String EmpNo) throws Exception;
    public List<Object[]> movPropDetails(String empNo) throws Exception;
    public PisMovableProperty getMovablePropertyById(Long movPropertyId) throws Exception;
    public Long addMovableProperty(PisMovableProperty movable) throws Exception;
    public Long editMovableProperty(PisMovableProperty movable) throws Exception;
    public List<Object[]> movablePropertyTransList(String movPropertyId) throws Exception;
    public List<Object[]> movPropTransactionApprovalData(String movPropertyId);
    public Long movablePropForward(String movPropertyId,String username, String action, String remarks, String apprEmpNo,String loginType ) throws Exception;
    public List<Object[]> immPropertyRemarksHistory(String ImmPropertyId) throws Exception;
    public List<Object[]> propertyApprovedList(String EmpNo,String FromDate,String ToDate) throws Exception;
    public Long addMovablePropertyTransaction(PisMovablePropertyTrans transaction) throws Exception;
	public List<Object[]> movPropertyRemarksHistory(String movPropertyId) throws Exception;
    public List<Object[]> constructionRenovationDetails(String EmpNo) throws Exception;
    public PisPropertyConstruction getConstructionById(Long ConstructionId) throws Exception;
    public Long addPropertyConstruction(PisPropertyConstruction construction) throws Exception;
    public Long editPropertyConstruction(PisPropertyConstruction construction) throws Exception;
    public Long addPropertyConstructionTransaction(PisPropertyConstructionTrans transaction) throws Exception;
    public List<Object[]> constructionTransList(String ConstructionId) throws Exception;
    public List<Object[]> constructionTransactionApprovalData(String ConstructionId);
    public List<Object[]> constructionRemarksHistory(String ConstructionId) throws Exception;
	public long constructionForward(String constructionId, String username, String action, String remarks, String empNo,String loginType) throws Exception;
	public List<Object[]> propertyConstructionApprovalList(String EmpNo) throws Exception;
	public List<LabMaster> getLabMasterDetails() throws Exception;
	
	
}

package com.vts.ems.itinventory.dao;

import java.util.List;

import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryHistory;
import com.vts.ems.itinventory.model.ITInventoryConfiguredHistory;
import com.vts.ems.itinventory.model.ITInventoryConfigured;
import com.vts.ems.model.EMSNotification;


public interface InventoryDao {
	public List<Object[]> getInventoryQuantityList(String empNo,String DeclarationYear) throws Exception;
	public long InventoryQtyAdd(ITInventory inventory)throws Exception;
	public ITInventory getITInventoryId(Long ItinventoryId) throws Exception;
	public long QuantityUpdate(ITInventory invntry) throws Exception;
	public long InventoryDetailsAddSubmit( ITInventoryConfigured details) throws Exception;
	public List<Object[]> getInventoryConfiguration(String empNo)throws Exception;
	public ITInventoryConfigured getInventoryConfigId(Long inventoryconfigid) throws Exception;
	public long ConfigureUpdate(ITInventoryConfigured config) throws Exception;
	public List<Object[]> getInventoryQtylist(String empNo,String DeclarationYear) throws Exception;
	public long ForwardDetails(ITInventory inventory) throws Exception;
	
	public long NotificationAdd(EMSNotification notification) throws Exception;
	public List<Object[]> SendNotification(String Logintype) throws Exception;
	public List<Object[]> getInventoryDeclaredList() throws Exception;
	public List<Object[]> getInventoryConfigure(String ITInventoryId) throws Exception;
	public List<Object[]> getApprovedForm(String invntryhistoryid) throws Exception;
	public long inventoryDetailsApprove(ITInventoryHistory Aprrove) throws Exception;
	public long inventoryDetailsReturn(ITInventory inventory)  throws Exception;
	public List<Object[]> getInventoryApprovedList(String year,String empNo,String LoginType)  throws Exception;
	public List<Object[]> getInventoryDeclaredListPreview(String iTInventoryId)throws Exception;
	public List<Object[]> getEmployeeList()throws Exception;
	public List<Object[]> getInventoryDashboardCount(String empno, String year) throws Exception ;
	public long inventoryapprove(ITInventory inventory) throws Exception ;
	public  long inventoryconfigapprove(ITInventoryConfiguredHistory config) throws Exception ;
    public List<Object[]> getInventoryConfigureApprovedlist(String iTInventoryId) throws Exception;
	
	public void UpdateAnnualDeclarationAllEmp(String Decl) throws Exception;	

	
}

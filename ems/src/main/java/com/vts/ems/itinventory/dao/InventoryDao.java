package com.vts.ems.itinventory.dao;

import java.util.List;

import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryConfigure;
import com.vts.ems.model.EMSNotification;


public interface InventoryDao {
	public List<Object[]> getInventoryQuantityList(String empNo) throws Exception;
	
	public long InventoryQtyAdd(ITInventory inventory)throws Exception;

	public ITInventory getITInventoryId(Long ItinventoryId) throws Exception;

	public long QuantityUpdate(ITInventory invntry) throws Exception;

	public long InventoryDetailsAddSubmit( ITInventoryConfigure details) throws Exception;

	public List<Object[]> getInventoryConfiguration(String empNo)throws Exception;

	public ITInventoryConfigure getInventoryConfigId(Long inventoryconfigid) throws Exception;

	public long ConfigureUpdate(ITInventoryConfigure config) throws Exception;

	public List<Object[]> getInventoryQtylist(String empNo,String DeclarationYear) throws Exception;

	public long ForwardDetails(ITInventory inventory) throws Exception;

	public List<Object[]> getInventoryList(String empNo,String DeclarationYear) throws Exception;
  
	public long NotificationAdd(EMSNotification notification) throws Exception;
	
	public List<Object[]> SendNotification(String Logintype) throws Exception;

	public List<Object[]> getInventoryForwardedList() throws Exception;

	public List<Object[]> getInventoryConfigure(String ITInventoryId) throws Exception;

	public List<Object[]> getInventoryForwardedListPreview(String iTInventoryId) throws Exception;

	public long inventoryDetailsApprove(ITInventory inventory) throws Exception;

	public long inventoryDetailsReturn(ITInventory inventory) throws Exception;
	

	
}

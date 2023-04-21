package com.vts.ems.itinventory.service;

import java.util.List;

import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryHistory;
import com.vts.ems.itinventory.model.ITInventoryConfiguredHistory;
import com.vts.ems.itinventory.model.ITInventoryConfigured;


public interface InventoryService {
	
   public List<Object[]> getInventoryQuantityList(String empNo) throws Exception;
   public long InventoryQtyAdd(ITInventory inventory) throws Exception;
   public long QuantityUpdate(ITInventory inventory) throws Exception;
   public long InventoryDetailsAddSubmit( ITInventoryConfigured details) throws Exception;
   public ITInventory getITInventoryId(long Itinventoryid )throws Exception;
   public List<Object[]> getInventoryConfiguration(String empNo) throws Exception;
   public ITInventoryConfigured getInventoryConfigId(long inventoryconfigid) throws Exception;
   public long ConfigureUpdate(ITInventoryConfigured details) throws Exception;
   public List<Object[]> getInventoryQtylist(String empNo,String DeclarationYear) throws Exception;
   public long ForwardDetails(ITInventory inventory,String empNo,String UserId) throws Exception;
   
   public List<Object[]> getInventoryDeclaredList() throws Exception;
   public List<Object[]> getInventoryConfigure(String ITInventoryId)throws Exception;
   public  List<Object[]> getApprovedForm(String invntryhistoryid) throws Exception;
   public long inventoryDetailsApprove(ITInventoryHistory Aprrove, ITInventory inventory,ITInventoryConfiguredHistory config, String empNo, String eno) throws Exception;
   public long inventoryDetailsReturn(ITInventory inventory, String empNo, String eno,String declarationyear) throws Exception;
   public List<Object[]> getInventoryApprovedList(String Year,String empNo,String LoginType) throws Exception;
   public List<Object[]> getInventoryDeclaredListPreview(String iTInventoryId) throws Exception;
   public List<Object[]> getEmployeeList() throws Exception;
   public List<Object[]> getInventoryDashboardCount(String empno, String year) throws Exception;

   public List<Object[]>  getInventoryConfigureApprovedlist(String iTInventoryId)throws Exception;
   

 
}

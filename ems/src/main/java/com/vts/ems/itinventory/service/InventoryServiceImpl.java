package com.vts.ems.itinventory.service;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vts.ems.ithelpdesk.controller.helpdeskcontroller;
import com.vts.ems.itinventory.controller.InventoryController;
import com.vts.ems.itinventory.dao.InventoryDao;
import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryConfigure;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;


@Service
public class InventoryServiceImpl implements InventoryService{
	
	private static final Logger logger = LogManager.getLogger(InventoryController.class);
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat sdtf = DateTimeFormatUtil.getSqlDateAndTimeFormat();
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	InventoryDao dao;
	
	@Override
	public List<Object[]> getInventoryQuantityList(String empNo) throws Exception {
	
		return dao.getInventoryQuantityList(empNo);
	}
	
	@Override
	public long InventoryQtyAdd(ITInventory inventory) throws Exception {
		
		
		return dao.InventoryQtyAdd(inventory);
	}

   @Override
	public long QuantityUpdate(ITInventory inventory) throws Exception {
		
		ITInventory invntry=dao.getITInventoryId(inventory.getItInventoryId());
		
		invntry.setItInventoryId(inventory.getItInventoryId());
		invntry.setDeclarationYear(inventory.getDeclarationYear());
		invntry.setDesktop(inventory.getDesktop());
		invntry.setDesktopIntendedBy(inventory.getDesktopIntendedBy());
		invntry.setDesktopRemarks(inventory.getDesktopRemarks());
		invntry.setLaptop(inventory.getLaptop());
		invntry.setLaptopIntendedBy(inventory.getLaptopIntendedBy());
		invntry.setLaptopRemarks(inventory.getLaptopRemarks());
	    invntry.setUSBPendrive(inventory.getUSBPendrive());
	    invntry.setUSBPendriveIntendedBy(inventory.getUSBPendriveIntendedBy());
	    invntry.setUSBPendriveRemarks(inventory.getUSBPendriveRemarks());
	    invntry.setPrinter(inventory.getPrinter());
	    invntry.setPrinterIntendedBy(inventory.getPrinterIntendedBy());
	    invntry.setPrinterRemarks(inventory.getPrinterRemarks());
	    invntry.setTelephone(inventory.getTelephone());  
	    invntry.setTelephoneIntendedBy(inventory.getTelephoneIntendedBy());
	    invntry.setTelephoneRemarks(inventory.getTelephoneRemarks());
	    invntry.setFaxMachine(inventory.getFaxMachine());
	    invntry.setFaxMachineIntendedBy(inventory.getFaxMachineIntendedBy());
	    invntry.setFaxMachineRemarks(inventory.getFaxMachineRemarks());
	    invntry.setScanner(inventory.getScanner());
	    invntry.setScannerIntendedBy(inventory.getScannerIntendedBy());
	    invntry.setScannerRemarks(inventory.getScannerRemarks());
	    invntry.setXeroxMachine(inventory.getXeroxMachine());
	    invntry.setXeroxMachineIntendedBy(inventory.getXeroxMachineIntendedBy());
		invntry.setXeroxMachineRemarks(inventory.getXeroxMachineRemarks());
		invntry.setMiscellaneous(inventory.getMiscellaneous());
		invntry.setMiscellaneousIntendedBy(inventory.getMiscellaneousIntendedBy());
		invntry.setMiscellaneousRemarks(inventory.getMiscellaneousRemarks());
		invntry.setModifiedBy(inventory.getModifiedBy());
		invntry.setModifiedDate(inventory.getModifiedDate());
	    return dao.QuantityUpdate(invntry);
		
	}

@Override
public long InventoryDetailsAddSubmit( ITInventoryConfigure details) throws Exception {
	
	
	return dao.InventoryDetailsAddSubmit(details);
}

@Override
public ITInventory getITInventoryId(long itinventoryid) throws Exception {
	
	return dao.getITInventoryId(itinventoryid);
}

@Override
public List<Object[]> getInventoryConfiguration(String empNo) throws Exception {
	
	return dao.getInventoryConfiguration(empNo);
}

@Override
public ITInventoryConfigure getInventoryConfigId(long inventoryconfigid) throws Exception {

	return dao.getInventoryConfigId(inventoryconfigid);
}

@Override
public long ConfigureUpdate(ITInventoryConfigure details) throws Exception {
	
	ITInventoryConfigure config=dao.getInventoryConfigId(details.getInventoryConfigureId());
	config.setInventoryConfigureId(details.getInventoryConfigureId());
	config.setItemType(details.getItemType());
	config.setConnectionType(details.getConnectionType());
	config.setCPU(details.getCPU());
	config.setMonitor(details.getMonitor());
	config.setRAM(details.getRAM());
	config.setAdditionalRAM(details.getAdditionalRAM());
	config.setKeyboard(details.getKeyboard());
	config.setMouse(details.getMouse());
	config.setExternalharddisk(details.getExternalharddisk());
	config.setExtraInternalharddisk(details.getExtraInternalharddisk());
	config.setOffice(details.getOffice());
	config.setOS(details.getOS());
	config.setPDF(details.getPDF());
	config.setBrowser(details.getBrowser());
	config.setKavach(details.getKavach());
	config.setModifiedBy(details.getModifiedBy());
	config.setModifiedDate(details.getModifiedDate());
	
	return dao.ConfigureUpdate(config);
  }

@Override
public List<Object[]> getInventoryQtylist(String empNo,String DeclarationYear) throws Exception {
	
	return dao.getInventoryQtylist(empNo,DeclarationYear);
}

@Override
public long ForwardDetails(ITInventory inventory,String UserId,String EmpNo) throws Exception {
	
	   
		List<Object[]> notifyto = dao.SendNotification("A");
		int i=0;
	    for( Object[] obj : notifyto ){
	    
	    EMSNotification notify = new EMSNotification();
		notify.setEmpNo(obj[i].toString());
		notify.setNotificationUrl("InventoryDetailsForwarded.htm");
		notify.setNotificationMessage("Inventory Details Forwarded");
		
	   if(inventory.getStatus()=="F" & notify.getEmpNo()!=null)
		{
			notify.setNotificationDate(LocalDate.now().toString());
			notify.setNotificationBy((EmpNo));
			notify.setIsActive(1);
			notify.setCreatedBy(UserId);
			notify.setCreatedDate(sdf1.format(new Date()));
			dao.NotificationAdd(notify);
		}
	 }
	return dao.ForwardDetails(inventory);
}

  @Override
  public List<Object[]> getInventoryList(String empNo,String DeclarationYear) throws Exception {
	
	return dao.getInventoryList(empNo,DeclarationYear);
  }

  @Override
  public List<Object[]> getInventoryForwardedList() throws Exception {
	
	return dao.getInventoryForwardedList();
}

@Override
public List<Object[]> getInventoryConfigure(String ITInventoryId) throws Exception {
	
	return dao.getInventoryConfigure(ITInventoryId);
}

@Override
public List<Object[]> getInventoryForwardedListPreview(String iTInventoryId) throws Exception {
	
	return dao.getInventoryForwardedListPreview(iTInventoryId);
}

@Override
public long inventoryDetailsApprove(ITInventory inventory) throws Exception {
	
	return dao.inventoryDetailsApprove(inventory);
	
 }

@Override
public long inventoryDetailsReturn(ITInventory inventory) throws Exception {
	
	return dao.inventoryDetailsReturn(inventory);
}
		
}

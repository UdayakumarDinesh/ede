package com.vts.ems.itinventory.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.ithelpdesk.dao.helpdeskDaoImpl;
import com.vts.ems.ithelpdesk.model.HelpDeskEmployee;
import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryConfigure;
import com.vts.ems.model.EMSNotification;
import com.vts.ems.utils.DateTimeFormatUtil;
@Transactional
@Repository
public class InventoryDaoImpl implements InventoryDao{
	
	private static final Logger logger = LogManager.getLogger(helpdeskDaoImpl.class);
	SimpleDateFormat sdtf= DateTimeFormatUtil.getSqlDateAndTimeFormat();
	SimpleDateFormat sdf= DateTimeFormatUtil.getSqlDateFormat();
	
	
	@PersistenceContext
	EntityManager manager;

	
	private static final String INVENTORYQUANTITYLIST="SELECT ItInventoryId,Desktop,DesktopIntendedBy,DesktopRemarks,Laptop,LaptopIntendedBy,LaptopRemarks,USBPendrive,USBPendriveIntendedBy,USBPendriveRemarks,Printer,PrinterIntendedBy,PrinterRemarks,Telephone,TelephoneIntendedBy,TelephoneRemarks,FaxMachine,FaxMachineIntendedBy,FaxMachineRemarks,Scanner,ScannerIntendedBy,ScannerRemarks,XeroxMachine,XeroxMachineIntendedBy,XeroxMachineRemarks,Miscellaneous,MiscellaneousIntendedBy,MiscellaneousRemarks FROM it_inventory WHERE isActive='1' AND EmpNo=:EmpNo";
	@Override
	public List<Object[]> getInventoryQuantityList(String empNo) throws Exception {

		Query query=manager.createNativeQuery(INVENTORYQUANTITYLIST);
		query.setParameter("EmpNo", empNo);
		return (List<Object[]>)query.getResultList();
		
	}
	@Override
	public long InventoryQtyAdd(ITInventory inventory) throws Exception {
		
		try {
			manager.persist(inventory);
			manager.flush();		
			return inventory.getItInventoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO InventoryQtyAdd() "+e);
			e.printStackTrace();
			return 0L;
		}
	}

	
	@Override
	public ITInventory getITInventoryId(Long ItinventoryId) throws Exception {
		
      try {
			ITInventory it = manager.find(ITInventory.class,(ItinventoryId));
			return it;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getITInventoryId() "+e);
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public long QuantityUpdate(ITInventory invntry) throws Exception {
		
		try {
			manager.merge(invntry);
			manager.flush();		
			return invntry.getItInventoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO QuantityUpdate() "+e);
			e.printStackTrace();
			return 0L;
		}
	}
	@Override
	public long InventoryDetailsAddSubmit(ITInventoryConfigure details) throws Exception {
		
		try {
			manager.persist(details);
			manager.flush();		
			return details.getInventoryConfigureId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO InventoryDetailsAddSubmit() "+e);
			e.printStackTrace();
			return 0L;
		}
	}
	
	private static final String INVENTORYCONFIGURATION="SELECT InventoryConfigureId,ItemType,ConnectionType,CPU,Monitor,RAM,AdditionalRAM,Keyboard,Mouse,Externalharddisk,ExtraInternalharddisk,Office,OS,PDF,Browser,Kavach FROM it_inventory_configure WHERE isActive='1' AND EmpNo=:EmpNo";
	@Override
	public List<Object[]> getInventoryConfiguration(String empNo) throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYCONFIGURATION);
		query.setParameter("EmpNo", empNo);
		
		return (List<Object[]>)query.getResultList();
	}
	
	
	@Override
	public ITInventoryConfigure getInventoryConfigId(Long inventoryconfigid) throws Exception {
	
		try {
			 ITInventoryConfigure ic = manager.find(ITInventoryConfigure.class,(inventoryconfigid));
			return ic ;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getInventoryConfigId() "+e);
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public long ConfigureUpdate(ITInventoryConfigure config) throws Exception {
		
		try {
			manager.merge(config);
			manager.flush();		
			return config.getInventoryConfigureId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO ConfigureUpdate() "+e);
			e.printStackTrace();
			return 0L;
		}
	}
	public static final String INVENTORYQTYLIST="CALL IT_Inventory_Quantity(:EmpNo,:DeclarationYear)";
	@Override
	public List<Object[]> getInventoryQtylist(String empNo,String DeclarationYear) throws Exception {
		
		Query query =manager.createNativeQuery(INVENTORYQTYLIST);
		
		query.setParameter("EmpNo", empNo);
		query.setParameter("DeclarationYear", DeclarationYear);
		return (List<Object[]>)query.getResultList();
	}
	public static final String FORWARDEDDETIALS="UPDATE it_inventory SET Date=:date,Status=:status,ERemarks=:Remarks WHERE ItInventoryId=:ItinventoryId";
	@Override
	public long ForwardDetails(ITInventory inventory) throws Exception {
		
		Query query=(Query) manager.createNativeQuery(FORWARDEDDETIALS);
		query.setParameter("ItinventoryId", inventory.getItInventoryId());
		query.setParameter("status", inventory.getStatus());
		query.setParameter("Remarks", inventory.getERemarks());
		query.setParameter("date", inventory.getDate());
		return query.executeUpdate();
	}
	public static final String INVENTORYLIST="CALL IT_Inventory_Quantity(:EmpNo,:DeclarationYear)";
	@Override
	public List<Object[]> getInventoryList(String empNo,String DeclarationYear) throws Exception {
	
		Query query=manager.createNativeQuery(INVENTORYLIST);
		query.setParameter("EmpNo", empNo);
		query.setParameter("DeclarationYear", DeclarationYear);
		return (List<Object[]>)query.getResultList();
	}
	
	 @Override
		public long NotificationAdd(EMSNotification notification) throws Exception {
			{
				try {
					manager.persist(notification);
					manager.flush();
					return notification.getNotificationId();
				}catch (Exception e) {
					logger.error(new Date()  + "Inside DAO NotificationAdd " + e);
					e.printStackTrace();
					return 0;
				}
			}
		}
	 
	 private static final String EMSTICKETNOTIFICATION  ="SELECT e.empno,e.empname,ed.desigid, l.LoginType,lt.LoginDesc,e.Email FROM employee e, employee_desig ed,login l,login_type lt WHERE l.empid=e.empid AND e.desigid = ed.DesigId AND l.LoginType = lt.LoginType  AND l.loginType =:loginType";
		@Override
		public List<Object[]> SendNotification(String Logintype) throws Exception {
			try {
					
					Query query= manager.createNativeQuery(EMSTICKETNOTIFICATION);
					query.setParameter("loginType", Logintype);
					List<Object[]> list =  (List<Object[]>)query.getResultList();
					return list;
				}catch (Exception e) {
					logger.error(new Date()  + "Inside DAO SendNotification " + e);
					e.printStackTrace();
					return null;
				}
				
	}
	private static final String INVENTORYFORWARDEDLIST ="SELECT it.ItInventoryId,e.EmpName,it.EmpNo,dm.DivisionName,ed.Designation,it.Desktop,it.DesktopIntendedBy,it.DesktopRemarks,it.Laptop,it.LaptopIntendedBy,it.LaptopRemarks, it.USBPendrive,it.USBPendriveIntendedBy,it.USBPendriveRemarks,it.Printer,it.PrinterIntendedBy,it.PrinterRemarks,it.Telephone,it.TelephoneIntendedBy,it.TelephoneRemarks,it.FaxMachine,it.FaxMachineIntendedBy,it.FaxMachineRemarks,it.Scanner,it.ScannerIntendedBy,it.ScannerRemarks,it.XeroxMachine,it.XeroxMachineIntendedBy,it.XeroxMachineRemarks, it.Miscellaneous,it.MiscellaneousIntendedBy,it.MiscellaneousRemarks,DATE(it.Date),it.status,it.DeclarationYear,it.ERemarks FROM it_inventory it,employee e, employee_desig ed,division_master dm  WHERE it.isActive='1' AND it.EmpNo=e.EmpNo AND ed.DesigId=e.DesigId AND e.divisionid=dm.divisionid AND it.status IN('F','A','R') ORDER BY it.Date  DESC";
	@Override
	public List<Object[]> getInventoryForwardedList() throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYFORWARDEDLIST);
		return (List<Object[]>)query.getResultList();
	}
		
	private static final String INVENTORYCONFIGURE="SELECT ic.InventoryConfigureId,ic.ItemType,ic.ConnectionType,ic.CPU,ic.Monitor,ic.RAM,ic.AdditionalRAM,ic.Keyboard,ic.Mouse,ic.Externalharddisk,ic.ExtraInternalharddisk,ic.Office,ic.OS,ic.PDF,ic.Browser,ic.Kavach FROM it_inventory_configure ic,it_inventory it WHERE ic.isActive='1' AND ic.ITInventoryId=it.ITInventoryId AND it.status IN('F','A','R') AND it.ITInventoryId=:inventoryid";	
	@Override
	public List<Object[]> getInventoryConfigure(String ITInventoryId) throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYCONFIGURE);
		query.setParameter("inventoryid", ITInventoryId);
		return (List<Object[]>)query.getResultList();
	}
	private static final String INVENTORYFORWARDEDLISTPREVIEW="CALL IT_Inventory_Forwarded(:inventoryid)"; 
	@Override
	public List<Object[]> getInventoryForwardedListPreview(String iTInventoryId) throws Exception {
		Query query=manager.createNativeQuery(INVENTORYFORWARDEDLISTPREVIEW);
		query.setParameter("inventoryid", iTInventoryId);
		return (List<Object[]>)query.getResultList();
		
	}
	private static final String INVENTORYDETIALSAPPROVE="UPDATE it_inventory SET Date=:date,Status=:status WHERE ItInventoryId=:ItinventoryId";
	@Override
	public long inventoryDetailsApprove(ITInventory inventory) throws Exception {
		
		Query query=(Query) manager.createNativeQuery(INVENTORYDETIALSAPPROVE);
		query.setParameter("ItinventoryId", inventory.getItInventoryId());
		query.setParameter("status", inventory.getStatus());
		query.setParameter("date", inventory.getDate());
		return query.executeUpdate();
	}
	
	private static final String INVENTORYDETIALSRETURN="UPDATE it_inventory SET ARemarks=:remarks,Date=:date,Status=:status WHERE ItInventoryId=:ItinventoryId";
	@Override
	public long inventoryDetailsReturn(ITInventory inventory) throws Exception {
	
		Query query=(Query) manager.createNativeQuery(INVENTORYDETIALSRETURN);
		query.setParameter("ItinventoryId", inventory.getItInventoryId());
		query.setParameter("remarks", inventory.getARemarks());
		query.setParameter("status", inventory.getStatus());
		query.setParameter("date", inventory.getDate());
		return query.executeUpdate();
	}

}

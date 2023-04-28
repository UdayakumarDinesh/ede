package com.vts.ems.itinventory.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.vts.ems.ithelpdesk.dao.helpdeskDaoImpl;
import com.vts.ems.ithelpdesk.model.HelpDeskEmployee;
import com.vts.ems.itinventory.model.ITInventory;
import com.vts.ems.itinventory.model.ITInventoryHistory;
import com.vts.ems.itinventory.model.ITInventoryConfiguredHistory;
import com.vts.ems.itinventory.model.ITInventoryConfigured;
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

	
	private static final String INVENTORYQUANTITYLIST="SELECT ItInventoryId,Desktop,DesktopIntendedBy,DesktopRemarks,Laptop,LaptopIntendedBy,LaptopRemarks,USBPendrive,USBPendriveIntendedBy,USBPendriveRemarks,Printer,PrinterIntendedBy,PrinterRemarks,Telephone,TelephoneIntendedBy,TelephoneRemarks,FaxMachine,FaxMachineIntendedBy,FaxMachineRemarks,Scanner,ScannerIntendedBy,ScannerRemarks,XeroxMachine,XeroxMachineIntendedBy,XeroxMachineRemarks,Miscellaneous,MiscellaneousIntendedBy,MiscellaneousRemarks,DeclarationYear,Status FROM it_inventory WHERE isActive='1' AND DeclaredBy=:EmpNo AND DeclarationYear=:Year";
	@Override
	public List<Object[]> getInventoryQuantityList(String empNo,String DeclarationYear) throws Exception {

		Query query=manager.createNativeQuery(INVENTORYQUANTITYLIST);
		query.setParameter("EmpNo", empNo);
		query.setParameter("Year", DeclarationYear);
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
	public long InventoryDetailsAddSubmit(ITInventoryConfigured details) throws Exception {
		
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
	
	private static final String INVENTORYCONFIGURATION="SELECT InventoryConfigureId,ItemType,ConnectionType,CPU,Monitor,RAM,AdditionalRAM,Keyboard,Mouse,Externalharddisk,ExtraInternalharddisk,Office,OS,PDF,Browser,Kavach FROM it_inventory_configured WHERE isActive='1' AND ConfiguredBy=:EmpNo  ORDER BY InventoryConfigureId DESC";
	@Override
	public List<Object[]> getInventoryConfiguration(String empNo) throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYCONFIGURATION);
		query.setParameter("EmpNo", empNo);
		
		return (List<Object[]>)query.getResultList();
	}
	
	
	@Override
	public ITInventoryConfigured getInventoryConfigId(Long inventoryconfigid) throws Exception {
	
		try {
			 ITInventoryConfigured ic = manager.find(ITInventoryConfigured.class,(inventoryconfigid));
			return ic ;
		} catch (Exception e) {
			logger.error(new Date() + "Inside DAO getInventoryConfigId() "+e);
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public long ConfigureUpdate(ITInventoryConfigured config) throws Exception {
		
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
	public static final String INVENTORYQTYLIST="CALL IT_Inventory_List(:EmpNo,:DeclarationYear)";
	@Override
	public List<Object[]> getInventoryQtylist(String empNo,String DeclarationYear) throws Exception {
		
		Query query =manager.createNativeQuery(INVENTORYQTYLIST);
		
		query.setParameter("EmpNo", empNo);
		query.setParameter("DeclarationYear", DeclarationYear);
		return (List<Object[]>)query.getResultList();
	}
	public static final String FORWARDEDDETIALS="UPDATE it_inventory SET  DeclarationYear=:declarationyear,ForwardedDate=:forwardeddate,Status=:status,ERemarks=:Remarks WHERE ItInventoryId=:ItinventoryId";
	@Override
	public long ForwardDetails(ITInventory inventory) throws Exception {
		
		Query query=(Query) manager.createNativeQuery(FORWARDEDDETIALS);
		query.setParameter("ItinventoryId", inventory.getItInventoryId());
		query.setParameter("status", inventory.getStatus());
		query.setParameter("Remarks", inventory.getERemarks());
		query.setParameter("forwardeddate", inventory.getForwardedDate());
		query.setParameter("declarationyear", inventory.getDeclarationYear());
		
		return query.executeUpdate();
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
	private static final String INVENTORYDECLAREDLIST ="SELECT it.ItInventoryId,e.EmpName,it.DeclarationYear,it.ForwardedDate,it.Desktop,it.Laptop, it.USBPendrive,it.Printer,it.Telephone,it.FaxMachine,it.Scanner,it.XeroxMachine,it.Miscellaneous,it.status,it.ERemarks FROM it_inventory it,employee e WHERE it.isActive='1' AND it.DeclaredBy=e.EmpNo  AND it.status IN('F','R')  ORDER BY it.ForwardedDate  DESC";
	@Override
	public List<Object[]> getInventoryDeclaredList() throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYDECLAREDLIST);
		return (List<Object[]>)query.getResultList();
	}
		
	private static final String INVENTORYCONFIGURE="SELECT ic.InventoryConfigureId,ic.ItemType,ic.ConnectionType,ic.CPU,ic.Monitor,ic.RAM,ic.AdditionalRAM,ic.Keyboard,ic.Mouse,ic.Externalharddisk,ic.ExtraInternalharddisk,ic.Office,ic.OS,ic.PDF,ic.Browser,ic.Kavach,ic.ITInventoryId,ic.ConfiguredBy FROM it_inventory_configured ic,it_inventory it WHERE ic.isActive='1' AND ic.ITInventoryId=it.ITInventoryId AND it.status IN('F','A','R') AND it.ITInventoryId=:inventoryid";	
	@Override
	public List<Object[]> getInventoryConfigure(String ITInventoryId) throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYCONFIGURE);
		query.setParameter("inventoryid", ITInventoryId);
		return (List<Object[]>)query.getResultList();
	}
	private static final String INVENTORYAPPROVEDFORMDOWNLOAD="CALL IT_Inventory_Approved(:ItInventoryHistoryId )"; 
	@Override
	public List<Object[]> getApprovedForm(String invntryhistoryid) throws Exception {
		Query query=manager.createNativeQuery(INVENTORYAPPROVEDFORMDOWNLOAD);
		query.setParameter("ItInventoryHistoryId", invntryhistoryid);
		return (List<Object[]>)query.getResultList();
		
	}
	
	@Override
	public long inventoryDetailsApprove(ITInventoryHistory Aprrove ) throws Exception {
		try {
			manager.persist(Aprrove);
			manager.flush();		
			return Aprrove.getItInventoryHistoryId();
		}	catch (Exception e) {
			logger.error(new Date() + "Inside DAO inventoryDetailsApprove() "+e);
			e.printStackTrace();
			return 0L;
		}
	}
		@Override
		public long inventoryconfigapprove(ITInventoryConfiguredHistory config) throws Exception {
			
			try {
				manager.persist(config);
				manager.flush();		
				return config.getConfigHistoryId();
			}	catch (Exception e) {
				logger.error(new Date() + "Inside DAO inventoryDetailsApprove() "+e);
				e.printStackTrace();
				return 0L;
			}	
			
		}
	
	
	private static final String INVENTORYDETIALSRETURN="UPDATE it_inventory SET ARemarks=:remarks,Status=:status,ReturnedBy=:returnedby WHERE ItInventoryId=:ItinventoryId";
	@Override
	public long inventoryDetailsReturn(ITInventory inventory) throws Exception {
	
		Query query=(Query) manager.createNativeQuery(INVENTORYDETIALSRETURN);
		query.setParameter("ItinventoryId", inventory.getItInventoryId());
		query.setParameter("remarks", inventory.getARemarks());
		query.setParameter("status", inventory.getStatus());
		query.setParameter("returnedby", inventory.getReturnedBy());
		return query.executeUpdate();
	}
	
	private static final String INVENTORYAPPROVEDLIST=" SELECT it.ItInventoryHistoryId,e.EmpName,it.DeclarationYear,it.ApprovedDate,it.Desktop,it.Laptop,it.USBPendrive,it.Printer,it.Telephone,it.FaxMachine,it.Scanner,it.XeroxMachine,it.Miscellaneous,it.status,it.ItInventoryId FROM it_inventory_history it,employee e WHERE it.isActive='1' AND it.DeclaredBy=e.EmpNo AND it.status IN('A')  AND CASE WHEN 'A'=:LoginType THEN it.DeclarationYear=:DeclarationYear  ELSE it.DeclaredBy=:EmpNo END ORDER BY it.ApprovedDate DESC;";
	@Override
	public List<Object[]> getInventoryApprovedList(String year,String empNo,String LoginType) throws Exception {
		
		Query query=manager.createNativeQuery(INVENTORYAPPROVEDLIST);
		query.setParameter("DeclarationYear", year);
		query.setParameter("EmpNo", empNo);
		query.setParameter("LoginType", LoginType);
		return (List<Object[]>)query.getResultList();
		
	}
	
	private static final String DECLAREDLISTPREVIEW="CALL IT_Inventory_Forwarded(:iTInventoryId)";
	@Override
	public List<Object[]> getInventoryDeclaredListPreview(String iTInventoryId) throws Exception {
		
		Query query=manager.createNativeQuery(DECLAREDLISTPREVIEW);
		query.setParameter("iTInventoryId", iTInventoryId);
		
		return (List<Object[]>)query.getResultList();
	}
	
	private static final String EMPLOYEELIST="SELECT e.EmpNo,e.EmpName FROM employee e,login l WHERE e.isActive='1' AND l.isactive='1' AND e.EmpId=l.EmpId AND l.loginType IN('U','P','A','F','Z','K','V','W','B','Y') ";
	@Override
	public List<Object[]> getEmployeeList() throws Exception {
		
		Query query=manager.createNativeQuery(EMPLOYEELIST);
		return (List<Object[]>)query.getResultList();
	}
	private static final String INVENTORYDASHBOARDCOUNT="CALL IT_Inventory_Dashboard_Count(:EmpNo,:DeclarationYear)";
	@Override
	public List<Object[]> getInventoryDashboardCount(String empno, String year) {
		
		Query query=manager.createNativeQuery(INVENTORYDASHBOARDCOUNT);
		query.setParameter("EmpNo", empno);
		query.setParameter("DeclarationYear", year);
		return (List<Object[]>)query.getResultList();
	}
	
	private static final String INVENTORYDETIALSAPPROVE=" UPDATE it_inventory SET Status=:status,ApprovedDate=:approveddate,AllowDecl=:allowdecl WHERE  ItInventoryId=:ItinventoryId";
	@Override
	public long inventoryapprove(ITInventory inventory) throws Exception {
		
		Query query=(Query) manager.createNativeQuery(INVENTORYDETIALSAPPROVE);
		query.setParameter("ItinventoryId", inventory.getItInventoryId());
		query.setParameter("approveddate", inventory.getApprovedDate());
		query.setParameter("allowdecl", inventory.getAllowDecl());
		query.setParameter("status", inventory.getStatus());
		
		return query.executeUpdate();
	}

	
	private static final String INVENTORYCONFIGUREAPRROVEDLIST="SELECT ic.ConfigHistoryId,ic.ItemType,ic.ConnectionType,ic.CPU,ic.Monitor,ic.RAM,ic.AdditionalRAM,ic.Keyboard,ic.Mouse,ic.Externalharddisk,ic.ExtraInternalharddisk,ic.Office,ic.OS,ic.PDF,ic.Browser,ic.Kavach,ic.ITInventoryId,ic.ConfiguredBy FROM it_inventory_configured_history ic,it_inventory it WHERE ic.isActive='1' AND ic.ITInventoryId=it.ITInventoryId AND it.status IN('F','A','R') AND it.ITInventoryId=:inventoryid";
	@Override
	public List<Object[]> getInventoryConfigureApprovedlist(String iTInventoryId) throws Exception {
		
		
		Query query=manager.createNativeQuery(INVENTORYCONFIGUREAPRROVEDLIST);
		query.setParameter("inventoryid", iTInventoryId);
		return (List<Object[]>)query.getResultList();
	}
	
	private static final String ALLOWDECLARATIONALLEMP="UPDATE  it_inventory SET  AllowDecl=:Decl WHERE isActive=1";
	@Override
	public void UpdateAnnualDeclarationAllEmp(String Decl) throws Exception {
		
		try {
			Query query = manager.createNativeQuery(ALLOWDECLARATIONALLEMP);
			query.setParameter("Decl", Decl);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(new Date() + "Inside DAO UpdateAnnualDeclarationAllEmp "+e);
		}	
		
	}
	

}

package com.vts.ems.itinventory.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity(name="it_inventory")
public class ITInventory implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private long ItInventoryId;
	private String DeclarationYear;
	private String Date;
	private String DeclaredBy;
	private int Desktop;
	private String DesktopIntendedBy;
	private String DesktopRemarks;
	private int Laptop;
	private String LaptopIntendedBy;
	private String LaptopRemarks;
	private int USBPendrive;
	private String USBPendriveIntendedBy;
	private String USBPendriveRemarks;
	private int Printer;
	private String PrinterIntendedBy;
	private String PrinterRemarks;
	private int Telephone;
	private String TelephoneIntendedBy;
	private String TelephoneRemarks;
	private int FaxMachine;
	private String FaxMachineIntendedBy;
	private String FaxMachineRemarks;
	private int Scanner;
	private String ScannerIntendedBy;
	private String ScannerRemarks;
	private int XeroxMachine;
	private String XeroxMachineIntendedBy;
	private String XeroxMachineRemarks;
	private int Miscellaneous;
	private String  MiscellaneousIntendedBy;
	private String  MiscellaneousRemarks;
    private String  Status;
    private String  ARemarks;
    private String  ReturnedBy;
    private String  ERemarks;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int isActive;
	
}

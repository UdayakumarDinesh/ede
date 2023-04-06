package com.vts.ems.itinventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="it_inventory_configure")
public class ITInventoryConfigure {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy  =  GenerationType.IDENTITY)
	private long InventoryConfigureId;
	private long ItInventoryId;
	private String EmpNo;
	private String ItemType;
	private String ConnectionType;
	private String CPU;
	private String Monitor;
	private String RAM;
	private String AdditionalRAM;
	private String Keyboard;
	private String Mouse;
	private String Externalharddisk;
	private String ExtraInternalharddisk;
	private String Office;
	private String OS;
	private String PDF;
	private String Browser;
	private String Kavach;
	private String  CreatedBy;
	private String  CreatedDate;
	private String  ModifiedBy;
	private String  ModifiedDate;
	private int isActive;

}

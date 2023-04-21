package com.vts.ems.pi.dao;

import java.util.Date;
import java.util.List;

import com.vts.ems.model.EMSNotification;
import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;

public interface PIDao {

	public List<Object[]> EmployeeAddressDetails(String EmpId) throws Exception;
	public Object[] ResAddressFormData(String addressResId) throws Exception;
	public List<Object[]> PermanentAddressDetails(String EmpId) throws Exception;
	public AddressPer getPerAddressData(String addressperid) throws Exception;
	public Object[] ResToAddressId(String EmpId) throws Exception;
	public long ResUpdatetoDate(Date toDate, String resAddressId) throws Exception;
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception;
	public AddressRes getResAddressDet(String resaddressid) throws Exception;
	public Object[] AddressIntimationAuth(String Logintype) throws Exception;
	public long NotificationAdd(EMSNotification notify) throws Exception;
	public long AddressResEdit(AddressRes addressRes) throws Exception;
	public Object[] PerAddressFormData(String addressPerId) throws Exception;

}

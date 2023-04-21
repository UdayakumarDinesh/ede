package com.vts.ems.pi.service;

import java.util.Date;
import java.util.List;

import com.vts.ems.pis.model.AddressPer;
import com.vts.ems.pis.model.AddressRes;

public interface PIService {
	
	public List<Object[]> ResAddressDetails(String EmpId) throws Exception;
	public Object[] ResAddressFormData(String addressResId)throws Exception;
	public List<Object[]> PermanentAddressDetails(String EmpId)throws Exception;
	public AddressPer getPerAddressData(String addressperid) throws Exception;
	public Object[] ResToAddressId(String EmpId) throws Exception;
	public long ResUpdatetoDate(Date toDate, String resAddressId) throws Exception;
	public AddressRes ResAddressIntimated(String resaddressid) throws Exception;
	public long ResAddressForward(String resAddressId, String username, String action, String remarks, String empId,String empNo, String loginType) throws Exception;
	public Object[] PerAddressFormData(String addressPerId) throws Exception;
}

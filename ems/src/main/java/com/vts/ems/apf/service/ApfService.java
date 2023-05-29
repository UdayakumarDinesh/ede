package com.vts.ems.apf.service;

import java.util.List;

public interface ApfService {
	public List<Object[]> allAPFApply(String empNo) throws Exception;
	public Object[] empNameAndDesi(String empNo) throws Exception;
}

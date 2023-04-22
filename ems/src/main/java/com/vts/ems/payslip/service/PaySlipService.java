package com.vts.ems.payslip.service;

import java.sql.ResultSet;

public interface PaySlipService {

	ResultSet MonthWisePaySlipSyncList(int month, int year)throws Exception;

}

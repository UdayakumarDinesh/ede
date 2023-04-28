package com.vts.ems.payslip.dao;

import java.sql.ResultSet;

public interface PaySlipDao {

	ResultSet MonthWisePaySlipSyncList(int month,int year) throws Exception;

}

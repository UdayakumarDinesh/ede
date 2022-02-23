package com.vts.ems;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class DateTimeFormatUtil 
{
	private SimpleDateFormat regularDateFormat=new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat sqlDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	
	public SimpleDateFormat getMonthNameAndYear() {
		return new SimpleDateFormat("MMM-yyyy");
	}
	public SimpleDateFormat getSqlDateFormat() {
		return sqlDateFormat;
	}
	public SimpleDateFormat getSqlDateAndTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public SimpleDateFormat getRegularDateFormat() {
		return regularDateFormat;
	}
	public SimpleDateFormat getDateMonthShortName() {
		return new SimpleDateFormat("dd-MMM-yyyy");
	}
	public SimpleDateFormat getDateMonthFullName() {
		return new SimpleDateFormat("dd-MMMM-yyyy");
	}
	
	
	

	public int getYearFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getYear();
	}
	public int getYearFromSqlDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getYear();
		
	}
	public int getYearFromSqlDateAndTime(String datetimestring)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldatetime=LocalDateTime.parse(datetimestring,formatter);
		return ldatetime.getYear();
		
	}
	
	public int getMonthFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonthValue();
	}
	public int getMonthFromSqlDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonthValue();
		
	}
	public int getMonthFromSqlDateAndTime(String datetimestring)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldatetime=LocalDateTime.parse(datetimestring,formatter);
		return ldatetime.getMonthValue();
		
	}
	
	public String getMonthValFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH);
	}
	public String getMonthValFromSqlDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH);
		
	}
	public String getMonthValFromSqlDateAndTime(String datetimestring)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldatetime=LocalDateTime.parse(datetimestring,formatter);
		return ldatetime.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH);		
	}
	
	
	public String getCurrentYear()
	{		
		return  String.valueOf(LocalDate.now().getYear());
	}
	
	public String getCurrentMonthShortName()
	{
		return new DateFormatSymbols().getShortMonths()[LocalDate.now().getMonthValue()];
	}
	
	public String getCurrentMonthFullName()
	{
		return new DateFormatSymbols().getMonths()[LocalDate.now().getMonthValue()];
	}
	
	/*---------------------------------------------*/
	
	public String getFirstDayofCurrentMonthSqlFormat()
	{
		String firstday=LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
		return firstday;
	}
	
	public String getLastDayofCurrentMonthSqlFormat()
	{
		String lastday=LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString();
		return lastday;
	}
	
	public String getFirstDayofCurrentMonthRegularFormat() throws Exception
	{
		String firstday=regularDateFormat.format(sqlDateFormat.parse(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString()));
		return firstday;
	}
	
	public String getLastDayofCurrentMonthRegularFormat() throws Exception
	{
		String lastday=regularDateFormat.format(sqlDateFormat.parse(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString()));
		return lastday;
	}	
	
	
	
	public String getFinancialYearStartDateSqlFormat()
	{
		String firstday=LocalDate.now().getYear()+"-04-01";
		return firstday;
	}	
	
	public String getFinancialYearEndDateSqlFormat()
	{
		String lastday=LocalDate.now().getYear()+"-03-31";
		return lastday;
	}
	
	public String getFinancialYearStartDateRegularFormat() throws Exception
	{
		String firstday="01-04-"+LocalDate.now().getYear();
		return firstday;
	}
	
	public String getFinancialYearEndDateRegularFormat() throws Exception
	{
		String lastday="31-03-"+LocalDate.now().getYear();
		return lastday;
	}
	
	public String getPreviousFinancialYearStartDateRegularFormat() throws Exception
	{
		String pfirstday="01-04-"+LocalDate.now().minusYears(1).getYear();
		return pfirstday;
	}
	public String getPreviousFinancialYearStartDateSqlFormat()
	{
		String pfirstday=LocalDate.now().minusYears(1).getYear()+"-04-01";
		return pfirstday;
	}	
	
	
	public String getCurrentYearStartDateSqlFormat()
	{
		String firstday=LocalDate.now().getYear()+"-01-01";
		return firstday;
	}	
	
	public String getCurrentYearStartDateRegularFormat()
	{
		String pfirstday="01-01-"+LocalDate.now().getYear();
		return pfirstday;
	}	
	
	public String SqlToRegularDate (String sqldate) throws ParseException
	{
		return regularDateFormat.format(sqlDateFormat.parse(sqldate));
	}
	
	
}

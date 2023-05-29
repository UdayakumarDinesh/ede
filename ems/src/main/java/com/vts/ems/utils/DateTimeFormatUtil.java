package com.vts.ems.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DateTimeFormatUtil 
{
	private static SimpleDateFormat regularDateFormat=new SimpleDateFormat("dd-MM-yyyy");
	private static SimpleDateFormat sqlDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter sqlDateFormatLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static  SimpleDateFormat getMonthNameAndYear() {
		return new SimpleDateFormat("MMM-yyyy");
	}
	
	
	public DateTimeFormatter getSqlDateFormatLocalDate() {
		return sqlDateFormatLocalDate;
	}


	public static  SimpleDateFormat getSqlDateFormat() {
		return sqlDateFormat;
	}
	public static  SimpleDateFormat getSqlDateAndTimeFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	public static  SimpleDateFormat getRegularDateFormat() {
		return regularDateFormat;
	}
	public static  SimpleDateFormat getDateMonthShortName() {
		return new SimpleDateFormat("dd-MMM-yyyy");
	}
	public static  SimpleDateFormat getDateMonthFullName() {
		return new SimpleDateFormat("dd-MMMM-yyyy");
	}
	
	public static long getDifferenceDays(Date d1, Date d2) {
	    long diff = d2.getTime() - d1.getTime();
	    return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	

	public static  int getYearFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getYear();
	}
	public static  int getYearFromSqlDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getYear();
		
	}
	public static  int getYearFromSqlDateAndTime(String datetimestring)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldatetime=LocalDateTime.parse(datetimestring,formatter);
		return ldatetime.getYear();
		
	}
	
	public static  int getMonthFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonthValue();
	}
	public static  int getMonthFromSqlDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonthValue();
		
	}
	public static  int getMonthFromSqlDateAndTime(String datetimestring)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldatetime=LocalDateTime.parse(datetimestring,formatter);
		return ldatetime.getMonthValue();
		
	}
	
	public static  String getMonthValFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH);
	}
	
	public static  String getMonthValFullFromRegularDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonth().getDisplayName(TextStyle.FULL,Locale.ENGLISH);
	}
	public static  String getMonthValFromSqlDate(String datestring) 
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ldate=LocalDate.parse(datestring,formatter);
		return ldate.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH);
		
	}
	public static  String getMonthValFromSqlDateAndTime(String datetimestring)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime ldatetime=LocalDateTime.parse(datetimestring,formatter);
		return ldatetime.getMonth().getDisplayName(TextStyle.SHORT,Locale.ENGLISH);		
	}
	
	
	public static  String getCurrentYear()
	{		
		return  String.valueOf(LocalDate.now().getYear());
	}
	
	
	/*---------------------------------------------*/
	
	public static  String getFirstDayofCurrentMonthSqlFormat()
	{
		String firstday=LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
		return firstday;
	}
	
	public static  String getLastDayofCurrentMonthSqlFormat()
	{
		String lastday=LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString();
		return lastday;
	}
	
	public static  String getFirstDayofCurrentMonthRegularFormat() throws Exception
	{
		String firstday=regularDateFormat.format(sqlDateFormat.parse(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString()));
		return firstday;
	}
	
	public static  String getLastDayofCurrentMonthRegularFormat() throws Exception
	{
		String lastday=regularDateFormat.format(sqlDateFormat.parse(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).toString()));
		return lastday;
	}	
	
	
	
	public static  String getFinancialYearStartDateSqlFormat()
	{
		String firstday=LocalDate.now().getYear()+"-04-01";
		return firstday;
	}	
	
	public static  String getFinancialYearEndDateSqlFormat()
	{
		String lastday=LocalDate.now().getYear()+"-03-31";
		return lastday;
	}
	
	public static  String getFinancialYearStartDateRegularFormat() throws Exception
	{
		String firstday="01-04-"+LocalDate.now().getYear();
		return firstday;
	}
	
	public static  String getFinancialYearStartDateRegularFormatSearch() throws Exception
	{
		String firstday="01-04-"+LocalDate.now().getYear();
		return firstday;
	}

	
	public static  String getFinancialYearStartDateRegularFormatCircular() throws Exception
	{
		String firstday="01-04-"+LocalDate.now().getYear();
		return firstday;
	}
	
	public static  String getFinancialYearStartDateRegularFormatOffice() throws Exception
	{
		String firstday="01-04-"+LocalDate.now().getYear();
		return firstday;
	}
	
	public static  String getFinancialYearEndDateRegularFormat() throws Exception
	{
		String lastday="31-03-"+LocalDate.now().getYear();
		
		return lastday;
	}
	
	public static  String getPreviousFinancialYearStartDateRegularFormat() throws Exception
	{
		String pfirstday="01-04-"+LocalDate.now().minusYears(1).getYear();
		return pfirstday;
	}
	public static  String getPreviousFinancialYearStartDateSqlFormat()
	{
		String pfirstday=LocalDate.now().minusYears(1).getYear()+"-04-01";
		return pfirstday;
	}	
	
	
	public static  String getCurrentYearStartDateSqlFormat()
	{
		String firstday=LocalDate.now().getYear()+"-01-01";
		return firstday;
	}	
	
	public static  String getCurrentYearStartDateRegularFormat()
	{
		String pfirstday="01-01-"+LocalDate.now().getYear();
		return pfirstday;
	}	
	
	public static  String SqlToRegularDate (String sqldate) throws ParseException
	{
		return regularDateFormat.format(sqlDateFormat.parse(sqldate));
	}
	
	public static  String RegularToSqlDate (String regulardate) throws ParseException
	{
		return sqlDateFormat.format(regularDateFormat.parse(regulardate));
	}

	public static java.sql.Date dateConversionSql(String sDate) {
		java.sql.Date ddate = null;

		SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MM-yyyy");
		try {
			java.util.Date jdate = sdf4.parse(sDate);
			long ms = jdate.getTime();
			ddate = new java.sql.Date(ms);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (ddate);
	}
	
	public static java.sql.Date  TodayDateInSqlFormat()
	{
 		java.sql.Date datetodaydb =null;
		try
		{
			Date dd=new Date();	
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy ");
	     	String datetoday=sdf.format(dd);
	        datetodaydb=dateConversionSql(datetoday);
	 		 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return datetodaydb;
	}
	
	public static String fromDatabaseToActual_inNumericFormOnly(String databaseDate)
	{
		String actualdate=null;
		try{
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date d = sdf.parse(databaseDate);
           sdf.applyPattern("dd-MM-yyyy");
            actualdate = sdf.format(d);
	       
		  }
		catch(Exception e){e.printStackTrace();}
		return(actualdate);
	
	}
	
	
	public static String fromDatabaseToActual_inNumericShortForm(String databaseDate)
	{
		String actualdate=null;
		try{
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date d = sdf.parse(databaseDate);
           sdf.applyPattern("dd/MM/yy");
            actualdate = sdf.format(d);
	       
		  }
		catch(Exception e){e.printStackTrace();}
		return(actualdate);
	
	}
	
	public static String fromDatabaseToActualInPeriodForm(String databaseDate)
	{
		String actualdate=null;
		try{
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date d = sdf.parse(databaseDate);
           sdf.applyPattern("MMM-yyyy");
            actualdate = sdf.format(d);
	       
		  }
		catch(Exception e){e.printStackTrace();}
		return(actualdate);
	
	}

	public static long dayDifference(String datebaseDate) {
		 long diff=0;
		try {
			 Date date = sqlDateFormat.parse(datebaseDate); // Convert the string to a Date object
	        	
	         
	         if(date.compareTo(new Date())<=0) {
	         Calendar cal1 = Calendar.getInstance();
	         Calendar cal2 = Calendar.getInstance();
	         cal1.setTime(date);
	         cal2.setTime(new Date());                                         
	         diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();		
	         diff/=(24 * 60 * 60 * 1000);
		}
		}catch(Exception e){e.printStackTrace();}
		
		return diff;

	}
	
	public static java.sql.Date getMinusOneDay(String toDate){
		try {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	        LocalDate locdate = LocalDate.parse(toDate, formatter);
	        LocalDate minusDate = locdate.minusDays(1);
	        String minusToDate = minusDate.format(formatter);
	        return dateConversionSql(minusToDate);
		}catch(Exception e){e.printStackTrace();}
		return null;
		
	}
	
	public static String getDateTimeToRegularDate(String date) {
		SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate=null;
        try {
            Date date1 = inputDateFormat.parse(date);
            formattedDate = outputDateFormat.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return formattedDate;
	}
	public static String fromDatabaseToActual(String databaseDate){
		String actualdate=null;
		try{
	       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date d = sdf.parse(databaseDate);
           sdf.applyPattern("dd-MMM-yyyy");
            actualdate = sdf.format(d);
	       
		  }
		catch(Exception e){System.out.println(e);}
		return(actualdate);
	
	}
	public static long CountNoOfDaysBwdates(String fromdate , String todate)throws Exception
	{
		try {
				LocalDate dateBefore = LocalDate.parse(fromdate);
			    LocalDate dateAfter = LocalDate.parse(todate);
	
			    long daysDiff = ChronoUnit.DAYS.between(dateBefore, dateAfter);
			    return daysDiff;
			}catch(Exception e){
			    e.printStackTrace();
			    return 0l;
			}
	}
	
	public static void main(String args[]) throws Exception {
		
		long dateDifference = DateTimeFormatUtil.dateDifference("2023-05-30");
		System.out.println(dateDifference);
	}
	
	public static long dateDifference(String databaseDate) throws Exception{
		try {
			LocalDate currentDate = LocalDate.now();
	        // Convert the given date string to LocalDate
	        LocalDate givenDate = LocalDate.parse(databaseDate);

	        // Calculate the number of days between the two dates
	        return ChronoUnit.DAYS.between(currentDate, givenDate);
		}catch (Exception e) {
			 e.printStackTrace();
			    return 0l;
		}
		

	}
}	


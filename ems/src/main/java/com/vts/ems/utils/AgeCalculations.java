package com.vts.ems.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgeCalculations {
	
	public static List<String> calages(List<Object[]> reportobjs,String type){
		
		List<String> rages=new ArrayList<>();
		for (Object[] objects : reportobjs) {
			Date date=null;
			String age="";
			
	     //Age Calculation	
			if(objects[0] !=null) {
				 date=(Date)objects[0];
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String dt=df.format(date);//2019/04/03
				dt=dt.replaceAll("/", "-");
				LocalDate userDob=LocalDate.parse(dt);
				LocalDate presentDate = LocalDate.now();
				int ayear=Period.between(userDob, presentDate).getYears();
				int amonth=Period.between(userDob, presentDate).getMonths();
				int days=Period.between(userDob, presentDate).getDays();
				
				 age=""+ayear+"(Y) "+amonth+"(M) "+days+"(D) ";
				rages.add(age);
			}
			
		if(objects[0]==null) {
			age="No Data Availavle";
			rages.add(age);
		}
			
	
		//Date Format FOR DOB AS DD-MM-YYYY
		if("DOB".equals(type)) {
		DateFormat dfdob = new SimpleDateFormat("dd/MM/yyyy");
		String dtdob=dfdob.format(date);
		dtdob=dtdob.replaceAll("/", "-");
		objects[4]=dtdob;
		}
		
		if("DOA".equals(type)) {
		Date doadate=(Date)objects[1];
		DateFormat doaformat = new SimpleDateFormat("dd/MM/yyyy");
		String doaft=doaformat.format(doadate);
		doaft=doaft.replaceAll("/", "-");
		objects[4]=doaft;
		}
		
		if("DOR".equals(type)) {
			Date dordate=(Date)objects[2];
			DateFormat dfdor = new SimpleDateFormat("dd/MM/yyyy");
			String dtdor=dfdor.format(dordate);
			dtdor=dtdor.replaceAll("/", "-");
			objects[4]=dtdor;
			}
	
		if("DOJ".equals(type)) {
			Date dojdate=(Date)objects[3];
			DateFormat dfdoj = new SimpleDateFormat("dd/MM/yyyy");
			String dtdoj=dfdoj.format(dojdate);
			dtdoj=dtdoj.replaceAll("/", "-");
			objects[4]=dtdoj;
			}

		}
		return rages;
		
	}

	public static List<String> getServiceYears(List<Object[]> dobreportobjs) {
		List<String> servyears=new ArrayList<>();
		for (Object[] objects : dobreportobjs) {
			String syears="";
		if(objects[1] !=null) {
			Date date=(Date)objects[1];
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String dt=df.format(date);//2019/04/03
			dt=dt.replaceAll("/", "-");
			LocalDate userDoj=LocalDate.parse(dt);
			LocalDate presentDate = LocalDate.now();
			int ayear=Period.between(userDoj, presentDate).getYears();
			int amonth=Period.between(userDoj, presentDate).getMonths();
			int days=Period.between(userDoj, presentDate).getDays();
	
			 syears=""+ayear+"(Y) "+amonth+"(M) "+days+"(D) ";
			servyears.add(syears);
		}
		if(objects[1] ==null) {
			syears="No Data Available";
			servyears.add(syears);
		}
	

	
		}
		
		return servyears;
	}
	
	public static String getMonthName(Integer month) {
		String monthString;
		 switch (month) {
         case 1:  monthString = "January";       break;
         case 2:  monthString = "February";      break;
         case 3:  monthString = "March";         break;
         case 4:  monthString = "April";         break;
         case 5:  monthString = "May";           break;
         case 6:  monthString = "June";          break;
         case 7:  monthString = "July";          break;
         case 8:  monthString = "August";        break;
         case 9:  monthString = "September";     break;
         case 10: monthString = "October";       break;
         case 11: monthString = "November";      break;
         case 12: monthString = "December";      break;
         case 0: monthString = "--No Month--";      break;
         default: monthString = "Invalid month"; break;
     }
		 return monthString;
		
	}

	

}

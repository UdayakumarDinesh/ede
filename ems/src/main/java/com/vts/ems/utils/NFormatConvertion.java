package com.vts.ems.utils;
import java.util.Locale;

public class NFormatConvertion {
   
	public static String convert(final Long d) 
	{
			/*
			 * NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
			 * System.out.println("ITALY representation of " + d + " : " + nf.format(d));
			 * 
			 * nf = NumberFormat.getInstance(Locale.GERMANY);
			 * System.out.println("GERMANY representation of " + d + " : " + nf.format(d));
			 * 
			 * nf = NumberFormat.getInstance(Locale.CHINESE);
			 * System.out.println("CHINESE representation of " + d + " : " + nf.format(d));
			 * 
			 * nf = NumberFormat.getInstance(Locale.US);
			 * System.out.println("US representation of " + d + " : " + nf.format(d));
			 * 
			 * nf = NumberFormat.getInstance(Locale.ENGLISH);
			 * System.out.println("ENGLISH representation of " + d + " : " + nf.format(d));
			 * 
			 * nf = NumberFormat.getInstance(Locale.UK);
			 * System.out.println("UK representation of " + d + " : " + nf.format(d));
			 */
	     
	     com.ibm.icu.text.NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
	     String Amount=format.format(d);
	     
	    
	     
	      StringBuilder myString = new StringBuilder(Amount);
	      char ch=' ';
	      myString.setCharAt(0,ch);
	    
	     return myString.toString();
		}
	}

package com.vts.ems.utils;
import java.util.Locale;

public class NFormatConvertion {
   
	public static String convert(final Long d) 
	{
			/*
			 * NumberFormat nf = NumberFormat.getInstance(Locale.ITALY);
			 * 
			 * nf = NumberFormat.getInstance(Locale.GERMANY);
			 * 
			 * nf = NumberFormat.getInstance(Locale.CHINESE);
			 * 
			 * nf = NumberFormat.getInstance(Locale.US);
			 * 
			 * nf = NumberFormat.getInstance(Locale.ENGLISH);
			 * 
			 * nf = NumberFormat.getInstance(Locale.UK);
			 */
	     
	     com.ibm.icu.text.NumberFormat format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
	     String Amount=format.format(d);
	     
	    
	     
	      StringBuilder myString = new StringBuilder(Amount);
	      char ch=' ';
	      myString.setCharAt(0,ch);
	    
	     return myString.toString();
		}
	}

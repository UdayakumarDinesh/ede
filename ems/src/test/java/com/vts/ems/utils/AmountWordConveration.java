package com.vts.ems.utils;



public class AmountWordConveration {
	
	    public static final String[] units = {
	            "", "One", "Two", "Three", "Four", "Five", "Six", "Seven",
	            "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen",
	            "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"
	    };

	    public static final String[] tens = {
	            "",        // 0
	            "",        // 1
	            "Twenty",  // 2
	            "Thirty",  // 3
	            "Forty",   // 4
	            "Fifty",   // 5
	            "Sixty",   // 6
	            "Seventy", // 7
	            "Eighty",  // 8
	            "Ninety"   // 9
	    };

	    public static String convert1(final long n) {
	        if (n < 0) {
	            return "Minus " + convert1(-n);
	        }

	        if (n < 20) {
	            return units[(int)n];
	        }

	        if (n < 100) {
	            return tens[(int)n / 10] + ((n % 10 != 0) ? " " : "") + units[(int)n % 10];
	        }

	        if (n < 1000) {
	            return units[(int)n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convert1(n % 100);
	        }

	        if (n < 100000) {
	            return convert1(n / 1000) + " Thousand" + ((n % 1000 != 0) ? " " : "") + convert1(n % 1000);
	        }

	        if (n < 10000000) {
	            return convert1(n / 100000) + " Lakh" + ((n % 1000000 != 0) ? " " : "") + convert1(n % 100000);
	        }

	        return convert1(n / 10000000) + " Crore"  + ((n % 10000000 != 0) ? " " : "") + convert1(n % 10000000);
	    }

	   
	
}

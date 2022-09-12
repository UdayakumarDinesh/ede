<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page language="java"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>exp sanc</title>
<style type="text/css">
h1 {
    border-bottom: 2px solid black;
    width: 53%;
}
</style>

<style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
}
 
 
 </style>  
</head>
<body>
<%
AmountWordConveration nw = new AmountWordConveration();
Object[] NewsPaperExpSancReport=(Object[])request.getAttribute("NewsPaperExpSancReport");

String RUPEES="Not Available";
String PAISA="Not Available";
String RupeeInWords="Not Available";
if(NewsPaperExpSancReport!=null)
{
	String total=NewsPaperExpSancReport[2].toString();
	 String [] totalRsPaisA=total.split("\\.");
	
	 String RupeeInNonFormat=totalRsPaisA[0];
	 PAISA=totalRsPaisA[1];
	
    RUPEES=new IndianRupeeFormat().rupeeFormat(RupeeInNonFormat);
    RupeeInWords  = nw.convert1(Integer.parseInt(RupeeInNonFormat));
}





 %>
 <div align="center"><input type="button"  class="btn btn-sm print-btn"  id="printPageButton" value="Print" onClick="window.print()"></div>
<div align="center"><h2><font size="+1"><br><br>EXPENDITURE SANCTION</font></h2></div>
<br><br><br>
In exercise of the power vested vide Re-imbursement of newspaper bills (Residential & Mobile)to the entitled GOs and Service Officers
of DARE for the various months in accordance with GOI/MOF/DOE OM F.No.24(3)/:Coord/----- dated 
,I hereby accord sanction for re-imbursement of newspaper bills as per attached list.
 <br><br><br>
 <b>Description of items / services: </b>Re-imbursement of newspaper bills (Residential & Mobile)
 to entitled GOs of DARE and Service Officers.<br><br><br><br>
 The Expenditure involved is debitable to Major Head : 2080 Minor Head : 800 Code Head 
 858/01 of Defence Services Estimates,Research & Development, and payment shall be released by CDA
  (R&D),CV Raman Nagar,Bangalore with unit code No.320000007.
  <br><br><br><br>
  Period:<b> from <%if(NewsPaperExpSancReport!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(NewsPaperExpSancReport[0].toString()));} %>to <%if(NewsPaperExpSancReport!=null){out.println(DateTimeFormatUtil.SqlToRegularDate(NewsPaperExpSancReport[1].toString()));} %>. </b>
  <br>Value: Rs <b><%=RUPEES%>.<%=PAISA%></b><br>
  (<tt><b>Rupees <%=RupeeInWords%> only</b></tt>)
  <br><br><br><br>
  <center><b>SANCTIONED</b></center>
  <br><br><br><br><br>
  <center><b>DIRECTOR</b></center>
  <br><br>
  <p>
  <b>Copy to :</b><br>
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>LAO(R&D),Bangalore</b>
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Test Audit,Bangalore</b>
  </p>
  <br><br><br>
  File No.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  Dt.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</body>
</html>
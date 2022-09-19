
<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page language="java"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Telephone Expenditure Sanction</title>
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
AmountWordConveration nw=new AmountWordConveration();
Object[] TeleExpSancReport=(Object[])request.getAttribute("TeleExpSancReport");

String RUPEES="Not Available";
String PAISA="Not Available";
String RupeeInWords="Not Available";
 
if(TeleExpSancReport!=null)
{
	String total=TeleExpSancReport[2].toString();
	 String [] totalRsPaisA=total.split("\\.");
	
	 String RupeeInNonFormat=totalRsPaisA[0];
	 PAISA=totalRsPaisA[1];
	
     RUPEES=IndianRupeeFormat.rupeeFormat(RupeeInNonFormat);
     RupeeInWords  = nw.convert1(Integer.parseInt(RupeeInNonFormat));
}



String TeleExpSncFileNo =(String)request.getAttribute("TeleExpSncFileNo");
String TelephoneAuthority =(String)request.getAttribute("TelephoneAuthority");
LabMaster LabDetails =(LabMaster)request.getAttribute("LabDetails");


SimpleDateFormat sdf2=new SimpleDateFormat("MMM-yyyy");
java.util.Date date=new java.util.Date();

 %>
 <center><input type="button"  class="btn btn-info btn-xs center-block"  id="printPageButton" value="Print" onClick="window.print()"></center>
<center><h2><font size="+1"><br><br>EXPENDITURE SANCTION</font></h2></center>

 <br><br><br>
 <b><%=TeleExpSncFileNo %>    </b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <b><%=sdf2.format(date) %></b>&nbsp;&nbsp;&nbsp;
<br>
<br>

In exercise of the power vested vide Re-imbursement of telephone bills (Residential & Mobile)to the entitled GOs 
of <%=LabDetails.getLabCode() %> for the various months in accordance with <%=TelephoneAuthority %> 
,sanction is  hereby accorded  for re-imbursement of telephone bills as per attached list.
 <br><br><br>
 <b>Description of items / services: </b>Re-imbursement of telephone bills (Residential & Mobile)
 to entitled GOs of <%=LabDetails.getLabCode() %>.<br><br><br><br>
 The Expenditure involved is debitable to Major Head : 2080 Minor Head : 800 Code Head 
 858/01 of Defence Services Estimates,Research & Development, and payment shall be released by CDA
  (R&D),CV Raman Nagar,Bangalore with unit code No.320000007.
  <br><br><br><br>
  Period:<b> from <%if(TeleExpSancReport!=null){out.println(DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(TeleExpSancReport[0].toString()));} %>to <%if(TeleExpSancReport!=null){out.println(DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(TeleExpSancReport[1].toString()));} %>. </b>
  <br>Value: Rs <b><%=RUPEES%>.<%=PAISA%></b><br>
  (<tt><b>Rupees <%=RupeeInWords%> only</b></tt>)
  <br><br><br><br>
  <center><b>SANCTIONED</b></center>
  <br><br><br><br><br>
  <%if(LabDetails.getLabCode().equalsIgnoreCase("STARC")){ %>
  <center><b>CEO</b></center>
  <%}else{ %>
  <center><b>Joint Director(Admin)<br>For Director, <%=LabDetails.getLabCode() %></b></center>
  <%} %>
  <br><br>
  <p>
  <b>Copy to :</b><br>
    <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>CDA(R&D),Bangalore-M Section</b>
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>LAO(R&D),Bangalore</b>
  <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Test Audit,Bangalore</b>
  </p>
 
</body>
</html>
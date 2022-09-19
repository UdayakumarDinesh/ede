
<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.text.DecimalFormat"%>

<%@ page language="java" %>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Telephone Contingent Bill</title>

<style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
}
 
 
 </style>  
<style>
/* css buttom border */
h1 {
    border-bottom: 2px solid black;
    width: 23%;
}
p2 {
 
    border-bottom: 2px solid black;
    width: 15%;
}
p3 {
    margin-right: 3;
    border-bottom: 2px solid black;
    width: 18%;
}
p4 {
    border-bottom: 2px solid black;
    width: 100%;
    border-right: 2px solid black;
    width: 100%;
    }

/* css buttom border */

p.ex1 {
    margin-left: 0;
}
/* p.ex2 { */
/*     margin-right: 330px; */
/* } */
div.r {
    text-align: right;
    margin-right: 0;
} 

.vl {
    border-top: 2px solid black;
    width: 100%;
    border-bottom: 2px solid black;
    width: 100%;
    border-left: 2px solid black;
    height: 500px;
    border-right: 2px solid black;
    height: 500px;
   
}
.alignleft {
	float: left;
}
.alignright {
	float: right;
}


table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}

</style>

</head>
<body>
<%
AmountWordConveration nw=new AmountWordConveration();
Object[] TelephoneContingentBillPrint=(Object[])request.getAttribute("TelephoneContingentBillPrint");
String RUPEES="Not Available";
String PAISA="Not Available";
String RupeeInWords="Not Available";
 

if(TelephoneContingentBillPrint!=null)
{
	 String total=TelephoneContingentBillPrint[2].toString();
	 String [] totalRsPaisA=total.split("\\.");
	
	 String RupeeInNonFormat=totalRsPaisA[0];
	 PAISA=totalRsPaisA[1];
	
     RUPEES=IndianRupeeFormat.rupeeFormat(RupeeInNonFormat);
     RupeeInWords  = nw.convert1(Integer.parseInt(RupeeInNonFormat));
}




String LabCode =(String)request.getAttribute("LabCode");

String TelephoneAuthority =(String)request.getAttribute("TelephoneAuthority");
LabMaster LabDetails=(LabMaster)request.getAttribute("LabDetails");
String TeleContingentVoucherNo =(String)request.getAttribute("TeleContingentVoucherNo");

String PublicFundNo =(String)request.getAttribute("PublicFundNo");


%>

<center><input type="button"  class="btn btn-info btn-xs center-block"  id="printPageButton" value="Print" onClick="window.print()"></center>
<center><h1><font size="+1">MISCELLANEOUS</font></h1></center>

<p><font size="+0"><b>Voucher No.:&nbsp;&nbsp;<%=TeleContingentVoucherNo %>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;</b></font></p>

<div class="r">
<p>Minor Head - 800(a)<br>Code Head - 858/01</p>
</div>

<center><p2>CONTINGENT BILL</p2></center>

<p>
Amount of Allotment ................................................................................................................. Rs.<br>
Amount expended existing the amount of this bill..................................................................... Rs.  <br> 
Balance of allotment existing the amount of this bill................................................................. Rs.<br> 
Expenditure on account of <h>Re-imbursement of telephone bills</h> incurred by <%=LabCode%> CGO's <br> 
 for the period <b> from <%if(TelephoneContingentBillPrint!=null){out.println(DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(TelephoneContingentBillPrint[0].toString()));} %>to <%if(TelephoneContingentBillPrint!=null){out.println(DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(TelephoneContingentBillPrint[1].toString()));} %>. </b></h><br>
<%if(TelephoneAuthority != null && !TelephoneAuthority.trim().equalsIgnoreCase("")){ %>
Authority - <%=TelephoneAuthority %>
<%} %>
</p>


<table>
  <tr>
    <th>&nbsp;SL.&nbsp;&nbsp;<br>&nbsp;NO.&nbsp;&nbsp;</th>
    <th>Details of items/Expenditure</th>
    <th>&nbsp;Qty.&nbsp;&nbsp;</th>
    <th colspan="2">&nbsp;Rate/ A.U&nbsp;&nbsp;<br>&nbsp;RS.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; P.&nbsp;&nbsp;&nbsp;&nbsp;</th>
    <th colspan="2">&nbsp;Amount&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>Rs.&nbsp;&nbsp; P.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
  </tr>
 <tr>
 <td> </td>
 <td>
 <br>
 &nbsp;&nbsp;Expenditure incurred towards re-imbursement of<br>
 &nbsp;&nbsp;Telephone bills (Residential & Mobile) to entitled<br>
 &nbsp;&nbsp;GOs  of <%=LabCode%> for various <br>
&nbsp;&nbsp;months as effective during the period <b>from <%if(TelephoneContingentBillPrint!=null){out.println(DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(TelephoneContingentBillPrint[0].toString()));} %> <br>
 &nbsp;&nbsp;to <%if(TelephoneContingentBillPrint!=null){out.println(DateTimeFormatUtil.fromDatabaseToActualInPeriodForm(TelephoneContingentBillPrint[1].toString()));} %> </b>as per the list attached.<br>
  <br>
 &nbsp;&nbsp;It is certified that the expenditure incurred is for<br>&nbsp;&nbsp; the
 bonafide Government purposes.<br>
 <br>
&nbsp;&nbsp;MODE OF PAYMENT : ECS Credit to<br>
&nbsp;&nbsp; <%=LabCode%> Public Fund Account<br>
&nbsp;&nbsp; No. <%=PublicFundNo %><br>
 <br> 
 </td>
 <td> </td>
 <td> </td>
 <td> </td>
    <td style="text-align: right;"> &nbsp;<b ><%=RUPEES%>&nbsp;&nbsp;</b></td>
    <td style="text-align: right;">&nbsp;<b><%=PAISA%> &nbsp;&nbsp;</b></td>
 </tr>
 
 <tr>
    <td colspan="5"><br>&nbsp;&nbsp;Advance received on:.............................................&nbsp;&nbsp;(Date)..................................&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <br>&nbsp;&nbsp;DEDUCT :
    <br><br>
    &nbsp;&nbsp;Net Total
    
    </td>
    <td style="text-align: right;"> &nbsp;<b><%=RUPEES%></b>&nbsp;&nbsp;</td>
    <td style="text-align: right;">&nbsp;<b><%=PAISA%></b>&nbsp;&nbsp; </td>
  </tr>
 
</table>



<p2>Reason for deduction</p2>
<p><b>Net amount (in words): (Rupees <%=RupeeInWords%> only)</b></p>
<p>Certified that the above charges have been necessarily incurred in the interest of the State.That the rates charges
 are the lowest obtainable and that I have personally checked the progressive total in the bill with that in contingent 
 registers and is found to agree.</p>
 
 <div id="textbox">
  <center><p2>Countersigned </p2>
  <p3 class="alignright"> Received Payment</p3></center>
</div>
 
  <p>
  Place :<%=LabCode%>,<%=LabDetails.getLabCity() %> <br>
  Date : 
  </p>
  
</body>
</html>
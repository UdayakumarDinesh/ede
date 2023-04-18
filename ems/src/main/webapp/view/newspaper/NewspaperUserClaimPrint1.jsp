<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" %>

<!DOCTYPE html>
<html>
<head>
<!--  Bootstrap -->
	<link rel="stylesheet" href="vtsfolder/bower_components/bootstrap/dist/css/bootstrap.min.css"/>
 
<style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
}

table {
	border-collapse: collapse;
	height: 110px;
	font-weight: bold;
	width: 620px;	
}

tr, th, td {
	border: 2px solid black;
	padding: 5px;
}
.table2 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}
.table2 td:first-child:not(.exclude){
	width: 6%;
}
.table2 td:nth-child(2):not(.exclude) {
  width: 40%;
}
.text-center{
	text-align: center;
}

.declare{
	width: 620px;
	margin-top: 25px;
	text-align: justify;
}
 </style>  
    
<title>Newspaper Claim Print</title>
</head>
<body>


<%
String name =(String)request.getAttribute("name");
String designation=(String)request.getAttribute("desig");
Object[] NewspaperUserPrintData=(Object[])request.getAttribute("NewspaperUserPrintData");
LabMaster LabDetails=(LabMaster)request.getAttribute("LabDetails");
String PayableRupee="Not Available";
String AdmissibleRupee="Not Available";
String ClaimRupee="Not Available";

/* String NewsClaimHeader =(String)request.getAttribute("NewsClaimHeader"); */

if(NewspaperUserPrintData!=null)
{
	 String ClaimAmountRsPaisA[]=IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[2].toString());
	 ClaimRupee=IndianRupeeFormat.rupeeFormat(ClaimAmountRsPaisA[0]);
	
	 String PayableAmountRsPaisA[]=IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[4].toString());
	 PayableRupee=IndianRupeeFormat.rupeeFormat(PayableAmountRsPaisA[0]);
	
	 String AdmissibleAmountRsPaisA[]=IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[3].toString());
	 AdmissibleRupee=IndianRupeeFormat.rupeeFormat(AdmissibleAmountRsPaisA[0]);
	 
}

%>


<div align="center">
	<table >
		<tr>
			<td class="text-center" rowspan="2"><span style="font-size: 30px">STARC</span>  <br>BANGALORE</td>
			<td class="text-center" width="300px" rowspan="2">CLAIM FORM FOR <br>REIMBURSEMENT <br> OF RESIDENTIAL NEWSPAPERS TO <br> EXECUTIVES </td>
			<td>SITAR-BNG <br> P&A-057</td>
			<td>Rev.: 04</td>
		</tr>
		<tr>
			<td>Date of Issue: <br>09.04.2018 </td>
			<td>Total<br> Pages-1 </td>
		</tr>
	</table>
	
	<div style="margin-top: 50px">
		<table class="table2">
			<tr>
				<td>1.</td>
				<td>Name of the Employee</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td>2.</td>
				<td>Emp. No.</td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td>3.</td>
				<td>Designation </td>
				<td colspan="2"></td>
			</tr>
			<tr>
				<td >4.</td>
				<td>Grade & Level in the Pay Matrix</td>
				<td></td>
				<td></td>
			</tr>
			<tr >
				<td rowspan="2" height="40px" >5.</td>
				<td rowspan="2">Bi-annual & Year of claim</td>
				<td style="font-weight: normal; height :20px;">January-June</td>
				<td style="font-weight: normal; height :20px;">July - December</td>
			</tr>
			<tr>
				<td class="exclude"></td>
				<td class="exclude"></td>
			</tr>
			<tr>
				<td>6.</td>
				<td>Amount of Reimbursement @ ` 500/- p.m.</td>
				<td colspan="2"></td>
			</tr>
		</table>
	</div>
	
	<div class="declare">
		I hereby certify that the (i) The newspaper (s) in respect of which reimbursement is claimed,
		is/are purchased by me. (ii) The subscription amount incurred by me is not less than the
		amount claimed towards subscription of newspaper / s for the period. (iii) The amount for
		which the reimbursement is being claimed has actually been paid by me and has not/will not
		be claimed by any other source.
		
		<div class="row">
		<div class="col-4">Date:</div>
		<div class="col-4">Signature of Employee <br> STARC Intercom No: </div>
		</div>
	</div>
	
	
	<input type="button"  class="btn btn-sm print-btn"  id="printPageButton" value="Print" onClick="window.print()">
</div>


</body>
</html>
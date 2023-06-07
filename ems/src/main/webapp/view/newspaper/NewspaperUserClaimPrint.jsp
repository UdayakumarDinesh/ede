<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java"%>

<!DOCTYPE html>
<html>
<%String name = (String) request.getAttribute("name"); 
String empNo = (String) request.getAttribute("empNo");
%>
<head>
<!--  Bootstrap -->
<link rel="stylesheet"
	href="vtsfolder/bower_components/bootstrap/dist/css/bootstrap.min.css" />

<style type="text/css">
@media print {
	#printPageButton {
		display: none;
	}
}

table {
	border-collapse: collapse;
	font-weight: bold;
	width: 620px;
}

tr, th, td {
	border: 2px solid black;
}

.table2 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}

.table2 td:first-child:not(.exclude) {
	width: 6%;
}

.table2 td:nth-child(2):not(.exclude) {
	width: 40%;
}

.text-center {
	text-align: center;
}

.declare {
	width: 560px;
	margin-top: 25px;
	margin-left: 13px;
	text-align: justify;
}


.table3 tr, th, td {
	border: 1px solid black;
	padding: 0px;
}

.table3 tr {
	height: 10px;
}

.table3 td {
	font-weight: normal;
}

.accDPT {
	margin-top: 30px;
}

.table4 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}

.table4 td {
	font-weight: normal;
}

.table4 td:first-child:not(.exlude) {
	width: 45%;
}

.sign {
	width: 620px;
	text-align: right;
	margin-top: 60px;
}

.break {
	page-break-after: always;
}

@page {
	size: 790px 1120px;
	margin-top: 49px;
	margin-left: 55px;
	margin-right: 55px;
	margin-buttom: 49px;
	border: 2px solid black; @ bottom-right { counter-increment : page;
	counter-reset: page 2;
	content: "Page " counter(page) " of " counter(pages);
	margin-bottom: 30px;
	margin-right: 10px;
}

@
top-right {
	content: "";
	margin-top: 30px;
	margin-right: 10px;
}

@
top-left {
	margin-top: 30px;
	margin-left: 10px;
	content: "Empno : <%=empNo %>";
}
}
div {
	width: 650px !important;
}

body {
	font-size: 15px !important;
}

table {
	width: 650px !important;
	margin-top: 10px;
	margin-bottom: 10px;
	margin-left: 10px;
	border-collapse: collapse;
	text-align: left;
}

tr, th, td {
	border: 1.5px solid black;
	padding: 4px;
}

.blue {
	color: blue;
}
.roww{
	display: flex;
	justify-content: space-between;
}

</style>

<title>Newspaper Claim Print</title>
</head>
<body>


	<%

	
	String designation = (String) request.getAttribute("designation");
	Object[] NewspaperUserPrintData = (Object[]) request.getAttribute("NewspaperUserPrintData");
	String LabLogo = (String) request.getAttribute("LabLogo");
	String PayableRupee = "Not Available";
	String RistrictedAmt = "Not Available";
	String ClaimRupee = "Not Available";
	
	SimpleDateFormat rdtf= new SimpleDateFormat("dd-MM-yyyy hh:mm a");

	/* String NewsClaimHeader =(String)request.getAttribute("NewsClaimHeader"); */

	if (NewspaperUserPrintData != null) {
		String ClaimAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[2].toString());
		ClaimRupee = IndianRupeeFormat.rupeeFormat(ClaimAmountRsPaisA[0]);

		String PayableAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[4].toString());
		PayableRupee = IndianRupeeFormat.rupeeFormat(PayableAmountRsPaisA[0]);

		String AdmissibleAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[3].toString());
		RistrictedAmt = IndianRupeeFormat.rupeeFormat(AdmissibleAmountRsPaisA[0]);

	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date today = new Date();
	%>


	<div align="center">
		<table style="height: 100px;">
			<tr>
				<td  rowspan="2">
				<img style="width: 80px; height: 90px; margin: -43px 0 0 15px;" align="left" 
					src="data:image/png;base64,<%=LabLogo%>">
			</td>
				<td class="text-center" width="300px" rowspan="2">CLAIM FORM
					FOR <br>REIMBURSEMENT <br> OF RESIDENTIAL NEWSPAPERS TO <br>
					EXECUTIVES
				</td>
				<td>SITAR-BNG <br> P&A-057
				</td>
				<td>Rev.: 04</td>
			</tr>
			<tr>
				<td>Date of Issue: <br><%=dateFormat.format(today)%>
				</td>
				<td>Total<br> Pages-1
				</td>
			</tr>
		</table>

		<div style="margin-top: 20px">
			<table class="table2">
				<tr>
					<td>1.</td>
					<td>Name of the Employee</td>
					<td colspan="2" class="blue"><%=name%></td>
				</tr>
				<tr>
					<td>2.</td>
					<td>Emp. No.</td>
					<td colspan="2" class="blue"><%=empNo%></td>
				</tr>
				<tr>
					<td>3.</td>
					<td>Designation</td>
					<td colspan="2" class="blue"><%=designation%></td>
				</tr>
				<tr>
					<td>4.</td>
					<td>Grade & Level in the Pay Matrix</td>
					<td style="text-align: center;" class="blue">
						<%
						if (NewspaperUserPrintData != null) {
						%><%=NewspaperUserPrintData[6]%>
						<%
						}
						%>
					</td>
					<td style="text-align: center;" class="blue">
						<%
						if (NewspaperUserPrintData != null) {
						%><%=NewspaperUserPrintData[5]%>
						<%
						}
						%>
					</td>
				</tr>
				<tr>
					<td height="40px">5.</td>
					<td>Bi-annual & Year of claim</td>
					<td class="exclude" colspan="2" class="blue">
						<%
						if (NewspaperUserPrintData[0].toString().equalsIgnoreCase("JAN-JUN")) {
						%>
						January-June <%=NewspaperUserPrintData[1]%> <%
 }
 if (NewspaperUserPrintData[0].toString().equalsIgnoreCase("JUL-DEC")) {
 %>July
						- December <%=NewspaperUserPrintData[1]%> <%
 }
 %>
					</td>
					<%-- <td class="exclude"><% if(NewspaperUserPrintData[0].toString().equalsIgnoreCase("JUL-DEC")){%>  <%=NewspaperUserPrintData[1]%> <%} else{%>-<%} %></td> --%>
				</tr>
				<tr>
					<td>6.</td>
					<td>Amount of Reimbursement @ ` 500/- p.m.</td>
					<%
					String amt = PayableRupee;
					amt = amt.replace(",", "");
					if (Integer.parseInt(amt) <= 3000) {
					%>
					<td colspan="2" class="blue">Rs <%=amt%> /-
					</td>
					<%
					} else {
					%>
					<td colspan="2" class="blue">Rs 3,000 /-</td>
					<%
					}
					%>
				</tr>
			</table>
		</div>

		<div class="declare" >
			I hereby certify that the (i) The newspaper (s) in respect of which
			reimbursement is claimed, is/are purchased by me. (ii) The
			subscription amount incurred by me is not less than the amount
			claimed towards subscription of newspaper / s for the period. (iii)
			The amount for which the reimbursement is being claimed has actually
			been paid by me and has not/will not be claimed by any other source.
			
				<p style=" display: inline; float: left; margin-top: 40px; " ><b>Date:</p>

				<p style=" display: inline; float: right; margin-top: 40px;  text-align: right; ">
					Signature of Employee <br> </b>
					<span style="color: blue"> <%=NewspaperUserPrintData[9]%>,&nbsp;<%=NewspaperUserPrintData[10]%></span> <br> <%if(!NewspaperUserPrintData[11].toString().equalsIgnoreCase("SDG") && !NewspaperUserPrintData[11].toString().equalsIgnoreCase("SBP") && !NewspaperUserPrintData[11].toString().equalsIgnoreCase("SBA") && !NewspaperUserPrintData[11].toString().equalsIgnoreCase("")) {%>  <span style="color: blue"> <%if (NewspaperUserPrintData[12] !=null) {%>[Forwarded On:  <%=rdtf.format(NewspaperUserPrintData[12]) %> ]<%} %> </span> <%} %><br> <b> STARC Intercom No: </b>
					
				</p>

		</div>
		<div style="margin-top: 120px;">
			<div align="left" style="margin-left: 30px; ">Note:</div>
			<table class="table3">
				<tr>
					<th>Name of the Quarter</th>
					<th>Due date of claim submission (on or before)</th>
				</tr>
				<tr>
					<td>January - June</td>
					<td>15th of July</td>
				</tr>
				<tr>
					<td>July - December</td>
					<td>15th of January</td>
				</tr>
			</table>
		</div>

		<div class="accDPT">
			<div
				style="text-decoration: underline; font-weight: bold; margin-bottom: 20px;">FOR
				ACCOUNTS DEPARTMENT USE</div>
			<table class="table4">
				<tr>
					<td>Claimed Amount</td>
					<td class="blue"><b>Rs <%=ClaimRupee%> /-
					</b></td>
				</tr>
				<tr>
					<td>Eligible Amount</td>
					<td class="blue"><b>Rs <%=RistrictedAmt%> /-
					</b></td>
				</tr>
				<tr>
					<td>Amount passed for payment</td>
					<%
					String amt1 = PayableRupee;
					amt1 = amt1.replace(",", "");
					if (Integer.parseInt(amt1) <= 3000) {
					%>
					<td colspan="2" class="blue"><b>Rs <%=amt1%> /-</td>
					<%
					} else {
					%>
					<td colspan="2" class="blue">Rs 3000 /-</b></td>
					<%
					}
					%>
				</tr>
			</table>
		</div>
		<!-- <div class="sign">
			<b style="margin-right: 50px; ">F&A Dept. / FO </b>
		</div> -->
	<%if(!NewspaperUserPrintData[11].toString().equalsIgnoreCase("SDG") && !NewspaperUserPrintData[11].toString().equalsIgnoreCase("SBP") && !NewspaperUserPrintData[11].toString().equalsIgnoreCase("SBA") && !NewspaperUserPrintData[11].toString().equalsIgnoreCase("")) {%>
		<div class="roww" style="margin-top: 50px;">
			<main ali style=" display: inline; float: left; text-align: left; margin-left: 20px;">
				<% if(NewspaperUserPrintData[19] != null) { %>
			<span style="color: blue">	<%=NewspaperUserPrintData[19] %> <%} %>, <br>
				<% if(NewspaperUserPrintData[20] != null) { %>
				<%=NewspaperUserPrintData[20] %> <%} %></span> <br>
				<% if(NewspaperUserPrintData[18] != null) { %>
				[Processed On :<span style="color: blue"> <%=rdtf.format(NewspaperUserPrintData[18]) %>]</span> <%} %>

			</main >
			<main style=" display: inline; float: right; text-align: right;">
				<% if(NewspaperUserPrintData[22] != null) { %>
				<span style="color: blue"><%=NewspaperUserPrintData[22] %> <%} %>, <br>
				<% if(NewspaperUserPrintData[23] != null) { %>
				<%=NewspaperUserPrintData[23] %> <%} %> </span> <br>
				<% if(NewspaperUserPrintData[21] != null) { %>
				[Authorized On :<span style="color: blue"><%=rdtf.format(NewspaperUserPrintData[21]) %>] </span> <%}} %>
			</main >
		</div>


	</div>

</body>
</html>
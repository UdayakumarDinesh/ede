<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java"%>

<!DOCTYPE html>
<html>
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
	width: 620px;
	margin-top: 25px;
	text-align: justify;
}

.decDiv {
	display: flex;
	justify-content: space-between;
	font-weight: bold;
	margin-top: 60px;
}

.note {
	width: 620px;
	margin-top: 30px;
}

.table3 tr, th, td {
	border: 1px solid black;
	padding: 0px;
}
.table3 tr{
	height: 10px;
}

.table3 td {
	font-weight: normal;
}
.accDPT{
	margin-top: 30px;
}
.table4 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}
.table4 td {
	font-weight: normal;
}

.table4 td:first-child:not(.exlude){
	width: 45%;
}
.sign{
	width: 620px;
	text-align: right;
	margin-top: 60px;
}
</style>

<title>Newspaper Claim Print</title>
</head>
<body>


	<%
	String name = (String) request.getAttribute("name");
	String empNo = (String) request.getAttribute("empNo");
	String designation = (String) request.getAttribute("desig");
	Object[] NewspaperUserPrintData = (Object[]) request.getAttribute("NewspaperUserPrintData");
	LabMaster LabDetails = (LabMaster) request.getAttribute("LabDetails");
	String PayableRupee = "Not Available";
	String AdmissibleRupee = "Not Available";
	String ClaimRupee = "Not Available";

	/* String NewsClaimHeader =(String)request.getAttribute("NewsClaimHeader"); */

	if (NewspaperUserPrintData != null) {
		String ClaimAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[2].toString());
		ClaimRupee = IndianRupeeFormat.rupeeFormat(ClaimAmountRsPaisA[0]);

		String PayableAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[4].toString());
		PayableRupee = IndianRupeeFormat.rupeeFormat(PayableAmountRsPaisA[0]);

		String AdmissibleAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[3].toString());
		AdmissibleRupee = IndianRupeeFormat.rupeeFormat(AdmissibleAmountRsPaisA[0]);

	}
	
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
	Date today=new Date();
	
	%>


	<div align="center">
		<table height="110">
			<tr>
				<td class="text-center" rowspan="2"><span
					style="font-size: 30px">STARC</span> <br>BANGALORE</td>
				<td class="text-center" width="300px" rowspan="2">CLAIM FORM
					FOR <br>REIMBURSEMENT <br> OF RESIDENTIAL NEWSPAPERS TO <br>
					EXECUTIVES
				</td>
				<td>SITAR-BNG <br> P&A-057
				</td>
				<td>Rev.: 04</td>
			</tr>
			<tr>
				<td>Date of Issue: <br><%=dateFormat.format(today) %>
				</td>
				<td>Total<br> Pages-1
				</td>
			</tr>
		</table>

		<div style="margin-top: 50px">
			<table class="table2">
				<tr>
					<td>1.</td>
					<td>Name of the Employee</td>
					<td colspan="2"><%=name%></td>
				</tr>
				<tr>
					<td>2.</td>
					<td>Emp. No.</td>
					<td colspan="2"><%=empNo%></td>
				</tr>
				<tr>
					<td>3.</td>
					<td>Designation</td>
					<td colspan="2"><%=designation%></td>
				</tr>
				<tr>
					<td>4.</td>
					<td>Grade & Level in the Pay Matrix</td>
					<td><%if(NewspaperUserPrintData!=null){%><%=NewspaperUserPrintData[6]%><%}%></td>
					<td><%if(NewspaperUserPrintData!=null){%><%=NewspaperUserPrintData[5]%><%}%></td>
				</tr>
				<tr>
					<td height="40px">5.</td>
					<td>Bi-annual & Year of claim</td>
					<td class="exclude" colspan="2"><% if(NewspaperUserPrintData[0].toString().equalsIgnoreCase("JAN-JUN")){%> January-June <%=NewspaperUserPrintData[1]%> <%} if(NewspaperUserPrintData[0].toString().equalsIgnoreCase("JUL-DEC")){%>July - December <%=NewspaperUserPrintData[1]%> <%} %></td>
					<%-- <td class="exclude"><% if(NewspaperUserPrintData[0].toString().equalsIgnoreCase("JUL-DEC")){%>  <%=NewspaperUserPrintData[1]%> <%} else{%>-<%} %></td> --%>
				</tr>
				<tr>
					<td>6.</td>
					<td>Amount of Reimbursement @ ` 500/- p.m.</td>
					<% String amt=AdmissibleRupee;
						amt=amt.replace(",", "");
					if(Integer.parseInt(amt)<=3000){ %>
					<td colspan="2">Rs <%=AdmissibleRupee%> /-</td>
					<%}else {%>
					<td colspan="2">Rs 3000 /-</td>
					<%} %>
				</tr>
			</table>
		</div>

		<div class="declare">
			I hereby certify that the (i) The newspaper (s) in respect of which
			reimbursement is claimed, is/are purchased by me. (ii) The
			subscription amount incurred by me is not less than the amount
			claimed towards subscription of newspaper / s for the period. (iii)
			The amount for which the reimbursement is being claimed has actually
			been paid by me and has not/will not be claimed by any other source.
			<div class="decDiv">
				<div class="decDiv1">Date:</div>

				<div style="margin-right: 80px;">
					Signature of Employee <br> STARC Intercom No:
				</div>
			</div>
		</div>
		<div class="note">
			<div align="left">Note:</div>
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
			<div style="text-decoration: underline;font-weight: bold; margin-bottom: 20px;" >FOR ACCOUNTS DEPARTMENT USE</div>
			<table class="table4">
				<tr>
					<td>Claimed Amount</td>
					<td><b>Rs <%=AdmissibleRupee%> /-</b></td>
				</tr>
				<tr>
					<td>Eligible Amount</td>
					<td><b>Rs <%=PayableRupee%> /-</b></td>
				</tr>
				<tr>
					<td>Amount passed for payment </td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="sign"><b style="margin-right: 50px;">F&A Dept. / FO </b></div>

		<input type="button"  class="btn btn-sm print-btn"  id="printPageButton" value="Print" onClick="window.print()">
	</div>


</body>
</html>
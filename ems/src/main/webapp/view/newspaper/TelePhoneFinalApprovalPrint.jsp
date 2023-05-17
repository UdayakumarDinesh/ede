<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Telephone Final Approval</title>
<style type="text/css">
.break {
	page-break-after: always;
}

@page {
	size: 790px 950px;
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
	content: "STARC/F&A/NEWSPAPER/2022-23/";
	margin-top: 30px;
	margin-right: 10px;
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
	margin-left:10px;
	border-collapse:collapse;
}

tr, th, td {
	border: 1.5px solid black;
	padding: 4px;
}
</style>
</head>
<body>
	<%
	List<Object[]> TelePhoneFinalAppro=(List<Object[]>) request.getAttribute("TelePhoneFinalAppro");
	String LabLogo = (String) request.getAttribute("LabLogo");
	SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();
	IndianRupeeFormat nfc=new IndianRupeeFormat();

	SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat rdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	%>
	<div class="center"">
		<div style="width: 100%; float: left;">
			<div
				style="width: 20%; margin-left: auto; margin-right: auto; border: 0;">
				<img style="width: 80px; height: 90px; margin: 5px;" align="left"
					src="data:image/png;base64,<%=LabLogo%>">
			</div>
			<div style="margin-left: auto; margin-right: auto; font-family:  Arial, Helvetica, sans-serif">
				<h3>
					<i style="font-size: 22px;"> SITAR </i>
				</h3>
			</div>
			<div style="margin-left: auto; margin-right: auto;">
				<p style="margin: 0; display: inline; float: left; font-family:  Arial, Helvetica, sans-serif">

					<b>Ref:&nbsp; STARC/F&A/TELEPHONE/2022-23/<%if(TelePhoneFinalAppro != null && TelePhoneFinalAppro.size()>0 ) {%> <%=TelePhoneFinalAppro.get(0)[0].toString()%>'<%= TelePhoneFinalAppro.get(0)[1].toString().substring(2,4)%> </b>
				</p>
				<p style="margin: 0; display: inline; float: right;">
					Dt:&nbsp;<%=rdf.format(date) %>
				</p>
			</div>
			<div style="margin-left:25px; margin-right: auto; margin-top: 80px; ">
				<p>
					The claims for reimbursement of Residential Telephone expenses for Executive received from the following employees for the month of <b>
					<%if(TelePhoneFinalAppro != null ) { String month=TelePhoneFinalAppro.get(0)[0].toString();%> 
					<% if(month.equalsIgnoreCase("JAN")) {%> <b>JANUARY 
					<%} else if(month.equalsIgnoreCase("FEB")) {%> FEBRUARY 
					<%} else if(month.equalsIgnoreCase("MAR")) {%> MARCH 
					<%} else if(month.equalsIgnoreCase("APR")) {%> APRIL 
					<%} else if(month.equalsIgnoreCase("MAY")) {%> MAY 
					<%} else if(month.equalsIgnoreCase("JUN")) {%> JUNE 
					<%} else if(month.equalsIgnoreCase("JUL")) {%> JULY 
					<%} else if(month.equalsIgnoreCase("AUG")) {%> AUGUST 
					<%} else if(month.equalsIgnoreCase("SEP")) {%> SEPTEMBER 
					<%} else if(month.equalsIgnoreCase("OCT")) {%> OCTOBER 
					<%} else if(month.equalsIgnoreCase("NOV")) {%> NOVEMBER 
					<%} else if(month.equalsIgnoreCase("DEC")) {%> DECEMBER 
					<%}%> &nbsp;-&nbsp; <%=TelePhoneFinalAppro.get(0)[1].toString()%> </b> <%}%></b>have been processed and admitted as under:
				</p>
			</div>
		</div>
		<%int i=0;
		long totalClaimAmount=0; 
		long totalAdmittedAm=0;%>
		<table align="center">
			<thead>
				<tr>
					<th>Sl No</th>
					<th>Emp No</th>
					<th>Name</th>
					<th>Grade</th>
					<th>Month of Claim</th>
					<th>Amount Eligible (Rs.)</th>
					<th>Amount Claimed (Rs.)</th>
					<th>Amount Admitted (Rs.)</th>
				</tr>
			</thead>
			<tbody>
			<% if(TelePhoneFinalAppro != null && TelePhoneFinalAppro.size()>0) {%>
			<% for(Object[] obj : TelePhoneFinalAppro) {%>
			<tr>
				<%totalClaimAmount +=Math.round(Double.parseDouble(obj[2].toString()));
				totalAdmittedAm +=Math.round(Double.parseDouble(obj[4].toString()));%>
				<td style="text-align: center; width: 8%"><%=++i %></td>
				<td style="text-align: center; width: 10%"><%= obj[8]%></td>
				<td style="text-align: left; width: 30%"><%= obj[7]%></td>
				<td style="text-align: center;"><%= obj[6]%></td>
				<td style="text-align: center;"><%= obj[0]%>-<%= obj[1].toString().substring(obj[1].toString().length()-2,obj[1].toString().length())%></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(obj[3].toString().substring(0,obj[3].toString().length()-3))%></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(obj[2].toString().substring(0,obj[2].toString().length()-3))%></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(obj[4].toString().substring(0,obj[4].toString().length()-3))%></td>
				
			</tr>
			<%}} %>
			<tr>
				<td style="text-align: center;" colspan="6"><b>Total</b></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(Long.toString(totalClaimAmount)) %></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(Long.toString(totalAdmittedAm))  %></td>
			</tr>
			</tbody>
		</table>
			
		<div style="margin-left: 15px; margin-top: 30px;">
			<p>The claims are accordingly admitted for the above employees, it is requested to approve the reimbursement of residential telephone expenses.</p>
			<p style="margin-top: 25px;">Put up for approval.</p>
			<p style="margin: 0; display: inline; float: left; margin-top: 40px;">Srikanth KR <br>	
               Executive Officer-Fin</p>
			<p style="display: inline; float: right; margin-top: 40px; margin-right: 25px;">Kavya Jayaraman <br>
			Senior Finance Officer</p>
			<p style="margin-top: 130px;">Dobbidi Ashok Kumar <br>
			Head-Budget, Finance & Project Accounting</p>
			<p style="text-align:center; margin-top: 30px;"><b>Sanctioned / Not sanctioned</b></p>
			<p style="text-align:center; margin-top: 60px;"><b>CEO</b></p>
		</div>
		<%} else {%> <h1 style="color: red; margin-top: 100px; text-align: center">No Data</h1> <%} %>
	</div>

</body>
</html>
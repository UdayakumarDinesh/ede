<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Date,java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%
	String Bill=(String) request.getAttribute("Bill");
	%>
<% if(!Bill.equalsIgnoreCase("YES")) {%>	
<title>Newspaper Final Approval</title>
<%} else { %>
<title>Newspaper Contingent Bill</title>
<%}%>
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
	List<Object[]> newsPaperFinalAppro=(List<Object[]>) request.getAttribute("newsPaperFinalAppro");
	List<Object[]> ContingentBillTrans=(List<Object[]>) request.getAttribute("ContingentBillTrans");
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

					<b>Ref:&nbsp; STARC/F&A/NEWSPAPER/2022-23/ </b>
				</p>
				<p style="margin: 0; display: inline; float: right;">
					Dt:&nbsp;<%=rdf.format(date) %>
				</p>
			</div>
			<%if(newsPaperFinalAppro != null && newsPaperFinalAppro.size()>0) {%>
			<div style="margin-left:25px; margin-right: auto; margin-top: 80px; ">
				<p>
					The residential newspaper claims for reimbursement received from the following employees for the period <b>
					<%if(newsPaperFinalAppro != null && newsPaperFinalAppro.get(0)[0].toString().equalsIgnoreCase("JAN-JUN")) {%>JANUARY to JUNE <%}else{  %>JULY to DECEMBER<%}  %> <%if(newsPaperFinalAppro != null){ %><%=newsPaperFinalAppro.get(0)[1] %> <%} %> </b>have been processed and admitted as under:
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
					<th>Amount Claimed (Rs.)</th>
					<th>Amount Admitted (Rs.)</th>
				</tr>
			</thead>
			<tbody>
			<% if(newsPaperFinalAppro != null && newsPaperFinalAppro.size()>0) {%>
			<% for(Object[] obj : newsPaperFinalAppro) {%>
			<tr>
				<%totalClaimAmount +=Math.round(Double.parseDouble(obj[2].toString()));
				totalAdmittedAm +=Math.round(Double.parseDouble(obj[4].toString()));%>
				<td style="text-align: center; width: 10%"><%=++i %></td>
				<td style="text-align: center; width: 15%"><%= obj[8]%></td>
				<td style="text-align: left; width: 30%"><%= obj[7]%></td>
				<td style="text-align: center;"><%= obj[6]%></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(obj[2].toString().substring(0,obj[2].toString().length()-3))%></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(obj[4].toString().substring(0,obj[4].toString().length()-3))%></td>
				
			</tr>
			<%}} %>
			<tr>
				<td style="text-align: center;" colspan="4"><b>Total</b></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(Long.toString(totalClaimAmount)) %></td>
				<td style="text-align: right;"><%= nfc.rupeeFormat(Long.toString(totalAdmittedAm))  %></td>
			</tr>
			</tbody>
		</table>
			
		<div style="margin-left: 15px; margin-top: 30px;">
			<p>The claims are accordingly admitted for the above employees and it is requested to condone for those employees who had submitted beyond due date and approve 
			the reimbursement of residential Newspaper claims.</p>
			<p style="margin-top: 25px;">Put up for approval please</p>
			<p style="margin: 0; display: inline; float: left; margin-top: 40px;"> <% if(ContingentBillTrans !=null && ContingentBillTrans.size()>1 ) {%><%=ContingentBillTrans.get(1)[2]%>,<br><%=ContingentBillTrans.get(1)[3]%>,<br>	
              <%=rdtf.format(ContingentBillTrans.get(1)[4])%> <%} %></p>
			<p style="display: inline; float: right; margin-top: 40px; margin-right: 25px;"><% if(ContingentBillTrans !=null && ContingentBillTrans.size()>2 ) {%><%=ContingentBillTrans.get(2)[2]%> ,<br><%=ContingentBillTrans.get(2)[3]%>,<br>
			<%=rdtf.format(ContingentBillTrans.get(2)[4].toString()) %> <%} %></p>
			<p style="margin-top: 130px;"><% if(ContingentBillTrans !=null && ContingentBillTrans.size()>3 ) {%><%=ContingentBillTrans.get(3)[2]%>, <br><%=ContingentBillTrans.get(3)[3]%>,<br>
			<%=rdtf.format(ContingentBillTrans.get(3)[4].toString()) %> <%} %></p>
			<p style="text-align:center; margin-top: 30px;"><b>Sanctioned / Not sanctioned	 </b></p>
			<p style="text-align:center; margin-top: 60px;"><b><% if(ContingentBillTrans !=null && ContingentBillTrans.size()>3 ) {%><%=ContingentBillTrans.get(3)[2]%> <%} %></b></p>
			<p style="text-align:center; margin-top: 60px;"><b>CEO</b></p>
		</div>
		<%} else { %> <h1 style="color: red; margin-top: 100px; text-align: center">No Data</h1> <%} %>
	</div>

</body>
</html>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>

<!DOCTYPE html>
<html>
<%
Object[] oneVehicle = (Object[]) request.getAttribute("oneVehicle");

SimpleDateFormat time = new SimpleDateFormat("HH:mm");
%>
<head>
<style type="text/css">
.break {
	page-break-after: always;
}

#pageborder {
	position: fixed;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
	border: 2px solid black;
}

@page {
	size: 790px 950px;
	margin-top: 49px;
	margin-left: 72px;
	margin-right: 39px;
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
	content: "";
}

@
top-center {
	margin-top: 30px;
	content: "";
}

@
bottom-center {
	margin-bottom: 30px;
	content: "";
}

}
p {
	text-align: justify;
	text-justify: inter-word;
}

body {
	font-size: 14px !important;
}

div {
	width: 650px !important;
}

table {
	align: left;
	width: 650px !important;
	max-width: 650px !important;
	margin-top: 10px;
	margin-bottom: 10px;
	margin-left: 10px;
	border-collapse: collapse;
}

th, td {
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
	-ms-word-break: break-all;
	word-break: break-all;
	/* Non standard for WebKit */
	word-break: break-word;
	-webkit-hyphens: auto;
	-moz-hyphens: auto;
	hyphens: auto;
}

.center {
	text-align: center;
}

.right {
	text-align: right;
}

input {
	border-width: 0 0 1px 0;
	width: 260px;
}

input:focus {
	outline: none;
}
</style>
<meta charset="ISO-8859-1">
<title>Vehicle Parking Application Download</title>
</head>
<body>
	<%
	String LabLogo = (String) request.getAttribute("LabLogo");
	String LoginType = (String) session.getAttribute("LoginType");
	SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();

	SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat rdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	%>
	<div class="center">
		<div style="width: 100%; float: left;">
			<div
				style="width: 20%; margin-left: auto; margin-right: auto; border: 0;">
				<img style="width: 80px; height: 90px; margin: 5px;" align="left"
					src="data:image/png;base64,<%=LabLogo%>">
			</div>
			<div style="margin-left: auto; margin-right: auto;">
				<h3>
					<span style="margin-left: -85px;"> FORM FOR VEHICLE PARKING REQUEST</span>
				</h3>
			</div>
		</div>
		<br> <br>
		<table
			style="margin-top: 140px; border-collapse: collapse; width: 100%;">
			<tbody>
				<tr>
					<td style="border: 0; width: 86%; text-decoration: underline;"><b>To</b>
					</td>
				</tr>
				<tr>
					<td style="border: 0;">The CEO</td>
				</tr>
				<tr>
					<td style="border: 0;">STARC</td>
				</tr>
				<tr>
					<td style="border: 0;"></td>
				</tr>
				<tr>
					<td style="border: 0;"></td>
				</tr>
				<tr>
					<td style="border: 0;"></td>
				</tr>
				<tr>
					<td style="border: 0;"></td>
				</tr>
				<tr>
					<td
						style="border: 0; margin-left: 10px; text-align: justify; text-justify: inter-word; font-size: 14px;"
						align="left"><B>Through</B>
						&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;Security Officer</td>
				</tr>
				<tr>
					<td style="border: 0;"></td>
				</tr>
				<tr>
					<td style="border: 0;"></td>
				</tr>


				<tr>
					<td style="border: 0;"><span style="margin-left: 60px;">I
							would like to request to give permission to park my vehicle in
							STARC.</span></td>
				</tr>
				<tr>
					<td style="border: 0;">Vehicle No&nbsp;&nbsp;&nbsp; <label
						style="margin-left: 45px;">:&nbsp;&nbsp;&nbsp;</label><input
						type="text"
						value="<%if (oneVehicle != null && oneVehicle[3] != null) {%> <%=oneVehicle[3]%> <%}%>"
						readonly></td>
				</tr>
				<tr>
					<td style="border: 0;"><label>From Date & Time
							&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</label><input type="text"
						value="<%if (oneVehicle != null && oneVehicle[5] != null) {%> <%=DateTimeFormatUtil.getDateTimeToRegularDate(oneVehicle[4].toString())%>&nbsp;&nbsp;&nbsp;<%=time.format(oneVehicle[4])%> <%}%>"
						readonly></td>
				</tr>
				<tr>
					<td style="border: 0;">To Date & Time <label
						style="margin-left: 25px;">:&nbsp;&nbsp;&nbsp;</label><input
						type="text"
						value="<%if (oneVehicle != null && oneVehicle[5] != null) {%><%=DateTimeFormatUtil.getDateTimeToRegularDate(oneVehicle[4].toString())%>&nbsp;&nbsp;&nbsp;<%=time.format(oneVehicle[5])%> <%}%>"
						readonly></td>
				</tr>

			</tbody>
		</table>
		<br>
		<div
			style="text-align: right; margin-right: 100px; margin-bottom: 40px;">
			Sincerely, <br>
		</div>
		<div style="width: 100%; text-align: right; margin-right: 100px;">
			Signature of Employee<br>
		</div>
		<div style="width: 100%; text-align: right; margin-right: 150px;">
			<%if (oneVehicle != null && oneVehicle[2] != null) {%>
			<%=oneVehicle[2]%>, <br>
			<%}%>
		</div>
		<div style="width: 100%; text-align: right; margin-right: 150px;">
			<%if (oneVehicle != null && oneVehicle[7] != null) {%>
			<%=oneVehicle[7]%>
			<%}%>
		</div>
		
		
		<br>
	</div>
</body>

</html>


<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.lang.Math"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Re-imbursement bill</title>
<style type="text/css">
 @media print {
  #printPageButton {
    display: none;
  }
}


table {
	border-collapse:collapse;
	border: 1px solid black;
	width: 620px;
	font-weight: bold;
	font-size: 14px;
}
table tr, th, td{
	border: 1px solid black;
	padding: 5px;
}
.table1 {
	border: 2px solid black;
	height: 100px;
	text-align: center;
}

</style> 
</head>
<body>

	<div align="center">
		<div>
			<table class="table1">
				<tr>
					<td rowspan="2"><span style="font-size: 28px;">STARC</span> <br> BANGALORE</td>
					<td rowspan="2">CLAIM FORM FOR REIMBURSEMENT OF<br>RESIDENTIAL TELEPHONE EXPENSES FOR<br>EXECUTIVES (MOBILE & / OR LANDLINE)</td>
					<td>STARC-BNG- <br> P&A-028</td>
					<td>Rev.: 00</td>
				</tr>
				<tr>
					<td>Date of Issue:<br>18.04.2018</td>
					<td>Total Pages-1</td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>
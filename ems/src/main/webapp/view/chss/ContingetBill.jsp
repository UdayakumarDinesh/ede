<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*" %>
	<!DOCTYPE html>
<html>
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
				size: 790px 1120px;
				margin-top: 49px;
				margin-left: 72px;
				margin-right: 39px;
				margin-buttom: 49px;
				border: 2px solid black;

				@bottom-right {
					content: "Page "counter(page) " of "counter(pages);
					margin-bottom: 30px;
					margin-right: 10px;
				}

				@top-right {
					content: "";
					margin-top: 30px;
					margin-right: 10px;
				}

				@top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "";
				}

				@top-center {
					margin-top: 30px;
					content: "";

				}

				@bottom-center {
					margin-bottom: 30px;
					content: "";
				}

			}

p {
	text-align: justify;
	text-justify: inter-word;
}
body
{
	font-size:14px !important;
}
div, table{
	width: 650px !important;
}

table{
	align: left;
	width: 650px !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	margin-left:10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
}

.center{

	text-align: center;
}

.right
{
	text-align: right;
}
			
			
</style>
		<meta charset="ISO-8859-1">
		<title>Bill</title>
	</head>

<body>

	
<%
	
	List<Object[]> ContingentList = (List<Object[]>)request.getAttribute("ContingentList");
	Object[]  contingentdata = (Object[])request.getAttribute("contingentdata");
%>

<div align="center">
	<%=ContingentList.size() %>
	<table>
		<tr>
			<th style="text-align: center;" >SN</th>
			<th>ClaimNo</th>
			<th>Employee</th>
			<th>Patient</th>
			<th class="right" style="width: 15%;">Amount Claimed (&#8377;)</th>
			<th class="right" style="width: 15%;">Reimbursable under CHSS  (&#8377;)</th>
		</tr>
		
		<%
		int i=0;
		for(Object[] obj: ContingentList){
			i++; %>
			<tr>
				<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%=i %></td>
				<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
				<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
				<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[12] %></td>
				<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[25] %></td>
				<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[26] %></td>
											
			</tr>
		<%} %>
	</table>
</div>
</body>

</html>
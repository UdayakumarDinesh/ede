<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.DateTimeFormatUtil"%>
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
				border: 1px solid black;

				@bottom-right {
					content: "Page "counter(page) " of "counter(pages);
					margin-bottom: 30px;
					margin-right: 10px;
				}

				@top-right {
					content: "Project:";
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

div, table{
	width: 650px !important;
}

table{
	align: left;
	width: 650px !important;
	margin-top: 10px; 
	margin-bottom: 10px;
	border-collapse:collapse;
	
}
th,td
{
	text-align: left;
	border: 1px solid black;
	padding: 4px;
}

.sn{

	text-align: center;
}
			
			
</style>
		<meta charset="ISO-8859-1">
		<title>Form</title>
	</head>

<body>

	
<%
	
	List<Object[]> chssbillslist = (List<Object[]>)request.getAttribute("chssbillslist");
	List<Object[]> ConsultDataList = (List<Object[]>)request.getAttribute("ConsultDataList");
	List<Object[]> TestsDataList = (List<Object[]>)request.getAttribute("TestsDataList");
	List<Object[]> MedicineDataList = (List<Object[]>)request.getAttribute("MedicineDataList");
	List<Object[]> OtherDataList = (List<Object[]>)request.getAttribute("OtherDataList");
	List<Object[]> MiscDataList = (List<Object[]>)request.getAttribute("MiscDataList");
	
	Object[] chssapplydata = (Object[])request.getAttribute("chssapplydata");
	Employee employee = (Employee)request.getAttribute("employee");
	
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();

%>
	<div align="center">
		<div align="center" >
			<div>
				<h4>MEDICAL CLAIM</h4>
				<div align="right"> No.of ENCL : &nbsp;<%=chssapplydata[8] %></div>
			</div>
		
			<table style="margin-left:10px;" >	
				<tbody>
					<tr>
						<th>Name:</th>
						<th>Emp No</th>
						<th>Grade</th>
					</tr>
					<tr>
						<td><%=employee.getEmpName() %></td>
						<td><%=employee.getEmpNo() %></td>
						<td><%=employee.getPayLevelId() %></td>
					</tr>
				</tbody>
			</table>
			<table style="margin-left:10px;" >	
				<tbody>
					<tr>
						<th>SN</th>
						<th>Patient Name</th>
						<th>Relation</th>
					</tr>
					<tr>
						<td>1</td>
						<td><%=chssapplydata[12] %></td>
						<td><%=chssapplydata[14] %></td>
					</tr>
				</tbody>
			</table>
			<table style="margin-left:10px;" >	
				<tbody>
					<tr>
						<th>Basic Pay :</th>
						<th colspan="2">Level in The Pay Matrix :</th>
						<th colspan="2">Ph.No. : </th>
					</tr>
					<tr>
						<th class="sn">SN</th>
						<th>Hospital / Medical / Diagnostics Centre Name</th>
						<th>Bill / Receipt No.</th>
						<th>Date</th>
						<th style="text-align: right;">Amount (Rs.)</th>
					</tr>
					<%for(int i=0;i<chssbillslist.size();i++){ %>
						<tr>
							<td class="sn"><%=i+1 %></td>
							<td><%=chssbillslist.get(i)[3] %></td>
							<td><%=chssbillslist.get(i)[2] %></td>
							<td><%=rdf.format(sdf.parse(chssbillslist.get(i)[4].toString())) %></td>
							<td style="text-align: right;"><%=chssbillslist.get(i)[5] %>&nbsp;(&#8377;)</td>
						</tr>
					<%} %>
				</tbody>
			</table>
		</div>
		
	</div>

</body>

</html>
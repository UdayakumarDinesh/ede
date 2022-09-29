<%@page import="java.time.LocalDate"%>
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
	<% Object[]  contingentdata = (Object[])request.getAttribute("contingentdata"); %>
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
					content: "<%=contingentdata[1]%>";
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
	
	HashMap<Long, ArrayList<Object[]>> ContingentList = (HashMap<Long, ArrayList<Object[]>>)request.getAttribute("ContingentList");
	String LabLogo = (String)request.getAttribute("LabLogo");
	Object[] labdata = (Object[])request.getAttribute("labdata");
	if(contingentdata[2]==null){
		contingentdata[2] = LocalDate.now().toString();
	}
	IndianRupeeFormat nfc=new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	
%>

<div align="center" style="margin-top: -15px" >
	<table style="margin-bottom: 10px;margin-top: 0px">
		<tr>
			<td style="width: 80px; height: 75px;border: 0;margin-bottom: 10px;"><img style="width: 80px; height: 90px;" align="left"   src="data:image/png;base64,<%=LabLogo%>"></td>
			<td style="font-size: 15px; font-weight:600;vertical-align: bottom;border: 0"> </td>
		</tr>
	</table>
	<table style="margin-bottom: 10px;margin-top: -65px;">
		<tr>
			<td style="width: 80px; height: 75px;border: 0;margin-bottom: 10px;"></td>
			<td style="font-size: 15px; font-weight:600;vertical-align: bottom;border: 0;"><%=labdata[0] %> <br><br> Ref: <%=contingentdata[1] %>  </td>
			<td style="font-size: 15px; vertical-align: bottom;border: 0;text-align: right;"><b style="font-weight:300;margin-right: 10px;">Dt.&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[2].toString()) %>
			<%if(contingentdata[9]!=null){ %>
									<br>	Approved On:<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[9].toString())%>
									<% } %></b>  </td>
		</tr>
	</table>
	<br>
	<div style="text-align: left ;margin: 0px 5px 0px 10px;">
		<p>
			The medical claims received up to <%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[10].toString()) %> during the month of 
								<%=" "+LocalDate.parse(contingentdata[10].toString()).getMonth() %> - <%=" "+LocalDate.parse(contingentdata[10].toString()).getYear() %> for reimbursement from the following
								employees have been processed and admitted at CHSS rates.
		</p>
	</div>
	
	
	<table>
		<tr>
			<th style="text-align: center;width: 10%" >SN</th>
			<th style="text-align: center;width: 15%;">Emp. No.</th>
			<th style="text-align: center;width: 30%;">Name</th>
			<th style="text-align: center;width: 25%;">Bank Account No.</th>
			<th class="right" style="width: 20%;">Bank Transfer (&#8377;)</th>
		</tr>
		
		<%long allowedamt=0;
		int i=0;
		for (Map.Entry mapEle : ContingentList.entrySet()) 
		{ 
			i++;
			int k=0;
			ArrayList<Object[]> arrlist = (ArrayList<Object[]>)mapEle.getValue();%>
			<tr>
				<td style="text-align: center;" ><%=i %></td>
				<td style="text-align: center;width: 15%;"><%=arrlist.get(0)[24] %></td>
				
				<td style="width:30%;"><%=arrlist.get(0)[22] %></td>
				<td style="text-align: center;width: 25%;"><%=arrlist.get(0)[26] %></td>
				<% long empallowedamount = 0;
		          	for(Object[] obj :arrlist )
			        {
		          		empallowedamount += Math.round( Double.parseDouble(obj[2].toString()));
			        } 
		          	allowedamt +=empallowedamount;
			    %>
				<td class="right" style="width: 20%;"><%=nfc.rupeeFormat(String.valueOf( empallowedamount)) %></td>
			</tr>
		<%}%>
	
			<tr>
				<td colspan="4" class="right"><b>Total</b></td>
				<td class="right"><b><%=nfc.rupeeFormat(String.valueOf(allowedamt)) %></b></td>
			</tr>
			<tr>
				<td colspan="5">In words Rupees <b>"<%=awc.convert1(allowedamt) %>"</b> Only</td>
			</tr>
	</table>
	
	</div>
	

</body>

</html>
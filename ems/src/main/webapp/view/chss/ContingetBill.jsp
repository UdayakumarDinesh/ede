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
	
	HashMap<Long, ArrayList<Object[]>> ContingentList = (HashMap<Long, ArrayList<Object[]>>)request.getAttribute("ContingentList");
	Object[]  contingentdata = (Object[])request.getAttribute("contingentdata");
	

	IndianRupeeFormat nfc=new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	
%>

<div align="center">
	
	<div style="text-align: left;margin: 5px 5px 5px 10px;">
		<span style="font-size: 20px; font-weight:600; ">SITAR</span><br>
		<span style="font-size: 15px; font-weight:600; ">Ref: <%=contingentdata[1] %></span><br>
		<p>
			The medical claim recieved upto <%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[8].toString()) %> during the month of 
			<%=" "+LocalDate.parse(contingentdata[8].toString()).getMonth() %> - <%=" "+LocalDate.parse(contingentdata[8].toString()).getYear() %> for reimbrusement from the following
			employees have been processed and admitted at CHSS rates.
		</p>
	
	</div>
	
	<table>
		<tr>
			<th style="text-align: center;" >SN</th>
			<th style="text-align: center;">Emp. No.</th>
			<th style="text-align: center;">Name</th>
			<th style="text-align: center;">Relation</th>
			<th style="text-align: center;">No. of Bills</th>
			<th class="right" style="width: 15%;">Amount Claimed (&#8377;)</th>
			<th class="right" style="width: 15%;">Amount Allowed (&#8377;)</th>
		</tr>
		
		<%long allowedamt=0,claimamt=0,billscount=0;
		int i=0;
		for (Map.Entry mapEle : ContingentList.entrySet()) 
		{
			int k=0;
			ArrayList<Object[]> arrlist = (ArrayList<Object[]>)mapEle.getValue();
          	for(Object[] obj :arrlist )
          	{
				i++; %>
			<tr>
				
				<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%=i %></td>
				<%if(k==0){ %>
					<td rowspan="<%=arrlist.size() %>" style="padding-top:5px; padding-bottom: 5px;"><%=obj[21] %></td>
				
					<td rowspan="<%=arrlist.size() %>"  style="padding-top:5px; padding-bottom: 5px;"><%=obj[19] %></td>
				<%} %>
				<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[14] %></td>
				<td class="center" style="padding-top:5px; padding-bottom: 5px;"><%=obj[22] %></td>
				<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[27] %></td>
				<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=obj[28] %></td>
											
			</tr>
		<%	k++;
			claimamt += Integer.parseInt(obj[27].toString());
			allowedamt +=Integer.parseInt(obj[28].toString());
			billscount += Integer.parseInt(obj[22].toString());
			} 
		}%>
		
			<tr>
				<td colspan="4" class="right">Total</td>
				<td class="center"><%=billscount %></td>
				<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(claimamt)) %></td>
				<td class="right">
					
					&#8377; <%=nfc.rupeeFormat(String.valueOf(allowedamt)) %>
												
				</td>	
			</tr>
	</table>
</div>
</body>

</html>
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
	/* Object[]  contingentdata = (Object[])request.getAttribute("contingentdata"); */
	List<Object[]> ApprovalAuth = (List<Object[]>)request.getAttribute("ApprovalAuth");
	String LabLogo = (String)request.getAttribute("LabLogo");
	Object[] labdata = (Object[])request.getAttribute("labdata");
	List<Object[]> contingentremarks = (List<Object[]>)request.getAttribute("contingentremarks");
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
			<td style="font-size: 15px; vertical-align: bottom;border: 0;text-align: right;">
			<b style="font-weight:300;margin-right: 10px;">Dt.&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[2].toString()) %></b>
			<%if(contingentdata[9]!=null){ %>
									<br>	Approved On :&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[9].toString())%>
									<% } %> 
			 </td>
		</tr>
	</table>
	<br>
	<div style="text-align: left ;margin: 0px 5px 0px 10px;">
		<p>
			The medical claim received up to <%=DateTimeFormatUtil.SqlToRegularDate(LocalDate.parse(contingentdata[2].toString()).withDayOfMonth(20).toString()) %> during the month of 
			<%=" "+LocalDate.parse(contingentdata[2].toString()).getMonth() %> - <%=" "+LocalDate.now().getYear() %> for reimbursement from the following
			employees have been processed and admitted at CHSS rates.
		</p>
	</div>
	
	
	<table>
		<tr>
			<th style="text-align: center;" >SN</th>
			<th style="text-align: center;width: 10%;">Emp. No.</th>
			<th style="text-align: center;">Name</th>
			<th style="text-align: center;">Relation</th>
			<th style="text-align: center;">Claim No</th>
			<th style="text-align: center;width: 10%;">No of Bills</th>
			<th class="right" style="width: 12%;">Amount Claimed (&#8377;)</th>
			<th class="right" style="width: 12%;">Amount Allowed (&#8377;)</th>
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
				<td style="padding-top:5px; padding-bottom: 5px;text-transform: capitalize;">
					<%if(obj[14]!=null && !obj[14].toString().equalsIgnoreCase("Self")){ %>
						<%=obj[12] %> (<%=obj[14] %>)
					<%}else{ %>
						<%=obj[14] %>
					<%} %>
				</td>
				<td class="center" style="padding-top:5px; padding-bottom: 5px;"><%=obj[16] %></td>
				<td class="center" style="padding-top:5px; padding-bottom: 5px;"><%=obj[22] %></td>
				<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[27].toString())) )) %></td>
				<td style="padding-top:5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[28].toString())) )) %></td>
											
			</tr>
		<%	k++;
			claimamt += Math.round(Double.parseDouble(obj[27].toString()));
			allowedamt +=Math.round(Double.parseDouble(obj[28].toString())) ;
			billscount += Integer.parseInt(obj[22].toString());
			} 
		}%>
	
			<tr>
				<td colspan="5" class="right"><b>Total</b></td>
				<td class="center"><%=billscount %></td>
				<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(claimamt)) %></td>
				<td class="right">
					
					&#8377; <%=nfc.rupeeFormat(String.valueOf(allowedamt)) %>
												
				</td>	
			</tr>
	</table>
	<div style="text-align: left;margin: 5px 5px 5px 10px;">
		<p>
			<%=contingentdata[8] %>
		</p>
	</div>
	</div>
		<div style="position: relative;margin-bottom: 0;page-break-inside: avoid !important;  page-break-before: auto !important;">
			<table style="width: 100%;margin: 60px 5px 5px 10px;">
				<tr>
					<td style="border: 0px;">
						<%for(Object[] auth : ApprovalAuth)
						{
							if(auth[1].toString().equalsIgnoreCase("PO")){
						%>
								<%=auth[2] %><br>
								<%=auth[4] %>
							
						<% }} %>
					</td>
					<td style="border: 0px;text-align: right;" >
		
						<%for(Object[] auth : ApprovalAuth)
						{
							if(auth[1].toString().equalsIgnoreCase("VO")){
						%>
								<span style="text-transform: capitalize;"> <%=auth[2] %><br>
								<%=auth[4] %></span>
							
						<% }} %>
			
					</td>
				</tr>
			</table>
		
		<table style="width: 100%;margin: 60px 5px 5px 10px;">
			<tr>
				<td style="border: 0px;">
				<%for(Object[] auth : ApprovalAuth)
				{
					if(auth[1].toString().equalsIgnoreCase("AO")){
				%>
						<span style="text-transform: capitalize;"><%=auth[2] %><br>
						<%=auth[4] %></span>
					
				<% }} %>
				</td>
			</tr>
		</table>
		
		
		<div align="center">
		<br><br><span ><b>Sanctioned / Not Sanctioned</b></span><br><br><br><br>
				<span><b>
					<%for(Object[] auth : ApprovalAuth)
					{
						if(auth[1].toString().equalsIgnoreCase("CEO")){
					%>
							<span style="text-transform: capitalize;"><%=auth[2] %><br>
							CEO, <%=labdata[0] %></span>
						
					<% }} %></b>
				</span>
			
			
		</div>
		
	</div>


</body>

</html>
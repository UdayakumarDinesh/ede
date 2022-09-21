<%@page import="java.math.MathContext"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" import="java.util.*" %>
<!DOCTYPE html>
<html>
<%  //Object[] claimsReportData = (Object[])request.getAttribute("claimsReportData");%>
<head>
<%
List<Object[]> claimsReportData = (List<Object[]>)request.getAttribute("claimsReportData");
String LabLogo = (String)request.getAttribute("LabLogo");
Object[] labdata = (Object[])request.getAttribute("labdata");

String currentDate = (String)request.getAttribute("currentDate"); 

SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yy");
SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
IndianRupeeFormat nfc=new IndianRupeeFormat();
AmountWordConveration awc = new AmountWordConveration();

%>
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
					counter-increment: page;
  					counter-reset: page 2;
					content: "Page "counter(page) " of "counter(pages);
					margin-bottom: 30px;
					margin-right: 10px;
				}


				@top-right {
					content: "Dt : <%=currentDate%>";
					margin-top: 30px;
					margin-right: 10px;
				}

				<%-- @top-left {
					margin-top: 30px;
					margin-left: 10px;
					content: "Emp No: ";
				} --%>

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

div
{
	width: 650px !important;
}
table{
	align: left;
	width: 650px !important;
	max-width: 650px !important;
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
.center{

	text-align: center;
}

.right
{
	text-align: right;
}
			
.text-blue
{
	color: blue;
}

.text-green
{
	color:  #008005;
}

.systemgen
{
	color: #008005;
}

.processed
{
	color: #A300B5;
}

.verified
{
	color: #0CB5ED;
}

 
			
</style>
		<meta charset="ISO-8859-1">
		

</head>
<body>


           <div style="text-align: left;margin: 5px 5px 5px 10px;">
							<img style="width: 80px; height: 90px; margin: 5px;" align="left"   src="data:image/png;base64,<%=LabLogo%>">
			
			  <div style="padding-left: 5px;">
			   <br>				
	           <span style="font-size: 15px; font-weight:600; "><%=labdata[0] %></span>
	           <br>
	           <br>
	           <!-- 	<div align="center"> -->
	                 <span   style="float:left;font-size: 15px; font-weight:600;left:50%;position:relative;justify-content: center;">CLAIMS REPORT</span> 
	            <!-- </div>  -->      

            </div>
	      <br>
	      </div>

	<table style="margin-top: 5px;">	
				<thead>
					 <tr>
						<th>SN</th>
						<th>Claim No</th>
						<th>Type</th>
						<th>Applicant</th>
						<th>Patient </th>
						<th>Applied Date</th>
						<!-- <th>No of Bills</th> -->
						<th>Claimed Amt</th>
						<th>Settled Amt</th>
						</tr>
					</thead>
			 <tbody>
			   <%long slno =0;
			     long totalClaimedAmt = 0;
			     long totalSettledAmt = 0;
			    
			   
				for(Object[] obj: claimsReportData)
				{
				 slno++;
				 totalClaimedAmt += Math.round( Double.parseDouble(obj[1].toString()));
				 totalSettledAmt += Math.round( Double.parseDouble(obj[2].toString()));
				%>
			 	<tr>
			    <td class="text-black"><%=slno %></td>
			    <td class="text-black"><%=obj[20] %></td>
			    <td class="text-black"><%=obj[10] %></td>
			    <td class="text-black"><%=obj[23] %></td>
			    <td class="text-black"><%=obj[16] %>&nbsp;(<%=obj[18] %>)</td>
			    <td class="text-black" style="text-align: center;"><%=rdf.format(sdf.parse(obj[19].toString())) %></td>
			    <!--  <td class="text-black"></td> -->
			    <td class="text-black"  style="text-align: right;"><%= nfc.rupeeFormat(obj[1].toString())%></td>
			    <td class="text-black"  style="text-align: right;"><%= nfc.rupeeFormat(obj[2].toString())%></td>
			    
			   </tr>
			   <%} %>
			</tbody>
			  <tfoot>
                <tr>
                 <td  colspan="6"  style="text-align: right;">Total :</td>
                 
                    <td style="text-align: right;"><%=nfc.rupeeFormat(totalClaimedAmt+"")%></td>
					<td style="text-align: right;"><%=nfc.rupeeFormat(totalSettledAmt+"")%></td>
               </tr>
            </tfoot>
			</table>		
	
	

	</div>

</body>
</html>
<%@page import="java.util.Date"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<title>Bill</title>
<%Object[] contingentdata = (Object[]) request.getAttribute("contingentdata"); %>
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
				margin-left: 53px;
				margin-right: 53px;
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
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

input[type=number] {
	-moz-appearance: textfield;
}

p {
	text-align: justify;
	text-justify: inter-word;
}

table {
	align: left;
	margin-top: 10px;
	margin-bottom: 10px;
	margin-left: 10px;
	border-collapse: collapse;
}

th, td {
	text-align: left;
	border: 1px solid black;
	padding: 4px;
}

.center {
	text-align: center;
}

.right {
	text-align: right;
}

.text-blue {
	color: blue;
}

.text-green {
	color: #4E944F;
}

div, table{
	width: 650px;
}

</style>


</head>
<body>


	<%
	List<Object[]> ContingentList = (List<Object[]>) request.getAttribute("ContingentList");
	List<Object[]> newsPaperRemarksHistory = (List<Object[]>) request.getAttribute("contingentremarks");
	
	String logintype = (String) request.getAttribute("logintype");

	IndianRupeeFormat nfc = new IndianRupeeFormat();
	AmountWordConveration awc = new AmountWordConveration();
	String LabLogo = (String) request.getAttribute("LabLogo");
	String onlyview = (String) request.getAttribute("onlyview");
	String isapproval = (String) request.getAttribute("isapproval");
	String billstatus = null;

	if (contingentdata != null) {
		billstatus = (contingentdata[4].toString());
	}
	

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date today = new Date();
	%>


	<div class="page card dashboard-card">

		<div class="card-body">



			<div class="card">
				<div class="card-body main-card ">
					
				 <div align="center">
						<form action="#" method="post" id="view-form">
							<input type="hidden" name="isapproval" value="Y"> <input
								type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />


							<div style="text-align: left; margin: 5px 5px 5px 10px;">
								<img style="width: 80px; height: 90px; margin: 5px;"
									align="left" src="data:image/png;base64,<%=LabLogo%>"> <span
									style="font-size: 20px; font-weight: 600; margin-top: 60px">SITAR</span>
								<span style="float: right; vertical-align: bottom; text-align: right;">
								<% if(contingentdata !=null && contingentdata[2] != null) { %> Dt.&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[2].toString()) %>
								<%} else if(contingentdata !=null && contingentdata[2] != null){ %>	Dt.&nbsp;<%=dateFormat.format(today)%> <%}%><br>
								<% if(contingentdata !=null && contingentdata[7] != null){%> <span style="margin-top: 10px">  Approved on : Dt.&nbsp;<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[7].toString()) %> <%} %></span>
								</span><br> <span style="font-size: 15px; font-weight: 600;">Ref:
									<%if( contingentdata != null ){%><%=contingentdata[1] %> <%} %> </span>
							</div>
							<div style="margin-top: 80px; margin-left: 10px; font-size: 14px;">
								<p>
									The Newspaper claims received up to
									<%=DateTimeFormatUtil.SqlToRegularDate(contingentdata[8].toString())%>
									during the month of
									<%=" " + LocalDate.parse(contingentdata[8].toString()).getMonth()%>
									-
									<%=" " + LocalDate.parse(contingentdata[8].toString()).getYear()%>
									for reimbursement from the following employees have been
									processed and admitted at Newspaper rates.
								</p>
							</div>
							<table>
								<tr>				
									<th style="text-align: center;">SN</th>
									<th style="text-align: center;">Emp. No.</th>
									<th style="text-align: center;">Name</th>
									<th class="center" style="width: 25%;">Bank Account No.	
										(&#8377;)</th>
									<th class="right" style="width: 20%;">Bank Transfer (&#8377;)
										</th>
								</tr>

								<%
								long allowedamt = 0, claimamt = 0, billscount = 0;
								int i = 0;

								int k = 0;

								for (Object[] obj : ContingentList) {
									i++;
								%>
								<tr>						
									<td
										style="text-align: center; padding-top: 5px; padding-bottom: 5px;"><%=i%></td>

									<td style="padding-top: 5px; padding-bottom: 5px;"><%=obj[7]%></td>
									<td style="padding-top: 5px; padding-bottom: 5px;"><%=obj[8]%></td>

							
									<td
										style="text-align: center; padding-top: 5px; padding-bottom: 5px;"><%=obj[12]%></td>
									<td
										style="padding-top: 5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[13].toString()))))%></td>
	
								</tr>
								<%
								k++;
								claimamt += Math.round(Double.parseDouble(obj[1].toString()));
								allowedamt += Math.round(Double.parseDouble(obj[13].toString()));
								/* 								billscount += Integer.parseInt(obj[25].toString()); */
								}
								%>
								<tr>
									<%
								if ((billstatus.equalsIgnoreCase("CGT") || billstatus.equalsIgnoreCase("RBV") || billstatus.equalsIgnoreCase("RBA")
										|| billstatus.equalsIgnoreCase("RBD")) && logintype.equalsIgnoreCase("K")) {
								%>
									<td colspan="5" class="right">Total</td>
									<%}else { %>
									<td colspan="4" class="right">Total</td>
									<%} %>
									<%-- 	<td class="center"><%=billscount %></td> --%>
									<%-- <td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(claimamt))%></td> --%>
									<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(allowedamt))%></td>
									
								</tr>
								<tr><td colspan="5">In words Rupees <b>"<%=awc.convert1(allowedamt) %>"</b> Only</td></tr>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>

					</div>

				</div>
			</div>

		</div>

	</div>


</body>

</html>
<%@page import="java.util.Date"%>
<%@page import="java.util.Map"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="com.vts.ems.utils.AmountWordConveration"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.vts.ems.utils.NFormatConvertion"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style type="text/css">
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
	width: 100% !important;
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
</style>


</head>
<body>


	<%
	List<Object[]> ContingentList = (List<Object[]>) request.getAttribute("ContingentList");
	Object[] contingentdata = (Object[]) request.getAttribute("contingentdata");
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
	Date today=new Date();
	%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Contingent Bill</h5>
			</div>
			<div class="col-md-9">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm">Newspaper</a></li>
					<%
					if (isapproval != null && isapproval.equalsIgnoreCase("Y")) {
					%>
					<li class="breadcrumb-item "><a href="NewspaperContingentAppro.htm">Pending
							Contingent Bills</a></li>
					<%
					} else {
					%>
					<li class="breadcrumb-item "><a
						href="NewspaperContingentAppro.htm">Contingent Bills</a></li>
					<%
					}
					%>
					<li class="breadcrumb-item active " aria-current="page">Contingent
						Bill</li>
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">

		<div class="card-body">

			<div align="center">
				<%
				String ses = (String) request.getParameter("result");
				String ses1 = (String) request.getParameter("resultfail");
				if (ses1 != null) {
				%>
				<div class="alert alert-danger" role="alert">
					<%=ses1%>
				</div>

				<%
				}
				if (ses != null) {
				%>

				<div class="alert alert-success" role="alert">
					<%=ses%>
				</div>
				<%
				}
				%>
			</div>



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
									style="font-size: 20px; font-weight: 600; margin-top: 30px">SITAR</span>
								<span style="float: right; vertical-align: bottom;">
									Dt.&nbsp;<%=dateFormat.format(today)%> </span><br> <span
									style="font-size: 15px; font-weight: 600;">Ref:
									STARC/F&A/NEWSPAPER/</span>
							</div>

							<table>
								<tr>
									<th style="text-align: center;">Select</th>

									<th style="text-align: center;">SN</th>
									<th style="text-align: center;">Emp. No.</th>
									<th style="text-align: center;">Name</th>
									<th style="text-align: center;">Claim No</th>
									<th class="right" style="width: 15%;">Amount Claimed
										(&#8377;)</th>
									<th class="right" style="width: 15%;">Amount Admitted
										(&#8377;)</th>
									<th style="width: 10%">Form</th>
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
										style="text-align: center; padding-top: 5px; padding-bottom: 5px;"><input
										type="checkbox" name="NewspaperId" value="<%=obj[0]%>">
									</td>

									<td
										style="text-align: center; padding-top: 5px; padding-bottom: 5px;"><%=i%></td>

									<td style="padding-top: 5px; padding-bottom: 5px;"><%=obj[7]%></td>
									<td style="padding-top: 5px; padding-bottom: 5px;"><%=obj[8]%></td>

									<td class="center"
										style="padding-top: 5px; padding-bottom: 5px;"><%=obj[9]%></td>
									<td
										style="padding-top: 5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[1].toString()))))%></td>
									<td
										style="padding-top: 5px; padding-bottom: 5px; text-align: right;"><%=nfc.rupeeFormat(String.valueOf(Math.round(Double.parseDouble(obj[2].toString()))))%></td>
									<td>

										<button type="submit" class="btn btn-sm" name="NewspaperId"
											value="<%=obj[0]%>" formaction="NewspaperClaimPreview.htm"
											formtarget="blank" formmethod="post" data-toggle="tooltip"
											data-placement="top" title="View">
											<i class="fa-solid fa-eye"></i>
										</button>

										<button type="submit" class="btn btn-sm" name="NewspaperId"
											value="<%=obj[0]%>" formaction="NewspaperPrint.htm"
											formtarget="_blank" formmethod="post" data-toggle="tooltip"
											data-placement="top" title="Download">
											<i style="color: #019267" class="fa-solid fa-download"></i>
										</button>

									</td>
								</tr>
								<%
								k++;
								claimamt += Math.round(Double.parseDouble(obj[1].toString()));
								allowedamt += Math.round(Double.parseDouble(obj[2].toString()));
								/* 								billscount += Integer.parseInt(obj[25].toString()); */
								}
								%>
								<tr>

									<td colspan="5" class="right">Total</td>
									<%-- 	<td class="center"><%=billscount %></td> --%>
									<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(claimamt))%></td>
									<td class="right">&#8377; <%=nfc.rupeeFormat(String.valueOf(allowedamt))%>

									</td>
								</tr>
							</table>
							<input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
							</form>
							<form action="NewspaperContingentApprove.htm">
							<%
							if (!contingentdata[4].toString().equalsIgnoreCase("CSD")) {
							%>
							<div class="col-md-12" align="left">
								Remarks : <br>
								<textarea class="w-100 form-control" rows="4" cols="100"
									id="remarks" name="remarks" maxlength="500"></textarea>
								<br>
							</div>
							<%
							}
							%>
							<div class="col-12" align="center">
								<%
								if (i > 0) {
								%>
								<%
								if (billstatus.equalsIgnoreCase("CGT") && logintype.equalsIgnoreCase("K")) {
								%>
								<button type="submit" class="btn btn-sm submit-btn"
									name="action" id="fwd-btn" value="F"
									formaction="NewspaperContingentApprove.htm" formmethod="POST"
									onclick="return confirm('Are You Sure To Forward?');">Forward</button>
								<button type="submit" class="btn btn-sm submit-btn"
									style="background-color: #D2001A"
									formaction="NewspaperContingentClaimDrop.htm" form="view-form"
									onclick="return checklength();">Drop From Bill</button>
								<%
								} else if ((billstatus.equalsIgnoreCase("RBV") || billstatus.equalsIgnoreCase("RBA")
										|| billstatus.equalsIgnoreCase("RBD")) && logintype.equalsIgnoreCase("K")) {
								%>
								<button type="submit" class="btn btn-sm submit-btn"
									name="action" id="fwd-btn" value="F"
									formaction="NewspaperContingentApprove.htm"
									onclick="return remarkRequired('R')">Forward</button>
								<button type="submit" class="btn btn-sm submit-btn"
									style="background-color: #D2001A"
									formaction="NewspaperContingentClaimDrop.htm" form="view-form"
									onclick="return checklength();">Drop From Bill</button>
								<%
								} else if (billstatus.equalsIgnoreCase("ABP") && logintype.equalsIgnoreCase("V")) {
								%>
								<button type="submit" class="btn btn-sm submit-btn"
									name="action" id="fwd-btn" value="VF"
									onclick="return confirm('Are You Sure To Forward?');">Forward</button>
								<button type="submit" class="btn btn-sm delete-btn"
									name="action" value="VR" onclick="return remarkRequired('R')">Return</button>
								<%
								}
								%>

								<%
								}
								%>
							</div>
							<input type="hidden" name="view_mode" value=""> <input
						type="hidden" name="contingentid" value="<%=contingentdata[0]%>">
							
							<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						</form>
					</div>
					

					<form action="CHSSContingentApprove.htm" method="post">
						<input type="hidden" name="contingentid" value=""> <input
							type="hidden" name="isapproval" value="Y"> <input
							type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
					</form>

					<%
					if (i == 0) {
					%>
					<form method="post" action="NewspaperContingentDelete.htm">
						<div class="col-12" align="center">
							<input type="hidden" name="contingentid"
								value="<%=contingentdata[0]%>"> <input type="hidden"
								name="isapproval" value="Y"> <input type="hidden"
								name="${_csrf.parameterName}" value="${_csrf.token}" />
							<button type="submit" class="btn btn-sm delete-btn" name="action"
								formaction="NewspaperContingentDelete.htm"
								onclick="return confirm('Are You Sure To delete?');">Delete</button>
						</div>
					</form>
					<%
					}
					%>

				</div>
			</div>

		</div>

	</div>

	<script type="text/javascript">
		function checklength() {
			if ($('input:checked').length > 0) {

				return confirm('Are You Sure to Drop the Claims from the Bill(s) ? ');
			} else {
				alert('Please Select Atleast One Claim!');
				return false;
			}
		}

		$('#fwd-btn').click(function() {
			$('#remarks').attr('required', false);

		});

		function remarkRequired(action) {
			if (action === 'R') {
				$('#remarks').attr('required', true);
				if ($('#remarks').val().trim() === '') {
					alert('Please Fill Remarks to Submit! ');
					return false;
				} else {
					return confirm('Are You Sure To Submit?');
				}

			} else {
				$('#remarks').attr('required', false);
				return confirm('Are You Sure To Submit 111?');
			}

		}
	</script>

</body>

</html>
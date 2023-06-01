<%@page import="com.vts.ems.master.model.LabMaster"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat,java.util.List"%>
<%@ page language="java"%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Newspaper Claim Preview</title>
<!--  Bootstrap -->
<link rel="stylesheet"
	href="vtsfolder/bower_components/bootstrap/dist/css/bootstrap.min.css" />

<style type="text/css">
@media print {
	#printPageButton {
		display: none;
	}
}

table {
	border-collapse: collapse;
	font-weight: bold;
	width: 720px;
}

#mainTable tr, th, td {
	border: 2px solid black;
}

.table2 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}

.table2 td:first-child:not(.exclude) {
	width: 6%;
}

.table2 td:nth-child(2):not(.exclude) {
	width: 40%;
}

.text-center {
	text-align: center;
}

.declare {
	width: 620px;
	margin-top: 25px;
	text-align: justify;
}

.decDiv {
	display: flex;
	justify-content: space-between;
	font-weight: bold;
	margin-top: 60px;
}

.note {
	
	margin-top: 30px;
	
}

.table3 tr, th, td {
	border: 1px solid black;
	padding: 0px;
}

.table3 tr {
	height: 10px;
}

.table3 td {
	font-weight: normal;
}

.accDPT {
	margin-top: 30px;
}

.table4 tr, th, td {
	border: 1px solid black;
	padding: 5px;
}

.table4 td {
	font-weight: normal;
}

.table4 td:first-child:not(.exlude) {
	width: 45%;
}

.sign {
	width: 620px;
	text-align: right;
	margin-top: 60px;
}

.blue {
	color: blue;
}

.borderDiv {
	align :center;
	border: 1.5px solid black;
	width: 800px;
	padding-bottom: 20px;
}
</style>


</head>
<body>


	<%
	Object[] NewspaperUserPrintData = (Object[]) request.getAttribute("NewspaperUserPrintData");
	List<Object[]> newsPaperRemarksHistory = (List<Object[]>)request.getAttribute("newsPaperRemarksHistory");
	String LabLogo = (String) request.getAttribute("LabLogo");
	String isApproval = (String) request.getAttribute("isApproval");
	String PayableRupee = "Not Available";
	String RistrictedAmt = "Not Available";
	String ClaimRupee = "Not Available";

	/* String NewsClaimHeader =(String)request.getAttribute("NewsClaimHeader"); */

	if (NewspaperUserPrintData != null) {
		String ClaimAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[2].toString());
		ClaimRupee = IndianRupeeFormat.rupeeFormat(ClaimAmountRsPaisA[0]);

		String PayableAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[4].toString());
		PayableRupee = IndianRupeeFormat.rupeeFormat(PayableAmountRsPaisA[0]);

		String AdmissibleAmountRsPaisA[] = IndianRupeeFormat.getRupeePaisaSplit(NewspaperUserPrintData[3].toString());
		RistrictedAmt = IndianRupeeFormat.rupeeFormat(AdmissibleAmountRsPaisA[0]);

	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	Date today = new Date();
	%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Newspaper Preview</h5>
			</div>
			<div class="col-md-8 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>

					<!-- 		<li class="breadcrumb-item "><a href="NocApproval.htm">NOC Approval</a></li>
					<li class="breadcrumb-item active" aria-current="page">
						Preview</li> -->

					<li class="breadcrumb-item "><a href="NewspaperList.htm">Newspaper
							Apply</a></li>
					<li class="breadcrumb-item active" aria-current="page">Newspaper
						Claim Form
				</ol>
			</div>

		</div>
	</div>

	<%
	if (NewspaperUserPrintData != null) {
	%>

	<div class="page card dashboard-card">
		<div class="card">
			<div class="card-body">
				<div class="borderDiv" align="center">
				<div align="center">
				
					<table height="110" style="margin-top: 30px;" id="mainTable">
						<tr>
							<td rowspan="2"><img
								style="width: 80px; height: 90px; margin: 0px 0 0 15px;"
								align="left" src="data:image/png;base64,<%=LabLogo%>"></td>
							<td class="text-center" width="300px" rowspan="2">CLAIM FORM
								FOR <br>REIMBURSEMENT <br> OF RESIDENTIAL NEWSPAPERS
								TO <br> EXECUTIVES
							</td>
							<td>SITAR-BNG <br> P&A-057
							</td>
							<td>Rev.: 04</td>
						</tr>
						<tr>
							<td>Date of Issue: <br><%=dateFormat.format(today)%>
							</td>
							<td>Total<br> Pages-1
							</td>
						</tr>
					</table>

					<div style="margin-top: 50px">
						<table class="table2">
							<tr>
								<td>1.</td>
								<td>Name of the Employee</td>
								<td colspan="2" class="blue"><%=NewspaperUserPrintData[9]%></td>
							</tr>
							<tr>
								<td>2.</td>
								<td>Emp. No.</td>
								<td colspan="2" class="blue"><%=NewspaperUserPrintData[8]%></td>
							</tr>
							<tr>
								<td>3.</td>
								<td>Designation</td>
								<td colspan="2" class="blue"><%=NewspaperUserPrintData[10]%></td>
							</tr>
							<tr>
								<td>4.</td>
								<td>Grade & Level in the Pay Matrix</td>
								<td class="blue" style="text-align: center;">
									<%
									if (NewspaperUserPrintData != null) {
									%><%=NewspaperUserPrintData[6]%> <%
 }
 %>
								</td>
								<td class="blue" style="text-align: center;">
									<%
									if (NewspaperUserPrintData != null) {
									%><%=NewspaperUserPrintData[5]%> <%
 }
 %>
								</td>
							</tr>
							<tr>
								<td height="40px">5.</td>
								<td>Bi-annual & Year of claim</td>
								<td class="exclude" colspan="2" class="blue">
									<%
									if (NewspaperUserPrintData[0].toString().equalsIgnoreCase("JAN-JUN")) {
									%> <span class="blue">January-June <%=NewspaperUserPrintData[1]%></span>
									<%
									}
									if (NewspaperUserPrintData[0].toString().equalsIgnoreCase("JUL-DEC")) {
									%> <span class="blue"> July - December <%=NewspaperUserPrintData[1]%></span>
									<%
									}
									%>
								</td>
								<%-- <td class="exclude"><% if(NewspaperUserPrintData[0].toString().equalsIgnoreCase("JUL-DEC")){%>  <%=NewspaperUserPrintData[1]%> <%} else{%>-<%} %></td> --%>
							</tr>
							<tr>
								<td>6.</td>
								<td>Amount of Reimbursement @ ` 500/- p.m.</td>
								<%
								String amt = PayableRupee;
								amt = amt.replace(",", "");
								if (Integer.parseInt(amt) <= 3000) {
								%>
								<td colspan="2" class="blue">Rs <%=PayableRupee%> /-
								</td>
								<%
								} else {
								%>
								<td colspan="2" class="blue">Rs 3000 /-</td>
								<%
								}
								%>
							</tr>
						</table>
					</div>

					<div class="declare">
						I hereby certify that the (i) The newspaper (s) in respect of
						which reimbursement is claimed, is/are purchased by me. (ii) The
						subscription amount incurred by me is not less than the amount
						claimed towards subscription of newspaper / s for the period.
						(iii) The amount for which the reimbursement is being claimed has
						actually been paid by me and has not/will not be claimed by any
						other source.
						<div class="decDiv">
							<div class="decDiv1">Date:</div>

							<div style="margin-right: 80px;">
								Signature of Employee <br> STARC Intercom No:
							</div>
						</div>
					</div>
					<div class="note" >
						<div align="left"> <span style="margin-left: 8%"> Note:</span></div>
						<table class="table3">
							<tr>
								<th>Name of the Quarter</th>
								<th>Due date of claim submission (on or before)</th>
							</tr>
							<tr>
								<td>January - June</td>
								<td>15th of July</td>
							</tr>
							<tr>
								<td>July - December</td>
								<td>15th of January</td>
							</tr>
						</table>
					</div>

					<div class="accDPT">
						<div
							style="text-decoration: underline; font-weight: bold; margin-bottom: 20px;">FOR
							ACCOUNTS DEPARTMENT USE</div>
						<table class="table4">
							<tr>
								<td>Claimed Amount</td>
								<td class="blue"><b>Rs <%=ClaimRupee%> /-
								</b></td>
							</tr>
							<tr>
								<td>Eligible Amount</td>
								<td class="blue"><b>Rs <%=RistrictedAmt%> /-
								</b></td>
							</tr>
							<tr>
								<td>Amount passed for payment</td>
								<%
								String amt1 = PayableRupee;
								amt1 = amt1.replace(",", "");
								if (Integer.parseInt(amt1) <= 3000) {
								%>
								<td colspan="2" class="blue"><b>Rs <%=PayableRupee%> /-</td>
								<%
								} else {
								%>
								<td colspan="2" class="blue">Rs 3000 /-</b></td>
								<%
								}
								%>
							</tr>
						</table>
					</div>
					<div class="sign">
						<b style="margin-right: 50px;">F&A Dept. / FO </b>
					</div>

				</div>
				
				<br>
				<div class="row">
					  <br>
						<%if(newsPaperRemarksHistory.size()>0){ %>
							<div class="col-md-8" align="center" style="margin: 10px 0px 5px 25px; padding:0px;border: 1px solid black;border-radius: 5px;">
								<%if(newsPaperRemarksHistory.size()>0){ %>
									<table style="margin: 3px;padding: 0px">
										<tr>
											<td style="border:none;padding: 0px">
												<h6 style="text-decoration: underline;">Remarks :</h6> 
											</td>											
										</tr>
										<%for(Object[] obj : newsPaperRemarksHistory){%>
										<tr>
											<td style="border:none;width: 80%;overflow-wrap: anywhere;padding: 0px">
												<%=obj[3]%>&nbsp; :
												<span style="border:none; color: blue;">	<%=obj[1] %></span>
											</td>
										</tr>
										<%} %>
									</table>
								<%} %>
							</div>
							<%} %>
					   </div>
				<form action="#">
					<%
					if (NewspaperUserPrintData != null && (NewspaperUserPrintData[11].toString().equalsIgnoreCase("CRT")
							|| NewspaperUserPrintData[11].toString().equalsIgnoreCase("SDG")
							|| NewspaperUserPrintData[11].toString().equalsIgnoreCase("SBA")
							|| NewspaperUserPrintData[11].toString().equalsIgnoreCase("SBP"))) {
					%>
					<div align="center" style="margin-leftt: 28.5%;">
						<div style="text-align: left; width: 580px;">
							<b>Remarks :</b>
						</div>
						<br>
						<textarea rows="4" cols="70" name="remarks" id="remarksarea"
							maxlength="250"></textarea>
						<br>

						<button type="submit" class="btn btn-sm submit-btn"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="FWD"
							onclick="return confirm('Are You Sure To Submit ?');">
							<i class="fa-solid fa-forward" style="color: #125B50"></i> Submit
							for processing
						</button>

					</div>
					<%
					} else if (isApproval != null && isApproval.equalsIgnoreCase("Y") && NewspaperUserPrintData != null
							&& (NewspaperUserPrintData[11].toString().equalsIgnoreCase("FWD"))) {
					%>
					<div align="center" style="margin-leftt: 28.5%;">
						<div style="text-align: left; width: 580px;">
							<b>Remarks :</b>
						</div>
						<br>
						<textarea rows="4" cols="70" name="remarks" id="remarksarea"
							maxlength="250"></textarea>
						<br>

					</div>
					<div align="center" style="margin-leftt: 28.5%;">
						<button type="submit" class="btn btn-sm submit-btn"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="DGM-A"
							onclick="return confirm('Are You Sure To Proceed?');">
							Process</button>

						<button type="submit" class="btn btn-sm btn-danger"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="DGM-R" onclick="return validateTextBox();">
							Return</button>
					</div>
					<%
					} else if (isApproval != null && isApproval.equalsIgnoreCase("Y") && NewspaperUserPrintData != null
							&& (NewspaperUserPrintData[11].toString().equalsIgnoreCase("VDG"))) {
					%>
					<div align="center" style="margin-leftt: 28.5%;">
						<div style="text-align: left; width: 580px;">
							<b>Remarks :</b>
						</div>
						<br>
						<textarea rows="4" cols="70" name="remarks" id="remarksarea"
							maxlength="250"></textarea>
						<br>

					</div>
					<div align="center" style="margin-leftt: 28.5%;">
						<button type="submit" class="btn btn-sm submit-btn"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="PO-A"
							onclick="return confirm('Are You Sure To Proceed?');">
							Process</button>

						<button type="submit" class="btn btn-sm btn-danger"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="PO-R" onclick="return validateTextBox();">
							Return</button>
					</div>
					<%
					} else if (isApproval != null && isApproval.equalsIgnoreCase("Y") && NewspaperUserPrintData != null
							&& (NewspaperUserPrintData[11].toString().equalsIgnoreCase("VBP"))) {
					%>
					<div align="center" style="margin-leftt: 28.5%;">
						<div style="text-align: left; width: 580px;">
							<b>Remarks :</b>
						</div>
						<br>
						<textarea rows="4" cols="70" name="remarks" id="remarksarea"
							maxlength="250"></textarea>
						<br>

					</div>
					<div align="center" style="margin-leftt: 28.5%;">
						<button type="submit" class="btn btn-sm submit-btn"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="AO-A"
							onclick="return confirm('Are You Sure To Proceed?');">
							Process</button>

						<button type="submit" class="btn btn-sm btn-danger"
							id="finalSubmission" formaction="NewspaperForward.htm"
							name="Action" value="AO-R" onclick="return validateTextBox();">
							Return</button>
					</div>
					<%
					}
					%>
					<input type="hidden" name="NewspaperId"
						value="<%=NewspaperUserPrintData[7]%>">
				</form>

			</div>
			</div>
		</div>
	</div>
	<%
	}
	%>

	<script>
		function validateTextBox() {
			if (document.getElementById("remarksarea").value.trim() != "") {
				return confirm('Are You Sure To Return?');

			} else {
				alert("Please enter Remarks");
				return false;
			}
		}
	</script>
</body>
</html>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.ibm.icu.impl.UResource.Array"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bank Detail Form</title>
</head>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
table {
	align: left;
	width: 100% !important;
	height: 100% !important;
	margin-top: 10px;
	margin-bottom: 10px;
	margin-left: 10px;
	border-collapse: collapse;
}

th, td {
	text-align: left;
	border: 1px solid black;
	padding: 4px;
	word-break: break-word;
	overflow-wrap: anywhere;
}

input {
	border-width: 0 0 1px 0;
	width: 80%;
}

input:focus {
	outline: none;
}
</style>
<body>

	<%
	String LabLogo = (String) request.getAttribute("LabLogo");
	SimpleDateFormat rdf = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sdtf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat rdtf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
	Date date = new Date();

	String isApproval = (String) request.getAttribute("isApproval");


	Object[] oneBankDeatil = (Object[]) request.getAttribute("oneBankDeatil");
	%>
	<div class="page card dashboard-card">
		<div class="card-body" align="center">
			<div class="row">
		


				<div class="card-body">
					<div class="card"
						style="padding-top: 0px; margin-top: -15px; width: 85%;">
						<form action="" method="post">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
							<div class="card-body main-card"
								style="padding-top: 0px; margin-top: -15px;" align="center">
								<table style="border: 0px; width: 100%">
									<tr>
										<td
											style="width: 20%; height: 75px; border: 0; margin-bottom: 10px;"><img
											style="width: 80px; height: 90px; margin: 5px;" align="left"
											src="data:image/png;base64,<%=LabLogo%>"></td>
										<td
											style="width: 60%; height: 75px; border: 0; text-align: center; vertical-align: bottom;"><h4>
												FORM FOR INTIMATION OF CHANGE OF <br>BANK DETAILS
											</h4></td>
										<td
											style="width: 20%; height: 75px; border: 0; vertical-align: bottom;">
											<span style="float: right;"> &nbsp;<span
												class="text-blue"></span></span>
										</td>
									</tr>
								</table>

								<table
									style="margin-top: 5%; border-collapse: collapse; width: 100%;">
									<tbody>
										<tr>
											<td style="border: 0; width: 86%">From&emsp;&emsp;&emsp;:&emsp;<%
											if (oneBankDeatil != null && oneBankDeatil[9] != null) {
											%> <%=oneBankDeatil[9]%> <%
 }
 %>
											</td>
											<td style="border: 0; width: 17%;">To &emsp;: &nbsp;P&A
												Dept</td>
										</tr>
										<tr>
											<td style="border: 0;">Emp. No.&emsp;&nbsp;&nbsp;:&emsp;
												<%
												if (oneBankDeatil != null && oneBankDeatil[1] != null) {
												%> <%=oneBankDeatil[1]%> <%}%>
											</td>
										</tr>
										<tr>
											<td style="border: 0;">Date&emsp;&emsp;&emsp;&nbsp;:&emsp;
												<%
												if (oneBankDeatil[12] != null) {
												%> <%=rdf.format(sdtf.parse(oneBankDeatil[12].toString()))%>
												<%
												}
												%>
											</td>
										</tr>
										<tr>
											<td style="border: 0;"></td>
										</tr>
										<tr>
											<td style="border: 0;"></td>
										</tr>
										<tr>
											<td
												style="border: 0; margin-left: 10px; text-align: justify; text-justify: inter-word; font-size: 14px;"
												align="left">This is to inform you that I have changed
												my Bank Details w.e.f.&nbsp;<input type="text"
												value=" <%if (oneBankDeatil != null && oneBankDeatil[6] != null) {%><%=rdf.format(oneBankDeatil[6])%>  <%}%>"
												readonly style="width: 12%; text-align: center;">&nbsp;
												and the present Bank Details are as under:
											</td>
										</tr>

										<tr>
											<td style="border: 0;">Bank Name<label
												style="margin-left: 70px;">:</label> <input type="text"
												value="<%if (oneBankDeatil != null && oneBankDeatil[2] != null) {%> <%=oneBankDeatil[2]%> <%}%>"
												readonly></td>
										</tr>
										<tr>
											<td style="border: 0;">Bank Branch Name<label
												style="margin-left: 20px;">:</label><input type="text"
												value="<%if (oneBankDeatil != null && oneBankDeatil[3] != null) {%> <%=oneBankDeatil[3]%> <%}%>"
												readonly></td>
										</tr>
										<tr>
											<td style="border: 0;">IFSC<label
												style="margin-left: 114px;">:</label><input type="text"
												value="<%if (oneBankDeatil != null && oneBankDeatil[4] != null) {%> <%=oneBankDeatil[4]%> <%}%>"
												readonly></td>
										</tr>
										<tr>
											<td style="border: 0;">Account No<label
												style="margin-left: 68px;">:</label><input type="text"
												value="<%if (oneBankDeatil != null && oneBankDeatil[5] != null) {%> <%=oneBankDeatil[5]%> <%}%>"
												readonly></td>
										</tr>
										<tr>
											<td style="border: 0;">The same may be recorded in the
												office records.</td>
										</tr>

									</tbody>
								</table>

								<div style="width: 100%; text-align: right; margin-left: -5%;">
									Signature of Employee<br>

								</div>
								<div style="width: 100%; text-align: right; margin-left: -5%;">
									<%if (oneBankDeatil != null && oneBankDeatil[9] != null) {%>
									<%=oneBankDeatil[9]%>
									<%}%>
								</div>
								<div style="width: 100%; text-align: right; margin-left: -5%;">
									<%if (oneBankDeatil[12] != null) {%>
									<span><%=rdtf.format(sdtf.parse(oneBankDeatil[12].toString()))%></span>
									<%}%>
								</div>
								<hr style="border: solid 1px;">

								<div style="width: 100%; border: 0; text-align: center;">
									<b style="font-size: 18px; text-decoration: underline">FOR
										ADMINISTRATION USE</b>
								</div>
								<br>

								<div
									style="margin-left: 10px; text-align: justify; text-justify: inter-word; font-size: 14px;"
									align="left">
									Intimation of change of address received on &nbsp;
									<%
									if (oneBankDeatil[12] != null) {
									%>
									<span style="text-decoration: underline;"><%=rdf.format(sdtf.parse(oneBankDeatil[12].toString()))%></span>
									<%
									}
									%>
									. The same has been updated in<br> the personal records.
								</div>
								<br> <br>
								<div style="width: 100%; text-align: right; margin-left: -5%;">
								</div>
								<div style="border: 0px; width: 100%; text-align: right;">
									Incharge-P&A <br>

									<%
									if (oneBankDeatil != null && oneBankDeatil[14] != null) {
									%>
									<br><%=oneBankDeatil[14]%><br>
									<%
									}
									%>
									<%if (oneBankDeatil[13] != null) {%>
									<span><%=rdtf.format(sdtf.parse(oneBankDeatil[13].toString()))%></span>
									<%}%>
								</div>
								<br>
								
								<%
								if (oneBankDeatil != null
										&& (oneBankDeatil[10].toString().equalsIgnoreCase("INI") || oneBankDeatil[10].toString().equalsIgnoreCase("RDG")
										|| oneBankDeatil[10].toString().equalsIgnoreCase("RPA"))) {
								%>
								<div align="left">
									<%
									if (oneBankDeatil[11] != null) {
									%>
									<span style="color: red">Remarks :</span>
									<%=oneBankDeatil[11]%>
									<%
									}
									%>

								</div>
								<div align="left">
									<b>Remarks :</b><br>
									<textarea rows="5" cols="85" name="remarks" id="remarksarea"
										maxlength="250"></textarea>
								</div>

								<button type="submit" class="btn btn-sm submit-btn"
									id="finalSubmission" formaction="ForwodApp.htm" name="bankId"
									value="<%=oneBankDeatil[0]%>"
									onclick="return confirm('Are You Sure To Submit?');">
									<i class="fa-solid fa-forward" style="color: #125B50"></i>
									Submit for verification
								</button>
								<%
								}
								%>

								<%
								if (oneBankDeatil != null && (oneBankDeatil[10].toString().equalsIgnoreCase("FWD")) && isApproval != null
										&& isApproval.equalsIgnoreCase("Y")) {
								%>
								<div align="left">
									<%
									if (oneBankDeatil[11] != null) {
									%>
									<span style="color: red">Remarks :</span>
									<%=oneBankDeatil[11]%>
									<%
									}
									%>

								</div>
								<div align="left">
									<b>Remarks :</b><br>
									<textarea rows="5" cols="85" name="remarks" id="remarksarea"
										maxlength="250"></textarea>
								</div>
								<button type="submit" class="btn btn-sm submit-btn"
									id="finalSubmission" formaction="BankFormDGMSubmit.htm"
									name="Action" value="A"
									onclick="return confirm('Are You Sure To Verify?');">
									Verify</button>

								<button type="submit" class="btn btn-sm btn-danger"
									id="finalSubmission" formaction="BankFormDGMSubmit.htm"
									name="Action" value="R" onclick="return validateTextBox();">
									Return</button>
								<%
								}
								%>

								<%
								if (oneBankDeatil != null && (oneBankDeatil[10].toString().equalsIgnoreCase("VDG")) && isApproval != null
										&& isApproval.equalsIgnoreCase("Y")) {
								%>
								<div align="left">
									<%
									if (oneBankDeatil[11] != null) {
									%>
									<span style="color: red">Remarks :</span>
									<%=oneBankDeatil[11]%>
									<%
									}
									%>

								</div>
								<div align="left">
									<b>Remarks :</b><br>
									<textarea rows="5" cols="85" name="remarks" id="remarksarea"
										maxlength="250"></textarea>
								</div>
								<button type="submit" class="btn btn-sm submit-btn"
									id="finalSubmission" formaction="BankFormPAndASubmit.htm"
									name="Action" value="A"
									onclick="return confirm('Are You Sure To Verify?');">
									Verify</button>

								<button type="submit" class="btn btn-sm btn-danger"
									id="finalSubmission" formaction="BankFormPAndASubmit.htm"
									name="Action" value="R" onclick="return validateTextBox();">
									Return</button>
								<%
								}
								%>

							</div>
							<input type="hidden" name="bankId"
								value="<%if (oneBankDeatil != null) {%><%=oneBankDeatil[0]%><%}%>">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

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

</html>
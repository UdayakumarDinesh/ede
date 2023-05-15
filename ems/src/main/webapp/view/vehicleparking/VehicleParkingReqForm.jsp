<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="java.util.Date,java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Vehicle Parking Form</title>
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
</head>
<body>

	<%
	String LabLogo = (String) request.getAttribute("LabLogo");

	Object[] oneVehicle = (Object[]) request.getAttribute("oneVehicle");
	String TypeOfAction  = (String) request.getAttribute("TypeOfAction");
	String isApproval = (String) request.getAttribute("isApproval");
	
	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	%>
	<%=TypeOfAction %><%=isApproval %>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Vehicle Parking</h5>
			</div>
			
			<div class="col-md-9">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="VehicleParking.htm">
							Vehicle Parking Application</a></li>	
					<%if(TypeOfAction !=null &&  TypeOfAction.equalsIgnoreCase("Pending")) {%>
					<li class="breadcrumb-item"> <a href="VehicleParkinglApproval.htm">
							Parking Pending List </a></li>	
					<li class="breadcrumb-item active " aria-current="page">
							Application Form </a></li>			
						
					<%} else if (TypeOfAction !=null && TypeOfAction.equalsIgnoreCase("All")){ %>
					
					<li class="breadcrumb-item active " aria-current="page">
							Application Form </a></li>	
					<%}else{ %>	
					<li class="breadcrumb-item "> <a href="VehicleParkinglApproved.htm">
							Parking Approved List </a></li>		
					<li class="breadcrumb-item active " aria-current="page">
							Application Form </a></li>				
					<%}%>				
				
					
				</ol>
			</div>
		</div>
	</div>


	<div class="page card dashboard-card">
		<div class="card-body" align="center">
			<div class="row">



				<div class="card-body">
					<div class="card"
						style="padding-top: 0px; margin-top: -15px; width: 85%;">
						<form action="VehicleParkFormSubmitAndPrint.htm" method="post">
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
												FORM FOR VEHICLE PARKING REQUEST<br>
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
											<td
												style="border: 0; width: 86%; text-decoration: underline;"><b>To</b>
											</td>
										</tr>
										<tr>
											<td style="border: 0;">The CEO</td>
										</tr>
										<tr>
											<td style="border: 0;">STARC</td>
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
												align="left"><B>Through</B>
												&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;Security Officer</td>
										</tr>
										<tr>
											<td style="border: 0;"></td>
										</tr>
										<tr>
											<td style="border: 0;"></td>
										</tr>


										<tr>
											<td style="border: 0;"><span style="margin-left: 60px;">I
													would like to request to give permission to park my vehicle
													in STARC.</span></td>
										</tr>
										<tr>
											<td style="border: 0;">Vehicle No&nbsp;&nbsp;&nbsp; <label
												style="margin-left: 50px;">:&nbsp;&nbsp;&nbsp;</label><input
												type="text"
												value="<%if (oneVehicle != null && oneVehicle[3] != null) {%> <%=oneVehicle[3]%> <%}%>"
												readonly></td>
										</tr>
										<tr>
											<td style="border: 0;"><label>From Date & Time
													&nbsp;&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;</label><input type="text"
												value="<%if (oneVehicle != null && oneVehicle[5] != null) {%> <%=DateTimeFormatUtil.getDateTimeToRegularDate(oneVehicle[4].toString())%>&nbsp;&nbsp;&nbsp;<%=time.format(oneVehicle[4])%> <%}%>"
												readonly></td>
										</tr>
										<tr>
											<td style="border: 0;">To Date & Time <label
												style="margin-left: 28px;">:&nbsp;&nbsp;&nbsp;</label><input
												type="text"
												value="<%if (oneVehicle != null && oneVehicle[5] != null) {%><%=DateTimeFormatUtil.getDateTimeToRegularDate(oneVehicle[4].toString())%>&nbsp;&nbsp;&nbsp;<%=time.format(oneVehicle[5])%> <%}%>"
												readonly></td>
										</tr>

									</tbody>
								</table>

								<br> <br>

								<div
									style="text-align: right; margin-right: 80px; margin-bottom: 40px;">
									Sincerely, <br>
								</div>
								<div style="width: 100%; text-align: right; margin-left: -5%;">
									Signature of Employee<br>
								</div>
								<div style="width: 100%; text-align: right; margin-left: -10%;">
									<%
									if (oneVehicle != null && oneVehicle[2] != null) {
									%>
									<%=oneVehicle[2]%>, <br>
									<%
									}
									%>
								</div>
								<div style="width: 100%; text-align: right; margin-left: -12%;">
									<%
									if (oneVehicle != null && oneVehicle[7] != null) {
									%>
									<%=oneVehicle[7]%>
									<%
									}
									%>
								</div>
							
								<%if (oneVehicle != null  && (oneVehicle[8].toString().equalsIgnoreCase("INI") ) || oneVehicle[8].toString().equalsIgnoreCase("RSO")) {%>
								<div align="left">
									<%
									if (oneVehicle[9] != null) {
									%>
									<span style="color: red">Remarks :</span>
									<%=oneVehicle[9]%>
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
									id="finalSubmission" formaction="VehicleParkingForward.htm"
									name="Action" value="FWD"
									onclick="return confirm('Are you sure you want to forward this application ?');">
									<i class="fa-solid fa-forward" style="color: #125B50"></i>
									Submit
								</button>
								<%} else if(oneVehicle != null && isApproval != null && oneVehicle[8].toString().equalsIgnoreCase("FWD") && isApproval.equalsIgnoreCase("Y")) { %>
								<div align="left">
									<%
									if (oneVehicle[9] != null) {
									%>
									<span style="color: red">Remarks :</span>
									<%=oneVehicle[9]%>
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
									id="finalSubmission" formaction="VehicleParkingForward.htm"
									name="Action" value="A"
									onclick="return confirm('Are You Sure To Verify?');">
									Verify</button>

								<button type="submit" class="btn btn-sm btn-danger"
									id="finalSubmission" formaction="VehicleParkingForward.htm"
									name="Action" value="R" onclick="return validateTextBox();">
									Return</button>
									<%} %>
							</div>
							<input type="hidden" name="vehicleAppId"
								value="<%if (oneVehicle != null) {%><%=oneVehicle[0]%><%}%>">
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
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
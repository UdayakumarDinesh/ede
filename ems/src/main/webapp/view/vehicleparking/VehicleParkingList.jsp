<%@page import="java.text.Format"%>
<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page
	import="java.text.SimpleDateFormat,com.vts.ems.utils.DateTimeFormatUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List, java.text.DateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Vehicle Parking</title>
<style type="text/css">
.trup {
	padding: 6px 10px 6px 10px;
	border-radius: 5px;
	font-size: 14px;
	font-weight: 600;
}

.trdown {
	padding: 0px 10px 5px 10px;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	font-size: 14px;
	font-weight: 600;
}
</style>
</head>
<body>

	<%
	List<Object[]> VehicleParkList = (List<Object[]>) request.getAttribute("VehicleParkList");
	Object[] empNameAndDesi = (Object[]) request.getAttribute("empNameAndDesi");

	String fromdate = (String) request.getAttribute("fromdate");
	String todate = (String) request.getAttribute("todate");

	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");

	SimpleDateFormat time = new SimpleDateFormat("HH:mm");
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>Vehicle Parking <small><b>&nbsp; - &nbsp;<%if(empNameAndDesi!=null){%> <%=empNameAndDesi[0]%> (<%=empNameAndDesi[1]%>)<%}%>
						</b></small> </h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item active" aria-current="page">
							Vehicle Parking </li>
				</ol>
			</div>
		</div>
	</div>

	<div class="page card dashboard-card">

		<div align="center">
			<%
			if (ses1 != null) {
			%>
			<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
				<%=ses1%>
			</div>
			<%
			}
			if (ses != null) {
			%>
			<div class="alert alert-success" role="alert"
				style="margin-top: 5px;">
				<%=ses%>
			</div>
			<%
			}
			%>
		</div>


		<div class="card">
			<div class="card-body">
				<!-- <h5>Vehicle Parking List</h5> -->
				<hr>
				<form action="#">
					<div class="table-responsive">
						<table
							class="table table-bordered table-hover table-striped table-condensed"
							id="myTable1">
							<thead>
								<tr>
									<th style="width: 5%;">Select</th>
									<th>Vehicle No</th>
									<th>From Date</th>
									<th>From Time</th>
									<th>To Date</th>
									<th>To Time</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
							</thead>
							<tbody>
								<%
								if (VehicleParkList != null && VehicleParkList.size() > 0) {
									for (Object ls[] : VehicleParkList) {
								%>
								<%-- <%
								if ((format.parse(DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())).after(format.parse(fromdate))
										&& format.parse(DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())).before(format.parse(todate))) ) {
								%> --%>

								<tr>
									<%
									if (ls[7].toString().equalsIgnoreCase("INI") || ls[7].toString().equalsIgnoreCase("RSO")) {
									%>
									<td style="text-align: center;"><input type="radio"
										name="vehicleAppId" value="<%=ls[0]%>" required="required"></td>
									<%
									} else {
									%>
									<td style="text-align: center;"><input type="radio"
										name="vehicleAppId" value="<%=ls[0]%>" required="required"
										disabled="disabled"></td>
									<%
									}
									%>
									<td><%=ls[3]%></td>
									<td style="text-align: center;"><%=DateTimeFormatUtil.getDateTimeToRegularDate(ls[4].toString())%></td>
									<td style="text-align: center;"><%=time.format(ls[4])%></td>
									<td style="text-align: center;"><%=DateTimeFormatUtil.getDateTimeToRegularDate(ls[5].toString())%></td>
									<td style="text-align: center;"><%=time.format(ls[5])%></td>
									<td>
										<%
										if (ls[6] != null) {
										%> <%
 if (ls[6] != null && ls[6].toString().equalsIgnoreCase("A")) {
 %>
										<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="VehTransacStatus.htm" value="<%=ls[0]%>"
											name="vehicleAppId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style="color: green; font-weight: 600;" formtarget="_blank">
											&nbsp; Approved <i
												class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button>  <%
 } else {
 %>
										<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="VehTransacStatus.htm" value="<%=ls[0]%>"
											name="vehicleAppId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style=" color: <%=ls[0]%>; font-weight: 600;"
											formtarget="_blank">
											&nbsp;
											<%=ls[9]%>
											<i class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button> <%
 }
 %> <%
 }
 %>
									</td>
									<td align="center"><button type="submit" class="btn btn-sm view-icon"
											formaction="GetVehicleParkingForm.htm" name="vehicleAppId"
											value="<%=ls[0]%>" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top"
											title="Form For Vehicle Parking Application"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button>
										<button type="submit" class="btn btn-sm" name="vehicleAppId"
											value="<%=ls[0]%>"
											formaction="VehicleParkFormSubmitAndPrint.htm"
											formtarget="blank" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top" title="Download">
											<i style="color: #019267" class="fa-solid fa-download"></i>
										</button></td>
								</tr>
								<%
								}
								/* } */
								}
								%>
							</tbody>
						</table>
					</div>
					<div>
						<div align="center">
							<button type="submit" class="btn btn-sm add-btn"
								formnovalidate="formnovalidate"
								formaction="VehicleParkAddEdit.htm" name="Action" value="Add">Add</button>
							<%
							if (VehicleParkList != null && VehicleParkList.size() > 0) {
							%>
							<button type="submit" formaction="VehicleParkAddEdit.htm"
								class="btn btn-sm edit-btn" name="Action" value="Edit"
								Onclick="EditPer()">Edit</button>
							<%
							}
							%>
						</div>
					</div>
					<input type="hidden" name="TypeOfAction" value="All">
				</form>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$("#myTable1").DataTable({
			"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
			"pagingType" : "simple"

		});

		function EditPer() {

			var fieldsperadd = $("input[name='vehicleAppId']").serializeArray();

			if (fieldsperadd.length === 0) {
				alert("Please Select Atleast One Item");

				event.preventDefault();
				return false;
			}
			return true;
		}

		$('#fromdate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			/* "minDate" :datearray,   */
			/* "startDate" : fdate, */
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});

		$('#todate').daterangepicker({
			"singleDatePicker" : true,
			"linkedCalendars" : false,
			"showCustomRangeLabel" : true,
			"minDate" : $("#fromdate").val(),
			"cancelClass" : "btn-default",
			showDropdowns : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
	</script>
</body>
</html>
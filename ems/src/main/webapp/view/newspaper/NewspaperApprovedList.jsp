<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDate"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>

	<%
List<Object[]> NewspaperClaimApprovedList=(List<Object[]>)request.getAttribute("NewspaperClaimApprovedList");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Newspaper Approved List</h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm">
							Newspaper </a></li>
					<li class="breadcrumb-item active " aria-current="page">Newspaper
						List</li>
				</ol>
			</div>
		</div>
	</div>

	<div align="center">
		<%String ses=(String)request.getParameter("result"); 
			String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null){ %>
		<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
			<%=ses1 %>
		</div>

		<%}if(ses!=null){ %>

		<div class="alert alert-success" role="alert" style="margin-top: 5px;">
			<%=ses %>
		</div>
		<%} %>
	</div>

	<div class="page card dashboard-card">
		<div class="card-body">
			<form action="" method="post">
				<table id="addDataTable"
					class="table table-hover table-striped  table-condensed  table-bordered  ">
					<thead>
						<tr>
							<th style="width: 5%;">Select</th>
							<th>Approved Date</th>
							<th>From Date</th>
							<th>To Date</th>
							<th>Total Amount</th>

						</tr>
					</thead>
					<tbody>
						<%if(NewspaperClaimApprovedList!=null&&NewspaperClaimApprovedList.size()>0){ 
		                        for(Object ls[]:NewspaperClaimApprovedList){%>
						<tr>
							<td style="text-align: center;"><input type="radio"
								name="NewspaperBillId" value="<%=ls[0]%>" required="required"></td>

							<td>
								<%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[1].toString()));%>
							</td>
							<td>
								<%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[2].toString()));%>
							</td>
							<td>
								<%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[3].toString()));%>
							</td>
							<td><%=ls[4]%></td>

						</tr>
						<%}} %>
					</tbody>
				</table>

				<div style="margin-left: 0px; margin-top: 0px;">
					<%if(NewspaperClaimApprovedList!=null&&NewspaperClaimApprovedList.size()!=0){%>
					<button type="submit" class="btn btn-warning"
						formaction="NewsApprovalPeriodEdit.htm">Edit Approval
						Period</button>
					<button type="submit" class="btn btn-info"
						formaction="NewspaperReportPrint.htm" formtarget="blank">Print
						Report</button>
					<button type="submit" class="btn btn-info"
						formaction="NewspaperContingentBillPrint.htm" formtarget="blank">Print
						Contingent bill</button>
					<button type="submit" class="btn btn-info"
						formaction="NewsPaperExpSancReport.htm" formtarget="blank">Expenditure
						Sanction</button>

					<%}%>

				</div>
				<div style="margin-top: 50px; ">
					<select class="form-control " style="width: 110px;"
						name="ClaimMonth">
						<option>JAN-JUN</option>
						<option>JUL-DEC</option>
					</select> <input type="text" id="selectYear" required="required"
						class="form-control input-sm"
						style="width: 70px; margin-left: 115px; margin-top: -35px; margin-bottom: 10px"
						name="ClaimYear" value="<%=LocalDate.now().getYear() %>"
						readonly="readonly" placeholder="Year" />
					<button type="submit" class="btn btn-success"
						formaction="NewsPaperFinalAppPrint.htm" formtarget="blank"
						formnovalidate="formnovalidate">Newspaper Final Approval</button>
				</div>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>

		</div>
	</div>

	<script type="text/javascript">
$("#selectYear").datepicker({
	autoclose: true,
    format: "yyyy",
    viewMode: "years", 
    minViewMode: "years",
    endDate: new Date(),
    setDate : new Date(),
});
</script>
</body>
</html>

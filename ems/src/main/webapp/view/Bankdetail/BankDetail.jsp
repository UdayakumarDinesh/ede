<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Bank Details</title>
<style type="text/css">
#myTable1 {
	width: 75%;
}
</style>
</head>
<body>

	<%
	List<Object[]> BankDetailList = (List<Object[]>) request.getAttribute("BankDetailList");

	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");
	%>

	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Bank Details</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="BankDetails.htm">
							Bank Details </a></li>
					<!-- <li class="breadcrumb-item active " aria-current="page">Telephone
						List</li> -->
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




		<div align="center">
			<form action="#">
				<div>
					<table
						class="table table-bordered table-hover table-striped table-condensed"
						id="myTable1">
						<thead>
							<tr>
								<th style="width: 5%;">Select</th>
								<!-- <th>Employee Name</th> -->
								<th>Bank Name</th>
								<th>Branch</th>
								<th>IFSC</th>
								<th>Account No</th>
							</tr>
						</thead>
						<tbody>
							<%
							if (BankDetailList != null && BankDetailList.size() > 0) {
								for (Object ls[] : BankDetailList) {
							%>
							<tr>
								<td style="text-align: center;"><input type="radio"
									name="bankId" value="<%=ls[0]%>" required="required"></td>
								<%-- <td><%=ls[1]%></td> --%>
								<td><%=ls[2]%></td>
								<td><%=ls[3]%></td>
								<td><%=ls[4]%></td>
								<td><%=ls[5]%></td>
							</tr>
							<%
							}
							}
							%>
						</tbody>
					</table>
				</div>
				<div>
					<div align="center">
						<button type="submit" class="btn btn-sm add-btn"
							formnovalidate="formnovalidate" formaction="BankDetailAddEdit.htm"
							name="Action" value="Add">Add</button>
						<%
						if (BankDetailList != null && BankDetailList.size() > 0) {
						%>
						<button type="submit" formaction="BankDetailAddEdit.htm"
							class="btn btn-sm edit-btn" name="Action" value="Edit">Edit</button>
						<button type="submit" class="btn btn-sm delete-btn" formaction="BankDetailRemove.htm"
							onclick="return  FunctionToCheckDelete()">Remove</button>
						<%
						}
						%>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
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
<style type="text/css">
.trup {
	padding: 5px 10px 0px 10px;
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
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
<body>
	<%
List<Object[]> NewspaperClaimList=(List<Object[]>)request.getAttribute("NewspaperClaimList");
String DGM=(String) request.getAttribute("DGM");
String PO=(String) request.getAttribute("PO");
String AO=(String) request.getAttribute("AO");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Newspaper Claims</h5>
			</div>
			<div class="col-md-9 ">
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
		<div class="card">
			<div class="card-body">
				<form action="#" method="post">
					<table
						class="table table-hover table-striped  table-condensed  table-bordered"
						id="myTable">
						<thead>
							<tr>
								<th style="width: 5%;">Select</th>
								<th>Claim Date</th>
								<th>Month Period</th>
								<th>Year</th>
								<th>Claim Amount</th>
								<th>Admissible Amount</th>
								<th>Payable Amount</th>
								<th>Status</th>
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<%if(NewspaperClaimList!=null&&NewspaperClaimList.size()>0){ 
							    	for(Object ls[]:NewspaperClaimList){%>
							<tr>
								<td style="text-align: center;"><input type="radio"
									name="NewspaperId" value="<%=ls[0]%>" required="required"></td>
								<td>
									<%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[8].toString()));%>
								</td>
								<td><%=ls[1]%></td>
								<td><%=ls[2]%></td>
								<td><%=ls[3]%></td>
								<td><%=ls[4]%></td>
								<td><%=ls[5]%></td>
								<%-- <td><%if(0!=(Integer.parseInt(ls[6].toString()))&&ls[7]!=null){%><span class="label label-success">Approved</span><%}else{%><span class="label label-primary">User Applied</span><%}%></td> --%>
								<td>
									<button type="submit" class="btn btn-sm btn-link w-100"
										formaction="BankDetTransacStatus.htm" value="<%=ls[0]%>"
										name="bankId" data-toggle="tooltip" data-placement="top"
										title="Transaction History" formnovalidate="formnovalidate"
										style="color: <%=ls[11]%>; font-weight: 600;"
										formtarget="_blank">
										&nbsp;
										<%=ls[10]%>
										<i class="fa-solid fa-arrow-up-right-from-square"
											style="float: right;"></i>
									</button>
								</td>
								<td align="center"><button type="submit"
										class="btn btn-sm view-icon"
										formaction="NewspaperClaimPreview.htm" name="NewspaperId"
										value="<%=ls[0]%>" formnovalidate="formnovalidate"
										data-toggle="tooltip" data-placement="top" formtarget="_blank"
										title="Form For Newspaper Claim" style="font-weight: 600;">
										<i class="fa-solid fa-eye"></i>
									</button>
									<button type="submit" class="btn btn-sm" name="NewspaperId"
										value="<%=ls[0]%>" formaction="NewspaperPrint.htm"
										 formnovalidate="formnovalidate" formtarget="_blank"
										data-toggle="tooltip" data-placement="top" title="Download">
										<i style="color: #019267" class="fa-solid fa-download"></i>
									</button></td>
							</tr>
							<%}}else{ %>

							<%} %>
						</tbody>
					</table>

					<div class="row">
						<div class="col-md-12" align="center">
							<button type="submit" class="btn btn-primary"
								formaction="NewspaperView.htm" name="AddNewspaper"
								value="AddNewspaper" formnovalidate="formnovalidate">Add</button>
							<%if(NewspaperClaimList!=null && NewspaperClaimList.size()!=0){%>
							<button type="submit" class="btn btn-warning"
								formaction="NewspaperEditView.htm">Edit</button>
							<!-- <button type="submit" class="btn btn-info" formaction="NewspaperPrint.htm" formtarget="_blank">Print</button> -->
							<%}%>
						</div>
					</div>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
<hr>
				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<b> Newspaper Approval Flow</b>
					</div>
				</div>

				<div>
					<div class="row"
						style="text-align: center; padding-top: 10px; padding-bottom: 15px;">
						<table align="center">
							<tr>
								<td class="trup"
									style="background: linear-gradient(to top, #3c96f7 10%, transparent 115%);">
									User <br> <%=session.getAttribute("EmpName")%>
								</td>
								<td rowspan="2"><i class="fa fa-long-arrow-right "
									aria-hidden="true"></i></td>

								<td class="trup"
									style="background: linear-gradient(to top, #FBC7F7 10%, transparent 115%);">
									DGM <br><%if (DGM != null) {%> <%=DGM%> <%} %>
								</td>

								<td rowspan="2"><i class="fa fa-long-arrow-right "
									aria-hidden="true"></i></td>
								<td class="trup"
									style="background: linear-gradient(to top, #4DB6AC 10%, transparent 115%);">
									PO <br> <%if (PO != null) {%> <%=PO%> <%} %>
								</td>
								<td rowspan="2"><i class="fa fa-long-arrow-right "
									aria-hidden="true"></i></td>

								<td class="trup"
									style="background: linear-gradient(to top, #6ba5df 10%, transparent 115%);">
									AO <br> <%if ( AO != null) {%> <%=AO%> <%} %>
								</td>

							</tr>
						</table>
					</div>
				</div>

			</div>
		</div>

	</div>

</body>

</html>
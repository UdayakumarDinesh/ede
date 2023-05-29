<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>APF Apply</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
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
	List<Object[]> AllAPFList = (List<Object[]>) request.getAttribute("AllAPFList");
	Object[] empNameAndDesi = (Object[]) request.getAttribute("empNameAndDesi");
	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");
	%>
	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-5">
				<h5>
					APF Apply <small><b>&nbsp; - &nbsp;<%
				if (empNameAndDesi != null) {
				%>
							<%=empNameAndDesi[0]%> (<%=empNameAndDesi[1]%>)<%
							}
							%>
					</b></small>
				</h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="PayrollDashBoard.htm">
							Payroll Dashboard </a></li>
					<li class="breadcrumb-item active" aria-current="page">APF
						Apply</li>
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

				<form action="#">
					<div class="table-responsive">
					
						<table class="table table-bordered table-hover table-striped table-condensed" id="myTable1">
							<thead>
								<tr>
									<th style="width: 5%;">Select</th>
									<th>From Date</th>
									<th>To Date</th>
									<th>Amount</th>

								</tr>
							</thead>
							<tbody>
							<%if(AllAPFList != null && AllAPFList.size()>0) {%>
							<% for(Object[] obj : AllAPFList) {%>
								<tr>
									<td style="text-align: center;"> <input type="radio" name="APFappId" value="<%=obj[0] %>"> </td>
									<td style="text-align: center;"><%=obj[2] %></td>
									<td style="text-align: center;"><%=obj[3] %></td>
									<td style="text-align: right;"><%=obj[4] %></td>
								</tr>
							<%}} %>

							</tbody>
						</table>
						
					</div>
					<div>
						<div align="center">
							<button type="submit" class="btn btn-sm add-btn"
								formnovalidate="formnovalidate"
								formaction="VehicleParkAddEdit.htm" name="Action" value="Add">Add</button>
							<%
							if (AllAPFList != null && AllAPFList.size() > 0) {
							%>
							<button type="submit" formaction="VehicleParkAddEdit.htm"
								class="btn btn-sm edit-btn" name="Action" value="Edit"
								Onclick="EditPer()">Edit</button>
							<%
							}
							%>
						</div>
					</div>
				</form>
			</div>
		</div>


	</div>

<script type="text/javascript">
("#myTable1").DataTable({
	"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
	"pagingType" : "simple"
});
</script>
</body>
</html>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
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
body {
	background-image: url("view/images/d7.jpg");
}
</style>
</head>
<body>


	<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Payroll Dashboard</h5>
			</div>
			<div class="col-md-6 ">
				<ol class="breadcrumb ">
					<li class="breadcrumb-item ml-auto"><a
						href="MainDashBoard.htm"><i
							class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item active " aria-current="page">Payroll
						Dashboard</li>
				</ol>
			</div>
		</div>
	</div>

	<%
	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");
	if (ses1 != null) {
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
			<%=ses1%>
		</div>
	</div>
	<%
	}
	if (ses != null) {
	%>
	<div align="center">
		<div class="alert alert-success" role="alert">
			<%=ses%>
		</div>
	</div>
	<%
	}
	%>

	<div class="page card dashboard-card" style="background: transparent;">
		<div class="card-body" align="center">
			<h3>Payroll Dashboard</h3>


		</div>
	</div>



	<script>
		
	</script>

</body>
</html>
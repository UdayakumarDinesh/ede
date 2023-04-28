<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
body{
 background-image: url("view/images/d3.jpg");
}
</style>
</head>
<body>
<%List<Object[]> list = (List<Object[]>)request.getAttribute("dashboard"); %>

	<div class="card-header page-top" >
		<div class="row">
			<div class="col-md-3">
				<h5>Tour DASHBOARD</h5>
			</div>
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Tour</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	
		
		<form action="#" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="page card dashboard-card" style="background: transparent;">
				<div class="card-body " align="center" style="background: transparent;">
						<h3>Tour Dashboard Details</h3>
				</div>
			</div>		
		</form>
</body>
</html>
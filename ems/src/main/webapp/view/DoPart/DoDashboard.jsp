<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>DO</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">


</style>
</head>
<body>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>DO Dashboard  </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="DoDashboard.htm">DO</a></li>
					</ol>
				</div>
			</div>
</div>	

<%
String ses=(String)request.getParameter("result"); 
String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	<div align="center"> 
			<div class="alert alert-danger" role="alert"><%=ses1%></div>
	</div>
	<%}if(ses!=null){%>
	<div align="center">
	        <div class="alert alert-success" role="alert"><%=ses%></div>
    </div>
    <%}%>

<div class="page card dashboard-card">
   <div class="card-body" align="center">
  		<h3>DO Dashboard</h3>
   

   </div>
</div>
</body>
</html>
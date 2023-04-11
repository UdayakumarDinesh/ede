<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MT orders</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
 <style type="text/css">
body{
 background-image: url("view/images/c2.jpg");
} 
</style>
</head>
<body>
<%String empname=(String)request.getAttribute("empname"); %>
<div class="card-header page-top " >
		<div class="row" style="background: transparent;">
			<div class="col-md-6" style="background: transparent;">
				<h5>MT Dashboard <small> <%if(empname!=null){%> <%=empname %> <%}%></small> </h5>
			</div>
				<div class="col-md-6" style="background: transparent;">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
					</ol>
				</div>
			</div>
</div>	

<%


String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>


<div class="page card dashboard-card" style="background: transparent;">
   <div class="card-body" align="center" style="background: transparent;">
  		<h3>MT Dashboard</h3>
   

   </div>
</div>
</body>
</html>
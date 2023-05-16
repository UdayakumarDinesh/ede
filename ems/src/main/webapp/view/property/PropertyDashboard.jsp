<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
/* body{
  background-image: url("view/images/a4.jpg"); 

} */
</style>

</head>
<body>
<%
Object[] empData=(Object[])request.getAttribute("EmpData");
%>
	<div class="card-header page-top" >
		<div class="row">
			<div class="col-md-7">
				<h5>PROPERTY DASHBOARD<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-5" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Property</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	
		<div align="center">
				<%String ses=(String)request.getParameter("result"); 
				String ses1=(String)request.getParameter("resultfail");
				if(ses1!=null){ %>
					<div class="alert alert-danger" role="alert">
						<%=ses1 %>
					</div>
					
				<%}if(ses!=null){ %>
					
					<div class="alert alert-success" role="alert">
						<%=ses %>
					</div>
				<%} %>
		</div>
		<form action="#" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="page card dashboard-card" style="background: transparent;">
				<div class="card-body " align="center">
				
						<h3>Property Dashboard Details</h3>
				
					
				</div>
			</div>		
		</form>
		
	
	


</body>
</html>
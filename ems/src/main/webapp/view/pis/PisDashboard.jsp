<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%List<Object[]> list = (List<Object[]>)request.getAttribute("dashboard"); %>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>PIS DASHBOARD</h5>
			</div>
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">PIS</li>
				    <!-- <li class="breadcrumb-item"><a href="#">Library</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Data</li> -->
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
			<div class="page card dashboard-card" >
				<div class="card-body " align="center">
				
						<h3>PIS Dashboard Details</h3>
				
					<%-- <div class="row" > 
						<!-- <div class="col-md-3">
							<button type="submit" class=" db-button w-100" formaction="PisAdminEmpList.htm" >Employee Details</button>
						</div>
						<div class="col-md-3">
							<button type="submit" class=" db-button w-100" formaction="LoginMaster.htm" >Login Master</button>
						</div>
						
						<div class="col-md-3">
							<button type="submit" class=" db-button w-100" formaction="##" >Role</button>
						</div> -->
						
						<%if(list!=null){ for(Object[] o:list){%>
						
						<div class="col-md-3" style="margin-bottom: 20px;">
							<button type="submit" class=" db-button w-100" formaction="<%=o[1] %>" ><%=o[0] %></button>
						</div>
						
						<%}}%>
					</div> --%>
				</div>
			</div>		
		</form>
</body>
</html>
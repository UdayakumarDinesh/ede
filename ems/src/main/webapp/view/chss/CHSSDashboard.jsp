<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"   import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>

<% String logintype = (String) session.getAttribute("LoginType"); 
	List<Object[]> dashboard = (List<Object[]>)request.getAttribute("dashboard");
%>

 <div class="col page">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS DASHBOARD</h5>
			</div>
			<div class="col-md-9 " >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
				    <li class="breadcrumb-item active " aria-current="page">CHSS</li>
				  </ol>
				</nav>
			</div>			
		</div>
	</div>	
	<div class="card-body" >
		<form action="#" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="card" >
				<div class="card-body " >
					<div class="row" > 
						<%-- <div class="col-md-3 ">
							<button type="submit" class=" db-button w-100" formaction="CHSSApply.htm" >CHSS Apply</button>
						</div>
						<div class="col-md-3 ">
							<button type="submit" class=" db-button w-100" formaction="CHSSAppliedList.htm" >CHSS List</button>
						</div>
						<%if( logintype.equalsIgnoreCase("K") || logintype.equalsIgnoreCase("V") ){ %>
							<div class="col-md-3 ">
								<button type="submit" class=" db-button w-100" formaction="CHSSApprovalsList.htm" >CHSS Approvals</button>
							</div>

						<%} %> --%>
						
						
						<%if(dashboard!=null){  for(Object[] O:dashboard){%>							
							 <div class="col-md-3 ">
								<button type="submit" class=" db-button w-100" formaction="<%=O[1]%>" ><%=O[0]%></button>
							</div>
						<%}}%>

					</div>
				</div>
			</div>		
		</form>
		
	</div>

 </div>

</body>
</html>
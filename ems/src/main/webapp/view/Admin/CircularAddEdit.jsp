<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Circular Add</title>
</head>
<body>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Circular Add</h5>
			</div>
				<div class="col-md-9">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="circulatAddEditList.htm"> Circular List </a></li>
                        <li class="breadcrumb-item active " aria-current="page">Circular Add</li>
					</ol>
			   </div>
		</div>
	</div>
		 
		 	 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="ChssTestSub.htm" method="POST" id="myfrm1" autocomplete="off">
						<div class="form-group">
							
						</div>

						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					</form>
				</div>
	   </div>
	</div>

</div>
		 
		 
		 
		 
		 
		 
</body>
</html>
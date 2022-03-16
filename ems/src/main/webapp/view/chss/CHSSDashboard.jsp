<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>

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
				    <!-- <li class="breadcrumb-item"><a href="#">Library</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Data</li> -->
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
						<div class="col-md-3">
							<button type="submit" class=" db-button " formaction="CHSSApply.htm" >CHSS Apply</button>
						</div>
						<div class="col-md-3">
							<button type="submit" class=" db-button " formaction="CHSSAppliedList.htm" >CHSS List</button>
						</div>
					</div>
				</div>
			</div>		
		</form>
		
	</div>

 </div>

</body>
</html>
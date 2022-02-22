<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="dependancy.jsp"></jsp:include>
<title>LogIn</title>
</head>

<body style="background-color:#D22779; ">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 " >
				<div class="col-md-4 float-right align-middle" style="margin-top: 15rem;margin-right:5rem; ">
					<div class="card" style="padding: 35px;">
						<div align="center">
							<h2 class="heading-section ">EMS</h2>
						</div>
						
						<div align="center" >
							<form action="${contPath}/login" autocomplete="off" method="post" >
							
								<div class="form-group">
									<input type="text" class="form-control"  name="username" placeholder="username" required="required">
								</div>
								
								<div class="form-group">
									<input type="password" class="form-control" name="password" placeholder="password" required="required">
								</div>
								
								<div class="form-group">
									<button type="submit" class="form-control btn btn-primary submit ">Log In</button>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
								</div>						
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>			
	</div>



</body>
</html>
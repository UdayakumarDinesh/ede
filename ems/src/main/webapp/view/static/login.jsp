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

<style type="text/css">



</style>

</head>

<body style="background-color:#980F5A;">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12 " >
				<div class="col-md-3 float-right align-middle" style="margin-top: 12rem;margin-right:5rem; ">
					<div class="card" style="padding: 35px;box-shadow: -4px 4px 5px black;">
						<div align="center">
							<h2 class="heading-section ">EMS</h2>
						</div>
						
						<div >
							<form action="${contPath}/login" autocomplete="off" method="post" >
							
								<div class="form-group">
									 <label for="username">Username</label>
									<input type="text" class="form-control"  name="username" placeholder="username" required="required">
								</div>
								
								<div class="form-group">
									<label for="password">Password</label>
									<input type="password" class="form-control" name="password" placeholder="password" required="required">
								</div>
								<span style="font-family: 'Lato', sans-serif;font-size: 15px;color:red;margin-bottom: 10px;" id="success-alert">${error}</span>
								<div class="form-group ">
									<button type="submit" class="form-control btn btn-primary submit  " onmouseover ="this.style.backgroundColor = '#134d61' " onmouseout ="this.style.backgroundColor = '#08293c' "  style="border-radius: 20px; background-color: #08293c; border-color:#980F5A " ><b>Login</b></button>
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
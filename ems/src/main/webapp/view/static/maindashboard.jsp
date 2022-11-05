<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="org.bouncycastle.util.Arrays"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<jsp:include page="../static/header.jsp"></jsp:include>
	<style>
	</style>

	</head>

	<body  >
	<%	
		List<Object[]> emplogintypelist     = (List<Object[]>)request.getAttribute("logintypeslist");
		String logintype   = (String)session.getAttribute("LoginType");
	%>
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<h5 style="padding-top: 0.5rem">DASHBOARD </h5>
			</div>
			<div class="col-md-9">
					<form action="EmpLogitypeChange.htm" method="post" style="float: right;">
							
								<b>Login As : &nbsp;</b> 
								<select class="form-control select2" name="logintype" onchange="this.form.submit();" style="margin-top: -5px;width: 200px;">
									<%for(Object[] login:emplogintypelist){ %>
										<option value="<%=login[0]%>" <%if(logintype.equalsIgnoreCase(login[0].toString())){ %>selected <%} %>><%=login[1]%></option>
									<%} %>
								</select>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							
					</form> 
				
			</div>
		</div>
	</div>	


	<div class="card dashboard-card" >
		<div class="card-body " >
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
			<div class="container-fluid">
				
				<div class="row">
					<div class="col-md-12" align="center">
						<h1>WELCOME</h1>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-12" align="center">
						<h1 style="font-weight: 900 !important;  font-size: 90px;  color: #1c6abd;">SEIP</h1>
					</div>
				</div>
			  
			</div>
		</div>
	
	</div>
	 

</html>
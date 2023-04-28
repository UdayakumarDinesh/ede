<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.vts.ems.utils.IndianRupeeFormat"%>
<%@page import="org.bouncycastle.util.Arrays"%>
<%@page import="org.apache.commons.lang3.ArrayUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<jsp:include page="../static/header.jsp"></jsp:include>
	</head>

	<%	
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");	
		List<Object[]> emplogintypelist     = (List<Object[]>)request.getAttribute("logintypeslist");
		String logintype   = (String)session.getAttribute("LoginType");
	%>
	
	<div class="card-header page-top"   style="padding: 0.25rem 1.25rem;">
		<div class="row">
			<div class="col-md-3">
				<h5 style="padding-top: 0.5rem;color: #009851;" >Dashboard</h5>
			</div>
			<div class="col-md-9">
				<%if(!logintype.equalsIgnoreCase("CE")){ %>
					<form action="EmpLogitypeChange.htm" method="post" style="float: right;">
						<div class="form-inline">
							<b>Login As : &nbsp;</b> 
							<select class="form-control select2" name="logintype" onchange="this.form.submit();" style="margin-top: -5px;width: 200px;align:right;">
								<%for(Object[] login:emplogintypelist){ %>
									<option value="<%=login[0]%>" <%if(logintype.equalsIgnoreCase(login[0].toString())){ %>selected <%} %>><%=login[1]%></option>
								<%} %>
							</select>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</div>
					</form> 
				<%} %>
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
			
			
			
			
			
		</div>
				
	</div>				
 
</body>
</html>
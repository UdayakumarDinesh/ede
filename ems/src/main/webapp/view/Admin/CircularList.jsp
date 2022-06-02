<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*" %> <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Circular List</title>
</head>

<body>
<%List<Object[]>  circular = (List<Object[]>)request.getAttribute("circulatlist");%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Circular List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">Circular List</li>
					</ol>
				</div>
			</div>
		 </div>
		  <%List<Object[]> circularlist = (List<Object[]>)request.getAttribute("circulatlist"); %> 
		 	 <div class="page card dashboard-card">
	<div class="card-body" >		
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
		
			<div class="card" >
			
				<div class="card-body " >
				
					<form action="circulatAddEditList.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<th>Select</th>
										<th>Description </th>
										<th>FromDate</th>
										<th>ToDate</th>
									</tr>
								</thead>
								<tbody>
									<%if(circularlist!=null && circularlist.size()>0){ 
										for(Object[] obj : circularlist){
									%>
										<tr>
											<td style="text-align:center;  width: 5%;"> <input type="radio" name="circulatId" value="<%=obj[0]%>"> </td>
											<td style="text-align:justify; width: 70%;"><%=obj[1]%></td>
											<td style="text-align:justify; width: 12%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%> <%} %></td>
											<td style="text-align:justify; width: 12%;"><%if(obj[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString())%> <%} %></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						
						<button type="submit" class="btn btn-sm add-btn" name="action" value="ADD" >ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
								
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
</body>

</html>
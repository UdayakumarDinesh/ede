<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.util.*" %> <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Circular</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%List<Object[]> circularlist = (List<Object[]>)request.getAttribute("circularlist");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Circular List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						
						
						<li class="breadcrumb-item active " aria-current="page">Circular List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 	 <div class="page card dashboard-card">
	<div class="card-body" >		
			<div class="card" >
				<div class="card-body main-card  " >
				
					<form action="##" method="POST" id="empForm" >
						
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										<th>SN</th>
										<th>Reference No</th>
										<th>Description </th>
										<th>Circular Date</th>
										<th>Download</th>										
									</tr>
								</thead>
								<tbody>
									<%if(circularlist!=null && circularlist.size()>0){ 
										int sn=0;
										for(Object[] obj : circularlist){
									%>
										<tr>
											<td align="center"><%=++sn %></td>
										    <td style="text-align:center;  width: 15%;"><%=obj[4]%></td>
											<td style="text-align:justify; width: 50%;"><%if(obj[1]!=null){%><%=obj[1]%><%}%></td>
											<td align="center"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%> <%} %></td>
											<td style="text-align:center;  width: 10%;"> <%if(obj[2]!=null){ %> 
											<button type="submit" class="btn btn-sm" name="path1" value="<%=obj[2]%>" formaction="download-CircularFile"  formmethod="post" title="Download">
											  <i style="color: #019267" class="fa-solid fa-download"></i>
										    </button>
											<%}else{%>--<%}%></td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>			
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	       </div>
</body>
</html>
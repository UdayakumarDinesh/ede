<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Tour Apply List</title>
</head>
<body>
<%
List<Object[]>  cacncellist = (List<Object[]>)request.getAttribute("CacncelList");
Object[] empdata = (Object[])request.getAttribute("Empdata");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour Canceled List <small><b>&nbsp;&nbsp; &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourProgram.htm">Tour</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour List</li>
					</ol>
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

<div class="container-fluid">	
<div class="card">
	<div class="card-body">
<form action="##" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
								  <tr>
								  	  <th>SN</th>
								  	  <th>Tour No</th>
									  <th>Date</th>
								      <th>Applied On</th>
								      <th>Name</th>
								      <th>Purpose</th>
								      <th>Status</th>
								  </tr>
					  </thead>
	                  <tbody>
	  
							 <%if(cacncellist!=null&&cacncellist.size()!=0){
						                            	 int sn=0;  
                               for(Object[] hlo :cacncellist){
                            	   String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(hlo[1].toString());
                                   String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(hlo[2].toString());
                                   String  applydate=DateTimeFormatUtil.fromDatabaseToActual(hlo[3].toString());
                                   long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(hlo[1].toString(), hlo[2].toString());
                            	   %>
	                             <tr>
	                             	  <td align="center"> <%=++sn %></td>
	                             	   <td>
	                             	   <%if(hlo[10]!=null && hlo[6]!=null && hlo[6].toString().equalsIgnoreCase("CAP")){%>
	                             	  <button type="submit" class="btn btn-sm btn-link w-100 " formaction="TourApplyReport.htm" formmethod="get" name="tourapplyid" value="Cancel/<%=hlo[7]%>" formtarget="_blank" 
													  style=" color:<%=hlo[10]%>; font-weight: 600;" ><%=hlo[10]%></button>
									  
	                             	  <%}else{ %>
	                             	    <button class="btn btn-sm btn-link w-100 ">--</button>
	                             	  <%}%>
	                             	   </td>
									  <td align="center"><%=stayfromdate%> To <%=staytodate%></td>
                               		  <td align="center"><%=applydate %><br/> for <%=noofdays%> Day(s)</td>
									  <td align="center"><%=hlo[0]+"" %></td>
									  <td><%=hlo[4]+"" %></td>
									  <td align="center" style=" color:<%=hlo[9]%>; font-weight: 600;">&nbsp;<%=hlo[8]%> </td>			  
								</tr> 
                  <%}}%>
	            </tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
				</form>	
	</div>
</div>	
</div>

</body>

</html>
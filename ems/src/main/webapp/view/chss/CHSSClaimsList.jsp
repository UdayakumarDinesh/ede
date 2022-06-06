<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>CHSS Claims List</title>
</head>
<body>
<body>
<%
List<Object[]> claimslist = (List<Object[]>)request.getAttribute("claimslist");
List<Object[]> emplist = (List<Object[]>)request.getAttribute("emplist");
String fromdate = (String)request.getAttribute("fromdate");
String todate   = (String)request.getAttribute("todate");
String empid = (String)request.getAttribute("empid");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Claims List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Claims List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
 <div class="page card dashboard-card">	

			<div class="card" >
			
				<div class="card-header" style="height: 4rem">
					<form action="ClaimsList.htm" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row justify-content-end">
					 <div class="col-1" align="right"></div>
						<div class="col-2"><h6>Employee </h6></div>
						<div class="col-2" align="left" style="margin-left: -10%;"><select class="form-control form-control select2" name="empname"   required="required" id="empname" >
					    	<%if(emplist!=null){ %>
						    <%for(Object[] obj:emplist){%>
							<option value="<%=obj[0]%>-<%=obj[3]%>"<% if(obj[0].toString().equalsIgnoreCase(empid)){%> selected<%}%> ><%=obj[3]%></option>
							<%}}%>
							</select>
					   </div>
					
						     <div class="col-2"  ><h6>From Date :</h6></div>
					         <div class="col-1" style="margin-left: -10%;"> 
								    <input type="text" style="width: 145%;"  class="form-control input-sm mydate"   readonly="readonly" <%if(fromdate!=null){ %> value="<%= fromdate %>" <%} %>   id="fromdate" name="fromdate"  required="required"  > 
								    <label class="input-group-addon btn" for="testdate"></label>              
							 </div>
							 
							  <div class="col-2"  style="margin-left: 1%;"><h6>To Date :</h6></div>
							  <div class="col-1" style="margin-left: -10%;">						
								     <input type="text" style="width: 145%;"  class="form-control input-sm mydate"  readonly="readonly"  <%if(todate!=null){ %> value="<%=todate%>" <%} %> id="todate" name="todate"  required="required"  > 							
							 		 <label class="input-group-addon btn" for="testdate"></label>    
							 </div>

							 <div class="col-3" align="right"></div>
						
							
					</div>
							 
				   </form>
				
				</div>
			
				<div class="card-body">
			 	

					<form action="##" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>From-Employee</th>
										<th>To-Employee</th>
										<th>Applied Date</th>
								
									</tr>
								</thead>
								<tbody>
							
										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>

			   </form>		
			  </div>
		   	 </div>				
	        </div>
	       
</body>
<script type="text/javascript">





$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	 "startDate" : <%=fromdate%>, 
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		 "startDate" : <%=todate%>, 
		"minDate" :$("#fromdate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});





</script>
</html>
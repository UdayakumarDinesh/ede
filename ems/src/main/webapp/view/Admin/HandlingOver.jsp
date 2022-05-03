<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Handling Over</title>
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>
<%
List<Object[]> handingoverlist = (List<Object[]>)request.getAttribute("handlingoverlist");
String fromdate = (String)request.getAttribute("fromdate");
String todate   = (String)request.getAttribute("todate");
String treat = (String)request.getAttribute("treat");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5> Handing Over List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item active " aria-current="page">Handing Over List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
 <div class="page card dashboard-card">	
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
			
				<div class="card-header" style="height: 4rem">
					<form action="HandingOver.htm" method="GET">
					<div class="row justify-content-end">
					
						     <div class="col-2"  align="right"><h6>From Date :</h6></div>
					         <div class="col-1"> 
								    <input type="text" style="width: 145%;"  class="form-control input-sm mydate"  onchange="this.form.submit()" readonly="readonly" <%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>   id="fromdate" name="fromdate"  required="required"  > 
								    <label class="input-group-addon btn" for="testdate"></label>              
							 </div>
							 
							  <div class="col-2" align="right" ><h6>To Date :</h6></div>
							  <div class="col-1">						
								     <input type="text" style="width: 145%;"  class="form-control input-sm mydate" onchange="this.form.submit()" readonly="readonly" onchange="this.form.submit()" <%if(todate!=null){%>value="<%=todate%>"<%}%>   id="todate" name="todate"  required="required"  > 							
							 		 <label class="input-group-addon btn" for="testdate"></label>    
							 </div>
							 
							 <div class="col-2" align="right">
							    
							 </div>
							
							
					</div>
							 
				   </form>
				
				</div>
			
				<div class="card-body">
			 	

					<form action="HandingOver.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>From-Employee</th>
										<th>To-Employee</th>
										<th>Applied Date</th>
										<th>From Date</th>
										<th>To Date</th>
										<th>Status</th>
									</tr>
								</thead>
								<tbody>
									<%for(Object[] obj:handingoverlist){ %>
										<tr>
											<td style="text-align: center;"><input type="radio" name="HandingOverId" value="<%=obj[0]%>"> </td>
											<td><%=obj[1]%></td>
											<td><%=obj[2]%></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[5].toString())%></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%></td>
											<td><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString())%></td>
											<td><%if("A".equalsIgnoreCase(obj[6].toString())){%>Created Date<%}else if("R".equalsIgnoreCase(obj[6].toString())){%>Revoked<%}else{ %> Santioned<%} %></td>
										</tr>
									<%}%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">						
						<button type="submit" class="btn btn-sm add-btn"    name="Action" value="ADD" >ADD    </button>
						<button type="submit" class="btn btn-sm delete-btn" name="Action" value="REVOKE" Onclick="Edit()" >REVOKE </button>
					    </div>						 
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
	/* "startDate" : fdate, */
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
		"minDate" :$("#fromdate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});




function Edit() {

	var fields = $("input[name='HandingOverId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One ");

		event.preventDefault();
		return false;
	}
	return true;
}
</script>
</html>
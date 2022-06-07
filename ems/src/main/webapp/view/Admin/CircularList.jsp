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
<%List<Object[]>  circular = (List<Object[]>)request.getAttribute("circulatlist");
String fromdate = (String)request.getAttribute("fromdate");
String todate = (String)request.getAttribute("todate");

%>

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
			<div class="card-header" style="height: 4rem">
					<form action="CircularLists.htm" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row justify-content-end">
					
						     <div class="col-2"  align="right"><h6>From Date :</h6></div>
					         <div class="col-1"> 
								    <input type="text" style="width: 145%;"  class="form-control input-sm mydate"  onchange="this.form.submit()" readonly="readonly" <%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>   id="fromdate" name="fromdate"  required="required"  > 
								    <label class="input-group-addon btn" for="testdate"></label>              
							 </div>
							 
							  <div class="col-2" align="right" ><h6>To Date :</h6></div>
							  <div class="col-1">						
								     <input type="text" style="width: 145%;"  class="form-control input-sm mydate" onchange="this.form.submit()" readonly="readonly" onchange="this.form.submit()" <%if(todate!=null){%>value="<%=todate%>" <%}%>   id="todate" name="todate"  required="required"  > 							
							 		 <label class="input-group-addon btn" for="testdate"></label>    
							 </div>

							 <div class="col-3" align="right"></div>			
					</div>
							 
				   </form>
				
				</div>
			
			
			
			
			
			
				<div class="card-body " >
				
					<form action="##" method="POST" id="empForm" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" > 
								<thead>
									<tr>
										<th>Select</th>
										<th>Description </th>
										<th>Circular Date</th>
										
										<th> Action</th>
									</tr>
								</thead>
								<tbody>
									<%if(circularlist!=null && circularlist.size()>0){ 
										for(Object[] obj : circularlist){
									%>
										<tr>
											<td style="text-align:center;  width: 5%;"> <input type="radio" name="circulatId" value="<%=obj[0]%>"> </td>
											<td style="text-align:justify; width: 70%;"><%=obj[1]%></td>
											<td style="text-align:justify; width: 10%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%> <%} %></td>								
											<td style="text-align:center;  width: 5%;"> <%if(obj[2]!=null){ %> 
											<button type="submit" class="btn btn-sm" name="path" value="<%=obj[2]%>//<%=obj[4] %>" formaction="download-CircularFile-attachment" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											  <i style="color: #019267" class="fa-solid fa-download"></i>
										    </button>
											<%}else{%>--<%}%>
											</td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						<button type="submit" class="btn btn-sm add-btn" formaction="CircularLists.htm" name="action" value="ADD" >ADD</button>
						<button type="submit" class="btn btn-sm edit-btn" formaction="CircularLists.htm" name="action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
								
					    </div>						 
					</div>
					
			   </form>		
			  </div>
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





function Edit(empForm)
{
	var fields = $("input[name='circulatId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Circular ");

		event.preventDefault();
		return false;
	}
	return true;
	
}

</script>
</html>
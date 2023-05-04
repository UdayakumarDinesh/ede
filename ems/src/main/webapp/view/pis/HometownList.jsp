<%@page import="com.vts.ems.pis.model.Employee"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List,com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hometown & Mobile</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 
<style type="text/css">

.trup
		{
			padding:5px 10px 0px 10px ;			
			border-top-left-radius : 5px; 
			border-top-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
			
			
		}
		
		.trdown
		{
			padding:0px 10px 5px 10px ;			
			border-bottom-left-radius : 5px; 
			border-bottom-right-radius: 5px;
			font-size: 14px;
			font-weight: 600;
		}



</style>
</head>



<body>
<%

    Object[] empdata = (Object[])request.getAttribute("Empdata");
	List<Object[]> mobile =(List<Object[]>)request.getAttribute("MobileDetails");
	List<Object[]> home =(List<Object[]>)request.getAttribute("HometownDetails");
	
%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Hometown List<small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%>
						</b></small></h5>
			</div>
				<div class="col-md-7">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item  " aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Hometown List</li>
					</ol>
				</div>
			</div>
	 </div>

<div class="page card dashboard-card">
		<div align="center">
		   <% String ses=(String)request.getParameter("result"); 
			  String ses1=(String)request.getParameter("resultfail");
			  if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert"  style="margin-top: 5px;">
					<%=ses1 %>
				</div>
			  <%}if(ses!=null){ %>
				<div class="alert alert-success" role="alert"  style="margin-top: 5px;">
					<%=ses %>
				</div>
			  <%} %>
		 </div>	
		 	
<div class="card">					
		<div class="card-body">
			<h5>Hometown List</h5>
			  <hr>
				<form action="Hometown.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Hometown</th>
					       <th>Nearest Railway Station</th>	
					       <th>State</th>					       								
					   </tr>
					   </thead>
					   <tbody>
                           <%if(home!=null){
					        for(Object[] obj : home){ %>
					   		<tr align="center">
					   		<td style="text-align: center;width:4%;"><input type="radio" name="hometownid" value="<%=obj[0]%>"></td>
						    <td style="text-align: left;"><%if(obj[2]!=null){ %> <%=obj[2] %> <%} %> </td>
						    <td style="text-align: left;"><%if(obj[3]!=null){ %> <%=obj[3] %> <%} %> </td>
						    <td style="text-align: left;"><%if(obj[4]!=null){ %> <%=obj[4] %> <%} %> </td>          
					   		</tr>
					   		<%} %>
					   </tbody>
				    </table>
				   </div>	
				   <div>
				   </div>
				   <div class="row">
						<div class="col-md-12 w-100" align="left">
							<br><b>NOTE :</b> <b style="color:red;">You Can Change Your Hometown Only Once In A Lifetime. </b>									
						</div>
					</div>

				    <div class="row text-center">
						<div class="col-md-12">
						     <%-- <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>"> --%>
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADDHometown"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDITHometown"  Onclick="EditPer(empForm)" >EDIT </button>
					    	<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
				    </div>	
					<%} %>						
				</form>
       </div>
  </div>			 		 
</div>							
		
		
		
		   
	        
			
		
<script type="text/javascript">
$("#myTable2").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});
$("#myTable3").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});

</script>
<script type="text/javascript">
function EditRes(myfrm) {

	var fields = $("input[name='mobilenumberid']").serializeArray();
	if (fields.length === 0) {
		alert("Please Select Atleast One Mobile Number");

		event.preventDefault();
		return false;
	}
	return true;
}

</script>
<script type="text/javascript">
function EditPer(myfrm) {

	var fieldsperadd = $("input[name='hometownid']").serializeArray();
 
	if (fieldsperadd.length === 0) {
		alert("Please Select Atleast One HomeTown");

		event.preventDefault();
		return false;
	}
	return true;
}

</script> 
</body>
</html>
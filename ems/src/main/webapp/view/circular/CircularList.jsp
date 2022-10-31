<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">


</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Circular List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CircularDashBoard.htm">Circular Dashboard</a></li>
					</ol>
				</div>
	
	</div>	
<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=ses1 %>
        </div>
   	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>
		

<div class="page card dashboard-card">
   <div class="card-body" align="center" >
   
   <form action="##" method="POST" id="circularForm" >
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
   
   
   
   
   
   			<div class="row text-center">
			  <div class="col-md-12">					
					<button type="submit" class="btn btn-sm add-btn" formaction="CircularAdd.htm"  >ADD</button>
			        <button type="submit" class="btn btn-sm edit-btn" formaction="CircularEdit.htm" name="action" value="EDITCIR"  Onclick="Edit(circularForm)" >EDIT </button>	
					<button type="submit" class="btn btn-sm delete-btn" formaction="CircularDelete.htm" name="action" value="DELETECIR"  Onclick="Delete(circularForm)" >DELETE </button>																	 
				</div>						 
			</div>	
			
			</form>	
			

			</div>

   </div>
</div>

 

<script>

function Edit(circularForm)
{
	var fields = $("input[name='circulatId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Circular ");

		event.preventDefault();
		return false;
	}
	return true;
	
}
	   
function Delete(circularForm)
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

</body>
</html>
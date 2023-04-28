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
	String LoginType = (String)session.getAttribute("LoginType");
	String edit = (String)request.getAttribute("edit");
	
	Object[] DGMEmpName = (Object[])request.getAttribute("DGMEmpName");
	Object[] PandAEmpName = (Object[])request.getAttribute("PandAEmpName");
	Employee emp=(Employee)request.getAttribute("EmployeeD");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5 style="width:115%;">Intimation For Hometown & Mobile</h5>
			</div>
			<div class="col-md-8" >
				<nav aria-label="breadcrumb">
				  <ol class="breadcrumb ">
				    <li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i>Home</a></li>
				    <li class="breadcrumb-item"><a href="PersonalIntimation.htm">Personal Intimations</a></li>
				    <li class="breadcrumb-item active " aria-current="page">Hometown & Mobile</li>
				  </ol>
				</nav>
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
				<form action="HomeTownAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
					   <thead>
									
					    <tr align="center">
					       <th>Select</th>
					       <th>Hometown</th>
					       <th>State</th>
					       <th>Nearest Railway Station</th>
						  <!--  <th>Mobile No.</th> -->							
						   <th>Status</th>
						   <th>Action</th> 
					   </tr>
					   </thead>
					   <tbody>

					   </tbody>
				    </table>
				   </div>	
				   <div class="row text-center">
						<div class="col-md-12">
						     <%-- <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>"> --%>
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADDPerAddress"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDITPerAddress"  Onclick="EditPer(empForm)" >EDIT </button>
					    	<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
					    </div>							
				</form>
             </div>
		</div>			 
		 
		 
		 
		 
<div class="card">					
		<div class="card-body">
			<h5>Mobile Number List</h5>
			  <hr>
				<form action="MobileNumberAddEdit.htm" method="POST" id="empForm">
				  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				  <div class="table-responsive">
				   	<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable3"> 
					   <thead>
						    <tr align="center">
						        <th>Select</th>
								<th>Mobile Number</th>
								<th>Mobile.From</th>
								<th>Mobile.To</th>
								<!-- <th>Mobile No.</th>	 -->
								<th>Status</th>
								<th>Action</th> 
						   </tr>
					   </thead>
					   <tbody>
					   		
					</tbody>
					</table>
					</div>	
					<div class="row text-center">
						<div class="col-md-12">
						     <%-- <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>"> --%>
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="EditRes(empForm)" >EDIT </button>
					    	<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
					    </div>							
				</form>
				
				
		<hr>
			<div class="row"  >
		 		<div class="col-md-12" style="text-align: center;"><b>Approval Flow</b></div>
		 	</div>
		 	<div class="row"  style="text-align: center; padding-top: 10px; padding-bottom: 15px; " >
	              <table align="center"  >
	               		<tr>
	                		<td class="trup" style="background: #E8E46E;">
	                			User 
	                		</td>
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%if(DGMEmpName!=null){ %>
	                		<td class="trup" style="background: #FBC7F7;">
	                			DGM 
	                		</td>
			                		 
	                		<td rowspan="2">
	                			<i class="fa fa-long-arrow-right " aria-hidden="true"></i>
	                		</td>
	               		<%} %>
	               		<%if(PandAEmpName!=null){ %>
	                		<td class="trup" style="background: #FBC7F7;" >
	                			P&A
	                		</td>
	               		<%} %>
	               	</tr>			   
		                	
	               	<tr>
	               		<td class="trdown" style="background: #E8E46E;" >	
				              <%=emp.getEmpName() %>
	                	</td>
	               		<%if(DGMEmpName!=null){ %>
	                		<td class="trdown" style="background: #FBC7F7;" >	
				                <%=DGMEmpName[1] %>
	                		</td>
	               		 <%} %>
	               		 <%if(PandAEmpName!=null){ %>
	               			<td class="trdown" style="background: #FBC7F7;" >	
			                	<%=PandAEmpName[1] %>
		           			</td>
		           		 <%} %>
		            	</tr>             	
			           </table>			             
			 	</div>
             
             
				
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

	var fields = $("input[name='mobileNumberId']").serializeArray();
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

	var fieldsperadd = $("input[name='homeTownId']").serializeArray();
 
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Handling Over</title>
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>
<%
List<Object[]> medicine = (List<Object[]>)request.getAttribute("MedicineList");

String treat = (String)request.getAttribute("treat");

%>



<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS HandlingOver List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">CHSS HandlingOver List</li>
					</ol>
				</div>
			</div>
		 </div>
		 
	
	
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
			
				<div class="card-body">
			 	
				
				<form action="MedicineList.htm" method="GET">
				
				<div class="row" style="padding-bottom: 10px;">
				<div class="col-2"></div>
					  <div class="col-2" align="right"><h4 style=" margin-left:-20%; " >FromDate :<span class="mandatory">*</span></h4></div>
				       <div class="col-2" align="right">
							    <input type="text" style="width: 70%;"  class="form-control input-sm mydate" readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="dob" name="dob"  required="required"  > 
							           <label class="input-group-addon btn" for="testdate">
							      
							    </label>              
						 </div>
						  <div class="col-2"><h4>ToDate  :<span class="mandatory">*</span></h4></div>
						  <div class="col-3">						
							    <input type="text" style="width: 50%; margin-left:-40%;" class="form-control input-sm mydate" readonly="readonly" value="<%=LocalDate.now() %>" placeholder=""  id="dob" name="dob"  required="required"  > 							
						 		 <label class="input-group-addon btn" for="testdate">
							      
							    </label>    
						 </div>
						 
						 
						 </div>
						 
			   </form>
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
			   
					<form action="##" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>From-Employee</th>
										<th>To-Employee</th>
										<th>FromDate</th>
										<th>ToDate</th>
									</tr>
								</thead>
								<tbody>
									
										<tr>
											<td style="text-align: center;"><input type="radio" name="MedicineId" value=""> </td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">						
						<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD" >ADD</button>
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
</body>
</html>
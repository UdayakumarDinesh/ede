<%@page import="java.util.List"%>
<%@ page language="java"%>
<!DOCTYPE html>
<html>
<head>





</head>
<body >

<%List<Object[]> UploadMcFc=(List<Object[]>)request.getAttribute("UploadMcFc");
String year=(String)request.getAttribute("year");%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Credit List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Credit List</li>
					</ol>
				</div>
			</div>
	</div>	
    
<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
   
   	<div class="row">
   		
   		<div class="col-md-8">
   			<form action="CreditPreview.htm" method="POST" id="preview">
			    <div class="row">
				    
				    <div class="col-md-2" style="margin-top: 4px;font-weight: bold;">
				             Credit :
				    </div>


				    <div class="col-md-2">
					    <div class="group-form">
					 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					    <input class="form-control  form-control" type="text" id="year1"  name="Year">
					    </div>
				    </div>
				    <div class="col-md-2">
					    <input type="button" value="Preview Credit" class="btn  btn-sm misc1-btn" onclick="leaveCredit()" style="margin-top: 3px;">
					</div>
				</div>
    		</form>
   		</div>

</body>
</html>
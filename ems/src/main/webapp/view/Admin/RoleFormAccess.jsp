<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>


<title>Role Access</title>
<style type="text/css">
.control-label{
	font-weight: 550 !important;
}


.table thead th{
	
	vertical-align: middle !important;
}

.header{
        position:sticky;
        top: 0 ;
        background-color: #346691;
    }
    
    .table button{
    	background-color: background !important;
    	font-size: 12px;
    }
    
 label{
 	font-size: 15px !important;
 }
 
   .toggle.ios, .toggle-on.ios, .toggle-off.ios { border-radius: 20rem; }
  .toggle.ios .toggle-handle { border-radius: 20rem; }
 
</style>
</head>
<body>
<%
List<Object[]> LoginTypeRoles=(List<Object[]>) request.getAttribute("LoginTypeRoles");
List<Object[]> FormDetailsList=(List<Object[]>) request.getAttribute("FormDetailsList");
List<Object[]> FormModulesList=(List<Object[]>) request.getAttribute("FormModulesList");
String logintype=(String)request.getAttribute("logintype");
String moduleid=(String)request.getAttribute("moduleid");

%>
<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Form Role</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item active " aria-current="page">Form Role</li>
					</ol>
				</div>
			</div>
		 </div>



		<div class="card">
			<div class="card-header">
				<form class="" method="post" action="Role.htm" id="myform">
					
						<div class="row">
		
							<div class="col-md-6">
								<h3 class="control-label" style="color: #145374;"> Forms List</h3>
							</div>
			
							<div class="col-sm-1half">	
								<h5 class="control-label" style="color: #145374;"> Role : </h5>
							</div>		
								
							<div class="col-md-2">			
										 <select class="form-control select2" id="logintype" required="required" name="logintype" onchange='submitForm();' >
						   						<% for (Object[] obj : LoginTypeRoles) {%>
												<option value="<%=obj[1]%>" <%if(obj[1].toString().equalsIgnoreCase(logintype)){ %>selected<% } %> ><%=obj[2]%></option>
												<%} %>
						  				</select>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								
							</div>
							
							<div class="col-sm-1half">	
								<h5 class="control-label"  style="color: #145374; margin-bottom: 10px;"> Module : </h5> 						
							</div>
							
							<div class="col-md-2">	
	
										 <select class="form-control select2" id="moduleid" required="required" name="moduleid" onchange='submitForm();' >
										 		<option value="A" >All </option>
						   						<% for (Object[] obj : FormModulesList) {%>
												<option value="<%=obj[0]%>" <%if(obj[0].toString().equalsIgnoreCase(moduleid)){ %>selected<% } %> ><%=obj[1]%></option>
												<%} %>
						  				</select>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />	
						
							</div>
							
						</div>
					
					</form>
			</div>
			<!-- card header -->
			
			<div class="card-body"> 

			           
			        	<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  > 
					        <thead style="background-color: rgb(15,80,180);color: white">
					          <tr>
			                	<th >SN</th>
			                    <th >Form Name</th>
			                    <th >Access</th>
			                </tr>
					        </thead>
        
					         <tbody>
							<% 
								if(FormDetailsList.size()>0){
									int count=1;
										for(Object[] 	obj:FormDetailsList){ %>
													   
								<tr>
									<td ><%=count %></td>
									<td><%=obj[2] %></td>
										<td>
										
											<input name="access"   onchange="FormNameEdit(<%=obj[0]%>)"  type="checkbox"  <%if(obj[1].toString().equalsIgnoreCase("A")){ %> disabled <%} %> <%if((obj[3]).toString().equalsIgnoreCase("1")){ %>checked<%}%> data-toggle="toggle" data-style="ios" data-onstyle="primary" data-offstyle="danger" data-width="105" data-height="15" data-on="<i class='fa fa-check' aria-hidden='true'></i> Active" data-off="<i class='fa fa-times' aria-hidden='true'></i> Inactive" >
											<input 	type="hidden" name="sample" value="attendance<%=count %>" >	
											<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" /> 
									</td>

								</tr>
 												
 								<%count++;}}else{ %>
 												
 								<tr>
 									<td colspan="3" style="text-align: center">No Forms Assigned</td>
 																						
 								</tr>
 												
 								<%} %>
 												
							</tbody>
      				
      				</table>
	           
			      </div>
			     
			    </div> 
			
		<!-- card Body -->
		</div>

</div>
</body>

<script type='text/javascript'> 
function submitForm()
{ 
  document.getElementById('myform').submit(); 
} 

function FormNameEdit(id){
		 $.ajax({

			type : "GET",
			url : "FormRoleActive.htm",
			data : {
						formroleaccessid : id
				   },
			datatype : 'json',
			success : function(result) {

			var result = JSON.parse(result);
	
			var values = Object.keys(result).map(function(e) {
		 				 return result[e]
		  
							});
				}
				   
			});
	 
}





</script>







</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,com.vts.*,java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<title>Role Access</title>
<style type="text/css">



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
  
  h6{
  	font-size: 16px;
  	    font-weight: 700 !important;
  }
 
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

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Form Role</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<!-- <li class="breadcrumb-item "><a href="MasterDashBoard.htm"> Master </a></li> -->
						<li class="breadcrumb-item active " aria-current="page">Form Role</li>
					</ol>
				</div>
			</div>
		 </div>

		 
		<div class="page card dashboard-card">
			<div class="card-header">
				<form class="" method="post" action="Role.htm" id="myform">
					
						<div class="row justify-content-end">
		
							<!-- <div class="col-md-6">
								<h6 class="control-label" style="color: #145374;"> Forms List</h6>
							</div> -->
			
							<div class="col-sm-1half">	
								<h6 class="control-label" style="color: #145374;"> Role : </h6>
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
								<h6 class="control-label"  style="color: #145374; margin-bottom: 10px;"> Module : </h6> 						
							</div>
							
							<div class="col-md-2">	
	
										 <select class="form-control select2" id="moduleid" required="required" name="moduleid" onchange='submitForm();' >
										 		<option value="0" >All </option>
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
			
			<div class="card-body main-card "> 
			        	<div class="table-responsive">
			        	<form action="UpdateRoleAcess.htm" method="POST" id="myform1">
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
										for(Object[] 	obj:FormDetailsList){
											String detailsid = null;
											String isactive  =null;	
											if(obj[0]!=null){
											   detailsid ="detailsid"+obj[0];
											   isactive  ="isactive"+obj[0];
											}else{
												detailsid ="detailsidId";
												isactive  ="isactiveId";
											}
											%>
													   
								<tr>
									<td ><%=count %>.</td>
									<td><%=obj[3] %></td>
									<td>
										
											<%-- <input name="access"   onchange="FormNameEdit(<%=obj[0]%>)"  type="checkbox"  <%if(obj[2].toString().equalsIgnoreCase("1")||obj[2].toString().equalsIgnoreCase("4")||obj[2].toString().equalsIgnoreCase("3")){ %> disabled <%} %> <%if(obj[4]!=null && (obj[4]).toString().equalsIgnoreCase("1")){ %>checked<%}%> data-toggle="toggle" data-style="ios" data-onstyle="primary" data-offstyle="danger" data-width="105" data-height="15" data-on="<i class='fa fa-check' aria-hidden='true'></i> Active" data-off="<i class='fa fa-times' aria-hidden='true'></i> Inactive" > --%>
											<input name="access"  value="<%=obj[0]%>"  onchange="UpdateIsActive('<%=obj[0]%>','<%=obj[1]%>','<%=obj[4]%>','<%=moduleid%>','<%=logintype%>');"  type="checkbox"  <%if(/* logintype.toString().equalsIgnoreCase("A") */false){ %> disabled <%} %> <%if(obj[4]!=null && (obj[4]).toString().equalsIgnoreCase("1")){ %>checked<%}%> data-toggle="toggle" data-style="ios" data-onstyle="primary" data-offstyle="danger" data-width="105" data-height="15" data-on="<i class='fa fa-check' aria-hidden='true'></i> Active" data-off="<i class='fa fa-times' aria-hidden='true'></i> Inactive" >
											<input type="hidden" name="sample"     value="attendance<%=count %>" >
											<input type="hidden" name="moduleid" value="<%=moduleid%>">	
											<input type="hidden" name="logintype1" value="<%=logintype%>">
											<input type="hidden" name ="<%=detailsid%>"  value="<%=obj[1]%>">
											<input type="hidden" name ="<%=isactive%>"  value="<%=obj[4]%>">
											<input type="hidden" id="formroleid" name ="formroleaccessid" value="<%=obj[0]%>">
											
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
	           </form>
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

/* function UpdateIsActive(value)
{
	console.log(value);
	if(value!=null){
		$("#formroleid").val(value);
		document.getElementById('myform1').submit(); 
	}else{
		$("#formroleid").val("Id");
		document.getElementById('myform1').submit(); 
	}
	
} */
function UpdateIsActive(formroleaccsid, detailsid,isactive,moduleid,logintype)
{
	 $.ajax({

			type : "GET",
			url : "UpdateRoleAcess.htm",
			data : {
						formroleaccessid : formroleaccsid,
						detailsid        : detailsid,
						isactive         : isactive,
						moduleid         : moduleid,
						logintype        : logintype
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

function FormNameEdit(id){
	
	
		 $.ajax({

			type : "POST",
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
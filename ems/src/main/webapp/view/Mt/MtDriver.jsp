<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Vehicle Operator</title>
</head>
<body>

<%List<Object[]> driver = (List<Object[]>)request.getAttribute("driverlist"); 
List<Object[]> driveremplist = (List<Object[]>)request.getAttribute("driveremplist");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>MT Vehicle Operator</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Vehicle Operator</li>	
					</ol>
				</div>
			</div>
</div>
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
				<div class="card-body " >
				
					<form action="MtDriverAddEdit.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<th>Select</th>
										<th>Sr No</th>
										<th>Vehicle Operator</th>
										<th>Designation</th>
										
										
									</tr>
								</thead>
								<tbody>
									<%if(driver!=null && driver.size()>0){
									for(Object[] obj : driver){ 
									%>
										<tr>
											<td style="text-align: center;"><input type="radio" name="driverid" value="<%=obj[0]%>"> </td>
											<td><%=obj[3]%></td>
											<td><%=obj[1]%></td>
											<td><%=obj[2]%></td>
										</tr>
									<%} }%>
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
							<button type="button" class="btn btn-sm add-btn AddItem" name="action" value="Add"   >ADD </button>
		
                           <button type="button" class="btn btn-sm delete-btn" name="action" value="Delete" Onclick=" Delete(empForm)" >InActive </button>
						</div>
					</div>
				</form>	
			</div>
		</div>		
		
	</div>
		<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     <form name="myfrm" action="MtDriverAddEdit.htm" method="POST" id="myfrm1" autocomplete="off">
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">Add Vehicle Operator</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
					             <div class="form-inline">
					        	 <div class="form-group "  >
					               <label>Vehicle Operator: <span class="mandatory"	style="color: red;">*</span>
					             </label> 
								</div>
					        	 <div class="form-group " style="width: 50%;" >
					        	 <select class="form-control select2 " style="width: 75%;"   name="empid" required="required" data-live-search="true" >
									
									<option value="" disabled="disabled" selected="selected">Select Driver </option>
									<%if(driveremplist!=null && driveremplist.size()>0){
						                for(Object[] obj : driveremplist){ %>
					                    <option value="<%=obj[2]%>" ><%=obj[1]%></option>
						                <%}
					                } %>  
								</select>
					        	
					        	 </div>
					        	 </div>
					      </div>
					      
					      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="EDITTEST" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					        </form>
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
</div>

</body>
<script type="text/javascript">
$(".AddItem").click(function(){ 
	
	 $('#myModal').modal('show');
});

	function Delete(myfrm){ 
		
		var fields = $("input[name='driverid']").serializeArray();

		if (fields.length === 0){
			alert("Please Select Atleast One Driver");
			event.preventDefault();
			return false;
		}
		
		var cnf = confirm("Are You Sure To InActive!");

		if(cnf){
			
			document.getElementById("empForm").submit();
			return true;

		}else{
			
			event.preventDefault();
			return false;
		}
	}

</script>
</html>
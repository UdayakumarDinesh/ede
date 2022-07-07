<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Medicine List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<style>
	.card-header{
		border-bottom: none !important;
		    padding: 0.5rem 1.25rem;
	}
</style>
</head>
<body>
<%
List<Object[]> medicine = (List<Object[]>)request.getAttribute("MedicineList");
List<Object[]> main =(List<Object[]>)request.getAttribute("treatment");
String treat = (String)request.getAttribute("treat");
String LoginType = (String) session.getAttribute("LoginType"); 
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Medicine List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						
						<li class="breadcrumb-item active " aria-current="page">CHSS Medicine List</li>
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
			
				<div class="card-header">
					<div class="row justify-content-end">
						<div class="col-2" style="margin-left:500px;" ><h6>Treatment Type :</h6></div>
						<form action="MedicineList.htm" method="GET">
						        <div class="col-3">
						        
			                  	<select class="form-control select2" name="tratementname" data-container="body" data-live-search="true"  onchange="this.form.submit();" style="width: 200px; align-items: center; font-size: 5px;">
								<%-- 	<option value="A" <%if(treat!=null){if("A".equalsIgnoreCase(treat)){ %>selected <%}}%>>All</option> --%> 
											<%if(main!=null&&main.size()>0){for(Object[] O:main){%>
												<%if(O[0].toString().equalsIgnoreCase("1")){ %>
												<option value="<%=O[0]%>" <%if(treat!=null){if(treat.equalsIgnoreCase(O[0].toString())){ %>selected <%}}%>> <%=O[1]%>&nbsp; (Inadmissible)</option>
												<%}else{ %>	
												<option value="<%=O[0]%>" <%if(treat!=null){if(treat.equalsIgnoreCase(O[0].toString())){ %>selected <%}}%>> <%=O[1]%></option>
												<%} %>
											<%}}%>
								</select>
						        </div>
					   </form>
					</div>
				</div>
			
				<div class="card-body main-card ">
				
					<form action="ChssMedicine.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						
						<div class="table-responsive">
						
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 				   			
								<thead>
									<tr>
										<th>Select</th>
										<th>SN</th>
										<th>Treatment Type</th>
										<th> Medicine Name </th>
										<th>Admissible</th>
									</tr>
								</thead>
								<tbody>
									<%	if(medicine!=null){
									for(Object[] obj : medicine){ 
									%>
										<tr>
											<td style="text-align: center;"><input type="radio" name="MedicineId" value="<%=obj[0] %>"> </td>
											<td><%=obj[3] %>. </td>
											<td><%=obj[1] %></td>
											<td><%=obj[2] %></td>
											<td>
												<%if(obj[2].toString().equalsIgnoreCase("Y")){ %>
													YES
												<%}else{%>
													No
												<%} %>				
											</td>
										</tr>
									 <%} }%> 
								</tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					
					<%if(LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P") || LoginType.equalsIgnoreCase("F") ){ %>
					<div class="row text-center">
						<div class="col-md-12">
											
							<button type="submit" class="btn btn-sm add-btn" name="Action" formnovalidate="formnovalidate"  value="ADD" >add</button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT" formnovalidate="formnovalidate"  Onclick="Edit(empForm)" >edit </button>
							<button type="button" class="btn btn-sm delete-btn" name="Action" value="DELETE"  Onclick="Delete(empForm);" >Delete </button>
							 
					    </div>						 
					</div>
					<%} %>
			  
	        
	        
	        <div class="container">
				
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Delete</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        
					        <!-- Modal body -->
					      <div class="modal-body">
					             <div class="form-inline">
					        	 <div class="form-group"  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile" > 
					      		 </div>
					      		 </div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : &nbsp;&nbsp;&nbsp;</label> 
					               <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					      		
					     </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="Action" formaction="ChssMedicineDelete.htm" formenctype="multipart/form-data" value="DELETE" onclick="return confirm('Are You Sure To Delete ?');" >SUBMIT</button>
					        </div>		
					        		       
					      </div>
					    </div>
					  </div>
					  
			</div>
	        
	  			</form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
	        
	        
	        
	        <script type="text/javascript">
	        function Edit(myfrm) {

	        	var fields = $("input[name='MedicineId']").serializeArray();

	        	if (fields.length === 0) {
	        		alert("Please Select Atleast One Medicine");

	        		event.preventDefault();
	        		return false;
	        	}
	        	return true;
	        }
	        
	        function Delete(myfrm) {

	        	var fields = $("input[name='MedicineId']").serializeArray();

	        	if (fields.length === 0) {
	        		alert("Please Select Atleast One Medicine");

	        		event.preventDefault();
	        		return false;
	        	}else
	        	{
	        		$('#myModal').modal('show');	
	        	}
	        	
	        }
	        </script>
</body>
</html>
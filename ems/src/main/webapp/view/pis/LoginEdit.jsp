<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@page import="java.util.List"%>
       <%@page import="com.vts.ems.login.Login" %>
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
<%
List<Object[]> loginlist = (List<Object[]>)request.getAttribute("loginlist");
List<Object[]> emplist = (List<Object[]>) request.getAttribute("emplist");
Login loginEditdata    =   (Login)request.getAttribute("logineditdata");
%>



	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>LoginType Edit </h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="LoginMaster.htm"> Login List </a></li>
						<li class="breadcrumb-item active " aria-current="page">Login Edit</li>
					</ol>
				</div>
			</div>
		 </div>
		 
		 
		  <div class="page card dashboard-card">
		 <div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >
		 
		 <form name="myfrm" action="UserManagerEditSubmit.htm" method="POST" enctype="multipart/form-data">
						<div class="form-group">
							<div class="table-responsive">
								<table class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									

										<tr>
											<th><label>Login Type : <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2" name="LoginType" id="LoginType" data-container="body" data-live-search="true" required="required" style="font-size: 5px;">
												
												<%for(Object[] login :loginlist){ %>
													<option value="<%=login[0]%>" <%if(loginEditdata.getLoginType().equalsIgnoreCase(login[0].toString())){%> selected <%}%>><%=login[1]%>   </option>
													<%} %>
											</select></td>
										</tr>
									<%-- 	<tr>
											<th><label>Employee : <span class="mandatory" style="color: red;">*</span>
											</label></th>
											<td><select class="form-control select2"
												name="Employee" id="Employee" data-container="body"
												data-live-search="true" style="font-size: 5px;">
												
												<%for(Object[] emp:emplist){ %>	
												
											   <option value="<%=emp[0]%>"  <%if(loginEditdata.getEmpId().toString().equalsIgnoreCase(emp[0].toString())){%> selected <%}%>><%=emp[1]%> </option>
												<%}%>
											</select></td>
										</tr> --%>
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
								<button type="submit" class="btn btn-sm submit-btn "
									onclick="return CommentsModel();"
									name="action" value="submit">SUBMIT</button>
							</div>

						</div>
						<input type="hidden" name="loginid"	value="<%=loginEditdata.getLoginId()%>" />
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						
						<!--------------------------- container ------------------------->
			<div class="container">
					
				<!-- The Modal -->
				<div class="modal" id="myModal">
					 <div class="modal-dialog">
					    <div class="modal-content">
					     
					        <!-- Modal Header -->
					        <div class="modal-header">
					          <h4 class="modal-title">The Reason For Edit</h4>
					          <button type="button" class="close" data-dismiss="modal">&times;</button>
					        </div>
					        <!-- Modal body -->
					        <div class="modal-body">
					        
					        	<div class="form-inline">
					        	<div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile"  > 
					      		</div>
					      		</div>
					        	
					        	<div class="form-inline">
					        	<div class="form-group w-100">
					               <label>Comments : <span class="mandatory"	style="color: red;">*</span>&nbsp;&nbsp;&nbsp;</label> 
					              <textarea  class=" form-control w-100" maxlength="1000" style="text-transform:capitalize;"  id="comments"  name="comments" required="required" ></textarea> 
					      		</div>
					      		</div>
					        </div>
					      
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <!-- Modal footer -->
					        <div class="modal-footer" >
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
					</form>
					</div>
		 	 </div>
		 </div>
	</div>
</body>
<script type="text/javascript">
function CommentsModel()
{
	 $('#myModal').modal('show');
	 return false;
}
</script>
</html>
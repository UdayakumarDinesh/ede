<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Departments Add</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<% String department=(String)request.getAttribute("Department");
List<Object[]> EmpList=(List<Object[]>)request.getAttribute("Emplist");
%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Departments ADD</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item"><a href="DepartmentsList.jsp">Departments List</a></li>					
						<li class="breadcrumb-item active " aria-current="page">Departments ADD</li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body  " align="center" >
					<%if(department!=null){ %>
					<form name="myfrm" action="DepartmentEdit.htm" method="POST" id="addfrm" autocomplete="off"  enctype="multipart/form-data" >	
						<%}else{%>
					<form name="myfrm" action="DepartmentAdd.htm" method="POST" id="addfrm" autocomplete="off"   >	
						<%}%>
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										<tr>
											<th><label>Department Code<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Department Code" type="text" id="depcode" name="Departmentcode" value=""
												style="text-transform:capitalize" required="required" maxlength="255" style="font-size: 15px;" ></td>
										</tr>
										<tr>
											<th><label>Department Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" 
												placeholder=" Enter Department Name" type="text" name="DepartmentName" value=""
												required="required" maxlength="255" style="font-size: 15px;" id="department"></td>
												
										</tr>
										<tr>
										     <th><label>Department Head <span class="mandatory" style="color: red;">*</span></label>
										     <td><select class="form-control select2" name="DepartmentHead">
										     
										     <option value=""></option>
										     
										 
										     </select>
										     
										     
										     
										     
										     </td>
										</tr>
									
								</table>
							</div>
						</div>
						</form>
					</form>
				</div>
			</div>
		</div>
  </div>
						
</body>
</html>
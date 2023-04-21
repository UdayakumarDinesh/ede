<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*"%>
    <%@ page import="com.vts.ems.master.model.Department" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Departments Add</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<% Object[] dept=(Object[])request.getAttribute("Department");
List<Object[]> EmpList=(List<Object[]>)request.getAttribute("Emplist");
List<Object[]> DGMList=(List<Object[]>)request.getAttribute("DGMList");
%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(dept!=null) {%>
			<h5>Departments EDIT</h5>
			<%}else{ %>
				<h5>Departments ADD</h5>
				<%} %>
			</div>
				<div class="col-md-9">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item"><a href="DepartmentsList.htm">Departments List</a></li>					
						<%if(dept!=null){ %>
						<li class="breadcrumb-item active " aria-current="page">Departments EDIT</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Departments ADD</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body  " align="center" >
					<%if(dept!=null){ %>
					<form name="myfrm" action="DepartmentEdit.htm" method="POST" id="addfrm" autocomplete="off"  enctype="multipart/form-data" >	
						<%}else{%>
					<form name="myfrm" action="DepartmentAdd.htm" method="POST" id="addfrm" autocomplete="off" >	
						<%}%>
							
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
															
								         
										<tr>
											<th><label>Department Code<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Department Code" type="text" id="depcode" name="Departmentcode" value="<%if(dept!=null){%><%=dept[1]%><%} %>"
												style="text-transform:capitalize" required="required" maxlength="3" style="font-size: 15px;"  ></td>

										</tr>
										<tr>
											<th><label>Department Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" 
												placeholder=" Enter Department Name" type="text" name="DepartmentName" value="<%if(dept!=null){%><%=dept[2]%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;" id="department"></td>
												
										</tr>
										<tr>
										     <th><label>Department Head <span class="mandatory" style="color: red;">*</span></label>
										     <td>
											     <select class="form-control form-control select2" name="DepartmentHead" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
											        <option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
											     <%if(EmpList!=null){ for(Object[] emp:EmpList){%>
											     	<option value="<%=emp[2]%>" <%if(dept!=null){if(dept[3].toString().equalsIgnoreCase(emp[2].toString())){%>selected="selected"<%}}%>><%=emp[1] %></option>
											 	 <%}}%>
											     </select>
										     </td>
										</tr>
										<tr>
										   <th> <label>DGM <span class="mandatory" style="color: red;">*</span></label> </th>
										   <td> 
											   <select class="form-control select2"  name="DGMId" id="DGMId" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width:100%;">
													<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
											    <%if(DGMList!=null){ for(Object[] dgm:DGMList){%>
											     	<option value="<%=dgm[0]%>" <%if(dept!=null){if(dept[3].toString().equalsIgnoreCase(dgm[0].toString())){%>selected="selected"<%}}%>><%=dgm[2] %></option>
											 	 <%}}%>
											   </select> 
										   </td> 
										</tr>
								</table>
							</div>
						</div>
						<%if(dept!=null){%>
								 <input type="hidden" name="Deptid" id="Deptid" value="<%=dept[0]%>">
								<button class="btn btn-sm submit-btn" onclick="return DepartmentEditcheck(addfrm)">Submit</button>
								<%}else {%>
								<button type="button" class="btn btn-sm submit-btn" onclick="return DepartmentAddcheck()">Submit</button>
								<%} %>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<%if(dept!=null){ %>
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
						        	<div class="form-group">
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action"  value="EDITTEST" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
					<%} %>
						<%if(dept!=null) {%>
						</form>
						<%}else{ %>
					</form>
					<%} %>
				</div>
			</div>
		</div>
  </div>
  <script type="text/javascript">

$('#depcode').keypress(function (e) {
    var regex = new RegExp("^[a-zA-Z0-9 \s]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    else
    {
    e.preventDefault();
    alert('Please Enter AlphaNumeric Characters Only');
    return false;
    }
});

</script>
	
	<script type="text/javascript">
	
	$('#depcode').keypress(function (e) {
	    var regex = new RegExp("^[A-Za-z0-9]*$" );
	    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
	    if (regex.test(str)) {
	        return true;
	    }
	    else
	    {
	    e.preventDefault();
	    alert('Please Enter Alphabates And Numbers');
	    return false;
	    }
	});
	
	
	function DepartmentAddcheck()
	{
		var depcode=$('#depcode').val();
		var department=$('#department').val();
		var count =true;
		$.ajax({

			type : "GET",
			url : "DepartmentAddCheck.htm",
			data : {
				
				Departmentcode:depcode.trim(),			
				
			},
			datatype : 'json',
			success : function(result)
			{
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0)
				{
					alert('Department Code Already Exists');
					count = false;
					event.preventDefault();
				}
				else if(count)
				{
						
							if(depcode==null || depcode==""||depcode=="null" || department==null || department=="" || department=="null"||department.trim()==="")
							{						
								alert('Enter Data Properly');
								return false;
								event.preventDefault();
							}else{
								var ret = confirm('Are you Sure To Submit ?');
							if(ret)
							{
									$('#addfrm').submit();
									return true;
							     }	
						        else{
								      return false;
								      event.preventDefault();
							     
						        }
							} 
				
			       }else{
	             return false;	
		            event.preventDefault();
	           }
			}
		});event.preventDefault();
		 
	}

	</script>
	<script type="text/javascript">
	function DepartmentEditcheck()
	{
		var depcode=$('#depcode').val();
		var department=$('#department').val();
		var Deptid=$('#Deptid').val();
		var count =true;
		$.ajax({

			type : "GET",
			url : "DepartmentEditCheck.htm",
			data : {
				
				Departmentcode:depcode.trim(),			
				Deptid:Deptid.trim(),
			},
			datatype : 'json',
			success : function(result)
			{
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0)
				{
					alert('Department Code Already Exists');
					count = false;
					event.preventDefault();
				}
				else if(count)
				{
						
							if(depcode==null || depcode==""||depcode=="null" || department==null || department=="" || department=="null"||department.trim()==="")
							{						
								alert('Enter Data Properly');
								return false;
								event.preventDefault();
							}else{
								$('#myModal').modal('show');
								return true;
							} 
				
			       }else{
	             return false;	
		            event.preventDefault();
	           }
			}
		});event.preventDefault();		 
	}
	</script>	
</body>
</html>
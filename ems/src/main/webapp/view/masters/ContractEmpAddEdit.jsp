<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.vts.ems.Admin.model.ContractEmployeeData" %>
    <%@page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Contract Employee Add</title>
</head>
<body>
<%SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
Object[] cemp=(Object[])request.getAttribute("ContractEmpData"); %>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-4">
			<%if(cemp!=null){ %>			
				<h5>Contract Employee Edit</h5>
				<%}else {%>
				<h5>Contract Employee Add</h5>
				<%} %>
			</div>
				<div class="col-md-8">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item"><a href="ContractEmployeeList.htm">Contract Employee  List</a></li>																
						<%if(cemp!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Contract Employee Edit</li>						
					  <%}else {%>
					  <li class="breadcrumb-item active " aria-current="page">Contract Employee Add</li>	
					<%} %>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card">
	<div class="card-body" >			
			 <div class="card"   > 
				<div class="card-body" align="center" >	
				<%if(cemp!=null){ %>									
					<form  action="ContractEmployeeEdit.htm" method="POST" id="myform" autocomplete="off" enctype="multipart/form-data">	
					<%}else{ %>
					<form  action="ContractEmployeeAdd.htm" method="POST" id="myform" autocomplete="off" enctype="multipart/form-data" >
					<%} %>	
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>										
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 60%;">																	         
										<tr>
											<%-- <th><label>User Name<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter User Name" type="text" id="userName" name="userName" value="<%if(cemp!=null){%><%=(cemp[1].toString())%><%}%>"
												 required="required" maxlength="5" style="font-size: 15px;"></td> --%>
										</tr> 
										
										<tr>
										<th><label>Employee  Name<span class="mandatory" style="color:red">*</span></label> </th>
										<td><input type="text" class="form-control form-control" placeholder="Enter Employee Name" id="EmpName" name="EmpName" value="<%if(cemp!=null){%><%=cemp[2]%><%}%>" maxlength="100" required="required"></td>
										</tr>
										
										<tr>
										<th><label>Date Of Birth<span class="mandatory" style="color:red">*</span></label> </th>
										<td><input type="text" class="form-control form-control"  id="dob" name="DOB" value="<%if(cemp!=null){%><%=sdf.format(cemp[3])%><%}%>"  required="required" readonly="readonly"></td>
										</tr>
										
										<tr>
										    <th><label>Email Id<span class="mandatory" style="color:red">*</span></label></th>
										    <td><input type="email"  pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"class="form-control form-control" placeholder="Enter Email Id" id="EmailId" name="EmailId" value="<%if(cemp!=null){%><%=cemp[4]%><%}%>" required="required"></td>										    
										</tr>
										
										<tr>
										     <th><label>Mobile No</label></th>
										     <td> <input type="text" class="form-control form-control" pattern="[6-9]{1}[0-9]{9}"placeholder="Enter Mobile No" id="mobileNo" name="MobileNo" value="<%if(cemp!=null){%><%=cemp[5]%><%}%>" maxlength="10" ></td>
										</tr>
									</table>
									<%if(cemp!=null){ %>
									<input type="hidden" value="<%=cemp[0] %>" name="ContractEmpId" id="ContractEmpId">
									<button class="btn btn-sm submit-btn" name="ContractEmpId" value="<%=cemp[0]%>"onclick="ContractEmpEditcheck()">SUBMIT</button>
									<%}else{ %>
									<button class="btn btn-sm submit-btn" name="ContractEmpId" value="" onclick="ContractEmpAddcheck()">SUBMIT</button>
									<%} %>
									</div>
								</div>
									<%if(cemp!=null){ %>
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
								<%if(cemp!=null){ %>
							</form>
							<%}else{ %>
							</form>
							<%} %>
						</div>
					</div>
				</div>
		 </div> 
	<script>

$('#dob').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"maxDate":new Date(),
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});			
	</script> 
	<script type="text/javascript">
	function ContractEmpAddcheck()
	{
		var CuserName=$('#userName').val();
		var EmpName=$('#EmpName').val();
		var count =true;
		$.ajax({

			type : "GET",
			url : "ContractEmpAddcheck.htm",
			data : {
				
				CUserName:CuserName.trim(),			
				
			},
			datatype : 'json',
			success : function(result)
			{
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0)
				{
					alert('UserName Already Exists');
					count = false;
					event.preventDefault();
				}
				else if(count)
				{
						
							if(CuserName==null || CuserName==""||CuserName=="null" || EmpName==null || EmpName=="" || EmpName=="null"||EmpName.trim()==="")
							{						
								alert('Enter Data Properly');
								return false;
								event.preventDefault();
							}else{
								var ret = confirm('Are you Sure To Submit ?');
							if(ret)
							{
									$('#myform').submit();
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
	function ContractEmpEditcheck()
	{
		var CuserName=$('#userName').val();
		var EmpName=$('#EmpName').val();
		var count =true;
		$.ajax({

			type : "GET",
			url : "ContractEmpEditcheck.htm",
			data : {
				
				CUserName:CuserName,				
				
			},
			datatype : 'json',
			success : function(result)
			{
				var ajaxresult = JSON.parse(result);
				
				if(ajaxresult>0)
				{
					alert('UserName Already Exists');
					count = false;
					event.preventDefault();
				}
				else if(count)
				{
						
							if( EmpName==null || EmpName=="" || EmpName=="null"||EmpName.trim()==="")
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
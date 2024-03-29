<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Update Seniority</title>
</head>
<body>
<%
Object[] empdetails = (Object[])request.getAttribute("empdetails");
%>
<div class="card-header page-top">
	<div class="row">
			<div class="col-md-3">
				<h5>Update Seniority</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item "><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Update Seniority</li>
					</ol>
				</div>
	</div>
</div>
<div class="page card dashboard-card"> 
	<div class="card-body">
	<div class="card">
		<div class="card-body">
		<form action="UpdateEmployeeSeniority.htm" method="POST" name="myfrm1" id="myfrm1">
		 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		 <div class="row">
		 <div class="col-md-1"></div>
		 	<div class="col-md-1">
                  <div class="form-group">
                        <label class="control-label">SrNo :</label><span class="mandatory">*</span>
                        <input class="form-control " type="number" min="0" name="UpdatedSrNo"  id="updatedsrno" onkeypress="return (event.charCode == 8 || event.charCode == 0 || event.charCode == 13) ? null : event.charCode >= 48 && event.charCode <= 57" id="SrNo" <%if(empdetails!=null && empdetails[0]!=null){%>value="<%=empdetails[0]%>"<%}%>>
						<input type="hidden" name="empid" <%if(empdetails!=null && empdetails[1]!=null){%>value="<%=empdetails[1]%>"<%}%>>	
                  </div>
            </div>
            <div class="col-md-2">
                  <div class="form-group">
                        <label class="control-label">Employee No :</label><span class="mandatory">*</span>
                       <input class="form-control " <%if(empdetails!=null && empdetails[2]!=null){%>value="<%=empdetails[2]%>"<%}%> readonly="readonly">
                  </div>
            </div>   
              <div class="col-md-2">
                  <div class="form-group">
                        <label class="control-label">Employee Name :</label><span class="mandatory">*</span>
                       <input class="form-control " <%if(empdetails!=null && empdetails[3]!=null){%>value="<%=empdetails[3]%>"<%}%> readonly="readonly">	
                  </div>
            </div> 
              <div class="col-md-2">
                  <div class="form-group">
                        <label class="control-label">Designation :</label><span class="mandatory">*</span>
                       <input class="form-control " <%if(empdetails!=null && empdetails[4]!=null){%>value="<%=empdetails[4]%>"<%}%> readonly="readonly">
						
                  </div>
            </div> 
               <div class="col-md-2">
                  <div class="form-group">
                        <label class="control-label">Department :</label><span class="mandatory">*</span>
                       <input class="form-control " <%if(empdetails!=null && empdetails[7]!=null){%>value="<%=empdetails[7]%>"<%}%> readonly="readonly">
						
                  </div>
            </div> 
            <!-- <div class="col-md-2">
                  <div class="form-group">
                       <label class="control-label">Type :</label><span class="mandatory">*</span>
                       <select class="form-control select2" name="SeniorityType" id="seniorityType" data-container="body" data-live-search="true" required="required" style="font-size: 5px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
													<option value="Promotion">Promotion</option>
													<option value="NewJoin">New Join</option>
													<option value="Retirement">Retirement</option>
					</select>			
                  </div>
            </div>    -->     		
		 <div class="col-md-2" style="margin-top: 2.9%;">
		  <div class="form-group" >
		 <button type="button" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="ChechSeniority();" >SUBMIT</button>
		  </div>
		 </div>
		 </div>
		 
		
		</form>
		
		
		</div>
	</div>
	</div>
</div>
<script type="text/javascript">
function ChechSeniority() {
	
	var srno   =$('#updatedsrno').val();
	$.ajax({

		type : "GET",
		url : "CheckSenirity.htm",
		datatype : 'json',
		success : function(result) {
			
			var ajaxresult = JSON.parse(result);
			var val= ajaxresult+1;
			console.log(val);
			if(srno<=val){
				
				if(confirm("Are you Sure to Submit?")){
					$("#myfrm1").submit();
					return true;
				}else{
					return false;
				}
				
			}else{
				alert('Seniority Number Should Be Max+1 Seniority Number');
				return false;
			}
		}
	});	
}

</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@page import="com.vts.ems.master.model.*"%>
     <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Empanelled Hospital</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>

<%

CHSSEmpanelledHospital list = (CHSSEmpanelledHospital)request.getAttribute("empanelled");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(list!=null){ %>
				<h5> Empanelled Hospital Edit</h5>
				<%}else{ %>
				<h5> Empanelled Hospital Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="EmpanneledHospitalList.htm">  Empanelled Hospital List </a></li>
						<%if(list!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Empanelled Hospital Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Empanelled Hospital Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	
<div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="EmpanelledHospitalAddEdit.htm" method="POST" id="addform" autocomplete="off">
						<div class="form-group">
							<div class="table-responsive">
								<table class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
										<tr>
											<th><label>Hospital Name <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Hospital Name" type="text" id="HospitalName" name="HospitalName" value="<%if(list!=null){ %><%=list.getHospitalName()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize; "
												></td>
										</tr>		
										
										<tr>
											<th><label>Hospital Address <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Hospital Address" type="text" id="HospitalAddress" name="HospitalAddress" value="<%if(list!=null){ %><%=list.getHospitalAddress()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize; "
												></td>
										</tr>									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="empanelledId" value="<%=list.getEmpanelledHospitalId()%>">
							<input type="hidden" name="action" value="EDITHOSPITAL">
								   <button type="submit" class="btn btn-sm submit-btn" onclick ="return confirm('Are You Sure To Update!')" >SUBMIT</button>
									<%}else{ %>
									<input type="hidden" name="action" value="ADDHOSPITAL">
									<button type="submit" class="btn btn-sm submit-btn" onclick ="return confirm('Are You Sure To Submit!')" >SUBMIT</button>
									<%} %>
							</div>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					</form>
				</div>
	   </div>
	</div>
</div>

</body>
<script type="text/javascript">

$('#HospitalName').keypress(function (e) {
    var regex = new RegExp("^[a-zA-Z \s]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    }
    else
    {
    e.preventDefault();
    alert('Please Enter Alphabate');
    return false;
    }
});


</script>
</html>
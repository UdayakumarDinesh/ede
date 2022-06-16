<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*" %>  <%@page import="com.vts.ems.master.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Doctor</title>
</head>
<body>

<%
List<Object[]> main = (List<Object[]>)request.getAttribute("treatment");
DoctorList list = (DoctorList)request.getAttribute("doctor");
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(list!=null){ %>
				<h5> Doctor Edit</h5>
				<%}else{ %>
				<h5> Doctor Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="DoctorList.htm">  Doctor List </a></li>
						<%if(list!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Doctor Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Doctor Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	
<div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="DoctorAddEdit.htm" method="POST" id="addform" autocomplete="off">
						<div class="form-group">
							<div class="table-responsive">
								<table class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
									
										<tr>
											<th><label>Doctor Name <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Doctor Name" type="text" id="DoctorName" name="DoctorName" value="<%if(list!=null){ %><%=list.getDoctorName()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize; "
												></td>
										</tr>								
											<tr>
											<th><label>Qualification <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Qualification" type="text" id="Qualification" name="Qualification" value="<%if(list!=null){ %><%=list.getQualification()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; text-transform:capitalize; "
												></td>
										</tr>
									
								
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="doctorId" value="<%=list.getDoctorId()%>">
							<input type="hidden" name="action" value="EDITDOCTOR">
								   <button type="submit" class="btn btn-sm submit-btn" onclick ="return confirm('Are You Sure To Update!')" >SUBMIT</button>
									<%}else{ %>
									<input type="hidden" name="action" value="ADDDOCTOR">
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

$('#DoctorName').keypress(function (e) {
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

$('#Qualification').keypress(function (e) {
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
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<style type="text/css">



</style>
</head>
<body>
<%
List<Object[]> loginlist = (List<Object[]>)request.getAttribute("loginlist");
List<Object[]> emplist = (List<Object[]>) request.getAttribute("emplist");

%>


<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>LoginType Add</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="LoginMaster.htm"> Login List </a></li>
						<li class="breadcrumb-item active " aria-current="page">LoginType Add</li>
					</ol>
				</div>
			</div>
		 </div>
	
	
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<div class="form-group">
						<div class="table-responsive">
							<table
								class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
								<tr>
										<th><label>USER NAME: <span class="mandatory"
												style="color: red;">*</span>
										</label></th>
										<td><input class="form-control form-control"
											placeholder="UserName" type="text" name="UserName"
											required="required" maxlength="255" style="font-size: 15px;"
											id="UserNameCheck">
											<div id="UserNameMsg" style="color: red;"></div></td>
										<td><input type="submit" class="btn btn-primary btn-sm"
											value="CHECK" id="check" /></td>
									</tr>
							
							</table>
						</div>
					</div>

					<form name="myfrm" action="UserManagerAddSubmit.htm" method="POST">
						<div class="form-group">
							<div class="table-responsive">
								<table
									class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
										<tr>
											<th><label>USER NAME: <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder="UserName" type="text" name="UserName"
												required="required" maxlength="255" style="font-size: 15px;"
												id="UserName" readonly="readonly"></td>
										</tr>

										<tr>
											<th><label>LOGIN TYPE: <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2" name="LoginType" id="LoginType" data-container="body" data-live-search="true" required="required" style="font-size: 5px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%for(Object[] login :loginlist){ %>
													<option value="<%=login[0]%>"><%=login[1]%></option>
													<%} %>
											</select></td>
										</tr>
										<tr>
											<th><label>EMPLOYEE: <span class="mandatory" style="color: red;">*</span>
											</label></th>
											<td><select class="form-control select2"
												name="Employee" id="Employee" data-container="body"
												data-live-search="true" style="font-size: 5px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option> 	
												<%for(Object[] emp:emplist){ %>	
											<option value="<%=emp[0]%>"><%=emp[1]%></option>
												<%}%>
											</select></td>
										</tr>
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action" value="submit">SUBMIT</button>
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


$("#UsernameSubmit").hide();
$(document).ready(function() {
	$("#check").click(function() {
		// SUBMIT FORM

		$('#UserName').val("");
		$("#UsernameSubmit").hide();
		var $UserName = $("#UserNameCheck").val();
		if ($UserName != "" && $UserName.length >= 4) {

			$.ajax({

				type : "GET",
				url : "UserNamePresentCount.htm",
				data : {
					UserName : $UserName
				},
				datatype : 'json',
				success : function(result) {

					var result = JSON.parse(result);
					var s = '';
					if (result > 0) {
						
						s = "UserName Not Available";
						$('#UserNameMsg').show(s);
						$('#UserNameMsg').html(s);
						$("#UsernameSubmit").hide();
						
					} else {
						
						$('#UserNameMsg').hide(s);
						$('#UserName').val($UserName);
						$("#UsernameSubmit").show();
					}
				}
			});
		}
	});
});




</script>
</html>
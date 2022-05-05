<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ page import="com.vts.ems.pis.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Designation</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>


<%
EmployeeDesig desig = (EmployeeDesig)request.getAttribute("desig");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(desig!=null){ %>
				<h5>Designation Edit</h5>
				<%}else{ %>
				<h5>Designation Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="Designation.htm">Designation List </a></li>
						<%if(desig!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Designation Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Designation Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	
	 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="DesignationAddEdit.htm" method="POST" id="myfrm1" autocomplete="off">
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										<tr>
											<th><label>Designation Code<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Designation Code" type="text" name="Designationcode" value="<%if(desig!=null){ %><%=desig.getDesigCode()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;"
												id="Name" ></td>
										</tr>
										<tr>
											<th><label>Designation Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Designation Name" type="text" name="DesignationName" value="<%if(desig!=null){%> <%=desig.getDesignation()%> <%} %>"
												required="required" maxlength="255" style="font-size: 15px;"
												id="Name" ></td>
										</tr>
										<tr>
											<th><label>Designation Limit  <i class="fa fa-inr" aria-hidden="true"></i> <span class="mandatory" style="color: red;"> *</span>
											</label></th>
											<td><input class="form-control form-control"
												placeholder="Enter Designation Limit" type="text" id="RateValue" name="Designationlimit" value="<%if(desig!=null){%><%=desig.getDesigLimit()%> <%}%>"
												required="required" maxlength="10" style="font-size: 15px;"
												id="Rate" ></td>
										</tr>
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(desig!=null){ %>
							<input type="hidden" name="deisignationid" value="<%=desig.getDesigId()%>">
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure to Update!');"
									name="action"  value="EDITTEST">SUBMIT</button>
									<%}else{ %>
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure to Submit!');"
									name="action"  value="ADDTEST">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					</form>
				</div>
	   </div>
	</div>

</div>
<script type="text/javascript">
setPatternFilter($("#RateValue"), /^-?\d*$/);
function setPatternFilter(obj, pattern) {
	  setInputFilter(obj, function(value) { return pattern.test(value); });
	}

function setInputFilter(obj, inputFilter) {
	  obj.on("input keydown keyup mousedown mouseup select contextmenu drop", function() {
	    if (inputFilter(this.value)) {
	      this.oldValue = this.value;
	      this.oldSelectionStart = this.selectionStart;
	      this.oldSelectionEnd = this.selectionEnd;
	    } else if (this.hasOwnProperty("oldValue")) {
	      this.value = this.oldValue;
	      this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
	    }
	  });
	}
</script>


</body>
</html>
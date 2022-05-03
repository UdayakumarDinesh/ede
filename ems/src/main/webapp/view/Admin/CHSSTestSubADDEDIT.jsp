<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@page import="java.util.List"%>
       <%@page import="com.vts.ems.chss.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>CHSS </title>
</head>
<body>


<%
List<Object[]> main =(List<Object[]>)request.getAttribute("TestMain");
CHSSTestSub list = (CHSSTestSub)request.getAttribute("subdata");

%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(list!=null){ %>
				<h5>CHSS Test Edit</h5>
				<%}else{ %>
				<h5>CHSS Test Add</h5>
				<%}%>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="TestSub.htm"> CHSS Test List </a></li>
						<%if(list!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Test Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Test Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
	
	 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="ChssTestSub.htm" method="POST">
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									
									
										<tr>
											<th><label>Main <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2" name="Main" id="Main" data-container="body" data-live-search="true" required="required" style="font-size: 5px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(main!=null&&main.size()>0){for(Object[] O:main){ %>
												<option value="<%=O[0]%>" <%if(list!=null){if(list.getTestMainId()==Long.parseLong(O[0].toString())){%> selected <%}}%>> <%=O[1]%></option>
												<%}}%>
											</select></td>
										</tr>
										<tr>
											<th><label>Name <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Name" type="text" name="Name" value="<%if(list!=null){ %><%=list.getTestName()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;"
												id="Name" ></td>
										</tr>

									
										<tr>
											<th><label> Rate  <i class="fa fa-inr" aria-hidden="true"></i> <span class="mandatory" style="color: red;"> *</span>
											</label></th>
											<td><input class="form-control form-control"
												placeholder="Enter Rate" type="text" id="RateValue" name="Rate" value="<%if(list!=null){%><%=list.getTestRate()%><%}%>"
												required="required" maxlength="10" style="font-size: 15px;"
												id="Rate" ></td>
										</tr>
									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="SubId" value="<%=list.getTestSubId()%>">
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
									name="action"  value="EDITTEST">SUBMIT</button>
									<%}else{ %>
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are You Sure To Submit?');"
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
<script type="text/javascript">


</script>
</body>
</html>
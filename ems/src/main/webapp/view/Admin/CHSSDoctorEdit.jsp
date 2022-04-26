<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.*" %>
    <%@page import="com.vts.ems.chss.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>CHSS Doctors Edit</title>
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>

<%
List<Object[]> main =(List<Object[]>)request.getAttribute("treatment");
CHSSDoctorRates list = (CHSSDoctorRates)request.getAttribute("docrate");
%>



<div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Doctors Edit</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="DoctorsMaster.htm"> CHSS Doctors List</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Doctors Edit</li>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >

					<form name="myfrm" action="DoctorsMaster.htm" method="POST" id="addform">
						<div class="form-group">
							<div class="table-responsive">
								<table
									class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">
									<thead>
									
									<%-- 	<tr>
											<th><label>Treatment Name <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2"  name="Treatementid" id="Treatementid" data-container="body" data-live-search="true" onchange="SetIsAdmissible();" required="required" style="font-size: 25px;">
												<option value="" disabled="disabled" selected="selected" hidden="true">--Select--</option>
												<%if(main!=null&&main.size()>0){for(Object[] O:main){ %>
												
												<option value="<%=O[0]%>" <%if(list!=null){if(list.getTreatTypeId()==Long.parseLong(O[0].toString())){%> selected <%}}%>> <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>								
											<tr>
											<th><label>Doctor Qualification <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Doctor Qualification" type="text" id="DocQualification" name="DocQualification" value="<%if(list!=null){ %><%=list.getDocQualification()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;">
												</td>
										    </tr>
										    <tr>
											<th><label>Doctor Rating <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Doctor Rating" type="text" id="DocRating" name="DocRating" value="<%if(list!=null){ %><%=list.getDocRating()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;">
												</td>
										    </tr> --%>
										    <tr>
											<th><label>Consultation-1 <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Consultation-1" type="text" id="Consultation1" name="Consultation1" value="<%if(list!=null){ %><%=list.getConsultation_1()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;">
												</td>
										    </tr>
										    <tr>
											<th><label>Consultation-2 <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Consultation-2" type="text" id="Consultation2" name="Consultation2" value="<%if(list!=null){ %><%=list.getConsultation_2()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px;">
												</td>
										    </tr>	
								</thead>
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
						    <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
							<input type="hidden" name="Rateid" value="<%if(list!=null){ %><%=list.getDocRateId()%><%}%>">
							<input type="hidden" name="Action" value="EDITDOCRATE">
							<button type="submit" class="btn btn-sm submit-btn" onclick="return confirm('Are You Sure To Update');">UPDATE</button>							
						</div>
			
				</form>
			</div>
	   </div>
	</div>
</div>
</body>
<script type="text/javascript">
setPatternFilter($("#Consultation1"), /^-?\d*$/);
setPatternFilter($("#Consultation2"), /^-?\d*$/);
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
</html>
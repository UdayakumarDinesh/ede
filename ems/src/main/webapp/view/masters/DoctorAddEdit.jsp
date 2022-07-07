<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*" %>  <%@page import="com.vts.ems.master.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
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
						<!-- <li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li> -->
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
				<%if(list!=null){%>	
					<form name="myfrm" action="DoctorEdit.htm" method="POST" id="addform" autocomplete="off" enctype="multipart/form-data" >
					<%}else{%>
					<form name="myfrm" action="DoctorAdd.htm" method="POST" id="addform" autocomplete="off">
						<%}%>
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
												 maxlength="255" style="font-size: 15px; "
												></td>
										</tr>
										<tr>
											<th><label>Address <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Doctor Address" type="text" id="address" name="address" value="<%if(list!=null){ %><%=list.getAddress()%><%} %>"
												required="required" maxlength="255" style="font-size: 15px; "
												></td>
										</tr>								
											<tr>
											<th><label>Phone No <span class="mandatory" style="color: red;">*</span></label></th>
											<td><input class="form-control form-control"
												placeholder=" Enter Phone No" type="text" id="phoneno" name="phoneno" value="<%if(list!=null){ %><%=list.getPhoneNo()%><%} %>"
												required="required" maxlength="10" style="font-size: 15px; text-transform:capitalize; "
												onblur="checknegative(this) "></td>
										</tr>
									
								
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(list!=null){ %>
							<input type="hidden" name="doctorId" value="<%=list.getDoctorId()%>">
							<input type="hidden" name="action" value="EDITDOCTOR">
								   <button type="submit" class="btn btn-sm submit-btn" onclick ="return CommentsModel()" >SUBMIT</button>
									<%}else{ %>
									<input type="hidden" name="action" value="ADDDOCTOR">
									<button type="submit" class="btn btn-sm submit-btn" onclick ="return confirm('Are You Sure To Submit!')" >SUBMIT</button>
									<%} %>
							</div>
						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
			<% if(list!=null){%>			
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
					        	 <div class="form-group "  >
					               <label>File : &nbsp;&nbsp;&nbsp;</label> 
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile"  > 
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="ADDITEM" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->						
						<%}%>
					<%if(list!=null){%></form><%}else{%></form><%}%>
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
<script type="text/javascript">


setPatternFilter($("#phoneno"), /^-?\d*$/);

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
function checknegative(str) {
    if (parseFloat(document.getElementById(str.id).value) < 0) {
        document.getElementById(str.id).value = "";
        document.getElementById(str.id).focus();
        alert('Negative Values Not allowed');
        return false;
    }
}

</script>
<script type="text/javascript">
function CommentsModel()
{
	var docName = $('#DoctorName').val();
	var qualification = $('#Qualification').val();
    var address = $('#address').val();
    var phoneno = $('#phoneno').val();
    if(docName=="" || docName=="null" || docName==null){
    	alert("Enter the Doctor Name!");
    	return false;
    }else if(qualification=="" || qualification=="null" || qualification==null){
    	alert("Enter the Qualification!");
    	return false;
    }else if(address=="" || address=="null" || address==null){
    	alert("Enter the Address!");
    	return false;
    }else if(phoneno=="" || phoneno=="null" || phoneno==null){
    	alert("Enter the Phone Number!");
    	return false;
    }else{
    	$('#myModal').modal('show');
    	return false;
    }
		 
		
}
</script>
</html>
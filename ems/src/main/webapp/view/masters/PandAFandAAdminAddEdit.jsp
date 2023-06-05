
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.vts.ems.pis.model.Employee"%>
    <%@page import="java.util.List,com.vts.ems.master.model.PisAdmins"%>
    <%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>P&A And F&A</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
  <%
  List<Object[]> list = (List<Object[]>)request.getAttribute("emplist");
  PisAdmins PandA =(PisAdmins) request.getAttribute("PandA");
  %>
  
  <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(PandA!=null){ %>
				<h5>Approval Authority Edit</h5>
				<%}else{ %>
				<h5>Approval Authority Add</h5>
				<%}%>
			</div>
			
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					    <li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li> 
						 <li class="breadcrumb-item "><a href="ChssApproval.htm">Approval Authority</a></li>
						<%if(PandA!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Approval Authority Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Approval Authority Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
    <div class="page card dashboard-card">
		<div class="card-body">
		   <div class="card" >
				<div class="card-body" align="center" >
					<%if(PandA!=null){ %>
					<form name="myfrm" action="PandAFandAEdit.htm" method="POST" id="addfrm1" autocomplete="off" enctype="multipart/form-data">	
						<%}else{%>
					<form name="myfrm" action="PandAFandAAdd.htm" method="POST" id="addfrm1" autocomplete="off">	
						<%}%>
						<div class="form-group">
							<div class="table-responsive" align="center" style="margin-left: 10%;">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 75%;" >								
										<tr>
											<th style="width: 20%;"><label>Employee : <span class="mandatory"	style="color: red;">*</span></label></th>
											<td><select class="form-control select2" name="employee"  id="employee" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width: 80%;">
												<option disabled="disabled" selected="selected" hidden="true" value="">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] O:list){ %>
												<option value="<%=O[2]%>" <%if(PandA!=null){if( PandA.getEmpAdmin().equalsIgnoreCase(O[2].toString())  ){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											    </select></td>
										</tr>	
									    <tr>
											<th style="width: 20%;"><label>Admin Type : <span class="mandatory"	style="color: red;">*</span></label></th>
											  <td>
											    <select class="form-control select2"  name="adminType" id="adminType" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width: 80%;">
	                                               <option value="P" <%if(PandA!=null && PandA.getAdminType()!=null && "P".equalsIgnoreCase(PandA.getAdminType())) {%> selected="selected" <%} %>>P&A Admin</option>
	                                               <option value="F" <%if(PandA!=null && PandA.getAdminType()!=null && "F".equalsIgnoreCase(PandA.getAdminType())) {%> selected="selected" <%} %>>F&A Admin</option>
	                                               <option value="S" <%if(PandA!=null && PandA.getAdminType()!=null && "S".equalsIgnoreCase(PandA.getAdminType())) {%> selected="selected" <%} %>>Scrutiny Officer</option>
											    </select>
											  </td>
											<%-- <td ><select class="form-control select2"  name="" id="fanda" data-container="body" data-live-search="true"  required="required" style="font-size: 25px;width: 80%;">
												<option disabled="disabled" selected="selected" hidden="true" value="">--Select--</option>
												<%if(list!=null&&list.size()>0){for(Object[] O:list){ %>
												<option value="<%=O[2]%>" <%if(PandA!=null){if( PandA.getFandAAdmin().equalsIgnoreCase(O[2].toString())  ){%> selected   <%}}%> > <%=O[1]%></option>
												<%}}%>
											    </select></td> --%>
										</tr>
										<tr>
										  <th style="width: 20%;"><label> Duration : <span class="mandatory"	style="color: red;">*</span></label></th>
										   <td >
                                              <input class="form-control" type="text" id="fdate" name="fdate" value="<%if(PandA!=null && PandA.getAdminFrom()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(PandA.getAdminFrom().toString())%><%}%>" required="required" readonly="readonly" style="width: 35%;display: inline;">
                                               &emsp;-&emsp;					 
								              <input class="form-control" type="text" id="tdate" name="tdate" value="<%if(PandA!=null && PandA.getAdminTo()!=null){ %> <%=DateTimeFormatUtil.SqlToRegularDate(PandA.getAdminTo().toString())%><%}%>" required="required"  readonly="readonly" style="width: 35%;display: inherit;">
	                                      </td>
	                                      <td style="border: 0;width: 30%;"></td>
									    </tr>									
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
							<%if(PandA!=null){ %>				
							<input type="hidden" id="adminsId" name="adminsId" value="<%=PandA.getAdminsId()%>">
								<button type="submit" class="btn btn-sm submit-btn"
									onclick="return checkEdit('addfrm1');"
									name="action"  value="EDIT">SUBMIT</button>
									<%}else{ %>
									
									<button type="submit" class="btn btn-sm submit-btn"
									onclick="return confirm('Are you Sure to Submit');"
									name="action"  value="ADD">SUBMIT</button>
									
									<%} %>
							</div>
							<!-- <div id="reset" class="btn btn-sm btn-info" style="margin-left: 2%;">RESET</div> -->

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
		          <%if(PandA!=null){ %>
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="EDIT" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
			<!----------------------------- container Close ---------------------------->	
						<%}%>		
					<%if(PandA!=null){ %>
					</form>
					<%}else{ %>
					</form>
					<%} %>
				</div>
	        </div>
	 </div>

</div>

<script type="text/javascript">

function checkEdit(frmid){
	if(confirm('Are you Sure To Submit ?') ){
		$('#myModal').modal('show');
		return true;
	}
	else{
		return false;
	}
	
}

$( "#fdate" ).change( function(){
    
	$( "#tdate" ).daterangepicker({
		"singleDatePicker" : true,
	    "linkedCalendars" : false,
	    "showCustomRangeLabel" : true,
	    "minDate" :$("#fdate").val(),  
	    "cancelClass" : "btn-default",
	    showDropdowns : true,
	    	locale : {
	    	format : 'DD-MM-YYYY'
	    } 
	});

	
});

$( "#fdate" ).daterangepicker({
    "singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    /* "minDate" :new Date(),  */ 
    "cancelClass" : "btn-default",
    showDropdowns : true,
    locale : {
    	format : 'DD-MM-YYYY'
    }
});

        
$( "#tdate" ).daterangepicker({
	"singleDatePicker" : true,
    "linkedCalendars" : false,
    "showCustomRangeLabel" : true,
    /* "minDate" :$("#fdate").val(),  
    "startDate" : $("#fdate").val(), */
    "cancelClass" : "btn-default",
    showDropdowns : true,
    	locale : {
    	format : 'DD-MM-YYYY'
    } 
});




</script>

<!-- <script type="text/javascript">
$('#reset').click(function() {
    location.reload();
});
$( document ).ready(function() {
	$("#fanda").change(function(){
		var panda=$('#panda').val();
		var fanda=$('#fanda').val();
		document.getElementById('p2').value=panda
		document.getElementById('f1').value=fanda
		$("#panda").prop("disabled", true); 
	});
	$("#panda").change(function() {
		var fanda=$('#fanda').val();
		var panda=$('#panda').val();
		document.getElementById('p2').value=panda
		document.getElementById('f1').value=fanda
	      $("#fanda").prop("disabled", true);
	});
});
</script> -->
</body>
</html>
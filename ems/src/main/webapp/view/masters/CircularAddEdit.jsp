<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="com.vts.ems.master.model.CircularList" %>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Circular Add</title>
</head>
<body>
<%CircularList circular = (CircularList)request.getAttribute("circular"); %>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(circular!=null){ %>
				<h5>Circular Edit</h5>
				<%}else{%>
				<h5>Circular Add</h5>
				<%} %>
			</div>
				<div class="col-md-9">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="PisAdminDashboard.htm"> Admin </a></li>
						<li class="breadcrumb-item "><a href="CircularLists.htm"> Circular List </a></li>
						<%if(circular!=null){ %>
                        <li class="breadcrumb-item active " aria-current="page">Circular Edit</li>
                        <%}else{ %>
                         <li class="breadcrumb-item active " aria-current="page">Circular Add</li>
                         <%} %>
					</ol>
			   </div>
		</div>
	</div>
		 
	<div class="page card dashboard-card">
	<div class="card-body" >			
		<div class="card" >
			<div class="card-body main-card  " align="center" >
			<%if(circular!=null){ %>
				<form name="myfrm" action="CircularEDIT.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data">
				<%}else{%>
				<form name="myfrm" action="CircularADD.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data">
					<%}%>
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<div class="row" align="center">
					
				 	
					<div class="col-md-2" style="margin-right: -70px;">
						<div class="form-group">
							<label><b>Circular Date</b><span class="mandatory"	style="color: red;">*</span></label>
							 <input type="text" style="width: 60%; " class="form-control input-sm " value=""  id="circulardate" name="circulardate"  required="required" readonly="readonly" >
						</div>
					</div>
					<div class="col-md-2" style="margin-right: -65px;">
						<div class="form-group">						
							<label><b>To Date </b><span class="mandatory"	style="color: red;">*</span></label>
							<input type="text"  style="width: 60%;" class="form-control input-sm " value=""  id="todate" name="todate"  required="required" readonly="readonly" > 
						</div>
					</div> 
				
					<div class="col-md-2" style="margin-right: -25px;">
						<div class="form-group">						
							<label><b>Reference No</b><span class="mandatory"	style="color: red;">*</span></label>
							<input type="text"  style="width: 80%;" class="form-control input-sm " <%if(circular!=null && circular.getReferenceNo()!=null){%>value="<%=circular.getReferenceNo()%>" <%}%>   id="referenceno" name="referenceno"  required="required" > 
						</div>
					</div>
						
						<div class="col-md-2" >
							<div class="form-group">
							<label><b>Description </b><span class="mandatory"	style="color: red;">*</span></label>
							  <textarea id="descrip" name="description" rows="2" cols="60" style="border-bottom-color: gray;" required="required" ><%if(circular!=null && circular.getDescription()!=null){%> <%=circular.getDescription()%><%}%></textarea>
							</div>
						</div>
						<div class="col-md-2" style="margin-left: 23%;">
						<div class="form-group">
						<label><b>File</b> <span class="mandatory"	style="color: red;">*</span></label>
						<%if(circular!=null ){%>
						 <input type="file"   class="form-control input-sm "  value=" <%=circular.getPath()%>"   id="file" name="selectedFile" >
							<%}else{%>
							 <input type="file"   class="form-control input-sm "  value="" required="required"  id="file" name="selectedFile" >
							 <%}%>
					</div>
					</div>
						<%if(circular!=null && circular.getPath()!=null){%>
						<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="height: 10%; width: 4%;  margin-top: 2.5%;" name="path" value="<%=circular.getPath()%>//<%=circular.getOriginalName()%>" formaction="download-CircularFile-attachment" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											  <i style="color: #019267" class="fa-solid fa-download fa-2x"></i>
										    </button><%} %>
						
					</div>
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<div class="row" >
		    			<div class="col-12" align="center">
		    			<%if(circular!=null ){%>
		    			<input type="hidden" name="circular" value="<%if(circular.getCircularId()!=null){%><%=circular.getCircularId()%><%}%>">
		    				<button type="submit" class="btn btn-sm submit-btn AddItem"  onclick="return CommentsModel();" name="action" value="CircularEdit" >UPDATE</button>
		    			<%}else{ %>
		    				<button type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Submit?');" name="action" value="CircularAdd" >SUBMIT</button>
		    				<%} %>
		    			</div>
		    		</div> 
						<%if(circular!=null ){%>
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
					               <input type="file" class=" form-control w-100"   id="file" name="selectedFile1" > 
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
					        	<button type="submit"  class="btn btn-sm submit-btn" name="action" value="CircularEdit" onclick="return confirm('Are You Sure To Submit?');" >SUBMIT</button>
					        </div>
					       
					      </div>
					    </div>
					  </div>
					</div>
					<!----------------------------- container Close ---------------------------->
						
						<%} %>
						
					<%if(circular!=null){ %></form><%}else{%></form><%}%>
			</div>
	   </div>
	</div>

</div>
		 
		 
		 
		 
		 
		 
</body>
<script type="text/javascript">

$('#circulardate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	<%if(circular!=null){%>
	 "startDate" : new Date('<%=circular.getCircularDate()%>'), 
	<%}%>
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#todate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#circulardate').val(),   
	<%if(circular!=null){%>
	 "startDate" : new Date('<%=circular.getToDate()%>'), 
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});



$("#circulardate").change( function(){
	var circulardate = $("#circulardate").val();
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
			
		"minDate" : circulardate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
});						



</script>

<script type="text/javascript">
function CommentsModel()
{
	var description = $('#descrip').val();
	var refNo = $('#referenceno').val();
	if(description=="" || description==null || description=="null"){
		 alert("Enter the Description!");
		 return false;
	}else if(refNo==''|| refNo==null || refNo=="null" ){
		 alert("Enter the Reference No!");
		 return false;
	}else{
		 $('#myModal').modal('show');
		 return false;
	}
}


</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="com.vts.ems.Admin.model.CircularList" %>
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
				<div class="card-body " align="center" >

					<form name="myfrm" action="CircularADDEDIT.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data">
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<div class="row" align="center">
					
				 	<div class="col-md-1" >
					<%-- 	<div class="form-group">						
							<label><b>From Date </b><span class="mandatory"	style="color: red;">*</span></label>
						<%if(circular!=null && circular.getToDate()!=null){%>
							 <input type="text"  style="width: 90%;" class="form-control input-sm mydate" readonly="readonly" onchange=" setTodate()" value="<%if(circular!=null && circular.getFromDate()!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(circular.getFromDate().toString().trim())%><%}%>" placeholder=""  id="fromdate1" name="fromdate"  required="required"  > 
						<%}else{%>
							 <input type="text"  style="width: 90%;" class="form-control input-sm mydate" readonly="readonly" onchange=" setTodate()" value="" placeholder=""  id="fromdate" name="fromdate"  required="required"  > 
						<%}%>
						</div> --%>
						</div> 
						
						<div class="col-md-2" >
						<div class="form-group">
						<label><b>Circular Date</b><span class="mandatory"	style="color: red;">*</span></label>
						<%if(circular!=null && circular.getToDate()!=null){%>
						 <input type="text" style="width: 90%;" class="form-control input-sm mydate1" readonly="readonly" value="<%if(circular!=null && circular.getCircularDate()!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(circular.getCircularDate().toString().trim())%><%}%>" placeholder=""  id="todate1" name="circulardate"  required="required"  >
						<%}else{%>
						 <input type="text" style="width: 90%;" class="form-control input-sm mydate" readonly="readonly" value="" placeholder=""  id="todate" name="circulardate"  required="required"  >
						<%}%>
						</div>
						</div>
						<div class="col-md-3">
						<div class="form-group">
						<label><b>Description </b><span class="mandatory"	style="color: red;">*</span></label>
						  <textarea  name="description" rows="2" cols="60" style="border-bottom-color: gray;"  ><%if(circular!=null && circular.getDescription()!=null){%> <%=circular.getDescription()%><%}%></textarea>
						</div>
						</div>
						<div class="col-md-2" style="margin-left: 15%;">
						<div class="form-group">
						<label><b>File</b></label>
						 <input type="file"   class="form-control input-sm "  value="<%if(circular!=null && circular.getPath()!=null){%> <%=circular.getPath()%><%}%>"   id="file" name="selectedFile" >
					</div>
					</div>
						<%if(circular!=null && circular.getPath()!=null){%>
					
						<%-- <a href="download-CircularFile-attachment?path=<%=circular.getPath()%>//<%=circular.getOriginalName() %>" title="Download" ><i style="color: #019267; margin-top: 100%;" class="fa-solid fa-download fa-2x"></i></a> <%}%> --%>
						<button type="submit" class="btn btn-sm" style="height: 10%; width: 4%;  margin-top: 2.5%;" name="path" value="<%=circular.getPath()%>//<%=circular.getOriginalName()%>" formaction="download-CircularFile-attachment" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											  <i style="color: #019267" class="fa-solid fa-download fa-2x"></i>
										    </button><%} %>
						
					</div>
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					 <div class="row" >
			    			<div class="col-12" align="center">
			    			<%if(circular!=null ){%>
			    			<input type="hidden" name="circular" value="<%if(circular.getCircularId()!=null){%><%=circular.getCircularId()%><%}%>">
			    				<button type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Submit!');" name="action" value="CircularEdit" >UPDATE</button>
			    			<%}else{ %>
			    				<button type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Submit!');" name="action" value="CircularAdd" >SUBMIT</button>
			    				<%} %>
			    			</div>
			    		</div> 
						
					</form>
				</div>
	   </div>
	</div>

</div>
		 
		 
		 
		 
		 
		 
</body>
<script type="text/javascript">

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	 "minDate" :new Date(),   
	/* "startDate" : new Date(), */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
function setTodate()
{
	var fromdate = $("#fromdate").val();
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :fromdate,   
		/* "startDate" : new Date(), */
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
}
$(document).ready(function() {
	
	var fromdate = $("#fromdate").val();
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :fromdate,   
		 "startDate" : new Date(), 
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});


$('#fromdate1').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/*  "minDate" :new Date(), */   
	/* "startDate" : new Date(), */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});
function setTodate()
{
	var fromdate = $("#fromdate1").val();
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :fromdate,   
		/* "startDate" : new Date(), */
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
}
$(document).ready(function() {
	
	var fromdate = $("#fromdate1").val();
	
	$('#todate1').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :fromdate,   
		/*  "startDate" : new Date(),  */
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});
</script>
</html>
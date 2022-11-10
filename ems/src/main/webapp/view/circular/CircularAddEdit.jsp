<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="com.vts.ems.circularorder.model.EMSCircular" %>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Circular Add</title>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
<% EMSCircular CirEditDetails = (EMSCircular)request.getAttribute("circularDetails");   %>



<div class="card-header page-top">
  <div class="row">
  
     	<div class="col-md-3">
     	<%if(CirEditDetails==null){  %>
     	<h5>Circular Add</h5>
     	<%}else{%>
		<h5>Circular Edit</h5>
		<%	}  %> 
     	</div>
     	
     	<div class="col-md-9">
			<ol class="breadcrumb">
				<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
				<li class="breadcrumb-item "><a href="CircularDashBoard.htm"> Circular </a></li>
				<li class="breadcrumb-item "><a href="CircularList.htm"> Circular List </a></li>
						<%if(CirEditDetails==null){  %>
                        <li class="breadcrumb-item active " aria-current="page">Circular Add</li>
                         <%}else{%>
                          <li class="breadcrumb-item active " aria-current="page">Circular Edit</li>
                        <%}%> 
			</ol>
		</div>  
 </div>
</div>


<div class="page card dashboard-card">
 <div class="card-body"  align="center">
	<div class="card" style="width: 50%">
		
		<div class="card-body main-card" align="left" >
			<%if(CirEditDetails!=null){ %> 
				<form name="myfrm" action="CircularEditSubmit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data" >
			<%}else{ %>	
		       <form name="myfrm" action="CircularAddSubmit.htm" method="POST" id="myfrm1" autocomplete="off"  enctype="multipart/form-data">		
		    <%}%>	
			
					
				<div class="row" >
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>Circular Date</b><span class="mandatory"	style="color: red;">*</span></label>
							 <input type="text" class="form-control input-sm " value=""  id="circulardate" name="circularDate"  required="required" readonly="readonly" >
						</div>
					</div>
					
				</div>
				<div class="row" >
					<div class="col-md-6">
						<div class="form-group">						
							<label><b>Circular No.</b><span class="mandatory"	style="color: red;">*</span></label>
							<input type="text"  class="form-control input-sm " <%if(CirEditDetails!=null && CirEditDetails.getCircularNo()!=null){%>value="<%=CirEditDetails.getCircularNo()%>" <%}%>  name="circularno"  required="required" maxlength="100"> 
						</div>
					</div>
					
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>File</b> <span class="mandatory"	style="color: red;">*</span></label>
							<div class="row" >
								<div class="col-md-10" >
									<input type="file"  accept="application/pdf" style="width: 100%" class="form-control input-sm "  value="" <%if(CirEditDetails==null ){%> required="required" <%} %> id="cirFile" name="FileAttach" >
								</div>
								<div class="col-md-2" >
								<%if(CirEditDetails!=null ){%>
									<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="margin-left:-15px; margin-top: 2.5%;" 
									name="CircularId" value="<%=CirEditDetails.getCircularId()%>"
									 formaction="DepCircularDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
										  <i style="color: #019267" class="fa-solid fa-download fa-2x"></i>
									</button>
								<%} %>
								</div>
							</div>
						</div>
					</div>
					
					</div>
					<div class="row">
					<div class="col-md-12" >
						<div class="form-group">
						<label><b>Description</b><span class="mandatory"	style="color: red;">*</span></label><br>
						  <textarea id="descrip" name="cirSubject" style="border-bottom-color: gray;width: 100%" required="required" maxlength="255" 
						  placeholder="Maximum 255 characters" ><%if(CirEditDetails!=null && CirEditDetails.getCirSubject()!=null){%> <%=CirEditDetails.getCirSubject()%><%}%></textarea>
						</div>
					</div>
			
						
				</div>
					<div class="row" >
		    			<div class="col-md-12" align="center">
		    			<%if(CirEditDetails!=null ){%>
		    				<input type="hidden" name="circularIdSel" value="<%if(CirEditDetails!=null){%><%=CirEditDetails.getCircularId()%><%}%>">
		    				<button type="submit" class="btn btn-sm submit-btn "  onclick="return confirm('Are You Sure To Submit?');"  >UPDATE</button>
		    			<%}else{ %>
		    				<button type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Submit?');"  >SUBMIT</button>
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

$('#circulardate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	<% if(CirEditDetails!=null){%>
	 "startDate" : new Date('<%=CirEditDetails.getCircularDate()%>'), 
	<%}%>
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

</script>




<script type="text/javascript">


$(function(){
    $("#cirFile").on('change', function(event) {
        
    	
    	var file = $("#cirFile").val();
    	console.log(file);
       	var upld = file.split('.').pop();  
       	if(!(upld.toLowerCase().trim()==='pdf' ))
       	{
    	    alert("Only PDF are allowed to Upload")
    	    document.getElementById("cirFile").value = "";
    	    return;
    	}
        

        
    });
});



</script>
<!-- <script type="text/javascript">

function ValidatePdf(myfrm1){ 
	
	var
	var file = $("#cirFile").val();
	console.log(file);
	  var upld = file.split('.').pop();  

	  if(upld!='pdf'){
		  alert("Only PDF are allowed to Upload");
		event.preventDefault();
		return false;
	}
	 
	var cnf = confirm("Are You Sure To Submit!");
    if(cnf){
		
		document.getElementById("myfrm1").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	}
}

</script> -->




</body>
</html>
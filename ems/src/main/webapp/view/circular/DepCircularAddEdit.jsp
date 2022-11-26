<%@page import="com.vts.ems.circularorder.model.EMSDepCircular"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Circular Add</title>
</head>
<body>
<%	
EMSDepCircular circular = (EMSDepCircular)request.getAttribute("circular");
	Object[] DepType = (Object[])request.getAttribute("DepType");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			
			<%if(Integer.parseInt(DepType[0].toString())==9){ %>
				<%if(circular!=null){ %>
					<h5> Government Order Edit</h5>
					<%}else{%>
					<h5> Government Order Add</h5>
					<%} %>
				</div>
			<%}else{ %>
				<%if(circular!=null){ %>
					<h5> <%=DepType[2] %>&nbsp;Circular Edit</h5>
					<%}else{%>
					<h5> <%=DepType[2] %>&nbsp;Circular Add</h5>
					<%} %>
				</div>
			
			<%} %>
				<div class="col-md-9">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="CircularDashBoard.htm"> Circular </a></li>
						
						<%if(Integer.parseInt(DepType[0].toString())==9){ %>
							<li class="breadcrumb-item "><a href="DepCircularList.htm?id=<%=DepType[0]%>"> <%=DepType[2] %>&nbsp; List </a></li>
							<%if(circular!=null){ %>
	                        <li class="breadcrumb-item active " aria-current="page"> Government Order Edit</li>
	                        <%}else{ %>
	                         <li class="breadcrumb-item active " aria-current="page"> Government Order Add</li>
	                         <%} %>
	                    <%}else{ %>
	                    	<li class="breadcrumb-item "><a href="DepCircularList.htm?id=<%=DepType[0]%>"> <%=DepType[2] %>&nbsp;Circular List </a></li>
	                    	<%if(circular!=null){ %>
	                    	
	                        <li class="breadcrumb-item active " aria-current="page"><%=DepType[2] %>&nbsp;Circular Edit</li>
	                        <%}else{ %>
	                         <li class="breadcrumb-item active " aria-current="page"><%=DepType[2] %>&nbsp;Circular Add</li>
	                         <%} %>
	                    
	                    <%} %>
					</ol>
			   </div>
		</div>
	</div>
		 
	<div class="page card dashboard-card">
	<div class="card-body" align="center">			
		<div class="card" style="width: 50%">
			<div class="card-body main-card" align="left" >
			
			<%if(circular!=null){ %>
				<form  action="DepCircularEditSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data">
			<%}else{%>
				<form  action="DepCircularAddSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data">
			<%}%>
			
					
				<div class="row" >
					<div class="col-md-6" >
						<div class="form-group">
						<%if(Integer.parseInt(DepType[0].toString())==9){ %>
							<label><b>Order Date</b><span class="mandatory"	style="color: red;">*</span></label>
						<%}else{ %>
							<label><b>Circular Date</b><span class="mandatory"	style="color: red;">*</span></label>
						<%} %>
							 <input type="text" class="form-control input-sm " value=""  id="circulardate" name="CircularDate"  required="required" readonly="readonly" >
						</div>
					</div>
					
				</div>
				<div class="row" >
					<div class="col-md-6">
						<div class="form-group">	
						<%if(Integer.parseInt(DepType[0].toString())==9){ %>					
							<label><b>Order No.</b><span class="mandatory"	style="color: red;">*</span></label>
							<%}else{ %>
							<label><b>Circular No.</b><span class="mandatory"	style="color: red;">*</span></label>
						<%} %>
							<input type="text"  class="form-control input-sm " <%if(circular!=null && circular.getDepCircularNo()!=null){%>value="<%=circular.getDepCircularNo()%>" <%}%>  name="CircularNo"  required="required" maxlength="100" > 
						</div>
					</div>
					
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>File</b> <span class="mandatory"	style="color: red;">*</span></label>
							<div class="row" >
								<div class="col-md-10" >
									<input type="file"  accept="application/pdf" style="width: 100%" class="form-control input-sm "  value="" <%if(circular==null ){%> required="required" <%} %> id="cirFile" name="CircularFile" >
								</div>
								<div class="col-md-2" >
								<%if(circular!=null ){%>
									<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="margin-left:-15px; margin-top: 2.5%;" 
									name="CircularId" value="<%=circular.getDepCircularId()%>"
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
						  <textarea id="descrip" name="description" style="border-bottom-color: gray;width: 100%" required="required" maxlength="255" 
						  placeholder="Maximum 255 characters" ><%if(circular!=null && circular.getDepCirSubject()!=null){%> <%=circular.getDepCirSubject()%><%}%></textarea>
						</div>
					</div>
			
						
				</div>
					<div class="row" >
		    			<div class="col-md-12" align="center">
		    			<%if(circular!=null ){%>
		    				<input type="hidden" name="circularId" value="<%if(circular!=null){%><%=circular.getDepCircularId()%><%}%>">
		    				<button type="submit" class="btn btn-sm submit-btn "  onclick="return confirm('Are You Sure To Submit?');"  >UPDATE</button>
		    			<%}else{ %>
		    				<button type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Submit?');"  >SUBMIT</button>
		    				<%} %>
		    			</div>
		    		</div> 
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<input type="hidden" name="DepTypeId" value="<%=DepType[0]%>">		
				</form>
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
	 "startDate" : new Date('<%=circular.getDepCircularDate()%>'), 
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
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="com.vts.ems.circularorder.model.EMSOfficeOrder" %>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<!-- <style>

.card .card-body {
     
    width: 80% !important;
    margin:0 auto;
    padding-top: 25px;
}  

</style> -->
</head>
<body>
<% EMSOfficeOrder OrderEditDetails = (EMSOfficeOrder)request.getAttribute("OfficeOrderDetails");   %>



<div class="card-header page-top">
  <div class="row">
  
     	<div class="col-md-3">
     	<%if(OrderEditDetails==null){  %>
     	<h5>Office Order Add</h5>
     	<%}else{%>
		<h5>Office Order Edit</h5>
		<%	}  %> 
     	</div>
     	
     	<div class="col-md-9">
			<ol class="breadcrumb">
				<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>

				<li class="breadcrumb-item "><a href="OfficeOrder.htm"> Office Order List </a></li>
						<%if(OrderEditDetails==null){  %>
                        <li class="breadcrumb-item active " aria-current="page">Office Order Add</li>
                         <%}else{%>
                          <li class="breadcrumb-item active " aria-current="page">Office Order Edit</li>
                        <%}%> 
			</ol>
		</div>  
 </div>
</div>


<div class="page card dashboard-card">
 <div class="card-body" align="center">
 
	<div class="card" style="width:50%">
	  <div class="card-body main-card  " align="left" >
	<%if(OrderEditDetails!=null){ %> 
		<form name="myfrm" action="OfficeOrderEditSubmit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data" >
	<%}else{ %>	
       <form name="myfrm" action="OfficeOrderAddSubmit.htm" method="POST" id="myfrm1" autocomplete="off"  enctype="multipart/form-data">		
    <%}%>	
	  <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	  
	   <div class="row" > 
	   	     
	        <div class="col-md-6" >
			   <div class="form-group">
				  <label><b>Order No</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 100%; " class="form-control input-sm "  name="OrderNo"  id="OrderNo" <%if(OrderEditDetails!=null && OrderEditDetails.getOrderNo()!=null){%>value="<%=OrderEditDetails.getOrderNo()%>" <%}%>   maxlength="20"  required="required" >
				</div>
			</div>
		</div>	
		
	  
	 <div class="row" >  	     	     	     
	       <div class="col-md-6"  >
			   <div class="form-group">
				  <label><b>Order Date</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 100%; " class="form-control input-sm " value="" name="OrderDate"  id="OrderDate"   required="required"  >
				</div>
			</div>
					       			
			  <div class="col-md-6">
			  <div class="form-group">
					<label><b>Upload File</b> <span class="mandatory"	style="color: red;">*</span></label>
	<div class="row">
		<div class="col-md-10">
			<%if(OrderEditDetails!=null ){%>
			<%System.out.println("filepath"+OrderEditDetails.getOrderPath());%>
			<input type="file" name="EditFileAttach" style="width:100%"  accept="application/pdf"  class="form-control input-sm "  value=""   id="OrderFile"  >
			<%}else{ %>
			<input type="file" name="FileAttach"  style="width:100%" accept="application/pdf"  class="form-control input-sm "  value="" required="required"  id="OrderFile"  >
			<%} %>	
			</div>
			<div class="col-md-2">		
			<!-- Download Button Incase of Edit -->		 
			<%if(OrderEditDetails!=null && OrderEditDetails.getOrderPath()!=null){%>	
			<button type="submit" class="btn btn-sm" formnovalidate="formnovalidate"  name="OrderId" value="<%=OrderEditDetails.getOrderId()%>" 
			formaction="OfficeOrderDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" 
			style=" margin-left:-50%;" > <i class="fa-solid fa-download fa-2x" style="color: green;"></i></button>
			<%} %>	
						
			  </div>			
			</div>
		  </div>
	  </div> 
	</div> 
	     <div class="row">
	       <div class="col-md-12">
			   <div class="form-group">
				  <label><b>Subject</b><span class="mandatory"	style="color: red;">*</span></label>		  
				   <textarea id="OrderSubject" name="OrderSubject" rows="2"  style="border-bottom-color: gray;width:100%" required="required" ><%if(OrderEditDetails!=null && OrderEditDetails.getOrderSubject()!=null){%><%=OrderEditDetails.getOrderSubject()%><%}%></textarea> 
				</div>
			</div>
	  </div>					     	 	 		  	
	<!--  <br> -->
	      <div class="row" >
		   <div class="col-12" align="center">
		   
		    <%if(OrderEditDetails!=null ){%>
		    <input type="hidden" name="OrderIdSel" value="<%if(OrderEditDetails!=null){%><%=OrderEditDetails.getOrderId()%><%}%>">
		    <button type="submit" class="btn btn-sm submit-btn " id="editOrder"  onclick="return confirm('Are You Sure To Submit?');" >UPDATE</button>
		    <%}else{ %>
		     <button type="submit"   class="btn btn-sm submit-btn"  id="addOrder" onclick="return confirm('Are You Sure To Submit?');"  >SUBMIT</button>
		     <%} %>  
		    	</div>
		    </div> 
				
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						
		<%if(OrderEditDetails!=null){%> 
		</form>
		<%}else{%>
	    </form>
	  <%}%>
	 
	  
	  </div>
	
	
	</div>
  </div>
 </div>
		


<script type="text/javascript">

$('#OrderDate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	<% if(OrderEditDetails!=null){%>
	 "startDate" : new Date('<%=OrderEditDetails.getOrderDate()%>'), 
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
    $("#OrderFile").on('change', function(event) {
        
    	
    	var file = $("#OrderFile").val();
    	console.log(file);
       	var upld = file.split('.').pop();  
       	if(!(upld.toLowerCase().trim()==='pdf' ))
       	{
    	    alert("Only PDF are allowed to Upload")
    	    document.getElementById("OrderFile").value = "";
    	    return;
    	}
        

        
    });
});



</script>


</body>
</html>
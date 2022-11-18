<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@ page import="com.vts.ems.circularorder.model.EMSOfficeOrder" %>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<title>Circular Add</title>

<style>
.card .card-body {
     
    width: 80% !important;
    margin:0 auto;
    padding-top: 25px;
} 

</style>
</head>
<body>
<% EMSOfficeOrder OrderEditDetails = (EMSOfficeOrder)request.getAttribute("OfficeOrderDetails");   %>



<div class="card-header page-top">
  <div class="row">
  
     	<div class="col-md-3">
     	<%if(OrderEditDetails==null){  %>
     	<h5>OfficeOrder Add</h5>
     	<%}else{%>
		<h5>OfficeOrder Edit</h5>
		<%	}  %> 
     	</div>
     	
     	<div class="col-md-9">
			<ol class="breadcrumb">
				<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>

				<li class="breadcrumb-item "><a href="OfficeOrder.htm"> OfficeOrder List </a></li>
						<%if(OrderEditDetails==null){  %>
                        <li class="breadcrumb-item active " aria-current="page">OfficeOrder Add</li>
                         <%}else{%>
                          <li class="breadcrumb-item active " aria-current="page">OfficeOrder Edit</li>
                        <%}%> 
			</ol>
		</div>  
 </div>
</div>


<div class="page card dashboard-card">
 <div class="card-body" >
	<div class="card" >
	  <div class="card-body main-card  " align="center" >
	<%if(OrderEditDetails!=null){ %> 
		<form name="myfrm" action="OfficeOrderEditSubmit.htm" method="POST" id="myfrm1" autocomplete="off" enctype="multipart/form-data" >
	<%}else{ %>	
       <form name="myfrm" action="OfficeOrderAddSubmit.htm" method="POST" id="myfrm1" autocomplete="off"  enctype="multipart/form-data">		
    <%}%>	
	  <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	  
	   <div class="row" align="center"> 
	   
	       <!--  <div class="col-md-3" style="margin-left: 20px;" > -->
	        <div class="col-md-3" style="margin-left: 10px;" >
			   <div class="form-group">
				  <label><b>Order No</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 70%; " class="form-control input-sm "  name="OrderNo"  id="OrderNo" <%if(OrderEditDetails!=null && OrderEditDetails.getOrderNo()!=null){%>value="<%=OrderEditDetails.getOrderNo()%>" <%}%>   maxlength="20"  required="required" >
				</div>
			</div>
			
	     <div class="col-md-2" style="margin-left: 200px;" >
			   <div class="form-group">
				  <label><b>Subject</b><span class="mandatory"	style="color: red;">*</span></label>
				   <textarea id="OrderSubject" name="OrderSubject" rows="2" cols="50" style="border-bottom-color: gray;" required="required" ><%if(OrderEditDetails!=null && OrderEditDetails.getOrderSubject()!=null){%><%=OrderEditDetails.getOrderSubject()%><%}%></textarea> 
				</div>
			</div>
     
	      
			
	  </div>
	  
	 <div class="row" align="center">  
	     
	     
	      <!-- <div class="col-md-3" style="margin-left: 20px;"> -->
	       <div class="col-md-3" style="margin-left: 10px;" >
			   <div class="form-group">
				  <label><b>Order Date</b><span class="mandatory"	style="color: red;">*</span></label>
				  <input type="text" style="width: 70%; " class="form-control input-sm " value="" name="OrderDate"  id="OrderDate"   required="required"  >
				</div>
			</div>
				
	       
			
			  <div class="col-md-3" style="margin-left: 200px;">
			  <div class="form-group">
					<label><b>Upload File</b> <span class="mandatory"	style="color: red;">*</span></label>
			<%if(OrderEditDetails!=null ){%>
			<%System.out.println("filepath"+OrderEditDetails.getOrderPath());%>
			<input type="file" name="EditFileAttach"   accept="application/pdf"  class="form-control input-sm "  value=""   id="OrderFile"  >
			<%}else{ %>
			<input type="file" name="FileAttach"   accept="application/pdf"  class="form-control input-sm "  value="" required="required"  id="OrderFile"  >
			<%} %>				
			</div>
		  </div>
		  
			<!-- Download Button Incase of Edit -->		 
			<%if(OrderEditDetails!=null && OrderEditDetails.getOrderPath()!=null){%>	
			<button type="submit" class="btn btn-sm" formnovalidate="formnovalidate"  name="OrderId" value="<%=OrderEditDetails.getOrderId()%>" 
			formaction="OfficeOrderDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" 
			style="height: 10%; width: 6%;  margin-top: 4%;" > <i class="fa-solid fa-download fa-2x" style="color: green;"></i></button>
			<%} %>		
		  
		
	 </div> 
	 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
	  
	
	 <br>
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
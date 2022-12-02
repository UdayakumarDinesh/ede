<%@page import="java.util.List"%>
<%@page import="com.vts.ems.circularorder.model.EMSGovtOrders"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
</head>
<body>
<%	
	EMSGovtOrders Order = (EMSGovtOrders)request.getAttribute("Order");
	List<Object[]> TopicList=(List<Object[]>)request.getAttribute("TopicList");
	String TopicId = (String)request.getAttribute("TopicId");
	if(Order!=null)
	{
		TopicId=String.valueOf(Order.getTopicId());
	}
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			
				<%if(Order!=null){ %>
					<h5> Government Order Edit</h5>
					<%}else{%>
					<h5> Government Order Add</h5>
					<%} %>
				</div>
			
				<div class="col-md-9">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
	                    	<li class="breadcrumb-item "><a href="GovtOrdersList.htm"> Government Orders </a></li>
	                    	<%if(Order!=null){ %>
	                        <li class="breadcrumb-item active " aria-current="page">Government Order Edit</li>
	                        <%}else{ %>
	                        <li class="breadcrumb-item active " aria-current="page">Government Order Add</li>
	                    
	                    	<%} %>
					</ol>
			   </div>
		</div>
	</div>
		 
	<div class="page card dashboard-card">
	<div class="card-body" align="center">			
		<div class="card" style="width: 50%">
			<div class="card-body main-card" align="left" >
			
			<%if(Order!=null){ %>
				<form  action="GovtOrderEditSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data">
			<%}else{%>
				<form  action="GovtOrderAddSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data">
			<%}%>
			
					
				<div class="row" >
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>Order Date</b><span class="mandatory"	style="color: red;">*</span></label>
							<input type="text" class="form-control input-sm " value=""  id="Orderdate" name="OrderDate"  required="required" readonly="readonly" >
						</div>
					</div>
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>Topic</b><span class="mandatory"	style="color: red;">*</span></label>
							<select class="form-control select2" name="TopicId" >
								<%for(Object[] topic : TopicList){ %>
									<option value="<%=topic[0]%>"  <%if(TopicId.equalsIgnoreCase(topic[0].toString())){ %>selected <%} %> ><%=topic[2]%></option>
								<%} %>
							</select>
						</div>
					</div>
				</div>
				<div class="row" >
					<div class="col-md-6">
						<div class="form-group">	
							<label><b>Order No.</b><span class="mandatory"	style="color: red;">*</span></label>
							<input type="text"  class="form-control input-sm " <%if(Order!=null && Order.getOrderNo()!=null){%> value="<%=Order.getOrderNo()%>" <%}%>  name="OrderNo"  required="required" maxlength="100" > 
						</div>
					</div>
					
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>File</b> <span class="mandatory"	style="color: red;">*</span></label>
							<div class="row" >
								<div class="col-md-10" >
									<input type="file"  accept="application/pdf" style="width: 100%" id="OrderFile" name="OrderFile"  class="form-control input-sm "  value="" <%if(Order==null ){%> required="required" <%} %> >
								</div>
								<div class="col-md-2" >
								<%if(Order!=null ){%>
									<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="margin-left:-15px; margin-top: 2.5%;" 
									name="OrderId" value="<%=Order.getGovtOrderId()%>"
									 formaction="GovtOrderDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
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
						  placeholder="Maximum 255 characters" ><%if(Order!=null && Order.getDescription()!=null){%> <%=Order.getDescription()%><%}%></textarea>
						</div>
					</div>
			
						
				</div>
					<div class="row" >
		    			<div class="col-md-12" align="center">
		    			<%if(Order!=null ){%>
		    				<input type="hidden" name="OrderId" value="<%=Order.getGovtOrderId()%>">
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
		 
</body>
<script type="text/javascript">

$('#Orderdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	<%if(Order!=null){%>
	 "startDate" : new Date('<%=Order.getOrderDate()%>'), 
	<%}%>
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$("#Orderdate").change( function(){
	var Orderdate = $("#Orderdate").val();
	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
			
		"minDate" : Orderdate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
});						

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
</html>
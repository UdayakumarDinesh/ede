<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.Mt.model.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>MT</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%Object[] list = (Object[])request.getAttribute("requestedit"); 
String empname= (String)request.getAttribute("empname");
%>
<div class="card-header page-top ">
		<div class="row">
				<div class="col-md-6">
					<h5>Edit Request <small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
				</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="MtDashboard.htm">MT</a></li>
						<li class="breadcrumb-item active " aria-current="page">MT Edit Request</li>	
					</ol>
				</div>
		</div>
</div>


 <div class="card" >
	<div class="card-body" >
		<div class="row">
		<div class="col-2"></div>
	<div class="col-8">
		<div class="card" >					
			<div class="card-body">
				<form action="MTRequestEditSubmit.htm" method="post" autocomplete="off" id="requesttripform">
				
				    <div class="form-group">
				        <div class="row">
				        	<div class="col-md-3">
						 	<label>Date of Start:<span class="mandatory">*</span></label>
							<div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly"    id="dos" name="Dtravel"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">						      
							    </label>                    
							</div>
						 </div>
						 
						 <div class="col-md-3">
						 	<label>Start Time (in Hrs):<span class="mandatory">*</span></label>
							<div class=" input-group">
							     <input  class="form-control form-control-sm" id="stime" <%if(list!=null&&list[2]!=null){%> value="<%=list[2] %>"<%}%> placeholder="Start Time"   name="Dtime" value=""  maxlength="250" required="required">                     
							</div>
						 </div>
						 
						 <div class="col-md-3">
						 	<label>Date of End:<span class="mandatory">*</span></label>
							<div class=" input-group">
							    <input type="text" class="form-control input-sm mydate" readonly="readonly"  placeholder=""  id="doe" name="Etravel"  required="required"  > 
							    <label class="input-group-addon btn" for="testdate">  
							    </label>                    
							</div>
						 </div>
						 
						 
						 <div class="col-md-3">
						 	<label>Return Time (in Hrs):<span class="mandatory">*</span></label>
							<div class=" input-group">
						        <input  class="form-control form-control-sm"    id="etime" name="Rtime" <%if(list!=null&&list[3]!=null){%>value="<%=list[3] %>" <%}%>  maxlength="250" required="required">
						 	</div>
						 </div>
						 
						</div>
					</div>
					
					<div class="form-group">
				        <div class="row">
				        
				        	<div class="col-md-4">					        	
					                <label>Source:<span class=" mandatory ">*</span></label>
					                <input type="text"  name="Source" id="source" <%if(list!=null&&list[4]!=null){%>value="<%=list[4] %>" <%}%> class=" form-control input-sm " maxlength="99"   placeholder="Source "  required="required">
							</div>
							<div class="col-md-4">	</div>
							<div class="col-md-4">					        	
					                <label>Destination:<span class=" mandatory ">*</span></label>
					                <input type="text"  name="Destination" id="destination" <%if(list!=null&&list[5]!=null){%>value="<%=list[5] %>" <%}%> class=" form-control input-sm " maxlength="99"   placeholder="Destination "  required="required">
							</div>

						</div>
					</div>
					
			
					
						 <div class="row" >
						    	<div class="col-12" align="center">
						    	    <input type="hidden" name="reqid1" <%if(list!=null&&list[0]!=null){%>value="<%=list[0] %>" <%}%>> 
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						    	    <button type="button" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="CheckData()" >SUBMIT</button>
						        </div>
				         </div> 		
				</form>	
		   </div>
	    </div>
	    </div>
	
			</div>
	</div>
</div>	
</body>
<script type="text/javascript">

$('#dos').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,	
	<%if(list!=null&&list[1]!=null){%>
	"startDate" :new Date("<%=list[1]%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#doe').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"minDate" :$('#dos').val(),
	<%if(list!=null&&list[6]!=null){%>
	"startDate" :new Date("<%=list[6]%>"),
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$( "#dos" ).change(function() {
	
	$('#doe').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : $('#dos').val(), 
		"startDate" : new Date(),
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
});

$(function() {
	   $('#stime,#etime,#stime1,#etime1').daterangepicker({
	            timePicker : true,
	            singleDatePicker:true,
	            timePicker24Hour : true,
	            timePickerIncrement : 1,
	            timePickerSeconds : false,
	            locale : {
	                format : 'HH:mm'
	            }
	        }).on('show.daterangepicker', function(ev, picker) {
	            picker.container.find(".calendar-table").hide();
	   });
	})
	
	
	function CheckData(){
		
		var starttime  = $('#stime').val();
		var returntime = $('#etime').val();
		var source = $('#source').val();
		var destination = $('#destination').val();
		
		
		if(starttime=="00:00"){
			alert("Please Select Start Time!");
			return false;
		}else if(returntime=="00:00"){
			alert("Please Select Return Time!");
			return false;
		} else if (source==""||source==null ||source=="null"){
			alert("Please Enter the Source Place!");
			return false;
		}else if (destination==""||destination==null ||destination=="null"){
			alert("Please Enter the Destination Place!");
			return false;
		}else {
			if(confirm("Are you sure to Submit!")){
				$('#requesttripform').submit();
				return true;
			}else{
				return false;
			}
		}
	}
	
</script>
</html>

<%@page import="com.vts.ems.circularorder.model.EMSNotice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body>
<%	
EMSNotice Notice = (EMSNotice)request.getAttribute("EMSNotice");
String FromDate = (String)request.getAttribute("FromDate");
String ToDate = (String)request.getAttribute("ToDate");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(Notice!=null){ %>
				<h5> Notice Edit</h5>
				<%}else{%>
				<h5> Notice Add</h5>
				<%} %>
			</div>
				<div class="col-md-9">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="EMSNotices.htm"> Notice List </a></li>
						<%if(Notice!=null){ %>
                        <li class="breadcrumb-item active " aria-current="page">Notice Edit</li>
                        <%}else{ %>
                         <li class="breadcrumb-item active " aria-current="page">Notice Add</li>
                         <%} %>
					</ol>
			   </div>
		</div>
	</div>
		 
	<div class="page card dashboard-card">
	<div class="card-body" align="center">			
		<div class="card" style="width: 50%">
			<div class="card-body main-card" align="left" >
			
			<%if(Notice!=null){ %>
				<form  action="EMSNoticeEditSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data">
			<%}else{%>
				<form  action="EMSNoticeAddSubmit.htm" method="POST" autocomplete="off" enctype="multipart/form-data">
			<%}%>
			
					
				<div class="row" >
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>Notice Date</b><span class="mandatory"	style="color: red;">*</span></label>
							 <input type="text" class="form-control input-sm "  <%if(Notice!=null && Notice.getNoticeDate()!=null){%>value="<%=DateTimeFormatUtil.SqlToRegularDate(Notice.getNoticeDate())%>" <%}%>   id="NoticeDate" name="NoticeDate"  required="required" readonly="readonly" >
						</div>
					</div>
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>To Date</b><span class="mandatory"	style="color: red;">*</span></label>
							 <input type="text" class="form-control input-sm " <%if(Notice!=null && Notice.getToDate()!=null){%>value="<%=DateTimeFormatUtil.SqlToRegularDate(Notice.getToDate())%>" <%}%>  id="ToDate" name="NoticeToDate"  required="required" readonly="readonly" >
						</div>
					</div>
					
				</div>
				<div class="row" >
					<div class="col-md-6">
						<div class="form-group">						
							<label><b>Notice No.</b></label>
							<input type="text"  class="form-control input-sm " <%if(Notice!=null && Notice.getReferenceNo()!=null){%>value="<%=Notice.getReferenceNo()%>" <%}%>  name="ReferenceNo" maxlength="100" > 
						</div>
					</div>
					
					<div class="col-md-6" >
						<div class="form-group">
							<label><b>File</b> <span class="mandatory"	style="color: red;">*</span></label>
							<div class="row" >
								<div class="col-md-10" >
									<input type="file"  accept="application/pdf" style="width: 100%" class="form-control input-sm "  value="" <%if(Notice==null ){%> required="required" <%} %> id="NoticeFile" name="NoticeFile" >
								</div>
								<div class="col-md-2" >
								<%if(Notice!=null ){%>
									<button type="submit" formnovalidate="formnovalidate" class="btn btn-sm" style="margin-left:-15px; margin-top: 2.5%;" 
									name="NoticeId" value="<%=Notice.getNoticeId()%>"
									 formaction="EMSNoticeDownload.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
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
						  placeholder="Maximum 255 characters" ><%if(Notice!=null && Notice.getDescription()!=null){%> <%=Notice.getDescription()%><%}%></textarea>
						</div>
					</div>
			
						
				</div>
					<div class="row" >
		    			<div class="col-md-12" align="center">
		    			<%if(Notice!=null ){%>
		    				<input type="hidden" name="NoticeId" value="<%if(Notice!=null){%><%=Notice.getNoticeId()%><%}%>">
		    				<button type="submit" class="btn btn-sm submit-btn "  onclick="return confirm('Are You Sure To Submit?');"  >UPDATE</button>
		    			<%}else{ %>
		    				<button type="submit" class="btn btn-sm submit-btn"  onclick="return confirm('Are You Sure To Submit?');"  >SUBMIT</button>
		    				<%} %>
		    			</div>
		    		</div> 
					<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
					<input type="hidden" name="FromDate" value="<%=FromDate %>" />
					<input type="hidden" name="ToDate" value="<%=ToDate %>" />
				</form>
			</div>
	   </div>
	</div>

</div>
		 
</body>
<script type="text/javascript">

$('#NoticeDate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});


$("#NoticeDate").change( function(){
	var Noticedate = $("#NoticeDate").val();
	
	$('#ToDate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : Noticedate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
});		



	var Noticedate = $("#NoticeDate").val();
	
	$('#ToDate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" : Noticedate,
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});	
	
					


$(function(){
    $("#NoticeFile").on('change', function(event) {
        
    	
    	var file = $("#NoticeFile").val();
    	console.log(file);
       	var upld = file.split('.').pop();  
       	if(!(upld.toLowerCase().trim()==='pdf' ))
       	{
    	    alert("Only PDF are allowed to Upload")
    	    document.getElementById("NoticeFile").value = "";
    	    return;
    	}
        

        
    });
});



</script>
</html>
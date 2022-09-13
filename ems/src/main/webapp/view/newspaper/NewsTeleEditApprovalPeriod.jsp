<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
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
Object[] NewspaperApprovalPeriodEditDetails=(Object[])request.getAttribute("NewspaperApprovalPeriodEditDetails");
Object[] TeleApprovalPeriodEditDetails=(Object[])request.getAttribute("TeleApprovalPeriodEditDetails");
%>

<div class="card-header page-top">
		<div class="row">
		<%if(NewspaperApprovalPeriodEditDetails!=null){ %>
			<div class="col-md-5">
				<h5>Newspaper Approval Period Edit</h5>
			</div>
				<div class="col-md-7 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm"> Newspaper </a></li>
						<li class="breadcrumb-item "><a href="NewspaperApprovedList.htm"> Newspaper Approval List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Newspaper Edit</li>
					</ol>
				</div>
			<%}else{ %>
				<div class="col-md-5">
				<h5>Telephone Approval Period Edit</h5>
			</div>
				<div class="col-md-7 ">
					<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm">Telephone</a></li>
					<li class="breadcrumb-item "><a href="TelephoneApprovedList.htm"> Telephone Approval List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Telephone Edit</li>
					</ol>
				</div>
			<%} %>
			</div>
		 </div>
		 
		<div align="center">
			<%String ses=(String)request.getParameter("result"); 
			String ses1=(String)request.getParameter("resultfail");
			if(ses1!=null){ %>
				<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
					<%=ses1 %>
				</div>
				
			<%}if(ses!=null){ %>
				
				<div class="alert alert-success" role="alert" style="margin-top: 5px;">
					<%=ses %>
				</div>
			<%} %>
		</div>
	
	 	<div class="page card dashboard-card">
			<div class="card-body" align="center">
   
                <form action="" method="post"> 
   
                  <div class="card" style="width: 50%; ">
	            
	                       <div class="card-header">
	                         <span class="h4">Select Period</span> 
	                       </div>
	           
	                 <div class="card-body">
	              
	              
	              <!-- From and To Date  -->
	             <div class="form-group">
	              <div class="row">
	              
	               <div class="col-md-6" align="left">
                       <label>From Date:</label>
                      <%if(NewspaperApprovalPeriodEditDetails!=null){%> 
                     <input type="text" name="FromDate" value="<%=DateTimeFormatUtil.SqlToRegularDate(NewspaperApprovalPeriodEditDetails[1].toString())%>" class="form-control input-sm currentdate"  maxlength="10"  required >
                     <%}else if(TeleApprovalPeriodEditDetails!=null){%>
                      <input type="text" name="FromDate" value="<%=DateTimeFormatUtil.SqlToRegularDate(TeleApprovalPeriodEditDetails[1].toString())%>" class="form-control input-sm currentdate"  maxlength="10"  required>
                     <%}else{%>
                       <br><span class="label label-danger">Error Occur While Fetching Data</span>
                     <%}%>
                   </div>
	              
	              
	               <div class="col-md-6"  align="left">
	                  <label>To Date:</label>
                      <%if(NewspaperApprovalPeriodEditDetails!=null){%> 
                     <input type="text" name="ToDate" value="<%=DateTimeFormatUtil.SqlToRegularDate(NewspaperApprovalPeriodEditDetails[2].toString())%>" class="form-control input-sm currentdate"  maxlength="10"  required >
                     <%}else if(TeleApprovalPeriodEditDetails!=null){%>
                      <input type="text" name="ToDate" value="<%=DateTimeFormatUtil.SqlToRegularDate(TeleApprovalPeriodEditDetails[2].toString())%>" class="form-control input-sm currentdate"  maxlength="10"  required>
                     <%}%>
                   </div>
	               
                  </div>
	            </div>
	             <!-- From and To Date  -->
	           
	            <!-- //Panel Body -->
	            </div> 
	           
	            <div class="card-footer" >
	             <%if(NewspaperApprovalPeriodEditDetails!=null){%> 
                   <button class="btn btn-success btn-sm"formaction="NewsApprovalPeriodEditSubmit.htm">Submit</button>
                    <input type="hidden" name="NewspaperBillId" value="<%=NewspaperApprovalPeriodEditDetails[0]%>">
                 <%}else if(TeleApprovalPeriodEditDetails!=null){%>
                  <button class="btn btn-success btn-sm" name="TelephoneApprovalPeriodEditSave" value="TelephoneApprovalPeriodEditSave" formaction="TelephonePeriodEditSave.htm"> Submit</button>
                  <input type="hidden" name="TeleBillId" value="<%=TeleApprovalPeriodEditDetails[0]%>">
                  <%}%>
	           	
	           		 <%if(NewspaperApprovalPeriodEditDetails!=null){%> 
		       		<button type="submit"  class="btn btn-sm  btn-info" formaction="NewspaperApprovedList.htm">Back</button>
			        <%}else if(TeleApprovalPeriodEditDetails!=null){%>
			       		<button type="submit"  class="btn btn-sm  btn-info" formaction="TelephoneApprovedList.htm">Back</button>
			        <%}%>
	            </div>
	            
	            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </div><!-- //panel -->
           </form>

           </div>
		</div>
	           

<script>

$('.currentdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,

	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});




</script>

  
</body>
</html>

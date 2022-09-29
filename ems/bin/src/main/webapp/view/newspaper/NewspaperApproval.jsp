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
	List<Object[]> NewspaperApprovalList=(List<Object[]>)request.getAttribute("NewspaperApprovalList");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
				<h5>Pending Newspaper Claims</h5>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm"> Newspaper </a></li>
						<li class="breadcrumb-item "><a href="NewspaperApproval.htm"> Newspaper List </a></li>
						<li class="breadcrumb-item active " aria-current="page">Newspaper List</li>
					</ol>
				</div>
			</div>
		 </div>
	
	 	<div class="page card dashboard-card">
		<form action="NewspaperApprove.htm" method="post">
			<div class="card-body" >
				
				
				<table  class="table table-bordered  table-striped  table-hover table-condensed ">
			            <thead>
			                <tr>
			                    <th>Name &amp; Designation</th>
			                    <th>Claim Month</th>
			                    <th>Claim Year</th>
			                    <th>Claim Amount</th>
			                    <th>Admissible Amount</th>
			                    <th>Payable Amount</th>
			                    <th>Remark</th>
			                    <th>Action</th>
			                </tr>
			            </thead>
			           
			            <tbody>
			            <%if(NewspaperApprovalList!=null&&NewspaperApprovalList.size()>0){%>
			            <%for(Object[] ls:NewspaperApprovalList){ %>
			            
			             <tr>
			                    <td style="text-align:left;"><%= ls[0]%>, <%=ls[1]%></td>
			                    <td><%=ls[3] %></td>
			                    <td><%=ls[4] %></td>
			                    <td><%=ls[5] %></td>
			                    <td><%=ls[6] %></td>
			                    <td><%=ls[7] %></td>
			                    <td><textarea name="remark<%=ls[2]%>"  rows="1" cols="20"  maxlength="90"></textarea></td>
			                    <td>    
			                            <span class="app">
			                            <label >Approve</label>
			                              <input type="checkbox" name="NewspaperApproveAction" value="<%=ls[2]%>" checked>
			                           </span>
			                           <input type="hidden" name="PayableAmount<%=ls[2]%>"    value="<%=ls[7]%>">
			                    </td>
			           </tr>
			           <%} }%><!-- for closed -->
			           </tbody>
			          </table>
			          
		         <div class="panel-footer" >
		         	<div class="row">
			            <div class="col-sm-2">
			            	<label>From Date</label>
			                <input type="text" class="form-control input-sm currentdate"  name="FromDate" required="required">
			            </div>
		         
			            <div class="col-sm-2">
				        	<label> To Date</label>
				            <input type="text"class="form-control input-sm currentdate"  name="ToDate" required="required">
			            </div>
		         
			            <div class="col-sm-1">
			            	<button  class="btn btn-success btn-sm"  name="ApprovalSubmit" value="ApprovalSubmit"  type="Submit"  style=" margin-top:25px;" >Submit</button>
			            </div>
		          	</div>
		      	</div>
          
      		</div>
      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      	</form>
       </div>
        


<script>

$('.currentdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	"drops" : "up",
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});




</script>



</body>
</html>
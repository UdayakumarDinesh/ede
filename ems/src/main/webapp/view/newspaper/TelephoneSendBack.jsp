<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%List<Object[]> TelephoneApprovalList=(List<Object[]>)request.getAttribute("TelephoneApprovalList");%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
				<h5>Telephone Claims Send Back</h5>
			</div>
				<div class="col-md-7 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TelephoneDashBoard.htm">Telephone</a></li>
						<li class="breadcrumb-item active " aria-current="page">Telephone Approved List</li>
					</ol>
				</div>
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
			
			<div class="card-body" >
    
    
   <form action="TelephoneSendbackSubmit.htm" method="post">
           <table  class="table table-bordered  table-striped  table-hover table-condensed ">
            <thead>
                <tr>
                    <th>Name &amp; Designation</th>
                    <th>Details</th>
                    <th>Claim Date</th>
                    <th>Claim Amount</th>
                    <th>Admissible Amount</th>
                    <th>Payable Amount</th>
                    <th>Admin Remarks</th>
                    <th>Remarks</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <%int count=1;
            if(TelephoneApprovalList!=null&&TelephoneApprovalList.size()>0){%>
            <%for(Object[] ls:TelephoneApprovalList){ %>
            
             <tr>
                    <td style="text-align:left;"><%= ls[0]%><br> <%=ls[1]%></td>
                    <td><a class="btn btn-primary btn-xs btn-outline" href="telephone-approval-individualclaim-details?TeleForwardId=<%=ls[6]%>" target="_blank"><%=count%></a></td>
                    <td><%out.println(DateTimeFormatUtil.SqlToRegularDate(ls[5].toString()));%></td>
                    <td><%=ls[2] %></td>
                    <td><%=ls[3] %></td>
                    <td><%=ls[4] %></td>
                    <td style="word-wrap: break-word;"><%if("B".equalsIgnoreCase(ls[7].toString())){%><%=ls[9] %><%}else{ %>--<%} %></td>
                    <td><textarea name="remark<%=ls[6]%>"  rows="1" cols="20"  maxlength="255"></textarea></td>
                    <td>    
                       <span class="disapp">
                            <label >Send Back</label>
                              <input type="checkbox" name="TeleApproveAction" value="<%=ls[6]%>" >
                       </span>
                   </td>
           </tr>
           <%count++;} }%><!-- for closed -->
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
		                 <label> </label>
		              <button  class="btn btn-success btn-sm"  name="SendbackSubmit" value="SendBackSubmit"  type="Submit"  style=" margin-top:25px;" >Submit</button>
		            </div>
		          </div>
		      </div>
		       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
       </form>
      </div>
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
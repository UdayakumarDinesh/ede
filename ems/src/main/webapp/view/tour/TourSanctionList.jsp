<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.*"%>
  <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Tour Apply List</title>
</head>
<body>
<%
List<Object[]>  sanctionlist = (List<Object[]>)request.getAttribute("SanctionList");
Object[] empdata = (Object[])request.getAttribute("Empdata");

%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Tour Sanctioned List <small><b>&nbsp;&nbsp; &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="TourProgram.htm">Tour</a></li>
						<li class="breadcrumb-item active " aria-current="page">Tour List</li>
					</ol>
				</div>
			</div>
</div>
	<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		if(ses1!=null){ %>
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
			
		<%}if(ses!=null){ %>
			
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		<%} %>
	</div>

<div class="container-fluid">	
<div class="card">
	<div class="card-body">
<form action="TourCancel.htm" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
								  <tr>
								  	  <th>SN</th>
									  <th>Date</th>
								      <th>Applied On</th>
								      <th>Name</th>
								      <th>Purpose</th>
								      <th>Status</th>
								      <th>Action</th>
								  </tr>
					  </thead>
	                  <tbody>
	  
							 <%if(sanctionlist!=null&&sanctionlist.size()!=0){
						                            	 int sn=0;  
                               for(Object[] hlo :sanctionlist){
                            	   String  stayfromdate=DateTimeFormatUtil.fromDatabaseToActual(hlo[1].toString());
                                   String  staytodate= DateTimeFormatUtil.fromDatabaseToActual(hlo[2].toString());
                                   String  applydate=DateTimeFormatUtil.fromDatabaseToActual(hlo[3].toString());
                                   long noofdays = DateTimeFormatUtil.CountNoOfDaysBwdates(hlo[1].toString(), hlo[2].toString());
                            	   %>
	                             <tr>
	                             	  <td align="center"> <%=++sn %></td>
									  <td align="center"><%=stayfromdate%> To <%=staytodate%></td>
                               		  <td align="center"><%=applydate %><br/> for <%=noofdays%> Day(s)</td>
									  <td align="center"><%=hlo[0]+"" %></td>
									  <td><%=hlo[4]+"" %></td>
									  <td align="center" style=" color:<%=hlo[9]%>; font-weight: 600;">&nbsp;<%=hlo[8]%> </td>
									  <td> 
									   <%if(hlo[6].toString().equalsIgnoreCase("ABC")){%>
									  		<button type="button" class="btn btn-sm edit-btn"  onclick="CancelTour('<%=hlo[7]%>')"  data-toggle="tooltip" data-placement="top" title="Cancel"><i class="fa fa-times" aria-hidden="true"></i> </button>
									  	<%}%>	
									   <%if(!hlo[6].toString().equalsIgnoreCase("INI") && !hlo[6].toString().equalsIgnoreCase("REV") && !hlo[6].toString().equalsIgnoreCase("ABC")){%>
									  		<button type="submit" class="btn btn-sm delete-btn" name="Action" value="Revoke/<%=hlo[7]%>"   data-toggle="tooltip" data-placement="top" title="Revoke"><i class="fa fa-undo" aria-hidden="true" ></i> </button>								  	
									  	<%}%>
									  </td>
								</tr> 
                  <%}}%>
	            </tbody>
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
				</form>	
	</div>
	<!---------------------- Model Open --------------------------------->

	<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <form action="TourCancel.htm">
		      <div class="modal-header">
		        <h5 class="modal-title">Reason For Cancel Tour</h5>
                 <button  type="button"  class="close" data-dismiss="modal">&times;</button>
		      </div>
		      <div class="modal-body" align="center">
		        <textarea rows="3" cols="40"  name="reason" required="required" maxlength="490" placeholder="Enter The Reason........!"></textarea>
		      </div>
		      <div class="modal-footer">
		      	<input type="hidden" name="Action" id="action">
		        <button type="submit" class="btn btn-sm submit-btn" >Submit</button>
		      </div>
	      </form>
	    </div>
	  </div>
	</div>
<!---------------------- Model Close --------------------------------->
	
</div>	
</div>
<script type="text/javascript">

function CancelTour(myfrm) {
	console.log("CANCEL/"+myfrm);
	 $("#action").val("CANCEL/"+myfrm);
	 $('#staticBackdrop').modal('toggle');
}
</script>
</body>

</html>
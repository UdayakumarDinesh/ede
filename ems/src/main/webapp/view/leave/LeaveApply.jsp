<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="com.vts.ems.leave.model.LeaveRegister"%>
<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<style type="text/css">
 #dis{
 text-align: center;
 }
 
.btn-group1 button {
  background-color: #344b8a; /* Green background */
  color: white; /* White text */
  padding: 10px 24px; /* Some padding */
  cursor: pointer; /* Pointer/hand icon */
  float: left; /* Float the buttons side by side */
}

/* Clear floats (clearfix hack) */
.btn-group1:after {
  content: "";
  clear: both;
  display: table;
}

.btn-group1 button:not(:last-child) {
  border-right:thin;
}

/* Add a background color on hover */
.btn-group1 button:hover {
  background-color: #3e8e41;
}

</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>LEAVE APPLY</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Apply</li>
					</ol>
				</div>
			</div>
	</div>	
<%String ses=(String)request.getParameter("result"); 
 String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){
	%><center>
	<div class="alert alert-danger" role="alert">
                     <%=ses1 %>
                    </div></center>
	<%}if(ses!=null){ %>
	<center>
	<div class="alert alert-success" role="alert" >
                     <%=ses %>
                   </div></center>
                    <%} %>
 <%
   
    String month=(String)request.getAttribute("month");
    String year=(String)request.getAttribute("year");
    String empNo=(String)request.getAttribute("empNo");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
    <form action="LeaveCredited.htm" method="POST" name="myfrm">
     <div class="row">
			<div class="col-md-3">
			 <div id="clb" class="panel panel-default">
	            <div class="panel-heading">
	                <span class="h4">Current Leave Balance</span>
	            </div>
	            <div class="panel-body p-015">
	                <table class=" table table-bordered table-hover table-striped table-condensed">
	                    <thead>
	                        <tr>
	                            <th>CL</th>
	                            <th>EL</th>
	                            <th>HPL/CML</th>
	                            <th>RH</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                           
	                         <tr>
	                            <td><%=0 %></td>
	                            <td><%=0 %></td>
	                            <td><%=0 %></td>
	                            <td><%=0 %></td>    
	                        </tr>
	                        
	                        
	                        
	                      
	                    </tbody>
	                </table>
	            </div>
	        </div><!-- / current-leave-balance-->
	        
	         <!--Holidays-->
	        <div class="panel panel-default">
	            <div class="panel-heading">
	                <span class="h4">Important Dates</span>
	            </div>
	            <div class="panel-body p-015">
	                <div class="">
	                    <ul class="nav nav-tabs">
	                        <li class="active"><a data-toggle="tab" href="#upcoming">Upcoming</a></li>
	                        <li><a data-toggle="tab" href="#general">Gen</a></li>
	                        <li><a data-toggle="tab" href="#restricted">RH</a></li>
	                          
	                    </ul>
	                   <div class="tab-content">
	                   <!-- upcoming holidays -->
	                    <div id="upcoming" class="tab-pane fade in active  pre-scrollable">
	                        <table class="table table-bordered table-hover table-striped table-condensed tabText">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody>
	                       
	                        </tbody>
	                        </table>
	                    </div>
	                   
	                   <!-- general holidays -->
	                    <div id="general" class="tab-pane fade pre-scrollable">
	                     <table class="table table-bordered table-hover table-striped table-condensed ">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                            <tbody>
	                        
	                        </tbody>
	                        </table>
	                     </div>
	                    
	                    <!-- restricted holidays -->
	                    <div id="restricted" class="tab-pane fade pre-scrollable">
	                    <table class="table table-bordered table-hover table-striped table-condensed tabText">
	                            <thead>
	                            <tr>
	                                  <th>Date</th>
	                                  <th style=" text-align: left;">Event</th> 
	                                    
	                            </tr>
	                            </thead>
	                         <tbody>
	                      
	                        </tbody>
	                        </table>
	                    </div>
	                    
	                    
	                    
	                 </div>    <!-- <div id="working" class="tab-pane fade in active"></div> -->
	                </div>    
	            </div>
	        </div>
	        <!--Holidays-->
			</div>
			<div class="col-md-6">
			
			</div>
			<div class="col-md-3">
			
			</div>
			
	</div>		




	   </form>
	   </div>
	   </div>






<script type="text/javascript">
function Edit(myfrm){
	
	 var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	}
	 
	var cnf=confirm("Are U Sure To Edit!");
	  if(cnf){
	
		 
	
		  return true;
	  }
	  
	  
	  else{
		  event.preventDefault();
			return false;
			}
			
	}
function Delete(myfrm){
	

	var fields = $("input[name='Aid']").serializeArray();

	  if (fields.length === 0){
	alert("PLESE SELECT ONE RECORD");
	 event.preventDefault();
	return false;
	
	}
	  var cnf=confirm("Are U Sure To Cancel!");
	  if(cnf){
	
	return true;
	
	}
	  else{
		  event.preventDefault();
			return false;
			}
	
	}


</script>
</body>
</html>
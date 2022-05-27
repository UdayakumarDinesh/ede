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
.dataTables_info, .dataTables_length{
  text-align: left !important;
}
</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Credit Preview</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item " ><a href="LeaveCredit.htm">Credit List</a></li>
						<li class="breadcrumb-item active " aria-current="page">Credit Preview</li>
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
    List<Object[]> clist=(List<Object[]>)request.getAttribute("CreditPreview");
    String month=(String)request.getAttribute("month");
    String year=(String)request.getAttribute("year");
    String empNo=(String)request.getAttribute("empNo");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
   <form action="LeaveCredited.htm" method="POST" name="myfrm">
    <div class="row">
    <div class="col-md-4">
    
    </div>
    <div class="col-md-2" align="right" style="margin-top: 4px;font-weight: bold;">
       Leave Credit For  :
    </div>
    <div class="col-md-1" align="left" style="margin-top: 4px;font-weight: bold;">
        <%=month %>  <%=year %>
    </div>
    <div class="col-md-1" align="left">
    <%if(clist!=null&&clist.size()>0){%>
    <input type="submit" value="Credit" onclick="return confirm('Are You Sure To Credit Leave For <%=month %> <%=year %> ?')" class="btn btn-success btn-sm" style="margin-top: 3px;">
    <%} %>
    </div>
    </div>
    
    <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
    <input type="hidden" type="text" value="<%=empNo %>"  name="empNo">
   <input type="hidden" type="text" value="<%=month %>"  name="month">
    <input type="hidden" type="text" value="<%=year %>"  name="Year">
    </form>
   
	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-12">
	    <form action="PisHolidayForm.htm" method="POST" name="myfrm">
	 <div class="table-responsive">
		<table class="table table-bordered table-hover table-striped table-condensed" > 
	  <thead>
	  <tr>
	  <th>SN</th>
	  <th>Employee</th>
	  <th>CL</th>
	  <th>EL</th>
	  <th>HPL</th>
	  <th>CML</th>
	  <th>RH</th>
	  
	  </tr>
	  </thead>
	  <tbody>
	  <%int count=1;
	  if(clist!=null&&clist.size()>0){
	  for(Object[] hlo :clist){ %>
	  <tr>
	  <td style="width: 100px;text-align: center;"><%=count %></td> 
	  <td style="width: 30%;text-align: center;"><%=hlo[1] %>,<%=hlo[2] %></td>
	  <td style="text-align: center;width: 100px;"><%if(hlo[9]!=null&&"N".equalsIgnoreCase(hlo[9].toString())){%><%=hlo[3] %><%}if(hlo[9]!=null&&"Y".equalsIgnoreCase(hlo[9].toString())){ %><%=hlo[8] %><%} %></td>
	  <td style="text-align: center;width: 100px;"><%=hlo[4] %></td>
	   <td style="text-align: center;width: 100px;"><%=hlo[5] %></td>
	    <td style="text-align: center;width: 100px;"><%=hlo[6] %></td>
	     <td style="text-align: center;width: 100px;"><%=hlo[7] %></td>

	  
	  
	  
	  </tr>
	  <%count++;}}else{ %>
	  
	  <tr>
	  <td colspan="7" style="text-align: center;font-weight: bold; font-size: medium;">  Already Leave Credited For  <%=month %> <%=year %> . </td>
	  </tr>
	  <%} %>
	  </tbody>
	   </table>
	   
	   </div>
	  

	   </form>
	   </div>
	   </div>

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
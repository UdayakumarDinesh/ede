<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Nominal-Roll-List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.custom-navbar{
	border-top-left-radius: 7px;
	border-top-right-radius: 7px;
	background-color: white !important;
}
</style>
</head>
<body>
<%
List<Object[]>  PersonalDetailsAll =(List<Object[]>)request.getAttribute("personaldetailsall");
String empname = (String)request.getAttribute("empname");
String catType =  (String)request.getAttribute("catType");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Nominal Roll Report<small> <%if(empname!=null){%> <%=empname%> <%}%> </small> </h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item"><a	href="PIS.htm"> PIS</a></li>
						<li class="breadcrumb-item active " aria-current="page">Nominal Roll Report</li>	
					</ol>
				</div>
		</div>
</div>
<div class="container-fluid">	
<form   action="NominalRollReport.htm" method="post" id="form1">
<div class="nav navbar bg-light dashboard-margin custom-navbar" >
   						<div class="col-md-2"></div>
						<!-- Select Cat Name -->
		  		         <div class="form-group"  >
		  		         <label ><b>Select Category  :</b> &nbsp; &nbsp; &nbsp;</label>
		  		        	<select class="form-control input-sm select2" name="catType"  data-live-search="true" onchange="this.form.submit()">
			 					  <option value="A" <%if("A".equalsIgnoreCase(catType)){%> selected="selected" <%}%>> All </option>
             				      <option value="N" <%if("N".equalsIgnoreCase(catType)){%> selected="selected" <%}%>> NGO </option>
             				      <option value="C" <%if("C".equalsIgnoreCase(catType)){%> selected="selected" <%}%>> CGO </option>
             				</select>
						  </div>
      				   <div class="col-md-2"></div>
      				   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      				   </div>
      				</form>
</div>
          
<div class="card" style="margin-top: 10px;">
				<div class="card-body main-card"  >
						     <div class="card-body">
  <div class="form-group"> 
  <div class="table-responsive">
		 <table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
		  	          <thead>
                                   <tr>            
                                   <th>Name</th>
                                   <th>Designation</th>
                                   <th>Group Name</th>
                                   <th>PunchCard Number</th>
                                   </tr>
                             </thead>
                          <tbody>
                        <%for(Object[] ls:PersonalDetailsAll){%>
                              
	                          <tr> 
	                             <td style='text-align:left;'><%=ls[1] %></td>
                                 <td> <%=ls[2]%></td>
                                 <td> <%=ls[3]%></td>
                                 <td> <%=ls[4]%></td>
                            </tr>
                     <%}%>
                          </tbody> 
    			</table>
    </div>
</div>
</div>
</div>
</div>


</body>
</html>
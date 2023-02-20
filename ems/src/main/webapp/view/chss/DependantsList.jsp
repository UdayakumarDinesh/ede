<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*"    
    import="java.text.SimpleDateFormat"
     %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dependants List</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
<%
List<Object[]> emplist=(List<Object[]>)request.getAttribute("Emplist"); 
List<Object[]> dependantList=(List<Object[]>)request.getAttribute("dependantList");
String empNo= (String)request.getAttribute("EmpNo");
SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
%>
<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Dependant List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm"> CHSS </a></li>
						<li class="breadcrumb-item active " aria-current="page">Dependant List</li>
					</ol>
				</div>
			</div>
		 </div>
	<div class="page card dashboard-card">	

			<div class="card" >
			
				<div class="card-header" style="height: 4rem">
					<form action="DependantsList.htm" method="POST" id="myform">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<table style="margin-left: 35%">
					
					<tr>
					     <th >Employee&nbsp;&nbsp;:</th>
					     <td>
					     <select class="form control select2" onchange="this.form.submit();" name="EmpNo" style="width:280px;" id="EmpName">
					     <option value="A">All</option>
					     <%if(emplist!=null){
					    	for( Object[] obj:emplist) {
					    	 
					    	 %>
					     <option value="<%=obj[0]%>"<%if(empNo.equalsIgnoreCase(obj[0].toString())){ %>selected="selected"<%} %> ><%=obj[1] %> </option>
					     <%}} %>
					     </select>
					    
					     </td>
					     
					   </tr>
					</table>
					</form>
					</div>
	 <div class="card-body main-card">
		
					<form action="DependantsList.htm" method="POST">
	     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>	               
	          <div class="table-responsive" >
	                <table  class="table table-bordered table-hover table-striped table-condensed" id="myTable" style=""  >
			           <thead>
			                  
			                  <tr>
			                       <th>SN</th>
			                       <th>Emp No</th>
			                        <th>Emp Name</th> 
			                       <th>Dependant Name</th>
			                       <th>Relation</th>
			                       <th>DOB</th>
			                       <th>Medical Dependant From</th>		                       
			                       
			                  </tr>	
			                  	           			           
			           </thead>
			           <tbody>
			           <%if(dependantList!=null){
			        	   int sn=0;
			        	  for(Object[] dep:dependantList) {
			        	   %>
			           
			           <tr>
			           <td style="text-align: Center;"><%=++sn %></td>
			           <td style="text-align: Center;"><%=dep[0] %></td>
			           <td><%=dep[1]  %></td>
			           <td><%=dep[2]  %></td>
			           <td><%=dep[5]  %></td>
			           <td style="text-align: Center;"><%=sdf.format(dep[3])%></td>
			           <td style="text-align: Center;"><%=sdf.format(dep[4]) %></td>
			           </tr>
			           <%}} %>
			           </tbody>
			           </table>
			           </div>
			           </form>
			           </div>
			           
			           </div>
				</div>
			
		
</body>
</html>
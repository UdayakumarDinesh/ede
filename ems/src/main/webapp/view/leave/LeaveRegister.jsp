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
.modal-dialog {
    max-width:750px;
    margin: 2rem auto;
}
.card-body{
padding: 0.5rem !important;
}
span{
font-weight: bold;
}
.table td, .table th {
font-size: 13px; !important;
}
.table th {
text-align: center;
}

</style>
</head>
<body>


<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Leave Register</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Leave Register</li>
					</ol>
				</div>
			</div>
	</div>	
<%    List<Employee> emplist=(List<Employee>)request.getAttribute("EmpList");
String empno=(String)request.getAttribute("empNo");
String year=(String)request.getAttribute("year");
String ses=(String)request.getParameter("result"); 
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

<div class="page card dashboard-card">

			 
   <div class="card-body" align="center" >
   <form action="LeaveRegister.htm" method="POST" name="myfrm">
    <div class="row">
    <div class="col-md-7">
    
    </div>
    
    <div class="col-md-3">
    <div class="group-form">
    <select class="form-control  selectpicker" required="required" name="empNo" title="Select Employee" data-live-search="ture" id="empNo">
    <%for(Employee emp:emplist){ %>
    <option value="<%=emp.getEmpNo() %>" <%if(emp.getEmpNo().equals(empno)){ %> selected="selected" <%} %>><%=emp.getEmpName() %></option>
    <%} %>
    </select>
    
    </div>
    </div>
    <div class="col-md-1">
    <div class="group-form">
 	<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
    <input class="form-control  form-control" type="text" id="year"  value="<%=year %>" name="Year">
    </div>
    </div>
    <div class="col-md-1">
    <input type="submit" value="Submit" class="btn btn-success btn-sm" style="margin-top: 3px;">
    </div>
    </div>
    
    
    </form>
    <%LeaveRegister register=(LeaveRegister)request.getAttribute("register");
    List<LeaveRegister> RegisterList=(List<LeaveRegister>)request.getAttribute("RegisterList");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-4">
	    <div class="card card-default border-success">
							<div class="card-header">
								<span>Casual Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="cl">
									<thead>
										<tr>
											<th >From &nbsp; To</th>
											<th >Appl Dt<br/>Sanc Dt</th>
											<th >Status </th>
											<th >Debit <br/> Credit</th>
											<th >Balance</th>
										</tr>
									</thead>
									<tbody>
									
									      <tr>
											<td style="text-align: center;"> - &nbsp; -</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LOB</td>
											<td style="text-align: right;"><%=register.getCL() %>  </td>
											<td style="text-align: right;"><%=register.getCL() %></td>
										 </tr>
									      <% 
									      double countCL=register.getCL();
									      
									      for(LeaveRegister cl:RegisterList){ 
									      if(cl.getCL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=cl.getFROM_DATE() %> <br> <%=cl.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=cl.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(cl.getSTATUS().equals("LKU")){
										    	countCL+=cl.getCL();	
										    	%>
										    <%=cl.getCL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!cl.getSTATUS().equals("LKU")){
										    	countCL-=cl.getCL();	
										    	%>
										    <%=cl.getCL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countCL %></td>
										  </tr>

										 <%} }%>
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countCL %> </td>
											<td style="text-align: right;"><%=countCL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						
						
						<div class="col-md-4">
	    <div class="card card-default border-success">
							<div class="card-header">
								<span >Earned Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="el">
									<thead>
										<tr>
											<th >From &nbsp; To</th>
											<th >Appl Dt<br/>Sanc Dt</th>
											<th >Status </th>
											<th >Debit <br/> Credit</th>
											<th >Balance</th>
										</tr>
									</thead>
									<tbody>
									      <tr>
											<td style="text-align: center;"> - &nbsp; -</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LOB</td>
											<td style="text-align: right;"><%=register.getEL() %> </td>
											<td style="text-align: right;"><%=register.getEL() %></td>
										 </tr>
									      <% 
									      int countEL=register.getEL();
									      
									      for(LeaveRegister el:RegisterList){ 
									      if(el.getEL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=el.getFROM_DATE() %> <br> <%=el.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=el.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(el.getSTATUS().equals("LKU")){
										    	countEL+=el.getEL();	
										    	%>
										    <%=el.getEL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!el.getSTATUS().equals("LKU")){
										    	countEL-=el.getEL();	
										    	%>
										    <%=el.getEL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countEL %></td>
										  </tr>

										 <%} }%>
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countEL %> </td>
											<td style="text-align: right;"><%=countEL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						
						<div class="col-md-4">
	    <div class="card card-default border-success">
							<div class="card-header">
								<span >CML/HPL Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="hpl">
									<thead>
										<tr>
											<th >From &nbsp; To</th>
											<th >Appl Dt<br/>Sanc Dt</th>
											<th >Status </th>
											<th >Debit <br/> Credit</th>
											<th >Balance</th>
										</tr>
									</thead>
									<tbody>
									      <tr>
											<td style="text-align: center;"> - &nbsp; -</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LOB</td>
											<td style="text-align: right;"><%=register.getHPL() %>  </td>
											<td style="text-align: right;"><%=register.getHPL() %></td>
										 </tr>
									      <% 
									      int countHPL=register.getHPL();
									      
									      for(LeaveRegister hpl:RegisterList){ 
									      if(hpl.getHPL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=hpl.getFROM_DATE() %> <br> <%=hpl.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=hpl.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(hpl.getSTATUS().equals("LKU")){
										    	countHPL+=hpl.getHPL();	
										    	%>
										    <%=hpl.getHPL()/2 %> / <%=hpl.getHPL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!hpl.getSTATUS().equals("LKU")){
										    	countHPL-=hpl.getHPL();	
										    	%>
										    - / <%=hpl.getHPL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countHPL %></td>
										  </tr>

										 <%}else 
									      if(hpl.getCML()>0){
									    	  
										      %>
	                                          <tr>
												<td style="text-align: center;"><%=hpl.getFROM_DATE() %> <br> <%=hpl.getTO_DATE() %></td>
												<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
												<td style="text-align: center;"><%=hpl.getSTATUS() %></td>
												<td style="text-align: right;">
											    -
												<br/> 
												<%if(!hpl.getSTATUS().equals("LKU")){
											    	countHPL-=hpl.getCML()*2;	
											    	%>
											     <%=hpl.getCML()%> / <%=hpl.getCML()*2 %>
											    <%}else{ %>
											    -
											    <%} %> 
												</td>
												<td style="text-align: right;"><%=countHPL %></td>
											  </tr>

											 <%} }%>
									      
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countHPL %> </td>
											<td style="text-align: right;"><%=countHPL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
       </div>
       

	   </div>
	   </div>
	

<script type="text/javascript">

$('#year').datepicker({
	 format: "yyyy",
	    viewMode: "years", 
	    minViewMode: "years",
	    autoclose: true,
	    todayHighlight: true
});
var tbody = $('#hpl  tbody');
tbody.html($('tr',tbody).get().reverse());
var el = $('#el  tbody');
el.html($('tr',el).get().reverse());
var cl = $('#cl  tbody');
cl.html($('tr',cl).get().reverse());
</script>
</body>
</html>
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
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">

.dashboard-card{
	overflow-x:hidden !important ; 
}

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
.nav-link span{
 	font-weight: 100 !important;
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
<%  Object[] empDetails=(Object[])request.getAttribute("empDetails");  
List<Employee> emplist=(List<Employee>)request.getAttribute("EmpList");
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
    <div class="col-md-1"  align="right">
    <span style="margin-top:4px; ">Page : </span>
    </div>
    <div class="col-md-6" align="left">
    <button type="button" id="btn1" class="btn btn-primary btn-sm active" onclick="getPage('btn1')" style="margin-top: 3px;">1</button>
    <button type="button" id="btn2" class="btn btn-primary btn-sm" onclick="getPage('btn2')" style="margin-top: 3px;">2</button>
    <button type="button" id="btn3" class="btn btn-primary btn-sm" onclick="getPage('btn3')" style="margin-top: 3px;">3</button>
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
    <input type="submit" value="Submit" class="btn submit-btn btn-sm" style="margin-top: 3px;">
    </div>
    </div>
    
    
    </form>
    <%LeaveRegister register=(LeaveRegister)request.getAttribute("register");
    List<LeaveRegister> RegisterList=(List<LeaveRegister>)request.getAttribute("RegisterList");
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
	   %>
	    <div class="row" style="margin-top:7px;border-color: #28a745 !important;" id="page1"> 
	    <div class="col-md-4">
	    <div class="card card-default ">
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
	    <div class="card card-default">
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
	    <div class="card card-default ">
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
        
          <div class="row" style="margin-top:7px;border-color: #28a745 !important;" id="page2"> 
	    <div class="col-md-4">
	    <div class="card card-default ">
							<div class="card-header">
								<span>RH Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="rh">
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
											<td style="text-align: right;"><%=register.getRH() %>  </td>
											<td style="text-align: right;"><%=register.getRH() %></td>
										 </tr>
									      <% 
									      double countRH=register.getRH();
									      
									      for(LeaveRegister rh:RegisterList){ 
									      if(rh.getRH()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=rh.getFROM_DATE() %> <br> <%=rh.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=rh.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(rh.getSTATUS().equals("LKU")){
										    	countRH+=rh.getRH();	
										    	%>
										    <%=rh.getRH() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!rh.getSTATUS().equals("LKU")){
										    	countRH-=rh.getRH();	
										    	%>
										    <%=rh.getRH() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countRH %></td>
										  </tr>

										 <%} }%>
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countRH %> </td>
											<td style="text-align: right;"><%=countRH %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						
						
						<div class="col-md-4">
	    <div class="card card-default">
							<div class="card-header">
								<span >SL Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="sl">
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
											<td style="text-align: right;"><%=register.getSL() %> </td>
											<td style="text-align: right;"><%=register.getSL() %></td>
										 </tr>
									      <% 
									      int countSL=register.getSL();
									      
									      for(LeaveRegister sl:RegisterList){ 
									      if(sl.getSL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=sl.getFROM_DATE() %> <br> <%=sl.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=sl.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(sl.getSTATUS().equals("LKU")){
										    	countSL+=sl.getSL();	
										    	%>
										    <%=sl.getSL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!sl.getSTATUS().equals("LKU")){
										    	countSL-=sl.getSL();	
										    	%>
										    <%=sl.getSL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countSL %></td>
										  </tr>

										 <%} }%>
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countSL %> </td>
											<td style="text-align: right;"><%=countSL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						
						<div class="col-md-4">
	    <div class="card card-default ">
							<div class="card-header">
								<span >EOL Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="eol">
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
											<td style="text-align: right;"><%=register.getEOL() %>  </td>
											<td style="text-align: right;"><%=register.getEOL() %></td>
										 </tr>
									      <% 
									      int countEOL=register.getEOL();
									      
									      for(LeaveRegister eol:RegisterList){ 
									      if(eol.getEOL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=eol.getFROM_DATE() %> <br> <%=eol.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=eol.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(eol.getSTATUS().equals("LKU")){
										    	countEOL+=eol.getEOL();	
										    	%>
										     <%=eol.getEOL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!eol.getSTATUS().equals("LKU")){
										    	countEOL-=eol.getEOL();	
										    	%>
										    - / <%=eol.getEOL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countEOL %></td>
										  </tr>

											 <%} }%>
									      
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countEOL %> </td>
											<td style="text-align: right;"><%=countEOL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
       </div>
    <div class="row" style="margin-top:7px;border-color: #28a745 !important;" id="page3"> 
	    <%if("M".equals(empDetails[5].toString())){ %>
	    <div class="col-md-4">
	    <div class="card card-default ">
							<div class="card-header">
								<span>PL Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="pl">
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
											<td style="text-align: right;"><%=register.getPL() %>  </td>
											<td style="text-align: right;"><%=register.getPL() %></td>
										 </tr>
									      <% 
									      double countPL=register.getPL();
									      
									      for(LeaveRegister pl:RegisterList){ 
									      if(pl.getPL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=pl.getFROM_DATE() %> <br> <%=pl.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=pl.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(pl.getSTATUS().equals("LKU")){
										    	countPL+=pl.getPL();	
										    	%>
										    <%=pl.getPL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!pl.getSTATUS().equals("LKU")){
										    	countPL-=pl.getPL();	
										    	%>
										    <%=pl.getPL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countPL %></td>
										  </tr>

										 <%} }%>
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countPL %> </td>
											<td style="text-align: right;"><%=countPL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						<%}else{ %>
						
						
						<div class="col-md-4">
	                     <div class="card card-default">
							<div class="card-header">
								<span >ML Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="ml">
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
											<td style="text-align: right;"><%=register.getML() %> </td>
											<td style="text-align: right;"><%=register.getML() %></td>
										 </tr>
									      <% 
									      int countML=register.getML();
									      
									      for(LeaveRegister ml:RegisterList){ 
									      if(ml.getML()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=ml.getFROM_DATE() %> <br> <%=ml.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=ml.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(ml.getSTATUS().equals("LKU")){
										    	countML+=ml.getML();	
										    	%>
										    <%=ml.getML() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!ml.getSTATUS().equals("LKU")){
										    	countML-=ml.getML();	
										    	%>
										    <%=ml.getML() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countML %></td>
										  </tr>

										 <%} }%>
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countML %> </td>
											<td style="text-align: right;"><%=countML %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						
						<div class="col-md-4">
	                        <div class="card card-default ">
		 					<div class="card-header">
								<span >CCL Leave</span>
							</div>
							
							<div class="card-body">
								
								<table class="table table-hover  table-striped table-condensed table-bordered" id="ccl">
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
											<td style="text-align: right;"><%=register.getCCL() %>  </td>
											<td style="text-align: right;"><%=register.getCCL() %></td>
										 </tr>
									      <% 
									      int countCCL=register.getCCL();
									      
									      for(LeaveRegister ccl:RegisterList){ 
									      if(ccl.getCCL()>0){
									    	  
									      %>
                                          <tr>
											<td style="text-align: center;"><%=ccl.getFROM_DATE() %> <br> <%=ccl.getTO_DATE() %></td>
											<td style="text-align: center;">Appl Dt<br/>Sanc Dt</td>
											<td style="text-align: center;"><%=ccl.getSTATUS() %></td>
											<td style="text-align: right;">
										    <%if(ccl.getSTATUS().equals("LKU")){
										    	countCCL+=ccl.getCCL();	
										    	%>
										     <%=ccl.getCCL() %>
										    <%}else{ %>
										    -
										    <%} %>
											<br/> 
											<%if(!ccl.getSTATUS().equals("LKU")){
										    	countCCL-=ccl.getCCL();	
										    	%>
										    <%=ccl.getCCL() %>
										    <%}else{ %>
										    -
										    <%} %> 
											</td>
											<td style="text-align: right;"><%=countCCL %></td>
										  </tr>

											 <%} }%>
									      
										 <tr>
											<td style="text-align: center;">- &nbsp;-</td>
											<td style="text-align: center;">- -</td>
											<td style="text-align: center;">LCB </td>
											<td style="text-align: right;"><%=countCCL %> </td>
											<td style="text-align: right;"><%=countCCL %></td>
										 </tr>
										</tbody>
									</table>
								
							</div><!-- panel-body -->
							</div>
						</div>
						
						<%} %>
       </div>
	   </div>
	   </div>
	

<script type="text/javascript">
$('#page1').show();
$('#page2').hide();
$('#page3').hide();
function getPage(id){
	$("#btn1").removeClass("active");
	$("#btn2").removeClass("active");
	$("#btn3").removeClass("active");
	$('#'+id).addClass('active');
	if('btn1'==id){
		$('#page1').show();
		$('#page2').hide();
		$('#page3').hide();
	}else if('btn2'==id){
		$('#page1').hide();
		$('#page2').show();
		$('#page3').hide();
		
	}else if('btn3'==id){
		$('#page1').hide();
		$('#page3').show();
		$('#page2').hide();
		
	}
	
}


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
var rh = $('#rh  tbody');
rh.html($('tr',rh).get().reverse());
var sl = $('#sl  tbody');
sl.html($('tr',sl).get().reverse());
var eol = $('#eol  tbody');
eol.html($('tr',eol).get().reverse());
</script>
</body>
</html>
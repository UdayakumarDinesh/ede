<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.noc.model.NocHigherEducation"%> 
<%-- <%@page import="com.vts.ems.itinventory.model.ITInventory"%>  --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include> 

</head>

<style>
body {
  overflow-x: hidden;
  overflow-y: hidden;
}


</style>
<body>

  <%
       Object[] NocEmpList= (Object[])request.getAttribute("NocEmpList");
       Object[] EmpPassport= (Object[])request.getAttribute("EmpPassport");
       SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
   	   SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
   	   String Empid=(String)request.getAttribute("EmpId");
   	   Object[] empData=(Object[])request.getAttribute("EmpData");
   	   
   	  NocHigherEducation noc=(NocHigherEducation)request.getAttribute("HigherEducation");
   	  
   	 /*  System.out.println("year---"+noc.getAcademicYear()); */
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			    <h5>Higher Education Add  <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="HigherEducation.htm">Higher Education</a></li>
					
					<% if(noc!=null){ %>
					
					<li class="breadcrumb-item active " aria-current="page"> Higher Education Edit</li>
					<%} else{ %>
				
					<li class="breadcrumb-item active " aria-current="page"> Higher Education Add</li>
					
					<%} %>
				</ol>
			</div>	
		</div>
	</div>
	
	
		
 <%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null){%>
	 <div align="center">
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
		</div>
		<%}if(ses!=null){ %>
		<div align="center">
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		</div>
		<%} %>
	
	
	<div class=" page card dashboard-card">
   <!--  <div class="card-body" > -->
		<div class="card" >
			<div class="card-body" >
			<% if(noc!=null){ %>
			
			<form action="NOCHigherEducationEditSubmit.htm" method="post" autocomplete="off" id="form" >
			
			<%} else{ %>
				<form action="HigherEducationAddSubmit.htm" method="post" autocomplete="off" id="form" >
				<%} %>
				<!-- <form action="" method="post" autocomplete="off" > -->
				
				
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			         <div class="col-md-2">
			                <label>Institution Type</label><br>
			                 <select class=" form-control select2" name="InstitutionType" required="required">
			                  <option value="" selected="selected" disabled="disabled">Select</option>
								
								<option value="University" <% if(noc!=null ){ if(noc.getInstitutionType().toString().equalsIgnoreCase("University")){%> selected <%}} %> >University</option>
								<option value="College" <% if(noc!=null){ if(noc.getInstitutionType().toString().equalsIgnoreCase("College")) { %> selected <%}} %> >College</option>
								
							</select>
			            </div>
			
			           <div class="col-md-3">
			                <label>Institution Name</label> 
			                 <input type="text" name="InstitutionName" value="<% if(noc!=null && noc.getInstitutionName()!=null){ %><%=noc.getInstitutionName() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Institution Name"  required="required" >
			            	
			            </div>	
			            
			            
			           <div class="col-md-2">
			                <label>Academic Year</label> 
			                <input  class="form-control date"   id="datepicker1" name="AcademicYear"  <%-- value="<% if(noc!=null && noc.getAcademicYear()!=null) {%><%=noc.getAcademicYear().toString().substring(0,4) %><%} %>" --%>  placeholder="Enter Academic Year"  required="required" >
			            	
			            </div>			
			            	
			            <div class="col-md-2">
			                <label>Name of Course </label>
			                <input type="text" id="" name="CourseName" value="<% if(noc!=null && noc.getCourse()!=null){ %><%=noc.getCourse() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Course Name"  required="required" >
			                    
			            </div>
			            <div class="col-md-3">
			                <label>Specialization </label>
			                <input type="text"  name="Specialization"   value="<% if(noc!=null && noc.getSpecialization()!=null){ %><%=noc.getSpecialization() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Specialization" required="required"
			                     >
			            </div>
			            
			            
			             
				  </div>
			    </div>
			
			     <div class="form-group">
			        <div class="row">
			        
			        <div class="col-md-2">
			                <label>Education Type</label><br>
			                 <select class=" form-control select2" name="EducationType" required="required">
			                  <option value="" selected="selected" disabled="disabled">Select</option>
								
								<option value="Regular"   <% if(noc!=null ){ if(noc.getEducationType().toString().equalsIgnoreCase("Regular")){%> selected <%}} %> >Regular</option>
								<option value="Distance Education" <% if(noc!=null ){ if(noc.getEducationType().toString().equalsIgnoreCase("Distance Education")){%> selected <%}} %>  >Distance Education</option>
								<option value="Correspondence" <% if(noc!=null ){ if(noc.getEducationType().toString().equalsIgnoreCase("Correspondence")){%> selected <%}} %>  >Correspondence</option>
								<option value="ERP"  <% if(noc!=null ){ if(noc.getEducationType().toString().equalsIgnoreCase("ERP")){%> selected <%}} %> >ERP</option>
								
							</select>
			            </div>
			            
			          <div class="col-md-4">
			                <label>Essential Educational Qualification  </label>
			                <input type="text" id="" name="QualificationRequired"   value="<% if(noc!=null && noc.getQualifiactionRequired()!=null){ %><%=noc.getQualifiactionRequired() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Essential Educational Qualification"  required="required"  >
			                   
			            </div>
			            
			             
			       </div>
			     </div>
			   
			  
			    
			<div class="col-12" align="center">
			         <%if(noc!=null){%>
			    	<button type="submit" class="btn btn-sm update-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Update')">Update</button>
			    	<input type="hidden" name="EducationId" value="<%=noc.getNocEducationId() %>">
			    	
			    	<%}else{ %> -
			    	<button type="submit" class="btn btn-sm submit-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Submit')">SUBMIT</button>
			    	
			    	<%} %>
			    	</div>
			    
			    
			    
			 </form>
			</div>
		</div>
		</div>
	<!-- </div> -->
	
	

<script type="text/javascript">

$("#table").DataTable({
    "lengthMenu": [5, 10, 25, 50, 75, 100],
    "pagingType": "simple"

});


$(document).ready(function(){
	 
	$("#datepicker1").datepicker({
    	
    	autoclose: true,
    	 format: 'yyyy',
    		 viewMode: "years", 
    		    minViewMode: "years"
    });
	 <%if(noc!=null && noc.getAcademicYear()!=null){%> 
	  document.getElementById("datepicker1").value =<%=noc.getAcademicYear()%> 
	  <%}else{%>
	  document.getElementById("datepicker1").value =new Date().getFullYear()
	  <%}%>
	
<%--  <% if(DeclarationYear!=null){%>
  document.getElementById("datepicker1").value = <%=DeclarationYear %>
  <%} %> --%>
    
   /*  $('#datepicker1').change(function () {
    	
       $('#form').submit();
        
    });  */
  
});
  
</script>

</body>
</html>
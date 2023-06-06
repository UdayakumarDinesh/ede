<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.noc.model.ExamIntimation"%> 
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
   	   
     	ExamIntimation exam=(ExamIntimation)request.getAttribute("ExamIntimation");
   	
   	
   	
   	  
   	 /*  System.out.println("year---"+noc.getAcademicYear()); */
  %>

<div class="card-header page-top">
		<div class="row">
			<div class="col-md-5">
			    <h5>Intimation For Exam Add  <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empData!=null){%><%=empData[1]%> (<%=empData[2]%>)<%}%>
						</b></small></h5>
			</div>
			<div class="col-md-7 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
					<li class="breadcrumb-item "><a href="IntimateExam.htm">Intimation For Exam</a></li>
					
					<% if(exam!=null){ %>
					
					<li class="breadcrumb-item active " aria-current="page"> Intimation For Exam Edit </li>
					<%} else{ %>
				
					<li class="breadcrumb-item active " aria-current="page"> Intimation For Exam Add</li>
					
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
		<div class="card"  style="width:75%;margin-left:15%;">
			<div class="card-body">
			<% if(exam!=null){ %>
			
			<form action="IntimationExamEditSubmit.htm" method="post" autocomplete="off" id="form" >
			
			<%} else{ %>
				<form action="IntimationExamAddSubmit.htm" method="post" autocomplete="off" id="form" >
				<%} %>
			
				
				
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <div class="form-group">
			        <div class="row">
			        
			            <div class="col-md-2">
			                <label>Name of Exam</label>
			                   <select class="form-control select2" name="ExamName" required="required">
			                  <option value="" selected="selected" disabled="disabled">Select</option>
								
								<option value="UPSC" <% if(exam!=null && exam.getExamName().toString().equalsIgnoreCase("UPSC")){ %>selected<%} %> >UPSC</option>
								<option value="SCC"  <% if(exam!=null && exam.getExamName().toString().equalsIgnoreCase("SCC")){ %>selected<%} %> >SCC</option>
								
							</select>
			                   
			            </div>
			            
			                 
			         <div class="col-md-2">
			                <label>Probable Date</label> 
			                <input  class="form-control date"  id="probabledate" name="ProbableDate" value=""   placeholder=""  required="required"  > 
			            	
			            </div>	
			        
			        
			           <div class="col-md-3">
			                <label>Advertisement Number</label> 
			                 <input type="text" name="AdNo" value="<% if(exam!=null && exam.getAdvNo()!=null){ %><%=exam.getAdvNo() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Advertisement No"  required="required" >
			            	
			            </div>	
			            
			         <div class="col-md-3">
			                <label>Advertisement Date</label> 
			                <input  class="form-control date"  id="Advdate" name="AdDate"    placeholder=""  required="required"  > 
			            	
			            </div>	
			            
			            
			    
			          
			          
			        </div>
			    </div>
			
			     <div class="form-group">
			        <div class="row">
			        
			        
			         <div class="col-md-3">
			                <label>Name of Organization</label> 
			                 <input type="text" name="OrgName" value="<% if(exam!=null && exam.getOrganizationName()!=null){ %><%=exam.getOrganizationName() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Organisation Name"  required="required" >
			            	
			            </div>	
			            
			         <div class="col-md-3">
			                <label>Place</label> 
			                 <input type="text" name="Place" value="<% if(exam!=null && exam.getPlace()!=null){ %><%=exam.getPlace() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Place Name"  required="required" >
			            	
			            </div>	
			            
			             <div class="col-md-3">
			                <label>Name of the post </label>
			                <input type="text" id="" name="PostName" value="<% if(exam!=null && exam.getPostName()!=null){ %><%=exam.getPostName() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter Post Name"  required="required" >
			                    
			            </div>
			            
			           <div class="col-md-3">
			                <label>Post Code / Post No</label>
			                <input type="text" id="" name="PostCode"   value="<% if(exam!=null && exam.getPostCode()!=null){ %><%=exam.getPostCode() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter PostCode/PostNo"  required="required"  >
			                   
			            </div>
			     
			            
			          
			            
			          </div>
			     </div>
			   
			  <div class="form-group">
			   <div class="row">
			   
			   <div class="col-md-2">
			                <label>Pay Level</label>
			                <input type="text" id="" name="PayLevel"   value="<% if(exam!=null && exam.getPayLevel()!=null){ %><%=exam.getPayLevel() %><%} %>"
			                    class=" form-control input-sm " placeholder="Enter PayLevel"  required="required"  >
			                   
			            </div>
			            
			             <div class="col-md-3">
			                <label>Application through</label><br>
			                 <select class="form-control select2" name="ApplThrough" required="required">
			                  <option value="" selected="selected" disabled="disabled">Select</option>
								
								<option value="Online"  <% if(exam!=null && exam.getApplicationThrough().toString().equalsIgnoreCase("Online")){ %>selected<%} %> >Online</option>
								<option value="Offline"  <% if(exam!=null && exam.getApplicationThrough().toString().equalsIgnoreCase("Offline")){ %>selected<%} %>  >Offline</option>
								
							</select>
			            </div>
			   
			   
			   
			   
			   
			     </div>
			   </div>
			  
			    
			<div class="col-12" align="center">
			         <% if(exam!=null){%>
			    	<button type="submit" class="btn btn-sm update-btn"  name="action" value="submit" onclick="return confirm('Are You Sure To Update')">Update</button>
			    	<input type="hidden" name="ExamId" value="<%=exam.getExamId() %>" >
			    	
			    	<%}else{ %> 
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

$('#Advdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	<%if(exam!=null && exam.getAdvDate()!=null){ %>
	"startDate" : new Date("<%=exam.getAdvDate() %>"),
	
	<%}%>
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

$('#probabledate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	//"minDate" :new Date(), 
	<%if(exam!=null && exam.getProbableDate()!=null){ %>
	"startDate" : new Date("<%=exam.getProbableDate() %>"),
	<%}%>
	
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

  
</script>

</body>
</html>
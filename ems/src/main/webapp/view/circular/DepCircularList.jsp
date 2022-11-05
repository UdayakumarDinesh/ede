
<%@page import="java.util.List"%>
<%@ page import="java.util.*" %> 
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<style type="text/css">
.row {
   
    display: flex;
    flex-wrap: wrap;
    margin-right: -32px;
    margin-left: -15px;
}


</style>
</head>
<body>

<%	
	String LoginType = (String)request.getAttribute("LoginType");
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	Object[] DepType = (Object[])request.getAttribute("DepType");
	
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5><%=DepType[2] %>&nbsp;Circular List</h5>
			</div>
				<div class="col-md-8 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CircularDashBoard.htm"> Circular </a></li>
						<li class="breadcrumb-item active" aria-current="page"><%=DepType[2] %>&nbsp;Circular List</li>
					</ol>
				</div>
	
	</div>	
<%	String ses=(String)request.getParameter("result"); 
 	String ses1=(String)request.getParameter("resultfail");
	if(ses1!=null)
	{
	%>
	<div align="center">
		<div class="alert alert-danger" role="alert">
        	<%=ses1 %>
        </div>
   	</div>
	<%}if(ses!=null){ %>
	<div align="center">
		<div class="alert alert-success" role="alert" >
        	<%=ses %>
        </div>
    </div>
	<%} %>
		
		 
	    	<div class="card" >
	        <div class="card-header" style="height: 4rem">
	              <form action="DepCircularList.htm" method="POST"> 
					      <div class="row justify-content-right">
					      <div class="col-7"></div>
						        <div class="col-2" style="margin-left: 1%; font-color:black;"><h6>From Date :  &nbsp;</h6></div>
					            <div class="col-1" style="margin-left: -9%"> 
						             <input type="text" style="width: 165%; background-color:white; text-align: left;"  class="form-control input-sm mydate" onchange="this.form.submit()" <%if(fromdate!=null){%> value="<%=DateTimeFormatUtil.SqlToRegularDate(fromdate)%>" <%}%>  readonly="readonly"  value=""  id="fromdate" name="FromDate"  required="required"   > 
							         <label class="input-group-addon btn" for="testdate"></label>              
						        </div>
							 
						
						        <div class="col-2"  style="margin-left: 4%"><h6>To Date : &nbsp;</h6></div>
							    <div class="col-1" style="margin-left: -10%">						
							         <input type="text" style="width: 165%; background-color:white;" class="form-control input-sm mydate" onchange="this.form.submit()" <%if(todate!=null){%>value="<%=DateTimeFormatUtil.SqlToRegularDate(todate)%>" <%}%> readonly="readonly"   value=""  id="todate" name="ToDate"  required="required"  > 							
							         <label class="input-group-addon btn" for="testdate"></label>    
							    </div>
				          </div>      
				       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	                   <input type="hidden" name="id" value="<%=DepType[0]%>"> 	
                  </form>	      
	       </div>    	             
	       </div>
    

<div class="card-body main-card">
       
        <form action="#" method="POST" id="circularForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:4%">Select</th>
					   <th style="width:10%">Circular No</th>
                       <th style="width:11%">Date</th>
                       <th style="width:45%">Subject</th>
                       <th style="width:8%">Download</th>
                  	</tr>
				</thead>
				<tbody>
				     <%
				        List<Object[]> allList=(List<Object[]>)request.getAttribute("circulatlist");
				        if(allList!=null){ int slno=0;  
                         for(Object[] ls:allList ){ 
                        %> 
                     <tr>
                      
                         <td style="text-align: center;"> <input type="radio" name="circularId" value="<%=ls[0]%>"></td>
                         <td><%=ls[2]%></td>
                         <td style="text-align: center;"><%=DateTimeFormatUtil.SqlToRegularDate(ls[4].toString())%></td>
                         <td><%=ls[3]%></td>
                         <td>
                         <button type="submit" class="btn btn-sm" name="CircularId" value="<%=ls[0] %>" formaction="DepCircularDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" formtarget="_blank">
						 <i class="fa-solid fa-download " style="color: green;"></i>
						 </button></td>
                         
                       </tr>
                           <%} }%>
                          
			    </tbody>
		     </table>
		     
		      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		      </div>
 
   			<div class="row text-center">
			  <div class="col-md-12">	
			  		<%if(LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P")){ %>				
						<button type="submit" class="btn btn-sm add-btn" formaction="DepCircularAdd.htm"  >ADD</button>
				        <button type="submit" class="btn btn-sm edit-btn" formaction="DepCircularEdit.htm" name="action" value="EDITCIR"  Onclick="Edit(circularForm)" >EDIT </button>	
						<button type="submit" class="btn btn-sm delete-btn" formaction="DepCircularDelete.htm" name="action" value="DELETECIR"  Onclick="Delete(circularForm)" >DELETE </button>
					<%} %>																	 
					<button type="submit" class="btn btn-sm search-btn" formaction="DepCircularSearch.htm" name="action" style="background-color:green;color:white">SEARCH </button>
					<input type="hidden" name="id" value="<%=DepType[0]%>">																 
				</div>						 
			</div>	
			
			</form>	
			

			</div>

   </div>


<script type="text/javascript">

$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	"cancelClass" : "btn-default",
	showDropdowns : true,
	locale : {
		format : 'DD-MM-YYYY'
	}
});

	
	$('#todate').daterangepicker({
		"singleDatePicker" : true,
		"linkedCalendars" : false,
		"showCustomRangeLabel" : true,
		"minDate" :$("#fromdate").val(),  
		"cancelClass" : "btn-default",
		showDropdowns : true,
		locale : {
			format : 'DD-MM-YYYY'
		}
	});
	
	
	$(document).ready(function(){
		   $('#fromdate, #todate').change(function(){
		       $('#myform').submit();
		    });
		});
	
	
	
function Edit(circularForm)
{
	var fields = $("input[name='circularId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Circular ");
        event.preventDefault();
		return false;
	}
	return true;
	
}
	   	   
	   

function Delete(circularForm){ 
	
	var fields = $("input[name='circularId']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One Circular");
		event.preventDefault();
		return false;
	}
	
	var cnf = confirm("Are You Sure To Delete!");
    if(cnf){
		
		document.getElementById("circularForm").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	}
}

</script>

</body>
</html>
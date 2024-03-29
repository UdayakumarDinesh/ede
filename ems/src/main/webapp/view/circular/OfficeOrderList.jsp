
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

<%String LoginType = (String)request.getAttribute("LoginType"); %>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Office Order List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="OfficeOrder.htm">Office Order List</a></li>
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
		
	<%String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate"); %>
		 
	    	<div class="card" >
		        <div class="card-header" style="height: 4rem">
		        	<form action="OfficeOrder.htm" method="POST"> 
		            	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="row justify-content-right">
						      <div class="col-7"></div>
							        <div class="col-2" style="margin-left: 1%; font-color:black;"><h6>From Date :  &nbsp;</h6></div>
						            <div class="col-1" style="margin-left: -9%"> 
							             <input type="text" style="width: 165%; background-color:white; text-align: left;"  class="form-control input-sm mydate" onchange="this.form.submit()" <%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>  readonly="readonly"  value=""  id="fromdate" name="fromdate"  required="required"   > 
								         <label class="input-group-addon btn" for="testdate"></label>              
							        </div>
								 
							
							        <div class="col-2"  style="margin-left: 4%"><h6>To Date : &nbsp;</h6></div>
								    <div class="col-1" style="margin-left: -10%">						
								         <input type="text" style="width: 165%; background-color:white;" class="form-control input-sm mydate" onchange="this.form.submit()" <%if(todate!=null){%>value="<%=todate%>" <%}%> readonly="readonly"   value=""  id="todate" name="todate"  required="required"  > 							
								         <label class="input-group-addon btn" for="testdate"></label>    
								    </div>
					  	</div>       	
	                  </form>	      
		       </div>    	             
	       </div>
    

<div class="card-body main-card">
       
        <form action="#" method="POST" id="OfficeOrderForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					   <th style="width:4%">Select</th>
					   <th style="width:10%">Order No</th>
                       <th style="width:11%">Date</th>
                       <th style="width:45%">Subject</th>
                       <th style="width:8%">Download</th>
                  	</tr>
				</thead>
				<tbody>
				     <%
				        List<Object[]> allList=(List<Object[]>)request.getAttribute("officelist");
				        if(allList!=null){ int slno=0;  
                         for(Object[] ls:allList ){ 
                        %> 
                     <tr>
                      
                         <td style="text-align: center;"> <input type="radio" name="OrderId" value="<%=ls[0]%>"></td>
                         <td><%=ls[1]%></td>
                         <td style="text-align: center;"><%=ls[2]%></td>
                         <td><%=ls[3]%></td>
                         <td style="text-align: center;" >
                         <button type="submit" class="btn btn-sm" name="OrderId" value="<%=ls[0] %>" formaction="OfficeOrderDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
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
					<button type="submit" class="btn btn-sm add-btn" formaction="OfficeOrderAdd.htm"  >ADD</button>
			        <button type="submit" class="btn btn-sm edit-btn" formaction="OfficeOrderEdit.htm" name="action" value="EDITCIR"  Onclick="Edit(OfficeOrderForm)" >EDIT </button>	
					<button type="submit" class="btn btn-sm delete-btn" formaction="OfficeOrderDelete.htm" name="action" value="DELETECIR"  Onclick="Delete(OfficeOrderForm)" >DELETE </button>
					<%} %>																	 
					<button type="submit" class="btn btn-sm search-btn" formaction="OfficeOrderSearch.htm" name="action" style="background-color:green;color:white">SEARCH </button>																 
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
	
	
	
function Edit(OfficeOrderForm)
{
	var fields = $("input[name='OrderId']").serializeArray();

	if (fields.length === 0) {
		alert("Please Select Atleast One Office Order ");
        event.preventDefault();
		return false;
	}
	return true;
	
}
	   	   
	   

function Delete(OfficeOrderForm){ 
	
	var fields = $("input[name='OrderId']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One Office Order");
		event.preventDefault();
		return false;
	}
	
	var cnf = confirm("Are You Sure To Delete!");
    if(cnf){
		
		document.getElementById("OfficeOrderForm").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	}
}

</script>

</body>
</html>
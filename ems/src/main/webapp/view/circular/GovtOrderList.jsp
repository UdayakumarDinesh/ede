
<%@page import="java.util.List"%>
<%@ page import="java.util.*" %> 
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<% String LoginType = (String)request.getAttribute("LoginType"); %>
 

<%if(LoginType!=null){ %>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

<%}else{ %>

<jsp:include page="../static/dependancy.jsp"></jsp:include>

<spring:url value="../../webresources/css/LoginPage.css" var="loginPageCss" />
<link href="${loginPageCss}" rel="stylesheet" />

<%} %>

<meta charset="ISO-8859-1">
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
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
	
	List<Object[]> allList=(List<Object[]>)request.getAttribute("GovtOrdersList");
	List<Object[]> TopicList=(List<Object[]>)request.getAttribute("TopicList");
	String TopicId = (String)request.getAttribute("TopicId");
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-4">
				<h5>Government Orders</h5>
			</div>
			<%if(LoginType!=null){ %>
				<div class="col-md-8 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active" aria-current="page">Government Orders </li>
					</ol>
				</div>
			<%}else{ %>
				<div class="col-md-7 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="login"><i class=" fa-solid fa-house-chimney fa-sm"></i> Login</a></li>
					</ol>
				</div>
			<%} %>
	
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
		
		 <br>
	    	<div class="card" >
	       		<div class="card-header" style="height: 4rem">
	              <form action="GovtOrdersList.htm" method="POST"> 
					      	<div class="row justify-content-right">
					      	<div class="col-4"></div>
					     	<div class="col-1.5" style="font-color:black;"><h6>Topic :  &nbsp;</h6></div>
							<div class="col-2" >
								<select class="form-control select2" name="TopicId" onchange="this.form.submit();">
									<option value="A" <%if(TopicId.equalsIgnoreCase("A")){ %>selected <%} %>>All</option>
									
									<%for(Object[] topic : TopicList){ %>
										<option value="<%=topic[0]%>"  <%if(TopicId.equalsIgnoreCase(topic[0].toString())){ %>selected <%} %> ><%=topic[2]%></option>
									<%} %>
									
								</select>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</div>
						        <div class="col-2" style="margin-left: 1%; font-color:black;"><h6>From Date :  &nbsp;</h6></div>
					            <div class="col-1" style="margin-left: -9%"> 
						             <input type="text" style="width: 165%; background-color:white; "  class="form-control input-sm mydate"  name="FromDate" <%if(fromdate!=null){%> value="<%=DateTimeFormatUtil.SqlToRegularDate(fromdate)%>" <%}%>  readonly="readonly"  id="fromdate"   required="required"   onchange="this.form.submit()"> 
							         <label class="input-group-addon btn" for="testdate"></label>              
						        </div>
							 
						
						        <div class="col-2"  style="margin-left: 4%"><h6>To Date : &nbsp;</h6></div>
							    <div class="col-1" style="margin-left: -10%">						
							         <input type="text" style="width: 165%; background-color:white;" class="form-control input-sm mydate"  name="ToDate"  <%if(todate!=null){%>value="<%=DateTimeFormatUtil.SqlToRegularDate(todate)%>" <%}%> readonly="readonly"   id="todate"  required="required"   onchange="this.form.submit()" > 							
							         <label class="input-group-addon btn" for="testdate"></label>    
							    </div>
				          </div>      
				       <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	                   <input type="hidden" name="TopicId" value="<%=TopicId%>"> 	
                  </form>	      
				</div>    	             
	       </div>
    

<div class="card-body main-card">
       
        <form action="#" method="POST" id="OrderForm">
             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
             <div class="table-responsive">
             <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable">
				<thead>
					<tr>
					<%if(LoginType!=null && (LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P"))){ %>
					   <th style="width:4%">Select</th>
					  <%}else{ %>
					  <th style="width:4%">SN</th>
					  <%} %>
					  <th style="width:10%">Order No</th>
                       <th style="width:11%">Date</th>
                       <th style="width:45%">Subject</th>
                       <th style="width:8%">Download</th>
                  	</tr>
				</thead>
				<tbody>
				     <%
				        if(allList!=null){ int slno=0;  
                         for(Object[] ls:allList ){ 
                        %> 
                     	<tr>
                      
	                     <%if(LoginType!=null && (LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P"))){ %>
						 	<td style="text-align: center;"> <input type="radio" name="OrderId" value="<%=ls[0]%>"></td>
						 <%}else{ %>
						 <td style="text-align: center;"> <%=allList.indexOf(ls)+1%></td>
						 <%} %>
                         
                         <td><%=ls[2]%></td>
                         <td style="text-align: center;"><%=DateTimeFormatUtil.SqlToRegularDate(ls[4].toString())%></td>
                         <td><%=ls[3]%></td>
                         <td style="text-align: center;">
                         <button type="submit" class="btn btn-sm" name="OrderId" value="<%=ls[0] %>" formaction="GovtOrderDownload.htm"  formmethod="post" data-toggle="tooltip" data-placement="top" title="Download" formtarget="_blank">
						 <i class="fa-solid fa-download " style="color: green;"></i>
						 </button></td>
                         
                       </tr>
                    <%} }%>
                          
			    </tbody>
		     </table>
		     
		      <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		      </div>
 				
 				<%if(LoginType!=null){ %>
		   			<div class="row text-center">
					  <div class="col-md-12">	
					  		<%if(LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P")){ %>				
								<button type="submit" class="btn btn-sm add-btn" formaction="GovtOrderAdd.htm"  >ADD</button>
						        <button type="submit" class="btn btn-sm edit-btn" formaction="GovtOrderEdit.htm" name="action" value="EDITCIR"  Onclick="Edit(OrderForm)" >EDIT </button>	
								<button type="submit" class="btn btn-sm delete-btn" formaction="GovtOrderDelete.htm" name="action" value="DELETECIR"  Onclick="Delete(OrderForm)" >DELETE </button>
							<%} %>																	 
							<button type="submit" class="btn btn-sm search-btn" formaction="GovtOrderSearch.htm" name="action" style="background-color:green;color:white">SEARCH </button>
							<input type="hidden" name="TopicId" value="<%=TopicId%>">																 
						</div>						 
					</div>	
				<%} %>
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
	
	
	
function Edit(OrderForm)
{
	var fields = $("input[name='OrderId']").serializeArray();

	if (fields.length === 0) {
		
		alert("Please Select Atleast One Order ");
        event.preventDefault();
		return false;
	}
	return true;
	
}
	   	   
	   

function Delete(OrderForm){ 
	
	var fields = $("input[name='OrderId']").serializeArray();

	if (fields.length === 0){
		alert("Please Select Atleast One Order");
		event.preventDefault();
		return false;
	}
	
	var cnf = confirm("Are You Sure To Delete!");
    if(cnf){
		
		document.getElementById("OrderForm").submit();
		return true;

	}else{
		
		event.preventDefault();
		return false;
	}
}

</script>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<%@page import="java.util.List"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contPath" value="${pageContext.request.contextPath}"/>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>CHSS Circular List</title>
<jsp:include page="../static/dependancy.jsp"></jsp:include>

<style type="text/css">
.group
{
    text-align: center;
    color: #3498DB;
    text-shadow: 0px 0px 1px #3A3B3C;
    text-decoration: underline;
}

</style>

</head>
<body>
<%
	List<Object[]> circulatlist = (List<Object[]> )request.getAttribute("circulatlist") ;
	String fromdate = (String)request.getAttribute("fromdate");
	String todate = (String)request.getAttribute("todate");
%>

<h3 class="group">CIRCULAR LIST</h3>
		<div class="card" >
			<div class="card-header" style="height: 4rem">
					<form action="CircularLists.htm" method="POST">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="row justify-content-end">
					
						     <div class="col-2"  align="right"><h6>From Date :</h6></div>
					         <div class="col-1"> 
								    <input type="text" style="width: 145%;"  class="form-control input-sm mydate"  onchange="this.form.submit()" readonly="readonly" <%if(fromdate!=null){%> value="<%=fromdate%>" <%}%>   id="fromdate" name="fromdate"  required="required"  > 
								    <label class="input-group-addon btn" for="testdate"></label>              
							 </div>
							 
							  <div class="col-2" align="right" ><h6>To Date :</h6></div>
							  <div class="col-1">						
								     <input type="text" style="width: 145%;"  class="form-control input-sm mydate" onchange="this.form.submit()" readonly="readonly" onchange="this.form.submit()" <%if(todate!=null){%>value="<%=todate%>" <%}%>   id="todate" name="todate"  required="required"  > 							
							 		 <label class="input-group-addon btn" for="testdate"></label>    
							 </div>

							 <div class="col-3" align="right"></div>			
					</div>
							 
				   </form>
				
				</div>
			
			
			
			
			
			
				<div class="card-body " >
				
					<form action="##" method="POST" id="empForm" >
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive" align="center">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable" style="width: 80%;"> 
								<thead>
									<tr>
										<th>Select</th>
										<th>Description </th>
										<th>Circular Date</th>
										<th>Valid Till</th>
										<th> Action</th>
									</tr>
								</thead>
								<tbody>
									<%if(circulatlist!=null && circulatlist.size()>0){ 
										for(Object[] obj : circulatlist){
									%>
										<tr>
											<td style="text-align:center;  width: 5%;"> <input type="radio" name="circulatId" value="<%=obj[0]%>"> </td>
											<td style="text-align:justify; width: 70%;"><%=obj[1]%></td>
											<td style="text-align:justify; width: 10%;"><%if(obj[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[3].toString())%> <%} %></td>
											<td style="text-align:justify; width: 10%;"><%if(obj[4]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(obj[4].toString())%> <%} %></td>
											<td style="text-align:center;  width: 5%;"> <%if(obj[2]!=null){ %> 
											<button type="submit" class="btn btn-sm" name="path" value="<%=obj[2]%>//<%=obj[5] %>" formaction="download-CircularFile-attachment" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
											  <i style="color: #019267" class="fa-solid fa-download"></i>
										    </button>
											<%}else{%>--<%}%>
											</td>
										</tr>
								<%} }%>
								</tbody>
							</table>
							
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
					
					
					<div class="row text-center">
						<div class="col-md-12">
						
						<a href="login.jsp"   class="btn btn-sm  btn-info">HOME</a>
								
					    </div>						 
					</div>
					
			   </form>		
			  </div>
		   	 </div>				
	        </div>
	        </div>
</body>

<script type="text/javascript">
$('#fromdate').daterangepicker({
	"singleDatePicker" : true,
	"linkedCalendars" : false,
	"showCustomRangeLabel" : true,
	/* "minDate" :datearray,   */
	/* "startDate" : fdate, */
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

	</script>
</html>
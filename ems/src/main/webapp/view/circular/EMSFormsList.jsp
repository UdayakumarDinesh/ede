
<%@page import="com.vts.ems.circularorder.model.EMSForms"%>
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
<meta charset="ISO-8859-1">

<% String LoginType = (String)request.getAttribute("LoginType"); %>
 

<%if(LoginType!=null){ %>
<jsp:include page="../static/header.jsp"></jsp:include>

<%}else{ %>

<jsp:include page="../static/dependancy.jsp"></jsp:include>
<spring:url value="../../webresources/css/LoginPage.css" var="loginPageCss" />
<link href="${loginPageCss}" rel="stylesheet" />

<%} %>

</head>
<body>

<%
 
List<Object[]> DepTypeList=(List<Object[]>)request.getAttribute("DepTypeList");
List<Object[]> FormsList=(List<Object[]>)request.getAttribute("FormsList");
String DepTypeId = (String)request.getAttribute("DepTypeId"); 
%>

<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>Forms</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item active">Forms</li>
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

		<div class="card">
			<div class="card-header" style="height: 4rem">
				<div class="row " >
					<div class="col-9"></div>
					<div class="col-1.5" style="font-color:black;"><h6>Department :  &nbsp;</h6></div>
					<div class="col-2" >
						<form action="EMSForms.htm" method="post">
							<select class="form-control select2" name="DepTypeId" onchange="this.form.submit();">
								<option value="A" <%if(DepTypeId.equalsIgnoreCase("A")){ %>selected <%} %>>All</option>
								
								<%for(Object[] deptype : DepTypeList){ %>
									<option value="<%=deptype[0]%>"  <%if(DepTypeId.equalsIgnoreCase(deptype[0].toString())){ %>selected <%} %> ><%=deptype[2]%></option>
								<%} %>
								
							</select>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</form>
					</div>
				</div>

			</div>
		</div>


		<div class="card-body main-card">
       
        <form action="#" method="POST" id="Form">
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
					   <th style="width:10%">Form No</th>
                       <th style="width:45%">Description</th>
                       <th style="width:8%">Download</th>
                  	</tr>
				</thead>
				<tbody>
				     <%
				        int slno=0;  
                        for(Object[] form:FormsList ){ 
                     %> 
                     <tr>
                      
                      <%if(LoginType!=null && (LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P"))){ %>
					  		<td style="text-align: center;"> <input type="radio" name="EMSFormId" value="<%=form[0] %>" required="required" > </td>
					 	<%}else{ %>
					 		<td style="text-align: center;"> <%=FormsList.indexOf(form)+1 %> </td>
					 	<%} %>
                         
                         <td style="text-align: center;" ><%=form[2]%></td>
                         <td><%=form[3]%></td>
                         <td> 
                         <button type="submit" class="btn btn-sm" name="EMSFormId" value="<%=form[0] %>" formaction="EMSFormDownload.htm"  formnovalidate="formnovalidate" formmethod="post" data-toggle="tooltip" data-placement="top" title="Download">
						 	<i class="fa-solid fa-download " style="color: green;"></i>
						 </button></td>
                         
                    </tr>
                   <%} %>
                          
			    </tbody>
		     </table>
		     <input type="hidden" name="DepTypeId" value="<%=DepTypeId%>">
		     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		     </div>
 
   			<div class="row text-center">
			  <div class="col-md-12">	
			  		<%if(LoginType!=null && (LoginType.equalsIgnoreCase("A") || LoginType.equalsIgnoreCase("P"))){ %>	
			  					
						<button type="submit" class="btn btn-sm add-btn" formaction="EMSFormAdd.htm" formnovalidate="formnovalidate"  >ADD</button>
						<button type="submit" class="btn btn-sm delete-btn" formaction="Delete.htm"    >DELETE </button>
						
						<!-- <button type="submit" class="btn btn-sm edit-btn" formaction="Edit.htm" name="action" value="EDITCIR"  Onclick="Edit(Form)" >EDIT </button> -->
						
					<%} %>																	 
					<!-- <button type="submit" class="btn btn-sm search-btn" formaction="Search.htm" name="action" style="background-color:green;color:white">SEARCH </button> -->																 
				</div>						 
			</div>	
			
			</form>	
			

			</div>

   </div>


<script type="text/javascript">

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
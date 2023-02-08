<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="java.util.List" %>
 <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
<title>Publication</title>
</head>
<body>
<body>

<%
List<Object[]> publicationlist = (List<Object[]>)request.getAttribute("Publicationlist");
Object[] empdata = (Object[])request.getAttribute("Empdata");
%>
<div class="card-header page-top">
	  <div class="row">
			<div class="col-md-6">
				<h5>Publication List <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item" ><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item" aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active" aria-current="page">Publication List</li>
					</ol>
				</div>
		</div>
</div>	


 <div class="page card dashboard-card">
	<div class="card-body">	
		
	<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		
		if(ses1!=null){%>
			<div class="alert alert-danger" role="alert">
				<%=ses1%>
			</div>
			
		<%}if(ses!=null){%>
			<div class="alert alert-success" role="alert">
				<%=ses%>
			</div>
		<%}%>
	</div>
		
			<div class="card" >
				<div class="card-body " >
					<form action="PublicationAddEditDelete.htm" method="post" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
							 <thead> 
                                   <tr>            
	                                    <th>Select</th>
	                                    <th>Publication Type</th>
	                                    <th>Author</th>
	                                    <th>Discipline</th>
	                                    <th>Title</th>
	                                    <th>Country</th>
	                                    <th>Publication Date</th>
                                   </tr>
                               </thead>
                               <tbody>
                              <%for(Object[] Publication:publicationlist){%>
	                          <tr> 
	                             <td align="center"> <input type="radio" name="PublicationId"  value="<%=Publication[1]%>" ></td>
                                 <td> <%=Publication[2]%> </td>
                                 <td> <%=Publication[3]%> </td>
                                 <td> <%=Publication[4]%> </td>
                                 <td> <%=Publication[5]%> </td>
                                 <td> <%=Publication[6]%> </td>
                                 <td align="center"> <%if(Publication[7]!=null){%> <%=DateTimeFormatUtil.SqlToRegularDate(Publication[7].toString())%> <%}%> </td>
                             </tr>
                             <%}%>
                           </tbody> 
							</table>
							<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
						</div>
	
	              <div class="row text-center">
						<div class="col-md-12">
						
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
							<!-- <button type="submit" class="btn btn-sm delete-btn" name="Action" value="Delete"  Onclick="Delete(empForm)" >DELETE </button>  -->
						</div>
						 
					</div>
				<!-- 	<div  class="row text-center" id = "dis" style="margin-top:20px;" align="center">
					<div class="col-md-12">
				 	<button type="submit" class="btn btnclr"  style="background-color: #750550; color:#fff;" name="family" value="family" disabled="disabled" >Family</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" >Education</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" >Appointment</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" >Awards</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" >Property</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" >Address</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" > Publication</button>
					<button type="submit" class="btn btnclr" Onclick="Edit(empForm)" disabled="disabled" >Passport</button>
					</div>
				</div> --> 
					<input type="hidden" name="empid" value="<%=empdata[2]%>">
				</form>	
			</div>

			</div>		
		
	</div>

 </div>
 <script type="text/javascript">
 
 function Edit(myfrm) {
		var fields = $("input[name='PublicationId']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One ");

			event.preventDefault();
			return false;
		}
		return true;
	}

	function Delete(myfrm) {

		var fields = $("input[name='PublicationId']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One ");
			event.preventDefault();
			return false;
		}
		var cnf = confirm("Are You Sure To Delete!");

		if (cnf) {

			return true;

		} else {
			event.preventDefault();
			return false;
		}
	}
 </script>
</body>
</html>
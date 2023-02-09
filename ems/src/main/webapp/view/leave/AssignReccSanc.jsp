<%@page import="com.ibm.icu.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>

</head>
<body >
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-3">
				<h5>ASSIGN RECC-SANC</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="LeaveDashBoard.htm">Leave</a></li>
						<li class="breadcrumb-item active " aria-current="page">Assign Recc-Sanc</li>
					</ol>
				</div>
			</div>
	</div>
<%
List<Object[]> AllRaSaAssignEmployee=(List<Object[]>)request.getAttribute("AllRaSaAssignEmployee");
%> 
<%
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

			 
   <div class="card-body" >
     	    <div class="row" style="margin-top:7px;"> 
	    <div class="col-md-12">
	    	            <form action="assign-recc-sanc.htm" method="post">
	 <div class="table-responsive">

		<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 

                              <thead> 
                                <tr>            
                                   <th>Select</th>
                                 <!--  <th>PunchCard No</th>  -->
                                   <th>Employee Name</th>
                                   <th>Leave Recc Officer</th>
                                   <th>Leave Sanc Officer</th>
                                   <th>Leave Status</th>
                                  </tr>
                               </thead>
                               <tbody>
                              <%for(Object[] ls:AllRaSaAssignEmployee){%>
	                            <tr> 
	                             <td><input type="radio" name="selecRadioForEmpid" required value="<%=ls[0]%>"> </td>
                               <%--  <td ><%if(ls[12]!=null){out.println(ls[12]);}else{out.println("Not Assign");}%></td>--%>
                                  <td style='text-align:left;'><%=ls[1]%></td>
                                 <td style='text-align:left;'><%=ls[2] %></td>
                                 <td style='text-align:left;'><%=ls[3]%></td>
                                 <td><%=ls[4]%></td>
                                </tr>   
                              <% } %>
                            
                            </tbody> 
                         </table>
        
  
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
           <button type="submit" name="Add" value="Add" class="btn btn-primary " style="margin-left: 42%;" formnovalidate="formnovalidate">Add</button>
           <%if(AllRaSaAssignEmployee!=null&&AllRaSaAssignEmployee.size()>0){%>
           <button type="submit" name="ViewDetails" value="ViewDetails" class="btn btn-info">View Details</button>
           <%}%>
     

     </form>




	   </div>
	  


	   </div>
	   </div>
	   </div>
	   



				

</body>
</html>
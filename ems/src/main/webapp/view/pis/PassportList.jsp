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
<title>Passport/Foreign Visit</title>
</head>
<body>
<body>

<%
List<Object[]> foreignvisitlist = (List<Object[]>)request.getAttribute("PassportVisitList");
Object[] empdata = (Object[])request.getAttribute("Empdata");
Object[] passportdetails=(Object[])request.getAttribute("PassportList");
%>
<div class="card-header page-top ">
		<div class="row">
			<div class="col-md-6">
				<h5>Passport/Foreign Visit <small><b>&nbsp;&nbsp; - &nbsp;&nbsp;<%if(empdata!=null){%><%=empdata[0]%> (<%=empdata[1]%>)<%}%></b></small></h5>
			</div>
				<div class="col-md-6">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item" ><a href="PisAdminDashboard.htm">Admin</a></li>
						<li class="breadcrumb-item" aria-current="page"><a href="PisAdminEmpList.htm">Employee List</a></li>
						<li class="breadcrumb-item active" aria-current="page">Passport/Foreign Visit List</li>
					</ol>
				</div>
			</div>
	</div>	


 <div class="page card dashboard-card">
	 

<div class="card-body" >		
			<div align="center">
		<%String ses=(String)request.getParameter("result"); 
		String ses1=(String)request.getParameter("resultfail");
		if(ses1!=null){ %>
			<div class="alert alert-danger" role="alert">
				<%=ses1 %>
			</div>
			
		<%}if(ses!=null){ %>
			
			<div class="alert alert-success" role="alert">
				<%=ses %>
			</div>
		<%} %>
	</div>
		
			<div class="card" >		
				<div class="card-body " >
					
					<h5>Passport</h5>
					<hr>
					<%if(passportdetails!=null){ %>
					<table class="table table-hover table-striped  table-condensed  table-bordered"  id="">
					   <thead> 
                                 <tr>            
	                                    <th>Passport Type</th>
	                                    <th>Valid From</th>
	                                    <th>Valid To</th>
	                                    <th>Passport No.</th>
	                                    <th>Edit</th>
                                 </tr>
                      </thead>
                      <tbody>
                           <tr> 
		                            <td align="center"><%=passportdetails[1]%></td> 
	                                <td align="center"><%if(passportdetails[2]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(passportdetails[2].toString())%><%}else{%>--<%}%></td> 
	                                <td align="center"><%if(passportdetails[3]!=null){%><%=DateTimeFormatUtil.SqlToRegularDate(passportdetails[3].toString())%><%}else{%>--<%}%></td> 
	                                <td align="center"><%=passportdetails[4]%></td> 
	                                <td style="padding-top:5px; padding-bottom: 5px;" align="center">
										<form action="AddEditPassport.htm" method="GET">
										 <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
									     <button type="submit" class="btn btn-sm" name="Action" value="PassportEdit"  data-toggle="tooltip" data-placement="top" title="Edit">
										 <i class="fa-solid fa-pen-to-square" style="color: #E45826"></i></button>	
									     </form>
									</td> 
                            </tr>
                       </tbody> 
					</table>
						<%}else{%>
				<form action="AddEditPassport.htm" method="GET">
                     <button  type="submit" name="Action" value="AddPassport"  class="btn btn-sm add-btn" style="margin-bottom:12px;" > Add Passport</button>
                     <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
                </form>
					<%}%>		
					</div>
					</div>					
	
					<div class="card">					
					<div class="card-body">
							<h5>Foreign Visit List</h5>
					<hr>
					<form action="ForeignVisitAddEdit.htm" method="POST" id="empForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable2"> 
				   <thead> 
                                <tr>            
                                   <th>Select</th>
                                   <th>Country Name</th>
                                   <th>NOC Letter No</th>
                                   <th>NOC Issued From</th>
                                   <th>Purpose</th>
                                </tr>
                               </thead>
                               <tbody>
	                             <%if(foreignvisitlist!=null&&foreignvisitlist.size()>0){
	                             for(Object[] ls:foreignvisitlist){ %>
	                          <tr> 
	                             <td align="center"><input type="radio" name="PassportVisitId"  value="<%=ls[0]%>" ></td>
                                 <td><%=ls[2]%></td>
                                 <td><%=ls[3]%></td>
                                 <td><%=ls[4]%></td>
                                 <td><%=ls[5]%></td>
                             </tr>
                         <%}}%>   
                           </tbody> 
                          </table>
					</div>		
						<div class="row text-center">
						<div class="col-md-12">
						     <input type="hidden" name="empid" value="<%if(empdata!=null){%><%=empdata[2]%><%}%>">
							<button type="submit" class="btn btn-sm add-btn" name="Action" value="ADD"   >ADD </button>
							<button type="submit" class="btn btn-sm edit-btn" name="Action" value="EDIT"  Onclick="Edit(empForm)" >EDIT </button>
					    	<!--  <button type="submit" class="btn btn-sm delete-btn" name="Action" value="DELETE" Onclick="Delete(empForm)" >DELETE </button> -->
					    </div>
					    </div>
				</form>	
			</div>		
		</div>
	</div>
 </div>
 <script type="text/javascript">
 
 function Edit(myfrm) {
		var fields = $("input[name='PassportVisitId']").serializeArray();

		if (fields.length === 0) {
			alert("Please Select Atleast One ");

			event.preventDefault();
			return false;
		}
		return true;
	}

	function Delete(myfrm) {

		var fields = $("input[name='PassportVisitId']").serializeArray();

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
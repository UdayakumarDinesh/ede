<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.vts.ems.ithelpdesk.model.HelpdeskCategory" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ticket Category</title>
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>
 <%
 HelpdeskCategory category = (HelpdeskCategory) request.getAttribute("tickcategory");
 %>
 
 <div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
			<%if(category!=null){ %>
				<h5>Ticket Category Edit</h5>
				<%}else{ %>
				<h5>Ticket Category Add</h5>
				<%}%>
			</div>
			
				<div class="col-md-9 ">
					<ol class="breadcrumb ">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					    <li class="breadcrumb-item "><a href="MasterDashBoard.htm">IT Support </a></li> 
						<li class="breadcrumb-item "><a href="DivisionGroup.htm">Ticket Category List </a></li>
						<%if(category!=null){ %>						
						<li class="breadcrumb-item active " aria-current="page">Ticket Category Edit</li>
						<%}else{ %>
						<li class="breadcrumb-item active " aria-current="page">Ticket Category Add</li>
						<%} %>
					</ol>
				</div>
			</div>
		 </div>
		 <div class="page card dashboard-card">
	<div class="card-body" >			
			<div class="card" >
				<div class="card-body " align="center" >
					<%if(category!=null){ %>
					<form name="myfrm" action="TicketCategoryEdit.htm" method="POST" id="addfrm1" autocomplete="off"  enctype="multipart/form-data" >	
						<%}else{%>
					<form name="myfrm" action="TicketCategoryAdd.htm" method="POST" id="addfrm1" autocomplete="off"   >	
						<%}%>
						<div class="form-group">
							<div class="table-responsive">
								<table	class="table table-bordered table-hover table-striped table-condensed " style="width: 65%;">								
										<tr>
											<th><label>Ticket Category<span class="mandatory"	style="color: red;">*</span></label></th>
											<td><input class="form-control form-control" style="text-transform:capitalize"
												placeholder=" Enter Ticket Category" type="text" id="ticketCategory" name="ticketCategory" value="<%if(category!=null){ %><%=category.getTicketCategory()%><%} %>"
												required="required" maxlength="200" style="font-size: 15px;"></td>
										</tr>
										
								</table>
							</div>
						</div>

						<div class="row" style="margin-left: 47%;" align="center">
							<div id="UsernameSubmit">
							<%if(category!=null){ %>
							<input type="hidden" id="divgrpid" name="divgrpid" value="<%=category.getTicketCategoryId()%>">
								<button type="submit" class="btn btn-sm submit-btn"
									
									name="action"  value="EDIT">SUBMIT</button>
									<%}else{ %>
									<button type="submit" class="btn btn-sm submit-btn"
									
									name="action"  value="ADD">SUBMIT</button>
									
									<%} %>
							</div>

						</div>
						<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
								
					<%if(category!=null){ %>
					</form>
					<%}else{ %>
					</form>
					<%} %>
				</div>
	   </div>
	</div>

</div>
</body>
</html>
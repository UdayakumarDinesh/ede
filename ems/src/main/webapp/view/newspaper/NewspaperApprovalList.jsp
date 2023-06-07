<%@page import="com.itextpdf.io.util.DateTimeUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"   pageEncoding="ISO-8859-1"%>
     <%@page import="java.util.List"%>
     <%@page import="java.time.LocalDate"%>
     <%@page import="com.vts.ems.utils.DateTimeFormatUtil" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>
<jsp:include page="../static/sidebar.jsp"></jsp:include>
</head>
<body>


<%
	List<Object[]> NewspapepApproList=(List<Object[]>)request.getAttribute("NewspapepApproList");
	
	String ses = (String) request.getParameter("result");
	String ses1 = (String) request.getParameter("resultfail");
%>


	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-6">
				<h5>Pending Newspaper Claims</h5>
			</div>
				<div class="col-md-6 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
						<li class="breadcrumb-item "><a href="NewspaperDashBoard.htm"> Newspaper </a></li>
						<!-- <li class="breadcrumb-item "><a href="NewspaperApproval.htm"> Newspaper List </a></li> -->
						<li class="breadcrumb-item active " aria-current="page">Newspaper List</li>
					</ol>
				</div>
			</div>
		 </div>
	
	 	<div class="page card dashboard-card">
	 	<div align="center">
			<%
			if (ses1 != null) {
			%>
			<div class="alert alert-danger" role="alert" style="margin-top: 5px;">
				<%=ses1%>
			</div>
			<%
			}
			if (ses != null) {
			%>
			<div class="alert alert-success" role="alert"
				style="margin-top: 5px;">
				<%=ses%>
			</div>
			<%
			}
			%>
		</div>
	 	
		<form action="NewspaperApprove.htm" method="post">
			<div class="card-body" >
				
				<table  class="table table-bordered  table-striped  table-hover table-condensed " id="myTable1">
			            <thead>
			                <tr>
			                    <th>Name &amp; Designation</th>
			                    <th>Claim Month</th>
			                    <th>Claim Year</th>			        
			                    <th>Status</th>			        
			                    <th>Action</th>
			                </tr>
			            </thead>
			           
			            <tbody>
			            <%if(NewspapepApproList!=null && NewspapepApproList.size()>0){%>
			            <%for(Object[] ls:NewspapepApproList){ %>
			            
			             <tr>
			                    <td style="text-align:left;"><%= ls[2]%>,&nbsp;<%=ls[3]%></td>
			                    <td style="text-align: center;"><%=ls[4] %></td>
			                    <td style="text-align: center;"><%=ls[5] %></td>
			                    <td>
					                    	<button type="submit" class="btn btn-sm btn-link w-100"
											formaction="NewspaperApplyTransacStatus.htm" value="<%=ls[0]%>"
											name="NewspaperId" data-toggle="tooltip" data-placement="top"
											title="Transaction History" formnovalidate="formnovalidate"
											style="color: <%=ls[8]%>; font-weight: 600;"
											formtarget="_blank">
											&nbsp;
											<%=ls[7]%>
											<i class="fa-solid fa-arrow-up-right-from-square"
												style="float: right;"></i>
										</button>
					                    </td>
			                    <td align="center">
			                    		<button type="submit" class="btn btn-sm view-icon"
											formaction="NewspaperClaimPreview.htm" name="NewspaperId"
											value="<%=ls[0]%>" formnovalidate="formnovalidate"
											data-toggle="tooltip" data-placement="top"
											title="Form For Newspaper Claim"
											style="font-weight: 600;">
											<i class="fa-solid fa-eye"></i>
										</button>
			                    </td>
			                    
			                    
			           </tr>
			           <%} }%>
			           </tbody>
			          </table>
			          		     
          
      		</div>
      		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
      		<input type="hidden" name="isApproval" value="Y"/>
      	</form>
       </div>
        
<script type="text/javascript">
$("#myTable1").DataTable({
	"lengthMenu" : [ 5, 10, 25, 50, 75, 100 ],
	"pagingType" : "simple"

});

</script>


</body>
</html>
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
<body>
<%

    int currentyear= java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	List<Object[]> InventoryForwardedList=(List<Object[]>)request.getAttribute("InventoryForwardedList");
	
	
	
%>

	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>Inventory Forwarded List</h5>
			</div>
			<div class="col-md-9 ">
				<ol class="breadcrumb">
					<li class="breadcrumb-item ml-auto"><a	href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home </a></li>
					<li class="breadcrumb-item "><a href="ITInventoryDashboard.htm">Dashboard </a></li>
					<li class="breadcrumb-item active " aria-current="page">Inventory Forwarded</li>
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
	
	
<div class="card-body main-card"   >
      <form action="#" method="POST" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
				 <table class="table table-hover  table-striped table-condensed table-bordered table-fixed" id="myTable" style="width:100% ;" >
						<thead>
							<tr>
								<th style="width: 2%">SN</th>
								<th style="width: 4%">Employee </th> 
								<th style="width: 3%">Declaration Year</th>
								<th style="width: 4%"> Date</th>
								<th style="width: 4%">No of Items</th> 
								<th style="width: 3%">Total Qty</th>
								<th style="width: 5%">Status</th>
								<th style="width: 5%">Remarks</th>
								<th style="width: 5%">Action</th>																		
							</tr>
						</thead>
						<tbody>
						 
					 <% int count=0;
						String Forwarded="Forwarded";
						String Initiated="Initiated";
						String Approved ="Approved";
						String Returned="Returned";
						int total=0;
						int item=0;
						for(Object[] obj:InventoryForwardedList){
							total=(Integer.parseInt(obj[5].toString())+Integer.parseInt(obj[8].toString())+Integer.parseInt(obj[11].toString())+Integer.parseInt(obj[14].toString())
							+Integer.parseInt(obj[17].toString())+Integer.parseInt(obj[20].toString())+Integer.parseInt(obj[23].toString())+Integer.parseInt(obj[26].toString()) +Integer.parseInt(obj[29].toString()));
							
						if(Integer.parseInt(obj[5].toString())!=0){%><% item++; %><%}  if(Integer.parseInt(obj[8].toString())!=0){%><% item++; %><%} 
						if(Integer.parseInt(obj[11].toString())!=0){%><%item++; %><%}  if(Integer.parseInt(obj[14].toString())!=0){%><%item++; %><%}
						if(Integer.parseInt(obj[17].toString())!=0){%><%item++; %><%}   if(Integer.parseInt(obj[20].toString())!=0){%><%item++; %><%}
						if(Integer.parseInt(obj[23].toString())!=0){%><%item++; %><%}  if(Integer.parseInt(obj[26].toString())!=0){%><%item++; %><%}
						if(Integer.parseInt(obj[29].toString())!=0){%><%item++; %><%}
						%>
						
							
					      <tr>
							  <td style="width: 1%; text-align: center;"><%=++count %></td>
							  <td style="width: 1%; text-align: left;"><%=obj[1] %></td>
									<td style="width: 2%;text-align:center;" ><%=obj[34].toString().substring(0,4) %></td>
									<td style="width: 2%;text-align:center;"><%=rdf.format(sdf.parse(obj[32].toString())) %></td>
									<td style="width: 2%;text-align:center;"><%=item %></td>  
									<td style="width: 2%;text-align:center;"><%=total %></td>
									<td style="width: 2%;text-align:center;" ><%if(obj[33].toString().equals("I")){ %><%=Initiated %><%} else if(obj[33].toString().equals("F")){%><%=Forwarded %><%} else if(obj[33].toString().equals("R")){%><%=Returned %><%} else if(obj[33].toString().equals("A")){%><%=Approved%><%} %></td>
									<td style="width: 2%;text-align:left;word-wrap: break-word;word-break: break-all;white-space: normal !important;"><%if( !obj[35].toString().equals("")){%><%=obj[35] %><%} else{ %>-<%}%></td>
									<td style="width: 2%;text-align:center;">
									 <%if(!obj[33].toString().equals("A")) {%> 
									<button type="submit" class="btn btn-sm "  name="inventoryid" value="<%=obj[0] %>" formaction="InventoryForwardedView.htm" formmethod="POST"  data-toggle="tooltip" title="" data-original-title="Inventory Details">
									<i class="fa fa-eye " style="color: black;"></i>
									</button>
									 <%} else { %>
									<button type="submit" class="btn btn-sm " name="inventoryid" value="<%=obj[0] %>" formaction="InventoryFormDownload.htm" formmethod="GET"  data-toggle="tooltip" title="" data-original-title="Preview">
									<i style="color: #019267" class="fa-solid fa-download"></i>
									</button> 
									<%} %>  
									
									</td>
							</tr>
							
								<%item=0;}%>
						</tbody>
					</table>
					
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				
          </form>
      </div>
   </div>
   </div>

</body>

</html>
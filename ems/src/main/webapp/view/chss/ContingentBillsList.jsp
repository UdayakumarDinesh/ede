<%@page import="java.time.LocalDate"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.vts.ems.utils.DateTimeFormatUtil"%>
<%@page import="com.vts.ems.chss.model.CHSSTreatType"%>
<%@page import="com.vts.ems.pis.model.Employee"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<jsp:include page="../static/header.jsp"></jsp:include>

</head>
<body>

<%
	
	
	List<Object[]> contingentlist=(List<Object[]>)request.getAttribute("ContingentList");
	
	SimpleDateFormat sdf = DateTimeFormatUtil.getSqlDateFormat();
	SimpleDateFormat rdf = DateTimeFormatUtil.getRegularDateFormat();
	String logintype = (String)request.getAttribute("logintype");
%>
 
 <div class="col page card">
	<div class="card-header page-top">
		<div class="row">
			<div class="col-md-3">
				<h5>CHSS Contingent Approval List</h5>
			</div>
				<div class="col-md-9 ">
					<ol class="breadcrumb">
						<li class="breadcrumb-item ml-auto"><a href="MainDashBoard.htm"><i class=" fa-solid fa-house-chimney fa-sm"></i> Home</a></li>
						<li class="breadcrumb-item "><a href="CHSSDashboard.htm">CHSS</a></li>
						<li class="breadcrumb-item active " aria-current="page">CHSS Contingent List</li>
					</ol>
				</div>
			</div>
	</div>	
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
				
					<form action="ApprovedBiils.htm" method="POST" style="float: right;">
							<button class="btn btn-sm" style="background-color: #94477b ;color: white; margin-bottom: 10px;" >Approved Bills</button>
							<br>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
							
					<form action="#" method="post" id="ClaimForm">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<div class="table-responsive">
				   			<table class="table table-bordered table-hover table-striped table-condensed"  id="myTable"> 
								<thead>
									<tr>
										<td style="padding-top:5px; padding-bottom: 5px;">SNo</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Contingent No</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Date</td>
										<td style="padding-top:5px; padding-bottom: 5px;">Status</td>
										<td style="padding-top:5px; padding-bottom: 5px;">View</td>
									</tr>
								</thead>
								<tbody>
									<%long slno=0;
									for(Object[] obj : contingentlist){ 
										slno++; %>
										<tr>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;" ><%= slno%></td>
											<td style="padding-top:5px; padding-bottom: 5px;"><%=obj[1] %></td>
											<%if(obj[2]!=null){ %>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(obj[2].toString()))%></td>
											<%}else{ %>
											<td style="text-align: center;padding-top:5px; padding-bottom: 5px;"><%=rdf.format(sdf.parse(LocalDate.now().toString()))%></td>
											<%} %>
											<td 
											<%if("7".equals(obj[8].toString())||"9".equals(obj[8].toString()) ||"11".equals(obj[8].toString())||"13".equals(obj[8].toString())){%>  
										    style=" color:#d1312c; font-weight: 600;"				
											<%}else if("1".equals(obj[8].toString())){%>
											 style="  color:#2db714; font-weight: 600;"     
											<%}else if("3".equals(obj[8].toString())||"12".equals(obj[8].toString())||"6".equals(obj[8].toString())){%>
											style="  color:#588c20; font-weight: 600;"
											<%}else if("10".equals(obj[8].toString())||"4".equals(obj[8].toString())||"2".equals(obj[8].toString())){%>
												style=" color:#149694; font-weight: 600;"
											<%}else{ %>
											style=" color:#0b4980; font-weight: 600;"
											<%} %>             ><%=obj[7] %></td>
											
											
											<td style="padding-top:5px; padding-bottom: 5px;">
												
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingentBillData.htm" formmethod="post" data-toggle="tooltip" data-placement="top" title="Details">
													<i class="fa-solid fa-circle-info"></i>
												</button>	
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBill.htm" formtarget="_blank" formmethod="post" data-toggle="tooltip" data-placement="top" title="View">
													<i class="fa-solid fa-eye"></i>
												</button>	
												<button type="submit" class="btn btn-sm" name="contingentid" value="<%=obj[0] %>" formaction="ContingetBillDownload.htm" formtarget="_blank" data-toggle="tooltip" data-placement="top" title="Download">
													<i style="color: #019267" class="fa-solid fa-download"></i>
												</button>
												
											</td>
										</tr>
									<%} %>
								</tbody>
							</table>
						</div>
					</form>
					<%if(logintype.equalsIgnoreCase("K")){ %>
					<div class="row" style="margin-top: 10px;">
					
						<div class="col-12"  align="center" >
							<form action="CHSSBatchList.htm" method="post">
								<button class="btn btn-sm" style="background-color: #FFD36E " >Generate Contingent Bill</button>
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							</form>
						</div>
							
					</div>
					<%} %>
				</div>
			</div>		
			
		</div>
	
	 </div>
	


</body>
</html>